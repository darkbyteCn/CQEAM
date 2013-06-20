package com.sino.soa.util.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: ETS_MISFA_UPDATE_LOG EtsMisfaUpdateLog</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsMisfaUpdateLogDTO extends CheckBoxDTO{

	private String logid = "";
	private String batchId = "";
	private String barcode = "";
	private int assetId = SyBaseSQLUtil.NULL_INT_VALUE;
	private int ifUpdateTagnumber = SyBaseSQLUtil.NULL_INT_VALUE;
	private String tagNumberFrom = "";
	private String tagNumberTo = "";
	private String locationFrom = "";
	private String locationTo = "";
	private String nameFrom = "";
	private String nameTo = "";
	private String modelFrom = "";
	private String modelTo = "";
	private String ownerFrom = "";
	private String ownerTo = "";
	private String updateType = "";
	private int organizationId = 0 ;
	private int transStatus = SyBaseSQLUtil.NULL_INT_VALUE;
	private String transErrormsg = "";
	private SimpleCalendar transDate = null;
	private String remark = "";
	private String costCenterFrom = "";
	private String costCenterTo = "";
	private int codeCombinationId = SyBaseSQLUtil.NULL_INT_VALUE;
	private SimpleCalendar creationDate = null;
	private int createdBy = SyBaseSQLUtil.NULL_INT_VALUE;
	private SimpleCalendar lastUpdateDate = null;
	private int lastUpdateBy = SyBaseSQLUtil.NULL_INT_VALUE;
	private int orgTo = SyBaseSQLUtil.NULL_INT_VALUE;
	private int codeCombinationIdTo = SyBaseSQLUtil.NULL_INT_VALUE;
	private String assetCategoryTo = "";
	private String transactionNo = "";
	private String manufacturerFrom ="";
	private String manufacturerTo ="";
	

	public EtsMisfaUpdateLogDTO() {
		super();
		this.transDate = new SimpleCalendar();
		this.creationDate = new SimpleCalendar();
		this.lastUpdateDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 ETS_MISFA_UPDATE_LOG_s.nextval
	 * @param logid String
	 */
	public void setLogid(String logid){
		this.logid = logid;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 BATCH_ID
	 * @param batchId String
	 */
	public void setBatchId(String batchId){
		this.batchId = batchId;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 ETS_ITEM_INFO.barcode
	 * @param barcode String
	 */
	public void setBarcode(String barcode){
		this.barcode = barcode;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 ASSET_ID
	 * @param assetId String
	 */
	public void setAssetId(int assetId){
		this.assetId = assetId;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 IF_UPDATE_TAGNUMBER
	 * @param ifUpdateTagnumber String
	 */
	public void setIfUpdateTagnumber(int ifUpdateTagnumber){
		this.ifUpdateTagnumber = ifUpdateTagnumber;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 TAG_NUMBER_FROM
	 * @param tagNumberFrom String
	 */
	public void setTagNumberFrom(String tagNumberFrom){
		this.tagNumberFrom = tagNumberFrom;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 TAG_NUMBER_TO
	 * @param tagNumberTo String
	 */
	public void setTagNumberTo(String tagNumberTo){
		this.tagNumberTo = tagNumberTo;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 LOCATION_FROM
	 * @param locationFrom String
	 */
	public void setLocationFrom(String locationFrom){
		this.locationFrom = locationFrom;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 LOCATION_TO
	 * @param locationTo String
	 */
	public void setLocationTo(String locationTo){
		this.locationTo = locationTo;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 NAME_FROM
	 * @param nameFrom String
	 */
	public void setNameFrom(String nameFrom){
		this.nameFrom = nameFrom;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 NAME_TO
	 * @param nameTo String
	 */
	public void setNameTo(String nameTo){
		this.nameTo = nameTo;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 MODEL_FROM
	 * @param modelFrom String
	 */
	public void setModelFrom(String modelFrom){
		this.modelFrom = modelFrom;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 MODEL_TO
	 * @param modelTo String
	 */
	public void setModelTo(String modelTo){
		this.modelTo = modelTo;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 OWNER_FROM
	 * @param ownerFrom String
	 */
	public void setOwnerFrom(String ownerFrom){
		this.ownerFrom = ownerFrom;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 OWNER_TO
	 * @param ownerTo String
	 */
	public void setOwnerTo(String ownerTo){
		this.ownerTo = ownerTo;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 LOCATION：地点变更DESC：资产名称规格型号变更TAG_NUMBER:条码号OWNER：保管人变更ALL:条码号+资产名+型号+地点
	 * @param updateType String
	 */
	public void setUpdateType(String updateType){
		this.updateType = updateType;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 ORGANIZATION_ID
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 0:未处理  1：完成  2：错误
	 * @param transStatus String
	 */
	public void setTransStatus(int transStatus){
		this.transStatus = transStatus;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 TRANS_ERRORMSG
	 * @param transErrormsg String
	 */
	public void setTransErrormsg(String transErrormsg){
		this.transErrormsg = transErrormsg;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 TRANS_DATE
	 * @param transDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setTransDate(String transDate) throws CalendarException{
		this.transDate.setCalendarValue(transDate);
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 REMARK
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 COST_CENTER_FROM
	 * @param costCenterFrom String
	 */
	public void setCostCenterFrom(String costCenterFrom){
		this.costCenterFrom = costCenterFrom;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 COST_CENTER_TO
	 * @param costCenterTo String
	 */
	public void setCostCenterTo(String costCenterTo){
		this.costCenterTo = costCenterTo;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 CODE_COMBINATION_ID
	 * @param codeCombinationId String
	 */
	public void setCodeCombinationId(int codeCombinationId){
		this.codeCombinationId = codeCombinationId;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 创建日期
	 * @param creationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		this.creationDate.setCalendarValue(creationDate);
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 创建人
	 * @param createdBy String
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 上次修改日期
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 上次修改人
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 ORG_TO
	 * @param orgTo String
	 */
	public void setOrgTo(int orgTo){
		this.orgTo = orgTo;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 CODE_COMBINATION_ID_TO
	 * @param codeCombinationIdTo String
	 */
	public void setCodeCombinationIdTo(int codeCombinationIdTo){
		this.codeCombinationIdTo = codeCombinationIdTo;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 ASSET_CATEGORY_TO
	 * @param assetCategoryTo String
	 */
	public void setAssetCategoryTo(String assetCategoryTo){
		this.assetCategoryTo = assetCategoryTo;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_LOG属性 存放调拨单号或报废单编号
	 * @param transactionNo String
	 */
	public void setTransactionNo(String transactionNo){
		this.transactionNo = transactionNo;
	}


	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 ETS_MISFA_UPDATE_LOG_s.nextval
	 * @return String
	 */
	public String getLogid() {
		return this.logid;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 BATCH_ID
	 * @return String
	 */
	public String getBatchId() {
		return this.batchId;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 ETS_ITEM_INFO.barcode
	 * @return String
	 */
	public String getBarcode() {
		return this.barcode;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 ASSET_ID
	 * @return String
	 */
	public int getAssetId() {
		return this.assetId;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 IF_UPDATE_TAGNUMBER
	 * @return String
	 */
	public int getIfUpdateTagnumber() {
		return this.ifUpdateTagnumber;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 TAG_NUMBER_FROM
	 * @return String
	 */
	public String getTagNumberFrom() {
		return this.tagNumberFrom;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 TAG_NUMBER_TO
	 * @return String
	 */
	public String getTagNumberTo() {
		return this.tagNumberTo;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 LOCATION_FROM
	 * @return String
	 */
	public String getLocationFrom() {
		return this.locationFrom;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 LOCATION_TO
	 * @return String
	 */
	public String getLocationTo() {
		return this.locationTo;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 NAME_FROM
	 * @return String
	 */
	public String getNameFrom() {
		return this.nameFrom;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 NAME_TO
	 * @return String
	 */
	public String getNameTo() {
		return this.nameTo;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 MODEL_FROM
	 * @return String
	 */
	public String getModelFrom() {
		return this.modelFrom;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 MODEL_TO
	 * @return String
	 */
	public String getModelTo() {
		return this.modelTo;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 OWNER_FROM
	 * @return String
	 */
	public String getOwnerFrom() {
		return this.ownerFrom;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 OWNER_TO
	 * @return String
	 */
	public String getOwnerTo() {
		return this.ownerTo;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 LOCATION：地点变更DESC：资产名称规格型号变更TAG_NUMBER:条码号OWNER：保管人变更ALL:条码号+资产名+型号+地点
	 * @return String
	 */
	public String getUpdateType() {
		return this.updateType;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 ORGANIZATION_ID
	 * @return String
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 0:未处理  1：完成  2：错误
	 * @return String
	 */
	public int getTransStatus() {
		return this.transStatus;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 TRANS_ERRORMSG
	 * @return String
	 */
	public String getTransErrormsg() {
		return this.transErrormsg;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 TRANS_DATE
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getTransDate() throws CalendarException {
		transDate.setCalPattern(getCalPattern());
		return this.transDate;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 REMARK
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 COST_CENTER_FROM
	 * @return String
	 */
	public String getCostCenterFrom() {
		return this.costCenterFrom;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 COST_CENTER_TO
	 * @return String
	 */
	public String getCostCenterTo() {
		return this.costCenterTo;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 CODE_COMBINATION_ID
	 * @return String
	 */
	public int getCodeCombinationId() {
		return this.codeCombinationId;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 创建日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getCreationDate() throws CalendarException {
		creationDate.setCalPattern(getCalPattern());
		return this.creationDate;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 创建人
	 * @return String
	 */
	public int getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 上次修改日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastUpdateDate() throws CalendarException {
		lastUpdateDate.setCalPattern(getCalPattern());
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 上次修改人
	 * @return String
	 */
	public int getLastUpdateBy() {
		return this.lastUpdateBy;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 ORG_TO
	 * @return String
	 */
	public int getOrgTo() {
		return this.orgTo;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 CODE_COMBINATION_ID_TO
	 * @return String
	 */
	public int getCodeCombinationIdTo() {
		return this.codeCombinationIdTo;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 ASSET_CATEGORY_TO
	 * @return String
	 */
	public String getAssetCategoryTo() {
		return this.assetCategoryTo;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_LOG属性 存放调拨单号或报废单编号
	 * @return String
	 */
	public String getTransactionNo() {
		return this.transactionNo;
	}

	/**
	 * @return the manufacturerFrom
	 */
	public String getManufacturerFrom() {
		return manufacturerFrom;
	}

	/**
	 * @param manufacturerFrom the manufacturerFrom to set
	 */
	public void setManufacturerFrom(String manufacturerFrom) {
		this.manufacturerFrom = manufacturerFrom;
	}

	/**
	 * @return the manufacturerTo
	 */
	public String getManufacturerTo() {
		return manufacturerTo;
	}

	/**
	 * @param manufacturerTo the manufacturerTo to set
	 */
	public void setManufacturerTo(String manufacturerTo) {
		this.manufacturerTo = manufacturerTo;
	}

}