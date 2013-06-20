package com.sino.ams.workorder.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderBatchDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.dto.ZeroTurnHeaderDTO;
import com.sino.ams.workorder.dto.ZeroTurnLineDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.sinoflow.appbase.model.AMSSQLProducer;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

public class ZeroTurnModel extends AMSSQLProducer {
	
	private SfUserDTO user = null;

	public ZeroTurnModel(SfUserBaseDTO userAccount, ZeroTurnHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
		 this.user = (SfUserDTO) userAccount;
	}
	
	//依据成本中心默认出对应的单位资产管理员
	@SuppressWarnings("unchecked")
	public SQLModel getUnitMaager(String costCenterCode){
		 SQLModel sqlModel = new SQLModel();
         List sqlArgs = new ArrayList();
         String sqlStr=" SELECT SUA.USER_ID,SU.USERNAME"
        	 		  +" FROM AMS_MIS_DEPT AMD,"
        	 		  +"      SF_USER_AUTHORITY SUA,"
        	 		  +"      SF_USER  SU"
        	 		  +" WHERE AMD.COST_CENTER_CODE=?"
        	 		  +" AND   SUA.GROUP_NAME=AMD.GROUP_NAME"
        	 		  +" AND   SUA.ROLE_NAME='单位资产管理员'"
        	 		  +" AND   SUA.USER_ID=SU.USER_ID";
          sqlArgs.add(costCenterCode);
          sqlModel.setSqlStr(sqlStr);
          sqlModel.setArgs(sqlArgs);
          return sqlModel;
	}
	
	
	public SQLModel getGroupId(int userId,String barcode){
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr="  SELECT SG.GROUP_ID,SUA.GROUP_NAME"
       	 		  +"  FROM SF_USER_AUTHORITY SUA,"
       	 		  +"       SF_USER  SU,"
       	 		  +"       SF_GROUP SG"
       	 		  +" WHERE  SG.GROUP_NAME=SUA.GROUP_NAME"
       	 		  +" AND   SUA.ROLE_NAME='单位资产管理员'"
       	 		  +" AND   SUA.USER_ID=SU.USER_ID"
       	 		  +" AND   SUA.USER_ID=?"
       	 		  +" AND  SUA.GROUP_NAME IN(" 
			      +"   SELECT  GROUP_NAME \n"
			      +"   FROM  AMS_MIS_DEPT \n"
			      +"   WHERE  \n"
			      +" COST_CENTER_CODE = (SELECT COST_CENTER_CODE FROM ETS_ITEM_TURN WHERE BARCODE=? ) \n"
			      +" AND charindex('_',GROUP_NAME) = 0 \n"
			      +" AND (GROUP_NAME is not null or GROUP_NAME <> '')\n"
			      +" AND  COMPANY_CODE = (SELECT COMPANY_CODE FROM ETS_OU_CITY_MAP  EOCM,ETS_ITEM_INFO EII WHERE EII.ORGANIZATION_ID=EOCM.ORGANIZATION_ID AND EII.BARCODE=?) )\n";
         sqlArgs.add(userId);
         sqlArgs.add(barcode);
         sqlArgs.add(barcode);
         sqlModel.setSqlStr(sqlStr);
         sqlModel.setArgs(sqlArgs);
         return sqlModel;
	}
	
	  public SQLModel getImpTurnDeleteModel() {
	        SQLModel sqlModel = new SQLModel();
	        List sqlArgs = new ArrayList();
	        //AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
	        String sqlStr = "DELETE FROM" + " ETS_ITEM_TURN";
	        		//+ " WHERE"
	                //+ " USER_ID = ?";
	        //sqlArgs.add(user.getUserId());
	        sqlModel.setSqlStr(sqlStr);
	        sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }

	
	//导入数据到临时表
	@SuppressWarnings("unchecked")
	public SQLModel insertEtsItemTurn(ZeroTurnLineDTO lineDTO){
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr=" INSERT INTO ETS_ITEM_TURN" +
        		"  (BARCODE, COMPANY_CODE, PROCURE_CODE, ASSETS_DESCRIPTION, ITEM_SPEC, " +
        		"   MANUFACTURER_NAME, CONTENT_CODE, ITEM_QTY, YEARS, PRICE, " +
        		"   START_DATE, OPE_ID, NLE_ID, IS_BULID, COST_CENTER_CODE,"+
        		"   WORKORDER_OBJECT_NAME, OBJECT_NO, RESPONSIBILITY_USER, RESPONSIBILITY_NAME, PROCURE_TYPE, RECEIVER, " +
        		"   RECEIVER_CONTACT, MIS_PROCURE_CODE,RESPONSIBILITY_DEPT," +
        		"   ASSET_KEY1, ASSET_KEY2,   ASSET_KEY3,  ASSET_TYPE,IS_DEPRN, IS_ADJUST,ATTRIBUTE4, ATTRIBUTE5,EXPECTED_DATE,CONTENT_NAME," +
        		"	 UNIT_OF_MEASURE,ITEM_CODE,MANUFACTURER_ID,ADDRESS_ID,CREATION_DATE,CREATED_BY,COST_CENTER_NAME,RESPONSIBILITY_CODE,RECORD)" +
       	 		"   VALUES(?,?,?,?,?, ?,?,CONVERT(decimal,?),CONVERT(INT,?),CONVERT(decimal(16,2),?)," +
       	 		"	 ?,?,?,?,?, ?,?,?,?,? ,?,?,?,? ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,GETDATE(),?,?,?,?)";
         sqlArgs.add(lineDTO.getBarcode());
         sqlArgs.add(lineDTO.getCompanyCode());
         sqlArgs.add(lineDTO.getProcureCode());
         sqlArgs.add(lineDTO.getAssetsDescription());
         sqlArgs.add(lineDTO.getItemSpec());
         
         sqlArgs.add(lineDTO.getManufacturerName());
         sqlArgs.add(lineDTO.getContentCode());
         sqlArgs.add(lineDTO.getItemQty());
         sqlArgs.add(lineDTO.getYears());
         sqlArgs.add(lineDTO.getPrice());
         
         sqlArgs.add(lineDTO.getStartDate());
         sqlArgs.add(lineDTO.getOpeId());
         sqlArgs.add(lineDTO.getNleId());
         sqlArgs.add(lineDTO.getIsBulid());
         sqlArgs.add(lineDTO.getCostCenterCode());
         
         sqlArgs.add(lineDTO.getWorkorderObjectName());
         sqlArgs.add(lineDTO.getObjectNo());
         sqlArgs.add(lineDTO.getResponsibilityUser());
         sqlArgs.add(lineDTO.getResponsibilityName());
         sqlArgs.add(lineDTO.getProcureType());
         
         sqlArgs.add(lineDTO.getReceiver());
         sqlArgs.add(lineDTO.getReceiverContact());
         sqlArgs.add(lineDTO.getMisProcureCode());
         sqlArgs.add(lineDTO.getResponsibilityDept());
        
         sqlArgs.add(lineDTO.getAssetKey1());
         sqlArgs.add(lineDTO.getAssetKey2());
         sqlArgs.add(lineDTO.getAssetKey3());
         sqlArgs.add(lineDTO.getAssetType());
         sqlArgs.add(lineDTO.getIsDeprn());
         sqlArgs.add(lineDTO.getIsAdjust());
         sqlArgs.add(lineDTO.getAttribute4());
         sqlArgs.add(lineDTO.getAttribute5());
         sqlArgs.add(lineDTO.getExpectedDate());
         sqlArgs.add(lineDTO.getContentName());
         sqlArgs.add(lineDTO.getUnitOfMeasure());
         sqlArgs.add(lineDTO.getItemCode());
         sqlArgs.add(lineDTO.getManufacturerId());
         sqlArgs.add(lineDTO.getAddressId());
         sqlArgs.add(user.getUserId());
         sqlArgs.add(lineDTO.getCenterName());
         sqlArgs.add(lineDTO.getResponsibilityDeptTWO());
         sqlArgs.add(lineDTO.getBarcode());
         
         sqlModel.setSqlStr(sqlStr);
         sqlModel.setArgs(sqlArgs);
         return sqlModel;
	}

	 /**
     * 增加  ****
     */
    @SuppressWarnings("unchecked")
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        ZeroTurnHeaderDTO dto = (ZeroTurnHeaderDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr =
                "INSERT INTO ZERO_TURN_ADD_H\n" +
                        "  (TRANS_ID,\n" +
                        "   TRANS_NO,\n" +
                        "   TRANS_TYPE,\n" +
                        "   TRANS_STATUS,\n" +
                        "   STATUS_TYPE,\n"+
                        "   ORGANIZATION_ID,\n" +
                        "   DEPT,\n" +
                        "   CREATED_REASON,\n" +
                        "   CREATION_DATE,\n" +
                        "   CREATED_BY,\n" +
                        "	EMERGENT_LEVEL,\n" +
                        "   ORDER_NO,\n"+
                        "	IS_SECOND)\n" +
                        "VALUES\n" +
                        "  (?," +
                       // "   ? , ?, ?, ?, ?, ?, GETDATE(), ?,'Y')";
        				"   ? , ?, ?,?, ?, ?, ?, GETDATE(), ?, ?,?,'Y')";
        sqlArgs.add(dto.getTransId());
        sqlArgs.add(dto.getTransNo());
        sqlArgs.add(dto.getTransType());
//      sqlArgs.add(dto.getTransStatus());
//      sqlArgs.add(dto.getStatsuType());
        sqlArgs.add("PRE_ASSETS");
		sqlArgs.add("预转资");
        sqlArgs.add(dto.getOrganizationId());
//      sqlArgs.add(user.getOrganizationId());
        sqlArgs.add(dto.getDeptCode());
        sqlArgs.add(dto.getCreatedReason());
        sqlArgs.add(dto.getUserId());
//      sqlArgs.add(user.getUserId());
        sqlArgs.add(dto.getEmergentLevel());
        sqlArgs.add(dto.getOrderNo());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 获取行信息  地点列表 ****
     * @param dto
     * @return
     */
    public SQLModel getLineDataModel(ZeroTurnHeaderDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =" SELECT zta.TRANS_ID, zta.TRANS_STATUS,zta.TRANS_STATUS_VAL,zta.COMPANY_CODE,zta.BARCODE,\n " +
        				" zta.CONTENT_CODE, zta.CONTENT_NAME, zta.ASSETS_DESCRIPTION, zta.ITEM_SPEC, zta.ITEM_QTY,\n" +
        				" zta.UNIT_OF_MEASURE, zta.MANUFACTURER_NAME, zta.OBJECT_NO, zta.WORKORDER_OBJECT_NAME,\n " +
        				" zta.RESPONSIBILITY_DEPT, zta.RESPONSIBILITY_USER, zta.SPECIALITY_DEPT, zta.START_DATE, zta.YEARS,\n" +
        				" zta.PRICE, zta.PROCURE_CODE, zta.COST_CENTER_CODE, zta.IS_SHARE, zta.IS_BULID, zta.LNE_ID, zta.OPE_ID,\n " +
        				" zta.CEX_ID, zta.NLE_ID, zta.REMARK,zta.COMPUTE_DAYS,zta.ZERO_NO,zta.RECORD,eit.PROCURE_CODE,eit.EXPECTED_DATE \n"+
                        " FROM ZERO_TURN_ADD_L zta,\n" +
                        "      ETS_ITEM_TURN eit \n" +
                        " WHERE zta.TRANS_ID = ?\n"+
        				" AND  zta.RECORD=eit.BARCODE";
        sqlArgs.add(dto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    public SQLModel getCountBarCode (String barCode){
    	 SQLModel sqlModel = new SQLModel();
         List sqlArgs = new ArrayList();
        String sqlStr = " SELECT COUNT(1) FROM ETS_ITEM_INFO EI WHERE EI.BARCODE =?";
         sqlArgs.add(barCode);
         sqlModel.setSqlStr(sqlStr);
         sqlModel.setArgs(sqlArgs);
         return sqlModel;
    }
 
	//插入数据到ETS_ITEM_INFO
	public SQLModel insertEtsItemInfo(ZeroTurnLineDTO lineDTO){
		SQLModel sqlModel = new SQLModel();
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        List sqlArgs = new ArrayList();
        String sqlStr ="INSERT INTO ETS_ITEM_INFO(" +
        		       " SYSTEMID,\n " +
        		       " BARCODE,\n" +
        		       //" VENDOR_BARCODE, " +
        		       " ITEM_QTY,\n " +
        		       //" DISABLE_DATE," +
        		       " REMARK,\n " +
        		       " START_DATE, \n" +
        		       " ITEM_CODE,\n " +
        		       " PROJECTID," +
        		       " ITEM_STATUS,\n " +
        		       //" ATTRIBUTE1, " +
        		       //" ATTRIBUTE2, " +
        		       //" SENDTOMIS_FLAG," +
        		       " MIS_ITEMNAME, " +                         //   需要
        		       " MIS_ITEMSPEC, " +						//   需要
        		       " CREATION_DATE,\n " +
        		       " CREATED_BY, \n" +
        		       " LAST_UPDATE_DATE, " +					//   需要
        		       " LAST_UPDATE_BY, " +						//   需要
        		       //" ASSET_ID, " +
        		       " ADDRESS_ID,\n " +							//没有值
        		       " FINANCE_PROP,\n" +
        		       //" ATTRIBUTE3, " +
        		       //" PARENT_BARCODE," +
        		       //" LAST_LOC_CHG_DATE, " +
        		       " ORGANIZATION_ID,\n " +
        		       //" FA_BARCODE, " +
        		       " IS_PARENT, " +
        		       " RESPONSIBILITY_USER,\n " +
        		       " RESPONSIBILITY_DEPT,\n " +					//没有值
        		       //" MAINTAIN_USER, " +
        		       //" MAINTAIN_DEPT," +
        		       " MANUFACTURER_ID," +
        		       " IS_SHARE,\n " +
        		       " CONTENT_CODE,\n " +
        		       " CONTENT_NAME,\n " +
        		       //" POWER," +
        		       " LNE_ID,\n  " +
        		       " CEX_ID,\n " +
        		       " OPE_ID,\n  " +
        		       " NLE_ID,\n " +
        		       " IS_TMP,\n " +
        		       " PRICE, \n" +
        		       //" OLD_CONTENT_CODE, " +
        		       //" OLD_CONTENT_NAME, " +
        		       //" REP_MANUFACTURER_ID, " +
        		       " SPECIALITY_DEPT,\n" +
        		       //" DZYH_ADDRESS, " +
        		       //" OTHER_INFO, " +
        		       //" SHARE_STATUS, " +
        		       " IS_TD, " +
        		       //" ACTUAL_QTY, " +
        		       " IS_RENTAL," +
        		       //" REMARK1, " +
        		       " REMARK2, " +
        		       " UNIT_OF_MEASURE, \n " +
        		       //" DISCARD_TYPE, " +
        		       //" DEAL_TYPE, " +
        		       //" REFER_NATIONAL_FUND," +
        		       //" SN_ID, " +
        		       " CONSTRUCT_STATUS " +				//    需要
        		       //" TF_NET_ASSET_VALUE, " +
        		       //" TF_DEPRN_COST, " +
        		       //" TF_DEPRECIATION," +
        		       //" OLD_BARCODE, " +
        		       //" SPECIALITY_USER2, " +
        		       // " SPECIALITY_USER" +
        		       ") " +
        		       " VALUES(" +
        		       " NEWID() ,\n" +    			//SYSTEMID
        		       " ?,\n" +           			//BARCODE
        		       // " ''," +        			    //VENDOR_BARCODE
        		       " CONVERT(decimal, ?),\n" +           			//ITEM_QTY
        		       //"'2012-5-3 9:39:1', " +        //DISABLE_DATE
        		       " ?,\n" +                    //REMARK
        		       " ?,\n " +                   //START_DATE
        		       " ?,\n" +                    //ITEM_CODE
        		       " ?,\n " +                   //PROJECTID
        		       " ?,\n " +				    //ITEM_STATUS
        		       //"'', " +
        		       //"'', " +
        		       //"'', " +
        		       " ?,\n" +
        		       " ?,\n " +
        		       " GETDATE(),\n " +           //CREATION_DATE
        		       " ?,\n" +                      //CREATED_BY
        		       " GETDATE(),\n" + 
        		       " ?,\n" +
        		       //"0, " +
        		       " ?,\n" +                    //ADDRESS_ID
        		       " ?,\n" +                    //FINANCE_PROP
        		       //" '', " +
        		       //"'', " +
        		       //"'2012-5-3 9:39:1', " +
        		       " ?,\n " +                    //ORGANIZATION_ID
        		       //"''," +
        		       " ?,\n" +
        		       " ?,\n " +                    //RESPONSIBILITY_USER
        		       " ?,\n" +                     //RESPONSIBILITY_DEPT
        		       //" ''," +
        		       //" '', " +
        		       " ?, \n " +                    //MANUFACTURER_ID
        		       " ?, \n" +                     //IS_SHARE
        		       " ?, \n" +                     //CONTENT_CODE
        		       " ?, \n" +                     //CONTENT_NAME
        		       //"''," +
        		       " ?, \n" +					  //LNE_ID
        		       " ?, \n" +					  //CEX_ID
        		       " ?, \n" +					  //OPE_ID
        		       " ?, \n" +					  //NLE_ID
        		       " 0, \n" +				      //IS_TMP
        		       " CONVERT(decimal(16,2), ?), \n" +   //PRICE
        		       //"''," +
        		       //" ''," +
        		       //" ''," +
        		       " ?,\n" +                      //SPECIALITY_DEPT
        		       //" '', " +
        		       //"'', " +
        		       //"'', " +
        		       " ?,\n" +
        		       //" 0, " +
        		       " ?,\n" +
        		       //"''," +
        		       " ?,\n" +
        		       " ?, \n" +                     //UNIT_OF_MEASURE
        		       //"''," +
        		       //" '', " +
        		       //"''," +
        		       //" ''," +
        		       " ? \n" +
        		       //" 0," +
        		       //" 0, " +
        		       //"''," +
        		       //" '', " +
        		       //"'', " +
        		       //"''" +
        		       ")";
         sqlArgs.add(lineDTO.getBarcode());
         sqlArgs.add(lineDTO.getItemQty());
         sqlArgs.add(lineDTO.getRemark());
         sqlArgs.add(lineDTO.getStartDate());
         sqlArgs.add(lineDTO.getItemCode());
         sqlArgs.add("d87500c590d34fd3882dd1a73757ef0d");
         sqlArgs.add("PRE_ASSETS");
         sqlArgs.add(lineDTO.getAssetsDescription());
         sqlArgs.add(lineDTO.getItemSpec());
         sqlArgs.add(lineDTO.getCreatedBy());
         sqlArgs.add(lineDTO.getCreatedBy());
         sqlArgs.add(lineDTO.getAddressIdTWO());                     //    没取到值
         sqlArgs.add("PRJ_MTL");
         sqlArgs.add(lineDTO.getCompanyCodeTWO());
         sqlArgs.add("Y");
         sqlArgs.add(lineDTO.getResponsibilityUserTWO());
         sqlArgs.add(lineDTO.getResponsibilityDeptTWO());			//    没取到值
         sqlArgs.add(lineDTO.getManufacturerIdTWO());				//    没取到值
         sqlArgs.add(lineDTO.getIsShare());
         sqlArgs.add(lineDTO.getContentCode());
         sqlArgs.add(lineDTO.getContentName());
         sqlArgs.add(lineDTO.getLneIdTWO());
         sqlArgs.add(lineDTO.getCexIdTWO());
         sqlArgs.add(lineDTO.getOpeIdTWO());
         sqlArgs.add(lineDTO.getNleIdTWO());
         //sqlArgs.add(0);
         sqlArgs.add(lineDTO.getPrice());
         sqlArgs.add(lineDTO.getSpecialityDeptTWO());
         sqlArgs.add(lineDTO.getIsTd());
         sqlArgs.add("N");
         sqlArgs.add(lineDTO.getProcureCode());
         sqlArgs.add(lineDTO.getUnitOfMeasure());
         sqlArgs.add(lineDTO.getIsBulid());
         sqlModel.setSqlStr(sqlStr);
         sqlModel.setArgs(sqlArgs);
         return sqlModel;
	}
	
	 /**
     * 根据主键获取对象   表单头*****
     */
    @SuppressWarnings("unchecked")
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        ZeroTurnHeaderDTO dto = (ZeroTurnHeaderDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT T.TRANS_ID,\n" +
                        "       T.TRANS_NO,\n" +  // REMARK, ORDER_FILER, ORDER_EXECUTER, COMPUTE_DAYS 
                        "       T.ORDER_NO,\n" +
                        "       T.REMARK1,\n" +
                        "       T.ORDER_FILER,\n" +
                        "       T.ORDER_EXECUTER,\n" +
                        "       T.COMPUTE_TIMS,\n" +
                        "       T.TRANS_TYPE,\n" +
                        "       T.TRANS_STATUS,\n" +
                        "		T.STATUS_TYPE,\n" +
                        "		T.ORDER_FILER,\n" +
                        "		T.ORDER_FILER_NAME,\n" +
                        "		T.ORDER_EXECUTER,\n" +
                        "		T.ORDER_EXECUTER_NAME,\n" +
                        "		T.COMPUTE_TIMS,\n" +
                        "       T.ORGANIZATION_ID,\n" +
                        "       dbo.APP_GET_ORGNIZATION_NAME(T.ORGANIZATION_ID) ORGANIZATION_NAME ,\n" +
                        "       AMD.DEPT_NAME  ,\n" +
                        "       T.CREATED_REASON,\n" +
                        "       T.CREATION_DATE,\n" +
                        "       T.CREATED_BY,\n" +
                        "       U.USERNAME CREATED_BY_NAME,\n" +
                        "       U.DEPT_CODE  DEPT_CODE,\n" +
                        "	    T.EMERGENT_LEVEL\n" +
                        "  FROM ZERO_TURN_ADD_H T, SF_USER U ,AMS_MIS_DEPT AMD\n " +
                        " WHERE T.TRANS_ID = ?\n" +
                        "   AND T.CREATED_BY = U.USER_ID\n" +
                        "   AND T.DEPT = AMD.DEPT_CODE";

        sqlArgs.add(dto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    public SQLModel getInserBatchModel(EtsWorkorderBatchDTO etsWorkorderBatch,SfUserDTO sfUser) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO "
            + " ETS_WORKORDER_BATCH("
            + " SYSTEMID,"
            + " WORKORDER_BATCH,"
            + " WORKORDER_BATCH_NAME,"
            + " REMARK,"
            + " PRJ_ID,"
            + " WORKORDER_TYPE,"
            + " STATUS,"
            + " ARCHFLAG,"
            + " ACTID,"
            + " CASEID,"
            + " DISTRIBUTE_GROUP_ID,"
            + " CREATION_DATE,"
            + " CREATED_BY,"
            + " LAST_UPDATE_DATE,"
            + " LAST_UPDATE_BY"
            + ") VALUES ("
            + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?, ?)";

        sqlArgs.add(etsWorkorderBatch.getSystemid());
        sqlArgs.add(etsWorkorderBatch.getWorkorderBatch());
        sqlArgs.add(etsWorkorderBatch.getWorkorderBatchName());
        sqlArgs.add(etsWorkorderBatch.getRemark());
        sqlArgs.add(etsWorkorderBatch.getPrjId());
        sqlArgs.add(etsWorkorderBatch.getWorkorderType());
        sqlArgs.add(etsWorkorderBatch.getStatus());
        sqlArgs.add(etsWorkorderBatch.getArchflag());
        sqlArgs.add(etsWorkorderBatch.getActid());
        sqlArgs.add(etsWorkorderBatch.getCaseid());
        sqlArgs.add(etsWorkorderBatch.getDistributeGroupId());
//        sqlArgs.add(etsWorkorderBatch.getCreationDate());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(etsWorkorderBatch.getLastUpdateDate());
        sqlArgs.add(etsWorkorderBatch.getLastUpdateBy());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
    
	public SQLModel getCreateWorkorderDataModel(ZeroTurnLineDTO dto) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO ETS_WORKORDER\n"
				+ "  (SYSTEMID,\n"
				//工单批号 + "   WORKORDER_BATCH,\n"   NOT NULL
				//工单号 + "   WORKORDER_NO,\n"    NOT NULL
				+ "   WORKORDER_TYPE,\n"
				//工单对象+ "   WORKORDER_OBJECT_NO,\n"
				+ "   START_DATE,\n"
				+ "   IMPLEMENT_DAYS,\n"
				//派往部门/接单部门+ "   GROUP_ID,\n"   NOT NULL
				//工单执行人+ "   IMPLEMENT_BY,\n"
				//扫描人+ "   SCANOVER_BY,\n"
				+ "   CHECKOVER_BY,\n"//核实人
				//组织+ "   ORGANIZATION_ID,\n"
				+ "   WORKORDER_FLAG,\n" //工单状态
				+ "   CREATION_DATE,\n"
				+ "   CREATED_BY,\n"
				+ "   COST_CENTER_CODE)\n"
				+ "VALUES\n"
				+ "  (NEWID(), ? ,"
				//+"?,?,"
				+ " GETDATE(),"
				//?,
				+"?,"
				//+"?,?,"
				+"?,"
				//+"?,?,"
				+" '11',GETDATE(),?,?)";

		//sqlArgs.add(workorderBatchNo);他的是用参数带进来的
		//sqlArgs.add(workorderNo);他的是用参数带进来的
		sqlArgs.add(dto.getTransType());
		//sqlArgs.add(workorderObjectNo);
		sqlArgs.add(60);
		//sqlArgs.add(defineDTO.getGroupId());
		//sqlArgs.add(defineDTO.getImplementBy());
		sqlArgs.add(dto.getCreatedBy());
		//sqlArgs.add(defineDTO.getCheckoverBy());
		//sqlArgs.add(defineDTO.getOrganizationId());
		sqlArgs.add(dto.getCreatedBy());
		sqlArgs.add(dto.getCostCenterCode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
	
	
	public SQLModel getInsertDtlModel(ZeroTurnLineDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_WORKORDER_DTL (\n" +
                //工单号"  (WORKORDER_NO,\n" +  NOT NULL
                //设备条码"   BARCODE,\n" +   NOT NULL
                //设备状态 "   ITEM_STATUS,\n" +
                "   ITEM_QTY,\n" +
                "   REMARK,\n" +
                //设备代码"   ITEM_CODE,\n" +
                //地址ID"   ADDRESS_ID,\n" +
                //机柜编号"   BOX_NO,\n" +
                //网元编号"   NET_UNIT,\n" +
                //父设备条码"   PARENT_BARCODE,\n" +
                //备注：用于条码专业，名称，型号"   ITEM_REMARK,\n" +
                "   RESPONSIBILITY_DEPT,\n" +
                "   RESPONSIBILITY_USER,\n" +
                //维护人员"   MAINTAIN_USER,\n" +
                //厂商ID"   MANUFACTURER_ID,\n" +
                //YN共享"   IS_SHARE,\n" +
                //功率 "   POWER
                ")\n" +
                " VALUES\n" +
                "  (" +
                //"?,?,?,"+
                "?,?,"+
                //"?,?,?,?,?,?,
                "?,?,"+
                //"?,?,?,?"+
                ")";

        //sqlArgs.add(workorderNo);//参数带进来的
        //sqlArgs.add(dto.getBarCode());
        //sqlArgs.add(StrUtil.strToInt(status));//参数带进来的
        sqlArgs.add(StrUtil.strToInt(dto.getItemQty()));
        sqlArgs.add(dto.getRemark());
        //sqlArgs.add(dto.getItemCode());
        //sqlArgs.add(etsItemInfo.getAddressId());
        //sqlArgs.add(etsItemInfo.getBoxNo());
        //sqlArgs.add(etsItemInfo.getNetUnit());
        //sqlArgs.add(etsItemInfo.getParentBarcode());
        //sqlArgs.add("");
        sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(dto.getResponsibilityUser());
        //sqlArgs.add(etsItemInfo.getMaintainUser());
        //sqlArgs.add(etsItemInfo.getManufacturerId());
        //sqlArgs.add(etsItemInfo.getShare());
        //sqlArgs.add(etsItemInfo.getPower());

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
	//修改表头
	@SuppressWarnings("unchecked")
	public SQLModel updateHeader(){
		SQLModel sqlModel = new SQLModel();
        ZeroTurnHeaderDTO dto = (ZeroTurnHeaderDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr =" UPDATE ZERO_TURN_ADD_H  \n"+
        			   " SET TRANS_STATUS=?,\n" +
        			   " STATUS_TYPE=?,\n"+
        			   "     LAST_UPDATE_DATE=GETDATE(),\n" +
        			   //"     LAST_UPDATE_BY=?,\n " +
        			   " 	 ORDER_NO =? ,\n"+
        			   " 	 REMARK1 =? ,\n"+
        			   " 	 ORDER_FILER =? ,\n"+
        			   " 	 ORDER_EXECUTER =? ,\n"+
        			   " 	 COMPUTE_TIMS =? \n"+
        			   " WHERE TRANS_ID=?";
        sqlArgs.add("ALR_ISSUED");
        sqlArgs.add("已下发");
        //sqlArgs.add(user.getUserId());
        sqlArgs.add(dto.getOrderNo());
        sqlArgs.add(dto.getRemark1());
        sqlArgs.add(dto.getOrderFiler());
        sqlArgs.add(dto.getOrderExecuter());
        sqlArgs.add(dto.getComputeTims());
        sqlArgs.add(dto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
	
    @Override
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        ZeroTurnHeaderDTO dto = (ZeroTurnHeaderDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr =
                "UPDATE ZERO_TURN_ADD_H SET \n" +
                       // " SET TRANS_STATUS=?,\n" +
                        "     CREATED_REASON=?,\n" +
                        "     LAST_UPDATE_DATE=GETDATE()\n" +
                       // "     LAST_UPDATE_BY=?\n" +
                        "WHERE TRANS_ID=?";
        
        //sqlArgs.add(dto.getTransStatus());
        sqlArgs.add(dto.getCreatedReason());
        //sqlArgs.add(dto.getUsername());
        sqlArgs.add(dto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    public SQLModel deleteImportModel() {
        ZeroTurnHeaderDTO dto = (ZeroTurnHeaderDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " ZERO_TURN_ADD_L"
                + " WHERE"
                + " TRANS_ID = ?";
        sqlArgs.add(dto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
	
	public SQLModel updateZeroHeader(String status,String val){
		SQLModel sqlModel = new SQLModel();
        ZeroTurnHeaderDTO dto = (ZeroTurnHeaderDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr =" UPDATE ZERO_TURN_ADD_H  \n"+
        			   " SET TRANS_STATUS=?,\n" +
        			   " STATUS_TYPE=?,\n"+
        			   "     LAST_UPDATE_DATE=GETDATE()\n" +
        			   //"   LAST_UPDATE_BY=?,\n " +
        			   " WHERE TRANS_ID=?";
        sqlArgs.add(status);
        sqlArgs.add(val);
        sqlArgs.add(dto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
	
	
	public SQLModel updateZeroHeaderByMgr(String status,String val,String orderExeciter,String orderExecuterName,String orderFiler,String orderFilerName){
		SQLModel sqlModel = new SQLModel();
        ZeroTurnHeaderDTO dto = (ZeroTurnHeaderDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr =" UPDATE ZERO_TURN_ADD_H  \n"+
        			   " SET TRANS_STATUS=?,\n" +
        			   " STATUS_TYPE=?,\n"+
        			   " LAST_UPDATE_DATE=GETDATE(),\n" +
        			   " ORDER_FILER=?,\n " +
        			   " ORDER_FILER_NAME=?,\n " +
        			   " ORDER_EXECUTER=?,\n " +
        			   " ORDER_EXECUTER_NAME=?,\n " +
        			   " COMPUTE_TIMS=?\n " +
        			   " WHERE TRANS_ID=?";
        sqlArgs.add(status);
        sqlArgs.add(val);
        sqlArgs.add(orderExeciter);
        sqlArgs.add(orderExecuterName);
        sqlArgs.add(orderFiler);
        sqlArgs.add(orderFilerName);
        sqlArgs.add(dto.getComputeTims());
        sqlArgs.add(dto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
	
	//修改ETS_ITEM_INFO状态
	@SuppressWarnings("unchecked")
	public SQLModel updateEtsItemStatus(String barcode,String status){
		SQLModel sqlModel = new SQLModel();
        ZeroTurnHeaderDTO dto = (ZeroTurnHeaderDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr=" UPDATE ETS_ITEM_INFO  \n"
        	         +" SET ITEM_STATUS =? \n"
        	         +" WHERE BARCODE =? \n";
        sqlArgs.add(status);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
	
	public SQLModel updateZeroLineStatus(String status,String statusVal){
		SQLModel sqlModel = new SQLModel();
        ZeroTurnHeaderDTO dto = (ZeroTurnHeaderDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr =" UPDATE ZERO_TURN_ADD_L  \n"+
        			   " SET TRANS_STATUS=?, \n" +
        			   " TRANS_STATUS_VAL=? \n"+
        			   " WHERE TRANS_ID=?";
        sqlArgs.add(status);
        sqlArgs.add(statusVal);
        sqlArgs.add(dto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
	
	public SQLModel getExport(String barcode) {
		String os="";
		if (barcode!=null&&barcode!="") {
			String [] arg=barcode.split(",");
			 for (int i = 0; i < arg.length; i++) {
				 String a=arg[i];
				if (i!=arg.length-1) {
					os+=""+a+"','";
				}else {
					os+=a;
				}
			}
		}
		 
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT BARCODE, COMPANY_CODE, PROCURE_CODE, ASSETS_DESCRIPTION, ITEM_SPEC, " +
        		"   MANUFACTURER_NAME, CONTENT_CODE, ITEM_QTY, YEARS, PRICE, " +
        		"   START_DATE, OPE_ID, NLE_ID, IS_BULID, COST_CENTER_CODE,"+
        		"   WORKORDER_OBJECT_NAME, OBJECT_NO, RESPONSIBILITY_USER, RESPONSIBILITY_NAME, PROCURE_TYPE, RECEIVER, " +
        		"   RECEIVER_CONTACT, MIS_PROCURE_CODE,EXPECTED_DATE" +
        		"   FROM ETS_ITEM_TURN WHERE BARCODE IN ( ? )";

        sqlArgs.add(os);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
	//关于WORKORDER的操作
	
	//判断项目编号是否存在
	   public SQLModel existsProjectModel(String projectNum) {
	        SQLModel sqlModel = new SQLModel();
	        List sqlArgs = new ArrayList();
	        String sqlStr = "SELECT COUNT(1) FROM ETS_PA_PROJECTS_ALL SPI WHERE SPI.SEGMENT1 = ?";

	        sqlArgs.add(projectNum);
	        sqlModel.setSqlStr(sqlStr);
	        sqlModel.setArgs(sqlArgs);

	        return sqlModel;
	    }
	   
	 //添加零购项目
	    public SQLModel insertEtsProjects(String projectNum)  {
	        SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = "INSERT INTO "
				+ " ETS_PA_PROJECTS_ALL("
				+ " PROJECT_ID,"
				+ " NAME,"
				+ " SEGMENT1,"
				+ " PROJECT_TYPE,"
				+ " PROJECT_STATUS_CODE,"
				+ " START_DATE,"
				+ " COMPLETION_DATE,"
				+ " ENABLED_FLAG,"
				+ " SOURCE,"
				+ " CREATION_DATE," 	
				+ " CREATED_BY,"
				+ " LAST_UPDATE_DATE,"
				+ " LAST_UPDATE_BY," 
				+ " MIS_PROJECT_ID,"
				+ " ORGANIZATION_ID,"
				+ " PROJECT_CLASS,"
				+ " DESCRIPTION,"
				+ " PROJECT_MANAGER,"
				+ " PM_PROJECT_REFERENCE,"
				+ " PM_PRODUCT_CODE"
				+ ") VALUES ("
				+ " NEWID(), ?, ?, ?, ?, getdate(),getdate(), ?, ?, getdate()" +
						", ?, getdate(), ?, ?, ?, ?, ?, ?, ?, ?" +
						")";
		
			sqlArgs.add("零购转资项目");
			sqlArgs.add(projectNum);
			sqlArgs.add("零购");
			sqlArgs.add("APPROVED");
//				sqlArgs.add(srvProjectInfo.getStartDate());
//				sqlArgs.add(srvProjectInfo.getCompletionDate());
			sqlArgs.add("Y");
			sqlArgs.add("MIS"); 
//				sqlArgs.add(srvProjectInfo.getCreationDate());
			sqlArgs.add(-1);
//				sqlArgs.add(srvProjectInfo.getLastUpdateDate());
			sqlArgs.add(-1);
			sqlArgs.add("111");
			sqlArgs.add(82);
			sqlArgs.add("CAPITAL");
			sqlArgs.add("零购转资项目");
			sqlArgs.add("");
			sqlArgs.add(projectNum);
			sqlArgs.add("AMG");

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    
	    //添加表数据ETS_FLEX_VALUE_SET
	    public SQLModel insertEtsValSet(String projectNum)  {
	        SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = "INSERT INTO "
				+ " ETS_FLEX_VALUE_SET("
				+ " CODE,"
				+ " NAME,"
				+ " DESCRIPTION,"
				+ " IS_INNER,"
				+ " MAINTAIN_FLAG,"
//				+ " CREATION_DATE,"
//				+ " CREATED_BY,"
//				+ " LAST_UPDATE_DATE,"
//				+ " LAST_UPDATE_BY,"
				+ " ENABLED," 	
				+ " FLEX_VALUE_SET_ID"
				+ ") VALUES ("
				+ " ?,?,?,?,?,?,?"
				+		")";
		
			sqlArgs.add("projectNum");
			sqlArgs.add("零购");
			sqlArgs.add("零购");
			sqlArgs.add("N");
			sqlArgs.add("N");
			sqlArgs.add("Y");
			sqlArgs.add("9990");

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    
	    
	    //添加表数据ETS_FLEX_VALUE_SET
	    public SQLModel existEtsValSet(String projectNum)  {
	        SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = " SELECT COUNT(1) FORM ETS_FLEX_VALUE_SET WHERE CODE=? ";
			sqlArgs.add("projectNum");

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    
	    
	    //添加表数据ETS_FLEX_VALUE_SET
	    public SQLModel insertEtsVal(String projectNum)  {
	        SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = "INSERT INTO "
				+ " ETS_FLEX_VALUES("
				+ " FLEX_VALUE_ID,"
				+ " FLEX_VALUE_SET_ID,"
				+ " CODE,"
				+ " VALUE,"
				+ " DESCRIPTION,"
				+ " ENABLED," 	
//				+ " ATTRIBUTE1," 	
//				+ " ATTRIBUTE2," 	
				+ " IS_INNER" 	
				+ ") VALUES ("
				+ " ?,?,?,?,?,?,?"
				+		")";
		
			sqlArgs.add("9999");
			sqlArgs.add("9990");
			sqlArgs.add("ZERO_TURN");
			sqlArgs.add("零购");
			sqlArgs.add("零购");
			sqlArgs.add("Y");
			sqlArgs.add("9999");

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    
	    
	    //添加表数据ETS_FLEX_VALUE_SET
	    public SQLModel existEtsVal(String projectNum)  {
	        SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = " SELECT COUNT(1) FORM  ETS_FLEX_VALUES WHERE CODE=? ";
			sqlArgs.add("projectNum");

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    
	    public SQLModel getObjectNo(int userId,String workOrderCode){
	    	SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr=" SELECT TOP 1 WORKORDER_OBJECT_NO  FROM ETS_OBJECT WHERE WORKORDER_OBJECT_CODE=?";
//            String sqlStr = " SELECT  EO.WORKORDER_OBJECT_NO "+
//					        "  FROM   ETS_OBJECT EO"+
//					       	"  WHERE  (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE = '' OR EO.DISABLE_DATE > GETDATE())"+
//					       	"        AND ('' = '' OR EO.WORKORDER_OBJECT_CODE LIKE '')"+
//					       	"        AND ('' = '' OR EO.WORKORDER_OBJECT_NAME LIKE '')"+
//					       	"        AND ('' = '' OR EO.COST_CODE = '')       AND ('' = '' OR EO.CITY = '')"+
//					       	"        AND ('' = '' OR EO.COUNTY = '')"+
//					       	"        AND EO.ORGANIZATION_ID = ?"+
//					       	"        AND (EO.OBJECT_CATEGORY >= '75' OR EO.OBJECT_CATEGORY <= '73')"+
//					       	"        AND EXISTS (SELECT NULL"+
//					       	"         FROM   AMS_OBJECT_ADDRESS  AOA,"+
//					       	"                ETS_ITEM_INFO       EII,"+
//					       	"                ETS_PA_PROJECTS_ALL EPPA"+
//					       	"         WHERE  EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO"+
//					       	"                AND EO.WORKORDER_OBJECT_CODE=?"+
////					       	"                AND AOA.ADDRESS_ID = EII.ADDRESS_ID"+
//					       	"                AND EII.PROJECTID = EPPA.PROJECT_ID"+
//					       	"                AND EPPA.PROJECT_ID = 'd87500c590d34fd3882dd1a73757ef0d'"+
//					       	"                AND (EII.ITEM_STATUS ='PRE_ASSETS' OR EII.ITEM_STATUS ='PRE_ASSETS' ))";
//			sqlArgs.add(userId);
            sqlArgs.add(workOrderCode);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    	
	    
	      
	
	    }
	    
	    /**
	     * 生成临时工单
	     * @param etsWorkorderDTO
	     * @return SQLModel
	     */
	    public SQLModel getInsertWorkorderDataModel(EtsWorkorderDTO etsWorkorderDTO) throws SQLModelException {
	        SQLModel sqlModel = new SQLModel();
	        List sqlArgs = new ArrayList();
			String sqlStr = "INSERT INTO "
			        + " ETS_WORKORDER_TMP("
			        + " SYSTEMID,"
			        + " WORKORDER_BATCH,"
			        + " WORKORDER_NO,"
			        + " WORKORDER_TYPE,"
			        + " WORKORDER_OBJECT_NO,"
			        + " START_DATE,"
			        + " IMPLEMENT_DAYS,"
			        + " GROUP_ID,"
			        + " IMPLEMENT_BY,"
			        + " PRJ_ID,"
			        + " DISTRIBUTE_DATE,"
			        + " DISTRIBUTE_BY,"
			        + " DOWNLOAD_DATE,"
			        + " DOWNLOAD_BY,"
			        + " SCANOVER_DATE,"
			        + " SCANOVER_BY,"
			        + " UPLOAD_DATE,"
			        + " UPLOAD_BY,"
			        + " CHECKOVER_DATE,"
			        + " CHECKOVER_BY,"
			        + " RESPONSIBILITY_USER,"
			        + " DIFFERENCE_REASON,"
			        + " ORGANIZATION_ID,"
			        + " WORKORDER_FLAG,"
			        + " REMARK,"
			        + " ACTID,"
			        + " CASEID,"
			        + " ARCHFLAG,"
			        + " ATTRIBUTE1,"
			        + " ATTRIBUTE2,"
			        + " ATTRIBUTE3,"
			        + " ATTRIBUTE4,"
			        + " ATTRIBUTE5,"
			        + " ATTRIBUTE6,"
			        + " DISTRIBUTE_GROUP,"
			        + " ATTRIBUTE7,"
			        + " CREATION_DATE,"
			        + " CREATED_BY,"
			        + " COST_CENTER_CODE"
			        + ") VALUES ("
			        + "  NEWID() , ?, ?, ?, ?, GETDATE(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?,?)";

			sqlArgs.add(etsWorkorderDTO.getWorkorderBatch());
			sqlArgs.add(etsWorkorderDTO.getWorkorderNo());
			sqlArgs.add(etsWorkorderDTO.getWorkorderType());
			sqlArgs.add(etsWorkorderDTO.getWorkorderObjectNo());
//	            sqlArgs.add(etsWorkorderDTO.getStartDate());
			sqlArgs.add(etsWorkorderDTO.getImplementDays());
			sqlArgs.add(etsWorkorderDTO.getGroupId());
			sqlArgs.add(etsWorkorderDTO.getImplementBy());
			sqlArgs.add(etsWorkorderDTO.getPrjId());
//	            sqlArgs.add(etsWorkorderDTO.getDistributeDate());
			sqlArgs.add(null);
			sqlArgs.add(etsWorkorderDTO.getDistributeBy());
//	            sqlArgs.add(etsWorkorderDTO.getDownloadDate());
			sqlArgs.add(null);
			sqlArgs.add(etsWorkorderDTO.getDownloadBy());
//	            sqlArgs.add(etsWorkorderDTO.getScanoverDate());
			sqlArgs.add(null);
			sqlArgs.add(etsWorkorderDTO.getScanoverBy());
//	            sqlArgs.add(etsWorkorderDTO.getUploadDate());
			sqlArgs.add(null);
			sqlArgs.add(etsWorkorderDTO.getUploadBy());
//	            sqlArgs.add(etsWorkorderDTO.getCheckoverDate());
			sqlArgs.add(null);
			sqlArgs.add(etsWorkorderDTO.getCheckoverBy());
			sqlArgs.add(etsWorkorderDTO.getResponsibilityUser());
			sqlArgs.add(etsWorkorderDTO.getDifferenceReason());
			sqlArgs.add(etsWorkorderDTO.getOrganizationId());
			sqlArgs.add(etsWorkorderDTO.getWorkorderFlag());
			sqlArgs.add(etsWorkorderDTO.getRemark());
			sqlArgs.add(etsWorkorderDTO.getActid());
			sqlArgs.add(etsWorkorderDTO.getCaseid());
			sqlArgs.add(etsWorkorderDTO.getArchflag());
			sqlArgs.add(etsWorkorderDTO.getAttribute1());
			sqlArgs.add(etsWorkorderDTO.getAttribute2());
			sqlArgs.add(etsWorkorderDTO.getAttribute3());
			sqlArgs.add(etsWorkorderDTO.getAttribute4());
			sqlArgs.add(etsWorkorderDTO.getAttribute5());
			sqlArgs.add(etsWorkorderDTO.getAttribute6());
			sqlArgs.add(etsWorkorderDTO.getDistributeGroup());
			sqlArgs.add(etsWorkorderDTO.getAttribute7());
			sqlArgs.add(etsWorkorderDTO.getCreatedBy());
			sqlArgs.add(etsWorkorderDTO.getCostCenterCode());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);

	        return sqlModel;
	    }
	    
	    
	    public SQLModel getContentName (String contentCode){
	    	SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr=" SELECT CONTENT_NAME FROM AMS_CONTENT_DIC WHERE CONTENT_CODE=?";
            sqlArgs.add(contentCode);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    
	    
	    public SQLModel getAddressId (String objectNo,String companyCode){
	    	SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr= "SELECT aoa.ADDRESS_ID FROM ETS_OBJECT eo,AMS_OBJECT_ADDRESS aoa,ETS_OU_CITY_MAP eocm " +
            		" WHERE  eo.WORKORDER_OBJECT_NO = aoa.OBJECT_NO " +
            		" AND  eo.WORKORDER_OBJECT_CODE = ? " +
            		" AND eo.ORGANIZATION_ID = eocm.ORGANIZATION_ID " +
            		" AND eocm.COMPANY_CODE = ? ";
            sqlArgs.add(objectNo);
            sqlArgs.add(companyCode);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    
	    
	    public SQLModel getAddressName(String objectCode){
	    	SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr="SELECT WORKORDER_OBJECT_NAME FROM ETS_OBJECT WHERE WORKORDER_OBJECT_CODE=?";
            sqlArgs.add(objectCode);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    
	    public SQLModel getEfvCode (String contentCode){
	    	SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr=
				          " SELECT EFV.CODE "+
				          "  FROM AMS_ITEM_CATEGORY_MAP AICM, "+
				          "      ETS_FLEX_VALUE_SET    EFVS, "+
				          "      ETS_FLEX_VALUES       EFV "+
				          "  WHERE AICM.ITEM_CATEGORY = EFV.VALUE "+
				          "  AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID "+
				          "  AND EFVS.CODE = 'ITEM_TYPE'  "+
				          "  AND AICM.FA_CATEGORY_CODE =? ";
            sqlArgs.add(contentCode);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    public SQLModel getAllExcelData (){
	    	SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr=" SELECT " +
        		"  TRANS_ID, COMPANY_CODE, BARCODE, CONTENT_CODE, CONTENT_NAME, " +
        		"   ASSETS_DESCRIPTION, ITEM_SPEC, ITEM_QTY, UNIT_OF_MEASURE, MANUFACTURER_NAME, " +
        		"   OBJECT_NO, WORKORDER_OBJECT_NAME, RESPONSIBILITY_DEPT, RESPONSIBILITY_USER, SPECIALITY_DEPT," +
        		"   START_DATE, YEARS, PRICE, PROCURE_CODE, COST_CENTER_CODE, IS_SHARE, " +
        		"   IS_BULID, LNE_ID, OPE_ID, CEX_ID, NLE_ID, REMARK,ADDRESS_ID,ITEM_CODE,MANUFACTURER_ID" +
        		" FROM  ETS_ITEM_TURN";
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    
	    public SQLModel getWlys (String responsibilityDept){
	    	SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr=" select AMS_LNE_ID from AMS_LNE where LOG_NET_ELE = ?";
            sqlArgs.add(responsibilityDept);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	   }
	    public SQLModel getWlcc (String responsibilityDept){
	    	SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr=" select AMS_LNE_ID from AMS_NLE where LNE_NAME = ?";
            sqlArgs.add(responsibilityDept);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	   }
	    public SQLModel getYwpt (String responsibilityDept){
	    	SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr="select AMS_OPE_ID from AMS_OPE where OPE_NAME = ?";
            sqlArgs.add(responsibilityDept);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	   }
	    
	    public SQLModel getcountyName (String countyId ,String code){
	    	SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr="select COUNTY_NAME from ETS_COUNTY WHERE COUNTY_CODE_MIS = ? AND COMPANY_CODE = ?";
            sqlArgs.add(countyId);
            sqlArgs.add(code);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	   }
	    
	    public SQLModel getTzfl (String responsibilityDept){
	    	SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr=" select AMS_CEX_ID from AMS_CEX where INVEST_CAT_NAME = ?";
            sqlArgs.add(responsibilityDept);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	   }
	   public SQLModel getRespDetp (String responsibilityDept){
	    	SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr="  SELECT DEPT_CODE from AMS_MIS_DEPT where DEPT_NAME = ?";
            sqlArgs.add(responsibilityDept);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	   }
	   
	   public SQLModel getDetpName (String userId){
	    	SQLModel sqlModel = new SQLModel();
           List sqlArgs = new ArrayList();
           String sqlStr="  SELECT DEPT_NAME FROM AMS_MIS_DEPT WHERE DEPT_CODE = (SELECT DEPT_CODE FROM AMS_MIS_EMPLOYEE WHERE EMPLOYEE_NUMBER = ? )";
           sqlArgs.add(userId);
           sqlModel.setSqlStr(sqlStr);
           sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	   }
	   
	   public SQLModel getCbzx (String responsibilityDept){
	    	SQLModel sqlModel = new SQLModel();
           List sqlArgs = new ArrayList();
           String sqlStr="  select COST_CENTER_CODE from AMS_MIS_DEPT where DEPT_NAME = ? ";
           sqlArgs.add(responsibilityDept);
           sqlModel.setSqlStr(sqlStr);
           sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	   }
	   public SQLModel getZYDetp (String responsibilityDept){
	    	SQLModel sqlModel = new SQLModel();
           List sqlArgs = new ArrayList();
           String sqlStr="  select amd.DEPT_CODE from AMS_MIS_DEPT amd," +
           		" SF_GROUP sg where amd.GROUP_NAME = sg.GROUP_NAME " +
           		" and sg.SPECIALITY_DEPT = 'Y' " +
           		" and amd.DEPT_NAME = ?";
           sqlArgs.add(responsibilityDept);
           sqlModel.setSqlStr(sqlStr);
           sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	   }
	   public SQLModel getPers (String pers){
	    	SQLModel sqlModel = new SQLModel();
           List sqlArgs = new ArrayList();
           String sqlStr=" select EMPLOYEE_ID from AMS_MIS_EMPLOYEE" +
           		" where EMPLOYEE_NUMBER = ?";
           sqlArgs.add(pers);
           sqlModel.setSqlStr(sqlStr);
           sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	   }
	   
	   public SQLModel getUnitOfMeasure (String zcml){
	    	SQLModel sqlModel = new SQLModel();
          List sqlArgs = new ArrayList();
          String sqlStr=" SELECT UNIT_OF_MEASURE FROM AMS_CONTENT_DIC WHERE CONTENT_CODE = ?";
          sqlArgs.add(zcml);
          sqlModel.setSqlStr(sqlStr);
          sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	   }
	   
	   
	   public SQLModel getUserName (String pers){
	    	SQLModel sqlModel = new SQLModel();
          List sqlArgs = new ArrayList();
          String sqlStr=" select USERNAME from SF_USER where EMPLOYEE_NUMBER = ?";
          sqlArgs.add(pers);
          sqlModel.setSqlStr(sqlStr);
          sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	   }
	   public SQLModel getManufacturerId (String pers){
	    	SQLModel sqlModel = new SQLModel();
         List sqlArgs = new ArrayList();
         String sqlStr=" SELECT MANUFACTURER_ID FROM AMS_MANUFACTURER WHERE MANUFACTURER_NAME= ?";
         sqlArgs.add(pers);
         sqlModel.setSqlStr(sqlStr);
         sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	   }
	   public SQLModel getBarcode(String companyCode){
		   SQLModel sqlModel = new SQLModel();
           List sqlArgs = new ArrayList();
           String sqlStr="  SELECT dbo.GET_BARCODE(?) ";
           sqlArgs.add(companyCode);
           sqlModel.setSqlStr(sqlStr);
           sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	   }
	   
	   
	   public SQLModel getZeroTurnDto() {
		   ZeroTurnHeaderDTO dto = (ZeroTurnHeaderDTO) super.dtoParameter;
		   SQLModel sqlModel = new SQLModel();
		   List sqlArgs = new ArrayList();
		   String sqlStr=
		   		" SELECT AMD.*  FROM ETS_ITEM_TURN  AMD \n"+
				" WHERE  AMD.COST_CENTER_CODE LIKE dbo.NVL(?, AMD.COST_CENTER_CODE) \n"+
				" AND NOT EXISTS \n"+
			    " (SELECT NULL \n"+
			    "    FROM ETS_ITEM_INFO EII \n"+
			    " WHERE  \n"+
			    " AMD.BARCODE=EII.BARCODE \n"+
			    "  AND EII.ITEM_STATUS<>'PRE_ASSETS') \n";
		   sqlArgs.add(dto.getCenterCode());
	       sqlModel.setSqlStr(sqlStr);
	       sqlModel.setArgs(sqlArgs);
	       return sqlModel;
	    }
	   /**
	    * 校验公司代码
	    * @param code
	    * @return
	    */
	   public SQLModel checkOrgId(String code){
		   SQLModel sqlModel = new SQLModel();
           List sqlArgs = new ArrayList();
           String sqlStr=" SELECT ORGANIZATION_ID FROM ETS_OU_CITY_MAP WHERE COMPANY_CODE = ?";
           sqlArgs.add(code);
           sqlModel.setSqlStr(sqlStr);
           sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	   }
	   
	   public SQLModel getCostCenterCode(String record){
		   SQLModel sqlModel = new SQLModel();
           List sqlArgs = new ArrayList();
           String sqlStr=" SELECT COST_CENTER_CODE FROM ETS_ITEM_TURN WHERE RECORD = ? ";
           sqlArgs.add(record);
           sqlModel.setSqlStr(sqlStr);
           sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	   }
	   
	   /**
	     * 增加  ****
	     */
	    @SuppressWarnings("unchecked")
	    public SQLModel insertWorkItem(String batch,String workNo,String barcode,String transId) {
	        SQLModel sqlModel = new SQLModel();
	        ZeroTurnHeaderDTO dto = (ZeroTurnHeaderDTO) dtoParameter;
	        List sqlArgs = new ArrayList();
	        String sqlStr =" INSERT INTO ETS_WORKORDER_ITEM(WORKORDER_BATCH, WORKORDER_NO, BARCODE,TRANS_ID) VALUES(?, ?, ?,?)";
	        sqlArgs.add(batch);
	        sqlArgs.add(workNo);
	        sqlArgs.add(barcode);
	        sqlArgs.add(transId);
	        sqlModel.setSqlStr(sqlStr);
	        sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    
	    public SQLModel isFilter(String transId) {
	        SQLModel sqlModel = new SQLModel();
	        ZeroTurnHeaderDTO dto = (ZeroTurnHeaderDTO) dtoParameter;
	        List sqlArgs = new ArrayList();
	        String sqlStr =
	        " SELECT COUNT(1) FROM ETS_WORKORDER WHERE \n"+
	        " WORKORDER_NO IN (SELECT WORKORDER_NO FROM ETS_WORKORDER_ITEM WHERE TRANS_ID=?)\n"+
	        " AND ARCHFLAG=0 \n";
	        sqlArgs.add(transId);
	        sqlModel.setSqlStr(sqlStr);
	        sqlModel.setArgs(sqlArgs);
	        return sqlModel;
	    }
	    

}
