SELECT INDEX_NAME
     , COLUMN_NAME
  FROM ALL_IND_COLUMNS
 WHERE TABLE_NAME = UPPER(?)
 ORDER BY CASE WHEN INDEX_NAME LIKE 'PK%' THEN 'A' ELSE INDEX_NAME END