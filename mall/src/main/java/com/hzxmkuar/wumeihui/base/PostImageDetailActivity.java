package com.hzxmkuar.wumeihui.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.hzxmkuar.wumeihui.R;


/**
 * Created by xzz on 2017/7/1.
 **/

public class PostImageDetailActivity extends BaseActivity {

    private String[] paths;
    private ViewPager viewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_image_detail);
        viewPager = findViewById(R.id.view_pager);


        getIntentData();
    }

    private void getIntentData() {
        String currentPath = getIntent().getStringExtra("CurrentPath");
        String pathList = getIntent().getStringExtra("PathList");
        paths = pathList.contains(";") ? pathList.split(";") : pathList.split(",");
        viewPager.setAdapter(adapter);
        for (int i = 0; i < paths.length; i++) {
            if (currentPath.equals(paths[i])) {
                viewPager.setCurrentItem(i);
                setTitleName(i + 1 + "/" + paths.length);
            }
        }
        setViewPagerListener(paths.length);
    }

    private void setViewPagerListener(int length) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitleName(position + 1 + "/" + length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    PagerAdapter adapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return paths.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView view = new PhotoView(PostImageDetailActivity.this);
            view.enable();
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            view.setTransitionName("LifeImage");
            System.out.println(paths[position] + (paths[position].contains("Camera")?"":"?imageMogr2/thumbnail/500x"));
            Glide.with(appContext).load(paths[position] + (paths[position].contains("Camera")?"":"?imageMogr2/thumbnail/500x")).into(view);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };
}