package com.sino.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.ResNameConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.dao.ItemInfoHistoryDAO;
import com.sino.ams.newasset.dto.ItemInfoHistoryDTO;
import com.sino.ams.newasset.model.ItemInfoHistoryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.model.SfUserModel;
import com.sino.ams.util.ResUtil;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: ItemInfoHistoryServlet</p>
 * <p>Description:程序自动生成服务程序“AmsItemInfoHistoryServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class ItemInfoHistoryServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(ItemInfoHistoryDTO.class.getName());
            ItemInfoHistoryDTO dto = (ItemInfoHistoryDTO) req2DTO.getDTO(req);
            String action = dto.getAct();
            conn = getDBConnection(req);
            ItemInfoHistoryDAO historyDAO = new ItemInfoHistoryDAO(user, dto, conn);
              
            if (action.equals("")) {
            	//TODO 2011-10-10
            	ResUtil.setAllResName(conn, req, ResNameConstant.ITEM_INFO_HISTORY );
            	
                produceWebComponent(user, dto, conn);
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = AssetsURLList.ITEM_HISTORY_PAGE;
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
            	//TODO 2011-10-10
            	ResUtil.setAllResName(conn, req, ResNameConstant.ITEM_INFO_HISTORY );
                
            	BaseSQLProducer sqlProducer = new ItemInfoHistoryModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                
                pageDAO.setDTOClassName(ItemInfoHistoryDTO.class.getName());
                //pageDAO.setCountPages(false);
                //pageDAO.setPageSize(22);
                
                pageDAO.produceWebData();
                
                produceWebComponent(user, dto, conn);
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = AssetsURLList.ITEM_HISTORY_PAGE;
            } else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
            	String excelType = req.getParameter("excelType");
                File file = historyDAO.getExportFile(excelType);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
        }
    }

    private void produceWebComponent(SfUserDTO user, ItemInfoHistoryDTO dto, Connection conn) throws QueryException {
        AssetsOptProducer optionProducer = new AssetsOptProducer(user, conn);

        String option = optionProducer.getDictOption("ITEM_TYPE", dto.getItemCategory());
        dto.setItemCategoryOption(option);

        option = optionProducer.getDictOption("ITEM_STATUS", dto.getItemStatus());
        dto.setItemStatusOption(option);

        option = optionProducer.getUserAsssetsDeptOption2(dto.getDeptCode());
        dto.setDeptOption(option);
    }
}
