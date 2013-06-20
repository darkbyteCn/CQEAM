package com.sino.ams.dzyh.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;

/**
* <p>Title: 表结构定义-H(EAM) EamDhBillH</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 张星
* @version 1.0
*/

public class EamDhBillHDTO extends CommonRecordDTO{

	private String billHeaderId = "";
	private String billNo = "";//单据编号
	private int billStatus = 0;//单据状态
	private int orgId = SyBaseSQLUtil.NULL_INT_VALUE;//组织id
	
	private String edbhCompany="";//公司名称
	private String edbhUsername="";//创建人

	public String getEdbhCompany() {
		return edbhCompany;
	}

	public void setEdbhCompany(String edbhCompany) {
		this.edbhCompany = edbhCompany;
	}

	public String getEdbhUsername() {
		return edbhUsername;
	}

	public void setEdbhUsername(String edbhUsername) {
		this.edbhUsername = edbhUsername;
	}

	/**
	 * 功能：设置表结构定义-H(EAM)属性 EAM_DH_BILL_H_S.NEXTVAL
	 * @param billHeaderId String
	 */
	public void setBillHeaderId(String billHeaderId){
		this.billHeaderId = billHeaderId;
	}

	/**
	 * 功能：设置表结构定义-H(EAM)属性 单据编号
	 * @param billNo String
	 */
	public void setBillNo(String billNo){
		this.billNo = billNo;
	}

	/**
	 * 功能：设置表结构定义-H(EAM)属性 单据状态(0:待处理  1：已完成)，参见字典“低值易耗单据状态”
	 * @param billStatus String
	 */
	public void setBillStatus(int billStatus){
		this.billStatus = billStatus;
	}

	/**
	 * 功能：设置表结构定义-H(EAM)属性 组织ID
	 * @param orgId String
	 */
	public void setOrgId(int orgId){
		this.orgId = orgId;
	}

	/**
	 * 功能：获取表结构定义-H(EAM)属性 EAM_DH_BILL_H_S.NEXTVAL
	 * @return String
	 */
	public String getBillHeaderId() {
		return this.billHeaderId;
	}

	/**
	 * 功能：获取表结构定义-H(EAM)属性 单据编号
	 * @return String
	 */
	public String getBillNo() {
		return this.billNo;
	}

	/**
	 * 功能：获取表结构定义-H(EAM)属性 单据状态(0:待处理  1：已完成)，参见字典“低值易耗单据状态”
	 * @return String
	 */
	public int getBillStatus() {
		return this.billStatus;
	}

	/**
	 * 功能：获取表结构定义-H(EAM)属性 组织ID
	 * @return String
	 */
	public int getOrgId() {
		return this.orgId;
	}

}