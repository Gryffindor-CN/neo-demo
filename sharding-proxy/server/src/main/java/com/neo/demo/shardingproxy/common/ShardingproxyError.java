package com.neo.demo.shardingproxy.common;

import com.neo.http.common.bean.Error;

public enum ShardingproxyError implements Error {
    ;

    private String code;
    private String message;

    ShardingproxyError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getRequestId() {
        return null;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
