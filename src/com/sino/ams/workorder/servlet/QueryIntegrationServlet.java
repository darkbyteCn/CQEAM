package com.sino.ams.workorder.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dao.QueryIntegrationDAO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.model.QueryIntegrationModel;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: V-jiachuanchuan
 * Date: 2007-11-5
 * Time: 16:34:22
 * Function:
 * To change this template use File | Settings | File Templates.
 */
public class QueryIntegrationServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        try {
            conn = DBManager.getDBConnection();
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);//从session中获取数据，根据实际情况自行修改。
            EtsWorkorderDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EtsWorkorderDTO.class.getName());
            dtoParameter = (EtsWorkorderDTO) req2DTO.getDTO(req);
            //组织
            OptionProducer prd = new OptionProducer(user, conn);
            String ouoption = prd.getAllOrganizationByPAM(dtoParameter.getOrganizationId(), true);
            req.setAttribute(WebAttrConstant.OU_OPTION, ouoption);
            //工单状态
            String status = prd.getDictOption(DictConstant.WORKORDER_STATUS, (String.valueOf(dtoParameter.getWorkorderFlag())));
            req.setAttribute(WebAttrConstant.PLAN_STATUS_OPTION, status);
            //工单类型
            String type = prd.getDictOption(DictConstant.WORKORDER_TYPE, dtoParameter.getWorkorderType());
            req.setAttribute(WebAttrConstant.PLAN_TYPE_OPTION, type);

            String cat = prd.getDictOption(DictConstant.OBJECT_CATEGORY, dtoParameter.getObjectCategory());
            req.setAttribute("CATEGORY", cat);

            String queryType = dtoParameter.getQueryType();
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
            if (action.equals("")) {
                if (queryType.equals(WebAttrConstant.WOR_STATUS_BATCH)) {
                    forwardURL = URLDefineList.QUERY_BATCH + "?queryType=" + queryType;
                } else {
                    forwardURL = URLDefineList.QUERY_INTEGRATION + "?queryType=" + queryType;
                }
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new QueryIntegrationModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                if (queryType.equals(WebAttrConstant.WOR_STATUS_BATCH)) {
                    forwardURL = URLDefineList.QUERY_BATCH + "?queryType=" + queryType;
                } else {
                    forwardURL = URLDefineList.QUERY_INTEGRATION + "?queryType=" + queryType;
                }

            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                SQLModel sqlModel = new SQLModel();
                SimpleQuery simpleQuery = null;


                dtoParameter.setWorkorderBatch(req.getParameter("WORKORDER_BATCH"));
                QueryIntegrationModel queryIntegrationModel = new QueryIntegrationModel(userAccount, dtoParameter);
//                sqlModel = queryIntegrationModel.getQueryBatacDtlModel();
//
//                simpleQuery = new SimpleQuery(sqlModel, conn);
//                simpleQuery.executeQuery();
//                RowSet queryBatchDtlSet = new RowSet();
//
//                if (simpleQuery.hasResult()) {
//                    queryBatchDtlSet = simpleQuery.getSearchResult();
//                }
//                req.setAttribute(QueryConstant.SPLIT_DATA_VIEW, queryBatchDtlSet);

                sqlModel = queryIntegrationModel.getQueryBatacDtlModel();
                WebPageView wpv = new WebPageView(req, conn);
//                wpv.setCalPattern(CalendarConstant.LINE_PATTERN);
                wpv.produceWebData(sqlModel);

                forwardURL = URLDefineList.QUERY_BATCH_DTL;
            } else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
                QueryIntegrationDAO queryIntegrationDAO = new QueryIntegrationDAO(userAccount, dtoParameter, conn);
                File file = queryIntegrationDAO.getExportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }
        } catch (PoolException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLModelException e) {
            e.printLog();
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException e) {
            e.printLog();
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataTransException e) {
            e.printLog();
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
