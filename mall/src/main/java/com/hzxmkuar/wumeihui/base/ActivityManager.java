package com.hzxmkuar.wumeihui.base;

import com.hzxmkuar.wumeihui.personal.MainActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.SearchDemandActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.SearchDetailActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.SelectDemandActivity;
import com.hzxmkuar.wumeihui.personal.integral.IntegrationDetailActivity;
import com.hzxmkuar.wumeihui.personal.myself.MyOrderActivity;
import com.hzxmkuar.wumeihui.personal.order.PersonOrderActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/27.
 */

public class ActivityManager {
    public static List<BaseActivity>activityList=new ArrayList<>();
    public static List<BaseActivity>merchantEnterList=new ArrayList<>();
    public static MainActivity mainActivity;
    public static PersonOrderActivity personOrderActivity;
    public static MyOrderActivity myOrderActivity;
    public static SelectDemandActivity demandActivity;
    public static SearchDemandActivity searchDemandActivity;
    public static SearchDetailActivity searchDetailActivity;
    public static IntegrationDetailActivity integrationDetailActivity;
}
