package com.sino.ams.system.house.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.system.house.dto.AmsItemFilesDTO;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: AmsItemFilesModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsItemFilesModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class AmsItemFilesModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：设备相关附件(EAM) AMS_ITEM_FILES 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsItemFilesDTO 本次操作的数据
	 */
	public AmsItemFilesModel(SfUserDTO userAccount, AmsItemFilesDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	/**
	 * 功能：框架自动生成设备相关附件(EAM) AMS_ITEM_FILES数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemFilesDTO amsItemFiles = (AmsItemFilesDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " AMS_ITEM_FILES("
			+ " BARCODE,"
			+ " FILE_DESC,"
			+ " FILE_PATH,"
			+ " SYSTEM_ID,"
			+ " CREATION_DATE,"
			+ " CREATED_BY"
			+ ") VALUES ("
			+ " ?, ?, ?, ?, GETDATE(), ?)";
		
		sqlArgs.add(amsItemFiles.getBarcode());
		sqlArgs.add(amsItemFiles.getFileDesc());
		sqlArgs.add(amsItemFiles.getFilePath());
		sqlArgs.add(amsItemFiles.getSystemId());
		sqlArgs.add(sfUser.getUserId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成设备相关附件(EAM) AMS_ITEM_FILES数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemFilesDTO amsItemFiles = (AmsItemFilesDTO)dtoParameter;
		String sqlStr = "UPDATE AMS_ITEM_FILES"
			+ " SET"
			+ " BARCODE = ?,"
			+ " FILE_DESC = ?,"
			+ " FILE_PATH = ?,"
			+ " LAST_UPDATE_DATE = GETDATE(),"
			+ " LAST_UPDATE_BY = ?"
			+ " WHERE"
			+ " SYSTEM_ID = ?";
		
		sqlArgs.add(amsItemFiles.getBarcode());
		sqlArgs.add(amsItemFiles.getFileDesc());
		sqlArgs.add(amsItemFiles.getFilePath());
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(amsItemFiles.getSystemId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成设备相关附件(EAM) AMS_ITEM_FILES数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemFilesDTO amsItemFiles = (AmsItemFilesDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
			+ " AMS_ITEM_FILES"
			+ " WHERE"
			+ " SYSTEM_ID = ?";
		sqlArgs.add(amsItemFiles.getSystemId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成设备相关附件(EAM) AMS_ITEM_FILES数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemFilesDTO amsItemFiles = (AmsItemFilesDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " BARCODE,"
			+ " FILE_DESC,"
			+ " FILE_PATH,"
			+ " SYSTEM_ID,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ " FROM"
			+ " AMS_ITEM_FILES"
			+ " WHERE"
			+ " SYSTEM_ID = ?";
		sqlArgs.add(amsItemFiles.getSystemId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成设备相关附件(EAM) AMS_ITEM_FILES多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getDataMuxModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemFilesDTO amsItemFiles = (AmsItemFilesDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " BARCODE,"
			+ " FILE_DESC,"
			+ " FILE_PATH,"
			+ " SYSTEM_ID,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ " FROM"
			+ " AMS_ITEM_FILES"
			+ " WHERE"
			+ "(? IS NULL OR BARCODE LIKE ?)"
			+ "(? IS NULL OR FILE_DESC LIKE ?)"
			+ "(? IS NULL OR FILE_PATH LIKE ?)"
			+ "(? IS NULL OR SYSTEM_ID LIKE ?)"
			+ "(? IS NULL OR CREATION_DATE LIKE ?)"
			+ "(? IS NULL OR CREATED_BY LIKE ?)"
			+ "(? IS NULL OR LAST_UPDATE_DATE LIKE ?)"
			+ "(? IS NULL OR LAST_UPDATE_BY LIKE ?)";
		sqlArgs.add(amsItemFiles.getBarcode());
		sqlArgs.add(amsItemFiles.getBarcode());
		sqlArgs.add(amsItemFiles.getFileDesc());
		sqlArgs.add(amsItemFiles.getFileDesc());
		sqlArgs.add(amsItemFiles.getFilePath());
		sqlArgs.add(amsItemFiles.getFilePath());
		sqlArgs.add(amsItemFiles.getSystemId());
		sqlArgs.add(amsItemFiles.getSystemId());
		sqlArgs.add(amsItemFiles.getCreationDate());
		sqlArgs.add(amsItemFiles.getCreationDate());
		sqlArgs.add(amsItemFiles.getCreatedBy());
		sqlArgs.add(amsItemFiles.getCreatedBy());
		sqlArgs.add(amsItemFiles.getLastUpdateDate());
		sqlArgs.add(amsItemFiles.getLastUpdateDate());
		sqlArgs.add(amsItemFiles.getLastUpdateBy());
		sqlArgs.add(amsItemFiles.getLastUpdateBy());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成设备相关附件(EAM) AMS_ITEM_FILES页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemFilesDTO amsItemFiles = (AmsItemFilesDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " BARCODE,"
			+ " FILE_DESC,"
			+ " FILE_PATH,"
			+ " SYSTEM_ID,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ " FROM"
			+ " AMS_ITEM_FILES"
			+ " WHERE"
			+ " (? IS NULL OR BARCODE LIKE ?)"
			+ "AND (? IS NULL OR FILE_DESC LIKE ?)"
			+ "AND (? IS NULL OR FILE_PATH LIKE ?)"
			+ "AND (? IS NULL OR SYSTEM_ID LIKE ?)"
			+ "AND (? IS NULL OR CREATION_DATE LIKE ?)"
			+ "AND (? IS NULL OR CREATED_BY LIKE ?)"
			+ "AND (? IS NULL OR LAST_UPDATE_DATE LIKE ?)"
			+ "AND (? IS NULL OR LAST_UPDATE_BY LIKE ?)";
		sqlArgs.add(amsItemFiles.getBarcode());
		sqlArgs.add(amsItemFiles.getBarcode());
		sqlArgs.add(amsItemFiles.getFileDesc());
		sqlArgs.add(amsItemFiles.getFileDesc());
		sqlArgs.add(amsItemFiles.getFilePath());
		sqlArgs.add(amsItemFiles.getFilePath());
		sqlArgs.add(amsItemFiles.getSystemId());
		sqlArgs.add(amsItemFiles.getSystemId());
		sqlArgs.add(amsItemFiles.getCreationDate());
		sqlArgs.add(amsItemFiles.getCreationDate());
		sqlArgs.add(amsItemFiles.getCreatedBy());
		sqlArgs.add(amsItemFiles.getCreatedBy());
		sqlArgs.add(amsItemFiles.getLastUpdateDate());
		sqlArgs.add(amsItemFiles.getLastUpdateDate());
		sqlArgs.add(amsItemFiles.getLastUpdateBy());
		sqlArgs.add(amsItemFiles.getLastUpdateBy());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


     /**
     * 功能：根据字段 barcode 构造查询数据SQL。
     * @param barcode String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByCompanyIdModel(String barcode){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
			+ " BARCODE,"
			+ " FILE_DESC,"
			+ " FILE_PATH,"
			+ " SYSTEM_ID,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ " FROM"
			+ " AMS_ITEM_FILES"
			+ " WHERE"
            + " BARCODE = ?";
        sqlArgs.add(barcode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据字段 barcode 获取数据
     * @param foreignKey 传入的外键字段名称。
     * @return SQLModel
     */
    public SQLModel getDataByForeignKeyModel(String foreignKey){
        SQLModel sqlModel = null;
        AmsItemFilesDTO amsItemFilesDTO = (AmsItemFilesDTO)dtoParameter;
        if(foreignKey.equals("barcode")){
            sqlModel = getDataByCompanyIdModel(amsItemFilesDTO.getBarcode());
        }
        return sqlModel;
    }

    /**
     * 功能：根据关联字段 barcode 构造数据删除SQL。
     * @param barcode String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDeleteByCompanyIdModel(String barcode){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
			          + " AMS_ITEM_FILES"
			          + " WHERE"
                      + " BARCODE = ?";
        sqlArgs.add(barcode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据字段barcode删除数据
     * @return SQLModel
     */
    public SQLModel getDeleteByForeignKeyModel(String foreignKey){
        SQLModel sqlModel = null;
        AmsItemFilesDTO amsItemFilesDTO = (AmsItemFilesDTO)dtoParameter;
        if(foreignKey.equals("barcode")){
            sqlModel = getDeleteByCompanyIdModel(amsItemFilesDTO.getBarcode());
        }
        return sqlModel;
    }

    /**
	 * 功能：框架自动生成设备相关附件(EAM) AMS_ITEM_FILES数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getCreateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemFilesDTO amsItemFiles = (AmsItemFilesDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " AMS_ITEM_FILES("
			+ " BARCODE,"
			+ " FILE_DESC,"
			+ " FILE_PATH,"
			+ " SYSTEM_ID,"
			+ " CREATION_DATE,"
			+ " CREATED_BY"
			+ ") VALUES ("
			+ " ?, ?, ?, ?, GETDATE(), ?)";

		sqlArgs.add(amsItemFiles.getBarcode());
		sqlArgs.add(amsItemFiles.getFileDesc());
		sqlArgs.add(amsItemFiles.getFilePath());
		sqlArgs.add(amsItemFiles.getSystemId());
		sqlArgs.add(sfUser.getUserId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


      /**
     * 功能：根据关联字段 barcode 构造数据删除SQL。
     * @param barcode String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getDeleteModel(String barcode){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
			          + " AMS_ITEM_FILES"
			          + " WHERE"
                      + " BARCODE = ?";
        sqlArgs.add(barcode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}