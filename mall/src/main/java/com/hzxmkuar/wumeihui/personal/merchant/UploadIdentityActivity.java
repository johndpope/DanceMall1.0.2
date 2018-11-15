package com.hzxmkuar.wumeihui.personal.merchant;

import android.Manifest;
import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.merchant.ShopInfoTo;
import hzxmkuar.com.applibrary.impl.PermissionListener;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/28.
 **/

public class UploadIdentityActivity extends BaseActivity implements PermissionListener,UploadImageIdListener {

    @BindView(R.id.back)
    AutoRelativeLayout back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.font_image)
    ImageView fontImage;
    @BindView(R.id.back_image)
    ImageView backImage;
    private MerchantEnterPresenter presenter;
    private String photoPath;
    private ImageView currentImage;
    private UploadImageModel uploadImageModel=new UploadImageModel();
    private ShopInfoTo mode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_verification);
        ButterKnife.bind(this);
        presenter=new MerchantEnterPresenter(this);
        ActivityManager.merchantEnterList.add(this);
        mode = (ShopInfoTo) getIntent().getSerializableExtra("Mode");
        setView();
        setTitleName("个人资质");
    }

    private void setView() {
        if (mode!=null){
            userName.setText(mode.getAptitude_name());

            if (mode.getAptitude_license().size()>0){
                displayImage(fontImage,mode.getAptitude_license().get(0).getPic());
                fontImage.setTag(mode.getAptitude_license().get(0).getImgid());
            }
            if (mode.getAptitude_license().size()>1) {
                displayImage(backImage, mode.getAptitude_license().get(1).getPic());
                backImage.setTag(mode.getAptitude_license().get(1).getImgid());
            }

        }
    }

    @OnClick({R.id.font_image, R.id.back_image, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.font_image:
                uploadHeadImageDialog();
                currentImage=fontImage;
                break;
            case R.id.back_image:
                uploadHeadImageDialog();
                currentImage=backImage;
                break;
            case R.id.submit:
                if (TextUtils.isEmpty(userName.getText().toString())){
                    showMessage("请填写姓名");
                    return;
                }
                if (fontImage.getTag()==null){
                    showMessage("请上传身份证正面照");
                    return;
                }
                if (backImage.getTag()==null){
                    showMessage("请上传身份证反面照");
                    return;
                }
                if (mode==null) {
                    presenter.enterParam.setAptitude_name(userName.getText().toString());
                    presenter.enterParam.setAptitude_license(fontImage.getTag() + "," + backImage.getTag());
                    presenter.enterParam.setBusiness_type(2);
                    presenter.merchantEnter();
                }else {
                    Intent intent=new Intent();
                    intent.putExtra("ImageId",fontImage.getTag() + "," + backImage.getTag());
                   intent.putExtra("ImagePath",fontImage.getTag(R.id.image_path) + "," + backImage.getTag(R.id.image_path));
                    setResult(RESULT_OK,intent);
                    finish();
                    goToAnimation(2);

                }
                break;
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

                        uploadImageModel.uploadImage(mPhotoPath,this);
                    }

                    break;
                case Constant.RESULT_CAMERA:

                    Uri uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                    if (uri2 != null && photoPath != null) {
                        Uri uri1 = Uri.fromFile(new File(photoPath));
                        uploadImageModel.uploadImage(photoPath,this);

                    }
                    break;


            }
        }
    }


    @Override
    public void uploadImageSuccess(String path, int imageId) {
        currentImage.setTag(null);
        displayImage(currentImage,path);
        currentImage.setTag(imageId);
        currentImage.setTag(R.id.image_path,path);
    }

    @Override
    protected void submitDataSuccess(Object data) {

        Intent intent=new Intent(appContext,MerchantCheckActivity.class);
        startActivity(intent);
        Observable.from(ActivityManager.merchantEnterList).subscribe(Activity::finish);
        goToAnimation(1);
    }


    protected void displayImage(ImageView imageView, String url) {
        Glide.with(MainApp.appContext)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)

                .into(imageView);

    }
}
