package com.sino.ams.system.rent.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.rent.dao.RentDAO;
import com.sino.ams.system.rent.dto.RentDTO;
import com.sino.ams.system.rent.model.RentModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: AmsAssetsPriviServlet</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsPriviServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class RentPriviQueryServlet extends BaseServlet {


	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			RentDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(RentDTO.class.getName());
			dtoParameter = (RentDTO)req2DTO.getDTO(req);
			conn = getDBConnection(req);
			RentDAO rentDAO = new RentDAO(user, dtoParameter, conn);
            OptionProducer op = new OptionProducer(user, conn);
            String cityOption1 = op.getAllOrganization(0,true);
            req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption1);
//            OptionProducer prd = new OptionProducer(user, conn);
            if (action.equals("")) {
                req.setAttribute("RENT_QUERY_DTO", dtoParameter);
                forwardURL = "/system/rent/rentPriviSearch.jsp";
//                forwardURL = URLDefineList.RENT_SEARCH;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new RentModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);           //
                pageDAO.produceWebData();
                req.setAttribute("RENT_QUERY_DTO", dtoParameter);
                forwardURL = "/system/rent/rentPriviSearch.jsp";
//                forwardURL = URLDefineList.RENT_SEARCH;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
                dtoParameter = new RentDTO();
                req.setAttribute(WebAttrConstant.RENT_DTO, dtoParameter);
				forwardURL = URLDefineList.RENT_INFO;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                rentDAO.setDTOClassName(RentDTO.class.getName());
                RentDTO rentInfo = (RentDTO)rentDAO.getDataByPrimaryKey();
                rentInfo.setCalPattern(CalendarConstant.LINE_PATTERN);   //
                if(rentInfo == null){
					rentInfo = new RentDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
                req.setAttribute(WebAttrConstant.RENT_DTO, rentInfo);
				forwardURL = URLDefineList.RENT_INFO;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {   //do_save 操作
                rentDAO.createData();
				message = rentDAO.getMessage();
//				message.setIsError(!operateResult);
//				System.out.println(message);
//                    System.out.println(message);
                    req.setAttribute(WebAttrConstant.RENT_DTO, dtoParameter);
					forwardURL = URLDefineList.RENT_SEARCH;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {       //修改操作
//              rentDAO.updateData();
                rentDAO.updateDatT(conn);
                message = rentDAO.getMessage();
//				message.setIsError(!operateResult);
					forwardURL = URLDefineList.RENT_SEARCH;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				rentDAO.deleteData();
				message = rentDAO.getMessage();
//				message.setIsError(!operateResult);
				forwardURL =URLDefineList.RENT_SEARCH;
            }else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
                File file = rentDAO.exportFile();
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
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException e) {
             e.printLog();
        } catch (SQLException e) {
             Logger.logError(e);
        } catch (SQLModelException e) {
             Logger.logError(e);
        } finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
              if (!forwardURL.equals("")) {
            forwarder.forwardView(forwardURL);
              }
            //根据实际情况修改页面跳转代码。
		}
	}
}
