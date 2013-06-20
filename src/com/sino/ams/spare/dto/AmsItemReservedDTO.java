package com.sino.ams.spare.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 备件业务保留表(AMS) AmsItemReserved</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsItemReservedDTO extends CheckBoxDTO{
	public AmsItemReservedDTO() {
		super();
		this.reservedDate = new SimpleCalendar();
	}

	private String transId = "";
	private SimpleCalendar reservedDate = null;
	private String itemCode = "";
	private String reservedCount = "0";


	/**
	 * 功能：设置备件业务保留表(AMS)属性 单据ID
	 * @param transId String
	 */
	public void setTransId(String transId){
		this.transId = transId;
	}

	/**
	 * 功能：设置备件业务保留表(AMS)属性 保留日期
	 * @param reservedDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setReservedDate(String reservedDate) throws CalendarException{
		this.reservedDate.setCalendarValue(reservedDate);
	}

	/**
	 * 功能：设置备件业务保留表(AMS)属性 保留类别
	 * @param itemCode String
	 */
	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

	/**
	 * 功能：设置备件业务保留表(AMS)属性 保留数量
	 * @param reservedCount String
	 */
	public void setReservedCount(String reservedCount){
		this.reservedCount = reservedCount;
	}


	/**
	 * 功能：获取备件业务保留表(AMS)属性 单据ID
	 * @return String
	 */
	public String getTransId() {
		return this.transId;
	}

	/**
	 * 功能：获取备件业务保留表(AMS)属性 保留日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getReservedDate() throws CalendarException {
		if(reservedDate != null){
			reservedDate.setCalPattern(getCalPattern());
		}
		return this.reservedDate;
	}

	/**
	 * 功能：获取备件业务保留表(AMS)属性 保留类别
	 * @return String
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 功能：获取备件业务保留表(AMS)属性 保留数量
	 * @return String
	 */
	public String getReservedCount() {
		return this.reservedCount;
	}

}