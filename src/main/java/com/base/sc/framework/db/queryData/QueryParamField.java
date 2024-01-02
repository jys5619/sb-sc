package com.base.sc.framework.db.queryData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParamField {
    private List<String> params;
    private String andString;
    private boolean kind;

    protected QueryParamField() {

    }

    public QueryParamField(String line) {
        this.params = new ArrayList<>();
        this.andString = "";
        if (line.indexOf("::") > -1) {
            trueKind(line);
        } else {
            falseKind(line);
        }
    }

    public List<String> getParams() {
        return params;
    }

    public String getAndString() {
        return andString;
    }

    public boolean isKind() {
        return kind;
    }

    private void trueKind(String line) {
        Pattern pattern = Pattern.compile("[:][:](.*?)[^a-zA-Z0-9_]");
        Matcher matcher = pattern.matcher(line + " ");

        while (matcher.find()) {
            String findParam = matcher.group(1);
            params.add(findParam);
            if (matcher.group(1) == null)
                break;
        }

        andString = line;
        for (String param : params) {
            andString = andString.replaceAll("::" + param, "?");
        }

        kind = true;
    }

    private void falseKind(String line) {
        Pattern pattern = Pattern.compile("[-][-](.*?)[^a-zA-Z0-9_]");
        Matcher matcher = pattern.matcher(line + " ");

        while (matcher.find()) {
            String findParam = matcher.group(1);
            params.add(findParam);
            if (matcher.group(1) == null)
                break;
        }

        andString = line;
        for (String param : params) {
            andString = andString.replaceAll("--" + param, "");
        }

        kind = false;
    }

    public Collection<? extends Object> getBindParam(Map<String, Object> realParams) {
        List<Object> resultParam = new ArrayList<>();
        for (String key : params) {
            resultParam.add(realParams.get(key));
        }

        return resultParam;
    }
}
