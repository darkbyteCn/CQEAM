package com.sino.ams.newSite.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newSite.dto.EamAddressAddHDTO;
import com.sino.ams.newSite.dto.EamAddressAddLDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/** 
 * @author 作者 :wangzhipeng
 * @version 创建时间：Apr 12, 2011 11:41:35 PM 
 * 类说明: 新增地点流程 表单行信息 Model
 *
 */
public class EamAddressAddLModel extends AMSSQLProducer {

	private SfUserDTO user = null;
	public EamAddressAddLModel(SfUserDTO userAccount, EamAddressAddLDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.user = (SfUserDTO) userAccount;
	}

	/**
	 * 返回所有查询信息，同时分页
	 */
	@SuppressWarnings("unchecked")
	public SQLModel getPageQueryModel(){
		SQLModel sqlModel = new SQLModel();
		EamAddressAddHDTO dto= (EamAddressAddHDTO)dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr=" SELECT MANU.MANUFACTURER_ID,\n" +
				"MANU.MANUFACTURER_CODE,\n" +
				" MANU.MANUFACTURER_NAME,\n" +
				" MANU.ENABLE,\n" +
				" MANU.CREATE_BY,\n" +
				" MANU.CREATE_DATE,\n" +
				" MANU.LAST_UPDATE_BY,\n" +
				" MANU.LAST_UPDATE_DATE\n" +
				" FROM AMS_MANUFACTURER MANU \n" +
				" WHERE ( " + SyBaseSQLUtil.isNull() + "  OR MANU.MANUFACTURER_CODE LIKE ?) \n" +
				" AND MANU.ENABLE = dbo.NVL(?,MANU.ENABLE) " ;
		//sqlArgs.add(dto.getManufacturerCode());
		//sqlArgs.add(dto.getManufacturerCode());
		//sqlArgs.add(dto.getEnable());		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
		
	}
	
	/**
	 * 增加 line 
	 */
    @SuppressWarnings("unchecked")
	public SQLModel getDataCreateModel(){
    	SQLModel sqlModel = new SQLModel();
		EamAddressAddLDTO dto= (EamAddressAddLDTO)dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr=
					"INSERT INTO EAM_ADDRESS_ADD_L\n" +
					"  (LINE_ID,\n" + 
					"   TRANS_ID,\n" + 
					"   WORKORDER_OBJECT_CODE,\n" + 
					"   WORKORDER_OBJECT_NAME,\n" + 
					"   OBJECT_CATEGORY,\n" + 
					"   COUNTY_CODE,\n" + 
					"   AREA_TYPE,\n" + 
					"   CITY,\n" + 
					"   COUNTY,\n" + 
					"   REMARK,\n" + 
					"   ADDR_MAINTAIN_TYPE,\n" + 
					"   ORGANIZATION_ID," +
					"   BTS_NO," +
					"	ERROR_MESSAGE," +
					"	SHARE_TYPE) VALUES\n" + 
					"  (NEWID(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?)";

				sqlArgs.add(dto.getTransId());
				sqlArgs.add(dto.getWorkorderObjectCode());
				sqlArgs.add(dto.getWorkorderObjectName());		
				sqlArgs.add(dto.getObjectCategory() );
				sqlArgs.add(dto.getCountyCode());
				sqlArgs.add(dto.getAreaType() );					
				sqlArgs.add(dto.getCity());
				sqlArgs.add(dto.getCounty());
				sqlArgs.add(dto.getRemark());
				sqlArgs.add(dto.getAddrMaintainType());
				sqlArgs.add(userAccount.getOrganizationId());
				sqlArgs.add(dto.getBtsNo());
				sqlArgs.add(dto.getErrorMessage());
				sqlArgs.add(dto.getShareType());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }

    /**
     * 根据条件获取行级结果集
     */	
    @SuppressWarnings("unchecked")
	public SQLModel getAllAddressLine(){
    	SQLModel sqlModel = new SQLModel();
		EamAddressAddLDTO dto= (EamAddressAddLDTO)dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr=
			"SELECT L.LINE_ID,\n" +
			"       L.TRANS_ID,\n" + 
			"       L.WORKORDER_OBJECT_CODE,\n" + 
			"       L.WORKORDER_OBJECT_NAME,\n" + 
			"       L.OBJECT_CATEGORY,\n" + 
			"       L.COUNTY_CODE,\n" + 
			"       L.AREA_TYPE,\n" + 
			"       L.CITY,\n" + 
			"       L.COUNTY,\n" + 
			"       L.ADDR_MAINTAIN_TYPE,\n" + 
			"       L.ORGANIZATION_ID,\n" +
			"		L.ERROR_MESSAGE,\n" +
			"		L.SHARE_TYPE\n" + 
			"  FROM EAM_ADDRESS_ADD_L L\n" + 
			" WHERE L.TRANS_ID = ?";
		sqlArgs.add(dto.getTransId());         
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
    
    /**
     * 删除行级信息(条件：transId)
     */
    public SQLModel deleAddressLine(String transId){
    	SQLModel sqlModel = new SQLModel();
    	List sqlArgs = new ArrayList();
    	String  sqlStr="DELETE FROM  EAM_ADDRESS_ADD_L" +
    			     "  WHERE TRANS_ID=?";
    	sqlArgs.add(transId);
    	sqlModel.setSqlStr(sqlStr);
    	sqlModel.setArgs(sqlArgs);
    	return sqlModel;
    }
    
    /**
     * 获取对象
     */
    @SuppressWarnings("unchecked")
	public SQLModel getPrimaryKeyDataModel(){
    	SQLModel sqlModel = new SQLModel();
    	List sqlArgs = new ArrayList();
    	String sqlStr ="SELECT MANU.MANUFACTURER_ID, \n" +
    			       "MANU.MANUFACTURER_CODE,  \n" +
    			       "MANU.ENABLE  \n" +
    			       "FROM AMS_MANUFACTURER MANU  \n" +
    			       " WHERE MANU.MANUFACTURER_ID =?  " ;
    	//sqlArgs.add(dto.getManufacturerId());
    	sqlModel.setSqlStr(sqlStr);
    	sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
}
