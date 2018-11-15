package com.hzxmkuar.wumeihui.personal.inquiry;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.hzxmkuar.wumeihui.base.util.TimePickerExpect;
import com.hzxmkuar.wumeihui.databinding.InquiryDesItemBinding;


import com.hzxmkuar.wumeihui.personal.inquiry.presenter.InquiryDesPresenter;
import com.hzxmkuar.wumeihui.personal.myself.MyInquiryActivity;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.inquery.ConfirmInquiryPageTo;
import hzxmkuar.com.applibrary.domain.inquery.ContactPeopleTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantDetailTo;
import hzxmkuar.com.applibrary.impl.PermissionListener;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/12.
 */

public class InquiryDesActivity extends BaseActivity implements OnDateSetListener, PermissionListener, UploadImageIdListener {
    @BindView(R.id.address_name)
    TextView addressName;
    @BindView(R.id.use_time)
    TextView useTime;
    @BindView(R.id.contact_name)
    TextView contactName;
    @BindView(R.id.service_layout)
    GridLayout serviceLayout;
    @BindView(R.id.separate_view_icon)
    View separateViewIcon;
    @BindView(R.id.separate_text)
    TextView separateText;
    @BindView(R.id.separate_inquiry)
    AutoLinearLayout separateInquiry;
    @BindView(R.id.together_view_icon)
    View togetherViewIcon;
    @BindView(R.id.together_text)
    TextView togetherText;
    @BindView(R.id.together_inquiry)
    AutoLinearLayout togetherInquiry;
    @BindView(R.id.select_merchant_layout)
    AutoRelativeLayout selectMerchantLayout;
    @BindView(R.id.select_number)
    TextView selectNumber;
    private String serviceTime = "";

    private ConfirmInquiryPageTo pageTo;


    private TextView selectMerchantName;
    private String selectImageTag;
    private Map<Integer, List<View>> viewMap = new HashMap<>();
    private String serviceListJson = "";
    List<ConfirmInquiryPageTo.ServiceListBean> serviceList;
    private InquiryDesPresenter presenter;
    private List<ImageView> selectImageList = new ArrayList<>();
    private String selectMerchant = "[]";
    private UploadImageModel uploadImageModel = new UploadImageModel();
    private ImageView currentImage;
    private String photoPath;
    private int sType=2;
    private MerchantDetailTo merchantDetailTo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_des);
        ButterKnife.bind(this);
        setTitleName("描述需求");
        merchantDetailTo = (MerchantDetailTo) getIntent().getSerializableExtra("MerchantDetailTo");
        presenter = new InquiryDesPresenter(this);
        setFinish();
    }

    private void setFinish() {
        ((TextView) findViewById(R.id.finish)).setText("添加服务");
        findViewById(R.id.finish).setOnClickListener(v -> {
            Intent intent = new Intent(appContext, SelectDemandActivity.class);
            String serviceIdString = "";
            for (ConfirmInquiryPageTo.ServiceListBean mode : serviceList)
                serviceIdString = serviceIdString + mode.getId() + ",";

            intent.putExtra("ServiceList", serviceIdString);
            startActivity(intent);
            goToAnimation(1);
        });
    }

    @SuppressLint("SetTextI18n")
    private void setSelectLayout(List<ConfirmInquiryPageTo.ServiceListBean> serviceList) {
        this.serviceList = serviceList;
        selectNumber.setText(serviceList.size() + "");
        for (int i = 0; i < serviceList.size(); i++) {
            List<View> childView = new ArrayList<>();
            ConfirmInquiryPageTo.ServiceListBean mode = serviceList.get(i);
            View mView = View.inflate(appContext, R.layout.inquiry_des_item, null);
            InquiryDesItemBinding binding = DataBindingUtil.bind(mView);
            displayImage(binding.serviceImage, mode.getService_img());
            binding.serviceName.setText(mode.getService_name());
            binding.delete.setOnClickListener(view -> {
                serviceLayout.removeView(mView);
                viewMap.remove(mode.getId());
                serviceList.remove(mode);
                selectNumber.setText(serviceList.size() + "");
//                showMessage(serviceList.size()+"");
            });

            for (ConfirmInquiryPageTo.ServiceListBean.GetPropertiesListBean serviceTo : mode.getGet_properties_list()) {
                View typeView;
                if (serviceTo.getPtype() == 4) {
                    typeView = View.inflate(appContext, R.layout.inquiry_des_type2, null);
                    TagFlowLayout flowLayout = typeView.findViewById(R.id.flow_layout);
                    flowLayout.setMaxSelectCount(1);
                    List<View> selectIconList = new ArrayList<>();
                    flowLayout.setAdapter(new TagAdapter<ConfirmInquiryPageTo.ServiceListBean.GetPropertiesListBean.ConflistBean>(serviceTo.getConflist()) {
                        @Override
                        public View getView(FlowLayout parent, int position, ConfirmInquiryPageTo.ServiceListBean.GetPropertiesListBean.ConflistBean data) {
                            View mView = View.inflate(appContext, R.layout.inquiry_des_flow_item, null);
                            ((TextView) mView.findViewById(R.id.service_name)).setText(data.getTitle());
                            selectIconList.add(mView.findViewById(R.id.select_icon));
                            return mView;
                        }
                    });

                    flowLayout.setOnTagClickListener((view, position, parent) -> {
                        Observable.from(selectIconList).subscribe(view1 -> view1.setBackgroundResource(R.drawable.service_un_select));
                        selectIconList.get(position
                        ).setBackgroundResource(R.drawable.service_select);
                        return false;
                    });
                } else if (serviceTo.getPtype() == 3) {
                    typeView = View.inflate(appContext, R.layout.inquiry_des_type2, null);
                    TagFlowLayout flowLayout = typeView.findViewById(R.id.flow_layout);
                    flowLayout.setMaxSelectCount(1000);
                    List<View> selectIconList = new ArrayList<>();
                    flowLayout.setAdapter(new TagAdapter<ConfirmInquiryPageTo.ServiceListBean.GetPropertiesListBean.ConflistBean>(serviceTo.getConflist()) {
                        @Override
                        public View getView(FlowLayout parent, int position, ConfirmInquiryPageTo.ServiceListBean.GetPropertiesListBean.ConflistBean data) {
                            View mView = View.inflate(appContext, R.layout.inquiry_des_flow_item, null);
                            ((TextView) mView.findViewById(R.id.service_name)).setText(data.getTitle());
                            selectIconList.add(mView);
                            return mView;
                        }
                    });

                    flowLayout.setOnTagClickListener((view, position, parent) -> {
//                        Observable.from(selectIconList).subscribe(view1 -> view1.setBackgroundResource(R.drawable.service_un_select));
//                        selectIconList.get(position).setBackgroundResource(R.drawable.service_select);
                        selectIconList.get(position).setSelected(!selectIconList.get(position).isSelected());
                        selectIconList.get(position).findViewById(R.id.select_icon).setBackgroundResource(selectIconList.get(position).isSelected() ? R.drawable.service_select : R.drawable.service_un_select);
                        return false;
                    });
                } else if (serviceTo.getPtype() == 5) {
                    typeView = View.inflate(appContext, R.layout.inquiry_des_type3, null);

                    for (int k = 0; k < serviceTo.getConflist().size(); k++) {
                        View type3Child = View.inflate(appContext, R.layout.inquiry_des_type3_child, null);
                        ((TextView) type3Child.findViewById(R.id.image_title)).setText(serviceTo.getConflist().get(k).getTitle());
                        ImageView stageImage = type3Child.findViewById(R.id.stage_image);
                        selectImageList.add(stageImage);
                        stageImage.setOnClickListener(view -> {
                            currentImage = (ImageView) view;
                            uploadHeadImageDialog();
                        });
                        ((GridLayout) typeView.findViewById(R.id.image_layout)).addView(type3Child);
                    }


                } else if (serviceTo.getPtype() == 6) {
                    typeView = View.inflate(appContext, R.layout.inquiry_des_type4, null);
                } else
                    typeView = View.inflate(appContext, R.layout.inquiry_des_type1, null);

                ((TextView) typeView.findViewById(R.id.title_name).findViewById(R.id.title_name)).setText(serviceTo.getPtitle());
                typeView.setTag(serviceTo.getPtype());
                childView.add(typeView);
                binding.parentView.addView(typeView);
            }

            if (merchantDetailTo!=null){
                selectMerchantName = mView.findViewById(R.id.select_merchant);
                selectMerchantName.setText(merchantDetailTo.getBus_info().getShop_name());
                selectMerchant="["+merchantDetailTo.getBus_info().getBus_uid()+"]";
            }
            mView.findViewById(R.id.select_merchant_layout).setOnClickListener(view -> {

                Intent intent = new Intent(appContext, SelectMerchantActivity.class);
                intent.putExtra("ServiceId",mode.getId()+"");
                selectMerchantName = mView.findViewById(R.id.select_merchant);
                startActivityForResult(intent, 30);
                goToAnimation(1);
            });
            viewMap.put(mode.getId(), childView);
            serviceLayout.addView(mView);
        }

    }

    private void initTimeDialog(String title) {
        TimePickerExpect mimePickerExpect = new TimePickerExpect.Builder()
                .setType(Type.ALL)
                .setCallBack(this)
                .setTitleStringId(title)
                .setYearText("年")
                .setMonthText("月")
                .setThemeColor(Color.parseColor("#4fb2d6"))
                .setDayText("日")
                .setWheelItemTextSize(14)
                .setHourText("点")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis()+24*3600*1000)
                .buildNew();
        mimePickerExpect.show(getSupportFragmentManager(), "year_month_day_hour");
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        if (TextUtils.isEmpty(serviceTime)) {
            serviceTime = DateUtil.longToString(millseconds, DateUtil.serviceTime) + ":00";
            initTimeDialog("结束时间");
            useTime.setTag(millseconds+"-");
        } else {
            serviceTime = serviceTime + "-" + DateUtil.longToString(millseconds, DateUtil.serviceTime) + ":00";
            useTime.setText(serviceTime);
            useTime.setTag((String)useTime.getTag()+millseconds);
        }
    }

    @OnClick({R.id.address_layout, R.id.use_time_layout, R.id.contact_layout, R.id.submit, R.id.separate_inquiry, R.id.together_inquiry, R.id.select_merchant_layout, R.id.finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.address_layout:
                Intent intent = new Intent(appContext, SearchPositionActivity.class);
                startActivityForResult(intent, 40);
                goToAnimation(1);
                break;
            case R.id.use_time_layout:
                serviceTime = "";
                initTimeDialog("开始时间");
                break;
            case R.id.contact_layout:
                intent = new Intent(appContext, ContactPeopleActivity.class);
                startActivityForResult(intent, 70);
                goToAnimation(1);
                break;
            case R.id.submit:

                Collections.reverse(serviceList);
                serviceListJson = "[";
                for (ConfirmInquiryPageTo.ServiceListBean mode : serviceList) {


                    if (mode.getGet_properties_list() != null && mode.getGet_properties_list().size() > 0) {
                        serviceListJson = serviceListJson + "{\"sid\":" + mode.getId() + ",\"form\":{";
                        for (int i = 0; i < viewMap.get(mode.getId()).size(); i++) {
                            View mView = viewMap.get(mode.getId()).get(i);
                            if ((Integer) mView.getTag() == 4) {
                                TagFlowLayout flowLayout = mView.findViewById(R.id.flow_layout);
                                if (flowLayout != null) {
                                    ArrayList<Integer> selectList = new ArrayList<>(flowLayout.getSelectedList());
//                                  if (selectList == null || selectList.size() == 0) {
//                                      showMessage("请选择服务要求");
//                                      return;
//                                  }
                                    if (selectList.size() > 0)
                                        serviceListJson = serviceListJson + "\"" + mode.getGet_properties_list().get(i).getId() + "\":[" + mode.getGet_properties_list().get(i).getConflist().get(selectList.get(0)).getId() + "],";
                                    else
                                        serviceListJson = serviceListJson + "\"" + mode.getGet_properties_list().get(i).getId() + "\":[" + "],";
                                }
                            } else if ((Integer) mView.getTag() == 3) {

                                TagFlowLayout flowLayout = mView.findViewById(R.id.flow_layout);
                                if (flowLayout != null) {
                                    ArrayList<Integer> selectList = new ArrayList<>(flowLayout.getSelectedList());
//                                 if (selectList == null || selectList.size() == 0) {
//                                     showMessage("请选择服务要求");
//                                     return;
//                                 }
                                    String selectString = "";
                                    for (int k = 0; k < selectList.size(); k++) {
                                        selectString = selectString + mode.getGet_properties_list().get(i).getConflist().get(selectList.get(k)).getId() + ",";
                                    }
                                    if (selectString.length()>0)
                                    serviceListJson = serviceListJson + "\"" + mode.getGet_properties_list().get(i).getId() + "\":[" + selectString.substring(0, selectString.length() - 1) + "],";
                                    else
                                        serviceListJson = serviceListJson + "\"" + mode.getGet_properties_list().get(i).getId() + "\":[" + "],";
                                }
                            } else if ((Integer) mView.getTag() == 5) {
                                String imageJson = "";
                                GridLayout imageLayout = mView.findViewById(R.id.image_layout);
                                if (imageLayout != null) {

                                    for (int k = 0; k < imageLayout.getChildCount(); k++) {
//                                        if (imageLayout.getChildAt(k).findViewById(R.id.stage_image).getTag() == null) {
//                                            showMessage("请添加图片");
//                                            return;
//                                        }
                                        imageJson = imageJson + "\"" + imageLayout.getChildAt(k).findViewById(R.id.stage_image).getTag() + "\",";
                                    }
                                    imageJson = "[" + imageJson.substring(0, imageJson.length() - 1) + "],";
                                    serviceListJson = serviceListJson + "\"" + mode.getGet_properties_list().get(i).getId() + "\":" + imageJson;
                                }
                            } else if ((Integer) mView.getTag() == 6) {
                                TextView textView = mView.findViewById(R.id.add_inquiry_content);
                                if (textView != null) {
//                                   if (TextUtils.isEmpty(textView.getText().toString())) {
//                                       showMessage("请填写完整");
//                                       return;
//                                   }
                                    serviceListJson = serviceListJson + "\"" + mode.getGet_properties_list().get(i).getId() + "\":[\"" + textView.getText() + "\"],";
                                }
                            } else {
                                TextView textView = mView.findViewById(R.id.inquiry_content);
                                if (textView != null) {

//                                    if (TextUtils.isEmpty(textView.getText().toString())) {
//                                        showMessage("请填写完整");
//                                        return;
//                                    }
                                    serviceListJson = serviceListJson + "\"" + mode.getGet_properties_list().get(i).getId() + "\":[\"" + textView.getText() + "\"],";
                                }
                            }


                        }
                    } else {
                        serviceListJson = serviceListJson + "{\"sid\":" + mode.getId() + ",\"form\":{}";
                    }

//

                    serviceListJson = serviceListJson.substring(0, serviceListJson.length() - 1) + "}," + "\"inquiry_bus\":" + selectMerchant + "},";
                }
                serviceListJson = serviceListJson.substring(0, serviceListJson.length() - 1) + "]";
                if (TextUtils.isEmpty(serviceTime)) {
                    showMessage("请选择时间");
                    return;
                }
                if (Long.valueOf(((String)useTime.getTag()).split("-")[0])>Long.valueOf(((String)useTime.getTag()).split("-")[1])){
                    showMessage("开始时间不能在结束时间之后");
                    return;
                }


                if (contactName.getTag() == null ||(Integer)contactName.getTag() == 0 || TextUtils.isEmpty(contactName.getText().toString())) {
                    showMessage("请选择联系人");
                    return;
                }
                System.out.println(serviceListJson);
                if ("[]".equals(serviceListJson)||serviceListJson.length()<2){
                    showMessage("请添加服务");
                    return;
                }
                presenter.addInquiry(serviceListJson, selectMerchant, serviceTime, (Integer) contactName.getTag(), addressName.getText().toString(),sType);

                break;
            case R.id.separate_inquiry:
                sType=2;
                selectMerchantLayout.setVisibility(View.GONE);
                separateInquiry.setBackgroundResource(R.drawable.rec_blue_bg);
                togetherInquiry.setBackgroundResource(R.drawable.rec_gray_bg);
                separateText.setTextColor(Color.parseColor("#3bafd9"));
                togetherText.setTextColor(Color.parseColor("#999999"));
                separateViewIcon.setBackgroundResource(R.drawable.separate_view_blue);
                togetherViewIcon.setBackgroundResource(R.drawable.together_view_gray);
                for (int i = 0; i < serviceLayout.getChildCount(); i++)
                    serviceLayout.getChildAt(i).findViewById(R.id.select_merchant_layout).setVisibility(View.VISIBLE);
                break;
            case R.id.together_inquiry:
                sType=1;
                selectMerchantLayout.setVisibility(View.VISIBLE);
                separateInquiry.setBackgroundResource(R.drawable.rec_gray_bg);
                togetherInquiry.setBackgroundResource(R.drawable.rec_blue_bg);
                separateText.setTextColor(Color.parseColor("#999999"));
                togetherText.setTextColor(Color.parseColor("#3bafd9"));
                separateViewIcon.setBackgroundResource(R.drawable.separate_view_gray);
                togetherViewIcon.setBackgroundResource(R.drawable.together_view_blue);
                for (int i = 0; i < serviceLayout.getChildCount(); i++)
                    serviceLayout.getChildAt(i).findViewById(R.id.select_merchant_layout).setVisibility(View.GONE);
                break;
            case R.id.select_merchant_layout:
                String serviceIds="";
                for (ConfirmInquiryPageTo.ServiceListBean serviceListBean:serviceList)
                    serviceIds=serviceIds+serviceListBean.getId()+",";
                intent = new Intent(appContext, SelectMerchantActivity.class);
                intent.putExtra("ServiceId",serviceIds.substring(0,serviceIds.length()-1));
                selectMerchantName = findViewById(R.id.select_merchant);
                startActivityForResult(intent, 30);
                goToAnimation(1);
                break;
            case R.id.finish:

                break;
        }
    }

    @Override
    public void loadDataSuccess(Object data) {
        pageTo = (ConfirmInquiryPageTo) data;
        setView();

    }

    private void setView() {
        selectMerchantName = findViewById(R.id.select_merchant);
        addressName.setText(pageTo.getService_address());
        contactName.setText(pageTo.getContact_info().getContact_name() + "  " + pageTo.getContact_info().getContact_telphone());
        contactName.setTag(pageTo.getContact_info().getContact_id());
        setSelectLayout(pageTo.getService_list());
        if (merchantDetailTo!=null){
            selectMerchantName.setText(merchantDetailTo.getBus_info().getShop_name());
            selectMerchant="["+merchantDetailTo.getBus_info().getBus_uid()+"]";
        }
    }


    @Override
    protected void submitDataSuccess(Object data) {
        Intent intent=new Intent(appContext,InquirySuccessActivity.class);
        if ( ActivityManager.demandActivity!=null)
        ActivityManager.demandActivity.finish();
        startActivity(intent);
        goToAnimation(1);
        finish();

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
                case 30:
                    if (data != null) {
                        selectMerchant = data.getStringExtra("SelectMerchant");
                        selectMerchantName.setText(data.getStringExtra("SelectMerchantName"));

                    }

                    break;
                case 40:
                    addressName.setText(data.getStringExtra("PoiInfo"));
                    break;
                case 70:
                    ContactPeopleTo.ListsBean contactTo = (ContactPeopleTo.ListsBean) data.getSerializableExtra("ContactInfo");
                    contactName.setText(contactTo.getConsignee() + "  " + contactTo.getTelephone());
                    contactName.setTag(contactTo.getId());
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
}
