package com.hzxmkuar.wumeihui.personal.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseFragment;

/**
 * Created by xzz on 2018/8/15.
 */

public class ChatMessageFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = View.inflate(appContext, R.layout.common_recycle_view, null);
        return mView;
    }
}
