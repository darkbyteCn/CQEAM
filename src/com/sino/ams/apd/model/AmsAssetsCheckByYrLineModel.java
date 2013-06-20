package com.sino.ams.apd.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.apd.dto.AmsAssetsCheckByYrLineDTO;
import com.sino.ams.apd.dto.AmsAssetsCheckOrderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.framework.sql.BaseSQLProducer;

public class AmsAssetsCheckByYrLineModel extends BaseSQLProducer {
	private SfUserDTO user = null;

	public AmsAssetsCheckByYrLineModel(SfUserDTO userAccount,
			AmsAssetsCheckByYrLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.user = userAccount;
	}
	
	//获取任务信息头表
	public SQLModel getTraskUserModel()
	  {
		    SQLModel sqlModel = new SQLModel();
		    List sqlArgs = new ArrayList();
		    AmsAssetsCheckByYrLineDTO dto = (AmsAssetsCheckByYrLineDTO)this.dtoParameter;
    	    String  sqlStr = " SELECT ACL.TRANS_ID, ACL.LINE_ID, ACL.CHECK_DATE, ACL.RECEIVD_BY, " +
    	    		         " ACL.RECEIVD_BY_NAME, ACL.RB_ORGANIZATION_ID,\n"
                            +" ACL.TRANS_STATUS, ACL.TRANS_STATUS_VALUE, ACL.COMPANY_CODE, ACL.COMPANY, \n"
                            +" ACL.BOOK_TYPE_CODE, ACL.BOOK_TYPE_NAME, " +
                             " ACH.TASK_START_DATE  START_CREATION_DATE ," +
                             " ACH.TASK_END_DATE  END_CREATION_DATE," +
                             " ACH.BASIC_DATE_BEGIN START_DATE," +
                             " ACH.BASIC_DATE_END END_DATE \n"
						    +" FROM AMS_ASSETS_CHECK_BY_LINE ACL, \n"
						    +"      AMS_ASSETS_CHECK_BY_HEADER ACH \n"
						    +" WHERE RECEIVD_BY=? \n"
    	    				+" AND ACL.TRANS_ID=ACH.TRANS_ID \n";
    	    sqlArgs.add(user.getUserId());
		    sqlModel.setSqlStr(sqlStr);
		    sqlModel.setArgs(sqlArgs);
		    return sqlModel;
	  }
	
	
	public SQLModel getPageQueryModel()
	  {
		    SQLModel sqlModel = new SQLModel();
		    List sqlArgs = new ArrayList();
		    AmsAssetsCheckByYrLineDTO dto = (AmsAssetsCheckByYrLineDTO)this.dtoParameter;
		    String createType=dto.getCreateType();//非实地盘点资产下发方式
		    String transtatus=dto.getTransStatus();
		    String sqlStr = "";
		    if(transtatus.equals("")){
			    if(createType.equals("BY_ASSETS")){
			    	//按资产种类
			    	sqlStr="  SELECT 'N'         CREATION_TYPE," +
			    		   "        '非实地资产'  CREATE_VALUE,\n" +
			    		   "        CREATE_TYPE  SEND_TYPE,  " +
			    		   "        '按资产种类('+CREATE_TYPE_VALUE+')'  SEND_VALUE ,\n" +
			    		   "        0            TRANS_USER ,\n" +
			    		   "        ''           TRANS_NAME \n" +
//			    		   "        CREATE_TYPE," +
//			    		   "        CREATE_TYPE_VALUE " +
			    		   "  FROM    AMS_CREAE_ORDER " +
			    		   "  WHERE   TYPE_VALUE='B' ";
			    	
			    }else if(createType.equals("BY_MANAGGER")){
			    	//按资产管理员
			    	sqlStr = 
					    	" SELECT 'N' CREATION_TYPE,\n" +
					    	"        '非实地资产' CREATE_VALUE,\n"+
					    	"        'BY_MANAGGER' SEND_TYPE, \n" +
					    	"        '按资产管理员' SEND_VALUE, \n"+
					    	"        0            TRANS_USER ,\n" +
				    		"        ''           TRANS_NAME \n" +
					    	" FROM" +
					    	"     SF_USER         SU,"+ 
					    	"	  ETS_OU_CITY_MAP EOCM "+
					    	" WHERE " +
					    	"       SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID "+
					    	" AND   SU.ORGANIZATION_ID = 82  \n"+
					    	" AND EXISTS 	      (SELECT NULL    \n"+     	 
					    	" FROM  SF_USER_AUTHORITY SUA      \n"+   	
					    	" WHERE SU.USER_ID = SUA.USER_ID      \n"+       
					    	" AND   SUA.ROLE_NAME = '公司资产管理员')   \n"+
					    	" AND   EXISTS  		(SELECT NULL 	 \n"+	   
					    	" FROM  SF_USER_RIGHT SUR 		  WHERE SUR.USER_ID = SU.USER_ID  \n"+			
					    	" )  \n";
			    }
			    
			    sqlStr+=" UNION  ALL "+
			    	    " SELECT 'Y'           CREATION_TYPE,\n" +
				    	"        '实地资产'     CREATE_VALUE,\n"+
				    	"        'BY_PDUSER'   SEND_TYPE, \n" +
				    	"        '按盘点责任人' SEND_VALUE, \n"+
				    	"        0            TRANS_USER ,\n" +
			    		"        ''           TRANS_NAME \n" +
				    	" FROM" +
				    	"     SF_USER         SU,"+ 
				    	"	  ETS_OU_CITY_MAP EOCM "+
				    	" WHERE " +
				    	"       SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID "+
				    	" AND   SU.ORGANIZATION_ID = 82  \n"+
				    	" AND EXISTS 	      (SELECT NULL    \n"+     	 
				    	" FROM  SF_USER_AUTHORITY SUA      \n"+   	
				    	" WHERE SU.USER_ID = SUA.USER_ID      \n"+       
				    	" AND   SUA.ROLE_NAME = '公司资产管理员')   \n"+
				    	" AND   EXISTS  		(SELECT NULL 	 \n"+	   
				    	" FROM  SF_USER_RIGHT SUR 		  WHERE SUR.USER_ID = SU.USER_ID  \n"+			
				    	" )   \n";
			    
			    sqlStr+=" UNION  ALL "+
	    	    " SELECT 'Y'           CREATION_TYPE,\n" +
		    	"        '实地资产'     CREATE_VALUE,\n"+
		    	"        'BY_DEPT'   SEND_TYPE, \n" +
		    	"        '按部门'    SEND_VALUE, \n"+
		    	"        0            TRANS_USER ,\n" +
	    		"        ''           TRANS_NAME \n" +
		    	" FROM" +
		    	"     SF_USER         SU,"+ 
		    	"	  ETS_OU_CITY_MAP EOCM "+
		    	" WHERE " +
		    	"       SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID "+
		    	" AND   SU.ORGANIZATION_ID = 82  \n"+
		    	" AND EXISTS 	      (SELECT NULL    \n"+     	 
		    	" FROM  SF_USER_AUTHORITY SUA      \n"+   	
		    	" WHERE SU.USER_ID = SUA.USER_ID      \n"+       
		    	" AND   SUA.ROLE_NAME = '公司资产管理员')   \n"+
		    	" AND   EXISTS  		(SELECT NULL 	 \n"+	   
		    	" FROM  SF_USER_RIGHT SUR 		  WHERE SUR.USER_ID = SU.USER_ID  \n"+			
		    	" )   \n";
					    
			    
		    }else {
		    	sqlStr= "  SELECT TRANS_ID, TRANS_NO, CRATE_DATE, TRANS_USER, TRANS_NAME, CREATION_TYPE, " +
		    			"  CREATE_VALUE, SEND_TYPE, SEND_VALUE "+
		    			"  FROM AMS_ASSETS_CHECK_ORDER" +
		    			"  WHERE TRANS_ID=?";
		    	sqlArgs.add(dto.getLineId());
		    	
		    }
		    sqlModel.setSqlStr(sqlStr);
		    sqlModel.setArgs(sqlArgs);
		    return sqlModel;
	  }
	
	//修改信息行表
	public SQLModel updateLineModel(AmsAssetsCheckByYrLineDTO dto) throws CalendarException
	  {
		    SQLModel sqlModel = new SQLModel();
		    List sqlArgs = new ArrayList();
//		    AmsAssetsCheckByYrLineDTO dto = (AmsAssetsCheckByYrLineDTO)this.dtoParameter;
    	    String  sqlStr = 
			    	    	" UPDATE AMS_ASSETS_CHECK_BY_LINE \n"+
    	    				" SET CHECK_DATE=?,\n"+
			    	    	"     TRANS_STATUS=?,\n"+
			    	    	"     TRANS_STATUS_VALUE=? \n"+
			    	    	" WHERE TRANS_ID=? \n"+
			    	    	" AND   LINE_ID=? \n";
    	    sqlArgs.add(dto.getLastUpdateDate());
    	    sqlArgs.add(dto.getTransStatus());
    	    sqlArgs.add(dto.getTransStatusValue());
    	    sqlArgs.add(dto.getTransId());
    	    sqlArgs.add(dto.getLineId());
		    sqlModel.setSqlStr(sqlStr);
		    sqlModel.setArgs(sqlArgs);
		    return sqlModel;
	  }
	
	//修改信息行表
	public SQLModel updateLineModelStatus(AmsAssetsCheckByYrLineDTO dto) throws CalendarException
	  {
		    SQLModel sqlModel = new SQLModel();
		    List sqlArgs = new ArrayList();
    	    String  sqlStr = 
			    	    	" UPDATE AMS_ASSETS_CHECK_BY_LINE \n"+
    	    				" SET TRANS_STATUS=?,\n"+
			    	    	"     TRANS_STATUS_VALUE=? \n"+
			    	    	" WHERE TRANS_ID=? \n"+
			    	    	" AND   LINE_ID=? \n";
    	    sqlArgs.add(dto.getLastUpdateDate());
    	    sqlArgs.add(dto.getTransStatus());
    	    sqlArgs.add(dto.getTransStatusValue());
    	    sqlArgs.add(dto.getTransId());
    	    sqlArgs.add(dto.getLineId());
		    sqlModel.setSqlStr(sqlStr);
		    sqlModel.setArgs(sqlArgs);
		    return sqlModel;
	  }
	
	//插入盘点工单信息
	public SQLModel getWorkOrderInsert(AmsAssetsCheckOrderDTO dto)
	  {
		    SQLModel sqlModel = new SQLModel();
		    List sqlArgs = new ArrayList();
    	    String  sqlStr = 
				    	      "  INSERT INTO AMS_ASSETS_CHECK_ORDER(" +
				    	      "  TRANS_ID, " +
				    	      "  TRANS_NO," +
				    	      "	 CRATE_DATE," +
				    	      "	 TRANS_USER, " +
				    	      "	 TRANS_NAME, " +
				    	      "  CREATION_TYPE, " +
				    	      "  CREATE_VALUE," +
				    	      "  SEND_TYPE, " +
				    	      "  SEND_VALUE) " +
				    	      "  VALUES(?, newid(), getDate(), ?, ?, ?, ?, ?,?)";
    	
    	    
    	    sqlArgs.add(dto.getTransId());
    	    sqlArgs.add(dto.getTransUser());
    	    sqlArgs.add(dto.getTransName());
    	    sqlArgs.add(dto.getCreationType());
    	    sqlArgs.add(dto.getCreateValue());
    	    sqlArgs.add(dto.getSendType());
    	    sqlArgs.add(dto.getSendValue());
		    sqlModel.setSqlStr(sqlStr);
		    sqlModel.setArgs(sqlArgs);
		    return sqlModel;
	  }
	
	
	public SQLModel getEtsItemInfoPh( ) throws CalendarException
	  {
		    SQLModel sqlModel = new SQLModel();
		    List sqlArgs = new ArrayList();
		    AmsAssetsCheckByYrLineDTO dto = (AmsAssetsCheckByYrLineDTO)this.dtoParameter;
  	        String  sqlStr = 
			  	        	 " INSERT INTO ETS_ITEM_INFO_PH(SYSTEMID, BARCODE, VENDOR_BARCODE, ITEM_QTY, DISABLE_DATE, REMARK, START_DATE, ITEM_CODE, PROJECTID, ITEM_STATUS, ATTRIBUTE1, ATTRIBUTE2, SENDTOMIS_FLAG, MIS_ITEMNAME, MIS_ITEMSPEC, CREATION_DATE, CREATED_BY, LAST_UPDATE_DATE, LAST_UPDATE_BY, ASSET_ID, ADDRESS_ID, FINANCE_PROP, ATTRIBUTE3, PARENT_BARCODE, LAST_LOC_CHG_DATE, ORGANIZATION_ID, FA_BARCODE, IS_PARENT, RESPONSIBILITY_USER, RESPONSIBILITY_DEPT, MAINTAIN_USER, MAINTAIN_DEPT, MANUFACTURER_ID, IS_SHARE, CONTENT_CODE, CONTENT_NAME, POWER, LNE_ID, CEX_ID, OPE_ID, NLE_ID, IS_TMP, PRICE, OLD_CONTENT_CODE, OLD_CONTENT_NAME, REP_MANUFACTURER_ID, SPECIALITY_DEPT, DZYH_ADDRESS, OTHER_INFO, SHARE_STATUS, IS_TD, ACTUAL_QTY, IS_RENTAL, REMARK1, REMARK2, UNIT_OF_MEASURE, DISCARD_TYPE, DEAL_TYPE, REFER_NATIONAL_FUND, SN_ID, CONSTRUCT_STATUS, TF_NET_ASSET_VALUE, TF_DEPRN_COST, TF_DEPRECIATION, OLD_BARCODE, SPECIALITY_USER2, SPECIALITY_USER, ARRIVAL_DATE ) " +
			  	        	 " SELECT SYSTEMID, BARCODE, VENDOR_BARCODE, ITEM_QTY, DISABLE_DATE, REMARK, START_DATE, ITEM_CODE, PROJECTID, ITEM_STATUS, ATTRIBUTE1, ATTRIBUTE2, SENDTOMIS_FLAG, MIS_ITEMNAME, MIS_ITEMSPEC, CREATION_DATE, CREATED_BY, LAST_UPDATE_DATE, LAST_UPDATE_BY, ASSET_ID, ADDRESS_ID, FINANCE_PROP, ATTRIBUTE3, PARENT_BARCODE, LAST_LOC_CHG_DATE, ORGANIZATION_ID, FA_BARCODE, IS_PARENT, RESPONSIBILITY_USER, RESPONSIBILITY_DEPT, MAINTAIN_USER, MAINTAIN_DEPT, MANUFACTURER_ID, IS_SHARE, CONTENT_CODE, CONTENT_NAME, POWER, LNE_ID, CEX_ID, OPE_ID, NLE_ID, IS_TMP, PRICE, OLD_CONTENT_CODE, OLD_CONTENT_NAME, REP_MANUFACTURER_ID, SPECIALITY_DEPT, DZYH_ADDRESS, OTHER_INFO, SHARE_STATUS, IS_TD, ACTUAL_QTY, IS_RENTAL, REMARK1, REMARK2, UNIT_OF_MEASURE, DISCARD_TYPE, DEAL_TYPE, REFER_NATIONAL_FUND, SN_ID, CONSTRUCT_STATUS, TF_NET_ASSET_VALUE, TF_DEPRN_COST, TF_DEPRECIATION, OLD_BARCODE, SPECIALITY_USER2, SPECIALITY_USER, ARRIVAL_DATE  FROM ETS_ITEM_INFO \n" +
			  	        	 " WHERE CREATION_DATE>?" +
			  	        	 " AND  ORGANIZATION_ID IN \n" +
			  	        	 " (SELECT  ORGANIZATION_ID  \n" +
			  	        	 "      FROM ETS_OU_CITY_MAP  \n" +
			  	        	 "   WHERE  COMPANY_CODE=? \n)";
  	        
  	        sqlArgs.add(dto.getLastUpdateDate());
  	        sqlArgs.add(dto.getCompanyCode());
		    sqlModel.setSqlStr(sqlStr);
		    sqlModel.setArgs(sqlArgs);
		    return sqlModel;
	  }
	
	
	
	
}
