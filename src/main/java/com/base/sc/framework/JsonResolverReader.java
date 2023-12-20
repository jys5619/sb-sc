package com.base.sc.framework;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.types.ResolvedPrimitiveType;

import springfox.documentation.common.Compatibility;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JsonResolverReader implements OperationBuilderPlugin {

    @Autowired
    private DocumentationPluginsManager pluginsManager;

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    @Override
    public void apply(OperationContext context) {
        boolean isJsonResolver = context.getParameters().stream()
                .anyMatch(p -> p.hasParameterAnnotation(JsonResolver.class));
        List<ResolvedMethodParameter> list = context.getParameters();
        String uri = context.requestMappingPattern();
        int index = 1;
        if (isJsonResolver) {
            Map<String, Object> mapForJson = new HashMap();
            for (ResolvedMethodParameter param : list) {
                if (!(param.getAnnotations().stream()
                        .anyMatch(p -> (p.annotationType().isAssignableFrom(JsonResolver.class))))) {
                    List<Compatibility<springfox.documentation.service.Parameter, RequestParameter>> parameters = new ArrayList<>();
                    ParameterContext parameterContext = new ParameterContext(param, context.getDocumentationContext(),
                            context.getGenericsNamingStrategy(), context, index++);
                    parameters.add(pluginsManager.parameter(parameterContext));
                    List<Compatibility<springfox.documentation.service.Parameter, RequestParameter>> compatibilities = parameters
                            .stream().filter(hiddenParameter().negate()).toList();
                    Collection<RequestParameter> requestParameters = compatibilities.stream()
                            .map(Compatibility::getModern)
                            .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
                    context.operationBuilder().requestParameters(requestParameters);
                    continue;
                }
                makeJsonParameterValueForJsonResolver(uri, mapForJson, param);
            }

        }
    }

    private void makeJsonParameterValueForJsonResolver(String uri, Map<String, Object> mapForJson,
            ResolvedMethodParameter param) {
        String parameter = "";
        Object object = null;
        try {
            if (isAnnotationFor(param, JsonResolver.class)) {
                parameter = param.findAnnotation(JsonResolver.class).get().name();
                object = param.findAnnotation(JsonResolver.class).get().defaultValue();
            }

            if ((object == null || "".equals(object.toString()))) {
                object = makeSample(uri, parameter, param.getParameterType());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("@Requestmapping 메소드의 파라메터가 잘못 선언되어 있습니다.");
        }
        mapForJson.put(parameter, object);

    }

    private boolean isAnnotationFor(ResolvedMethodParameter param, Class cls) {
        for (Annotation annotation : param.getAnnotations()) {
            String str1 = annotation.annotationType().getSimpleName();
            String str2 = cls.getSimpleName();
            if (str1.equals(str2))
                return false;
        }
        return true;
    }

    private Predicate<Compatibility<springfox.documentation.service.Parameter, RequestParameter>> hiddenParameter() {
        return c -> c.getLegacy().map(springfox.documentation.service.Parameter::isHidden).orElse(false);
    }

    private Object makeSample(String uri, String paramName, ResolvedType type) {
        Class erasedType = type.getErasedType();
        List<ResolvedType> typeParameters = type.getTypeBindings().getTypeParameters();
        if (type instanceof ResolvedPrimitiveType) {
            Object obj = makeSample(uri, paramName, type.getErasedType(), 1);
            return obj;
        }
        if ( )
    }

}
