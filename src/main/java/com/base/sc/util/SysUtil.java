package com.base.sc.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SysUtil {

    public static Set<Class<?>> findAllClasses(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Set<Class<?>> classList = new HashSet<>();
        Set<String> readLines = reader.lines()
                .map(line -> line)
                .collect(Collectors.toSet());

        Set<String> subPackageName = readLines.stream()
                .filter(line -> {
                    System.out.println(" > " + line);
                    return !line.endsWith(".class");
                })
                .collect(Collectors.toSet());

        for (String subName : subPackageName) {
            classList.addAll(findAllClasses(packageName + "." + subName));
        }

        Set<Class<?>> myClass = readLines.stream()
                .filter(line -> {
                    System.out.println(" > " + line);
                    return line.endsWith(".class");
                })
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());

        classList.addAll(myClass);

        return classList;
    }

    private static Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }
}
