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

import com.bumptech.glide.Glide;
import com.hzxmkuar.wumeihui.MainApp;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.CommonDialog;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.impl.UploadImageIdListener;
import com.hzxmkuar.wumeihui.base.impl.UploadImageListener;
import com.hzxmkuar.wumeihui.base.impl.UploadImageModel;
import com.hzxmkuar.wumeihui.base.impl.UploadImagePathListener;
import com.hzxmkuar.wumeihui.base.util.DateUtil;
import com.hzxmkuar.wumeihui.base.util.FileUtil;
import com.hzxmkuar.wumeihui.personal.merchant.presenter.MerchantEnterPresenter;
import com.makeramen.roundedimageview.RoundedImageView;


import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.merchant.ShopInfoTo;
import hzxmkuar.com.applibrary.impl.PermissionListener;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/3.
 **/

public class UploadAptitudeActivity extends BaseActivity implements UploadImagePathListener {

    @BindView(R.id.company_name)
    EditText companyName;
    @BindView(R.id.company_image)
    GridLayout companyImage;
    private MerchantEnterPresenter presenter;
    private UploadImageModel uploadImageModel = new UploadImageModel();
    private String photoPath;
    private ShopInfoTo mode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_aptitude);
        ActivityManager.merchantEnterList.add(this);
        ButterKnife.bind(this);
        setTitleName(Constant.UPLOAD_APTITUDE);
        mode = (ShopInfoTo) getIntent().getSerializableExtra("Mode");
        presenter = new MerchantEnterPresenter(this);
        setView();


    }

    private void setView() {
        if (mode != null) {
            companyName.setText(mode.getAptitude_name());
            if (mode.getAptitude_license()!=null) {
                for (int i = 0; i < mode.getAptitude_license().size(); i++)
                    imagePaths.add(mode.getAptitude_license().get(i).getPic());
                setPostImageLayout(companyImage);
            }

        }else    setPostImageLayout(companyImage, 160);
    }

    @OnClick({R.id.company_image, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.submit:
                if (TextUtils.isEmpty(companyName.getText().toString())) {
                    showMessage("请填写公司名称");
                    return;
                }
                if (companyImage.getChildCount() < 2) {
                    showMessage("请上传图片");
                    return;
                }
                if (mode!=null) {
                    for (int i = 0; i < mode.getAptitude_license().size(); i++)
                        imagePaths.remove(mode.getAptitude_license().get(i).getPic());
                }
                if (imagePaths.size()==0){

                        String path="";
                        String key="";

                        for (int i=mode.getAptitude_license().size()-1;i>=0;i--) {
                            key = mode.getAptitude_license().get(i).getImgid() + "," + key;
                            path = mode.getAptitude_license().get(i).getPic() + "," + path;
                        }
                        Intent intent=new Intent();
                        intent.putExtra("CompanyName",companyName.getText().toString());
                        intent.putExtra("ImageId",key);
                        intent.putExtra("ImagePath",path);
                        setResult(RESULT_OK,intent);
                        goToAnimation(2);
                        finish();

                }else
                uploadImageModel.uploadImage(imagePaths, this);

                break;
        }
    }


    @Override
    public void uploadImageSuccess(String path,String key) {
        presenter.enterParam.setAptitude_license(key + "");
        presenter.enterParam.setBusiness_type(1);
        presenter.enterParam.setAptitude_name(companyName.getText().toString());

        if (mode!=null){
            for (int i=mode.getAptitude_license().size()-1;i>=0;i--) {
                key = mode.getAptitude_license().get(i).getImgid() + "," + key;
                path = mode.getAptitude_license().get(i).getPic() + "," + path;
            }

            Intent intent=new Intent();
            intent.putExtra("CompanyName",companyName.getText().toString());
            intent.putExtra("ImageId",key);
            intent.putExtra("ImagePath",path);
            showMessage(path);
            setResult(RESULT_OK,intent);
            goToAnimation(2);
            finish();
        }else
        presenter.merchantEnter();
    }

    protected void setPostImageLayout(GridLayout itemImageLayout) {
        pictureLayout = itemImageLayout;

        itemImageLayout.removeAllViews();

        for (int i = 0; i < (imagePaths.size() == 4 ? 4 : imagePaths.size() + 1) && i < 4; i++) {
            View mView = View.inflate(appContext, R.layout.circle_post_image_item, null);
            RoundedImageView imageView = mView.findViewById(R.id.image_view);
            mView.findViewById(R.id.delete_image).setVisibility((imagePaths.size() == 0 || imagePaths.size() == i) ? View.GONE : View.VISIBLE);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(appContext).load(i < imagePaths.size() ? imagePaths.get(i) : R.drawable.post_image_default).into(imageView);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = (int) (150.0 / 750 * getScreenWidth());
            layoutParams.height = (int) (150.0 / 750 * getScreenWidth());
            layoutParams.setMarginStart((int) (24.0 / 750 * getScreenWidth()));
            mView.setLayoutParams(layoutParams);
            mView.setTag(i);
            mView.setOnClickListener(view -> getPermissionPhoto(Manifest.permission.CAMERA, view, this));
            mView.findViewById(R.id.delete_image).setOnClickListener(view -> {

                if (mode!=null){
                    for (int k = 0; k < mode.getAptitude_license().size(); k++){
                        if (imagePaths.get((Integer) mView.getTag()).equals(mode.getAptitude_license().get(k).getPic()))
                            mode.getAptitude_license().remove(k);
                    }
                }
                imagePaths.remove(imagePaths.get((Integer) mView.getTag()));
                setPostImageLayout(itemImageLayout);
            });

            itemImageLayout.addView(mView);
        }
    }

    @Override
    protected void submitDataSuccess(Object data) {
        Observable.from(ActivityManager.merchantEnterList).subscribe(activity -> finish());
        Intent intent=new Intent(appContext,MerchantCheckActivity.class);
        startActivity(intent);
        goToAnimation(1);
    }
}
