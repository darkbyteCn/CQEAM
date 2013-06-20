package com.sino.ams.system.trust.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;

/**
* <p>Title: 待维责任 AmsMaintainResponsibility</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 张云
* @version 1.0
*/

public class AmsMaintainResponsibilityDTO extends EtsObjectDTO{

	private String systemId = "";
	private String companyId = "";
    private String companyId2 = "";
	private int objectNo = SyBaseSQLUtil.NULL_INT_VALUE;
	private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;
	private String remark = "";

    private String  checkPoint = "";

	private String toConfirmLocOpt = "";
	private String confirmedLocOpt = "";
	private String countyOpt = "";





	/**
	 * 功能：设置待维责任属性 备注
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}



	public String getSystemId() {
		return systemId;
	}



	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}



	public String getCompanyId() {
		return companyId;
	}



	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}



	public String getCompanyId2() {
		return companyId2;
	}



	public void setCompanyId2(String companyId2) {
		this.companyId2 = companyId2;
	}



	public int getObjectNo() {
		return objectNo;
	}



	public void setObjectNo(int objectNo) {
		this.objectNo = objectNo;
	}



	public int getOrganizationId() {
		return organizationId;
	}



	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}



	/**
	 * 功能：获取待维责任属性 备注
	 * @return String
	 */
	public String getRemark(){
		return this.remark;
	}

    /**
	 * 功能：获取检查点（EO.WORKORDER_OBJECT_NO||'@@@'||AC.COMPANY_ID CHECK_POINT）  上次更新人
	 * @return String
	 */
    public String getCheckPoint() {
        return checkPoint;
    }

	public String getConfirmedLocOpt() {
		return confirmedLocOpt;
	}

	public String getToConfirmLocOpt() {
		return toConfirmLocOpt;
	}

	public String getCountyOpt() {
		return countyOpt;
	}

	public void setCheckPoint(String checkPoint) {
        this.checkPoint=checkPoint;
    }

	public void setConfirmedLocOpt(String confirmedLocOpt) {
		this.confirmedLocOpt = confirmedLocOpt;
	}

	public void setToConfirmLocOpt(String toConfirmLocOpt) {
		this.toConfirmLocOpt = toConfirmLocOpt;
	}

	public void setCountyOpt(String countyOpt) {
		this.countyOpt = countyOpt;
	}
}
