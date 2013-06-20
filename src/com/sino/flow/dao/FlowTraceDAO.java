package com.sino.flow.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.query.WebPageView;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.ReqAttributeList;
import com.sino.flow.dto.FlowDTO;
import com.sino.flow.model.FlowTraceModel;

/**
 * Created by wwb.
 * User: demo
 * Date: 2006-12-24
 * Time: 17:22:54
 */
public class FlowTraceDAO {
    private HttpServletRequest req;
    private Connection conn;

    public FlowTraceDAO(HttpServletRequest req, Connection conn) {
        this.req = req;
        this.conn = conn;
    }

    /**
     * 取当前用户有多少待办任务,显示在警世区内
     * @param currUserId
     */
    public void getInboxTotal(String currUserId) throws QueryException, ContainerException {
        String total = "0";
        SimpleQuery sq = new SimpleQuery(FlowTraceModel.getInboxCountModel(currUserId), conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            total = (String) row.getValue("TOTAL");
        }
        req.setAttribute(ReqAttributeList.ALERT_AREA_TOTAL, total);
    }

    /**
     * 取当前用户的发件箱总数
     * @param currUserId
     * @throws QueryException
     * @throws ContainerException
     */
    public void getOutboxTotal(String currUserId) throws QueryException, ContainerException {
        String total = "0";
        SimpleQuery sq = new SimpleQuery(FlowTraceModel.getOutBoxCountModel(currUserId), conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            total = (String) row.getValue("TOTAL");
        }
        req.setAttribute(ReqAttributeList.OUT_BOX_TOTAL, total);
    }

    /**
     * 取个人申请总数
     */
    public void getPersonalTotal(String currUserId) throws QueryException, ContainerException {
        String total = "0";
        SimpleQuery sq = new SimpleQuery(FlowTraceModel.getPersonalModel(currUserId), conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            total = (String) row.getValue("TOTAL");
        }
        req.setAttribute(ReqAttributeList.PERSONAL_TOTAL, total);
    }

    /**
     * 取所有申请总数
     * @throws QueryException
     * @throws ContainerException
     */
    public void getAllTotal() throws QueryException, ContainerException {
        String total = "0";
        SimpleQuery sq = new SimpleQuery(FlowTraceModel.getAllModel(), conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            total = (String) row.getValue("TOTAL");
        }
        req.setAttribute(ReqAttributeList.ALL_TOTAL, total);
    }

    /**
     * 查找收件箱
     * @param userId
     * @throws QueryException
     */
    public void getInbox(String userId) throws QueryException {
        String applyNumber = StrUtil.nullToString(req.getParameter("applyNumber"));
        String procName = StrUtil.nullToString(req.getParameter("procName"));
        SimpleQuery sq = new SimpleQuery(FlowTraceModel.getInboxModel(userId, applyNumber, procName), conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        req.setAttribute(ReqAttributeList.INBOX_DATA, rs);
    }

    /**
     * 在办箱
     * 取在办箱数据
     * @param userId
     */
    public void getHandle(String userId) throws QueryException {
        String applyNumber = StrUtil.nullToString(req.getParameter("applyNumber"));
        String procName = StrUtil.nullToString(req.getParameter("procName"));
        SimpleQuery sq = new SimpleQuery(FlowTraceModel.getHandelModel(userId, applyNumber, procName), conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        req.setAttribute(ReqAttributeList.HANDLE_DATA, rs);
    }

    /**
     * 收件箱+在办箱
     * @param userId
     * @throws QueryException
     */
    public RowSet getMyWork(String userId, String actId) throws QueryException {
        String applyNumber = StrUtil.nullToString(req.getParameter("applyNumber"));
        String procName = StrUtil.nullToString(req.getParameter("procName"));
        SimpleQuery sq = new SimpleQuery(FlowTraceModel.getMyWorkModel(userId, applyNumber, procName, actId), conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    /**
     * 发件箱
     * @param userId
     * @throws QueryException
     */
    public RowSet getMyWorked(String userId) throws QueryException {
        String fromDate = StrUtil.nullToString(req.getParameter("fromDate"));
        String toDate = StrUtil.nullToString(req.getParameter("toDate"));
        String applyNumber = StrUtil.nullToString(req.getParameter("applyNumber"));
        String procName = StrUtil.nullToString(req.getParameter("procName"));
        SimpleQuery sq = new SimpleQuery(FlowTraceModel.getMyWorkedModel(userId, fromDate, toDate, applyNumber, procName), conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.executeQuery();
        return sq.getSearchResult();
    }


    /**
     * 需要归档的
     * @param userId
     * @throws QueryException
     */
    public void getArchieve(String userId) throws QueryException {
        String reportNumber = StrUtil.nullToString(req.getParameter("reportNumber"));
        SimpleQuery sq = new SimpleQuery(FlowTraceModel.getArchieveModel(userId, reportNumber), conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        req.setAttribute(ReqAttributeList.INBOX_DATA, rs);
    }

    /**
     * 取个人转发的申请
     * @param userId
     * @throws QueryException
     */
    public void getOutbox(String userId) throws QueryException {
        String fromDate = StrUtil.nullToString(req.getParameter("fromDate"));
        String toDate = StrUtil.nullToString(req.getParameter("toDate"));
        String applyNumber = StrUtil.nullToString(req.getParameter("applyNumber"));
        String procName = StrUtil.nullToString(req.getParameter("procName"));
        SimpleQuery sq = new SimpleQuery(FlowTraceModel.getOutboxModel(userId, fromDate, toDate, applyNumber, procName), conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        req.setAttribute(ReqAttributeList.OUTBOX_DATA, rs);
    }

    /**
     * 取个人创建的申请
     * @param userId
     * @throws QueryException
     */
    public void getPersonal(String userId, String fromDate, String toDate, String expenseType, String headerStatus, String reportNumber) throws QueryException {
//        SimpleQuery sq = new SimpleQuery(FlowTraceModel.getPersonalDetailModel(userId,fromDate,toDate), conn);
//        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
//        sq.executeQuery();
//        RowSet rs = sq.getSearchResult();
//        req.setAttribute(ReqAttributeList.PERSONAL_DATA, rs);
        WebPageView view = new WebPageView(req, conn);
        view.setCalPattern(CalendarConstant.LINE_PATTERN);
        view.produceWebData(FlowTraceModel.getPersonalDetailModel(userId, fromDate, toDate, expenseType, headerStatus, reportNumber));
    }

    /**
     * 取当前用户可以取消的申请
     * @param userId
     * @throws QueryException
     */
    public void getCancel(String userId) throws QueryException {
        SimpleQuery sq = new SimpleQuery(FlowTraceModel.getCancelModel(userId), conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        req.setAttribute(ReqAttributeList.CANCEL_DATA, rs);
    }

    /**
     * 取可以取消申请的总数
     * @param userId 当前用户
     */
    public void getCancelTotal(String userId) throws QueryException, ContainerException {
        String total = "0";
        SimpleQuery sq = new SimpleQuery(FlowTraceModel.getCancelTotalModel(userId), conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            total = (String) row.getValue("TOTAL");
        }
        req.setAttribute(ReqAttributeList.CANCEL_TOTAL, total);
    }

    /**
     * 签收
     */
    public void signApply(String userId) throws SQLException, DataHandleException, QueryException, ContainerException {
        String[] actId = req.getParameterValues("actId");
        String[] applyNum = req.getParameterValues("applyNum");
        String[] isSign = req.getParameterValues("isSign");
        String retStr = "";
        if (actId != null && actId.length > 0) {
            SignApplyDAO dao = new SignApplyDAO();
            for (int i = 0; i < actId.length; i++) {
                if (isSign[i].equals("Y")) {
                    String flag = dao.sign(actId[i], userId, conn);
                    //如果flag为0，表示已经有人签收，返回用户信息
                    if (flag.equals("0")) {
                        retStr += applyNum[i] + ";";
                    }
                }
            }
            if (!retStr.equals("")) {
                retStr = "签收成功！以下申请已经被别人签收：" + retStr;
            } else {
                retStr = "签收成功！";
            }
        }
        req.setAttribute("SIGN_MSG", retStr);
    }

    public void getBack(String userId) throws SQLException {
        try {
            conn.setAutoCommit(false);
            String actId = req.getParameter("actId");
            FlowAction fa = new FlowAction(conn);
            FlowDTO dto = new FlowDTO();
            dto.setActId(actId);
            dto.setSessionUserId(StrUtil.strToInt(userId));
            dto.setApproveContent("取回");
            fa.setDto(dto);
            fa.getBack();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
