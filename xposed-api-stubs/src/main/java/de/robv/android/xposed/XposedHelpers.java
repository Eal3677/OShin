package de.robv.android.xposed;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class XposedHelpers {

    private XposedHelpers() {}

    public static Object callStaticMethod(Class<?> clazz, String methodName, Object... args) throws Throwable {
        Method method = findMethodBestMatch(clazz, methodName, toTypes(args));
        method.setAccessible(true);
        return method.invoke(null, args);
    }

    public static int getStaticIntField(Class<?> clazz, String fieldName) throws Throwable {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.getInt(null);
    }

    public static Object callMethod(Object instance, String methodName, Class<?>[] parameterTypes, Object... args) throws Throwable {
        Method method = instance.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(instance, args);
    }

    private static Method findMethodBestMatch(Class<?> clazz, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException {
        try {
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException ignored) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (!method.getName().equals(methodName)) continue;
                if (method.getParameterCount() != parameterTypes.length) continue;
                return method;
            }
            throw new NoSuchMethodException(clazz.getName() + "#" + methodName);
        }
    }

    private static Class<?>[] toTypes(Object[] args) {
        Class<?>[] types = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            types[i] = args[i] == null ? Object.class : args[i].getClass();
        }
        return types;
    }
}
