package com.sino.nm.ams.mss.servlet;

import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.dto.Request2DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.exception.*;
import com.sino.base.db.conn.DBManager;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.nm.ams.mss.dao.MssItemInfoChangeDAO;
import com.sino.nm.ams.mss.model.MssItemInfoChangeModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.File;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_yuyao
 * Date: 2011-4-27
 * Time: 14:08:46
 * To change this template use File | Settings | File Templates.
 */
public class MssItemInfoChangeServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String msg="";
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            EtsItemInfoDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EtsItemInfoDTO.class.getName());
            dtoParameter = (EtsItemInfoDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            MssItemInfoChangeDAO etsItemMatchRecDAO = new MssItemInfoChangeDAO(user, dtoParameter, conn);
             	OptionProducer op = new OptionProducer(user, conn);
              	String allRoleOption = op.getDictOption("FINANCE_PROP",dtoParameter.getFinanceProp());
				req.setAttribute(WebAttrConstant.ALL_ROLE_OPTION, allRoleOption);
            if (action.equals("")) {
                    forwardURL = "/nm/mss/MssItemInfoChange.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new MssItemInfoChangeModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                 pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();


                    forwardURL = "/nm/mss/MssItemInfoChange.jsp";

            } else if (action.equals(AMSActionConstant.MATCH_ACTION)) {
                  DTOSet dtos = new DTOSet();
                String ms="";
                String[] systemIds = req.getParameterValues("systemId");
                String[] barcodes = req.getParameterValues("barcode");
                String []newResponsibilityDept=req.getParameterValues("newResponsibilityDeptName");
                String []newResponsibilityUser=req.getParameterValues("newResponsibilityUserName");
                String []newAddressId=req.getParameterValues("newAddressName");
                String []newMaintainUser=req.getParameterValues("newMaintainUser");
                String []itemCodes=req.getParameterValues("itemCode");
                ms = etsItemMatchRecDAO.confirmRentAssets(systemIds,newResponsibilityDept,newResponsibilityUser,newAddressId,newMaintainUser,barcodes,itemCodes);
                if(ms.equals("")){
                    forwardURL = "/servlet/com.sino.nm.ams.mss.servlet.MssItemInfoChangeServlet?act="+WebActionConstant.QUERY_ACTION ;
                    msg="实物信息变更成功！";

                }else{
                   forwardURL = "/servlet/com.sino.nm.ams.mss.servlet.MssItemInfoChangeServlet?act="+WebActionConstant.QUERY_ACTION ;
                    msg=ms+"实物信息变更未成功,请确认!";
                }

            }else if (action.equals("ENABLE")) {
                  DTOSet dtos = new DTOSet();
                String ms="";
                String[] systemIds = req.getParameterValues("systemId");
                String[] barcodes = req.getParameterValues("barcode");
                String []newResponsibilityDept=req.getParameterValues("newResponsibilityDeptName");
                String []newResponsibilityUser=req.getParameterValues("newResponsibilityUserName");
                String []newAddressId=req.getParameterValues("newAddressName");
                String []newMaintainUser=req.getParameterValues("newMaintainUser");
                String []itemCodes=req.getParameterValues("itemCode");
                ms = etsItemMatchRecDAO.enableAssets(systemIds,newResponsibilityDept,newResponsibilityUser,newAddressId,newMaintainUser,barcodes,itemCodes);
                if(ms.equals("")){
                    forwardURL = "/servlet/com.sino.nm.ams.mss.servlet.MssItemInfoChangeServlet?act="+WebActionConstant.QUERY_ACTION ;
                    msg="生效成功！";

                }else{
                   forwardURL = "/servlet/com.sino.nm.ams.mss.servlet.MssItemInfoChangeServlet?act="+WebActionConstant.QUERY_ACTION ;
                    msg=ms+"生效未成功,请确认!";
                }

            }else if (action.equals("DISABLE")) {
                  DTOSet dtos = new DTOSet();
                String ms="";
                String[] systemIds = req.getParameterValues("systemId");
                String[] barcodes = req.getParameterValues("barcode");
                String []newResponsibilityDept=req.getParameterValues("newResponsibilityDeptName");
                String []newResponsibilityUser=req.getParameterValues("newResponsibilityUserName");
                String []newAddressId=req.getParameterValues("newAddressName");
                String []newMaintainUser=req.getParameterValues("newMaintainUser");
                String []itemCodes=req.getParameterValues("itemCode");
                ms = etsItemMatchRecDAO.disableAssets(systemIds,newResponsibilityDept,newResponsibilityUser,newAddressId,newMaintainUser,barcodes,itemCodes);
                if(ms.equals("")){
                    forwardURL = "/servlet/com.sino.nm.ams.mss.servlet.MssItemInfoChangeServlet?act="+WebActionConstant.QUERY_ACTION ;
                    msg="失效成功！";

                }else{
                   forwardURL = "/servlet/com.sino.nm.ams.mss.servlet.MssItemInfoChangeServlet?act="+WebActionConstant.QUERY_ACTION ;
                    msg=ms+"失效未成功,请确认!";
                }

            }else if(action.equals(WebActionConstant.EXPORT_ACTION)){
              	File file = etsItemMatchRecDAO.exportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
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
        } catch (DataHandleException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
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
        } catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} /*catch(CalendarException e){
           e.printLog();
              message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        }*/catch(SQLModelException e){
            e.printLog();
              message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        }  finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (msg.equals("")) {
                    forwarder.forwardView(forwardURL);
                } else {
                    forwarder.forwardView(forwardURL, msg);
                }
            //根据实际情况修改页面跳转代码。
        }
    }

}