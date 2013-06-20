package com.sino.ams.newasset.dto;

import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.base.dto.CheckBoxDTO;

/**
 * <p>Title: AMS_FA_CATEGORY_V AmsFaCategoryV</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsFaCategoryVDTO extends CheckBoxDTO {

    private String faCatCode1 = "";
    private String faCatName1 = "";
    private String faCatCode2 = "";
    private String faCatName2 = "";
    private String faCatCode3 = "";
    private String faCatName3 = "";
    private String faCategoryCode = "";
    private String faCategoryName = "";
    private String userId = "";
    private String userName = "";
    private String mtlPrivi = AssetsWebAttributes.MTL_PRIVI_N;

    /**
     * 功能：设置AMS_FA_CATEGORY_V属性 FA_CAT_CODE_1
     * @param faCatCode1 String
     */
    public void setFaCatCode1(String faCatCode1) {
        this.faCatCode1 = faCatCode1;
    }

    /**
     * 功能：设置AMS_FA_CATEGORY_V属性 FA_CAT_NAME_1
     * @param faCatName1 String
     */
    public void setFaCatName1(String faCatName1) {
        this.faCatName1 = faCatName1;
    }

    /**
     * 功能：设置AMS_FA_CATEGORY_V属性 FA_CAT_CODE_2
     * @param faCatCode2 String
     */
    public void setFaCatCode2(String faCatCode2) {
        this.faCatCode2 = faCatCode2;
    }

    /**
     * 功能：设置AMS_FA_CATEGORY_V属性 FA_CAT_NAME_2
     * @param faCatName2 String
     */
    public void setFaCatName2(String faCatName2) {
        this.faCatName2 = faCatName2;
    }

    /**
     * 功能：设置AMS_FA_CATEGORY_V属性 FA_CAT_CODE_3
     * @param faCatCode3 String
     */
    public void setFaCatCode3(String faCatCode3) {
        this.faCatCode3 = faCatCode3;
    }

    /**
     * 功能：设置AMS_FA_CATEGORY_V属性 FA_CAT_NAME_3
     * @param faCatName3 String
     */
    public void setFaCatName3(String faCatName3) {
        this.faCatName3 = faCatName3;
    }

    /**
     * 功能：设置AMS_FA_CATEGORY_V属性 FA_CATEGORY_CODE
     * @param faCategoryCode String
     */
    public void setFaCategoryCode(String faCategoryCode) {
        this.faCategoryCode = faCategoryCode;
    }

    /**
     * 功能：设置AMS_FA_CATEGORY_V属性 FA_CATEGORY_NAME
     * @param faCategoryName String
     */
    public void setFaCategoryName(String faCategoryName) {
        this.faCategoryName = faCategoryName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMtlPrivi(String mtlPrivi) {
        this.mtlPrivi = mtlPrivi;
    }

    /**
     * 功能：获取AMS_FA_CATEGORY_V属性 FA_CAT_CODE_1
     * @return String
     */
    public String getFaCatCode1() {
        return this.faCatCode1;
    }

    /**
     * 功能：获取AMS_FA_CATEGORY_V属性 FA_CAT_NAME_1
     * @return String
     */
    public String getFaCatName1() {
        return this.faCatName1;
    }

    /**
     * 功能：获取AMS_FA_CATEGORY_V属性 FA_CAT_CODE_2
     * @return String
     */
    public String getFaCatCode2() {
        return this.faCatCode2;
    }

    /**
     * 功能：获取AMS_FA_CATEGORY_V属性 FA_CAT_NAME_2
     * @return String
     */
    public String getFaCatName2() {
        return this.faCatName2;
    }

    /**
     * 功能：获取AMS_FA_CATEGORY_V属性 FA_CAT_CODE_3
     * @return String
     */
    public String getFaCatCode3() {
        return this.faCatCode3;
    }

    /**
     * 功能：获取AMS_FA_CATEGORY_V属性 FA_CAT_NAME_3
     * @return String
     */
    public String getFaCatName3() {
        return this.faCatName3;
    }

    /**
     * 功能：获取AMS_FA_CATEGORY_V属性 FA_CATEGORY_CODE
     * @return String
     */
    public String getFaCategoryCode() {
        return this.faCategoryCode;
    }

    /**
     * 功能：获取AMS_FA_CATEGORY_V属性 FA_CATEGORY_NAME
     * @return String
     */
    public String getFaCategoryName() {
        return this.faCategoryName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getMtlPrivi() {
        return mtlPrivi;
    }
}
