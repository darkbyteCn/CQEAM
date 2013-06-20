package com.sino.ams.system.kpi.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sino.ams.system.kpi.dto.KpiDefineDTO;
import com.sino.ams.system.kpi.model.KpiDefineModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.UserTransaction;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

public class KpiDefineDAO extends BaseDAO {

    private SfUserDTO sfUser = null;

    /**
     * 功能：项目维护表(EAM) ETS_PA_PROJECTS_ALL 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter KpiDefineDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public KpiDefineDAO(SfUserDTO userAccount, KpiDefineDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        KpiDefineDTO dtoPara = (KpiDefineDTO) dtoParameter;
        super.sqlProducer = new KpiDefineModel((SfUserDTO) userAccount, dtoPara);
    }

    
    public void getAllData(HttpServletRequest req) throws DataHandleException, QueryException, ContainerException {
    	SQLModel sqlModel = ((KpiDefineModel)sqlProducer).getAllDataModel();
    	WebPageView webPageView = new WebPageView(req, conn);
    	webPageView.setCalPattern(CalendarConstant.LINE_PATTERN); //表示只取年月日   不取具体的时分秒
    	webPageView.produceWebData(sqlModel);    	
    	RowSet rows = (RowSet) req.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
    	Row row=null;
    	
    	Map map=getDictOptions("KPI_TYPE");			
    	if (rows != null && rows.getSize() > 0) {
            for (int i = 0; i < rows.getSize(); i++) {
            	row = rows.getRow(i);        		
            	if (!row.containsKey("KPI_TYPE_OPTIONS")) {
            		row.addField("KPI_TYPE_OPTIONS", map.get(row.getValue("KPI_TYPE")));
            	} else {
            		row.setField("KPI_TYPE_OPTIONS", map.get(row.getValue("KPI_TYPE")));
            	}
            }
    	}    
    }	
    
    public void saveAllData(HttpServletRequest req,DTOSet dtos) throws DataHandleException, QueryException, ContainerException {
    	UserTransaction userTrans = null;
    	SQLModel sqlModel=null;
    	KpiDefineDTO dto=null;
        try {
            userTrans = new UserTransaction(conn);
            userTrans.beginTransaction();
            String[] isChangeFlag=req.getParameterValues("isChangeFlag");
            
            for (int i=0;i<dtos.getSize();i++) {
            	if ("Y".equals(isChangeFlag[i])) {
            		dto=(KpiDefineDTO)dtos.getDTO(i);
            		sqlModel = ((KpiDefineModel)sqlProducer).getDataModel(dto);
            		SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            		sq.setCalPattern(CalendarConstant.LINE_PATTERN);
            		sq.executeQuery();
            		if (sq.hasResult()) {
            			sqlModel = ((KpiDefineModel)sqlProducer).updateDataModel(dto);
            			DBOperator.updateRecord(sqlModel, conn);
            		} else {
            			sqlModel = ((KpiDefineModel)sqlProducer).insertDataModel(dto);
            			DBOperator.updateRecord(sqlModel, conn);
            		}
            	}
            }
            userTrans.commitTransaction();
        }
        catch (DataHandleException e) {
            try {
                if (userTrans != null) {
                    userTrans.rollbackTransaction();
                }
            } catch (DataHandleException e1) {
                Logger.logError(e1);
            }
            throw e;
        }
    }	
    
    public Map getDictOptions(String dictCode) throws QueryException, ContainerException {
    	Map map=new HashMap();
    	SQLModel sqlModel = new SQLModel();
		List list = new ArrayList();
		String sqlStr = "SELECT"
				+ " EFV.CODE,"
				+ " EFV.VALUE"
				+ " FROM"
				+ " ETS_FLEX_VALUES    EFV,"
				+ " ETS_FLEX_VALUE_SET EFVS"
				+ " WHERE"
				+ " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
				+ " AND EFV.ENABLED = 'Y'"
				+ " AND EFVS.CODE = ?";
		list.add(dictCode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(list);
		SimpleQuery sq = new SimpleQuery(sqlModel, conn);
		sq.setCalPattern(CalendarConstant.LINE_PATTERN);
		sq.executeQuery();
		RowSet rs=sq.getSearchResult();	
		
		for (int i=0;i<rs.getSize();i++) {
			Row outer =rs.getRow(i);
			StringBuffer sb=new StringBuffer("<option value=\"\">--请选择--</option>\n");
			for (int j=0;j<rs.getSize();j++) {
				Row inner=rs.getRow(j);
				sb.append("<option value=\""+inner.getValue("CODE")+"\"");
				if (i==j) {
					sb.append("selected");
				}
				sb.append("/>"+inner.getValue("VALUE")+"</option>\n");
			}
			map.put(outer.getValue("CODE"),sb.toString());
		}
		return map;
		//StringBuffer sb=new StringBuffer("");
		//for (int i=0;i<rs.getSize();i++) {
		//	Row row=rs.getRow(i);
		//	sb.append("<option value="+row.getValue("CODE")+"\">"+row.getValue("VALUE")+"</option>");
		//}
		//return sb.toString();
    }

}
