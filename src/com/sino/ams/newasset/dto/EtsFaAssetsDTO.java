package com.sino.ams.newasset.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
 * <p>Title: MIS固定资产表(EAM) EtsFaAssets</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class EtsFaAssetsDTO extends EtsItemInfoDTO {

    private int assetId = 0;
    private String faCategory1 = "";
    private String faCategory2 = "";
    private String assetsDescription = "";
    private String faCategoryCode = "";
    private String unitOfMeasure = "";
    private int lifeInYears;
    private String modelNumber = "";
    private String tagNumber = "";
    private float currentUnits;
    private String assetsLocation = "";
    private SimpleCalendar datePlacedInService = null;
    private int isRetirements;
    private String assetNumber = "";
    private float cost;
    private float deprnCost;
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
    private int codeCombinationId;
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
    private float originalCost;
    private SimpleCalendar fromDate = null;
    private SimpleCalendar toDate = null;
    private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;

    private String assetsStatusDes = "";
    private float impairReserve ;
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

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public EtsFaAssetsDTO() {
        datePlacedInService = new SimpleCalendar();
        assetsCreateDate = new SimpleCalendar();
        this.fromDate = new SimpleCalendar();
        this.toDate = new SimpleCalendar();
        this.dateRetired = new SimpleCalendar();
        this.dateEffective = new SimpleCalendar();
        setPrimaryKeyName("assetId");
    }

    public String getNetAssetValue() {
        return netAssetValue;
    }

    public void setNetAssetValue(String netAssetValue) {
        this.netAssetValue = netAssetValue;
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

    /**
     * 功能：设置固定资产当前信息(EAM)属性 资产ID
     * @param assetId String
     */
    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 一级分类
     * @param faCategory1 String
     */
    public void setFaCategory1(String faCategory1) {
        this.faCategory1 = faCategory1;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 二级分类
     * @param faCategory2 String
     */
    public void setFaCategory2(String faCategory2) {
        this.faCategory2 = faCategory2;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 资产描述
     * @param assetsDescription String
     */
    public void setAssetsDescription(String assetsDescription) {
        this.assetsDescription = assetsDescription;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 资产分类代码
     * @param faCategoryCode String
     */
    public void setFaCategoryCode(String faCategoryCode) {
        this.faCategoryCode = faCategoryCode;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 单位
     * @param unitOfMeasure String
     */
    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 年限
     * @param lifeInYears String
     */
    public void setLifeInYears(int lifeInYears) {
        this.lifeInYears = lifeInYears;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 型号
     * @param modelNumber String
     */
    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 标签号
     * @param tagNumber String
     */
    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 数量
     * @param currentUnits String
     */
    public void setCurrentUnits(int currentUnits) {
        this.currentUnits = currentUnits;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 资产地点
     * @param assetsLocation String
     */
    public void setAssetsLocation(String assetsLocation) {
        this.assetsLocation = assetsLocation;
    }


    /**
     * 功能：设置固定资产当前信息(EAM)属性 启用日期--作查询用
     * @param datePlacedInService SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setDatePlacedInService(String datePlacedInService) throws
            CalendarException {
        this.datePlacedInService.setCalendarValue(datePlacedInService);
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 是否报废   1：报废
     * @param isRetirements String
     */
    public void setIsRetirements(int isRetirements) {
        this.isRetirements = isRetirements;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 资产编号
     * @param assetNumber String
     */
    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 原值
     * @param cost String
     */
    public void setCost(float cost) {
        this.cost = cost;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 残值
     * @param deprnCost String
     */
    public void setDeprnCost(float deprnCost) {
        this.deprnCost = deprnCost;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 资产状态  0：正常
     * @param assetsStatus String
     */
    public void setAssetsStatus(int assetsStatus) {
        this.assetsStatus = assetsStatus;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 资产账簿code
     * @param bookTypeCode String
     */
    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 责任人
     * @param assignedToName String
     */
    public void setAssignedToName(String assignedToName) {
        this.assignedToName = assignedToName;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 null
     * @param assignedToNumber String
     */
    public void setAssignedToNumber(String assignedToNumber) {
        this.assignedToNumber = assignedToNumber;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 null
     * @param segment1 String
     */
    public void setSegment1(String segment1) {
        this.segment1 = segment1;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 null
     * @param segment2 String
     */
    public void setSegment2(String segment2) {
        this.segment2 = segment2;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 null
     * @param transToMis String
     */
    public void setTransToMis(int transToMis) {
        this.transToMis = transToMis;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 null
     * @param transToMisDesc String
     */
    public void setTransToMisDesc(int transToMisDesc) {
        this.transToMisDesc = transToMisDesc;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 null
     * @param transToMisTag String
     */
    public void setTransToMisTag(int transToMisTag) {
        this.transToMisTag = transToMisTag;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 null
     * @param transToMisLoc String
     */
    public void setTransToMisLoc(int transToMisLoc) {
        this.transToMisLoc = transToMisLoc;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 null
     * @param codeCombinationId String
     */
    public void setCodeCombinationId(int codeCombinationId) {
        this.codeCombinationId = codeCombinationId;
    }

    /**
     * 功能：设置固定资产当前信息(EAM)属性 公司代码
     * @param companyCode String
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public void setTreeCategory(String treeCategory) {
        this.treeCategory = treeCategory;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public void setAssetsCategory(String assetsCategory) {
        this.assetsCategory = assetsCategory;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 资产ID
     * @return String
     */
    public int getAssetId() {
        return this.assetId;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 一级分类
     * @return String
     */
    public String getFaCategory1() {
        return this.faCategory1;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 二级分类
     * @return String
     */
    public String getFaCategory2() {
        return this.faCategory2;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 资产描述
     * @return String
     */
    public String getAssetsDescription() {
        return this.assetsDescription;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 资产分类代码
     * @return String
     */
    public String getFaCategoryCode() {
        return this.faCategoryCode;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 单位
     * @return String
     */
    public String getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 年限
     * @return String
     */
    public int getLifeInYears() {
        return this.lifeInYears;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 型号
     * @return String
     */
    public String getModelNumber() {
        return this.modelNumber;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 标签号
     * @return String
     */
    public String getTagNumber() {
        return this.tagNumber;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 数量
     * @return String
     */
    public float getCurrentUnits() {
        return this.currentUnits;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 资产地点
     * @return String
     */
    public String getAssetsLocation() {
        return this.assetsLocation;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 启用日期
     * @return SimpleCalendar
     * @throws CalendarException
     */
    public SimpleCalendar getDatePlacedInService() throws CalendarException {
        datePlacedInService.setCalPattern(getCalPattern());
        return this.datePlacedInService;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 是否报废   1：报废
     * @return String
     */
    public int getIsRetirements() {
        return this.isRetirements;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 资产编号
     * @return String
     */
    public String getAssetNumber() {
        return this.assetNumber;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 原值
     * @return String
     */
    public float getCost() {
        return this.cost;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 残值
     * @return String
     */
    public float getDeprnCost() {
        return this.deprnCost;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 资产状态  0：正常
     * @return String
     */
    public int getAssetsStatus() {
        return this.assetsStatus;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 资产账簿code
     * @return String
     */
    public String getBookTypeCode() {
        return this.bookTypeCode;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 责任人
     * @return String
     */
    public String getAssignedToName() {
        return this.assignedToName;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 null
     * @return String
     */
    public String getAssignedToNumber() {
        return this.assignedToNumber;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 null
     * @return String
     */
    public String getSegment1() {
        return this.segment1;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 null
     * @return String
     */
    public String getSegment2() {
        return this.segment2;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 null
     * @return String
     */
    public int getTransToMis() {
        return this.transToMis;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 null
     * @return String
     */
    public int getTransToMisDesc() {
        return this.transToMisDesc;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 null
     * @return String
     */
    public int getTransToMisTag() {
        return this.transToMisTag;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 null
     * @return String
     */
    public int getTransToMisLoc() {
        return this.transToMisLoc;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 null
     * @return String
     */
    public int getCodeCombinationId() {
        return this.codeCombinationId;
    }

    /**
     * 功能：获取固定资产当前信息(EAM)属性 公司代码
     * @return String
     */
    public String getCompanyCode() {
        return this.companyCode;
    }

    public String getTreeCategory() {
        return treeCategory;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getExportType() {
        return exportType;
    }

    public String getAssetsCategory() {
        return assetsCategory;
    }

    public int getRetirements() {
        return isRetirements;
    }

    public void setRetirements(int retirements) {
        isRetirements = retirements;
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


    public String getDepreciationAccount() {
        return depreciationAccount;
    }

    public void setDepreciationAccount(String depreciationAccount) {
        this.depreciationAccount = depreciationAccount;
    }

    public void setAssetsCreateDate(String assetsCreateDate) throws CalendarException {
        this.assetsCreateDate.setCalendarValue(assetsCreateDate);
    }

    public void setDepreciationAccountName(String depreciationAccountName) {
        this.depreciationAccountName = depreciationAccountName;
    }

    public SimpleCalendar getAssetsCreateDate() throws CalendarException {
        assetsCreateDate.setCalPattern(getCalPattern());
        return assetsCreateDate;
    }

    public String getDepreciationAccountName() {
        return depreciationAccountName;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    public void setMisTagNumber(String misTagNumber) {
        this.misTagNumber = misTagNumber;
    }

    public void setOriginalCost(int originalCost) {
        this.originalCost = originalCost;
    }

    public String getBookTypeName() {
        return bookTypeName;
    }

    public String getMisTagNumber() {
        return misTagNumber;
    }

    public float getOriginalCost() {
        return originalCost;
    }
//      public SimpleCalendar getStartDate()throws CalendarException{
//       startDate.setCalPattern(getCalPattern());
//        return  this.startDate;
//    }
//     public void setStartDate()throws CalendarException{
//        this.startDate.setCalendarValue(startDate);
//    }
//
//       public SimpleCalendar getEndDate()throws CalendarException{
//       endDate.setCalPattern(getCalPattern());
//        return  this.endDate;
//    }
//     public void setEndDate()throws CalendarException{
//        this.endDate.setCalendarValue(endDate);

    //    }
    public void setFromDate(String fromDate) throws CalendarException {
        this.fromDate.setCalendarValue(fromDate);
    }

    public SimpleCalendar getFromDate() throws CalendarException {
        fromDate.setCalPattern(getCalPattern());
        return this.fromDate;
    }

    public void setToDate(String toDate) throws CalendarException {
        this.toDate.setCalendarValue(toDate);
    }

    public SimpleCalendar getToDate() throws CalendarException {
        toDate.setCalPattern(getCalPattern());
        return this.toDate;
    }

    /**
     * @return the assetsStatusDes
     */
    public String getAssetsStatusDes() {
        return assetsStatusDes;
    }

    /**
     * @param assetsStatusDes the assetsStatusDes to set
     */
    public void setAssetsStatusDes(String assetsStatusDes) {
        this.assetsStatusDes = assetsStatusDes;
    }

    /**
     * @return the impairReserve
     */
    public float getImpairReserve() {
        return impairReserve;
    }

    /**
     * @param impairReserve the impairReserve to set
     */
    public void setImpairReserve(float impairReserve) {
        this.impairReserve = impairReserve;
    }

    /**
     * @return the recycleValue
     */
    public String getRecycleValue() {
        return recycleValue;
    }

    /**
     * @param recycleValue the recycleValue to set
     */
    public void setRecycleValue(String recycleValue) {
        this.recycleValue = recycleValue;
    }

    public String getCostRetired() {
        return costRetired;
    }

    public void setCostRetired(String costRetired) {
        this.costRetired = costRetired;
    }

    public SimpleCalendar getDateEffective() throws CalendarException {
        this.dateEffective.setCalPattern(getCalPattern());
        return dateEffective;
    }

    public void setDateEffective(String dateEffective) throws CalendarException {
        this.dateEffective.setCalendarValue(dateEffective);
    }

    public SimpleCalendar getDateRetired() throws CalendarException {
        this.dateRetired.setCalPattern(getCalPattern());
        return dateRetired;
    }

    public void setDateRetired(String dateRetired) throws CalendarException {
        this.dateRetired.setCalendarValue(dateRetired);
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
