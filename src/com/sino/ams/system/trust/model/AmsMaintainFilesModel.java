package com.sino.ams.system.trust.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.trust.dto.AmsMaintainFilesDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: AmsMaintainFilesModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsMaintainFilesModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsMaintainFilesModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：代维公司相关文件 AMS_MAINTAIN_FILES 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsMaintainFilesDTO 本次操作的数据
	 */
	public AmsMaintainFilesModel(SfUserDTO userAccount, AmsMaintainFilesDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	/**
	 * 功能：框架自动生成代维公司相关文件 AMS_MAINTAIN_FILES数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsMaintainFilesDTO amsMaintainFiles = (AmsMaintainFilesDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " AMS_MAINTAIN_FILES("
			+ " SYSTEM_ID,"
			+ " FILE_DESCRIPTION,"
			+ " FILE_PATH,"
			+ " COMPANY_ID,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " FILE_NAME"
			+ ") VALUES ("
			+ "  NEWID() , ?, ?, ?, ?, ?, ?, ?)";
		
		sqlArgs.add(amsMaintainFiles.getFileDescription());
		sqlArgs.add(amsMaintainFiles.getFilePath());
		sqlArgs.add(amsMaintainFiles.getCompanyId());
		sqlArgs.add(amsMaintainFiles.getCreatedBy());
		sqlArgs.add(amsMaintainFiles.getLastUpdateDate());
		sqlArgs.add(amsMaintainFiles.getLastUpdateBy());
		sqlArgs.add(amsMaintainFiles.getFileName());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成代维公司相关文件 AMS_MAINTAIN_FILES数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsMaintainFilesDTO amsMaintainFiles = (AmsMaintainFilesDTO)dtoParameter;
		String sqlStr = "UPDATE AMS_MAINTAIN_FILES"
			+ " SET"
			+ " FILE_DESCRIPTION = ?,"
			+ " FILE_PATH = ?,"
			+ " COMPANY_ID = ?,"
			+ " CREATION_DATE = ?,"
			+ " CREATED_BY = ?,"
			+ " LAST_UPDATE_DATE = ?,"
			+ " LAST_UPDATE_BY = ?,"
			+ " FILE_NAME = ?"
			+ " WHERE"
			+ " SYSTEM_ID = ?";
		
		sqlArgs.add(amsMaintainFiles.getFileDescription());
		sqlArgs.add(amsMaintainFiles.getFilePath());
		sqlArgs.add(amsMaintainFiles.getCompanyId());
		sqlArgs.add(amsMaintainFiles.getCreationDate());
		sqlArgs.add(amsMaintainFiles.getCreatedBy());
		sqlArgs.add(amsMaintainFiles.getLastUpdateDate());
		sqlArgs.add(amsMaintainFiles.getLastUpdateBy());
		sqlArgs.add(amsMaintainFiles.getFileName());
		sqlArgs.add(amsMaintainFiles.getSystemId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成代维公司相关文件 AMS_MAINTAIN_FILES数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsMaintainFilesDTO amsMaintainFiles = (AmsMaintainFilesDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
			+ " AMS_MAINTAIN_FILES"
			+ " WHERE"
			+ " SYSTEM_ID = ?";
		sqlArgs.add(amsMaintainFiles.getSystemId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成代维公司相关文件 AMS_MAINTAIN_FILES数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsMaintainFilesDTO amsMaintainFiles = (AmsMaintainFilesDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " SYSTEM_ID,"
			+ " FILE_DESCRIPTION,"
			+ " FILE_PATH,"
			+ " COMPANY_ID,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " FILE_NAME"
			+ " FROM"
			+ " AMS_MAINTAIN_FILES"
			+ " WHERE"
			+ " SYSTEM_ID = ?";
		sqlArgs.add(amsMaintainFiles.getSystemId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成代维公司相关文件 AMS_MAINTAIN_FILES多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getDataMuxModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsMaintainFilesDTO amsMaintainFiles = (AmsMaintainFilesDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " SYSTEM_ID,"
			+ " FILE_DESCRIPTION,"
			+ " FILE_PATH,"
			+ " COMPANY_ID,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " FILE_NAME"
			+ " FROM"
			+ " AMS_MAINTAIN_FILES"
			+ " WHERE"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR SYSTEM_ID LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR FILE_DESCRIPTION LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR FILE_PATH LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR COMPANY_ID LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR FILE_NAME LIKE ?)";
		sqlArgs.add(amsMaintainFiles.getSystemId());
		sqlArgs.add(amsMaintainFiles.getSystemId());
		sqlArgs.add(amsMaintainFiles.getFileDescription());
		sqlArgs.add(amsMaintainFiles.getFileDescription());
		sqlArgs.add(amsMaintainFiles.getFilePath());
		sqlArgs.add(amsMaintainFiles.getFilePath());
		sqlArgs.add(amsMaintainFiles.getCompanyId());
		sqlArgs.add(amsMaintainFiles.getCompanyId());
		sqlArgs.add(amsMaintainFiles.getCreationDate());
		sqlArgs.add(amsMaintainFiles.getCreationDate());
		sqlArgs.add(amsMaintainFiles.getCreatedBy());
		sqlArgs.add(amsMaintainFiles.getCreatedBy());
		sqlArgs.add(amsMaintainFiles.getLastUpdateDate());
		sqlArgs.add(amsMaintainFiles.getLastUpdateDate());
		sqlArgs.add(amsMaintainFiles.getLastUpdateBy());
		sqlArgs.add(amsMaintainFiles.getLastUpdateBy());
		sqlArgs.add(amsMaintainFiles.getFileName());
		sqlArgs.add(amsMaintainFiles.getFileName());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


    /**
     * 功能：根据外键关联字段 companyId 构造查询数据SQL。
     * 框架自动生成数据代维公司相关文件 AMS_MAINTAIN_FILES详细信息查询SQLModel，请根据实际需要修改。
     * @param companyId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByCompanyIdModel(String  companyId){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
            + " SYSTEM_ID,"
            + " FILE_DESCRIPTION,"
            + " FILE_PATH,"
            + " CREATION_DATE,"
            + " CREATED_BY,"
            + " LAST_UPDATE_DATE,"
            + " LAST_UPDATE_BY,"
            + " FILE_NAME"
            + " FROM"
            + " AMS_MAINTAIN_FILES"
            + " WHERE"
            + " COMPANY_ID = ?";
        sqlArgs.add(companyId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键获取数据
     * @param foreignKey 传入的外键字段名称。
     * @return SQLModel
     */
    public SQLModel getDataByForeignKeyModel(String foreignKey){
        SQLModel sqlModel = null;
        AmsMaintainFilesDTO amsMaintainFiles = (AmsMaintainFilesDTO)dtoParameter;
        if(foreignKey.equals("companyId")){
            sqlModel = getDataByCompanyIdModel(amsMaintainFiles.getCompanyId());
        }
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 companyId 构造数据删除SQL。
     * 框架自动生成数据代维公司相关文件 AMS_MAINTAIN_FILES 数据删除SQLModel，请根据实际需要修改。
     * @param companyId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDeleteByCompanyIdModel(String  companyId){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                        + " AMS_MAINTAIN_FILES"
                        + " WHERE"
                        + " COMPANY_ID = ?";
        sqlArgs.add(companyId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键字段删除数据
     * @param foreignKey 传入的外键字段名称。
     * @return SQLModel
     */
    public SQLModel getDeleteByForeignKeyModel(String foreignKey){
        SQLModel sqlModel = null;
        AmsMaintainFilesDTO amsMaintainFiles = (AmsMaintainFilesDTO)dtoParameter;
        if(foreignKey.equals("companyId")){
            sqlModel = getDeleteByCompanyIdModel(amsMaintainFiles.getCompanyId());
        }
        return sqlModel;
    }
    
    /**
	 * 功能：框架自动生成代维公司相关文件 AMS_MAINTAIN_FILES页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsMaintainFilesDTO amsMaintainFiles = (AmsMaintainFilesDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " SYSTEM_ID,"
			+ " FILE_DESCRIPTION,"
			+ " FILE_PATH,"
			+ " COMPANY_ID,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " FILE_NAME"
			+ " FROM"
			+ " AMS_MAINTAIN_FILES"
			+ " WHERE"
			+ " ( " + SyBaseSQLUtil.isNull() + "  OR SYSTEM_ID LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR FILE_DESCRIPTION LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR FILE_PATH LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR COMPANY_ID LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR FILE_NAME LIKE ?)";
		sqlArgs.add(amsMaintainFiles.getSystemId());
		sqlArgs.add(amsMaintainFiles.getSystemId());
		sqlArgs.add(amsMaintainFiles.getFileDescription());
		sqlArgs.add(amsMaintainFiles.getFileDescription());
		sqlArgs.add(amsMaintainFiles.getFilePath());
		sqlArgs.add(amsMaintainFiles.getFilePath());
		sqlArgs.add(amsMaintainFiles.getCompanyId());
		sqlArgs.add(amsMaintainFiles.getCompanyId());
		sqlArgs.add(amsMaintainFiles.getCreationDate());
		sqlArgs.add(amsMaintainFiles.getCreationDate());
		sqlArgs.add(amsMaintainFiles.getCreatedBy());
		sqlArgs.add(amsMaintainFiles.getCreatedBy());
		sqlArgs.add(amsMaintainFiles.getLastUpdateDate());
		sqlArgs.add(amsMaintainFiles.getLastUpdateDate());
		sqlArgs.add(amsMaintainFiles.getLastUpdateBy());
		sqlArgs.add(amsMaintainFiles.getLastUpdateBy());
		sqlArgs.add(amsMaintainFiles.getFileName());
		sqlArgs.add(amsMaintainFiles.getFileName());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}