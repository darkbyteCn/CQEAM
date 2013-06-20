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
import com.sino.base.db.util.SeqProducer;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.util.XmlUtil;
import org.jdom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-3-13
 * Time: 14:54:03
 * （非网络资产）
 */
public class BillCreate extends BaseServlet {
    private String conFilePath = "";
    private static final String m_sContentType = "text/xml; charset=GBK";
    private String categoryDesc = "";

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = null;
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            PrintWriter resout = res.getWriter();
            String sFile = "";
            String test = "";
            Logger.logInfo("PDA run CreateBill servlet begin....");
            RequestParser reqPar = new RequestParser();
			reqPar.transData(req);
            resout.println("<?xml version=\"1.0\" encoding=\"GB2312\" ?> ");
            resout.println("<CreateBill>");
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
                Element el=xml.getRootElement();

                if (!el.getName().equals("bill")) {
                    ClearBeforeCancel(resout, conFilePath, false, "can't get XML file child");
                    return;
                }
                String batch_id = "";
                String createdBy = "";
                String objectNo = "";
                String organizationId  = "";
                String bill_type  = "";
                String groupId  = "";
                batch_id = xml.getElementAttrValue(el, "batch_id");
                System.out.println("batch_id is ;"+ batch_id);
                bill_type = xml.getElementAttrValue(el, "bill_type");
                System.out.println("bill_type is ;"+ bill_type);
                groupId = xml.getElementAttrValue(el, "groupId");
                System.out.println("groupId is ;"+ groupId);
                createdBy = xml.getElementAttrValue(el, "createdBy");
                System.out.println("createdBy is ;"+ createdBy);
                objectNo = xml.getElementAttrValue(el, "objectNo");   //盘点地点
                System.out.println("objectNo is ;"+ objectNo);
                organizationId  = xml.getElementAttrValue(el, "organizationId");
                System.out.println("organizationId  is ;"+ organizationId );

                SfUserDTO userAccount=  PDAUtil.getUserInfo(conn,createdBy);
                String companyCode = userAccount.getCompanyCode();
                OrderNumGenerator numberProducer = new OrderNumGenerator(conn, companyCode, "YQPD");
                String userId = getUserId(conn, createdBy);
                String  orderNo = numberProducer.getOrderNum();

                CreateBill(conn,batch_id,bill_type,groupId,userId,objectNo,organizationId,orderNo);
                
                setResultValue(resout, true, "", orderNo);
            } else {
                ClearBeforeCancel(resout, conFilePath, false, "can't find xml file");
            }
        } catch (PoolException e) {
            Logger.logError(e);
        } catch (ContainerException e) {
            e.printStackTrace();
        } catch (UploadException ex) {
			ex.printLog();
		} catch (FileSizeException ex) {
			ex.printLog();
//		} catch (DataHandleException e) {
//            e.printStackTrace();
        } catch (QueryException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }



    private void setResultValue(PrintWriter out, boolean b_flag, String errMsg, String id ) {
        out.println("<result message=\"" + errMsg + "\" >"
                + String.valueOf(b_flag)
                + "</result>");
        out.println("<bill   id=\"" + id  + "\" />");
        out.println("</CreateBill>");
        out.close();
    }

    /**
     * 功能；插盘点 批 表(EAM)
     * @return
     * @throws DataHandleException
     */

    private boolean CreateBatch(Connection conn, String batch_id ,String userId,String organizationId,String groupId) throws DataHandleException {
        boolean flag = false;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_CHK_BILL_BATCH\n" +
                "  (BATCH_ID,BATCH_NO,ORGANIZATION_ID,BATCH_STATUS,GROUP_ID,CREATION_DATE,CREATED_BY)\n" +
                " VALUES\n" +
                "  (?,?,?,?,?,GETDATE(),?)";
        sqlArgs.add(batch_id);
        sqlArgs.add("");
//        sqlArgs.add(objectNo);
        sqlArgs.add(organizationId);
        sqlArgs.add("10"); //新增
        sqlArgs.add(groupId);
        sqlArgs.add(userId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        flag = DBOperator.updateRecord(sqlModel, conn);
        return flag;
    }

    /**
     * 功能;插表盘点 头 表(EAM)
     * @return
     * @throws DataHandleException
     */
    private boolean CreateHead(Connection conn,String orderNo, String batch_id ,String userId,String objectNo,String organizationId,String bill_type,String groupId) throws DataHandleException {
        boolean flag = false;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_CHK_BILL_HEAD\n" +
                "  (HEADER_ID,BATCH_ID,BILL_NO,BILL_TYPE,BILL_STATUS,ORGANIZATION_ID,CHECK_LOCATION,GROUP_ID,CREATION_DATE,CREATED_BY)\n" +
                " VALUES\n" +
                "  (AMS_CHK_BILL_HEAD_S.NEXTVAL,?,?,?,?,?,?,?,GETDATE(),?)";
//        sqlArgs.add(orderNo);
        sqlArgs.add(batch_id);
//        sqlArgs.add(objectNo);
        sqlArgs.add(orderNo);  //单据号
        sqlArgs.add(bill_type);
        sqlArgs.add("0");   //默认
        sqlArgs.add(organizationId);
        sqlArgs.add(objectNo);
        sqlArgs.add(groupId);
        sqlArgs.add(userId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        flag = DBOperator.updateRecord(sqlModel, conn);
        return flag;
    }


    private void ClearBeforeCancel(PrintWriter resout, String conFilePath, boolean flag, String msg) {
        setResultValue(resout, flag, msg, "");
        PDAUtil.clearDir(conFilePath);
    }

   private String  getUserId(Connection conn,String loginName) throws QueryException, ContainerException {
     String userId = "";
     SQLModel sqlModel = new SQLModel();
     List sqlArgs = new  ArrayList();
     String  sqlStr = "SELECT SU.USER_ID FROM SF_USER SU WHERE SU.LOGIN_NAME = ?" ;
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
     * 功能：非网路资产创建数据。
     * @return boolean
     */                         //batch_id 批号,bill_type 单据类型,groupId,userId,objectNo 盘点地点,organizationId,orderNo 单据号
    public boolean CreateBill(Connection conn,String batch_id,String bill_type,String groupId,String userId,String objectNo,String organizationId,String orderNo){
        boolean operateResult = false;
        boolean autoCommit = false;
        boolean hasError = true;
        try {
                autoCommit = conn.getAutoCommit();
                conn.setAutoCommit(false);
                String batchId= getNextBatchId(conn);
                if(StrUtil.isEmpty(batch_id)){
//                    batch_id = batchId;
                    CreateBatch(conn,batchId,userId,organizationId,groupId);
                }
                CreateHead(conn,orderNo,batchId,userId,objectNo,organizationId,bill_type,groupId);
                operateResult = true;
                conn.commit();
                hasError = false;
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            ex.printLog();
        } finally{
            try {
                if(hasError){
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
        return operateResult;
    }

    private String getNextBatchId(Connection conn) throws SQLException {
        SeqProducer seqProducer = new SeqProducer(conn);
        return StrUtil.nullToString(seqProducer.getStrNextSeq("AMS_CHK_BILL_BATCH_S"));
    }

}
