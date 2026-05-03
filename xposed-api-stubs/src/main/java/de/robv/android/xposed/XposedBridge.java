package de.robv.android.xposed;

import java.lang.reflect.Member;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public final class XposedBridge {
    public static final ClassLoader BOOTCLASSLOADER = ClassLoader.getSystemClassLoader();

    private XposedBridge() {
    }

    public static final class CopyOnWriteSortedSet<E> {
        private final Set<E> elements = new LinkedHashSet<>();

        public synchronized boolean add(E e) {
            return elements.add(e);
        }

        public synchronized boolean remove(E e) {
            return elements.remove(e);
        }

        public synchronized Object[] getSnapshot() {
            return elements.toArray();
        }

        public synchronized Set<E> asSet() {
            return Collections.unmodifiableSet(new LinkedHashSet<>(elements));
        }
    }

    public static void log(String text) {
    }

    public static void log(Throwable t) {
    }

    public static Object invokeOriginalMethod(Member method, Object thisObject, Object[] args) throws Throwable {
        return null;
    }

    public static XC_MethodHook.Unhook hookMethod(Member hookMethod, XC_MethodHook callback) {
        return callback.new Unhook(hookMethod);
    }

    public static Set<XC_MethodHook.Unhook> hookAllMethods(Class<?> hookClass, String methodName, XC_MethodHook callback) {
        return Collections.emptySet();
    }

    public static Set<XC_MethodHook.Unhook> hookAllConstructors(Class<?> hookClass, XC_MethodHook callback) {
        return Collections.emptySet();
    }

    public static void unhookMethod(Member hookMethod, XC_MethodHook callback) {
    }

}
