package com.sino.ams.system.manydimensions.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.match.amselementmatch.dto.AmsElementMatchDTO;
import com.sino.ams.system.manydimensions.dao.NleDAO;
import com.sino.ams.system.manydimensions.model.NleModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * User: 李轶
 * Date: 2009-6-16
 * Time: 17:32:55
 * Function:		网络层次属性维护
 */
public class NleServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        String msg = "";
        String act = req.getParameter("act");
        act = StrUtil.nullToString(act);
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        
        try {
        	SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
        	Request2DTO req2DTO = new Request2DTO(); 
        	req2DTO.setDTOClassName(AmsElementMatchDTO.class.getName());
            AmsElementMatchDTO dto = (AmsElementMatchDTO) req2DTO.getDTO(req);
            
            req2DTO.setDTOClassName(SfUserDTO.class.getName());
            conn = DBManager.getDBConnection();	
            NleDAO dao = new NleDAO(user, dto, conn);
			
			req.setAttribute(WebAttrConstant.AMS_ELEMENT_MATCH_DTO, dto);
            if (act.equals("")) {
				forwardURL = URLDefineList.NLE_PAGE;				
            } else if (act.equals(WebActionConstant.QUERY_ACTION)) {
            	BaseSQLProducer sqlProducer = new NleModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
            	forwardURL = URLDefineList.NLE_PAGE;
            } else if (act.equals(WebActionConstant.DETAIL_ACTION)) {
            	
            	forwardURL = URLDefineList.NLE_DETAIL_PAGE;
            } else if (act.equals(WebActionConstant.DELETE_ACTION)) {
            	this.getAmsLneIds(req, dto);
            	dao.deleteData();
				message = dao.getMessage();
				message = getMessage(MsgKeyConstant.DELETE_DATA_SUCCESS);
				message.addParameterValue("网络层次属性");
            	forwardURL = URLDefineList.NLE_PAGE;
            } else if (act.equals(WebActionConstant.CREATE_ACTION)) {
            	dao.createData();
				message = dao.getMessage();
				message = getMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
				message.addParameterValue("网络层次属性");
				req.setAttribute(WebAttrConstant.BARCODE_RECEIVE_DTO, dto);
            	forwardURL = URLDefineList.NLE_PAGE;
            }  else if (act.equals(WebActionConstant.EXPORT_ACTION)) {      //导出记录
				File file = dao.exportFile();
				dao.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (act.equals(AMSActionConstant.VALIDATE_ACTION)) {                                          //验证barcode是否存在
				res.setContentType("text/html;charset=GBK");
				PrintWriter out = res.getWriter();			
				boolean isExist = dao.existObject();
				if (isExist) {
					out.print("Y");
				} else {
					out.print("N");
				}					
				out.close();
			} 
        } catch (PoolException e) {
            message.setIsError(true);
            Logger.logError(e);
        } catch (DTOException e) {
            message.setIsError(true);
            Logger.logError(e);
        } catch (QueryException e) {
			e.printStackTrace();
		} catch (DataHandleException e) {
			e.printStackTrace();
		} catch (WebFileDownException e) {
			e.printStackTrace();
		} catch (UploadException e) {
			e.printStackTrace();
		}finally {
            DBManager.closeDBConnection(conn);
            if (!msg.equals("")) {
                res.setContentType("text/html;charset=GBK");
                PrintWriter out = res.getWriter();
                out.print("<script language=\"javascript\">\n");
                out.println("alert(\"" + msg + "\");");
                out.println("window.close();\n");
                out.print("</script>");
            } else {
                ServletForwarder sforwarder = new ServletForwarder(req, res);
                setHandleMessage(req, message);
                sforwarder.forwardView(forwardURL);
            }
        }
    }
    
    private void getAmsLneIds(HttpServletRequest req, AmsElementMatchDTO dto) throws UploadException {
        try {
            CheckBoxProp checkProp = new CheckBoxProp("subCheck");
            checkProp.setIgnoreOtherField(true);
            RequestParser reqParser = new RequestParser();
            reqParser.setCheckBoxProp(checkProp);
            reqParser.transData(req);
            String[] exarr = reqParser.getParameterValues("amsLneId");
            if (exarr != null) {
                StringBuffer ids = new StringBuffer();
                for (int i = 0; i < exarr.length; i++) {
                    ids.append(exarr[i] + ",");
                }
                dto.setAmsLneId(ids.substring(0, ids.length()-1));
            }
        } catch (StrException ex) {
            ex.printLog();
            throw new UploadException(ex);
        } 
  }

}
