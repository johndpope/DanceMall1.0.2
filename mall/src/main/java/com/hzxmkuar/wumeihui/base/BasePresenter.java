package com.hzxmkuar.wumeihui.base;

import android.view.View;
import android.widget.Toast;

import com.hzxmkuar.wumeihui.MainApp;
import com.hzxmkuar.wumeihui.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.login.UserInfoTo;
import util.MD5;

/**
 * Created by Administrator on 2018/9/3.
 **/

public class BasePresenter<T> {
    protected BaseActivity activity;
    protected BaseFragment mFragment;
    protected UserInfoHelp userInfoHelp=new UserInfoHelp();
    protected UserInfoTo userInfoTo;
    private LoadingDialog loadingDialog;
    private boolean canDismiss;
    private String rawString="";
    protected int recyclePageIndex=1;
    private List<T> dataList;

    public  String getHashString(Class clazz,Object object) {
        rawString="";
        BaseParam param= (BaseParam) object;
        userInfoTo=userInfoHelp.getUserInfo();
        if (userInfoTo!=null){
            param.setUid(userInfoTo.getUid());
            param.setHashid(userInfoTo.getHashid());
        }

        Map<String, String> map= new HashMap<>();
        Field[] fs = clazz.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field field = fs[i];
            field.setAccessible(true);
            String valString;
            try {
                if( field.get(object)==null){
                    valString = "";
                }else{
                    valString = field.get(object).toString();
                }

                if (!"serialVersionUID".equals(field.getName())&&!"$change".equals(field.getName())&&!"time".equals(field.getName())&&!"apiId".equals(field.getName())&&!"terminal".equals(field.getName())&&!"hash".equals(field.getName()))
                map.put(field.getName(), valString);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        map.put("time",param.getTime()+"");
        map.put("apiId","7c13634bef78989a88dc90233f9d40f4");
        map.put("terminal","3");
        if (userInfoTo!=null){
            map.put("uid",param.getUid()+"");
            map.put("hashid",param.getHashid());
        }

        Collection<String> keyset= map.keySet();
        List list= new ArrayList<>(keyset);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            if(i==(list.size()-1)){

                rawString+=list.get(i)+map.get(list.get(i));

            }else{

                rawString+=list.get(i)+map.get(list.get(i));

            }
        }


            rawString = rawString+"f6c72c70321c6ddbccbaea829e1c517f";

        System.out.println(rawString+"===========");
        return MD5.getMD5(rawString);
    }


    public  String getHashStringNoUser(Class clazz,Object object) {
        rawString="";
        BaseParam param= (BaseParam) object;
        userInfoTo=userInfoHelp.getUserInfo();


        Map<String, String> map= new HashMap<>();
        Field[] fs = clazz.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field field = fs[i];
            field.setAccessible(true);
            String valString;
            try {
                if( field.get(object)==null){
                    valString = "";
                }else{
                    valString = field.get(object).toString();
                }

                if (!"serialVersionUID".equals(field.getName())&&!"$change".equals(field.getName())&&!"time".equals(field.getName())&&!"apiId".equals(field.getName())&&!"terminal".equals(field.getName())&&!"hash".equals(field.getName()))
                    map.put(field.getName(), valString);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        map.put("time",param.getTime()+"");
        map.put("apiId","7c13634bef78989a88dc90233f9d40f4");
        map.put("terminal","3");


        Collection<String> keyset= map.keySet();
        List list= new ArrayList<>(keyset);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            if(i==(list.size()-1)){

                rawString+=list.get(i)+map.get(list.get(i));

            }else{

                rawString+=list.get(i)+map.get(list.get(i));

            }
        }


        rawString = rawString+"f6c72c70321c6ddbccbaea829e1c517f";

        System.out.println(rawString+"===========");
        return MD5.getMD5(rawString);
    }
  protected void showMessage(String message){
      Toast.makeText(MainApp.appContext,message,Toast.LENGTH_LONG).show();
  }
    protected void submitDataSuccess(Object data){
        if (activity!=null)
        activity.submitDataSuccess(data);
        else
            mFragment.submitDataSuccess(data);
    }

    protected void getDataSuccess(T data){
        if (activity!=null)
        activity.loadDataSuccess(data);
        else
            mFragment.loadDataSuccess(data);
    }
    protected void getListDataSuccess(List<T> data){
        activity.loadDataSuccess(data);

    }

    protected void initContext(BaseActivity activity){
        this.activity=activity;
        userInfoTo = userInfoHelp.getUserInfo();
    }
    protected void initContext(BaseFragment fragment){
        this.mFragment=fragment;
        userInfoTo = userInfoHelp.getUserInfo();
    }


    protected void showLoadingDialog() {
        canDismiss = false;
        if (loadingDialog == null)
            loadingDialog = new LoadingDialog(activity == null ? mFragment.getActivity() :activity,"", R.drawable.loading_animation);
        if (!loadingDialog.isShowing()) {
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.show();
            loadingDialog.setOnDismissListener(dialog -> {
                if (!canDismiss && activity != null) {
                    activity.finish();
                }
            });
        }
    }

    public void dismissLoadingDialog() {
        canDismiss = true;
        if (loadingDialog != null)
            loadingDialog.dismiss();
    }

    public void recycleViewLoadMore() {
        recyclePageIndex++;
    }

    public void recycleViewRefresh() {
        recyclePageIndex = 1;
        if (mFragment != null)
            mFragment.mRecycleView.scrollToPosition(0);
        else
            activity.mRecycleView.scrollToPosition(0);
    }

    public void recycleItemClick(View view, int position) {
        if (activity!=null)
            activity.recycleItemClick(view,position,dataList.get(position));
        else
            mFragment.recycleItemClick(view,position,dataList.get(position));
    }

    public void setRecycleList(List dataList) {
        this.dataList=dataList;
        if (activity == null) {
            mFragment.mRecycleView.refreshComplete(10);
            mFragment.baseAdapter.setList(dataList);

            if (dataList!=null&&dataList.size()-10*recyclePageIndex < 0)
                mFragment.mRecycleView.setNoMore(true);
            mFragment.baseAdapter.notifyDataSetChanged();


        } else {
            activity.mRecycleView.refreshComplete(10);
            activity.baseAdapter.setList(dataList);

            if (dataList!=null&&dataList.size()-10*recyclePageIndex <0)
                activity.mRecycleView.setNoMore(true);
            activity.baseAdapter.notifyDataSetChanged();
        }
    }
    public void recyclerViewLoadMore(){
        recyclePageIndex++;
    }

    public void recyclerViewRefresh(){
        recyclePageIndex=1;
    }
}
