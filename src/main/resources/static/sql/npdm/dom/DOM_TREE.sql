WITH TARGET_CLASS_NAME AS (
    SELECT PCLASS_NAME
      FROM PSYSCLASSINFO
     WHERE PCLASS_NAME = ?
)
, TARGET_MAX_LEVEL AS (
    SELECT MAX(LEVEL) AS MAX_LVL
      FROM PSYSCLASSINFO
    START WITH PCLASS_NAME IN (SELECT PCLASS_NAME FROM TARGET_CLASS_NAME)
    CONNECT BY PRIOR PCLASS_PARENT_OBID = PCLASS_OBID
)
, TREE_CLASS_NAME AS (
    SELECT 0 AS GB
         , MAX_LVL - LVL + 1 AS LVL
         , PCLASS_NAME AS CLASS_NAME
         , NVL((SELECT PCLASS_NAME FROM TARGET_CLASS_NAME WHERE PCLASS_NAME = A.PCLASS_NAME), '') AS TARGET_CLASS_NAME
         , '' || LVL AS PATHS
         , ROWNUM AS RN
      FROM (SELECT LEVEL AS LVL
                 , PCLASS_NAME
              FROM PSYSCLASSINFO
            START WITH PCLASS_NAME IN (SELECT PCLASS_NAME FROM TARGET_CLASS_NAME)
            CONNECT BY PRIOR PCLASS_PARENT_OBID = PCLASS_OBID
             ORDER BY LEVEL DESC
           ) A
         , (SELECT MAX_LVL
              FROM TARGET_MAX_LEVEL
           ) B
     UNION ALL
    SELECT 1 AS GB
         , MAX_LVL + LEVEL - 1 AS LVL
         , PCLASS_NAME AS CLASS_NAME
         , '' AS TARGET_CLASS_NAME
         , '' AS PATHS
         , ROWNUM AS RN
      FROM PSYSCLASSINFO
         , (SELECT MAX_LVL
              FROM TARGET_MAX_LEVEL
           ) B
     WHERE LEVEL > 1
    START WITH PCLASS_NAME IN (SELECT PCLASS_NAME FROM TARGET_CLASS_NAME)
    CONNECT BY PRIOR PCLASS_OBID = PCLASS_PARENT_OBID
)
SELECT CLASS_NAME
     , TARGET_CLASS_NAME
     , LVL
  FROM TREE_CLASS_NAME
 ORDER BY GB, RN