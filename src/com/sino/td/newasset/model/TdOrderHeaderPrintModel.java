package com.sino.td.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.td.newasset.dto.TdAssetsTransHeaderDTO;

/**
 * <p>Title: OrderQueryModel</p>
 * <p>Description:程序自动生成SQL构造器“OrderQueryModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class TdOrderHeaderPrintModel extends TdAssetsTransHeaderModel {

	/**
	 * 功能：资产业务头表(EAM)--取代原表 TD_ASSETS_TRANS_HEADER 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter TdAssetsTransHeaderDTO 本次操作的数据
	 */
	public TdOrderHeaderPrintModel(SfUserDTO userAccount, TdAssetsTransHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成资产业务头表(EAM)--取代原表 TD_ASSETS_TRANS_HEADER页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			TdAssetsTransHeaderDTO dto = (TdAssetsTransHeaderDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " AATH.TRANS_ID,"
							+ " AATH.TRANS_NO,"
							+ " AATH.TRANS_TYPE,"
							+ " AATH.TRANSFER_TYPE,"
							+ " AATH.TRANS_STATUS,"
							+ " AATH.FROM_ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " dbo.NVL(AMD.DEPT_NAME, EOCM.COMPANY) FROM_DEPT_NAME,"
							+ " AATH.RECEIVED_USER,"
							+ " AATH.CREATION_DATE,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE,"
							+ " SU.USERNAME CREATED"
							+ " FROM"
							+ " TD_ASSETS_TRANS_HEADER AATH,"
							+ " AMS_MIS_DEPT       AMD,"
							+ " ETS_OU_CITY_MAP    EOCM,"
							+ " SF_USER            SU"
							+ " WHERE"
							+ " AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
							+ " AND AATH.FROM_DEPT *= AMD.DEPT_CODE"
							+ " AND AATH.CREATED_BY = SU.USER_ID"
							+ " AND AATH.TRANS_TYPE = ?"
							+ " AND AATH.CREATION_DATE >= ISNULL(?, AATH.CREATION_DATE)"
							+ " AND AATH.CREATION_DATE <= ISNULL(?, AATH.CREATION_DATE)"
							+ " AND (" + SyBaseSQLUtil.isNull() + " OR AATH.TRANSFER_TYPE = dbo.NVL(?, AATH.TRANSFER_TYPE))"
							+ " AND AATH.TRANS_NO LIKE dbo.NVL(?, AATH.TRANS_NO)"   ;
            if(dto.getPrintType().compareTo("PRINT_TRANS_IN")==0)
            {
                //调入
                sqlStr =sqlStr+ " AND AATH.TO_ORGANIZATION_ID = ?";
            }else{
                sqlStr =sqlStr+ " AND AATH.FROM_ORGANIZATION_ID = ?";
            }

			sqlArgs.add(dto.getTransType());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getTransferType());
			sqlArgs.add(dto.getTransferType());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(userAccount.getOrganizationId());
			if (dto.getTransType().equals(AssetsDictConstant.ASS_RED)) {//调拨单据
				if (dto.getPrintType().equals(AssetsWebAttributes.PRINT_TRANS_OUT)) { //调出方(含各审批人)打印
					StringBuffer acceptStatus = new StringBuffer();
					acceptStatus.append("'");
					acceptStatus.append(AssetsDictConstant.COMPLETED);
					acceptStatus.append("','");
					acceptStatus.append(AssetsDictConstant.ORDER_STS_ASSIGNED);
					acceptStatus.append("','");
					acceptStatus.append(AssetsDictConstant.ORDER_STS_CONFIRMD);
					acceptStatus.append("'");
					sqlStr = sqlStr
							 + " AND AATH.TRANS_STATUS IN (" + acceptStatus + ")"
							//权限控制放开
//							 + " AND EXISTS ("
//							 + " SELECT"
//							 + " NULL"
//							 + " FROM"
//							 + " SF_ACT_LOG SAL"
//							 + " WHERE"
//							 + " AATH.TRANS_ID = SAL.APP_ID"
//							 + " AND AATH.TRANS_NO = SAL.APPLY_NUMBER"
//							 + " AND (SAL.CUR_USERID = ? OR SAL.COMPLETE_USER = ?))"
							 + " AND EXISTS ("
							 + " SELECT"
							 + " NULL"
							 + " FROM"
							 + " TD_ASSETS_TRANS_LINE AATL"
							 + " WHERE"
							 + " AATH.TRANS_ID = AATL.TRANS_ID"
							 + " AND  " + SyBaseSQLUtil.isNotNull("AATL.CONFIRM_DATE") + " )";
//					sqlArgs.add(userAccount.getUserId());
//					sqlArgs.add(userAccount.getUserId());
				} else if (dto.getPrintType().equals(AssetsWebAttributes.PRINT_TRANS_IN)) {
					sqlStr = sqlStr
							 + " AND EXISTS ("
							 + " SELECT"
							 + " NULL"
							 + " FROM"
							 + " TD_ASSETS_TRANS_LINE AATL"
							 + " WHERE"
							 + " AATH.TRANS_ID = AATL.TRANS_ID"
							 + " AND  " + SyBaseSQLUtil.isNotNull("AATL.CONFIRM_DATE") + " )" ;

                            // + " AND AATL.RESPONSIBILITY_USER = ?)";
					//sqlArgs.add(userAccount.getEmployeeId());
				}
			} else {//其他单据
				sqlStr += " AND AATH.TRANS_STATUS = ?";
				sqlArgs.add(AssetsDictConstant.COMPLETED);
//				sqlStr +=  " AND EXISTS ("
//							 + " SELECT"
//							 + " NULL"
//							 + " FROM"
//							 + " SF_ACT_LOG SAL"
//							 + " WHERE"
//							 + " AATH.TRANS_ID = SAL.APP_ID"
//							 + " AND AATH.TRANS_NO = SAL.APPLY_NUMBER"
//							 + " AND (SAL.CUR_USERID = ? OR SAL.COMPLETE_USER = ?))";
//						 sqlArgs.add(userAccount.getUserId());
//						 sqlArgs.add(userAccount.getUserId());
			}
			if (dto.getTransType().equals(AssetsDictConstant.ASS_RED)) {
				sqlStr = sqlStr
						 + " ORDER BY"
						 + " AATH.TRANSFER_TYPE,"
						 + " AATH.CREATION_DATE DESC";
			} else {
				sqlStr += " ORDER BY AATH.CREATION_DATE DESC";
			}
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}

		return sqlModel;
	}
}
