package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-4-28
 * Time: 16:10:49
 * To change this template use File | Settings | File Templates.
 */
public class AssetsInviModel extends AMSSQLProducer {

	/**
	 * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsTransHeaderDTO 本次操作的数据
	 */
	public AssetsInviModel(SfUserDTO userAccount, AmsAssetsTransHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		String sqlStr = " SELECT"
						+ " AATH.TRANS_ID,"
						+ " AATH.TRANS_NO,"
						+ " AATH.TRANS_TYPE,"
						+ " AATH.TRANS_STATUS,"
						+ " AATH.FROM_DEPT,"
						+ " AATH.TO_DEPT,"
						+ " AATH.FROM_OBJECT_NO,"
						+ " AATH.TO_OBJECT_NO,"
						+ " AATH.TRANS_DATE,"
						+ " AATH.TO_ORGANIZATION_ID,"
						+ " AATH.CREATION_DATE,"
						+ " AATH.CREATED_BY,"
						+ " AATH.LAST_UPDATE_DATE,"
						+ " AATH.LAST_UPDATE_BY,"
						+ " AATH.FROM_PERSON,"
						+ " AATH.CANCELED_DATE,"
						+ " AATH.CANCELED_REASON,"
						+ " AATH.TO_PERSON,"
						+ " AATH.CREATED_REASON,"
						+ " AATH.APPROVED_DATE,"
						+ " AATH.FROM_ORGANIZATION_ID,"
						+ " AATH.FROM_GROUP,"
						+ " AATH.TO_GROUP,"
						+ " AMD2.DEPT_NAME FROM_DEPT_NAME,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE,"
						+ " SU.USERNAME CREATED,"
						+ " EO.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,"
						+ " EOCM.COMPANY TO_COMPANY_NAME,"
						+ " AMD.DEPT_NAME TO_DEPT_NAME,"
						+ " SG.GROUP_NAME FROM_GROUP_NAME,"
						+ " SU3.USERNAME APPROVED_USER,"
						+ " SU2.USERNAME RECEIVED_USER_NAME"
						+ " FROM"
						+ " AMS_ASSETS_TRANS_HEADER AATH,"
						+ " ETS_OBJECT              EO,"
						+ " ETS_OU_CITY_MAP         EOCM,"
						+ " AMS_MIS_DEPT            AMD2,"
						+ " AMS_MIS_DEPT            AMD,"
						+ " SF_GROUP                SG,"
						+ " SF_USER                 SU,"
						+ " SF_USER                 SU2,"
						+ " SF_USER                 SU3"
						+ " WHERE"
						+ " AATH.CREATED_BY = SU.USER_ID"
						+ " AND AATH.FROM_GROUP = SG.GROUP_ID"
						+ " AND AATH.FROM_DEPT *= AMD2.DEPT_CODE"
						+ " AND AATH.TO_DEPT *= AMD.DEPT_CODE"
						+ " AND AMD.COMPANY_CODE *= EOCM.COMPANY_CODE"
						+ " AND AATH.TO_OBJECT_NO *= EO.WORKORDER_OBJECT_NO"
						+ " AND AATH.RECEIVED_USER *= SU2.USER_ID"
						+ " AND AATH.APPROVED_BY *= SU3.USER_ID"
						+ " AND TRANS_ID = ?";
        sqlArgs.add(dto.getTransId());

        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws com.sino.base.exception.SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
			String sqlStr = "SELECT T.TRANS_TYPE,\n" +
                                  " T.TRANS_ID,\n" +
                                  " T.TRANS_TYPE_NAME,\n" +
                                  " T.TRANS_NO,\n" +
                                  " T.REASON,\n" +
                                  " T.TRANS_STATUS,\n" +
                                  " T.CREATION_DATE,\n" +
                                  " SF_FLOW_PKG.GET_CUR_USER_NAMES_BY_APP_ID(TRANS_ID,'AMS_ASSETS_TRANS_HEADER') CURR_USER \n" +
                             " FROM \n" +
                                 " (SELECT AMS_PUB_PKG.GET_FLEX_VALUE(AACH.ORDER_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_NAME, \n" +
                                         " AACH.TRANS_NO, \n" +
                                         " AACH.HEADER_ID TRANS_ID,\n" +
                                         " AACH.DIFFERENCE_REASON REASON,\n" +
                                         " AMS_PUB_PKG.GET_FLEX_VALUE(AACH.ORDER_STATUS, 'CHKORDER_STATUS') TRANS_STATUS, \n" +
                                         " AACH.CREATION_DATE,\n" +
                                         " AACH.ORDER_TYPE TRANS_TYPE\n" +
                                    " FROM AMS_ASSETS_CHECK_HEADER AACH\n" +
                                    " WHERE ( " + SyBaseSQLUtil.isNull() + "  OR AACH.ORDER_TYPE = ?)\n" +
                                      " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACH.ORDER_STATUS = ?)\n" +
                                      " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACH.TRANS_NO LIKE ?)\n" +
                                      " AND AACH.CREATION_DATE >= ISNULL(?, AACH.CREATION_DATE)" +
							          " AND AACH.CREATION_DATE <= ISNULL(?, AACH.CREATION_DATE)";
           if (!userAccount.isProvAssetsManager()) {
               sqlStr += " AND AACH.IMPLEMENT_BY = ?";
           }
                                 sqlStr += " UNION ALL\n" +
                                 " SELECT DISTINCT AMS_PUB_PKG.GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_NAME, \n" +
                                        " AATH.TRANS_NO, \n" +
                                        " AATH.TRANS_ID TRANS_ID, \n" +
                                        " AATH.CREATED_REASON REASON,\n" +
                                        " AMS_PUB_PKG.GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS, \n" +
                                        " AATH.CREATION_DATE,\n" +
                                        " AATH.TRANS_TYPE TRANS_TYPE\n" +
                                   " FROM AMS_ASSETS_TRANS_HEADER AATH,\n" +
                                        " SF_APPROVE_CONTENT SAC,\n" +
                                        " SF_ACT_LOG SAL" +
                                  " WHERE AATH.TRANS_NO = SAL.APPLY_NUMBER \n" +
                                     "AND SAC.ACTID = SAL.ACTID\n"+
                                     " AND ( " + SyBaseSQLUtil.isNull() + "  OR AATH.TRANS_TYPE = ?)" +
                                     " AND ( " + SyBaseSQLUtil.isNull() + "  OR AATH.TRANS_STATUS = ?)" +
                                     " AND ( " + SyBaseSQLUtil.isNull() + "  OR AATH.TRANS_NO LIKE ?)" +
                                     " AND AATH.CREATION_DATE >= ISNULL(?, AATH.CREATION_DATE)" +
							         " AND AATH.CREATION_DATE <= ISNULL(?, AATH.CREATION_DATE)";
           if (!userAccount.isProvAssetsManager()) {
               sqlStr += " AND SAC.APPROVE_PERSON_ID = ?";
           }
                                     sqlStr += ") T\n" +
                            " ORDER BY T.TRANS_TYPE_NAME,T.TRANS_NO";
            sqlArgs.add(dto.getTransType());
            sqlArgs.add(dto.getTransType());
            sqlArgs.add(dto.getTransStatus());
            sqlArgs.add(dto.getTransStatus());
            sqlArgs.add(dto.getTransNo());
            sqlArgs.add(dto.getTransNo());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getEndDate());
            if (!userAccount.isProvAssetsManager()) {
                sqlArgs.add(userAccount.getUserId());
            }
            sqlArgs.add(dto.getTransType());
            sqlArgs.add(dto.getTransType());
            sqlArgs.add(dto.getTransStatus());
            sqlArgs.add(dto.getTransStatus());
            sqlArgs.add(dto.getTransNo());
            sqlArgs.add(dto.getTransNo());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getEndDate());
            if (!userAccount.isProvAssetsManager()) {
                sqlArgs.add(userAccount.getUserId());
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
