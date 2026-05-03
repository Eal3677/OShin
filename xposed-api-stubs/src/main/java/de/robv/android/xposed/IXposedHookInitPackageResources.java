package de.robv.android.xposed;

import de.robv.android.xposed.callbacks.XC_InitPackageResources;

public interface IXposedHookInitPackageResources extends IXposedMod {
    void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable;
}
