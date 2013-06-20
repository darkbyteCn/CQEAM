package com.sino.flow.example.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.flow.bean.FlowBusiness;
import com.sino.flow.example.model.NewModel;
import com.sino.flow.exception.FlowException;
import com.sino.framework.security.bean.SessionUtil;

/**
 * Created by wwb.
 * User: V-wangwenbin
 * Date: 2007-9-20
 * Time: 17:05:27
 */
public class NewDAO {
    private HttpServletRequest request;
    private Connection conn;

    public NewDAO(HttpServletRequest request, Connection conn) {
        this.request = request;
        this.conn = conn;
    }

    //暂存，不走流程
    public void save() {

    }

    public void complete() throws DataHandleException, SQLException, FlowException {
        try {
            conn.setAutoCommit(false);
            //step1:应用处理应用自己的业务
            SeqProducer sp = new SeqProducer(conn);
            String appId = StrUtil.nullToString(sp.getStrNextSeq("SF_FLOW_TEST_S"));
            DBOperator.updateRecord(NewModel.saveModel(request, appId), conn);
            //step2:走流程
            String appNum = StrUtil.nullToString(request.getParameter("appNum"));
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
            /*
            *流程构造器参数说明：
            *conn,连接
            *request,
            *appId,应用ID，也就是应用主键
            *appNum,应用编号,即单据号
            *userId,当前用户ID
            *userName,当前用户用户名
            */
            FlowBusiness fb = new FlowBusiness(conn, request, appId, appNum, StrUtil.nullToString(userAccount.getUserId()), userAccount.getUsername());
            /*
            *flow2Next参数说明
            *第一个参数：审批意见，如果为空，自动生成
            *第二个参数：是否发送短信
            */
            fb.flow2Next("", true);
            conn.commit();
        } catch (DataHandleException e) {
            conn.rollback();
            throw e;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } catch (FlowException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void cancel() throws SQLException, FlowException {
        try {
            conn.setAutoCommit(false);
            //step1:处理应用业务
            //step2:撤消流程
            String appId = StrUtil.nullToString(request.getParameter("appId"));
            String appNum = StrUtil.nullToString(request.getParameter("appNum"));
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
            FlowBusiness fb = new FlowBusiness(conn, request, appId, appNum, StrUtil.nullToString(userAccount.getUserId()), userAccount.getUsername());
            fb.cancelFlow();
            conn.commit();
        } catch (FlowException e) {
            conn.rollback();
            throw e;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void find(String appId) throws QueryException {
        SimpleQuery sq = new SimpleQuery(NewModel.findModel(appId), conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null) {
            request.setAttribute("TEST_RESULT", rs);
        }
    }
}
