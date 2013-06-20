package com.sino.ams.system.resource.dto;

import java.sql.Timestamp;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
* <p>Title: SfResDefine</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfResDefineDTO extends CheckBoxDTO{
	private int systemId = 0;
	private String resId = "";
	private String resParId = "";
	private String resName = "";
	private String resUrl = "";
	private int sortNo;
	private String isPopup = "";
	private String principal = "";
	private String enabled = "";
	private String isInner = "";
	private String visible = "";
	private String popscript = "";
	private int levelNum ;
	private Timestamp creationDate = null;
	private int createdBy = -1;
	private Timestamp lastUpdateDate = null;
	private int lastUpdateBy = -1;
	private String firstChildId = "";
	private String hasChild = "0";
	private String businessDesc = "";
	private String isFinished = "";

	private String newResId = "";//资源结构调整过程中，生成的新ID
	private String newResParId = "";//资源结构调整过程中，生成的新上级栏目ID
	/**
	 * 功能：设置DTO属性 systemId
	 * @param systemId String
	 */
	public void setSystemId(int systemId){
		this.systemId = systemId;
	}

	/**
	 * 功能：设置DTO属性 resId
	 * @param resId String
	 */
	public void setResId(String resId){
		this.resId = resId;
	}

	/**
	 * 功能：设置DTO属性 resParId
	 * @param resParId String
	 */
	public void setResParId(String resParId){
		this.resParId = resParId;
	}

	/**
	 * 功能：设置DTO属性 resName
	 * @param resName String
	 */
	public void setResName(String resName){
		this.resName = resName;
	}

	/**
	 * 功能：设置DTO属性 resUrl
	 * @param resUrl String
	 */
	public void setResUrl(String resUrl){
		this.resUrl = resUrl;
	}

	/**
	 * 功能：设置DTO属性 sortNo
	 * @param sortNo String
	 */
	public void setSortNo(int sortNo){
		this.sortNo = sortNo;
	}

	/**
	 * 功能：设置DTO属性 isPopup
	 * @param isPopup String
	 */
	public void setIsPopup(String isPopup){
		this.isPopup = isPopup;
	}

	/**
	 * 功能：设置DTO属性 principal
	 * @param principal String
	 */
	public void setPrincipal(String principal){
		this.principal = principal;
	}

	/**
	 * 功能：设置DTO属性 enabled
	 * @param enabled String
	 */
	public void setEnabled(String enabled){
		this.enabled = enabled;
	}

	/**
	 * 功能：设置DTO属性 isInner
	 * @param isInner String
	 */
	public void setIsInner(String isInner){
		this.isInner = isInner;
	}

	/**
	 * 功能：设置DTO属性 visible
	 * @param visible String
	 */
	public void setVisible(String visible){
		this.visible = visible;
	}

	/**
	 * 功能：设置DTO属性 popscript
	 * @param popscript String
	 */
	public void setPopscript(String popscript){
		this.popscript = popscript;
	}

	/**
	 * 功能：设置DTO属性 levelNum
	 * @param levelNum String
	 */
	public void setLevelNum(int levelNum){
		this.levelNum = levelNum;
	}

	/**
	 * 功能：设置DTO属性 creationDate
	 * @param creationDate Timestamp
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		if(!StrUtil.isEmpty(creationDate)){
			SimpleCalendar cal = new SimpleCalendar(creationDate);
			this.creationDate = cal.getSQLTimestamp();
		}
	}

	/**
	 * 功能：设置DTO属性 createdBy
	 * @param createdBy String
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置DTO属性 lastUpdateDate
	 * @param lastUpdateDate Timestamp
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		if(!StrUtil.isEmpty(lastUpdateDate)){
			SimpleCalendar cal = new SimpleCalendar(lastUpdateDate);
			this.lastUpdateDate = cal.getSQLTimestamp();
		}
	}

	/**
	 * 功能：设置DTO属性 lastUpdateBy
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}

	public void setFirstChildId(String firstChildId) {
		this.firstChildId = firstChildId;
	}

	public void setHasChild(String hasChild) {
		this.hasChild = hasChild;
	}

	public void setNewResId(String newResId) {
		this.newResId = newResId;
	}

	public void setNewResParId(String newResParId) {
		this.newResParId = newResParId;
	}

	public void setBusinessDesc(String businessDesc) {
		this.businessDesc = businessDesc;
	}

	public void setIsFinished(String isFinished) {
		this.isFinished = isFinished;
	}

	/**
	 * 功能：获取DTO属性 systemId
	 * @return String
	 */
	public int getSystemId(){
		return this.systemId;
	}

	/**
	 * 功能：获取DTO属性 resId
	 * @return String
	 */
	public String getResId(){
		return this.resId;
	}

	/**
	 * 功能：获取DTO属性 resParId
	 * @return String
	 */
	public String getResParId(){
		return this.resParId;
	}

	/**
	 * 功能：获取DTO属性 resName
	 * @return String
	 */
	public String getResName(){
		return this.resName;
	}

	/**
	 * 功能：获取DTO属性 resUrl
	 * @return String
	 */
	public String getResUrl(){
		return this.resUrl;
	}

	/**
	 * 功能：获取DTO属性 sortNo
	 * @return String
	 */
	public int getSortNo(){
		return this.sortNo;
	}

	/**
	 * 功能：获取DTO属性 isPopup
	 * @return String
	 */
	public String getIsPopup(){
		return this.isPopup;
	}

	/**
	 * 功能：获取DTO属性 principal
	 * @return String
	 */
	public String getPrincipal(){
		return this.principal;
	}

	/**
	 * 功能：获取DTO属性 enabled
	 * @return String
	 */
	public String getEnabled(){
		return this.enabled;
	}

	/**
	 * 功能：获取DTO属性 isInner
	 * @return String
	 */
	public String getIsInner(){
		return this.isInner;
	}

	/**
	 * 功能：获取DTO属性 visible
	 * @return String
	 */
	public String getVisible(){
		return this.visible;
	}

	/**
	 * 功能：获取DTO属性 popscript
	 * @return String
	 */
	public String getPopscript(){
		return this.popscript;
	}

	/**
	 * 功能：获取DTO属性 levelNum
	 * @return String
	 */
	public int getLevelNum(){
		return this.levelNum;
	}

	/**
	 * 功能：获取DTO属性 creationDate
	 * @return Timestamp
	 */
	public Timestamp getCreationDate(){
		return this.creationDate;
	}

	/**
	 * 功能：获取DTO属性 createdBy
	 * @return String
	 */
	public int getCreatedBy(){
		return this.createdBy;
	}

	/**
	 * 功能：获取DTO属性 lastUpdateDate
	 * @return Timestamp
	 */
	public Timestamp getLastUpdateDate(){
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取DTO属性 lastUpdateBy
	 * @return String
	 */
	public int getLastUpdateBy(){
		return this.lastUpdateBy;
	}

	public String getFirstChildId() {
		return firstChildId;
	}

	public String getHasChild() {
		return hasChild;
	}

	public String getNewResId() {
		return newResId;
	}

	public String getNewResParId() {
		return newResParId;
	}

	public String getBusinessDesc() {
		return businessDesc;
	}

	public String getIsFinished() {
		return isFinished;
	}

	/**
	 * 功能：获取指定URL树形资源结构中本URL对象的父亲。
	 * 用于更新URL资源时的递归更新。
	 * @param resources DTOSet
	 * @return SfResDefineDTO
	 */
	public SfResDefineDTO getParent(DTOSet resources) {
		SfResDefineDTO parent = null;
		for(int i = 0; i < resources.getSize(); i++){
			parent = (SfResDefineDTO)resources.getDTO(i);
			if(parent.getResId().equals(getResParId())){
				break;
			}
		}
		return parent;
	}
}
