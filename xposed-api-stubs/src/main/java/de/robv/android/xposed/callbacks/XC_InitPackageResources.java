package de.robv.android.xposed.callbacks;

import de.robv.android.xposed.XposedBridge;

public abstract class XC_InitPackageResources extends XCallback {
    public XC_InitPackageResources() {
        super();
    }

    public XC_InitPackageResources(int priority) {
        super(priority);
    }

    public abstract void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable;

    public static final class InitPackageResourcesParam extends XCallback.Param {
        public String packageName;
        public Object res;

        public InitPackageResourcesParam(XposedBridge.CopyOnWriteSortedSet<XC_InitPackageResources> callbacks) {
        }
    }
}
