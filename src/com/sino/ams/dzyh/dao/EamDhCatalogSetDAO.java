package com.sino.ams.dzyh.dao;


import java.sql.Connection;
import java.util.Date;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.dzyh.dto.EamDhCatalogSetDTO;
import com.sino.ams.dzyh.model.EamDhCatalogSetModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EamDhCatalogSetDAO</p>
 * <p>Description:程序自动生成服务程序“EamDhCatalogSetDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Administrator
 * @version 1.0
 */


public class EamDhCatalogSetDAO extends AMSBaseDAO {

	/**
	 * 功能：低值易耗品类别表(EAM) EAM_DH_CATALOG_SET 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamDhCatalogSetDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EamDhCatalogSetDAO(SfUserDTO userAccount, EamDhCatalogSetDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		EamDhCatalogSetDTO dtoPara = (EamDhCatalogSetDTO)dtoParameter;
		super.sqlProducer = new EamDhCatalogSetModel((SfUserDTO)userAccount, dtoPara);
	}

	public void createData() throws DataHandleException {
		EamDhCatalogSetDTO eamDto = (EamDhCatalogSetDTO)dtoParameter;
		String lastDate = (new Date()).toLocaleString();
		int enabled = eamDto.getEnabled();
		try
		{
			if (enabled == 0)
				eamDto.setEndDate(lastDate);
			else
				eamDto.setEndDate("");
		}
		catch (CalendarException ex)
		{
			ex.printLog();
		}
		super.createData();
	}

	public void updateData() throws DataHandleException {
		EamDhCatalogSetDTO eamDto = (EamDhCatalogSetDTO)dtoParameter;
		String lastDate = (new Date()).toLocaleString();
		int enabled = eamDto.getEnabled();
		try
		{
			if (enabled==0)
				eamDto.setEndDate(lastDate);
			else
				eamDto.setEndDate("");
		}
		catch (CalendarException ex)
		{
			ex.printLog();
		}
		super.updateData();
	}
}
