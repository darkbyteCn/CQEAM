package com.sino.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>Title: 资产业务头表(EAM)--取代原表 AmsAssetsTransHeader</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsSpecialAssetsDTO extends CommonRecordDTO {

	private String transId = "";
	private String transNo = "";
	private String transType = "";
	private String transStatus = "";
	private int fromOrganizationId;
	private int toOrganizationId;
	private int fromDept;
	private int toDept;
	private int fromObjectNo;
	private int toObjectNo;
	private String fromPerson = "";
	private String toPerson = "";
	private String createdReason = "";
	private SimpleCalendar canceledDate = null;
	private String canceledReason = "";
	private SimpleCalendar approvedDate = null;
	private String approvedBy = "";
	private String receivedUser = "";
	private String receivedUserName = "";
	private SimpleCalendar transDate = null;


	private String transTypeValue = ""; //资产业务类型描述
	private String created = ""; //创建人姓名;
	private String toObjectName = ""; //目的地点名称
	private String toUserName = ""; //目标保管人姓名
	private String transStatusDesc = ""; //单据状态描述
	private String toDeptName = ""; //目的部门名称
	private String fromCompanyName = "";
	private String fromDeptName = "";
	private String toCompanyName = "";

	private int fromGroup;
	private String fromGroupName = "";
	private String fromGroupOption = "";

	private String toGroup = "";
	private String toGroupName = "";
	private String toGroupOption = "";

	private String fromDeptOption = "";
	private String toDeptOption = "";
	private String approvedUser = "";
	private String flowCode = "";
	private String statusOption = "";
	private boolean flow2End = false; //是否流程结束
	private String transferType = ""; //调拨单的哪一种类型：部门内，部门间，地市间
	private String transferTypeOption = "";
	private String printType = "";
	private boolean canReceive = true;

	private String email = ""; //创建人电子邮件
	private String phoneNumber = ""; //创建人手机号码
	private String bookTypeCode = ""; //调出公司资产账簿代码
	private String bookTypeName = ""; //调出公司资产账簿名称
	private String toCompanyOption = ""; //调入公司下拉框选项
	private String userDeptName = ""; //用户所属部门
	private String faContentCode = ""; //资产大类别，目前分为：管理类还是网络类
	private String faContentName = ""; //资产大类别，目前分为：管理类还是网络类
	private String faContentOption = ""; //资产大类别下拉框选项

	private ServletConfigDTO servletConfig = null;
	private String fromCompanyOption = ""; //为内蒙古移动而设置
	private String bookTypeOption = ""; //为内蒙古移动而设置


	private String sectionRight = ""; //加入流程相关信息
	private String hiddenRight = ""; //加入流程相关信息
	private String attribute1 = ""; //加入流程相关信息
	private String attribute2 = ""; //加入流程相关信息
	private String attribute3 = ""; //加入流程相关信息
	private String attribute4 = ""; //加入流程相关信息
	private String attribute5 = ""; //加入流程相关信息
	private String groupProp = ""; //建单组别属性

	private String lossesName = ""; //损耗名称
	private SimpleCalendar lossesDate = null; //损耗日期
	private String groupPid = ""; //用于审批过程中，当前组别的父组别
	private String producedNewBarcode = "N";
	private String controlPrivi = "N";

    private String currTaskId = "";//用于审批过程中的特殊控制
    private String currRoleName = "";//用于审批过程中的特殊控制
    private String accessSheet = "";//附件张数
    
    private String assetsTypeName ="";
    private String assetsType = "";
    private String assetsTypeOption = "";
    private String transTypeOption = "";

    private String[] barcodess = null;

    public String[] getBarcodess() {
        return barcodess;
    }

    public String getTransTypeOption() {
		return transTypeOption;
	}

	public void setTransTypeOption(String transTypeOption) {
		this.transTypeOption = transTypeOption;
	}

	public void setBarcodess(String[] barcodess) {
        this.barcodess = barcodess;
    }

    public String getAssetsTypeName() {
		return assetsTypeName;
	}

	public void setAssetsTypeName(String assetsTypeName) {
		this.assetsTypeName = assetsTypeName;
	}

	public String getAccessSheet() {
        return accessSheet;
    }

    public void setAccessSheet(String accessSheet) {
        this.accessSheet = accessSheet;
    }

    public String getAssetsType() {
		return assetsType;
	}

	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}

	public String getAssetsTypeOption() {
		return assetsTypeOption;
	}

	public void setAssetsTypeOption(String assetsTypeOption) {
		this.assetsTypeOption = assetsTypeOption;
	}

	public String getCurrTaskId() {
        return currTaskId;
    }

    public void setCurrTaskId(String currTaskId) {
        this.currTaskId = currTaskId;
    }

    public String getCurrRoleName() {
        return currRoleName;
    }

    public void setCurrRoleName(String currRoleName) {
        this.currRoleName = currRoleName;
    }

    public AmsSpecialAssetsDTO() {
		super();
		this.canceledDate = new SimpleCalendar();
		this.approvedDate = new SimpleCalendar();
		this.transDate = new SimpleCalendar();
		this.printType = AssetsWebAttributes.PRINT_TRANS_OUT;
		this.lossesDate = new SimpleCalendar();
		setCurrCalendar("lossesDate");
	}

	/**
	 * 功能：设置servletConfig配置对象
	 * @param servletConfig ServletConfigDTO
	 */
	public void setServletConfig(ServletConfigDTO servletConfig) {
		this.servletConfig = servletConfig;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 事务序列号
	 * @param transId String
	 */
	public void setTransId(String transId) {
		this.transId = transId;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 单据编号
	 * @param transNo String
	 */
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 单据类型
	 * @param transType String
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 单据状态
	 * @param transStatus String
	 */
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 原OU组织ID
	 * @param fromOrganizationId String
	 */
	public void setFromOrganizationId(int fromOrganizationId) {
		this.fromOrganizationId = fromOrganizationId;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 目标OU组织ID
	 * @param toOrganizationId String
	 */
	public void setToOrganizationId(int toOrganizationId) {
		this.toOrganizationId = toOrganizationId;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 原组别
	 * @param fromDept String
	 */
	public void setFromDept(int fromDept) {
		this.fromDept = fromDept;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 目标组别
	 * @param toDept String
	 */
	public void setToDept(int toDept) {
		this.toDept = toDept;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 原地点
	 * @param fromObjectNo String
	 */
	public void setFromObjectNo(int fromObjectNo) {
		this.fromObjectNo = fromObjectNo;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 目标地点
	 * @param toObjectNo String
	 */
	public void setToObjectNo(int toObjectNo) {
		this.toObjectNo = toObjectNo;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 原保管人,存employeeNumber
	 * @param fromPerson String
	 */
	public void setFromPerson(String fromPerson) {
		this.fromPerson = fromPerson;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 目标保管人,存employeeNumber
	 * @param toPerson String
	 */
	public void setToPerson(String toPerson) {
		this.toPerson = toPerson;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 单据创建原因
	 * @param createdReason String
	 */
	public void setCreatedReason(String createdReason) {
		this.createdReason = createdReason;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 撤销日期
	 * @param canceledDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCanceledDate(String canceledDate) throws CalendarException {
		this.canceledDate.setCalendarValue(canceledDate);
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 撤销原因
	 * @param canceledReason String
	 */
	public void setCanceledReason(String canceledReason) {
		this.canceledReason = canceledReason;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 批准日期
	 * @param approvedDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setApprovedDate(String approvedDate) throws CalendarException {
		this.approvedDate.setCalendarValue(approvedDate);
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 批准人
	 * @param approvedBy String
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 接收人
	 * @param receivedUser String
	 */
	public void setReceivedUser(String receivedUser) {
		this.receivedUser = receivedUser;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public void setFromCompanyName(String fromCompanyName) {
		this.fromCompanyName = fromCompanyName;
	}

	public void setFromDeptName(String fromDeptName) {
		this.fromDeptName = fromDeptName;
	}

	public void setToCompanyName(String toCompanyName) {
		this.toCompanyName = toCompanyName;
	}

	public void setToDeptName(String toDeptName) {
		this.toDeptName = toDeptName;
	}

	public void setToObjectName(String toObjectName) {
		this.toObjectName = toObjectName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public void setTransStatusDesc(String transStatusDesc) {
		this.transStatusDesc = transStatusDesc;
	}

	public void setTransTypeValue(String transTypeValue) {
		this.transTypeValue = transTypeValue;
	}

	public void setFromDeptOption(String fromDeptOption) {
		this.fromDeptOption = fromDeptOption;
	}

	public void setToDeptOption(String toDeptOption) {
		this.toDeptOption = toDeptOption;
	}

	public void setFromGroup(int fromGroup) {
		this.fromGroup = fromGroup;
	}

	public void setFromGroupName(String fromGroupName) {
		this.fromGroupName = fromGroupName;
	}

	public void setFromGroupOption(String fromGroupOption) {
		this.fromGroupOption = fromGroupOption;
	}

	public void setToGroup(String toGroup) {
		this.toGroup = toGroup;
	}

	public void setToGroupName(String toGroupName) {
		this.toGroupName = toGroupName;
	}

	public void setToGroupOption(String toGroupOption) {
		this.toGroupOption = toGroupOption;
	}

	public void setApprovedUser(String approvedUser) {
		this.approvedUser = approvedUser;
	}

	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}

	public void setReceivedUserName(String receivedUserName) {
		this.receivedUserName = receivedUserName;
	}

	public void setStatusOption(String statusOption) {
		this.statusOption = statusOption;
	}

	public void setFlow2End(boolean flow2End) {
		this.flow2End = flow2End;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public void setTransferTypeOption(String transferTypeOption) {
		this.transferTypeOption = transferTypeOption;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}

	public void setCanReceive(boolean canReceive) {
		this.canReceive = canReceive;
	}

	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setToCompanyOption(String toCompanyOption) {
		this.toCompanyOption = toCompanyOption;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setUserDeptName(String userDeptName) {
		this.userDeptName = userDeptName;
	}

	public void setFaContentCode(String faContentCode) {
		this.faContentCode = faContentCode;
	}

	public void setFaContentOption(String faContentOption) {
		this.faContentOption = faContentOption;
	}

	public void setFaContentName(String faContentName) {
		this.faContentName = faContentName;
	}

	public void setFromCompanyOption(String fromCompanyOption) {
		this.fromCompanyOption = fromCompanyOption;
	}

	public void setBookTypeOption(String bookTypeOption) {
		this.bookTypeOption = bookTypeOption;
	}

	public void setHiddenRight(String hiddenRight) {
		this.hiddenRight = hiddenRight;
	}

	public void setSectionRight(String sectionRight) {
		this.sectionRight = sectionRight;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public void setGroupProp(String groupProp) {
		this.groupProp = groupProp;
	}

	public void setLossesDate(String lossesDate) throws CalendarException {
		this.lossesDate.setCalendarValue(lossesDate);
	}

	public void setLossesName(String lossesName) {
		this.lossesName = lossesName;
	}

	/**
	 * 功能：设置资产业务头表(EAM)--取代原表属性 接收日期
	 * @param transDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setTransDate(String transDate) throws CalendarException {
		this.transDate.setCalendarValue(transDate);
	}


	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 事务序列号
	 * @return String
	 */
	public String getTransId() {
		return this.transId;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 单据编号
	 * @return String
	 */
	public String getTransNo() {
		return this.transNo;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 单据类型
	 * @return String
	 */
	public String getTransType() {
		return this.transType;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 单据状态
	 * @return String
	 */
	public String getTransStatus() {
		return this.transStatus;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 原OU组织ID
	 * @return String
	 */
	public int getFromOrganizationId() {
		return this.fromOrganizationId;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 目标OU组织ID
	 * @return String
	 */
	public int getToOrganizationId() {
		return this.toOrganizationId;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 原组别
	 * @return String
	 */
	public int getFromDept() {
		return this.fromDept;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 目标组别
	 * @return String
	 */
	public int getToDept() {
		return this.toDept;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 原地点
	 * @return String
	 */
	public int getFromObjectNo() {
		return this.fromObjectNo;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 目标地点
	 * @return String
	 */
	public int getToObjectNo() {
		return this.toObjectNo;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 原保管人,存employeeNumber
	 * @return String
	 */
	public String getFromPerson() {
		return this.fromPerson;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 目标保管人,存employeeNumber
	 * @return String
	 */
	public String getToPerson() {
		return this.toPerson;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 单据创建原因
	 * @return String
	 */
	public String getCreatedReason() {
		return this.createdReason;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 撤销日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getCanceledDate() throws CalendarException {
		canceledDate.setCalPattern(getCalPattern());
		return this.canceledDate;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 撤销原因
	 * @return String
	 */
	public String getCanceledReason() {
		return this.canceledReason;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 批准日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getApprovedDate() throws CalendarException {
		approvedDate.setCalPattern(getCalPattern());
		return this.approvedDate;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 批准人
	 * @return String
	 */
	public String getApprovedBy() {
		return this.approvedBy;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 接收人
	 * @return String
	 */
	public String getReceivedUser() {
		return this.receivedUser;
	}

	/**
	 * 功能：获取资产业务头表(EAM)--取代原表属性 接收日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getTransDate() throws CalendarException {
		transDate.setCalPattern(getCalPattern());
		return this.transDate;
	}

	public String getCreated() {
		return created;
	}

	public String getFromCompanyName() {
		return fromCompanyName;
	}

	public String getFromDeptName() {
		return fromDeptName;
	}

	public String getToCompanyName() {
		return toCompanyName;
	}

	public String getToDeptName() {
		return toDeptName;
	}

	public String getToObjectName() {
		return toObjectName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public String getTransStatusDesc() {
		return transStatusDesc;
	}

	/**
	 * 功能：获取单据类型
	 * @return String
	 */
	public String getTransTypeValue() {
		String strValue = transTypeValue;
		if (transType.equals(AssetsDictConstant.ASS_RED)) {
			String[] transOpts = AssetsDictConstant.SP_ASSETS_OPT_VALUES;
			for (int i = 0; i < transOpts.length; i++) {
				if (transferType.equals(transOpts[i])) {
					strValue = AssetsDictConstant.SP_ASSETS_OPT_LABELS[i];
					break;
				}
			}
		}
		return strValue;
	}

	public String getFromDeptOption() {
		return fromDeptOption;
	}

	public String getToDeptOption() {
		return toDeptOption;
	}

	public int getFromGroup() {
		return fromGroup;
	}

	public String getFromGroupName() {
		return fromGroupName;
	}

	public String getFromGroupOption() {
		return fromGroupOption;
	}

	public String getToGroup() {
		return toGroup;
	}

	public String getToGroupName() {
		return toGroupName;
	}

	public String getToGroupOption() {
		return toGroupOption;
	}

	public String getApprovedUser() {
		return approvedUser;
	}

	public String getFlowCode() {
		return flowCode;
	}

	public String getReceivedUserName() {
		return receivedUserName;
	}

	public String getStatusOption() {
		return statusOption;
	}

	public boolean isFlow2End() {
		return flow2End;
	}

	public String getTransferType() {
		return transferType;
	}

	public String getTransferTypeOption() {
		return transferTypeOption;
	}

	public String getPrintType() {
		return printType;
	}

	public String getBookTypeCode() {
		return bookTypeCode;
	}

	public String getBookTypeName() {
		return bookTypeName;
	}

	public String getEmail() {
		return email;
	}

	public String getToCompanyOption() {
		return toCompanyOption;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getUserDeptName() {
		return userDeptName;
	}

	public String getFaContentCode() {
		return faContentCode;
	}

	public String getFaContentOption() {
		return faContentOption;
	}

	public String getFaContentName() {
		return faContentName;
	}

	public String getFromCompanyOption() {
		return fromCompanyOption;
	}

	public ServletConfigDTO getServletConfig() {
		return servletConfig;
	}

	public String getBookTypeOption() {
		return bookTypeOption;
	}

	public String getHiddenRight() {
		return hiddenRight;
	}

	public String getSectionRight() {
		return sectionRight;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public String getGroupProp() {
		return groupProp;
	}

	public SimpleCalendar getLossesDate() throws CalendarException {
		lossesDate.setCalPattern(getCalPattern());
		return lossesDate;
	}

	public String getLossesName() {
		return lossesName;
	}

	public boolean canReceive() {
		return canReceive;
	}

	public void setGroupPid(String groupPid) {
		this.groupPid = groupPid;
	}

	public void setProducedNewBarcode(String producedNewBarcode) {
		this.producedNewBarcode = producedNewBarcode;
	}

	public void setControlPrivi(String controlPrivi) {
		this.controlPrivi = controlPrivi;
	}

	public String getGroupPid() {
		return this.groupPid;
	}

	public String getProducedNewBarcode() {
		return producedNewBarcode;
	}

	public String getControlPrivi() {
		return controlPrivi;
	}

	/**
	 * 功能：获取流程名称
	 * @return String
	 */
	public String getProcdureName() {
		String procedureName = "";
		if (transType.equals(AssetsDictConstant.ASS_RED)) { //调拨
			procedureName = AssetsDictConstant.PROCEDURE_NAME_SPECIAL_TRANSFER;
		}else if (transType.equals(AssetsDictConstant.ASS_DIS)) { //报废
			procedureName = AssetsDictConstant.PROCEDURE_NAME_DISCARD;
		} else if (transType.equals(AssetsDictConstant.ASS_FREE)) { //闲置
			procedureName = AssetsDictConstant.PROCEDURE_NAME_FREE;
		} 
		return procedureName;
	}
		
}
