SELECT PATTRIBUTE_NAME      AS ATTRIBUTE_NAME
     , PDBMS_TABLE          AS DBMS_TABLE
     , PDBMS_COLUMN         AS DBMS_COLUMN
     , PCOLUMN_ALIAS        AS COLUMN_ALIAS
     , PDATA_TYPE           AS DATA_TYPE
     , DECODE(PDATA_TYPE, 1, 'STRING'
                        , 2, 'INTEGER'
                        , 3, 'FLOAT'
                        , 4, 'DATE'
                        , 5, 'BOOLEAN'
                        , 6, 'LONG'
                        , 7, 'DOUBLE'
                        , 8, 'BIGDECIMAL'
                        , 9, 'ATTRIBUTESET'
                        ,10, 'LONGSTRING'
                        ,11, 'ARRAY'
                        ,12, 'FILE'
                        ,13, 'USERID'
                        ,14, 'OBID'
                        ,15, 'STRLIST'
                        ,16, 'STRSET'
                        ,17, 'OBJECT'
                        , ''
             )              AS DATA_TYPE_NAME
     , PDEFAULT_VALUE       AS DEFAULT_VALUE
     , PVALUE_SETTING_INFO  AS VALUE_SETTING_INFO
     , PLENGTHS             AS LENGTHS
     , PCLASS_NAME          AS CLASS_NAME
     , PDEFINED_CLASS_NAME  AS DEFINED_CLASS_NAME
  FROM PSYSATTRREF
 WHERE PCLASS_NAME = ?
 ORDER BY PSORTING