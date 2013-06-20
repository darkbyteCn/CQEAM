package com.sino.ams.yj.resource.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 战备应急通信资源 AmsYjCommunicateResource</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsYjCommunicateResourceDTO extends CheckBoxDTO{

	private String resourceId = "";
	private String deptName = "";
	private String equipmentName = "";
	private String resourceQty = "";
	private String location = "";
	private String model = "";
	private String normalStatus = "";
	private String chargeDept = "";
	private String charger = "";
	private String chargerMobile = "";
	private String keeper = "";
	private String keeperMobile = "";
	private String remark = "";
	private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;
	private SimpleCalendar creationDate = null;
	private String createUser = "";
	private SimpleCalendar lastUpdateDate = null;
	private String lastUpdateUser = "";
    private String orgOpt="";
    private String equipmentOpt ="";

    private String comvan="";//是否有应急通信车
    private String isatphone="";//是否有卫星电话

    public AmsYjCommunicateResourceDTO() {
		super();
		this.creationDate = new SimpleCalendar();
		this.lastUpdateDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置战备应急通信资源属性 序号
	 * @param resourceId String
	 */
	public void setResourceId(String resourceId){
		this.resourceId = resourceId;
	}

	/**
	 * 功能：设置战备应急通信资源属性 	单位名称
	 * @param deptName String
	 */
	public void setDeptName(String deptName){
		this.deptName = deptName;
	}

	/**
	 * 功能：设置战备应急通信资源属性 	装备名称
	 * @param equipmentName String
	 */
	public void setEquipmentName(String equipmentName){
		this.equipmentName = equipmentName;
	}

	/**
	 * 功能：设置战备应急通信资源属性 数量
	 * @param resourceQty String
	 */
	public void setResourceQty(String resourceQty){
		this.resourceQty = resourceQty;
	}

	/**
	 * 功能：设置战备应急通信资源属性 	置放位置
	 * @param location String
	 */
	public void setLocation(String location){
		this.location = location;
	}

	/**
	 * 功能：设置战备应急通信资源属性 	规格
	 * @param model String
	 */
	public void setModel(String model){
		this.model = model;
	}

	/**
	 * 功能：设置战备应急通信资源属性 是否能正常使用
	 * @param normalStatus String
	 */
	public void setNormalStatus(String normalStatus){
		this.normalStatus = normalStatus;
	}

	/**
	 * 功能：设置战备应急通信资源属性 	主管部门
	 * @param chargeDept String
	 */
	public void setChargeDept(String chargeDept){
		this.chargeDept = chargeDept;
	}

	/**
	 * 功能：设置战备应急通信资源属性 	负责人
	 * @param charger String
	 */
	public void setCharger(String charger){
		this.charger = charger;
	}

	/**
	 * 功能：设置战备应急通信资源属性 	手机
	 * @param chargerMobile String
	 */
	public void setChargerMobile(String chargerMobile){
		this.chargerMobile = chargerMobile;
	}

	/**
	 * 功能：设置战备应急通信资源属性 	保管人
	 * @param keeper String
	 */
	public void setKeeper(String keeper){
		this.keeper = keeper;
	}

	/**
	 * 功能：设置战备应急通信资源属性 	保管人-手机
	 * @param keeperMobile String
	 */
	public void setKeeperMobile(String keeperMobile){
		this.keeperMobile = keeperMobile;
	}

	/**
	 * 功能：设置战备应急通信资源属性 备注
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置战备应急通信资源属性 组织ID
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置战备应急通信资源属性 创建日期
	 * @param creationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		this.creationDate.setCalendarValue(creationDate);
	}

	/**
	 * 功能：设置战备应急通信资源属性 创建人
	 * @param createUser String
	 */
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	/**
	 * 功能：设置战备应急通信资源属性 上次修改日期
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}

	/**
	 * 功能：设置战备应急通信资源属性 上次修改人
	 * @param lastUpdateUser String
	 */
	public void setLastUpdateUser(String lastUpdateUser){
		this.lastUpdateUser = lastUpdateUser;
	}


	/**
	 * 功能：获取战备应急通信资源属性 序号
	 * @return String
	 */
	public String getResourceId() {
		return this.resourceId;
	}

	/**
	 * 功能：获取战备应急通信资源属性 	单位名称
	 * @return String
	 */
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * 功能：获取战备应急通信资源属性 	装备名称
	 * @return String
	 */
	public String getEquipmentName() {
		return this.equipmentName;
	}

	/**
	 * 功能：获取战备应急通信资源属性 数量
	 * @return String
	 */
	public String getResourceQty() {
		return this.resourceQty;
	}

	/**
	 * 功能：获取战备应急通信资源属性 	置放位置
	 * @return String
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * 功能：获取战备应急通信资源属性 	规格
	 * @return String
	 */
	public String getModel() {
		return this.model;
	}

	/**
	 * 功能：获取战备应急通信资源属性 是否能正常使用
	 * @return String
	 */
	public String getNormalStatus() {
		return this.normalStatus;
	}

	/**
	 * 功能：获取战备应急通信资源属性 	主管部门
	 * @return String
	 */
	public String getChargeDept() {
		return this.chargeDept;
	}

	/**
	 * 功能：获取战备应急通信资源属性 	负责人
	 * @return String
	 */
	public String getCharger() {
		return this.charger;
	}

	/**
	 * 功能：获取战备应急通信资源属性 	手机
	 * @return String
	 */
	public String getChargerMobile() {
		return this.chargerMobile;
	}

	/**
	 * 功能：获取战备应急通信资源属性 	保管人
	 * @return String
	 */
	public String getKeeper() {
		return this.keeper;
	}

	/**
	 * 功能：获取战备应急通信资源属性 	保管人-手机
	 * @return String
	 */
	public String getKeeperMobile() {
		return this.keeperMobile;
	}

	/**
	 * 功能：获取战备应急通信资源属性 备注
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取战备应急通信资源属性 组织ID
	 * @return String
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取战备应急通信资源属性 创建日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getCreationDate() throws CalendarException {
		creationDate.setCalPattern(getCalPattern());
		return this.creationDate;
	}

	/**
	 * 功能：获取战备应急通信资源属性 创建人
	 * @return String
	 */
	public String getCreateUser() {
		return this.createUser;
	}

	/**
	 * 功能：获取战备应急通信资源属性 上次修改日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastUpdateDate() throws CalendarException {
		lastUpdateDate.setCalPattern(getCalPattern());
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取战备应急通信资源属性 上次修改人
	 * @return String
	 */
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

    public String getOrgOpt() {
        return orgOpt;
    }

    public void setOrgOpt(String orgOpt) {
        this.orgOpt = orgOpt;
    }

    public String getComvan() {
        return comvan;
    }

    public void setComvan(String comvan) {
        this.comvan = comvan;
    }

    public String getEquipmentOpt() {
        return equipmentOpt;
    }

    public void setEquipmentOpt(String equipmentOpt) {
        this.equipmentOpt = equipmentOpt;
    }

    public String getIsatphone() {
        return isatphone;
    }

    public void setIsatphone(String isatphone) {
        this.isatphone = isatphone;
    }
}