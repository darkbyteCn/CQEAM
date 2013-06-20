package com.sino.ams.newasset.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.scrap.constant.ScrapAppConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;


/**
 * <p>Title: AmsAssetsTransLineModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsTransLineModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class AmsAssetsTransLineModel extends AMSSQLProducer {

	/**
	 * 功能：AMS_ASSETS_TRANS_LINE 数据库SQL构造层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsTransLineDTO 本次操作的数据
	 */
	public AmsAssetsTransLineModel(SfUserDTO userAccount, AmsAssetsTransLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成AMS_ASSETS_TRANS_LINE数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
			String sqlStr = "INSERT INTO"
							+ " AMS_ASSETS_TRANS_LINE("
							+ " LINE_ID,"
							+ " TRANS_ID,"
							+ " BARCODE,"
							+ " OLD_LOCATION,"
							+ " OLD_RESPONSIBILITY_USER,"
							+ " OLD_RESPONSIBILITY_DEPT,"
							+ " OLD_DEPRECIATION_ACCOUNT,"
							+ " OLD_FA_CATEGORY_CODE,"
							+ " ASSIGNED_TO_LOCATION,"
							+ " RESPONSIBILITY_USER,"
							+ " RESPONSIBILITY_DEPT,"
							+ " DEPRECIATION_ACCOUNT,"
							+ " FA_CATEGORY_CODE,"
							+ " LINE_STATUS,"
							+ " LINE_TRANS_DATE,"
							+ " LINE_REASON, "
							+ " REMARK,"
							+ " NET_UNIT,"
							+ " ASSET_ID,"
							+ " SOFT_INUSE_VERSION,"
							+ " SOFT_DEVALUE_VERSION,"
							+ " DEPRECIATION,"
							+ " DEPRN_COST,"
                            + " IMPAIR_RESERVE,"
                            + " MANUFACTURER_NAME,"
                            + " PREPARE_DEVALUE,"
                            + " RETIREMENT_COST," +
                              " REJECT_TYPE"
                            + ") VALUES ("
							+ " newid() , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?) \n" ;

			sqlArgs.add(dto.getTransId());
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getOldLocation());
			sqlArgs.add(dto.getOldResponsibilityUser());
			sqlArgs.add(dto.getOldResponsibilityDept());
			sqlArgs.add(dto.getOldDepreciationAccount());
			sqlArgs.add(dto.getOldFaCategoryCode());
			sqlArgs.add(dto.getAssignedToLocation());
			sqlArgs.add(dto.getResponsibilityUser());
			sqlArgs.add(dto.getResponsibilityDept());
			sqlArgs.add(dto.getDepreciationAccount());
			sqlArgs.add(dto.getFaCategoryCode());
			sqlArgs.add(dto.getLineStatus());
            if (dto.getLineTransDate().getCalendarValue().equals("")) {
                sqlArgs.add(null);
            } else {
                sqlArgs.add(dto.getLineTransDate());
            }
			sqlArgs.add(dto.getLineReason());
			sqlArgs.add(dto.getRemark());
			sqlArgs.add(dto.getNetUnit());
			sqlArgs.add(dto.getAssetId());
			sqlArgs.add(dto.getSoftInuseVersion());
			sqlArgs.add(dto.getSoftDevalueVersion());
			sqlArgs.add(dto.getDepreciation());
			sqlArgs.add(dto.getDeprnCost());
            sqlArgs.add(dto.getImpairReserve());
            sqlArgs.add(dto.getManufacturerName());
            sqlArgs.add(dto.getPrepareDevalue());
            sqlArgs.add(dto.getRetirementCost());
            sqlArgs.add(dto.getRejectType());
            
			if (dto.getTransType().equals(AssetsDictConstant.ASS_RFU)) { //紧急调拨(补汇总)
				sqlStr += 
				"	UPDATE AMS_ASSETS_TRANS_HEADER  SET TRANS_TYPE_DEFINE = 'RFUED' \n" +
				"	 WHERE TRANS_ID = ? \n" +
				"	   AND TRANS_TYPE = 'ASS-TRS' \n" ;
				sqlArgs.add(dto.getRemark());
			}
            
            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成AMS_ASSETS_TRANS_LINE数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		String sqlStr = "DELETE FROM"
						+ " AMS_ASSETS_TRANS_LINE"
						+ " WHERE"
						+ " LINE_ID = ?";
		sqlArgs.add(dto.getLineId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 transId 构造查询数据SQL。
	 * 框架自动生成数据AMS_ASSETS_TRANS_LINE详细信息查询SQLModel，请根据实际需要修改。
	 * @param transId String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByTransIdModel(String transId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		String transType = dto.getTransType();
		
		if( transType.equals( ScrapAppConstant.TRANS_TYPE )){
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
			sb.append("	  dbo.APP_GET_FLEX_VALUE(AATL.REJECT_TYPE, 'DIS_TYPE') REJECT_TYPE_NAME ,  \n ");
			sb.append("	  AATL.REMARK  \n ");
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
			
		} else if( transType.equals( DictConstant.ASS_RFU )){ //紧急调拨(补汇总) 查询单据资产信息
			sqlStr = 
				" SELECT TH.TRANS_STATUS, TH.TRANS_TYPE_DEFINE, TH.TRANS_ID, TH.TRANS_NO, TL.BARCODE, \n" +
				"		 AV.ASSET_NUMBER, AV.ASSET_ID, AV.ASSETS_DESCRIPTION, \n" +
				"        AV.MODEL_NUMBER, AV.CURRENT_UNITS, AV.UNIT_OF_MEASURE, \n" +
				"        TH.FROM_OBJECT_NO, TH.TO_OBJECT_NO, \n" +
				"        EO1.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME, EO2.WORKORDER_OBJECT_NAME TO_OBJECT_NAME \n" +
				"   FROM AMS_ASSETS_TRANS_HEADER TH, AMS_ASSETS_TRANS_LINE TL, dbo.AMS_ASSETS_ADDRESS_V_EX1 AV, \n" +
				"        ETS_OBJECT EO1, ETS_OBJECT EO2 \n" +
				"  WHERE TH.TRANS_ID = TL.TRANS_ID \n" +
				"    AND TH.TRANS_TYPE = 'ASS-TRS' \n" +
				"    AND (TH.TRANSFER_TYPE = 'INN_DEPT' OR LTRIM(TH.TRANSFER_TYPE) IS NULL) \n" +
				"    AND TH.TRANS_STATUS = 'ARCHIEVED' \n" +
				"    AND AV.BARCODE = TL.BARCODE \n" +
				"    AND TH.FROM_OBJECT_NO = EO1.WORKORDER_OBJECT_NO \n" +
				"    AND TH.TO_OBJECT_NO = EO2.WORKORDER_OBJECT_NO \n" +
				"    AND (LTRIM(TH.TRANS_TYPE_DEFINE) IS NULL OR LTRIM(TH.TRANS_TYPE_DEFINE) = 'RFUED') \n" +
				//"    AND (CASE TH.TRANS_TYPE_DEFINE WHEN NULL THEN '' ELSE TH.TRANS_TYPE_DEFINE END) <> 'RFUED' \n" +
			    "    AND TH.TRANS_ID <> ? \n" +
			    "	 AND EXISTS ( SELECT NULL FROM AMS_ASSETS_TRANS_HEADER TH1, AMS_ASSETS_TRANS_LINE TL1 \n" +
			    "	               WHERE TH1.TRANS_ID = TL1.TRANS_ID \n" +
			    "	                 AND TH1.TRANS_ID = ? \n" +
			    " 	                 AND TH1.TRANS_TYPE = 'ASS-RFU' \n" +
			    "	                 AND TL1.BARCODE = TL.BARCODE ) \n" +
			    "    AND TH.FROM_OBJECT_NO IN \n" +
			    "           (SELECT DISTINCT TL2.OLD_LOCATION FROM AMS_ASSETS_TRANS_HEADER TH2, AMS_ASSETS_TRANS_LINE TL2 \n" +
		        "             WHERE TH2.TRANS_ID = TL2.TRANS_ID \n" +
		        "               AND TH2.TRANS_ID = ? \n" +
		        "               AND TH2.TRANS_TYPE = 'ASS-RFU' ) \n" +
			    "    AND TH.TO_OBJECT_NO IN \n" +
			    "           (SELECT DISTINCT TL2.ASSIGNED_TO_LOCATION FROM AMS_ASSETS_TRANS_HEADER TH2, AMS_ASSETS_TRANS_LINE TL2 \n" +
		        "             WHERE TH2.TRANS_ID = TL2.TRANS_ID \n" +
		        "               AND TH2.TRANS_ID = ? \n" +
		        "               AND TH2.TRANS_TYPE = 'ASS-RFU' ) \n" +
				"  ORDER BY TH.TRANS_NO DESC, TL.BARCODE DESC \n" ;
			
			sqlArgs.add(transId);
			sqlArgs.add(transId);
			sqlArgs.add(transId);
			sqlArgs.add(transId);
			
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			return sqlModel;

		}else{
		
			sqlStr = "SELECT"
					 + " AATL.TRANS_ID,"
					 + " AATL.LINE_ID,"
					 + " AATL.BARCODE,"
					 + " AATL.NEW_BARCODE,"
					 + " AATL.REMARK,"
					 + " dbo.IS_IMPORTANT_ASSETS( AAAV.CONTENT_CODE ) IMPORTANT_FLAG, \n "   //是否重要资产
					 + " AATL.LINE_REASON,"
					 
					 //+ " AAAV.DEPRN_LEFT_MONTH,"
					 
					 + " AAAV.ASSET_NUMBER,"
					 + " AAAV.ASSETS_DESCRIPTION,"
					 + " AAAV.ITEM_NAME,"
					 + " AAAV.ITEM_QTY,"
					 + " AAAV.ITEM_SPEC,AAAV.START_DATE,AAAV.UNIT_OF_MEASURE,AAAV.WORKORDER_OBJECT_LOCATION,"
					 + " AAAV.MODEL_NUMBER,"
					 + " ISNULL(AAAV.CURRENT_UNITS, 1) CURRENT_UNITS,"
					 + " AAAV.VENDOR_NAME,"
					 + " AAAV.UNIT_OF_MEASURE,"
					 + " AAAV.COST,"
					 + " AAAV.DEPRN_COST,"
					 + " AAAV.DEPRECIATION,"
					 + " AAAV.DEPRECIATION SUM_DEPRECIATION,"
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
					 + " AATL.FA_CATEGORY_CODE,"
					 + " AATL.DEPRECIATION_ACCOUNT,"
					 + " AATL.LINE_TRANS_DATE,"
					 + " AATL.NET_UNIT,"
					 + " AATL.ASSET_ID,"
//					 + " AATL.REMARK,"
					 + " AATL.SOFT_INUSE_VERSION,"
					 + " AATL.SOFT_DEVALUE_VERSION,"
					 + " AATL.DEPRECIATION,"
					 + " AATL.DEPRN_COST,"
                     + " AATL.RETIREMENT_COST,"
                     + " AATL.IMPAIR_RESERVE,"
                     + " AATL.MANUFACTURER_NAME,"
                     + " AATL.PREPARE_DEVALUE,"
//					 + " (SELECT"
//					 + " AOA.ADDRESS_ID"
//					 + " FROM"
//					 + " AMS_OBJECT_ADDRESS AOA"
//					 + " WHERE"
//					 + " AOA.OBJECT_NO = EON.WORKORDER_OBJECT_NO"
//					 + " AND AOA.BOX_NO = '0000'"
//					 + " AND AOA.NET_UNIT = '0000'"
//					 + " ) ADDRESS_ID,"
                     + " AATL.REJECT_TYPE,"
                     + " dbo.APP_GET_FLEX_VALUE(AATL.REJECT_TYPE, 'DIS_TYPE') REJECT_TYPE_NAME  "
					 + " FROM"
					 + " AMS_ASSETS_TRANS_LINE AATL,"
					 + " AMS_MIS_EMPLOYEE      AMEO,"
					 + " AMS_MIS_DEPT          AMDO,"
					 + " ETS_OBJECT            EOO,"
					 + " AMS_MIS_EMPLOYEE      AMEN,"
					 + " AMS_MIS_DEPT          AMDN,"
					 + " ETS_OBJECT            EON,"
;
			if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
				sqlStr =sqlStr + " TD_ASSETS_ADDRESS_V  AAAV";
			} else {
				sqlStr =sqlStr + " AMS_ASSETS_ADDRESS_V  AAAV";
			}
			sqlStr =sqlStr
					 + " WHERE"
					 + " AATL.OLD_LOCATION *= EOO.WORKORDER_OBJECT_NO"
					 + " AND AATL.OLD_RESPONSIBILITY_USER *= AMEO.EMPLOYEE_ID"
					 + " AND AATL.OLD_RESPONSIBILITY_DEPT *= AMDO.DEPT_CODE"
					 + " AND AATL.ASSIGNED_TO_LOCATION *= EON.WORKORDER_OBJECT_NO"
					 + " AND AATL.RESPONSIBILITY_USER *= AMEN.EMPLOYEE_ID"
					 + " AND AATL.RESPONSIBILITY_DEPT *= AMDN.DEPT_CODE"
					 + " AND AATL.BARCODE = AAAV.BARCODE"
					 + " AND AATL.TRANS_ID = ?";
		}
		sqlArgs.add(transId);
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

	/**
	 * 功能：根据外键关联字段 transId 构造数据删除SQL。
	 * 框架自动生成数据AMS_ASSETS_TRANS_LINE数据删除SQLModel，请根据实际需要修改。
	 * @param transId String
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByTransIdModel(String transId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE "
						+ " FROM"
						+ " AMS_ASSETS_TRANS_LINE"
						+ " WHERE"
						+ " TRANS_ID = ?";
		sqlArgs.add(transId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键字段删除数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDeleteByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		if (foreignKey.equals("transId")) {
			sqlModel = getDeleteByTransIdModel(dto.getTransId());
		}
		return sqlModel;
	}

	/**
	 * 功能：获取判断行条码是否被保留
	 * @return SQLModel
	 */
	public SQLModel getHasReservedModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " 1"
						+ " FROM"
						+ " AMS_ASSETS_RESERVED AAR"
						+ " WHERE"
						+ " AAR.BARCODE = ?";
		sqlArgs.add(dto.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取判断行条码是否被保留
	 * @return SQLModel
	 */
	public SQLModel getCancelLinesByHeaderModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_TRANS_LINE  "
						+ " SET"
						+ " LINE_STATUS = ?"
						+ " WHERE"
						+ " TRANS_ID = ?";
		sqlArgs.add(AssetsDictConstant.CANCELED);
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取调拨单行折旧费用账户更新SQL(2008-12-01 17:43)
	 * @return SQLModel
	 */
	public SQLModel getAccountUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_TRANS_LINE"
						+ " SET"
						+ " DEPRECIATION_ACCOUNT = dbo.NVL(?, DEPRECIATION_ACCOUNT)"
						+ " WHERE"
						+ " TRANS_ID = ?"
						+ " AND BARCODE = ?";
		sqlArgs.add(dto.getDepreciationAccount());
		sqlArgs.add(dto.getTransId());
		sqlArgs.add(dto.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getTransLineUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_TRANS_LINE"
						+ " SET"
						+ " RESPONSIBILITY_USER = dbo.NVL(?, RESPONSIBILITY_USER),"
						+ " ASSIGNED_TO_LOCATION = dbo.NVL(?, ASSIGNED_TO_LOCATION),"
						+ " DEPRECIATION_ACCOUNT = dbo.NVL(?, DEPRECIATION_ACCOUNT),"
						+ " FA_CATEGORY_CODE = dbo.NVL(?, FA_CATEGORY_CODE)"
						+ " WHERE"
						+ " TRANS_ID = ?"
						+ " AND BARCODE = ?";
		sqlArgs.add(dto.getResponsibilityUser());
		sqlArgs.add(dto.getAssignedToLocation());
		sqlArgs.add(dto.getDepreciationAccount());
		sqlArgs.add(dto.getFaCategoryCode());
		sqlArgs.add(dto.getTransId());
		sqlArgs.add(dto.getBarcode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
