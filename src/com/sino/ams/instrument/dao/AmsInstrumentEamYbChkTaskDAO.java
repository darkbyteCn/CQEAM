package com.sino.ams.instrument.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.instrument.dto.AmsInstrumentEamYbChkTaskDTO;
import com.sino.ams.instrument.model.AmsInstrumentEamYbChkTaskModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

public class AmsInstrumentEamYbChkTaskDAO extends AMSBaseDAO {
	
	AmsInstrumentEamYbChkTaskModel modelProducer = null;

	/**
     * 功能：仪器仪表管理(EAM) EAM_YB_CHK_TASK 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsInstrumentEamYbChkTaskDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
	public AmsInstrumentEamYbChkTaskDAO(SfUserDTO userAccount, AmsInstrumentEamYbChkTaskDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		modelProducer = (AmsInstrumentEamYbChkTaskModel)sqlProducer;
	}

	/**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsInstrumentEamYbChkTaskDTO dtoPara = (AmsInstrumentEamYbChkTaskDTO) dtoParameter;
		super.sqlProducer = new AmsInstrumentEamYbChkTaskModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：检验任务名称是否已经存在
	 * @param objCode
	 * @return
	 * @throws QueryException
	 */
	public boolean checkName(String objName) throws QueryException {
        SQLModel sqlModel = modelProducer.getNameHasBeenModel(objName);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.hasResult();
    }
}
