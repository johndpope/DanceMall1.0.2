package com.hzxmkuar.wumeihui.base;




import rx.Observer;

/**
 * Created by xzz on 2018/6/12.
 */

public abstract class MyObserver<T> implements Observer<T> {


    private BaseActivity activity;
    private BaseFragment fragment;
    private BasePresenter presenter;


    public MyObserver(BasePresenter presenter) {
        this.presenter = presenter;
    }

    public MyObserver(BaseActivity activity) {
        this.activity = activity;
    }

    public MyObserver(BaseFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onCompleted() {
        if (presenter != null)
            presenter.dismissLoadingDialog();
//        if (activity != null)
//            activity.dismissLoadingDialog();
//        if (fragment != null)
//            fragment.dismissLoadingDialog();
    }

    @Override
    public void onError(Throwable e) {
        if (presenter != null)
            presenter.dismissLoadingDialog();
//        if (activity != null) {
//            activity.dismissLoadingDialog();
//        }
//        if (fragment != null) {
//            fragment.dismissLoadingDialog();
//        }
//
//        if (e instanceof java.net.SocketTimeoutException) {
//            netError(1);
//        } else if (e instanceof java.net.UnknownHostException) {
//            netError(0);
//        }
        System.out.println(e+"eeeee");
    }

    public void netError(int type) {
//        if (activity != null)
//            activity.netError(type);
//        if (fragment != null)
//            fragment.netError(type);
//        if (baseModel != null)
//            baseModel.netError(type);
    }
}
