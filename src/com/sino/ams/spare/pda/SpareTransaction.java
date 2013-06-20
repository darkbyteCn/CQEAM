package com.sino.ams.spare.pda;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sino.base.data.Row;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;

import com.sino.pda.ProcessXML;
import com.sino.ams.spare.dao.BjdbApproveDAO;
import com.sino.ams.spare.dto.AmsItemAllocateDDTO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: pda提交数据后的处理</p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2007-11-8
 */
public class SpareTransaction extends ProcessXML {
    public static final String BILL_TYPE_XGRK = "1";        //新购入库
    public static final String BILL_TYPE_BJFK = "2";        //备件返库
    public static final String BILL_TYPE_BJCK_S = "3";      //备件出库_市
    public static final String BILL_TYPE_BJFP_OUT = "4";    //备件分配_设备出库
    public static final String BILL_TYPE_BJFP_IN = "5";     //备件分配_设备入库
    public static final String BILL_TYPE_BJDB_OUT = "6";    //备件调拨_设备出库
    public static final String BILL_TYPE_BJDB_IN = "7";     //备件调拨_设备入库
    public static final String BILL_TYPE_OTHERS_IN = "8";   //其它设备入库（如：资产、工程物资）
    public static final String BILL_TYPE_OTHERS_OUT = "9";  //其它设备出库（如：资产、工程物资）

    private AmsItemTransHDTO header = null;
    private DTOSet dtos = null;
    private String bill_type = "";

    public void initData() {
        setRootName("INVTransaction");
        header = new AmsItemTransHDTO();
        dtos = new DTOSet();
    }

    public boolean processData(Document xmlDoc) {
        boolean success = false;
        try {
            constructDTOs(xmlDoc);
            if (bill_type.equals(BILL_TYPE_BJDB_OUT) || bill_type.equals(BILL_TYPE_BJDB_IN)) {
                setTransInfoByNo("AMS_ITEM_ALLOCATE_H");
            } else {
                setTransInfoByNo("AMS_ITEM_TRANS_H");
            }
            BjdbApproveDAO approveDAO = new BjdbApproveDAO((SfUserDTO) userAccount, header, conn);
            approveDAO.saveDetails(dtos); //这里只保存条码信息,不审批单据
            success = true;

        } catch (DTOException e) {
            Logger.logError(e);
        } catch (QueryException e) {
            Logger.logError(e);
        } catch (ContainerException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            Logger.logError(e);
        }
        return success;
    }

    /**
     * 根据上传的XML文件来构造DTO
     * @param xmlDoc Document
     * @throws DTOException
     */
    private void constructDTOs(Document xmlDoc) throws DTOException {
        Element root = xmlDoc.getRootElement();
        String orderNo = root.getAttributeValue("no");
        bill_type = root.getAttributeValue("bill_type");
        req.setAttribute("bill_type", bill_type);
        header.setTransNo(orderNo);
        List items = root.getChildren();
        Element item = null;
        AmsItemAllocateDDTO detailDTO = null;
        for (int i = 0; i < items.size(); i++) {
            item = (Element) items.get(i);
            detailDTO = new AmsItemAllocateDDTO();
            detailDTO.setBarcode(item.getAttributeValue("code"));
            detailDTO.setItemCategory(item.getAttributeValue("catetory"));
            dtos.addDTO(detailDTO);
        }
    }

    /**
     * 根据单据号查询相关信息
     * @param tableName 表名
     * @throws QueryException
     * @throws ContainerException
     */
    private void setTransInfoByNo(String tableName) throws QueryException, ContainerException {
        SQLModel sqlModel = new SQLModel();
        List argList = new ArrayList();
        String sqlStr = " SELECT TRANS_ID,TO_ORGANIZATION_ID FROM " + tableName + "" +
                "       WHERE TRANS_NO = ?";
        argList.add(header.getTransNo());
        sqlModel.setArgs(argList);
        sqlModel.setSqlStr(sqlStr);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        Row row = sq.getFirstRow();
        header.setTransId(String.valueOf(row.getValue("TRANS_ID")));
        header.setToOrganizationId(Integer.parseInt(row.getStrValue("TO_ORGANIZATION_ID")));
    }
}
