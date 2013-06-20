package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: OrderQueryModel</p>
 * <p>Description:程序自动生成SQL构造器“OrderQueryModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class OrderQueryModel extends AMSSQLProducer {

    /**
     * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsTransHeaderDTO 本次操作的数据
     */
    public OrderQueryModel(SfUserDTO userAccount, AmsAssetsTransHeaderDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：框架自动生成资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER数据详细信息查询SQLModel，请根据实际需要修改。
     *
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
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
            String sqlStr = "SELECT"
                    + " AATH.TRANS_ID,     \n"
                    + " AATH.TRANS_NO,    \n"
                    + " AATH.TRANS_TYPE,    \n"
                    + " AATH.TRANSFER_TYPE,    \n"
                    + " AATH.TRANS_STATUS,    \n"
                    + " CASE AATH.TRANSFER_TYPE WHEN 'INN_DEPT' THEN '部门内调拨单' WHEN 'BTW_DEPT' THEN '部门间调拨单' WHEN 'BTW_COMP' THEN '公司间调拨单' WHEN 'INN_DEPT_RFU' THEN '紧急调拨单(补汇总)' ELSE '' END TRANSFER_TYPE_DESC,    \n"
                    + " AATH.FROM_ORGANIZATION_ID,    \n"
                    + " EOCM.COMPANY,    \n"
                    + " dbo.NVL(AMD.DEPT_NAME, EOCM.COMPANY) FROM_DEPT_NAME,    \n"
                    + " AATH.RECEIVED_USER,    \n"
                    + " AATH.CREATION_DATE,    \n"
                    + " dbo.APP_GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,    \n"
                    + " dbo.APP_GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE,    \n"
                    + " SU.USERNAME CREATED,    \n"
                    + " AATH.CREATED_REASON     \n"
                    + " FROM"
                    + " AMS_ASSETS_TRANS_HEADER AATH,    \n"
                    + " AMS_MIS_DEPT       AMD,    \n"
                    + " ETS_OU_CITY_MAP    EOCM,    \n"
                    + " SF_USER     SU    \n"
                    + " WHERE"
                    + " AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID    \n"
                    + " AND CONVERT(VARCHAR,AATH.FROM_DEPT) *= AMD.DEPT_CODE    \n"
                    + " AND AATH.CREATED_BY = SU.USER_ID    \n"
                    + " AND AATH.TRANS_TYPE = ?    \n"
                    + " AND ( ? = '' OR AATH.CREATION_DATE >= ISNULL(?, AATH.CREATION_DATE) )    \n"
                    + " AND ( ? = '' OR AATH.CREATION_DATE <= ISNULL(?, AATH.CREATION_DATE) )    \n"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AATH.TRANSFER_TYPE = dbo.NVL(?, AATH.TRANSFER_TYPE))    \n";
            sqlArgs.add(dto.getTransType());

            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getSQLEndDate());
            sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getTransferType());
            sqlArgs.add(dto.getTransferType());

            sqlStr = sqlStr
                    + " AND ( ? = '' OR AATH.TRANS_STATUS = dbo.NVL(?, AATH.TRANS_STATUS) )    \n"
                    + " AND ( ? = '' OR AATH.TRANS_NO LIKE dbo.NVL(?, AATH.TRANS_NO) )    \n";
            sqlArgs.add(dto.getTransStatus());
            sqlArgs.add(dto.getTransStatus());
            sqlArgs.add(dto.getTransNo());
            sqlArgs.add(dto.getTransNo());

			if( dto.getTransferType().equals( "BTW_COMP" ) ){
				StringBuffer mngDepts = null;
                sqlStr += " AND ( AATH.CREATED_BY = ? OR (" ;

				DTOSet ds = userAccount.getPriviDeptCodes();
				if( null != ds ){
                    mngDepts = new StringBuffer("");
                    for (int i = 0; i < ds.getSize(); i++) {
						mngDepts.append(((AmsMisDeptDTO) ds.getDTO(i)).getDeptCode());
						if (i < ds.getSize() - 1) {
							mngDepts.append(",");
						}
					}
                    if( !StrUtil.isEmpty( mngDepts )){
                        sqlStr += "  (AATH.TO_ORGANIZATION_ID=? AND CONVERT(INT,AATH.TO_DEPT) IN ("
                               + mngDepts.toString()
                               + ")) OR ";
                    }
				}
		        sqlStr += "     (? IN (SELECT dbo.NVL(SAL.SFACT_TASK_USERS,'') FROM SF_ACT_PROCESS_V SAL WHERE SAL.SFACT_APPL_COLUMN_1 = AATH.TRANS_NO)    \n"
            		    + " 		AND AATH.TO_ORGANIZATION_ID = ? AND CONVERT(VARCHAR,AATH.TO_DEPT) = CONVERT(VARCHAR,?))) )    \n";
            
	            sqlArgs.add(userAccount.getUserId());
	            if( !StrUtil.isEmpty( mngDepts )){
					sqlArgs.add(userAccount.getOrganizationId());
				}
				sqlArgs.add(userAccount.getLoginName());
				sqlArgs.add(userAccount.getOrganizationId());
				sqlArgs.add(userAccount.getDeptCode());
			} else {
				sqlStr += " AND ( AATH.CREATED_BY = ? )     \n"; 
				sqlArgs.add(userAccount.getUserId());
			}
            
			//SJ ADD 判断是否有该标签存在
			if(!StrUtil.isEmpty( dto.getBarcodeSearch() )){
            	sqlStr += " AND EXISTS (SELECT NULL FROM AMS_ASSETS_TRANS_LINE AL WHERE AATH.TRANS_ID = AL.TRANS_ID AND ( AL.BARCODE LIKE ? OR AL.NEW_BARCODE LIKE ? ) )    \n "; 
            	sqlArgs.add(dto.getBarcodeSearch());
            	sqlArgs.add(dto.getBarcodeSearch());
            }
				
            if (dto.getTransType().equals(AssetsDictConstant.ASS_RED)) {
                sqlStr = sqlStr
                        + " ORDER BY"
                        + " AATH.TRANSFER_TYPE,    \n"
                        + " AATH.CREATION_DATE DESC    \n";
            } else {
                sqlStr += " ORDER BY AATH.CREATION_DATE DESC    \n";
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
