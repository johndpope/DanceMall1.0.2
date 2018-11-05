package com.hzxmkuar.wumeihui.message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.message.adapter.SystemMessageAdapter;
import com.hzxmkuar.wumeihui.message.presenter.SystemMessagePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xzz on 2018/8/15.
 */

public class SystemMessageFragment extends BaseFragment {

    @BindView(R.id.recycle_view)
    LRecyclerView recycleView;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = View.inflate(appContext, R.layout.common_recycle_view, null);
        SystemMessagePresenter presenter = new SystemMessagePresenter(this);

        unbinder = ButterKnife.bind(this, mView);
        setRecycleView(new SystemMessageAdapter(getActivity()),recycleView,presenter);
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
