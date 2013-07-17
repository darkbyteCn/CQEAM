/**
 * Company: 北京思诺博信息技术有限公司
 * Function:选择工单地点
 * User: zhoujs
 * Date: 2007-9-26
 * Time: 10:28:44
 */
package com.sino.ams.workorder.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.data.Row;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.Request2DTO;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.WebCheckProp;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dao.EtsWorkorderDAO;
import com.sino.ams.workorder.dto.EtsWorkorderBatchDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.model.OrderExtendModel;
import com.sino.ams.workorder.util.WorkOrderUtil;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import org.json.JSONArray;

public class WorkorderChooseSevrlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
      //  PrintWriter pw = null;
      //  JSONArray retArray = new JSONArray();
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;

        String workorderType = StrUtil.nullToString(req
                .getParameter("workorderType"));

        try {
            conn = DBManager.getDBConnection();
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);

            EtsObjectDTO etsObject = null;
            EtsWorkorderBatchDTO batchDTO = null;
            EtsWorkorderDTO workorder = null;

            Request2DTO req2DTO = new Request2DTO();

            req2DTO.setDTOClassName(EtsWorkorderDTO.class.getName());
            workorder = (EtsWorkorderDTO) req2DTO.getDTO(req);

            EtsWorkorderDAO etsWorkorderDAO = new EtsWorkorderDAO(userAccount, workorder, conn);

            req2DTO.setDTOClassName(EtsWorkorderBatchDTO.class.getName());
            batchDTO = (EtsWorkorderBatchDTO) req2DTO.getDTO(req);

            req2DTO.setDTOClassName(EtsObjectDTO.class.getName());
            etsObject = (EtsObjectDTO) req2DTO.getDTO(req);
            String projectOpt = "";
            String countyCodeShi = req.getParameter("countyCodeShi");
            String countyCodeXian = req.getParameter("countyCodeXian");
            if (workorderType.equals(DictConstant.ORDER_TYPE_HDV)) {
                String prjId = req.getParameter("prjId");
                String prjName = req.getParameter("prjName");
                if (countyCodeXian != null) {
                    if (!countyCodeShi.trim().equals("")
                            && countyCodeXian.trim().equals("")) {
                        etsObject.setCountyCodecode(countyCodeShi);
                    } else if (!countyCodeShi.trim().equals("")
                            && !countyCodeXian.trim().equals("")) {
                        etsObject.setCountyCodecode(countyCodeXian);
                    }
                }
                etsObject.setProjectId(prjId);
                etsObject.setProjectName(prjName);
            } else {
                OptionProducer optProducer = new OptionProducer(userAccount, conn);
                projectOpt = optProducer.getProjectOption(etsObject.getProjectId());
            }
            OrderExtendModel orderExtend = new OrderExtendModel();
            SQLModel sqlModel = null;
            RowSet rowSet = null;
            if (action.equals(WebActionConstant.NEW_ACTION)) {
                int groupId = StrUtil.strToInt(req.getParameter("distributeGroupId"));
                // workorder.setGroupId(batchDTO.getDistributeGroupId());
                // workorder.setDistributeGroup(batchDTO.getDistributeGroupId());
                workorder.setGroupId(groupId);
                workorder.setDistributeGroup(groupId);

                String curr = CalendarUtil.getCurrDate() + " 00:00:00";
                workorder.setStartDate(curr);
                req.setAttribute(WebAttrConstant.ETS_WORKORDER_DTO, workorder);
                req.setAttribute(WebAttrConstant.ETS_WORKORDER_BATCH_DTO, batchDTO);
                req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, etsObject);
                req.setAttribute(WebAttrConstant.PROJECT_OPTION, projectOpt);
                req.setAttribute(WebAttrConstant.WORKORDER_LOC_ROWSET, rowSet);

                AssetsOptProducer assetsOptproducer = new AssetsOptProducer(userAccount, conn);
                String countNameOptions = assetsOptproducer.getAmsCountNameOptions("请选择");
                req.setAttribute("COUNT_NAME_OPTIONS", countNameOptions);
                forwardURL = URLDefineList.WORKORDER_CHOOSE_PAGE;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {// 查询工单地点
                sqlModel = orderExtend.getQueryLocDataModel(userAccount, etsObject, workorderType, batchDTO.getWorkorderBatch());
                WebPageView pageView = new WebPageView(req, conn);
                pageView.setPageSize(18);

                WebCheckProp checkProp = new CheckBoxProp("objNos_n");
                checkProp.addDbField("WORKORDER_OBJECT_NO");
                pageView.setWebCheckProp(checkProp);

                pageView.produceWebData(sqlModel);
                req.setAttribute(WebAttrConstant.ETS_WORKORDER_DTO, workorder);
                req.setAttribute(WebAttrConstant.ETS_WORKORDER_BATCH_DTO, batchDTO);
                req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, etsObject);
                req.setAttribute(WebAttrConstant.PROJECT_OPTION, projectOpt);
                AssetsOptProducer assetsOptproducer = new AssetsOptProducer(userAccount, conn);
                String cityOptions = assetsOptproducer.getAmsCountNameOptions(countyCodeShi);
                String countOptions = assetsOptproducer.getAmsCountNameChByShi(countyCodeShi, countyCodeXian);
                req.setAttribute("COUNT_NAME_OPTIONS", cityOptions);
                req.setAttribute("COUNT_OPTIONS", countOptions);
                forwardURL = URLDefineList.WORKORDER_CHOOSE_PAGE;
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {// 生成工单（临时）选中信息写入临时表
                WorkOrderUtil workOrderUtil = new WorkOrderUtil();
                workorder.setAttribute7(workorder.getAttribute7().equals("") ? workOrderUtil.getItemCategory(workorder.getObjectCategory()) : workorder.getAttribute7());
                workorder.setAttribute4(workOrderUtil.getObjectCategoryDesc(conn, workorder.getObjectCategory()));
                RequestParser parser = new RequestParser();
                CheckBoxProp checkProp = new CheckBoxProp("objNos_n");
                checkProp.setIgnoreOtherField(true);
                parser.setCheckBoxProp(checkProp);
                parser.transData(req);


//				String[] workorderObjectNos = req.getParameterValues("objNos_n");
                String[] workorderObjectNos = parser.getParameterValues("workorderObjectNo");
                // 判断是否存在相同地点和项目的交接工单
                // RowSet rowset =
                // etsWorkorderDAO.existHandoverWorkorder(workorderObjectNos,
                // workorder);
                boolean operatorResult = etsWorkorderDAO.createTmpData(workorderObjectNos, workorder, userAccount);
                
           
                
                forwardURL = "/public/windowClose.jsp?retValue=1";
            } else if (action.equals("CHOOSE")) {// 选择执行人
                String groupId = StrUtil.nullToString(req.getParameter("groupId"));
                OptionProducer op = new OptionProducer(userAccount, conn);
                String groupAttr = op.getAllGroup(String.valueOf(groupId), userAccount.getOrganizationId(), false, false);
                String userOfGroup = op.getUsersOfGroup(StrUtil.strToInt(groupId), "");
                req.setAttribute("userOfGroup", userOfGroup);
                req.setAttribute("GROUPATTR", groupAttr);
                forwardURL = "/workorder/util/chooseImplement.jsp";
            } else if (action.equals("arc")) {// 选择归档人
                String groupId = StrUtil.nullToString(req.getParameter("groupId"));
                OptionProducer op = new OptionProducer(userAccount, conn);
                String groupAttr = op.getAllGroup(String.valueOf(groupId), userAccount.getOrganizationId(), false, false);
                String userOfGroup = op.getArcUsersOfGroup(groupId, "", true);
                req.setAttribute("userOfGroup", userOfGroup);
                req.setAttribute("GROUPATTR", groupAttr);
                forwardURL = "/workorder/util/chooseArcUser.jsp";
            } else if (action.equals("mine")) {
                workorder.setGroupId(batchDTO.getDistributeGroupId());
                workorder.setDistributeGroup(batchDTO.getDistributeGroupId());
                String curr = CalendarUtil.getCurrDate() + " 00:00:00";
                workorder.setStartDate(curr);
                req.setAttribute(WebAttrConstant.ETS_WORKORDER_DTO, workorder);
                req.setAttribute(WebAttrConstant.ETS_WORKORDER_BATCH_DTO,
                        batchDTO);
                req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, etsObject);
                req.setAttribute(WebAttrConstant.PROJECT_OPTION, projectOpt);
                req.setAttribute(WebAttrConstant.WORKORDER_LOC_ROWSET, rowSet);
                forwardURL = "/workorder/bts/chooseOrders.jsp";
            } else if (action.equals("myQuery")) {
                sqlModel = orderExtend.getMineLocDataModel(userAccount,
                        etsObject, workorderType, batchDTO.getWorkorderBatch());
                SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
                simpleQuery.executeQuery();
                rowSet = simpleQuery.getSearchResult();
                req.setAttribute(WebAttrConstant.ETS_WORKORDER_DTO, workorder);
                req.setAttribute(WebAttrConstant.ETS_WORKORDER_BATCH_DTO,
                        batchDTO);
                req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, etsObject);
                req.setAttribute(WebAttrConstant.PROJECT_OPTION, projectOpt);
                req.setAttribute(WebAttrConstant.WORKORDER_LOC_ROWSET, rowSet); // --
                forwardURL = "/workorder/bts/chooseOrders.jsp";
            } else if (action.equals("excel")) {
                String excel = StrUtil.nullToString(req.getParameter("excel"));
                sqlModel = orderExtend.getQueryLocExcelModel(userAccount,
                        etsObject, workorderType, batchDTO.getWorkorderBatch(),
                        excel);
                WebPageView pageView = new WebPageView(req, conn);
                pageView.setPageSize(18);

                WebCheckProp checkProp = new CheckBoxProp("objNos_n");
                checkProp.addDbField("WORKORDER_OBJECT_NO");
                pageView.setWebCheckProp(checkProp);

                pageView.produceWebData(sqlModel);

                req.setAttribute(WebAttrConstant.ETS_WORKORDER_DTO, workorder);
                req.setAttribute(WebAttrConstant.ETS_WORKORDER_BATCH_DTO,
                        batchDTO);
                req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, etsObject);
                req.setAttribute(WebAttrConstant.PROJECT_OPTION, projectOpt);
                AssetsOptProducer assetsOptproducer = new AssetsOptProducer(userAccount, conn);
                String cityOptions = assetsOptproducer.getAmsCountNameOptions(countyCodeShi);
                String countOptions = assetsOptproducer.getAmsCountNameChByShi(countyCodeShi, countyCodeXian);
                req.setAttribute("COUNT_NAME_OPTIONS", cityOptions);
                req.setAttribute("COUNT_OPTIONS", countOptions);
                forwardURL = URLDefineList.WORKORDER_CHOOSE_PAGE;
            } else {
                forwardURL = "/public/windowClose.jsp";
            }

        } catch (Throwable ex) {
            Logger.logError(ex);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
