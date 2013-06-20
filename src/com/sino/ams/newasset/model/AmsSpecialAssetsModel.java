package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.newasset.dto.AmsSpecialAssetsDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: AmsSpecialAssetsModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsSpecialAssetsModel”</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AmsSpecialAssetsModel extends AMSSQLProducer {
    private SfUserDTO user = null;

    /**
     * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsTransHeaderDTO 本次操作的数据
     */
    public AmsSpecialAssetsModel(SfUserDTO userAccount, AmsSpecialAssetsDTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.user = (SfUserDTO) userAccount;
    }

    /**
     * 功能：框架自动生成资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsSpecialAssetsDTO dto = (AmsSpecialAssetsDTO) dtoParameter;
            String sqlStr = "INSERT INTO "
                            + " AMS_ASSETS_TRANS_HEADER("
                            + " TRANS_ID,"
                            + " TRANS_NO,"
                            + " TRANS_TYPE,"
                            + " TRANSFER_TYPE,"
                            + " TRANS_STATUS,"
                            + " FROM_ORGANIZATION_ID,"
                            + " TO_ORGANIZATION_ID,"
                            + " FROM_DEPT,"
                            + " FROM_GROUP,"
                            + " TO_DEPT,"
                            + " FROM_OBJECT_NO,"
                            + " TO_OBJECT_NO,"
                            + " FROM_PERSON,"
                            + " TO_PERSON,"
                            + " CREATED_REASON,"
                            + " CREATION_DATE,"
                            + " CREATED_BY,"
                            + " RECEIVED_USER,"
                            + " TRANS_DATE,"
                            + " FA_CONTENT_CODE,"
                            + " LOSSES_NAME,"
                            + " LOSSES_DATE,"
                            + " ASSETS_TYPE"
                            + ") VALUES ("
                            + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?, ?, ?, ?, ?,?)";
            sqlArgs.add(dto.getTransId());
            sqlArgs.add(dto.getTransNo());
            sqlArgs.add(dto.getTransType());
            sqlArgs.add(dto.getTransferType());
            sqlArgs.add(dto.getTransStatus());
            sqlArgs.add(dto.getFromOrganizationId());
            sqlArgs.add(dto.getToOrganizationId());
            sqlArgs.add(dto.getFromDept());
            sqlArgs.add(dto.getFromGroup());
            sqlArgs.add(dto.getToDept());
            sqlArgs.add(dto.getFromObjectNo());
            sqlArgs.add(dto.getToObjectNo());
            sqlArgs.add(dto.getFromPerson());
            sqlArgs.add(dto.getToPerson());
            sqlArgs.add(dto.getCreatedReason());
            sqlArgs.add(userAccount.getUserId());
            sqlArgs.add(dto.getReceivedUser());
            sqlArgs.add(dto.getTransDate());
            sqlArgs.add(dto.getFaContentCode());
            sqlArgs.add(dto.getLossesName());
            sqlArgs.add(dto.getLossesDate());
            sqlArgs.add(dto.getAssetsType());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsSpecialAssetsDTO dto = (AmsSpecialAssetsDTO) dtoParameter;
            String sqlStr = "UPDATE AMS_ASSETS_TRANS_HEADER"
                            + " SET"
                            + " TRANS_NO = ?,"
                            + " TRANS_STATUS = ?,"
                            + " TO_ORGANIZATION_ID = ?,"
                            + " FROM_DEPT = ?,"
                            + " TO_DEPT = ?,"
                            + " FROM_OBJECT_NO = ?,"
                            + " TO_OBJECT_NO = ?,"
                            + " FROM_PERSON = ?,"
                            + " TO_PERSON = ?,"
                            + " CREATED_REASON = ?,"
                            + " LAST_UPDATE_DATE = GETDATE(),"
                            + " LAST_UPDATE_BY = ?,"
                            + " RECEIVED_USER = ?,"
                            + " FA_CONTENT_CODE = ?,"
                            + " TRANS_DATE = ?,"
                            + " LOSSES_DATE = ?,"
                            + " LOSSES_NAME = ?"
                            + " WHERE"
                            + " TRANS_ID = ?";

            sqlArgs.add(dto.getTransNo());
            sqlArgs.add(dto.getTransStatus());
            sqlArgs.add(dto.getToOrganizationId());
            sqlArgs.add(dto.getFromDept());
            sqlArgs.add(dto.getToDept());
            sqlArgs.add(dto.getFromObjectNo());
            sqlArgs.add(dto.getToObjectNo());
            sqlArgs.add(dto.getFromPerson());
            sqlArgs.add(dto.getToPerson());
            sqlArgs.add(dto.getCreatedReason());
            sqlArgs.add(userAccount.getUserId());
            sqlArgs.add(dto.getReceivedUser());
            sqlArgs.add(dto.getFaContentCode());
            sqlArgs.add(dto.getTransDate());
            sqlArgs.add(dto.getLossesDate());
            sqlArgs.add(dto.getLossesName());
            sqlArgs.add(dto.getTransId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsSpecialAssetsDTO dto = (AmsSpecialAssetsDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                        + " AMS_ASSETS_TRANS_HEADER"
                        + " WHERE"
                        + " TRANS_ID = ?";
        sqlArgs.add(dto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsSpecialAssetsDTO dto = (AmsSpecialAssetsDTO) dtoParameter;
        String sqlStr = " SELECT"
                        + " AATH.TRANS_ID,"
                        + " AATH.TRANS_NO,"
                        + " AATH.FROM_DEPT,"
                        + " AATH.TO_DEPT,"
                        + " AATH.ASSETS_TYPE,"
                        + " AATH.TRANS_TYPE,"
                        + " AATH.TRANSFER_TYPE,"
                        + " AATH.TRANS_STATUS,"
                        + " AATH.TRANS_DATE,"
                        + " AATH.CREATION_DATE,"
                        + " AATH.CREATED_BY,"
                        + " AATH.LAST_UPDATE_DATE,"
                        + " AATH.LAST_UPDATE_BY,"
                        + " AATH.CANCELED_DATE,"
                        + " AATH.CANCELED_REASON,"
                        + " AATH.CREATED_REASON,"
                        + " AATH.APPROVED_DATE,"
                        + " AATH.FROM_ORGANIZATION_ID,"
                        + " AATH.FROM_GROUP,"
                        + " AATH.FA_CONTENT_CODE,"
                        + " AATH.LOSSES_NAME,"
                        + " AATH.LOSSES_DATE,"
                        + " AMD.DEPT_NAME FROM_DEPT_NAME,"
                        + " AMS_PUB_PKG.GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,"
                        + " AMS_PUB_PKG.GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE,"
                        + " AMS_PUB_PKG.GET_FLEX_VALUE(AATH.FA_CONTENT_CODE, 'FA_CONTENT_CODE') FA_CONTENT_NAME,"
                        + " SU.USERNAME CREATED,"
                        + " SU.EMAIL,"
                        + " SU.MOBILE_PHONE PHONE_NUMBER,"
                        + " SG.GROUP_NAME FROM_GROUP_NAME,"
                        + " 0 GROUP_PROP,"
                        + " EOCM.BOOK_TYPE_CODE,"
                        + " EOCM.BOOK_TYPE_NAME,"
                        + " EOCM.COMPANY FROM_COMPANY_NAME,"
                        + " AMD2.DEPT_NAME USER_DEPT_NAME,"
                        + " AATH.TO_ORGANIZATION_ID,"
                        + " EOCM2.COMPANY TO_COMPANY_NAME"
                        + " FROM"
                        + " AMS_ASSETS_TRANS_HEADER AATH,"
                        + " ETS_OU_CITY_MAP         EOCM,"
                        + " AMS_MIS_DEPT            AMD,"
                        + " SF_GROUP                SG,"
                        + " SF_USER                 SU,"
                        + " AMS_MIS_EMPLOYEE        AME,"
                        + " AMS_MIS_DEPT            AMD2,"
                        + " ETS_OU_CITY_MAP         EOCM2"
                        + " WHERE"
                        + " AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                        + " AND AATH.FROM_DEPT *= AMD.DEPT_CODE"
                        + " AND AATH.FROM_GROUP = SG.GROUP_ID"
                        + " AND AATH.CREATED_BY = SU.USER_ID"
                        + " AND SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER"
                        + " AND AME.DEPT_CODE *= AMD2.DEPT_CODE"
                        + " AND AATH.TO_ORGANIZATION_ID *= EOCM2.ORGANIZATION_ID"
                        + " AND TRANS_ID = ?";
        sqlArgs.add(dto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsSpecialAssetsDTO dto = (AmsSpecialAssetsDTO)dtoParameter;
            String sqlStr = "SELECT"
                            + " AATH.TRANS_ID,"
                            + " AATH.TRANS_NO,"
                            + " AATH.TRANS_TYPE,"
                            + " AATH.TRANSFER_TYPE,"
                            + " AATH.TRANS_STATUS,"
                            + " AATH.FROM_ORGANIZATION_ID,"
                            + " AATH.LOSSES_NAME,"
                            + " AATH.LOSSES_DATE,"
                            + " EOCM.COMPANY,"
                            + " dbo.NVL(AMD.DEPT_NAME, EOCM.COMPANY) FROM_DEPT_NAME,"
                            + " AATH.RECEIVED_USER,"
                            + " AATH.CREATION_DATE,"
                            + " AMS_PUB_PKG.GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,"
                            + " AMS_PUB_PKG.GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE,"
                            + " AMS_PUB_PKG.GET_FLEX_VALUE(AATH.ASSETS_TYPE, 'FINANCE_PROP')ASSETS_TYPE_NAME,"
                            + " SU.USERNAME CREATED"
                            + " FROM"
                            + " AMS_ASSETS_TRANS_HEADER AATH,"
                            + " AMS_MIS_DEPT       AMD,"
                            + " ETS_OU_CITY_MAP    EOCM,"
                            + " SF_USER            SU"
                            + " WHERE"
                            + " AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                            + " AND AATH.FROM_DEPT *= AMD.DEPT_CODE"
                            + " AND AATH.CREATED_BY = SU.USER_ID"
                            + " AND AATH.TRANS_TYPE = dbo.NVL(?, AATH.TRANS_TYPE)"
                            + " AND AATH.TRANS_STATUS = ?"
                            + " AND AATH.CREATED_BY = ?"
                            + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AATH.TRANS_NO LIKE ?)"
                            + " AND AATH.CREATION_DATE >= ISNULL(?, AATH.CREATION_DATE)"
                            + " AND AATH.CREATION_DATE <= ISNULL(?, AATH.CREATION_DATE)"
                            + " AND " + SyBaseSQLUtil.isNotNull("EII.FA_BARCODE") + ""
                            + " ORDER BY"
                            + " AATH.TRANS_TYPE,"
                            + " AATH.TRANS_NO";
            sqlArgs.add(dto.getTransType());
            sqlArgs.add(dto.getTransStatus());
            sqlArgs.add(userAccount.getUserId());
            sqlArgs.add(dto.getTransNo());
            sqlArgs.add(dto.getTransNo());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getSQLEndDate());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：撤销单据
     * @param transId String
     * @return SQLModel
     */
    public SQLModel getOrderCancelModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE"
                        + " AMS_ASSETS_TRANS_HEADER AATH"
                        + " SET"
                        + " AATH.TRANS_STATUS = ?,"
                        + " AATH.CANCELED_DATE = GETDATE(),"
                        + " AATH.CANCELED_REASON = ?,"
                        + " AATH.LAST_UPDATE_DATE = GETDATE(),"
                        + " AATH.LAST_UPDATE_BY = ?"
                        + " WHERE"
                        + " AATH.TRANS_ID = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(AssetsDictConstant.CANCELED);
        sqlArgs.add(AssetsDictConstant.CANCAL_REASON);
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：
     * @return SQLModel
     */
    public SQLModel getOrderModel() { //导出模板
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT \n"
                        + " AATH.TRANS_ID,\n"
                        + " ''   MB1,''   MB2,''   MB3,''   MB4,\n"
                        + " ''   MB5,''   MB6,''   MB7,''   MB8,\n"
                        + " ''   MB9,''   MB10,''   MB11,''   MB12,\n"
                        + " ''   MB13,''   MB14,''   MB15,''   MB16,\n"
                        + " ''   MB17,''   MB18,''   MB19,''   MB20,\n"
                        + " ''   MB21,''   MB22,''   MB23,''   MB24,\n"
                        + " ''   MB25,''   MB26,''   MB27 \n"
                        + " FROM \n"
                        + " AMS_ASSETS_TRANS_HEADER AATH \n";
        List sqlArgs = new ArrayList();
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：查找用户所对应的PID，流程用
     * @return SQLModel
     */
    public SQLModel getGroupPidModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = " SELECT DISTINCT (SG.P_FLOW_ID)\n" +
                        " FROM SF_USER SU," +
                             " SF_GROUP SG," +
                             " SF_USER_RIGHT SUR\n" +
                        "WHERE SU.USER_ID = SUR.USER_ID " +
                             " AND SUR.GROUP_ID = SG.GROUP_ID " +
                             " AND SG.P_FLOW_ID IN(2185, 2223)" +
                             " AND SU.USER_ID = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getQueryBarcodeExcelModel(String excel, AmsSpecialAssetsDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String transferType = dto.getTransferType();
        boolean isDeptMgr = user.isDptAssetsManager();
        boolean isCompMgr = user.isComAssetsManager();
        String mtlMgrProps = user.getMtlMgrProps();

        String sqlStr = "SELECT /*+rule */"
                 + " AAAV.BARCODE,"
                 + " AAAV.ASSET_NUMBER,"
                 + " AAAV.ASSET_ID,"
                 + " AAAV.ASSETS_DESCRIPTION,"
                 + " AAAV.MODEL_NUMBER,"
                 + " AAAV.ITEM_NAME,"
                 + " AAAV.ITEM_SPEC,"
                 + " AAAV.VENDOR_NAME,"
                 + " AAAV.UNIT_OF_MEASURE,"
                 + " ISNULL(AAAV.CURRENT_UNITS, 1) CURRENT_UNITS,"
                 + " AAAV.MANUFACTURER_NAME,"
                 + " AAAV.FA_CATEGORY_CODE OLD_FA_CATEGORY_CODE,"
                 + " AAAV.DEPT_CODE OLD_RESPONSIBILITY_DEPT,"
                 + " AAAV.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,"
                 + " AAAV.WORKORDER_OBJECT_NO OLD_LOCATION,"
                 + " AAAV.WORKORDER_OBJECT_CODE OLD_LOCATION_CODE,"
                 + " AAAV.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,"
                 + " AAAV.RESPONSIBILITY_USER OLD_RESPONSIBILITY_USER,"
                 + " AAAV.RESPONSIBILITY_USER_NAME OLD_RESPONSIBILITY_USER_NAME,";
        if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) { //部门内调拨
        	 sqlStr = sqlStr
             + " CASE WHEN AAAV.RESPONSIBILITY_USER=? THEN RESPONSIBILITY_USER ELSE '' END RESPONSIBILITY_USER,"
             +
             " CASE WHEN AAAV.RESPONSIBILITY_USER=? THEN RESPONSIBILITY_USER_NAME ELSE '' END RESPONSIBILITY_USER_NAME,"
             + " AAAV.ORGANIZATION_ID TO_ORGANIZATION_ID,";
            sqlArgs.add(user.getEmployeeId());
            sqlArgs.add(user.getEmployeeId());
        }
        sqlStr = sqlStr
                 + " AAAV.DATE_PLACED_IN_SERVICE,"
                 + " AAAV.COST,"
                 + " AAAV.DEPRN_COST,"
                 + " AAAV.COST RETIREMENT_COST,"
                 + " AAAV.DEPRECIATION_ACCOUNT OLD_DEPRECIATION_ACCOUNT,"
                 + " AAAV.DEPRECIATION,"
                 + " AMS_PUB_PKG.GET_FLEX_VALUE(AAAV.ASSETS_STATUS,'ASSETS_STATUS') ASSETS_STATUS_DES ,"
                 + " AAAV.IMPAIR_RESERVE"
                 + " FROM"
                 + " AMS_ASSETS_ADDRESS_V AAAV"
                 + " WHERE"
                 + " AAAV.ORGANIZATION_ID = ?";
        sqlArgs.add(user.getOrganizationId());
        if (!StrUtil.isEmpty(dto.getFromDept())) { //部门限制
            sqlStr += " AND AAAV.DEPT_CODE = ?";
            sqlArgs.add(dto.getFromDept());
        } else { //根据操作人员权限限制
            if (!isCompMgr && mtlMgrProps.equals("") && isDeptMgr) {
                DTOSet depts = user.getPriviDeptCodes();
                AmsMisDeptDTO dept = null;
                String deptCodes = "(";
                for (int i = 0; i < depts.getSize(); i++) {
                    dept = (AmsMisDeptDTO) depts.getDTO(i);
                    deptCodes += "'" + dept.getDeptCode() + "', ";
                }
                deptCodes += "'')";
                sqlStr += " AND AAAV.DEPT_CODE IN " + deptCodes;
            }
        }
        if (!user.isDptAssetsManager() && !user.isComAssetsManager() && mtlMgrProps.equals("")) { //责任人限制
            if (!dto.getTransferType().equals("BTW_COMP")) {
                sqlStr += " AND AAAV.RESPONSIBILITY_USER = ?";
                sqlArgs.add(user.getEmployeeId());
            }
        }
        if (dto.getTransType().equals(AssetsDictConstant.ASS_RED)) { //调拨
            sqlStr += " AND (AAAV.ITEM_STATUS " + SyBaseSQLUtil.isNullNoParam() + "  OR AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
            sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
            sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
        } else if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS)) { //报废
            sqlStr += " AND (AAAV.ITEM_STATUS " + SyBaseSQLUtil.isNullNoParam() + "  OR AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
            sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
            sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
        } else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { //报废前提：正常或者闲置
            sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
            sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
            sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
        } else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { //处置前提：已报废或者闲置
            sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
            sqlArgs.add(AssetsDictConstant.ITEM_STATUS_DISCARDED);
            sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
        } else if (dto.getTransType().equals(AssetsDictConstant.ASS_SUB)) { //减值前提：正常或者闲置
            sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
            sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
            sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
        }


        if (!excel.equals("")) {
            sqlStr += " AND AAAV.BARCODE IN (" + excel + ")";
        }

        sqlStr = sqlStr
                 + " AND AAAV.PARENT_BARCODE " + SyBaseSQLUtil.isNullNoParam() + " "
                 + " AND " + SyBaseSQLUtil.isNotNull("EII.FA_BARCODE") + ""
                 + " AND NOT EXISTS ("
                 + " SELECT"
                 + " NULL"
                 + " FROM"
                 + " AMS_ASSETS_RESERVED AAR"
                 + " WHERE"
                 + " AAR.BARCODE = AAAV.BARCODE)";

    sqlModel.setSqlStr(sqlStr);
    sqlModel.setArgs(sqlArgs);

    return sqlModel;
    }


    //EXCEL导入时，查出没有导入成功的BARCODE
    public SQLModel getQueryBarcodeAllExcelModel(String excel, AmsSpecialAssetsDTO dto, String returnModel) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String mtlMgrProps = user.getMtlMgrProps();

        String sqlStr = "SELECT AAAV.BARCODE FROM AMS_ASSETS_ADDRESS_V AAAV WHERE AAAV.BARCODE IN (" + excel + ") AND AAAV.BARCODE NOT IN (" + returnModel + ")";
        sqlArgs.add(user.getOrganizationId());
        if (!StrUtil.isEmpty(dto.getFromDept())) { //部门限制
            sqlArgs.add(dto.getFromDept());
	    }
        if (!user.isDptAssetsManager() && !user.isComAssetsManager() && mtlMgrProps.equals("")) { //责任人限制
            if(!dto.getTransferType().equals("BTW_COMP"))
            {
               sqlArgs.add(user.getEmployeeId());
            }
	    }
        if (dto.getTransType().equals(AssetsDictConstant.ASS_RED)) { //调拨
            sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
            sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
        } else if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS)) { //报废
            sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
            sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
        } else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { //报废前提：正常或者闲置
            sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
            sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
        } else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { //处置前提：已报废或者闲置
            sqlArgs.add(AssetsDictConstant.ITEM_STATUS_DISCARDED);
            sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
        } else if (dto.getTransType().equals(AssetsDictConstant.ASS_SUB)) { //减值前提：正常或者闲置
            sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
            sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
        }
        sqlArgs.add(AssetsDictConstant.FA_CONTENT_CODE);
		sqlArgs.add(dto.getFaContentCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
