package org.example.util;

import org.example.entity.enums.ReadableEnum;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ReflectUtils {
    public static Map<String, String> getObjFields(Object object) {
        Map<String, String> map = new HashMap<>();
        if (object == null) {
            return map;
        }

        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // 允许访问 private

            String fieldName = field.getName();
            String key = camelToSnake(fieldName); // 驼峰转下划线

            try {
                Object value = field.get(object);
                if (value == null) continue;

                Class<?> type = field.getType();

                if (type == int.class || type == Integer.class) {
                    map.put(key, String.valueOf(value));
                } else if (type == String.class) {
                    map.put(key, (String) value);
                } else if (type == BigDecimal.class) {
                    BigDecimal bd = (BigDecimal) value;
                    map.put(key, bd.toPlainString());
                } else if (value instanceof ReadableEnum) {
                    ReadableEnum readableEnum = (ReadableEnum) value;
                    map.put(key, readableEnum.getValue());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    private static String camelToSnake(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        StringBuilder result = new StringBuilder();
        char[] chars = str.toCharArray();

        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                result.append('_').append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
