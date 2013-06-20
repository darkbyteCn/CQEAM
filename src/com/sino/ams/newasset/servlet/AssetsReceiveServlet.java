package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OrgOptionProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AssetsLineReceiveDAO;
import com.sino.ams.newasset.dao.AssetsReceiveDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.model.AssetsRcvHeaderModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AssetsReceiveServlet extends BaseServlet {

    /**
     * 所有的Servlet都必须实现的方法。
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
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
            dto.setServletConfig(getServletConfig(req));

            OrgOptionProducer op = new OrgOptionProducer(user, conn);
            String opp = op.getAllOrgnizationOption(StrUtil.nullToString(user.getOrganizationId()));
            req.setAttribute("FROM_COMPANY", opp);
            String action = dto.getAct();
            dto.setCalPattern(LINE_PATTERN);
            if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = AssetsURLList.ASSETS_RCV_QRY;
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                dto.setTransType(AssetsDictConstant.ASS_RED);
                dto.setTransStatus(AssetsDictConstant.APPROVED);
                BaseSQLProducer sqlProducer = new AssetsRcvHeaderModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = AssetsURLList.ASSETS_RCV_QRY;
            } else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) { //不可编辑的详细信息页面
                AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) req.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
                if (headerDTO == null) {
                    AssetsReceiveDAO receiveDAO = new AssetsReceiveDAO(user, dto, conn);
                    receiveDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
                    headerDTO = (AmsAssetsTransHeaderDTO) receiveDAO.getDataByPrimaryKey();
                    headerDTO.setCanReceive(receiveDAO.canReceiveOrder()); //判断该单据是否能接收，以便控制是否显示“接收”按钮
                    headerDTO.setCalPattern(LINE_PATTERN);
                }
                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                DTOSet dtos = (DTOSet) req.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
                if (dtos == null) {
                    AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
                    lineDTO.setTransId(headerDTO.getTransId());
                    AssetsLineReceiveDAO lineDAO = new AssetsLineReceiveDAO(user, lineDTO, conn);
                    lineDAO.setCalPattern(LINE_PATTERN);
                    lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                    dtos = (DTOSet) lineDAO.getDataByForeignKey("transId");
                }
                req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, dtos);
                forwardURL = AssetsURLList.RCV_DETAIL_PAGE;
            } else if (action.equals(AssetsActionConstant.ASSIGN_ACTION)) { //保存
                AssetsReceiveDAO receiveDAO = new AssetsReceiveDAO(user, dto, conn);
                req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                req2DTO.setIgnoreFields(AmsAssetsTransHeaderDTO.class);
                DTOSet dtos = req2DTO.getDTOSet(req);
                ServletConfigDTO servletConfig = getServletConfig(req);
                receiveDAO.setServletConfig(servletConfig);
                boolean operateResult = receiveDAO.assignTransAssets(dtos);
                if (!operateResult) {
                    dto.setCanReceive(!operateResult);
                    req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, dto);
                }
                message = receiveDAO.getMessage();
                if (servletConfig.isRcvProcEnabled()) { //加入接收审批流程，内蒙部门间调拨需要该流程
                    if (dto.getTransferType().equals(AssetsDictConstant.TRANS_BTW_DEPT)) {
                        forwardURL = AssetsURLList.RCV_HEADER_SERVLET;
                        forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                        forwardURL += "&receiveHeaderId=" + receiveDAO.getRcvHeaderId();
                    } else {
                        forwardURL = AssetsURLList.ASSETS_RCV_SERVLRT;
                        forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                        forwardURL += "&transId=" + dto.getTransId();
                        req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, dtos);
                    }
                } else {
                    forwardURL = AssetsURLList.ASSETS_RCV_SERVLRT;
                    forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                    forwardURL += "&transId=" + dto.getTransId();
                    req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, dtos);
                }
            } else {
                message = getMessage(AssetsMessageKeys.INVALID_REQ);
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
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
