package com.sino.ams.print.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.print.dto.AmsBarcodePrintDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: AmsBarcodePrintModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsBarcodePrintModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class AmsBarcodePrintModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：条码打印信息表 AMS_BARCODE_PRINT 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsBarcodePrintDTO 本次操作的数据
	 */
	public AmsBarcodePrintModel(SfUserDTO userAccount, AmsBarcodePrintDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成条码打印信息表 AMS_BARCODE_PRINT数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsBarcodePrintDTO amsBarcodePrint = (AmsBarcodePrintDTO)dtoParameter;
			String sqlStr = "INSERT INTO "
				+ " AMS_BARCODE_PRINT("
				+ " ID,"
				+ " BATCH_NO,"
				+ " TAG_TYPE,"
				+ " TAG_NUMBER,"
				+ " APPLY_REASON,"
				+ " APPLY_USER,"
				+ " APPLY_DATE,"
				+ " APPROVE_USER,"
				+ " APPROVE_DATE,"
				+ " APPROVE_RESULT,"
				+ " PRINT_USER,"
				+ " PRINT_DATE,"
				+ " REMARK,"
                + " STATUS,"
              	+ " END_DATE,"
				+ " APPLY_GROUP,"
				+ " ORGANIZATION_ID,"
				+ " TAG_COLOR"
                + ") VALUES ("
				+ "? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,'0', ?, ?, ?,?)";
		// " + SyBaseSQLUtil.getNewID( "AMS_BARCODE_PRINT_S" ) + " 
            sqlArgs.add(amsBarcodePrint.getId()) ;
            sqlArgs.add(amsBarcodePrint.getBatchNo());
			sqlArgs.add(amsBarcodePrint.getTagType());
			sqlArgs.add(amsBarcodePrint.getTagNumber());
			sqlArgs.add(amsBarcodePrint.getApplyReason());
			sqlArgs.add(amsBarcodePrint.getApplyUser());
			sqlArgs.add(amsBarcodePrint.getApplyDate());
			sqlArgs.add(amsBarcodePrint.getApproveUser());
			sqlArgs.add(amsBarcodePrint.getApproveDate());
			sqlArgs.add(amsBarcodePrint.getApproveResult());
			sqlArgs.add(amsBarcodePrint.getPrintUser());
			sqlArgs.add(amsBarcodePrint.getPrintDate());
			sqlArgs.add(amsBarcodePrint.getRemark());
			sqlArgs.add(amsBarcodePrint.getEndDate());
			sqlArgs.add(amsBarcodePrint.getApplyGroup());
//			sqlArgs.add(amsBarcodePrint.getStatus());
			sqlArgs.add(sfUser.getOrganizationId());
			sqlArgs.add(amsBarcodePrint.getTagColor());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成条码打印信息表 AMS_BARCODE_PRINT数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsBarcodePrintDTO amsBarcodePrint = (AmsBarcodePrintDTO)dtoParameter;
			String sqlStr = "UPDATE AMS_BARCODE_PRINT"
				+ " SET"
//				+ " BATCH_NO = ?,"
//				+ " TAG_TYPE = ?,"
//				+ " TAG_NUMBER = ?,"
//				+ " APPLY_REASON = ?,"
//				+ " APPLY_USER = ?,"
//				+ " APPLY_DATE = ?,"
				+ " APPROVE_USER = ?,"
				+ " APPROVE_DATE = ?,"
				+ " APPROVE_RESULT = ?,"
				+ " PRINT_USER = ?,"
				+ " PRINT_DATE = ?,"
				+ " REMARK = ?,"
				+ " STATUS = ?"
				+ " WHERE"
				+ " ID = ?";
		
//			sqlArgs.add(amsBarcodePrint.getBatchNo());
//			sqlArgs.add(amsBarcodePrint.getTagType());
//			sqlArgs.add(amsBarcodePrint.getTagNumber());
//			sqlArgs.add(amsBarcodePrint.getApplyReason());
//			sqlArgs.add(amsBarcodePrint.getApplyUser());
//			sqlArgs.add(amsBarcodePrint.getApplyDate());
			sqlArgs.add(amsBarcodePrint.getApproveUser());
			sqlArgs.add(amsBarcodePrint.getApproveDate());
			sqlArgs.add(amsBarcodePrint.getApproveResult());
			sqlArgs.add(amsBarcodePrint.getPrintUser());
			sqlArgs.add(amsBarcodePrint.getPrintDate());
			sqlArgs.add(amsBarcodePrint.getRemark());
			sqlArgs.add(amsBarcodePrint.getStatus());
			sqlArgs.add(amsBarcodePrint.getId());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成条码打印信息表 AMS_BARCODE_PRINT数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsBarcodePrintDTO amsBarcodePrint = (AmsBarcodePrintDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " AMS_BARCODE_PRINT"
				+ " WHERE"
				+ " ID = ?";
			sqlArgs.add(amsBarcodePrint.getId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成条码打印信息表 AMS_BARCODE_PRINT数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {                //明细
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsBarcodePrintDTO amsBarcodePrint = (AmsBarcodePrintDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " ID,"
            + "'2'  FIRST,"
            + " BATCH_NO,"
			+ " TAG_TYPE,"
			+ " TAG_NUMBER,"
            + " AMS_PUB_PKG.GET_USER_NAME(APPLY_USER) APPLY_NAME,"
            + " AMS_PUB_PKG.GET_USER_NAME(APPROVE_USER) APPROVE_NAME,"
            + " AMS_PUB_PKG.GET_USER_NAME(PRINT_USER) PRINT_NAME,"
            + " APPLY_REASON,"
			+ " APPLY_USER,"
			+ " APPLY_DATE,"
			+ " APPROVE_USER,"
			+ " APPROVE_DATE,"
			+ " APPROVE_RESULT,"
			+ " PRINT_USER,"
			+ " PRINT_DATE,"
			+ " REMARK,"
			+ " STATUS,"
			+ " END_DATE,"
			+ " APPLY_GROUP,"
			+ " TAG_COLOR"
			+ " FROM"
			+ " AMS_BARCODE_PRINT"
			+ " WHERE"
			+ " ID = ?";
		sqlArgs.add(amsBarcodePrint.getId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成条码打印信息表 AMS_BARCODE_PRINT多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getMuxDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsBarcodePrintDTO amsBarcodePrint = (AmsBarcodePrintDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " ID,"
				+ " BATCH_NO,"
				+ " TAG_TYPE,"
				+ " TAG_NUMBER,"
				+ " APPLY_REASON,"
				+ " APPLY_USER,"
				+ " APPLY_DATE,"
				+ " APPROVE_USER,"
				+ " APPROVE_DATE,"
				+ " APPROVE_RESULT,"
				+ " PRINT_USER,"
				+ " PRINT_DATE,"
				+ " REMARK,"
				+ " STATUS,"
           		+ " END_DATE,"
			    + " APPLY_GROUP,"
			    + " TAG_COLOR"
                + " FROM"
				+ " AMS_BARCODE_PRINT"
				+ " WHERE"
				+ " ( " + SyBaseSQLUtil.isNull() + "  OR ID LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR BATCH_NO LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR TAG_TYPE LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR TAG_NUMBER LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR APPLY_REASON LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR APPLY_USER LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR APPLY_DATE LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR APPROVE_USER LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR APPROVE_DATE LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR APPROVE_RESULT LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR PRINT_USER LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR PRINT_DATE LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR REMARK LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR STATUS LIKE ?)";
			sqlArgs.add(amsBarcodePrint.getId());
			sqlArgs.add(amsBarcodePrint.getId());
			sqlArgs.add(amsBarcodePrint.getBatchNo());
			sqlArgs.add(amsBarcodePrint.getBatchNo());
			sqlArgs.add(amsBarcodePrint.getTagType());
			sqlArgs.add(amsBarcodePrint.getTagType());
			sqlArgs.add(amsBarcodePrint.getTagNumber());
			sqlArgs.add(amsBarcodePrint.getTagNumber());
			sqlArgs.add(amsBarcodePrint.getApplyReason());
			sqlArgs.add(amsBarcodePrint.getApplyReason());
			sqlArgs.add(amsBarcodePrint.getApplyUser());
			sqlArgs.add(amsBarcodePrint.getApplyUser());
			sqlArgs.add(amsBarcodePrint.getApplyDate());
			sqlArgs.add(amsBarcodePrint.getApplyDate());
			sqlArgs.add(amsBarcodePrint.getApproveUser());
			sqlArgs.add(amsBarcodePrint.getApproveUser());
			sqlArgs.add(amsBarcodePrint.getApproveDate());
			sqlArgs.add(amsBarcodePrint.getApproveDate());
			sqlArgs.add(amsBarcodePrint.getApproveResult());
			sqlArgs.add(amsBarcodePrint.getApproveResult());
			sqlArgs.add(amsBarcodePrint.getPrintUser());
			sqlArgs.add(amsBarcodePrint.getPrintUser());
			sqlArgs.add(amsBarcodePrint.getPrintDate());
			sqlArgs.add(amsBarcodePrint.getPrintDate());
			sqlArgs.add(amsBarcodePrint.getRemark());
			sqlArgs.add(amsBarcodePrint.getRemark());
			sqlArgs.add(amsBarcodePrint.getStatus());
			sqlArgs.add(amsBarcodePrint.getStatus());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成条码打印信息表 AMS_BARCODE_PRINT页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsBarcodePrintDTO amsBarcodePrint = (AmsBarcodePrintDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " ID,"
				+ " BATCH_NO,"
				+" CASE WHEN TAG_TYPE='1' THEN '大' ELSE '小' END TAG_TYPE,"
				+ " TAG_NUMBER,"
                + " AMS_PUB_PKG.GET_USER_NAME(APPLY_USER) APPLY_NAME,"
                + " AMS_PUB_PKG.GET_USER_NAME(APPROVE_USER) APPROVE_NAME,"
                + " AMS_PUB_PKG.GET_USER_NAME(PRINT_USER) PRINT_NAME,"
                + " APPLY_REASON,"
				+ " APPLY_USER,"
				+ " APPLY_DATE,"
				+ " APPROVE_USER,"
				+ " APPROVE_DATE,"
				+ " APPROVE_RESULT,"
				+ " PRINT_USER,"
				+ " PRINT_DATE,"
				+ " REMARK,"
				+ " STATUS,"
           		+ " END_DATE,"
			    + " APPLY_GROUP,"
			    + " TAG_COLOR"
                + " FROM"
				+ " AMS_BARCODE_PRINT"
				+ " WHERE"
				+ " ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_USER_NAME(APPLY_USER) LIKE ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + "  OR APPLY_DATE >=?) "
                + " AND ( " + SyBaseSQLUtil.isNull() + "  OR APPLY_DATE <=?) "
                + " AND (APPLY_USER = ? OR APPROVE_USER = ? OR PRINT_USER=?)"    ;
            sqlArgs.add(amsBarcodePrint.getApplyName());
            sqlArgs.add(amsBarcodePrint.getApplyName());
			sqlArgs.add(amsBarcodePrint.getFromDate());
			sqlArgs.add(amsBarcodePrint.getFromDate());
			sqlArgs.add(amsBarcodePrint.getToDate());
			sqlArgs.add(amsBarcodePrint.getToDate());
            sqlArgs.add(sfUser.getUserId());
            sqlArgs.add(sfUser.getUserId());
            sqlArgs.add(sfUser.getUserId());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

}