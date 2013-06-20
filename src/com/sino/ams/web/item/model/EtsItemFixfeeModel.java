package com.sino.ams.web.item.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.web.item.dto.EtsItemFixfeeDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsItemFixfeeModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsItemFixfeeModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class EtsItemFixfeeModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：实物资产维修成本(EAM) ETS_ITEM_FIXFEE 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsItemFixfeeDTO 本次操作的数据
	 */
	public EtsItemFixfeeModel(SfUserDTO userAccount, EtsItemFixfeeDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	/**
	 * 功能：框架自动生成实物资产维修成本(EAM) ETS_ITEM_FIXFEE数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel()throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemFixfeeDTO etsItemFixfee = (EtsItemFixfeeDTO)dtoParameter;
        String sqlStr=null;
        try{
         sqlStr = "INSERT INTO ETS_ITEM_FIXFEE\n" +
                "  (SYSTEM_ID,\n" +
                "   BARCODE,\n" +
                "   FIX_DATE,\n" +
                "   AMOUNT,\n" +
                "   REMARK,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY)\n" +
                "VALUES\n" +
                "  ( NEWID() , ?, ?, ?, ?, GETDATE(), ?)";
		
		sqlArgs.add(etsItemFixfee.getBarcode());
		sqlArgs.add(etsItemFixfee.getFixDate());
		sqlArgs.add(etsItemFixfee.getAmount());
		sqlArgs.add(etsItemFixfee.getRemark());
		sqlArgs.add(sfUser.getUserId());
        }catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成实物资产维修成本(EAM) ETS_ITEM_FIXFEE数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel()throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemFixfeeDTO etsItemFixfee = (EtsItemFixfeeDTO)dtoParameter;
        String sqlStr=null;
         try{
         sqlStr = "UPDATE ETS_ITEM_FIXFEE\n" +
                "   SET BARCODE       = ?,\n" +
                "       FIX_DATE         = ?,\n" +
                "       AMOUNT           = ?,\n" +
                "       REMARK           = ?,\n" +
                "       LAST_UPDATE_DATE = GETDATE(),\n" +
                "       LAST_UPDATE_BY   = ?\n" +
                " WHERE SYSTEM_ID = ?";
		
		sqlArgs.add(etsItemFixfee.getBarcode());
		sqlArgs.add(etsItemFixfee.getFixDate());
		sqlArgs.add(etsItemFixfee.getAmount());
		sqlArgs.add(etsItemFixfee.getRemark());
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(etsItemFixfee.getSystemId());
		   }
        catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成实物资产维修成本(EAM) ETS_ITEM_FIXFEE数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemFixfeeDTO etsItemFixfee = (EtsItemFixfeeDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
			+ " ETS_ITEM_FIXFEE"
			+ " WHERE"
			+ " SYSTEM_ID = ?";
		sqlArgs.add(etsItemFixfee.getSystemId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成实物资产维修成本(EAM) ETS_ITEM_FIXFEE数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemFixfeeDTO etsItemFixfee = (EtsItemFixfeeDTO)dtoParameter;
		String sqlStr = "SELECT ESI.ITEM_NAME,\n" +
                "       SU.USERNAME,\n" +
                "       ETF.FIX_DATE,\n" +
                "       ETF.AMOUNT,\n" +
                "       ETF.REMARK,\n" +
                "       ETF.CREATION_DATE,\n" +
                "       ETF.SYSTEM_ID,\n" +
                "       ETF.BARCODE\n" +
                "  FROM ETS_ITEM_FIXFEE ETF,\n" +
                "       ETS_ITEM_INFO   ETI,\n" +
                "       ETS_SYSTEM_ITEM ESI,\n" +
                "       SF_USER         SU\n" +
                " WHERE ETF.BARCODE = ETI.BARCODE\n" +
                "   AND ETI.ITEM_CODE *= ESI.ITEM_CODE\n" +
                "   AND SU.USER_ID = ETF.CREATED_BY\n" +
                "   AND ETF.SYSTEM_ID = ?";
		sqlArgs.add(etsItemFixfee.getSystemId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成实物资产维修成本(EAM) ETS_ITEM_FIXFEE多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getDataMuxModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemFixfeeDTO etsItemFixfee = (EtsItemFixfeeDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " SYSTEM_ID,"
			+ " BARCODE,"
			+ " FIX_DATE,"
			+ " AMOUNT,"
			+ " FIX_NO,"
			+ " ATTRIBUTE1,"
			+ " ATTRIBUTE2,"
			+ " REMARK,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ " FROM"
			+ " ETS_ITEM_FIXFEE"
			+ " WHERE"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR SYSTEM_ID LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR BARCODE LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR FIX_DATE LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR AMOUNT LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR FIX_NO LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE1 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE2 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR REMARK LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
		sqlArgs.add(etsItemFixfee.getSystemId());
		sqlArgs.add(etsItemFixfee.getSystemId());
		sqlArgs.add(etsItemFixfee.getBarcode());
		sqlArgs.add(etsItemFixfee.getBarcode());
		sqlArgs.add(etsItemFixfee.getAmount());
		sqlArgs.add(etsItemFixfee.getAmount());
		sqlArgs.add(etsItemFixfee.getFixNo());
		sqlArgs.add(etsItemFixfee.getFixNo());
		sqlArgs.add(etsItemFixfee.getAttribute1());
		sqlArgs.add(etsItemFixfee.getAttribute1());
		sqlArgs.add(etsItemFixfee.getAttribute2());
		sqlArgs.add(etsItemFixfee.getAttribute2());
		sqlArgs.add(etsItemFixfee.getRemark());
		sqlArgs.add(etsItemFixfee.getRemark());
		sqlArgs.add(etsItemFixfee.getCreationDate());
		sqlArgs.add(etsItemFixfee.getCreationDate());
		sqlArgs.add(etsItemFixfee.getCreatedBy());
		sqlArgs.add(etsItemFixfee.getCreatedBy());
		sqlArgs.add(etsItemFixfee.getLastUpdateDate());
		sqlArgs.add(etsItemFixfee.getLastUpdateDate());
		sqlArgs.add(etsItemFixfee.getLastUpdateBy());
		sqlArgs.add(etsItemFixfee.getLastUpdateBy());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 barcodeNo 构造查询数据SQL。
	 * 框架自动生成数据实物资产维修成本(EAM) ETS_ITEM_FIXFEE详细信息查询SQLModel，请根据实际需要修改。
	 * @param barcodeNo String 
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByBarcodeNoModel(String barcodeNo){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " SYSTEM_ID,"
			+ " FIX_DATE,"
			+ " AMOUNT,"
			+ " FIX_NO,"
			+ " ATTRIBUTE1,"
			+ " ATTRIBUTE2,"
			+ " REMARK,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ " FROM"
			+ " ETS_ITEM_FIXFEE"
			+ " WHERE"
			+ " BARCODE = ?";
		sqlArgs.add(barcodeNo);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键获取数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey){
		SQLModel sqlModel = null;
		EtsItemFixfeeDTO etsItemFixfee = (EtsItemFixfeeDTO)dtoParameter;
		if(foreignKey.equals("barcodeNo")){
			sqlModel = getDataByBarcodeNoModel(etsItemFixfee.getBarcode());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 barcodeNo 构造数据删除SQL。
	 * 框架自动生成数据实物资产维修成本(EAM) ETS_ITEM_FIXFEE 数据删除SQLModel，请根据实际需要修改。
	 * @param barcodeNo String 
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDeleteByBarcodeNoModel(String barcodeNo){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM"
						+ " ETS_ITEM_FIXFEE"
						+ " WHERE"
						+ " BARCODE = ?";
		sqlArgs.add(barcodeNo);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键字段删除数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDeleteByForeignKeyModel(String foreignKey){
		SQLModel sqlModel = null;
		EtsItemFixfeeDTO etsItemFixfee = (EtsItemFixfeeDTO)dtoParameter;
		if(foreignKey.equals("barcodeNo")){
			sqlModel = getDeleteByBarcodeNoModel(etsItemFixfee.getBarcode());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成实物资产维修成本(EAM) ETS_ITEM_FIXFEE页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemFixfeeDTO etsItemFixfee = (EtsItemFixfeeDTO)dtoParameter;
		String sqlStr = "SELECT ESI.ITEM_NAME,\n" +
                "       SU.USERNAME,\n" +
                "       ETF.FIX_DATE,\n" +
                "       ETF.AMOUNT,\n" +
                "       ETF.REMARK,\n" +
                "       ETF.CREATION_DATE,\n" +
                "       ETF.SYSTEM_ID,\n" +
                "       dbo.NVL(NULL, ESI.ITEM_NAME)\n" +
                "  FROM ETS_ITEM_FIXFEE ETF,\n" +
                "       ETS_ITEM_INFO   EII,\n" +
                "       ETS_SYSTEM_ITEM ESI,\n" +
                "       SF_USER         SU\n" +
                " WHERE ETF.BARCODE = EII.BARCODE\n" +
                "   AND EII.ITEM_CODE *= ESI.ITEM_CODE\n" +
                "   AND SU.USER_ID = ETF.CREATED_BY\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ETF.CREATION_DATE >= TO_DATE(?, 'YYYY-MM-DD'))\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ETF.CREATION_DATE < TO_DATE(?, 'YYYY-MM-DD')+1)\n" +
                "   AND EII.ORGANIZATION_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EII.ORGANIZATION_ID)))";
        sqlArgs.add(etsItemFixfee.getItemName());
        sqlArgs.add(etsItemFixfee.getItemName());
        sqlArgs.add(etsItemFixfee.getFromDate());
        sqlArgs.add(etsItemFixfee.getFromDate());
        sqlArgs.add(etsItemFixfee.getToDate());
        sqlArgs.add(etsItemFixfee.getToDate());
        sqlArgs.add(etsItemFixfee.getCompany());

        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}