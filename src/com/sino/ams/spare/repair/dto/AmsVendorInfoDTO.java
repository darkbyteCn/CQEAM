package com.sino.ams.spare.repair.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 备件维修厂商信息 AmsVendorInfo</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsVendorInfoDTO extends CheckBoxDTO{
	private String vendorCode = "";
	private String vendorName = "";
	private String address = "";
	private String contact = "";
	private String phone = "";
	private String fax = "";
	private String attribute1 = "";
	private String attribute2 = "";
    private String vendorId = "";

    public AmsVendorInfoDTO() {
		super();
	}

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
    /**
	 * 功能：设置备件维修厂商信息属性 供应商编码
	 * @param vendorCode String
	 */
	public void setVendorCode(String vendorCode){
		this.vendorCode = vendorCode;
	}

	/**
	 * 功能：设置备件维修厂商信息属性 供应商名称
	 * @param vendorName String
	 */
	public void setVendorName(String vendorName){
		this.vendorName = vendorName;
	}

	/**
	 * 功能：设置备件维修厂商信息属性 供应商地址
	 * @param address String
	 */
	public void setAddress(String address){
		this.address = address;
	}

	/**
	 * 功能：设置备件维修厂商信息属性 联系人
	 * @param contact String
	 */
	public void setContact(String contact){
		this.contact = contact;
	}

	/**
	 * 功能：设置备件维修厂商信息属性 电话
	 * @param phone String
	 */
	public void setPhone(String phone){
		this.phone = phone;
	}

	/**
	 * 功能：设置备件维修厂商信息属性 传真
	 * @param fax String
	 */
	public void setFax(String fax){
		this.fax = fax;
	}

	/**
	 * 功能：设置备件维修厂商信息属性 ATTRIBUTE1
	 * @param attribute1 String
	 */
	public void setAttribute1(String attribute1){
		this.attribute1 = attribute1;
	}

	/**
	 * 功能：设置备件维修厂商信息属性 ATTRIBUTE2
	 * @param attribute2 String
	 */
	public void setAttribute2(String attribute2){
		this.attribute2 = attribute2;
	}


	/**
	 * 功能：获取备件维修厂商信息属性 供应商编码
	 * @return String
	 */
	public String getVendorCode() {
		return this.vendorCode;
	}

	/**
	 * 功能：获取备件维修厂商信息属性 供应商名称
	 * @return String
	 */
	public String getVendorName() {
		return this.vendorName;
	}

	/**
	 * 功能：获取备件维修厂商信息属性 供应商地址
	 * @return String
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * 功能：获取备件维修厂商信息属性 联系人
	 * @return String
	 */
	public String getContact() {
		return this.contact;
	}

	/**
	 * 功能：获取备件维修厂商信息属性 电话
	 * @return String
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * 功能：获取备件维修厂商信息属性 传真
	 * @return String
	 */
	public String getFax() {
		return this.fax;
	}

	/**
	 * 功能：获取备件维修厂商信息属性 ATTRIBUTE1
	 * @return String
	 */
	public String getAttribute1() {
		return this.attribute1;
	}

	/**
	 * 功能：获取备件维修厂商信息属性 ATTRIBUTE2
	 * @return String
	 */
	public String getAttribute2() {
		return this.attribute2;
	}

}