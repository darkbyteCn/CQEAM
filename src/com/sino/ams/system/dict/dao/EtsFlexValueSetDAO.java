package com.sino.ams.system.dict.dao;


import java.sql.Connection;

import com.sino.ams.system.dict.dto.EtsFlexValueSetDTO;
import com.sino.ams.system.dict.model.EtsFlexValueSetModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.DBActionConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.util.DataUniqueChecker;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.ValidateException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: EtsFlexValueSetDAO</p>
 * <p>Description:程序自动生成服务程序“EtsFlexValueSetDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class EtsFlexValueSetDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：字典分类表(AMS) ETS_FLEX_VALUE_SET 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsFlexValueSetDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EtsFlexValueSetDAO(SfUserDTO userAccount, EtsFlexValueSetDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		EtsFlexValueSetDTO dtoPara = (EtsFlexValueSetDTO)dtoParameter;
		super.sqlProducer = new EtsFlexValueSetModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：插入字典分类表(AMS)表“ETS_FLEX_VALUE_SET”数据。
	 * @return boolean
	 */
	public void createData() throws DataHandleException {
		 saveData(DBActionConstant.INSERT);
	}

	/**
	 * 功能：更新字典分类表(AMS)表“ETS_FLEX_VALUE_SET”数据。
	 * @return boolean
	 */
	public void updateData() throws DataHandleException {
		 saveData(DBActionConstant.UPDATE);
	}

	private boolean saveData(String dbAction) throws DataHandleException {
		boolean operateResult = false;
		try {
			String tableName = "ETS_FLEX_VALUE_SET";
			DataUniqueChecker dataChecker = new DataUniqueChecker(tableName, dtoParameter, conn);
			dataChecker.setDBAction(dbAction);
			if (dataChecker.isDataValid()) {
				if(dbAction.equals(DBActionConstant.INSERT)){
					 super.createData();
				} else if(dbAction.equals(DBActionConstant.UPDATE)){
					super.updateData();
				}
				getMessage().addParameterValue("字典分类");
			} else {
				prodMessage(MsgKeyConstant.UNIQUE_ERROR);
				message.addParameterValue(dataChecker.getInValidData());
				message.setIsError(true);
			}
		} catch (ValidateException ex) {
			ex.printLog();
			prodMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
		}
		return operateResult;
	}
	/**
	 * 功能：删除字典分类表(AMS)表“ETS_FLEX_VALUE_SET”数据。
	 * @return boolean
	 */
	public void deleteData() throws DataHandleException {
		 super.deleteData();
		getMessage().addParameterValue("字典分类表(AMS)");
	}
}
