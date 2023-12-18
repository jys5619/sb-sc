WITH KEYWORDINFO AS (
  SELECT ? AS KEYWORD FROM DUAL
)
SELECT PCLASS_NAME
     , PNAMES
     , PTITLES
     , PBUSINESS_UNIT_CODE
     , PUPPER_ORGANIZATION_CODE
  FROM PTORGANIZATION
 WHERE ((SELECT KEYWORD FROM KEYWORDINFO) IN (PNAMES, PCLASS_NAME, PTITLES, PBUSINESS_UNIT_CODE, PUPPER_ORGANIZATION_CODE) OR
        PTITLES LIKE '%' || (SELECT KEYWORD FROM KEYWORDINFO) || '%'
       )
   AND PCLASS_NAME IN ('BusinessUnit', 'DivisionUnit', 'PlantUnit')
   AND PSTATES = 'Active'
   AND (PUPPER_ORGANIZATION_CODE IN ('HE','HA','VS', 'LGE') OR PBUSINESS_UNIT_CODE IN ('HE','HA','VS'))
   AND PCLASS_NAME IN ('BusinessUnit', 'DivisionUnit', 'PlantUnit')
 ORDER BY PCLASS_NAME, DECODE(PCLASS_NAME, 'DivisionUnit', PUPPER_ORGANIZATION_CODE, PBUSINESS_UNIT_CODE), PTITLES