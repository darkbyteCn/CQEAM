package com.sino.ams.newasset.bean;

import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.exception.HandlerException;
import com.sino.base.exception.StrException;
import com.sino.base.web.EventHandler;
import com.sino.base.web.EventHandlers;
import com.sino.base.web.WebRadio;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AssetsRadioProducer {

    private SfUserDTO userAccount = null;

    public AssetsRadioProducer(SfUserDTO userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 功能：构造资产归属范围单选按钮。
     * @return String
     * @throws HandlerException
     */
    public String getAssetsRadio() throws HandlerException {
        StringBuffer assetsRadio = new StringBuffer();
        try {
            String[] valCates = AssetsWebAttributes.ASSETS_TREE_TYPES;
            String[] capCates = AssetsWebAttributes.CAPTIONS_TREE_TYPES;
            String radioName = "treeCategory";
            EventHandler handler = new EventHandler();
            handler.setEventName("onClick");
            handler.setFunName("do_ChangeAssetsTree");
            EventHandlers handlers = new EventHandlers();
            handlers.addHandler(handler);
            String assetsValue = "";
            boolean isDeptMgr = userAccount.isDptAssetsManager();
            boolean isComMgr = userAccount.isComAssetsManager();
            boolean isProvMgr = userAccount.isProvAssetsManager();
            for (int i = 0; i < valCates.length; i++) {
                assetsValue = valCates[i];
                if (assetsValue.equals(AssetsWebAttributes.ASSETS_TREE_DEPART)) { //部门资产管理员判断
                    if (!isDeptMgr) {
                        continue;
                    }
                }
                if (assetsValue.equals(AssetsWebAttributes.ASSETS_TREE_COMPAN)) { //公司资产管理员判断
                    if (!isComMgr) {
                        continue;
                    }
                }
                if (assetsValue.equals(AssetsWebAttributes.ASSETS_TREE_PROVIN)) { //全省资产管理员判断
                    if (!isProvMgr) {
                        continue;
                    }
                }
                WebRadio radio = new WebRadio(radioName);
                radio.addValueCaption(valCates[i], capCates[i]);
                radio.setFontColor("#FFFFFF");
                radio.setFontSize(2);
                radio.addEventHandlers(handlers);
                assetsRadio.append(radio.toString());
            }
        } catch (StrException ex) {
            ex.printLog();
            throw new HandlerException(ex);
        }
        return assetsRadio.toString();
    }

    /**
     * 功能：构造调出和调入单据打印单选按钮
     * @param printType 打印类型
     * @return String
     * @throws StrException
     */
    public String getOrderPrintRadio(String printType) throws StrException {
        StringBuffer printRadio = new StringBuffer();
        String[] valCates = AssetsWebAttributes.PRINT_TRANS_VALUE;
        String[] capCates = AssetsWebAttributes.PRINT_TRANS_CAP;
        String radioName = "printType";
        for (int i = 0; i < valCates.length; i++) {
            WebRadio radio = new WebRadio(radioName);
            radio.addValueCaption(valCates[i], capCates[i]);
            radio.setCheckedValue(printType);
            printRadio.append(radio.toString());
        }
        return printRadio.toString();
    }

    /**
     * 功能：构造专业管理员权限用单选按钮
     * @param mtlPrivi 资产管理员权限属性
     * @return String
     * @throws StrException
     */
    public String getMtlPriviRadio(String mtlPrivi) throws StrException {
        StringBuffer printRadio = new StringBuffer();
        String[] valCates = AssetsWebAttributes.MTL_PRIVI_VAL;
        String[] capCates = AssetsWebAttributes.MTL_PRIVI_CAP;
        String radioName = "mtlPrivi";
        for (int i = 0; i < valCates.length; i++) {
            WebRadio radio = new WebRadio(radioName);
            radio.addValueCaption(valCates[i], capCates[i]);
            radio.setCheckedValue(mtlPrivi);
            printRadio.append(radio.toString());
        }
        return printRadio.toString();
    }

    /**
     * 功能：资产用户分配属性
     * @param assProp 分配属性
     * @return String
     * @throws HandlerException
     */
    public String getAssignRadio(String assProp) throws HandlerException {
        StringBuffer printRadio = new StringBuffer();
        try {
            String[] valCates = AssetsWebAttributes.ASSIGN_VAL;
            String[] capCates = AssetsWebAttributes.ASSIGN_CAP;
            String radioName = "assProp";
            EventHandler handler = new EventHandler();
            handler.setEventName("onClick");
            handler.setFunName("do_ChangeAssignProp");
            EventHandlers handlers = new EventHandlers();
            handlers.addHandler(handler);
            boolean isDeptMgr = userAccount.isDptAssetsManager();
            boolean isComMgr = userAccount.isComAssetsManager();
            String value = "";
            for (int i = 0; i < valCates.length; i++) {
                value = valCates[i];
                if (value.equals(AssetsWebAttributes.ASSIGN_COST_CENTER)) { //公司资产管理员判断
                    if (!isComMgr) {
                        continue;
                    }
                }
                if (value.equals(AssetsWebAttributes.ASSIGN_RESPONSIBLE_USER)) { //部门资产管理员判断
                    if (!isDeptMgr && !isComMgr) {
                        continue;
                    }
                }
                WebRadio radio = new WebRadio(radioName);
                radio.addValueCaption(valCates[i], capCates[i]);
                radio.setFontColor("#FFFFFF");
                radio.setFontSize(2);
                radio.setCheckedValue(assProp);
                radio.addEventHandlers(handlers);
                printRadio.append(radio.toString());
            }
        } catch (StrException ex) {
            ex.printLog();
            throw new HandlerException(ex);
        }
        return printRadio.toString();
    }
}
