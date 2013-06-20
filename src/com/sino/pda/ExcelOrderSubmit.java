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

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDtlDTO;
import com.sino.ams.workorder.model.OrderExtendModel;
import com.sino.ams.workorder.model.WorkorderModel;
import com.sino.ams.workorder.util.WorkOrderUtil;
import com.sino.base.data.Row;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.FileSizeException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.UploadException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * User: zhoujs
 * Date: 2007-12-17
 * Time: 11:40:21
 * Function:Excel提交工单
 */
public class ExcelOrderSubmit extends BaseServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=GBK";
    private static int startRowNum = 1;
    private static int columnNum = 7;
    private boolean hasCommit = true;


    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String forwardURL = "";
        Message message = null;
        Connection conn = null;

        String showMsg = "";

        try {
            res.setContentType(CONTENT_TYPE);
            PrintWriter resout = res.getWriter();

            conn = DBManager.getDBConnection();
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);

            Logger.logInfo("Excel submit servlet begin....");
            RequestParser reqPar = new RequestParser();

            reqPar.transData(req);


            resout.println("<?xml version=\"1.0\" ?> ");
            resout.println("<SubmitExcelOrder>");

            UploadFile[] upFiles = null;
            UploadRow uploadRow;
            boolean returnFlag = false;


            String conFilePath = PDAUtil.getCurUploadFilePath(conn);

            UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
            uploadFileSaver.saveFiles(conFilePath);

            uploadRow = uploadFileSaver.getRow();
            upFiles = uploadRow.getFiles();

            if (upFiles == null) {
                returnFlag = true;
                setResultValue(resout, false, "upload file " + SyBaseSQLUtil.isNull() + " ", "");
            } else {
                if (upFiles.length == 1) {
                    if (upFiles[0].getFileName().equals("")) {
                        Logger.logError("can't find upload xml files");
                        returnFlag = true;
                        setResultValue(resout, false, "can't find upload  files,please check the file.", "");
                    }
                } else {
                    Logger.logError("can't find any xml file");
                    setResultValue(resout, false, "can't find any xml file", "");
                    returnFlag = true;
                }
            }
            if (returnFlag) {
                return;
            }

            UploadFile uploadFile = upFiles[0];
            String fileName = uploadFile.getAbsolutePath();
//=======================
//            conn = DBManager.getDBConnection();//不能重复获取数据库连接
            boolean autoCommit = conn.getAutoCommit();


            XlsOrderReader xlsUtil = new XlsOrderReader();
            xlsUtil.setFileName(fileName);
            xlsUtil.setNumberOfColumn(columnNum);
            xlsUtil.setStartRowNum(startRowNum);
            DTOSet dtoSet = xlsUtil.readXls(0);

            submitOrderDtl(dtoSet, conn);

            conn.commit();
            conn.setAutoCommit(autoCommit);

            forwardURL = "/workorder/order/uploadFromExcel.jsp";
            showMsg = "上传成功！";
        } catch (PoolException e) {
            forwardURL = URLDefineList.ERROR_PAGE;
            e.printStackTrace();
        } catch (DTOException e) {
            forwardURL = URLDefineList.ERROR_PAGE;
            e.printStackTrace();
        } catch (SQLException e) {
            forwardURL = URLDefineList.ERROR_PAGE;
            e.printStackTrace();
        } catch (DataHandleException e) {
            forwardURL = URLDefineList.ERROR_PAGE;
            e.printStackTrace();
        } catch (QueryException e) {
            forwardURL = URLDefineList.ERROR_PAGE;
            e.printStackTrace();
        } catch (ContainerException e) {
            forwardURL = URLDefineList.ERROR_PAGE;
            e.printStackTrace();
        } catch (UploadException e) {
            forwardURL = URLDefineList.ERROR_PAGE;
            e.printStackTrace();
        } catch (FileSizeException e) {
            forwardURL = URLDefineList.ERROR_PAGE;
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (showMsg.equals("")) {
                forwarder.forwardView(forwardURL);
            } else {
                forwarder.forwardView(forwardURL, showMsg);
            }
        }
    }

    /**
     * 提交工单
     * @param dtoSet
     * @param conn
     * @return
     * @throws QueryException
     * @throws ContainerException
     * @throws DataHandleException
     */
    private boolean submitOrderDtl(DTOSet dtoSet, Connection conn) throws QueryException, ContainerException, DataHandleException {
        boolean hasSubmit = false;
        boolean operatorResult = false;
        if (dtoSet != null && dtoSet.getSize() > 0) {
            String workorderNo = "";
            List sqlModelList = new ArrayList();
            OrderModel orderModel = new OrderModel();
            WorkorderModel workorderModel = new WorkorderModel();
            SQLModel sqlModel = new SQLModel();
            WorkOrderUtil orderUtil = new WorkOrderUtil();
            for (int i = 0; i < dtoSet.getSize(); i++) {
                hasSubmit =false;
                EtsWorkorderDtlDTO orderDtlDTO = (EtsWorkorderDtlDTO) dtoSet.getDTO(i);
                if (StrUtil.isEmpty(orderDtlDTO.getWorkorderNo())) {
                    continue;
                }
                if (workorderNo.equals("")||(!orderDtlDTO.getWorkorderNo().equals(workorderNo))){
                    hasSubmit = orderUtil.hasSubmit(orderDtlDTO.getWorkorderNo(), conn);
                }

                if (hasSubmit) {
                    Logger.logInfo("工单"+orderDtlDTO.getWorkorderNo()+"已上传！");
                    continue;
                }
                orderDtlDTO=enrichOrderDtl(conn,orderDtlDTO);

                /**
                 * 写dtl表和interface表
                 */
                sqlModel = orderModel.getInsertDtlModel(orderDtlDTO);
                DBOperator.updateRecord(sqlModel, conn);
                sqlModel = orderModel.getInsertInterfaceModel(orderDtlDTO);
                DBOperator.updateRecord(sqlModel, conn);
                /**
                 * 更新工单进度、工单状态
                 */
                if (!orderDtlDTO.getWorkorderNo().equals(workorderNo)) {
//                    if (!workorderNo.equals("")) {
                        sqlModel = orderModel.getUpdateUploadOrderModel(orderDtlDTO.getWorkorderNo(), "");
                        DBOperator.updateRecord(sqlModel, conn);
                        sqlModel = workorderModel.getUpdateOrderProcessModel(orderDtlDTO.getWorkorderNo(), DictConstant.WORKORDER_NODE_UPLODADED, true);
                        DBOperator.updateRecord(sqlModel, conn);
//                    }
                }
                workorderNo = orderDtlDTO.getWorkorderNo();
            }
        }

        operatorResult=true;
        return operatorResult;

    }

    private void setResultValue(PrintWriter out, boolean b_flag, String errMsg, String m_workorder_no) {
        out.println("<result message=\"" + errMsg + "\" >" + String.valueOf(b_flag) + "</result>");
        out.println("<workorder id=\"" + m_workorder_no + "\" />");
        out.println("</SubmitExcelOrder>");
        out.close();
    }

    /**
     *
     * @param conn
     * @param orderDtlDTO
     * @return
     */
    private EtsWorkorderDtlDTO enrichOrderDtl(Connection conn,EtsWorkorderDtlDTO orderDtlDTO) throws ContainerException, QueryException {
        OrderExtendModel orderExtendModel=new OrderExtendModel();
        SQLModel sqlModel=orderExtendModel.getAddressByOrderNo(orderDtlDTO.getWorkorderNo());
        SimpleQuery simpleQuery=new SimpleQuery(sqlModel,conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
            Row row=simpleQuery.getFirstRow();
            orderDtlDTO.setAddressId((Integer)row.getValue("ADDRESS_ID"));
            orderDtlDTO.setWorkorderObjectNo(row.getStrValue("WORKORDER_OBJECT_NO"));
            orderDtlDTO.setBoxNo(row.getStrValue("BOX_NO"));
            orderDtlDTO.setNetUnit(row.getStrValue("NET_UNIT"));
        }
        return orderDtlDTO;
    }
}
