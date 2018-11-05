package com.hzxmkuar.wumeihui.circle.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.base.CommonDialog;
import com.hzxmkuar.wumeihui.circle.PostActivity;
import com.hzxmkuar.wumeihui.circle.PostDetailActivity;
import com.hzxmkuar.wumeihui.circle.adapter.CircleAdapter;
import com.hzxmkuar.wumeihui.circle.presenter.CirclePresenter;
import com.hzxmkuar.wumeihui.databinding.CircleHeadViewBinding;
import com.hzxmkuar.wumeihui.message.ChatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hzxmkuar.com.applibrary.domain.circle.CircleBannerTo;
import hzxmkuar.com.applibrary.domain.circle.CircleTo;
import hzxmkuar.com.applibrary.domain.circle.TopicTo;
import hzxmkuar.com.applibrary.domain.merchant.ServiceListTo;

/**
 * Created by xzz on 2018/8/15.
 **/

public class CircleFragment extends BaseFragment {

    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    Unbinder unbinder;
    private CirclePresenter presenter;
    private GridLayout sortLeftLayout;
    private GridLayout sortRightLayout;
    private View lastView;
    private CircleHeadViewBinding bind;
    private int selectType;
    private List<TopicTo.ListsBean> serviceList;
    private View bootView;
    private int scrollY;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bootView = View.inflate(appContext, R.layout.fragment_communication, null);
        unbinder = ButterKnife.bind(this, bootView);

        presenter = new CirclePresenter(this);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollY=scrollY+dy;
            }
        });
        return bootView;
    }

    private void setHeadView(List<CircleBannerTo.ListsBean> menuList) {
        headView = View.inflate(appContext, R.layout.circle_head_view, null);
        bind = DataBindingUtil.bind(headView);

        for (int i = 0; i < menuList.size(); i++) {
            View mView = View.inflate(appContext, R.layout.circle_head_item, null);
            displayImage(mView.findViewById(R.id.menu_image), menuList.get(i).getTopic_img());
            mView.setTag(i);
            ((TextView) mView.findViewById(R.id.menu_name)).setText(menuList.get(i).getTopic_name());
            ((GridLayout) headView.findViewById(R.id.menu_layout)).addView(mView);
            mView.setOnClickListener(view -> {
                presenter.getPostList(menuList.get((Integer) mView.getTag()).getId());
                bind.sortTitle.setText(menuList.get((Integer) mView.getTag()).getTopic_name());
            });
        }
        CircleAdapter adapter = new CircleAdapter(getActivity());
        presenter.getPostList(0);
        setRecycleView(adapter, recycleView, presenter,true);

        bind.selectTopicLayout.setOnClickListener(view -> topicPopWindow(bind.popLayout));
        bind.selectSortLayout.setOnClickListener(view -> sortPopWindow(bind.popLayout));
        bind.selectLocalLayout.setOnClickListener(view -> {

            bind.localView.setSelected(!bind.localView.isSelected());
            bind.localView.setBackgroundResource(bind.localView.isSelected() ? R.drawable.position_select : R.drawable.position_un_select);
            presenter.param.setIs_local(bind.localView.isSelected()?1:0);
            presenter.getPostList(presenter.param.getTopic_id());
        });



    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.camera, R.id.message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.camera:
                Intent intent = new Intent(appContext, PostActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.message:
                intent = new Intent(appContext, ChatActivity.class);
                intent.putExtra("UserId", "15571586467");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void recycleItemClick(View view, int position, Object data) {
        super.recycleItemClick(view, position, data);
        Intent intent = new Intent(appContext, PostDetailActivity.class);

        intent.putExtra("PostId", ((CircleTo.ListsBean) data).getId());
        startActivity(intent);
        goToAnimation(1);
    }

    @Override
    public void loadDataSuccess(Object data) {
        List<CircleBannerTo.ListsBean> menuList = (List<CircleBannerTo.ListsBean>) data;
        setHeadView(menuList);
    }

    public void setSort(List<TopicTo.ListsBean> serviceList) {

this.serviceList=serviceList;
    }

    private void sortPopWindow(View anchor){
        View contentView=View.inflate(appContext,R.layout.circle_sort_layout,null);

        PopupWindow window=new PopupWindow(contentView, getScreenWidth(), getScreenHeight()+scrollY-bind.popLayout.getBottom()-getScreenWidth()*138/750, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        window.showAsDropDown(anchor, 0, 0);
        ((TextView)contentView.findViewById(R.id.sort_text1)).setTextColor(selectType == 1 ? Color.parseColor("#3bafd9") : Color.parseColor("#000000"));
        contentView.findViewById(R.id.sort_select_icon_1).setVisibility(selectType == 1 ? View.VISIBLE : View.GONE);
        ((TextView)contentView.findViewById(R.id.sort_text2)).setTextColor(selectType == 2 ? Color.parseColor("#3bafd9") : Color.parseColor("#000000"));
        contentView.findViewById(R.id.sort_select_icon_2).setVisibility(selectType == 2 ? View.VISIBLE : View.GONE);
        ((TextView)contentView.findViewById(R.id.sort_text3)).setTextColor(selectType == 3 ? Color.parseColor("#3bafd9") : Color.parseColor("#000000"));
        contentView.findViewById(R.id.sort_select_icon_3).setVisibility(selectType == 3 ? View.VISIBLE : View.GONE);
        ((TextView)contentView.findViewById(R.id.sort_text4)).setTextColor(selectType == 3 ? Color.parseColor("#3bafd9") : Color.parseColor("#000000"));
        contentView.findViewById(R.id.sort_select_icon_4).setVisibility(selectType == 3 ? View.VISIBLE : View.GONE);
        contentView.findViewById(R.id.sort_text1).setOnClickListener(view -> {
            selectType = 1;
            presenter.param.setSort_by(1);
            presenter.getPostList(presenter.param.getTopic_id());
            bind.sortLayout.setVisibility(View.GONE);
            window.dismiss();
        });
        contentView.findViewById(R.id.sort_text2).setOnClickListener(view -> {
            selectType = 2;
            presenter.param.setSort_by(2);
            presenter.getPostList(presenter.param.getTopic_id());
            bind.sortLayout.setVisibility(View.GONE);
            window.dismiss();
        });
        contentView.findViewById(R.id.sort_text3).setOnClickListener(view -> {
            selectType = 3;
            presenter.param.setSort_by(3);
            presenter.getPostList(presenter.param.getTopic_id());
            bind.sortLayout.setVisibility(View.GONE);
            window.dismiss();
        });
        contentView.findViewById(R.id.sort_text4).setOnClickListener(view -> {
            selectType = 4;
            presenter.param.setSort_by(4);
            presenter.getPostList(presenter.param.getTopic_id());
            bind.sortLayout.setVisibility(View.GONE);
            window.dismiss();
        });
    }

    private void topicPopWindow(View anchor) {
        View contentView = View.inflate(appContext, R.layout.circle_topic_layout, null);


        PopupWindow window = new PopupWindow(contentView, getScreenWidth(), getScreenHeight()+scrollY-bind.popLayout.getBottom()-getScreenWidth()*138/750, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        window.showAsDropDown(anchor, 0,0);
          sortLeftLayout=contentView.findViewById(R.id.sort_left_layout);
        sortRightLayout=contentView.findViewById(R.id.sort_right_layout);

        for (TopicTo.ListsBean mainServiceTo : serviceList) {

            View mView = View.inflate(appContext, R.layout.communication_sort_left_item, null);
            ((TextView) mView.findViewById(R.id.type_name)).setText(mainServiceTo.getTopic_name());
            if (sortLeftLayout.getChildCount() == 0) {
                mView.setBackgroundColor(Color.parseColor("#f7f7f7"));
                lastView = mView;
            }
            sortLeftLayout.addView(mView);
            sortRightLayout.removeAllViews();
            mView.setOnClickListener(view -> {
                lastView.setBackgroundColor(Color.parseColor("#ffffff"));
                mView.setBackgroundColor(Color.parseColor("#f7f7f7"));
                lastView = mView;
                sortRightLayout.removeAllViews();
                for (TopicTo.ListsBean value : mainServiceTo.getList2()) {
                    View childView = View.inflate(appContext, R.layout.communication__sort_right_item, null);
                    ((TextView) childView.findViewById(R.id.type_name)).setText(value.getTopic_name());
                    sortRightLayout.addView(childView);
                    childView.setOnClickListener(view2 -> {
                        window.dismiss();
                        bind.topicLayout.setVisibility(View.GONE);
                        presenter.param.setTopic_id(value.getId());
                        presenter.getPostList(value.getId());
                        bind.sortTitle.setText(value.getTopic_name());
                    });
                }
            });

        }
        for (TopicTo.ListsBean value : serviceList.get(0).getList2()) {
            View childView = View.inflate(appContext, R.layout.communication__sort_right_item, null);
            ((TextView) childView.findViewById(R.id.type_name)).setText(value.getTopic_name());
            sortRightLayout.addView(childView);
            childView.setOnClickListener(view -> {
                window.dismiss();
                bind.topicLayout.setVisibility(View.GONE);
                presenter.param.setTopic_id(value.getId());
                presenter.getPostList(value.getId());
                bind.sortTitle.setText(value.getTopic_name());
            });
        }
    }
}
