package com.sino.ams.newasset.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsAssetsCheckBatchDAO;
import com.sino.ams.newasset.dao.AmsAssetsCheckHeaderDAO;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.model.AmsAssetsCheckBatchModel;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: BatchQueryServlet</p>
 * <p>Description:程序自动生成服务程序“BatchQueryServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class BatchQueryServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsCheckBatchDTO.class.getName());
            AmsAssetsCheckBatchDTO dtoParameter = (AmsAssetsCheckBatchDTO)
                                                  req2DTO.getDTO(req);
            FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            AmsAssetsCheckBatchDAO batchDAO = new AmsAssetsCheckBatchDAO(user,
                    dtoParameter, conn);
            if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = AssetsURLList.BATCH_QRY_PAGE;
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsAssetsCheckBatchModel(user,
                        dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.addDbField("BATCH_ID");
                pageDAO.setWebCheckProp(checkProp);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = AssetsURLList.BATCH_QRY_PAGE;
            } else if (action.equals(AssetsActionConstant.NEW_ACTION)) {
                AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) req.
                                             getAttribute(AssetsWebAttributes.
                        CHECK_BATCH_DATA);
                DTOSet chkOrders = getCheckOrders(req, req2DTO);
                if (dto == null) {
                    dto = dtoParameter;
                    dto = fillData(user, dto, conn);
                }
                req.setAttribute(AssetsWebAttributes.CHECK_BATCH_DATA, dto);
                if (chkOrders != null) {
                    req.setAttribute(AssetsWebAttributes.CHECK_HEADER_DATAS,
                                     chkOrders);
                }
                forwardURL = AssetsURLList.BATCH_EDIT_PAGE;
            } else if (action.equals(AssetsActionConstant.EDIT_ACTION)) {
                batchDAO.setDTOClassName(AmsAssetsCheckBatchDTO.class.getName());
                batchDAO.setCalPattern(LINE_PATTERN);
                AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) batchDAO.
                                             getDataByPrimaryKey();
                if (dto == null) {
                    dto = dtoParameter;
                    dto = fillData(user, dto, conn);
                    message = getMessage(AssetsMessageKeys.DATA_NOT_EXIST);
                } else {
                    dto = fillDept2DTO(user, dto, conn);
                    AmsAssetsCheckHeaderDTO orderDTO = new
                            AmsAssetsCheckHeaderDTO();
                    orderDTO.setBatchId(dtoParameter.getBatchId());
                    AmsAssetsCheckHeaderDAO orderDAO = new
                            AmsAssetsCheckHeaderDAO(user, orderDTO, conn);
                    orderDAO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.
                                             getName());
                    orderDAO.setCalPattern(LINE_PATTERN);
                    DTOSet orders = (DTOSet) orderDAO.getDataByForeignKey(
                            "batchId");
                    req.setAttribute(AssetsWebAttributes.CHECK_HEADER_DATAS,
                                     orders);
                }
                req.setAttribute(AssetsWebAttributes.CHECK_BATCH_DATA, dto);
                forwardURL = AssetsURLList.BATCH_EDIT_PAGE;
            } else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
                batchDAO.setDTOClassName(AmsAssetsCheckBatchDTO.class.getName());
                batchDAO.setCalPattern(LINE_PATTERN);
                AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) batchDAO.
                                             getDataByPrimaryKey();
                if (dto == null) {
                    dto = dtoParameter;
                    dto = fillData(user, dto, conn);
                    message = getMessage(AssetsMessageKeys.DATA_NOT_EXIST);
                } else {
                    dto = fillDept2DTO(user, dto, conn);
                    AmsAssetsCheckHeaderDTO orderDTO = new
                            AmsAssetsCheckHeaderDTO();
                    orderDTO.setBatchId(dtoParameter.getBatchId());
                    AmsAssetsCheckHeaderDAO orderDAO = new
                            AmsAssetsCheckHeaderDAO(user, orderDTO, conn);
                    orderDAO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.
                                             getName());
                    orderDAO.setCalPattern(LINE_PATTERN);
                    DTOSet orders = (DTOSet) orderDAO.getDataByForeignKey(
                            "batchId");
                    req.setAttribute(AssetsWebAttributes.CHECK_HEADER_DATAS,
                                     orders);
                }
                req.setAttribute(AssetsWebAttributes.CHECK_BATCH_DATA, dto);
                forwardURL = AssetsURLList.BATCH_DETAIL_PAGE;
//            } else if (action.equals(AssetsActionConstant.SAVE_ACTION)) {
//                dtoParameter.setBatchStatus(AssetsDictConstant.SAVE_TEMP);
//                batchDAO.setDTOParameter(dtoParameter);
//                DTOSet chkOrders = getCheckOrders(req, req2DTO);
//                boolean operateResult = batchDAO.saveCheckOrders(chkOrders,
//                        flowDTO);
//                message = batchDAO.getMessage();
//                if (operateResult) {
//                    dtoParameter = (AmsAssetsCheckBatchDTO) batchDAO.
//                                   getDTOParameter();
//                    dtoParameter = fillData(user, dtoParameter, conn);
//                    forwardURL = AssetsURLList.CHECK_BATC_SERVLET;
//                    forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
//                    forwardURL += "&batchId=" + dtoParameter.getBatchId();
//                } else {
//                    dtoParameter = fillData(user, dtoParameter, conn);
//                    req.setAttribute(AssetsWebAttributes.CHECK_BATCH_DATA,
//                                     dtoParameter);
//                    forwardURL = AssetsURLList.CHECK_BATC_SERVLET;
//                    forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
//                }
//            } else if (action.equals(AssetsActionConstant.SUBMIT_ACTION)) {
//                dtoParameter.setBatchStatus(AssetsDictConstant.IN_PROCESS);
//                batchDAO.setDTOParameter(dtoParameter);
//                DTOSet chkOrders = getCheckOrders(req, req2DTO);
//                boolean operateResult = batchDAO.submitCheckOrders(chkOrders,
//                        flowDTO);
//                message = batchDAO.getMessage();
//                if (operateResult) {
//                    dtoParameter = (AmsAssetsCheckBatchDTO) batchDAO.
//                                   getDTOParameter();
//                    dtoParameter = fillData(user, dtoParameter, conn);
//                    forwardURL = AssetsURLList.CHECK_BATC_SERVLET;
//                    forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
//                    forwardURL += "&batchId=" + dtoParameter.getBatchId();
//                } else {
//                    dtoParameter = fillData(user, dtoParameter, conn);
//                    req.setAttribute(AssetsWebAttributes.CHECK_BATCH_DATA,
//                                     dtoParameter);
//                    forwardURL = AssetsURLList.CHECK_BATC_SERVLET;
//                    forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
//                }
//			} else if (action.equals(AssetsActionConstant.DISTRIBUTE_ACTION)) {
//				dtoParameter.setBatchStatus(AssetsDictConstant.CHK_STATUS_DISTRUIBUTED);
//				batchDAO.setDTOParameter(dtoParameter);
//				batchDAO.distributeChkOrder();
//				dtoParameter = (AmsAssetsCheckBatchDTO)batchDAO.getDTOParameter();
//				message = batchDAO.getMessage();
//				forwardURL = AssetsURLList.CHECK_BATC_SERVLET;
//				forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
//				forwardURL += "&batchId=" + dtoParameter.getBatchId();
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
        } catch (StrException ex) {
            ex.printLog();
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

    /**
     * 对新建时的DTO数据进行补充
     * @param user SfUserDTO
     * @param dto AmsAssetsCheckBatchDTO
     * @param conn Connection
     * @return AmsAssetsCheckBatchDTO
     * @throws DTOException
     */
    private AmsAssetsCheckBatchDTO fillData(SfUserDTO user,
                                            AmsAssetsCheckBatchDTO dto,
                                            Connection conn) throws
            DTOException {
        try {
            dto.setCreatedBy(user.getUserId());
            dto.setCreatedUser(user.getUsername());
            dto.setOrganizationId(user.getOrganizationId());
            dto.setCompanyName(user.getCompany());
            dto.setCurrCreationDate();
            dto.setCalPattern(LINE_PATTERN);
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String deptOptions = optProducer.getUserAsssetsDeptOption(StrUtil.nullToString(dto.
                    getCheckDept()));
            dto.setCheckDeptOption(deptOptions);
            DTOSet assetsGroups = user.getUserGroups();
            if (assetsGroups.getSize() == 1) {
                SfGroupDTO sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
                dto.setGroupId(sfGRoup.getGroupId());
                dto.setGroupName(sfGRoup.getGroupname());
            }
        } catch (QueryException ex) {
            ex.printLog();
            throw new DTOException(ex);
        }
        return dto;
    }

    /**
     * 功能：对DTO数据构造盘点部门下拉列表
     * @param user SfUserDTO
     * @param dto AmsAssetsCheckBatchDTO
     * @param conn Connection
     * @return AmsAssetsCheckBatchDTO
     * @throws QueryException
     */
    private AmsAssetsCheckBatchDTO fillDept2DTO(SfUserDTO user,
                                                AmsAssetsCheckBatchDTO dto,
                                                Connection conn) throws
            QueryException {
        AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
        String deptOptions = optProducer.getUserAsssetsDeptOption(StrUtil.nullToString(dto.
                getCheckDept()));
        dto.setCheckDeptOption(deptOptions);
        return dto;
    }

    /**
     * 功能：构造盘点工单
     * @param req HttpServletRequest
     * @param req2DTO Request2DTO
     * @return DTOSet
     * @throws DTOException
     */
    private DTOSet getCheckOrders(HttpServletRequest req, Request2DTO req2DTO) throws
            DTOException {
        DTOSet orders = new DTOSet();
        req2DTO.setIgnoreFields(AmsAssetsCheckBatchDTO.class);
        req2DTO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
        orders = req2DTO.getDTOSet(req);
        return orders;
    }
}
