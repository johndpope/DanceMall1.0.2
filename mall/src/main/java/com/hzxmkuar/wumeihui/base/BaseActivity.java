package com.hzxmkuar.wumeihui.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.hzxmkuar.wumeihui.MainApp;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.util.GlideCircleTransform;
import com.hzxmkuar.wumeihui.base.util.StatueBarUtil;
import com.hzxmkuar.wumeihui.base.view.RecycleViewHeadView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.login.UserInfoTo;
import hzxmkuar.com.applibrary.impl.FragmentPermissionListener;
import hzxmkuar.com.applibrary.impl.PermissionListener;
import util.photopick.PhotoPickerActivity;
import util.photopick.PhotoPreviewActivity;
import util.photopick.SelectModel;
import util.photopick.intent.PhotoPickerIntent;
import util.photopick.intent.PhotoPreviewIntent;

/**
 * Created by xzz on 2018/8/14.
 */

public class BaseActivity<T> extends FragmentActivity implements FragmentPermissionListener {
    public ArrayList<String> imagePaths = new ArrayList<>();
    protected GridLayout pictureLayout;
    protected Context appContext;
    protected UserInfoHelp userInfoHelp=new UserInfoHelp();
    protected UserInfoTo userInfoTo;
    public BaseAdapter baseAdapter;
    public LRecyclerView mRecycleView;
    private int maxNum;
    protected ImageView singleImage;
    protected View headView;
    private String imagePathString;
    private int maxImageNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatueBarUtil.setStatueBarTransparent(getWindow());
        StatueBarUtil.setStatueBarTextBlack(getWindow());
        appContext=this;
        userInfoTo=userInfoHelp.getUserInfo();
        ActivityManager.activityList.add(this);
    }

    protected void setTitleName(String name){
       View back=findViewById(R.id.back);
        if (back!=null)
            back.setOnClickListener(v -> onBackPressed());
        TextView title=findViewById(R.id.title_name);
        if (title!=null)
            title.setText(name);
        if (findViewById(R.id.finish)!=null)
            findViewById(R.id.finish).setOnClickListener(v -> {
                finishClick();
            });
    }

    public void goToAnimation(int type) {

        switch (type) {
            case 1:
                // 跳转到下一个界面
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case 2:
                // 按返回
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case 3:
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }

    protected void showMessage(String message){
        Toast.makeText(appContext,message,Toast.LENGTH_LONG).show();
    }

    protected void finishClick(){

    }

    public int getScreenWidth() {
        WindowManager wm = (WindowManager) MainApp.appContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();
    }
    public int getScreenHeight() {
        WindowManager wm = (WindowManager) MainApp.appContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getHeight();
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
                imagePaths.remove(imagePaths.get((Integer) mView.getTag()));
                setPostImageLayout(itemImageLayout);
            });
            itemImageLayout.addView(mView);
        }
    }

    protected void setPostImageLayoutNight(GridLayout itemImageLayout,int width) {
        pictureLayout = itemImageLayout;
        maxNum=8;

        itemImageLayout.removeAllViews();

        for (int i = 0; i < (imagePaths.size() == 8 ? 8 : imagePaths.size() + 1) && i < 8; i++) {
            View mView = View.inflate(appContext, R.layout.circle_post_image_item, null);
            RoundedImageView imageView = mView.findViewById(R.id.image_view);
            mView.findViewById(R.id.delete_image).setVisibility((imagePaths.size() == 0 || imagePaths.size() == i) ? View.GONE : View.VISIBLE);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(appContext).load(i < imagePaths.size() ? imagePaths.get(i) : R.drawable.post_image_default).into(imageView);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width =  width * getScreenWidth()/ 750 ;
            layoutParams.height = width * getScreenWidth()/ 750;
            layoutParams.setMarginStart((int) (24.0 / 750 * getScreenWidth()));
            mView.setLayoutParams(layoutParams);
            mView.setTag(i);
            mView.setOnClickListener(view -> getPermissionPhoto(Manifest.permission.CAMERA, view, this));
            mView.findViewById(R.id.delete_image).setOnClickListener(view -> {
                imagePaths.remove(imagePaths.get((Integer) mView.getTag()));
                setPostImageLayout(itemImageLayout);
            });
            itemImageLayout.addView(mView);
        }
    }
    protected void setPostImageLayout(GridLayout itemImageLayout,int width) {
        pictureLayout = itemImageLayout;

        itemImageLayout.removeAllViews();

        for (int i = 0; i < (imagePaths.size() == 4 ? 4 : imagePaths.size() + 1) && i < 4; i++) {
            View mView = View.inflate(appContext, R.layout.circle_post_image_item, null);
            RoundedImageView imageView = mView.findViewById(R.id.image_view);
            mView.findViewById(R.id.delete_image).setVisibility((imagePaths.size() == 0 || imagePaths.size() == i) ? View.GONE : View.VISIBLE);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(appContext).load(i < imagePaths.size() ? imagePaths.get(i) : R.drawable.post_image_default).into(imageView);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = width * getScreenWidth()/ 750;
            layoutParams.height = width * getScreenWidth()/ 750;
            if (i!=0)
            layoutParams.setMarginStart((int) (20.0 / 750 * getScreenWidth()));
            mView.setLayoutParams(layoutParams);
            mView.setTag(i);
            mView.setOnClickListener(view -> getPermissionPhoto(Manifest.permission.CAMERA, view, this));
            mView.findViewById(R.id.delete_image).setOnClickListener(view -> {
                imagePaths.remove(imagePaths.get((Integer) mView.getTag()));
                setPostImageLayout(itemImageLayout);
            });
            itemImageLayout.addView(mView);
        }
    }

    protected void setPostImageLayout(GridLayout itemImageLayout,int width,int num) {
        pictureLayout = itemImageLayout;
        maxNum=num;
        itemImageLayout.removeAllViews();

        for (int i = 0; i < (imagePaths.size() == num ? num : imagePaths.size() + 1) && i < num; i++) {
            View mView = View.inflate(appContext, R.layout.circle_post_image_item, null);
            RoundedImageView imageView = mView.findViewById(R.id.image_view);
            mView.findViewById(R.id.delete_image).setVisibility((imagePaths.size() == 0 || imagePaths.size() == i) ? View.GONE : View.VISIBLE);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(appContext).load(i < imagePaths.size() ? imagePaths.get(i) : R.drawable.post_image_default).into(imageView);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = width * getScreenWidth()/ 750;
            layoutParams.height = width * getScreenWidth()/ 750;
            if (i!=0)
                layoutParams.setMarginStart((int) (20.0 / 750 * getScreenWidth()));
            mView.setLayoutParams(layoutParams);
            mView.setTag(i);
            mView.setOnClickListener(view ->{
          pictureLayout.setTag(itemImageLayout.getTag());
                getPermissionPhoto(Manifest.permission.CAMERA, view, this);
            } );
            mView.findViewById(R.id.delete_image).setOnClickListener(view -> {
                imagePaths.remove(imagePaths.get((Integer) mView.getTag()));
                setPostImageLayout(itemImageLayout);
            });
            itemImageLayout.addView(mView);
        }
    }

    /**
     * 这个view必须设置tag
     */
    public void getPermissionPhoto(String permission, View view, FragmentPermissionListener permissionListener) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(permission).subscribe(grant -> {
            if (grant)
                permissionListener.accept(permission, view);
            else
                permissionListener.refuse(permission);

        });
    }

    @Override
    public void accept(String permission, View view) {
        if (Manifest.permission.CAMERA.equals(permission)) {
            getPermissionPhoto(Manifest.permission.WRITE_EXTERNAL_STORAGE,view,this);


        }
        if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
            getPermissionPhoto(Manifest.permission.READ_EXTERNAL_STORAGE, view, this);
        }
        if (Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission)) {
            if ((Integer) view.getTag() == imagePaths.size()) {
                PhotoPickerIntent intent = new PhotoPickerIntent(appContext);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照
                intent.setMaxTotal(maxNum==0?4:maxNum); // 最多选择照片数量，默认为6
                intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                startActivityForResult(intent,10);
            } else {
                PhotoPreviewIntent intent = new PhotoPreviewIntent(appContext);
                intent.setCurrentItem(0);
                intent.setPhotoPaths(imagePaths);
                startActivityForResult(intent,20);
            }
        }
    }

    @Override
    public void refuse(String permission) {

    }

    protected void setImageLayout(List<Integer> imageList, GridLayout imageLayout){
        for (int i=0;i<imageList.size();i++) {
            ImageView imageView = new ImageView(appContext);
            Glide.with(appContext).load(imageList.get(i)).into(imageView);
            GridLayout.LayoutParams layoutParams=new GridLayout.LayoutParams();
            layoutParams.height=157*getScreenWidth()/750;
            layoutParams.width=157*getScreenWidth()/750;
            if (i!=3)
                layoutParams.rightMargin=20*getScreenWidth()/750;
            else
                layoutParams.rightMargin=0;
            imageView.setLayoutParams(layoutParams);
            imageLayout.addView(imageView);
        }
    }
    protected void setImageLayout(List<Integer> imageList, GridLayout imageLayout,int width){
        for (int i=0;i<imageList.size();i++) {
            ImageView imageView = new ImageView(appContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(appContext).load(imageList.get(i)).into(imageView);
            GridLayout.LayoutParams layoutParams=new GridLayout.LayoutParams();
            layoutParams.height=width*getScreenWidth()/750;
            layoutParams.width=width*getScreenWidth()/750;
            if (i!=3)
                layoutParams.rightMargin=20*getScreenWidth()/750;
            else
                layoutParams.rightMargin=0;
            imageView.setLayoutParams(layoutParams);
            imageLayout.addView(imageView);
        }
    }

    protected void setImageLayout(GridLayout imageLayout,List<String> imageList,int width){
        imagePathString = "";
        for (int i=0;i<imageList.size();i++) {
            ImageView imageView = new ImageView(appContext);
            imagePathString=imagePathString+imageList.get(i)+",";
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(appContext).load(imageList.get(i)).into(imageView);
            GridLayout.LayoutParams layoutParams=new GridLayout.LayoutParams();
            layoutParams.height=width*getScreenWidth()/750;
            layoutParams.width=width*getScreenWidth()/750;
            if (i!=3)
                layoutParams.rightMargin=20*getScreenWidth()/750;
            else
                layoutParams.rightMargin=0;
            imageView.setTag(imageList.get(i));
            imageView.setLayoutParams(layoutParams);
            imageView.setOnClickListener(view -> {

                Intent intent = new Intent(appContext, PostImageDetailActivity.class);
                intent.putExtra("CurrentPath", (String) view.getTag());
               if (imagePathString.length()>0)
                intent.putExtra("PathList", imagePathString.substring(0,imagePathString.length()-1));
//                startActivity(intent);
//                goToAnimation(1);
            });
            imageLayout.addView(imageView);

        }
    }

    protected void setImageLayout(String imagePath, GridLayout imageLayout,int width) {
        if (TextUtils.isEmpty(imagePath))
            return;
        imageLayout.removeAllViews();
        String path[] = imagePath.split(imagePath.contains(",") ? "," : ";");
        for (String aPath : path) {
            PhotoView photoView = new PhotoView(this);
            disPlayImage(photoView, aPath);
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = (int) (getScreenWidth() * width / 750.0);
            layoutParams.height = (int) (getScreenWidth() * width / 750.0);
            layoutParams.leftMargin = (int) (24.0 / 750 * getScreenWidth());
            photoView.setTag(aPath);
            photoView.setLayoutParams(layoutParams);
            photoView.setOnClickListener(view -> {
                Intent intent = new Intent(appContext, PostImageDetailActivity.class);
                intent.putExtra("CurrentPath", (String) view.getTag());
                intent.putExtra("PathList", imagePath);
               startActivity(intent);
                goToAnimation(1);
            });
            imageLayout.addView(photoView);
        }
    }
    protected void disPlayRoundImage(ImageView imageView) {

        Glide.with(appContext).load(R.drawable.post_image_default).transform(new GlideCircleTransform(appContext)).into(imageView);



    }
    protected void disPlayRoundImage(ImageView imageView,String url) {

        Glide.with(appContext).load(url).transform(new GlideCircleTransform(appContext)).placeholder(R.drawable.user_default_icon).into(imageView);



    }
    protected void displayImage(ImageView imageView,String url) {

        Glide.with(appContext).load(url).into(imageView);

    }
    protected void displayImage(ImageView imageView,String url,int icon) {

        Glide.with(appContext).load(url).placeholder(icon).error(icon).into(imageView);

    }

    protected void disPlayImage(ImageView imageView,String imageUrl) {
        if (TextUtils.isEmpty(imageUrl))
            Glide.with(appContext).load(R.drawable.default_head_image).into(imageView);
        else if (imageUrl.contains("http")) {
            System.out.println(imageUrl + "?imageMogr2/thumbnail/500x");
            Glide.with(appContext).load(imageUrl + "?imageMogr2/thumbnail/500x").into(imageView);
        }


    }


    public void loadDataSuccess(T data) {

    }

    public void loadDataSuccess(MessageTo data) {

    }

    public void loadDataSuccess(List data, int total) {

    }

    public void loadDataSuccess(List<T> data) {

    }

    protected void submitDataSuccess(T data) {

    }

    protected boolean checkPhone(String phone){
        if (TextUtils.isEmpty(phone)){
            showMessage("请输入手机号");
            return false;
        }
        if (phone.length()!=11){
            showMessage("请输入正确手机号");
            return false;
        }
        return true;
    }



    protected void setRecycleView(BaseAdapter adapter, LRecyclerView recycleView, BasePresenter presenter) {
        this.baseAdapter = adapter;
        this.mRecycleView = recycleView;
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
       if (headView!=null)
           lRecyclerViewAdapter.addHeaderView(headView);
        recycleView.setRefreshHeader(new RecycleViewHeadView(appContext));
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.appColor, R.color.appColor, R.color.transparent);
        recycleView.setOnLoadMoreListener(presenter::recycleViewLoadMore);
        recycleView.setOnRefreshListener(presenter::recycleViewRefresh);
        lRecyclerViewAdapter.setOnItemClickListener(presenter::recycleItemClick);
//        recycleView.forceToRefresh();


    }

    protected void setRecycleView(BaseAdapter adapter, LRecyclerView recycleView, BasePresenter presenter,int number) {
        this.baseAdapter = adapter;
        this.mRecycleView = recycleView;
        recycleView.setLayoutManager(new GridLayoutManager(this,number));

        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setRefreshHeader(new RecycleViewHeadView(appContext));
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.appColor, R.color.appColor, R.color.transparent);
        recycleView.setOnLoadMoreListener(presenter::recycleViewLoadMore);
        recycleView.setOnRefreshListener(presenter::recycleViewRefresh);
        lRecyclerViewAdapter.setOnItemClickListener(presenter::recycleItemClick);
//        recycleView.forceToRefresh();


    }

    protected void setRecycleViewRefresh(BaseAdapter adapter, LRecyclerView recycleView, BasePresenter presenter) {
        this.baseAdapter = adapter;
        this.mRecycleView = recycleView;
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setRefreshHeader(new RecycleViewHeadView(appContext));
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.appColor, R.color.appColor, R.color.transparent);
        recycleView.setOnLoadMoreListener(presenter::recycleViewLoadMore);
        recycleView.setOnRefreshListener(presenter::recycleViewRefresh);
        lRecyclerViewAdapter.setOnItemClickListener(presenter::recycleItemClick);
//        recycleView.forceToRefresh();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case 10:
                    imagePaths = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    if (pictureLayout != null) {
                        if (maxNum==8)
                            setPostImageLayoutNight(pictureLayout,120);
                        else
                        setPostImageLayout(pictureLayout);
                    }
                    else {
                        singleImage.setTag(null);
                        Glide.with(appContext).load(imagePaths.get(0)).into(singleImage);
                    }


                    break;
                // 预览
                case 20:
                    imagePaths = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    if (pictureLayout != null)
                        if (maxNum==8)
                            setPostImageLayoutNight(pictureLayout,120);
                        else
                            setPostImageLayout(pictureLayout);
                    else {
                        singleImage.setTag(null);
                        Glide.with(appContext).load(imagePaths.get(0)).into(singleImage);
                    }
                    break;
            }
        }
    }

    public void getPermission(String permission, PermissionListener permissionListener) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(permission).subscribe(grant -> {
            if (grant)
                permissionListener.accept(permission);
            else
                permissionListener.refuse(permission);

        });
    }

    public void recycleItemClick(View view, int position, T data) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToAnimation(2);
    }
}
