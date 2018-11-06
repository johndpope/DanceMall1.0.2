package com.hzxmkuar.wumeihui.base.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.PostImageDetailActivity;
import com.hzxmkuar.wumeihui.base.util.GlideCircleTransform;

import java.util.List;



/**
 * Created by xzz on 2017/6/24.
 **/

public class BaseAdapter<T, H> extends RecyclerView.Adapter<BindingHolder<H>> {

    protected List<T> mList;
    protected Context mContext;
    private Activity activity;
    private String imagePathString;

    public BaseAdapter(Activity context) {
        this.mContext = context;
        activity = context;
    }

    public BaseAdapter(Context context) {
        this.mContext = context;

    }

    @NonNull
    @Override
    public BindingHolder<H> onCreateViewHolder(ViewGroup parent,int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BindingHolder<H> holder,int position) {
        holder.itemView.setOnClickListener(view -> {
            if (listener != null)
                listener.itemClick(mList.get(position),position,holder.itemView);

        });

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 :mList.size();
    }

    public void setList(List<T> list) {
        this.mList = list;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener<T> {
        void itemClick(T mode, int position, View view);
    }

    protected OnItemClickListener<T> listener;

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.listener = listener;

    }

    public int getScreenWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        return metrics.widthPixels;
    }

    protected void disPlayImage(ImageView imageView,String imageUrl) {
        if (TextUtils.isEmpty(imageUrl))
            Glide.with(mContext).load(R.drawable.post_image_default).into(imageView);
        else if (imageUrl.contains("http")) {
            System.out.println(imageUrl + "?imageMogr2/thumbnail/500x");
            Glide.with(mContext).load(imageUrl + "?imageMogr2/thumbnail/500x").into(imageView);
        }


    }
    protected void disPlayRoundImage(ImageView imageView) {

            Glide.with(mContext).load(R.drawable.post_image_default).transform(new GlideCircleTransform(mContext)).into(imageView);



    }
    protected void disPlayRoundImage(ImageView imageView,String url) {

            Glide.with(mContext).load(url).transform(new GlideCircleTransform(mContext)).into(imageView);



    }
    protected void disPlayRoundImage(ImageView imageView,String url,int icon) {

        Glide.with(mContext).load(url).transform(new GlideCircleTransform(mContext)).placeholder(icon).error(icon).into(imageView);



    }
    protected void disPlayImage(ImageView imageView,String imageUrl,int icon) {
        if (TextUtils.isEmpty(imageUrl))
            Glide.with(mContext).load(icon).into(imageView);
        else if (imageUrl.contains("http")) {
            System.out.println(imageUrl + "?imageMogr2/thumbnail/500x");
            Glide.with(mContext).load(imageUrl + "?imageMogr2/thumbnail/500x").placeholder(icon).error(icon).into(imageView);
        }

        else
            Glide.with(mContext).load("http://7xk6y7.com2.z0.glb.qiniucdn.com/" + imageUrl+"?imageMogr2/thumbnail/500x").placeholder(icon).error(icon).into(imageView);

    }

    public void goToAnimation(int type) {


        switch (type) {
            case 1:
                // 跳转到下一个界面
                activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case 2:
                // 按返回
                activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;

            case 3:
                activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;

        }
    }

    protected void setImageLayout(String imagePath, GridLayout imageLayout) {
        if (TextUtils.isEmpty(imagePath))
            return;
        imageLayout.removeAllViews();
        String path[] = imagePath.split(imagePath.contains(",") ? "," : ";");
        for (String aPath : path) {
            PhotoView photoView = new PhotoView(mContext);
            disPlayImage(photoView, aPath);
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = (int) (getScreenWidth() * 151 / 750.0);
            layoutParams.height = (int) (getScreenWidth() * 151 / 750.0);
            layoutParams.leftMargin = (int) (24.0 / 750 * getScreenWidth());
            layoutParams.bottomMargin = (int) (24.0 / 750 * getScreenWidth());
            photoView.setTag(aPath);
            photoView.setLayoutParams(layoutParams);
            photoView.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, PostImageDetailActivity.class);
                intent.putExtra("CurrentPath", (String) view.getTag());
                intent.putExtra("PathList", imagePath);
                mContext.startActivity(intent);
                goToAnimation(1);
            });
            imageLayout.addView(photoView);
        }
    }
    protected void setImageLayout(List<Integer>imageList,GridLayout imageLayout){
        for (int i=0;i<imageList.size();i++) {
            ImageView imageView = new ImageView(mContext);
            Glide.with(mContext).load(imageList.get(i)).into(imageView);
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

    protected void setImageLayout(List<String>imageList,GridLayout imageLayout,int width){
        for (int i=0;i<imageList.size();i++) {
            ImageView imageView = new ImageView(mContext);
            Glide.with(mContext).load(imageList.get(i)).into(imageView);
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
            ImageView imageView = new ImageView(mContext);
            imagePathString=imagePathString+imageList.get(i)+",";
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(mContext).load(imageList.get(i)).into(imageView);
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

                Intent intent = new Intent(mContext, PostImageDetailActivity.class);
                intent.putExtra("CurrentPath", (String) view.getTag());
                if (imagePathString.length()>0)
                    intent.putExtra("PathList", imagePathString.substring(0,imagePathString.length()-1));
//                startActivity(intent);
//                goToAnimation(1);
            });
            imageLayout.addView(imageView);

        }
    }

}
