package com.sino.ams.workorderDefine.dto;

import com.sino.ams.appbase.dto.AMSBaseDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.util.StrUtil;

/**
 * 巡检自定义传输对象
 * 对应表: ETS_WORKORDER_DEFINE
 * @author 刘勇
 *  
 */
public class WorkorderDefineDTO extends AMSBaseDTO{
	private String workorderDefineId = "";  //巡检自定义标识
	private int organizationId ;  //公司OU
	private String objectCategory = "";  // 地点专业
	private String city = ""; //市
	private String county = "";  //所属区县
	private String costCenterCode = "";//成本中心代码	
    private int implementBy; //执行人
    private int checkoverBy;    //归档人
    private String implementByName = ""; //执行人
    private String checkoverByName = ""; //归档人
    private int workorderCycle = -1; //巡检周期
    private String enabled = ""; //是否有效
    private SimpleCalendar workorderExecDate = null; //上次执行日期
    private SimpleCalendar creationDate = null; //创建日期
    private SimpleCalendar lastUpdateDate = null; //修改日期
    private int groupId = -1;//组别
    private int workorderTiggerTime ;  //巡检自动创建触发时间
    
    private String organizationName = ""; //公司名称
    private String objectCategoryOption = "";
    private String cityOption = "";
    private String countyOption = "";
    private String costCenterName = "";//成本中心名称
    private String workorderCycleOption = ""; //巡检周期下拉选择框
    private String implementByOption = ""; //执行人选择下拉选择框
    private String checkoverByOption = ""; //归档人选择下拉选择框
    
    private String auxiliaryInfo = ""; //辅助信息
    
    public String getAuxiliaryInfo() {
		return auxiliaryInfo;
	}

	public void setAuxiliaryInfo(String auxiliaryInfo) {
		this.auxiliaryInfo = auxiliaryInfo;
	} 
    
    public String getImplementByOption() {
		return implementByOption;
	}

	public void setImplementByOption(String implementByOption) {
		this.implementByOption = implementByOption;
	}

	public String getCheckoverByOption() {
		return checkoverByOption;
	}

	public void setCheckoverByOption(String checkoverByOption) {
		this.checkoverByOption = checkoverByOption;
	}

	public WorkorderDefineDTO() {
        this.workorderExecDate = new SimpleCalendar();
        this.creationDate = new SimpleCalendar();
        this.lastUpdateDate = new SimpleCalendar();
    }

	public String getWorkorderDefineId() {
		return workorderDefineId;
	}

	public void setWorkorderDefineId(String workorderDefineId) {
		this.workorderDefineId = workorderDefineId;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getObjectCategory() {
		return objectCategory;
	}

	public void setObjectCategory(String objectCategory) {
		this.objectCategory = objectCategory;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCostCenterCode() {
		return costCenterCode;
	}

	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}

	public int getImplementBy() {
		return implementBy;
	}

	public void setImplementBy(int implementBy) {
		this.implementBy = implementBy;
	}

	public int getCheckoverBy() {
		return checkoverBy;
	}

	public void setCheckoverBy(int checkoverBy) {
		this.checkoverBy = checkoverBy;
	}

	public int getWorkorderCycle() {
		return workorderCycle;
	}

	public void setWorkorderCycle(int workorderCycle) {
		this.workorderCycle = workorderCycle;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getObjectCategoryOption() {
		return objectCategoryOption;
	}

	public void setObjectCategoryOption(String objectCategoryOption) {
		this.objectCategoryOption = objectCategoryOption;
	}

	public String getCountyOption() {
		return countyOption;
	}

	public void setCountyOption(String countyOption) {
		this.countyOption = countyOption;
	}

	public String getWorkorderCycleOption() {
		return workorderCycleOption;
	}

	public void setWorkorderCycleOption(String workorderCycleOption) {
		this.workorderCycleOption = workorderCycleOption;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityOption() {
		return cityOption;
	}

	public void setCityOption(String cityOption) {
		this.cityOption = cityOption;
	}

	public SimpleCalendar getWorkorderExecDate() {
		return workorderExecDate;
	}

	public void setWorkorderExecDate(String workorderExecDate) {
		if (StrUtil.isNotEmpty(workorderExecDate)) {
			this.workorderExecDate = new SimpleCalendar(workorderExecDate);
		}
	}

	public SimpleCalendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		if (StrUtil.isNotEmpty(creationDate)) {
			this.creationDate = new SimpleCalendar(creationDate);
		}
	}

	public SimpleCalendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		if (StrUtil.isNotEmpty(lastUpdateDate)) {
			this.lastUpdateDate = new SimpleCalendar(lastUpdateDate);
		}
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getWorkorderTiggerTime() {
		return workorderTiggerTime;
	}

	public void setWorkorderTiggerTime(int workorderTiggerTime) {
		this.workorderTiggerTime = workorderTiggerTime;
	}

	public String getImplementByName() {
		return implementByName;
	}

	public void setImplementByName(String implementByName) {
		this.implementByName = implementByName;
	}

	public String getCheckoverByName() {
		return checkoverByName;
	}

	public void setCheckoverByName(String checkoverByName) {
		this.checkoverByName = checkoverByName;
	}
    
}
