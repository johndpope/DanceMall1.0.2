package com.hzxmkuar.wumeihui.circle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.CommonDialog;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.Event;
import com.hzxmkuar.wumeihui.base.util.SpUtil;
import com.hzxmkuar.wumeihui.circle.presenter.PostPresenter;
import com.hzxmkuar.wumeihui.personal.MainActivity;
import com.hzxmkuar.wumeihui.personal.order.SelectPayActivity;
import com.jzxiang.pickerview.adapters.ArrayWheelAdapter;
import com.jzxiang.pickerview.wheel.OnWheelChangedListener;
import com.jzxiang.pickerview.wheel.WheelView;
import com.kyleduo.switchbutton.SwitchButton;
import com.ruffian.library.RTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.order.OrderSettleInfoTo;


/**
 * Created by Administrator on 2018/9/2.
 **/

public class PostActivity extends BaseActivity {
    @BindView(R.id.image_layout)
    GridLayout imageLayout;
    @BindView(R.id.switchButton)
    SwitchButton switchButton;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.topic_left)
    RTextView topicLeft;
    @BindView(R.id.topic_right)
    RTextView topicRight;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.day)
    TextView day;
    @BindView(R.id.price)
    TextView price;
    private int topicId = -1;
    private PostPresenter presenter;
    private String city;
    private String[] dayData;
    private WheelView wheelLeft;
    private int orderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        setTitleName(Constant.POST);
        setPostImageLayoutNight(imageLayout,120);
        EventBus.getDefault().register(this);
        setView();
        presenter = new PostPresenter(this);

    }

    private void setView() {
        switchButton.setChecked(false);

    }

    @SuppressLint("SetTextI18n")
    public void showSelectDateTimeDialog(final String[] left, final String[][] right, Integer[][] topicIdList) {

        final CommonDialog dialog = new CommonDialog(this, getScreenWidth(), (int) (getScreenWidth() * 0.2), R.layout.dialog_wheel_topic, R.style.DialogDown);

        TextView btnAdd = dialog.findViewById(R.id.btn_add);
        RelativeLayout mLayout = dialog.findViewById(R.id.layout);
        TextView btnCancel = dialog.findViewById(R.id.btn_cancel);

        final WheelView wheelLeft = dialog.findViewById(R.id.wheelLeft);
        final WheelView wheelRight = dialog.findViewById(R.id.wheelRight);

        mLayout.setOnClickListener(v -> dialog.dismiss());
        btnAdd.setOnClickListener(v -> {


            topicId = topicIdList[wheelLeft.getCurrentItem()][wheelRight.getCurrentItem()];
            if (topicId == 0) {
                showMessage("请选择一个话题");
                return;
            }
            topicLeft.setVisibility(View.VISIBLE);
            topicRight.setVisibility(View.VISIBLE);
            topicLeft.setText(left[wheelLeft.getCurrentItem()]);
            topicRight.setText(right[wheelLeft.getCurrentItem()][wheelRight.getCurrentItem()]);
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        wheelLeft.setVisibleItems(5);
        wheelLeft.setCyclic(false);
        wheelLeft.setViewAdapter(new ArrayWheelAdapter<>(appContext, left));
        String mLeftPosition = "0";
        setWheelCurrent(mLeftPosition, wheelLeft, left);
        wheelRight.setVisibleItems(5);
        wheelRight.setCyclic(false);
        wheelRight.setViewAdapter(new ArrayWheelAdapter<>(appContext, right[0]));
        final String mRightPosition = "0";

        setWheelCurrent(mRightPosition, wheelRight, right[0]);

        LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1);
        paramsLeft.gravity = Gravity.START;
        LinearLayout.LayoutParams paramsRight = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1);
        paramsRight.gravity = Gravity.END;
        wheelLeft.addChangingListener((wheel, oldValue, newValue) -> {
            if (right[newValue] == null || right[newValue].length == 0)
                return;
            wheelRight.setViewAdapter(new ArrayWheelAdapter<>(appContext, right[newValue]));
            if (!TextUtils.isEmpty(mRightPosition)) {
                int position = Integer.parseInt(mRightPosition);
                if (position > right[newValue].length) {
                    wheelRight.setCurrentItem(0);
                }

            }


        });


        if (dialog.isShowing()) {
            dialog.dismiss();
        }


        dialog.show();

    }

    private void setWheelCurrent(String rightPosition, WheelView wheelView, String[] right) {
        if (TextUtils.isEmpty(rightPosition)) {
            wheelView.setCurrentItem(0);
        } else {
            int position = Integer.parseInt(rightPosition);
            if (position <= right.length) {
                wheelView.setCurrentItem(position);
            } else {
                wheelView.setCurrentItem(0);
            }
        }
    }

    @Override
    protected void submitDataSuccess(Object data) {

        Intent intent = new Intent(appContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Index", 1);
        startActivity(intent);
        goToAnimation(1);

    }

    @OnClick({R.id.post, R.id.topic_layout, R.id.select_position_layout, R.id.top_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.post:
                if (TextUtils.isEmpty(content.getText().toString())) {
                    showMessage("请填写帖子内容");
                    return;
                }
                if (topicId == -1) {
                    showMessage("请选择话题");
                    return;
                }
                if (switchButton.isChecked()) {
                    presenter.addTopOrder(wheelLeft==null?1:wheelLeft.getCurrentItem() + 1);
                } else {
                    presenter.addPost(content.getText().toString(), topicId, imagePaths, address.getText().toString(), TextUtils.isEmpty(city) ? SpUtil.getString("LocateCity") : city,0,0);
                }

                break;
            case R.id.topic_layout:
                presenter.getTopic();
                break;
            case R.id.select_position_layout:
                Intent intent = new Intent(appContext, PoiSearchActivity.class);
                startActivityForResult(intent, 40);
                goToAnimation(1);
                break;
            case R.id.top_layout:
                selectDayDialog();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 40) {
            address.setText(data.getStringExtra("PoiInfo"));
            city = data.getStringExtra("City");
        }
    }

    private void selectDayDialog() {
        final CommonDialog dialog = new CommonDialog(this, getScreenWidth(), (int) (getScreenWidth() * 0.2), R.layout.dialog_select_day, R.style.DialogDown);
        dialog.show();
        dialog.findViewById(R.id.btn_add).setOnClickListener(v -> dialog.dismiss());
        dialog.findViewById(R.id.layout);
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());

        wheelLeft = dialog.findViewById(R.id.wheelLeft);
        dayData = new String[31];
        for (int i = 1; i <= 31; i++)
            dayData[i - 1] = (i + "天");
        wheelLeft.setViewAdapter(new ArrayWheelAdapter<>(appContext, dayData));
        wheelLeft.addChangingListener((wheel, oldValue, newValue) -> {
            day.setText(dayData[wheelLeft.getCurrentItem()] );
            price.setText("￥ " + (wheelLeft.getCurrentItem() + 1) * 10 + ".00");
        });
    }


    public void topOrderSuccess(OrderSettleInfoTo data) {
        Intent intent = new Intent(appContext, SelectPayActivity.class);
        intent.putExtra("Money", data.getTotal_amount() + "");
        intent.putExtra("OrderId", data.getOrder_id());
        intent.putExtra("Type", 5);
        orderId=data.getOrder_id();
        startActivity(intent);
        goToAnimation(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void getPayResult(Event<Integer> event){
        if ("PayResultData".equals(event.getType())){
            if (event.getData()==10){
                presenter.addPost(content.getText().toString(), topicId, imagePaths, address.getText().toString(), TextUtils.isEmpty(city) ? SpUtil.getString("LocateCity") : city,wheelLeft==null?1:wheelLeft.getCurrentItem()+1,orderId);
            }
        }
    }

    public void praiseSuccess() {

    }
}
