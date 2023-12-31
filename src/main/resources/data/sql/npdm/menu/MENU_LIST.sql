WITH FIND_OBID AS (
    SELECT OBID
      FROM PSYSMENU
     WHERE PNAMES LIKE '%' || ? || '%'
)
, FIND_ROOT_OBID_LIST AS (
    SELECT OBID
      FROM (SELECT PFROM_OBID AS OBID
                 , CONNECT_BY_ISLEAF AS LEAF
              FROM PSYSRELATIONINFO
             START WITH PFROM_OBID IN (SELECT OBID FROM FIND_OBID)
           CONNECT BY PRIOR PFROM_OBID = PTO_OBID
             UNION ALL
            SELECT PFROM_OBID AS OBID
                 , CONNECT_BY_ISLEAF AS LEAF
              FROM PSYSRELATIONINFO
             START WITH PTO_OBID IN (SELECT OBID FROM FIND_OBID)
           CONNECT BY PRIOR PFROM_OBID = PTO_OBID
           )
     WHERE LEAF = 1
     GROUP BY OBID
)
, FIND_ROOT_OBID AS (
    SELECT OBID
         , ROWNUM AS RN
      FROM FIND_ROOT_OBID_LIST
)
, FIND_CHILD_LIST AS (
    SELECT B.PFROM_OBID
         , B.PFROM_OBID AS PTO_OBID
         , A.RN
         , 0 AS LVL
         , 0 AS SORTING
         , '/0000000' AS PATHS
      FROM FIND_ROOT_OBID A
         , PSYSRELATIONINFO B
     WHERE B.PFROM_OBID = A.OBID
     GROUP BY B.PFROM_OBID, A.RN
     UNION ALL
    SELECT B.PFROM_OBID
         , B.PTO_OBID
         , A.RN
         , LEVEL AS LVL
         , B.PSORTING AS SORTING
         , SYS_CONNECT_BY_PATH(B.PSORTING + 1000000, '/') AS PATH
      FROM FIND_ROOT_OBID A
         , PSYSRELATIONINFO B
     START WITH B.PFROM_OBID = A.OBID
   CONNECT BY PRIOR B.PTO_OBID = B.PFROM_OBID
)
SELECT DISTINCT
       A.RN
     , A.LVL
     , A.SORTING
     , A.PATHS
     , B.PNAMES AS PARENT_MENU
     , C.PNAMES AS CHILD_MENU
     , C.PLABELS    AS LABELS
     , C.PLABELS_KR AS LABELS_KR
     , C.PLINK_HERF AS LINK_HERF
     , C.PLINK_ALT  AS LINK_ALT
     , C.PIMAGES    AS IMAGES
     , C.PACCESS_EXPRESSION AS ACCESS_EXPRESSION
     , C.PMODULE_NAME AS MODULE_NAME
  FROM FIND_CHILD_LIST A
     , PSYSMENU B
     , PSYSMENU C
 WHERE B.OBID = A.PFROM_OBID
   AND C.OBID = A.PTO_OBID
 ORDER BY A.RN, A.PATHS