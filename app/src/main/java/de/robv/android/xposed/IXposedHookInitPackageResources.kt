package de.robv.android.xposed

import de.robv.android.xposed.callbacks.XC_InitPackageResources

interface IXposedHookInitPackageResources : IXposedMod {
    @Throws(Throwable::class)
    fun handleInitPackageResources(resparam: XC_InitPackageResources.InitPackageResourcesParam?)
}
