package com.sino.ams.newasset.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * <p>Title: MIS部门(HR) AmsHrDept</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsHrDeptDTO extends CheckBoxDTO {

    private String hrDeptId = "";
    private String hrDeptName = "";
    private String companyCode = "";

    public AmsHrDeptDTO() {
        super();
    }

    /**
     * 功能：设置MIS部门(HR)属性 部门ID
     * @param hrDeptId String
     */
    public void setHrDeptId(String hrDeptId) {
        this.hrDeptId = hrDeptId;
    }

    /**
     * 功能：设置MIS部门(HR)属性 部门名称
     * @param hrDeptName String
     */
    public void setHrDeptName(String hrDeptName) {
        this.hrDeptName = hrDeptName;
    }

    /**
     * 功能：设置MIS部门(HR)属性 公司代码
     * @param companyCode String
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }


    /**
     * 功能：获取MIS部门(HR)属性 部门ID
     * @return String
     */
    public String getHrDeptId() {
        return this.hrDeptId;
    }

    /**
     * 功能：获取MIS部门(HR)属性 部门名称
     * @return String
     */
    public String getHrDeptName() {
        return this.hrDeptName;
    }

    /**
     * 功能：获取MIS部门(HR)属性 公司代码
     * @return String
     */
    public String getCompanyCode() {
        return this.companyCode;
    }

}
