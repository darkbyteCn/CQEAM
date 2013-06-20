package com.sino.sinoflow.todo.dao;

import java.sql.Connection;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.util.StrUtil;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.todo.dto.OaTodoDTO;
import com.sino.sinoflow.todo.model.OaTodoModel;

/**
 * 
 * @系统名称:
 * @功能描述:
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Nov 30, 2011
 */
public class OaTodoDAO extends BaseDAO {
	protected OaTodoDTO oaTodoForm = null;

	public OaTodoDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		this.initSQLProducer(userAccount, dtoParameter);
	}

	@Override
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		oaTodoForm = (OaTodoDTO) dtoParameter;
		sqlProducer = new OaTodoModel((SfUserDTO) userAccount, oaTodoForm);
	}

	/**
	 * 从EAM插入待办数据到中间表OA_TODO
	 * 
	 * @param appId
	 * @return
	 */
	public boolean insertOaTODOFromEAM(String appId) {
		if (StrUtil.isEmpty(appId)) {
			return false;
		}
		boolean isSuccess = false;
		OaTodoModel oaTodoModel = null;
		try {
			oaTodoModel = (OaTodoModel) sqlProducer;
			if (oaTodoModel != null) {
				oaTodoModel.setDTOParameter(oaTodoForm);
				isSuccess = DBOperator.updateRecord(oaTodoModel
						.getInsertOATodoFromEAMModel(appId), conn);
			}
		} catch (Exception ex) {
		} finally {
			return isSuccess;
		}
	}

//	/**
//	 * 这个方法是在流程提交后才有效 从EAM插入已办数据到中间表OA_TODO_DELE
//	 * 
//	 * @param appId
//	 * @return
//	 */
//	public boolean insertOaTodoDeleFromEAM(String appId, String actId) {
//		if (StrUtil.isEmpty(appId)) {
//			return false;
//		}
//		boolean isSuccess = false;
//		OaTodoModel oaTodoModel = null;
//		try {
//			oaTodoModel = (OaTodoModel) sqlProducer;
//			if (oaTodoModel != null) {
//				oaTodoModel.setDTOParameter(oaTodoForm);
//				isSuccess = DBOperator.updateRecord(oaTodoModel
//						.getInsertOATodoDeleFromEAMModel(appId, actId), conn);
//			}
//		} catch (Exception ex) {
//		} finally {
//			return isSuccess;
//		}
//	}

	/**
	 * 这个方法是在流程提交前才有效,一般使用这个方法，不使用上面2个参数的 从EAM插入已办数据到中间表OA_TODO_DELE
	 * 
	 * @param appId
	 * @return
	 */
	public boolean insertOaTodoDeleFromEAM(String appId) {
		if (StrUtil.isEmpty(appId)) {
			return false;
		}
		boolean isSuccess = false;
		OaTodoModel oaTodoModel = null;
		try {
			oaTodoModel = (OaTodoModel) sqlProducer;
			if (oaTodoModel != null) {
				oaTodoModel.setDTOParameter(oaTodoForm);
				isSuccess = DBOperator.updateRecord(oaTodoModel
						.getInsertOATodoDeleFromEAMModel(appId), conn);
			}
		} catch (Exception ex) {
		} finally {
			return isSuccess;
		}
	} 

}
