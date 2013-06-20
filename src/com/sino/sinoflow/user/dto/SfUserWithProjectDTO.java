package com.sino.sinoflow.user.dto;

/**
* <p>Title: SfUserRight</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfUserWithProjectDTO extends SfUserBaseDTO {
    private String projectName="";
	private String groupName = "";
	private String roleName = "";

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

    public String getProjectName() {
        return projectName;
    }

    public String getGroupName() {
		return groupName;
	}

	public String getRoleName() {
		return roleName;
	}

}
