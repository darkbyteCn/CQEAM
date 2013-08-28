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
		String level=dto.getLevel();        //任务等级
		String sendType=dto.getSendType();  //下发方式
		String isTd=userAccount.getIsTd();  //是否TD用户
		
//		if(sendType.equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER)){
//			//下给地市管理员
//		}else if(sendType.equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER)){
//			//下给地市部门管理员
//		}else if(sendType.equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON)){
//			//下给资产责任人或者特定人员
//		level.equals("3")  地市下发盘点任务
//		level.equals("4")  部门下发盘点任务
		String sqlStr="";
			 sqlStr=
				" SELECT    DISTINCT(EII.BARCODE), \n"+
				"           EFA.ASSETS_DESCRIPTION, \n"+
				" 	        EII.CONTENT_NAME, \n"+
				"           ''   CHECK_STATUS,"+
				"           ''   NOTES, "+
				" 	        EII.ITEM_STATUS \n"+
				" 	FROM    ETS_ITEM_INFO EII, \n";
			 
			 if("Y".equalsIgnoreCase(isTd)){
				 sqlStr+=" 	ETS_FA_ASSETS_TD EFA, \n" +
				 		 "  ETS_ITEM_MATCH_TD EIM,\n ";
			 }else {
				 sqlStr+=" 	ETS_FA_ASSETS  EFA, \n" +
				 		 "  ETS_ITEM_MATCH EIM, \n";
			 }
				
				sqlStr+=
				" 	        ETS_SYSTEM_ITEM     ESI, \n"+
				" 	        ETS_OBJECT          EO, \n"+
				" 	        AMS_OBJECT_ADDRESS  AOA,\n" +
				"           SF_USER SU,\n" +
				"			AMS_ASSETS_YAER_CHECK_TASK_BASEDATE ACTB," ;
				
				if((sendType.equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER)
						&&level.equals("3"))||level.equals("4")){
					//地市下发非实地任务
					//下给地市部门管理员
					sqlStr+=
				            " AMS_MIS_EMPLOYEE AME,\n" +
				            " AMS_MIS_DEPT AMD,\n";
				}
				
				    sqlStr+=
			    "           AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE CTOL \n" +
				" 	WHERE EIM.ASSET_ID = EFA.ASSET_ID  \n"+
				"   AND   CTOL.ORDER_NUMBER=? \n" +
				"   AND   CTOL.IMPLEMENT_BY =SU.USER_ID \n" +
				"   AND   EFA.ASSETS_CREATE_DATE <= ACTB.CHECK_BASE_DATE_CITY \n" +
				"   AND   ACTB.ORGANIZATION_ID=EII.ORGANIZATION_ID \n" +
				"   AND   ACTB.BASEDATE_YEAR=SUBSTRING(CONVERT(VARCHAR(32),getdate(),112),1,4) \n" ;
				
				if(sendType.equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER)
						&&level.equals("3")){
					//地市下发非实地任务
				    //下给地市部门管理员
					  sqlStr+=
					            " AND 	SU.EMPLOYEE_NUMBER = AME.EMPLOYEE_NUMBER \n" +
					            " AND 	AME.DEPT_CODE = AMD.DEPT_CODE \n" +
					            " AND   EII.RESPONSIBILITY_DEPT =AMD.DEPT_CODE \n" +
					            " AND   AMD.DEPT_CODE=CTOL.IMPLEMENT_DEPT_ID \n";
//					sqlStr+="  AND  EII.RESPONSIBILITY_DEPT IN (" +
//							"  SELECT SGM.DEPT_ID FROM \n" +
//							"	      SINO_GROUP_MATCH SGM,\n"+
//							"	      SF_GROUP SG,\n"+
//							"	      SF_USER SU,\n"+
//							"	      SF_USER_AUTHORITY SUA\n"+
//							"	WHERE   SU.USER_ID = SUA.USER_ID\n"+
//							"	AND     SUA.GROUP_NAME = SG.GROUP_NAME\n"+
//							"	AND     SG.GROUP_ID = SGM.GROUP_ID\n"+
//							"	AND     SU.USER_ID = ?" +
//							")";
			    }
				
				if(level.equals("4")){
					//部门下发非实地任务
				    //下发给部门人员[资产责任人]
					  sqlStr+=
						     " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID"+
							 " AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE" +
							 " AND AMD.DEPT_CODE=CTOL.IMPLEMENT_DEPT_ID " +
							 " AND SU.EMPLOYEE_NUMBER = AME.EMPLOYEE_NUMBER" +
							 " AND AME.DEPT_CODE = AMD.DEPT_CODE";
			    }
				
				    sqlStr+=
				" 	AND   EII.SYSTEMID = EIM.SYSTEMID \n"+
				"   AND   EII.ITEM_STATUS NOT IN('DISCARDED')"+//去掉报废资产   <!-- 2013-07-04 Jeffery-->
				"   AND   EII.ORGANIZATION_ID=? \n"+           //查询本地市资产<!-- 2013-07-04 Jeffery-->
				" 	AND   EII.ITEM_CODE = ESI.ITEM_CODE \n"+
				" 	AND   EII.ADDRESS_ID=AOA.ADDRESS_ID \n"+
				" 	AND   AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n"+
				"   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_DESCRIPTION LIKE ?)"+
				"   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)"+
				"   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)" +
			    "   AND NOT EXISTS (SELECT 1 FROM ETS_ITEM_INFO_CONFIRM EIIC WHERE EIIC.BARCODE=EII.BARCODE AND EIIC.CONFIRM_YR=SUBSTRING(CONVERT(VARCHAR(32),getdate(),112),1,4))";
	
				sqlArgs.add(dto.getOrderNumber());
//				sqlArgs.add(userAccount.getUserId());//hlj
				sqlArgs.add(userAccount.getOrganizationId());
				sqlArgs.add(dto.getAssetsDescription());
				sqlArgs.add(dto.getAssetsDescription());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getModelNumber());
				sqlArgs.add(dto.getModelNumber());
		
		
        if(orderType.equals(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_SOFTWARE)){
			sqlStr +="  AND  EII.CONTENT_CODE like '%9999'";
			
		}else if(orderType.equals(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_CLIENT)){
			sqlStr +="  AND  EII.BARCODE IN (SELECT BARCODE FROM AMS_ASSETS_YAER_CHECK_CLIENT WHERE ORGANIZATION_ID=?)";
			sqlArgs.add(userAccount.getOrganizationId());
			
		}else if(orderType.equals(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_PIPELINE)){
			sqlStr +="  AND ( EII.CONTENT_CODE LIKE '%.04-07%' " +
					"      OR EII.CONTENT_CODE LIKE '%.04-06%' " +
					"      OR EII.CONTENT_CODE LIKE '%.04-04%' " +
					"      OR EII.CONTENT_CODE LIKE '%.04-05%')";
		}
            sqlStr+=" ORDER BY EII.BARCODE";
		
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	public SQLModel getConfirmModel(String barcode,String checkStatus,String notes) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		if(notes.equals(",,")){
			notes="";
		}
		EtsItemYearCheckDTO dto = (EtsItemYearCheckDTO) this.dtoParameter;
		String sqlStr=
			          " INSERT INTO ETS_ITEM_INFO_CONFIRM(" +
			          " CONRIRM_BY, " +
			          " CONFIRM_ORG_ID, " +
			          " CONFIRM_STATUS," +
			          " CONFIRM_DATE, " +
			          " CONFIRM_YR,";
		if(!notes.equals("")){
			  sqlStr+=" NOTES,";
		}
		      sqlStr+=
		              "   SYSTEMID, BARCODE, VENDOR_BARCODE, ITEM_QTY, DISABLE_DATE, REMARK, START_DATE, ITEM_CODE, PROJECTID, ITEM_STATUS, ATTRIBUTE1, ATTRIBUTE2, SENDTOMIS_FLAG, MIS_ITEMNAME, MIS_ITEMSPEC, CREATION_DATE, CREATED_BY, LAST_UPDATE_DATE, LAST_UPDATE_BY, ASSET_ID, ADDRESS_ID, FINANCE_PROP, ATTRIBUTE3, PARENT_BARCODE, LAST_LOC_CHG_DATE, ORGANIZATION_ID, FA_BARCODE, IS_PARENT, RESPONSIBILITY_USER, RESPONSIBILITY_DEPT, MAINTAIN_USER, MAINTAIN_DEPT, MANUFACTURER_ID, IS_SHARE, CONTENT_CODE, CONTENT_NAME, POWER, LNE_ID, CEX_ID, OPE_ID, NLE_ID, IS_TMP, PRICE, OLD_CONTENT_CODE, OLD_CONTENT_NAME, REP_MANUFACTURER_ID, SPECIALITY_DEPT, DZYH_ADDRESS, OTHER_INFO, SHARE_STATUS, IS_TD, ACTUAL_QTY, IS_RENTAL, REMARK1, REMARK2, UNIT_OF_MEASURE, DISCARD_TYPE, DEAL_TYPE, REFER_NATIONAL_FUND, SN_ID, CONSTRUCT_STATUS, TF_NET_ASSET_VALUE, TF_DEPRN_COST, " +
		              "   TF_DEPRECIATION, OLD_BARCODE, SPECIALITY_USER2, SPECIALITY_USER)"+ 
					  " SELECT   " +
					    userAccount.getUserId()+"," +
					    userAccount.getOrganizationId()+"," +
					    "'"+checkStatus+"',"+
				      " GETDATE(), \n" +
				      " SUBSTRING(CONVERT(VARCHAR(32),getdate(),112),1,4) ,\n" ;
		      if(!notes.equals("")){
				  sqlStr+="'"+notes+"'," ;
			  }     
				  sqlStr+=    
					  "  SYSTEMID, BARCODE, VENDOR_BARCODE, ITEM_QTY, DISABLE_DATE, REMARK, START_DATE, ITEM_CODE, PROJECTID, ITEM_STATUS, ATTRIBUTE1, ATTRIBUTE2, SENDTOMIS_FLAG, MIS_ITEMNAME, MIS_ITEMSPEC, CREATION_DATE, CREATED_BY, LAST_UPDATE_DATE, LAST_UPDATE_BY, ASSET_ID, ADDRESS_ID, FINANCE_PROP, ATTRIBUTE3, PARENT_BARCODE, LAST_LOC_CHG_DATE, ORGANIZATION_ID, FA_BARCODE, IS_PARENT, RESPONSIBILITY_USER, RESPONSIBILITY_DEPT, MAINTAIN_USER, MAINTAIN_DEPT, MANUFACTURER_ID, IS_SHARE, CONTENT_CODE, CONTENT_NAME, POWER, LNE_ID, CEX_ID, OPE_ID, NLE_ID, IS_TMP, PRICE, OLD_CONTENT_CODE, OLD_CONTENT_NAME, REP_MANUFACTURER_ID, SPECIALITY_DEPT, DZYH_ADDRESS, OTHER_INFO, SHARE_STATUS, IS_TD, ACTUAL_QTY, IS_RENTAL, REMARK1, REMARK2, UNIT_OF_MEASURE, DISCARD_TYPE, DEAL_TYPE, REFER_NATIONAL_FUND, SN_ID, CONSTRUCT_STATUS, TF_NET_ASSET_VALUE, TF_DEPRN_COST, " +
					  "  TF_DEPRECIATION, OLD_BARCODE, SPECIALITY_USER2, SPECIALITY_USER"+
					  "	FROM   ETS_ITEM_INFO "+
					  " WHERE  BARCODE = ? ";
		
		sqlArgs.add(barcode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	
	public SQLModel getQueryLevelModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemYearCheckDTO dto = (EtsItemYearCheckDTO) this.dtoParameter;
		String sqlStr=
			" SELECT  CTB.NON_ADDRESS_ASSETS_SOFT," +
			" 		  CTB.NON_ADDRESS_ASSETS_CLIENT, \n" +
			" 		  CTB.NON_ADDRESS_ASSETS_PIPELINE, \n" +
			" 	      CTOL.ORDER_LEVEL,\n" +
			" 		  CTOL.IMPLEMENT_DEPT_ID,\n"+
			" 		  CTOL.IMPLEMNET_DEPT_NAME \n"+
			" FROM  AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE CTOL, \n" +
			"       AMS_ASSETS_YAER_CHECK_TASK_BASEDATE   CTB \n"+
			" WHERE CTOL.ORDER_NUMBER=? \n"+
			" AND   CTOL.IMPLEMENT_BY=? \n"+
			" AND   CTB.BASE_DATE_TYPE='2'\n"+
			" AND   CTOL.IMPLEMENT_ORGANIZATION_ID=? \n" +
			" AND   CTB.ORGANIZATION_ID = CTOL.IMPLEMENT_ORGANIZATION_ID"+
			" AND   CTOL.ORDER_STATUS NOT IN('DO_CANCLE','DO_RETURN','DO_COMPLETE') \n";
		sqlArgs.add(dto.getOrderNumber());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	} 

}
