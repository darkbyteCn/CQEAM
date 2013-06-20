package com.sino.ams.match.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-12-5
 * Time: 10:54:58
 * To change this template use File | Settings | File Templates.
 */
public class HideMisAssetDTO extends CheckBoxDTO {

    private String systemid = "";
    private int itemAttr;
    private int assetId;
    private int quantity;
    private int batchid;
    private SimpleCalendar matchDate = null;
    private int flag;
    private int userId;
    private SimpleCalendar creationDate = null;
    private int createdBy;
    private SimpleCalendar lastUpdateDate = null;
    private int lastUpdateBy;
    private int organizationId;
    private String barcode = "";
    private String tagNumber = "";
    private int projectId;
    private String workorderObjectNo = "";
    private String workorderObjectLocation = "";
    private String itemName = "";
    private String itemSpec = "";
    private String financeProp = "";
    private String oldFinanceProp = "";
    private String newFinanceProp = "";
    private String matchType = "";
    private String segment1 = "";
    private String isMatch = "";
    private String workorderObjectName = "";
    private String domethod = "";
    private String countyCode = "";
    private String reMark = "";
    private String assetsDescription ="";
    private String assetsLocation ="";

    public String getAssetsDescription() {
        return assetsDescription;
    }

    public void setAssetsDescription(String assetsDescription) {
        this.assetsDescription = assetsDescription;
    }

    public String getAssetsLocation() {
        return assetsLocation;
    }

    public void setAssetsLocation(String assetsLocation) {
        this.assetsLocation = assetsLocation;
    }

    public String getReMark() {
        return reMark;
    }

    public void setReMark(String reMark) {
        this.reMark = reMark;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getDomethod() {
        return domethod;
    }

    public void setDomethod(String domethod) {
        this.domethod = domethod;
    }

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }

    public String getMatch() {
        return isMatch;
    }

    public void setMatch(String match) {
        isMatch = match;
    }

    public String getSegment1() {
        return segment1;
    }

    public void setSegment1(String segment1) {
        this.segment1 = segment1;
    }
    public String getOldFinanceProp() {
        return oldFinanceProp;
    }

    public void setOldFinanceProp(String oldFinanceProp) {
        this.oldFinanceProp = oldFinanceProp;
    }

    public String getNewFinanceProp() {
        return newFinanceProp;
    }

    public void setNewFinanceProp(String newFinanceProp) {
        this.newFinanceProp = newFinanceProp;
    }


    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }


    public String getFinanceProp() {
        return financeProp;
    }

    public void setFinanceProp(String financeProp) {
        this.financeProp = financeProp;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public String getWorkorderObjectNo() {
        return workorderObjectNo;
    }

    public void setWorkorderObjectNo(String workorderObjectNo) {
        this.workorderObjectNo = workorderObjectNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getWorkorderObjectLocation() {
        return workorderObjectLocation;
    }

    public void setWorkorderObjectLocation(String workorderObjectLocation) {
        this.workorderObjectLocation = workorderObjectLocation;
    }


    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public HideMisAssetDTO() {
        super();
        this.matchDate = new SimpleCalendar();
        this.creationDate = new SimpleCalendar();
        this.lastUpdateDate = new SimpleCalendar();
    }


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 功能：设置资产匹配-匹配数据存储(EAM)属性 SYSTEMID
     *
     * @param systemid String
     */
    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    /**
     * 功能：设置资产匹配-匹配数据存储(EAM)属性 ITEM_ATTR
     *
     * @param itemAttr String
     */
    public void setItemAttr(int itemAttr) {
        this.itemAttr = itemAttr;
    }

    /**
     * 功能：设置资产匹配-匹配数据存储(EAM)属性 ASSET_ID
     *
     * @param assetId String
     */
    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    /**
     * 功能：设置资产匹配-匹配数据存储(EAM)属性 QUANTITY
     *
     * @param quantity String
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * 功能：设置资产匹配-匹配数据存储(EAM)属性 BATCHID
     *
     * @param batchid String
     */
    public void setBatchid(int batchid) {
        this.batchid = batchid;
    }

    /**
     * 功能：设置资产匹配-匹配数据存储(EAM)属性 MATCH_DATE
     *
     * @param matchDate SimpleCalendar
     * @throws com.sino.base.exception.CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setMatchDate(String matchDate) throws CalendarException {
        this.matchDate.setCalendarValue(matchDate);
    }

    /**
     * 功能：设置资产匹配-匹配数据存储(EAM)属性 FLAG
     *
     * @param flag String
     */
    public void setFlag(int flag) {
        this.flag = flag;
    }

    /**
     * 功能：设置资产匹配-匹配数据存储(EAM)属性 USER_ID
     *
     * @param userId String
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 功能：设置资产匹配-匹配数据存储(EAM)属性 创建日期
     *
     * @param creationDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException {
        this.creationDate.setCalendarValue(creationDate);
    }

    /**
     * 功能：设置资产匹配-匹配数据存储(EAM)属性 创建人
     *
     * @param createdBy String
     */
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 功能：设置资产匹配-匹配数据存储(EAM)属性 上次修改日期
     *
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        this.lastUpdateDate.setCalendarValue(lastUpdateDate);
    }

    /**
     * 功能：设置资产匹配-匹配数据存储(EAM)属性 上次修改人
     *
     * @param lastUpdateBy String
     */
    public void setLastUpdateBy(int lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * 功能：设置资产匹配-匹配数据存储(EAM)属性 上次修改人
     *
     * @param organizationId String
     */
    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 功能：获取资产匹配-匹配数据存储(EAM)属性 SYSTEMID
     *
     * @return String
     */
    public String getSystemid() {
        return this.systemid;
    }

    /**
     * 功能：获取资产匹配-匹配数据存储(EAM)属性 ITEM_ATTR
     *
     * @return String
     */
    public int getItemAttr() {
        return this.itemAttr;
    }

    /**
     * 功能：获取资产匹配-匹配数据存储(EAM)属性 ASSET_ID
     *
     * @return String
     */
    public int getAssetId() {
        return this.assetId;
    }

    /**
     * 功能：获取资产匹配-匹配数据存储(EAM)属性 QUANTITY
     *
     * @return String
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * 功能：获取资产匹配-匹配数据存储(EAM)属性 BATCHID
     *
     * @return String
     */
    public int getBatchid() {
        return this.batchid;
    }

    /**
     * 功能：获取资产匹配-匹配数据存储(EAM)属性 MATCH_DATE
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getMatchDate() throws CalendarException {
        matchDate.setCalPattern(getCalPattern());
        return this.matchDate;
    }

    /**
     * 功能：获取资产匹配-匹配数据存储(EAM)属性 FLAG
     *
     * @return String
     */
    public int getFlag() {
        return this.flag;
    }

    /**
     * 功能：获取资产匹配-匹配数据存储(EAM)属性 USER_ID
     *
     * @return String
     */
    public int getUserId() {
        return this.userId;
    }

    /**
     * 功能：获取资产匹配-匹配数据存储(EAM)属性 创建日期
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getCreationDate() throws CalendarException {
        creationDate.setCalPattern(getCalPattern());
        return this.creationDate;
    }

    /**
     * 功能：获取资产匹配-匹配数据存储(EAM)属性 创建人
     *
     * @return String
     */
    public int getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 功能：获取资产匹配-匹配数据存储(EAM)属性 上次修改日期
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getLastUpdateDate() throws CalendarException {
        lastUpdateDate.setCalPattern(getCalPattern());
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取资产匹配-匹配数据存储(EAM)属性 上次修改人
     *
     * @return String
     */
    public int getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    /**
     * 功能：获取资产匹配-匹配数据存储(EAM)属性 上次修改人
     *
     * @return String
     */
    public int getOrganizationId() {
        return organizationId;
    }
}
