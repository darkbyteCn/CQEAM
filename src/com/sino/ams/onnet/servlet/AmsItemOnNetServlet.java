package com.sino.ams.onnet.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.onnet.dao.AmsItemOnNetDAO;
import com.sino.ams.onnet.dto.AmsItemOnNetDTO;
import com.sino.ams.onnet.model.AmsItemOnNetModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
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
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */

public class AmsItemOnNetServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
        String showMsg = "";
        Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsItemOnNetDTO.class.getName());
			AmsItemOnNetDTO dtoParameter = (AmsItemOnNetDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			AmsItemOnNetDAO amsItemOnNetDAO = new AmsItemOnNetDAO(user, dtoParameter, conn);
            OptionProducer  optProducer = new OptionProducer(user,conn);
            String cityOption = optProducer.getAllOrganization(dtoParameter.getOrganizationId(),true);
            req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
            String vendorOption = optProducer.getSpareVendorOption( String.valueOf(dtoParameter.getVendorId()));
            req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
            if (action.equals("")) {
                req.setAttribute(WebAttrConstant.ON_NET_DTO, dtoParameter);
                forwardURL = URLDefineList.ON_NET_SEARCH;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsItemOnNetModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				String count = amsItemOnNetDAO.getCount();
				req.setAttribute(WebAttrConstant.ON_NET_DTO, dtoParameter);
                req.setAttribute(WebAttrConstant.DIFF_COUNT, count);
                forwardURL = URLDefineList.ON_NET_SEARCH;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				forwardURL = URLDefineList.ON_NET_DETAIL;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				amsItemOnNetDAO.setDTOClassName(AmsItemOnNetDTO.class.getName());
				AmsItemOnNetDTO amsItemOnNet = (AmsItemOnNetDTO)amsItemOnNetDAO.getDataByPrimaryKey();
				if(amsItemOnNet == null){
					amsItemOnNet = new AmsItemOnNetDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.ON_NET_DTO, amsItemOnNet);
				forwardURL = URLDefineList.ON_NET_DETAIL;
            } else if (action.equals(WebActionConstant.CHECK_ACTION)) { //查询导入的表的操作
                AmsItemOnNetModel onNetModel = new AmsItemOnNetModel(user, dtoParameter);
                if(amsItemOnNetDAO.importHasError()){
                SQLModel sqlModel = onNetModel.getQueryImportModel();
                SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
                simpleQuery.executeQuery();
                RowSet rows = simpleQuery.getSearchResult();
                req.setAttribute(WebAttrConstant.ETS_SPARE_DTO, rows);
                }else{
                 amsItemOnNetDAO.getImpOnNetModel();  
               }
                amsItemOnNetDAO.deleteImportModel();
                forwardURL = URLDefineList.ON_NET_SEARCH;
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				amsItemOnNetDAO.createData();
                req.setAttribute(WebAttrConstant.ON_NET_DTO, dtoParameter);
                forwardURL = URLDefineList.ON_NET_SEARCH;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				amsItemOnNetDAO.updateData();
                req.setAttribute(WebAttrConstant.ON_NET_DTO, dtoParameter);
                forwardURL = URLDefineList.ON_NET_SEARCH;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				amsItemOnNetDAO.deleteData();
                req.setAttribute(WebAttrConstant.ON_NET_DTO, dtoParameter);
                forwardURL =URLDefineList.ON_NET_SEARCH;
            }else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出到Excel
                File file = amsItemOnNetDAO.exportFile();
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
			//请根据实际情况处理消息
			forwardURL = "保持界面录入的数据，返回到原页面，并显示上面给出的消息";
		} catch (WebFileDownException e) {
            e.printStackTrace();
        } catch (SQLModelException ex) {
           ex.printLog();
        }  catch (ContainerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			     if (!StrUtil.isEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                if(showMsg.equals("")){
                forwarder.forwardView(forwardURL);       }
                else{
                     forwarder.forwardOpenerView(forwardURL, showMsg);
                }
            }
            //根据实际情况修改页面跳转代码。
		}
	}
}