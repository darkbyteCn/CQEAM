package com.sino.ams.newasset.dao;


import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsRcvHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsRcvLineDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.model.AmsAssetsRcvLineModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: AmsAssetsRcvLineDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsRcvLineDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class AmsAssetsRcvLineDAO extends AMSBaseDAO {
	private AmsAssetsRcvHeaderDTO rcvHeader = null;

	/**
	 * 功能：资产调拨接收行表(用于部门间和公司间资产调拨) AMS_ASSETS_RCV_LINE 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsRcvLineDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsAssetsRcvLineDAO(SfUserDTO userAccount, AmsAssetsRcvLineDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsRcvLineDTO dto = (AmsAssetsRcvLineDTO) dtoParameter;
		sqlProducer = new AmsAssetsRcvLineModel((SfUserDTO) userAccount, dto);
	}

	public void setRcvHeader(AmsAssetsRcvHeaderDTO rcvHeader) {
		this.rcvHeader = rcvHeader;
	}

	/**
	 * 功能：根据接收分配行信息生成调拨接收单行信息
	 * @param assignLines DTOSet
	 * @throws DataHandleException
	 */
	public void createRcvLines(DTOSet assignLines) throws DataHandleException {
		try {
			AmsAssetsRcvLineModel modelProducer = (AmsAssetsRcvLineModel) sqlProducer;
			int lineCount = assignLines.getSize();
			AmsAssetsTransLineDTO assignLine = null;
			AmsAssetsRcvLineDTO rcvLine = null;
			SQLModel sqlModel = null;
			for (int i = 0; i < lineCount; i++) {
				assignLine = (AmsAssetsTransLineDTO) assignLines.getDTO(i);
				rcvLine = new AmsAssetsRcvLineDTO();
				rcvLine.setReceiveHeaderId(StrUtil.strToInt(rcvHeader.getReceiveHeaderId()));
				rcvLine.setTransLineId(assignLine.getLineId());
				rcvLine.setCurrCreationDate();
				rcvLine.setCreatedBy(userAccount.getUserId());
				setDTOParameter(rcvLine);
				sqlModel = modelProducer.getDataCreateModel();
				DBOperator.updateRecord(sqlModel, conn);
			}
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}
}
