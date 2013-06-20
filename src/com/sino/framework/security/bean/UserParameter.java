package com.sino.framework.security.bean;

/**
 * <p>Title: SinoBase</p>
 * <p>Description: 北京思诺博信息科技有限公司基础库：用户参数信息</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class UserParameter {
    private String loginName;
    private String loginPwd;
    private String userId = "";
    private boolean encrypt = true;
    private String arithmetic = "ETS_ENCRYPT.ENCODE";
    private String source;

    public UserParameter() {
        super();
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    public void setArithmetic(String arithmetic) {
        this.arithmetic = arithmetic;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isEncrypt() {
        return encrypt;
    }

    public String getArithmetic() {
        return arithmetic;
    }

    public String getSource() {
        return source;
    }
}
