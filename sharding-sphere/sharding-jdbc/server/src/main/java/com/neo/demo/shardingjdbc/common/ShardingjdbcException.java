package com.neo.demo.shardingjdbc.common;

import com.neo.http.common.bean.Error;
import com.neo.http.common.lang.NeoHttpException;

public class ShardingjdbcException extends NeoHttpException {
    public ShardingjdbcException() {
    }

    public ShardingjdbcException(Error error) {
        super(error);
    }

    public ShardingjdbcException(String message) {
        super(message);
    }

    public ShardingjdbcException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShardingjdbcException(Throwable cause) {
        super(cause);
    }

    protected ShardingjdbcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Error getError() {
        return super.getError();
    }
}
