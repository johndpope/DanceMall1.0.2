package com.hzxmkuar.wumeihui.message;

import android.Manifest;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;


import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.PermissionsUtils;
import com.hzxmkuar.wumeihui.base.util.StatueBarUtil;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.function.Consumer;

import hzxmkuar.com.applibrary.impl.PermissionListener;
import rx.functions.Action1;

/**
 * Created by Administrator on 2018/9/28.
 **/

public class ChatActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatueBarUtil.setStatueBarColor(getWindow(), "#3FB9FF");
        setContentView(R.layout.activity_chat);
        new RxPermissions(this).requestEach(
                Manifest.permission.RECORD_AUDIO
        )
                .subscribe(permission -> {
                    if (permission.granted) {
                        // 用户已经同意该权限
                        init();

                    } else if (permission.shouldShowRequestPermissionRationale) {
                        init();
                    } else {
                        showMessage("请手动打开录音权限");

                    }
                });


    }

    public void init() {
        getPermission(Manifest.permission.RECORD_AUDIO, new PermissionListener() {
            @Override
            public void accept(String permission) {

                EMClient.getInstance().login(userInfoTo.getMobile(), "123456", new EMCallBack() {
                    @Override
                    public void onSuccess() {

                        runOnUiThread(() -> {


                            EaseChatFragment fragment = new EaseChatFragment();
                            Bundle args = new Bundle();

                            args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                            args.putString(EaseConstant.EXTRA_USER_ID, getIntent().getStringExtra("UserId"));
                            args.putString("Name", getIntent().getStringExtra("Name"));
                            fragment.setArguments(args);
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss();

                        });
                    }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }

            @Override
            public void refuse(String permission) {
                finish();
            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        new RxPermissions(this).requestEach(
                Manifest.permission.RECORD_AUDIO
        )
                .subscribe(permission -> {
                    if (permission.granted) {
                        // 用户已经同意该权限
                        init();

                    } else if (permission.shouldShowRequestPermissionRationale) {
                        init();
                    } else {
                        showMessage("请手动打开录音权限");

                    }
                });
    }


}
