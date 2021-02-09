package com.neo.demo.shardingjdbc.common;

import com.neo.http.common.bean.Error;

public enum  ShardingjdbcError implements Error {
    ;

    private String code;
    private String message;

    ShardingjdbcError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getRequestId() {
        return null;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
