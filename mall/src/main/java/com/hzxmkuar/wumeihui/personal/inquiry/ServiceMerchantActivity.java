package com.hzxmkuar.wumeihui.personal.inquiry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.util.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/31.
 **/

public class ServiceMerchantActivity extends BaseActivity {
    @BindView(R.id.merchant_layout)
    GridLayout merchantLayout;
    @BindView(R.id.case_layout)
    GridLayout caseLayout;
    @BindView(R.id.comment_layout)
    GridLayout commentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_detail);
        ButterKnife.bind(this);
        setMerchantView();
        setCaseView();
        setCommentView();
    }

    private void setMerchantView() {

//        List<DemandSearchTo> demandSearchList = new ArrayList<>();
//        DemandSearchTo searchTo = new DemandSearchTo();
//        searchTo.setTypeName("视频");
//        searchTo.setChildList(new ArrayList<>(Arrays.asList(new DemandSearchChildTo("P3租赁"), new DemandSearchChildTo("P3租赁"), new DemandSearchChildTo("P3租赁"))));
//        DemandSearchTo searchTo1 = new DemandSearchTo();
//        searchTo1.setTypeName("灯光");
//        searchTo1.setChildList(new ArrayList<>(Arrays.asList(new DemandSearchChildTo("LED大灯"), new DemandSearchChildTo("LED大灯"))));
//        demandSearchList.add(searchTo);
//        demandSearchList.add(searchTo1);
//        for (int i = 0; i < demandSearchList.size(); i++) {
//            View mView = View.inflate(appContext, R.layout.search_detail_item, null);
//            for (int j = 0; j < demandSearchList.get(i).getChildList().size(); j++) {
//                View childView=View.inflate(appContext,R.layout.search_detail_child_item,null);
//                DemandSearchChildTo searchChildTo=demandSearchList.get(i).getChildList().get(j);
//                ((TextView)mView.findViewById(R.id.type_name)).setText(searchChildTo.getTypeName());
//                ((GridLayout)mView.findViewById(R.id.type_layout)).addView(childView);
//            }
//            merchantLayout.addView(mView);
//
//        }

    }

    private void setCaseView(){
        for (int i=0;i<2;i++){
            View mView=View.inflate(appContext,R.layout.service_case_item,null);
            Glide.with(this).load(R.drawable.test2).transform(new GlideRoundTransform(appContext,10)).into((ImageView) mView.findViewById(R.id.case_image));
            caseLayout.addView(mView);
        }
    }
    private void setCommentView(){
        for (int i=0;i<2;i++){
            View mView=View.inflate(appContext,R.layout.service_comment_item,null);
            commentLayout.addView(mView);
            List<Integer>imageList=new ArrayList<>();
            imageList.add(R.drawable.post_image_default);
            imageList.add(R.drawable.post_image_default);
            imageList.add(R.drawable.post_image_default);
            imageList.add(R.drawable.post_image_default);
            setImageLayout(mView.findViewById(R.id.image_layout),imageList);
        }
    }

    private void setImageLayout(GridLayout imageLayout,List<Integer>imageList){
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
}
