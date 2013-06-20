package com.sino.ams.bean;

import com.sino.base.data.Row;
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
public class IsLevel2GroupServlet extends BaseServlet {

    private static final String m_sContentType = "text/html; charset=GBK";

    /**
     * 所有的Servlet都必须实现的方法。
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType(m_sContentType);
        PrintWriter resout = res.getWriter();
        String group = util.getReqPara(req, "group");
        Connection conn = null;
        try {
            conn = getDBConnection(req);
            SQLModel sqlModel = isLevel2Group(group);
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if(simpleQuery.hasResult()) {
               Row row = simpleQuery.getSearchResult().getRow(0);
               resout.write(row.getStrValue("SECOND_DEPT"));
            } else {
                resout.write("Y");
            }
        } catch (QueryException ex) {
            ex.printLog();
        } catch (PoolPassivateException ex) {
            ex.printLog();
        } catch (ContainerException ex) {
            ex.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
        }
    }

    public SQLModel isLevel2Group(String group) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT SMD.SECOND_DEPT"
            + " FROM"
            + " SF_GROUP SG, SINO_GROUP_MATCH SGM, SINO_MIS_DEPT SMD"
            + " WHERE"
            + " SG.GROUP_NAME = ?"
            + " AND SGM.GROUP_ID = SG.GROUP_ID"
            + " AND SGM.DEPT_ID = SMD.DEPT_ID";

        sqlArgs.add(group);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
