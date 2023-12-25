package com.base.sc.framework;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JsonResolverReader implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return isSupportsParameter(parameter.getParameterType())
                && parameter.hasParameterAnnotation(JsonResolver.class);
    }

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> payloadMap = mapper.readValue(httpServletRequest.getInputStream(),
                new TypeReference<Map<String, Object>>() {
                });

        JsonResolver jsonResolver = parameter.getParameterAnnotation(JsonResolver.class);
        String name = jsonResolver.name();
        Type genericType = getGenericTypeClass(parameter);

        Object result = null;

        if (payloadMap.containsKey(name)) {
            result = makeDataObject(payloadMap, name, parameter.getParameterType(), genericType);
        }

        return result;
    }

    private Object makeDataObject(Map<String, Object> dataObject, String key, Type type, Type genericType) {
        Class<?> clazz = getClass(type);

        if (isVariable(clazz)) {
            return getVariableValue(clazz, dataObject.get(key));
        } else if (List.class.isAssignableFrom(clazz)) {
            List resultList = new ArrayList();
            ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) dataObject.get(key);
            for (Map<String, Object> map : list) {
                Object obj = getObjectValue(map, genericType, 1);
                resultList.add(obj);
            }
            return resultList;
        } else if (Map.class.isAssignableFrom(clazz)) {
            return dataObject == null ? new HashMap<>() : ""; // TODO
        } else if (Set.class.isAssignableFrom(clazz)) {
            return dataObject == null ? new HashSet<>() : ""; // TODO
        } else if (clazz.isEnum()) {
            Object[] objArray = clazz.getEnumConstants();
            return (objArray.length > 0) ? objArray[0] : null;
        } else if (type instanceof Class) {
            return getObjectValue((Map<String, Object>) dataObject.get(key), type, 1);
        }
        return null;
    }

    private Object getObjectValue(Map<String, Object> dataObject, Type type, int depth) {
        Class<?> clazz = getClass(type);
        Object obj = null;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            if (constructor == null) {
                return null;
            }
            obj = constructor.newInstance();
            setObjectField(dataObject, obj, depth);
        } catch (Exception e) {
            if (obj != null) {
                return obj;
            }
            obj = new Object();
        }
        return obj;
    }

    private Type getGenericTypeClass(MethodParameter parameter) {
        if (!parameter.getParameterType().getName().equals(parameter.getGenericParameterType().getTypeName())) {
            Type[] actualTypeArguments = ((ParameterizedType) parameter.getGenericParameterType())
                    .getActualTypeArguments();
            Type type = actualTypeArguments[actualTypeArguments.length - 1];
            try {
                return Class.forName(type.getTypeName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void setObjectField(Map<String, Object> dataObject, Object voObject, int depth) {
        List<Field> fieldList = getInheritedDeclaredFields(voObject.getClass(), null);
        for (Field voField : fieldList) {
            Class<?> clazz = voField.getType();
            if (isVariable(clazz)) {
                Object obj = getVariableValue(clazz, dataObject.get(voField.getName()));
                setFieldValue(voObject, voField, obj, true);
            } else if (isCollectionType(voField.getType())) {
                if (voField.getGenericType() instanceof ParameterizedType) {
                    Type genericType = voField.getGenericType();
                    ParameterizedType parameterizedType = (ParameterizedType) voField.getGenericType();
                    Type rawType = parameterizedType.getRawType();
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    Type nestedType = actualTypeArguments[actualTypeArguments.length - 1];
                    if (nestedType.getTypeName().equals(voObject.getClass().getTypeName())) {
                        if (!isCollectionType(nestedType.getClass())) {
                            System.out.println("Recursive ===> " + nestedType.getTypeName());
                            if (depth > 2)
                                continue;
                        }
                        List resultList = new ArrayList();
                        ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) dataObject
                                .get(voField.getName());
                        for (Map<String, Object> map : list) {
                            Object obj = getObjectValue(map, voField.getGenericType(), 1);
                            resultList.add(obj);
                        }

                        // Object obj = makeSample(dataObject, (ParameterizedType)
                        // voField.getGenericType(), depth + 1);
                        setFieldValue(voObject, voField, resultList, false);
                        continue;
                    } else {
                        List resultList = new ArrayList();
                        ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) dataObject
                                .get(voField.getName());
                        for (Map<String, Object> map : list) {
                            Object obj = getObjectValue(map, voField.getGenericType(), 1);
                            resultList.add(obj);
                        }
                        setFieldValue(voObject, voField, resultList, false);
                        continue;
                    }
                }
            } else {
                Object obj = getObjectValue(getMapData(dataObject, voField.getName()), voField.getType(),
                        depth + 1);
                setFieldValue(voObject, voField, obj, true);
            }
        }
    }

    private Map<String, Object> getMapData(Map<String, Object> dataObject, String key) {
        return (Map<String, Object>) dataObject.get(key);
    }

    private void setFieldValue(Object obj, Field voField, Object value, boolean emptyCheck) {
        try {
            if (value != null) {
                if (!(Modifier.isFinal(voField.getModifiers()) && Modifier.isStatic(voField.getModifiers()))) {
                    if (!voField.canAccess(obj)) {
                        voField.setAccessible(true);
                        if (!emptyCheck || voField.get(obj) == null) {
                            voField.set(obj, value);
                        }
                        voField.setAccessible(false);
                    } else {
                        if (!emptyCheck || voField.get(obj) == null) {
                            voField.set(obj, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Object getVariableValue(Class<?> clazz, Object dataObject) {
        Object result = null;
        if (dataObject == null) {
            result = dataObject;
        } else if (String.class.isAssignableFrom(clazz)) {
            result = dataObject.toString();
        } else if (Integer.class.isAssignableFrom(clazz) || int.class.isAssignableFrom(clazz)) {
            result = Integer.valueOf(dataObject.toString());
        } else if (Long.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz)) {
            result = Long.valueOf(dataObject.toString());
        } else if (Float.class.isAssignableFrom(clazz) || float.class.isAssignableFrom(clazz)) {
            result = Float.valueOf(dataObject.toString());
        } else if (Double.class.isAssignableFrom(clazz) || double.class.isAssignableFrom(clazz)) {
            result = Double.valueOf(dataObject.toString());
        } else if (Boolean.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz)) {
            result = Boolean.valueOf(dataObject.toString());
        } else if (Date.class.isAssignableFrom(clazz)) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);
                format.setTimeZone(TimeZone.getTimeZone("UTC"));
                result = format.parse(dataObject.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private List<Field> getInheritedDeclaredFields(Class<?> fromClass, Class<?> stopWhenClass) {
        if (stopWhenClass == null) {
            stopWhenClass = Object.class;
        }
        List<Field> fields = new ArrayList<>();
        List<Class<?>> classes = new ArrayList<>();

        Class<?> cls = fromClass;
        do {
            classes.add(cls);
            cls = cls.getSuperclass();
        } while (cls != null && !cls.equals(stopWhenClass));

        for (int i = classes.size() - 1; i >= 0; i--) {
            try {
                Field[] fields1 = classes.get(i).getDeclaredFields();
                if (null != fields1 || fields1.length > 0)
                    fields.addAll(Arrays.asList(fields1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fields;
    }

    private Class<?> getClass(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if (componentClass != null) {
                return Array.newInstance(componentClass, 0).getClass();
            }
        }
        String className = getClassName(type);
        if (className == null || className.isEmpty()) {
            return null;
        }
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    private String getClassName(Type type) {
        if (type == null)
            return "";
        String className = type.toString();
        if (className.startsWith("class ")) {
            className = className.substring("class ".length());
        }
        return className;
    }

    private boolean isCollectionType(Class<?> type) {
        return List.class.isAssignableFrom(type) ||
                Set.class.isAssignableFrom(type) ||
                Map.class.isAssignableFrom(type);
    }

    private List<Object> makeList(Object obj) {
        return null;
    }

    private boolean isSupportsParameter(Class<?> clazz) {
        boolean result = clazz.isAssignableFrom(String.class) ||
                clazz.isAssignableFrom(Integer.class) ||
                clazz.isAssignableFrom(int.class) ||
                clazz.isAssignableFrom(Long.class) ||
                clazz.isAssignableFrom(long.class) ||
                clazz.isAssignableFrom(Float.class) ||
                clazz.isAssignableFrom(float.class) ||
                clazz.isAssignableFrom(Double.class) ||
                clazz.isAssignableFrom(double.class) ||
                clazz.isAssignableFrom(Boolean.class) ||
                clazz.isAssignableFrom(boolean.class) ||
                clazz.isAssignableFrom(Date.class) ||
                clazz.isAssignableFrom(List.class) ||
                clazz.isAssignableFrom(Map.class) ||
                clazz.isAssignableFrom(Set.class) ||
                isProjectVO(clazz);

        return result;
    }

    private boolean isVariable(Class<?> clazz) {
        return clazz.isAssignableFrom(String.class) ||
                clazz.isAssignableFrom(Integer.class) ||
                clazz.isAssignableFrom(int.class) ||
                clazz.isAssignableFrom(Long.class) ||
                clazz.isAssignableFrom(long.class) ||
                clazz.isAssignableFrom(Float.class) ||
                clazz.isAssignableFrom(float.class) ||
                clazz.isAssignableFrom(Double.class) ||
                clazz.isAssignableFrom(double.class) ||
                clazz.isAssignableFrom(Boolean.class) ||
                clazz.isAssignableFrom(boolean.class) ||
                clazz.isAssignableFrom(Date.class);
    }

    private boolean isProjectVO(Class<?> clazz) {
        return clazz.getName().startsWith("com.base.sc.biz.vo") && clazz.getName().endsWith("VO");
    }
}
