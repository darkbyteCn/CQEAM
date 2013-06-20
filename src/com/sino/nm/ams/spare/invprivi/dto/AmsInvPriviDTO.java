package com.sino.nm.ams.spare.invprivi.dto;

import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.ReflectException;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;

import com.sino.ams.constant.DictConstant;

/**
 * <p>Title: 仓库权限表(AMS) AmsInvPrivi</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class AmsInvPriviDTO extends CheckBoxDTO {
    private String rownum = "";
    private int userId;
    private String invCode = "";
    private String actionCode = "";
    private String priviId = "";

    //权限种类
    private String invIn = "0";
    private String invOut = "0";
    private String invQuery = "0";

    /*  private String invApply = "0";
private String invBadIn = "0";
private String invBadReturn = "0";
private String invDiscard = "0";
private String invNewIn = "0";
private String invOrderPrint = "0";
private String invOut = "0";
private String invQuery = "0";
private String invRcvIn = "0";
private String invRepairIn = "0";
private String invSendRepair = "0";
private String invTransfer = "0";*/

    //查询条件
    private String executeUser = "";
    private String executeInv = "";
    
    private String businessCategory = ""; //业务类型，对应业务类型选项卡
    private String bizCategoryOpt = ""; //业务类型下拉列表

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getInvIn() {
        return invIn;
    }

    public void setInvIn(String invIn) {
        this.invIn = invIn;
    }

    public String getRownum() {
        return rownum;
    }

    public void setRownum(String rownum) {
        this.rownum = rownum;
    }

    public String getExecuteInv() {
        return executeInv;
    }

    public void setExecuteInv(String executeInv) {
        this.executeInv = executeInv;
    }


    public String getExecuteUser() {
        return executeUser;
    }

    public void setExecuteUser(String executeUser) {
        this.executeUser = executeUser;
    }


    /**
     * 功能：设置仓库权限表(AMS)属性 用户ID
     *
     * @param userId String
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 功能：设置仓库权限表(AMS)属性 仓库ID
     *
     * @param invCode String
     */
    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    /**
     * 功能：设置仓库权限表(AMS)属性 操作类型
     *
     * @param actionCode String
     */
    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    /**
     * 功能：设置仓库权限表(AMS)属性 序列号
     *
     * @param priviId String
     */
    public void setPriviId(String priviId) {
        this.priviId = priviId;
    }


    public void setInvOut(String invOut) {
        this.invOut = invOut;
    }

    public void setInvQuery(String invQuery) {
        this.invQuery = invQuery;
    }


    /**
     * 功能：获取仓库权限表(AMS)属性 用户ID
     *
     * @return String
     */
    public int getUserId() {
        return this.userId;
    }

    /**
     * 功能：获取仓库权限表(AMS)属性 仓库ID
     *
     * @return String
     */
    public String getInvCode() {
        return this.invCode;
    }

    /**
     * 功能：获取仓库权限表(AMS)属性 操作类型
     *
     * @return String
     */
    public String getActionCode() {
        return this.actionCode;
    }

    /**
     * 功能：获取仓库权限表(AMS)属性 序列号
     *
     * @return String
     */
    public String getPriviId() {
        return this.priviId;
    }


    public String getInvOut() {
        return invOut;
    }

    public String getInvQuery() {
        return invQuery;
    }


    public boolean hasPrivi(String property) throws ReflectException {
        boolean hasPrivi = false;
        property = StrUtil.getJavaField(property);
        if (ReflectionUtil.hasProperty(this.getClass(), property)) {
            String propValue = (String) ReflectionUtil.getProperty(this, property);
            hasPrivi = propValue.equals(DictConstant.HAS_PRIVI_YES);
        }
        return hasPrivi;
    }

	public String getBizCategoryOpt() {
		return bizCategoryOpt;
	}

	public void setBizCategoryOpt(String bizCategoryOpt) {
		this.bizCategoryOpt = bizCategoryOpt;
	}
}
