package com.sino.ams.system.manydimensions.servlet;

import com.sino.ams.constant.ResNameConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.manydimensions.dao.ManyDimensionsDAO1;
import com.sino.ams.system.manydimensions.model.ManyDimensionsModel1;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.util.ResUtil;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.WebGrid;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.PrintWriter;


/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-8-18
 * Time: 11:28:17
 * To change this template use File | Settings | File Templates.
 */
public class ManyDimensionsServlet1 extends BaseServlet {
	
    private final static String AJAX_RES_TXT = "1";
    private final static String AJAX_RES_HTM = "2";
    private final static String AJAX_RES_XML = "3";

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
//            conn= DBManager.getDBConnection(Connection.TRANSACTION_READ_UNCOMMITTED);
			
			//String dept = StrUtil.isEmpty(req.getParameter("dept")) ? user.getDeptCode() : req.getParameter("dept");
			String dept = req.getParameter("dept");
			
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
			String deptOptions = optProducer.getUserAsssetsDeptOption2(dto.getResponsibilityDept());
		    req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOptions);
		    
//			String userOptions = optProducer.getUserByDeptOption(dto.getResponsibilityUser(), dept);
//		    req.setAttribute(AssetsWebAttributes.USER_OPTIONS, userOptions);
		    
            ManyDimensionsDAO1 rptDAO = new ManyDimensionsDAO1(user, dto, conn);
            String[] manyDimensionIsNull = new String[1];
             
			if (action.equals("")) {
				ResUtil.setAllResName(conn, req, ResNameConstant.MANY_DIMENSIONS );
				
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
                req.setAttribute("MANYDIMENTSON", manyDimensionIsNull);
				forwardURL = AssetsURLList.MANY_DIMENSION_PAGE;
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				//栏目定义标头
				ResUtil.setAllResName(conn, req, ResNameConstant.MANY_DIMENSIONS );
	            
                manyDimensionIsNull = req.getParameterValues("manyDimensionsIsNull");
                req.setAttribute("MANYDIMENTSON", manyDimensionIsNull);
                if (manyDimensionIsNull != null) {
                    String manyDimensionsValue = manyDimensionIsNull[0];
                    dto.setManyDimensionsValue(manyDimensionsValue);
                }
				BaseSQLProducer sqlProducer = new ManyDimensionsModel1(user, dto);
//				WebGrid wg = new WebGrid( sqlProducer.getPageQueryModel() , conn, req  );
//				wg.set
//				wg.executeQuery(); 
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setPageSize(20);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("BARCODE");
                pageDAO.setWebCheckProp(checkProp);
//                pageDAO.setTotalSummary( true );
//                pageDAO.setPageSummary( true );
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = AssetsURLList.MANY_DIMENSION_PAGE;
			} else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
				
                manyDimensionIsNull = req.getParameterValues("manyDimensionsIsNull");
                req.setAttribute("MANYDIMENTSON", manyDimensionIsNull);
                if (manyDimensionIsNull != null) {
                    String manyDimensionsValue = manyDimensionIsNull[0];
                    dto.setManyDimensionsValue(manyDimensionsValue);
                }
                
				String excelType = req.getParameter("excelType");
				File file = rptDAO.getExportFile(excelType);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(AssetsActionConstant.UPDATE_ACTION)) {
				RequestParser parser = new RequestParser();
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.setIgnoreOtherField(true);
                parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] barcodes = parser.getParameterValues("barcode");
                String[] contentCodes = req.getParameterValues("contentCodeBar");
				String[] contentNames = req.getParameterValues("contentName");
				
				String[] oldContentCodes = req.getParameterValues("oldContentCode");
				
				String[] lneIds = req.getParameterValues("lneId");
				String[] cexIds = req.getParameterValues("cexId");
				String[] opeIds = req.getParameterValues("opeId");
				String[] nleIds = req.getParameterValues("nleId");
				rptDAO.updateItems(barcodes, contentCodes, contentNames, lneIds, cexIds, opeIds, nleIds, oldContentCodes);
				message = rptDAO.getMessage();
				forwardURL = "/servlet/com.sino.ams.system.manydimensions.servlet.ManyDimensionsServlet1";
				forwardURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
				forwardURL += "&dept=" + dept;
//			} else if (action.equals("CHOOSE_DEPT")) {
//			    userOptions = optProducer.getUserByDeptOption(dto.getResponsibilityUser(), dept);
//                processAjaxResponse(res, userOptions, AJAX_RES_HTM);
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataTransException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (StrException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (UploadException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (PoolException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (ContainerException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			if(!StrUtil.isEmpty(forwardURL)){
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
            throw new ServletException(ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
	
}