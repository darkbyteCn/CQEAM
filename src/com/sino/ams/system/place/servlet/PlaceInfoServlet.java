package com.sino.ams.system.place.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.place.dao.PlaceInfoDAO;
import com.sino.ams.system.place.dto.PlaceInfoDTO;
import com.sino.ams.system.place.model.PlaceInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class PlaceInfoServlet extends BaseServlet {
	private final static String AJAX_RES_TXT = "1";
    private final static String AJAX_RES_HTM = "2";
    private final static String AJAX_RES_XML = "3";
    
	/**
	 * 所有的Servlet都必须实现的方法。
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 * @todo Implement this com.sino.base.PubServlet method
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
				Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			String isTd = user.getIsTd();
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(PlaceInfoDTO.class.getName());
			PlaceInfoDTO dto = (PlaceInfoDTO) req2DTO.getDTO(req);
			if(isTd.equals("Y")){
				dto.setLoc1SetName(ResourceBundle.getBundle("config.app").getString("TD_LOC1_SET_NAME"));
			}else if(isTd.equals("N")){
				dto.setLoc1SetName(ResourceBundle.getBundle("config.app").getString("MIS_LOC1_SET_NAME"));
			}
			String action = dto.getAct();
			conn = getDBConnection(req);
			OptionProducer  op = new OptionProducer(user, conn);
			String cityOption1=op.getAllOrganizationCode(dto.getCompanyCode(), true);
			req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption1);
			PlaceInfoDAO placeDao=new PlaceInfoDAO(user, dto, conn);
			if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/system/place/placeInfoQuery.jsp";
			} else if (action.equals(AMSActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new PlaceInfoModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/system/place/placeInfoQuery.jsp";
			}
			else if (action.equals(AMSActionConstant.DETAIL_ACTION)) {
				int id=Integer.parseInt(req.getParameter("flexValueId"));
				dto.setFlexValueId(id);
				BaseSQLProducer model=new PlaceInfoModel(user, dto);
		    	SQLModel sqlModel = model.getPrimaryKeyDataModel();
		        PageQueryDAO pageDAO = new PageQueryDAO(req, conn, model);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData(); 
				forwardURL = "/system/place/placeInfoDetail.jsp";
			}
			else if (action.equals(AMSActionConstant.SAVE_ACTION)) {
				PlaceInfoModel model=new PlaceInfoModel(user, dto);
				SQLModel sqlModel=model.getDataUpdateModel(dto);
				DBOperator.updateRecord(sqlModel, conn);
				forwardURL = "/system/place/placeInfoQuery.jsp";
			}
            else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}

		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (DTOException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (QueryException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}    
		finally {
			DBManager.closeDBConnection(conn);
			if(!forwardURL.equals("")){
				setHandleMessage(req, message);
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
	
	/**
     * 功能：处理Ajax请求
     *
     * @param res        Http响应对象
     * @param resContent Http响应内容
     * @param resType    AJAX类型
     * @throws ServletException 响应Ajax出错时抛出该异常
     */
    private static void processAjaxResponse(HttpServletResponse res, Object resContent, String resType) throws ServletException {
        PrintWriter out = null;
        try {
            if (resContent != null) {
                resType = resType.toUpperCase();
                if (resType.equals(AJAX_RES_TXT)) {
                    res.setContentType("text/plain;charset=GBK");
                } else if (resType.equals(AJAX_RES_XML)) {
                    res.setContentType("text/xml;charset=GBK");
                } else if (resType.equals(AJAX_RES_HTM)) {
                    res.setContentType("text/html;charset=GBK");
                }
                out = res.getWriter();
                out.print(resContent);
            }
        } catch (IOException ex) {
            Logger.logError(ex);
            throw new ServletException(ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    
	private String[] getObjectNos(HttpServletRequest req) throws UploadException {
        String[] objectNos = null;
        try {
            RequestParser parser = new RequestParser();
            CheckBoxProp checkProp = new CheckBoxProp("subCheck");
            checkProp.setIgnoreOtherField(true);
            parser.setCheckBoxProp(checkProp);
            parser.transData(req);
            objectNos = parser.getParameterValues("workorderObjectNo");
        } catch (StrException ex) {
            ex.printLog();
            throw new UploadException(ex);
        }
        return objectNos;
    }
	
	
	public String getSimpleObjectCode(String objectNo ) throws DataHandleException{
		int startPos = objectNo.indexOf( "." );
		int endPos = objectNo.lastIndexOf( "." );
		if( startPos == endPos || startPos == -1 || endPos == -1 ){
			return objectNo;
		}else{
			return objectNo.substring( startPos + 1 , endPos );
		}
	}
}
