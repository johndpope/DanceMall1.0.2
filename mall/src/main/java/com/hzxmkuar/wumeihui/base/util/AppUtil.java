package com.hzxmkuar.wumeihui.base.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by Administrator on 2018/11/5.
 */

public class AppUtil {
    public static boolean readSIMCard(Context context) {

        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(TELEPHONY_SERVICE);// 取得相关系统服务
        String imsi = manager.getSubscriberId(); // 取出IMSI


        if (imsi == null || imsi.length() <= 0) {
            Toast.makeText(context,"请插入手机卡",Toast.LENGTH_LONG).show();
          return false;

        } else {
           return true;

        }
    }
    public static boolean  isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;

    }
}
