package com.sino.flow.example.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.util.StrUtil;
import com.sino.flow.bean.FlowBusiness;
import com.sino.flow.exception.FlowException;
import com.sino.framework.security.bean.SessionUtil;

/**
 * Created by wwb.
 * User: V-wangwenbin
 * Date: 2007-9-20
 * Time: 17:05:34
 */
public class ApproveDAO {
    private HttpServletRequest req;
    private Connection conn;
    SfUserDTO userAccount;

    public ApproveDAO(HttpServletRequest req, Connection conn) {
        this.req = req;
        this.conn = conn;
        this.userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
    }

    //通过流转，转向下一办理人
    public void pass() throws SQLException, FlowException {
        try {
            conn.setAutoCommit(false);
            //step1;处理应用业务
            //step2:流程转向下一节点
            /*
            *流程构造器参数说明：
            *conn,连接
            *request,
            *appId,应用ID，也就是应用主键
            *appNum,应用编号,即单据号
            *userId,当前用户ID
            *userName,当前用户用户名
            */
            String appId = StrUtil.nullToString(req.getParameter("appId"));
            String appNum = StrUtil.nullToString(req.getParameter("appNum"));
            FlowBusiness fb = new FlowBusiness(conn, req, appId, appNum, StrUtil.nullToString(userAccount.getUserId()), userAccount.getUsername());
            /*
            *flow2Next参数说明
            *第一个参数：审批意见，如果为空，自动生成
            *第二个参数：是否发送短信
            */
            fb.flow2Next("", true);
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

    //退回
    public void back() throws FlowException, SQLException {
        try {
            conn.setAutoCommit(false);
            //step1;处理应用业务
            //step2:流程转向下一节点
            /*
            *流程构造器参数说明：
            *conn,连接
            *request,
            *appId,应用ID，也就是应用主键
            *appNum,应用编号,即单据号
            *userId,当前用户ID
            *userName,当前用户用户名
            */
            String appId = StrUtil.nullToString(req.getParameter("appId"));
            String appNum = StrUtil.nullToString(req.getParameter("appNum"));
            FlowBusiness fb = new FlowBusiness(conn, req, appId, appNum, StrUtil.nullToString(userAccount.getUserId()), userAccount.getUsername());
             /*
            *reject2Begin参数说明
            *第一个参数：审批意见，如果为空，自动生成
            *第二个参数：是否发送短信
            */
            fb.reject2Begin("",true);
            //如果退回上一个办理人
            //fb.reject("",true);
            conn.commit();
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
}
