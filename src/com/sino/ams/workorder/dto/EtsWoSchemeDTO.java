package com.sino.ams.workorder.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 新建工单基站配置信息(EAM) EtsWoSchemeTmp</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsWoSchemeDTO extends CheckBoxDTO{

	private int systemid;
	private String workorderNo = "";
	private String itemCode = "";
	private int itemQty;
	private String attribute1 = "";
	private String attribute2 = "";
	private String attribute3 = "";
	private String attribute4 = "";
	private String attribute5 = "";
	private int attribute6;


	/**
	 * 功能：设置新建工单基站配置信息(EAM)属性 null
	 * @param systemid String
	 */
	public void setSystemid(int systemid){
		this.systemid = systemid;
	}

	/**
	 * 功能：设置新建工单基站配置信息(EAM)属性 null
	 * @param workorderNo String
	 */
	public void setWorkorderNo(String workorderNo){
		this.workorderNo = workorderNo;
	}

	/**
	 * 功能：设置新建工单基站配置信息(EAM)属性 null
	 * @param itemCode String
	 */
	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

	/**
	 * 功能：设置新建工单基站配置信息(EAM)属性 null
	 * @param itemQty String
	 */
	public void setItemQty(int itemQty){
		this.itemQty = itemQty;
	}

	/**
	 * 功能：设置新建工单基站配置信息(EAM)属性 null
	 * @param attribute1 String
	 */
	public void setAttribute1(String attribute1){
		this.attribute1 = attribute1;
	}

	/**
	 * 功能：设置新建工单基站配置信息(EAM)属性 null
	 * @param attribute2 String
	 */
	public void setAttribute2(String attribute2){
		this.attribute2 = attribute2;
	}

	/**
	 * 功能：设置新建工单基站配置信息(EAM)属性 null
	 * @param attribute3 String
	 */
	public void setAttribute3(String attribute3){
		this.attribute3 = attribute3;
	}

	/**
	 * 功能：设置新建工单基站配置信息(EAM)属性 null
	 * @param attribute4 String
	 */
	public void setAttribute4(String attribute4){
		this.attribute4 = attribute4;
	}

	/**
	 * 功能：设置新建工单基站配置信息(EAM)属性 null
	 * @param attribute5 String
	 */
	public void setAttribute5(String attribute5){
		this.attribute5 = attribute5;
	}

	/**
	 * 功能：设置新建工单基站配置信息(EAM)属性 null
	 * @param attribute6 String
	 */
	public void setAttribute6(int attribute6){
		this.attribute6 = attribute6;
	}


	/**
	 * 功能：获取新建工单基站配置信息(EAM)属性 null
	 * @return String
	 */
	public int getSystemid(){
		return this.systemid;
	}

	/**
	 * 功能：获取新建工单基站配置信息(EAM)属性 null
	 * @return String
	 */
	public String getWorkorderNo(){
		return this.workorderNo;
	}

	/**
	 * 功能：获取新建工单基站配置信息(EAM)属性 null
	 * @return String
	 */
	public String getItemCode(){
		return this.itemCode;
	}

	/**
	 * 功能：获取新建工单基站配置信息(EAM)属性 null
	 * @return String
	 */
	public int getItemQty(){
		return this.itemQty;
	}

	/**
	 * 功能：获取新建工单基站配置信息(EAM)属性 null
	 * @return String
	 */
	public String getAttribute1(){
		return this.attribute1;
	}

	/**
	 * 功能：获取新建工单基站配置信息(EAM)属性 null
	 * @return String
	 */
	public String getAttribute2(){
		return this.attribute2;
	}

	/**
	 * 功能：获取新建工单基站配置信息(EAM)属性 null
	 * @return String
	 */
	public String getAttribute3(){
		return this.attribute3;
	}

	/**
	 * 功能：获取新建工单基站配置信息(EAM)属性 null
	 * @return String
	 */
	public String getAttribute4(){
		return this.attribute4;
	}

	/**
	 * 功能：获取新建工单基站配置信息(EAM)属性 null
	 * @return String
	 */
	public String getAttribute5(){
		return this.attribute5;
	}

	/**
	 * 功能：获取新建工单基站配置信息(EAM)属性 null
	 * @return String
	 */
	public int getAttribute6(){
		return this.attribute6;
	}

}