package com.sino.soa.service;

/**
 * User: zhoujs
 * Date: 2009-5-31 17:16:47
 * Function:SOA服务处理
 */
public class SOAService {

    public void runService() {
        System.out.println("start SOA service...");
        SOAProcess soaProcess = new SOAProcess();
        soaProcess.synMisInfo();
        soaProcess.synTDInfo();

    }

}