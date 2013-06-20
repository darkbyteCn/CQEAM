package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.match.dto.AmsMactPropertyDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: jiangtao
 * Date: 2007-11-21
 * Time: 11:55:32
 * To change this template use File | Settings | File Templates.
 */
public class AmsMactPropertyModel extends AMSSQLProducer {

	/**
	 * 功能：未匹配资产清单 数据库SQL构造层构造函数
	 *
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
	 */
	public AmsMactPropertyModel(SfUserDTO userAccount,
			AmsMactPropertyDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成已匹配资产清单 查询 。
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		AmsMactPropertyDTO dto = (AmsMactPropertyDTO) dtoParameter;
		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
			try {
				List sqlArgs = new ArrayList();
				String sqlStr = "SELECT"
						+ " EII.BARCODE, \n "
						+ " dbo.NVL(EFA.MIS_TAG_NUMBER,EFA.TAG_NUMBER) TAG_NUMBER, \n "
						+ " ESI.ITEM_NAME, \n "
						+ " EFA.ASSETS_DESCRIPTION, \n "
						+ " ESI.ITEM_SPEC, \n "
						+ " EFA.MODEL_NUMBER, \n "
						+ " EO.WORKORDER_OBJECT_CODE, \n "
						+ " EFA.ASSETS_LOCATION_CODE, \n "
						+ " EO.WORKORDER_OBJECT_LOCATION, \n "
						+ " EFA.ASSETS_LOCATION, \n "
						+ " AME.USER_NAME, \n "
						+ " EFA.ASSIGNED_TO_NAME, \n " +
                        "  EII.CONTENT_CODE,\n" +
                        "       EII.CONTENT_NAME,\n" +
                        "       EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 FA_CODE,\n" +
                        "       EFA.FA_CATEGORY2"
						//+ " ACCV1.COST_CENTER_NAME COST_NAME_AMS, \n "
						//+ " ACCV2.COST_CENTER_NAME COST_NAME_MIS"
						+ " FROM"
						//+ " AMS_COST_CENTER_V   ACCV1, \n "
						//+ " AMS_COST_CENTER_V   ACCV2, \n "
						+ " AMS_MIS_EMPLOYEE    AME, \n "
						+ " ETS_ITEM_MATCH_TD      EIM, \n "
						+ " ETS_FA_ASSETS_TD       EFA, \n "
						+ " ETS_ITEM_INFO       EII, \n "
						+ " AMS_OBJECT_ADDRESS  AOA, \n "
						+ " ETS_OBJECT          EO, \n "
						//+ " AMS_COST_DEPT_MATCH ACDM, \n "
						+ " ETS_SYSTEM_ITEM     ESI"
						+ " WHERE"
						+ " EIM.SYSTEMID = EII.SYSTEMID"
						+ " AND EII.ITEM_CODE = ESI.ITEM_CODE"
						+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
						+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
//						+ " AND (EO.OBJECT_CATEGORY < = 70 OR EO.OBJECT_CATEGORY = 80)"
						//2011.11.02去掉在途的过滤条件
						//+ " AND (  CONVERT( INT , EO.OBJECT_CATEGORY ) <= 70 OR  CONVERT( INT , EO.OBJECT_CATEGORY ) = 80 )"
						//+ " AND EII.RESPONSIBILITY_DEPT = ACDM.DEPT_CODE(+)"
						//+ " AND ACDM.COST_CENTER_CODE = ACCV1.COST_CENTER_CODE(+)"
						+ " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID "
						+ " AND EIM.ASSET_ID = EFA.ASSET_ID"
						//+ " AND SUBSTR(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ACCV2.COST_CENTER_CODE(+)"
						//+ " AND ((? IS NULL OR ACCV1.COST_CENTER_CODE LIKE ?)"
						//+ " OR (? IS NULL OR ACCV1.COST_CENTER_NAME LIKE ?)"
						//+ " OR (? IS NULL OR ACCV2.COST_CENTER_CODE LIKE ?)"
						//+ " OR (? IS NULL OR ACCV2.COST_CENTER_NAME LIKE ?))"
						+ " AND ( ? = '' OR ESI.ITEM_NAME LIKE ? OR ESI.ITEM_SPEC LIKE ? )"
						+ " AND ( ? = '' OR EII.BARCODE LIKE ? OR EFA.TAG_NUMBER LIKE ? OR  EFA.MIS_TAG_NUMBER LIKE ? )"
						+ " AND ( ? = '' OR EO.WORKORDER_OBJECT_NAME LIKE ? OR EO.WORKORDER_OBJECT_CODE LIKE ? )"
						
//						+ " AND (ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME) OR (? IS NULL OR ESI.ITEM_SPEC LIKE ?))"
//						+ " AND (EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE) OR EFA.TAG_NUMBER LIKE dbo.NVL(?, EFA.TAG_NUMBER) OR (? IS NULL OR EFA.MIS_TAG_NUMBER LIKE ?))"
//						+ " AND ((EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)) OR"
//						+ " (EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)))"
						+ " AND EO.ORGANIZATION_ID = ?" + " ORDER BY"
						+ " EO.WORKORDER_OBJECT_CODE DESC";
				/*
				sqlArgs.add(dto.getCostCenterName());
				sqlArgs.add(dto.getCostCenterName());
				sqlArgs.add(dto.getCostCenterName());
				sqlArgs.add(dto.getCostCenterName());
				sqlArgs.add(dto.getCostCenterName());
				sqlArgs.add(dto.getCostCenterName());
				sqlArgs.add(dto.getCostCenterName());
				sqlArgs.add(dto.getCostCenterName());
				*/
				sqlArgs.add(dto.getItemName());
				sqlArgs.add(dto.getItemName());
				sqlArgs.add(dto.getItemName());
				
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getBarcode());

				sqlArgs.add(dto.getWorkorderObjectName());
				sqlArgs.add(dto.getWorkorderObjectName());
				sqlArgs.add(dto.getWorkorderObjectName());
				
				sqlArgs.add(userAccount.getOrganizationId());

				sqlModel.setSqlStr(sqlStr);
				sqlModel.setArgs(sqlArgs);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			try {
				List sqlArgs = new ArrayList();
				String sqlStr = "SELECT \n "
						+ " EII.BARCODE, \n "
						+ " dbo.NVL(EFA.MIS_TAG_NUMBER,EFA.TAG_NUMBER) TAG_NUMBER, \n "
						+ " ESI.ITEM_NAME, \n "
						+ " EFA.ASSETS_DESCRIPTION, \n "
						+ " ESI.ITEM_SPEC, \n "
						+ " EFA.MODEL_NUMBER, \n "
						+ " EO.WORKORDER_OBJECT_CODE, \n "
						+ " EFA.ASSETS_LOCATION_CODE, \n "
						+ " EO.WORKORDER_OBJECT_LOCATION, \n "
						+ " EFA.ASSETS_LOCATION, \n "
						+ " AME.USER_NAME, \n "
						+ " EFA.ASSIGNED_TO_NAME, \n " +
                        "  EII.CONTENT_CODE,\n" +
                        "       EII.CONTENT_NAME,\n" +
                        "       EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 FA_CODE,\n" +
                        "       EFA.FA_CATEGORY2 \n "
						//+ " ACCV1.COST_CENTER_NAME COST_NAME_AMS, \n "
						//+ " ACCV2.COST_CENTER_NAME COST_NAME_MIS"
						+ " FROM \n "
						//+ " AMS_COST_CENTER_V   ACCV1, \n "
						//+ " AMS_COST_CENTER_V   ACCV2, \n " 
						+ " ETS_ITEM_MATCH      EIM (INDEX ETS_ITEM_MATCH_110708_U1),  \n "
						+ " ETS_FA_ASSETS       EFA (INDEX ETS_FA_ASSETS_PK),  \n "
						+ " ETS_ITEM_INFO       EII (INDEX ETS_ITEM_INFO_110708_N7),  \n "
						+ " AMS_OBJECT_ADDRESS  AOA (INDEX AMS_OBJECT_ADDRESS_N), \n " 
						+ " ETS_OBJECT          EO (INDEX ETS_OBJECT_11089119911), \n " 
						+ " AMS_MIS_EMPLOYEE    AME (INDEX AMS_MIS_EM_6748144351), \n " 
						+ " ETS_SYSTEM_ITEM     ESI (INDEX ETS_SYSTEM_ITEM_PK) \n "  
//						+ " ETS_ITEM_MATCH      EIM, \n "
//						+ " ETS_FA_ASSETS       EFA, \n "
//						+ " ETS_ITEM_INFO       EII, \n "
//						+ " AMS_OBJECT_ADDRESS  AOA (INDEX AMS_OBJECT_ADDRESS_N ), \n "
//						+ " ETS_OBJECT          EO, \n "
//						+ " AMS_MIS_EMPLOYEE    AME, \n "
//						//+ " AMS_COST_DEPT_MATCH ACDM, \n "
//						+ " ETS_SYSTEM_ITEM     ESI \n  "
						+ " WHERE \n "
						+ " EIM.SYSTEMID = EII.SYSTEMID \n " 
						+ " AND EII.ITEM_CODE = ESI.ITEM_CODE \n " 
						+ " AND EIM.ASSET_ID = EFA.ASSET_ID \n "
						+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n "
						+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n "
//						+ " AND (EO.OBJECT_CATEGORY < = 70 OR EO.OBJECT_CATEGORY = 80)"
						//2011.11.02去掉在途的过滤条件
						//+ " AND (  CONVERT( INT , EO.OBJECT_CATEGORY ) <= 70 OR  CONVERT( INT , EO.OBJECT_CATEGORY ) = 80 ) \n "
						//+ " AND EII.RESPONSIBILITY_DEPT = ACDM.DEPT_CODE(+)"
						//+ " AND ACDM.COST_CENTER_CODE = ACCV1.COST_CENTER_CODE(+)"
						+ " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID \n " 
						//+ " AND SUBSTR(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ACCV2.COST_CENTER_CODE(+)"
						//+ " AND ((? IS NULL OR ACCV1.COST_CENTER_CODE LIKE ?)"
						//+ " OR (? IS NULL OR ACCV1.COST_CENTER_NAME LIKE ?)"
						//+ " OR (? IS NULL OR ACCV2.COST_CENTER_CODE LIKE ?)"
						//+ " OR (? IS NULL OR ACCV2.COST_CENTER_NAME LIKE ?))"
						+ " AND ( ? = '' OR ESI.ITEM_NAME LIKE ? OR ESI.ITEM_SPEC LIKE ? ) \n "
						+ " AND ( ? = '' OR EII.BARCODE LIKE ? OR EFA.TAG_NUMBER LIKE ? OR  EFA.MIS_TAG_NUMBER LIKE ? ) \n "
						+ " AND ( ? = '' OR EO.WORKORDER_OBJECT_NAME LIKE ? OR EO.WORKORDER_OBJECT_CODE LIKE ? ) \n "
						
//						+ " AND (ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME) OR (? IS NULL OR ESI.ITEM_SPEC LIKE ?))"
//						+ " AND (EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE) OR EFA.TAG_NUMBER LIKE dbo.NVL(?, EFA.TAG_NUMBER) OR (? IS NULL OR EFA.MIS_TAG_NUMBER LIKE ?))"
//						+ " AND ((EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)) OR"
//						+ " (EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)))"
						
						+ " AND EO.ORGANIZATION_ID = ? \n " 
						+ " AND EII.ORGANIZATION_ID = ? \n " 
						+ " ORDER BY \n "
						+ " EO.WORKORDER_OBJECT_CODE DESC";
				/*
				sqlArgs.add(dto.getCostCenterName());
				sqlArgs.add(dto.getCostCenterName());
				sqlArgs.add(dto.getCostCenterName());
				sqlArgs.add(dto.getCostCenterName());
				sqlArgs.add(dto.getCostCenterName());
				sqlArgs.add(dto.getCostCenterName());
				sqlArgs.add(dto.getCostCenterName());
				sqlArgs.add(dto.getCostCenterName());
				*/
				sqlArgs.add(dto.getItemName());
				sqlArgs.add(dto.getItemName());
				sqlArgs.add(dto.getItemName());
				
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getBarcode());

				sqlArgs.add(dto.getWorkorderObjectName());
				sqlArgs.add(dto.getWorkorderObjectName());
				sqlArgs.add(dto.getWorkorderObjectName());
				
				sqlArgs.add(userAccount.getOrganizationId());
				sqlArgs.add(userAccount.getOrganizationId());

				sqlModel.setSqlStr(sqlStr);
				sqlModel.setArgs(sqlArgs);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return sqlModel;
	}

}
