package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 工时定义 SfWorkHour</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfWorkHourDTO extends CheckBoxDTO{

	private int workHourId = 0;
	private int workingDate = 0;
	private String workBegin1 = "";
	private String workEnd1 = "";
	private String workBegin2 = "";
	private String workEnd2 = "";
	private int createdBy = -1;
	private String creationDate = "";
	private String lastUpdatedBy = "";
	private String lastUpdateDate = "";
	private String workHourName = "";

	public String getWorkHourName() {
		return workHourName;
	}

	public void setWorkHourName(String workHourName) {
		this.workHourName = workHourName;
	}

	public SfWorkHourDTO() {
		super();
	}

	/**
	 * 功能：设置工时定义属性 工作时表 ID
	 * @param workHourId String
	 */
	public void setWorkHourId(int workHourId){
		this.workHourId = workHourId;
	}

	/**
	 * 功能：设置工时定义属性 工作日, Bit0:星期一 Bit1:星期二 Bit2:星期三 Bit3:星期四 Bit4:星期五 Bit5:星期六 Bit6:星期天
	 * @param workingDate String
	 */
	public void setWorkingDate(int workingDate){
		this.workingDate = workingDate;
	}

	/**
	 * 功能：设置工时定义属性 上午开始工作时间(24hh:mm:ss)
	 * @param workBegin1 String
	 */
	public void setWorkBegin1(String workBegin1){
		this.workBegin1 = workBegin1;
	}

	/**
	 * 功能：设置工时定义属性 上午结束工作时间
	 * @param workEnd1 String
	 */
	public void setWorkEnd1(String workEnd1){
		this.workEnd1 = workEnd1;
	}

	/**
	 * 功能：设置工时定义属性 下午开始工作时间
	 * @param workBegin2 String
	 */
	public void setWorkBegin2(String workBegin2){
		this.workBegin2 = workBegin2;
	}

	/**
	 * 功能：设置工时定义属性 下午结束工作时间
	 * @param workEnd2 String
	 */
	public void setWorkEnd2(String workEnd2){
		this.workEnd2 = workEnd2;
	}

	/**
	 * 功能：设置工时定义属性 创建人
	 * @param createdBy String
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置工时定义属性 最后更新人
	 * @param lastUpdatedBy String
	 */
	public void setLastUpdatedBy(String lastUpdatedBy){
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * 功能：获取工时定义属性 工作时表 ID
	 * @return String
	 */
	public int getWorkHourId() {
		return this.workHourId;
	}

	/**
	 * 功能：获取工时定义属性 工作日, Bit0:星期一 Bit1:星期二 Bit2:星期三 Bit3:星期四 Bit4:星期五 Bit5:星期六 Bit6:星期天
	 * @return String
	 */
	public int getWorkingDate() {
		return this.workingDate;
	}

	/**
	 * 功能：获取工时定义属性 上午开始工作时间(24hh:mm:ss)
	 * @return String
	 */
	public String getWorkBegin1() {
		return this.workBegin1;
	}

	/**
	 * 功能：获取工时定义属性 上午结束工作时间
	 * @return String
	 */
	public String getWorkEnd1() {
		return this.workEnd1;
	}

	/**
	 * 功能：获取工时定义属性 下午开始工作时间
	 * @return String
	 */
	public String getWorkBegin2() {
		return this.workBegin2;
	}

	/**
	 * 功能：获取工时定义属性 下午结束工作时间
	 * @return String
	 */
	public String getWorkEnd2() {
		return this.workEnd2;
	}

	/**
	 * 功能：获取工时定义属性 创建人
	 * @return String
	 */
	public int getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * 功能：获取工时定义属性 最后更新人
	 * @return String
	 */
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
}