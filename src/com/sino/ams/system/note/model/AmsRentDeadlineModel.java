package com.sino.ams.system.note.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.note.dto.AmsRentDeadlineDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: AmsRentDeadlineModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsRentDeadlineModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class AmsRentDeadlineModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：租期设置(EAM) AMS_RENT_DEADLINE 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsRentDeadlineDTO 本次操作的数据
	 */
	public AmsRentDeadlineModel(SfUserDTO userAccount, AmsRentDeadlineDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	/**
	 * 功能：框架自动生成租期设置(EAM) AMS_RENT_DEADLINE数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();

			List sqlArgs = new ArrayList();
			AmsRentDeadlineDTO amsRentDeadline = (AmsRentDeadlineDTO)dtoParameter;
			String sqlStr = "INSERT INTO "
				+ " AMS_RENT_DEADLINE("
				+ " DEADLINE_ID,"
				+ " BARCODE,"
				+ " NOTICE_BEFORE,"
				+ " ORGANIZATION_ID,"
				+ " CTREATION_DATE,"
				+ " CREATED_BY"
				+ ") VALUES ("
				+ "  NEWID() , ?, ?, ?, GETDATE(),?)";
		
			sqlArgs.add(amsRentDeadline.getBarcode());
			sqlArgs.add(amsRentDeadline.getNoticeBefore());
			sqlArgs.add(sfUser.getOrganizationId());
			sqlArgs.add(sfUser.getUserId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	/**
	 * 功能：框架自动生成租期设置(EAM) AMS_RENT_DEADLINE数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();

			List sqlArgs = new ArrayList();
			AmsRentDeadlineDTO amsRentDeadline = (AmsRentDeadlineDTO)dtoParameter;
			String sqlStr = "UPDATE AMS_RENT_DEADLINE"
                        + " SET"
                        + " BARCODE = ?,"
                        + " NOTICE_BEFORE = ?,"
                        + " LAST_UPDATE_DATE = GETDATE(),"
                        + " LAST_UPDATE_BY = ?"
                        + " WHERE"
                        + " DEADLINE_ID = ?";
		
			sqlArgs.add(amsRentDeadline.getBarcode());
			sqlArgs.add(amsRentDeadline.getNoticeBefore());
			sqlArgs.add(sfUser.getUserId());
			sqlArgs.add(amsRentDeadline.getDeadlineId());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成租期设置(EAM) AMS_RENT_DEADLINE数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsRentDeadlineDTO amsRentDeadline = (AmsRentDeadlineDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " AMS_RENT_DEADLINE"
				+ " WHERE"
				+ " DEADLINE_ID = ?";
			sqlArgs.add(amsRentDeadline.getDeadlineId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成租期设置(EAM) AMS_RENT_DEADLINE数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {                   //明细
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsRentDeadlineDTO amsRentDeadline = (AmsRentDeadlineDTO)dtoParameter;
		String sqlStr = "SELECT"
                    + " ARD.DEADLINE_ID,\n"
                    + " ESI.ITEM_NAME,\n"
                    + " ESI.ITEM_SPEC,\n"
                    + " ESI.ITEM_CATEGORY,\n"
                    + " ARD.BARCODE,\n"
                    + " AHI.END_DATE,\n"
                    + " ARD.NOTICE_BEFORE\n"
                    + " FROM "
                    + " ETS_ITEM_INFO     EII,\n"
                    + " ETS_SYSTEM_ITEM   ESI,\n"
                    + " AMS_HOUSE_INFO    AHI,\n"
                    + " AMS_RENT_DEADLINE ARD\n"
                    + " WHERE "
                    + " AHI.BARCODE = ARD.BARCODE\n"
                    + " AND EII.BARCODE = AHI.BARCODE\n"
                    + " AND ESI.ITEM_CODE = EII.ITEM_CODE\n"
                    + " AND  " + SyBaseSQLUtil.isNotNull("AHI.END_DATE") + " \n"
                    + " AND ARD.DEADLINE_ID = ?"
                    + " UNION ALL\n"
                    + " SELECT"
                    + " ARD.DEADLINE_ID,\n"
                    + " ESI.ITEM_NAME,\n"
                    + " ESI.ITEM_SPEC,\n"
                    + " ESI.ITEM_CATEGORY,\n"
                    + " ARD.BARCODE,\n"
                    + " ALI.END_DATE,\n"
                    + " ARD.NOTICE_BEFORE\n"
                    + " FROM "
                    + " ETS_ITEM_INFO     EII,\n"
                    + " ETS_SYSTEM_ITEM   ESI,\n"
                    + " AMS_LAND_INFO     ALI,\n"
                    + " AMS_RENT_DEADLINE ARD\n"
                    + " WHERE "
                    + " ALI.BARCODE = ARD.BARCODE\n"
                    + " AND EII.BARCODE = ALI.BARCODE\n"
                    + " AND ESI.ITEM_CODE = EII.ITEM_CODE\n"
                    + " AND  " + SyBaseSQLUtil.isNotNull("ALI.END_DATE") + " "
			        + " AND ARD.DEADLINE_ID = ?";
		sqlArgs.add(amsRentDeadline.getDeadlineId());
		sqlArgs.add(amsRentDeadline.getDeadlineId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成租期设置(EAM) AMS_RENT_DEADLINE多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出 */
	public SQLModel getMuxDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsRentDeadlineDTO amsRentDeadline = (AmsRentDeadlineDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " DEADLINE_ID,"
				+ " BARCODE,"
				+ " NOTICE_BEFORE,"
				+ " ORGANIZATION_ID,"
				+ " CTREATION_DATE,"
				+ " CREATED_BY,"
				+ " LAST_UPDATE_DATE,"
				+ " LAST_UPDATE_BY"
				+ " FROM"
				+ " AMS_RENT_DEADLINE"
				+ " WHERE"
				+ " ( " + SyBaseSQLUtil.isNull() + "  OR DEADLINE_ID LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR BARCODE LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR NOTICE_BEFORE LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ORGANIZATION_ID LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR CTREATION_DATE LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
			sqlArgs.add(amsRentDeadline.getDeadlineId());
			sqlArgs.add(amsRentDeadline.getDeadlineId());
			sqlArgs.add(amsRentDeadline.getBarcode());
			sqlArgs.add(amsRentDeadline.getBarcode());
			sqlArgs.add(amsRentDeadline.getNoticeBefore());
			sqlArgs.add(amsRentDeadline.getNoticeBefore());
			sqlArgs.add(amsRentDeadline.getOrganizationId());
			sqlArgs.add(amsRentDeadline.getOrganizationId());
			sqlArgs.add(amsRentDeadline.getCtreationDate());
			sqlArgs.add(amsRentDeadline.getCtreationDate());
			sqlArgs.add(amsRentDeadline.getCreatedBy());
			sqlArgs.add(amsRentDeadline.getCreatedBy());
			sqlArgs.add(amsRentDeadline.getLastUpdateDate());
			sqlArgs.add(amsRentDeadline.getLastUpdateDate());
			sqlArgs.add(amsRentDeadline.getLastUpdateBy());
			sqlArgs.add(amsRentDeadline.getLastUpdateBy());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 barcode 构造查询数据SQL。
	 * 框架自动生成数据租期设置(EAM) AMS_RENT_DEADLINE详细信息查询SQLModel，请根据实际需要修改。
	 * @param barcode String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByBarcodeModel(String barcode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " DEADLINE_ID,"
			+ " NOTICE_BEFORE,"
			+ " ORGANIZATION_ID,"
			+ " CTREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ " FROM"
			+ " AMS_RENT_DEADLINE"
			+ " WHERE"
			+ " BARCODE = ?";
		sqlArgs.add(barcode);
		
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
		AmsRentDeadlineDTO amsRentDeadline = (AmsRentDeadlineDTO)dtoParameter;
		if(foreignKey.equals("barcode")){
			sqlModel = getDataByBarcodeModel(amsRentDeadline.getBarcode());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 barcode 构造数据删除SQL。
	 * 框架自动生成数据租期设置(EAM) AMS_RENT_DEADLINE数据删除SQLModel，请根据实际需要修改。
	 * @param barcode String
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByBarcodeModel(String barcode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " DEADLINE_ID,"
			+ " NOTICE_BEFORE,"
			+ " ORGANIZATION_ID,"
			+ " CTREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ " FROM"
			+ " AMS_RENT_DEADLINE"
			+ " WHERE"
			+ " BARCODE = ?";
		sqlArgs.add(barcode);
		
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
		AmsRentDeadlineDTO amsRentDeadline = (AmsRentDeadlineDTO)dtoParameter;
		if(foreignKey.equals("barcode")){
			sqlModel = getDeleteByBarcodeModel(amsRentDeadline.getBarcode());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成租期设置(EAM) AMS_RENT_DEADLINE页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出 */
	public SQLModel getPageQueryModel() throws SQLModelException {            //查询
		SQLModel sqlModel = new SQLModel();

			List sqlArgs = new ArrayList();
			AmsRentDeadlineDTO amsRentDeadline = (AmsRentDeadlineDTO)dtoParameter;
			String sqlStr = "SELECT ARI.BARCODE,\n" +
                    "       ARI.RENT_PERSON,\n" +
                    "       ARI.END_DATE,\n" +
                    "       ARI.END_DATE - GETDATE() ,\n" +
                    "       TRUNC(ARI.END_DATE - GETDATE()) DAYS\n" +
                    "  FROM AMS_RENT_INFO ARI\n" +
                    " WHERE TO_NUMBER(ARI.END_DATE - GETDATE()) > 0\n" +
                    "   AND TO_NUMBER(ARI.END_DATE - GETDATE()) < 30"
//                    + " AND  " + SyBaseSQLUtil.isNotNull("ALI.END_DATE") + " "
				    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR ARI.BARCODE LIKE ?)"
				    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR ARI.RENT_PERSON LIKE ?)";

            sqlArgs.add(amsRentDeadline.getBarcode());
			sqlArgs.add(amsRentDeadline.getBarcode());
            sqlArgs.add(amsRentDeadline.getRentPerson());
            sqlArgs.add(amsRentDeadline.getRentPerson());
//			sqlArgs.add(amsRentDeadline.getNoticeBefore());
//			sqlArgs.add(amsRentDeadline.getNoticeBefore());
//            sqlArgs.add(amsRentDeadline.getBarcode());
//			sqlArgs.add(amsRentDeadline.getBarcode());
//			sqlArgs.add(amsRentDeadline.getNoticeBefore());
//			sqlArgs.add(amsRentDeadline.getNoticeBefore());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}


	/**
	 * 功能：获取判断当前用户是否有权限执行数据编辑操作。BARCODE 的存在性
	 * @param barcode String
	 * @return SQLModel
	 */
	public SQLModel getVerifyBarcodeModel(String barcode) {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql =   "SELECT "
                    + " ARD.*"
                    + " FROM"
                    + " AMS_RENT_DEADLINE  ARD\n"
                    + " WHERE"
                    + " ARD.BARCODE = ?";
        strArg.add(barcode);
        sqlModel.setSqlStr(strSql);
        sqlModel.setArgs(strArg);
        return sqlModel;
    }

}