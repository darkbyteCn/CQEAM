package com.sino.pda;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.data.Row;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.spare.dto.AmsItemTransDDTO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.util.XmlUtil;
import org.jdom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-3-12
 * Time: 13:34:54
 * Function: 提交
 */
public class SubmitSpareOrder extends BaseServlet {
    private String conFilePath = "";
//    private static final String CONTENT_TYPE = "text/xml; charset=GBK";
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private String categoryDesc = "";

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = null;
        Connection conn = null;
        try {
            res.setContentType(CONTENT_TYPE);
            PrintWriter resout = res.getWriter();

            conn = DBManager.getDBConnection();
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
            String sFile = "";
            String test = "";
            Logger.logInfo("PDA run SubmitSpareOrder servlet begin....");
            RequestParser reqPar = new RequestParser();
            reqPar.transData(req);
            test = reqPar.getParameter("test");
            if (test.equals("Y")) {
                resout.println("<?xml version=\"1.0\" encoding=\"GB2312\" ?> ");
            } else {
                resout.println("<?xml version=\"1.0\" ?> ");
            }
            resout.println("<SignWorkOrder>");
            UploadFile[] upFiles = null;
            UploadRow uploadRow;
            boolean hasError = false;
            conFilePath = PDAUtil.getCurUploadFilePath(conn);   //获得”工单“文件路径

            UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
            uploadFileSaver.saveFiles(conFilePath);

            uploadRow = uploadFileSaver.getRow();
            upFiles = uploadRow.getFiles();

            if (upFiles == null) {
                Logger.logError("upload file " + SyBaseSQLUtil.isNull() + " ");
                hasError = true;
                setResultValue(resout, false, "upload file " + SyBaseSQLUtil.isNull() + " ", "");
            } else {
                if (upFiles.length == 1) {
                    if (upFiles[0].getFileName().equals("")) {
                        Logger.logError("can't find upload xml files");
                        hasError = true;
                        setResultValue(resout, false, "can't find upload xml files,please check PDA prog or GPRS", "");
                    }
                } else {
                    Logger.logError("can't find any xml file");
                    setResultValue(resout, false, "can't find any xml file", "");
                    hasError = true;
                }
            }
            //==================================================
            if (!hasError) {
                sFile = upFiles[0].getAbsolutePath();
                XmlUtil xml = new XmlUtil();
                if (!xml.loadXmlFile(sFile)) {
                    ClearBeforeCancel(resout, conFilePath, false, "load xml file error!");
                    return;
                }
                List allOrderNos = xml.getAllRootChildren();
                int orderNum = allOrderNos.size();
                if (orderNum < 1) {
                    ClearBeforeCancel(resout, conFilePath, false, "can't get XML file child");
                    return;
                }
                String transNo = "";
                String transType = "";
                String creationDate = "";
                String creationUser = "";
                String vendor = "";
                //获取<spareOrder  transType=>	，root attribute
//                transNo = xml.getElementAttrValue(el, "transNo");

//                lineId = xml.getElementAttrValue(el, "lineId");

//                System.out.println("transType is ;"+ transType);
//                transType = xml.getElementAttrValue(el, "transType");
//                System.out.println("createdBy is ;"+ transType);
//                creationDate = xml.getElementAttrValue(el, "creationDate");
//                System.out.println("objectNo is ;"+ creationDate);
//                creationUser  = xml.getElementAttrValue(el, "creationUser");
//                System.out.println("organizationId  is ;"+ creationUser );
//                 vendor  = xml.getElementAttrValue(el, "vendor");
//                System.out.println("organizationId  is ;"+ vendor );
//                    PDAOrderUtil pdaOrderUtil = new PDAOrderUtil();
//                    pdaOrderUtil.createWorkorder(conn, xml);
//                String companyCode = userAccount.getCompanyCode();
//                OrderNumGenerator numberProducer = new OrderNumGenerator(conn, companyCode, transType);
//                String userId = getUserId(conn, creationUser);
//                String  orderNo = numberProducer.getOrderNum();
//                AmsItemTransHDTO spareHDTO = new AmsItemTransHDTO();
//                spareHDTO.setTransNo(xml.getElementAttrValue(el, "transNo"));
//                spareHDTO.setTransType(xml.getElementAttrValue(el, "transType"));
//                spareHDTO.setCreationDate(xml.getElementAttrValue(el, "creationDate"));
//                spareHDTO.setCreatedBy(userId);

                DTOSet spddtos = SpareDDTO(conn, xml);      //获得行明细的dtosets
//                creatSpareH(conn, spareHDTO);    //创建头表
//                creatSpareL(conn,spldtos);                                //创建行表
//                creatSpareD(conn, spddtos);
                 submitSpareD(conn, spddtos);
                setResultValue(resout, true, "", transNo);
            } else {
                ClearBeforeCancel(resout, conFilePath, false, "can't find xml file");
            }
        } catch (PoolException e) {
            Logger.logError(e);
        } catch (ContainerException e) {
            Logger.logError(e);
        } catch (UploadException ex) {
            ex.printLog();
        } catch (FileSizeException ex) {
            ex.printLog();
        } catch (DataHandleException e) {
            Logger.logError(e);
        } catch (QueryException e) {
            Logger.logError(e);
//        } catch (SQLException e) {
//           Logger.logError(e);
        } catch (CalendarException e) {
            Logger.logError(e);
        } catch (DTOException e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
//            setHandleMessage(req, message);
//            ServletForwarder forwarder = new ServletForwarder(req, res);

//            forwarder.forwardView(forwardURL);

        }
    }


    private void setResultValue(PrintWriter out, boolean b_flag, String errMsg, String spareOrder) {
        out.println("<result message=\"" + errMsg + "\" >"
                + String.valueOf(b_flag)
                + "</result>");
        out.println("</SignWorkOrder>");
        out.close();
    }


    private boolean creatSpareH(Connection conn, AmsItemTransHDTO spareHDTO) throws DataHandleException, CalendarException {
        boolean flag = false;                    //备件盘点
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_ITEM_TRANS_H\n" +
                "  (TRANS_ID,TRANS_NO,TRANS_TYPE, CREATION_DATE, CREATED_BY)\n" +
                " VALUES\n" +
                "  (AMS_ITEM_TRANS_H_S.NEXTVAL,?,?, 'creat',?)";
        sqlArgs.add(spareHDTO.getTransNo());
        sqlArgs.add(spareHDTO.getTransType());
        sqlArgs.add(spareHDTO.getCreationDate());
        sqlArgs.add(spareHDTO.getCreatedBy());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        flag = DBOperator.updateRecord(sqlModel, conn);
        return flag;
    }


    private void ClearBeforeCancel(PrintWriter resout, String conFilePath, boolean flag, String msg) {
        setResultValue(resout, flag, msg, "");
        PDAUtil.clearDir(conFilePath);
    }

    private String getUserId(Connection conn, String loginName) throws QueryException, ContainerException {
        String userId = "";
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT SU.USER_ID FROM SF_USER SU WHERE SU.LOGIN_NAME = ?";
        sqlArgs.add(loginName);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        Row row = sq.getFirstRow();
        userId = row.getValue("USER_ID").toString();
        return userId;
    }


    /**
     * 根据上传的XML文件来构造行明细DTO
     * @throws DTOException
     */
    private DTOSet SpareDDTO(Connection conn, XmlUtil xml) throws DTOException, QueryException, ContainerException {
        DTOSet dtos = null;
        dtos = new DTOSet();
        Element root = xml.getRootElement();
        List sparorders = root.getChildren();
        Element sparorder = null;
        Element config = null;
        Element item = null;
        AmsItemTransDDTO spareDDTO = null;
        for (int i = 0; i < sparorders.size(); i++) { //sparorder
            sparorder = (Element) sparorders.get(i);
            String transNo = sparorder.getAttributeValue("transNo");
            String transIdH = getTransIdH(conn, transNo);
            String orgId = getOrgId(conn, transNo);
            List configs = sparorder.getChildren();
            for (int k = 0; k < configs.size(); k++) {//configer
                config = (Element) configs.get(k);
                String lineId = config.getAttributeValue("lineId");
                String transId = getTransId(conn, lineId);
                List items = config.getChildren();
                for (int j = 0; j < items.size(); j++) { //item
                    item = (Element) items.get(j);
                    spareDDTO = new AmsItemTransDDTO();
                    if(StrUtil.isEmpty(transId)){
                        spareDDTO.setTransId(transIdH);
                    } else{
                        spareDDTO.setTransId(transId);
                    }
                    spareDDTO.setLineId(lineId);
//                    spareDDTO.setItemCode(item.getAttributeValue("itemCode"));
                    spareDDTO.setSerialNo(item.getAttributeValue("serialNo"));
                    spareDDTO.setBarcode(item.getAttributeValue("barcode"));
                    spareDDTO.setItemName(item.getAttributeValue("itemName"));
                    spareDDTO.setItemSpec(item.getAttributeValue("itemSpec"));
                    spareDDTO.setQuantity(StrUtil.strToInt(item.getAttributeValue("quantity")));
                    spareDDTO.setTroubleReason(item.getAttributeValue("trouble_reson"));
                    spareDDTO.setTroubleLoc(item.getAttributeValue("trouble_loc"));
                    spareDDTO.setOrganizationId(StrUtil.strToInt(orgId));
//          spareDDTO.setQuantity(item.getAttributeValue("vendorCode"));
                    dtos.addDTO(spareDDTO);
                }
            }
        }
        return dtos;
    }


    private boolean creatSpareL(Connection conn, DTOSet spLS) throws DataHandleException, CalendarException {
        List sqModels = new ArrayList();
        boolean flag = false;
        AmsItemTransLDTO spL = null;
        for (int i = 0; i < spLS.getSize(); i++) {
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            spL = (AmsItemTransLDTO) spLS.getDTO(i);
            String sqlStr = "INSERT INTO AMS_ITEM_TRANS_L\n" +
                    "  (TRANS_ID,LINE_ID,ITEM_CODE, QUANTITY, BARCODE)\n" +
                    " VALUES\n" +
                    "  (?,AMS_ITEM_TRANS_L_S.NEXTVAL,?,?, ?,?)";
            sqlArgs.add(spL.getLineId());
            sqlArgs.add(spL.getItemCode());
            sqlArgs.add(spL.getQuantity());
            sqlArgs.add(spL.getBarcode());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            sqModels.add(sqlModel);
        }
        flag = DBOperator.updateBatchRecords(sqModels, conn);
        return flag;
    }

    //插入备件行明细的sql
    private boolean creatSpareD(Connection conn, DTOSet spDS) throws DataHandleException, CalendarException {
        List sqModels = new ArrayList();
        boolean flag = false;
        AmsItemTransDDTO spD = null;
        for (int i = 0; i < spDS.getSize(); i++) {
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            spD = (AmsItemTransDDTO) spDS.getDTO(i);
            String sqlStr = "INSERT INTO AMS_ITEM_TRANS_D\n" +
                    "  (TRANS_ID,LINE_ID,DETAIL_ID,ORGANIZATION_ID,BARCODE, QUANTITY,CONFIRM_QUANTITY, SERIAL_NO,TROUBLE_REASON,TROUBLE_LOC)\n" +
                    " VALUES\n" +
                    "  (?,?,AMS_ITEM_TRANS_D_S.NEXTVAL,?,?,?,?,?,?,?)";
            sqlArgs.add(spD.getTransId());
            sqlArgs.add(spD.getLineId());
            sqlArgs.add(spD.getOrganizationId());
            sqlArgs.add(spD.getBarcode());
            sqlArgs.add(spD.getQuantity());
            sqlArgs.add(spD.getConfirmQuantity());
            sqlArgs.add(spD.getSerialNo());
            sqlArgs.add(spD.getTroubleReason());
            sqlArgs.add(spD.getTroubleLoc());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            sqModels.add(sqlModel);
        }
        flag = DBOperator.updateBatchRecords(sqModels, conn);
        return flag;
    }


    private String getTransId(Connection conn, String lineId) throws QueryException, ContainerException {
        String transId = "";
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AITL.TRANS_ID,AITL.LINE_ID FROM AMS_ITEM_TRANS_L AITL WHERE AITL.LINE_ID = ?";
        sqlArgs.add(lineId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            Row row = sq.getFirstRow();
            transId = row.getValue("TRANS_ID").toString();
        }
        return transId;
    }


    private String getTransIdH(Connection conn, String TransNo) throws QueryException, ContainerException {
        String transId = "";
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AITH.TRANS_ID FROM AMS_ITEM_TRANS_H AITH WHERE AITH.TRANS_NO = ?";
        sqlArgs.add(TransNo);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            Row row = sq.getFirstRow();
            transId = row.getValue("TRANS_ID").toString();
        }
        return transId;
    }




    private String getOrgId(Connection conn, String transNO) throws QueryException, ContainerException {
        String orgId = "";
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT AITH.FROM_ORGANIZATION_ID\n" +
                        "  FROM AMS_ITEM_TRANS_H AITH\n" +
                        " WHERE AITH.TRANS_NO = ?";
        sqlArgs.add(transNO);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            Row row = sq.getFirstRow();
            orgId = row.getValue("FROM_ORGANIZATION_ID").toString();
        }
        return orgId;
    }

    /**
     * 功能：执行提交操作；
     * @param conn
     * @param spDS
     * @return
     * @throws DataHandleException
     * @throws CalendarException
     * @throws QueryException
     */
    //执行提交操作
    private boolean submitSpareD(Connection conn, DTOSet spDS) throws DataHandleException, CalendarException, QueryException {
//        List sqModels = new ArrayList();
        boolean flag = true;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsItemTransDDTO spD = null;
            for (int i = 0; i < spDS.getSize(); i++) {
                spD = (AmsItemTransDDTO) spDS.getDTO(i);
                if (i == 0) {
                    if (doVeifTransId(conn, spD.getTransId())) {
                        deletePartNO(conn, spD.getTransId());
                    }
                }
                insertTransH(conn, spD);
                updateStrus(conn, spD.getTransId());
            }
            flag = false;
        } catch (SQLException ex) {
            Logger.logError(ex.toString());
        } finally {
            try {
                if (flag) {
                    conn.rollback();
                } else {
                    conn.commit();
                    conn.setAutoCommit(autoCommit);
                }
            } catch (SQLException ex) {
                Logger.logError(ex.toString());
            }
        }
        return flag;
    }

    private void insertTransH(Connection conn, AmsItemTransDDTO spD) throws DataHandleException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_ITEM_TRANS_D\n" +
                "  (TRANS_ID,LINE_ID,DETAIL_ID,ORGANIZATION_ID,BARCODE, QUANTITY,CONFIRM_QUANTITY, SERIAL_NO,TROUBLE_REASON,TROUBLE_LOC)\n" +
                " VALUES\n" +
                "  (?,?,AMS_ITEM_TRANS_D_S.NEXTVAL,?,?,?,?,?,?,?)";
        sqlArgs.add(spD.getTransId());
        sqlArgs.add(spD.getLineId());
        sqlArgs.add(spD.getOrganizationId());
        sqlArgs.add(spD.getBarcode());
        sqlArgs.add(spD.getQuantity());
        sqlArgs.add(spD.getConfirmQuantity());
        sqlArgs.add(spD.getSerialNo());
        sqlArgs.add(spD.getTroubleReason());
        sqlArgs.add(spD.getTroubleLoc());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DBOperator.updateRecord(sqlModel, conn);
    }


 private boolean doVeifTransId(Connection conn, String transId) throws QueryException {
        boolean has = false;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String str = "SELECT 1 FROM AMS_ITEM_TRANS_D AITD WHERE AITD.TRANS_ID = ?";
        sqlArgs.add(transId);
        sqlModel.setSqlStr(str);
        sqlModel.setArgs(sqlArgs);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            has = true;
        }
        return has;
    }


    private void deletePartNO(Connection conn, String transId) throws QueryException, DataHandleException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM AMS_ITEM_TRANS_D WHERE TRANS_ID = ?";
        sqlArgs.add(transId);
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        DBOperator.updateRecord(sqlModel, conn);
    }

    private void updateStrus(Connection conn, String transId) throws DataHandleException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_ITEM_TRANS_H\n" +
                "   SET TRANS_STATUS = ?\n" +
                " WHERE TRANS_ID = ?";
        sqlArgs.add("SCANED");
        sqlArgs.add(transId);
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        DBOperator.updateRecord(sqlModel, conn);
    }
}
