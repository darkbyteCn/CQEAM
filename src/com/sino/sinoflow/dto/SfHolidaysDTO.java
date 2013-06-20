package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: SF_HOLIDAYS SfHolidays</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfHolidaysDTO extends CheckBoxDTO{

	private int holidaysId = 0;
	private String holidays = "";
	private String name = "";
	private int year = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public SfHolidaysDTO() {
		super();
	}

	/**
	 * 功能：设置SF_HOLIDAYS属性 节假日 ID
	 * @param holidaysId String
	 */
	public void setHolidaysId(int holidaysId){
		this.holidaysId = holidaysId;
	}

	/**
	 * 功能：设置SF_HOLIDAYS属性 节假日, 输入格式为XX.XX,如10.01, 以  ;  分隔
	 * @param holidays String
	 */
	public void setHolidays(String holidays){
		this.holidays = holidays;
	}


	/**
	 * 功能：获取SF_HOLIDAYS属性 节假日 ID
	 * @return String
	 */
	public int getHolidaysId() {
		return this.holidaysId;
	}

	/**
	 * 功能：获取SF_HOLIDAYS属性 节假日, 输入格式为XX.XX,如10.01, 以  ;  分隔
	 * @return String
	 */
	public String getHolidays() {
		return this.holidays;
	}

}