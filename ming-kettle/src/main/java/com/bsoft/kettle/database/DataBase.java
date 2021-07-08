package com.bsoft.kettle.database;

import lombok.Builder;

@Builder
public class DataBase {
    private String url;
    private String userName;
    private String passWord;
}
