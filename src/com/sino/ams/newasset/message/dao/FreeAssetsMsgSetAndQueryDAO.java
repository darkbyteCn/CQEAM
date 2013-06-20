package com.sino.ams.newasset.message.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.newasset.message.model.FreeAssetsMsgSetAndQueryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.newasset.message.dto.FreeAssetsMsgSetAndQueryDTO;

public class FreeAssetsMsgSetAndQueryDAO extends AMSBaseDAO {

	/**
	 * 功能：消息类别定义 SF_MSG_CATEGORY 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfMsgCategoryDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public FreeAssetsMsgSetAndQueryDAO(SfUserDTO userAccount, FreeAssetsMsgSetAndQueryDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		FreeAssetsMsgSetAndQueryDTO dtoPara = (FreeAssetsMsgSetAndQueryDTO)dtoParameter;
		super.sqlProducer = new FreeAssetsMsgSetAndQueryModel((SfUserDTO)userAccount, dtoPara);
	}

	public void createData() throws DataHandleException{
		FreeAssetsMsgSetAndQueryDTO dtoPara = (FreeAssetsMsgSetAndQueryDTO) dtoParameter;
		SeqProducer seqProducer = new SeqProducer(conn);
		//int msgCategoryId = seqProducer.getStrNextSeq("SF_MSG_CATEGORY");
		String msgCategoryId = "NEWID()";
		dtoPara.setMsgCategoryId(msgCategoryId);
		super.createData();
	}

	/**
	 * 功能：保存短消息设置
	 * @return boolean
	 */
	public boolean saveMessageData() {
		boolean operateResult = false;
		try {
			FreeAssetsMsgSetAndQueryDTO dtoPara = (FreeAssetsMsgSetAndQueryDTO) dtoParameter;
			//int msgCategoryId = dtoPara.getMsgCategoryId();
			String msgCategoryId = dtoPara.getMsgCategoryId();
			//if ( msgCategoryId < 1 ) {
			if ( msgCategoryId.equals("") ) {
				createData();
			} else {
				updateData();
			}
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} finally{
			if(operateResult){
				prodMessage(CustMessageKey.MESSAGE_SAVE_SUCCESS);
			} else {
				prodMessage(CustMessageKey.MESSAGE_SAVE_FAILURE);
			}
			message.setIsError(!operateResult);
		}
		return operateResult;
	}

	/**
	 * 功能：初始化短消息类别定义
	 * @return boolean
	 */
	public boolean initMessage(){
		boolean operateResult = false;
		try {
			FreeAssetsMsgSetAndQueryModel modelProducer = (FreeAssetsMsgSetAndQueryModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getMessageInitModel();
			DBOperator.updateRecord(sqlModel, conn);
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} finally {
			if(operateResult){
				prodMessage(CustMessageKey.MESSAGE_INIT_SUCCESS);
			} else {
				prodMessage(CustMessageKey.MESSAGE_INIT_FAILURE);
			}
			message.setIsError(!operateResult);
		}
		return operateResult;
	}
}
