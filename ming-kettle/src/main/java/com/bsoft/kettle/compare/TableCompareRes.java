package com.bsoft.kettle.compare;

import lombok.Builder;

@Builder
public class TableCompareRes {
    private String tableName;
    private String columnName;
    private String columnType;
    private String columnLength;
    private String columnCommon;
    private String columnSql;
}
