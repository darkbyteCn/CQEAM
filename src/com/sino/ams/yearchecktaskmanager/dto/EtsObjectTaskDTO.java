package com.sino.ams.yearchecktaskmanager.dto;


import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;


public class EtsObjectTaskDTO extends CommonRecordDTO {
	
	//jeffery
	
	 private String orderNumber ="";//盘点任务编码
	 
	 private String taskType= "";
	 
	//jeffery
	

	 
	private String costCenterCode="";
	
	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	private String costCenterName=""; 
	private String countyCodeShi="";
	private String countyCodeXian="";
	private String countyCodecode="";
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
	private int isall = -1;
	private int isTempAddr = -1;
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
    private String provinceCode = "";
    
    private String isCheck = "";
    private String itemStatus = "";
    private String btsNo = ""; //基站或营业厅编号	
    private String latitudeLongitude = ""; //经纬度
    private String auxiliaryInfo = ""; //辅助信息
    
    private String deptToSite = ""; //部门间调拨 根据部门过虑地点
    
	private int cityId = -1;
	private String cityName ="";
	private int areaId= -1 ;
	private String areaName="" ;
	
	private String cost_code =""; //所属成本中心代码
	
	private String actioncode = "";
	
	private String loc2Code = ""; //地点第二段描述
	private String loc3Code = ""; //地点第三段代码
	private String shareType = ""; //是否共享
	
	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public String getLoc3Code() {
		return loc3Code;
	}

	public void setLoc3Code(String loc3Code) {
		this.loc3Code = loc3Code;
	}

	public void setLoc2Code(String loc2Code) {
		this.loc2Code = loc2Code;
	}

	public String getLoc2Code() {
		return loc2Code;
	}

	public void setLoc2Desc(String loc2Code) {
		this.loc2Code = loc2Code;
	}

	public String getAuxiliaryInfo() {
		return auxiliaryInfo;
	}

	public void setAuxiliaryInfo(String auxiliaryInfo) {
		this.auxiliaryInfo = auxiliaryInfo;
	}

	public String getLatitudeLongitude() {
		return latitudeLongitude;
	}

	public void setLatitudeLongitude(String latitudeLongitude) {
		this.latitudeLongitude = latitudeLongitude;
	}

	public String getCountyCodecode() {
		return countyCodecode;
	}

	public void setCountyCodecode(String countyCodecode) {
		this.countyCodecode = countyCodecode;
	}
    //Excel行号
    private String excelLineId;
    
    public String getExcelLineId() {
		return excelLineId;
	}

	public void setExcelLineId(String excelLineId) {
		this.excelLineId = excelLineId;
	}

	public String getBtsNo() {
		return btsNo;
	}

	public void setBtsNo(String btsNo) {
		this.btsNo = btsNo;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

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

    //add by   李轶   2009-09-21
    private boolean areaTypeIsNull = false;  //行政区划是否为空

    //专为LOOKUP中READIO框所设属性
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

    public EtsObjectTaskDTO() {
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

/**
	 * 功能：设置资产地点表(EAM)属性 序列号
	 *
	 * @param workorderObjectNo String
	 */
	public void setWorkorderObjectNo(String workorderObjectNo) {
		this.workorderObjectNo = workorderObjectNo;
	}

/**
	 * 功能：设置资产地点表(EAM)属性 地点代码
	 *
	 * @param workorderObjectCode String
	 */
	public void setWorkorderObjectCode(String workorderObjectCode) {
		this.workorderObjectCode = workorderObjectCode;
	}

    /**
     * Function:        areaTyp " + SyBaseSQLUtil.isNull() + "  or not
     * @return          boolean
     */
    public boolean isAreaTypeIsNull() {
        return areaTypeIsNull;
    }

    public void setAreaTypeIsNull(boolean areaTypeIsNull) {
        this.areaTypeIsNull = areaTypeIsNull;
    }

    /**
	 * 功能：设置资产地点表(EAM)属性 地点名称

	 *
	 * @param workorderObjectName String
	 */
	public void setWorkorderObjectName(String workorderObjectName) {
		this.workorderObjectName = workorderObjectName;
	}

/**
	 * 功能：设置资产地点表(EAM)属性 所在地点
	 *
	 * @param workorderObjectLocation String
	 */
	public void setWorkorderObjectLocation(String workorderObjectLocation) {
		this.workorderObjectLocation = workorderObjectLocation;
	}

/**
	 * 功能：设置资产地点表(EAM)属性 组织ID
	 *
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

/**
	 * 功能：设置资产地点表(EAM)属性 区县代码
	 *
	 * @param countyCode String
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

/**
	 * 功能：设置资产地点表(EAM)属性 失效日期
	 *
	 * @param disableDate Timestamp
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
//    public void setDisableDate(String disableDate) throws CalendarException {
//        if (!StrUtil.isEmpty(disableDate)) {
//            SimpleCalendar cal = new SimpleCalendar(disableDate);
//            this.disableDate = cal.getSQLTimestamp();
//        }
//    }

/**
	 * 功能：设置资产地点表(EAM)属性 备注
	 *
	 * @param remark String
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

/**
	 * 功能：设置资产地点表(EAM)属性 地点类别
	 *
	 * @param objectCategory String
	 */
	public void setObjectCategory(String objectCategory) {
		this.objectCategory = objectCategory;
	}

/**
	 * 功能：设置资产地点表(EAM)属性 巡检模式
	 *
	 * @param isall String
	 */
	public void setIsall(int isall) {
		this.isall = isall;
	}

/**
	 * 功能：设置资产地点表(EAM)属性 是否临时地点
	 *
	 * @param isTempAddr String
	 */
	public void setIsTempAddr(int isTempAddr) {
		this.isTempAddr = isTempAddr;
	}


	/**
	 * 功能：设置资产地点表(EAM)属性 所属工程
	 *
	 * @param projectId String
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

/**
	 * 功能：获取资产地点表(EAM)属性 序列号
	 *
	 * @return String
	 */
	public String getWorkorderObjectNo() {
		return this.workorderObjectNo;
	}

/**
	 * 功能：获取资产地点表(EAM)属性 地点代码
	 *
	 * @return String
	 */
	public String getWorkorderObjectCode() {
		return this.workorderObjectCode;
	}

/**
	 * 功能：获取资产地点表(EAM)属性 地点名称
	 *
	 * @return String
	 */
	public String getWorkorderObjectName() {
		return this.workorderObjectName;
	}

/**
	 * 功能：获取资产地点表(EAM)属性 所在地点
	 *
	 * @return String
	 */
	public String getWorkorderObjectLocation() {
		return this.workorderObjectLocation;
	}

/**
	 * 功能：获取资产地点表(EAM)属性 组织ID
	 *
	 * @return String
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}

/**
	 * 功能：获取资产地点表(EAM)属性 区县代码
	 *
	 * @return String
	 */
	public String getCountyCode() {
		return this.countyCode;
	}

/**
	 * 功能：获取资产地点表(EAM)属性 失效日期
	 *
	 * @return Timestamp
	 */
//    public Timestamp getDisableDate() {
//        return this.disableDate;
//    }

/**
	 * 功能：获取资产地点表(EAM)属性 备注
	 *
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

/**
	 * 功能：获取资产地点表(EAM)属性 地点类别
	 *
	 * @return String
	 */
	public String getObjectCategory() {
		return this.objectCategory;
	}

/**
	 * 功能：获取资产地点表(EAM)属性 巡检模式
	 *
	 * @return String
	 */
	public int getIsall() {
		return this.isall;
	}

/**
	 * 功能：获取资产地点表(EAM)属性 是否临时地点
	 *
	 * @return String
	 */
	public int getIsTempAddr() {
		return this.isTempAddr;
	}




	/**
	 * 功能：获取资产地点表(EAM)属性 所属工程
	 *
	 * @return String
	 */
	public String getProjectId() {
		return this.projectId;
	}

	/**
	 * 功能：区县名称
	 *
	 * @return String
	 */
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

	public void setCountyName(String countyName) {
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

    /**
     * Function:    获取地点专业
     * @return      地点专业(String类型)
     */
    public String getLocCategoryDesc() {
        return locCategoryDesc;
    }

    /**
     * Function:                设置地点专业
     * @param locCategoryDesc:  地点专业名称
     */
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

    public int getTempAddr() {
        return isTempAddr;
    }

    public void setTempAddr(int tempAddr) {
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

//	public void setDisableDate(SimpleCalendar disableDate) {
//		this.disableDate = disableDate;
//	}

	public String getCountyOptions() {
		return countyOptions;
	}

	public void setCountyOptions(String countyOptions) {
		this.countyOptions = countyOptions;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getDeptToSite() {
		return deptToSite;
	}

	public void setDeptToSite(String deptToSite) {
		this.deptToSite = deptToSite;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getCost_code() {
		return cost_code;
	}

	public void setCost_code(String cost_code) {
		this.cost_code = cost_code;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCostCenterCode() {
		return costCenterCode;
	}

	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}
	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
	public String getCountyCodeShi() {
		return countyCodeShi;
	}

	public void setCountyCodeShi(String countyCodeShi) {
		this.countyCodeShi = countyCodeShi;
	}
	public String getCountyCodeXian() {
		return countyCodeXian;
	}

	public void setCountyCodeXian(String countyCodeXian) {
		this.countyCodeXian = countyCodeXian;
	}

	public String getActioncode() {
		return actioncode;
	}

	public void setActioncode(String actioncode) {
		this.actioncode = actioncode;
	}
	
	public boolean isAddLocation(){
        return (StrUtil.isNotEmpty(remark) && remark.equals("新增"));
    }

    public boolean isUpdateLocation(){
        return (StrUtil.isNotEmpty(remark) && remark.equals("修改"));
    }

    public boolean isDisableLocation(){
        return (StrUtil.isNotEmpty(remark) && remark.equals("失效"));
    }

}
