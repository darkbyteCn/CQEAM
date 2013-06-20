package com.sino.ams.workorder.servlet;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.framework.servlet.BaseServlet;

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
public class getGrouNameByZero extends BaseServlet {

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
        String costCenterCode = req.getParameter("costCenterCode");
        String companyCode = req.getParameter("companyCode");
        Connection conn = null;
        try {
            conn = getDBConnection(req);
            SQLModel sqlModel = getGroupZero(costCenterCode,companyCode);
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            RowSet rs = null;
            String group = "";
            if(simpleQuery.hasResult()) {
            	rs = simpleQuery.getSearchResult();
                Row row;
                for(int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);
                    if(group == "")
                        group = row.getStrValue("GROUP_NAME");
                    else
                        group += ";" + row.getStrValue("GROUP_NAME");
                }
               resout.write(group);
            } else {
                resout.write("");
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

    public SQLModel getGroupZero(String costCenterCode,String companyCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = 
        	" SELECT  GROUP_NAME \n"+
        	" FROM  AMS_MIS_DEPT \n"+
        	" WHERE  \n"+
        	" COST_CENTER_CODE = ? \n"+
        	" AND charindex('_',GROUP_NAME) = 0 \n"+
        	" AND (GROUP_NAME is not null or GROUP_NAME <> '')\n"+
        	" AND  COMPANY_CODE = ? \n";

        sqlArgs.add(costCenterCode);
        sqlArgs.add(companyCode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
