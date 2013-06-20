package com.sino.nm.spare2.bean;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.web.WebConstant;
import com.sino.base.log.Logger;
import com.sino.base.lookup.LookUpProp;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.nm.spare2.dto.AmsItemTransLDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-3-26
 */
public class SpareLookUpServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        String lookUpName = req.getParameter("lookUpName");
        lookUpName = StrUtil.nullToString(lookUpName);
        Message message = SessionUtil.getMessage(req);
        try {
            if (lookUpName.equals("")) {
                message = getMessage(AssetsMessageKeys.INVALID_REQ);
                message.setIsError(true);
                message.setNeedClose(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            } else {
                LookUpProp lookProp = new LookUpProp(lookUpName);
                String[] dispNames = null;
                String[] dispLabels = null;
                String[] retFields = null;
                String[] viewPercent = null;
                String[] qryNames = null;
                String[] qryLabels = null;
                String[] primaryKeys = null;
                if (lookUpName.equals(SpareLookUpConstant.BJSXFH_ITEM_INFO)) {  //备件送修返还查询设备(NM) HERRY 2008-3-26
                    lookProp.setMultipleChose(true);
                    dispNames = new String[]{"BATCH_NO", "BARCODE", "ITEM_NAME", "ITEM_SPEC", "REPAIR_QUANTITY", "RETURNED_QUANTITY"};
                    dispLabels = new String[]{"送修单号", "物料编码", "设备名称", "规格型号", "送修数量", "已返还数量"};
                    retFields = new String[]{"BATCH_NO", "BARCODE", "ITEM_CODE", "ITEM_NAME", "ITEM_SPEC", "REPAIR_QUANTITY", "RETURNED_QUANTITY"};
                    viewPercent = new String[]{"18%", "15%", "20%", "25%", "9%", "10%"};
                    qryNames = new String[]{"BATCH_NO", "BARCODE", "ITEM_NAME", "ITEM_SPEC"};
                    qryLabels = new String[]{"送修单号", "物料编码", "设备名称", "规格型号"};
                    primaryKeys = new String[]{"BATCH_NO", "BARCODE"};
                    lookProp.setTotalWidth(790);
                    lookProp.setMemorySpan(true);
                    lookProp.setDtoClass(AmsItemTransLDTO.class);
                } else if (lookUpName.equals(SpareLookUpConstant.BJCK_SPARE_INFO)) {//备件出库查找设备(NM) HERRY 2008-3-27
                    lookProp.setMultipleChose(true);
                    dispNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ONHAND_QTY"};
                    dispLabels = new String[]{"物料编码", "设备名称", "规格型号", "可用量"};
                    viewPercent = new String[]{"20%", "25%", "40%", "10%"};
                    retFields = new String[]{"BARCODE", "ITEM_CODE", "ITEM_NAME", "ITEM_SPEC", "ONHAND_QTY"};
                    qryNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
                    qryLabels = new String[]{"物料编码", "设备名称", "规格型号"};
                    primaryKeys = new String[]{"BARCODE"};

                    lookProp.setTotalWidth(750);
                    lookProp.setMemorySpan(true);
                    lookProp.setDtoClass(AmsItemTransLDTO.class);
                }else if (lookUpName.equals(SpareLookUpConstant.BJBF_SPARE_INFO_DX)) {  //备件报废查找设备(NM)从待修数量报废 HERRY 2008-7-1
                    dispNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ONHAND_QTY"};
                    dispLabels = new String[]{"物料编码", "设备名称", "规格型号", "待修数量"};
                    retFields = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ONHAND_QTY", "ITEM_CODE", "STORAGE_ID"};
                    viewPercent = new String[]{"20%", "30%", "40%", "10%"};
                    qryNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
                    qryLabels = new String[]{"物料编码", "设备名称", "规格型号"};
                    primaryKeys = new String[]{"BARCODE"};

                    lookProp.setTotalWidth(750);
                    lookProp.setMemorySpan(true);
                    lookProp.setDtoClass(AmsItemTransLDTO.class);
                }else if (lookUpName.equals(SpareLookUpConstant.BJBF_SPARE_INFO_SX)) {  //备件报废查找设备(NM)从送修数量报废 HERRY 2008-7-3
                    dispNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ONHAND_QTY"};
                    dispLabels = new String[]{"物料编码", "设备名称", "规格型号", "送修数量"};
                    retFields = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ONHAND_QTY", "ITEM_CODE", "STORAGE_ID", "SOURCE_ID"};
                    viewPercent = new String[]{"20%", "30%", "40%", "10%"};
                    qryNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
                    qryLabels = new String[]{"物料编码", "设备名称", "规格型号"};
                    primaryKeys = new String[]{"BARCODE"};

                    lookProp.setTotalWidth(750);
                    lookProp.setMemorySpan(true);
                    lookProp.setDtoClass(AmsItemTransLDTO.class);
                }else if (lookUpName.equals(SpareLookUpConstant.OBJECT_NO)) { //备件仓库
					lookProp.setMultipleChose(false);
					dispNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					dispLabels = new String[]{"仓库代码", "仓库名称", "仓库地点"};
					retFields = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION", "WORKORDER_OBJECT_NO"};
					viewPercent = new String[]{"10%","35%", "40%"};
					qryNames = new String[]{"WORKORDER_OBJECT_CODE","WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					qryLabels = new String[]{"仓库代码", "仓库名称", "仓库地点"};
					primaryKeys = new String[]{"WORKORDER_OBJECT_NO"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsObjectDTO.class);
				}else if (lookUpName.equals(SpareLookUpConstant.OU_OBJECT)) { //备件仓库和OU
					lookProp.setMultipleChose(false);
					dispNames = new String[]{"COMPANY", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					dispLabels = new String[]{"公司", "仓库名称", "仓库地点"};
					viewPercent = new String[]{"30%", "30%", "36%"};
					retFields = new String[]{"COMPANY", "ORGANIZATION_ID", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION", "WORKORDER_OBJECT_NO"};
					qryNames = new String[]{"COUNTY_NAME", "WORKORDER_OBJECT_NAME"};
					qryLabels = new String[]{"公司", "仓库名称"};
					primaryKeys = new String[]{"WORKORDER_OBJECT_NO"};
					lookProp.setTotalWidth(750);
					lookProp.setDtoClass(EtsObjectDTO.class);
				}
                lookProp.setCalPattern(LINE_PATTERN);
                lookProp.setDisFieldNames(dispNames);
                lookProp.setDisFieldLabels(dispLabels);
                lookProp.setRetFields(retFields);
                lookProp.setViewPercent(viewPercent);
                lookProp.setQryFieldNames(qryNames);
                lookProp.setQryFieldLabels(qryLabels);
                lookProp.setPrimaryKeys(primaryKeys);
                lookProp.setModelClass(SpareLookUpModel.class);
                forwardURL = WebConstant.LOOK_UP_SERVLET;
                SessionUtil.saveLoopUpProp(req, lookProp);
            }
        } catch (Exception ex) {
            Logger.logError(ex);
            throw new ServletException(ex);
        } finally {
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
