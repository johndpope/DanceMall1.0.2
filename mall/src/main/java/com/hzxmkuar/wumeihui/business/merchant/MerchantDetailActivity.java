package com.hzxmkuar.wumeihui.business.merchant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.AlertDialog;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.PostImageDetailActivity;
import com.hzxmkuar.wumeihui.base.util.AppUtil;
import com.hzxmkuar.wumeihui.business.merchant.presenter.MerchantDetailPresenter;
import com.hzxmkuar.wumeihui.databinding.ServiceCommentItemBinding;
import com.hzxmkuar.wumeihui.message.ChatActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.CaseDetailActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.InquiryDesActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.SelectDemandActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.ServiceCaseActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.ServiceCommentActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCollectTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantDetailTo;
import hzxmkuar.com.applibrary.impl.PermissionListener;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/30.
 **/

public class MerchantDetailActivity extends BaseActivity {
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.service_name)
    TextView serviceName;
    @BindView(R.id.shop_type)
    TextView shopType;
    @BindView(R.id.star)
    TextView star;
    @BindView(R.id.deal_number)
    TextView dealNumber;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.merchant_layout)
    GridLayout merchantLayout;
    @BindView(R.id.case_layout)
    GridLayout caseLayout;
    @BindView(R.id.comment_layout)
    GridLayout commentLayout;
    @BindView(R.id.shop_image)
    ImageView shopImage;
    @BindView(R.id.comment_count)
    TextView commentCount;
    @BindView(R.id.collect_icon)
    View collectIcon;
    private MerchantDetailTo detailTo;
    private MerchantDetailPresenter presenter;
    private String imagePathString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_detail);
        ButterKnife.bind(this);
        setTitleName(Constant.MERCHANT_DETAIL);
        presenter = new MerchantDetailPresenter(this);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void loadDataSuccess(Object data) {
        detailTo = (MerchantDetailTo) data;
        setCaseLayout(detailTo.getCase_list());
        setServiceLayout(detailTo.getService_list());
        setCommentLayout(detailTo.getUser_reviews());
        shopName.setText(detailTo.getBus_info().getShop_name());
        shopType.setText(detailTo.getBus_info().getStype() );
        star.setText("综合评分 " + detailTo.getBus_info().getRatings());
        dealNumber.setText("成交 " + detailTo.getBus_info().getOrder_num() + "笔");
        serviceName.setText(detailTo.getBus_info().getServices());
        address.setText(detailTo.getBus_info().getFinaladdress());
        displayImage(shopImage, detailTo.getBus_info().getShop_banner());
        collectIcon.setBackgroundResource(detailTo.getIs_collected()==1?R.drawable.merchant_collection:R.drawable.collection_icon);


    }

    private void setCommentLayout(MerchantDetailTo.UserReviewsBean commentBean) {
        if (commentBean == null)
            return;
        commentCount.setText("用户点评 (" + commentBean.getTotal() + ")");
        List<MerchantDetailTo.UserReviewsBean.ListsBean> commentList = commentBean.getLists();
        if (commentList == null || commentList.size() == 0)
            return;
        for (int i = 0; i < commentList.size(); i++) {
            View mView = View.inflate(appContext, R.layout.service_comment_item, null);
            ServiceCommentItemBinding binding = DataBindingUtil.bind(mView);
            MerchantDetailTo.UserReviewsBean.ListsBean mode = commentList.get(i);
            displayImage(binding.headImage, mode.getUser_info().getHeadimgurl());
            binding.userName.setText(mode.getUser_info().getUsername());
            binding.userTag.setText(mode.getUser_info().getUser_tag());
            binding.time.setText(mode.getDateline());
            binding.address.setText(mode.getAddress());
            binding.service.setText(mode.getServices());
            binding.content.setText(mode.getContent());
            List<String> imageList = new ArrayList<>();
            Observable.from(mode.getPic_list()).subscribe(picListBean -> imageList.add(picListBean.getPic()));
            setImageLayoutLocal(binding.imageLayout, imageList, 158);
            commentLayout.addView(mView);


            binding.starLayout.removeAllViews();
            for (int k=0;k<mode.getScore();k++){
                View starView=new View(appContext);
                starView.setBackgroundResource(R.drawable.service_star_icon);
                GridLayout.LayoutParams layoutParams=new GridLayout.LayoutParams();
                layoutParams.width=getScreenWidth()*25/750;
                layoutParams.height=getScreenWidth()*25/750;
                layoutParams.leftMargin=20;
                starView.setLayoutParams(layoutParams);
                binding.starLayout.addView(starView);
            }
        }
    }


    private void setServiceLayout(List<MerchantDetailTo.ServiceListBean> serviceList) {
        if (serviceList == null || serviceList.size() == 0)
            return;
        for (int i = 0; i < serviceList.size(); i++) {
            View mView = View.inflate(appContext, R.layout.search_detail_item, null);
            ((TextView) mView.findViewById(R.id.type_name)).setText(serviceList.get(i).getCate_name());
            GridLayout childView = mView.findViewById(R.id.type_layout);
            for (int k = 0; k < serviceList.get(i).getLists().size(); k++) {
                View serviceView = View.inflate(appContext, R.layout.search_detail_child_item, null);
                MerchantDetailTo.ServiceListBean.ListsBeanX serviceTo = serviceList.get(i).getLists().get(k);
                ((TextView) serviceView.findViewById(R.id.type_name)).setText(serviceTo.getService_name());
                displayImage(serviceView.findViewById(R.id.head_image),serviceTo.getService_img());
                serviceView.findViewById(R.id.inquiry).setOnClickListener(view -> {

                    presenter.inquiry(serviceTo.getId());

               });
                childView.addView(serviceView);
            }
            merchantLayout.addView(mView);

        }
    }

    private void setCaseLayout(List<MerchantDetailTo.CaseListBean> caseList) {
        if (caseList == null || caseList.size() == 0)
            return;
        for (int i = 0; i < caseList.size(); i++) {
            MerchantDetailTo.CaseListBean caseTo = caseList.get(i);
            View mView = View.inflate(appContext, R.layout.service_case_item, null);
            ((TextView) mView.findViewById(R.id.case_title)).setText(caseTo.getCase_desc());
            displayImage(mView.findViewById(R.id.case_image), caseTo.getCase_img());
            caseLayout.addView(mView);
            mView.setOnClickListener(view -> {
                Intent intent=new Intent(appContext, CaseDetailActivity.class);
                intent.putExtra("CaseId",caseTo.getId());
                startActivity(intent);
                goToAnimation(1);
            });
        }
    }

    @OnClick({R.id.more_case_layout, R.id.more_comment_layout, R.id.chat_layout, R.id.phone_layout, R.id.collect_layout,R.id.inquiry_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more_case_layout:
                Intent intent = new Intent(appContext, ServiceCaseActivity.class);
                intent.putExtra("MerchantId", getIntent().getIntExtra("MerchantId", 0));
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.more_comment_layout:
                intent = new Intent(appContext, ServiceCommentActivity.class);
                intent.putExtra("MerchantId", getIntent().getIntExtra("MerchantId", 0));
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.chat_layout:
                if (userInfoTo.getMobile().equals(detailTo.getBus_info().getTelephone())){
                    showMessage("自己不能与自己聊天");
                    return;
                }
                intent = new Intent(appContext, ChatActivity.class);
                intent.putExtra("UserId", detailTo.getBus_info().getTelephone());
                intent.putExtra("Name",detailTo.getBus_info().getShop_name());
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.phone_layout:
                if (!AppUtil.readSIMCard(appContext,this))
                    return;
                AlertDialog.show(this, "确认拨打电话").setOnClickListener(view1 -> {
                    AlertDialog.dismiss();

                    getPermission(Manifest.permission.CALL_PHONE, new PermissionListener() {
                        @Override
                        public void accept(String permission) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            Uri data = Uri.parse("tel:" + detailTo.getBus_info().getTelephone());
                            intent.setData(data);
                            startActivity(intent);

                        }

                        @Override
                        public void refuse(String permission) {

                        }
                    });
                });

                break;

            case R.id.collect_layout:
                presenter.collectMerchant();
                break;
            case R.id.inquiry_layout:
                intent=new Intent(appContext, SelectDemandActivity.class);
                intent.putExtra("MerchantDetailTo",detailTo);
                startActivity(intent);
                goToAnimation(1);

                break;
        }
    }

    public void setCollect(MerchantCollectTo data) {

        if (data.getIs_collected() == 1) {
            showMessage("收藏成功");
            collectIcon.setBackgroundResource(R.drawable.merchant_collection);
        } else {
            showMessage("取消收藏成功");
            collectIcon.setBackgroundResource(R.drawable.collection_icon);
        }
    }

    private void setImageLayoutLocal(GridLayout imageLayout,List<String> imageList,int width){
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
            imageView.setTag(R.id.image_path,imagePathString);
            imageView.setLayoutParams(layoutParams);
            imageView.setOnClickListener(view -> {

                Intent intent = new Intent(appContext, PostImageDetailActivity.class);
                intent.putExtra("CurrentPath", (String) view.getTag());

                    intent.putExtra("PathList", ((String)view.getTag(R.id.image_path)).substring(0,((String)view.getTag(R.id.image_path)).length()-1));
                startActivity(intent);
                goToAnimation(1);
            });
            imageLayout.addView(imageView);

        }
    }

    @Override
    protected void submitDataSuccess(Object data) {

        Intent intent = new Intent(appContext, InquiryDesActivity.class);
        intent.putExtra("MerchantDetailTo",detailTo);
        startActivity(intent);
        goToAnimation(1);
    }
}
