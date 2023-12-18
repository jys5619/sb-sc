package com.base.sc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StrUtil {
    public static boolean isEmpty(String text) {
        return ( text == null || text.length() == 0 );
    }

    public static boolean equals(String str1, String str2) {
        if ( str1 == null && str2 == null ) return true;
        if ( str1 == null || str2 == null ) return false;

        return str1.equals(str2);
    }

    public static String getCamelCase(String snakeCase) {
        if ( isEmpty(snakeCase) ) return "";
        Pattern compile = Pattern.compile("([a-z])_([a-z])");
        Matcher matcher = compile.matcher(snakeCase.toLowerCase());
        return matcher.replaceAll(matchResult -> {
            return String.format(
                    "%s%s",
                    matchResult.group(1).toLowerCase(),
                    matchResult.group(2).toUpperCase()
            );
        });
    }

    public static String getSnakeCase(String camelCase) {
        if ( isEmpty(camelCase) ) return "";
        Pattern pattern = Pattern.compile("([a-z])([A-Z])");
        Matcher matcher = pattern.matcher(camelCase);
        return matcher.replaceAll(matchResult -> {
            return String.format("%s_%s", matchResult.group(1), matchResult.group(2));
        });
    }
}
