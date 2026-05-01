package de.robv.android.xposed.callbacks

import android.content.pm.ApplicationInfo

open class XC_LoadPackage {
    class LoadPackageParam {
        var packageName: String? = null
        var processName: String? = null
        var classLoader: ClassLoader? = null
        var appInfo: ApplicationInfo? = null
        var isFirstApplication: Boolean = false
    }
}
