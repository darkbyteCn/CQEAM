package com.sino.ams.spare.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 备件业务明细表(AMS) AmsItemTransD</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsItemTransDDTO extends CheckBoxDTO{

	private String transId = "";
	private String lineId = "";
	private String detailId = "";
	private int organizationId = -1;
	private String itemCode = "-1";
	private String barcode = "";
	private int quantity = -1;
    private String confirmQuantity = "";
	private String curOnhandQty = "";
    private String serialNo = "";
    private String isAllot="0";
    private String itemName="";
    private String itemSpec="";
    private String troubleReason="";
    private String troubleLoc="";
    private String itemCategory = "";

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getTroubleReason() {
    return troubleReason;
}

    public void setTroubleReason(String troubleReason) {
        this.troubleReason = troubleReason;
    }

    public String getTroubleLoc() {
        return troubleLoc;
    }

    public void setTroubleLoc(String troubleLoc) {
        this.troubleLoc = troubleLoc;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public AmsItemTransDDTO() {
		super();
	}

	/**
	 * 功能：设置备件业务明细表(AMS)属性 事务交易ID
	 * @param transId String
	 */
	public void setTransId(String transId){
		this.transId = transId;
	}

	/**
	 * 功能：设置备件业务明细表(AMS)属性 行ID
	 * @param lineId String
	 */
	public void setLineId(String lineId){
		this.lineId = lineId;
	}

	/**
	 * 功能：设置备件业务明细表(AMS)属性 明细ID
	 * @param detailId String
	 */
	public void setDetailId(String detailId){
		this.detailId = detailId;
	}

	/**
	 * 功能：设置备件业务明细表(AMS)属性 OU
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置备件业务明细表(AMS)属性 设备代码
	 * @param itemCode String
	 */
	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

	/**
	 * 功能：设置备件业务明细表(AMS)属性 数量
	 * @param quantity String
	 */
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	/**
	 * 功能：设置备件业务明细表(AMS)属性 确认数量
	 * @param confirmQuantity String
	 */
	public void setConfirmQuantity(String confirmQuantity){
		this.confirmQuantity = confirmQuantity;
	}

	/**
	 * 功能：设置备件业务明细表(AMS)属性 当前库存现有量
	 * @param curOnhandQty String
	 */
	public void setCurOnhandQty(String curOnhandQty){
		this.curOnhandQty = curOnhandQty;
	}


	/**
	 * 功能：获取备件业务明细表(AMS)属性 事务交易ID
	 * @return String
	 */
	public String getTransId() {
		return this.transId;
	}

	/**
	 * 功能：获取备件业务明细表(AMS)属性 行ID
	 * @return String
	 */
	public String getLineId() {
		return this.lineId;
	}

	/**
	 * 功能：获取备件业务明细表(AMS)属性 明细ID
	 * @return String
	 */
	public String getDetailId() {
		return this.detailId;
	}

	/**
	 * 功能：获取备件业务明细表(AMS)属性 OU
	 * @return String
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取备件业务明细表(AMS)属性 设备代码
	 * @return String
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 功能：获取备件业务明细表(AMS)属性 数量
	 * @return String
	 */
	public int getQuantity() {
		return this.quantity;
	}

	/**
	 * 功能：获取备件业务明细表(AMS)属性 确认数量
	 * @return String
	 */
	public String getConfirmQuantity() {
		return this.confirmQuantity;
	}

	/**
	 * 功能：获取备件业务明细表(AMS)属性 当前库存现有量
	 * @return String
	 */
	public String getCurOnhandQty() {
		return this.curOnhandQty;
	}

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getAllot() {
        return isAllot;
    }

    public void setAllot(String allot) {
        isAllot = allot;
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
}