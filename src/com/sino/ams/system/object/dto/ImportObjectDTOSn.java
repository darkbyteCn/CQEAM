package com.sino.ams.system.object.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-6-1
 * Time: 17:47:35
 * To change this template use File | Settings | File Templates.
 */
public class ImportObjectDTOSn extends CommonRecordDTO {

	private String workorderObjectNo = "";
	private String workorderObjectCode = ""; //仓库代码
	private String workorderObjectName = ""; //仓库名称
	private String workorderObjectLocation = "";
	private int organizationId = -1;
	private String countyCode = "";  //所属成本中心
	private SimpleCalendar disableDate = null;
	private String remark = "";
	private String objectCategory = ""; //对象地点定义的专业分类 ， 对应仓库类型选项卡
	private String businessCategory = ""; //对象地点定义的业务分类 ，对应业务类型选项卡
	private String objectCategoryName = "";
	private String isall = "";
	private String isTempAddr = "";
	private String projectId = "";
	//-----------------------------------------
	private String countyName = "";
	private String projectName = "";
	private String categoryName = "";
	private String transferType = "";
	private String organizationOption = "";
	private String countyOption = "";
	private String objCategoryOption = "";
	private String deptCode = ""; //所属成本中心
	private String deptName = "";
	private String locationCode = ""; //所在地点代码
    private String locCategoryDesc = ""; //地点专业
	private String enabled = "";
    private String areaType = "";//区域类型
    private String areaTypeOption = "";//区域类型
    private String isTd = ""; //是否TD地点
    private String companyCode = "";

    private String cityOption = "";
    private String city = "";
    private String county = "";
    private String countyOptions ="";
    private String location = "";
    private String cityCode = "";
    private String countyCode2 = "";

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountyCode2() {
        return countyCode2;
    }

    public void setCountyCode2(String countyCode2) {
        this.countyCode2 = countyCode2;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    private boolean areaTypeIsNull = false;  //行政区划是否为空

    private String contentBlurred = "";//是否包含模糊查询

    public String getContentBlurred() {
        return contentBlurred;
    }

    public void setContentBlurred(String contentBlurred) {
        this.contentBlurred = contentBlurred;
    }

    public String getIsTdOption() {
		StringBuffer option = new StringBuffer();
		option.append("<option value=\"\">-请选择-</option>");
		option.append("<option value=\"Y\"");
		if(isTd.equals("Y")){
			option.append(" selected");
		}
		option.append(">是</option>");
		option.append("<option value=\"N\"");
		if(isTd.equals("N")){
			option.append(" selected");
		}
		option.append(">否</option>");
		return option.toString();
	}


	public String getIsTd() {
		return isTd;
	}

	public void setIsTd(String isTd) {
		this.isTd = isTd;
	}

	public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public ImportObjectDTOSn() {
		super();
		this.disableDate = new SimpleCalendar();
	}

	public void setDisableDate(String disableDate) throws CalendarException {
		this.disableDate.setCalendarValue(disableDate);
	}

	public SimpleCalendar getDisableDate() throws CalendarException {
		disableDate.setCalPattern(getCalPattern());
		return this.disableDate;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public void setWorkorderObjectNo(String workorderObjectNo) {
		this.workorderObjectNo = workorderObjectNo;
	}

	public void setWorkorderObjectCode(String workorderObjectCode) {
		this.workorderObjectCode = workorderObjectCode;
	}

    public boolean isAreaTypeIsNull() {
        return areaTypeIsNull;
    }

    public void setAreaTypeIsNull(boolean areaTypeIsNull) {
        this.areaTypeIsNull = areaTypeIsNull;
    }

	public void setWorkorderObjectName(String workorderObjectName) {
		this.workorderObjectName = workorderObjectName;
	}

	public void setWorkorderObjectLocation(String workorderObjectLocation) {
		this.workorderObjectLocation = workorderObjectLocation;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setObjectCategory(String objectCategory) {
		this.objectCategory = objectCategory;
	}

	public void setIsall(String isall) {
		this.isall = isall;
	}

	public void setIsTempAddr(String isTempAddr) {
		this.isTempAddr = isTempAddr;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getWorkorderObjectNo() {
		return this.workorderObjectNo;
	}

	public String getWorkorderObjectCode() {
		return this.workorderObjectCode;
	}

	public String getWorkorderObjectName() {
		return this.workorderObjectName;
	}

	public String getWorkorderObjectLocation() {
		return this.workorderObjectLocation;
	}

	public int getOrganizationId() {
		return this.organizationId;
	}

	public String getCountyCode() {
		return this.countyCode;
	}

	public String getRemark() {
		return this.remark;
	}

	public String getObjectCategory() {
		return this.objectCategory;
	}

	public String getIsall() {
		return this.isall;
	}

	public String getIsTempAddr() {
		return this.isTempAddr;
	}

	public String getProjectId() {
		return this.projectId;
	}

	public String getCountyName() {
		return this.countyName;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getTransferType() {
		return transferType;
	}

	public String getOrganizationOption() {
		return organizationOption;
	}

	public String getCountyOption() {
		return countyOption;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public String getObjectCategoryName() {
		return objectCategoryName;
	}

	public String getObjCategoryOption() {
		return objCategoryOption;
	}

	public String getEnabled() {
		return enabled;
	}

	void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public void setOrganizationOption(String organizationOption) {
		this.organizationOption = organizationOption;
	}

	public void setCountyOption(String countyOption) {
		this.countyOption = countyOption;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public void setObjectCategoryName(String objectCategoryName) {
		this.objectCategoryName = objectCategoryName;
	}

	public void setObjCategoryOption(String objCategoryOption) {
		this.objCategoryOption = objCategoryOption;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

    public String getLocCategoryDesc() {
        return locCategoryDesc;
    }

    public void setLocCategoryDesc(String locCategoryDesc) {
        this.locCategoryDesc = locCategoryDesc;
    }

    public String getEnableOption() {
		StringBuffer option = new StringBuffer();
		option.append("<option value=\"\">-请选择-</option>");
		option.append("<option value=\"Y\"");
		if(enabled.equals("Y")){
			option.append(" selected");
		}
		option.append(">有效</option>");
		option.append("<option value=\"N\"");
		if(enabled.equals("N")){
			option.append(" selected");
		}
		option.append(">无效</option>");
		return option.toString();
	}

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

    public String getTempAddr() {
        return isTempAddr;
    }

    public void setTempAddr(String tempAddr) {
        isTempAddr = tempAddr;
    }

    public String getAreaTypeOption() {
        return areaTypeOption;
    }

    public void setAreaTypeOption(String areaTypeOption) {
        this.areaTypeOption = areaTypeOption;
    }

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityOption() {
		return cityOption;
	}

	public void setCityOption(String cityOption) {
		this.cityOption = cityOption;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setDisableDate(SimpleCalendar disableDate) {
		this.disableDate = disableDate;
	}

	public String getCountyOptions() {
		return countyOptions;
	}

	public void setCountyOptions(String countyOptions) {
		this.countyOptions = countyOptions;
	}

}