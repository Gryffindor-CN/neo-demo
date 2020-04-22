package com.neo.demo.shardingproxy.common;

import com.neo.http.common.bean.Error;
import com.neo.http.common.lang.NeoHttpException;

public class ShardingproxyException extends NeoHttpException {

    public ShardingproxyException(Error error) {
        super(error);
    }

    public ShardingproxyException(String message) {
        super(message);
    }

    public ShardingproxyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShardingproxyException(Throwable cause) {
        super(cause);
    }

    protected ShardingproxyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Error getError() {
        return super.getError();
    }
}
