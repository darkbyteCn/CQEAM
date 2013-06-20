package com.sino.ams.system.project.dto;

import java.sql.Timestamp;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.calen.SimpleDate;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DateException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: 项目维护表(EAM) EtsPaProjectsAll</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class EtsPaProjectsAllDTO extends CheckBoxDTO {

    private String projectId = "";
    private String bookTypeCode = "";
    private String name = "";
    private String segment1 = "";
    private String projectType = "";
    private String projectStatusCode = "";
    private Timestamp startDate = null;
    private Timestamp completionDate = null;
    private String enabledFlag = "";
    private String source = "";
    private Timestamp creationDate = null;
    private int createdBy = 0;
    private Timestamp lastUpdateDate = null;
    private int lastUpdateBy = 0;
    private int misProjectId = 0;
    private String projectNumber = "";

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }


    /**
     * 功能：设置项目维护表(EAM)属性 序列号
     * @param projectId String
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * 功能：设置项目维护表(EAM)属性 项目名称
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 功能：设置项目维护表(EAM)属性 项目编号
     * @param segment1 String
     */
    public void setSegment1(String segment1) {
        this.segment1 = segment1;
    }

    /**
     * 功能：设置项目维护表(EAM)属性 项目类型
     * @param projectType String
     */
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    /**
     * 功能：设置项目维护表(EAM)属性 项目状态代码
     * @param projectStatusCode String
     */
    public void setProjectStatusCode(String projectStatusCode) {
        this.projectStatusCode = projectStatusCode;
    }

    /**
     * 功能：设置项目维护表(EAM)属性 项目开始日期
     * @param startDate Timestamp
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setStartDate(String startDate) throws CalendarException {
        if (!StrUtil.isEmpty(startDate)) {
            SimpleCalendar cal = new SimpleCalendar(startDate);
            this.startDate = cal.getSQLTimestamp();
        }
    }

    /**
     * 功能：设置项目维护表(EAM)属性 项目完成日期
     * @param completionDate Timestamp
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCompletionDate(String completionDate) throws CalendarException {
        if (!StrUtil.isEmpty(completionDate)) {
            SimpleCalendar cal = new SimpleCalendar(completionDate);
            this.completionDate = cal.getSQLTimestamp();
        }
    }

    /**
     * 功能：设置项目维护表(EAM)属性 'Y'
     * @param enabledFlag String
     */
    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    /**
     * 功能：设置项目维护表(EAM)属性 来源 'MIS'  or 'AMS'
     * @param source String
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 功能：设置项目维护表(EAM)属性 创建日期
     * @param creationDate Timestamp
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException {
        if (!StrUtil.isEmpty(creationDate)) {
            SimpleCalendar cal = new SimpleCalendar(creationDate);
            this.creationDate = cal.getSQLTimestamp();
        }
    }

    /**
     * 功能：设置项目维护表(EAM)属性 创建人
     * @param createdBy String
     */
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 功能：设置项目维护表(EAM)属性 上次修改日期
     * @param lastUpdateDate Timestamp
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        if (!StrUtil.isEmpty(lastUpdateDate)) {
            SimpleCalendar cal = new SimpleCalendar(lastUpdateDate);
            this.lastUpdateDate = cal.getSQLTimestamp();
        }
    }

    /**
     * 功能：设置项目维护表(EAM)属性 上次修改人
     * @param lastUpdateBy String
     */
    public void setLastUpdateBy(int lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * 功能：设置项目维护表(EAM)属性 MIS项目ID
     * @param misProjectId String
     */
    public void setMisProjectId(int misProjectId) {
        this.misProjectId = misProjectId;
    }


    /**
     * 功能：获取项目维护表(EAM)属性 序列号
     * @return String
     */
    public String getProjectId() {
        return this.projectId;
    }

    /**
     * 功能：获取项目维护表(EAM)属性 项目名称
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * 功能：获取项目维护表(EAM)属性 项目编号
     * @return String
     */
    public String getSegment1() {
        return this.segment1;
    }

    /**
     * 功能：获取项目维护表(EAM)属性 项目类型
     * @return String
     */
    public String getProjectType() {
        return this.projectType;
    }

    /**
     * 功能：获取项目维护表(EAM)属性 项目状态代码
     * @return String
     */
    public String getProjectStatusCode() {
        return this.projectStatusCode;
    }

    /**
     * 功能：获取项目维护表(EAM)属性 项目开始日期
     * @return Timestamp
     */
    public Timestamp getStartDate() {
        return this.startDate;
    }

    /**
     * 功能：获取项目维护表(EAM)属性 项目完成日期
     * @return Timestamp
     */
    public Timestamp getCompletionDate() {
        return this.completionDate;
    }

    /**
     * 功能：获取项目维护表(EAM)属性 'Y'
     * @return String
     */
    public String getEnabledFlag() {
        return this.enabledFlag;
    }

    /**
     * 功能：获取项目维护表(EAM)属性 来源 'MIS'  or 'AMS'
     * @return String
     */
    public String getSource() {
        return this.source;
    }

    /**
     * 功能：获取项目维护表(EAM)属性 创建日期
     * @return Timestamp
     */
    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    /**
     * 功能：获取项目维护表(EAM)属性 创建人
     * @return String
     */
    public int getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 功能：获取项目维护表(EAM)属性 上次修改日期
     * @return Timestamp
     */
    public Timestamp getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取项目维护表(EAM)属性 上次修改人
     * @return String
     */
    public int getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    /**
     * 功能：获取项目维护表(EAM)属性 MIS项目ID
     * @return String
     */
    public int getMisProjectId() {
        return this.misProjectId;
    }

    public String getBookTypeCode() {
		return bookTypeCode;
	}

	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}

    public String getStartDate1() throws DateException {
        String strActiveStartDate = "";
        SimpleDate date = new SimpleDate();
        if (startDate != null) {
            date.setDateValue(startDate);
        }
        strActiveStartDate = date.getDateValue();
        return strActiveStartDate;
    }


    public String getCompletionDate1() throws DateException {
        String strActiveEndDate = "";
        if (completionDate != null) {
            SimpleDate date = new SimpleDate();
            date.setDateValue(completionDate);
            strActiveEndDate = date.getDateValue();
        }
        return strActiveEndDate;
    }

}