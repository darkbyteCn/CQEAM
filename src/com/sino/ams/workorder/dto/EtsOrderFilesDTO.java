package com.sino.ams.workorder.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.math.AdvancedNumber;

/**
* <p>Title: 工单文件处理(EAM) EtsOrderFiles</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsOrderFilesDTO extends CheckBoxDTO{

	private AdvancedNumber systemid = null;
	private String workorderBatch = "";
	private String titel = "";
	private String fileName = "";
	private String filePath = "";
	private String fileType = "";
	private AdvancedNumber isTruefile = null;
	private String remark = "";
	private AdvancedNumber handler = null;
	private SimpleCalendar recordDate = null;

	public EtsOrderFilesDTO() {
		super();
		this.recordDate = new SimpleCalendar();

		this.systemid = new AdvancedNumber();
		this.isTruefile = new AdvancedNumber();
		this.handler = new AdvancedNumber();
	}

	/**
	 * 功能：设置工单文件处理(EAM)属性 流水号
	 * @param systemid AdvancedNumber
	 */
	public void setSystemid(AdvancedNumber systemid){
		this.systemid = systemid;
	}

	/**
	 * 功能：设置工单文件处理(EAM)属性 工单批号
	 * @param workorderBatch String
	 */
	public void setWorkorderBatch(String workorderBatch){
		this.workorderBatch = workorderBatch;
	}

	/**
	 * 功能：设置工单文件处理(EAM)属性 主题
	 * @param titel String
	 */
	public void setTitel(String titel){
		this.titel = titel;
	}

	/**
	 * 功能：设置工单文件处理(EAM)属性 文件名
	 * @param fileName String
	 */
	public void setFileName(String fileName){
		this.fileName = fileName;
	}

	/**
	 * 功能：设置工单文件处理(EAM)属性 文件保存路径
	 * @param filePath String
	 */
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}

	/**
	 * 功能：设置工单文件处理(EAM)属性 文件类型
	 * @param fileType String
	 */
	public void setFileType(String fileType){
		this.fileType = fileType;
	}

	/**
	 * 功能：设置工单文件处理(EAM)属性 定稿文件(1表示定稿文件；0 表示修改文件
)
	 * @param isTruefile AdvancedNumber
	 */
	public void setIsTruefile(AdvancedNumber isTruefile){
		this.isTruefile = isTruefile;
	}

	/**
	 * 功能：设置工单文件处理(EAM)属性 备注
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置工单文件处理(EAM)属性 提交人
	 * @param handler AdvancedNumber
	 */
	public void setHandler(AdvancedNumber handler){
		this.handler = handler;
	}

	/**
	 * 功能：设置工单文件处理(EAM)属性 提交日期
	 * @param recordDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setRecordDate(String recordDate) throws CalendarException{
		this.recordDate.setCalendarValue(recordDate);
	}


	/**
	 * 功能：获取工单文件处理(EAM)属性 流水号
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getSystemid() {
		return this.systemid;
	}

	/**
	 * 功能：获取工单文件处理(EAM)属性 工单批号
	 * @return String
	 */
	public String getWorkorderBatch() {
		return this.workorderBatch;
	}

	/**
	 * 功能：获取工单文件处理(EAM)属性 主题
	 * @return String
	 */
	public String getTitel() {
		return this.titel;
	}

	/**
	 * 功能：获取工单文件处理(EAM)属性 文件名
	 * @return String
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * 功能：获取工单文件处理(EAM)属性 文件保存路径
	 * @return String
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * 功能：获取工单文件处理(EAM)属性 文件类型
	 * @return String
	 */
	public String getFileType() {
		return this.fileType;
	}

	/**
	 * 功能：获取工单文件处理(EAM)属性 定稿文件(1表示定稿文件；0 表示修改文件
)
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getIsTruefile() {
		return this.isTruefile;
	}

	/**
	 * 功能：获取工单文件处理(EAM)属性 备注
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取工单文件处理(EAM)属性 提交人
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getHandler() {
		return this.handler;
	}

	/**
	 * 功能：获取工单文件处理(EAM)属性 提交日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getRecordDate() throws CalendarException {
		recordDate.setCalPattern(getCalPattern());
		return this.recordDate;
	}

}