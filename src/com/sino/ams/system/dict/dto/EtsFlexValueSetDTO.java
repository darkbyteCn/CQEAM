package com.sino.ams.system.dict.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
* <p>Title: 字典分类表(AMS) EtsFlexValueSet</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsFlexValueSetDTO extends CheckBoxDTO{

	private String flexValueSetId = "";
	private String code = "";
	private String name = "";
	private String description = "";
	private String isInner = "Y";
	private String maintainFlag = "Y";
	private SimpleCalendar creationDate = null;
	private int createdBy = 0;
	private SimpleCalendar lastUpdateDate = null;
	private int lastUpdateBy = 0;
	private String enabled = "Y";
	private String remark = "";

	/**
	 * 功能：设置字典分类表(AMS)属性 系统ID
	 * @param flexValueSetId String
	 */
	public void setFlexValueSetId(String flexValueSetId){
		this.flexValueSetId = flexValueSetId;
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 分类代码
	 * @param code String
	 */
	public void setCode(String code){
		this.code = code;
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 分类名称
	 * @param name String
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 分类描述
	 * @param description String
	 */
	public void setDescription(String description){
		this.description = description;
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 是否内置分类，内置分类不允许维护
	 * @param isInner String
	 */
	public void setIsInner(String isInner){
		this.isInner = isInner;
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 维护开关，'Y'表示可维护，'N'表示不可维护
	 * @param maintainFlag String
	 */
	public void setMaintainFlag(String maintainFlag){
		this.maintainFlag = maintainFlag;
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 创建日期
	 * @param creationDate Timestamp
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		if(!StrUtil.isEmpty(creationDate)){
			this.creationDate = new SimpleCalendar(creationDate);
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
			this.lastUpdateDate = new SimpleCalendar(lastUpdateDate);
		}
	}

	/**
	 * 功能：设置字典分类表(AMS)属性 上次修改人
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 系统ID
	 * @return String
	 */
	public String getFlexValueSetId(){
		return this.flexValueSetId;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 分类代码
	 * @return String
	 */
	public String getCode(){
		return this.code;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 分类名称
	 * @return String
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 分类描述
	 * @return String
	 */
	public String getDescription(){
		return this.description;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 是否内置分类，内置分类不允许维护
	 * @return String
	 */
	public String getIsInner(){
		return this.isInner;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 维护开关，'Y'表示可维护，'N'表示不可维护
	 * @return String
	 */
	public String getMaintainFlag(){
		return this.maintainFlag;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 创建日期
	 * @return Timestamp
	 */
	public SimpleCalendar getCreationDate(){
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
	public SimpleCalendar getLastUpdateDate(){
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取字典分类表(AMS)属性 上次修改人
	 * @return String
	 */
	public int getLastUpdateBy(){
		return this.lastUpdateBy;
	}

	public String getEnabled() {
		return enabled;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
