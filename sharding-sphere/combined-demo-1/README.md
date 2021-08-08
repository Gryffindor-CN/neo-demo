# 1 查询 MySQL 数据库、表容量
> MySQL 中有一个数据库 information_schema，这个库保存了 MySQL 服务器 所有数据库的信息。如：这台MySQL服务器上，到底有哪些数据库、各个数据库有哪些表，每张表的字段类型是什么，各个数据库要什么权限才能访问，等等。

## 概述
一般查询数据库、表使用（容量）情况都可以使用以下查询语句：
```sql
-- 切换到 schema 库
USE information_schema;

-- 查看数据库存使用（大小）情况，其中：
-- TABLES：表示 information_schema 的 TABLE 表
-- <db_name>：表示你要查询的数据库名称
SELECT 
	CONCAT(ROUND(SUM(t.DATA_LENGTH/1024/1024), 2), 'MB') AS data
FROM 
	TABLES t
WHERE t.TABLE_SCHEMA='<db_name>';

-- 查看表使用（大小）情况
-- TABLES 和 <db_name>：同上
-- <table_name>：目标表名称
SELECT 
	CONCAT(ROUND(SUM(t.DATA_LENGTH/1024/1024), 2), 'MB') AS data
FROM 
	TABLES t
WHERE t.TABLE_SCHEMA='<db_name>' AND t.TABLE_NAME='<table_name>';

--- 查看所有数据库容量大小
SELECT
t.TABLE_SCHEMA AS '数据库',
SUM(table_rows) AS '记录数',
SUM(TRUNCATE(t.DATA_LENGTH/1024/1024, 2)) AS '数据容量(MB)',
SUM(TRUNCATE(t.INDEX_LENGTH/1024/1024, 2)) AS '索引容量(MB)'
FROM information_schema.TABLES t
GROUP BY t.TABLE_SCHEMA
ORDER BY SUM(t.DATA_LENGTH) DESC, SUM(t.INDEX_LENGTH) DESC;

--- 查看所有数据库各表容量大小
SELECT
t.TABLE_SCHEMA AS '数据库',
t.TABLE_NAME AS '表名',
t.TABLE_ROWS AS '记录数',
TRUNCATE(DATA_LENGTH/1024/1024, 2) AS '数据容量(MB)',
TRUNCATE(INDEX_LENGTH/1024/1024, 2) AS '索引容量(MB)'
FROM information_schema.TABLES t
ORDER BY t.DATA_LENGTH DESC, t.INDEX_LENGTH DESC;

--- 查看指定数据库容量大小
--- <table_name>：目标表名称
SELECT
t.TABLE_SCHEMA AS '数据库',
SUM(t.TABLE_ROWS) AS '记录数',
SUM(TRUNCATE(t.DATA_LENGTH/1024/1024, 2)) AS '数据容量（MB）',
SUM(TRUNCATE(t.INDEX_LENGTH/1024/1024, 2)) AS '索引容量（MB）'
FROM information_schema.TABLES t
WHERE t.TABLE_SCHEMA='<table_name>';

--- 查看指定数据库各个表的容量大小
--- <table_name>：目标表名称
SELECT
t.TABLE_SCHEMA as '数据库',
t.TABLE_NAME as '表名',
t.TABLE_ROWS as '记录数',
TRUNCATE(t.DATA_LENGTH/1024/1024, 2) as '数据容量(MB)',
TRUNCATE(t.INDEX_LENGTH/1024/1024, 2) as '索引容量(MB)'
FROM information_schema.TABLES t
where t.TABLE_SCHEMA='<table_name>'
order by t.DATA_LENGTH desc, t.INDEX_LENGTH desc;
```