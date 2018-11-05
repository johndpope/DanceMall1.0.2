package com.hzxmkuar.wumeihui.personal.merchant;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzxmkuar.wumeihui.MainApp;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.CommonDialog;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.impl.UploadImageIdListener;
import com.hzxmkuar.wumeihui.base.impl.UploadImageModel;
import com.hzxmkuar.wumeihui.base.util.DateUtil;
import com.hzxmkuar.wumeihui.base.util.FileUtil;

import com.hzxmkuar.wumeihui.personal.merchant.presenter.MerchantEnterPresenter;
import com.jzxiang.pickerview.adapters.ArrayWheelAdapter;
import com.jzxiang.pickerview.wheel.WheelView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.soundcloud.android.crop.Crop;
import com.zhy.autolayout.AutoRelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.login.CityTo;
import hzxmkuar.com.applibrary.domain.merchant.CaseInfoTo;
import hzxmkuar.com.applibrary.impl.PermissionListener;

/**
 * Created by Administrator on 2018/9/3.
 **/

public class MerchantEnterInputActivity extends BaseActivity implements PermissionListener, UploadImageIdListener {

    @BindView(R.id.case_image_layout)
    GridLayout caseImageLayout;
    @BindView(R.id.head_image)
    ImageView headImage;
    @BindView(R.id.shop_name)
    EditText shopName;
    @BindView(R.id.main_image)
    ImageView mainImage;
    @BindView(R.id.address_layout)
    AutoRelativeLayout addressLayout;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.detail_address)
    EditText detailAddress;
    private String photoPath;
    private boolean isHeadImage;
    private MerchantEnterPresenter presenter;
    private List<CityTo> cityList;
    private String[] provinceData;
    private String[][] cityData;
    private HashMap<String, String[]> areaMap = new HashMap<>();
    private String city;
    private List<CaseInfoTo> caseList = new ArrayList<>();
    private UploadImageModel uploadImageModel = new UploadImageModel();
    private ImageView currentImage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_input_enter);
        ButterKnife.bind(this);
        setTitleName(Constant.MERCHANT_ENTER);
        ActivityManager.merchantEnterList.add(this);
        setPostImageLayout(caseImageLayout, 120);
        disPlayRoundImage(headImage);
        presenter = new MerchantEnterPresenter(this);
        getCityData();
        setCaseLayout();

    }

    @OnClick({R.id.shop_image_layout, R.id.main_image, R.id.submit, R.id.address_layout, R.id.service_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shop_image_layout:
                currentImage = headImage;
                uploadHeadImageDialog();
                break;
            case R.id.main_image:
               currentImage=mainImage;
                uploadHeadImageDialog();
                break;
            case R.id.submit:
                presenter.enterParam.setShop_logo((Integer) headImage.getTag());
                presenter.enterParam.setShop_banner((Integer) mainImage.getTag());
                presenter.enterParam.setShop_name(shopName.getText().toString());
                presenter.enterParam.setAddress((String) address.getTag());
                presenter.enterParam.setDetail_address(detailAddress.getText().toString());
                String caseJson = "[";
                for (int i = 0; i < caseList.size(); i++) {
                    caseJson = caseJson + "{\"img\":" + caseList.get(i).getImageId() + ",\"desc\":\"" + caseList.get(i).getCaseDes() + "\"},";
                }
                caseJson = caseJson.substring(0, caseJson.length() - 1) + "]";
                presenter.enterParam.setCase_example(caseJson);
                Intent intent = new Intent(appContext, getIntent().getBooleanExtra("IsPerson", false) ? UploadIdentityActivity.class : UploadAptitudeActivity.class);
                intent.putExtra("EnterParam", presenter.enterParam);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.address_layout:
                showPopWindow();
                break;
            case R.id.service_layout:
                intent = new Intent(appContext, SelectServiceActivity.class);
                startActivityForResult(intent, 50);
                break;

        }
    }


    private void showPopWindow() {
        CommonDialog dialog = new CommonDialog(this, R.layout.edit_address_pop_layout, R.style.MyDialogStyle);

        WheelView wvProvince = dialog.findViewById(R.id.wv_address_province);
        WheelView wvCitys = dialog.findViewById(R.id.wv_address_city);
        WheelView wvArea = dialog.findViewById(R.id.wv_address_area);
        dialog.findViewById(R.id.cancel).setOnClickListener(view1 -> dialog.dismiss());
        dialog.findViewById(R.id.confirm).setOnClickListener(view1 -> {
            String id = cityList.get(wvProvince.getCurrentItem()).getId() + "," + cityList.get(wvProvince.getCurrentItem()).getSub().get(wvCitys.getCurrentItem()).getId() + "," + cityList.get(wvProvince.getCurrentItem()).getSub().get(wvCitys.getCurrentItem()).getSub().get(wvArea.getCurrentItem()).getId();
            dialog.dismiss();
            address.setTag(id);
            address.setText(cityList.get(wvProvince.getCurrentItem()).getArea() + " " + cityList.get(wvProvince.getCurrentItem()).getSub().get(wvCitys.getCurrentItem()).getArea() + " " + cityList.get(wvProvince.getCurrentItem()).getSub().get(wvCitys.getCurrentItem()).getSub().get(wvArea.getCurrentItem()).getArea());
        });

        dialog.show();

        wvProvince.setViewAdapter(new ArrayWheelAdapter<>(appContext, provinceData));
        wvCitys.setViewAdapter(new ArrayWheelAdapter<>(appContext, cityData[0]));
        wvArea.setViewAdapter(new ArrayWheelAdapter<>(appContext, areaMap.get(cityData[0][0])));
        wvProvince.addChangingListener((wheel, oldValue, newValue) -> {
            wvCitys.setViewAdapter(new ArrayWheelAdapter<>(appContext, cityData[newValue]));
            wvArea.setViewAdapter(new ArrayWheelAdapter<>(appContext, areaMap.get(cityData[newValue][0])));
        });
        wvCitys.addChangingListener((wheel, oldValue, newValue) -> {
            wvArea.setViewAdapter(new ArrayWheelAdapter<>(appContext, areaMap.get(cityData[wvProvince.getCurrentItem()][wvCitys.getCurrentItem()])));
        });
    }

    private void getCityData() {
        new Thread(() -> {
            JSONObject mJsonObj;
            try {
                StringBuilder sb = new StringBuilder();
                InputStream is = appContext.getClass().getClassLoader().getResourceAsStream("assets/" + "province.json");
                int len;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    sb.append(new String(buf, 0, len, "utf-8"));
                }
                is.close();
                mJsonObj = new JSONObject(sb.toString());
                cityList = new Gson().fromJson(mJsonObj.getJSONArray("RECORD").toString(), new TypeToken<List<CityTo>>() {
                }.getType());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            provinceData = new String[cityList.size()];
            cityData = new String[cityList.size()][];

            for (int i = 0; i < provinceData.length; i++) {
                provinceData[i] = cityList.get(i).getArea();

                if (cityList.get(i).getSub() != null) {
                    cityData[i] = new String[cityList.get(i).getSub().size()];


                    for (int j = 0; j < cityData[i].length; j++) {

                        cityData[i][j] = cityList.get(i).getSub().get(j).getArea();
                        if (cityList.get(i).getSub().get(j).getSub() != null && cityList.get(i).getSub().get(j).getSub().size() > 0) {
                            String[] areaList = new String[cityList.get(i).getSub().get(j).getSub().size()];
                            for (int k = 0; k < cityList.get(i).getSub().get(j).getSub().size(); k++) {
                                areaList[k] = cityList.get(i).getSub().get(j).getSub().get(k).getArea();
                            }
                            areaMap.put(cityData[i][j], areaList);
                        }
                    }

                }
            }
        }).start();

    }


    private void setCaseLayout() {

        caseImageLayout.removeAllViews();
        imagePaths.clear();
        for (int i = 0; i < caseList.size(); i++) {
            imagePaths.add(caseList.get(i).getImageUrl());
        }
        for (int i = 0; i < (imagePaths.size() == 4 ? 4 : imagePaths.size() + 1) && i < 4; i++) {
            View mView = View.inflate(appContext, R.layout.circle_post_image_item, null);
            RoundedImageView imageView = mView.findViewById(R.id.image_view);
            mView.findViewById(R.id.delete_image).setVisibility((imagePaths.size() == 0 || imagePaths.size() == i) ? View.GONE : View.VISIBLE);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(appContext).load(i < imagePaths.size() ? imagePaths.get(i) : R.drawable.post_image_default).into(imageView);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = 120 * getScreenWidth() / 750;
            layoutParams.height = 120 * getScreenWidth() / 750;
            if (i != 0)
                layoutParams.setMarginStart((int) (20.0 / 750 * getScreenWidth()));
            mView.setLayoutParams(layoutParams);
            mView.setTag(i);
            mView.setOnClickListener(view -> {
                if ((int) mView.getTag() == caseList.size()) {
                    Intent intent = new Intent(appContext, AddCaseActivity.class);
                    startActivityForResult(intent, 60);
                    goToAnimation(1);
                }
            });
            mView.findViewById(R.id.delete_image).setOnClickListener(view -> {
                caseList.remove((int) mView.getTag());
                setPostImageLayout(caseImageLayout);
            });
            caseImageLayout.addView(mView);
        }
    }

    private void uploadHeadImageDialog() {
        CommonDialog alertDialog = new CommonDialog(this, getScreenWidth(), (int) (getScreenWidth() * 0.2), R.layout.dialog_pic_type, R.style.DialogDown);
        alertDialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.findViewById(R.id.btn_camera).setOnClickListener(v -> {
            alertDialog.dismiss();
            getPermission(Manifest.permission.CAMERA, this);
        });
        alertDialog.findViewById(R.id.btn_album).setOnClickListener(v -> {
            alertDialog.dismiss();
            getPermission(Manifest.permission.READ_EXTERNAL_STORAGE, this);
        });
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    public void accept(String permission) {
        switch (permission) {
            case Manifest.permission.CAMERA:
                getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,this);
                break;
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                openCamera();
                break;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                enterAlbum();
                break;

        }

    }

    @Override
    public void refuse(String permission) {

    }

    private void openCamera() {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                ContentValues values = new ContentValues(1);
                File mOutPhotoFile = new File(MainApp.getCacheImagePath(), DateUtil.getDateString(DateUtil.mFormatTimeCamara) + ".png");
                photoPath = mOutPhotoFile.getAbsolutePath();
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                values.put(MediaStore.Images.Media.DATA, photoPath);
                uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            File mOutPhotoFile = new File(MainApp.getCacheImagePath(), DateUtil.getDateString(DateUtil.mFormatTimeCamara) + ".png");
            photoPath = mOutPhotoFile.getAbsolutePath();
            uri = Uri.fromFile(mOutPhotoFile);

        }
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intentCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intentCamera, Constant.RESULT_CAMERA);


    }


    private void enterAlbum() {

        // 打开相册
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return_data", true);
        startActivityForResult(intent, Constant.RESULT_SDCARD);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.RESULT_SDCARD:
                    Uri uri = data.getData();
                    String mPhotoPath = FileUtil.getPath(this, uri);
                    if (!TextUtils.isEmpty(mPhotoPath)) {

                        uploadImageModel.uploadImage(mPhotoPath, this);
                    }

                    break;
                case Constant.RESULT_CAMERA:

                    Uri uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                    if (uri2 != null && photoPath != null) {
                        Uri uri1 = Uri.fromFile(new File(photoPath));
                        uploadImageModel.uploadImage(photoPath, this);

                    }
                    break;
                case 50:
                    presenter.enterParam.setMain_service(data.getStringExtra("ServiceId").substring(0, data.getStringExtra("ServiceId").length() - 1));
                    break;
                case 60:
                    CaseInfoTo infoTo = (CaseInfoTo) data.getSerializableExtra("CaseInfo");
                    caseList.add(infoTo);
                    setCaseLayout();
                    break;


            }
        }
    }


    @Override
    public void uploadImageSuccess(String path, int imageId) {
        displayImage(currentImage, path);
        currentImage.setTag(imageId);
    }
}
