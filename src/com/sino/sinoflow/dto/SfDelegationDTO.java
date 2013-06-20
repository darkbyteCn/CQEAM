package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 委派定义 SfDelegation</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfDelegationDTO extends CheckBoxDTO{

	private int delegationId = 0;
	private int userId = 0;
	private int delegateTo = 0;
	private int statusCtl = 0;
	private String startDate = "";
	private String endDate = "";
	private String sName = "";
	private String eName = "";

	public String getSName() {
		return sName;
	}

	public void setSName(String name) {
		sName = name;
	}

	public String getEName() {
		return eName;
	}

	public void setEName(String name) {
		eName = name;
	}

	public SfDelegationDTO() {
		super();
	}

	/**
	 * 功能：设置委派定义属性 ID
	 * @param delegationId String
	 */
	public void setDelegationId(int delegationId){
		this.delegationId = delegationId;
	}

	/**
	 * 功能：设置委派定义属性 USER_ID
	 * @param userId String
	 */
	public void setUserId(int userId){
		this.userId = userId;
	}

	/**
	 * 功能：设置委派定义属性 DELEGATE_TO
	 * @param delegateTo String
	 */
	public void setDelegateTo(int delegateTo){
		this.delegateTo = delegateTo;
	}

	/**
	 * 功能：设置委派定义属性 0:未委派 1:已委派
	 * @param statusCtl String
	 */
	public void setStatusCtl(int statusCtl){
		this.statusCtl = statusCtl;
	}

	/**
	 * 功能：获取委派定义属性 ID
	 * @return String
	 */
	public int getDelegationId() {
		return this.delegationId;
	}

	/**
	 * 功能：获取委派定义属性 USER_ID
	 * @return String
	 */
	public int getUserId() {
		return this.userId;
	}

	/**
	 * 功能：获取委派定义属性 DELEGATE_TO
	 * @return String
	 */
	public int getDelegateTo() {
		return this.delegateTo;
	}

	/**
	 * 功能：获取委派定义属性 0:未委派 1:已委派
	 * @return String
	 */
	public int getStatusCtl() {
		return this.statusCtl;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}