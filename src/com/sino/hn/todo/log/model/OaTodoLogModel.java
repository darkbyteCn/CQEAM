package com.sino.hn.todo.log.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.hn.todo.log.OaLogConstant;
import com.sino.hn.todo.log.dto.OaTodoLogDTO;
import com.sino.sinoflow.todo.constant.HNOAConstant;

/**
 * 
 * @系统名称: 
 * @功能描述: 待办/已办 日志 OA_TODO_LOG / OA_TODO_DELE_LOG
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Nov 30, 2011
 */
public class OaTodoLogModel extends BaseSQLProducer {
	OaTodoLogDTO oaTodoLogDTO = null;

	public OaTodoLogModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
		oaTodoLogDTO = (OaTodoLogDTO) dtoParameter;
	}

	private String getDBName(OaTodoLogDTO oaTodoLogDTO) {
		String logDBName = OaLogConstant.DB_OA_TODO_LOG;
		if (oaTodoLogDTO.getTodoType().trim().equals(HNOAConstant.OA_TODO_TYPE_OPEN)) {
			logDBName = OaLogConstant.DB_OA_TODO_LOG;
		} else if (oaTodoLogDTO.getTodoType().trim().equals(
				HNOAConstant.OA_TODO_TYPE_CLOSE)) {
			logDBName = OaLogConstant.DB_OA_TODO_DELE_LOG;
		}
		return logDBName;
	}

	/**
	 * 统一待办接口表 OA_TODO_LOG / OA_TODO_DELE_LOG 数据插入
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();

		List sqlArgs = new ArrayList();

		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO \n ");
		sql.append(getDBName(oaTodoLogDTO));
		sql.append(" ( \n ");
		sql.append(" DOC_ID, \n ");
		sql.append(" WORK_ID, \n ");
		sql.append(" USER_ID, \n ");
		sql.append(" TITLE, \n ");
		sql.append(" START_TIME, \n ");
		sql.append(" TODO_URL, \n ");
		sql.append(" PRI, \n ");
		sql.append(" TODO_TYPE, \n ");
		sql.append(" DOC_TYPE, \n ");
		sql.append(" SENDER, \n ");

		sql.append(" SOURCE_ID, \n ");
		sql.append(" SYS_ID, \n ");
		sql.append(" CLOSE_TIME, \n ");
		sql.append(" LAST_UPDATE_DATE, \n ");
		
		sql.append(" BEGIN_SEND_TIME, \n ");
		sql.append(" END_SEND_TIME, \n ");
		sql.append(" EAM_MSG, \n ");
		sql.append(" RESULT_CODE, \n ");
		sql.append(" RESULT_DESC \n ");
		
		sql.append(" ) VALUES (  \n ");
		sql.append(" ?, ?, ?, ?, ?,    ?, ?, ?, ?, ?,   ?, ?, ? , GETDATE(),?,?,?,?,? ) \n ");
		sqlArgs.add(oaTodoLogDTO.getDocId());
		sqlArgs.add(oaTodoLogDTO.getWorkId());
		sqlArgs.add(oaTodoLogDTO.getUserId());
		sqlArgs.add(oaTodoLogDTO.getTitle());
		sqlArgs.add(oaTodoLogDTO.getStartTime());
		sqlArgs.add(oaTodoLogDTO.getTodoUrl());
		sqlArgs.add(oaTodoLogDTO.getPri());
		sqlArgs.add(oaTodoLogDTO.getTodoType());
		sqlArgs.add(oaTodoLogDTO.getDocType());
		sqlArgs.add(oaTodoLogDTO.getSender());
		sqlArgs.add(oaTodoLogDTO.getSourceId());
		sqlArgs.add(oaTodoLogDTO.getSysId());
		sqlArgs.add(oaTodoLogDTO.getCloseTime());
//		sqlArgs.add(oaTodoLogDTO.getLastUpdateDate());
		
		sqlArgs.add(oaTodoLogDTO.getBeginSendTime());
		sqlArgs.add(oaTodoLogDTO.getEndSendTime());
		sqlArgs.add(oaTodoLogDTO.getEamMsg());
		sqlArgs.add(oaTodoLogDTO.getResultCode());
		sqlArgs.add(oaTodoLogDTO.getResultDesc());

		sqlModel.setSqlStr(sql.toString());
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
}
