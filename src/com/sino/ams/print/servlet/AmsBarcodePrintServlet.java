package com.sino.ams.print.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.print.dao.AmsBarcodePrintDAO;
import com.sino.ams.print.dto.AmsBarcodePrintDTO;
import com.sino.ams.print.model.AmsBarcodePrintModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.CalendarUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: AmsBarcodePrintServlet</p>
 * <p>Description:程序自动生成服务程序“AmsBarcodePrintServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author Zyun
 * @version 1.0
 */


public class AmsBarcodePrintServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        String Mage = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;

        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsBarcodePrintDTO.class.getName());
            AmsBarcodePrintDTO dtoParameter = (AmsBarcodePrintDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            AmsBarcodePrintDAO amsBarcodePrintDAO = new AmsBarcodePrintDAO(user, dtoParameter, conn);

            OptionProducer prd = new OptionProducer(user,conn);
            String printGR = prd.getPrintGroup();
            req.setAttribute(WebAttrConstant.PRINT_OPTION, printGR);

            FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
            String sectionRigth = req.getParameter("sectionRight");
            if (action.equals("")) {
                dtoParameter.setApplyUser(user.getUserId());
//                amsBarcodePrint.setApproveDate(user.getUsername());
//                amsBarcodePrint.setToOrganizationId(user.getOrganizationId());
//                amsBarcodePrint.setToOrganizationName(user.getCompany());
                String applyGroup = req.getParameter("applyGroup");
                dtoParameter.setApplyDate(CalendarUtil.getCurrDate());
                dtoParameter.setStatus(0);     //状态：处理中
                dtoParameter.setApplyGroup(applyGroup);
                req.setAttribute(WebAttrConstant.BARCODE_PRINT_DTO, dtoParameter);
                forwardURL = URLDefineList.BARCODE_PRINT_INFO;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsBarcodePrintModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                 pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(WebAttrConstant.BARCODE_PRINT_DTO, dtoParameter);
                forwardURL = URLDefineList.BARCODE_PRINT_STATISTIC;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                AmsBarcodePrintDTO amsBarcodePrint = (AmsBarcodePrintDTO) req.getAttribute("获取因为失败而保持的数据，请根据实际情况修改");
                if (amsBarcodePrint == null) {
                    amsBarcodePrint = dtoParameter;//表示没有因失败而保持的数据，则产生默认的对象数据，数据由com.sino.ams.print.dto.AmsBarcodePrintDTO的构造函数确定
                }
                req.setAttribute(WebAttrConstant.BARCODE_PRINT_DTO, amsBarcodePrint);
                forwardURL = URLDefineList.BARCODE_PRINT_INFO;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {    //详细页面
                amsBarcodePrintDAO.setDTOClassName(AmsBarcodePrintDTO.class.getName());
                AmsBarcodePrintDTO amsBarcodePrint = (AmsBarcodePrintDTO) amsBarcodePrintDAO.getDataByPrimaryKey();
                if (amsBarcodePrint == null) {
                    amsBarcodePrint = new AmsBarcodePrintDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                amsBarcodePrint.setCalPattern(CalendarConstant.LINE_PATTERN);
                req.setAttribute(WebAttrConstant.BARCODE_PRINT_DTO, amsBarcodePrint);
                forwardURL = URLDefineList.BARCODE_PRINT_INFO;
            } else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {           //打印单据提交
                flowDTO.setSessionUserId(user.getUserId());

                flowDTO.setSessionUserName(user.getUsername());
                boolean operateResult= amsBarcodePrintDAO.submit(flowDTO);
//                amsBarcodePrintDAO.createData();
                dtoParameter.setFirst("2");
                req.setAttribute(WebAttrConstant.BARCODE_PRINT_DTO, dtoParameter);
                  if (operateResult) {
                    forwardURL = URLDefineList.BARCODE_PRINT_INFO +"?act=''";
                    Mage = "打印申请单据：" + dtoParameter.getBatchNo() + "已生成!";
                } else {
                   forwardURL = URLDefineList.BARCODE_PRINT_INFO;
                }
//                forwardURL = URLDefineList.BARCODE_PRINT_INFO;
            } else if (action.equals(WebActionConstant.APPROVE_ACTION)) {      //通过审批

                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                String ApproveUser =  String.valueOf(user.getUserId());

                boolean operateResult = amsBarcodePrintDAO.approveOrder(flowDTO, ApproveUser,sectionRigth);
                message = amsBarcodePrintDAO.getMessage();
                message.setIsError(!operateResult);
                 req.setAttribute(WebAttrConstant.BARCODE_PRINT_DTO, dtoParameter);
                if(operateResult){Mage="确认成功！";}else{Mage="确认失败！";}

//                 forwardURL = URLDefineList.BARCODE_PRINT_INFO;
            } else if (action.equals(WebActionConstant.REJECT_ACTION)) {       //拒绝审批
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_PREV);
                amsBarcodePrintDAO.reject(flowDTO);
                Mage = "申领单已退回!";
                req.setAttribute(WebAttrConstant.BARCODE_PRINT_DTO, dtoParameter);
                forwardURL = URLDefineList.BARCODE_PRINT_INFO;
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
                amsBarcodePrintDAO.updateData();
                forwardURL = URLDefineList.BARCODE_PRINT_INFO;
            } else if (action.equals(WebActionConstant.CHECK_ACTION)) {    //标签申领人选择组别
//                amsBarcodePrintDAO.deleteData();
                
                forwardURL = URLDefineList.CHOOSE_PRINT_GROUP;
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException ex) {
            ex.printLog();
            //请根据实际情况处理消息
            forwardURL = "保持界面录入的数据，返回到原页面，并显示上面给出的消息";
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CalendarException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
//            setHandleMessage(req, message);
//            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (Mage.equals("")) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            } else {
//                forwarder.forwardView("",Mage);
                res.setContentType("text/html;charset=GBK");
                PrintWriter out = res.getWriter();
                out.print("<script language=\"javascript\">\n");
                out.println("alert(\"" + Mage + "\");");
                out.println("window.close();\n");
                out.print("</script>");
            }
        }
    }
}