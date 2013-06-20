package com.sino.nm.ams.inv.storeman.dto;

import com.sino.ams.system.basepoint.dto.EtsObjectDTO;


/**
 * <p>Title: 默认仓管员维护 EamInvStoreman</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 于士博
 * @version 1.0
 */
public class EamInvStoremanDTO extends EtsObjectDTO {

	private String storemanId = ""; //Sequence主键ID
	private int userId; //仓管员ID（sf_user.user_id）
	private String userName = ""; //SF_USER表中的USERNAME属性
	private String createdUser = ""; //SF_USER表中的USERNAME属性，为了识别创建人名字
	private String updatedUser = ""; //SF_USER表中的USERNAME属性，为了识别修改人名字
	private String invCategoryName = ""; //对应仓库类型字段
	private String bizCategoryName = ""; //对应业务类型字段
	private String createdById = ""; //EAM_INV_STOREMAN表中的CREATED_BY这个字段的对应字段
	private String lastUpdateById = ""; //EAM_INV_STOREMAN表中的LAST_UPDATE_BY这个字段对应的字段
	private String ouOption = "";//公司信息下拉框数据列表
	private String invCategoryOpt = "";//仓库类型下拉框数据列表
	private String bizCategoryOpt = "";//业务类型下拉框数据列表
	
	//------------------------------------以下为AMS_INV_PRICI表中的字段------------------------------------//
	private String invCode = ""; //仓库ID
	private String actionCode = ""; //操作类型，数据字典INV_PRIVI(库存权限)
	private String priviId = ""; //主键，序列号
	
	
	private String codeExist = ""; //检查仓库代码是否已经存在
	
	private String workorderObjectNo1 = ""; 

	public String getCodeExist() {
		return codeExist;
	}

	public void setCodeExist(String codeExist) {
		this.codeExist = codeExist;
	}

	public String getInvCode() {
		return invCode;
	}

	public void setInvCode(String invCode) {
		this.invCode = invCode;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getPriviId() {
		return priviId;
	}

	public void setPriviId(String priviId) {
		this.priviId = priviId;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public String getLastUpdateById() {
		return lastUpdateById;
	}

	public void setLastUpdateById(String lastUpdateById) {
		this.lastUpdateById = lastUpdateById;
	}

	public String getInvCategoryName() {
		return invCategoryName;
	}

	public void setInvCategoryName(String invCategoryName) {
		this.invCategoryName = invCategoryName;
	}

	public String getBizCategoryName() {
		return bizCategoryName;
	}

	public void setBizCategoryName(String bizCategoryName) {
		this.bizCategoryName = bizCategoryName;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStoremanId() {
		return storemanId;
	}

	public void setStoremanId(String storemanId) {
		this.storemanId = storemanId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getOuOption() {
		return ouOption;
	}

	public String getBizCategoryOpt() {
		return bizCategoryOpt;
	}

	public String getInvCategoryOpt() {
		return invCategoryOpt;
	}

	public void setOuOption(String ouOption) {
		this.ouOption = ouOption;
	}

	public void setBizCategoryOpt(String bizCategoryOpt) {
		this.bizCategoryOpt = bizCategoryOpt;
	}

	public void setInvCategoryOpt(String invCategoryOpt) {
		this.invCategoryOpt = invCategoryOpt;
	}

	public String getWorkorderObjectNo1() {
		return workorderObjectNo1;
	}

	public void setWorkorderObjectNo1(String workorderObjectNo1) {
		this.workorderObjectNo1 = workorderObjectNo1;
	}
}
