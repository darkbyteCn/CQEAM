package com.sino.soa.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;

/**
 * User: zhoujs
 * Date: 2009-6-8 15:10:21
 * Function:
 */
public class SOAUtil {
    public static Map getPropMap() {
        Map outProps = new HashMap();
        outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        outProps.put(WSHandlerConstants.USER, "admin");//设置用户名
        outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
        outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientPasswordHandler.class.getName());//设置密码
        return outProps;

    }

}
