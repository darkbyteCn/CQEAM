package com.sino.ams.dzyh.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.dzyh.dto.EamDhCheckLineDTO;
import com.sino.ams.dzyh.model.DHLineDownloadModel;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
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
public class DHLineDownloadDAO extends AMSBaseDAO {

	public DHLineDownloadDAO(BaseUserDTO userAccount, EamDhCheckLineDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		EamDhCheckLineDTO dto = (EamDhCheckLineDTO)dtoParameter;
		sqlProducer = new DHLineDownloadModel(userAccount, dto);
	}


	/**
	 * 功能：获取下载的资产
	 * @return DTOSet
	 * @throws QueryException
	 */
	public DTOSet getDownloadAssets() throws QueryException {
		DHLineDownloadModel modelProducer = (DHLineDownloadModel)sqlProducer;
		SQLModel sqlModel = modelProducer.getDownloadAssetsModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setSql(sqlModel);
		simp.setDTOClassName(EamDhCheckLineDTO.class.getName());
		simp.executeQuery();
		return simp.getDTOSet();
	}
}
