SELECT TABLE_NAME,
    COLUMN_NAME,
    ORDINAL_POSITION AS SORT_NO,
    COLUMN_DEFAULT AS DEFAULTS,
    IS_NULLABLE AS NULLABLE,
    DATA_TYPE,
    CHARACTER_MAXIMUM_LENGTH AS LEN
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = 'PUBLIC'
ORDER BY TABLE_NAME,
    ORDINAL_POSITION