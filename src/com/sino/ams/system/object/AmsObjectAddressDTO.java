package com.sino.ams.system.object;

import com.sino.ams.bean.CommonRecordDTO;

/**
 *
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsObjectAddressDTO extends CommonRecordDTO{
	private String addressId = "";
	private String objectNo = "";
	private String boxNo = "";
    private String netUnit = "";
    private int organizationId;
    private String remark = "";
    private String addressNo = "";
    private String objectNos = "";

	/**
	* 功能：自动生成数据传输对象构造函数：组合地点表(EAM)。
	* <B>有日期字段时同时需要完成其初始化，如有其他需要初始化的字段请在此完成。
	* 尤其是生成代码后数据库表又增加了日期字段，必须在此增加其初始化</B>
	*/
	public AmsObjectAddressDTO() {
		super();
	}

	/**
	 * 功能：设置属性： 组合地点
	 * @param addressId String
	 */
	public void setAddressId(String addressId){
		this.addressId = addressId;
	}

	/**
	 * 功能：设置属性： 地点ID
	 * @param objectNo String
	 */
	public void setObjectNo(String objectNo){
		this.objectNo = objectNo;
	}

	/**
	 * 功能：设置属性： 机柜编号
	 * @param boxNo String
	 */
	public void setBoxNo(String boxNo){
		this.boxNo = boxNo;
	}

	/**
	 * 功能：设置属性： 网元编号
	 * @param netUnit String
	 */
	public void setNetUnit(String netUnit){
		this.netUnit = netUnit;
	}

	/**
	 * 功能：设置属性： 所属OU
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置属性： 备注
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置属性： 组合地点
	 * @param addressNo String
	 */
	public void setAddressNo(String addressNo){
		this.addressNo = addressNo;
	}


	/**
	 * 功能：获取属性： 组合地点
	 * @return String
	 */
	public String getAddressId() {
		return this.addressId;
	}

	/**
	 * 功能：获取属性： 地点ID
	 * @return String
	 */
	public String getObjectNo() {
		return this.objectNo;
	}

	/**
	 * 功能：获取属性： 机柜编号
	 * @return String
	 */
	public String getBoxNo() {
		return this.boxNo;
	}

	/**
	 * 功能：获取属性： 网元编号
	 * @return String
	 */
	public String getNetUnit() {
		return this.netUnit;
	}

	/**
	 * 功能：获取属性： 所属OU
	 * @return String
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取属性： 备注
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取属性： 组合地点
	 * @return String
	 */
	public String getAddressNo() {
		return this.addressNo;
	}

    public String getObjectNos() {
        return objectNos;
    }

    public void setObjectNos(String objectNos) {
        this.objectNos = objectNos;
    }
}
