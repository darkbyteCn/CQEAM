package com.sino.ams.sampling.bean;

import java.sql.Connection;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.QueryException;
import com.sino.base.web.DatabaseForWeb;


/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class SamplingOptProducer extends AssetsOptProducer {

	public SamplingOptProducer(SfUserDTO userAccount, Connection conn) {
		super(userAccount, conn);
	}

	/**
	 * 功能：获取指定任务未分配的OU组织下拉列表
	 * @param taskId String
	 * @return String
	 * @throws QueryException
	 */
	public String getTaskLeftOuOPt(String taskId) throws QueryException {
		SQLModel sqlModel = OptPrdSQLProducer.getTaskLeftOuOptModel(taskId);
		DatabaseForWeb db2web = new DatabaseForWeb(sqlModel, conn);
		return db2web.getOptionHtml();
	}

	/**
	 * 功能：获取指定任务已分配的OU组织下拉列表
	 * @param taskId String
	 * @return String
	 * @throws QueryException
	 */
	public String getTaskSampledOuOpt(String taskId) throws QueryException{
		SQLModel sqlModel = OptPrdSQLProducer.getTaskSampledOuOptModel(taskId);
		DatabaseForWeb db2web = new DatabaseForWeb(sqlModel, conn);
		return db2web.getOptionHtml();
	}

	/**
	 * 功能：构造工单状态下拉列表框
	 * @param selectedValue String
	 * @return String
	 * @throws QueryException
	 */
	public String getOrderStatusOpt(String selectedValue) throws QueryException{
		SQLModel sqlModel = OptPrdSQLProducer.getOrderStatusModel();
		DatabaseForWeb db2web = new DatabaseForWeb(sqlModel, conn);
		return db2web.getOptionHtml(selectedValue, true);
	}
}
