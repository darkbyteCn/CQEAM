package com.sino.ams.system.trust.dto;

import java.sql.Timestamp;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
* <p>Title: 代维人员表(EAM) AmsMaintainPeople</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsMaintainPeopleDTO extends CheckBoxDTO{

	private String userId = "";
	private String userName = "";
	private String userTelephone = "";
	private String userMobilePhone = "";
	private String email = "";
	private String bpNumber = "";
	private String companyId =  SyBaseSQLUtil.NULL_INT_VALUE+"";
	private Timestamp creationDate = null;
	private int createdBy =  SyBaseSQLUtil.NULL_INT_VALUE;
	private Timestamp lastUpdateDate = null;
	private int lastUpdateBy =  SyBaseSQLUtil.NULL_INT_VALUE;


	/**
	 * 功能：设置代维人员表(EAM)属性 序列号
	 * @param userId String
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}

	/**
	 * 功能：设置代维人员表(EAM)属性 代维人员姓名
	 * @param userName String
	 */
	public void setUserName(String userName){
		this.userName = userName;
	}

	/**
	 * 功能：设置代维人员表(EAM)属性 代维人员固话
	 * @param userTelephone String
	 */
	public void setUserTelephone(String userTelephone){
		this.userTelephone = userTelephone;
	}

	/**
	 * 功能：设置代维人员表(EAM)属性 代维人员移动电话
	 * @param userMobilePhone String
	 */
	public void setUserMobilePhone(String userMobilePhone){
		this.userMobilePhone = userMobilePhone;
	}

	/**
	 * 功能：设置代维人员表(EAM)属性 代维人员电子邮箱
	 * @param email String
	 */
	public void setEmail(String email){
		this.email = email;
	}

	/**
	 * 功能：设置代维人员表(EAM)属性 代维人员BP机号码
	 * @param bpNumber String
	 */
	public void setBpNumber(String bpNumber){
		this.bpNumber = bpNumber;
	}

	/**
	 * 功能：设置代维人员表(EAM)属性 所属代维公司
	 * @param companyId String
	 */
	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	/**
	 * 功能：设置代维人员表(EAM)属性 创建日期
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
	 * 功能：设置代维人员表(EAM)属性 创建人
	 * @param createdBy String
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置代维人员表(EAM)属性 上次修改日期
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
	 * 功能：设置代维人员表(EAM)属性 上次修改人
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}


	/**
	 * 功能：获取代维人员表(EAM)属性 序列号
	 * @return String
	 */
	public String getUserId(){
		return this.userId;
	}

	/**
	 * 功能：获取代维人员表(EAM)属性 代维人员姓名
	 * @return String
	 */
	public String getUserName(){
		return this.userName;
	}

	/**
	 * 功能：获取代维人员表(EAM)属性 代维人员固话
	 * @return String
	 */
	public String getUserTelephone(){
		return this.userTelephone;
	}

	/**
	 * 功能：获取代维人员表(EAM)属性 代维人员移动电话
	 * @return String
	 */
	public String getUserMobilePhone(){
		return this.userMobilePhone;
	}

	/**
	 * 功能：获取代维人员表(EAM)属性 代维人员电子邮箱
	 * @return String
	 */
	public String getEmail(){
		return this.email;
	}

	/**
	 * 功能：获取代维人员表(EAM)属性 代维人员BP机号码
	 * @return String
	 */
	public String getBpNumber(){
		return this.bpNumber;
	}

	/**
	 * 功能：获取代维人员表(EAM)属性 所属代维公司
	 * @return String
	 */
	public String getCompanyId(){
		return this.companyId;
	}

	/**
	 * 功能：获取代维人员表(EAM)属性 创建日期
	 * @return Timestamp
	 */
	public Timestamp getCreationDate(){
		return this.creationDate;
	}

	/**
	 * 功能：获取代维人员表(EAM)属性 创建人
	 * @return String
	 */
	public int getCreatedBy(){
		return this.createdBy;
	}

	/**
	 * 功能：获取代维人员表(EAM)属性 上次修改日期
	 * @return Timestamp
	 */
	public Timestamp getLastUpdateDate(){
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取代维人员表(EAM)属性 上次修改人
	 * @return String
	 */
	public int getLastUpdateBy(){
		return this.lastUpdateBy;
	}

}