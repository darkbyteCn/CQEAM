package com.sino.soa.mis.srv.projectInfo.dao;


import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.mis.srv.projectInfo.dto.SrvProjectInfoDTO;
import com.sino.soa.mis.srv.projectInfo.model.SrvProjectInfoModel;

import java.sql.Connection;


/**
 * <p>Title: SrvProjectInfoDAO</p>
 * <p>Description:程序自动生成服务程序“SrvProjectInfoDAO”</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author zhoujs
 * @version 1.0
 */


public class SrvProjectInfoDAO extends AMSBaseDAO {

    /**
     * 功能：项目信息服务 SRV_PROJECT_INFO 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter SrvProjectInfoDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public SrvProjectInfoDAO(SfUserDTO userAccount, SrvProjectInfoDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SrvProjectInfoDTO dtoPara = (SrvProjectInfoDTO) dtoParameter;
        super.sqlProducer = new SrvProjectInfoModel((SfUserDTO) userAccount, dtoPara);
    }

	/**
	 * 
	 * @param projectNum
	 * @return
	 * @throws QueryException
	 */
    public boolean isProjecdtExists(String projectNum) throws QueryException {
        SrvProjectInfoModel projectInfoModel = (SrvProjectInfoModel)sqlProducer;
        boolean isExists = true;
        SQLModel sqlModel = new SQLModel();
        sqlModel = projectInfoModel.existsProjectModel(projectNum);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        isExists = sq.hasResult();

        return isExists;
    }

    

}