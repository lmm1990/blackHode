# blackHode（黑洞统计）

一款高性能的实时数据统计框架。

## :sparkles: 特性

1. 性能卓越，本地环境测试，统计速度：10W+，每秒
2. 无需编码，简单配置即可支持统计不同的数据
3. 可实时观察统计效率，监控界面：http://localhost:9999/
4. 支持在监控界面停止统计

## ⚡ 性能测试
1. 安装kafka环境
2. 运行produce-kafka-data-test初始化测试数据
3. 更改blackHode项目配置（mysql、kafka）
4. 运行blackHode项目，打开监控界面：http://localhost:9999/ 观测统计速度，预计10分钟后统计速度达到10W+/秒

## 💪 技术栈
1. spring-boot
2. fastjson
3. mybatis-plus
4. mysql-connector-java
5. druid
6. disruptor
7. spring-kafka
8. vertx-core

## 📖 文档

### 数据表配置（configure/tableConfig.json）
```
[{
  /* 数据库名称 */
  "dbName": "statistics",
  /* 数据表名称 */
  "tableName": "ad_space_report",
  /* 数据表备注 */
  "tableComment": "广告位报表",
  /* 分表类型：YEAR（按年分表）、MONTH（按月分表）、NONE（不分表） */
  "tableShardingType": "MONTH",
  /* 数据表字段列表 */
  "columnList": [{
    /* 列名 */
    "name": "_current_date",
    /* 数据类型：CHAR、VARCHAR、TEXT、TIMESTAMP、DATETIME、DATE、TIME、INT、FLOAT*/
    "type": "DATE",
    /* 长度 */
    "length": 0,
    /* 列备注 */
    "comment": "日期",
    /* 是否是自增列，自增列必须是主键 */
    "autoIncrement": false,
    /* 是否是主键列 */
    "primaryKey": true,
    /* 是否不为空 */
    "notNull":true
  }]
}]
```

### 数据源关系配置（configure/sourceDataMapper.json）

```
[
  {
    /* 数据表名 */
    "tableName": "ad_space_report",
    /* 时间列 */
    "dateTimeColumn": {
      /* 列名 */
      "columnName": "_current_date",
      /* 时间格式化字符串 */
      "dateFormat": "yyyy-MM-dd",
      /* 字段对应的数据源，行数据的下标 */
      "index": 0
    },
    /* 主键列列表 */
    "primaryKeyList": [
      {
        /* 列名 */
        "columnName": "_current_date",
        /* 字段对应的数据源，行数据的下标 */
        "index": 0
      }
    ],
    /* 数据统计列列表 */
    "countColumnList": [
      {
        /* 列名 */
        "columnName": "pv",
        /* 字段对应的数据源，行数据的下标 */
        "index": 4
      }
    ]
  }
]
```

## 💿 快速开始

1. 更改blackHode项目配置（mysql、kafka）
2. 更改数据表配置：configure/tableConfig.json
3. 更新数据源&数据表关系配置：configure/sourceDataMapper.json
4. 运行blackHode项目

## 🎯 线路图

- [X] 停止统计时防止数据丢失
- [ ] 支持UV统计

## 🎁 致谢

灵感来自 @lilonglong