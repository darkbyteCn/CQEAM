package com.sino.ams.system.trust.dto;

import java.sql.Timestamp;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
* <p>Title: 代维公司相关文件 AmsMaintainFiles</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsMaintainFilesDTO extends CheckBoxDTO{

	private int systemId;
	private String fileDescription = "";
	private String filePath = "";
    private String  companyId;
	private Timestamp creationDate = null;
	private int createdBy;
	private Timestamp lastUpdateDate = null;
	private int lastUpdateBy;
	private String fileName = "";

    public int getSystemId() {
        return systemId;
    }

    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }

    public String  getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String  companyId) {
        this.companyId = companyId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(int lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

	/**
	 * 功能：设置代维公司相关文件属性 文件描述
	 * @param fileDescription String
	 */
	public void setFileDescription(String fileDescription){
		this.fileDescription = fileDescription;
	}

	/**
	 * 功能：设置代维公司相关文件属性 文件存储路径
	 * @param filePath String
	 */
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}

	/**
	 * 功能：设置代维公司相关文件属性 创建日期
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
	 * 功能：设置代维公司相关文件属性 上次修改日期
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
	 * 功能：设置代维公司相关文件属性 文件名
	 * @param fileName String
	 */
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	/**
	 * 功能：获取代维公司相关文件属性 文件描述
	 * @return String
	 */
	public String getFileDescription(){
		return this.fileDescription;
	}

	/**
	 * 功能：获取代维公司相关文件属性 文件存储路径
	 * @return String
	 */
	public String getFilePath(){
		return this.filePath;
	}

	/**
	 * 功能：获取代维公司相关文件属性 创建日期
	 * @return Timestamp
	 */
	public Timestamp getCreationDate(){
		return this.creationDate;
	}

	/**
	 * 功能：获取代维公司相关文件属性 上次修改日期
	 * @return Timestamp
	 */
	public Timestamp getLastUpdateDate(){
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取代维公司相关文件属性 文件名
	 * @return String
	 */
	public String getFileName(){
		return this.fileName;
	}

}