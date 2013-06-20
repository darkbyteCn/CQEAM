package com.sino.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ProcedureDTO extends CommonRecordDTO {
    private String procdureName = "";

    public ProcedureDTO() {
        super();
    }

    public void setProcdureName(String procdureName) {
        this.procdureName = procdureName;
    }

    public String getProcdureName() {
        return procdureName;
    }
}
