package com.sino.ams.ct.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.ct.dto.EtsItemInfoDTO;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: EtsItemInfoModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsItemInfoModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 于士博
 * @version 1.0
 */
public class EtsItemInfoModel extends BaseSQLProducer {
	
	private SfUserDTO sfUser = null;

	/**
	 * 功能：标签号信息(EAM) ETS_ITEM_INFO 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsItemInfoDTO 本次操作的数据
	 */
	public EtsItemInfoModel(SfUserDTO userAccount, EtsItemInfoDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	/**
     * 功能：框架自动生成ETS_ITEM_INFO页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
	public SQLModel getPageQueryModel() throws SQLModelException {
		
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
		EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO) dtoParameter;
		String sqlStr = "";
        
        if (etsItemInfo.getQryType().equals(WebAttrConstant.BY_DAIWEI)) {
            sqlStr = "SELECT" +
                     " EII.BARCODE,\n" +
                     " ESI.ITEM_NAME,\n" +
                     " ESI.ITEM_SPEC,\n" +
                     " EO.WORKORDER_OBJECT_CODE,\n" +
                     " EO.WORKORDER_OBJECT_LOCATION,\n" +
                     " AMC.NAME\n" +
                     " FROM ETS_OBJECT EO,\n" +
                     " AMS_MAINTAIN_COMPANY AMC,\n" +
                     " AMS_MAINTAIN_RESPONSIBILITY AMR,\n" +
                     " AMS_OBJECT_ADDRESS AOA,\n" +
                     " ETS_ITEM_INFO EII,\n" +
                     " ETS_SYSTEM_ITEM ESI\n" +
                     " WHERE AMC.COMPANY_ID = AMR.COMPANY_ID\n" +
                     " AND AMR.OBJECT_NO = AOA.OBJECT_NO\n" +
                     " AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                     " AND AOA.ADDRESS_ID = EII.ADDRESS_ID\n" +
                     " AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                     // "AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)\n" +
                     // "AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
                     " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMC.NAME LIKE ?)\n" +
                     " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
                     " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)";
            		// sqlArgs.add(itemInfo.getItemName());
            		// sqlArgs.add(itemInfo.getItemName());
            		// sqlArgs.add(itemInfo.getItemSpec());
            		// sqlArgs.add(itemInfo.getItemSpec());
                    sqlArgs.add(etsItemInfo.getDaiwei());
                    sqlArgs.add(etsItemInfo.getDaiwei());
                    sqlArgs.add(etsItemInfo.getOrganizationId());
                    sqlArgs.add(etsItemInfo.getOrganizationId());
                    sqlArgs.add(etsItemInfo.getBarcode());
                    sqlArgs.add(etsItemInfo.getBarcode());
        } else {
        	/*
            sqlStr = "SELECT" +
                    " EII.SYSTEMID," +
                    " EII.BARCODE," +
//                    " TOC_CHAR(EII.START_DATE," +
//                    " 'YYYY-MM-DD') START_DATE," +
                    " TOC_CHAR(EFCA.DATE_PLACED_IN_SERVICE," +
                    " 'YYYY-MM-DD') DATE_PLACED_IN_SERVICE," +
                    " TOC_CHAR(EII.DISABLE_DATE," +
                    " 'YYYY-MM-DD') DISABLE_DATE," +
                    " EP.PROJECT_ID," +
                    " EP.NAME PROJECT_NAME," +
                    " EII.FINANCE_PROP," +
                    " EII.SENDTOMIS_FLAG," +
                    " ESI.ITEM_CODE," +
                    " ESI.ITEM_NAME," +
                    " ESI.ITEM_SPEC," +
                    // " EFV.VALUE ITEM_CATEGORY," +
                    //" MS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY," +
                    " ESI.ITEM_CATEGORY," +
                    " EO.WORKORDER_OBJECT_NAME," +
                    " AOA.ADDRESS_ID," +
                    " AOA.BOX_NO," +
                    " AOA.NET_UNIT," +
                    " EC.COUNTY_CODE," +
                    " EC.COUNTY_NAME" +
                    " FROM " +
                    " AMS_ASSETS_CT_ADDRESS_V AACAV" +
//                    " ETS_ITEM_INFO EII," +
//                    " ETS_FA_CT_ASSETS EFCA," +
//                    " ETS_OBJECT EO, " +
//                    " AMS_OBJECT_ADDRESS AOA," +
//                    " ETS_SYSTEM_ITEM ESI," +
//                    " ETS_PA_PROJECTS_ALL EP," +
//                    " ETS_COUNTY EC" +
                    // " ETS_FLEX_VALUES EFV," +
                    // " ETS_FLEX_VALUE_SET EFVS " +
                    " WHERE EII.ITEM_CODE = ESI.ITEM_CODE" +
                    " AND EII.ADDRESS_ID = AOA.ADDRESS_ID" +
                    // " AND EFVS.FLEX_VALUE_SET_ID=EFV.FLEX_VALUE_SET_ID" +
                    // " AND EFVS.CODE = 'ITEM_TYPE'\n" +
                    // " AND ESI.ITEM_CATEGORY = EFV.CODE " +
                    " AND EII.PROJECTID *= EP.PROJECT_ID" +
                    " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO" +
                    " AND EO.COUNTY_CODE *= EC.COUNTY_CODE" +
                    " AND EII.FINANCE_PROP = 'CTZC'";
            */
        	
        	sqlStr = "SELECT" +
        			 " AACAV.SYSTEMID," +
        			 " AACAV.BARCODE," +      
        			 " AACAV.DATE_PLACED_IN_SERVICE DATE_PLACED_IN_SERVICE," +
        			 " AACAV.DISABLE_DATE DISABLE_DATE," +
        			 " AACAV.PROJECT_ID," +
        			 " AACAV.PROJECT_NAME," +
        			 " AACAV.FINANCE_PROP," +
        			 " AACAV.SENDTOMIS_FLAG," +
        			 " AACAV.ITEM_CODE," +
        			 " AACAV.ITEM_NAME," +
        			 " AACAV.ITEM_SPEC," +
        			 " AACAV.ITEM_CATEGORY," +
        			 " AACAV.WORKORDER_OBJECT_NAME," +
        			 " AACAV.ADDRESS_ID," +
        			 " AACAV.BOX_NO," +
        			 " AACAV.NET_UNIT," +
        			 " AACAV.COUNTY_CODE," +
        			 " AACAV.COUNTY_NAME" +
        			 " FROM" +   
        			 " AMS_ASSETS_CT_ADDRESS_V AACAV" +
        			 " WHERE" +  
        			 " AACAV.FINANCE_PROP = 'CTZC'";

            if (etsItemInfo.getQryType().equals(WebAttrConstant.BY_PROJECTID)) {
            	
                sqlStr += 
//                		" AND ( " + SyBaseSQLUtil.isNull() + "  OR EP.NAME LIKE ?)\n" +
                		" AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.PROJECT_NAME LIKE ?)\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.ORGANIZATION_ID = ?)\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.ITEM_SPEC LIKE ?)";
                sqlArgs.add(etsItemInfo.getProjectName());
                //sqlArgs.add(etsItemInfo.getName());
                sqlArgs.add(etsItemInfo.getProjectName());
                //sqlArgs.add(etsItemInfo.getName());
                sqlArgs.add(etsItemInfo.getOrganizationId());
                sqlArgs.add(etsItemInfo.getOrganizationId());
                sqlArgs.add(etsItemInfo.getItemSpec());
                sqlArgs.add(etsItemInfo.getItemSpec());
            } else if (etsItemInfo.getQryType().equals(WebAttrConstant.BY_BARCODE)) {
            	
                sqlStr += " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.ORGANIZATION_ID = ?)\n" +
                        " AND (  " + SyBaseSQLUtil.isNull() + "  OR AACAV.BARCODE LIKE ?)";
                		// +"and rownum<100";
                sqlArgs.add(etsItemInfo.getOrganizationId());
                sqlArgs.add(etsItemInfo.getOrganizationId());
                sqlArgs.add(etsItemInfo.getBarcode());
                sqlArgs.add(etsItemInfo.getBarcode());
            } else if (etsItemInfo.getQryType().equals(WebAttrConstant.BY_SPEC)) {
            	
                sqlStr += " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.ORGANIZATION_ID = ? )\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.ITEM_SPEC LIKE ? )" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.ITEM_CATEGORY = ? )\n" +
//                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR EP.NAME LIKE ?)\n";
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.PROJECT_NAME LIKE ?)\n";
                sqlArgs.add(etsItemInfo.getOrganizationId());
                sqlArgs.add(etsItemInfo.getOrganizationId());
                sqlArgs.add(etsItemInfo.getItemSpec());
                sqlArgs.add(etsItemInfo.getItemSpec());
                sqlArgs.add(etsItemInfo.getItemCategory());
                sqlArgs.add(etsItemInfo.getItemCategory());
                sqlArgs.add(etsItemInfo.getProjectName());
                //sqlArgs.add(etsItemInfo.getName());
                sqlArgs.add(etsItemInfo.getProjectName());
                //sqlArgs.add(etsItemInfo.getName());
            } else if (etsItemInfo.getQryType().equals(WebAttrConstant.BY_CATEGORY)) {
            	
                sqlStr += " AND ( " + SyBaseSQLUtil.isNull() + "  OR  AACAV.ORGANIZATION_ID = ? )\n" +
//                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR  EII.FINANCE_PROP = ? )\n" +
//                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR  AACAV.DATE_PLACED_IN_SERVICE >= ? )\n" +
//                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR  AACAV.DATE_PLACED_IN_SERVICE <= ? )\n" +
                		" AND BETWEEN ISNULL(?, AACAV.DATE_PLACED_IN_SERVICE) AND ISNULL(?, AACAV.DATE_PLACED_IN_SERVICE)\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.PROJECT_NAME LIKE ?)\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.ITEM_SPEC LIKE ?)";
                sqlArgs.add(etsItemInfo.getOrganizationId());
                sqlArgs.add(etsItemInfo.getOrganizationId());
//              sqlArgs.add(etsItemInfo.getFinanceProp());
//              sqlArgs.add(etsItemInfo.getFinanceProp());
                sqlArgs.add(etsItemInfo.getMinTime());
                sqlArgs.add(etsItemInfo.getMinTime());
                sqlArgs.add(etsItemInfo.getMaxTime());
                sqlArgs.add(etsItemInfo.getMaxTime());
                sqlArgs.add(etsItemInfo.getProjectName());
                //sqlArgs.add(etsItemInfo.getName());
                sqlArgs.add(etsItemInfo.getProjectName());
                //sqlArgs.add(etsItemInfo.getName());
                sqlArgs.add(etsItemInfo.getItemSpec());
                sqlArgs.add(etsItemInfo.getItemSpec());
            } else if (etsItemInfo.getQryType().equals(WebAttrConstant.BY_LOCUS)) {
            	
                sqlStr += " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.ORGANIZATION_ID = ? )\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.OBJECT_CATEGORY = ? )\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.PROJECT_NAME LIKE ?)\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.ITEM_SPEC LIKE ?)" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.WORKORDER_OBJECT_NAME LIKE ?)" +
                        " AND (AACAV.OBJECT_CATEGORY < ? AACAV EO.OBJECT_CATEGORY = ?)";
                sqlArgs.add(etsItemInfo.getOrganizationId());
                sqlArgs.add(etsItemInfo.getOrganizationId());
                sqlArgs.add(etsItemInfo.getObjectCategory());
                sqlArgs.add(etsItemInfo.getObjectCategory());
                //sqlArgs.add(etsItemInfo.getProjectName());
                sqlArgs.add(etsItemInfo.getName());
                //sqlArgs.add(etsItemInfo.getProjectName());
                sqlArgs.add(etsItemInfo.getName());
                sqlArgs.add(etsItemInfo.getItemSpec());
                sqlArgs.add(etsItemInfo.getItemSpec());
                sqlArgs.add(etsItemInfo.getWorkorderObjectName());
                sqlArgs.add(etsItemInfo.getWorkorderObjectName());
                sqlArgs.add(WebAttrConstant.INV_CATEGORY);
                sqlArgs.add(AssetsDictConstant.NETADDR_OTHERS);

            } else if (etsItemInfo.getQryType().equals(WebAttrConstant.BY_ALLOT)) {
            	
                sqlStr += " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.OBJECT_CATEGORY = ? )\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.ORGANIZATION_ID = ? )\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.WORKORDER_OBJECT_NAME LIKE ?)" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.ITEM_SPEC LIKE ?)" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACAV.PROJECT_NAME LIKE ?)\n" +
                        " AND AACAV.OBJECT_CATEGORY > ?" +
                        " AND AACAV.OBJECT_CATEGORY < ?";
                sqlArgs.add(etsItemInfo.getObjectCategory());
                sqlArgs.add(etsItemInfo.getObjectCategory());
                sqlArgs.add(etsItemInfo.getOrganizationId());
                sqlArgs.add(etsItemInfo.getOrganizationId());
                sqlArgs.add(etsItemInfo.getWorkorderObjectName());
                sqlArgs.add(etsItemInfo.getWorkorderObjectName());
                sqlArgs.add(etsItemInfo.getItemSpec());
                sqlArgs.add(etsItemInfo.getItemSpec());
                sqlArgs.add(etsItemInfo.getProjectName());
                //sqlArgs.add(etsItemInfo.getName());
                sqlArgs.add(etsItemInfo.getProjectName());
                //sqlArgs.add(etsItemInfo.getName());
                sqlArgs.add(WebAttrConstant.INV_CATEGORY);
                sqlArgs.add(AssetsDictConstant.NETADDR_OTHERS);
            }
        }
        
        /*
        	List sqlArgs = new ArrayList();
			EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO) dtoParameter;
			String sqlStr = "";
			sqlStr = "SELECT"
					+" EII.BARCODE,"
					+" ESI.ITEM_NAME,"
					+" ESI.ITEM_SPEC,"
					+" ESI.ITEM_CATEGORY,"
					+" EII.START_DATE,"
					+" EPPA.NAME PROJECT_NAME,"
					+" EO.WORKORDER_OBJECT_NAME,"
					+" EC.COUNTY_NAME"
					+" FROM"
					+" ETS_ITEM_INFO EII,"
					+" ETS_SYSTEM_ITEM ESI,"
					+" ETS_PA_PROJECTS_ALL EPPA,"
					+" ETS_OBJECT EO,"
					+" ETS_COUNTY EC,"
					+" AMS_OBJECT_ADDRESS AOA"
					+" WHERE"
					+" EII.ITEM_CODE = ESI.ITEM_CODE"
					+" AND"
					+" EII.PROJECTID = EPPA.PROJECT_ID"
					+" AND"
					+" EII.ADDRESS_ID = AOA.ADDRESS_ID AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
					+" AND"
					+" EO.COUNTY_CODE *= EC.COUNTY_CODE";

        */
        
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
	
	/**
	 * 功能：根据标签号获取设备详细信息SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPrimaryKeyDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		EtsItemInfoDTO dto = (EtsItemInfoDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AACAV.*"
						+ " FROM"
						+ " AMS_ASSETS_CT_ADDRESS_V AACAV"
						+ " WHERE"
						+ " AACAV.BARCODE = ?";
		sqlArgs.add(dto.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
        
}
