package com.sino.ams.system.manufacturer.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.manufacturer.EtsManufacturerDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * User: 李轶
 * Date: 2009-12-14
 * Time: 11:42:55
 * Function:		供应商维护
 */
public class ManufacturerModel extends AMSSQLProducer {
    private EtsManufacturerDTO dto = null;


    public ManufacturerModel(BaseUserDTO userAccount, EtsManufacturerDTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.dto =  dtoParameter;
    }

    /**
	 *
	 * Function:		查询所有供应商记录分页数据
	 * @return			SQLModel   返回页面翻页查询SQLModel
	 * @author  		李轶
	 * @Date:   		Dec 14, 2009
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT AM.MANUFACTURER_ID,\n" +
                        "       AM.MANUFACTURER_CODE,\n" +
                        "       AM.MANUFACTURER_NAME,\n" +
                        "       AM.ENABLE,\n" +
                        "       CREATEUSER.USERNAME CREATE_BY,\n" +
                        "       AM.CREATE_DATE,\n" +
                        "       UPDATEUSER.USERNAME LAST_UPDATE_BY,\n" +
                        "       AM.LAST_UPDATE_DATE\n" +
                        "  FROM AMS_MANUFACTURER AM \n" +
                        "  LEFT JOIN  \n" +
                        "  SF_USER          CREATEUSER  \n" +
                        "  ON AM.CREATE_BY = CREATEUSER.USER_ID \n" +
                        "  LEFT JOIN  \n" +
                        "  SF_USER          UPDATEUSER \n" +
                        "  ON AM.LAST_UPDATE_BY =  UPDATEUSER.USER_ID \n" +
//                        "       SF_USER          CREATEUSER,\n" +
//                        "       SF_USER          UPDATEUSER\n"  +
                        " WHERE " + 
//                        "AM.CREATE_BY *= CREATEUSER.USER_ID\n" +
//                        "   AND AM.LAST_UPDATE_BY *= UPDATEUSER.USER_ID" + 
                        "   AM.ENABLE = dbo.NVL(?, AM.ENABLE)" +
						"   AND ( " + SyBaseSQLUtil.nullStringParam() + " OR AM.MANUFACTURER_CODE LIKE '%' || ? || '%' )\n" ;
		sqlArgs.add(dto.getEnable());
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getManufacturerCode());
//		sqlArgs.add(dto.getManufacturerCode());
//		sqlArgs.add(dto.getManufacturerCode()); 
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：	通过框架自动根据SQLModel, 删除指定供应商信息。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		
		String[] tmp = dto.getManufacturerId().split(",");
		String manufacturerId = "";
		for (int i = 0; i < tmp.length; i++) {
			manufacturerId += "'" + tmp[i] + "',"; 
		}
		manufacturerId = manufacturerId.substring(0, manufacturerId.length() - 1);
		
		String sqlStr = "DELETE FROM"
			+ " 				AMS_MANUFACTURER "
			+ " 		 WHERE"
			+ " 				MANUFACTURER_ID IN (" + manufacturerId + ")";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * Function:			得到选定的供应商记录
	 * @return SQLModel 	删除用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT AM.MANUFACTURER_ID,\n" +
                        "       AM.MANUFACTURER_CODE,\n" +
                        "       AM.ENABLE\n" +
                        "  FROM AMS_MANUFACTURER AM\n" +
                        " WHERE AM.MANUFACTURER_ID = ?";
		sqlArgs.add(dto.getManufacturerId());
		//sqlArgs.add("'" + dto.getManufacturerId() + "'");
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成数据插入SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = 
			"INSERT INTO AMS_MANUFACTURER\n" +
	        "  (MANUFACTURER_ID,\n" +
	        "   MANUFACTURER_CODE,\n" +
	        "   MANUFACTURER_NAME,\n" +
	        "   ENABLE,\n" +
	        "   CREATE_BY,\n" +
	        "   CREATE_DATE)\n" +
	        " VALUES\n" +
	        //"  ( " + SyBaseSQLUtil.getNewID( "AMS_MANUFACTURER_S" ) + " , ?, ?, ?, ?, GETDATE())";
	        "  ( NEWID(), ?, ?, ?, ?, GETDATE())";
		sqlArgs.add(dto.getManufacturerCode());
		sqlArgs.add(dto.getManufacturerCode());
		sqlArgs.add(dto.getEnable());
		sqlArgs.add(userAccount.getUserId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造判断供应商信息是否存在的SQL
	 * @return SQLModel
	 */
	public SQLModel getManufacturerExistModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " AMS_MANUFACTURER AM"
						+ " WHERE"
						+ " AM.MANUFACTURER_CODE = ?";
		sqlArgs.add(dto.getManufacturerCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    /**
	 * Function：		修改指定厂商信息记录。
	 * @return SQLModel	修改用SQLModel
	 */
	public SQLModel getDataUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList(0);
		String sqlStr = "UPDATE AMS_MANUFACTURER"
								+ " SET"
								+ " MANUFACTURER_CODE = ?,"
								+ " MANUFACTURER_NAME = ?,"
//								+ " ENABLE = ?,"
								+ "	LAST_UPDATE_BY = ?,"
								+ " LAST_UPDATE_DATE = GETDATE()"
								+ " WHERE"
								+ "  	MANUFACTURER_ID= ?";

		sqlArgs.add(dto.getManufacturerCode());
        sqlArgs.add(dto.getManufacturerCode());
//        sqlArgs.add(dto.getEnable());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getManufacturerId());
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    /**
	 * 功能：构造廠商失效或生效SQL
	 * @return SQLModel
	 */
	public SQLModel getDisableOrEnableModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		
		String[] tmp = dto.getManufacturerId().split(",");
		String manufacturerId = "";
		for (int i = 0; i < tmp.length; i++) {
			manufacturerId += "'" + tmp[i] + "',";
		}
		manufacturerId = manufacturerId.substring(0, manufacturerId.length() - 1);
		
		String sqlStr = "UPDATE"
						+ " AMS_MANUFACTURER "
						+ " SET"
						+ " ENABLE = ?,"
                        + "	LAST_UPDATE_BY = ?,"
                        + " LAST_UPDATE_DATE = GETDATE()"
						+ " WHERE"
						//+ " MANUFACTURER_ID IN ('" + dto.getManufacturerId() + "')";
						+ " MANUFACTURER_ID IN (" + manufacturerId + ")";
        sqlArgs.add(dto.getEnable());
        sqlArgs.add(userAccount.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}