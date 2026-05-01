package de.robv.android.xposed.callbacks

open class XC_InitPackageResources {
    class InitPackageResourcesParam {
        var packageName: String? = null
        var res: Any? = null
    }
}
