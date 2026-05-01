package de.robv.android.xposed;

public final class XposedBridge {
    private XposedBridge() {}

    public static final ClassLoader BOOTCLASSLOADER = ClassLoader.getSystemClassLoader();
}
