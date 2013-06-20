package com.sino.ams.newasset.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.calen.SimpleDate;
import com.sino.base.calen.SimpleTime;
import com.sino.base.constant.WorldConstant;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.TimeException;
import com.sino.base.util.StrUtil;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * 
 * @系统名称: 
 * @功能描述: 此DTO只用于资产详细页面显示，请注意不能做业务处理。float字段在这里都是String.
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Nov 28, 2011
 */
public class AmsAssetsAddressVDetailDTO extends EtsItemInfoDTO {

	private String countyName = "";
	private String itemCategoryName = "";
	private String faCategoryName = "";
	private String itemStatusName = "";
	private String transferType = ""; //调拨单的哪一种类型：部门内，部门间，地市间
	private String scrapValue = ""; //残值--财务上应为定值
	private String depreciation; //累计折旧--从开始折旧日起算,按照一定的方法,例如平均折旧法,折旧到财务当期时的累计折旧额
	private String depreAccountOption = ""; //折旧账户下拉列表
	private String faCategoryOption = ""; //固定资产列表下拉框
	private String assignProp = "";
	private String objectCategory = ""; //地点类别
	private String objectCategoryName = ""; //地点类别名称
	private String vendorNumber = ""; //厂商编号
	private String projectNumber = ""; //项目编号
	private String employeeNumber = "";
	private String srcPage = "";
	private String userId = "";
	private String assProp = "";
	private String bookTypeOption = "";
	private String startCategoryCode = "";
	private String endCategoryCode = "";
	private String startCost = "";
	private String endCost = "";
	private String faContentCode = ""; //资产大类别，目前分为：管理类还是网络类
	private String faContentName = ""; //资产大类别，目前分为：管理类还是网络类
	private String faContentOption = ""; //资产大类别下拉框选项
	private String transferTypeOption = "";
	private String itemStatusOption = "";
	private String innDeptOpt = "";
	private String btwDeptOpt = "";
	private String btwCompOpt = "";
	private String fromCompanyOption = ""; //为内蒙古移动而设置
	private ServletConfigDTO servletConfig = null;
	private int fromOrganizationId;
	private String itemCategoryOpt = "";
	private String radioData = "";
	private String maintainCompany = "";
	private String maintainCompanyOpt = "";
	private String costCenterCode = "";
	private SimpleCalendar startCreationDate = null;
	private SimpleCalendar endCreationDate = null;
	private String orgOption = "";
	private String fromBarcode = "";
	private String toBarcode = "";
	private String accountPeriod = "";
	private String accPeriodOpt = "";
	private String analyseType = "";
	private String deptOption = "";
    private String manufacturerCode = "";//厂商CODE
    private String constructStatus = "";
    private String fromPower = "";
    private String toPower = "";
    private String radioShare = "";
    private String specialityDept = "";//实物部门
    private String specialityDeptName = "";
    private String specialityUser = "";//专业责任人
    private String specialityUserName="";//专业责任人姓名
    private String isAbate = "";
    private String isTD = "";//是否TD
    private String accessType = "";
    private SimpleCalendar dateRetiredStart = null;
    private SimpleCalendar dateRetiredEnd = null;
    private SimpleCalendar dateEffectiveStart = null;
    private SimpleCalendar dateEffectiveEnd = null;
    private SimpleCalendar startDisableDate = null; //失效日期
	private SimpleCalendar endDisableDate = null; //失效日期
    private String discarded = "";
    private boolean lneIsNull = true;   //逻辑网络元素是否为空
    private boolean cexIsNull = true;   //投资分类是否为空
    private boolean opeIsNull = true;   //业务平台是否为空
    private boolean nleIsNull = true;   //网络层次是否为空
    private boolean snIsNull = true;    //支撑网设备类型是否为空
    private String isCheck = "";    //is or not check
    private String pageTitle = "";

    private boolean isNewQuery = false;
    private String isRental = "";
    private int actualQty = SyBaseSQLUtil.NULL_INT_VALUE;
    private String areaType = "";
    private String areaTypeOpt = "";
    private String city = "";
    private String cityOpt = "";
    private String county = "";
    private String countyOpt = "";
    private String areaCode = "";
    private String nDeptName = "";
    private String remark1 = "";
    private String remark2 = "";

    private String tfType = "";//通服资产类型
    private String otherInfo = "";

    private SimpleCalendar rentDate = null; //失效日期
    private SimpleCalendar startRentDate = null; //失效日期
	private SimpleCalendar endRentDate = null; //失效日期
    private String rentPerson = "";
    private String contractNumber = "";
    private String contractName = "";
    private String manyDimensionsType = "";//多维度类型
    private String manyDimensionsValue = "";

    private String isOverageAssets = ""; //是否逾龄资产
    private String isImportantAssets = ""; //是否重要网络资产
    
    
    
    private String areaTypeName = "";
    private String deprnLeftMonth  = "";
    
    private String getDeprnReServe = "";
    private String deprnAmount  = "";
    private String impairAmount  = "";
    private String ytdImpairment  = "";
    private String ytdDeprn = "";
    
    private String originalCost = "";
    private String currentUnits = "";
    private String cost = "";
    private String deprnCost = "";
    
    private String netUnitCode = "" ; //NET_UNIT_CODE
    private String investCatCode = ""; 
    
    
    
    
    /******/
    private int assetId = 0;
    private String faCategory1 = "";
    private String faCategory2 = "";
    private String assetsDescription = "";
    private String faCategoryCode = "";
    private String unitOfMeasure = "";
    private int lifeInYears;
    private String modelNumber = "";
    private String tagNumber = "";
    private String assetsLocation = "";
    private SimpleCalendar datePlacedInService = null;
    private int isRetirements;
    private String assetNumber = "";
    private int assetsStatus;
    private String bookTypeCode = "";
    private String assignedToName = "";
    private String assignedToNumber = "";
    private String segment1 = "";
    private String segment2 = "";
    private int transToMis;
    private int transToMisDesc;
    private int transToMisTag;
    private int transToMisLoc;
    private String codeCombinationId;
    private String companyCode = "";

    private String treeCategory = "";
    private String assetsCategory = "";
    private String deptCode = "";
    private String deptName = "";
    private String companyName = "";
    private String exportType = "";
    private String key = "";
    private String countyCodeMis = "";
    private String assetsLocationCode = "";
    private String segment3 = "";
    private String segment2Temp = "";
    private String depreciationAccount = "";

    private SimpleCalendar assetsCreateDate = null;
    private String depreciationAccountName = ""; //折旧账户
    private String bookTypeName = "";
    private String misTagNumber = "";
    private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;

    private String assetsStatusDes = "";
    private String impairReserve = "";
    private String recycleValue = "";

    private String netAssetValue = ""; //资产净值
    
    //报废日期
    private SimpleCalendar dateRetired = null;
    //报废有效日期
    private SimpleCalendar dateEffective = null;
    //报废值
    private String costRetired = "";
    //报废类型
    private String retirementTypeCode = "";

    private boolean chkUser = false;
    private boolean chkResponsibilityDept = false;
    
    private String assetAddress="";
    
    public String getAssetAddress() {
		return assetAddress;
	}

	public void setAssetAddress(String assetAddress) {
		this.assetAddress = assetAddress;
	}

	public AmsAssetsAddressVDetailDTO() {
		super();
		this.startCreationDate = new SimpleCalendar();
		this.endCreationDate = new SimpleCalendar();
		this.dateEffectiveEnd = new SimpleCalendar();
		this.dateEffectiveStart = new SimpleCalendar();
		this.dateRetiredStart = new SimpleCalendar();
		this.dateRetiredEnd = new SimpleCalendar();
		this.startDisableDate = new SimpleCalendar();
		this.endDisableDate = new SimpleCalendar();
		this.startRentDate = new SimpleCalendar();
		this.endRentDate = new SimpleCalendar();
		this.rentDate = new SimpleCalendar();
		datePlacedInService = new SimpleCalendar();
        assetsCreateDate = new SimpleCalendar();
        this.dateRetired = new SimpleCalendar();
        this.dateEffective = new SimpleCalendar();
	}

    public String getManyDimensionsType() {
        return manyDimensionsType;
    }

    public void setManyDimensionsType(String manyDimensionsType) {
        this.manyDimensionsType = manyDimensionsType;
    }

    public String getManyDimensionsValue() {
        return manyDimensionsValue;
    }

    public void setManyDimensionsValue(String manyDimensionsValue) {
        this.manyDimensionsValue = manyDimensionsValue;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getTfType() {
        return tfType;
    }

    public void setTfType(String tfType) {
        this.tfType = tfType;
    }

    public String getConstructStatus() {
        return constructStatus;
    }

    public void setConstructStatus(String constructStatus) {
        this.constructStatus = constructStatus;
    }

    public boolean isSnIsNull() {
        return snIsNull;
    }

    public void setSnIsNull(boolean snIsNull) {
        this.snIsNull = snIsNull;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getnDeptName() {
        return nDeptName;
    }

    public void setnDeptName(String nDeptName) {
        this.nDeptName = nDeptName;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getAreaTypeOpt() {
        return areaTypeOpt;
    }

    public void setAreaTypeOpt(String areaTypeOpt) {
        this.areaTypeOpt = areaTypeOpt;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityOpt() {
        return cityOpt;
    }

    public void setCityOpt(String cityOpt) {
        this.cityOpt = cityOpt;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountyOpt() {
        return countyOpt;
    }

    public void setCountyOpt(String countyOpt) {
        this.countyOpt = countyOpt;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public int getActualQty() {
        return actualQty;
    }

    public void setActualQty(int actualQty) {
        this.actualQty = actualQty;
    }

    public String getRental() {
        return isRental;
    }

    public void setRental(String rental) {
        isRental = rental;
    }

    public boolean isNewQuery() {
        return isNewQuery;
    }

    public void setNewQuery(boolean newQuery) {
        isNewQuery = newQuery;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getCheck() {
        return isCheck;
    }

    public void setCheck(String check) {
        isCheck = check;
    }

    public String getDiscarded() {
        return discarded;
    }

    public void setDiscarded(String discarded) {
        this.discarded = discarded;
    }

    public String getSpecialityUser() {
        return specialityUser;
    }

    public void setSpecialityUser(String specialityUser) {
        this.specialityUser = specialityUser;
    }

    public String getSpecialityUserName() {
        return specialityUserName;
    }

    public void setSpecialityUserName(String specialityUserName) {
        this.specialityUserName = specialityUserName;
    }

    public SimpleCalendar getEndDisableDate() throws CalendarException {
		endDisableDate.setCalPattern(getCalPattern());
		return this.endDisableDate;
	}

 

    public SimpleCalendar getStartDisableDate() throws CalendarException {
		startDisableDate.setCalPattern(getCalPattern());
		return this.startDisableDate;
	}

 

    public SimpleCalendar getStartRentDate() throws CalendarException {
		startRentDate.setCalPattern(getCalPattern());
		return this.startRentDate;
	}

 

    public SimpleCalendar getEndRentDate() throws CalendarException {
		endRentDate.setCalPattern(getCalPattern());
		return this.endRentDate;
	}
 

    public String getIsTD() {
        return isTD;
    }

    public void setIsTD(String TD) {
        isTD = TD;
    }

    public String getIsAbate() {
        return isAbate;
    }

    public void setIsAbate(String abate) {
        isAbate = abate;
    }
    public String getSpecialityDeptName() {
        return specialityDeptName;
    }

    public void setSpecialityDeptName(String specialityDeptName) {
        this.specialityDeptName = specialityDeptName;
    }

    public String getSpecialityDept() {
        return specialityDept;
    }

    public void setSpecialityDept(String specialityDept) {
        this.specialityDept = specialityDept;
    }

    public String getFromPower() {
        return fromPower;
    }

    public void setFromPower(String fromPower) {
        this.fromPower = fromPower;
    }

    public String getToPower() {
        return toPower;
    }

    public void setToPower(String toPower) {
        this.toPower = toPower;
    }

    public String getRadioShare() {
        return radioShare;
    }

    public void setRadioShare(String radioShare) {
        this.radioShare = radioShare;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 COUNTY_NAME
	 * @param countyName String
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}


	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 COUNTY_NAME
	 * @return String
	 */
	public String getCountyName() {
		return this.countyName;
	}

	public String getItemCategoryName() {
		return itemCategoryName;
	}

 


	public void setScrapValue(String scrapValue) {
		this.scrapValue = scrapValue;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public void setItemStatusName(String itemStatusName) {
		this.itemStatusName = itemStatusName;
	}

	public void setDepreciation(String depreciation) {
		this.depreciation = depreciation;
	}

	public void setDepreAccountOption(String depreAccountOption) {
		this.depreAccountOption = depreAccountOption;
	}

	public void setFaCategoryOption(String faCategoryOption) {
		this.faCategoryOption = faCategoryOption;
	}

	public void setFaCategoryName(String faCategoryName) {
		this.faCategoryName = faCategoryName;
	}

	public void setAssignProp(String assignProp) {
		this.assignProp = assignProp;
	}

	public void setObjectCategory(String objectCategory) {
		this.objectCategory = objectCategory;
	}

	public void setObjectCategoryName(String objectCategoryName) {
		this.objectCategoryName = objectCategoryName;
	}

	public void setVendorNumber(String vendorNumber) {
		this.vendorNumber = vendorNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public void setSrcPage(String srcPage) {
		this.srcPage = srcPage;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setAssProp(String assProp) {
		this.assProp = assProp;
	}

	public void setBookTypeOption(String bookTypeOption) {
		this.bookTypeOption = bookTypeOption;
	}

	public void setEndCategoryCode(String endCategoryCode) {
		this.endCategoryCode = endCategoryCode;
	}

	public void setStartCategoryCode(String startCategoryCode) {
		this.startCategoryCode = startCategoryCode;
	}

	public void setEndCost(String endCost) {
		this.endCost = endCost;
	}

	public void setStartCost(String startCost) {
		this.startCost = startCost;
	}

	public void setFaContentCode(String faContentCode) {
		this.faContentCode = faContentCode;
	}

	public void setFaContentName(String faContentName) {
		this.faContentName = faContentName;
	}

	public void setFaContentOption(String faContentOption) {
		this.faContentOption = faContentOption;
	}

	public void setTransferTypeOption(String transferTypeOption) {
		this.transferTypeOption = transferTypeOption;
	}

	public void setItemStatusOption(String itemStatusOption) {
		this.itemStatusOption = itemStatusOption;
	}

	public void setBtwCompOpt(String btwCompOpt) {
		this.btwCompOpt = btwCompOpt;
	}

	public void setBtwDeptOpt(String btwDeptOpt) {
		this.btwDeptOpt = btwDeptOpt;
	}

	public void setInnDeptOpt(String innDeptOpt) {
		this.innDeptOpt = innDeptOpt;
	}

	public void setFromOrganizationId(int fromOrganizationId) {
		this.fromOrganizationId = fromOrganizationId;
	}

	public void setServletConfig(ServletConfigDTO servletConfig) {
		this.servletConfig = servletConfig;
	}

	public void setFromCompanyOption(String fromCompanyOption) {
		this.fromCompanyOption = fromCompanyOption;
	}

	public void setItemCategoryOpt(String itemCategoryOpt) {
		this.itemCategoryOpt = itemCategoryOpt;
	}


	public void setRadioData(String radioData) {
		this.radioData = radioData;
	}

	public void setMaintainCompany(String maintainCompany) {
		this.maintainCompany = maintainCompany;
	}

	public void setMaintainCompanyOpt(String maintainCompanyOpt) {
		this.maintainCompanyOpt = maintainCompanyOpt;
	}

	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}

	public void setOrgOption(String orgOption) {
		this.orgOption = orgOption;
	}


	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 startCreationDate
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getStartCreationDate() throws CalendarException {
		startCreationDate.setCalPattern(getCalPattern());
		return this.startCreationDate;
	}


	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 endCreationDate
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getEndCreationDate() throws CalendarException {
		endCreationDate.setCalPattern(getCalPattern());
		return this.endCreationDate;
	}

	public String getScrapValue() {
		return scrapValue;
	}

	public String getTransferType() {
		return transferType;
	}

	public String getItemStatusName() {
		return itemStatusName;
	}

	/**
	 * 功能：获取累计折旧
	 * @return String
	 */
	public String getDepreciation() {
		return depreciation;
	}


	public String getDepreAccountOption() {
		return depreAccountOption;
	}

	public String getFaCategoryOption() {
		return faCategoryOption;
	}

	public String getFaCategoryName() {
		return faCategoryName;
	}

	public String getAssignProp() {
		return assignProp;
	}

	public String getObjectCategory() {
		return objectCategory;
	}

	public String getObjectCategoryName() {
		return objectCategoryName;
	}

	public String getVendorNumber() {
		return vendorNumber;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public String getSrcPage() {
		return srcPage;
	}

	public String getUserId() {
		return userId;
	}

	public String getAssProp() {
		return assProp;
	}

	public String getBookTypeOption() {
		return bookTypeOption;
	}

	public String getEndCategoryCode() {
		return endCategoryCode;
	}

	public String getStartCategoryCode() {
		return startCategoryCode;
	}

	public String getEndCost() {
		return endCost;
	}

	public String getStartCost() {
		return startCost;
	}

	public String getFaContentCode() {
		return faContentCode;
	}

	public String getFaContentName() {
		return faContentName;
	}

	public String getFaContentOption() {
		return faContentOption;
	}

	public String getTransferTypeOption() {
		return transferTypeOption;
	}

	public String getItemStatusOption() {
		return itemStatusOption;
	}

	public String getBtwCompOpt() {
		return btwCompOpt;
	}

	public String getBtwDeptOpt() {
		return btwDeptOpt;
	}

	public String getInnDeptOpt() {
		return innDeptOpt;
	}

	public int getFromOrganizationId() {
		return fromOrganizationId;
	}

	public ServletConfigDTO getServletConfig() {
		return servletConfig;
	}

	public String getFromCompanyOption() {
		return fromCompanyOption;
	}

	public String getItemCategoryOpt() {
		return itemCategoryOpt;
	}

	public String getRadioData() {
		return radioData;
	}

	public String getMaintainCompany() {
		return maintainCompany;
	}

	public String getMaintainCompanyOpt() {
		return maintainCompanyOpt;
	}

	public String getCostCenterCode() {
		return costCenterCode;
	}

	public String getOrgOption() {
		return orgOption;
	}

	public String getFromBarcode() {
		return fromBarcode;
	}

	public void setFromBarcode(String fromBarcode) {
		this.fromBarcode = fromBarcode;
	}

	public String getToBarcode() {
		return toBarcode;
	}

	public String getAccountPeriod() {
		return accountPeriod;
	}

	public String getAccPeriodOpt() {
		return accPeriodOpt;
	}

	public String getAnalyseType() {
		return analyseType;
	}

	public String getDeptOption() {
		return deptOption;
	}

	public void setToBarcode(String toBarcode) {
		this.toBarcode = toBarcode;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}

	public void setAccPeriodOpt(String accPeriodOpt) {
		this.accPeriodOpt = accPeriodOpt;
	}

	public void setAnalyseType(String analyseType) {
		this.analyseType = analyseType;
	}

	public void setDeptOption(String deptOption) {
		this.deptOption = deptOption;
	}


    public SimpleCalendar getRentDate() throws CalendarException {
        this.rentDate.setCalPattern(getCalPattern());
        return rentDate;
    }


	public SimpleCalendar getDateEffectiveEnd() throws CalendarException {
		this.dateEffectiveEnd.setCalPattern(getCalPattern());
		return dateEffectiveEnd;
	}


	public SimpleCalendar getDateEffectiveStart() throws CalendarException {
		this.dateEffectiveStart.setCalPattern(getCalPattern());
		return dateEffectiveStart;
	}


	public SimpleCalendar getDateRetiredEnd() throws CalendarException {
		this.dateRetiredEnd.setCalPattern(getCalPattern());
		return dateRetiredEnd;
	}

 
	public SimpleCalendar getDateRetiredStart() throws CalendarException {
		this.dateRetiredStart.setCalPattern(getCalPattern());
		return dateRetiredStart;
	}

 

	/**
	 * 功能：构造查询条件截至日期的最后一秒所的日历对象。免去应用中每个查询SQL自己构造截至日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getSQLEndCreationDate(){
		SimpleCalendar sqlEndCal = new SimpleCalendar();
		if (!StrUtil.isEmpty(endCreationDate.toString())) {
			try {
				SimpleDate date = endCreationDate.getSimpleDate();
				SimpleTime time = SimpleTime.getEndTime();
				sqlEndCal = new SimpleCalendar(date, time);
			} catch (TimeException ex) {
				ex.printLog();
			}
		}
		return sqlEndCal;
	}

	/**
	 * 功能：获取资产分配下拉列表
	 * @return String
	 */
	public String getAssignOptions() {
		StringBuffer assignOption = new StringBuffer();
		String[] assignValues = AssetsDictConstant.ASSIGNED_VALUE_LIMIT;
		String assignValue = "";
		assignOption.append("<option value=\"\">---请选择---</option>");
		for (int i = 0; i < assignValues.length; i++) {
			assignValue = assignValues[i];
			assignOption.append("<option value=\"");
			assignOption.append(assignValue);
			assignOption.append("\"");
			if (assignProp.equals(assignValue)) {
				assignOption.append(" selected");
			}
			assignOption.append(">");
			assignOption.append(AssetsDictConstant.ASSIGNED_LABEL_LIMIT[i]);
			assignOption.append("</option>");
			assignOption.append(WorldConstant.ENTER_CHAR);
		}
		return assignOption.toString();
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

    /**
     * Function         获得逻辑网络元素是否为空
     * @return          true   or   false
     */
    public boolean isLneIsNull() {
        return lneIsNull;
    }

    public void setLneIsNull(boolean lneIsNull) {
        this.lneIsNull = lneIsNull;
    }

    public boolean isCexIsNull() {
        return cexIsNull;
    }

    public void setCexIsNull(boolean cexIsNull) {
        this.cexIsNull = cexIsNull;
    }

    public boolean isOpeIsNull() {
        return opeIsNull;
    }

    public void setOpeIsNull(boolean opeIsNull) {
        this.opeIsNull = opeIsNull;
    }

    public boolean isNleIsNull() {
        return nleIsNull;
    }

    public void setNleIsNull(boolean nleIsNull) {
        this.nleIsNull = nleIsNull;
    }

    public String getRentPerson() {
        return rentPerson;
    }

    public void setRentPerson(String rentPerson) {
        this.rentPerson = rentPerson;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

	public String getIsOverageAssets() {
		return isOverageAssets;
	}

	public void setIsOverageAssets(String isOverageAssets) {
		this.isOverageAssets = isOverageAssets;
	}

	public String getIsImportantAssets() {
		return isImportantAssets;
	}

	public void setIsImportantAssets(String isImportantAssets) {
		this.isImportantAssets = isImportantAssets;
	}

	public String getAreaTypeName() {
		return areaTypeName;
	}

	public void setAreaTypeName(String areaTypeName) {
		this.areaTypeName = areaTypeName;
	}

	public String getDeprnLeftMonth() {
		return deprnLeftMonth;
	}

	public void setDeprnLeftMonth(String deprnLeftMonth) {
		this.deprnLeftMonth = deprnLeftMonth;
	}

	public String getGetDeprnReServe() {
		return getDeprnReServe;
	}

	public void setGetDeprnReServe(String getDeprnReServe) {
		this.getDeprnReServe = getDeprnReServe;
	}

	public String getDeprnAmount() {
		return deprnAmount ; 
	}

	public void setDeprnAmount(String deprnAmount) {
		this.deprnAmount = deprnAmount;
	}

	public String getImpairAmount() {
		return impairAmount;
	}

	public void setImpairAmount(String impairAmount) {
		this.impairAmount = impairAmount;
	}

	public String getYtdImpairment() {
		return ytdImpairment;
	}

	public void setYtdImpairment(String ytdImpairment) {
		this.ytdImpairment = ytdImpairment;
	}

	public String getYtdDeprn() {
		return ytdDeprn;
	}

	public void setYtdDeprn(String ytdDeprn) {
		this.ytdDeprn = ytdDeprn;
	}

	public String getNetUnitCode() {
		return netUnitCode;
	}

	public void setNetUnitCode(String netUnitCode) {
		this.netUnitCode = netUnitCode;
	}

	public String getInvestCatCode() {
		return investCatCode;
	}

	public void setInvestCatCode(String investCatCode) {
		this.investCatCode = investCatCode;
	}
 
	public void setOriginalCost(String originalCost) {
		this.originalCost = originalCost;
	}

	public void setCurrentUnits(String currentUnits) {
		this.currentUnits = currentUnits;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public void setDeprnCost(String deprnCost) {
		this.deprnCost = deprnCost;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getIsRental() {
		return isRental;
	}

	public void setIsRental(String isRental) {
		this.isRental = isRental;
	}

	public String getNDeptName() {
		return nDeptName;
	}

	public void setNDeptName(String deptName) {
		nDeptName = deptName;
	}

	public int getAssetId() {
		return assetId;
	}

	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	public String getFaCategory1() {
		return faCategory1;
	}

	public void setFaCategory1(String faCategory1) {
		this.faCategory1 = faCategory1;
	}

	public String getFaCategory2() {
		return faCategory2;
	}

	public void setFaCategory2(String faCategory2) {
		this.faCategory2 = faCategory2;
	}

	public String getAssetsDescription() {
		return assetsDescription;
	}

	public void setAssetsDescription(String assetsDescription) {
		this.assetsDescription = assetsDescription;
	}

	public String getFaCategoryCode() {
		return faCategoryCode;
	}

	public void setFaCategoryCode(String faCategoryCode) {
		this.faCategoryCode = faCategoryCode;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public int getLifeInYears() {
		return lifeInYears;
	}

	public void setLifeInYears(int lifeInYears) {
		this.lifeInYears = lifeInYears;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	public String getAssetsLocation() {
		return assetsLocation;
	}

	public void setAssetsLocation(String assetsLocation) {
		this.assetsLocation = assetsLocation;
	}

	public SimpleCalendar getDatePlacedInService() throws CalendarException {
		this.datePlacedInService.setCalPattern( getCalPattern() );
		return datePlacedInService;
	}

	public void setDatePlacedInService(String datePlacedInService) throws CalendarException {
		this.datePlacedInService.setCalendarValue(datePlacedInService);
	}

	public int getIsRetirements() {
		return isRetirements;
	}

	public void setIsRetirements(int isRetirements) {
		this.isRetirements = isRetirements;
	}

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	public int getAssetsStatus() {
		return assetsStatus;
	}

	public void setAssetsStatus(int assetsStatus) {
		this.assetsStatus = assetsStatus;
	}

	public String getBookTypeCode() {
		return bookTypeCode;
	}

	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}

	public String getAssignedToName() {
		return assignedToName;
	}

	public void setAssignedToName(String assignedToName) {
		this.assignedToName = assignedToName;
	}

	public String getAssignedToNumber() {
		return assignedToNumber;
	}

	public void setAssignedToNumber(String assignedToNumber) {
		this.assignedToNumber = assignedToNumber;
	}

	public String getSegment1() {
		return segment1;
	}

	public void setSegment1(String segment1) {
		this.segment1 = segment1;
	}

	public String getSegment2() {
		return segment2;
	}

	public void setSegment2(String segment2) {
		this.segment2 = segment2;
	}

	public int getTransToMis() {
		return transToMis;
	}

	public void setTransToMis(int transToMis) {
		this.transToMis = transToMis;
	}

	public int getTransToMisDesc() {
		return transToMisDesc;
	}

	public void setTransToMisDesc(int transToMisDesc) {
		this.transToMisDesc = transToMisDesc;
	}

	public int getTransToMisTag() {
		return transToMisTag;
	}

	public void setTransToMisTag(int transToMisTag) {
		this.transToMisTag = transToMisTag;
	}

	public int getTransToMisLoc() {
		return transToMisLoc;
	}

	public void setTransToMisLoc(int transToMisLoc) {
		this.transToMisLoc = transToMisLoc;
	}

	public String getCodeCombinationId() {
		return codeCombinationId;
	}

	public void setCodeCombinationId(String codeCombinationId) {
		this.codeCombinationId = codeCombinationId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getTreeCategory() {
		return treeCategory;
	}

	public void setTreeCategory(String treeCategory) {
		this.treeCategory = treeCategory;
	}

	public String getAssetsCategory() {
		return assetsCategory;
	}

	public void setAssetsCategory(String assetsCategory) {
		this.assetsCategory = assetsCategory;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCountyCodeMis() {
		return countyCodeMis;
	}

	public void setCountyCodeMis(String countyCodeMis) {
		this.countyCodeMis = countyCodeMis;
	}

	public String getAssetsLocationCode() {
		return assetsLocationCode;
	}

	public void setAssetsLocationCode(String assetsLocationCode) {
		this.assetsLocationCode = assetsLocationCode;
	}

	public String getSegment3() {
		return segment3;
	}

	public void setSegment3(String segment3) {
		this.segment3 = segment3;
	}

	public String getSegment2Temp() {
		return segment2Temp;
	}

	public void setSegment2Temp(String segment2Temp) {
		this.segment2Temp = segment2Temp;
	}

	public String getDepreciationAccount() {
		return depreciationAccount;
	}

	public void setDepreciationAccount(String depreciationAccount) {
		this.depreciationAccount = depreciationAccount;
	}

	public SimpleCalendar getAssetsCreateDate() throws CalendarException {
		this.assetsCreateDate.setCalPattern( getCalPattern() );
		return assetsCreateDate;
	}

	public void setAssetsCreateDate(String assetsCreateDate) throws CalendarException {
		this.assetsCreateDate.setCalendarValue(assetsCreateDate);
	}

	public String getDepreciationAccountName() {
		return depreciationAccountName;
	}

	public void setDepreciationAccountName(String depreciationAccountName) {
		this.depreciationAccountName = depreciationAccountName;
	}

	public String getBookTypeName() {
		return bookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}

	public String getMisTagNumber() {
		return misTagNumber;
	}

	public void setMisTagNumber(String misTagNumber) {
		this.misTagNumber = misTagNumber;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getAssetsStatusDes() {
		return assetsStatusDes;
	}

	public void setAssetsStatusDes(String assetsStatusDes) {
		this.assetsStatusDes = assetsStatusDes;
	}

	public String getImpairReserve() {
		return impairReserve;
	}

	public void setImpairReserve(String impairReserve) {
		this.impairReserve = impairReserve;
	}

	public String getRecycleValue() {
		return recycleValue;
	}

	public void setRecycleValue(String recycleValue) {
		this.recycleValue = recycleValue;
	}

	public String getNetAssetValue() {
		return netAssetValue;
	}

	public void setNetAssetValue(String netAssetValue) {
		this.netAssetValue = netAssetValue;
	}

	public String getOriginalCost() {
		return originalCost;
	}

	public String getCurrentUnits() {
		return currentUnits;
	}

	public String getCost() {
		return cost;
	}

	public String getDeprnCost() {
		return deprnCost;
	}

	public void setStartCreationDate(String startCreationDate) throws CalendarException{
		this.startCreationDate.setCalendarValue(startCreationDate);
	}

	public void setEndCreationDate(String endCreationDate) throws CalendarException{
		this.endCreationDate.setCalendarValue(endCreationDate);
	}

	public void setDateRetiredStart(String dateRetiredStart) throws CalendarException{
		this.dateRetiredStart.setCalendarValue(dateRetiredStart);
	}

	public void setDateRetiredEnd(String dateRetiredEnd) throws CalendarException{
		this.dateRetiredEnd.setCalendarValue(dateRetiredEnd);
	}

	public void setDateEffectiveStart(String dateEffectiveStart) throws CalendarException{
		this.dateEffectiveStart.setCalendarValue(dateEffectiveStart);
	}

	public void setDateEffectiveEnd(String dateEffectiveEnd) throws CalendarException{
		this.dateEffectiveEnd.setCalendarValue(dateEffectiveEnd);
	}

	public void setStartDisableDate(String startDisableDate) throws CalendarException{
		this.startDisableDate.setCalendarValue(startDisableDate);
	}

	public void setEndDisableDate(String endDisableDate) throws CalendarException{
		this.endDisableDate.setCalendarValue(endDisableDate);
	}

	public void setRentDate(String rentDate)throws CalendarException {
		this.rentDate.setCalendarValue(rentDate);
	}

	public void setStartRentDate(String startRentDate)throws CalendarException{
		this.startRentDate.setCalendarValue(startRentDate);
	}

	public void setEndRentDate(String endRentDate)throws CalendarException {
		this.endRentDate.setCalendarValue(endRentDate);
	}

	public SimpleCalendar getDateRetired() throws CalendarException {
        this.dateRetired.setCalPattern(getCalPattern());
        return dateRetired;
    }

	public void setDateRetired(String dateRetired) throws CalendarException {
		this.dateRetired.setCalendarValue(dateRetired);
	}

	public SimpleCalendar getDateEffective() throws CalendarException{
		this.dateEffective.setCalPattern(getCalPattern());
        return dateEffective;
	}

	public void setDateEffective(String dateEffective) throws CalendarException{
		this.dateEffective.setCalendarValue(dateEffective);
	}

	public String getCostRetired() {
		return costRetired;
	}

	public void setCostRetired(String costRetired) {
		this.costRetired = costRetired;
	}

	public String getRetirementTypeCode() {
		return retirementTypeCode;
	}

	public void setRetirementTypeCode(String retirementTypeCode) {
		this.retirementTypeCode = retirementTypeCode;
	}

	public boolean isChkUser() {
		return chkUser;
	}

	public void setChkUser(boolean chkUser) {
		this.chkUser = chkUser;
	}

	public boolean isChkResponsibilityDept() {
		return chkResponsibilityDept;
	}

	public void setChkResponsibilityDept(boolean chkResponsibilityDept) {
		this.chkResponsibilityDept = chkResponsibilityDept;
	}
}