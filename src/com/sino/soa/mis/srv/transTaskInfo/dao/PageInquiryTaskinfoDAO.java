package com.sino.soa.mis.srv.transTaskInfo.dao;


import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.mis.srv.transTaskInfo.dto.PageInquiryTaskinfoDTO;   
import com.sino.soa.mis.srv.transTaskInfo.model.PageInquiryTaskinfoModel;  

import java.sql.Connection;

/**
 * <p>Title: SrvTaskinfoDAO</p>
 * <p>Description:程序自动生成服务程序“SrvTaskinfoDAO”</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author wangzp
 * function:查询项目任务信息服务(分页) 
 */

public class PageInquiryTaskinfoDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：SRV_TASKINFO 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SrvTaskinfoDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public PageInquiryTaskinfoDAO(SfUserDTO userAccount, PageInquiryTaskinfoDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		PageInquiryTaskinfoDTO dtoPara = (PageInquiryTaskinfoDTO)dtoParameter;
		super.sqlProducer = new PageInquiryTaskinfoModel((SfUserDTO)userAccount, dtoPara);
	}
	
	/**
	 * 
	 * @param taskNumber
	 * @return (true:存在 false:不存在)
	 * @throws QueryException
	 */
	public boolean isProjectTaskExists(String taskNumber,String projectNum) throws QueryException{
		PageInquiryTaskinfoModel projectTaskInfoModel = new PageInquiryTaskinfoModel((SfUserDTO) userAccount, (PageInquiryTaskinfoDTO) dtoParameter);
	        boolean isExists = true;
	        SQLModel sqlModel = new SQLModel();
	        sqlModel = projectTaskInfoModel.existsProjectTaskModel(taskNumber, projectNum);
	        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
	        sq.executeQuery();
	        isExists = sq.hasResult();
	        return isExists;
	}
	
	
}