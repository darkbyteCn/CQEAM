package com.sino.ams.system.item.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.item.dto.EtsSysitemDistributeDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.util.ArrUtil;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsSysitemDistributeModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsSysitemDistributeModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */
//public class EtsSysitemDistributeModel  {
//    private EtsSysitemDistributeDTO etsSysitemDistribute = null;
//    private SfUserDTO SfUser = null;
//
//    public EtsSysitemDistributeModel(SfUserDTO etsSysitemDistribute) {
//        this.etsSysitemDistribute = etsSysitemDistribute;
//    }




public class EtsSysitemDistributeModel extends BaseSQLProducer {

	private EtsSysitemDistributeDTO etsSysitemDistribute = null;
	private SfUserDTO SfUser = null;

	/**
	 * 功能：物料组织分配表 ETS_SYSITEM_DISTRIBUTE 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsSysitemDistributeDTO 本次操作的数据
	 */
	public EtsSysitemDistributeModel(SfUserDTO userAccount, EtsSysitemDistributeDTO dtoParameter) {
		super(userAccount, dtoParameter);
		SfUser = userAccount;
		this.etsSysitemDistribute = (EtsSysitemDistributeDTO)dtoParameter;
	}

    /**
	 * 功能：框架自动生成物料组织分配表 ETS_SYSITEM_DISTRIBUTE数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
       if( SfUser.getOrganizationId() == SyBaseSQLUtil.ORG_ID  ){
        String sqlStr = "INSERT INTO "
			+ " ETS_SYSITEM_DISTRIBUTE("
			+ " SYSTEM_ID,"
			+ " ITEM_CODE,"
			+ " ORGANIZATION_ID,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
            + " LAST_UPDATE_DATE,"
            + " LAST_UPDATE_BY"
			+ ") VALUES ("
			+ "NEWID() , ?, ?, " +SyBaseSQLUtil.getCurDate() + ", ?, " +SyBaseSQLUtil.getCurDate() + ", ?)";
		
		sqlArgs.add(etsSysitemDistribute.getItemCode());
		sqlArgs.add(etsSysitemDistribute.getOrganizationId());
		sqlArgs.add(SfUser.getUserId());
		sqlArgs.add(SfUser.getUserId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
       }else{
          String sqlStr = "INSERT INTO "
			+ " ETS_SYSITEM_DISTRIBUTE("
			+ " SYSTEM_ID,"
			+ " ITEM_CODE,"
			+ " ORGANIZATION_ID,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
            + " IS_TMP,"
            + " LAST_UPDATE_DATE,"
            + " LAST_UPDATE_BY"
            + ") VALUES ("
            + "NEWID() , ?, ?, " +SyBaseSQLUtil.getCurDate() + ", ?,'Y', " +SyBaseSQLUtil.getCurDate() + ", ?)";

		sqlArgs.add(etsSysitemDistribute.getItemCode());
		sqlArgs.add(etsSysitemDistribute.getOrganizationId());
		sqlArgs.add(SfUser.getUserId());
		sqlArgs.add(SfUser.getUserId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs); 
       }
        return sqlModel;
	}

    /**
	 * 功能：
     * @param  distrDatas DTOSet
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public List getCreateOrgModel(DTOSet distrDatas){
        List sqModels = new ArrayList();
        EtsSysitemDistributeDTO dtoPara = null;
     if((SfUser.isProvinceUser()) || (SfUser.isSysAdmin())){     //省公司
        for (int i = 0; i < distrDatas.getSize(); i++){
            dtoPara = (EtsSysitemDistributeDTO)distrDatas.getDTO(i);        //获取指定索引的行
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = " INSERT INTO "
                + " ETS_SYSITEM_DISTRIBUTE("
                + " SYSTEM_ID,"
                + " ITEM_CODE,"
                + " ORGANIZATION_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + ") VALUES (NEWID(), ?, ?, " +SyBaseSQLUtil.getCurDate() + ", ?, " +SyBaseSQLUtil.getCurDate() + ", ?)";
            sqlArgs.add(dtoPara.getItemCode());
            sqlArgs.add(dtoPara.getOrganizationId());
            sqlArgs.add(SfUser.getUserId());
            sqlArgs.add(SfUser.getUserId());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            sqModels.add(sqlModel);
        }
     }else{                                                  //对于地市公司增加的设备分类 ，其属性为临时设备
         for (int i = 0; i < distrDatas.getSize(); i++){
            dtoPara = (EtsSysitemDistributeDTO)distrDatas.getDTO(i);        //获取指定索引的行
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = "INSERT INTO "
                + " ETS_SYSITEM_DISTRIBUTE("
                + " SYSTEM_ID,"
                + " ITEM_CODE,"
                + " ORGANIZATION_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " IS_TMP,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + ") VALUES ("
                + "NEWID()  , ?, ?, " +SyBaseSQLUtil.getCurDate() + ", ?,'N', " +SyBaseSQLUtil.getCurDate() + ", ?)";
            sqlArgs.add(dtoPara.getItemCode());
            sqlArgs.add(dtoPara.getOrganizationId());
            sqlArgs.add(SfUser.getUserId());
            sqlArgs.add(SfUser.getUserId());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            sqModels.add(sqlModel);
        }
     }
        return sqModels;
	}
    /**
	 * 功能：框架自动生成物料组织分配表 ETS_SYSITEM_DISTRIBUTE数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE ETS_SYSITEM_DISTRIBUTE"
			+ " SET"
			+ " ITEM_CODE = ?,"
			+ " ORGANIZATION_ID = ?,"
			+ " CREATION_DATE = ?,"
			+ " CREATED_BY = ?,"
			+ " LAST_UPDATE_DATE = ?,"
			+ " LAST_UPDATE_BY = ?"
			+ " WHERE"
			+ " SYSTEM_ID = ?";
		
		sqlArgs.add(etsSysitemDistribute.getItemCode());
		sqlArgs.add(etsSysitemDistribute.getOrganizationId());
		sqlArgs.add(etsSysitemDistribute.getCreationDate());
		sqlArgs.add(etsSysitemDistribute.getCreatedBy());
		sqlArgs.add(etsSysitemDistribute.getLastUpdateDate());
		sqlArgs.add(etsSysitemDistribute.getLastUpdateBy());
		sqlArgs.add(etsSysitemDistribute.getSystemId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成物料组织分配表 ETS_SYSITEM_DISTRIBUTE数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(String itemCode){
        SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM"
			+ " ETS_SYSITEM_DISTRIBUTE"
			+ " WHERE"
			+ " ITEM_CODE = ?";
		sqlArgs.add(itemCode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}

	/**
	 * 功能：框架自动生成物料组织分配表 ETS_SYSITEM_DISTRIBUTE数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel(String itemCode){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " SYSTEM_ID,"
			+ " ITEM_CODE,"
			+ " ORGANIZATION_ID,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ " FROM"
			+ " ETS_SYSITEM_DISTRIBUTE"
			+ " WHERE"
			+ " ITEM_CODE = ?";
		sqlArgs.add(itemCode);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成物料组织分配表 ETS_SYSITEM_DISTRIBUTE多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getDataMuxModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " SYSTEM_ID,"
			+ " ITEM_CODE,"
			+ " ORGANIZATION_ID,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ " FROM"
			+ " ETS_SYSITEM_DISTRIBUTE"
			+ " WHERE"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR SYSTEM_ID LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ITEM_CODE LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ORGANIZATION_ID LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
		sqlArgs.add(etsSysitemDistribute.getSystemId());
		sqlArgs.add(etsSysitemDistribute.getSystemId());
		sqlArgs.add(etsSysitemDistribute.getItemCode());
		sqlArgs.add(etsSysitemDistribute.getItemCode());
		sqlArgs.add(etsSysitemDistribute.getOrganizationId());
		sqlArgs.add(etsSysitemDistribute.getOrganizationId());
		sqlArgs.add(etsSysitemDistribute.getCreationDate());
		sqlArgs.add(etsSysitemDistribute.getCreationDate());
		sqlArgs.add(etsSysitemDistribute.getCreatedBy());
		sqlArgs.add(etsSysitemDistribute.getCreatedBy());
		sqlArgs.add(etsSysitemDistribute.getLastUpdateDate());
		sqlArgs.add(etsSysitemDistribute.getLastUpdateDate());
		sqlArgs.add(etsSysitemDistribute.getLastUpdateBy());
		sqlArgs.add(etsSysitemDistribute.getLastUpdateBy());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 itemCode 构造查询数据SQL。
	 * 框架自动生成数据物料组织分配表 ETS_SYSITEM_DISTRIBUTE详细信息查询SQLModel，请根据实际需要修改。
	 * @param itemCode String 
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByItemCodeModel(String itemCode){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " SYSTEM_ID,"
			+ " ORGANIZATION_ID,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ " FROM"
			+ " ETS_SYSITEM_DISTRIBUTE"
			+ " WHERE"
			+ " ITEM_CODE = ?";
		sqlArgs.add(itemCode);
		
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
		if(foreignKey.equals("itemCode")){
			sqlModel = getDataByItemCodeModel(etsSysitemDistribute.getItemCode());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成物料组织分配表 ETS_SYSITEM_DISTRIBUTE页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " SYSTEM_ID,"
			+ " ITEM_CODE,"
			+ " ORGANIZATION_ID,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ " FROM"
			+ " ETS_SYSITEM_DISTRIBUTE"
			+ " WHERE"
			+ " ( " + SyBaseSQLUtil.isNull() + "  OR SYSTEM_ID LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ITEM_CODE LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ORGANIZATION_ID LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
		sqlArgs.add(etsSysitemDistribute.getSystemId());
		sqlArgs.add(etsSysitemDistribute.getSystemId());
		sqlArgs.add(etsSysitemDistribute.getItemCode());
		sqlArgs.add(etsSysitemDistribute.getItemCode());
		sqlArgs.add(etsSysitemDistribute.getOrganizationId());
		sqlArgs.add(etsSysitemDistribute.getOrganizationId());
		sqlArgs.add(etsSysitemDistribute.getCreationDate());
		sqlArgs.add(etsSysitemDistribute.getCreationDate());
		sqlArgs.add(etsSysitemDistribute.getCreatedBy());
		sqlArgs.add(etsSysitemDistribute.getCreatedBy());
		sqlArgs.add(etsSysitemDistribute.getLastUpdateDate());
		sqlArgs.add(etsSysitemDistribute.getLastUpdateDate());
		sqlArgs.add(etsSysitemDistribute.getLastUpdateBy());
		sqlArgs.add(etsSysitemDistribute.getLastUpdateBy());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}



 	public SQLModel getDeleteItemModel(String[] itemCodes) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String orderno = ArrUtil.arrToSqlStr(itemCodes);
		String sqlStr =  "DELETE FROM ETS_SYSITEM_DISTRIBUTE WHERE ITEM_CODE IN (" + orderno + ")";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}