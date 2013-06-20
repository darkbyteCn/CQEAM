package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;

import java.util.List;

/**
* <p>Title: 组别属性 SfGroup</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class DeptGroupDTO extends CheckBoxDTO{

    private String sfProject = "";
    private String project = "";
    private String group = "";
    private String dept = "";

    private List<DeptGroupLineDTO> lines = null;

    public DeptGroupDTO() {
		super();

	}

    public void setSfProject(String sfProject) {
        this.sfProject = sfProject;
    }

    public String getSfProject() {
        return this.sfProject;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getProject() {
        return this.project;
    }

    public void setGroup(String group){
		this.group = group;
	}

	public String getGroup() {
		return this.group;
	}

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDept() {
        return this.dept;
    }

    public List<DeptGroupLineDTO> getLines() {
        return lines;
    }

    public void setLines(List<DeptGroupLineDTO> lines) {
        this.lines = lines;
    }

}