package com.sino.ams.newasset.report.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.report.dto.ReportInDataDTO;
import com.sino.ams.newasset.report.model.ReportInDataModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.Row;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-14
 * Time: 16:50:12
 * To change this template use File | Settings | File Templates.
 */
public class ReportInDataDAO extends AMSBaseDAO {

	public ReportInDataDAO(SfUserDTO userAccount, ReportInDataDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 *
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		ReportInDataDTO dto = (ReportInDataDTO) dtoParameter;
		sqlProducer = new ReportInDataModel(user, dto);
	}

   /**
	 *
	 * Function:		判断指定的KPI编码是否属于KPI指标
     * @param kpiCode   KPI编码
	 * @return			boolean类型，false表示不属于KPI指标
	 * @author  		李轶
	 * @Date:   		2009-09-25
	 */
	public Row getIsKpi(String kpiCode) throws QueryException {
       boolean result = false;
       ReportInDataModel ridModel = (ReportInDataModel)sqlProducer;
	   SQLModel sqlModel = ridModel.getIsKpiModel();
       SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
       simpleQuery.executeQuery();
       Row row = null;
       if(simpleQuery.hasResult()){
           row = simpleQuery.getFirstRow();
       } else {
           row = null;
       }
       return row;
    }

}
