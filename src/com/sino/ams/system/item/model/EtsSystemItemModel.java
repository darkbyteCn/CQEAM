package com.sino.ams.system.item.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.item.dto.EtsSystemItemDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * <p>Title: EtsSystemItemModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsSystemItemModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class EtsSystemItemModel extends AMSSQLProducer {

    private EtsSystemItemDTO dto = null;

    /**
     * 功能：设备分类表(EAM) ETS_SYSTEM_ITEM 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsSystemItemDTO 本次操作的数据
     */
    public EtsSystemItemModel(SfUserDTO userAccount, EtsSystemItemDTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.dto = dtoParameter;
    }

    /**
     * 功能：框架自动生成设备分类表(EAM) ETS_SYSTEM_ITEM数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String isTmpCode = "N";
        if ((!userAccount.isProvinceUser()) && (!userAccount.isSysAdmin())) {
            isTmpCode = "N";
        }
        dto.setIsTmpCode(isTmpCode);
        String sqlStr = "INSERT INTO "
                + " ETS_SYSTEM_ITEM("
                + " ITEM_CODE,"
                + " ITEM_NAME,"
                + " ITEM_SPEC,"
                + " ITEM_CATEGORY,"
                + " MIS_ITEM_CODE,"
                + " ENABLED,"
                + " MEMO,"
                + " YEARS,"
                + " ITEM_UNIT,"
                + " VENDOR_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " MASTER_ORGANIZATION_ID,"
                + " IS_TMP_CODE,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + ") VALUES ("
                + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +SyBaseSQLUtil.getCurDate() + ", ?, ?, ?, " + SyBaseSQLUtil.getCurDate() + " , ?)";

        sqlArgs.add(dto.getItemCode());
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getItemSpec());
        sqlArgs.add(dto.getItemCategory());
        sqlArgs.add(dto.getMisItemCode());
        sqlArgs.add(dto.getEnabled());
        sqlArgs.add(dto.getMemo());
        sqlArgs.add(dto.getYears());
        sqlArgs.add(dto.getItemUnit());
        sqlArgs.add(dto.getVendorId());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(servletConfig.getProvinceOrgId());
        sqlArgs.add(dto.getIsTmpCode());
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成设备分类表(EAM) ETS_SYSTEM_ITEM数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_SYSTEM_ITEM"
                + " SET"
                + " ITEM_NAME = ?,"
                + " ITEM_SPEC = ?,"
                + " ITEM_CATEGORY = ?,"
                + " MIS_ITEM_CODE = ?,"
                + " ENABLED = ?,"
                + " MEMO = ?,"
                + " YEARS = ?,"
                + " ITEM_UNIT = ?,"
                + " VENDOR_ID = ?,"
                + " IS_FIXED_ASSETS = ?,"
                + " LAST_UPDATE_DATE = " +SyBaseSQLUtil.getCurDate() + ","
                + " LAST_UPDATE_BY = ?"
                + " WHERE"
                + " ITEM_CODE = ?";
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getItemSpec());
        sqlArgs.add(dto.getItemCategory());
        sqlArgs.add(dto.getMisItemCode());
        sqlArgs.add(dto.getEnabled());
        sqlArgs.add(dto.getMemo());
        sqlArgs.add(dto.getYears());
        sqlArgs.add(dto.getItemUnit());
        sqlArgs.add(dto.getVendorId());
        sqlArgs.add(dto.getIsFixedAssets());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getItemCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 功能：框架自动生成设备分类表(EAM) ETS_SYSTEM_ITEM数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " ETS_SYSTEM_ITEM"
                + " WHERE"
                + " ITEM_CODE = ?";
        sqlArgs.add(dto.getItemCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成设备分类表(EAM) ETS_SYSTEM_ITEM数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {          //点明细用的SQL
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " EMPV.VENDOR_NAME,"
                + " ESI.ITEM_CODE,"
                + " ESI.ITEM_NAME,"
                + " ESI.ITEM_SPEC,"
                + " ESI.ITEM_CATEGORY,"
                + " ESI.MIS_ITEM_CODE,"
                + " ESI.ENABLED,"
                + " ESI.MEMO,\n"
                + " ESI.YEARS,\n"
                + " ESI.ITEM_UNIT,\n"
                + " ESI.VENDOR_ID,\n"
                + " ESI.IS_FIXED_ASSETS,\n"
                + " ESI.CREATION_DATE,\n"
                + " ESI.CREATED_BY,\n"
                + " ESI.LAST_UPDATE_DATE,\n"
//                + " ESI.LAST_UPDATE_BY,\n"
                + SyBaseSQLUtil.getDBOwner() + "APP_GET_USER_NAME(ESI.LAST_UPDATE_BY) lastUpdateByName,\n"
                + " ESI.MASTER_ORGANIZATION_ID\n"
                + " FROM "
                + " ETS_SYSTEM_ITEM ESI "
                + " LEFT JOIN "
                + " ETS_MIS_PO_VENDORS EMPV\n"
                + " ON EMPV.VENDOR_ID  = ESI.VENDOR_ID "
                + " WHERE "
                + "  ITEM_CODE = ?";
        sqlArgs.add(dto.getItemCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成设备分类表(EAM) ETS_SYSTEM_ITEM多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getDataMuxModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " EFV.VALUE ITEM_TYPE,"
                + " EFV2.VALUE ITEM_UNIT,"
                + " ESI.ITEM_CODE,"
                + " ESI.ITEM_NAME,"
                + " ESI.ITEM_SPEC,"
                + " ESI.ITEM_CATEGORY,"
                + " ESI.MIS_ITEM_CODE,"
                + " ESI.ENABLED,"
                + " ESI.MEMO,"
                + " ESI.YEARS,"
                + " ESI.VENDOR_ID,"
                + " ESI.IS_FIXED_ASSETS,"
                + " ESI.CREATION_DATE,"
                + " ESI.CREATED_BY"
                + " FROM"
                + " ETS_SYSTEM_ITEM ESI,"
                + " ETS_FLEX_VALUES EFV,"
                + " ETS_FLEX_VALUES EFV2 "
                + " WHERE"
                + " ESI.ITEM_CATEGORY = EFV.CODE "
                + " AND ESI.ITEM_UNIT  = EFV2.CODE"
                + " AND EFV2.FLEX_VALUE_SET_ID = '19'"
                + " AND EFV.FLEX_VALUE_SET_ID = '1'"
                + " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.MIS_ITEM_CODE LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.VENDOR_ID LIKE ?)";
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getItemCategory());
        sqlArgs.add(dto.getItemCategory());
        sqlArgs.add(dto.getMisItemCode());
        sqlArgs.add(dto.getMisItemCode());
        sqlArgs.add(dto.getVendorId());
        sqlArgs.add(dto.getVendorId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成设备分类表(EAM) ETS_SYSTEM_ITEM页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        
        sqlStr=
	        	"SELECT   \n" +
	        	"        ESD.SYSTEM_ID, \n" +
				"        ESD.ORGANIZATION_ID, \n" +
				"        OU.COMPANY, \n" +
				"        ESI.ITEM_CODE, \n" +
				"        " + SyBaseSQLUtil.getDBOwner() + "APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, '"+DictConstant.ITEM_TYPE+"' ) ITEM_TYPE, \n" +
				"        ESI.ITEM_NAME, \n" +
				"        ESI.ITEM_SPEC, \n" +
				"        ESI.ITEM_CATEGORY, \n" +
				"        ESI.MIS_ITEM_CODE, \n" +
				"        ESI.ITEM_UNIT, \n" +  //ESI存的就是单位描述,直接取
				"        ESI.ENABLED, \n" +
				"        ESI.MEMO, \n" +
				"        ESI.YEARS, \n" +
				"        ESI.IS_FIXED_ASSETS, \n" +
				"        ESI.VENDOR_ID, \n" +
				"        " + SyBaseSQLUtil.getDBOwner() + "APP_GET_VENDOR_NAME(ESI.VENDOR_ID)  VENDOR_NAME, \n" +
				"        ESI.CREATION_DATE, \n" +
				"        ESI.CREATED_BY \n" +
				"  FROM  ETS_SYSITEM_DISTRIBUTE ESD, \n" +
				"        ETS_SYSTEM_ITEM ESI , \n" +
				"        ETS_OU_CITY_MAP OU \n" +
				" WHERE   \n" +
				"        ESD.ITEM_CODE=ESI.ITEM_CODE \n" +
				"   AND  ESD.ORGANIZATION_ID=OU.ORGANIZATION_ID \n" +
				
                //" AND ESI.ENABLED  ='Y'"+
				"   AND  ESI.IS_TMP_CODE  ='N' \n" ;  //不是临时设备
				if (!(userAccount.isProvinceUser() || userAccount.isSysAdmin())) {  //非省公司用户 //非系统管理员
					sqlStr= sqlStr+
				"   AND ESD.ORGANIZATION_ID =" + userAccount.getOrganizationId();
				}else{
					sqlStr= sqlStr+
					"   AND ESD.ORGANIZATION_ID =" + dto.getMasterOrganizationId();
				}
				
				if(dto.getItemName()!= null && !"".equals(dto.getItemName())){
					sqlStr= sqlStr+
				"   AND  ESI.ITEM_NAME LIKE ?   \n" ;
					 sqlArgs.add(dto.getItemName());
				}
				if(dto.getItemCategory()!= null && !"".equals(dto.getItemCategory())){
					sqlStr= sqlStr+
				"   AND  ESI.ITEM_CATEGORY LIKE ?   \n" ;
					 sqlArgs.add(dto.getItemCategory());
				}
				if(dto.getItemSpec()!= null && !"".equals(dto.getItemSpec())){
					sqlStr= sqlStr+
				"   AND  ESI.ITEM_SPEC LIKE ?  \n" ;
					sqlArgs.add(dto.getItemSpec());
				}
				if(dto.getVendorName()!= null && !"".equals(dto.getVendorName())){
					sqlStr= sqlStr+
				"   AND  " + SyBaseSQLUtil.getDBOwner() + "APP_GET_VENDOR_NAME(ESI.VENDOR_ID)  LIKE ? \n" ;
					sqlArgs.add(dto.getVendorName());
				}
				//sqlStr= sqlStr+" ORDER BY  ESI.ITEM_NAME ";
			       
			        
			       
			        
        /*      
        if ((userAccount.isProvinceUser()) || (userAccount.isSysAdmin())) {  //省公司用户
            sqlStr = "SELECT " + SyBaseSQLUtil.getDBOwner() + "APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, ?) ITEM_TYPE,\n" +
                    "       ESI.ITEM_UNIT,\n" +     //ESI存的就是单位描述,直接取
                    "       EMPV.VENDOR_ID,\n" +
                    "       EMPV.VENDOR_NAME,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       ESI.MIS_ITEM_CODE,\n" +
                    "       ESI.ENABLED,\n" +
                    "       ESI.MEMO,\n" +
                    "       ESI.YEARS,\n" +
                    "       ESI.IS_FIXED_ASSETS,\n" +
                    "       ESI.CREATION_DATE,\n" +
                    "       ESI.CREATED_BY\n" +
                    "  FROM ETS_SYSTEM_ITEM ESI LEFT JOIN  ETS_MIS_PO_VENDORS EMPV ON EMPV.VENDOR_ID = ESI.VENDOR_ID \n" +
                    " WHERE "
//                        + " AND ESI.ENABLED  ='Y'"
                    + " ESI.IS_TMP_CODE  ='N'"       //不是临时设备
                    + " AND ( " + SyBaseSQLUtil.nullStringParam() + " OR ESI.ITEM_NAME LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.nullStringParam() + " OR ESI.ITEM_CATEGORY LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.nullStringParam() + " OR ESI.ITEM_SPEC LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.nullStringParam() + " OR EMPV.VENDOR_NAME LIKE ?)";                   
        } else {                                                     //地市公司管理员
            sqlStr = "SELECT " + SyBaseSQLUtil.getDBOwner() + "APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, ?) ITEM_TYPE,\n" +
                    "       ESI.ITEM_UNIT,\n" +       //ESI存的就是单位描述,直接取
                    "       ESI.VENDOR_ID,\n" +
                    "       " + SyBaseSQLUtil.getDBOwner() + "APP_GET_VENDOR_NAME(ESI.VENDOR_ID) VENDOR_NAME,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ESI.ITEM_CATEGORY,\n" +
                    "       ESI.MIS_ITEM_CODE,\n" +
                    "       ESI.ENABLED,\n" +
                    "       ESI.MEMO,\n" +
                    "       ESI.YEARS,\n" +
                    "       ESI.IS_FIXED_ASSETS,\n" +
                    "       ESI.CREATION_DATE,\n" +
                    "       ESI.CREATED_BY\n" +
                    "  FROM ETS_SYSTEM_ITEM ESI, ETS_SYSITEM_DISTRIBUTE ESD\n" +
                    " WHERE ESI.ITEM_CODE = ESD.ITEM_CODE"
                    + " AND ESD.ORGANIZATION_ID =" + userAccount.getOrganizationId()
//                        + " AND ESI.ENABLED  ='Y'"
                    + " AND ESI.IS_TMP_CODE  ='N'"            //不是临时设备
                    + " AND ( " + SyBaseSQLUtil.nullStringParam() + " OR ESI.ITEM_NAME LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.nullStringParam() + " OR ESI.ITEM_CATEGORY LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.nullStringParam() + " OR ESI.ITEM_SPEC LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.nullStringParam() + " OR " + SyBaseSQLUtil.getDBOwner() + "APP_GET_VENDOR_NAME(ESI.VENDOR_ID) LIKE ?)";
           
        }
        sqlArgs.add(DictConstant.ITEM_TYPE);
        SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemName() );
        SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemCategory() );
        SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemSpec() );
        SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getVendorName() );
        */

        
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel getVerifyModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT"
                + " ESI.* "
                + " FROM"
                + " ETS_SYSTEM_ITEM ESI "
                + " WHERE"
                + " ESI.ITEM_NAME = ? ";
        sqlArgs.add(dto.getItemName());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
