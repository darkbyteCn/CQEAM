package com.sino.ams.newasset.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * <p>Title: 固定资产目录字典 AmsFaDict</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsFaDictDTO extends CheckBoxDTO {

    private String faCode = "";
    private String faDescription = "";
    private String faContentCode = "";

    public AmsFaDictDTO() {
        super();
    }

    /**
     * 功能：设置固定资产目录字典属性 固定资产第二段的第一段代码
     * @param faCode String
     */
    public void setFaCode(String faCode) {
        this.faCode = faCode;
    }

    /**
     * 功能：设置固定资产目录字典属性 固定资产第二段的第一段描述
     * @param faDescription String
     */
    public void setFaDescription(String faDescription) {
        this.faDescription = faDescription;
    }

    /**
     * 功能：设置固定资产目录字典属性 EAM系统定义的固定资产目录字典代码
     * @param faContentCode String
     */
    public void setFaContentCode(String faContentCode) {
        this.faContentCode = faContentCode;
    }


    /**
     * 功能：获取固定资产目录字典属性 固定资产第二段的第一段代码
     * @return String
     */
    public String getFaCode() {
        return this.faCode;
    }

    /**
     * 功能：获取固定资产目录字典属性 固定资产第二段的第一段描述
     * @return String
     */
    public String getFaDescription() {
        return this.faDescription;
    }

    /**
     * 功能：获取固定资产目录字典属性 EAM系统定义的固定资产目录字典代码
     * @return String
     */
    public String getFaContentCode() {
        return this.faContentCode;
    }
}
