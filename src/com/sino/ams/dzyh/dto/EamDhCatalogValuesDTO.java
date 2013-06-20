package com.sino.ams.dzyh.dto;

/**
* <p>Title: 低值易耗品目录维护表(EAM) EamDhCatalogValues</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 张星
* @version 1.0
*/

public class EamDhCatalogValuesDTO extends EamDhCatalogSetDTO{

	private String catalogValueId = "";
	private String catalogCode = "";
	private int catalogSetId = 0;
	private String catalogName = "";
	private int unit = 0;
	private String description = "";
	private int barcodeFlag = 0;
	private int commonFlag = 0;
	private String attribute1 = "";
	private String attribute2 = "";

	private String unitValue="";	//计量单位

	public String getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}

	/**
	 * 功能：设置低值易耗品目录维护表(EAM)属性 EAM_DH_CATALOG_VALUES_S.NEXTVAL
	 * @param catalogValueId String
	 */
	public void setCatalogValueId(String catalogValueId){
		this.catalogValueId = catalogValueId;
	}

	/**
	 * 功能：设置低值易耗品目录维护表(EAM)属性 目录编号
	 * @param catalogCode String
	 */
	public void setCatalogCode(String catalogCode){
		this.catalogCode = catalogCode;
	}

	/**
	 * 功能：设置低值易耗品目录维护表(EAM)属性 EAM_DH_CATALOG_SET. CATLOG_SET_ID
	 * @param catalogSetId String
	 */
	public void setCatalogSetId(int catalogSetId){
		this.catalogSetId = catalogSetId;
	}

	/**
	 * 功能：设置低值易耗品目录维护表(EAM)属性 品名
	 * @param catalogName String
	 */
	public void setCatalogName(String catalogName){
		this.catalogName = catalogName;
	}

	/**
	 * 功能：设置低值易耗品目录维护表(EAM)属性 计量单位
	 * @param unit String
	 */
	public void setUnit(int unit){
		this.unit = unit;
	}

	/**
	 * 功能：设置低值易耗品目录维护表(EAM)属性 注明
	 * @param description String
	 */
	public void setDescription(String description){
		this.description = description;
	}

	/**
	 * 功能：设置低值易耗品目录维护表(EAM)属性 条码标识
	 * @param barcodeFlag String
	 */
	public void setBarcodeFlag(int barcodeFlag){
		this.barcodeFlag = barcodeFlag;
	}

	/**
	 * 功能：设置低值易耗品目录维护表(EAM)属性 常用标识
	 * @param commonFlag String
	 */
	public void setCommonFlag(int commonFlag){
		this.commonFlag = commonFlag;
	}

	/**
	 * 功能：设置低值易耗品目录维护表(EAM)属性 备用字段1
	 * @param attribute1 String
	 */
	public void setAttribute1(String attribute1){
		this.attribute1 = attribute1;
	}

	/**
	 * 功能：设置低值易耗品目录维护表(EAM)属性 备用字段2
	 * @param attribute2 String
	 */
	public void setAttribute2(String attribute2){
		this.attribute2 = attribute2;
	}

	/**
	 * 功能：获取低值易耗品目录维护表(EAM)属性 EAM_DH_CATALOG_VALUES_S.NEXTVAL
	 * @return String
	 */
	public String getCatalogValueId() {
		return this.catalogValueId;
	}

	/**
	 * 功能：获取低值易耗品目录维护表(EAM)属性 目录编号
	 * @return String
	 */
	public String getCatalogCode() {
		return this.catalogCode;
	}

	/**
	 * 功能：获取低值易耗品目录维护表(EAM)属性 EAM_DH_CATALOG_SET. CATLOG_SET_ID
	 * @return String
	 */
	public int getCatalogSetId() {
		return this.catalogSetId;
	}

	/**
	 * 功能：获取低值易耗品目录维护表(EAM)属性 品名
	 * @return String
	 */
	public String getCatalogName() {
		return this.catalogName;
	}

	/**
	 * 功能：获取低值易耗品目录维护表(EAM)属性 计量单位
	 * @return String
	 */
	public int getUnit() {
		return this.unit;
	}

	/**
	 * 功能：获取低值易耗品目录维护表(EAM)属性 注明
	 * @return String
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 功能：获取低值易耗品目录维护表(EAM)属性 条码标识
	 * @return String
	 */
	public int getBarcodeFlag() {
		return this.barcodeFlag;
	}

	/**
	 * 功能：获取低值易耗品目录维护表(EAM)属性 常用标识
	 * @return String
	 */
	public int getCommonFlag() {
		return this.commonFlag;
	}

	/**
	 * 功能：获取低值易耗品目录维护表(EAM)属性 备用字段1
	 * @return String
	 */
	public String getAttribute1() {
		return this.attribute1;
	}

	/**
	 * 功能：获取低值易耗品目录维护表(EAM)属性 备用字段2
	 * @return String
	 */
	public String getAttribute2() {
		return this.attribute2;
	}
}
