package com.sino.soa.td.srv.AssetPeriodStatus.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.td.srv.AssetPeriodStatus.dto.TdSrvAssetPeriodStatusDTO;

/**
 * <p>Title: SrvAssetBookModel</p>
 * <p>Description:程序自动生成SQL构造器“SrvAssetBookModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author zhoujs
 * @version 1.0
 */


public class TdSrvAssetPeriodModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：资产账簿服务 SRV_ASSET_BOOK 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SrvAssetBookDTO 本次操作的数据
	 */
	public TdSrvAssetPeriodModel(SfUserDTO userAccount, TdSrvAssetPeriodStatusDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成资产账簿服务 SRV_ASSET_BOOK数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		TdSrvAssetPeriodStatusDTO periodStatusDTO = (TdSrvAssetPeriodStatusDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " SRV_ASSET_PERIOD_STATUS("
			+"  BOOK_TYPE_CODE,"
			+ " PERIOD_NAME,"
			+ " MIS_PERIOD_NAME,"
			+ " START_DATE,"
			+ " END_DATE,"
			+ " PERIOD_OPEN_DATE,"
			+ " PERIOD_CLOSE_DATE,"
			+ " GL_TRANSFER_FLAG,"
			+ "	PERIOD_STATUS,"
			+ "	ORGANIZATION_ID"
			+ ") VALUES ("
			+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		sqlArgs.add(periodStatusDTO.getBookTypeCode());
		sqlArgs.add(periodStatusDTO.getPeriodName());
		sqlArgs.add(periodStatusDTO.getMisPeriodName());
		try {
			sqlArgs.add(periodStatusDTO.getStartDate());
			sqlArgs.add(periodStatusDTO.getEndDate());
			sqlArgs.add(periodStatusDTO.getPeriodOpenDate());
            if(periodStatusDTO.getPeriodStatus().equals("CLOSE")){
                sqlArgs.add(periodStatusDTO.getPeriodCloseDate());
            }else{
                sqlArgs.add(null);
            }
		} catch (CalendarException e) {
			
			e.printLog();
		}
		sqlArgs.add(periodStatusDTO.getGlTransferFlag());
		sqlArgs.add(periodStatusDTO.getPeriodStatus());
		sqlArgs.add(periodStatusDTO.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成OU组织信息服务 SRV_OU_ORGANIZATION数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		TdSrvAssetPeriodStatusDTO srvVendorInfoDTO = (TdSrvAssetPeriodStatusDTO)dtoParameter;
		String sqlStr = "UPDATE "
			+ " SRV_ASSET_PERIOD_STATUS SET"
//			+"  BOOK_TYPE_CODE=?,"
//			+ " PERIOD_NAME=?,"
			+ " START_DATE=?,"
			+ " END_DATE=?,"
			+ " PERIOD_OPEN_DATE=?,"
			+ " PERIOD_CLOSE_DATE=?,"
			+ " GL_TRANSFER_FLAG=?,"
			+ "	PERIOD_STATUS=?"
//            + " MIS_PERIOD_NAME=?,"
//            + " ORGANIZATION_ID=?"
			+ " WHERE BOOK_TYPE_CODE=? AND MIS_PERIOD_NAME=?"
			;
//		sqlArgs.add(srvVendorInfoDTO.getBookTypeCode());
//		sqlArgs.add(srvVendorInfoDTO.getPeriodName());
		try {
			sqlArgs.add(srvVendorInfoDTO.getStartDate());
			sqlArgs.add(srvVendorInfoDTO.getEndDate());
			sqlArgs.add(srvVendorInfoDTO.getPeriodOpenDate());

            if(srvVendorInfoDTO.getPeriodStatus().equals("CLOSE")){
                sqlArgs.add(srvVendorInfoDTO.getPeriodCloseDate());
            }else{
                sqlArgs.add(null);
            }
		} catch (CalendarException e) {
			
			e.printLog();
		}
		sqlArgs.add(srvVendorInfoDTO.getGlTransferFlag());
		sqlArgs.add(srvVendorInfoDTO.getPeriodStatus());
//		sqlArgs.add(srvVendorInfoDTO.getMisPeriodName());
//		sqlArgs.add(srvVendorInfoDTO.getOrganizationId());
		sqlArgs.add(srvVendorInfoDTO.getBookTypeCode());
		sqlArgs.add(srvVendorInfoDTO.getMisPeriodName());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	public SQLModel getEcouInforModel() {
		SQLModel sqlModel = new SQLModel();
			String sqlStr = "SELECT" 
			+"	ECOM.BOOK_TYPE_CODE,"
			+"	ECOM.MIS_PERIOD_NAME,"
			+"	ECOM.PERIOD_NAME"
			+"	FROM SRV_ASSET_PERIOD_STATUS ECOM";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
}