package com.sino.ams.spare.check.dto;

import com.sino.base.SinoBaseObject;
import com.sino.base.math.AdvancedNumber;

/**
* <p>Title: 备件盘点行表(AMS) AmsItemCheckLine</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsItemCheckLineDTO extends SinoBaseObject {

	private AdvancedNumber headerId = null;
	private AdvancedNumber lineId = null;
	private AdvancedNumber systemid = null;
	private String systemStatus = "";
	private String scanStatus = "";
	private String archiveStatus = "";
	private String remark = "";
	private String barcode = "";
	private String archiveRemark = "";
     private String itemName="";
    private String itemSpec="";
     private String itemQty="";

    public AmsItemCheckLineDTO() {
		super();

		this.headerId = new AdvancedNumber();
		this.lineId = new AdvancedNumber();
		this.systemid = new AdvancedNumber();
	}

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    /**
	 * 功能：设置备件盘点行表(AMS)属性 头ID
	 * @param headerId AdvancedNumber
	 */
	public void setHeaderId(AdvancedNumber headerId){
		this.headerId = headerId;
	}

	/**
	 * 功能：设置备件盘点行表(AMS)属性 盘点行ID
	 * @param lineId AdvancedNumber
	 */
	public void setLineId(AdvancedNumber lineId){
		this.lineId = lineId;
	}

	/**
	 * 功能：设置备件盘点行表(AMS)属性 设备ID
	 * @param systemid AdvancedNumber
	 */
	public void setSystemid(AdvancedNumber systemid){
		this.systemid = systemid;
	}

	/**
	 * 功能：设置备件盘点行表(AMS)属性 系统状态
	 * @param systemStatus String
	 */
	public void setSystemStatus(String systemStatus){
		this.systemStatus = systemStatus;
	}

	/**
	 * 功能：设置备件盘点行表(AMS)属性 扫描状态
	 * @param scanStatus String
	 */
	public void setScanStatus(String scanStatus){
		this.scanStatus = scanStatus;
	}

	/**
	 * 功能：设置备件盘点行表(AMS)属性 归档状态
	 * @param archiveStatus String
	 */
	public void setArchiveStatus(String archiveStatus){
		this.archiveStatus = archiveStatus;
	}

	/**
	 * 功能：设置备件盘点行表(AMS)属性 备注
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置备件盘点行表(AMS)属性 标签号
	 * @param barcode String
	 */
	public void setBarcode(String barcode){
		this.barcode = barcode;
	}

	/**
	 * 功能：设置备件盘点行表(AMS)属性 核实备注(0:以扫描结果为准；1:以目前状态为准)
	 * @param archiveRemark String
	 */
	public void setArchiveRemark(String archiveRemark){
		this.archiveRemark = archiveRemark;
	}


	/**
	 * 功能：获取备件盘点行表(AMS)属性 头ID
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getHeaderId() {
		return this.headerId;
	}

	/**
	 * 功能：获取备件盘点行表(AMS)属性 盘点行ID
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getLineId() {
		return this.lineId;
	}

	/**
	 * 功能：获取备件盘点行表(AMS)属性 设备ID
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getSystemid() {
		return this.systemid;
	}

	/**
	 * 功能：获取备件盘点行表(AMS)属性 系统状态
	 * @return String
	 */
	public String getSystemStatus() {
		return this.systemStatus;
	}

	/**
	 * 功能：获取备件盘点行表(AMS)属性 扫描状态
	 * @return String
	 */
	public String getScanStatus() {
		return this.scanStatus;
	}

	/**
	 * 功能：获取备件盘点行表(AMS)属性 归档状态
	 * @return String
	 */
	public String getArchiveStatus() {
		return this.archiveStatus;
	}

	/**
	 * 功能：获取备件盘点行表(AMS)属性 备注
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取备件盘点行表(AMS)属性 标签号
	 * @return String
	 */
	public String getBarcode() {
		return this.barcode;
	}

	/**
	 * 功能：获取备件盘点行表(AMS)属性 核实备注(0:以扫描结果为准；1:以目前状态为准)
	 * @return String
	 */
	public String getArchiveRemark() {
		return this.archiveRemark;
	}

}