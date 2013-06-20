package com.sino.ams.system.dict.dto;

import java.sql.Timestamp;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
* <p>Title: 字典分类表(AMS) EtsFlexValues</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsFlexValuesDTO extends CheckBoxDTO{

	private String flexValueId = "";
	private String flexValueSetId = "";
	private String flexValueSetName = "";
	private String code = "";
	private String value = "";
	private String description = "";
	private String enabled = "Y";
	private String attribute1 = "";
	private String attribute2 = "";
	private String isInner = "Y";
	private Timestamp creationDate = null;
	private int createdBy ;
	private Timestamp lastUpdateDate = null;
	private int lastUpdateBy ;
	private String fileVersion = "";
    private String attribute3 = "";
	private String attribute4 = "";
	private String attribute5 = "";
	private String attribute6 = "";
	private String remark = "";
	public String getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

//	public void setCreationDate(Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}
//
//	public void setLastUpdateDate(Timestamp lastUpdateDate) {
//		this.lastUpdateDate = lastUpdateDate;
//	}

	/**
	 * 功能：设置字典分类表(AMS)属性 系统ID
	 * @param flexValueId String
	 */
	public void setFlexValueId(String flexValueId){
		this.flexValueId = flexValueId;
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 分类ID
	 * @param flexValueSetId String
	 */
	public void setFlexValueSetId(String flexValueSetId){
		this.flexValueSetId = flexValueSetId;
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 代码
	 * @param code String
	 */
	public void setCode(String code){
		this.code = code;
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 值
	 * @param value String
	 */
	public void setValue(String value){
		this.value = value;
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 描述
	 * @param description String
	 */
	public void setDescription(String description){
		this.description = description;
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 是否有效
	 * @param enabled String
	 */
	public void setEnabled(String enabled){
		this.enabled = enabled;
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 null
	 * @param attribute1 String
	 */
	public void setAttribute1(String attribute1){
		this.attribute1 = attribute1;
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 null
	 * @param attribute2 String
	 */
	public void setAttribute2(String attribute2){
		this.attribute2 = attribute2;
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 是否内置字典，内置字典不允许修改
	 * @param isInner String
	 */
	public void setIsInner(String isInner){
		this.isInner = isInner;
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 创建日期
	 * @param creationDate Timestamp
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		if(!StrUtil.isEmpty(creationDate)){
			SimpleCalendar cal = new SimpleCalendar(creationDate);
			this.creationDate = cal.getSQLTimestamp();
		}
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 创建人
	 * @param createdBy String
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 上次修改日期
	 * @param lastUpdateDate Timestamp
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		if(!StrUtil.isEmpty(lastUpdateDate)){
			SimpleCalendar cal = new SimpleCalendar(lastUpdateDate);
			this.lastUpdateDate = cal.getSQLTimestamp();
		}
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 上次修改人
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}

	public void setFlexValueSetName(String flexValueSetName) {
		this.flexValueSetName = flexValueSetName;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 系统ID
	 * @return String
	 */
	public String getFlexValueId(){
		return this.flexValueId;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 分类ID
	 * @return String
	 */
	public String getFlexValueSetId(){
		return this.flexValueSetId;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 代码
	 * @return String
	 */
	public String getCode(){
		return this.code;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 值
	 * @return String
	 */
	public String getValue(){
		return this.value;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 描述
	 * @return String
	 */
	public String getDescription(){
		return this.description;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 是否有效
	 * @return String
	 */
	public String getEnabled(){
		return this.enabled;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 null
	 * @return String
	 */
	public String getAttribute1(){
		return this.attribute1;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 null
	 * @return String
	 */
	public String getAttribute2(){
		return this.attribute2;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 是否内置字典，内置字典不允许修改
	 * @return String
	 */
	public String getIsInner(){
		return this.isInner;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 创建日期
	 * @return Timestamp
	 */
	public Timestamp getCreationDate(){
		return this.creationDate;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 创建人
	 * @return String
	 */
	public int getCreatedBy(){
		return this.createdBy;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 上次修改日期
	 * @return Timestamp
	 */
	public Timestamp getLastUpdateDate(){
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 上次修改人
	 * @return String
	 */
	public int getLastUpdateBy(){
		return this.lastUpdateBy;
	}

	public String getFlexValueSetName() {
		return flexValueSetName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

//	public String getLastUpdateDate() {
//		return lastUpdateDate;
//	}
//
//	public void setLastUpdateDate(String lastUpdateDate) {
//		this.lastUpdateDate = lastUpdateDate;
//	}

}
