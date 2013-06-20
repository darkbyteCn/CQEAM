package com.sino.soa.td.srv.projectInfo.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.soa.td.srv.projectInfo.dto.TDSrvProjectInfoDTO;

/**
 * <p>Title: SrvProjectInfoModel</p>
 * <p>Description:程序自动生成SQL构造器“SrvProjectInfoModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author wangzp
 * @version 1.0
 */

public class TDSrvProjectInfoModel extends AMSSQLProducer {

    /**
     * 功能：项目信息服务 SRV_PROJECT_INFO 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter SrvProjectInfoDTO 本次操作的数据
     */
    public TDSrvProjectInfoModel(SfUserDTO userAccount, TDSrvProjectInfoDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：框架自动生成项目信息服务 SRV_PROJECT_INFO数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            TDSrvProjectInfoDTO srvProjectInfo = (TDSrvProjectInfoDTO) dtoParameter;
            String sqlStr = "INSERT INTO "
				+ " ETS_PA_PROJECTS_ALL("
				+ " PROJECT_ID,"
				+ " NAME,"
				+ " SEGMENT1,"
				+ " PROJECT_TYPE,"
				+ " PROJECT_STATUS_CODE,"
				+ " START_DATE,"
				+ " COMPLETION_DATE,"
				+ " ENABLED_FLAG,"
				+ " SOURCE,"
				+ " CREATION_DATE," 	
				+ " CREATED_BY,"
				+ " LAST_UPDATE_DATE,"
				+ " LAST_UPDATE_BY," 
				+ " MIS_PROJECT_ID,"
				+ " ORGANIZATION_ID,"
				+ " PROJECT_CLASS,"
				+ " DESCRIPTION,"
				+ " PROJECT_MANAGER,"
				+ " PM_PROJECT_REFERENCE,"
				+ " PM_PRODUCT_CODE"
				+ ") VALUES ("
				+ " NEWID(), ?, ?, ?, ?, ?, ?, ?, ?, ?" +
						", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
						")";
		
			sqlArgs.add(srvProjectInfo.getName());
			sqlArgs.add(srvProjectInfo.getSegment1());
			sqlArgs.add(srvProjectInfo.getProjectType());
			sqlArgs.add(srvProjectInfo.getProjectStatusCode());
			sqlArgs.add(srvProjectInfo.getStartDate());
			sqlArgs.add(srvProjectInfo.getCompletionDate());
			sqlArgs.add(srvProjectInfo.getEnabledFlag());
			sqlArgs.add("TDMIS"); 
			sqlArgs.add(srvProjectInfo.getCreationDate());
			sqlArgs.add(userAccount.getCreatedBy());
			sqlArgs.add(srvProjectInfo.getLastUpdateDate());
			sqlArgs.add(userAccount.getCreatedBy());
			sqlArgs.add(srvProjectInfo.getMisProjectId());
			sqlArgs.add(srvProjectInfo.getOrganizationId());
			sqlArgs.add(srvProjectInfo.getProjectClass());
			sqlArgs.add(srvProjectInfo.getDescription());
			sqlArgs.add(srvProjectInfo.getProjectManager());
			sqlArgs.add(srvProjectInfo.getPmProjectReference());
			sqlArgs.add(srvProjectInfo.getPmProductCode());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成项目信息服务 SRV_PROJECT_INFO数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            TDSrvProjectInfoDTO srvProjectInfo = (TDSrvProjectInfoDTO) dtoParameter;
            String sqlStr = "UPDATE ETS_PA_PROJECTS_ALL"
				+ " SET"
				+ " NAME = ?,"
				//+ " PROJECT_ID = ?,"
				+ " PROJECT_TYPE = ?,"
				+ " PROJECT_STATUS_CODE = ?,"
				+ " START_DATE = ?,"
				+ " COMPLETION_DATE = ?,"
				+ " ENABLED_FLAG = ?,"
//				+ " SOURCE = ?,"
//				+ " CREATION_DATE = ?,"
//
//				+ " CREATED_BY = ?,"
				+ " LAST_UPDATE_DATE = ?,"
				+ " LAST_UPDATE_BY = ?," 
				
//				+ " MIS_PROJECT_ID = ?,"
//				+ " ORGANIZATION_ID = ?,"
				//	+	""
				+ " PROJECT_CLASS = ?,"
				+ " DESCRIPTION = ?,"
				+ " PROJECT_MANAGER = ?,"
				+ " PM_PROJECT_REFERENCE = ?,"
				+ " PM_PRODUCT_CODE = ?"
				+ " WHERE"
				+ " SEGMENT1 = ?";
		
			sqlArgs.add(srvProjectInfo.getName());
			//sqlArgs.add(srvProjectInfo.getProjectId());
			sqlArgs.add(srvProjectInfo.getProjectType());
			sqlArgs.add(srvProjectInfo.getProjectStatusCode());
			sqlArgs.add(srvProjectInfo.getStartDate());
			sqlArgs.add(srvProjectInfo.getCompletionDate());
			sqlArgs.add(srvProjectInfo.getEnabledFlag());
//			sqlArgs.add("TDMIS");
//			sqlArgs.add(srvProjectInfo.getCreationDate());
//			sqlArgs.add(sfUser.getCreatedBy());
			sqlArgs.add(srvProjectInfo.getLastUpdateDate());
			sqlArgs.add(userAccount.getCreatedBy());
//			sqlArgs.add(srvProjectInfo.getMisProjectId());
//			sqlArgs.add(srvProjectInfo.getOrganizationId());
			sqlArgs.add(srvProjectInfo.getProjectClass());
			sqlArgs.add(srvProjectInfo.getDescription());
			sqlArgs.add(srvProjectInfo.getProjectManager());
			sqlArgs.add(srvProjectInfo.getPmProjectReference());
			sqlArgs.add(srvProjectInfo.getPmProductCode());
			sqlArgs.add(srvProjectInfo.getSegment1());
			
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成项目信息服务 SRV_PROJECT_INFO数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        TDSrvProjectInfoDTO srvProjectInfo = (TDSrvProjectInfoDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " ETS_PA_PROJECTS_ALL";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成项目信息服务 SRV_PROJECT_INFO数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		TDSrvProjectInfoDTO srvProjectInfo = (TDSrvProjectInfoDTO) dtoParameter;
		String sqlStr = "SELECT "
			+ " PROJECT_ID,"
			+ " NAME,"
			+ " SEGMENT1,"
			+ " PROJECT_TYPE,"
			+ " PROJECT_STATUS_CODE,"
			+ " START_DATE,"
			+ " COMPLETION_DATE,"
			+ " ENABLED_FLAG,"
			+ " SOURCE,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " MIS_PROJECT_ID,"
			+ " ORGANIZATION_ID,"
			+ " PROJECT_CLASS,"
			+ " DESCRIPTION,"
			+ " PROJECT_MANAGER,"
			+ " PM_PROJECT_REFERENCE,"
			+ " PM_PRODUCT_CODE"
			+ " FROM"
			+ " ETS_PA_PROJECTS_ALL"
			+ " WHERE"
			+ " PROJECT_ID = ?";
		sqlArgs.add(srvProjectInfo.getProjectId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
    /**
     * 功能：框架自动生成项目信息服务 SRV_PROJECT_INFO多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getMuxDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			TDSrvProjectInfoDTO srvProjectInfo = (TDSrvProjectInfoDTO) dtoParameter;
			String sqlStr = "SELECT "
				+ " PROJECT_ID,"
				+ " NAME,"
				+ " SEGMENT1,"
				+ " PROJECT_TYPE,"
				+ " PROJECT_STATUS_CODE,"
				+ " START_DATE,"
				+ " COMPLETION_DATE,"
				+ " ENABLED_FLAG,"
				+ " SOURCE,"
				+ " CREATION_DATE,"
				+ " CREATED_BY,"
				+ " LAST_UPDATE_DATE,"
				+ " LAST_UPDATE_BY,"
				+ " MIS_PROJECT_ID,"
				+ " ORGANIZATION_ID,"
				+ " PROJECT_CLASS,"
				+ " DESCRIPTION,"
				+ " PROJECT_MANAGER,"
				+ " PM_PROJECT_REFERENCE,"
				+ " PM_PRODUCT_CODE"
				+ " FROM"
				+ " ETS_PA_PROJECTS_ALL"
				+ " WHERE"
				+ " (? IS NULL OR PROJECT_ID LIKE ?)"
				+ " AND (? IS NULL OR NAME LIKE ?)"
				+ " AND (? IS NULL OR SEGMENT1 LIKE ?)"
				+ " AND (? IS NULL OR PROJECT_TYPE LIKE ?)"
				+ " AND (? IS NULL OR PROJECT_STATUS_CODE LIKE ?)"
				+ " AND (? IS NULL OR START_DATE LIKE ?)"
				+ " AND (? IS NULL OR COMPLETION_DATE LIKE ?)"
				+ " AND (? IS NULL OR ENABLED_FLAG LIKE ?)"
				+ " AND (? IS NULL OR SOURCE LIKE ?)"
				+ " AND (? IS NULL OR CREATION_DATE LIKE ?)"
				+ " AND (? IS NULL OR CREATED_BY LIKE ?)"
				+ " AND (? IS NULL OR LAST_UPDATE_DATE LIKE ?)"
				+ " AND (? IS NULL OR LAST_UPDATE_BY LIKE ?)"
				+ " AND (? IS NULL OR MIS_PROJECT_ID LIKE ?)"
				+ " AND (? IS NULL OR ORGANIZATION_ID LIKE ?)"
				+ " AND (? IS NULL OR PROJECT_CLASS LIKE ?)"
				+ " AND (? IS NULL OR DESCRIPTION LIKE ?)"
				+ " AND (? IS NULL OR PROJECT_MANAGER LIKE ?)"
				+ " AND (? IS NULL OR PM_PROJECT_REFERENCE LIKE ?)"
				+ " AND (? IS NULL OR PM_PRODUCT_CODE LIKE ?)";
			sqlArgs.add(srvProjectInfo.getProjectId());
			sqlArgs.add(srvProjectInfo.getProjectId());
			sqlArgs.add(srvProjectInfo.getName());
			sqlArgs.add(srvProjectInfo.getName());
			sqlArgs.add(srvProjectInfo.getSegment1());
			sqlArgs.add(srvProjectInfo.getSegment1());
			sqlArgs.add(srvProjectInfo.getProjectType());
			sqlArgs.add(srvProjectInfo.getProjectType());
			sqlArgs.add(srvProjectInfo.getProjectStatusCode());
			sqlArgs.add(srvProjectInfo.getProjectStatusCode());
			sqlArgs.add(srvProjectInfo.getStartDate());
			sqlArgs.add(srvProjectInfo.getStartDate());
			sqlArgs.add(srvProjectInfo.getCompletionDate());
			sqlArgs.add(srvProjectInfo.getCompletionDate());
			sqlArgs.add(srvProjectInfo.getEnabledFlag());
			sqlArgs.add(srvProjectInfo.getEnabledFlag());
			sqlArgs.add(srvProjectInfo.getSource());
			sqlArgs.add(srvProjectInfo.getSource());
			sqlArgs.add(srvProjectInfo.getCreationDate());
			sqlArgs.add(srvProjectInfo.getCreationDate());
			sqlArgs.add(srvProjectInfo.getCreatedBy());
			sqlArgs.add(srvProjectInfo.getCreatedBy());
			sqlArgs.add(srvProjectInfo.getLastUpdateDate());
			sqlArgs.add(srvProjectInfo.getLastUpdateDate());
			sqlArgs.add(srvProjectInfo.getLastUpdateBy());
			sqlArgs.add(srvProjectInfo.getLastUpdateBy());
			sqlArgs.add(srvProjectInfo.getMisProjectId());
			sqlArgs.add(srvProjectInfo.getMisProjectId());
			sqlArgs.add(srvProjectInfo.getOrganizationId());
			sqlArgs.add(srvProjectInfo.getOrganizationId());
			sqlArgs.add(srvProjectInfo.getProjectClass());
			sqlArgs.add(srvProjectInfo.getProjectClass());
			sqlArgs.add(srvProjectInfo.getDescription());
			sqlArgs.add(srvProjectInfo.getDescription());
			sqlArgs.add(srvProjectInfo.getProjectManager());
			sqlArgs.add(srvProjectInfo.getProjectManager());
			sqlArgs.add(srvProjectInfo.getPmProjectReference());
			sqlArgs.add(srvProjectInfo.getPmProjectReference());
			sqlArgs.add(srvProjectInfo.getPmProductCode());
			sqlArgs.add(srvProjectInfo.getPmProductCode());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

    /**
     * 功能：框架自动生成项目信息服务 SRV_PROJECT_INFO页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            TDSrvProjectInfoDTO srvProjectInfo = (TDSrvProjectInfoDTO) dtoParameter;
            String sqlStr = "SELECT "
				+ " PROJECT_ID,"
				+ " NAME,"
				+ " SEGMENT1,"
				+ " PROJECT_TYPE,"
				+ " PROJECT_STATUS_CODE,"
				+ " START_DATE,"
				+ " COMPLETION_DATE,"
				+ " ENABLED_FLAG,"
				+ " SOURCE,"
				+ " CREATION_DATE,"
				+ " CREATED_BY,"
				+ " LAST_UPDATE_DATE,"
				+ " LAST_UPDATE_BY,"
				+ " MIS_PROJECT_ID,"
				+ " ORGANIZATION_ID,"
				+ " PROJECT_CLASS,"
				+ " DESCRIPTION,"
				+ " PROJECT_MANAGER,"
				+ " PM_PROJECT_REFERENCE,"
				+ " PM_PRODUCT_CODE"
				+ " FROM"
				+ " ETS_PA_PROJECTS_ALL"
				+ " WHERE"
				+ " (? IS NULL OR PROJECT_ID LIKE ?)"
				+ " AND (? IS NULL OR NAME LIKE ?)"
				+ " AND (? IS NULL OR SEGMENT1 LIKE ?)"
				+ " AND (? IS NULL OR PROJECT_TYPE LIKE ?)"
				+ " AND (? IS NULL OR PROJECT_STATUS_CODE LIKE ?)"
				+ " AND (? IS NULL OR START_DATE LIKE ?)"
				+ " AND (? IS NULL OR COMPLETION_DATE LIKE ?)"
				+ " AND (? IS NULL OR ENABLED_FLAG LIKE ?)"
				+ " AND (? IS NULL OR SOURCE LIKE ?)"
				+ " AND (? IS NULL OR CREATION_DATE LIKE ?)"
				+ " AND (? IS NULL OR CREATED_BY LIKE ?)"
				+ " AND (? IS NULL OR LAST_UPDATE_DATE LIKE ?)"
				+ " AND (? IS NULL OR LAST_UPDATE_BY LIKE ?)"
				+ " AND (? IS NULL OR MIS_PROJECT_ID LIKE ?)"
				+ " AND (? IS NULL OR ORGANIZATION_ID LIKE ?)"
				+ " AND (? IS NULL OR PROJECT_CLASS LIKE ?)"
				+ " AND (? IS NULL OR DESCRIPTION LIKE ?)"
				+ " AND (? IS NULL OR PROJECT_MANAGER LIKE ?)"
				+ " AND (? IS NULL OR PM_PROJECT_REFERENCE LIKE ?)"
				+ " AND (? IS NULL OR PM_PRODUCT_CODE LIKE ?)";
			sqlArgs.add(srvProjectInfo.getProjectId());
			sqlArgs.add(srvProjectInfo.getProjectId());
			sqlArgs.add(srvProjectInfo.getName());
			sqlArgs.add(srvProjectInfo.getName());
			sqlArgs.add(srvProjectInfo.getSegment1());
			sqlArgs.add(srvProjectInfo.getSegment1());
			sqlArgs.add(srvProjectInfo.getProjectType());
			sqlArgs.add(srvProjectInfo.getProjectType());
			sqlArgs.add(srvProjectInfo.getProjectStatusCode());
			sqlArgs.add(srvProjectInfo.getProjectStatusCode());
			sqlArgs.add(srvProjectInfo.getStartDate());
			sqlArgs.add(srvProjectInfo.getStartDate());
			sqlArgs.add(srvProjectInfo.getCompletionDate());
			sqlArgs.add(srvProjectInfo.getCompletionDate());
			sqlArgs.add(srvProjectInfo.getEnabledFlag());
			sqlArgs.add(srvProjectInfo.getEnabledFlag());
			sqlArgs.add(srvProjectInfo.getSource());
			sqlArgs.add(srvProjectInfo.getSource());
			sqlArgs.add(srvProjectInfo.getCreationDate());
			sqlArgs.add(srvProjectInfo.getCreationDate());
			sqlArgs.add(srvProjectInfo.getCreatedBy());
			sqlArgs.add(srvProjectInfo.getCreatedBy());
			sqlArgs.add(srvProjectInfo.getLastUpdateDate());
			sqlArgs.add(srvProjectInfo.getLastUpdateDate());
			sqlArgs.add(srvProjectInfo.getLastUpdateBy());
			sqlArgs.add(srvProjectInfo.getLastUpdateBy());
			sqlArgs.add(srvProjectInfo.getMisProjectId());
			sqlArgs.add(srvProjectInfo.getMisProjectId());
			sqlArgs.add(srvProjectInfo.getOrganizationId());
			sqlArgs.add(srvProjectInfo.getOrganizationId());
			sqlArgs.add(srvProjectInfo.getProjectClass());
			sqlArgs.add(srvProjectInfo.getProjectClass());
			sqlArgs.add(srvProjectInfo.getDescription());
			sqlArgs.add(srvProjectInfo.getDescription());
			sqlArgs.add(srvProjectInfo.getProjectManager());
			sqlArgs.add(srvProjectInfo.getProjectManager());
			sqlArgs.add(srvProjectInfo.getPmProjectReference());
			sqlArgs.add(srvProjectInfo.getPmProjectReference());
			sqlArgs.add(srvProjectInfo.getPmProductCode());
			sqlArgs.add(srvProjectInfo.getPmProductCode());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

    
    public SQLModel existsProjectModel(String projectNum) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT * FROM ETS_PA_PROJECTS_ALL SPI WHERE SPI.SEGMENT1 = ?";

        sqlArgs.add(projectNum);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

}