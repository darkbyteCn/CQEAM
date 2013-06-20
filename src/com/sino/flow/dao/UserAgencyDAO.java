package com.sino.flow.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.flow.dto.UserAgencyDTO;
import com.sino.flow.model.UserAgencyModel;
import com.sino.framework.security.bean.SessionUtil;

//import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: Lijun
 * Date: 2007-1-23
 * Time: 10:10:35
 * To change this template use File | Settings | File Templates.
 */
public class UserAgencyDAO {
    private SQLModel sqlModel = null;
    private UserAgencyModel userAgencyModel = null;
    private Connection conn = null;
    private HttpServletRequest request = null;
    private String userId = "";

    public UserAgencyDAO(Connection conn, HttpServletRequest request) {
        this.conn = conn;
        this.request = request;
        sqlModel = new SQLModel();
        userAgencyModel = new UserAgencyModel();
        SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
        this.userId = StrUtil.nullToString(userAccount.getUserId());
    }

    public void setParameter(UserAgencyDTO userAgencyDTO) {
        userAgencyModel.setDtoParameter(userAgencyDTO);
    }

    public void prodUserAgency() throws QueryException {
        userAgencyModel.getDtoParameter().setUserId(userId);
        sqlModel = userAgencyModel.getProdUserAgencyModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.executeQuery();
        request.setAttribute("SPLIT_DATA_VIEW", sq.getSearchResult());
    }

    public void prodUserAgencyDetail() throws QueryException {
        userAgencyModel.getDtoParameter().setUserId(userId);
        sqlModel = userAgencyModel.getProdUserAgencyDetailModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.setDTOClassName(UserAgencyDTO.class.getName());
        sq.executeQuery();
        request.setAttribute("SPLIT_DATA_VIEW", sq.getFirstDTO());
    }

    //插入新的代理人
    public void prodInsertNewUserAgency() throws SQLException, DataHandleException {
        try {
            conn.setAutoCommit(false);
            SeqProducer sp = new SeqProducer(conn);
            userAgencyModel.getDtoParameter().setUserId(userId);
            String id = StrUtil.nullToString(sp.getStrNextSeq("SF_FLOW_AGENT_S"));
            userAgencyModel.getDtoParameter().setId(id);
            sqlModel = userAgencyModel.getInsertNewUserAgencyModel();
            DBOperator.updateRecord(sqlModel, conn);
            //将当事人的待办单据转到代理人信箱
            DBOperator.updateRecord(userAgencyModel.addAgntInActInfo(userId), conn);
            conn.commit();
        } catch (DataHandleException e) {
            conn.rollback();
            throw e;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    //修改代理人
    public void prodUpdateUserAgency() throws DataHandleException, SQLException {
        try {
            conn.setAutoCommit(false);
            sqlModel = userAgencyModel.getUpdateUserAgencyModel();
            DBOperator.updateRecord(sqlModel, conn);
            //将当事人的待办单据转到代理人信箱
            DBOperator.updateRecord(userAgencyModel.addAgntInActInfo(userId), conn);
            conn.commit();
        } catch (DataHandleException e) {
            conn.rollback();
            throw e;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void prodDisableUserAgency() throws DataHandleException, SQLException {
        try {
            conn.setAutoCommit(false);
            sqlModel = userAgencyModel.getDisableUserAgencyModel();
            DBOperator.updateRecord(sqlModel, conn);
            //更新sf_act_info表信息，将代理人处理的任务，全部转到当事人信箱。
            DBOperator.updateRecord(userAgencyModel.updateSfActInfo(userId), conn);
            conn.commit();
        } catch (DataHandleException e) {
            conn.rollback();
            throw e;
        } catch (SQLException e) {
            conn.rollback();
            throw new DataHandleException(e);
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
