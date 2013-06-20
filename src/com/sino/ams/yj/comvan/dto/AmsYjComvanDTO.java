package com.sino.ams.yj.comvan.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.calen.SimpleCalendar;

/**
* <p>Title: 应急通信车信息表 AmsYjComvan</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急通信车
*/

public class AmsYjComvanDTO extends CheckBoxDTO{

	private String comvanId = "";
	private String deptName = "";
	private String manufacturer = "";
	private String model = "";
	private String refitFirm = "";
	private String length = "";                //int
	private String quality = "";              //int
	private String antennaMastForm = "";
	private String hasOilengine = "";
	private String licensePlate = "";
	private String frameNumber = "";
	private String lWH = "";
	private String originalCost = "";          //int
	private String btsManufacturer = "";
	private String btsModel = "";
	private String carrierFrequencyvAllocate = "";
	private String carrierFrequencyvQty = "";  //int
	private String installedBsc = "";
	private String otherGsmUnit = "";
	private String transForm = "";
	private String transItemModel = "";
	private String bandwidth = "";
	private String hasSatelliteTransmissions = "";
	private String typeOfTraffic = "";
	private String useTimes = "";              //int
	private String usedTraffic = "";
	private String useScene = "";
	private String remark = "";
    private int organizationId = -1;
    private SimpleCalendar creationDate = null;
    private String createUser = "";
    private SimpleCalendar lastUpdateDate = null;
    private String lastUpdateUser = "";
    private String resourceId= "";          //int
    private String equipmentName ="";
    private String orgOpt="";


    public AmsYjComvanDTO() {
		super();

        creationDate=new SimpleCalendar();
        lastUpdateDate=new SimpleCalendar();

	}

	/**
	 * 功能：设置应急通信车信息表属性 储备单位
	 * @param deptName String
	 */
	public void setDeptName(String deptName){
		this.deptName = deptName;
	}

	/**
	 * 功能：设置应急通信车信息表属性 生产商
	 * @param manufacturer String
	 */
	public void setManufacturer(String manufacturer){
		this.manufacturer = manufacturer;
	}

	/**
	 * 功能：设置应急通信车信息表属性 型号
	 * @param model String
	 */
	public void setModel(String model){
		this.model = model;
	}

	/**
	 * 功能：设置应急通信车信息表属性 车辆改装厂
	 * @param refitFirm String
	 */
	public void setRefitFirm(String refitFirm){
		this.refitFirm = refitFirm;
	}

	/**
	 * 功能：设置应急通信车信息表属性 整车长度(mm)
	 * @param length String
	 */
	public void setLength(String length){
		this.length = length;
	}



	/**
	 * 功能：设置应急通信车信息表属性 天线桅杆形式（液压/气压/电动/手动）
	 * @param antennaMastForm String
	 */
	public void setAntennaMastForm(String antennaMastForm){
		this.antennaMastForm = antennaMastForm;
	}

	/**
	 * 功能：设置应急通信车信息表属性 是否有油机
	 * @param hasOilengine String
	 */
	public void setHasOilengine(String hasOilengine){
		this.hasOilengine = hasOilengine;
	}

	/**
	 * 功能：设置应急通信车信息表属性 现有车牌照
	 * @param licensePlate String
	 */
	public void setLicensePlate(String licensePlate){
		this.licensePlate = licensePlate;
	}

	/**
	 * 功能：设置应急通信车信息表属性 车架号
	 * @param frameNumber String
	 */
	public void setFrameNumber(String frameNumber){
		this.frameNumber = frameNumber;
	}

	/**
	 * 功能：设置应急通信车信息表属性 长×宽×高(mm)
	 * @param lWH String
	 */
	public void setLWH(String lWH){
		this.lWH = lWH;
	}

	/**
	 * 功能：设置应急通信车信息表属性 资产原值(万元)
	 * @param originalCost String
	 */
	public void setOriginalCost(String originalCost){
		this.originalCost = originalCost;
	}

	/**
	 * 功能：设置应急通信车信息表属性 厂家
	 * @param btsManufacturer String
	 */
	public void setBtsManufacturer(String btsManufacturer){
		this.btsManufacturer = btsManufacturer;
	}

	/**
	 * 功能：设置应急通信车信息表属性  型号
	 * @param btsModel String
	 */
	public void setBtsModel(String btsModel){
		this.btsModel = btsModel;
	}

	/**
	 * 功能：设置应急通信车信息表属性 载频分配
	 * @param carrierFrequencyvAllocate String
	 */
	public void setCarrierFrequencyvAllocate(String carrierFrequencyvAllocate){
		this.carrierFrequencyvAllocate = carrierFrequencyvAllocate;
	}

	/**
	 * 功能：设置应急通信车信息表属性 总载频数
	 * @param carrierFrequencyvQty String
	 */
	public void setCarrierFrequencyvQty(String carrierFrequencyvQty){
		this.carrierFrequencyvQty = carrierFrequencyvQty;
	}

	/**
	 * 功能：设置应急通信车信息表属性 是否安装BSC
	 * @param installedBsc String
	 */
	public void setInstalledBsc(String installedBsc){
		this.installedBsc = installedBsc;
	}

	/**
	 * 功能：设置应急通信车信息表属性 安装的其他GSM系统网元
	 * @param otherGsmUnit String
	 */
	public void setOtherGsmUnit(String otherGsmUnit){
		this.otherGsmUnit = otherGsmUnit;
	}

	/**
	 * 功能：设置应急通信车信息表属性 传输方式
	 * @param transForm String
	 */
	public void setTransForm(String transForm){
		this.transForm = transForm;
	}

	/**
	 * 功能：设置应急通信车信息表属性 设备型号
	 * @param transItemModel String
	 */
	public void setTransItemModel(String transItemModel){
		this.transItemModel = transItemModel;
	}

	/**
	 * 功能：设置应急通信车信息表属性 是否有卫星传输
	 * @param hasSatelliteTransmissions String
	 */
	public void setHasSatelliteTransmissions(String hasSatelliteTransmissions){
		this.hasSatelliteTransmissions = hasSatelliteTransmissions;
	}

	/**
	 * 功能：设置应急通信车信息表属性 可提供业务种类（GSM/TD-SCDMA/WLAN/会议电视等）
	 * @param typeOfTraffic String
	 */
	public void setTypeOfTraffic(String typeOfTraffic){
		this.typeOfTraffic = typeOfTraffic;
	}

	/**
	 * 功能：设置应急通信车信息表属性 使用次数
	 * @param useTimes String
	 */
	public void setUseTimes(String useTimes){
		this.useTimes = useTimes;
	}

	/**
	 * 功能：设置应急通信车信息表属性 使用时提供的业务（GSM/TD-SCDMA/WLAN/会议电视等）
	 * @param usedTraffic String
	 */
	public void setUsedTraffic(String usedTraffic){
		this.usedTraffic = usedTraffic;
	}

	/**
	 * 功能：设置应急通信车信息表属性 主要使用场景及地点
	 * @param useScene String
	 */
	public void setUseScene(String useScene){
		this.useScene = useScene;
	}

	/**
	 * 功能：设置应急通信车信息表属性 其他说明
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：获取应急通信车信息表属性 储备单位
	 * @return String
	 */
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * 功能：获取应急通信车信息表属性 生产商
	 * @return String
	 */
	public String getManufacturer() {
		return this.manufacturer;
	}

	/**
	 * 功能：获取应急通信车信息表属性 型号
	 * @return String
	 */
	public String getModel() {
		return this.model;
	}

	/**
	 * 功能：获取应急通信车信息表属性 车辆改装厂
	 * @return String
	 */
	public String getRefitFirm() {
		return this.refitFirm;
	}

	/**
	 * 功能：获取应急通信车信息表属性 整车长度(mm)
	 * @return String
	 */
	public String getLength() {
		return this.length;
	}

	
	/**
	 * 功能：获取应急通信车信息表属性 天线桅杆形式（液压/气压/电动/手动）
	 * @return String
	 */
	public String getAntennaMastForm() {
		return this.antennaMastForm;
	}

	/**
	 * 功能：获取应急通信车信息表属性 是否有油机
	 * @return String
	 */
	public String getHasOilengine() {
		return this.hasOilengine;
	}

	/**
	 * 功能：获取应急通信车信息表属性 现有车牌照
	 * @return String
	 */
	public String getLicensePlate() {
		return this.licensePlate;
	}

	/**
	 * 功能：获取应急通信车信息表属性 车架号
	 * @return String
	 */
	public String getFrameNumber() {
		return this.frameNumber;
	}

	/**
	 * 功能：获取应急通信车信息表属性 长×宽×高(mm)
	 * @return String
	 */
	public String getLWH() {
		return this.lWH;
	}

	/**
	 * 功能：获取应急通信车信息表属性 资产原值(万元)
	 * @return String
	 */
	public String getOriginalCost() {
		return this.originalCost;
	}

	/**
	 * 功能：获取应急通信车信息表属性 厂家
	 * @return String
	 */
	public String getBtsManufacturer() {
		return this.btsManufacturer;
	}

	/**
	 * 功能：获取应急通信车信息表属性  型号
	 * @return String
	 */
	public String getBtsModel() {
		return this.btsModel;
	}

	/**
	 * 功能：获取应急通信车信息表属性 载频分配
	 * @return String
	 */
	public String getCarrierFrequencyvAllocate() {
		return this.carrierFrequencyvAllocate;
	}

	/**
	 * 功能：获取应急通信车信息表属性 总载频数
	 * @return String
	 */
	public String getCarrierFrequencyvQty() {
		return this.carrierFrequencyvQty;
	}

	/**
	 * 功能：获取应急通信车信息表属性 是否安装BSC
	 * @return String
	 */
	public String getInstalledBsc() {
		return this.installedBsc;
	}

	/**
	 * 功能：获取应急通信车信息表属性 安装的其他GSM系统网元
	 * @return String
	 */
	public String getOtherGsmUnit() {
		return this.otherGsmUnit;
	}

	/**
	 * 功能：获取应急通信车信息表属性 传输方式
	 * @return String
	 */
	public String getTransForm() {
		return this.transForm;
	}

	/**
	 * 功能：获取应急通信车信息表属性 设备型号
	 * @return String
	 */
	public String getTransItemModel() {
		return this.transItemModel;
	}

	/**
	 * 功能：获取应急通信车信息表属性 是否有卫星传输
	 * @return String
	 */
	public String getHasSatelliteTransmissions() {
		return this.hasSatelliteTransmissions;
	}

	/**
	 * 功能：获取应急通信车信息表属性 可提供业务种类（GSM/TD-SCDMA/WLAN/会议电视等）
	 * @return String
	 */
	public String getTypeOfTraffic() {
		return this.typeOfTraffic;
	}

	/**
	 * 功能：获取应急通信车信息表属性 使用次数
	 * @return String
	 */
	public String getUseTimes() {
		return this.useTimes;
	}

	/**
	 * 功能：获取应急通信车信息表属性 使用时提供的业务（GSM/TD-SCDMA/WLAN/会议电视等）
	 * @return String
	 */
	public String getUsedTraffic() {
		return this.usedTraffic;
	}

	/**
	 * 功能：获取应急通信车信息表属性 主要使用场景及地点
	 * @return String
	 */
	public String getUseScene() {
		return this.useScene;
	}

	/**
	 * 功能：获取应急通信车信息表属性 其他说明
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrgOpt() {
        return orgOpt;
    }

    public void setOrgOpt(String orgOpt) {
        this.orgOpt = orgOpt;
    }

    public SimpleCalendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String  creationDate) {
        this.creationDate = new SimpleCalendar(creationDate);
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public SimpleCalendar getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String  lastUpdateDate) {
        this.lastUpdateDate = new SimpleCalendar(lastUpdateDate);
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getComvanId() {
		return comvanId;
	}

	public void setComvanId(String comvanId) {
		this.comvanId = comvanId;
	}
}