package com.sino.hn.sms.service;

import com.sino.base.config.SMSConfig;

public abstract class SMSService extends Thread { 
	public abstract void setSMSConfig(SMSConfig smsConfig); 
}
