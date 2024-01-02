package com.base.sc.framework.db.queryData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.core.io.ClassPathResource;

import com.base.sc.biz.vo.root.ObjectRootVO;
import com.base.sc.biz.vo.root.ObjectVO;

public class QueryUtil {

    public static Map<String, Object> convertToMap(ObjectVO obj,
            Class<?> stopWhenClass, boolean isNotInputNull) {
        try {
            if (Objects.isNull(obj)) {
                return Collections.emptyMap();
            }
            Map<String, Object> convertMap = new LinkedHashMap<>();

            List<Field> fields = getInheritedDeclaredFields(obj.getClass(), stopWhenClass);

            for (Field field : fields) {
                field.setAccessible(true);
                if (!isNotInputNull || field.get(obj) != null) {
                    convertMap.put(field.getName(), field.get(obj));
                }
            }
            return convertMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param <T>
     * @param clazz
     * @return
     */
    public static <T extends ObjectRootVO> T getObjectRootVOValue(Class<T> clazz) {
        T obj = null;
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            if (constructor == null) {
                return null;
            }
            obj = constructor.newInstance();
        } catch (Exception e) {
        }
        return obj;
    }

    /**
     * @param <T>
     * @param clazz
     * @return
     */
    public static <T extends ObjectVO> T getObjectVOValue(Class<T> clazz) {
        T obj = null;
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            if (constructor == null) {
                return null;
            }
            obj = constructor.newInstance();
        } catch (Exception e) {
        }
        return obj;
    }

    public static <T extends ObjectVO> List<Field> getInheritedDeclaredFields(Class<T> clazz,
            Class<?> stopWhenClass) {
        List<Field> fields = new ArrayList<>();
        List<Class<?>> classes = new ArrayList<>();

        Class<?> cls = clazz;
        do {
            classes.add(cls);
            cls = cls.getSuperclass();
        } while (cls != null && !cls.equals(stopWhenClass));

        for (int i = classes.size() - 1; i >= 0; i--) {
            try {
                Field[] fields1 = classes.get(i).getDeclaredFields();
                if (null != fields1 && fields1.length > 0)
                    fields.addAll(Arrays.asList(fields1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fields;
    }

    // Map -> VO
    public static <T extends ObjectRootVO> T convertToObjectRootVO(Map<String, Object> map,
            Class<? extends ObjectRootVO> type) {
        try {
            ObjectRootVO instance = getObjectRootVOValue(type);
            Field[] fields = type.getDeclaredFields();

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                for (Field field : fields) {
                    if (entry.getKey().equals(field.getName())) {
                        field.setAccessible(true);

                        Object value = Objects.isNull(entry.getValue()) && field.getType().isPrimitive()
                                ? null
                                : map.get(field.getName());

                        field.set(instance, value);
                        break;
                    }
                }
            }
            return (T) instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Map -> VO
    public static <T extends ObjectVO> T convertToObjectVO(Map<String, Object> map, Class<? extends ObjectVO> type) {
        try {
            ObjectVO instance = getObjectVOValue(type);
            Field[] fields = type.getDeclaredFields();

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                for (Field field : fields) {
                    if (entry.getKey().equals(field.getName())) {
                        field.setAccessible(true);

                        Object value = Objects.isNull(entry.getValue()) && field.getType().isPrimitive()
                                ? null
                                : map.get(field.getName());

                        field.set(instance, value);
                        break;
                    }
                }
            }
            return (T) instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T getObject(Class<?> clazz) {
        T instance = null;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            if (constructor == null) {
                return null;
            }
            instance = (T) constructor.newInstance();
        } catch (Exception e) {
        }
        return instance;
    }

    public static <T> T convertToObject(Map<String, Object> map, Class<?> type) {
        T instance = null;
        try {
            instance = getObject(type);
            if (instance == null) {
                return null;
            }

            Field[] fields = type.getDeclaredFields();

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                for (Field field : fields) {
                    if (entry.getKey().equals(field.getName())) {
                        field.setAccessible(true);

                        Object value = Objects.isNull(entry.getValue()) && field.getType().isPrimitive()
                                ? null
                                : map.get(field.getName());

                        field.set(instance, value);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    public static String getSql(String id) {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        StringBuffer sb = new StringBuffer();
        try {
            ClassPathResource resource = new ClassPathResource("data/" + id + ".sql");
            is = resource.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            br.lines().forEach(s -> sb.append(s).append("\n"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (Exception e) {
                }
            if (isr != null)
                try {
                    isr.close();
                } catch (Exception e) {
                }
            if (br != null)
                try {
                    br.close();
                } catch (Exception e) {
                }
        }

        return sb.toString();
    }
}
