package com.sino.soa.mis.srv.vendor.model;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.mis.srv.vendor.dto.SrvVendorInfoDTO;
import java.util.ArrayList;
import java.util.List;
/**
 * <p>Title: SrvAssetBookModel</p>
 * <p>Description:程序自动生成SQL构造器“SrvAssetBookModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author wangzhipeng
 * DATE:2011-09-08
 * DSC:同步厂商信息
 */


public class SrvVendorInfoModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：资产账簿服务 SRV_ASSET_BOOK 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SrvAssetBookDTO 本次操作的数据
	 */
	public SrvVendorInfoModel(SfUserDTO userAccount, SrvVendorInfoDTO dtoParameter) {
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
		SrvVendorInfoDTO srvVendorInfoDTO = (SrvVendorInfoDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " ETS_MIS_PO_VENDORS("
			+"  VENDOR_ID,"
			+ " VENDOR_NAME,"
			+ " VENDOR_NAME_ALT,"
			+ " SEGMENT1,"
			+ " SUMMARY_FLAG,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ "	LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " SOURCE,"
			+ " VENDOR_TYPE"
			+ ") VALUES ("
			+ " NEWID(), ?, ?, ?, ?, GETDATE(), ?, GETDATE(), ?, 'MIS',0)";
		
		sqlArgs.add(srvVendorInfoDTO.getVendorName());
		sqlArgs.add(srvVendorInfoDTO.getVendorNameAlt());
		sqlArgs.add(srvVendorInfoDTO.getVendorNumber());
		sqlArgs.add(srvVendorInfoDTO.getVatFlag());
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(sfUser.getUserId());
//		sqlArgs.add(srvVendorInfoDTO.getVendorTypeDisp());
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
		SrvVendorInfoDTO srvVendorInfoDTO = (SrvVendorInfoDTO)dtoParameter;
		String sqlStr = "UPDATE "
			+ " ETS_MIS_PO_VENDORS SET" 
			+ " VENDOR_NAME=?,"
			+ " VENDOR_NAME_ALT=?,"
			+ " SEGMENT1=?,"
			+ " SUMMARY_FLAG=?,"
			+ " CREATION_DATE=GETDATE(),"
			+ " CREATED_BY=?,"
			+ "	LAST_UPDATE_DATE=GETDATE(),"
			+ " LAST_UPDATE_BY=?,"
			+ " SOURCE='MIS',"
			+ " VENDOR_TYPE=0"
			+ " WHERE SEGMENT1=?"
			;
		sqlArgs.add(srvVendorInfoDTO.getVendorName());
		sqlArgs.add(srvVendorInfoDTO.getVendorNameAlt());
		sqlArgs.add(srvVendorInfoDTO.getVendorNumber());
		sqlArgs.add(srvVendorInfoDTO.getVatFlag());
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(srvVendorInfoDTO.getVendorNumber());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	public SQLModel getEcouInforModel() {
		SQLModel sqlModel = new SQLModel();
			String sqlStr = "SELECT" 
			+"	ECOM.SEGMENT1 "
			+"	FROM ETS_MIS_PO_VENDORS ECOM " 
			+"  WHERE ECOM.SOURCE='MIS'";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
}