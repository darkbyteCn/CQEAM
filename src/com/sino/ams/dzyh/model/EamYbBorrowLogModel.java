package com.sino.ams.dzyh.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.dto.EamYbBorrowLogDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EamYbBorrowLogModel</p>
 * <p>Description:程序自动生成SQL构造器“EamYbBorrowLogModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author mshtang
 * @version 1.0
 */

public class EamYbBorrowLogModel extends AMSSQLProducer {

    /**
     * 功能：仪器仪表借用日志 EAM_YB_BORROW_LOG 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EamYbBorrowLogDTO 本次操作的数据
     */
    public EamYbBorrowLogModel(BaseUserDTO userAccount, EamYbBorrowLogDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：框架自动生成仪器仪表借用日志 EAM_YB_BORROW_LOG数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            EamYbBorrowLogDTO dto = (EamYbBorrowLogDTO) dtoParameter;
            String sqlStr = "INSERT INTO "
                    + " EAM_YB_BORROW_LOG("
                    + " BORROW_LOG_ID,"
                    + " BARCODE,"
                    + " STATUS,"
                    + " BORROW_USER_ID,"
                    + " BORROW_DATE,"
                    + " RETURN_DATE_PLAN,"
                    + " REMARK,"
                    + " ORG_ID,"
                    + " GROUP_ID,"
                    + " CREATED_BY,"
                    + " CREATION_DATE"
                    + ") VALUES (?, ?, ?, ?, GETDATE(), ?, ?, ?, ?, ?, GETDATE())";

            sqlArgs.add(dto.getBorrowLogId());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getStatus());
            sqlArgs.add(userAccount.getUserId());
            sqlArgs.add(dto.getReturnDatePlan());
            sqlArgs.add(dto.getRemark());
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(dto.getGroupId());
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
     * 功能：框架自动生成仪器仪表借用日志 EAM_YB_BORROW_LOG数据更新SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            EamYbBorrowLogDTO dto = (EamYbBorrowLogDTO) dtoParameter;
            String sqlStr = "UPDATE EAM_YB_BORROW_LOG"
                    + " SET"
                    + " STATUS = ?,"
                    + " BORROW_DATE = GETDATE(),"
                    + " RETURN_DATE_PLAN = ?,"
                    + " GROUP_ID = ?,"
                    + " REMARK = ?,"
                    + " LAST_UPDATE_BY = ?,"
                    + " LAST_UPDATE_DATE = GETDATE()"
                    + " WHERE"
                    + " BORROW_LOG_ID = ?";

            sqlArgs.add(dto.getStatus());
            sqlArgs.add(dto.getReturnDatePlan());
            sqlArgs.add(dto.getGroupId());
            sqlArgs.add(dto.getRemark());
            sqlArgs.add(userAccount.getUserId());
            sqlArgs.add(dto.getBorrowLogId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }


    /**
     * 功能：构造仪器仪表申请撤销SQL
     *
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getBorrowCancelModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamYbBorrowLogDTO dto = (EamYbBorrowLogDTO) dtoParameter;
        String sqlStr = "UPDATE"
                + " EAM_YB_BORROW_LOG"
                + " SET"
                + " STATUS = ?,"
                + " CANCEL_DATE = GETDATE(),"
                + " LAST_UPDATE_BY = ?,"
                + " LAST_UPDATE_DATE = GETDATE()"
                + " WHERE"
                + " BORROW_LOG_ID = ?";

        sqlArgs.add(dto.getStatus());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getBorrowLogId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成仪器仪表借用日志 EAM_YB_BORROW_LOG数据详细信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamYbBorrowLogDTO dto = (EamYbBorrowLogDTO) dtoParameter;
        String sqlStr = "SELECT"
                + " EII.BARCODE,"
                + " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
                + " ESI.ITEM_NAME,"
                + " ESI.ITEM_SPEC,"
                + " EDCV.CATALOG_NAME,"
                + " EO.WORKORDER_OBJECT_NAME,"
                + " EII.ORGANIZATION_ID,"
                + " AMS_PUB_PKG.GET_ORGNIZATION_NAME(EII.ORGANIZATION_ID) COMPANY,"
                + " EII.START_DATE,"
                + " AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
                + " AME.USER_NAME RESPONSIBILITY_USER_NAME,"
                + " EII.MAINTAIN_USER,"
                + " EYBL.BORROW_LOG_ID,"
                + " CASE WHEN EYBL.BORROW_LOG_ID IS NULL OR EYBL.BORROW_LOG_ID='' THEN '未被申请' ELSE '已被申请' END  IS_APPLYED, "
                + " EYBL.STATUS,"
                + " AMS_PUB_PKG.GET_FLEX_VALUE(EYBL.STATUS, 'YB_BORROW_STATUS') STATUS_VALUE,"
                + " EYBL.CREATED_BY,"
                + " AMS_PUB_PKG.GET_USER_NAME(EYBL.CREATED_BY) CREATED_USER,"
                + " EYBL.CREATION_DATE,"
                + " EYBL.BORROW_DATE,"
                + " EYBL.RETURN_DATE_PLAN,"
                + " EYBL.APPROVE_CONTENT,"
                + " EYBL.GROUP_ID,"
                + " AMS_PUB_PKG.GET_GROUP_NAME(EYBL.GROUP_ID) GROUP_NAME,"
                + " EYBL.BORROW_USER_ID,"
                + " AMS_PUB_PKG.GET_USER_NAME(EYBL.BORROW_USER_ID) BORROW_USER"
                + " FROM"
                + " AMS_MIS_DEPT          AMD,"
                + " AMS_MIS_EMPLOYEE      AME,"
                + " EAM_YB_BORROW_LOG     EYBL,"
                + " ETS_OBJECT            EO,"
                + " AMS_OBJECT_ADDRESS    AOA,"
                + " EAM_DH_CATALOG_VALUES EDCV,"
                + " ETS_SYSTEM_ITEM       ESI,"
                + " ETS_ITEM_INFO         EII"
                + " WHERE"
                + " EII.ITEM_CODE = ESI.ITEM_CODE"
                + " AND ESI.ITEM_CATEGORY2 = EDCV.CATALOG_CODE"
                + " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
                + " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
                + " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE"
                + " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
                + " AND EII.BARCODE = EYBL.BARCODE"
                + " AND AOA.BOX_NO = '0000'"
                + " AND AOA.NET_UNIT = '0000'"
                + " AND ESI.ITEM_CATEGORY = ?"
                + " AND EYBL.BORROW_LOG_ID = ?";
        sqlArgs.add(LvecDicts.CATEGORY_YQYB);
        sqlArgs.add(dto.getBorrowLogId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成仪器仪表借用日志 EAM_YB_BORROW_LOG页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamYbBorrowLogDTO dto = (EamYbBorrowLogDTO) dtoParameter;
        String sqlStr = "SELECT"
                + " EII.BARCODE,"
                + " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
                + " ESI.ITEM_NAME,"
                + " ESI.ITEM_SPEC,"
                + " EDCV.CATALOG_NAME,"
                + " EO.WORKORDER_OBJECT_NAME,"
                + " EII.ORGANIZATION_ID,"
                + " AMS_PUB_PKG.GET_ORGNIZATION_NAME(EII.ORGANIZATION_ID) COMPANY,"
                + " EII.START_DATE,"
                + " AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
                + " AME.USER_NAME RESPONSIBILITY_USER_NAME,"
                + " EII.MAINTAIN_USER,"
                + " EYBL.BORROW_LOG_ID,"
                + " CASE WHEN EYBL.BORROW_LOG_ID IS NULL OR EYBL.BORROW_LOG_ID='' THEN '未被申请' ELSE '已被申请' END  IS_APPLYED, "
                + " EYBL.STATUS,"
                + " AMS_PUB_PKG.GET_FLEX_VALUE(EYBL.STATUS, 'YB_BORROW_STATUS') STATUS_VALUE,"
                + " EYBL.CREATED_BY,"
                + " AMS_PUB_PKG.GET_USER_NAME(EYBL.CREATED_BY) CREATED_USER,"
                + " EYBL.CREATION_DATE,"
                + " EYBL.BORROW_DATE,"
                + " EYBL.RETURN_DATE_PLAN,"
                + " EYBL.BORROW_USER_ID,"
                + " AMS_PUB_PKG.GET_USER_NAME(EYBL.BORROW_USER_ID) BORROW_USER"
                + " FROM"
                + " AMS_MIS_DEPT          AMD,"
                + " AMS_MIS_EMPLOYEE      AME,"
                + " EAM_YB_BORROW_LOG     EYBL,"
                + " ETS_OBJECT            EO,"
                + " AMS_OBJECT_ADDRESS    AOA,"
                + " EAM_DH_CATALOG_VALUES EDCV,"
                + " ETS_SYSTEM_ITEM       ESI,"
                + " ETS_ITEM_INFO         EII"
                + " WHERE"
                + " EII.ITEM_CODE = ESI.ITEM_CODE"
                + " AND ESI.ITEM_CATEGORY2 = EDCV.CATALOG_CODE"
                + " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
                + " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
                + " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE";
        if (dto.getResponsibilityUserName().equals("")) {
            sqlStr += " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID";
        } else {
            sqlStr += " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID";
        }

        sqlStr += " AND EII.BARCODE *= EYBL.BARCODE"
                + " AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)"
                + " AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)";

        if (!dto.getResponsibilityUserName().equals("")) {
            sqlStr += " AND AME.USER_NAME LIKE dbo.NVL(?, AME.USER_NAME)";
        }

        sqlStr += " AND AOA.BOX_NO = '0000'"
                + " AND AOA.NET_UNIT = '0000'"
                + " AND ESI.ITEM_CATEGORY = ?"
                + " AND EII.ORGANIZATION_ID = ?"
                + " AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE = '' OR EII.DISABLE_DATE > GETDATE())"
                + " AND EII.BARCODE NOT IN("
                + " SELECT"
                + " DISTINCT EYBL2.BARCODE"
                + " FROM"
                + " EAM_YB_BORROW_LOG EYBL2"
                + " WHERE"
                + " EYBL2.STATUS IN ('YB_BR_STATUS_WAPPROVE','YB_BR_STATUS_APPROVE','YB_BR_STATUS_BORROW'))"
                + " AND EII.BARCODE NOT IN("
                + " SELECT"
                + " DISTINCT EYBL2.BARCODE"
                + " FROM"
                + " EAM_YB_BORROW_LOG EYBL2"
                + " WHERE"
                + " EYBL2.STATUS = 'YB_BR_STATUS_ADD'"
                + " AND EYBL2.CREATED_BY <> ?)";
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getBarcode());
        if (!dto.getResponsibilityUserName().equals("")) {
            sqlArgs.add(dto.getResponsibilityUserName());
        }
        sqlArgs.add(LvecDicts.CATEGORY_YQYB);
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(userAccount.getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：构造选中条码的新建借用单信息SQL
     *
     * @return SQLModel
     */
    public SQLModel getBarcodeApplyModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EamYbBorrowLogDTO dto = (EamYbBorrowLogDTO) dtoParameter;
        String sqlStr = "SELECT"
                + " EII.BARCODE,"
                + " EII.VENDOR_BARCODE,"
                + " ESI.ITEM_NAME,"
                + " ESI.ITEM_SPEC,"
                + " ? CREATED_BY,"
                + " ? CREATED_USER,"
                + " ? BORROW_USER_ID,"
                + " ? BORROW_USER,"
                + " ? STATUS,"
                + " ? STATUS_VALUE,"
                + " ? ORG_ID,"
                + " GETDATE() BORROW_DATE,"
                + " GETDATE() CREATION_DATE"
                + " FROM"
                + " ETS_SYSTEM_ITEM ESI,"
                + " ETS_ITEM_INFO   EII"
                + " WHERE"
                + " ESI.ITEM_CODE = EII.ITEM_CODE"
                + " AND ESI.ITEM_CATEGORY = ?"
                + " AND NOT EXISTS("
                + " SELECT"
                + " NULL"
                + " FROM"
                + " EAM_YB_BORROW_LOG EYBL"
                + " WHERE"
                + " EII.BARCODE = EYBL.BARCODE"
                + " AND EYBL.STATUS IN ('YB_BR_STATUS_ADD', 'YB_BR_STATUS_WAPPROVE','YB_BR_STATUS_APPROVE', 'YB_BR_STATUS_BORROW'))"
                + " AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE = '' OR EII.DISABLE_DATE > GETDATE())"
                + " AND EII.BARCODE = ?";

        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(userAccount.getUsername());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(userAccount.getUsername());
        sqlArgs.add(LvecDicts.YB_BR_STATUS1_ADD);
        sqlArgs.add(LvecDicts.YB_BR_STATUS2_ADD);
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(LvecDicts.CATEGORY_YQYB);
        sqlArgs.add(dto.getBarcode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
