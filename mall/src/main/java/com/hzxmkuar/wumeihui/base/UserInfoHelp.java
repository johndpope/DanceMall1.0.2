package com.hzxmkuar.wumeihui.base;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hzxmkuar.wumeihui.base.util.SpUtil;

import hzxmkuar.com.applibrary.domain.login.UserInfoTo;

/**
 * Created by Administrator on 2018/9/11.
 */

public class UserInfoHelp {

    public boolean getUserLogin(){
     return     SpUtil.getBoolean("UserLogin");
    }

    public void saveUserLogin(boolean login){
        SpUtil.put("UserLogin",login);
    }

    public void saveUserInfo(UserInfoTo userInfo){

        SpUtil.put("MainUserInfo", JSON.toJSONString(userInfo));
    }

    public UserInfoTo getUserInfo() {
        if (!TextUtils.isEmpty(SpUtil.getString("MainUserInfo")))
            return new Gson().fromJson(SpUtil.getString("MainUserInfo"),UserInfoTo.class);
        return null;
    }
}
