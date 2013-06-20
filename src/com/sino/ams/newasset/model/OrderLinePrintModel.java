package com.sino.ams.newasset.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.scrap.constant.ScrapAppConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;


/**
 * <p>Title: OrderLinePrintModel</p>
 * <p>Description:程序自动生成SQL构造器“OrderLinePrintModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class OrderLinePrintModel extends AMSSQLProducer {
	private String printType = "";

	/**
	 * 功能：AMS_ASSETS_TRANS_LINE 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsTransLineDTO 本次操作的数据
	 */
	public OrderLinePrintModel(SfUserDTO userAccount, AmsAssetsTransLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：设置打印类型：调出打印还是调入打印(仅用于调拨单)
	 * @param printType String
	 */
	public void setPrintType(String printType) {
		this.printType = printType;
	}

	/**
	 * 功能：根据外键关联字段 transId 构造查询数据SQL。
	 * 框架自动生成数据AMS_ASSETS_TRANS_LINE详细信息查询SQLModel，请根据实际需要修改。
	 * @param transId String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByTransIdModel(String transId) {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		String transType = dto.getTransType();
		if (transType.equals(AssetsDictConstant.ASS_SUB)) { //资产减值单据
			sqlStr = "SELECT"
					 + " AATL.TRANS_ID,"
					 + " AATL.LINE_ID,"
					 + " AATL.BARCODE,"
					 + " AATL.REMARK,"
					 + " AAAV.ITEM_NAME,"
					 + " AAAV.ITEM_SPEC,"
					 + " AAAV.ASSET_NUMBER,"
					 + " AAAV.ASSETS_DESCRIPTION,"
					 + " AAAV.MODEL_NUMBER,"
					 + " dbo.NVL(CONVERT(VARCHAR,AAAV.CURRENT_UNITS), '1') CURRENT_UNITS,"
					 + " AAAV.VENDOR_NAME,"
					 + " AAAV.UNIT_OF_MEASURE,"
					 + " AAAV.COST,"
					 + " AAAV.DEPRN_COST,"
					 + " AAAV.DEPRECIATION,"
					 + " AAAV.DATE_PLACED_IN_SERVICE,"
					 + " AAAV.LIFE_IN_YEARS,"
					 + " AATL.FA_CATEGORY_CODE,"
					 + " AATL.DEPRECIATION_ACCOUNT,"
					 + " AATL.NET_UNIT,"
					 + " AATL.ASSET_ID,"
					 + " AATL.SOFT_INUSE_VERSION,"
					 + " AATL.SOFT_DEVALUE_VERSION,"
					 + " AATL.DEPRECIATION,"
					 + " AATL.DEPRN_COST,"
					 + " AATL.PREPARE_DEVALUE"
					 + " FROM"
					 + " AMS_ASSETS_TRANS_LINE AATL,"
					 + " AMS_ASSETS_ADDRESS_V  AAAV"
					 + " WHERE"
					 + " AATL.BARCODE = AAAV.BARCODE"
					 + " AND AATL.TRANS_ID = ?";
            sqlArgs.add(transId);
        }

		else if( transType.equals( ScrapAppConstant.TRANS_TYPE ) ){ //其他报废资产
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT  \n ");
			sb.append("	  AATL.TRANS_ID,  \n ");
			sb.append("	  AATL.LINE_ID,  \n ");
			sb.append("	  AATL.BARCODE,  \n ");
			sb.append("	  AATL.REJECT_TYPE,  \n ");
			sb.append("	  AATL.RETIREMENT_COST,  \n ");
			sb.append("	  EII.ITEM_CODE,  \n ");
			sb.append("   ESI.ITEM_NAME ASSETS_DESCRIPTION,  \n ");
			sb.append("	  ESI.ITEM_SPEC MODEL_NUMBER,  \n ");
	//		sb.append("	EO.WORKORDER_OBJECT_CODE,  \n ");
	//		sb.append("	EO.WORKORDER_OBJECT_NAME,  \n ");
			sb.append( "  EO.WORKORDER_OBJECT_CODE OLD_LOCATION_CODE, \n "); 
		    sb.append( "  EO.WORKORDER_OBJECT_LOCATION OLD_LOCATION_NAME,   \n ");  
		    
			sb.append( "  EII.RESPONSIBILITY_DEPT OLD_RESPONSIBILITY_DEPT, \n "); 
			sb.append( "  dbo.APP_GET_DEPT_NAME( EII.RESPONSIBILITY_DEPT ) OLD_RESPONSIBILITY_DEPT_NAME, \n "); 
			sb.append( "  EII.RESPONSIBILITY_USER OLD_RESPONSIBILITY_USER,     \n "); 
			sb.append( "  AME.USER_NAME OLD_RESPONSIBILITY_USER_NAME , \n "); 
			sb.append("	  EII.START_DATE DATE_PLACED_IN_SERVICE,  \n ");
			sb.append("	  dbo.APP_GET_FLEX_VALUE(AATL.REJECT_TYPE, 'DIS_TYPE') REJECT_TYPE_NAME   \n ");
			sb.append("FROM   \n ");
			sb.append("	    AMS_ASSETS_TRANS_LINE AATL ,  \n ");
			sb.append("	    ETS_ITEM_INFO EII ,  \n ");
			sb.append("	    ETS_SYSTEM_ITEM ESI,  \n ");
			sb.append("	    ETS_OBJECT EO ,  \n ");
			sb.append( "    AMS_MIS_EMPLOYEE AME,  \n "); 
			sb.append("	    AMS_OBJECT_ADDRESS AOA   \n ");
			sb.append("WHERE  \n ");
			sb.append("		AATL.TRANS_ID = ? \n  ");
			sb.append( "    AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID   \n "); 
			sb.append("		AND AATL.BARCODE = EII.BARCODE   \n ");
			sb.append("		AND EII.ITEM_CODE = ESI.ITEM_CODE   \n ");
			sb.append("		AND EII.ADDRESS_ID = AOA.ADDRESS_ID   \n ");
			sb.append("		AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO  \n ");
			
			sqlStr = sb.toString();
			sqlArgs.add(transId);
        }	
        else { //其他单据，暂不单列
/*			sqlStr = "SELECT"
					 + " AATL.TRANS_ID,"
					 + " AATL.LINE_ID,"
					 + " AATL.BARCODE,"
					 + " AATL.NEW_BARCODE,"
					 + " AATL.REMARK,"
					 + " AAAV.ASSET_NUMBER,"
					 + " AAAV.ITEM_NAME ASSETS_DESCRIPTION,"
					 + " AAAV.ITEM_SPEC MODEL_NUMBER,"
					 + " dbo.NVL(CONVERT(VARCHAR,AAAV.CURRENT_UNITS), '1') CURRENT_UNITS,"
					 + " AAAV.COST,"
					 + " AAAV.DEPRN_COST,"
					 + " AAAV.DEPRECIATION,"
					 + " AAAV.DATE_PLACED_IN_SERVICE,"
					 + " AAAV.LIFE_IN_YEARS,"
					 + " EOO.WORKORDER_OBJECT_NO OLD_LOCATION,"
					 + " EOO.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,"
					 + " AMEO.EMPLOYEE_ID OLD_RESPONSIBILITY_USER,"
					 + " AMEO.USER_NAME OLD_RESPONSIBILITY_USER_NAME,"
					 + " AMDO.DEPT_CODE OLD_RESPONSIBILITY_DEPT,"
					 + " AMDO.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,"
					 + " EON.WORKORDER_OBJECT_NO ASSIGNED_TO_LOCATION,"
					 + " EON.WORKORDER_OBJECT_NAME ASSIGNED_TO_LOCATION_NAME,"
					 + " AMEN.EMPLOYEE_ID RESPONSIBILITY_USER,"
					 + " AMEN.USER_NAME RESPONSIBILITY_USER_NAME,"
					 + " AMDN.DEPT_CODE RESPONSIBILITY_DEPT,"
					 + " AMDN.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
					 + " dbo.AASP_GET_TO_ORGNIZATION_ID(AATL.LINE_ID) TO_ORGANIZATION_ID,"
					 + " AATL.OLD_FA_CATEGORY_CODE,"
					 + " AATL.OLD_DEPRECIATION_ACCOUNT,"
					 + " dbo.NVL(AATL.FA_CATEGORY_CODE, AAAV.FA_CATEGORY_CODE) FA_CATEGORY_CODE,"
					 + " AATL.DEPRECIATION_ACCOUNT,"
					 + " AATL.LINE_TRANS_DATE,"
					 + " (SELECT"
					 + " AOA.ADDRESS_ID"
					 + " FROM"
					 + " AMS_OBJECT_ADDRESS AOA"
					 + " WHERE"
					 + " AOA.OBJECT_NO = EON.WORKORDER_OBJECT_NO"
					 + " AND AOA.BOX_NO = '0000'"
					 + " AND AOA.NET_UNIT = '0000'"
					 + " ) ADDRESS_ID"
					 + " FROM"
					 + " AMS_ASSETS_TRANS_LINE AATL,"
					 + " AMS_MIS_EMPLOYEE      AMEO,"
					 + " AMS_MIS_DEPT          AMDO,"
					 + " ETS_OBJECT            EOO,"
					 + " AMS_MIS_EMPLOYEE      AMEN,"
					 + " AMS_MIS_DEPT          AMDN,"
					 + " ETS_OBJECT            EON,"
					 + " dbo.AMS_ASSETS_ADDRESS_V  AAAV"
					 + " WHERE"
					 + " AATL.OLD_LOCATION = EOO.WORKORDER_OBJECT_NO"
					 + " AND AATL.OLD_RESPONSIBILITY_USER *= AMEO.EMPLOYEE_ID"
					 + " AND AATL.OLD_RESPONSIBILITY_DEPT *= AMDO.DEPT_CODE"
					 + " AND AATL.ASSIGNED_TO_LOCATION *= EON.WORKORDER_OBJECT_NO"
					 + " AND AATL.RESPONSIBILITY_USER *= AMEN.EMPLOYEE_ID"
					 + " AND AATL.RESPONSIBILITY_DEPT *= AMDN.DEPT_CODE"
					 + " AND AATL.BARCODE = AAAV.BARCODE"
					 + " AND AATL.TRANS_ID = ?";*/

			sqlStr = "SELECT \n"
				 + " AATL.NET_UNIT, \n"
				 + " AAAV.ASSET_NUMBER, \n"
				 + " AATL.BARCODE, \n"
				 + " AAAV.ITEM_NAME ASSETS_DESCRIPTION, \n"
				 + " AAAV.CONTENT_CODE, \n"
				 + " substring(AAAV.CONTENT_NAME, dbo.charindex_4('-', AAAV.CONTENT_NAME, 1, 3) + 1, 100) CONTENT_NAME, \n"
				 //+ " AAAV.CONTENT_NAME, \n"
				 + " AATL.MANUFACTURER_NAME, \n"
				 + " AAAV.ITEM_SPEC MODEL_NUMBER, \n"
				 + " AAAV.DATE_PLACED_IN_SERVICE, \n"
				 + " AAAV.UNIT_OF_MEASURE, \n"
				 + " AAAV.DEPRN_LEFT_MONTH, \n"
				 + " AAAV.LIFE_IN_YEARS * 12 LIFE_IN_YEARS, \n"
				 + " AATL.SOFT_INUSE_VERSION, \n"
				 + " AATL.SOFT_DEVALUE_VERSION, \n"

				 //+ " AAAV.COST COST, \n"
				 //+ " AAAV.DEPRECIATION DEPRECIATION, \n"
				 //+ " AAAV.COST - AAAV.DEPRECIATION NET_ASSET_VALUE, \n"
				 //+ " AAAV.IMPAIR_RESERVE IMPAIR_RESERVE, \n"
				 //+ " AAAV.COST - AAAV.DEPRECIATION - AAAV.IMPAIR_RESERVE DEPRN_COST, \n"
				 
				 + " ISNULL(CONVERT(DECIMAL(18,2), LTRIM(CONVERT(VARCHAR, AAAV.COST))), 0) COST, \n"
				 + " ISNULL(CONVERT(DECIMAL(18,2), LTRIM(CONVERT(VARCHAR, AAAV.DEPRECIATION))), 0) DEPRECIATION, \n"
				 + " ISNULL(CONVERT(DECIMAL(18,2), LTRIM(CONVERT(VARCHAR, AAAV.COST))), 0) - ISNULL(CONVERT(DECIMAL(18,2), LTRIM(CONVERT(VARCHAR, AAAV.DEPRECIATION))), 0) NET_ASSET_VALUE, \n"
				 + " ISNULL(CONVERT(DECIMAL(18,2), LTRIM(CONVERT(VARCHAR, AAAV.IMPAIR_RESERVE))), 0) IMPAIR_RESERVE, \n"
				 + " ISNULL(CONVERT(DECIMAL(18,2), LTRIM(CONVERT(VARCHAR, AAAV.COST))), 0) - ISNULL(CONVERT(DECIMAL(18,2), LTRIM(CONVERT(VARCHAR, AAAV.DEPRECIATION))), 0) - ISNULL(CONVERT(DECIMAL(18,2), LTRIM(CONVERT(VARCHAR, AAAV.IMPAIR_RESERVE))), 0) DEPRN_COST, \n"
				 + " ISNULL(CONVERT(DECIMAL(18,2), LTRIM(CONVERT(VARCHAR, AAAV.SCRAP_VALUE))), 0) SCRAP_VALUE, \n"
				 
				 + " AATL.REMARK, \n"
				 
				 + " AATL.TRANS_ID,"
				 + " AATL.LINE_ID,"
				 + " AATL.NEW_BARCODE,"
				 + " dbo.NVL(CONVERT(VARCHAR,AAAV.CURRENT_UNITS), '1') CURRENT_UNITS,"
				 + " EOO.WORKORDER_OBJECT_NO OLD_LOCATION,"
				 + " EOO.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,"
				 + " AMEO.EMPLOYEE_ID OLD_RESPONSIBILITY_USER,"
				 + " AMEO.USER_NAME OLD_RESPONSIBILITY_USER_NAME,"
				 + " AMDO.DEPT_CODE OLD_RESPONSIBILITY_DEPT,"
				 + " AMDO.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,"
				 + " EON.WORKORDER_OBJECT_NO ASSIGNED_TO_LOCATION,"
				 + " EON.WORKORDER_OBJECT_NAME ASSIGNED_TO_LOCATION_NAME,"
				 + " AMEN.EMPLOYEE_ID RESPONSIBILITY_USER,"
				 + " AMEN.USER_NAME RESPONSIBILITY_USER_NAME,"
				 + " AMDN.DEPT_CODE RESPONSIBILITY_DEPT,"
				 + " AMDN.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
				 + " dbo.AASP_GET_TO_ORGNIZATION_ID(AATL.LINE_ID) TO_ORGANIZATION_ID,"
				 + " AATL.OLD_FA_CATEGORY_CODE,"
				 + " AATL.OLD_DEPRECIATION_ACCOUNT,"
				 + " dbo.NVL(AATL.FA_CATEGORY_CODE, AAAV.FA_CATEGORY_CODE) FA_CATEGORY_CODE,"
				 + " AATL.DEPRECIATION_ACCOUNT,"
				 + " AATL.LINE_TRANS_DATE,"
				 + " (SELECT"
				 + " AOA.ADDRESS_ID"
				 + " FROM"
				 + " AMS_OBJECT_ADDRESS AOA"
				 + " WHERE"
				 + " AOA.OBJECT_NO = EON.WORKORDER_OBJECT_NO"
				 + " AND AOA.BOX_NO = '0000'"
				 + " AND AOA.NET_UNIT = '0000'"
				 + " ) ADDRESS_ID"
				 + " FROM"
				 + " AMS_ASSETS_TRANS_LINE AATL,"
				 + " AMS_MIS_EMPLOYEE      AMEO,"
				 + " AMS_MIS_DEPT          AMDO,"
				 + " ETS_OBJECT            EOO,"
				 + " AMS_MIS_EMPLOYEE      AMEN,"
				 + " AMS_MIS_DEPT          AMDN,"
				 + " ETS_OBJECT            EON,"
				 + " dbo.AMS_ASSETS_ADDRESS_V  AAAV"
				 + " WHERE"
				 + " AATL.OLD_LOCATION = EOO.WORKORDER_OBJECT_NO"
				 + " AND AATL.OLD_RESPONSIBILITY_USER *= AMEO.EMPLOYEE_ID"
				 + " AND AATL.OLD_RESPONSIBILITY_DEPT *= AMDO.DEPT_CODE"
				 + " AND AATL.ASSIGNED_TO_LOCATION *= EON.WORKORDER_OBJECT_NO"
				 + " AND AATL.RESPONSIBILITY_USER *= AMEN.EMPLOYEE_ID"
				 + " AND AATL.RESPONSIBILITY_DEPT *= AMDN.DEPT_CODE"
				 + " AND AATL.BARCODE = AAAV.BARCODE"
				 + " AND AATL.TRANS_ID = ?";
        	
            sqlArgs.add(transId);
            if (transType.equals(AssetsDictConstant.ASS_RED)) {
				if (printType.equals(AssetsWebAttributes.PRINT_TRANS_IN)) {
					sqlStr = sqlStr
							 + " AND  " + SyBaseSQLUtil.isNotNull("AATL.CONFIRM_DATE") + " "   ;
							// + " AND AATL.RESPONSIBILITY_USER = ?";
					//sqlArgs.add(userAccount.getEmployeeId());
				} else if (printType.equals(AssetsWebAttributes.PRINT_TRANS_OUT)) {
					//权限控制放开
					/*sqlStr = sqlStr
							 + " AND EXISTS("
							 + " SELECT"
							 + " NULL"
							 + " FROM"
							 + " SF_ACT_LOG              SAL,"
							 + " AMS_ASSETS_TRANS_HEADER AATH"
							 + " WHERE"
							 + " AATH.TRANS_ID = SAL.APP_ID"
							 + " AND AATH.TRANS_NO = SAL.APPLY_NUMBER"
							 + " AND AATH.TRANS_ID = AATL.TRANS_ID"
							 +
							 " AND (SAL.CUR_USERID = ? OR SAL.COMPLETE_USER = ?))";
								   sqlArgs.add(userAccount.getUserId());
								   sqlArgs.add(userAccount.getUserId());*/
				}
			}
		}

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键获取数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		if (foreignKey.equals("transId")) {
			sqlModel = getDataByTransIdModel(dto.getTransId());
		}
		return sqlModel;
	}
}
