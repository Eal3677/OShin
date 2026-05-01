package de.robv.android.xposed.callbacks;

import de.robv.android.xposed.XposedBridge;

public abstract class XC_LoadPackage extends XCallback {
    public XC_LoadPackage() {
        super();
    }

    public XC_LoadPackage(int priority) {
        super(priority);
    }

    public abstract void handleLoadPackage(LoadPackageParam lpparam) throws Throwable;

    public static final class LoadPackageParam extends XCallback.Param {
        public String packageName;
        public String processName;
        public ClassLoader classLoader;
        public Object appInfo;
        public boolean isFirstApplication;

        public LoadPackageParam(XposedBridge.CopyOnWriteSortedSet<XC_LoadPackage> callbacks) {
        }
    }
}
