SELECT TC.TABLE_NAME
     , TC.COLUMN_ID
     , TC.COLUMN_NAME
     , CC.COMMENTS
     , DECODE(TC.DATA_TYPE, 'TIMESTAMP(6)', 'TIMESTAMP', TC.DATA_TYPE) AS DATA_TYPE
     , NVL(DECODE(TC.DATA_TYPE, 'NUMBER', NVL(TC.DATA_PRECISION, 38), TC.DATA_LENGTH), 10) AS LEN
     , DECODE(TC.DATA_TYPE, 'NUMBER', NVL(TC.DATA_SCALE, 0), 0) AS SCALE_LEN
     , TC.NULLABLE
  FROM ALL_TAB_COLUMNS TC
     , ALL_COL_COMMENTS CC
 WHERE TC.OWNER IN ('NPDM_MGR')
   AND TC.COLUMN_NAME LIKE '%' || UPPER(?) || '%'
   AND TC.TABLE_NAME = CC.TABLE_NAME(+)
   AND TC.COLUMN_NAME = CC.COLUMN_NAME(+)
 ORDER BY TC.TABLE_NAME