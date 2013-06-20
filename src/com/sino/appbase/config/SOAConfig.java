package com.sino.appbase.config;

import com.sino.base.SinoBaseObject;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public class SOAConfig extends SinoBaseObject {
    private String systemName;
    private String encoding;
    private String host;
    private int port;
    private String icpID;
    private String icpAuth;
    private String startTime;
    private String endTime;

    public SOAConfig() {
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setIcpAuth(String icpAuth) {
        this.icpAuth = icpAuth;
    }

    public void setIcpID(String icpID) {
        this.icpID = icpID;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getIcpAuth() {
        return icpAuth;
    }

    public String getIcpID() {
        return icpID;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}