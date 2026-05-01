package de.robv.android.xposed

interface IXposedHookZygoteInit : IXposedMod {
    @Throws(Throwable::class)
    fun initZygote(startupParam: StartupParam?)

    class StartupParam {
        var modulePath: String? = null
        var startsSystemServer: Boolean = false
    }
}
