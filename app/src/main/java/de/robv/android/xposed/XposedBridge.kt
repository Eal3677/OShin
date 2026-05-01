package de.robv.android.xposed

object XposedBridge {
    @JvmField
    val BOOTCLASSLOADER: ClassLoader = ClassLoader.getSystemClassLoader()

    @JvmStatic
    fun log(message: String) {
        // No-op stub for non-Xposed build environments.
    }

    @JvmStatic
    fun log(throwable: Throwable) {
        // No-op stub for non-Xposed build environments.
    }
}
