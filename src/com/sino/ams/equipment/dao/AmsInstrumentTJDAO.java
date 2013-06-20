package com.sino.ams.equipment.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.equipment.dto.AmsInstrumentTJDTO;
import com.sino.ams.equipment.model.AmsInstrumentTJModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: AmsInstrumentTJDAO</p>
 * <p>Description:程序自动生成服务程序“AmsInstrumentTJDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */
public class AmsInstrumentTJDAO extends AMSBaseDAO {
	
	AmsInstrumentTJModel modelProducer = null;

	/**
     * 功能：仪器仪表管理(EAM) ETS_ITEM_INFO  ETS_SYSTEM_ITEM   ETS_OBJECT  EAM_ITEM_DISPOSE 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsInstrumentRegistrationDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
	public AmsInstrumentTJDAO(SfUserDTO userAccount, AmsInstrumentTJDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		modelProducer = (AmsInstrumentTJModel)sqlProducer;
	}

	/**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsInstrumentTJDTO dtoPara = (AmsInstrumentTJDTO) dtoParameter;
		super.sqlProducer = new AmsInstrumentTJModel((SfUserDTO) userAccount, dtoPara);
	}
	
}
