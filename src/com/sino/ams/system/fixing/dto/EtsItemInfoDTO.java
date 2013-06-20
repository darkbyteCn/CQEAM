package com.sino.ams.system.fixing.dto;


import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;
import com.sino.ams.appbase.dto.AMSBaseDTO;
import com.sino.ams.bean.SyBaseSQLUtil;

/**
 * <p>Title: 标签号信息(EAM) EtsItemInfo</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class EtsItemInfoDTO extends AMSBaseDTO {

	private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;
	private String  countyCode = "";
	private String diffTypeCode = ""; 
	private String systemid;
    private String barcode = "";
    private String faBarcode = "";
    private String vendorBarcode = "";
    private String vendorId;
    private String itemQty = "";
    private SimpleCalendar disableDate = null;
    private String remark = "";
    private String belongto = "";
    private String itemCode = "";
    private String years = "";
    private String projectid;
    private String itemStatus = "";
    private String attribute1 = "";
    private String attribute2 = "";
    private String sendtomisFlag = "";
    private String misItemname = "";
    private String misItemspec = "";
    private int assetId;
    private String addressId = "";
    private String addressNo = "";
    
    private String itemName = "";
    private String vendorName = "";
    private String itemCategory = "";
    private String itemSpec = "";
    private String userDate = "";
    private String age = "";
    private String address = "";
    private String projectName = "";
    private String prjName = "";
    private String workorderObjectNo = "";
    private String workorderObjectName = "";
    private String prjId;
    private String barcode1 = "";
    private String company = "";
    private String itemCategoryDesc = "";
    private String disable = "";
    private String systemId;
    private String companyCode = "";

    private String parentBarcode = "";
    private String boxNo = "";
    private String netUnit = "";
   
    private String key = "";
    private String workorderObjectLocation = "";
    private String workorderObjectCode = "";
    private String financeProp = "";

    private String responsibilityUser = ""; //责任人
    private String userName = ""; //责任人
    private String responsibilityUserName = "";//责任人
    private String responsibilityDept = "";//责任部门
    private String deptName = ""; //使用部门名称
    private String responsibilityDeptName = "";//责任部门
    private String maintainUser = "" ;//维护人员（代维人员）
    private String maintainUserName = "";//维护人员（代维人员）
    private String maintainDept = "";//使用部门
    private String maintainDeptName = "";
    private String financePropName = "";
	private String newResponsibilityUser = "";//责任人
	private String newResponsibilityDept = "";//责任部门
	private String newMaintainUser = "";//维护人员（代维人员）
	private String newAddressId = "";

    //---陕西新增
    String manufacturerId = "" ; //厂商ID
    String manufacturerName = "";//厂商
    String isShare = "";//是否共享设备
    String shareOption="";//共享下拉框
    String contentCode = ""; //资产目录代码
    String contentName = ""; //资产目录名称
    String power = "";//额定功率
    String lneId = ""; //逻辑网络元素属性
    String cexId = ""; //投资分类属性
    String opeId = ""; //业务平台属性
    String nleId = ""; //网络层次属性
    String snId = ""; //支撑网设备类型
    String shareStatus="";//共享状态
    private String dzyhAddress = "";//山西低值易耗地点

    private String financePropOpt = "";
    private String itemUnit = "";
    private String discardType = "";
    private String dealType = "";

    private String lneCode = "";
    private String cexCode = "";
    private String opeCode = "";
    private String nleCode = "";
    private String lneName = "";
    private String cexName = "";
    private String opeName = "";
    private String nleName = "";
    private String contentCodeBar = "";

    private String logNetEle = "";
    private String investCatName = "";
    private String snName = "";
    private String check = "";

    private String searchType = ""; 
       
    private String orderCategory = "";
    private String orderDtlUrl = "";
    private String oldContentCode = "";
    
    public String getOldContentCode() {
		return oldContentCode;
	}

	public void setOldContentCode(String oldContentCode) {
		this.oldContentCode = oldContentCode;
	}

	public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }


    public EtsItemInfoDTO() {
        this.disableDate = new SimpleCalendar();
        setPrimaryKeyName("systemid");
    }
    

    public String getLneCode() {
        return lneCode;
    }

    public void setLneCode(String lneCode) {
        this.lneCode = lneCode;
    }

    public String getCexCode() {
        return cexCode;
    }

    public void setCexCode(String cexCode) {
        this.cexCode = cexCode;
    }

    public String getOpeCode() {
        return opeCode;
    }

    public void setOpeCode(String opeCode) {
        this.opeCode = opeCode;
    }

    public String getNleCode() {
        return nleCode;
    }

    public void setNleCode(String nleCode) {
        this.nleCode = nleCode;
    }

    public String getCexName() {
        return cexName;
    }

    public void setCexName(String cexName) {
        this.cexName = cexName;
    }

    public String getNleName() {
        return nleName;
    }

    public void setNleName(String nleName) {
        this.nleName = nleName;
    }

    public String getContentCodeBar() {
        return contentCodeBar;
    }

    public void setContentCodeBar(String contentCodeBar) {
        this.contentCodeBar = contentCodeBar;
    }

    public String getSnId() {
        return snId;
    }

    public void setSnId(String snId) {
        this.snId = snId;
    }

    public String getLogNetEle() {
        return logNetEle;
    }

    public void setLogNetEle(String logNetEle) {
        this.logNetEle = logNetEle;
    }

    public String getInvestCatName() {
        return investCatName;
    }

    public void setInvestCatName(String investCatName) {
        this.investCatName = investCatName;
    }

    public String getOpeName() {
        return opeName;
    }

    public void setOpeName(String opeName) {
        this.opeName = opeName;
    }

    public String getLneName() {
        return lneName;
    }

    public void setLneName(String lneName) {
        this.lneName = lneName;
    }

    public String getSnName() {
        return snName;
    }

    public void setSnName(String snName) {
        this.snName = snName;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getDiscardType() {
        return discardType;
    }

    public void setDiscardType(String discardType) {
        this.discardType = discardType;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getFinancePropOpt() {
        return financePropOpt;
    }

    public void setFinancePropOpt(String financePropOpt) {
        this.financePropOpt = financePropOpt;
    }

    public String getDzyhAddress() {
        return dzyhAddress;
    }

    public void setDzyhAddress(String dzyhAddress) {
        this.dzyhAddress = dzyhAddress;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getDisable() {
        return disable;
    }

    public void setDisable(String disable) {
        this.disable = disable;
    }

    public String getItemCategoryDesc() {
        return itemCategoryDesc;
    }

    public void setItemCategoryDesc(String itemCategoryDesc) {
        this.itemCategoryDesc = itemCategoryDesc;
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBarcode1() {
        return barcode1;
    }

    public void setBarcode1(String barcode1) {
        this.barcode1 = barcode1;
    }

    public String  getPrjId() {
        return prjId;
    }

    public void setPrjId(String  prjId) {
        this.prjId = prjId;
    }

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }

    public String getPrjName() {
        return prjName;
    }

    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 系统ID(序列号)
     * @param systemid String
     */
    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 标签号
     * @param barcode String
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 对应MIS固定资产条码
     * @param faBarcode String
     */
    public void setFaBarcode(String faBarcode) {
        this.faBarcode = faBarcode;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 对应厂商条码
     * @param vendorBarcode String
     */
    public void setVendorBarcode(String vendorBarcode) {
        this.vendorBarcode = vendorBarcode;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 供应商
     * @param vendorId String
     */
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 设备数量
     * @param itemQty String
     */
    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 无效日期
     * @param disableDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setDisableDate(String disableDate) throws CalendarException {
        this.disableDate.setCalendarValue(disableDate);
    }

    /**
     * 功能：设置标签号信息(EAM)属性 备注
     * @param remark String
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 所属机柜
     * @param belongto String
     */
    public void setBelongto(String belongto) {
        this.belongto = belongto;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 分类编码
     * @param itemCode String
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 服务年限
     * @param years String
     */
    public void setYears(String years) {
        this.years = years;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 工程ID
     * @param projectid String
     */
    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 设备状态
     * @param itemStatus String
     */
    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 null
     * @param attribute1 String
     */
    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 null
     * @param attribute2 String
     */
    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 同步到MIS标志
     * @param sendtomisFlag String
     */
    public void setSendtomisFlag(String sendtomisFlag) {
        this.sendtomisFlag = sendtomisFlag;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 MIS设备名称
     * @param misItemname String
     */
    public void setMisItemname(String misItemname) {
        this.misItemname = misItemname;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 MIS规格型号
     * @param misItemspec String
     */
    public void setMisItemspec(String misItemspec) {
        this.misItemspec = misItemspec;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 所属资产ID
     * @param assetId String
     */
    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 资产地点ID
     * @param addressId String
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 组织ID
     * @param organizationId String
     */
    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }


    /**
     * 功能：获取标签号信息(EAM)属性 系统ID(序列号)
     * @return String
     */
    public String getSystemid() {
        return this.systemid;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 标签号
     * @return String
     */
    public String getBarcode() {
        return this.barcode;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 对应MIS固定资产条码
     * @return String
     */
    public String getFaBarcode() {
        return this.faBarcode;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 对应厂商条码
     * @return String
     */
    public String getVendorBarcode() {
        return this.vendorBarcode;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 供应商
     * @return String
     */
    public String getVendorId() {
        return this.vendorId;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 设备数量
     * @return String
     */
    public String getItemQty() {
        return this.itemQty;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 无效日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getDisableDate() {
        return this.disableDate;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 备注
     * @return String
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 所属机柜
     * @return String
     */
    public String getBelongto() {
        return this.belongto;
    }


    /**
     * 功能：获取标签号信息(EAM)属性 分类编码
     * @return String
     */
    public String getItemCode() {
        return this.itemCode;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 服务年限
     * @return String
     */
    public String getYears() {
        return this.years;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 工程ID
     * @return String
     */
    public String getProjectid() {
        return this.projectid;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 设备状态
     * @return String
     */
    public String getItemStatus() {
        return this.itemStatus;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 null
     * @return String
     */
    public String getAttribute1() {
        return this.attribute1;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 null
     * @return String
     */
    public String getAttribute2() {
        return this.attribute2;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 同步到MIS标志
     * @return String
     */
    public String getSendtomisFlag() {
        return this.sendtomisFlag;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 MIS设备名称
     * @return String
     */
    public String getMisItemname() {
        return this.misItemname;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 MIS规格型号
     * @return String
     */
    public String getMisItemspec() {
        return this.misItemspec;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 所属资产ID
     * @return String
     */
    public int getAssetId() {
        return this.assetId;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 资产地点ID
     * @return String
     */
    public String getAddressId() {
        return this.addressId;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 组织ID
     * @return String
     */
    public int getOrganizationId() {
        return this.organizationId;
    }


    public String getParentBarcode() {
        return parentBarcode;
    }

    public void setParentBarcode(String parentBarcode) {
        this.parentBarcode = parentBarcode;
    }


    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    public String getNetUnit() {
        return netUnit;
    }

    public void setNetUnit(String netUnit) {
        this.netUnit = netUnit;
    }

    public String  getCountyCode() {
        return countyCode;
    }

    public String getDiffTypeCode() {
		return diffTypeCode;
	}

	public void setDiffTypeCode(String diffTypeCode) {
		this.diffTypeCode = diffTypeCode;
	}
    
    public void setCountyCode(String  countyCode) {
        this.countyCode = countyCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getWorkorderObjectNo() {
        return workorderObjectNo;
    }

    public String  getMaintainUser() {
        return maintainUser;
    }

    public String getMaintainUserName() {
        return maintainUserName;
    }

    public String getResponsibilityDept() {
        return responsibilityDept;
    }

    public String getResponsibilityDeptName() {
        return responsibilityDeptName;
    }

    public String getResponsibilityUser() {
        return responsibilityUser;
    }

    public String getResponsibilityUserName() {
        return responsibilityUserName;
    }

    public String getAddressNo() {
        return addressNo;
    }

    public void setWorkorderObjectNo(String workorderObjectNo) {
        this.workorderObjectNo = workorderObjectNo;
    }

    public void setMaintainUser(String  maintainUser) {
        this.maintainUser = maintainUser;
    }

    public void setMaintainUserName(String maintainUserName) {
        this.maintainUserName = maintainUserName;
    }

    public void setResponsibilityUserName(String responsibilityUserName) {
        this.responsibilityUserName = responsibilityUserName;
    }

    public void setResponsibilityUser(String responsibilityUser) {
        this.responsibilityUser = responsibilityUser;
    }

    public void setResponsibilityDeptName(String responsibilityDeptName) {
        this.responsibilityDeptName = responsibilityDeptName;
    }

    public void setResponsibilityDept(String responsibilityDept) {
        this.responsibilityDept = responsibilityDept;
    }

    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo;
    }

    public String getWorkorderObjectLocation() {
        return workorderObjectLocation;
    }

    public void setWorkorderObjectLocation(String workorderObjectLocation) {
        this.workorderObjectLocation = workorderObjectLocation;
    }

    public String getWorkorderObjectCode() {
        return workorderObjectCode;
    }

    public void setWorkorderObjectCode(String workorderObjectCode) {
        this.workorderObjectCode = workorderObjectCode;
    }

    public String getFinanceProp() {
        return financeProp;
    }

    public String getMaintainDept() {
        return maintainDept;
    }

    public void setFinanceProp(String financeProp) {
        this.financeProp = financeProp;
    }

    public void setMaintainDept(String maintainDept) {
        this.maintainDept = maintainDept;
    }

    public String getMaintainDeptName() {
        return maintainDeptName;
    }

    public void setMaintainDeptName(String maintainDeptName) {
        this.maintainDeptName = maintainDeptName;
    }

    public String getFinancePropName() {
        return financePropName;
    }

    public void setFinancePropName(String financePropName) {
        this.financePropName = financePropName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getShare() {
        return isShare;
    }

    public void setShare(String share) {
        isShare = share;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getLneId() {
        return lneId;
    }

    public void setLneId(String lneId) {
        this.lneId = lneId;
    }

    public String getCexId() {
        return cexId;
    }

    public void setCexId(String cexId) {
        this.cexId = cexId;
    }

    public String getOpeId() {
        return opeId;
    }

    public void setOpeId(String opeId) {
        this.opeId = opeId;
    }

    public String getNleId() {
        return nleId;
    }

    public void setNleId(String nleId) {
        this.nleId = nleId;
    }

    public String getShareOption() {
        return shareOption;
    }

    public void setShareOption(String shareOption) {
        this.shareOption = shareOption;
    }

    public String getShareStatus() {
        return shareStatus;
    }

    public void setShareStatus(String shareStatus) {
        this.shareStatus = shareStatus;
    }

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}

	public String getOrderDtlUrl() {
		return orderDtlUrl;
	}

	public void setOrderDtlUrl(String orderDtlUrl) {
		this.orderDtlUrl = orderDtlUrl;
	}

	public String getNewResponsibilityUser() {
		return newResponsibilityUser;
	}

	public void setNewResponsibilityUser(String newResponsibilityUser) {
		this.newResponsibilityUser = newResponsibilityUser;
	}

	public String getNewResponsibilityDept() {
		return newResponsibilityDept;
	}

	public void setNewResponsibilityDept(String newResponsibilityDept) {
		this.newResponsibilityDept = newResponsibilityDept;
	}

	public String getNewMaintainUser() {
		return newMaintainUser;
	}

	public void setNewMaintainUser(String newMaintainUser) {
		this.newMaintainUser = newMaintainUser;
	}

	public String getNewAddressId() {
		return newAddressId;
	}

	public void setNewAddressId(String newAddressId) {
		this.newAddressId = newAddressId;
	}
}
