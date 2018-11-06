package com.hzxmkuar.wumeihui.base;

/**
 * Created by Administrator on 2018/11/5.
 */


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Dragon on 2017/11/8.
 */
public class PermissionsUtils {
    static final int VOICE_REQUEST_CODE = 66;
    private String[] permissions;
    private OnPermissionsCallback onPermissionsCallback;

    /**
     * 单例
     *
     * @param permissions           请求权限
     * @param onPermissionsCallback 回调
     * @return
     */
    public static PermissionsUtils permission(String[] permissions, OnPermissionsCallback onPermissionsCallback) {
        return new PermissionsUtils(permissions, onPermissionsCallback);
    }

    /**
     * 初始化
     *
     * @param onPermissionsCallback 回调
     */
    private PermissionsUtils(OnPermissionsCallback onPermissionsCallback) {
        this(null, onPermissionsCallback);
    }

    /**
     * 初始化
     *
     * @param permissions           请求权限
     * @param onPermissionsCallback 回调
     */
    private PermissionsUtils(String[] permissions, OnPermissionsCallback onPermissionsCallback) {
        this.onPermissionsCallback = onPermissionsCallback;
        this.permissions = permissions;
    }

    /**
     * 请求权限
     *
     * @return
     */
    public boolean requestPermissions(Activity activity) {
        return requestPermissions(activity, permissions);
    }

    /**
     * 请求权限
     *
     * @param activity    上下文
     * @param permissions 请求权限
     */
    private boolean requestPermissions(Activity activity, String[] permissions) {
        this.permissions = permissions;
        //判断是否开启摄像头权限
        if ((ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
            return true;
            //判断是否开启语音权限
        } else {
            //请求获取摄像头权限
//            Toast.makeText(mContext,&quot;无权限&quot;,Toast.LENGTH_SHORT).show();
            if (permissions != null)
                ActivityCompat.requestPermissions(activity,
                        permissions, VOICE_REQUEST_CODE);
            return false;
        }
    }

    /**
     * 请求权限结果，对应onRequestPermissionsResult()方法。
     *
     * @param requestCode  请求返回码
     * @param permissions  请求权限
     * @param grantResults 请求状态
     */
    public void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        boolean isAllow = true;
        String[] noAllowPermissions = new String[]{};
        int size = 0;
        if (requestCode == VOICE_REQUEST_CODE) {
            for (int i = 0; i < grantResults.length; i ++){
                if (!(grantResults[i] == PackageManager.PERMISSION_DENIED)) {
                    /**
                     * 给权限
                     */
//                    onPermissionsCallback.onAllow();
                } else {
                    if (isAllow) {
                        isAllow = false;
                    }
                    /**
                     * 没给权限
                     */
                    //用户不同意，向用户展示该权限作用
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        /**
                         * 禁止询问
                         */
//                        onPermissionsCallback.onProhibited();
                        noAllowPermissions[size] =permissions[i];
                    } else {
                        /**
                         * 询问
                         */
                        onPermissionsCallback.onASK();
                    }
                }
            }
            /**
             * 给权限
             */
            if (isAllow) {
                onPermissionsCallback.onAllow();
            }
            /**
             * 有权限被禁止了
             */
            if (noAllowPermissions.length > 0) {
                onPermissionsCallback.onProhibited(noAllowPermissions);
            }
        }
    }

    public interface OnPermissionsCallback {
        void onAllow();

        void onProhibited(String[] noAllowPermissions);

        void onASK();
    }
}