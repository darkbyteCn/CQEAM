package com.sino.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * <p>Title: GROUP_MATCH GroupMatch</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class SfGroupMatchDTO extends CommonRecordDTO {

    private String deptCode = "";
    private String deptName = "";
    private int groupId;
    private String groupname = "";

    /**
     * 功能：设置GROUP_MATCH属性 DEPT_CODE
     * @param deptCode String
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    /**
     * 功能：设置GROUP_MATCH属性 DEPT_NAME
     * @param deptName String
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * 功能：设置GROUP_MATCH属性 GROUP_ID
     * @param groupId String
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * 功能：设置GROUP_MATCH属性 GROUPNAME
     * @param groupname String
     */
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }


    /**
     * 功能：获取GROUP_MATCH属性 DEPT_CODE
     * @return String
     */
    public String getDeptCode() {
        return this.deptCode;
    }

    /**
     * 功能：获取GROUP_MATCH属性 DEPT_NAME
     * @return String
     */
    public String getDeptName() {
        return this.deptName;
    }

    /**
     * 功能：获取GROUP_MATCH属性 GROUP_ID
     * @return String
     */
    public int getGroupId() {
        return this.groupId;
    }

    /**
     * 功能：获取GROUP_MATCH属性 GROUPNAME
     * @return String
     */
    public String getGroupname() {
        return this.groupname;
    }

}
