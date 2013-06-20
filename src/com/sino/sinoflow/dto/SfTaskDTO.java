package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 任务属性 SfTask</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfTaskDTO extends CheckBoxDTO{

	private int procedureId = 0;
	private int taskId = 0;
	private String taskName = "";
	private String taskDesc = "";
	private String groupName = "";
	private String roleName = "";
	private int flowType = 0;
	private String api = "";
	private int duration = 0;
	private int durationUnit = 0;
    private String url = "";
    private String divRight = "";
	private String divHidden = "";
	private int controlType = 0;
	private int cycleType = 0;
    private String cycleUrl = "";
    private String reviewGroup = "";
	private String reviewRole = "";
	private String reviewDesc = "";
    private String reviewUrl = "";
    private String reviewDivRight = "";
	private String reviewDivHidden = "";
    private int copyFlag = 0;
    private String copyReason = "";
    private String copyHiddenDiv = "";
    private String copyUrl = "";
    private String attribute1 = "";
    private String attribute2 = "";
    private String attribute3 = "";
    private String attribute4 = "";
    private String attribute5 = "";
    private int tid = 0;

    public SfTaskDTO() {
		super();

	}

	/**
	 * 功能：设置任务属性属性 PROCEDURE_ID
	 * @param procedureId String
	 */
	public void setProcedureId(int procedureId){
		this.procedureId = procedureId;
	}

	/**
	 * 功能：设置任务属性属性 任务 ID
	 * @param taskId String
	 */
	public void setTaskId(int taskId){
		this.taskId = taskId;
	}

	/**
	 * 功能：设置任务属性属性 任务名称
	 * @param taskName String
	 */
	public void setTaskName(String taskName){
		this.taskName = taskName;
	}

	/**
	 * 功能：设置任务属性属性 任务描述
	 * @param taskDesc String
	 */
	public void setTaskDesc(String taskDesc){
		this.taskDesc = taskDesc;
	}

	/**
	 * 功能：设置任务属性属性 组别
	 * @param groupName String
	 */
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}

	/**
	 * 功能：设置任务属性属性 角色
	 * @param roleName String
	 */
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}

	/**
	 * 功能：设置任务属性属性 流向类型, 0:直流 1:分流 2:并流 3:辅流 4:子流  如果是 JOIN 0:等待 1:不等待
	 * @param flowType String
	 */
	public void setFlowType(int flowType){
		this.flowType = flowType;
	}

	/**
	 * 功能：设置任务属性属性 接口程序
	 * @param api String
	 */
	public void setApi(String api){
		this.api = api;
	}

	/**
	 * 功能：设置任务属性属性 预期时间
	 * @param duration String
	 */
	public void setDuration(int duration){
		this.duration = duration;
	}

	/**
	 * 功能：设置任务属性属性 计算单元, 0:工作时 1:实际天数
	 * @param durationUnit String
	 */
	public void setDurationUnit(int durationUnit){
		this.durationUnit = durationUnit;
	}

    /**
     * 功能：设置任务属性属性 区段
     * @param url String
     */
    public void setUrl(String url){
        this.url = url;
    }


    /**
	 * 功能：设置任务属性属性 区段
	 * @param divRight String
	 */
	public void setDivRight(String divRight){
		this.divRight = divRight;
	}

	/**
	 * 功能：设置任务属性属性 隠藏
	 * @param divHidden String
	 */
	public void setDivHidden(String divHidden){
		this.divHidden = divHidden;
	}

	/**
	 * 功能：设置任务属性属性 任务控制, 0:无 1:经办人 2:会签 3:阅示
	 * @param controlType String
	 */
	public void setControlType(int controlType){
		this.controlType = controlType;
	}

	/**
	 * 功能：设置任务属性属性 会签种类, 0:人员会签 1:组别会签
	 * @param cycleType String
	 */
	public void setCycleType(int cycleType){
		this.cycleType = cycleType;
	}

    /**
     * 功能：设置任务属性属性 会签种类, 0:人员会签 1:组别会签
     * @param cycleUrl String
     */
    public void setCycleUrl(String cycleUrl){
        this.cycleUrl = cycleUrl;
    }

	/**
	 * 功能：设置任务属性属性 阅示组别
	 * @param reviewGroup String
	 */
	public void setReviewGroup(String reviewGroup){
		this.reviewGroup = reviewGroup;
	}

	/**
	 * 功能：设置任务属性属性 阅示角色
	 * @param reviewRole String
	 */
	public void setReviewRole(String reviewRole){
		this.reviewRole = reviewRole;
	}

	/**
	 * 功能：设置任务属性属性 阅示提示
	 * @param reviewDesc String
	 */
	public void setReviewDesc(String reviewDesc){
		this.reviewDesc = reviewDesc;
	}

    /**
     * 功能：设置任务属性属性 阅示区段
     * @param reviewUrl String
     */
    public void setReviewUrl(String reviewUrl){
        this.reviewUrl = reviewUrl;
    }

    /**
	 * 功能：设置任务属性属性 阅示区段
	 * @param reviewDivRight String
	 */
	public void setReviewDivRight(String reviewDivRight){
		this.reviewDivRight = reviewDivRight;
	}

	/**
	 * 功能：设置任务属性属性 阅示隠藏
	 * @param reviewDivHidden String
	 */
	public void setReviewDivHidden(String reviewDivHidden){
		this.reviewDivHidden = reviewDivHidden;
	}

    public void setCopyFlag(int copyFlag) {
        this.copyFlag = copyFlag;
    }

    public void setCopyReason(String copyReason) {
        this.copyReason = copyReason;
    }

    public void setCopyHiddenDiv(String copyHiddenDiv) {
        this.copyHiddenDiv = copyHiddenDiv;
    }

    public void setCopyUrl(String copyUrl) {
        this.copyUrl = copyUrl;
    }

    /**
     * 功能：设置任务属性属性 阅示隠藏
     * @param attribute1 String
     */
    public void setAttribute1(String attribute1){
        this.attribute1 = attribute1;
    }

    /**
     * 功能：设置任务属性属性 阅示隠藏
     * @param attribute2 String
     */
    public void setAttribute2(String attribute2){
        this.attribute2 = attribute2;
    }

    /**
     * 功能：设置任务属性属性 阅示隠藏
     * @param attribute3 String
     */
    public void setAttribute3(String attribute3){
        this.attribute3 = attribute3;
    }

    /**
     * 功能：设置任务属性属性 阅示隠藏
     * @param attribute4 String
     */
    public void setAttribute4(String attribute4){
        this.attribute4 = attribute4;
    }

    /**
     * 功能：设置任务属性属性 阅示隠藏
     * @param attribute5 String
     */
    public void setAttribute5(String attribute5){
        this.attribute5 = attribute5;
    }

    /**
     * 功能：设置任务属性属性 阅示隠藏
     * @param tid String
     */
    public void setTid(int tid){
        this.tid = tid;
    }

    /**
	 * 功能：获取任务属性属性 PROCEDURE_ID
	 * @return String
	 */
	public int getProcedureId() {
		return this.procedureId;
	}

	/**
	 * 功能：获取任务属性属性 任务 ID
	 * @return String
	 */
	public int getTaskId() {
		return this.taskId;
	}

	/**
	 * 功能：获取任务属性属性 任务名称
	 * @return String
	 */
	public String getTaskName() {
		return this.taskName;
	}

	/**
	 * 功能：获取任务属性属性 任务描述
	 * @return String
	 */
	public String getTaskDesc() {
		return this.taskDesc;
	}

	/**
	 * 功能：获取任务属性属性 组别
	 * @return String
	 */
	public String getGroupName() {
		return this.groupName;
	}

	/**
	 * 功能：获取任务属性属性 角色
	 * @return String
	 */
	public String getRoleName() {
		return this.roleName;
	}

	/**
	 * 功能：获取任务属性属性 流向类型, 0:直流 1:分流 2:并流 3:辅流 4:子流  如果是 JOIN 0:等待 1:不等待
	 * @return String
	 */
	public int getFlowType() {
		return this.flowType;
	}

	/**
	 * 功能：获取任务属性属性 接口程序
	 * @return String
	 */
	public String getApi() {
		return this.api;
	}

	/**
	 * 功能：获取任务属性属性 预期时间
	 * @return String
	 */
	public int getDuration() {
		return this.duration;
	}

	/**
	 * 功能：获取任务属性属性 计算单元, 0:工作时 1:实际天数
	 * @return String
	 */
	public int getDurationUnit() {
		return this.durationUnit;
	}

    /**
     * 功能：获取任务属性属性 区段
     * @return String
     */
    public String getUrl() {
        return this.url;
    }

    /**
	 * 功能：获取任务属性属性 区段
	 * @return String
	 */
	public String getDivRight() {
		return this.divRight;
	}

	/**
	 * 功能：获取任务属性属性 隠藏
	 * @return String
	 */
	public String getDivHidden() {
		return this.divHidden;
	}

	/**
	 * 功能：获取任务属性属性 任务控制, 0:无 1:经办人 2:会签 3:阅示
	 * @return String
	 */
	public int getControlType() {
		return this.controlType;
	}

	/**
	 * 功能：获取任务属性属性 会签种类, 0:人员会签 1:组别会签
	 * @return String
	 */
	public int getCycleType() {
		return this.cycleType;
	}

    /**
     * 功能：获取任务属性属性 会签种类, 0:人员会签 1:组别会签
     * @return String
     */
    public String getCycleUrl() {
        return this.cycleUrl;
    }

    /**
	 * 功能：获取任务属性属性 阅示组别
	 * @return String
	 */
	public String getReviewGroup() {
		return this.reviewGroup;
	}

	/**
	 * 功能：获取任务属性属性 阅示角色
	 * @return String
	 */
	public String getReviewRole() {
		return this.reviewRole;
	}

	/**
	 * 功能：获取任务属性属性 阅示提示
	 * @return String
	 */
	public String getReviewDesc() {
		return this.reviewDesc;
	}

    /**
     * 功能：获取任务属性属性 阅示提示
     * @return String
     */
    public String getReviewUrl() {
        return this.reviewUrl;
    }

    /**
	 * 功能：获取任务属性属性 阅示区段
	 * @return String
	 */
	public String getReviewDivRight() {
		return this.reviewDivRight;
	}

	/**
	 * 功能：获取任务属性属性 阅示隠藏
	 * @return String
	 */
	public String getReviewDivHidden() {
		return this.reviewDivHidden;
	}

    public int getCopyFlag() {
        return this.copyFlag;
    }

    public String getCopyReason() {
        return this.copyReason;
    }

    public String getCopyHiddenDiv() {
        return this.copyHiddenDiv;
    }

    public String getCopyUrl() {
        return this.copyUrl;
    }

    /**
     * 功能：获取任务属性属性 阅示隠藏
     * @return String
     */
    public String getAttribute1() {
        return this.attribute1;
    }

    /**
     * 功能：获取任务属性属性 阅示隠藏
     * @return String
     */
    public String getAttribute2() {
        return this.attribute2;
    }

    /**
     * 功能：获取任务属性属性 阅示隠藏
     * @return String
     */
    public String getAttribute3() {
        return this.attribute3;
    }

    /**
     * 功能：获取任务属性属性 阅示隠藏
     * @return String
     */
    public String getAttribute4() {
        return this.attribute4;
    }

    /**
     * 功能：获取任务属性属性 阅示隠藏
     * @return String
     */
    public String getAttribute5() {
        return this.attribute5;
    }

    /**
     * 功能：获取任务属性属性 阅示隠藏
     * @return String
     */
    public int getTid() {
        return this.tid;
    }
}