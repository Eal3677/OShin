package com.suqi8.oshin.utils

import android.content.Context
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.hjq.permissions.permission.base.IPermission

fun requestPermissions(context: Context, permissions: IPermission, onGranted: () -> Unit = {}) {
    XXPermissions.with(context)
        .permission(permissions)
        .request(object : OnPermissionCallback {
            override fun onGranted(permissions: MutableList<IPermission>, allGranted: Boolean) {
                if (allGranted) {
                    onGranted()
                } else {
                    toast(
                        context,
                        "Some permissions were granted, but others were denied.\nSome features may not work properly."
                    )
                }
            }

            override fun onDenied(permissions: MutableList<IPermission>, doNotAskAgain: Boolean) {
                if (doNotAskAgain) {
                    toast(context, "Permission permanently denied. Please grant read/write file permissions manually.")
                    // 如果权限被永久拒绝，重定向到设置
                    XXPermissions.startPermissionActivity(context, permissions)
                } else {
                    toast(context, "Failed to obtain read/write file permissions.")
                }
            }
        })

}
