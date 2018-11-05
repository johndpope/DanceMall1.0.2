package com.hzxmkuar.wumeihui.base.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.Toast;

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

}
