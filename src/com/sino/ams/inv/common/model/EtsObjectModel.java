package com.sino.ams.inv.common.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.util.ArrUtil;

/**
 * <p>Title: EtsObjectModel</p>
 * <p>Description:仓库地点设置</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author V-yushibo
 * @version 1.0
 */
public class EtsObjectModel extends AMSSQLProducer {

	/**
     * 功能：资产地点表(EAM) ETS_OBJECT 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsObjectDTO 本次操作的数据
     */
	public EtsObjectModel(SfUserDTO userAccount, EtsObjectDTO dtoParameter) {
		super(userAccount, dtoParameter);
		// TODO Auto-generated constructor stub
	}

	/**
     * 功能：框架自动生成资产地点表(EAM) ETS_OBJECT数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO etsObject = (EtsObjectDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " ETS_OBJECT("
                + " WORKORDER_OBJECT_NO,"
                + " WORKORDER_OBJECT_CODE,"
                + " WORKORDER_OBJECT_NAME,"
                + " WORKORDER_OBJECT_LOCATION,"
                + " ORGANIZATION_ID,"
                + " COUNTY_CODE,"
                + " REMARK,"
                + " OBJECT_CATEGORY,"
                + " BUSINESS_CATEGORY,"
                + " CREATION_DATE,"
                + " CREATED_BY"
                + ") VALUES ("
                + "  NEWID() , ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(),?)";

        sqlArgs.add(etsObject.getWorkorderObjectCode());
        sqlArgs.add(etsObject.getWorkorderObjectName());
        sqlArgs.add(etsObject.getWorkorderObjectLocation());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(etsObject.getCountyCode());
        sqlArgs.add(etsObject.getRemark());
        sqlArgs.add(etsObject.getObjectCategory());
        sqlArgs.add(etsObject.getBusinessCategory());
        sqlArgs.add(userAccount.getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成资产地点表(EAM) ETS_OBJECT数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO etsObject = (EtsObjectDTO) dtoParameter;
        String sqlStr = "UPDATE ETS_OBJECT"
                + " SET"
                + " WORKORDER_OBJECT_CODE = ?,"
                + " WORKORDER_OBJECT_NAME = ?,"
                + " WORKORDER_OBJECT_LOCATION = ?,"
                + " ORGANIZATION_ID = ?,"
                + " COUNTY_CODE = ?,"
//                + " DISABLE_DATE = ?,"
                + " REMARK = ?,"
                + " OBJECT_CATEGORY = ?,"
                + " BUSINESS_CATEGORY = ?,"
                + " ISALL = ?,"
                + " IS_TEMP_ADDR = ?,"
//                + " CREATION_DATE = ?,"
//                + " CREATED_BY = ?,"
//                + " LAST_UPDATE_DATE = ?,"
                + " LAST_UPDATE_BY = ?,"
                + " PROJECT_ID = ?"
                + " WHERE"
                + " WORKORDER_OBJECT_NO = ?";

        sqlArgs.add(etsObject.getWorkorderObjectCode());
        sqlArgs.add(etsObject.getWorkorderObjectName());
        sqlArgs.add(etsObject.getWorkorderObjectLocation());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(etsObject.getCountyCode());
//        sqlArgs.add(etsObject.getDisableDate());
        sqlArgs.add(etsObject.getRemark());
        sqlArgs.add(etsObject.getObjectCategory());
        sqlArgs.add(etsObject.getBusinessCategory());
        sqlArgs.add(etsObject.getIsall());
        sqlArgs.add(etsObject.getIsTempAddr());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(etsObject.getProjectId());
        sqlArgs.add(etsObject.getWorkorderObjectNo());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成资产仓库表(EAM) ETS_OBJECT数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    /*public SQLModel getDataDeleteModel(){
         SQLModel sqlModel = new SQLModel();
         List sqlArgs = new ArrayList();
         EtsObjectDTO etsObject = (EtsObjectDTO)dtoParameter;
         String sqlStr = "DELETE FROM"
             + " ETS_OBJECT"
             + " WHERE"
             + " WORKORDER_OBJECT_NO = ?";
         sqlArgs.add(etsObject.getWorkorderObjectNo());
         sqlModel.setSqlStr(sqlStr);
         sqlModel.setArgs(sqlArgs);
         return sqlModel;
     }*/

    /**
     * 功能：框架自动生成资产仓库表(EAM) ETS_OBJECT数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO etsObject = (EtsObjectDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " WORKORDER_OBJECT_NO,"
                + " WORKORDER_OBJECT_CODE,"
                + " WORKORDER_OBJECT_NAME,"
                + " WORKORDER_OBJECT_LOCATION,"
                + " ORGANIZATION_ID,"
                + " COUNTY_CODE,"
                + " DISABLE_DATE,"
                + " REMARK,"
                + " OBJECT_CATEGORY,"
                + " BUSINESS_CATEGORY,"
                + " ISALL,"
                + " IS_TEMP_ADDR,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY,"
                + " PROJECT_ID"
                + " FROM"
                + " ETS_OBJECT"
                + " WHERE"
                + " WORKORDER_OBJECT_NO = ?";
        sqlArgs.add(etsObject.getWorkorderObjectNo());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：框架自动生成资产地点表(EAM) ETS_OBJECT页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO etsObject = (EtsObjectDTO) dtoParameter;
        
        String sqlStr = "";
        	   sqlStr = "SELECT" +
        	   			" EO.WORKORDER_OBJECT_NO," +
        	   			" EO.WORKORDER_OBJECT_CODE," +
        	   			" EO.WORKORDER_OBJECT_NAME," +
        	   			" EO.WORKORDER_OBJECT_LOCATION," +
        	   			" EC.COUNTY_NAME," +
        	   			" EFV.VALUE," +
        	   			" EO.DISABLE_DATE," +
        	   			" EO.ORGANIZATION_ID," +
        	   			" EO.OBJECT_CATEGORY," +
        	   			" EO.BUSINESS_CATEGORY" +
        	   			" FROM" +
        	   			" ETS_OBJECT EO," + 
        	   			" ETS_COUNTY EC," + 
        	   			" ETS_FLEX_VALUES EFV," + 
        	   			" ETS_FLEX_VALUE_SET EFVS," +
                        " ETS_OU_CITY_MAP    EOCM " +
        	   			" WHERE" +
                        " EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"     +
						" AND EC.COUNTY_CODE =* EO.COUNTY_CODE"           +
                        " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE" +
        	   			" AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)" +
        	   			" AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)" +
        	   			//" AND EO.WORKORDER_OBJECT_LOCATION LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)" +
        	   			" AND EO.ORGANIZATION_ID = ?" +
        	   			" AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.COUNTY_CODE LIKE ?)" +
        	   			" AND EO.OBJECT_CATEGORY LIKE dbo.NVL(?, EO.OBJECT_CATEGORY)" +
        	   			" AND EO.BUSINESS_CATEGORY LIKE dbo.NVL(?, EO.BUSINESS_CATEGORY)" +
        	   			" AND EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID" +
        	   			" AND EFV.CODE = EO.BUSINESS_CATEGORY" +
        	   			" AND EFVS.CODE = 'INV_BIZ_CATEGORY'" +
        	   			" AND EO.OBJECT_CATEGORY > ?" +
        	   			" AND EO.OBJECT_CATEGORY < ?";
        
        sqlArgs.add(etsObject.getWorkorderObjectCode());
        
        sqlArgs.add(etsObject.getWorkorderObjectName());
        
        //sqlArgs.add(etsObject.getWorkorderObjectLocation());
        sqlArgs.add(userAccount.getOrganizationId());
        
        sqlArgs.add(etsObject.getCountyCode());
        sqlArgs.add(etsObject.getCountyCode());
        
        sqlArgs.add(etsObject.getObjectCategory());
        
        sqlArgs.add(etsObject.getBusinessCategory());
        
        sqlArgs.add(WebAttrConstant.INV_CATEGORY);
        sqlArgs.add(DictConstant.NETADDR_OTHERS);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：执行批量失效操作。
     * @param workorderObjectCodes 仓库ID
     * @return SQLModel 返回数据用SQLModel
     */
    public SQLModel getDisabledModel(String[] workorderObjectCodes) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String orderno = ArrUtil.arrToSqlStr(workorderObjectCodes);
        String sqlStr = "UPDATE "
                + " ETS_OBJECT"
                + " SET"
                + " DISABLE_DATE = GETDATE()"
                + " WHERE"
                + " WORKORDER_OBJECT_CODE IN (" + orderno + ")";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：执行批量生效操作。
     * @param workorderObjectCodes 仓库ID
     * @return SQLModel 返回数据用SQLModel
     */
    public SQLModel getEfficientModel(String[] workorderObjectCodes) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String orderno = ArrUtil.arrToSqlStr(workorderObjectCodes);
        String sqlStr = "UPDATE "
                + " ETS_OBJECT"
                + " SET"
                + " DISABLE_DATE = NULL"
                + " WHERE"
                + " WORKORDER_OBJECT_CODE IN (" + orderno + ")";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：执行失效操作。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getInureModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO etsObject = (EtsObjectDTO) dtoParameter;
        String sqlStr = "UPDATE "
                + " ETS_OBJECT"
                + " SET"
                + " DISABLE_DATE = null"
                + " WHERE"
                + " WORKORDER_OBJECT_NO = ?";
        sqlArgs.add(etsObject.getWorkorderObjectNo());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：执行失效操作。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO etsObject = (EtsObjectDTO) dtoParameter;
        String sqlStr = "UPDATE "
                + " ETS_OBJECT"
                + " SET"
                + " DISABLE_DATE = GETDATE()"
                + " WHERE"
                + " WORKORDER_OBJECT_NO = ?";
        sqlArgs.add(etsObject.getWorkorderObjectNo());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：检查同组织下是否有相同类型仓库
     * @param objCategory 仓库类型
     * @return SQLModel
     */
    public SQLModel getTypeObjHasBeenModel(String objCategory) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT  EO.WORKORDER_OBJECT_CODE " +
                " FROM ETS_OBJECT EO " +
                " WHERE   EO.OBJECT_CATEGORY= ? " +
                " AND EO.ORGANIZATION_ID = ?" +
                " AND ROWNUM < 2 ";
        sqlArgs.add(objCategory);
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：检查WORKORDER_OBJECT_CODE是否重复
     * @param objCode 仓库代码
     * @return SQLModel
     */
    public SQLModel getCodeHasBeenModel(String objCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT 1 FROM ETS_OBJECT EO " +
                " WHERE EO.WORKORDER_OBJECT_CODE = ?  ";
        sqlArgs.add(objCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 向地点表(AMS_OBJECT_ADDRESS)中插入数据
     * @return
     */
    public SQLModel getAddressCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO etsObject = (EtsObjectDTO) dtoParameter;
        String sqlStr = "INSERT INTO AMS_OBJECT_ADDRESS\n" +
                "  (ADDRESS_ID,\n" +
                "   OBJECT_NO,\n" +
                "   BOX_NO,\n" +
                "   NET_UNIT,\n" +
                "   ORGANIZATION_ID,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY,\n" +
                "   REMARK,\n" +
                "   ADDRESS_NO)\n" +
                "  ( SELECT \n" +
                "    NEWID()  ADDRESS_ID,\n" +
                "   EO.WORKORDER_OBJECT_NO OBJECT_NO,\n" +
                "   '0000' BOX_NO,\n" +
                "   '0000' NET_UNIT,\n" +
                "   EO.ORGANIZATION_ID ORGANIZATION_ID,\n" +
                "   EO.CREATION_DATE CREATION_DATE,\n" +
                "   EO.CREATED_BY CREATED_BY,\n" +
                "   EO.REMARK REMARK,\n" +
                "   EO.WORKORDER_OBJECT_NO||'.0000.0000' ADDRESS_NO\n" +
                "   FROM ETS_OBJECT EO WHERE rownum<2 AND EO.WORKORDER_OBJECT_CODE = ?\n" +
                "   )";
        sqlArgs.add(etsObject.getWorkorderObjectCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 某仓库是否有设备
     * @param workorderObjectCode 仓库代码
     * @return SQLModel
     */
    public SQLModel getItemsByObjectModel(String workorderObjectCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                "  FROM AMS_SPARE_INFO ASI, ETS_OBJECT EO\n" +
                " WHERE ASI.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND (ASI.QUANTITY > 0 OR ASI.DISREPAIR_QUANTITY > 0)\n" +
                "   AND EO.WORKORDER_OBJECT_CODE =  ?\n";
        sqlArgs.add(workorderObjectCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
