package com.sino.ams.print.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 条码打印信息表 AmsBarcodePrint</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsBarcodePrintDTO extends CheckBoxDTO{

	private String id = "";
	private String batchNo = "";
	private String tagType = "";
	private int tagNumber;
	private String applyReason = "";
	private int applyUser;
	private SimpleCalendar applyDate = null;
	private int approveUser;
	private SimpleCalendar approveDate = null;
	private String approveResult = "";
	private int printUser;
	private SimpleCalendar printDate = null;
	private String remark = "";
	private int status;
    private String first = "";
    private String applyName ="";
    private String approveName ="";
    private String printName ="";
    private String applyGroup = "";
    private String tagColor = "";

    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }

    public String getApplyGroup() {
        return applyGroup;
    }

    public void setApplyGroup(String applyGroup) {
        this.applyGroup = applyGroup;
    }

    private SimpleCalendar fromDate = null;
    private SimpleCalendar toDate = null;
    private SimpleCalendar endDate = null;

//
    public SimpleCalendar getEndDate() throws CalendarException {
	    endDate.setCalPattern(getCalPattern());
        return this.endDate;
    }

    public void setEndDate(String endDate)throws CalendarException{
		this.endDate.setCalendarValue(endDate);
	}

    public SimpleCalendar getToDate() throws CalendarException {
		toDate.setCalPattern(getCalPattern());
        return this.toDate;
    }

    public void setToDate(String toDate)throws CalendarException{
		this.toDate.setCalendarValue(toDate);
	}

    public SimpleCalendar getFromDate() throws CalendarException {
		fromDate.setCalPattern(getCalPattern());
        return this.fromDate;
    }

    public void setFromDate(String fromDate)throws CalendarException{
		this.fromDate.setCalendarValue(fromDate);
	}
    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getApproveName() {
        return approveName;
    }

    public void setApproveName(String approveName) {
        this.approveName = approveName;
    }

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public AmsBarcodePrintDTO() {
		super();
		this.applyDate = new SimpleCalendar();
		this.approveDate = new SimpleCalendar();
		this.printDate = new SimpleCalendar();
        this.fromDate = new SimpleCalendar();
		this.toDate = new SimpleCalendar();
		this.endDate = new SimpleCalendar();
    }

	/**
	 * 功能：设置条码打印信息表属性 序列
	 * @param id String
	 */
	public void setId(String id){
		this.id = id;
	}

	/**
	 * 功能：设置条码打印信息表属性 单据号
	 * @param batchNo String
	 */
	public void setBatchNo(String batchNo){
		this.batchNo = batchNo;
	}

	/**
	 * 功能：设置条码打印信息表属性 标签类型
	 * @param tagType String
	 */
	public void setTagType(String tagType){
		this.tagType = tagType;
	}

	/**
	 * 功能：设置条码打印信息表属性 标签数量
	 * @param tagNumber String
	 */
	public void setTagNumber(int tagNumber){
		this.tagNumber = tagNumber;
	}

	/**
	 * 功能：设置条码打印信息表属性 申请原因
	 * @param applyReason String
	 */
	public void setApplyReason(String applyReason){
		this.applyReason = applyReason;
	}

	/**
	 * 功能：设置条码打印信息表属性 申请人
	 * @param applyUser String
	 */
	public void setApplyUser(int applyUser){
		this.applyUser = applyUser;
	}

	/**
	 * 功能：设置条码打印信息表属性 申请日期
	 * @param applyDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setApplyDate(String applyDate) throws CalendarException{
		this.applyDate.setCalendarValue(applyDate);
	}

	/**
	 * 功能：设置条码打印信息表属性 审批人
	 * @param approveUser String
	 */
	public void setApproveUser(int approveUser){
		this.approveUser = approveUser;
	}

	/**
	 * 功能：设置条码打印信息表属性 审批日期
	 * @param approveDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setApproveDate(String approveDate) throws CalendarException{
		this.approveDate.setCalendarValue(approveDate);
	}

	/**
	 * 功能：设置条码打印信息表属性 审批结果
	 * @param approveResult String
	 */
	public void setApproveResult(String approveResult){
		this.approveResult = approveResult;
	}

	/**
	 * 功能：设置条码打印信息表属性 打印人
	 * @param printUser String
	 */
	public void setPrintUser(int printUser){
		this.printUser = printUser;
	}

	/**
	 * 功能：设置条码打印信息表属性 打印日期
	 * @param printDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setPrintDate(String printDate) throws CalendarException{
		this.printDate.setCalendarValue(printDate);
	}

	/**
	 * 功能：设置条码打印信息表属性 备注
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置条码打印信息表属性 单据状态(0-处理中;1-已撤销;2-已完成)
	 * @param status String
	 */
	public void setStatus(int status){
		this.status = status;
	}


	/**
	 * 功能：获取条码打印信息表属性 序列
	 * @return String
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 功能：获取条码打印信息表属性 单据号
	 * @return String
	 */
	public String getBatchNo() {
		return this.batchNo;
	}

	/**
	 * 功能：获取条码打印信息表属性 标签类型
	 * @return String
	 */
	public String getTagType() {
		return this.tagType;
	}

	/**
	 * 功能：获取条码打印信息表属性 标签数量
	 * @return String
	 */
	public int getTagNumber() {
		return this.tagNumber;
	}

	/**
	 * 功能：获取条码打印信息表属性 申请原因
	 * @return String
	 */
	public String getApplyReason() {
		return this.applyReason;
	}

	/**
	 * 功能：获取条码打印信息表属性 申请人
	 * @return String
	 */
	public int getApplyUser() {
		return this.applyUser;
	}

	/**
	 * 功能：获取条码打印信息表属性 申请日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getApplyDate() throws CalendarException {
		applyDate.setCalPattern(getCalPattern());
		return this.applyDate;
	}

	/**
	 * 功能：获取条码打印信息表属性 审批人
	 * @return String
	 */
	public int getApproveUser() {
		return this.approveUser;
	}

	/**
	 * 功能：获取条码打印信息表属性 审批日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getApproveDate() throws CalendarException {
		approveDate.setCalPattern(getCalPattern());
		return this.approveDate;
	}

	/**
	 * 功能：获取条码打印信息表属性 审批结果
	 * @return String
	 */
	public String getApproveResult() {
		return this.approveResult;
	}

	/**
	 * 功能：获取条码打印信息表属性 打印人
	 * @return String
	 */
	public int getPrintUser() {
		return this.printUser;
	}

	/**
	 * 功能：获取条码打印信息表属性 打印日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getPrintDate() throws CalendarException {
		printDate.setCalPattern(getCalPattern());
		return this.printDate;
	}

	/**
	 * 功能：获取条码打印信息表属性 备注
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取条码打印信息表属性 单据状态(0-处理中;1-已撤销;2-已完成)
	 * @return String
	 */
	public int getStatus() {
		return this.status;
	}

}