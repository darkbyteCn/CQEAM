package com.sino.framework.security.dto;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.SinoBaseObject;
import com.sino.base.dto.DTO;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public class ServletConfigDTO extends SinoBaseObject implements DTO {
    private boolean loadConnPools = false;
    private boolean loadMessages = false;
    private boolean startSMSService = false;
    private boolean startSOAService = false;
    private boolean startListener = false;
    private boolean startWorkorderDefineService = false;
    private int listenFrequency = 0;
    private boolean startRecycleServer = false;
    private boolean checkUnique = false;
    private boolean checkEmpty = false;
    private boolean checkLength = false;
    private boolean checkNumber = false;
    private String systemName = "";
    private String sysAdminRole = "";//系统管理员
    private String cityAdminRole = "";//地市管理员
    private String provAssetsMgr = "";//全省资产管理员
    private String compAssetsMgr = "";//公司资产管理员
    private String deptAssetsMgr = "";//部门资产管理员
    private String mtlAssetsMgr = "";//专业资产管理员

    private int provinceOrgId;
    private String proCompanyCode = "";
    private int tdProvinceOrgId = 0;//TD吽鼠侗郪眽ID
    private String tdProCompanyCode = "";//TD吽鼠侗測鎢
    private String proCompanyName = "";
    private String provinceCode = "";
    private String tdProvinceCode = "";
    private String loginImage = "";
    private String topImage = "";
    private boolean rcvProcEnabled = false;//是否启用调拨资产接收审批流程
    private boolean subProcEnabled = false;//是否启用资产减值审批流程
    private boolean freeProcEnabled = false;//是否启用资产闲置审批流程
    private boolean assignArchiveUser = false;//创建工单(含网络工单和资产盘点工单)时是否指定归档人

    private String initRunner = "";//根据其他参数动态运行的类
    private String envName = "";//服务器环境显示名称，用于区分测试环境还是正式环境等避免用户误操作

    private static List ignoreFields = null;

    static {
        ignoreFields = new ArrayList();//以下字段可以不必配置在WEB.XML文件中
        ignoreFields.add("startListener");
        ignoreFields.add("listenFrequency");
        ignoreFields.add("startRecycleServer");
        ignoreFields.add("checkUnique");
        ignoreFields.add("checkEmpty");
        ignoreFields.add("checkNumber");
        ignoreFields.add("subProcEnabled");
        ignoreFields.add("subProcEnabled");
        ignoreFields.add("freeProcEnabled");
        ignoreFields.add("envName");
    }

    /**
     * 功能：获取地市管理员角色名称
     * @return String
     */
    public String getCityAdminRole() {
        return cityAdminRole;
    }

    /**
     * 功能：获取配置文件监听频率
     * @return int
     */
    public int getListenFrequency() {
        return listenFrequency;
    }

    /**
     * 功能：连接池是否加载
     * @return boolean
     */
    public boolean isLoadConnPools() {
        return loadConnPools;
    }

    /**
     * 功能：消息服务是否加载
     * @return boolean
     */
    public boolean isLoadMessages() {
        return loadMessages;
    }

    /**
     * 获取省公司OU组织ID
     * @return String
     */
    public int getProvinceOrgId() {
        return provinceOrgId;
    }

    /**
     * 功能：配置文件监听器是否加载
     * @return boolean
     */
    public boolean isStartListener() {
        return startListener;
    }

    /**
     * 手机短信服务是否加载
     * @return boolean
     */
    public boolean isStartSMSService() {
        return startSMSService;
    }

    /**
     * 功能：获取系统管理员角色名称
     * @return String
     */
    public String getSysAdminRole() {
        return sysAdminRole;
    }

    /**
     * 功能：获取本应用系统的名称
     * @return String
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     * 功能：是否对数据进行空检查
     * @return boolean
     */
    public boolean isCheckEmpty() {
        return checkEmpty;
    }

    /**
     * 功能：是否对数据进行长度检查
     * 仅限于字符型字段数据
     * @return boolean
     */
    public boolean isCheckLength() {
        return checkLength;
    }

    /**
     * 功能：是否对数据进行唯一性检查
     * @return boolean
     */
    public boolean isCheckUnique() {
        return checkUnique;
    }

    /**
     * 功能：获取省公司代码
     * @return String
     */
    public String getProCompanyCode() {
        return proCompanyCode;
    }

    /**
     * 功能：获取省公司名称
     * @return String
     */
    public String getProCompanyName() {
        return proCompanyName;
    }

    /**
     * 功能：获取省代码
     * @return String
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * 功能：是否启动资源回收器：用于WebQuickQuery
     * @return boolean
     */
    public boolean isStartRecycleServer() {
        return startRecycleServer;
    }

    /**
     * 功能：是否进行数字检查
     * @return boolean
     */
    public boolean isCheckNumber() {
        return checkNumber;
    }

    /**
     * 功能：获取登陆页面背景图片
     * @return String
     */
    public String getLoginImage() {
        return loginImage;
    }

    /**
     * 功能：获取登陆成功后框架顶部背景图片
     * @return String
     */
    public String getTopImage() {
        return topImage;
    }

    public String getInitRunner() {
        return initRunner;
    }

    public String getCompAssetsMgr() {
        return compAssetsMgr;
    }

    public String getDeptAssetsMgr() {
        return deptAssetsMgr;
    }

    public String getMtlAssetsMgr() {
        return mtlAssetsMgr;
    }

    public String getProvAssetsMgr() {
        return provAssetsMgr;
    }

    public boolean isFreeProcEnabled() {
        return freeProcEnabled;
    }

    public boolean isRcvProcEnabled() {
        return rcvProcEnabled;
    }

    public boolean isSubProcEnabled() {
        return subProcEnabled;
    }

    public boolean isAssignArchiveUser() {
        return assignArchiveUser;
    }

    public String getEnvName() {
        return envName;
    }

    /**
     * 功能：设置地市管理员角色名称
     * @param cityAdminRole String
     */
    public void setCityAdminRole(String cityAdminRole) {
        this.cityAdminRole = cityAdminRole;
    }

    /**
     * 功能：设置监听器监听频率
     * @param listenFrequency int
     */
    public void setListenFrequency(int listenFrequency) {
        this.listenFrequency = listenFrequency;
    }

    /**
     * 功能：设置数据库连接池的加载性
     * @param loadConnPools boolean
     */
    public void setLoadConnPools(boolean loadConnPools) {
        this.loadConnPools = loadConnPools;
    }

    /**
     * 功能：设置消息服务的启动属性
     * @param loadMessages boolean
     */
    public void setLoadMessages(boolean loadMessages) {
        this.loadMessages = loadMessages;
    }

    /**
     * 功能：设置省公司OU组织ID
     * @param provinceOrgId String
     */
    public void setProvinceOrgId(int provinceOrgId) {
        this.provinceOrgId = provinceOrgId;
    }

    /**
     * 功能：设置监听器启动属性
     * @param startListener boolean
     */
    public void setStartListener(boolean startListener) {
        this.startListener = startListener;
    }

    /**
     * 功能：设置手机短信服务启动属性
     * @param startSMSService boolean
     */
    public void setStartSMSService(boolean startSMSService) {
        this.startSMSService = startSMSService;
    }

    /**
     * 功能：设置系统管理员角色名称
     * @param sysAdminRole String
     */
    public void setSysAdminRole(String sysAdminRole) {
        this.sysAdminRole = sysAdminRole;
    }

    /**
     * 功能：设置应用系统名称
     * @param systemName String
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    /**
     * 功能：设置是否需要进行空检查
     * @param checkEmpty boolean
     */
    public void setCheckEmpty(boolean checkEmpty) {
        this.checkEmpty = checkEmpty;
    }

    /**
     * 功能：设置是否需要进行长度检查
     * @param checkLength boolean
     */
    public void setCheckLength(boolean checkLength) {
        this.checkLength = checkLength;
    }

    /**
     * 功能：设置是否需要进行唯一性检查
     * @param checkUnique boolean
     */
    public void setCheckUnique(boolean checkUnique) {
        this.checkUnique = checkUnique;
    }

    /**
     * 功能：设置省公司代码
     * @param proCompanyCode String
     */
    public void setProCompanyCode(String proCompanyCode) {
        this.proCompanyCode = proCompanyCode;
    }

    /**
     * 功能：设置省公司名称
     * @param proCompanyName String
     */
    public void setProCompanyName(String proCompanyName) {
        this.proCompanyName = proCompanyName;
    }

    /**
     * 功能：设置省代码
     * @param provinceCode String
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    /**
     * 功能：设置是否启动资源回收器
     * @param startRecycleServer boolean
     */
    public void setStartRecycleServer(boolean startRecycleServer) {
        this.startRecycleServer = startRecycleServer;
    }

    /**
     * 功能：设置是否进行数字检查
     * @param checkNumber boolean
     */
    public void setCheckNumber(boolean checkNumber) {
        this.checkNumber = checkNumber;
    }

    public void setLoginImage(String loginImage) {
        this.loginImage = loginImage;
    }

    public void setTopImage(String topImage) {
        this.topImage = topImage;
    }

    public void setInitRunner(String initRunner) {
        this.initRunner = initRunner;
    }

    public void setCompAssetsMgr(String compAssetsMgr) {
        this.compAssetsMgr = compAssetsMgr;
    }

    public void setDeptAssetsMgr(String deptAssetsMgr) {
        this.deptAssetsMgr = deptAssetsMgr;
    }

    public void setMtlAssetsMgr(String mtlAssetsMgr) {
        this.mtlAssetsMgr = mtlAssetsMgr;
    }

    public void setProvAssetsMgr(String provAssetsMgr) {
        this.provAssetsMgr = provAssetsMgr;
    }

    public void setFreeProcEnabled(boolean freeProcEnabled) {
        this.freeProcEnabled = freeProcEnabled;
    }

    public void setRcvProcEnabled(boolean rcvProcEnabled) {
        this.rcvProcEnabled = rcvProcEnabled;
    }

    public void setSubProcEnabled(boolean subProcEnabled) {
        this.subProcEnabled = subProcEnabled;
    }

    public void setAssignArchiveUser(boolean assignArchiveUser) {
        this.assignArchiveUser = assignArchiveUser;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public List getIgnoreFields() {
        return ignoreFields;
    }

    /**
     * SOA諉諳督昢岆瘁樓婥
     * @return boolean
     */
    public boolean isStartSOAService() {
        return startSOAService;
    }

    /**
     * 扢离SOA諉諳?雄扽俶
     * @param startSOAService
     */
    public void setStartSOAService(boolean startSOAService) {
        this.startSOAService = startSOAService;
    }

    public int getTdProvinceOrgId() {
        return tdProvinceOrgId;
    }

    public void setTdProvinceOrgId(int tdProvinceOrgId) {
        this.tdProvinceOrgId = tdProvinceOrgId;
    }

    public String getTdProCompanyCode() {
        return tdProCompanyCode;
    }

    public void setTdProCompanyCode(String tdProCompanyCode) {
        this.tdProCompanyCode = tdProCompanyCode;
    }

    public String getTdProvinceCode() {
        return tdProvinceCode;
    }

    public void setTdProvinceCode(String tdProvinceCode) {
        this.tdProvinceCode = tdProvinceCode;
    }

    public boolean isStartWorkorderDefineService() {
        return startWorkorderDefineService;
    }

    public void setStartWorkorderDefineService(boolean startWorkorderDefineService) {
        this.startWorkorderDefineService = startWorkorderDefineService;
    }
}
