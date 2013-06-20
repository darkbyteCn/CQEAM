package com.sino.ams.system.specialty.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CalendarUtililyDTO;
import com.sino.base.exception.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-12-20
 * Time: 11:53:42
 * To change this template use File | Settings | File Templates.
 */
public class OtherDTO  extends CalendarUtililyDTO {

    private String systemid = "";
    private String barcode = "";
    private String faBarcode = "";
    private String vendorBarcode = "";
    private int vendorId;
    private String itemQty = "";
    private SimpleCalendar disableDate = null;
    private String remark = "";
    private String belongto = "";
    private String itemCode = "";
    private int years;
    private String projectid = "";
    private String itemStatus = "";
    private String attribute1 = "";
    private String attribute2 = "";
    private String sendtomisFlag = "";
    private String misItemname = "";
    private String misItemspec = "";
    private int createdBy;
    private SimpleCalendar lastUpdateDate = null;
    private int lastUpdateBy;
    private String assetId = "";
    private String addressId = "";
    private int organizationId;
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
    private String prjId = "";
    private String barcode1 = "";
    private String company = "";
    private String itemCategoryDesc = "";
    private String disable = "";
    private String systemId = "";
    private String companyCode = "";

    private String parentBarcode = "";
    private String boxNo = "";
    private String netUnit = "";
    private String countyCode = "";
    private String key = "";

	private String responsibilityUser = "";//责任人
	private String responsibilityUserName = "";//责任人
	private String responsibilityDept = "";//责任部门
	private String responsibilityDeptName = "";//责任部门
	private String maintainUser = "";//维护人员（代维人员）
	private String maintainUserName = "";//维护人员（代维人员）

    private String manufacturerId = "";//厂商ID
    private String manufacturerCode = "";//厂商CODE
    private String manufacturerName = "";//厂商名称
    private String isShare = "";//是否共享
    private String contentCode = "";//目录代码
    private String contentName = "";//目录名称
    private String power = "";//功率

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    public String getShare() {
        return isShare;
    }

    public void setShare(String share) {
        isShare = share;
    }

    public OtherDTO() {
        this.lastUpdateDate = new SimpleCalendar();
        this.disableDate = new SimpleCalendar();
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

    public String getPrjId() {
        return prjId;
    }

    public void setPrjId(String prjId) {
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
    public void setVendorId(int vendorId) {
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
     * @throws com.sino.base.exception.CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
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
    public void setYears(int years) {
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
     * 功能：设置标签号信息(EAM)属性 创建人
     * @param createdBy String
     */
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 上次修改日期
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        this.lastUpdateDate.setCalendarValue(lastUpdateDate);
    }

    /**
     * 功能：设置标签号信息(EAM)属性 上次修改人
     * @param lastUpdateBy String
     */
    public void setLastUpdateBy(int lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * 功能：设置标签号信息(EAM)属性 所属资产ID
     * @param assetId String
     */
    public void setAssetId(String assetId) {
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
    public int getVendorId() {
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
    public int getYears() {
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
     * 功能：获取标签号信息(EAM)属性 创建人
     * @return String
     */
    public int getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 上次修改日期
     * @return SimpleCalendar
     * @throws com.sino.base.exception.CalendarException
     *
     */
    public SimpleCalendar getLastUpdateDate() throws CalendarException {
        lastUpdateDate.setCalPattern(getCalPattern());
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 上次修改人
     * @return String
     */
    public int getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    /**
     * 功能：获取标签号信息(EAM)属性 所属资产ID
     * @return String
     */
    public String getAssetId() {
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

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
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

	public String getMaintainUser() {
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

	public void setWorkorderObjectNo(String workorderObjectNo) {
        this.workorderObjectNo = workorderObjectNo;
    }

	public void setMaintainUser(String maintainUser) {
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
}
