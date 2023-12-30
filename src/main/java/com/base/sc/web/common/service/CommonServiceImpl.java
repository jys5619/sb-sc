package com.base.sc.web.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.base.sc.framework.db.QueryExecutor;
import com.base.sc.framework.sysvo.ColumnVO;
import com.base.sc.framework.sysvo.TableVO;
import com.base.sc.util.StrUtil;
import com.base.sc.util.SysUtil;

@Service("commonService")
public class CommonServiceImpl implements CommonService {

    @Override
    public String getScript() {
        List<TableVO> tableList = QueryExecutor.selectList("syssql/SELECT_TABLE_LIST", null, TableVO.class);
        List<ColumnVO> columnList = QueryExecutor.selectList("syssql/SELECT_COLUMN_LIST", null, ColumnVO.class);

        StringBuilder sb = new StringBuilder();
        Map<String, String> packageList = SysUtil.getClassList("com.base.sc.biz.vo");

        for (TableVO tableVO : tableList) {
            String tableName = StrUtil.getCamelUpperCase(tableVO.getTableName());
            String classSimpleName = tableName + "VO";

            List<ColumnVO> targetColumnList = columnList.stream()
                    .filter(vo -> vo.getTableName().equals(tableVO.getTableName())).toList();

            sb.append("let ").append(classSimpleName).append(" = function () {\n");
            sb.append("  return {\n");
            for (ColumnVO columnVO : targetColumnList) {
                if ("DATE".equals(columnVO.getDataType())) {
                    sb.append("    ").append(StrUtil.getCamelCase(columnVO.getColumnName())).append(": {\n")
                            .append("      date: null,\n")
                            .append("      val: ''\n")
                            .append("    },\n");
                } else if ("TIMESTAMP".equals(columnVO.getDataType())) {
                    sb.append("    ").append(StrUtil.getCamelCase(columnVO.getColumnName())).append(": {\n")
                            .append("      datetime: null,\n")
                            .append("      val: ''\n")
                            .append("    },\n");
                } else {
                    sb.append("    ").append(StrUtil.getCamelCase(columnVO.getColumnName())).append(": ")
                            .append(columnVO.getDefaults() == null ? "null" : columnVO.getDefaults()).append(",\n");
                }
            }
            sb.append("    className: '").append(
                    packageList.containsKey(classSimpleName) ? packageList.get(classSimpleName) : classSimpleName)
                    .append("',\n");
            sb.append("    outData: {},\n");
            sb.append("  }\n");
            sb.append("};\n\n");
        }
        return sb.toString();
    }

}
