package com.sino.td.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsMisTagChgDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class TdNewTagProduceModel extends AMSSQLProducer {
	public TdNewTagProduceModel(SfUserDTO userAccount, AmsMisTagChgDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：获取生成新标签SQL
	 * @return SQLModel
	 */
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		AmsMisTagChgDTO dto = (AmsMisTagChgDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO"
						+ " AMS_MIS_TAG_CHG("
						+ " ID,"
						+ " TAG_NUMBER_FROM,"
						+ " TAG_NUMBER_TO,"
						+ " REF_NUMBER,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " FROM_ORGANIZATION_ID,"
						+ " TO_ORGANIZATION_ID"
						+ ") VALUES( NEWID() , ?, ?, ?, GETDATE() , ?, ?, ?)";
		sqlArgs.add(dto.getTagNumberFrom());
		sqlArgs.add(dto.getTagNumberTo());
		sqlArgs.add(dto.getRefNumber());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getFromOrganizationId());
		sqlArgs.add(dto.getToOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取为调拨单行生成新标签的SQL
	 * @return SQLModel
	 */
	public SQLModel getTransLineUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		AmsMisTagChgDTO dto = (AmsMisTagChgDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " TD_ASSETS_TRANS_LINE AATL"
						+ " SET"
						+ " AATL.NEW_BARCODE = ?"
						+ " WHERE"
						+ " AATL.TRANS_ID = ?"
						+ " AND AATL.BARCODE = ?"
						+ " AND AATL.NEW_BARCODE  " + SyBaseSQLUtil.isNullNoParam() + " ";
		sqlArgs.add(dto.getTagNumberTo());
		sqlArgs.add(dto.getTransId());
		sqlArgs.add(dto.getTagNumberFrom());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
