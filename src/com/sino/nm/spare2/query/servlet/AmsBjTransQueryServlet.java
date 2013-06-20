package com.sino.nm.spare2.query.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.flow.bean.FlowExtend;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.nm.spare2.allot.dao.AmsBjsAllotDAO;
import com.sino.nm.spare2.allot.dto.AmsBjsAllotHDTO;
import com.sino.nm.spare2.dao.AmsItemTransLDAO;
import com.sino.nm.spare2.dao.BjghDAO;
import com.sino.nm.spare2.query.dao.AmsBjTransQueryDAO;
import com.sino.nm.spare2.query.model.AmsBjTransQueryModel;
import com.sino.nm.spare2.repair.dao.BjSendRepairDAO;
import com.sino.nm.spare2.returnBj.dao.BjReturnRepairDAO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author TOTTI
 *         Date: 2007-12-17
 */
public class AmsBjTransQueryServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String showMsg = "";
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AmsBjsAllotHDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsBjsAllotHDTO.class.getName());
            dtoParameter = (AmsBjsAllotHDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            AmsBjTransQueryDAO dao = new AmsBjTransQueryDAO(user, dtoParameter, conn);
            dao.setServletConfig(getServletConfig(req));
            dao.setCalPattern(CalendarConstant.LINE_PATTERN);
            OptionProducer op = new OptionProducer(user, conn);
            String transStaSelect = op.getDictOption("ORDER_STATUS", dtoParameter.getTransStatus());
            req.setAttribute(WebAttrConstant.TRANS_STATUS, transStaSelect);
            dtoParameter.setTransStatusOption(transStaSelect);
            String transTypeSelect = op.getDictOption("ORDER_TYPE_SPARE", dtoParameter.getTransType());
            req.setAttribute(WebAttrConstant.TRANS_TYPE, transTypeSelect);
            dtoParameter.setTransStatusOption(transTypeSelect);
            req.setAttribute("AMSBJTRANSNOHDTO", dtoParameter);

            if (action.equals("")) {
                forwardURL = "/nm/spare2/query/bjTransQuery.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsBjTransQueryModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                forwardURL = "/nm/spare2/query/bjTransQuery.jsp";
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                String transId = req.getParameter("transId");
                String transType = req.getParameter("transType");

                /*if (transType.equals("BJSX")) {
                    dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                    BjSendRepairDAO daoS = new BjSendRepairDAO(user, dtoParameter, conn);
                    req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, dto);
                    req.setAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO, daoS.getLines(dto.getTransId()));
                    forwardURL = "/spare/query/bjSendRepairQuery.jsp";

                } else*/
                if (transType.equals(DictConstant.BJBF)) {
                    dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                    FlowExtend fe = new FlowExtend(conn);
                    dto.setActId(fe.getActId(dto.getTransId(), "AMS_ITEM_TRANS_H"));
                    req.setAttribute(WebAttrConstant.ALLOT_H_DTO, dto);
                    req.setAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO, dao.getLines(dto.getTransId()));
                    forwardURL = "/nm/spare2/query/bjRejectQuery.jsp?actId=" + dto.getActId();

                } else if (transType.equals(DictConstant.BJFH)) {
                    dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                    BjReturnRepairDAO daor = new BjReturnRepairDAO(user, dtoParameter, conn);
                    DTOSet ds = daor.getLines(dto.getTransId());
                    req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, dto);
                    req.setAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO, ds);
                    forwardURL = "/nm/spare2/query/bjReturnQueryDetail.jsp";
                } else if (transType.equals(DictConstant.BJFP)) {

                    AmsBjsAllotDAO daoa = new AmsBjsAllotDAO(user, dtoParameter, conn);
                    daoa.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) daoa.getDataByPrimaryKey();
//                    FlowExtend fe = new FlowExtend(conn);
//                    dto.setActId(fe.getActId(dto.getTransId(), "AMS_ITEM_ALLOCATE_H"));
                    req.setAttribute(WebAttrConstant.ALLOT_H_DTO, dto);
                    req.setAttribute(WebAttrConstant.ALLOT_D_DTO, daoa.getLines(dto.getTransId()));

                    forwardURL = "/nm/spare2/query/bjAlotQuery.jsp?actId=" + dto.getActId();
                } else if (transType.equals(DictConstant.BJDB)) {
                    dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                    FlowExtend fe = new FlowExtend(conn);
                    dto.setActId(fe.getActId(dto.getTransId(), "AMS_ITEM_ALLOCATE_H"));
                    req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, dto);
//                    BjdbApproveDAO ldao = new BjdbApproveDAO(user, null, conn);
                    req.setAttribute("AIT_LINES", dao.getLine(dtoParameter.getTransId()));
//                    String trans = "trans";
//                    forwardURL = "/servlet/com.sino.ams.spare2.servlet.BjdbServlet?act=" + WebActionConstant.DETAIL_ACTION + "&transId=" + dtoParameter.getTransId() + "&trans=" + trans;
                    forwardURL = "/nm/spare2/query/bjDBQuery.jsp?actId=" + dto.getActId();
                } else if (transType.equals(DictConstant.BJCK)) {
                    dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                    req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, dto);
                    AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
                    req.setAttribute("AIT_LINES", ldao.getLines(dtoParameter.getTransId()));
                    forwardURL = "/nm/spare2/query/bjckQuery.jsp";
                } else if (transType.equals(DictConstant.BJRK)) {
                    dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                    req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, dto);
                    AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
                    req.setAttribute("AIT_LINES", ldao.getLines(dtoParameter.getTransId()));
                    forwardURL = "/nm/spare2/query/xgrkOrderQuery.jsp";
                } else if (transType.equals(DictConstant.BJFK)) {
                    dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                    req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, dto);
                    AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
                    req.setAttribute("AIT_LINES", ldao.getLines(dtoParameter.getTransId()));
                    forwardURL = "/nm/spare2/query/bjfkCreateQuery.jsp";
                } else if (transType.equals(DictConstant.BJSL)) {
                    dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                    req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, dto);
                    AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
                    req.setAttribute("AIT_LINES", ldao.getLines(dtoParameter.getTransId()));
                    forwardURL = "/nm/spare2/query/bjslQuery.jsp";
                } else if (transType.equals(DictConstant.FTMCK)) {
                    dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                    req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, dto);
                    AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
                    req.setAttribute("AIT_LINES", ldao.getLines(dtoParameter.getTransId()));
                    forwardURL = "/nm/spare2/query/noBarcodeOut.jsp";

                } else if (transType.equals(DictConstant.FTMRK)) {
                    dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                    req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, dto);
                    AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
                    req.setAttribute("AIT_LINES", ldao.getLines(dtoParameter.getTransId()));
                    forwardURL = "/nm/spare2/query/noBarcodeIn.jsp";

                } else if (transType.equals(DictConstant.TMCK)) {
                    dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                    req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, dto);
                    AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
                    req.setAttribute("AIT_LINES", ldao.getLines(dtoParameter.getTransId()));
                    forwardURL = "/nm/spare2/query/barcodeOutQuery.jsp";
                } else if (transType.equals(DictConstant.TMRK)) {
                    dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                    req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, dto);
                    AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
                    req.setAttribute("AIT_LINES", ldao.getLines(dtoParameter.getTransId()));
                    forwardURL = "/nm/spare2/query/barcodeInQuery.jsp";
                } else if (transType.equals(DictConstant.BJSX)) {
                    dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                    dao.setCalPattern(CalendarConstant.LINE_PATTERN);
                    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                    req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, dto);
                    BjSendRepairDAO ldao = new BjSendRepairDAO(user, null, conn);
                    req.setAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO, ldao.getLines(dtoParameter.getTransId()));
                    forwardURL = "/nm/spare2/query/bjSendRepairQuery.jsp";
                } else if (transType.equals(DictConstant.BJGH)) {
                    dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                    dao.setCalPattern(CalendarConstant.LINE_PATTERN);
                    AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                    req.setAttribute("AIT_HEADER", dto);

                    BjghDAO ldao = new BjghDAO(user, null, conn);
                    req.setAttribute("AIT_LINES", ldao.getQueryLines(dtoParameter.getTransId()));
                    forwardURL = "/nm/spare2/query/bjghQueryDetail.jsp";
                }
            } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
                File file = dao.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }
        } catch (PoolPassivateException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataTransException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder sf = new ServletForwarder(req, res);
            if (showMsg.equals("")) {
                sf.forwardView(forwardURL);
            } else {
                sf.forwardParentView(forwardURL, showMsg);
//                sf.closeParent(showMsg);
            }

        }
    }
}
