package com.sino.soa.common;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import com.sino.config.SinoConfig;
import org.apache.ws.security.WSPasswordCallback;

public class ClientPasswordHandler implements CallbackHandler {
	public void handle(Callback[] callbacks) throws IOException,	UnsupportedCallbackException {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		//设置密码
		pc.setPassword(SinoConfig.getProperty("PASSWORD"));
	}
}
