package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ChkOrderDownLoadModel extends AMSSQLProducer {

	public ChkOrderDownLoadModel(SfUserDTO userAccount,
								 AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：构造获取当前用户可下载的所有盘点工单的SQL
	 * @return SQLModel
	 */
	public SQLModel getAllChkOrdersModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String orderStatus = "'"
							 + AssetsDictConstant.CHK_STATUS_DISTRUIBUTED
							 + "', '"
							 + AssetsDictConstant.CHK_STATUS_DOWNLOADED
							 + "'";
		String sqlStr = "SELECT"
						+ " AACH.HEADER_ID,"
						+ " AACH.TRANS_NO,"
						+ " AACH.START_TIME,"
						+ " AACH.IMPLEMENT_DAYS,"
						+ " dateadd(day,AACH.IMPLEMENT_DAYS,AACH.START_TIME) DEADLINE_DATE,"
						+ " EO.WORKORDER_OBJECT_NO CHECK_LOCATION,"
						+ " EO.WORKORDER_OBJECT_LOCATION OBJECT_LOCATION,"
						+ " EO.WORKORDER_OBJECT_NAME,"
						+ " EO.WORKORDER_OBJECT_CODE OBJECT_CODE,"
						+ " AACH.CREATION_DATE,"
						+ " SU.LOGIN_NAME CREATED_LOGIN_USER,"
						+ " AACH.DISTRIBUTE_DATE,"
						+ " AACH.ORDER_TYPE ORDER_TYPE,"
						+ " NEW_LOCATION,"
						+ " dbo.APP_GET_FLEX_VALUE(AACH.ORDER_TYPE, 'ORDER_TYPE_ASSETS') ORDER_TYPE_NAME"
						+ " FROM"
						+ " AMS_ASSETS_CHECK_HEADER AACH,"
						+ " ETS_OBJECT              EO,"
						+ " SF_USER                 SU"
						+ " WHERE"
						+ " AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO"
						+ " AND AACH.CREATED_BY = SU.USER_ID"
						+ " AND AACH.ORDER_STATUS IN ("
						+ orderStatus
						+ ")"
						+ " AND AACH.IMPLEMENT_BY = ?";
//		sqlArgs.add(AssetsDictConstant.ASS_CHK);
//		sqlArgs.add(AssetsDictConstant.ASS_CHK);
		sqlArgs.add(userAccount.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造更新当前用户下载的工单的SQL
	 * @return SQLModel
	 */
	public SQLModel getDownloadChkOrdersModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_CHECK_HEADER"
						+ " SET"
						+ " ORDER_STATUS = ?,"
						+ " DOWNLOAD_DATE = getdate(),"
						+ " DOWNLOAD_BY = ?"
						+ " WHERE"
						+ " HEADER_ID = ?";
		sqlArgs.add(dto.getOrderStatus());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造获取当前用户某一盘点工单下的所有待盘点资产SQL
	 * @return SQLModel
	 */
    public SQLModel getOrderAssetsModel() {
		SQLModel sqlModel = new SQLModel();
        if(userAccount.getIsTd().equals("Y")){
            sqlModel=getTDAssetsModel();
        }else{
            sqlModel=getAssetsModel();
        }
		return sqlModel;
	}

    private SQLModel getAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT /*+ RULE*/"
						+ " AACL.HEADER_ID,"
						+ " AAAV.BARCODE,"
						+ " AAAV.ITEM_CODE,"
						+ " AAAV.VENDOR_BARCODE,"
						+ " AAAV.START_DATE,"
						+ " AAAV.ITEM_CATEGORY,"
						+ " AAAV.ITEM_NAME,"
						+ " AAAV.ITEM_SPEC,"
						+ " AAAV.RESPONSIBILITY_USER,"
						+ " AAAV.DEPT_CODE RESPONSIBILITY_DEPT,"
						+ " AAAV.MANUFACTURER_ID ,"
						+ " AAAV.IS_SHARE ,"
						+ " AAAV.CONTENT_CODE ,"
						+ " AAAV.CONTENT_NAME ,"
						+ " AAAV.POWER,"
						+ " 'N' REPLACE_FLAG,"
						+ " AAAV.CONSTRUCT_STATUS,"
						+ " AAAV.FINANCE_PROP,"
						+ " AAAV.LNE_ID,"
						+ " AAAV.LOG_NET_ELE LNE_NAME,"
						+ " AAAV.CEX_ID,"
						+ " AAAV.INVEST_CAT_NAME CEX_NAME,"
						+ " AAAV.OPE_ID,"
						+ " AAAV.OPE_NAME,"
						+ " AAAV.NLE_ID,"
						+ " AAAV.LNE_NAME NLE_NAME"
						+ " FROM"
						+ " AMS_ASSETS_CHECK_LINE AACL,"
						+ " AMS_ASSETS_ADDRESS_V  AAAV"
						+ " WHERE"
						+ " AACL.BARCODE = AAAV.BARCODE"
						+ " AND AACL.HEADER_ID = ?";
		sqlArgs.add(dto.getHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    private SQLModel getTDAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT /*+ RULE*/"
						+ " AACL.HEADER_ID,"
						+ " AAAV.BARCODE,"
						+ " AAAV.ITEM_CODE,"
						+ " AAAV.VENDOR_BARCODE,"
						+ " AAAV.START_DATE,"
						+ " AAAV.ITEM_CATEGORY,"
						+ " AAAV.ITEM_NAME,"
						+ " AAAV.ITEM_SPEC,"
						+ " AAAV.RESPONSIBILITY_USER,"
						+ " AAAV.DEPT_CODE RESPONSIBILITY_DEPT,"
						+ " AAAV.MANUFACTURER_ID ,"
						+ " AAAV.IS_SHARE ,"
						+ " AAAV.CONTENT_CODE ,"
						+ " AAAV.CONTENT_NAME ,"
						+ " AAAV.POWER,"
						+ " 'N' REPLACE_FLAG,"
						+ " AAAV.CONSTRUCT_STATUS,"
						+ " AAAV.FINANCE_PROP,"
						+ " AAAV.LNE_ID,"
						+ " AAAV.LOG_NET_ELE LNE_NAME,"
						+ " AAAV.CEX_ID,"
						+ " AAAV.INVEST_CAT_NAME CEX_NAME,"
						+ " AAAV.OPE_ID,"
						+ " AAAV.OPE_NAME,"
						+ " AAAV.NLE_ID,"
						+ " AAAV.LNE_NAME NLE_NAME"
						+ " FROM"
						+ " AMS_ASSETS_CHECK_LINE AACL,"
						+ " TD_ASSETS_ADDRESS_V  AAAV"
						+ " WHERE"
						+ " AACL.BARCODE = AAAV.BARCODE"
						+ " AND AACL.HEADER_ID = ?";
		sqlArgs.add(dto.getHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
