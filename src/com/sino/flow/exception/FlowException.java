package com.sino.flow.exception;

import com.sino.base.exception.SinoBaseException;

/**
 * Created by wwb.
 * User: demo
 * Date: 2006-12-20
 * Time: 20:53:57
 */
public class FlowException extends SinoBaseException {
    public FlowException() {
    }
    public FlowException(String msg) {
        super(msg);
    }

    public FlowException(Exception e) {
        super(e);
    }
}
