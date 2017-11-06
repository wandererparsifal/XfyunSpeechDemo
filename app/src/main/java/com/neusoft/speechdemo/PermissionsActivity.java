package com.neusoft.speechdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by yangming on 17-11-2.
 */
public class PermissionsActivity extends Activity implements EasyPermissions.PermissionCallbacks {

    private static final String TAG = PermissionsActivity.class.getSimpleName();

    /**
     * 所要申请的权限
     */
    private String[] mPermissions = {Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (EasyPermissions.hasPermissions(this, mPermissions)) { // 检查是否获取该权限
            Log.d(TAG, "已获取权限");
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            // 第二个参数是被拒绝后再次申请该权限的解释
            // 第三个参数是请求码
            // 第四个参数是要申请的权限
            EasyPermissions.requestPermissions(this, "必要的权限未被授权，请授权", 0, mPermissions);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "获取成功的权限" + perms);
        if (perms.size() == mPermissions.length) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "获取失败的权限" + perms);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 把申请权限的回调交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
