package com.sino.ams.log.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
* <p>Title: 用户个人收藏夹(EAM) EtsFavorites</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsFavoritesDTO extends CheckBoxDTO{

	private String systemid = "";
	private int sortNo;
	private int userId;
	private int sfResId;
	private SimpleCalendar creationDate = null;
	private int createdBy;
	private SimpleCalendar lastUpdateDate = null;
	private int lastUpdateBy;
    private String menu="";
    private String smallMenu="";


    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getSmallMenu() {
        return smallMenu;
    }

    public void setSmallMenu(String smallMenu) {
        this.smallMenu = smallMenu;
    }

    /**
	 * 功能：设置用户个人收藏夹(EAM)属性 流水号
	 * @param systemid String
	 */
	public void setSystemid(String systemid){
		this.systemid = systemid;
	}

	/**
	 * 功能：设置用户个人收藏夹(EAM)属性 排序号
	 * @param sortNo String
	 */
	public void setSortNo(int sortNo){
		this.sortNo = sortNo;
	}

	/**
	 * 功能：设置用户个人收藏夹(EAM)属性 用户ID
	 * @param userId String
	 */
	public void setUserId(int userId){
		this.userId = userId;
	}

	/**
	 * 功能：设置用户个人收藏夹(EAM)属性 栏目ID
	 * @param sfResId String
	 */
	public void setSfResId(int sfResId){
		this.sfResId = sfResId;
	}

	/**
	 * 功能：设置用户个人收藏夹(EAM)属性 创建日期
	 * @param creationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		if(!StrUtil.isEmpty(creationDate)){
			this.creationDate = new SimpleCalendar(creationDate);
		}
	}

	/**
	 * 功能：设置用户个人收藏夹(EAM)属性 创建人
	 * @param createdBy String
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置用户个人收藏夹(EAM)属性 上次修改日期
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		if(!StrUtil.isEmpty(lastUpdateDate)){
			this.lastUpdateDate = new SimpleCalendar(lastUpdateDate);
		}
	}

	/**
	 * 功能：设置用户个人收藏夹(EAM)属性 上次修改人
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}


	/**
	 * 功能：获取用户个人收藏夹(EAM)属性 流水号
	 * @return String
	 */
	public String getSystemid(){
		return this.systemid;
	}

	/**
	 * 功能：获取用户个人收藏夹(EAM)属性 排序号
	 * @return String
	 */
	public int getSortNo(){
		return this.sortNo;
	}

	/**
	 * 功能：获取用户个人收藏夹(EAM)属性 用户ID
	 * @return String
	 */
	public int getUserId(){
		return this.userId;
	}

	/**
	 * 功能：获取用户个人收藏夹(EAM)属性 栏目ID
	 * @return String
	 */
	public int getSfResId(){
		return this.sfResId;
	}

	/**
	 * 功能：获取用户个人收藏夹(EAM)属性 创建日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getCreationDate(){
		return this.creationDate;
	}

	/**
	 * 功能：获取用户个人收藏夹(EAM)属性 创建人
	 * @return String
	 */
	public int getCreatedBy(){
		return this.createdBy;
	}

	/**
	 * 功能：获取用户个人收藏夹(EAM)属性 上次修改日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getLastUpdateDate(){
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取用户个人收藏夹(EAM)属性 上次修改人
	 * @return String
	 */
	public int getLastUpdateBy(){
		return this.lastUpdateBy;
	}

}