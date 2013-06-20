package com.sino.ams.yj.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.math.AdvancedNumber;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 应急保障设备名称字典表 AmsYjItem</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsYjItemDTO extends CheckBoxDTO{

	private String itemCode = "";
	private String itemName = "";
	private String itemCategory = "";
	private SimpleCalendar creationDate = null;
	private SimpleCalendar lastUpdateDate = null;
	private int createUser = SyBaseSQLUtil.NULL_INT_VALUE;;
	private int lastUpdateUser = SyBaseSQLUtil.NULL_INT_VALUE;;

	public AmsYjItemDTO() {
		super();
		this.creationDate = new SimpleCalendar();
		this.lastUpdateDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置应急保障设备名称字典表属性 序号
	 * @param itemCode AdvancedNumber
	 */
	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

	/**
	 * 功能：设置应急保障设备名称字典表属性 装备名称
	 * @param itemName String
	 */
	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	/**
	 * 功能：设置应急保障设备名称字典表属性 字典类型
	 * @param itemCategory String
	 */
	public void setItemCategory(String itemCategory){
		this.itemCategory = itemCategory;
	}

	/**
	 * 功能：设置应急保障设备名称字典表属性 创建日期
	 * @param creationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		this.creationDate.setCalendarValue(creationDate);
	}

	/**
	 * 功能：设置应急保障设备名称字典表属性 创建人
	 * @param createUser AdvancedNumber
	 */
	public void setCreateUser(int createUser){
		this.createUser = createUser;
	}

	/**
	 * 功能：设置应急保障设备名称字典表属性 上次修改日期
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}

	/**
	 * 功能：设置应急保障设备名称字典表属性 上次修改人
	 * @param lastUpdateUser AdvancedNumber
	 */
	public void setLastUpdateUser(int lastUpdateUser){
		this.lastUpdateUser = lastUpdateUser;
	}


	/**
	 * 功能：获取应急保障设备名称字典表属性 序号
	 * @return AdvancedNumber
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 功能：获取应急保障设备名称字典表属性 装备名称
	 * @return String
	 */
	public String getItemName() {
		return this.itemName;
	}

	/**
	 * 功能：获取应急保障设备名称字典表属性 字典类型
	 * @return String
	 */
	public String getItemCategory() {
		return this.itemCategory;
	}

	/**
	 * 功能：获取应急保障设备名称字典表属性 创建日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getCreationDate() throws CalendarException {
		creationDate.setCalPattern(getCalPattern());
		return this.creationDate;
	}

	/**
	 * 功能：获取应急保障设备名称字典表属性 创建人
	 * @return AdvancedNumber
	 */
	public int getCreateUser() {
		return this.createUser;
	}

	/**
	 * 功能：获取应急保障设备名称字典表属性 上次修改日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastUpdateDate() throws CalendarException {
		lastUpdateDate.setCalPattern(getCalPattern());
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取应急保障设备名称字典表属性 上次修改人
	 * @return AdvancedNumber
	 */
	public int getLastUpdateUser() {
		return this.lastUpdateUser;
	}

}