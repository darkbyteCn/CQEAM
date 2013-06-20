package com.sino.ams.web.item.dto;

import java.sql.Timestamp;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
* <p>Title: 实物资产维修成本(EAM) EtsItemFixfee</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsItemFixfeeDTO extends CheckBoxDTO{

	private String systemId = "";
	private String barcode = "";
	private SimpleCalendar fixDate = null;
    private String amount = "";
	private String fixNo = "";
	private String attribute1 = "";
	private String attribute2 = "";
	private String remark = "";
	private Timestamp creationDate = null;
	private int createdBy;
	private Timestamp lastUpdateDate = null;
	private int lastUpdateBy;
    private String itemName="";
    private String fromDate="";
    private String toDate="";
    private String itemSpec="";
    private String company="";

     public EtsItemFixfeeDTO(){
         this.fixDate=new SimpleCalendar();
     }

   public SimpleCalendar getFixDate() throws CalendarException {
        fixDate.setCalPattern(getCalPattern());
        return this.fixDate;
    }

    public void setFixDate(String fixDate) throws CalendarException {
        this.fixDate.setCalendarValue(fixDate);
    }
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }







    /**
	 * 功能：设置实物资产维修成本(EAM)属性 序列号
	 * @param systemId String
	 */
	public void setSystemId(String systemId){
		this.systemId = systemId;
	}





	/**
	 * 功能：设置实物资产维修成本(EAM)属性 维修费用
	 * @param amount String
	 */
	public void setAmount(String amount){
		this.amount = amount;
	}

	/**
	 * 功能：设置实物资产维修成本(EAM)属性 null
	 * @param fixNo String
	 */
	public void setFixNo(String fixNo){
		this.fixNo = fixNo;
	}

	/**
	 * 功能：设置实物资产维修成本(EAM)属性 null
	 * @param attribute1 String
	 */
	public void setAttribute1(String attribute1){
		this.attribute1 = attribute1;
	}

	/**
	 * 功能：设置实物资产维修成本(EAM)属性 null
	 * @param attribute2 String
	 */
	public void setAttribute2(String attribute2){
		this.attribute2 = attribute2;
	}

	/**
	 * 功能：设置实物资产维修成本(EAM)属性 备注
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置实物资产维修成本(EAM)属性 创建日期
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
	 * 功能：设置实物资产维修成本(EAM)属性 创建人
	 * @param createdBy String
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置实物资产维修成本(EAM)属性 上次修改日期
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
	 * 功能：设置实物资产维修成本(EAM)属性 上次修改人
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}


	/**
	 * 功能：获取实物资产维修成本(EAM)属性 序列号
	 * @return String
	 */
	public String getSystemId(){
		return this.systemId;
	}

	/**
	 * 功能：获取实物资产维修成本(EAM)属性 标签号
	 * @return String
	 */

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }


	/**
	 * 功能：获取实物资产维修成本(EAM)属性 维修费用
	 * @return String
	 */
	public String getAmount(){
		return this.amount;
	}

	/**
	 * 功能：获取实物资产维修成本(EAM)属性 null
	 * @return String
	 */
	public String getFixNo(){
		return this.fixNo;
	}

	/**
	 * 功能：获取实物资产维修成本(EAM)属性 null
	 * @return String
	 */
	public String getAttribute1(){
		return this.attribute1;
	}

	/**
	 * 功能：获取实物资产维修成本(EAM)属性 null
	 * @return String
	 */
	public String getAttribute2(){
		return this.attribute2;
	}

	/**
	 * 功能：获取实物资产维修成本(EAM)属性 备注
	 * @return String
	 */
	public String getRemark(){
		return this.remark;
	}

	/**
	 * 功能：获取实物资产维修成本(EAM)属性 创建日期
	 * @return Timestamp
	 */
	public Timestamp getCreationDate(){
		return this.creationDate;
	}

	/**
	 * 功能：获取实物资产维修成本(EAM)属性 创建人
	 * @return String
	 */
	public int getCreatedBy(){
		return this.createdBy;
	}

	/**
	 * 功能：获取实物资产维修成本(EAM)属性 上次修改日期
	 * @return Timestamp
	 */
	public Timestamp getLastUpdateDate(){
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取实物资产维修成本(EAM)属性 上次修改人
	 * @return String
	 */
	public int getLastUpdateBy(){
		return this.lastUpdateBy;
	}

}