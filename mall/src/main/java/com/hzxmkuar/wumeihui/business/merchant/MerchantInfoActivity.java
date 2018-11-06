package com.hzxmkuar.wumeihui.business.merchant;

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
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.CommonDialog;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.impl.UploadImageIdListener;
import com.hzxmkuar.wumeihui.base.impl.UploadImageModel;
import com.hzxmkuar.wumeihui.base.util.DateUtil;
import com.hzxmkuar.wumeihui.base.util.FileUtil;
import com.hzxmkuar.wumeihui.business.merchant.presenter.MerchantInfoPresenter;
import com.hzxmkuar.wumeihui.personal.merchant.AddCaseActivity;
import com.hzxmkuar.wumeihui.personal.merchant.ModifyCaseActivity;
import com.hzxmkuar.wumeihui.personal.merchant.SelectServiceActivity;
import com.hzxmkuar.wumeihui.personal.merchant.UploadAptitudeActivity;
import com.hzxmkuar.wumeihui.personal.merchant.UploadIdentityActivity;
import com.jzxiang.pickerview.adapters.ArrayWheelAdapter;
import com.jzxiang.pickerview.wheel.WheelView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.autolayout.AutoLinearLayout;
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
import hzxmkuar.com.applibrary.domain.merchant.ShopInfoTo;
import hzxmkuar.com.applibrary.impl.PermissionListener;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/5.
 */

public class MerchantInfoActivity extends BaseActivity implements PermissionListener, UploadImageIdListener {


    @BindView(R.id.back)
    AutoRelativeLayout back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_layout)
    AutoRelativeLayout titleLayout;
    @BindView(R.id.head_image)
    ImageView headImage;
    @BindView(R.id.shop_name)
    EditText shopName;
    @BindView(R.id.image_layout)
    ImageView imageLayout;
    @BindView(R.id.service_layout)
    AutoRelativeLayout serviceLayout;
    @BindView(R.id.case_image_layout)
    GridLayout caseImageLayout;
    @BindView(R.id.case_layout)
    AutoLinearLayout caseLayout;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.address_layout)
    AutoRelativeLayout addressLayout;
    @BindView(R.id.detail_address)
    EditText detailAddress;
    @BindView(R.id.company_name)
    EditText companyName;
    @BindView(R.id.company_image_layout)
    GridLayout companyImageLayout;
    @BindView(R.id.company_layout)
    AutoLinearLayout companyLayout;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.name_des)
    TextView nameDes;
    @BindView(R.id.company_des)
    TextView companyDes;
    private MerchantInfoPresenter presenter;
    private ShopInfoTo mode;
    private String addressData;
    private UploadImageModel uploadImageModel = new UploadImageModel();
    private ImageView currentImage;
    private List<CityTo> cityList;
    private String[] provinceData;
    private String[][] cityData;
    private String photoPath;
    private List<CaseInfoTo> caseList = new ArrayList<>();
    private HashMap<String, String[]> areaMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_info);
        ButterKnife.bind(this);
        setTitleName(Constant.SHOP_INFO);
        presenter = new MerchantInfoPresenter(this);


    }

    @Override
    public void loadDataSuccess(Object data) {
        mode = (ShopInfoTo) data;
        new Thread(() -> {
            initJson();
            getCityData();
        }).start();
        disPlayRoundImage(headImage, mode.getShop_logo().getPic());
        headImage.setTag(0);
        shopName.setText(mode.getShop_name());
        detailAddress.setText(mode.getDetail_address());
        displayImage(imageLayout, mode.getShop_banner().getPic());
        imageLayout.setTag(0);
        companyName.setText(mode.getAptitude_name());
        List<String> imageList = new ArrayList<>();
        nameDes.setText(mode.getBusiness_type()==1?"公司名称":"姓名");
        companyDes.setText(mode.getBusiness_type()==1?"公司资质":"个人资质");
        String license = "";
        for (int i = 0; i < mode.getAptitude_license().size(); i++) {
            imageList.add(mode.getAptitude_license().get(i).getPic());
            license = license + mode.getAptitude_license().get(i).getImgid() + ",";
        }
        setImageLayout(companyImageLayout, imageList, 120);
        Observable.from(mode.getCase_example()).subscribe(caseExampleBean -> {
            CaseInfoTo infoTo = new CaseInfoTo();
            infoTo.setImageUrl(caseExampleBean.getPic());
            infoTo.setImageId(caseExampleBean.getImgid());
            infoTo.setCaseDes(caseExampleBean.getDesc());
            infoTo.setId(caseExampleBean.getId());

            caseList.add(infoTo);
        });
        setCaseLayout();
        presenter.param.setMain_service(mode.getMain_service());
        address.setTag(mode.getAddress());
        presenter.param.setAptitude_license(license.substring(0, license.length() - 1));

    }

    private void initJson() {

        JSONObject mJsonObj = null;
        addressData = "";
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = getClass().getClassLoader().getResourceAsStream("assets/" + "province.json");
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "utf-8"));
            }
            is.close();
            mJsonObj = new JSONObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String[] addressId = mode.getAddress().split(",");
            cityList = new Gson().fromJson(mJsonObj.getJSONArray("RECORD").toString(), new TypeToken<List<CityTo>>() {
            }.getType());
            for (CityTo province : cityList) {
                if (addressId[0].equals(province.getId() + "")) {
                    addressData = addressData + province.getArea();

                    for (CityTo city : province.getSub()) {
                        if (addressId[1].equals(city.getId() + "")) {
                            addressData = addressData + city.getArea();
                            for (CityTo area : city.getSub()) {
                                if (addressId[2].equals(area.getId() + "")) {
                                    addressData = addressData + area.getArea();
                                    runOnUiThread(() -> address.setText(addressData));
                                }
                            }
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @OnClick({R.id.head_image, R.id.image_layout, R.id.service_layout, R.id.address_layout, R.id.company_layout, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_image:
                currentImage = headImage;
                uploadHeadImageDialog();
                break;
            case R.id.image_layout:
                currentImage = imageLayout;
                uploadHeadImageDialog();
                break;
            case R.id.service_layout:
                Intent intent = new Intent(appContext, SelectServiceActivity.class);
                intent.putExtra("ServiceId", mode.getMain_service());
                startActivityForResult(intent, 50);
                break;
            case R.id.address_layout:
                showPopWindow();
                break;
            case R.id.company_layout:
                intent = new Intent(appContext, mode.getBusiness_type() != 1 ? UploadIdentityActivity.class : UploadAptitudeActivity.class);
                intent.putExtra("Mode", mode);
                startActivityForResult(intent, mode.getBusiness_type() != 1 ? 110 : 100);
                break;
            case R.id.submit:
                presenter.param.setShop_id(mode.getShop_id());
                presenter.param.setShop_logo((Integer) headImage.getTag());
                presenter.param.setShop_banner((Integer) imageLayout.getTag());
                presenter.param.setShop_name(shopName.getText().toString());
                presenter.param.setAddress((String) address.getTag());
                presenter.param.setDetail_address(detailAddress.getText().toString());
                String caseJson = "[";
                for (int i = 0; i < caseList.size(); i++) {
                    caseJson = caseJson + "{\"img\":" + caseList.get(i).getImageId() + ",\"desc\":\"" + caseList.get(i).getCaseDes() + "\"},";
                }
                caseJson = caseJson.substring(0, caseJson.length() - 1) + "]";
                presenter.param.setCase_example(caseJson);
                presenter.param.setAptitude_name(companyName.getText().toString());
                presenter.modifyMerchant();
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
                    presenter.param.setMain_service(data.getStringExtra("ServiceId").substring(0, data.getStringExtra("ServiceId").length() - 1));
                    break;
                case 60:
                    CaseInfoTo infoTo = (CaseInfoTo) data.getSerializableExtra("CaseInfo");
                    caseList.add(infoTo);
                    setCaseLayout();
                    break;
                case 100:

                    presenter.param.setAptitude_name(data.getStringExtra("CompanyName"));
                    String[] imagePath = data.getStringExtra("ImagePath").split(",");
                    String imageId = data.getStringExtra("ImageId");
                    mode.getAptitude_license().clear();

                    List<String> imageList = new ArrayList<>();
                    companyImageLayout.removeAllViews();
                    for (int i = 0; i < imagePath.length; i++) {
                        ShopInfoTo.AptitudeTo aptitudeTo = new ShopInfoTo.AptitudeTo();
                        aptitudeTo.setImgid(imageId.split(",")[i]);
                        aptitudeTo.setPic(imagePath[i]);
                        imageList.add(imagePath[i]);
                        mode.getAptitude_license().add(aptitudeTo);
                    }
                    setImageLayout(companyImageLayout, imageList, 120);
                    presenter.param.setAptitude_license(data.getStringExtra("ImageId"));
                    break;
                case 110:
                    presenter.param.setAptitude_name(data.getStringExtra("CompanyName"));
                    presenter.param.setAptitude_license(data.getStringExtra("ImageId"));
                    companyImageLayout.removeAllViews();
                    setImageLayout(data.getStringExtra("ImagePath"), companyImageLayout, 120);
                    break;
                case 120:
                    infoTo = (CaseInfoTo) data.getSerializableExtra("CaseInfo");
                    for (int i = 0; i < caseList.size(); i++) {
                        if (infoTo.getId() == caseList.get(i).getId()) {
                            caseList.get(i).setCaseDes(infoTo.getCaseDes());
                            caseList.get(i).setImageId(infoTo.getImageId());
                            caseList.get(i).setImageUrl(infoTo.getImageUrl());
                        }
                    }

                    setCaseLayout();
                    break;


            }
        }
    }


    @Override
    public void uploadImageSuccess(String path, int imageId) {
        currentImage.setTag(null);
        displayImage(currentImage, path);
        currentImage.setTag(imageId);
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
                } else {
                    if ((Integer) mView.getTag()<mode.getCase_example().size()) {
                        Intent intent = new Intent(appContext, ModifyCaseActivity.class);
                        intent.putExtra("CaseTo", mode.getCase_example().get((Integer) mView.getTag()));
                        startActivityForResult(intent, 120);
                        goToAnimation(1);
                    }else {
                        CaseInfoTo infoTo = caseList.get((Integer) mView.getTag());
                        ShopInfoTo.CaseExampleBean bean=new ShopInfoTo.CaseExampleBean();
                        bean.setId(infoTo.getId());
                        bean.setDesc(infoTo.getCaseDes());
                        bean.setPic(infoTo.getImageUrl());
                        bean.setImgid(infoTo.getImageId());
                        Intent intent = new Intent(appContext, ModifyCaseActivity.class);
                        intent.putExtra("CaseTo", bean);
                        startActivityForResult(intent, 120);
                        goToAnimation(1);
                    }
                }
            });
            mView.findViewById(R.id.delete_image).setVisibility(View.GONE);
            caseImageLayout.addView(mView);
        }
    }

    @Override
    protected void submitDataSuccess(Object data) {
        finish();
        startActivity(getIntent());
        goToAnimation(1);
    }
}
