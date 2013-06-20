package com.sino.ams.freeflow;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Function			闲置资产统计
 * User: 李轶
 * Date: 2009-4-13
 * Time: 17:05:30
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsFreeServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
          String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
         try {
            AmsAssetsFreeDTO dtoParameter = null;
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsFreeDTO.class.getName());
            dtoParameter = (AmsAssetsFreeDTO) req2DTO.getDTO(req);
            conn= DBManager.getDBConnection();
            String orgOption = "";
            OptionProducer op = new OptionProducer(user, conn);
            int organizationId = StrUtil.isEmpty(dtoParameter.getOrganizationId()) ? user.getOrganizationId() : dtoParameter.getOrganizationId();
            if(action.equals("")){
                req.setAttribute(QueryConstant.QUERY_DTO,dtoParameter);
                if(dtoParameter.getAccessType() != null && dtoParameter.getAccessType().equals("ASS_REPORT")){
                	if(user.getOrganizationId()==82){
        				if(dtoParameter.getOrganizationId() ==0){
        					orgOption = op.getAllOrganization(0, true);
        				}else{
        					orgOption = op.getAllOrganization(organizationId, true);
        				}
        				req.setAttribute(WebAttrConstant.CITY_OPTION, orgOption);
        			}else{
        				orgOption = op.getOrganizationOpt(organizationId);
        			}
                	forwardURL= URLDefineList.ASSETS_FREE_REPORT_SEARCH;
                }else{
                	forwardURL= URLDefineList.ASSETS_FREE_SEARCH;
                }                
            } else if(action.equals(WebActionConstant.QUERY_ACTION)){
                BaseSQLProducer sqlProducer = new AmsAssetsFreeModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                 req.setAttribute(QueryConstant.QUERY_DTO,dtoParameter);
                 req.setAttribute(QueryConstant.QUERY_DTO,dtoParameter);
                 if(dtoParameter.getAccessType() != null && dtoParameter.getAccessType().equals("ASS_REPORT")){
                	 if("82".equals(user.getOrganizationId())){
         				if("".equals(dtoParameter.getOrganizationId())){
         					orgOption = op.getAllOrganization(0, true);
         				}else{
         					orgOption = op.getAllOrganization(organizationId, true);
         				}
         				req.setAttribute(WebAttrConstant.CITY_OPTION, orgOption);
         			}else{
         				orgOption = op.getOrganizationOpt(organizationId);
         			}
                 	forwardURL= URLDefineList.ASSETS_FREE_REPORT_SEARCH;
                 }else{
                 	forwardURL= URLDefineList.ASSETS_FREE_SEARCH;
                 } 
            }  else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
            	AmsAssetsFreeDAO aafrDAO = new AmsAssetsFreeDAO(user, dtoParameter, conn);
				File file = aafrDAO.getExportFile(dtoParameter);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else{
                message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL= MessageConstant.MSG_PRC_SERVLET;
        } catch (DataTransException e) {
			e.printStackTrace();
		} catch (WebFileDownException e) {
			e.printStackTrace();
		}  finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
