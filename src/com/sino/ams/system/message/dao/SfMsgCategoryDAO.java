package com.sino.ams.system.message.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.system.message.model.SfMsgCategoryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sms.dto.SfMsgCategoryDTO;

/**
 * <p>Title: SfMsgCategoryDAO</p>
 * <p>Description:程序自动生成服务程序“SfMsgCategoryDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class SfMsgCategoryDAO extends AMSBaseDAO {


	/**
	 * 功能：消息类别定义 SF_MSG_CATEGORY 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfMsgCategoryDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public SfMsgCategoryDAO(SfUserDTO userAccount, SfMsgCategoryDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		SfMsgCategoryDTO dtoPara = (SfMsgCategoryDTO)dtoParameter;
		super.sqlProducer = new SfMsgCategoryModel((SfUserDTO)userAccount, dtoPara);
	}

	public void createData() throws DataHandleException{
		try {
			SfMsgCategoryDTO dtoPara = (SfMsgCategoryDTO) dtoParameter;
			SeqProducer seqProducer = new SeqProducer(conn);
			int msgCategoryId = seqProducer.getStrNextSeq("SF_MSG_CATEGORY");
			dtoPara.setMsgCategoryId(msgCategoryId);
			super.createData();
		} catch (SQLException ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能：保存短消息设置
	 * @return boolean
	 */
	public boolean saveMessageData() {
		boolean operateResult = false;
		try {
			SfMsgCategoryDTO dtoPara = (SfMsgCategoryDTO) dtoParameter;
			int msgCategoryId = dtoPara.getMsgCategoryId();
			if ( msgCategoryId < 1 ) {
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
			SfMsgCategoryModel modelProducer = (SfMsgCategoryModel) sqlProducer;
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
