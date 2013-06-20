package com.sino.ams.newasset.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.calen.SimpleDate;
import com.sino.base.calen.SimpleTime;
import com.sino.base.constant.WorldConstant;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.TimeException;
import com.sino.base.util.StrUtil;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>Title: AMS_ASSETS_ADDRESS_V AmsAssetsAddressV</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsAssetsAddressVDTO extends EtsFaAssetsDTO {

	private String countyName = "";
	private String itemCategoryName = "";
	private String faCategoryName = "";
	private String itemStatusName = "";
	private String transferType = ""; //调拨单的哪一种类型：部门内，部门间，地市间
	private String scrapValue = ""; //残值--财务上应为定值
	private float depreciation; //累计折旧--从开始折旧日起算,按照一定的方法,例如平均折旧法,折旧到财务当期时的累计折旧额
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
    private String deprnLeftMonth ;
    
    private float getDeprnReServe ;
    private float deprnAmount ;
    private float impairAmount ;
    private float ytdImpairment ;
    private float ytdDeprn ;
    
    private String netUnitCode = "" ; //NET_UNIT_CODE
    private String investCatCode = ""; 
    
    private int matchOrganizationId = 0 ;
    
    public AmsAssetsAddressVDTO() {
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

    public void setEndDisableDate(String endDisableDate) throws CalendarException {
		this.endDisableDate.setCalendarValue(endDisableDate);
	}

    public SimpleCalendar getStartDisableDate() throws CalendarException {
		startDisableDate.setCalPattern(getCalPattern());
		return this.startDisableDate;
	}

    public void setStartDisableDate(String startDisableDate) throws CalendarException {
		this.startDisableDate.setCalendarValue(startDisableDate);
	}

    public SimpleCalendar getStartRentDate() throws CalendarException {
		startRentDate.setCalPattern(getCalPattern());
		return this.startRentDate;
	}

    public void setStartRentDate(String startRentDate) throws CalendarException {
		this.startRentDate.setCalendarValue(startRentDate);
	}

    public SimpleCalendar getEndRentDate() throws CalendarException {
		endRentDate.setCalPattern(getCalPattern());
		return this.endRentDate;
	}

    public void setEndRentDate(String endRentDate) throws CalendarException {
		this.endRentDate.setCalendarValue(endRentDate);
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

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 DATE_PLACED_IN_SERVICE
	 * @param startCreationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setStartCreationDate(String startCreationDate) throws CalendarException {
		this.startCreationDate.setCalendarValue(startCreationDate);
	}


	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 DATE_PLACED_IN_SERVICE
	 * @param endCreationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setEndCreationDate(String endCreationDate) throws CalendarException {
		this.endCreationDate.setCalendarValue(endCreationDate);
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

	public void setDepreciation(float depreciation) {
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
	public float getDepreciation() {
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

    public void setRentDate(String rentDate) throws CalendarException {
        this.rentDate.setCalendarValue(rentDate);
    }

	public SimpleCalendar getDateEffectiveEnd() throws CalendarException {
		this.dateEffectiveEnd.setCalPattern(getCalPattern());
		return dateEffectiveEnd;
	}

	public void setDateEffectiveEnd(String dateEffectiveEnd) throws CalendarException {
		this.dateEffectiveEnd.setCalendarValue(dateEffectiveEnd);
	}

	public SimpleCalendar getDateEffectiveStart() throws CalendarException {
		this.dateEffectiveStart.setCalPattern(getCalPattern());
		return dateEffectiveStart;
	}

	public void setDateEffectiveStart(String dateEffectiveStart) throws CalendarException {
		this.dateEffectiveStart.setCalendarValue(dateEffectiveStart);
	}

	public SimpleCalendar getDateRetiredEnd() throws CalendarException {
		this.dateRetiredEnd.setCalPattern(getCalPattern());
		return dateRetiredEnd;
	}

	public void setDateRetiredEnd(String dateRetiredEnd) throws CalendarException {
		this.dateRetiredEnd.setCalendarValue(dateRetiredEnd);
	}

	public SimpleCalendar getDateRetiredStart() throws CalendarException {
		this.dateRetiredStart.setCalPattern(getCalPattern());
		return dateRetiredStart;
	}

	public void setDateRetiredStart(String dateRetiredStart) throws CalendarException {
		this.dateRetiredStart.setCalendarValue(dateRetiredStart);
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

	public float getGetDeprnReServe() {
		return getDeprnReServe;
	}

	public void setGetDeprnReServe(float getDeprnReServe) {
		this.getDeprnReServe = getDeprnReServe;
	}

	public float getDeprnAmount() {
		return deprnAmount ; 
	}

	public void setDeprnAmount(float deprnAmount) {
		this.deprnAmount = deprnAmount;
	}

	public float getImpairAmount() {
		return impairAmount;
	}

	public void setImpairAmount(float impairAmount) {
		this.impairAmount = impairAmount;
	}

	public float getYtdImpairment() {
		return ytdImpairment;
	}

	public void setYtdImpairment(float ytdImpairment) {
		this.ytdImpairment = ytdImpairment;
	}

	public float getYtdDeprn() {
		return ytdDeprn;
	}

	public void setYtdDeprn(float ytdDeprn) {
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

	public int getMatchOrganizationId() {
		return matchOrganizationId;
	}

	public void setMatchOrganizationId(int matchOrganizationId) {
		this.matchOrganizationId = matchOrganizationId;
	}
}