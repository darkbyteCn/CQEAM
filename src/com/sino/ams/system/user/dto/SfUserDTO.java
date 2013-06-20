package com.sino.ams.system.user.dto;

import java.util.List;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

/**
 * <p>Title: SfUser</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class SfUserDTO extends SfUserBaseDTO {
	private String pendNumber="0";
	private String userType="";
	private String useType="";
    public String getPendNumber() {
		return pendNumber;
	}

	public void setPendNumber(String pendNumber) {
		this.pendNumber = pendNumber;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	private int userId;
    private String loginName = "";
    private String password = "";
    private String username = "";
    private String workno = "";
    private String officeTel = "";
    private String fax = "";
    private String mobilePhone = "";
    private String email = "";
    private int displayOrder = 0;
    private SimpleCalendar passwordDate = null;
    private SimpleCalendar passwordOverdue = null;
    private int organizationId = -1;
    private int matchOrganizationId = -1;
    private String isInner = "";
    private SimpleCalendar creationDate = null;
    private int createdBy = -1;
    private SimpleCalendar lastUpdateDate = null;
    private int lastUpdateBy = -1;
    private String company = "";
    private String companyCode = "";
    private String employeeNumber = "";
    private String srcFrom = "";
    private String maintainCompany = "";
    private String isMaintainUser = "";
    private String oaName = "";

    private DTOSet userRights = null;
    private boolean sysAdmin = false;
    private boolean cityAdmin = false;
    private boolean provinceUser = false;

    private int currGroupId;//当前组别
    private String currGroupName = "";//当前组别
    private List currRoleIds = null;//当前组别下所具有的所有角色
    private List authorizedURLs = null;//当前组别能访问的URL资源。
    private DTOSet userGroups = null;  //用户所有组别DTOSet

    private boolean isToAddr = true;      // 明细到地点
    private boolean isToNetUnit = false;   // 明细到网元
    private boolean isToBox = false;      //  明细到机柜

    private String userNumber = "";
    private boolean validUser = false;

    private boolean dptAssetsManager = false;
    private boolean comAssetsManager = false;
    private boolean provAssetsManager = false;
    private boolean mtlAssetsManager = false;//是否为专业资产管理员
    private boolean dptMgr = false;//是否部门经理
    private DTOSet priviDeptCodes = null;
    private String tmpInvCode = "";//在途库
    private String tmpAddressId = "";//在途库对应的addressId

    private String employeeId = "";//对应MIS的员工ID
    private String deptCode = "";//对应MIS的成本中心代码
    private String deptName = "";//对应MIS的成本中心名称
    private String transferType = "";
    private String bookTypeCode = "";//资产账簿代码
    private String bookTypeName = "";//资产账簿名称
    private DTOSet transConfigs = null;
    private String mtlMgrProps = "";//专业资产管理员属性
    private String roleId = "";
    private String maintainCompanyName = "";
    private String isTd = "";   //是否为TD用户
    private String isTt = "";   //是否为TD用户
    private String deptCategory = "";  //员工隶属部门分类
    private String provinceCode = "";  //省代码
    private String tdProvinceCode = "";  //省代码

    private String isSms = ""; //是否需要短信提醒
    
    private String logonTime = "";
    

    public String getDeptCategory() {
        return deptCategory;
    }

    public void setDeptCategory(String deptCategory) {
        this.deptCategory = deptCategory;
    }

    public String getIsSms() {
		return isSms;
	}

	public void setIsSms(String isSms) {
		this.isSms = isSms;
	}

	public String getIsTd() {
        return isTd;
    }

    public void setIsTd(String isTd) {
        this.isTd = isTd;
    }

    public SfUserDTO() {
        super();
        this.passwordOverdue = new SimpleCalendar();
    }

    public boolean isMtlAssetsManager() {
        return mtlAssetsManager;
    }

    public void setMtlAssetsManager(boolean mtlAssetsManager) {
        this.mtlAssetsManager = mtlAssetsManager;
    }

    public boolean isDptMgr() {
        return dptMgr;
    }

    public void setDptMgr(boolean dptMgr) {
        this.dptMgr = dptMgr;
    }

    public String getMaintainCompany() {
        return maintainCompany;
    }

    public void setMaintainCompany(String maintainCompany) {
        this.maintainCompany = maintainCompany;
    }

    public String getIsMaintainUser() {
        return isMaintainUser;
    }

    public boolean isMaintainUser() {
        return (isMaintainUser.equals("Y"));
    }

    public void setIsMaintainUser(String isMaintainUser) {
        this.isMaintainUser = isMaintainUser;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWorkno(String workno) {
        this.workno = workno;
    }

    public void setOfficeTel(String officetel) {
        this.officeTel = officetel;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setMobilePhone(String movetel) {
        this.mobilePhone = movetel;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setDisplayOrder(int sortno) {
        this.displayOrder = sortno;
    }

    public void setPasswordDate(String passwordDate) throws CalendarException {
        if (!StrUtil.isEmpty(passwordDate)) {
            this.passwordDate = new SimpleCalendar(passwordDate);
        }
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public void setIsInner(String isInner) {
        this.isInner = isInner;
    }

    public void setCreationDate(String creationDate) throws CalendarException {
        if (!StrUtil.isEmpty(creationDate)) {
            this.creationDate = new SimpleCalendar(creationDate);
        }
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        if (!StrUtil.isEmpty(lastUpdateDate)) {
            this.lastUpdateDate = new SimpleCalendar(lastUpdateDate);
        }
    }

    public void setLastUpdateBy(int lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public void setUserRights(DTOSet userRights) {
        this.userRights = userRights;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public void setCurrGroupId(int currGroupId) {
        this.currGroupId = currGroupId;
    }

    public void setAuthorizedURLs(List authorizedURLs) {
        this.authorizedURLs = authorizedURLs;
    }

    public void setCurrRoleIds(List currRoleIds) {
        this.currRoleIds = currRoleIds;
    }

    public void setCurrGroupName(String currGroupName) {
        this.currGroupName = currGroupName;
    }

    public void setSysAdmin(boolean sysAdmin) {
        this.sysAdmin = sysAdmin;
    }

    public void setProvinceUser(boolean provinceUser) {
        this.provinceUser = provinceUser;
    }

    public void setCityAdmin(boolean cityAdmin) {
        this.cityAdmin = cityAdmin;
    }

    public void setInner(String inner) {
        isInner = inner;
    }

    public void setUserGroups(DTOSet userGroups) {
        this.userGroups = userGroups;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getWorkno() {
        return this.workno;
    }

    public String getOfficeTel() {
        return this.officeTel;
    }

    public String getFax() {
        return this.fax;
    }

    public String getMobilePhone() {
        return this.mobilePhone;
    }


    public String getEmail() {
        return this.email;
    }

    public int getDisplayOrder() {
        return this.displayOrder;
    }

    public SimpleCalendar getPasswordDate() {
        return this.passwordDate;
    }

    public int getOrganizationId() {
        return this.organizationId;
    }

    public String getIsInner() {
        return this.isInner;
    }

    public SimpleCalendar getCreationDate() {
        return this.creationDate;
    }

    public int getCreatedBy() {
        return this.createdBy;
    }

    public SimpleCalendar getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    public int getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    public String getCompany() {
        return company;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public int getCurrGroupId() {
        return currGroupId;
    }

    public List getCurrRoleIds() {
        return currRoleIds;
    }

    public List getAuthorizedURLs() {
        return authorizedURLs;
    }

    public String getCurrGroupName() {
        return currGroupName;
    }

    public boolean isSysAdmin() {
        return sysAdmin;
    }

    public boolean isProvinceUser() {
        return provinceUser;
    }

    public boolean isCityAdmin() {
        return cityAdmin;
    }

    public DTOSet getUserRight() {
        return userRights;
    }

    public String getInner() {
        return isInner;
    }

    public DTOSet getUserGroups() {
        return userGroups;
    }

    public String getSrcFrom() {
        return srcFrom;
    }

    public boolean isToAddr() {
        return isToAddr;
    }

    public void setIsToAddr(boolean isToAddr) {
        this.isToAddr = isToAddr;
    }

    public boolean isToNetUnit() {
        return isToNetUnit;
    }

    public void setIsToNetUnit(boolean isToNetUnit) {
        this.isToNetUnit = isToNetUnit;
    }

    public boolean isToBox() {
        return isToBox;
    }

    public void setIsToBox(boolean isToBox) {
        this.isToBox = isToBox;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public boolean isValidUser() {
        return validUser;
    }

    public boolean isComAssetsManager() {
        return comAssetsManager;
    }

    public DTOSet getPriviDeptCodes() {
        return priviDeptCodes;
    }

    public boolean isProvAssetsManager() {

        return provAssetsManager;
    }

    public String getTmpInvCode() {
        return tmpInvCode;
    }

    public String getTmpAddressId() {
        return tmpAddressId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getTransferType() {
        return transferType;
    }

    public boolean isDptAssetsManager() {
        return dptAssetsManager;
    }

    public String getBookTypeCode() {
        return bookTypeCode;
    }

    public String getBookTypeName() {
        return bookTypeName;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public void setSrcFrom(String srcFrom) {
        this.srcFrom = srcFrom;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }

    public void setComAssetsManager(boolean comAssetsManager) {

        this.comAssetsManager = comAssetsManager;
    }

    public void setPriviDeptCodes(DTOSet priviDeptCodes) {
        this.priviDeptCodes = priviDeptCodes;
    }

    public void setProvAssetsManager(boolean provAssetsManager) {

        this.provAssetsManager = provAssetsManager;
    }

    public void setTmpInvCode(String tmpInvCode) {
        this.tmpInvCode = tmpInvCode;
    }

    public void setTmpAddressId(String tmpAddressId) {
        this.tmpAddressId = tmpAddressId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public void setDptAssetsManager(boolean dptAssetsManager) {
        this.dptAssetsManager = dptAssetsManager;
    }

    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    public void setTransConfigs(DTOSet transConfigs) {
        this.transConfigs = transConfigs;
    }

    public void setMtlMgrProps(String mtlMgrProps) {
        this.mtlMgrProps = mtlMgrProps;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setMaintainCompanyName(String maintainCompanyName) {
        this.maintainCompanyName = maintainCompanyName;
    }

    public DTOSet getTransConfigs() {
        return transConfigs;
    }

    public String getMtlMgrProps() {
        return mtlMgrProps;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getMaintainCompanyName() {
        return maintainCompanyName;
    }

    public SimpleCalendar getPasswordOverdue() {
        return passwordOverdue;
    }

    public void setPasswordOverdue(String passwordOverdue) throws CalendarException {
        this.passwordOverdue.setCalendarValue(passwordOverdue);
    }

    public String getOaName() {
        return oaName;
    }

    public void setOaName(String oaName) {
        this.oaName = oaName;
    }

    public int getMatchOrganizationId() {
        return matchOrganizationId;
    }

    public void setMatchOrganizationId(int matchOrganizationId) {
        this.matchOrganizationId = matchOrganizationId;
    }

	public String getLogonTime() {
		return logonTime;
	}

	public void setLogonTime(String logonTime) {
		this.logonTime = logonTime;
	}

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getTdProvinceCode() {
        return tdProvinceCode;
    }

    public void setTdProvinceCode(String tdProvinceCode) {
        this.tdProvinceCode = tdProvinceCode;
    }

    public String getIsTt() {
        return isTt;
    }

    public void setIsTt(String tt) {
        isTt = tt;
    }

    public static void main(String[] args) throws CalendarException {
        SfUserDTO user = new SfUserDTO();
        user.setEndDate("2014-03-20 00:00:00");
    }
}