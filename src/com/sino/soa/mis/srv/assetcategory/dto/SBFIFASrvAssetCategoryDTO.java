package com.sino.soa.mis.srv.assetcategory.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-6
 * Time: 17:15:59
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFASrvAssetCategoryDTO extends CheckBoxDTO {

    private String categoryId = null;
    private String description = "";
    private String categoryType = "";
    private String segment1 = "";
    private String segment2 = "";
    private String segment3 = "";
    private String assetCostAccountCcid = null;
    private String reserveAccountCcid = null;
    private String assetClearingAccountCcid = null;
    private String lifeInMonths = null;
    private String percentSalvageValue = null;
    private String enabledFlag = "";
    private String attribute1 = "";
    private String inventorial = "";
    private String capitalizeFlag = "";
    private String bookTypeCode = "";
    private SimpleCalendar lastUpdateDate = null;
    private String orgOption="";
    private String concatenatedSegments ="";
    private String assetsType ="";

    public SBFIFASrvAssetCategoryDTO() {
        super();
        this.lastUpdateDate = new SimpleCalendar();
    }

    /**
     * 功能：设置资产类别服务属性 资产类别ID
     * @param categoryId String
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 功能：设置资产类别服务属性 类别描述
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 功能：设置资产类别服务属性 分类类型"NON-LEASE"非租赁等
     * @param categoryType String
     */
    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    /**
     * 功能：设置资产类别服务属性 应用领域
     * @param segment1 String
     */
    public void setSegment1(String segment1) {
        this.segment1 = segment1;
    }

    /**
     * 功能：设置资产类别服务属性 目录分类
     * @param segment2 String
     */
    public void setSegment2(String segment2) {
        this.segment2 = segment2;
    }

    /**
     * 功能：设置资产类别服务属性 名称
     * @param segment3 String
     */
    public void setSegment3(String segment3) {
        this.segment3 = segment3;
    }

    /**
     * 功能：设置资产类别服务属性 资产原值科目
     * @param assetCostAccountCcid String
     */
    public void setAssetCostAccountCcid(String assetCostAccountCcid) {
        this.assetCostAccountCcid = assetCostAccountCcid;
    }

    /**
     * 功能：设置资产类别服务属性 资产折旧科目
     * @param reserveAccountCcid String
     */
    public void setReserveAccountCcid(String reserveAccountCcid) {
        this.reserveAccountCcid = reserveAccountCcid;
    }

    /**
     * 功能：设置资产类别服务属性 资产结算科目
     * @param assetClearingAccountCcid String
     */
    public void setAssetClearingAccountCcid(String assetClearingAccountCcid) {
        this.assetClearingAccountCcid = assetClearingAccountCcid;
    }

    /**
     * 功能：设置资产类别服务属性 折旧年限
     * @param lifeInMonths String
     */
    public void setLifeInMonths(String lifeInMonths) {
        this.lifeInMonths = lifeInMonths;
    }

    /**
     * 功能：设置资产类别服务属性 残值率
     * @param percentSalvageValue String
     */
    public void setPercentSalvageValue(String percentSalvageValue) {
        this.percentSalvageValue = percentSalvageValue;
    }

    /**
     * 功能：设置资产类别服务属性 是否启用
     * @param enabledFlag String
     */
    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    /**
     * 功能：设置资产类别服务属性 计量单位
     * @param attribute1 String
     */
    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    /**
     * 功能：设置资产类别服务属性 是否实地盘存
     * @param inventorial String
     */
    public void setInventorial(String inventorial) {
        this.inventorial = inventorial;
    }

    /**
     * 功能：设置资产类别服务属性 是否资本化
     * @param capitalizeFlag String
     */
    public void setCapitalizeFlag(String capitalizeFlag) {
        this.capitalizeFlag = capitalizeFlag;
    }

    /**
     * 功能：设置资产类别服务属性 资产账套
     * @param bookTypeCode String
     */
    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    /**
     * 功能：设置资产类别服务属性 最新更新时间
     * @param lastUpdateDate SimpleCalendar
     * @throws com.sino.base.exception.CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        this.lastUpdateDate.setCalendarValue(lastUpdateDate);
    }


    /**
     * 功能：获取资产类别服务属性 资产类别ID
     * @return String
     */
    public String getCategoryId() {
        return this.categoryId;
    }

    /**
     * 功能：获取资产类别服务属性 类别描述
     * @return String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 功能：获取资产类别服务属性 分类类型"NON-LEASE"非租赁等
     * @return String
     */
    public String getCategoryType() {
        return this.categoryType;
    }

    /**
     * 功能：获取资产类别服务属性 应用领域
     * @return String
     */
    public String getSegment1() {
        return this.segment1;
    }

    /**
     * 功能：获取资产类别服务属性 目录分类
     * @return String
     */
    public String getSegment2() {
        return this.segment2;
    }

    /**
     * 功能：获取资产类别服务属性 名称
     * @return String
     */
    public String getSegment3() {
        return this.segment3;
    }

    /**
     * 功能：获取资产类别服务属性 资产原值科目
     * @return String
     */
    public String getAssetCostAccountCcid() {
        return this.assetCostAccountCcid;
    }

    /**
     * 功能：获取资产类别服务属性 资产折旧科目
     * @return String
     */
    public String getReserveAccountCcid() {
        return this.reserveAccountCcid;
    }

    /**
     * 功能：获取资产类别服务属性 资产结算科目
     * @return String
     */
    public String getAssetClearingAccountCcid() {
        return this.assetClearingAccountCcid;
    }

    /**
     * 功能：获取资产类别服务属性 折旧年限
     * @return String
     */
    public String getLifeInMonths() {
        return this.lifeInMonths;
    }

    /**
     * 功能：获取资产类别服务属性 残值率
     * @return String
     */
    public String getPercentSalvageValue() {
        return this.percentSalvageValue;
    }

    /**
     * 功能：获取资产类别服务属性 是否启用
     * @return String
     */
    public String getEnabledFlag() {
        return this.enabledFlag;
    }

    /**
     * 功能：获取资产类别服务属性 计量单位
     * @return String
     */
    public String getAttribute1() {
        return this.attribute1;
    }

    /**
     * 功能：获取资产类别服务属性 是否实地盘存
     * @return String
     */
    public String getInventorial() {
        return this.inventorial;
    }

    /**
     * 功能：获取资产类别服务属性 是否资本化
     * @return String
     */
    public String getCapitalizeFlag() {
        return this.capitalizeFlag;
    }

    /**
     * 功能：获取资产类别服务属性 资产账套
     * @return String
     */
    public String getBookTypeCode() {
        return this.bookTypeCode;
    }

    /**
     * 功能：获取资产类别服务属性 最新更新时间
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getLastUpdateDate() throws CalendarException {
        lastUpdateDate.setCalPattern(getCalPattern());
        return this.lastUpdateDate;
	}

	/**
	 * @return the orgOption
	 */
	public String getOrgOption() {
		return orgOption;
	}

	/**
	 * @param orgOption the orgOption to set
	 */
	public void setOrgOption(String orgOption) {
		this.orgOption = orgOption;
	}

	public String getConcatenatedSegments() {
		return concatenatedSegments;
	}

	public void setConcatenatedSegments(String concatenatedSegments) {
		this.concatenatedSegments = concatenatedSegments;
	}

	public String getAssetsType() {
		return assetsType;
	}

	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}

}