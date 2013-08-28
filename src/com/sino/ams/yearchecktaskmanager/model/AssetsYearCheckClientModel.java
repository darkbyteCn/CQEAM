package com.sino.ams.yearchecktaskmanager.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.omg.PortableInterceptor.USER_EXCEPTION;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearClientDTO;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;

public class AssetsYearCheckClientModel extends AMSSQLProducer {

	public AssetsYearCheckClientModel(SfUserDTO userAccount, AssetsYearClientDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}
	
	 public SQLModel getPageQueryModel()
	    throws SQLModelException
	  {
	    SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	    AssetsYearClientDTO dto = (AssetsYearClientDTO)this.dtoParameter;
	    String isImport=dto.getIsImport();
	    String sqlStr="";
	    String isTd=userAccount.getIsTd();  //是否TD用户
	    if(isImport.equals("")){
	       sqlStr=
				" SELECT    EII.BARCODE, \n"+
				"           EFA.ASSETS_DESCRIPTION, \n"+
				" 	        EFA.FA_CATEGORY1, \n"+
				" 	        EO.WORKORDER_OBJECT_CODE, \n"+
				" 	        EO.WORKORDER_OBJECT_NAME, \n"+
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
				" 	        ETS_OBJECT          EO, \n"+
				" 	        AMS_OBJECT_ADDRESS  AOA" +
				
				" 	WHERE EIM.ASSET_ID = EFA.ASSET_ID  \n"+
				" 	AND   EII.SYSTEMID = EIM.SYSTEMID \n"+
				" 	AND   EII.ADDRESS_ID=AOA.ADDRESS_ID \n"+
				" 	AND   AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n"+
				"   AND   EII.ITEM_STATUS NOT IN('DISCARDED')"+//去掉报废资产  
//				"   AND   EO.OBJECT_CATEGORY_NAME='客户地点'"+
//				"   AND   EO.OBJECT_CATEGORY='66'"+
				"   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_DESCRIPTION LIKE ?)"+
				"   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)"+
				"   AND SUBSTRING(EII.CONTENT_CODE,4,11) IN ('02-07-01-01','02-07-02-01','02-07-02-02',"+
				"		 '02-07-02-03','02-07-02-04','02-07-04-01','02-07-04-02','02-07-04-03','02-07-04-04',"+
				"		'02-07-04-05','03-05-01-01','03-05-01-02','03-05-01-03','03-05-01-04','03-05-01-05',"+
				"		'03-05-01-06')"+   //客户端资产确认条件
	            "   AND   EII.BARCODE NOT IN( SELECT BARCODE FROM AMS_ASSETS_YAER_CHECK_CLIENT AYCC " +
	            "   WHERE AYCC.ORGANIZATION_ID=?)" +
	            "   AND   EII.ORGANIZATION_ID=?";
				sqlArgs.add(dto.getAssetsDescription());
				sqlArgs.add(dto.getAssetsDescription());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(dto.getBarcode());
				sqlArgs.add(userAccount.getOrganizationId());
				sqlArgs.add(userAccount.getOrganizationId());
				
	    }else if(isImport.equals("1")){
	    	 sqlStr=
				" SELECT BARCODE, ORGANIZATION_ID, ASSETS_DESCRIPTION, FA_CATEGORY1, WORKORDER_OBJECT_CODE, "+
				" WORKORDER_OBJECT_NAME, CRETE_BY "+
				"	FROM dbo.AMS_ASSETS_YAER_CHECK_CLIENT_IMP"+
				" WHERE CRETE_BY=?"+
				" AND   ORGANIZATION_ID=? ORDER BY BARCODE ";
			sqlArgs.add(userAccount.getUserId());		
			sqlArgs.add(userAccount.getOrganizationId());	
	    }
	      sqlModel.setSqlStr(sqlStr);
	      sqlModel.setArgs(sqlArgs);
	    return sqlModel;
	  }
	 
	 public SQLModel getClientModel(String barcode) {
			SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			AssetsYearClientDTO dto = (AssetsYearClientDTO) this.dtoParameter;
			String sqlStr=
				" INSERT INTO AMS_ASSETS_YAER_CHECK_CLIENT(" +
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

	 public SQLModel getClientImpModel(AssetsYearClientDTO cdto) {
			SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			AssetsYearClientDTO dto = (AssetsYearClientDTO) this.dtoParameter;
			String sqlStr=
				" INSERT INTO AMS_ASSETS_YAER_CHECK_CLIENT_IMP(BARCODE, ORGANIZATION_ID, " +
				"     ASSETS_DESCRIPTION, FA_CATEGORY1, " +
				"     WORKORDER_OBJECT_CODE, WORKORDER_OBJECT_NAME,CRETE_BY) " +
				" VALUES(?, ?, ?, ?, ?, ?,?)" ;
			sqlArgs.add(cdto.getBarcode());		
			sqlArgs.add(userAccount.getOrganizationId());		
			sqlArgs.add(cdto.getAssetsDescription());		
			sqlArgs.add(cdto.getFaCategory1());		
			sqlArgs.add(cdto.getWorkorderObjectCode());		
			sqlArgs.add(cdto.getWorkorderObjectName());		
			sqlArgs.add(userAccount.getUserId());		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			return sqlModel;
		}
	 
	 
	 public SQLModel getClientDelModel() {
			SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			AssetsYearClientDTO dto = (AssetsYearClientDTO) this.dtoParameter;
			String sqlStr=
				" DELETE  FROM AMS_ASSETS_YAER_CHECK_CLIENT_IMP"+
				" WHERE CRETE_BY=?"+
				" AND   ORGANIZATION_ID=?";
			sqlArgs.add(userAccount.getUserId());		
			sqlArgs.add(userAccount.getOrganizationId());		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			return sqlModel;
		}
}
