package br.com.activeultra.core.util;

import java.lang.reflect.Field;

public class ObjectUtil {

    public static <T> void copiarAtributos(T source, T target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source and target objects must not be null.");
        }

        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        Field[] sourceFields = sourceClass.getDeclaredFields();

        for (Field sourceField : sourceFields) {
            try {
                sourceField.setAccessible(true); // Make private fields accessible
                Object sourceValue = sourceField.get(source); // Get value from source

                Field targetField;
                try {
                    targetField = targetClass.getDeclaredField(sourceField.getName());
                    targetField.setAccessible(true); // Make private fields accessible
                } catch (NoSuchFieldException e) {
                    // If the field doesn't exist in the target, skip it
                    continue;
                }

                if (isPojo(sourceValue)) {
                    // If the value is a POJO, copy recursively
                    Object nestedTarget = targetField.get(target);
                    if (nestedTarget == null) {
                        nestedTarget = sourceField.getType().getDeclaredConstructor().newInstance();
                        targetField.set(target, nestedTarget);
                    }
                    copiarAtributos(sourceValue, nestedTarget);
                } else {
                    // Otherwise, directly set the value
                    targetField.set(target, sourceValue);
                }
            } catch (Exception _) {
                // Handle potential exceptions gracefully (log, throw, or ignore)
            }
        }
    }

    private static boolean isPojo(Object obj) {
        if (obj == null) {
            return false;
        }
        Class<?> clazz = obj.getClass();
        return !(clazz.isPrimitive() || clazz.equals(String.class) || Number.class.isAssignableFrom(clazz)
                || clazz.equals(Boolean.class) || clazz.equals(Character.class));
    }
}
