package com.sino.ams.match.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.match.model.LastChangeViewModel;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.Row;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class LastChangeViewDAO extends AMSBaseDAO {

	public LastChangeViewDAO(BaseUserDTO userAccount, EtsItemInfoDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		EtsItemInfoDTO dto = (EtsItemInfoDTO)dtoParameter;
		sqlProducer = new LastChangeViewModel(user, dto);
	}

	/**
	 * 功能：获取上次变更信息
	 * @return String
	 * @throws QueryException
	 */
	public String getLastChangeInfo() throws QueryException {
		StringBuffer changeInfo = new StringBuffer();
		try {
			LastChangeViewModel modelProducer = (LastChangeViewModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getLastChangeModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				Row row = simp.getFirstRow();
				changeInfo.append("标签号：");
				changeInfo.append(row.getValue("BARCODE"));
				changeInfo.append("<br>变更用户：");
				changeInfo.append(row.getValue("LAST_CHG_USER"));
				changeInfo.append("<br>单据类型：");
				changeInfo.append(row.getValue("LAST_ORDER_TYPE"));
				changeInfo.append("<br>变更单据：");
				changeInfo.append(row.getValue("LAST_ORDER_NO"));
				changeInfo.append("<br>变更日期：");
				changeInfo.append(row.getValue("LAST_CHG_DATE"));
			}
		} catch (ContainerException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return changeInfo.toString();
	}
}
