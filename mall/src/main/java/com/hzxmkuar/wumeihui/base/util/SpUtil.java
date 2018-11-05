package com.hzxmkuar.wumeihui.base.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.hzxmkuar.wumeihui.MainApp;


/**
 * Created by xzz on 2016/1/11.
 **/
public class SpUtil {
    private Context context = MainApp.appContext;

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("park", Context.MODE_PRIVATE);
    }

    public static int getInt(String key) {
        SharedPreferences sp = getSharedPreferences(MainApp.appContext);
        return sp.getInt(key, 0);
    }

    public static int getInt(String key, int defValue) {
        SharedPreferences sp = getSharedPreferences(MainApp.appContext);
        return sp.getInt(key, defValue);
    }

    /**
     * 从首选项中获取String值,默认值为null
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        SharedPreferences sp = getSharedPreferences(MainApp.appContext);
        return sp.getString(key, null);
    }

    /**
     * 从首选项中获取boolean值,默认值为false
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(String key) {
        SharedPreferences sp = getSharedPreferences(MainApp.appContext);
        return sp.getBoolean(key, false);
    }



    /**
     * 向首选项中存储数据(仅限于String,int,boolean三种数据类型)
     */
    public static void put(String key, Object value) {
        SharedPreferences sp = getSharedPreferences(MainApp.appContext);
        SharedPreferences.Editor editor = sp.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }
        editor.apply();
    }


}