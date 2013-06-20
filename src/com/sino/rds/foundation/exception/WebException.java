package com.sino.rds.foundation.exception;

import com.sino.base.exception.ObjectException;

public class WebException extends ObjectException {
    public WebException() {
        super();
    }

    public WebException(String msg) {
        super(msg);
    }

    public WebException(Exception cause) {
        super(cause);
    }
}
