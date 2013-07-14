package com.sino.ams.yearchecktaskmanager.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.model.EtsFaAssetsModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dto.EtsItemYearCheckDTO;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;

public class AssetsYearCheckFaModel extends AMSSQLProducer {

	public AssetsYearCheckFaModel(SfUserDTO userAccount, EtsItemYearCheckDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}
	
	public SQLModel getLineModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemYearCheckDTO dto = (EtsItemYearCheckDTO) this.dtoParameter;
		String orderType=dto.getOrderType();//任务类型
		String sqlStr=
					" SELECT    DISTINCT(EII.BARCODE), \n"+
					"           EFA.ASSETS_DESCRIPTION, \n"+
//					" 	        EII.MAINTAIN_USER , \n"+
//					" 	        ESI.ITEM_SPEC MODEL_NUMBER, \n"+
//					" 	        EFA.FA_CATEGORY1, \n"+
					" 	        EII.CONTENT_NAME, \n"+
//					" 	        EII.CONTENT_CODE, \n"+
//					" 	        EO.WORKORDER_OBJECT_LOCATION, \n"+ 
//					" 	        EO.WORKORDER_OBJECT_CODE, \n"+
//					" 	        EO.WORKORDER_OBJECT_NAME, \n"+
//					" 	        EII.CONSTRUCT_STATUS, \n"+
//					" 	        EII.IS_SHARE, \n"+
//					" 	        EII.OPE_ID, \n"+
//					" 	        EII.NLE_ID, \n"+
					"           ''   CHECK_STATUS,"+
					"           ''   NOTES, "+
					" 	        EII.ITEM_STATUS \n"+
					" 	FROM    ETS_ITEM_INFO EII, \n"+
					" 	        ETS_FA_ASSETS  EFA, \n"+
					" 	        ETS_ITEM_MATCH EIM, \n"+
					" 	        ETS_SYSTEM_ITEM     ESI, \n"+
					" 	        ETS_OBJECT          EO, \n"+
					" 	        AMS_OBJECT_ADDRESS  AOA," +
					"           SF_USER SU," +
					" 			SF_USER_AUTHORITY SUA," +
					" 			AMS_MIS_EMPLOYEE AME," +
					" 			AMS_MIS_DEPT AMD," +
					"           AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE CTOL \n" +
					
					" 	WHERE EIM.ASSET_ID = EFA.ASSET_ID  \n"+
					"   AND   SU.USER_ID = SUA.USER_ID" +
					"   AND   CTOL.ORDER_NUMBER=?" +
					"   AND   CTOL.IMPLEMNET_ROLE_NAME=SUA.ROLE_NAME" +
					"   AND   CTOL.IMPLEMENT_BY =SUA.USER_ID" +
					"   AND   SU.EMPLOYEE_NUMBER = AME.EMPLOYEE_NUMBER " +
					"   AND   AME.DEPT_CODE = AMD.DEPT_CODE" +
					"   AND   EII.RESPONSIBILITY_DEPT=AMD.DEPT_CODE"+
					
					" 	AND   EII.SYSTEMID = EIM.SYSTEMID \n"+
//					" 	AND   EII.BARCODE LIKE '%3910-000001%' \n"+//TEST 
					" 	AND   EII.ITEM_CODE = ESI.ITEM_CODE \n"+
					" 	AND   EII.ADDRESS_ID=AOA.ADDRESS_ID \n"+
					" 	AND   AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n"+
					"   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_DESCRIPTION LIKE ?)"+
					"   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)"+
					"   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)" +
				    "   AND NOT EXISTS (SELECT 1 FROM ETS_ITEM_INFO_CONFIRM EIIC WHERE EIIC.BARCODE=EII.BARCODE )";
		
		sqlArgs.add(dto.getOrderNumber());
		sqlArgs.add(dto.getAssetsDescription());
		sqlArgs.add(dto.getAssetsDescription());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getModelNumber());
		
		
        if(orderType.equals(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_SOFTWARE)){
			sqlStr +="  AND  EII.CONTENT_CODE like '%9999'";
			
		}else if(orderType.equals(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_CLIENT)){
//			sqlStr +="  AND  EO.OBJECT_CATEGORY_NAME='客户地点'";
			sqlStr +="  AND  EII.BARCODE IN (SELECT BARCODE FROM AMS_ASSETS_YAER_CHECK_CLIENT WHERE ORGANIZATION_ID=?)";
			sqlArgs.add(userAccount.getOrganizationId());
			
		}else if(orderType.equals(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_PIPELINE)){
			sqlStr +="  AND ( EII.CONTENT_CODE LIKE '%.04-07%' " +
					"      OR EII.CONTENT_CODE LIKE '%.04-06%' " +
					"      OR EII.CONTENT_CODE LIKE '%.04-04%' " +
					"      OR EII.CONTENT_CODE LIKE '%.04-05%')";
		}
            sqlStr+=" ORDER BY EII.BARCODE";
		
		
//		//TEST 
//		String sqlStr=" SELECT EII.BARCODE ," +
//				" '测试资产' ASSETS_DESCRIPTION," +
//				"  EII.CONTENT_NAME," +
//				" ''   CHECK_STATUS,"+
//				" ''   NOTES FROM ETS_ITEM_INFO EII   WHERE EII.CONTENT_CODE like '%9999' "+
//		        " AND NOT EXISTS (SELECT 1 FROM ETS_ITEM_INFO_CONFIRM EIIC WHERE EIIC.BARCODE=EII.BARCODE )";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	public SQLModel getConfirmModel(String barcode,String checkStatus,String notes) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemYearCheckDTO dto = (EtsItemYearCheckDTO) this.dtoParameter;
		String sqlStr=
			          " INSERT INTO ETS_ITEM_INFO_CONFIRM(" +
//			          " CONRIRM_BY, " +
//			          " CONFIRM_ORG_ID, " +
//			          " CONFIRM_STATUS," +
			          " CONFIRM_DATE, "+
//			          " NOTES, "+
		              "       SYSTEMID, BARCODE, VENDOR_BARCODE, ITEM_QTY, DISABLE_DATE, REMARK, START_DATE, ITEM_CODE, PROJECTID, ITEM_STATUS, ATTRIBUTE1, ATTRIBUTE2, SENDTOMIS_FLAG, MIS_ITEMNAME, MIS_ITEMSPEC, CREATION_DATE, CREATED_BY, LAST_UPDATE_DATE, LAST_UPDATE_BY, ASSET_ID, ADDRESS_ID, FINANCE_PROP, ATTRIBUTE3, PARENT_BARCODE, LAST_LOC_CHG_DATE, ORGANIZATION_ID, FA_BARCODE, IS_PARENT, RESPONSIBILITY_USER, RESPONSIBILITY_DEPT, MAINTAIN_USER, MAINTAIN_DEPT, MANUFACTURER_ID, IS_SHARE, CONTENT_CODE, CONTENT_NAME, POWER, LNE_ID, CEX_ID, OPE_ID, NLE_ID, IS_TMP, PRICE, OLD_CONTENT_CODE, OLD_CONTENT_NAME, REP_MANUFACTURER_ID, SPECIALITY_DEPT, DZYH_ADDRESS, OTHER_INFO, SHARE_STATUS, IS_TD, ACTUAL_QTY, IS_RENTAL, REMARK1, REMARK2, UNIT_OF_MEASURE, DISCARD_TYPE, DEAL_TYPE, REFER_NATIONAL_FUND, SN_ID, CONSTRUCT_STATUS, TF_NET_ASSET_VALUE, TF_DEPRN_COST, TF_DEPRECIATION, OLD_BARCODE, SPECIALITY_USER2, SPECIALITY_USER, ARRIVAL_DATE)"+ 
					  " SELECT   " +
//					  " ?," +
//					  " ?," +
//					  " ?," +
					  " GETDATE(),  " +
//					  " ?,  " +
					  "       SYSTEMID, BARCODE, VENDOR_BARCODE, ITEM_QTY, DISABLE_DATE, REMARK, START_DATE, ITEM_CODE, PROJECTID, ITEM_STATUS, ATTRIBUTE1, ATTRIBUTE2, SENDTOMIS_FLAG, MIS_ITEMNAME, MIS_ITEMSPEC, CREATION_DATE, CREATED_BY, LAST_UPDATE_DATE, LAST_UPDATE_BY, ASSET_ID, ADDRESS_ID, FINANCE_PROP, ATTRIBUTE3, PARENT_BARCODE, LAST_LOC_CHG_DATE, ORGANIZATION_ID, FA_BARCODE, IS_PARENT, RESPONSIBILITY_USER, RESPONSIBILITY_DEPT, MAINTAIN_USER, MAINTAIN_DEPT, MANUFACTURER_ID, IS_SHARE, CONTENT_CODE, CONTENT_NAME, POWER, LNE_ID, CEX_ID, OPE_ID, NLE_ID, IS_TMP, PRICE, OLD_CONTENT_CODE, OLD_CONTENT_NAME, REP_MANUFACTURER_ID, SPECIALITY_DEPT, DZYH_ADDRESS, OTHER_INFO, SHARE_STATUS, IS_TD, ACTUAL_QTY, IS_RENTAL, REMARK1, REMARK2, UNIT_OF_MEASURE, DISCARD_TYPE, DEAL_TYPE, REFER_NATIONAL_FUND, SN_ID, CONSTRUCT_STATUS, TF_NET_ASSET_VALUE, TF_DEPRN_COST, TF_DEPRECIATION, OLD_BARCODE, SPECIALITY_USER2, SPECIALITY_USER, ARRIVAL_DATE "+
					  "	FROM   ETS_ITEM_INFO "+
					  " WHERE  BARCODE = ? ";
		
//		sqlArgs.add(userAccount.getUserId());
//		sqlArgs.add(userAccount.getOrganizationId());
//		sqlArgs.add(checkStatus);
//		sqlArgs.add(dto.getCheckStatusOption());
//		sqlArgs.add(notes);
		sqlArgs.add(barcode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	public SQLModel getMatchUpdateModel(String barcode,String checkStatus,String notes) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		if(notes.equals(",,")){
			notes="";
		}
		EtsItemYearCheckDTO dto = (EtsItemYearCheckDTO) this.dtoParameter;
		String sqlStr=
			" UPDATE ETS_ITEM_INFO_CONFIRM \n"+
			" SET CONRIRM_BY=?,\n"+
			"    CONFIRM_ORG_ID=?,\n"+
			"    CONFIRM_STATUS=?, \n"+
			"    NOTES=? \n"+
			" WHERE BARCODE=? \n";
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(checkStatus);
		sqlArgs.add(notes);
		sqlArgs.add(barcode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	//--------------------------
	
	 public SQLModel getPageQueryModel()
	    throws SQLModelException
	  {
	    SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	      EtsItemYearCheckDTO dto = (EtsItemYearCheckDTO)this.dtoParameter;
	      String sqlStr=
				" SELECT    EII.BARCODE, \n"+
				"           EFA.ASSETS_DESCRIPTION, \n"+
//				" 	        EII.MAINTAIN_USER , \n"+
//				" 	        ESI.ITEM_SPEC MODEL_NUMBER, \n"+
				" 	        EFA.FA_CATEGORY1, \n"+
//				" 	        EII.CONTENT_NAME, \n"+
//				" 	        EII.CONTENT_CODE, \n"+
//				" 	        EO.WORKORDER_OBJECT_LOCATION, \n"+ 
				" 	        EO.WORKORDER_OBJECT_CODE, \n"+
				" 	        EO.WORKORDER_OBJECT_NAME, \n"+
//				" 	        EII.CONSTRUCT_STATUS, \n"+
//				" 	        EII.IS_SHARE, \n"+
//				" 	        EII.OPE_ID, \n"+
//				" 	        EII.NLE_ID, \n"+
				" 	        EII.ITEM_STATUS \n"+
				" 	FROM    ETS_ITEM_INFO EII, \n"+
				" 	        ETS_FA_ASSETS  EFA, \n"+
				" 	        ETS_ITEM_MATCH EIM, \n"+
				" 	        ETS_SYSTEM_ITEM     ESI, \n"+
				" 	        ETS_OBJECT          EO, \n"+
				" 	        AMS_OBJECT_ADDRESS  AOA," +
				"           SF_USER SU," +
				" 			SF_USER_AUTHORITY SUA," +
				" 			AMS_MIS_EMPLOYEE AME," +
				" 			AMS_MIS_DEPT AMD" +
				
				" 	WHERE EIM.ASSET_ID = EFA.ASSET_ID  \n"+
				"   AND   SU.USER_ID = SUA.USER_ID" +
//				"   AND   CTOL.ORDER_NUMBER=?" +
//				"   AND   CTOL.IMPLEMNET_ROLE_NAME=SUA.ROLE_NAME" +
//				"   AND   CTOL.IMPLEMENT_BY =SUA.USER_ID" +
				"   AND   SU.EMPLOYEE_NUMBER = AME.EMPLOYEE_NUMBER " +
				"   AND   AME.DEPT_CODE = AMD.DEPT_CODE" +
				"   AND   EII.RESPONSIBILITY_DEPT=AMD.DEPT_CODE"+
				
				" 	AND   EII.SYSTEMID = EIM.SYSTEMID \n"+
				" 	AND   EII.ITEM_CODE = ESI.ITEM_CODE \n"+
				" 	AND   EII.ADDRESS_ID=AOA.ADDRESS_ID \n"+
				" 	AND   AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n"+
				"   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_DESCRIPTION LIKE ?)"+
				"   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)"+
				"   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)" +
				" 	AND   EII.BARCODE LIKE '%3910-000001%' \n" +//TEST  客户端资产确认条件
	            "   AND   EII.BARCODE NOT IN( SELECT BARCODE FROM AMS_ASSETS_YAER_CHECK_CLIENT AYCC " +
	            "   WHERE AYCC.ORGANIZATION_ID=?)";
				sqlArgs.add(dto.getAssetsDescription());
				sqlArgs.add(dto.getAssetsDescription());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getModelNumber());
				sqlArgs.add(dto.getModelNumber());
				sqlArgs.add(userAccount.getOrganizationId());

	      sqlModel.setSqlStr(sqlStr);
	      sqlModel.setArgs(sqlArgs);
	    return sqlModel;
	  }
	 
	 public SQLModel getClientModel(String barcode) {
			SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			EtsItemYearCheckDTO dto = (EtsItemYearCheckDTO) this.dtoParameter;
			String sqlStr=
				" INSERT INTO dbo.AMS_ASSETS_YAER_CHECK_CLIENT(" +
				"       BARCODE, ORGANIZATION_ID, CREATION_BY," +
				"       CREATION_DATE, LAST_UPDATE_BY, LAST_UPDATE_DATE)" +
				"VALUES(?, ?, ?, getdate(), ?, getdate())";
			sqlArgs.add(barcode);
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(userAccount.getUserId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			return sqlModel;
		}
	 
	 
	 public SQLModel getExportModel()
	    throws SQLModelException
	  {
	    SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	      EtsItemYearCheckDTO dto = (EtsItemYearCheckDTO)this.dtoParameter;
	      String sqlStr=
				" SELECT    EII.BARCODE, \n"+
				"           EFA.ASSETS_DESCRIPTION, \n"+
//				" 	        EII.MAINTAIN_USER , \n"+
//				" 	        ESI.ITEM_SPEC MODEL_NUMBER, \n"+
				" 	        EFA.FA_CATEGORY1, \n"+
//				" 	        EII.CONTENT_NAME, \n"+
//				" 	        EII.CONTENT_CODE, \n"+
//				" 	        EO.WORKORDER_OBJECT_LOCATION, \n"+ 
				" 	        EO.WORKORDER_OBJECT_CODE, \n"+
				" 	        EO.WORKORDER_OBJECT_NAME, \n"+
//				" 	        EII.CONSTRUCT_STATUS, \n"+
//				" 	        EII.IS_SHARE, \n"+
//				" 	        EII.OPE_ID, \n"+
//				" 	        EII.NLE_ID, \n"+
				" 	        EII.ITEM_STATUS \n"+
				" 	FROM    ETS_ITEM_INFO EII, \n"+
				" 	        ETS_FA_ASSETS  EFA, \n"+
				" 	        ETS_ITEM_MATCH EIM, \n"+
				" 	        ETS_SYSTEM_ITEM     ESI, \n"+
				" 	        ETS_OBJECT          EO, \n"+
				" 	        AMS_OBJECT_ADDRESS  AOA," +
				"           SF_USER SU," +
				" 			SF_USER_AUTHORITY SUA," +
				" 			AMS_MIS_EMPLOYEE AME," +
				" 			AMS_MIS_DEPT AMD" +
				
				" 	WHERE EIM.ASSET_ID = EFA.ASSET_ID  \n"+
				"   AND   SU.USER_ID = SUA.USER_ID" +
//				"   AND   CTOL.ORDER_NUMBER=?" +
//				"   AND   CTOL.IMPLEMNET_ROLE_NAME=SUA.ROLE_NAME" +
//				"   AND   CTOL.IMPLEMENT_BY =SUA.USER_ID" +
				"   AND   SU.EMPLOYEE_NUMBER = AME.EMPLOYEE_NUMBER " +
				"   AND   AME.DEPT_CODE = AMD.DEPT_CODE" +
				"   AND   EII.RESPONSIBILITY_DEPT=AMD.DEPT_CODE"+
				
				" 	AND   EII.SYSTEMID = EIM.SYSTEMID \n"+
				" 	AND   EII.ITEM_CODE = ESI.ITEM_CODE \n"+
				" 	AND   EII.ADDRESS_ID=AOA.ADDRESS_ID \n"+
				" 	AND   AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n"+
				"   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_DESCRIPTION LIKE ?)"+
				"   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)"+
				"   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)" +
				" 	AND   EII.BARCODE LIKE '%3910-000001011%' \n" +//TEST  客户端资产确认条件
	            "   AND   EII.BARCODE NOT IN( SELECT BARCODE FROM AMS_ASSETS_YAER_CHECK_CLIENT AYCC " +
	            "   WHERE AYCC.ORGANIZATION_ID=?)";
				sqlArgs.add(dto.getAssetsDescription());
				sqlArgs.add(dto.getAssetsDescription());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getModelNumber());
				sqlArgs.add(dto.getModelNumber());
				sqlArgs.add(userAccount.getOrganizationId());

	      sqlModel.setSqlStr(sqlStr);
	      sqlModel.setArgs(sqlArgs);
	    return sqlModel;
	  }

}
