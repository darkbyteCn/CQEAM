package com.sino.ams.dzyh.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.dto.EamDhBillLDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;


/**
 * <p>Title: EamDhBillLModel</p>
 * <p>Description:程序自动生成SQL构造器“EamDhBillLModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class EamDhBillLModel extends AMSSQLProducer {

	/**
	 * 功能：表结构定义-L(EAM) EAM_DH_BILL_L 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamDhBillLDTO 本次操作的数据
	 */
	public EamDhBillLModel(SfUserDTO userAccount, EamDhBillLDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成表结构定义-L(EAM) EAM_DH_BILL_L数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EamDhBillLDTO dto = (EamDhBillLDTO)dtoParameter;
			String sqlStr = "INSERT INTO "
				+ " EAM_DH_BILL_L("
				+ " BILL_LINE_ID,"
				+ " BILL_HEADER_ID,"
				+ " CATALOG_VALUE_ID,"
				+ " ITEM_CODE,"
				+ " QUANTITY,"
				+ " PRICE,"
				+ " RESPONSIBILITY_DEPT,"
				+ " RESPONSIBILITY_USER,"
				+ " WORKORDER_OBJECT_NO,"
				+ " MAINTAIN_USER,"
				+ " LAST_LOC_CHG_DATE,"
				+ " MANUFACTORY,"
				+ " REMARK,"
				+ " CREATED_BY,"
				+ " CREATION_DATE"
				+ " ) VALUES ("
				+ "  NEWID() , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";
		
			sqlArgs.add(dto.getBillHeaderId());
			sqlArgs.add(dto.getCatalogValueId());
			sqlArgs.add(dto.getItemCode());
			sqlArgs.add(dto.getQuantity());
			sqlArgs.add(dto.getPrice());
			sqlArgs.add(dto.getResponsibilityDept());
			sqlArgs.add(dto.getResponsibilityUser());
			sqlArgs.add(dto.getWorkorderObjectNo());
			sqlArgs.add(dto.getMaintainUser());
			sqlArgs.add(dto.getLastLocChgDate());
			sqlArgs.add(dto.getManufactory());
			sqlArgs.add(dto.getRemark());
			sqlArgs.add(userAccount.getUserId());
			
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成表结构定义-L(EAM) EAM_DH_BILL_L数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EamDhBillLDTO dto = (EamDhBillLDTO)dtoParameter;
			String sqlStr = "UPDATE EAM_DH_BILL_L"
				+ " SET"
				+ " BILL_HEADER_ID = ?,"
				+ " CATALOG_VALUE_ID = ?,"
				+ " ITEM_CODE = ?,"
				+ " QUANTITY = ?,"
				+ " PRICE = ?,"
				+ " RESPONSIBILITY_DEPT = ?,"
				+ " RESPONSIBILITY_USER = ?,"
				+ " WORKORDER_OBJECT_NO = ?,"
				+ " MAINTAIN_USER = ?,"
				+ " LAST_LOC_CHG_DATE = ?,"
				+ " MANUFACTORY = ?,"
				+ " REMARK = ?,"
				+ " CREATED_BY = ?,"
				+ " CREATION_DATE = ?,"
				+ " LAST_UPDATE_BY = ?,"
				+ " LAST_UPDATE_DATE = ?"
				+ " WHERE"
				+ " BILL_LINE_ID = ?";
		
			sqlArgs.add(dto.getBillHeaderId());
			sqlArgs.add(dto.getCatalogValueId());
			sqlArgs.add(dto.getItemCode());
			sqlArgs.add(dto.getQuantity());
			sqlArgs.add(dto.getPrice());
			sqlArgs.add(dto.getResponsibilityDept());
			sqlArgs.add(dto.getResponsibilityUser());
			sqlArgs.add(dto.getWorkorderObjectNo());
			sqlArgs.add(dto.getMaintainUser());
			sqlArgs.add(dto.getLastLocChgDate());
			sqlArgs.add(dto.getManufactory());
			sqlArgs.add(dto.getRemark());
			sqlArgs.add(dto.getCreatedBy());
			sqlArgs.add(dto.getCreationDate());
			sqlArgs.add(dto.getLastUpdateBy());
			sqlArgs.add(dto.getLastUpdateDate());
			sqlArgs.add(dto.getBillLineId());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成表结构定义-L(EAM) EAM_DH_BILL_L数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhBillLDTO dto = (EamDhBillLDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " EAM_DH_BILL_L"
				+ " WHERE"
				+ " BILL_LINE_ID = ?";
			sqlArgs.add(dto.getBillLineId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成表结构定义-L(EAM) EAM_DH_BILL_L数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhBillLDTO dto = (EamDhBillLDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " BILL_LINE_ID,"
			+ " BILL_HEADER_ID,"
			+ " CATALOG_VALUE_ID,"
			+ " ITEM_CODE,"
			+ " QUANTITY,"
			+ " PRICE,"
			+ " RESPONSIBILITY_DEPT,"
			+ " RESPONSIBILITY_USER,"
			+ " WORKORDER_OBJECT_NO,"
			+ " MAINTAIN_USER,"
			+ " LAST_LOC_CHG_DATE,"
			+ " MANUFACTORY,"
			+ " REMARK,"
			+ " CREATED_BY,"
			+ " CREATION_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " LAST_UPDATE_DATE"
			+ " FROM"
			+ " EAM_DH_BILL_L"
			+ " WHERE"
			+ " BILL_LINE_ID = ?";
		sqlArgs.add(dto.getBillLineId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成表结构定义-L(EAM) EAM_DH_BILL_L页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EamDhBillLDTO dto = (EamDhBillLDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " EDBH.BILL_HEADER_ID,"
				+ " EDBH.ORG_ID,"
				+ " EOCM.COMPANY EDBH_COMPANY,"
				+ " EDBH.BILL_NO,"
				+ " AMS_PUB_PKG.GET_USER_NAME(EDBH.CREATED_BY) EDBH_USERNAME,"
				+ " EDBH.CREATION_DATE,"
				
				+ " CASE WHEN EDBH.BILL_STATUS='1' THEN '已完成' ELSE '待处理' END BILL_STATUS "
				+ " FROM"
				+ " EAM_DH_BILL_H         EDBH,"
				+ " ETS_OU_CITY_MAP 	  EOCM"
				+ " WHERE"
				+ " EDBH.ORG_ID = EOCM.ORGANIZATION_ID"
				+ " AND EDBH.ORG_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EDBH.ORG_ID))) "
				+ " AND EDBH.CREATION_DATE >= ISNULL(?, EDBH.CREATION_DATE) "
				+ " AND EDBH.CREATION_DATE <= ISNULL(?, EDBH.CREATION_DATE) "
				+ " AND EDBH.BILL_NO LIKE dbo.NVL(?, EDBH.BILL_NO) "
				+ " AND EDBH.BILL_STATUS = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EDBH.BILL_STATUS)))";
			sqlArgs.add(dto.getOrgId());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(dto.getBillNo());
			sqlArgs.add(dto.getBillStatus());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	public SQLModel getDeleteByBillHeaderIdModel(String billHeaderId)
	{
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM EAM_DH_BILL_L WHERE BILL_HEADER_ID = ?";
		sqlArgs.add(billHeaderId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDataCreateHeaderModel(EamDhBillLDTO lineDTO)
		throws SQLModelException
	{
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO  " 
			+ " EAM_DH_BILL_H(" 
			+ " BILL_HEADER_ID," 
			+ " BILL_NO," 
			+ " BILL_STATUS," 
			+ " ORG_ID," 
			+ " CREATED_BY," 
			+ " CREATION_DATE" 
			+ " ) VALUES ( " 
			+ "  NEWID() , ?, ?, ?, ?, GETDATE())";
		sqlArgs.add(lineDTO.getBillNo());
		sqlArgs.add(lineDTO.getBillStatus());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(userAccount.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getCreateEIIModel(EamDhBillLDTO lineDTO)
		throws SQLModelException
	{
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		try
		{
			String sqlStr = " INSERT  INTO \n" 
			+ " ETS_ITEM_INFO(\n" 
			+ " SYSTEMID,\n" 
			+ " BARCODE,\n" 
			+ " ITEM_CODE,\n" 
			+ " ITEM_QTY,\n" 
			+ " PRICE,\n" 
			+ " CREATION_DATE,\n" 
			+ " CREATED_BY,\n " 
			+ " FINANCE_PROP,\n" 
			+ " ADDRESS_ID,\n" 
			+ " ORGANIZATION_ID,\n" 
			+ " RESPONSIBILITY_USER,\n " 
			+ " RESPONSIBILITY_DEPT,\n" 
			+ " MAINTAIN_USER,\n" 
			+ " LAST_LOC_CHG_DATE,\n" 
			+ " ATTRIBUTE3,\n" 
			+ " REMARK\n" 
			+ " )VALUES(\n" 
			+ "  NEWID(),?, ?, ?, ?,GETDATE(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			sqlArgs.add(lineDTO.getBarcode());
			sqlArgs.add(lineDTO.getItemCode());
			sqlArgs.add(lineDTO.getQuantity());
			sqlArgs.add(lineDTO.getPrice());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(lineDTO.getFinanceProp());
			sqlArgs.add(lineDTO.getWorkorderObjectNo());
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(lineDTO.getResponsibilityUser());
			sqlArgs.add(lineDTO.getResponsibilityDept());
			sqlArgs.add(lineDTO.getMaintainUser());
			sqlArgs.add(lineDTO.getLastLocChgDate());
			sqlArgs.add(lineDTO.getManufactory());
			sqlArgs.add(lineDTO.getRemark());
			System.out.println("\n============------->>>>>>>" + lineDTO.getWorkorderObjectNo() + "\n");
			System.out.println("\n============------->>>>>>>" + userAccount.getOrganizationId() + "\n");
			System.out.println("\n============------->>>>>>>" + lineDTO.getCreationDate() + "\n");
			
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			System.out.println("\n----------===========---------========---------=======----==-=-=-=-=-=-\n");
		}
		catch (CalendarException ex)
		{
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}