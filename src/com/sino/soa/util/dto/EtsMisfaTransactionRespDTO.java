package com.sino.soa.util.dto;

import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.math.AdvancedNumber;

/**
* <p>Title: ETS_MISFA_TRANSACTION_RESP EtsMisfaTransactionResp</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsMisfaTransactionRespDTO extends CheckBoxDTO{

	private String transactionType = "";
	private AdvancedNumber organizationId = null;
	private AdvancedNumber userId = null;
	private AdvancedNumber respId = null;
	private AdvancedNumber respApplId = null;
	private String employeeNumber = "";

	private String snCode = "";           //SN_CODE 支撑网设备类型
	private String opeCode = "";           //OPE_CODE 支撑网设备类型
	
	public EtsMisfaTransactionRespDTO() {
		super();

		this.organizationId = new AdvancedNumber();
		this.userId = new AdvancedNumber();
		this.respId = new AdvancedNumber();
		this.respApplId = new AdvancedNumber();
	}

	/**
	 * 功能：设置ETS_MISFA_TRANSACTION_RESP属性 TRANSACTION_TYPE
	 * @param transactionType String
	 */
	public void setTransactionType(String transactionType){
		this.transactionType = transactionType;
	}

	/**
	 * 功能：设置ETS_MISFA_TRANSACTION_RESP属性 ORGANIZATION_ID
	 * @param organizationId AdvancedNumber
	 */
	public void setOrganizationId(AdvancedNumber organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置ETS_MISFA_TRANSACTION_RESP属性 USER_ID
	 * @param userId AdvancedNumber
	 */
	public void setUserId(AdvancedNumber userId){
		this.userId = userId;
	}

	/**
	 * 功能：设置ETS_MISFA_TRANSACTION_RESP属性 RESP_ID
	 * @param respId AdvancedNumber
	 */
	public void setRespId(AdvancedNumber respId){
		this.respId = respId;
	}

	/**
	 * 功能：设置ETS_MISFA_TRANSACTION_RESP属性 RESP_APPL_ID
	 * @param respApplId AdvancedNumber
	 */
	public void setRespApplId(AdvancedNumber respApplId){
		this.respApplId = respApplId;
	}

	/**
	 * 功能：设置ETS_MISFA_TRANSACTION_RESP属性 员工编号
	 * @param employeeNumber String
	 */
	public void setEmployeeNumber(String employeeNumber){
		this.employeeNumber = employeeNumber;
	}


	/**
	 * 功能：获取ETS_MISFA_TRANSACTION_RESP属性 TRANSACTION_TYPE
	 * @return String
	 */
	public String getTransactionType() {
		return this.transactionType;
	}

	/**
	 * 功能：获取ETS_MISFA_TRANSACTION_RESP属性 ORGANIZATION_ID
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取ETS_MISFA_TRANSACTION_RESP属性 USER_ID
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getUserId() {
		return this.userId;
	}

	/**
	 * 功能：获取ETS_MISFA_TRANSACTION_RESP属性 RESP_ID
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getRespId() {
		return this.respId;
	}

	/**
	 * 功能：获取ETS_MISFA_TRANSACTION_RESP属性 RESP_APPL_ID
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getRespApplId() {
		return this.respApplId;
	}

	/**
	 * 功能：获取ETS_MISFA_TRANSACTION_RESP属性 员工编号
	 * @return String
	 */
	public String getEmployeeNumber() {
		return this.employeeNumber;
	}

	public String getSnCode() {
		return snCode;
	}

	public void setSnCode(String snCode) {
		this.snCode = snCode;
	}

	public String getOpeCode() {
		return opeCode;
	}

	public void setOpeCode(String opeCode) {
		this.opeCode = opeCode;
	}

}