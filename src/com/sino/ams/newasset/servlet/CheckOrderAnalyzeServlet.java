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
import com.sino.ams.newasset.dao.AmsAssetsCheckLineDAO;
import com.sino.ams.newasset.dao.ArchiveHeaderDAO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
import com.sino.ams.newasset.model.AmsAssetsCheckHeaderModel;
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
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: CheckOrderAnalyzeServlet</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsCheckHeaderServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class CheckOrderAnalyzeServlet extends BaseServlet {

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
            req2DTO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
            AmsAssetsCheckHeaderDTO dtoParameter = (AmsAssetsCheckHeaderDTO)
                    req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            if (action.equals("")) {
                AssetsOptProducer optProducer = new AssetsOptProducer(user,
                        conn);
                String deptOptions = optProducer.getUserAsssetsDeptOption(
                		StrUtil.nullToString(dtoParameter.getCheckDept()));
                dtoParameter.setCheckDeptOption(deptOptions);

                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = AssetsURLList.ANALYZE_ORDER_QRY;
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                dtoParameter.setOrderStatus(AssetsDictConstant.
                                            CHK_STATUS_ARCHIEVED);
                BaseSQLProducer sqlProducer = new AmsAssetsCheckHeaderModel(
                        user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                AssetsOptProducer optProducer = new AssetsOptProducer(user,
                        conn);
                String deptOptions = optProducer.getUserAsssetsDeptOption(
                		StrUtil.nullToString(dtoParameter.getCheckDept()));
                dtoParameter.setCheckDeptOption(deptOptions);

                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = AssetsURLList.ANALYZE_ORDER_QRY;
            } else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
                ArchiveHeaderDAO archiveDAO = new ArchiveHeaderDAO(user,
                        dtoParameter, conn);
                archiveDAO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.
                                           getName());
                archiveDAO.setCalPattern(LINE_PATTERN);
                dtoParameter = (AmsAssetsCheckHeaderDTO) archiveDAO.
                               getDataByPrimaryKey();
                AmsAssetsCheckLineDTO checkLine = new AmsAssetsCheckLineDTO();
                checkLine.setHeaderId(dtoParameter.getHeaderId());
                AmsAssetsCheckLineDAO lineDAO = new AmsAssetsCheckLineDAO(user,
                        checkLine, conn);
                lineDAO.setCalPattern(LINE_PATTERN);
                lineDAO.setDTOClassName(AmsAssetsCheckLineDTO.class.getName());
                DTOSet chkLines = (DTOSet) lineDAO.getDataByForeignKey(
                        "headerId");

                req.setAttribute(AssetsWebAttributes.CHECK_HEADER_DATA,
                                 dtoParameter);
                req.setAttribute(AssetsWebAttributes.CHECK_LINE_DATAS, chkLines);
                forwardURL = AssetsURLList.ANALYZE_ORDER_DTL;
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
            //根据实际情况修改页面跳转代码。
        }
    }
}
