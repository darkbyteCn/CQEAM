package com.sino.ams.yj.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.math.AdvancedNumber;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 应急通信保障队伍表 AmsYjUser</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author wzp
* @version 1.0
*/

public class AmsYjUserDTO extends AmsYjTeamDTO{

	private String teamId = null;
    private String userId = "";
	private String userName = "";
	private String post = "";
	private String tel = "";
	private String category = "";
	private String attribute = "";
	private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;
	private SimpleCalendar creationDate = null;
	private SimpleCalendar lastUpdateDate = null;
	private String createUser = null;
	private String lastUpdateUser = null;
	private String  remark = "";

	public AmsYjUserDTO() {
		super();
		this.creationDate = new SimpleCalendar();
		this.lastUpdateDate = new SimpleCalendar();

	}

	/**
	 * 功能：设置应急通信保障队伍表属性 队伍ID
	 * @param teamId AdvancedNumber
	 */
	public void setTeamId(String teamId){
		this.teamId = teamId;
	}

	/**
	 * 功能：设置应急通信保障队伍表属性 姓名
	 * @param userName String
	 */
	public void setUserName(String userName){
		this.userName = userName;
	}

	/**
	 * 功能：设置应急通信保障队伍表属性 职务
	 * @param post String
	 */
	public void setPost(String post){
		this.post = post;
	}

	/**
	 * 功能：设置应急通信保障队伍表属性 手机号
	 * @param tel String
	 */
	public void setTel(String tel){
		this.tel = tel;
	}

	/**
	 * 功能：设置应急通信保障队伍表属性 专业
	 * @param category String
	 */
	public void setCategory(String category){
		this.category = category;
	}

	/**
	 * 功能：设置应急通信保障队伍表属性 属性
	 * @param attribute String
	 */
	public void setAttribute(String attribute){
		this.attribute = attribute;
	}

	/**
	 * 功能：设置应急通信保障队伍表属性 组织ID
	 * @param organizationId AdvancedNumber
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置应急通信保障队伍表属性 创建日期
	 * @param creationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		this.creationDate.setCalendarValue(creationDate);
	}

	/**
	 * 功能：设置应急通信保障队伍表属性 创建人
	 * @param createUser AdvancedNumber
	 */
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	/**
	 * 功能：设置应急通信保障队伍表属性 上次修改日期
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}

	/**
	 * 功能：设置应急通信保障队伍表属性 上次修改人
	 * @param lastUpdateUser AdvancedNumber
	 */
	public void setLastUpdateUser(String lastUpdateUser){
		this.lastUpdateUser = lastUpdateUser;
	}


	/**
	 * 功能：获取应急通信保障队伍表属性 队伍ID
	 * @return AdvancedNumber
	 */
	public String getTeamId() {
		return this.teamId;
	}

	/**
	 * 功能：获取应急通信保障队伍表属性 姓名
	 * @return String
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * 功能：获取应急通信保障队伍表属性 职务
	 * @return String
	 */
	public String getPost() {
		return this.post;
	}

	/**
	 * 功能：获取应急通信保障队伍表属性 手机号
	 * @return String
	 */
	public String getTel() {
		return this.tel;
	}

	/**
	 * 功能：获取应急通信保障队伍表属性 专业
	 * @return String
	 */
	public String getCategory() {
		return this.category;
	}

	/**
	 * 功能：获取应急通信保障队伍表属性 属性
	 * @return String
	 */
	public String getAttribute() {
		return this.attribute;
	}

	/**
	 * 功能：获取应急通信保障队伍表属性 组织ID
	 * @return AdvancedNumber
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取应急通信保障队伍表属性 创建日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getCreationDate() throws CalendarException {
		creationDate.setCalPattern(getCalPattern());
		return this.creationDate;
	}

	/**
	 * 功能：获取应急通信保障队伍表属性 创建人
	 * @return AdvancedNumber
	 */
	public String getCreateUser() {
		return this.createUser;
	}

	/**
	 * 功能：获取应急通信保障队伍表属性 上次修改日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastUpdateDate() throws CalendarException {
		lastUpdateDate.setCalPattern(getCalPattern());
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取应急通信保障队伍表属性 上次修改人
	 * @return AdvancedNumber
	 */
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

       public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}