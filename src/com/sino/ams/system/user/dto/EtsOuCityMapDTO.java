package com.sino.ams.system.user.dto;

import java.sql.Timestamp;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
* <p>Title: EtsOuCityMap</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsOuCityMapDTO extends CheckBoxDTO{
	private int organizationId;
	private String company = "";
	private String companyCode = "";
	private String enabled = "";
	private Timestamp creationDate = null;
	private int createdBy;
	private Timestamp lastUpdateDate = null;
	private int lastUpdateBy;
	private String bookTypeCode = "";
	private String bookTypeName = "";
	/**
	 * 功能：设置DTO属性 organizationId
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置DTO属性 company
	 * @param company String
	 */
	public void setCompany(String company){
		this.company = company;
	}

	/**
	 * 功能：设置DTO属性 companyCode
	 * @param companyCode String
	 */
	public void setCompanyCode(String companyCode){
		this.companyCode = companyCode;
	}

	/**
	 * 功能：设置DTO属性 enabled
	 * @param enabled String
	 */
	public void setEnabled(String enabled){
		this.enabled = enabled;
	}

	/**
	 * 功能：设置DTO属性 creationDate
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
	 * 功能：设置DTO属性 createdBy
	 * @param createdBy String
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置DTO属性 lastUpdateDate
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
	 * 功能：设置DTO属性 lastUpdateBy
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}

	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}

	/**
	 * 功能：获取DTO属性 organizationId
	 * @return String
	 */
	public int getOrganizationId(){
		return this.organizationId;
	}

	/**
	 * 功能：获取DTO属性 company
	 * @return String
	 */
	public String getCompany(){
		return this.company;
	}

	/**
	 * 功能：获取DTO属性 companyCode
	 * @return String
	 */
	public String getCompanyCode(){
		return this.companyCode;
	}

	/**
	 * 功能：获取DTO属性 enabled
	 * @return String
	 */
	public String getEnabled(){
		return this.enabled;
	}

	/**
	 * 功能：获取DTO属性 creationDate
	 * @return Timestamp
	 */
	public Timestamp getCreationDate(){
		return this.creationDate;
	}

	/**
	 * 功能：获取DTO属性 createdBy
	 * @return String
	 */
	public int getCreatedBy(){
		return this.createdBy;
	}

	/**
	 * 功能：获取DTO属性 lastUpdateDate
	 * @return Timestamp
	 */
	public Timestamp getLastUpdateDate(){
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取DTO属性 lastUpdateBy
	 * @return String
	 */
	public int getLastUpdateBy(){
		return this.lastUpdateBy;
	}

	public String getBookTypeCode() {
		return bookTypeCode;
	}

	public String getBookTypeName() {
		return bookTypeName;
	}

}
