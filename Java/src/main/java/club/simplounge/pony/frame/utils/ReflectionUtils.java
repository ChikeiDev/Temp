package club.simplounge.pony.frame.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtils {

	private ReflectionUtils() {}

	public static boolean isFieldValueInstanceOf(Object object, String field, Class check) {
		boolean returnValue = false;
		try {
			Class<?> clazz = object.getClass();
			Field objectField = clazz.getDeclaredField(field);
			boolean accessible = objectField.isAccessible();
			if (!accessible) objectField.setAccessible(true);
			if (objectField.get(object).getClass().isInstance(check)) returnValue = true;
			if (!accessible) objectField.setAccessible(false);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	public static Object getPrivateField(Object object, String field) {
		Object toReturn = null;
		try {
			Class<?> clazz = object.getClass();
			Field objectField = clazz.getDeclaredField(field);
			objectField.setAccessible(true);
			toReturn = objectField.get(object);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	public static Object getPrivateField(Object object, String field, Class<?> clazz) {
		Object toReturn = null;
		try {
			Field objectField = clazz.getDeclaredField(field);
			objectField.setAccessible(true);
			toReturn = objectField.get(object);
			//if (!accessible) objectField.setAccessible(false);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	public static void setPrivateField(Object object, String field, Object newValue) {
		try {
			Class<?> clazz = object.getClass();
			Field objectField = clazz.getDeclaredField(field);
			boolean accessible = objectField.isAccessible();
			if (!accessible) objectField.setAccessible(true);
			objectField.set(object, newValue);
			if (!accessible) objectField.setAccessible(false);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void setPrivateField(Object object, String field, Object newValue, Class<?> clazz) {
		try {
			Field objectField = clazz.getDeclaredField(field);
			boolean accessible = objectField.isAccessible();
			if (!accessible) objectField.setAccessible(true);
			objectField.set(object, newValue);
			if (!accessible) objectField.setAccessible(false);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void invokePrivateMethod(Object object, String methodName) {
		try {
			Method method = object.getClass().getDeclaredMethod(methodName);
			boolean accessible = method.isAccessible();
			if (!accessible) method.setAccessible(true);
			method.invoke(object);
			if (!accessible) method.setAccessible(false);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
