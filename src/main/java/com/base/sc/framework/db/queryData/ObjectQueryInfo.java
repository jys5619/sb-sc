package com.base.sc.framework.db.queryData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.base.sc.biz.common.DateField;
import com.base.sc.biz.common.DateTimeField;
import com.base.sc.biz.vo.root.ObjectRootVO;
import com.base.sc.biz.vo.root.ObjectVO;
import com.base.sc.framework.user.MyInfo;
import com.base.sc.util.StrUtil;

public class ObjectQueryInfo {
    private String tableName;
    private String selectSql;
    private String insertsql;
    private String updatesql;
    private List<Field> fields;
    private List<String> paramList;

    public <T extends ObjectVO> ObjectQueryInfo(Class<T> clazz) {
        this.paramList = new ArrayList<>();
        this.tableName = StrUtil
                .getSnakeUpperCase(clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 2));
        this.fields = QueryUtil.getInheritedDeclaredFields(clazz, ObjectRootVO.class);
    }

    private String getSelectSql() {
        if (StrUtil.isEmpty(this.selectSql)) {
            StringBuilder sb = new StringBuilder();

            sb.append("SELECT ID\n");
            for (Field field : fields) {
                sb.append("     , ").append(StrUtil.getSnakeUpperCase(field.getName())).append("\n");
            }
            sb.append("     , MODIFIER\n")
                    .append("     , MODIFIED\n")
                    .append("     , CREATOR\n")
                    .append("     , CREATED\n")
                    .append("  FROM ").append(tableName).append("\n")
                    .append(" WHERE 1 = 1\n")
                    .append("   %field");
            this.selectSql = sb.toString();
        }
        return this.selectSql;
    }

    private String getInsertSql() {
        if (StrUtil.isEmpty(this.insertsql)) {
            StringBuilder sb = new StringBuilder();

            sb.append("INSERT\n")
                    .append("  INTO ").append(tableName).append("\n")
                    .append("     ( ID\n");
            for (Field field : fields) {
                sb.append("     , ").append(StrUtil.getSnakeUpperCase(field.getName())).append("\n");
            }
            sb.append("     , MODIFIER\n")
                    .append("     , MODIFIED\n")
                    .append("     , CREATOR\n")
                    .append("     , CREATED\n")
                    .append("     )\n")
                    .append("VALUES\n")
                    .append("     ( ?\n");
            for (Field field : fields) {
                sb.append("     , ?\n");
                paramList.add(field.getName());
            }
            sb.append("     , ?\n")
                    .append("     , NOW()\n")
                    .append("     , ?\n")
                    .append("     , NOW()\n")
                    .append("     )");

            this.insertsql = sb.toString();
        }
        return this.insertsql;
    }

    private String getUpdateSql() {
        if (StrUtil.isEmpty(this.updatesql)) {
            StringBuilder sb = new StringBuilder();

            this.updatesql = sb.append("UPDATE ").append(tableName).append("\n")
                    .append("   SET MODIFIER = ?\n")
                    .append("     , MODIFIED = NOW()\n")
                    .append("     %field")
                    .append(" WHERE ID = ?").toString();
        }
        return this.updatesql;
    }

    public QueryExecutorParam getSelectExecutorParam(ObjectVO objectVO) {
        List<Object> bindParamList = new ArrayList<>();
        Map<String, Object> params = QueryUtil.convertToMap(objectVO, ObjectVO.class, true);
        StringBuilder sb = new StringBuilder();
        List<Field> rootFields = QueryUtil.getInheritedDeclaredFields(ObjectRootVO.class, ObjectVO.class);
        List<String> rootColumns = rootFields.stream().map(Field::getName).toList();

        for (String field : params.keySet()) {
            if (paramList.contains(field) || rootColumns.contains(field)) {
                if (params.get(field) != null) {
                    if (params.get(field) instanceof DateField) {
                        sb.append("   AND ").append(StrUtil.getSnakeUpperCase(field))
                                .append(" = PARSEDATETIME (?, 'yyyyMMdd')\n");
                    } else if (params.get(field) instanceof DateTimeField) {
                        sb.append("   AND ").append(StrUtil.getSnakeUpperCase(field))
                                .append(" = PARSEDATETIME (?, 'yyyyMMddhhmmss')\n");
                    } else {
                        sb.append("   AND ").append(StrUtil.getSnakeUpperCase(field)).append(" = ?\n");
                    }
                    bindParamList.add(params.get(field));
                }
            }
        }

        QueryExecutorParam queryExecutorParam = new QueryExecutorParam();
        queryExecutorParam.setSql(this.getSelectSql().replace("%field", sb.toString()));
        queryExecutorParam.setParamList(bindParamList);
        return queryExecutorParam;
    }

    public QueryExecutorParam getSaveExecutorParam(ObjectRootVO objectRootVO) {
        QueryExecutorParam queryExecutorParam = null;

        if (StrUtil.isEmpty(objectRootVO.getId())) {
            Map<String, Object> params = QueryUtil.convertToMap(objectRootVO, ObjectRootVO.class, true);
            queryExecutorParam = getInsertExecutorParam(params);
        } else {
            Map<String, Object> params = QueryUtil.convertToMap(objectRootVO, ObjectRootVO.class, false);
            params.put("id", objectRootVO.getId());
            queryExecutorParam = getUpdateExecutorParam(params);
        }

        return queryExecutorParam;
    }

    private QueryExecutorParam getInsertExecutorParam(Map<String, Object> params) {
        List<Object> bindParamList = new ArrayList<>();
        bindParamList.add(UUID.randomUUID().toString().replaceAll("-", "U"));
        for (String param : paramList) {
            bindParamList.add(params.get(param));
        }
        bindParamList.add(MyInfo.getinstance().getUserId());
        bindParamList.add(MyInfo.getinstance().getUserId());

        QueryExecutorParam queryExecutorParam = new QueryExecutorParam();
        queryExecutorParam.setSql(this.getInsertSql());
        queryExecutorParam.setParamList(bindParamList);
        return queryExecutorParam;
    }

    private QueryExecutorParam getUpdateExecutorParam(Map<String, Object> params) {
        List<Object> bindParamList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        bindParamList.add(MyInfo.getinstance().getUserId());
        for (String field : params.keySet()) {
            if (paramList.contains(field)) {
                sb.append("     , ").append(StrUtil.getSnakeUpperCase(field)).append(" = ?\n");
                bindParamList.add(params.get(field));
            }
        }
        bindParamList.add(params.get("id"));

        QueryExecutorParam queryExecutorParam = new QueryExecutorParam();
        queryExecutorParam.setSql(this.getUpdateSql().replace("%field", sb.toString()));
        queryExecutorParam.setParamList(bindParamList);
        return queryExecutorParam;
    }

}
