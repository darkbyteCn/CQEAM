package com.sino.ams.bean;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 */
public class GetLoginNameByEmpIdServlet extends BaseServlet {

    private static final String m_sContentType = "text/html; charset=GBK";

    /**
     * 所有的Servlet都必须实现的方法。
     *
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType(m_sContentType);
        PrintWriter resout = res.getWriter();
        String id = util.getReqPara(req, "id");
        Connection conn = null;
        String retStr = "";
        try {
            conn = getDBConnection(req);
            SQLModel sqlModel = getLoginName(id);
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            RowSet rs = null;
            if (simpleQuery.hasResult()) {
                retStr = simpleQuery.getFirstRow().getStrValue("LOGIN_NAME");
            }
        } catch (QueryException ex) {
            ex.printLog();
        } catch (PoolPassivateException ex) {
            ex.printLog();
        } catch (ContainerException ex) {
            ex.printLog();
        } finally {
            if (resout != null) {
                resout.write(retStr);
                resout.close();
            }
            DBManager.closeDBConnection(conn);
        }
    }

    public SQLModel getLoginName(String id) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT SU.LOGIN_NAME"
                + " FROM"
                + " SF_USER SU, AMS_MIS_EMPLOYEE AME"
                + " WHERE"
                + " AME.EMPLOYEE_ID = ?"
                + " AND AME.EMPLOYEE_NUMBER = SU.EMPLOYEE_NUMBER";

        sqlArgs.add(id);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
