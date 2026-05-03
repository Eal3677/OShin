package de.robv.android.xposed;

public interface IXposedHookCmdInit extends IXposedMod {
    void initCmdApp(StartupParam startupParam) throws Throwable;

    final class StartupParam {
        public String modulePath;
        public String startClassName;
    }
}
