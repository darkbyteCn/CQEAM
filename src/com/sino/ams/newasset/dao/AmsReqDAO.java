package com.sino.ams.newasset.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.model.AmsReqModel;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.web.DatabaseForWeb;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-13
 * Time: 10:41:30
 * To change this template use File | Settings | File Templates.
 */
public class AmsReqDAO {
    private Connection conn;
    AmsReqModel model;

    public AmsReqDAO(Connection conn) {
        this.conn = conn;
        this.model=new AmsReqModel();
    }

    public String getHeaderId() throws SQLException {
    	SeqProducer seq = new SeqProducer(conn);
    	return seq.getGUID();
    }
    public String getItemCode(String iName,String iSpec)throws QueryException, ContainerException {
        String  iCode="";
        SQLModel sm=model.getItemCode(iName,iSpec) ;
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            iCode = (String) row.getValue("ITEM_CODE");
        }
        return iCode;
    }
     public String getAddreId(String adresName,int orgId)throws QueryException, ContainerException {
//        int  addreId=0;
        String  addreId="";
        SQLModel sm=model.getAddreId(adresName,orgId) ;
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            addreId = (String)row.getValue("ADDRESS_ID");
        }
        return addreId;
    }
     public String getDepId(String deptName,int orgId)throws QueryException, ContainerException {
        String  addreId="";
//        int  addreId=0;
        SQLModel sm=model.getDept(deptName,orgId) ;
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            if (!row.getValue("DEPT_CODE").equals("")) {
            	//addreId = (Integer) row.getValue("DEPT_CODE");
            	addreId = (String)row.getValue("DEPT_CODE");
            }
        }
        return addreId;
    }
     public String getUseId(String useName,int orgId)throws QueryException, ContainerException {
//        int  addreId=0;
          String  addreId="";
        SQLModel sm=model.getUserId(useName,orgId) ;
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            addreId = (String)row.getValue("EMPLOYEE_ID");
        }
        return addreId;
    }
    //获取名明细信息
    public String getCardDefineListOption(String selectedValue,int userId) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT CD.CARD_ID,CD.CARD_NAME FROM YS_CARD_DEFINE CD WHERE CD.USER_ID = ? AND CD.DEPOT_ID = ? ORDER BY CD.CARD_CODE ";
        sqlArgs.add(userId);
        sqlArgs.add(selectedValue);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }
}
