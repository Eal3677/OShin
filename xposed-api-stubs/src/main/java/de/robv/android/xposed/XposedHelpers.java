package de.robv.android.xposed;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class XposedHelpers {
    private XposedHelpers() {
    }

    public static Object callMethod(Object obj, String methodName, Object... args) {
        try {
            Method method = findMethodBestMatch(obj.getClass(), methodName, args);
            method.setAccessible(true);
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object callMethod(Object obj, String methodName, Class<?>[] parameterTypes, Object... args) {
        try {
            Method method = obj.getClass().getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object callStaticMethod(Class<?> clazz, String methodName, Object... args) {
        try {
            Method method = findMethodBestMatch(clazz, methodName, args);
            method.setAccessible(true);
            return method.invoke(null, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int getStaticIntField(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.getInt(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getObjectField(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setObjectField(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?> findClass(String className, ClassLoader classLoader) {
        try {
            return Class.forName(className, false, classLoader);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Method findMethodBestMatch(Class<?> clazz, String methodName, Object... args) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (!method.getName().equals(methodName)) {
                continue;
            }
            if (method.getParameterTypes().length == args.length) {
                return method;
            }
        }
        throw new RuntimeException(new NoSuchMethodException(methodName));
    }

    public static Constructor<?> findConstructorBestMatch(Class<?> clazz, Object... args) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length == args.length) {
                return constructor;
            }
        }
        throw new RuntimeException(new NoSuchMethodException(clazz.getName()));
    }

    public static XC_MethodHook.Unhook findAndHookMethod(Class<?> clazz, String methodName, Object... parameterTypesAndCallback) {
        XC_MethodHook callback = (XC_MethodHook) parameterTypesAndCallback[parameterTypesAndCallback.length - 1];
        Method method = null;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method candidate : methods) {
            if (candidate.getName().equals(methodName)) {
                method = candidate;
                break;
            }
        }
        if (method == null) {
            throw new RuntimeException(new NoSuchMethodException(methodName));
        }
        return XposedBridge.hookMethod(method, callback);
    }

    public static XC_MethodHook.Unhook findAndHookMethod(String className, ClassLoader classLoader, String methodName, Object... parameterTypesAndCallback) {
        Class<?> targetClass = findClass(className, classLoader);
        return findAndHookMethod(targetClass, methodName, parameterTypesAndCallback);
    }
}
