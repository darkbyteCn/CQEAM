package com.sino.ams.system.trust.dto;

import java.sql.Timestamp;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
* <p>Title: 代维公司表(EAM) AmsMaintainCompany</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsMaintainCompanyDTO extends CheckBoxDTO{
    private String companyId="";
	private String name = "";
	private String address = "";
	private String legalRepresentative = "";
	private String contactPeople = "";
	private String officeTelephone = "";
	private String contactTelephone = "";
	private String faxNumber = "";
	private int organizationId ;
	private Timestamp creationDate = null;
	private int createdBy;
    private Timestamp lastUpdateDate = null;
	private int lastUpdateBy;
	private String  countyCode="";
	private String remark = "";

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String  getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String  countyCode) {
        this.countyCode = countyCode;
    }

    public int getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(int lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String  companyId) {
        this.companyId = companyId;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	/**
	 * 功能：设置代维公司表(EAM)属性 公司名称
	 * @param name String
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * 功能：设置代维公司表(EAM)属性 公司地址
	 * @param address String
	 */
	public void setAddress(String address){
		this.address = address;
	}

	/**
	 * 功能：设置代维公司表(EAM)属性 法人代表
	 * @param legalRepresentative String
	 */
	public void setLegalRepresentative(String legalRepresentative){
		this.legalRepresentative = legalRepresentative;
	}

	/**
	 * 功能：设置代维公司表(EAM)属性 联系人
	 * @param contactPeople String
	 */
	public void setContactPeople(String contactPeople){
		this.contactPeople = contactPeople;
	}

	/**
	 * 功能：设置代维公司表(EAM)属性 办公电话
	 * @param officeTelephone String
	 */
	public void setOfficeTelephone(String officeTelephone){
		this.officeTelephone = officeTelephone;
	}

	/**
	 * 功能：设置代维公司表(EAM)属性 联系人电话
	 * @param contactTelephone String
	 */
	public void setContactTelephone(String contactTelephone){
		this.contactTelephone = contactTelephone;
	}

	/**
	 * 功能：设置代维公司表(EAM)属性 传真号码
	 * @param faxNumber String
	 */
	public void setFaxNumber(String faxNumber){
		this.faxNumber = faxNumber;
	}

	/**
	 * 功能：设置代维公司表(EAM)属性 所属OU组织ID
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置代维公司表(EAM)属性 创建日期
	 * @param creationDate Timestamp
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		if(!StrUtil.isEmpty(creationDate)){
			SimpleCalendar cal = new SimpleCalendar(creationDate);
			this.creationDate = cal.getSQLTimestamp();
		}
	}

	/**
	 * 功能：设置代维公司表(EAM)属性 上次修改日期
	 * @param lastUpdateDate Timestamp
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		if(!StrUtil.isEmpty(lastUpdateDate)){
			SimpleCalendar cal = new SimpleCalendar(lastUpdateDate);
			this.lastUpdateDate = cal.getSQLTimestamp();
		}
	}

	/**
	 * 功能：获取代维公司表(EAM)属性 公司名称
	 * @return String
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * 功能：获取代维公司表(EAM)属性 公司地址
	 * @return String
	 */
	public String getAddress(){
		return this.address;
	}

	/**
	 * 功能：获取代维公司表(EAM)属性 法人代表
	 * @return String
	 */
	public String getLegalRepresentative(){
		return this.legalRepresentative;
	}

	/**
	 * 功能：获取代维公司表(EAM)属性 联系人
	 * @return String
	 */
	public String getContactPeople(){
		return this.contactPeople;
	}

	/**
	 * 功能：获取代维公司表(EAM)属性 办公电话
	 * @return String
	 */
	public String getOfficeTelephone(){
		return this.officeTelephone;
	}

	/**
	 * 功能：获取代维公司表(EAM)属性 联系人电话
	 * @return String
	 */
	public String getContactTelephone(){
		return this.contactTelephone;
	}

	/**
	 * 功能：获取代维公司表(EAM)属性 传真号码
	 * @return String
	 */
	public String getFaxNumber(){
		return this.faxNumber;
	}

	/**
	 * 功能：获取代维公司表(EAM)属性 所属OU组织ID
	 * @return String
	 */
	public int getOrganizationId(){
		return this.organizationId;
	}

	/**
	 * 功能：获取代维公司表(EAM)属性 创建日期
	 * @return Timestamp
	 */
	public Timestamp getCreationDate(){
		return this.creationDate;
	}

	/**
	 * 功能：获取代维公司表(EAM)属性 上次修改日期
	 * @return Timestamp
	 */
	public Timestamp getLastUpdateDate(){
		return this.lastUpdateDate;
	}

}