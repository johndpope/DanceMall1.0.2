package com.hzxmkuar.wumeihui.personal.myself;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hzxmkuar.wumeihui.MainApp;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.CommonDialog;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.impl.UploadImageIdListener;
import com.hzxmkuar.wumeihui.base.impl.UploadImageModel;
import com.hzxmkuar.wumeihui.base.util.DateUtil;
import com.hzxmkuar.wumeihui.base.util.FileUtil;
import com.hzxmkuar.wumeihui.personal.myself.presenter.IdentityPresenter;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.user.IdentityResultTo;
import hzxmkuar.com.applibrary.impl.PermissionListener;

/**
 * Created by Administrator on 2018/9/5.
 */

public class IdentityVerificationActivity extends BaseActivity implements PermissionListener, UploadImageIdListener {

    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.company_name)
    EditText companyName;
    @BindView(R.id.company_image)
    ImageView companyImage;
    @BindView(R.id.company_layout)
    AutoLinearLayout companyLayout;
    @BindView(R.id.face_front)
    ImageView faceFront;
    @BindView(R.id.face_background)
    ImageView faceBackground;
    @BindView(R.id.person_layout)
    AutoLinearLayout personLayout;
    @BindView(R.id.submit)
    TextView submit;
    private String photoPath;
    private ImageView currentImage;
    private UploadImageModel uploadImageModel = new UploadImageModel();
    private IdentityPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);
        setTitleName(Constant.IDENTITY_VERIFICATION);
        presenter = new IdentityPresenter(this);

    }

    @OnClick({R.id.type_layout, R.id.company_image, R.id.face_front, R.id.face_background, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.type_layout:
                selectTypeDialog();
                break;
            case R.id.company_image:
                currentImage = companyImage;
                uploadHeadImageDialog();
                break;
            case R.id.face_front:
                currentImage = faceFront;
                uploadHeadImageDialog();
                break;
            case R.id.face_background:
                currentImage = faceBackground;
                uploadHeadImageDialog();
                break;
            case R.id.submit:
                if (companyLayout.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(companyName.getText().toString())) {
                        showMessage("请填写公司名称");
                        return;
                    }
                    if (companyImage.getTag() == null || (Integer) companyImage.getTag() == 0) {
                        showMessage("请上传企业营业执照");
                        return;
                    }
                    presenter.companyIdentity(companyName.getText().toString(), (Integer) companyImage.getTag());
                } else {
                    if (faceFront.getTag() == null || (Integer) faceFront.getTag() == 0) {
                        showMessage("请上传身份证正面");
                        return;
                    }
                    if (faceBackground.getTag() == null || (Integer) faceBackground.getTag() == 0) {
                        showMessage("请上传身份证反面");
                        return;
                    }

                    presenter.personIdentity((int) faceFront.getTag(), (int) faceBackground.getTag());
                }
                break;
        }
    }

    private void selectTypeDialog() {
        CommonDialog alertDialog = new CommonDialog(this, getScreenWidth(), (int) (getScreenWidth() * 0.2), R.layout.dialog_select_verification_type, R.style.DialogDown);
        alertDialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.findViewById(R.id.btn_camera).setOnClickListener(v -> {
            alertDialog.dismiss();
            type.setText("个人");
            personLayout.setVisibility(View.VISIBLE);
            companyLayout.setVisibility(View.GONE);
        });
        alertDialog.findViewById(R.id.btn_album).setOnClickListener(v -> {
            alertDialog.dismiss();
            type.setText("企业");
            personLayout.setVisibility(View.GONE);
            companyLayout.setVisibility(View.VISIBLE);

        });
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
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
                getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, this);
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
                File mOutPhotoFile = new File(MainApp.getCacheImagePath(), DateUtil.getDateString(DateUtil.mFormatTimeCamaraDetail) + ".png");
                photoPath = mOutPhotoFile.getAbsolutePath();
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                values.put(MediaStore.Images.Media.DATA, photoPath);
                uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            File mOutPhotoFile = new File(MainApp.getCacheImagePath(), DateUtil.getDateString(DateUtil.mFormatTimeCamaraDetail) + ".png");
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


            }
        }
    }


    @Override
    public void uploadImageSuccess(String path, int imageId) {
        currentImage.setTag(null);
        System.out.println(path);
        displayImage(currentImage, path);
        currentImage.setTag(imageId);

    }


    @Override
    public void loadDataSuccess(Object data) {
        IdentityResultTo mode = (IdentityResultTo) data;
        if (mode.getStatus() == 2 || mode.getStatus() == 1) {
            submit.setVisibility(View.GONE);
        }

        if (mode.getAuth_info() != null && mode.getAuth_info().getImg1() != null && mode.getAuth_info().getImg2() != null) {
            if (mode.getAuth_type() == 2) {
                displayImage(faceFront, mode.getAuth_info().getImg1());
                displayImage(faceBackground, mode.getAuth_info().getImg2());
                faceBackground.setEnabled(false);
                faceFront.setEnabled(false);
            }

            if (mode.getAuth_type() == 1) {
                displayImage(companyImage, mode.getAuth_info().getImg1());
                companyName.setText(mode.getAuth_info().getName());
                companyImage.setEnabled(false);
            }

        }
    }
}
