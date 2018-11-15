package com.hzxmkuar.wumeihui.business.main.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.hzxmkuar.wumeihui.MainApp;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.base.CommonDialog;
import com.hzxmkuar.wumeihui.base.util.AppUtil;
import com.hzxmkuar.wumeihui.base.util.SpUtil;
import com.hzxmkuar.wumeihui.business.main.presenter.WorkSignPresenter;
import com.hzxmkuar.wumeihui.business.merchant.MerchantManagerActivity;
import com.hzxmkuar.wumeihui.business.myself.BondActivity;
import com.hzxmkuar.wumeihui.business.myself.ExtensionActivity;
import com.hzxmkuar.wumeihui.business.myself.MoneyManagerActivity;
import com.hzxmkuar.wumeihui.business.myself.MyMerchantOrderActivity;
import com.hzxmkuar.wumeihui.business.myself.MyQuoteActivity;
import com.hzxmkuar.wumeihui.circle.MyCollectionActivity;
import com.hzxmkuar.wumeihui.circle.MyLookActivity;
import com.hzxmkuar.wumeihui.circle.MyPostActivity;
import com.hzxmkuar.wumeihui.message.ChatActivity;
import com.hzxmkuar.wumeihui.personal.MainActivity;
import com.hzxmkuar.wumeihui.personal.myself.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import hzxmkuar.com.applibrary.domain.merchant.MerchantMySelfTo;
import hzxmkuar.com.applibrary.impl.PermissionListener;

/**
 * Created by xzz on 2018/8/15.
 */

public class WorkFragment extends BaseFragment {

    @BindView(R.id.head_image)
    ImageView headImage;
    Unbinder unbinder;
    @BindView(R.id.nick_name)
    TextView nickName;
    @BindView(R.id.role_name)
    TextView roleName;
    @BindView(R.id.no_quote_num)
    TextView noQuoteNum;
    @BindView(R.id.quote_num)
    TextView quoteNum;

    @BindView(R.id.waite_confirm_num)
    TextView waiteConfirmNum;
    @BindView(R.id.waite_pay_end_num)
    TextView waitePayEndNum;
    @BindView(R.id.waite_service_num)
    TextView waiteServiceNum;
    @BindView(R.id.waite_evaluate_num)
    TextView waiteEvaluateNum;
    @BindView(R.id.publish_num)
    TextView publishNum;
    @BindView(R.id.collect_num)
    TextView collectNum;
    @BindView(R.id.look_num)
    TextView lookNum;
    @BindView(R.id.integration)
    View integration;
    private MerchantMySelfTo mode;
    private CommonDialog dialog;
    private WorkSignPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = View.inflate(appContext, R.layout.fragment_work, null);
        unbinder = ButterKnife.bind(this, mView);
        userInfoTo = userInfoHelp.getUserInfo();
        presenter = new WorkSignPresenter(this);
        setView();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getMyselfInfo();
    }

    private void setView() {
        disPlayRoundImage(headImage, userInfoTo.getFace_url());
        nickName.setText(userInfoTo.getUsername());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.head_image, R.id.nick_name, R.id.role_name, R.id.coupon_layout, R.id.integration, R.id.quote_layout, R.id.order_layout
            , R.id.look_layout, R.id.collection_layout, R.id.publish_layout, R.id.setting_layout, R.id.money_manager_layout, R.id.custom_service_layout, R.id.merchant_manager_layout, R.id.switch_user,
            R.id.share_layout, R.id.no_quote_layout, R.id.quote_num_layout, R.id.order_id_layout1, R.id.order_id_layout2, R.id.order_id_layout3, R.id.order_id_layout4
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_image:
            case R.id.nick_name:
            case R.id.role_name:
                Intent intent = new Intent(appContext, MerchantManagerActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.coupon_layout:
                intent = new Intent(appContext, ExtensionActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.integration:
                intent = new Intent(appContext, BondActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.quote_layout:
                intent = new Intent(appContext, MyQuoteActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.order_layout:
                intent = new Intent(appContext, MyMerchantOrderActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.publish_layout:
                intent = new Intent(appContext, MyPostActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.collection_layout:
                intent = new Intent(appContext, MyCollectionActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.look_layout:
                intent = new Intent(appContext, MyLookActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.setting_layout:
                intent = new Intent(appContext, SettingActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.merchant_manager_layout:
                intent = new Intent(appContext, MerchantManagerActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.money_manager_layout:
                intent = new Intent(appContext, MoneyManagerActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.custom_service_layout:
                NiftyDialogBuilder dialog = NiftyDialogBuilder.getInstance(getActivity());
                dialog.setContentView(R.layout.my_self_contact_layout);
                dialog.show();
                dialog.findViewById(R.id.phone).setOnClickListener(view1 -> {
                    dialog.dismiss();
                    if (!AppUtil.readSIMCard(appContext,getActivity()))
                        return;
                    getPermission(Manifest.permission.CALL_PHONE, new PermissionListener() {
                        @Override
                        public void accept(String permission) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            Uri data = Uri.parse("tel:" + mode.getKf_tel());
                            intent.setData(data);
                            startActivity(intent);

                        }

                        @Override
                        public void refuse(String permission) {

                        }
                    });
                });
                dialog.findViewById(R.id.chat).setOnClickListener(view1 -> {
                    dialog.dismiss();
                    Intent intent1 = new Intent(appContext, ChatActivity.class);
                    intent1.putExtra("Name", "在线客服");
                    intent1.putExtra("UserId", mode.getKf_tel());
                    startActivity(intent1);
                });
                dialog.findViewById(R.id.parent).setOnClickListener(view1 -> dialog.dismiss());

                break;
            case R.id.switch_user:
                intent = new Intent(appContext, MainActivity.class);

                startActivity(intent);
                getActivity().finish();
                goToAnimation(1);
                break;
            case R.id.share_layout:
                showShare();
                break;
            case R.id.no_quote_layout:
                intent = new Intent(appContext, MyQuoteActivity.class);
                intent.putExtra("Index", 1);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.quote_num_layout:
                intent = new Intent(appContext, MyQuoteActivity.class);
                intent.putExtra("Index", 2);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.order_id_layout1:
                intent = new Intent(appContext, MyMerchantOrderActivity.class);
                intent.putExtra("Index", 1);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.order_id_layout2:
                intent = new Intent(appContext, MyMerchantOrderActivity.class);
                intent.putExtra("Index", 2);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.order_id_layout3:
                intent = new Intent(appContext, MyMerchantOrderActivity.class);
                intent.putExtra("Index", 3);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.order_id_layout4:
                intent = new Intent(appContext, MyMerchantOrderActivity.class);
                intent.putExtra("Index", 4);
                startActivity(intent);
                goToAnimation(1);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    public void setMySelfView(MerchantMySelfTo mode) {

        this.mode = mode;
        noQuoteNum.setText(mode.getMy_inquiry_busoffer().getBusoffer_no() + "");
        quoteNum.setText(mode.getMy_inquiry_busoffer().getBusoffer_yes() + "");
        waiteConfirmNum.setText(mode.getMy_order().getUncomfirm() + "");
        waitePayEndNum.setText(mode.getMy_order().getUnpay() + "");
        waiteServiceNum.setText(mode.getMy_order().getUnservice() + "");
        waiteEvaluateNum.setText(mode.getMy_order().getServicing() + "");
        publishNum.setText(mode.getMy_wmgroup().getPublished() + "");
        collectNum.setText(mode.getMy_wmgroup().getCollected() + "");
        lookNum.setText(mode.getMy_wmgroup().getRecent_browse() + "");
        roleName.setText(mode.getUser_tag());
        disPlayRoundImage(headImage, mode.getFace_url());
        nickName.setText(mode.getUsername());
        integration.setVisibility(mode.getIs_deposit()==2?View.GONE:View.VISIBLE);
        SpUtil.put("HaveBond", (mode.getIs_deposit() == 2 || mode.getIs_deposit() == 1));
        SpUtil.put("ChatName", mode.getUsername());
        SpUtil.put("ChatPic", mode.getFace_url());


    }

    public void showShare() {

        dialog = new CommonDialog(getActivity(), getScreenWidth(), (int) (getScreenHeight() * 140.0 / 1344), R.layout.dialog_share_moment, R.style.DialogDown);
        dialog.show();
        dialog.findViewById(R.id.wechat_share).setOnClickListener(view1 -> {
            wChatShare();
            dialog.dismiss();
        });

        dialog.findViewById(R.id.moment_share).setOnClickListener(view1 -> {
            momentShare();

            dialog.dismiss();
        });
        dialog.findViewById(R.id.parent).setOnClickListener(view -> dialog.dismiss());
        dialog.findViewById(R.id.cancel).setOnClickListener(view -> dialog.dismiss());

    }


    public void wChatShare() {
        Wechat.ShareParams shareParams = new Wechat.ShareParams();
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setUrl(MainApp.shareUrl);
        shareParams.setTitle("舞美汇");
        shareParams.setText("中国领先的舞美服务平台");

        shareParams.setImageData(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        Platform weChatApp = ShareSDK.getPlatform(Wechat.NAME);
        weChatApp.share(shareParams);
        dialog.dismiss();
    }


    public void momentShare() {
        WechatMoments.ShareParams shareParams = new WechatMoments.ShareParams();
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setUrl(MainApp.shareUrl);
        shareParams.setTitle("舞美汇");
        shareParams.setText("中国领先的舞美服务平台");
        shareParams.setImageData(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher
        ));
        Platform moment = ShareSDK.getPlatform(WechatMoments.NAME);
        moment.share(shareParams);
        dialog.dismiss();
    }

}
