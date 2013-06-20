package com.sino.ams.yj.ensure.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.math.AdvancedNumber;
import com.sino.base.exception.CalendarException;

/**
 * <p>Title: 应急通信保障事件信息表 AmsYjCommunicateEnsure</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-应急通信保障情况
 */

public class AmsYjCommunicateEnsureDTO extends CheckBoxDTO {

    private String communicateId = "";
    private String deptName = "";
    private String ensureName = "";
    private String eventType = "";
    private SimpleCalendar ensureDateFrom = null;
    private SimpleCalendar ensureDateTo = null;
    private String manpowerQty = "";        //INT
    private String manpowerTimes = "";      //INT
    private String comvanQty = "";          //INT
    private String comvanTimes = "";        //INT
    private String equipmentQty = "";       //INT
    private String equipmentUnit = "";      //INT
    private String blockDegree = "";
    private String lossCondition = "";
    private String ensureMeasure = "";
    private String recoverSituation = "";
    private String governmentEvaluate = "";
    private String reasonAffect = "";
    private String question = "";
    private String guardMeasure = "";

    private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;
    private SimpleCalendar creationDate = null;
    private int createUser = SyBaseSQLUtil.NULL_INT_VALUE;
    private SimpleCalendar lastUpdateDate = null;
    private int lastUpdateUser = SyBaseSQLUtil.NULL_INT_VALUE;
    private String orgOpt = "";
    private String ensureLocation = "";

    public AmsYjCommunicateEnsureDTO() {
        super();
        this.ensureDateFrom = new SimpleCalendar();
        this.ensureDateTo = new SimpleCalendar();
        this.creationDate = new SimpleCalendar();
        this.lastUpdateDate = new SimpleCalendar();

    }

    /**
     * 功能：设置应急通信保障事件信息表属性 序号
     *
     * @param communicateId String
     */
    public void setCommunicateId(String communicateId) {
        this.communicateId = communicateId;
    }

    /**
     * 功能：设置应急通信保障事件信息表属性 	单位
     *
     * @param deptName String
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * 功能：设置应急通信保障事件信息表属性 通信保障名称
     *
     * @param ensureName String
     */
    public void setEnsureName(String ensureName) {
        this.ensureName = ensureName;
    }

    /**
     * 功能：设置应急通信保障事件信息表属性 事件类型
     *
     * @param eventType String
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * 功能：设置应急通信保障事件信息表属性 保障时间起
     * @param ensureDateFrom SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setEnsureDateFrom(String ensureDateFrom) throws CalendarException {
        this.ensureDateFrom.setCalendarValue(ensureDateFrom);
    }
    
    public String getManpowerQty() {
		return manpowerQty;
	}

	public void setManpowerQty(String manpowerQty) {
		this.manpowerQty = manpowerQty;
	}

	public String getManpowerTimes() {
		return manpowerTimes;
	}

	public void setManpowerTimes(String manpowerTimes) {
		this.manpowerTimes = manpowerTimes;
	}

	public String getComvanQty() {
		return comvanQty;
	}

	public void setComvanQty(String comvanQty) {
		this.comvanQty = comvanQty;
	}

	public String getComvanTimes() {
		return comvanTimes;
	}

	public void setComvanTimes(String comvanTimes) {
		this.comvanTimes = comvanTimes;
	}

	public String getEquipmentQty() {
		return equipmentQty;
	}

	public void setEquipmentQty(String equipmentQty) {
		this.equipmentQty = equipmentQty;
	}

	public String getEquipmentUnit() {
		return equipmentUnit;
	}

	public void setEquipmentUnit(String equipmentUnit) {
		this.equipmentUnit = equipmentUnit;
	}

	public String getCommunicateId() {
		return communicateId;
	}

	/**
     * 功能：获取应急通信保障事件信息表属性 保障时间起
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getEnsureDateFrom() throws CalendarException {
        ensureDateFrom.setCalPattern(getCalPattern());
        return this.ensureDateFrom;
    }  
    
    /**
     * 功能：设置应急通信保障事件信息表属性 保障时间止
     *
     * @param ensureDateTo SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setEnsureDateTo(String ensureDateTo) throws CalendarException {
        this.ensureDateTo.setCalendarValue(ensureDateTo);
    }

    /**
     * 功能：设置应急通信保障事件信息表属性 通信阻断程度
     *
     * @param blockDegree String
     */
    public void setBlockDegree(String blockDegree) {
        this.blockDegree = blockDegree;
    }

    /**
     * 功能：设置应急通信保障事件信息表属性 损失情况
     *
     * @param lossCondition String
     */
    public void setLossCondition(String lossCondition) {
        this.lossCondition = lossCondition;
    }

    /**
     * 功能：设置应急通信保障事件信息表属性 应急保障措施
     *
     * @param ensureMeasure String
     */
    public void setEnsureMeasure(String ensureMeasure) {
        this.ensureMeasure = ensureMeasure;
    }

    /**
     * 功能：设置应急通信保障事件信息表属性 通信恢复情况及时间
     *
     * @param recoverSituation String
     */
    public void setRecoverSituation(String recoverSituation) {
        this.recoverSituation = recoverSituation;
    }

    /**
     * 功能：设置应急通信保障事件信息表属性 地方政府整体评价
     *
     * @param governmentEvaluate String
     */
    public void setGovernmentEvaluate(String governmentEvaluate) {
        this.governmentEvaluate = governmentEvaluate;
    }

    /**
     * 功能：设置应急通信保障事件信息表属性 事件原因及影响范围
     *
     * @param reasonAffect String
     */
    public void setReasonAffect(String reasonAffect) {
        this.reasonAffect = reasonAffect;
    }

    /**
     * 功能：设置应急通信保障事件信息表属性 存在的问题
     *
     * @param question String
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * 功能：设置应急通信保障事件信息表属性 未来防范措施
     *
     * @param guardMeasure String
     */
    public void setGuardMeasure(String guardMeasure) {
        this.guardMeasure = guardMeasure;
    }

    /**
     * 功能：获取应急通信保障事件信息表属性 	单位
     *
     * @return String
     */
    public String getDeptName() {
        return this.deptName;
    }

    /**
     * 功能：获取应急通信保障事件信息表属性 通信保障名称
     *
     * @return String
     */
    public String getEnsureName() {
        return this.ensureName;
    }

    /**
     * 功能：获取应急通信保障事件信息表属性 事件类型
     *
     * @return String
     */
    public String getEventType() {
        return this.eventType;
    }

    /**
     * 功能：获取应急通信保障事件信息表属性 保障时间止
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getEnsureDateTo() throws CalendarException {
        ensureDateTo.setCalPattern(getCalPattern());
        return this.ensureDateTo;
    }

    /**
     * 功能：获取应急通信保障事件信息表属性 通信阻断程度
     *
     * @return String
     */
    public String getBlockDegree() {
        return this.blockDegree;
    }

    /**
     * 功能：获取应急通信保障事件信息表属性 损失情况
     *
     * @return String
     */
    public String getLossCondition() {
        return this.lossCondition;
    }

    /**
     * 功能：获取应急通信保障事件信息表属性 应急保障措施
     *
     * @return String
     */
    public String getEnsureMeasure() {
        return this.ensureMeasure;
    }

    /**
     * 功能：获取应急通信保障事件信息表属性 通信恢复情况及时间
     *
     * @return String
     */
    public String getRecoverSituation() {
        return this.recoverSituation;
    }

    /**
     * 功能：获取应急通信保障事件信息表属性 地方政府整体评价
     *
     * @return String
     */
    public String getGovernmentEvaluate() {
        return this.governmentEvaluate;
    }

    /**
     * 功能：获取应急通信保障事件信息表属性 事件原因及影响范围
     *
     * @return String
     */
    public String getReasonAffect() {
        return this.reasonAffect;
    }

    /**
     * 功能：获取应急通信保障事件信息表属性 存在的问题
     *
     * @return String
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * 功能：获取应急通信保障事件信息表属性 未来防范措施
     *
     * @return String
     */
    public String getGuardMeasure() {
        return this.guardMeasure;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public SimpleCalendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = new SimpleCalendar(creationDate);
    }

    public int getCreateUser() {
        return createUser;
    }

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }

    public SimpleCalendar getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = new SimpleCalendar(lastUpdateDate);
    }

    public int getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(int lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getOrgOpt() {
        return orgOpt;
    }

    public void setOrgOpt(String orgOpt) {
        this.orgOpt = orgOpt;
    }

    public String getEnsureLocation() {
        return ensureLocation;
    }

    public void setEnsureLocation(String ensureLocation) {
        this.ensureLocation = ensureLocation;
    }
}