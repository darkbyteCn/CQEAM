package com.sino.ams.newasset.servlet;

import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.service.AssetsDiscardService;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class AssetsDiscardServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            conn = getDBConnection(req);
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
            AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) req2DTO.getDTO(req);
            ServletConfigDTO servletConfig = getServletConfig(req);
            dto.setServletConfig(servletConfig);
            String act = dto.getAct();
            AssetsDiscardService service = new AssetsDiscardService(user, dto, conn);
            if (act.equals(AMSActionConstant.DETAIL_ACTION)) {
                AmsAssetsTransHeaderDTO data = service.searchDataByPrimaryKey();
                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, data);
                forwardURL = AssetsURLList.ASSETS_DISCARD_PAGE;
            } else if (act.equals(AMSActionConstant.SAVE_ACTION)) {
                processDataLines(dto, req2DTO, req);
                service.processSave();
                dto = (AmsAssetsTransHeaderDTO) service.getDTOParameter();
                forwardURL = AssetsURLList.ASSETS_TRANS_SERVLET;
                forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                forwardURL += "&transId=" + dto.getTransId();
            } else if (act.equals(AMSActionConstant.SUBMIT_ACTION)) {
                processDataLines(dto, req2DTO, req);
                service.processSubmit();
                dto = (AmsAssetsTransHeaderDTO) service.getDTOParameter();
                forwardURL = AssetsURLList.ASSETS_TRANS_SERVLET;
                forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                forwardURL += "&transId=" + dto.getTransId();
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!StrUtil.isEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
        }
    }

    private void processDataLines(AmsAssetsTransHeaderDTO data,
                                  Request2DTO req2DTO,
                                  HttpServletRequest req) throws DTOException {
        req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
        req2DTO.setIgnoreFields(AmsAssetsTransHeaderDTO.class);
        DTOSet lines = req2DTO.getDTOSet(req);
        if (lines != null && !lines.isEmpty()) {
            int lineCount = lines.getSize();
            List<AmsAssetsTransLineDTO> listData = new ArrayList<AmsAssetsTransLineDTO>();
            for (int i = 0; i < lineCount; i++) {
                AmsAssetsTransLineDTO line = (AmsAssetsTransLineDTO) lines.getDTO(i);
                listData.add(line);
            }
            data.setLines(listData);
        }
    }
}
