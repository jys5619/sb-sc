package com.base.sc.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.base.sc.biz.common.DateField;
import com.base.sc.biz.common.DateTimeField;
import com.base.sc.biz.vo.dev.DevProjectVO;
import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.types.ResolvedArrayType;
import com.fasterxml.classmate.types.ResolvedInterfaceType;
import com.fasterxml.classmate.types.ResolvedObjectType;
import com.fasterxml.classmate.types.ResolvedPrimitiveType;
import com.fasterxml.classmate.types.ResolvedRecursiveType;
import com.fasterxml.classmate.types.TypePlaceHolder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JsonResolverReader implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return isSupportsParameter(parameter.getParameterType()) && parameter.hasParameterAnnotation(JsonResolver.class);
    }

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> payloadMap = mapper.readValue(httpServletRequest.getInputStream(), new TypeReference<Map<String,Object>>(){});

        // parameter.getMethod();
        // parameter.getAnnotatedElement();
        // parameter.getAnnotatedElement();
        // parameter.getMember();
        // parameter.getParameterName();
        // parameter.getGenericParameterType();
        JsonResolver jsonResolver = parameter.getParameterAnnotation(JsonResolver.class);
        String name = jsonResolver.name();

        Object result = null;

        if ( payloadMap.containsKey(name) ) {
            getObject(payloadMap.get(name), parameter.getParameterType());
        }
 
        return result;
    }

    public Object getObject(Object dataObject, Class<?> clazz) {
        Object resultObject = null;
        if ( isVariable(clazz) ) {
            resultObject = dataObject;
        } else if ( isProjectVO(clazz) ) {
            resultObject = makeVO(dataObject, clazz.getName());
        } else if ( clazz.isAssignableFrom(List.class) ) {
            resultObject = makeList(dataObject);
        }

        return resultObject;
    }

    private Object makeVO(Object dataObject, String className) {
        Object result = null;
        try {
            Class<?> cls = Class.forName(className);
            try {
                result = makeSample(dataObject, cls, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    private Object makeSample(Object dataObject, Type type, int depth) {
        Class<?> cls = getClass(type);
        if ( type instanceof Class ) {
            if ( isVariable(cls) ) {
                return getVariableValue(cls, dataObject);
            } else if ( List.class.isAssignableFrom(cls) ) {
                return dataObject == null ? new ArrayList<>() : ""; // TODO
            } else if ( Map.class.isAssignableFrom(cls) ) {
                return dataObject == null ? new HashMap<>() : ""; // TODO
            } else if ( Set.class.isAssignableFrom(cls) ) {
                return dataObject == null ? new HashSet<>() : ""; // TODO
            } else if ( cls.isEnum() ) {
                Object[] objArray = cls.getEnumConstants();
                return ( objArray.length > 0 ) ? objArray[0] : null;
            } else {
                Object obj = null;
                try {
                    Constructor<?> constructor = cls.getDeclaredConstructor();
                    if ( constructor == null ) return null;
                    obj = constructor.newInstance();
                    setDataValue(dataObject, obj, depth + 1);
                    return obj;
                } catch ( Exception e) {
                    if ( obj != null ) return obj;
                    return new Object();
                }
            }
        } else if ( type instanceof ParameterizedType ) {
            Object obj = makeSample(dataObject, (ParameterizedType)type, depth + 1);
            return obj;
        } else if ( type instanceof GenericArrayType ) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if ( componentClass != null ) {
                return Array.newInstance(componentClass, 0);
            }
        }
        return null;
    }

    
    private void setDataValue(Object dataObject, Object voObject, int depth) {
        List<Field> fieldList = getInheritedDeclaredFields(voObject.getClass(), null);
        for ( Field voField : fieldList ) {
            Class<?> cls = voField.getType();
            if ( isVariable(cls) ) {
                 setFieldValue(voObject, voField, getVariableValue(cls, dataObject), true);
            } else if ( DateField.class.isAssignableFrom(voField.getType()) ) {
                setFieldValue(voObject, voField, new DateField(), true);
                continue;
            } else if ( DateTimeField.class.isAssignableFrom(voField.getType()) ) {
                setFieldValue(voObject, voField, new DateTimeField(), true);
                continue;
            } else if ( isCollectionType(voField.getType()) ) {
                if ( voField.getGenericType() instanceof ParameterizedType ) {
                    Type genericType = voField.getGenericType();
                    ParameterizedType parameterizedType = (ParameterizedType)voField.getGenericType();
                    Type rawType = parameterizedType.getRawType();
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    Type nestedType = actualTypeArguments[actualTypeArguments.length-1];
                    if ( nestedType.getTypeName().equals(voObject.getClass().getTypeName()) ) {
                        if ( !isCollectionType(nestedType.getClass())) {
                            System.out.println("Recursive ===> " + nestedType.getTypeName());
                            if ( depth > 2 ) continue;
                        }
                        Object obj = makeSample(dataObject, (ParameterizedType)voField.getGenericType(), depth + 1);
                        setFieldValue(voObject, voField, obj, false);
                        continue;
                    } else {
                        Object obj = makeSample(dataObject, voField.getGenericType(), depth + 1);
                        setFieldValue(voObject, voField, obj, false);
                        continue;
                    }
                } else {
                    if ( cls.isEnum() ) {
                        Object[] objArray = cls.getEnumConstants();
                        if ( objArray.length > 0 ) setFieldValue(voObject, voField, objArray[0], true);
                        continue;
                    }
                    if ( cls.isArray() ) {
                        Type componentType = voField.getType().getComponentType();
                        Object[] objArray = (Object[])Array.newInstance((Class)componentType, 1);
                        objArray[0] = makeSample(dataObject, componentType, depth + 1);
                        setFieldValue(voObject, voField, objArray, false);
                        continue;
                    }
                    if ( cls.isInterface() ) {
                        continue;
                    }
                    // if ( ObjectRoot.class.isAssignableFrom(voField.getType()) ) continue;
                    // Object obj = makeSample(uri, paramName, voField.getGenericType(), depth + 1);
                    // setFieldValue(voObject, voField, obj, false);
                    // continue;
                }

            }
        }
    }

    private List<Field> getInheritedDeclaredFields(Class<?> fromClass, Class<?> stopWhenClass) {
        if ( stopWhenClass == null ) {
            stopWhenClass = Object.class;
        }
        List<Field> fields = new ArrayList<>();
        List<Class<?>> classes = new ArrayList<>();

        Class<?> cls = fromClass;
        do {
            classes.add(cls);
            cls = cls.getSuperclass();
        } while ( cls != null && !cls.equals(stopWhenClass));

        for ( int i = classes.size() - 1; i >= 0; i-- ) {
            try {
                Field[] fields1 = classes.get(i).getDeclaredFields();
                if ( null != fields1 || fields1.length > 0 ) fields.addAll(Arrays.asList(fields1));
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        return fields;
    }

    private void setFieldValue(Object obj, Field voField, Object value, boolean emptyCheck) {
        try {
            if ( value != null ) {
                if ( !(Modifier.isFinal(voField.getModifiers()) && Modifier.isStatic(voField.getModifiers()))) {
                    if ( !voField.canAccess(obj) ) {
                        voField.setAccessible(true);
                        if ( !emptyCheck || voField.get(obj) == null ) {
                            voField.set(obj, value);
                        }
                        voField.setAccessible(false);
                    } else {
                        if ( !emptyCheck || voField.get(obj) == null ) {
                            voField.set(obj, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private Class<?> getClass(Type type) {
        if ( type instanceof Class) {
            return (Class<?>) type;
        } else if ( type instanceof ParameterizedType ) {
            return getClass(((ParameterizedType) type).getRawType());
        } else if ( type instanceof GenericArrayType ) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if ( componentClass != null) {
                return Array.newInstance(componentClass, 0).getClass();
            }
        }
        String className = getClassName(type);
        if ( className == null || className.isEmpty() ) {
            return null;
        }
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e ) {
            throw new RuntimeException();
        }
    }

    private String getClassName(Type type) {
        if ( type == null ) return "";
        String className = type.toString();
        if ( className.startsWith("class ") ) {
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
        isProjectVO(clazz)
        ;

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

    private Object getVariableValue(Class<?> clazz, Object dataObject) {
        Object result = null;
        if ( dataObject == null ) {
            result = dataObject;
        } else if ( String.class.isAssignableFrom(clazz) ) {
            result = dataObject.toString();
        } else if ( Integer.class.isAssignableFrom(clazz) || int.class.isAssignableFrom(clazz) ) {
            result = Integer.valueOf(dataObject.toString());
        } else if ( Long.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz) ) {
            result = Long.valueOf(dataObject.toString());
        } else if ( Float.class.isAssignableFrom(clazz) || float.class.isAssignableFrom(clazz) ) {
            result = Float.valueOf(dataObject.toString());
        } else if ( Double.class.isAssignableFrom(clazz) || double.class.isAssignableFrom(clazz) ) {
            result = Double.valueOf(dataObject.toString());
        } else if ( Boolean.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz) ) {
            result = Boolean.valueOf(dataObject.toString());
        } else if ( Date.class.isAssignableFrom(clazz) ) {
            result = Boolean.valueOf(dataObject.toString());
        }
        return result;
    }

    private boolean isUserVariable(Class<?> clazz) {
        return clazz.isAssignableFrom(DateTimeField.class) || clazz.isAssignableFrom(DateField.class);
    }
    private boolean isProjectVO(Class<?> clazz) {
        return clazz.getName().startsWith("com.base.sc.biz.vo") && clazz.getName().endsWith("VO");
    }

    private boolean get(Class<?> clazz) {
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
        (clazz.getName().startsWith("com.base.sc.biz.vo") && clazz.getName().endsWith("VO"))
        ;

        return result;
    }
}
