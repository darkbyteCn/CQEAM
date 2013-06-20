package com.sino.ams.synchronize.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.synchronize.dto.AmsSynTmpDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AmsSynTmpModel extends AMSSQLProducer {

	public AmsSynTmpModel(BaseUserDTO userAccount, AmsSynTmpDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：构造数据插入SQL
	 * @return SQLModel
	 */
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		AmsSynTmpDTO dto = (AmsSynTmpDTO)dtoParameter;
		String sqlStr = "INSERT INTO AMS_SYN_TMP(SOURCE_STR, TARGET_STR) VALUES(?, ?)";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getSourceStr());
		sqlArgs.add(dto.getTargetStr());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造数据删除SQL
	 * @return SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "DELETE FROM AMS_SYN_TMP";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
}
