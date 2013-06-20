package com.sino.ams.newasset.servlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.freeflow.AmsAssetsFreeDTO;
import com.sino.ams.newasset.dao.AmsAssetsShareDAO;
import com.sino.ams.newasset.model.AmsAssetsShareModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Function :		查询的共享资产
 * @author liyi
 *
 */
public class AmsAssetsShareServlet extends BaseServlet {
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
            AmsAssetsShareDAO aasDAO = new AmsAssetsShareDAO(user, dtoParameter, conn);
            String shareStatusOpt = aasDAO.getAllShareStatus(dtoParameter.getShareStatus());
            req.setAttribute("SHARE_STATUS", shareStatusOpt);
            if(action.equals("")){
                req.setAttribute(QueryConstant.QUERY_DTO,dtoParameter);
                forwardURL= URLDefineList.ASSETS_SHARE_SEARCH;
            } else if(action.equals(WebActionConstant.QUERY_ACTION)){
                BaseSQLProducer sqlProducer = new AmsAssetsShareModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                 req.setAttribute(QueryConstant.QUERY_DTO,dtoParameter);
                forwardURL=URLDefineList.ASSETS_SHARE_SEARCH;
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)){
            	DTOSet dtos = this.getBarcodes(req);
            	aasDAO.getDistributeShareStatus(dtoParameter.getShareStatus(), dtos);
				message = aasDAO.getMessage();
				message = getMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
				message.addParameterValue("资产共享状态分配");
				forwardURL = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsShareServlet?act=" + WebActionConstant.QUERY_ACTION;
            }else{
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
        } catch (DataHandleException e) {
			e.printStackTrace();
		} catch (UploadException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
    
    private DTOSet getBarcodes(HttpServletRequest req) throws UploadException {
        DTOSet dtos = new DTOSet();
        try {
            CheckBoxProp checkProp = new CheckBoxProp("subCheck");
            checkProp.setIgnoreOtherField(true);
            RequestParser reqParser = new RequestParser();
            reqParser.setCheckBoxProp(checkProp);
            reqParser.transData(req);
            String[] exarr = reqParser.getParameterValues("barcode");
            if (exarr != null) {
            	AmsAssetsFreeDTO dto;
                String inarr;
                for (int i = 0; i < exarr.length; i++) {
                    inarr = exarr[i];
                    if(inarr != null && !inarr.equals("")){
                    	dto = new AmsAssetsFreeDTO();
                        dto.setBarcode(inarr);
                        dtos.addDTO(dto);
                    }
                    
                }
            }
        } catch (StrException ex) {
            ex.printLog();
            throw new UploadException(ex);
        } catch (DTOException ex) {
            ex.printLog();
            throw new UploadException(ex);
        }
        return dtos;
  }
}
