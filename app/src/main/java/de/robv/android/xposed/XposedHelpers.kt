package de.robv.android.xposed

import java.lang.reflect.Field
import java.lang.reflect.Method

object XposedHelpers {
    @JvmStatic
    fun callStaticMethod(clazz: Class<*>, methodName: String, vararg args: Any?): Any? {
        val (parameterTypes, values) = resolveParameterTypesAndArgs(args)
        val method = findDeclaredMethod(clazz, methodName, parameterTypes)
        method.isAccessible = true
        return method.invoke(null, *values)
    }

    @JvmStatic
    fun callMethod(instance: Any?, methodName: String, vararg args: Any?): Any? {
        requireNotNull(instance) { "instance must not be null" }
        val (parameterTypes, values) = resolveParameterTypesAndArgs(args)
        val method = findDeclaredMethod(instance.javaClass, methodName, parameterTypes)
        method.isAccessible = true
        return method.invoke(instance, *values)
    }

    @JvmStatic
    fun getStaticIntField(clazz: Class<*>, fieldName: String): Int {
        val field = findDeclaredField(clazz, fieldName)
        field.isAccessible = true
        return field.getInt(null)
    }

    private fun resolveParameterTypesAndArgs(args: Array<out Any?>): Pair<Array<Class<*>>, Array<out Any?>> {
        if (args.isNotEmpty() && args[0] is Array<*>) {
            @Suppress("UNCHECKED_CAST")
            val parameterTypes = args[0] as Array<Class<*>>
            val values = args.copyOfRange(1, args.size)
            return parameterTypes to values
        }

        val inferredParameterTypes: Array<Class<*>> = Array(args.size) { index ->
            val value = args[index]
            value?.javaClass ?: (Any::class.java as Class<*>)
        }
        return inferredParameterTypes to args
    }

    private fun findDeclaredMethod(clazz: Class<*>, methodName: String, parameterTypes: Array<Class<*>>): Method {
        var current: Class<*>? = clazz
        while (current != null) {
            try {
                return current.getDeclaredMethod(methodName, *parameterTypes)
            } catch (_: NoSuchMethodException) {
                current = current.superclass
            }
        }
        throw NoSuchMethodException("Method $methodName not found in ${clazz.name}")
    }

    private fun findDeclaredField(clazz: Class<*>, fieldName: String): Field {
        var current: Class<*>? = clazz
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName)
            } catch (_: NoSuchFieldException) {
                current = current.superclass
            }
        }
        throw NoSuchFieldException("Field $fieldName not found in ${clazz.name}")
    }
}
