package com.sino.ams.system.item.dto;

import java.sql.Timestamp;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
* <p>Title: ETS_MIS_PO_VENDORS EtsMisPoVendors</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsMisPoVendorsDTO extends CheckBoxDTO{

	private String vendorId = "";
	private String vendorName = "";
	private String vendorNameAlt = "";
	private String segment1 = "";
	private String summaryFlag = "";
	private Timestamp creationDate = null;
	private int createdBy;
	private Timestamp lastUpdateDate = null;
	private int lastUpdateBy;


	/**
	 * 功能：设置ETS_MIS_PO_VENDORS属性 null
	 * @param vendorId String
	 */
	public void setVendorId(String vendorId){
		this.vendorId = vendorId;
	}

	/**
	 * 功能：设置ETS_MIS_PO_VENDORS属性 null
	 * @param vendorName String
	 */
	public void setVendorName(String vendorName){
		this.vendorName = vendorName;
	}

	/**
	 * 功能：设置ETS_MIS_PO_VENDORS属性 null
	 * @param vendorNameAlt String
	 */
	public void setVendorNameAlt(String vendorNameAlt){
		this.vendorNameAlt = vendorNameAlt;
	}

	/**
	 * 功能：设置ETS_MIS_PO_VENDORS属性 null
	 * @param segment1 String
	 */
	public void setSegment1(String segment1){
		this.segment1 = segment1;
	}

	/**
	 * 功能：设置ETS_MIS_PO_VENDORS属性 null
	 * @param summaryFlag String
	 */
	public void setSummaryFlag(String summaryFlag){
		this.summaryFlag = summaryFlag;
	}

	/**
	 * 功能：设置ETS_MIS_PO_VENDORS属性 创建日期
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
	 * 功能：设置ETS_MIS_PO_VENDORS属性 创建人
	 * @param createdBy String
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置ETS_MIS_PO_VENDORS属性 上次修改日期
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
	 * 功能：设置ETS_MIS_PO_VENDORS属性 上次修改人
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}


	/**
	 * 功能：获取ETS_MIS_PO_VENDORS属性 null
	 * @return String
	 */
	public String getVendorId(){
		return this.vendorId;
	}

	/**
	 * 功能：获取ETS_MIS_PO_VENDORS属性 null
	 * @return String
	 */
	public String getVendorName(){
		return this.vendorName;
	}

	/**
	 * 功能：获取ETS_MIS_PO_VENDORS属性 null
	 * @return String
	 */
	public String getVendorNameAlt(){
		return this.vendorNameAlt;
	}

	/**
	 * 功能：获取ETS_MIS_PO_VENDORS属性 null
	 * @return String
	 */
	public String getSegment1(){
		return this.segment1;
	}

	/**
	 * 功能：获取ETS_MIS_PO_VENDORS属性 null
	 * @return String
	 */
	public String getSummaryFlag(){
		return this.summaryFlag;
	}

	/**
	 * 功能：获取ETS_MIS_PO_VENDORS属性 创建日期
	 * @return Timestamp
	 */
	public Timestamp getCreationDate(){
		return this.creationDate;
	}

	/**
	 * 功能：获取ETS_MIS_PO_VENDORS属性 创建人
	 * @return String
	 */
	public int getCreatedBy(){
		return this.createdBy;
	}

	/**
	 * 功能：获取ETS_MIS_PO_VENDORS属性 上次修改日期
	 * @return Timestamp
	 */
	public Timestamp getLastUpdateDate(){
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取ETS_MIS_PO_VENDORS属性 上次修改人
	 * @return String
	 */
	public int getLastUpdateBy(){
		return this.lastUpdateBy;
	}

}