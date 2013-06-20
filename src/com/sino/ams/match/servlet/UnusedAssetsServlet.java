package com.sino.ams.match.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.match.dao.EtsItemMatchDAO;
import com.sino.ams.match.dto.EtsItemMatchDTO;
import com.sino.ams.match.model.UnusedAssetsModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: V-jiachuanchuan
 * Date: 2007-11-22
 * Time: 13:00:04
 * To change this template use File | Settings | File Templates.
 */
public class UnusedAssetsServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        try {
            conn = DBManager.getDBConnection();
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);//从session中获取数据，根据实际情况自行修改。
            EtsItemMatchDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EtsItemMatchDTO.class.getName());
            dtoParameter = (EtsItemMatchDTO) req2DTO.getDTO(req);
            EtsItemMatchDAO etsItemMatchDAO = new EtsItemMatchDAO(user, dtoParameter, conn);

//            OptionProducer prd = new OptionProducer(user, conn);
//            String ouoption = prd.getAllOrganization(user.getOrganizationId());
//            req.setAttribute(WebAttrConstant.OU_OPTION, ouoption);

            if (action.equals("")) {
                forwardURL = URLDefineList.UNUSED_ASSETS;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new UnusedAssetsModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                forwardURL = URLDefineList.UNUSED_ASSETS;
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {
//                etsItemMatchDAO.setDTOClassName(EtsItemMatchDTO.class.getName());
//               String[]  assetIds = req.getParameterValues("subCheck");

//                etsItemMatchDAO.unUserdisplay(); //先删除匹配表的数据再进行删除操作。


                UnusedAssetsModel orderExtendModel = new UnusedAssetsModel(user, dtoParameter);
                SQLModel sqlModel = orderExtendModel.getDataDeleteModel();
                DBOperator.updateRecord(sqlModel, conn);
//                etsItemMatchDAO.deletExist();

//                if(etsItemMatch == null){
//                    etsItemMatch = new EtsItemMatchDTO();
//                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
//                    message.setIsError(true);
//                }
                forwardURL = URLDefineList.UNUSED_ASSETS;
            }

        } catch (PoolException e) {
            Logger.logError(e);
        } catch (QueryException e) {
            Logger.logError(e);
        } catch (DTOException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            Logger.logError(e);
        } catch (SQLModelException e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (!StrUtil.isEmpty(forwardURL)) {
                forwarder.forwardView(forwardURL);
            }
        }
    }
}


