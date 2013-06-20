package com.sino.sinoflow.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.db.conn.DBManager;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.exception.*;
import com.sino.base.data.RowSet;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.util;


/**
 * <p>Title: SfValidationServlet</p>
 * <p>Description:程序自动生成服务程序“SfValidationServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class IsGroupsValid extends BaseServlet {

    private static final String m_sContentType = "text/html; charset=GBK";

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType(m_sContentType);
        PrintWriter resout = res.getWriter();
        String groups = util.getReqPara(req, "groups");
        String newGroups = req.getParameter("newGroups");
        Connection conn = null;
        String retGroups = "";
        String checkGroups = "";
        try {
            conn = getDBConnection(req);
            String[] group = groups.split(",");
            int count = group.length;
            SQLModel sqlModel;
            SimpleQuery simpleQuery;
            for(int i = 0; i < group.length; i++) {
                int index = group[i].lastIndexOf(".");
                if(index < 0) {
                    count--;
                    continue;
                }
                String parentGroup = "'" + group[i].substring(0, index) + "'";
                if(checkGroups.indexOf(parentGroup) >= 0) {
                    count--;
                    continue;
                }
                if(checkGroups.equals("")) {
                    checkGroups = parentGroup;
                } else {
                    checkGroups += "," + parentGroup;
                }
            }
            sqlModel = isGroupValid(checkGroups);
            simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if(simpleQuery.hasResult()) {
                RowSet rs = simpleQuery.getSearchResult();
                if(rs.getSize() == count) {
                    if(newGroups.equals("")) {
                        resout.write("");
                        return;
                    }
                    sqlModel = isOldGroup(newGroups);
                    simpleQuery = new SimpleQuery(sqlModel, conn);
                    simpleQuery.executeQuery();
                    if(simpleQuery.hasResult()) {
                        rs = simpleQuery.getSearchResult();
                        for(int i = 0; i < rs.getSize(); i++) {
                            String groupName = "'" + rs.getRow(i).getStrValue("GROUP_NAME") + "'";
                            if(newGroups.indexOf(groupName) >= 0) {
                                if(retGroups.equals("")) {
                                    retGroups = groupName;
                                } else {
                                    retGroups += "," + groupName;
                                }
                            }
                        }
                        resout.write("错误:新建或修改的组别 " + retGroups + "已存在!");
                        return;
                    } else {
                        resout.write("");
                        return;
                    }
                }
                checkGroups = "," + checkGroups;
                for(int i = 0; i < rs.getSize(); i++) {
                    String groupName = rs.getRow(i).getStrValue("GROUP_NAME");
                    String existGroup = "'" + groupName + "'";
                    if(checkGroups.indexOf(existGroup) >= 0) {
                        checkGroups = checkGroups.replace("," + existGroup, "");
                    }
                }
                retGroups = checkGroups.substring(1);
            } else {
                retGroups = checkGroups;
            }
            resout.write("组别名称检查错误, 请确认已设置父组别 " + retGroups + "!");
        } catch (QueryException ex) {
            ex.printLog();
            resout.write("ERROR: QueryException!");
        } catch (PoolPassivateException ex) {
            ex.printLog();
            resout.write("ERROR: PoolPassivateException!");
        } catch (ContainerException ex) {
            ex.printLog();
            resout.write("ERROR: ContainerException!");
        } finally {
            DBManager.closeDBConnection(conn);
        }
	}

    private SQLModel isGroupValid(String groups) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT DISTINCT GROUP_NAME"
            + " FROM"
            + " SF_GROUP"
            + " WHERE"
            + " GROUP_NAME IN (" + groups + ")";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    private SQLModel isOldGroup(String groups) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT DISTINCT GROUP_NAME"
            + " FROM"
            + " SF_GROUP"
            + " WHERE"
            + " GROUP_NAME IN (" + groups + ")";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}