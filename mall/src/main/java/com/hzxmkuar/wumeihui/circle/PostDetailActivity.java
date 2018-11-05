package com.hzxmkuar.wumeihui.circle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.hzxmkuar.wumeihui.MainApp;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.CommonDialog;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.circle.presenter.PostDetailPresenter;
import com.hzxmkuar.wumeihui.databinding.PostDetailHeadViewBinding;
import com.hzxmkuar.wumeihui.message.ChatActivity;
import com.hzxmkuar.wumeihui.personal.main.adapter.PostCommentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import hzxmkuar.com.applibrary.domain.circle.CollectionTo;
import hzxmkuar.com.applibrary.domain.circle.PostDetailTo;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/2.
 */

public class PostDetailActivity extends BaseActivity {
    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    @BindView(R.id.comment_content)
    EditText commentContent;
    @BindView(R.id.collect)
    View collect;
    @BindView(R.id.comment_count)
    TextView commentCount;
    @BindView(R.id.praise)
    View praise;
    private List<String> imagePath = new ArrayList<>();
    private PostDetailPresenter presenter;
    private PostDetailHeadViewBinding headBind;
    private CommonDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);
        setTitleName(Constant.POST_DETAIL);

        presenter = new PostDetailPresenter(this);
    }

    @SuppressLint("SetTextI18n")
    private void setHeadView(PostDetailTo mode) {
        View headView = View.inflate(appContext, R.layout.post_detail_head_view, null);
        headBind = DataBindingUtil.bind(headView);
        Glide.with(appContext).load(mode.getUser_info().getHeadimgurl()).placeholder(R.drawable.user_default_icon).into(headBind.headImage);
        commentCount.setText(mode.getComments() + "");
        collect.setBackgroundResource(mode.getIs_collected() == 1 ? R.drawable.service_star_icon : R.drawable.service_star_gray_icon);
        headBind.praiseIcon.setBackgroundResource(mode.getIs_likes() == 1 ? R.drawable.praise_already_icon : R.drawable.praise_icon);
        praise.setBackgroundResource(mode.getIs_likes() == 1 ? R.drawable.praise_already_icon : R.drawable.praise_icon);
        praise.setSelected(mode.getIs_likes() == 1);
        headBind.commentCount.setText(mode.getComments() + "");
        headBind.lookCount.setText(mode.getViews() + "");
        headBind.likeCount.setText(mode.getLikes() + "");
        headBind.content.setText(mode.getContent());
        headBind.topic.setText(mode.getTopic());
        headBind.nickName.setText(mode.getUser_info().getUsername());
        headBind.userTag.setText(mode.getUser_info().getUser_tag());
        headBind.postTime.setText(mode.getDateline());
//
        imagePath.clear();
        if (mode.getPic_list() != null && mode.getPic_list().size() > 0) {
            Observable.from(mode.getPic_list()).subscribe(picListBean -> imagePath.add(picListBean.getPic()));
            setImageLayout(headBind.imageLayout, imagePath, 158);
        }

        recycleView.setLayoutManager(new LinearLayoutManager(this));
        PostCommentAdapter commentAdapter = new PostCommentAdapter(this);
        commentAdapter.setList(mode.getComments_list());
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(commentAdapter);
        lRecyclerViewAdapter.addHeaderView(headView);
        recycleView.setAdapter(lRecyclerViewAdapter);
        commentAdapter.setReplyListener(commentTo -> {
            presenter.addComment(commentContent.getText().toString(), commentTo.getId());
        });
        headBind.message.setOnClickListener(view -> {
            Intent intent = new Intent(appContext, ChatActivity.class);
            intent.putExtra("UserId", mode.getUser_info().getTelephone());
            intent.putExtra("Name",mode.getUser_info().getUsername());
            startActivity(intent);
            goToAnimation(1);
        });
        headBind.praise.setOnClickListener(view -> presenter.praise());
        praise.setOnClickListener(view -> presenter.praise());
    }

    @Override
    public void loadDataSuccess(Object data) {
        setHeadView((PostDetailTo) data);


    }

    @OnClick({R.id.collect, R.id.send, R.id.share_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.collect:
                presenter.collectPost();
                break;
            case R.id.send:
                presenter.addComment(commentContent.getText().toString(), 0);
                break;
            case R.id.share_layout:
                showShare();
                break;
        }
    }

    public void commentSuccess() {
        commentContent.setText("");
        headBind.commentCount.setText(Integer.valueOf(headBind.commentCount.getText().toString())+1);
        commentCount.setText(Integer.valueOf(commentCount.getText().toString())+1);
    }

    public void setCollection(CollectionTo data) {
        showMessage(data.getIs_collected() == 1 ? "收藏帖子成功" : "取消收藏成功");
        collect.setBackgroundResource(data.getIs_collected() == 1 ? R.drawable.service_star_icon : R.drawable.service_star_gray_icon);

    }

    public void praiseSuccess() {
        praise.setSelected(!praise.isSelected());
        headBind.praiseIcon.setBackgroundResource(praise.isSelected() ? R.drawable.praise_already_icon : R.drawable.praise_icon);
        praise.setBackgroundResource(praise.isSelected() ? R.drawable.praise_already_icon : R.drawable.praise_icon);
        headBind.likeCount.setText(Integer.valueOf(headBind.likeCount.getText().toString())+(praise.isSelected()?1:-1)+"");
    }

    public void showShare() {

        dialog = new CommonDialog(this, getScreenWidth(), (int) (getScreenHeight() * 160.0 / 1344), R.layout.dialog_share_moment, R.style.DialogDown);
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
