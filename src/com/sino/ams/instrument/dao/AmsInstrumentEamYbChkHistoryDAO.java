package com.sino.ams.instrument.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.instrument.dto.AmsInstrumentEamYbChkMaintainDTO;
import com.sino.ams.instrument.model.AmsInstrumentEamYbChkHistoryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

public class AmsInstrumentEamYbChkHistoryDAO extends AMSBaseDAO {

	AmsInstrumentEamYbChkHistoryModel modelProducer = null;
	
	public AmsInstrumentEamYbChkHistoryDAO(SfUserDTO userAccount, AmsInstrumentEamYbChkMaintainDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		modelProducer = (AmsInstrumentEamYbChkHistoryModel)sqlProducer;
	}

	/**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsInstrumentEamYbChkMaintainDTO dtoPara = (AmsInstrumentEamYbChkMaintainDTO) dtoParameter;
		super.sqlProducer = new AmsInstrumentEamYbChkHistoryModel((SfUserDTO)userAccount, dtoPara);
	}

}
