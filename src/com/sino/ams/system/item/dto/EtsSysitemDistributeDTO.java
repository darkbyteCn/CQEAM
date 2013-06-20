package com.sino.ams.system.item.dto;

import java.sql.Timestamp;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
* <p>Title: 物料组织分配表 EtsSysitemDistribute</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsSysitemDistributeDTO extends CheckBoxDTO{

	private int systemId  ;
	private String itemCode = "";
	private int organizationId  ;
	private Timestamp creationDate = null;
	private int createdBy ;
	private Timestamp lastUpdateDate = null;
	private int lastUpdateBy  ;

	/**
	 * 功能：设置物料组织分配表属性 序列号
	 * @param systemId String
	 */
	public void setSystemId(int systemId){
		this.systemId = systemId;
	}

	/**
	 * 功能：设置物料组织分配表属性 设备代码
	 * @param itemCode String
	 */
	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

	/**
	 * 功能：设置物料组织分配表属性 分配组织
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置物料组织分配表属性 创建日期
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
	 * 功能：设置物料组织分配表属性 创建人
	 * @param createdBy String
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置物料组织分配表属性 上次修改日期
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
	 * 功能：设置物料组织分配表属性 上次修改人
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}


	/**
	 * 功能：获取物料组织分配表属性 序列号
	 * @return String
	 */
	public int getSystemId(){
		return this.systemId;
	}

	/**
	 * 功能：获取物料组织分配表属性 设备代码
	 * @return String
	 */
	public String getItemCode(){
		return this.itemCode;
	}

	/**
	 * 功能：获取物料组织分配表属性 分配组织
	 * @return String
	 */
	public int getOrganizationId(){
		return this.organizationId;
	}

	/**
	 * 功能：获取物料组织分配表属性 创建日期
	 * @return Timestamp
	 */
	public Timestamp getCreationDate(){
		return this.creationDate;
	}

	/**
	 * 功能：获取物料组织分配表属性 创建人
	 * @return String
	 */
	public int getCreatedBy(){
		return this.createdBy;
	}

	/**
	 * 功能：获取物料组织分配表属性 上次修改日期
	 * @return Timestamp
	 */
	public Timestamp getLastUpdateDate(){
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取物料组织分配表属性 上次修改人
	 * @return String
	 */
	public int getLastUpdateBy(){
		return this.lastUpdateBy;
	}

}
