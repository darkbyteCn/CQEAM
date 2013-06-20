package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 流属性 SfFlow</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfFlowDTO extends CheckBoxDTO{

	private int procedureId = 0;
	private int flowId = 0;
	private String flowDesc = "";
	private int fromTaskId = 0;
	private int toTaskId = 0;
	private String flowCode = "";
    private int flowProp = 0;
    private int flowType = 0;
	private String hint = "";

	public SfFlowDTO() {
		super();

	}

	/**
	 * 功能：设置流属性属性 过程 ID
	 * @param procedureId String
	 */
	public void setProcedureId(int procedureId){
		this.procedureId = procedureId;
	}

	/**
	 * 功能：设置流属性属性 流 ID
	 * @param flowId String
	 */
	public void setFlowId(int flowId){
		this.flowId = flowId;
	}

	/**
	 * 功能：设置流属性属性 流描述
	 * @param flowDesc String
	 */
	public void setFlowDesc(String flowDesc){
		this.flowDesc = flowDesc;
	}

	/**
	 * 功能：设置流属性属性 入流任务
	 * @param fromTaskId String
	 */
	public void setFromTaskId(int fromTaskId){
		this.fromTaskId = fromTaskId;
	}

	/**
	 * 功能：设置流属性属性 出流任务
	 * @param toTaskId String
	 */
	public void setToTaskId(int toTaskId){
		this.toTaskId = toTaskId;
	}

	/**
	 * 功能：设置流属性属性 分流控制码
	 * @param flowCode String
	 */
	public void setFlowCode(String flowCode){
		this.flowCode = flowCode;
	}

    /**
     * 功能：设置流属性属性 流类型
     * @param flowProp String
     */
    public void setFlowProp(int flowProp){
        this.flowProp = flowProp;
    }

    /**
	 * 功能：设置流属性属性 流类型
	 * @param flowType String
	 */
	public void setFlowType(int flowType){
		this.flowType = flowType;
	}

	/**
	 * 功能：设置流属性属性 任务提示
	 * @param hint String
	 */
	public void setHint(String hint){
		this.hint = hint;
	}


	/**
	 * 功能：获取流属性属性 过程 ID
	 * @return String
	 */
	public int getProcedureId() {
		return this.procedureId;
	}

	/**
	 * 功能：获取流属性属性 流 ID
	 * @return String
	 */
	public int getFlowId() {
		return this.flowId;
	}

	/**
	 * 功能：获取流属性属性 流描述
	 * @return String
	 */
	public String getFlowDesc() {
		return this.flowDesc;
	}

	/**
	 * 功能：获取流属性属性 入流任务
	 * @return String
	 */
	public int getFromTaskId() {
		return this.fromTaskId;
	}

	/**
	 * 功能：获取流属性属性 出流任务
	 * @return String
	 */
	public int getToTaskId() {
		return this.toTaskId;
	}

	/**
	 * 功能：获取流属性属性 分流控制码
	 * @return String
	 */
	public String getFlowCode() {
		return this.flowCode;
	}

    /**
     * 功能：获取流属性属性 流类型
     * @return String
     */
    public int getFlowProp() {
        return this.flowProp;
    }

	/**
	 * 功能：获取流属性属性 流类型
	 * @return String
	 */
	public int getFlowType() {
		return this.flowType;
	}

	/**
	 * 功能：获取流属性属性 任务提示
	 * @return String
	 */
	public String getHint() {
		return this.hint;
	}

}