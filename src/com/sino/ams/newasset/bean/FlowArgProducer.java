package com.sino.ams.newasset.bean;

import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class FlowArgProducer {
    private SfUserDTO userAccount = null;
    private String procedureName = "";
    private String groupId = "";

    public FlowArgProducer(SfUserDTO userAccount,
                           AmsAssetsTransHeaderDTO headerDTO) {
        super();
        this.userAccount = userAccount;
        initProcedureName(headerDTO);
    }

    /**
     * 功能：流程名称初始化
     * @param headerDTO AmsAssetsTransHeaderDTO
     */
    private void initProcedureName(AmsAssetsTransHeaderDTO headerDTO) {
        String transType = headerDTO.getTransType();
        if (transType.equals(AssetsDictConstant.ASS_RED)) {
            procedureName = AssetsDictConstant.PROCEDURE_NAME_TRANSFER;
        } else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
            procedureName = AssetsDictConstant.PROCEDURE_NAME_DISCARD;
        } else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
            procedureName = AssetsDictConstant.PROCEDURE_NAME_CLEAR;
        } else if (transType.equals(AssetsDictConstant.ASS_CHK)) {
            procedureName = AssetsDictConstant.PROCEDURE_NAME_CHECK;
        }
    }

    /**
     * 功能：设置当前组别ID
     * @param groupId String
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 功能：获取流程所需javascript脚本
     * @return String
     */
    public String getProcArgScript() {
        StringBuffer scriptCode = new StringBuffer();
        scriptCode.append(WorldConstant.ENTER_CHAR);
        scriptCode.append("var paraObj = new Object();");
        scriptCode.append(WorldConstant.ENTER_CHAR);
        scriptCode.append("function getProcArgs(){");
        scriptCode.append(WorldConstant.ENTER_CHAR);
        scriptCode.append(WorldConstant.TAB_CHAR);
        scriptCode.append("paraObj.procedureName = \"");
        scriptCode.append(procedureName);
        scriptCode.append("\";");
        scriptCode.append(WorldConstant.ENTER_CHAR);
        scriptCode.append(WorldConstant.TAB_CHAR);
        scriptCode.append("paraObj.orgId = ");
        scriptCode.append(userAccount.getOrganizationId());
        scriptCode.append(";");
        if (!StrUtil.isEmpty(groupId)) {
            scriptCode.append(WorldConstant.ENTER_CHAR);
            scriptCode.append(WorldConstant.TAB_CHAR);
            scriptCode.append("paraObj.groupId = ");
            scriptCode.append(groupId);
            scriptCode.append(";");
        }
        scriptCode.append(WorldConstant.ENTER_CHAR);
        scriptCode.append(WorldConstant.TAB_CHAR);
        scriptCode.append("paraObj.useId = ");
        scriptCode.append(userAccount.getUserId());
        scriptCode.append(";");
        scriptCode.append(WorldConstant.ENTER_CHAR);
        scriptCode.append(WorldConstant.TAB_CHAR);
        scriptCode.append("paraObj.useName = \"");
        scriptCode.append(userAccount.getUsername());
        scriptCode.append("\";");
        scriptCode.append(WorldConstant.ENTER_CHAR);
        scriptCode.append(WorldConstant.TAB_CHAR);
        scriptCode.append("paraObj.submitH = \"submitH()\";");
        scriptCode.append(WorldConstant.ENTER_CHAR);
        scriptCode.append(WorldConstant.TAB_CHAR);
        scriptCode.append("return paraObj;");
        scriptCode.append(WorldConstant.ENTER_CHAR);
        scriptCode.append("}");
        return scriptCode.toString();
    }
}
