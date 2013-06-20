package com.sino.ams.adjunct.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.object.model.ImportObjectModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.PubServlet;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.security.bean.SessionUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-8-5
 * Time: 11:33:20
 * To change this template use File | Settings | File Templates.
 */
public class GetPkNameServlet extends PubServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Connection conn = null;
        String forward = "";
        String nextPage = "";
        String seqName = StrUtil.nullToString(req.getParameter("seqName"));
        PrintWriter out = res.getWriter();
        try {
            conn = DBManager.getDBConnection();
            
			SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
			String newId = getNewId(userAccount, conn);
			out.print(newId);
            //SeqProducer sp = new SeqProducer(conn);
            //int orderPkName = sp.getStrNextSeq(seqName);
            //out.print(orderPkName);
        } catch (PoolException e) {
            Logger.logError(e);
            out.write("ERROR");
        } catch (QueryException e) {
        	Logger.logError(e);
            out.write("ERROR");
		} catch (SQLModelException e) {
			Logger.logError(e);
	        out.write("ERROR");
		} catch (ContainerException e) {
			Logger.logError(e);
	        out.write("ERROR");
		}finally {
            DBManager.closeDBConnection(conn);
            out.flush();
            out.close();
        }
    }
    private String getNewId(SfUserDTO userAccount, Connection conn) throws QueryException, ContainerException, SQLModelException {
        String newId = "";
        ImportObjectModel modelProducer = new ImportObjectModel(userAccount, null);
        SQLModel sqlModel = modelProducer.getNewId();
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            newId = StrUtil.nullToString(row.getValue("NEWID"));
        }
        return newId;

    }
}
