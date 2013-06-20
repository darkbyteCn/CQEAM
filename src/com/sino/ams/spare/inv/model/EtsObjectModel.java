package com.sino.ams.spare.inv.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.util.ArrUtil;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class EtsObjectModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    public EtsObjectModel(SfUserDTO userAccount, EtsObjectDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO etsObject = (EtsObjectDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " ETS_OBJECT("
                + " WORKORDER_OBJECT_NO,"
                + " WORKORDER_OBJECT_CODE,"
                + " WORKORDER_OBJECT_NAME,"
                + " WORKORDER_OBJECT_LOCATION,"
                + " ORGANIZATION_ID,"
                + " COUNTY_CODE,"
                + " REMARK,"
                + " OBJECT_CATEGORY,"
                + " CREATION_DATE,"
                + " CREATED_BY"
                + ") VALUES ("
                + " NEWID(), ?, ?, ?, ?, ?, ?, ?, GETDATE(),?)";

        sqlArgs.add(etsObject.getWorkorderObjectCode());
        sqlArgs.add(etsObject.getWorkorderObjectName());
        sqlArgs.add(etsObject.getWorkorderObjectLocation());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(etsObject.getCountyCode());
        sqlArgs.add(etsObject.getRemark());
        sqlArgs.add(etsObject.getObjectCategory());
        sqlArgs.add(sfUser.getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO etsObject = (EtsObjectDTO) dtoParameter;
        String sqlStr = "UPDATE ETS_OBJECT"
                + " SET"
                + " WORKORDER_OBJECT_CODE = ?,"
                + " WORKORDER_OBJECT_NAME = ?,"
                + " WORKORDER_OBJECT_LOCATION = ?,"
                + " ORGANIZATION_ID = ?,"
                + " COUNTY_CODE = ?,"
                + " REMARK = ?,"
                + " OBJECT_CATEGORY = ?,"
                + " ISALL = ?,"
                + " IS_TEMP_ADDR = ?,"
                + " LAST_UPDATE_BY = ?,"
                + " PROJECT_ID = ?"
                + " WHERE"
                + " WORKORDER_OBJECT_NO = ?";

        sqlArgs.add(etsObject.getWorkorderObjectCode());
        sqlArgs.add(etsObject.getWorkorderObjectName());
        sqlArgs.add(etsObject.getWorkorderObjectLocation());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(etsObject.getCountyCode());
        sqlArgs.add(etsObject.getRemark());
        sqlArgs.add(etsObject.getObjectCategory());
        sqlArgs.add(etsObject.getIsall());
        sqlArgs.add(etsObject.getIsTempAddr());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(etsObject.getProjectId());
        sqlArgs.add(etsObject.getWorkorderObjectNo());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO etsObject = (EtsObjectDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " WORKORDER_OBJECT_NO,"
                + " WORKORDER_OBJECT_CODE,"
                + " WORKORDER_OBJECT_NAME,"
                + " WORKORDER_OBJECT_LOCATION,"
                + " ORGANIZATION_ID,"
                + " COUNTY_CODE,"
                + " DISABLE_DATE,"
                + " REMARK,"
                + " OBJECT_CATEGORY,"
                + " ISALL,"
                + " IS_TEMP_ADDR,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY,"
                + " PROJECT_ID"
                + " FROM"
                + " ETS_OBJECT"
                + " WHERE"
                + " WORKORDER_OBJECT_NO = ?";
        sqlArgs.add(etsObject.getWorkorderObjectNo());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO etsObject = (EtsObjectDTO) dtoParameter;
        String sqlStr = "SELECT EO.WORKORDER_OBJECT_NO,\n" +
                "       EO.WORKORDER_OBJECT_CODE,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       EO.WORKORDER_OBJECT_LOCATION,\n" +
                "       EO.ORGANIZATION_ID,\n" +
                "       EO.COUNTY_CODE,\n" +
                "       EO.DISABLE_DATE,\n" +
                "       EO.OBJECT_CATEGORY,\n" +
                "       EC.COUNTY_NAME\n" +
                "  FROM ETS_OBJECT EO,\n" +
                "       ETS_COUNTY EC\n" +
                " WHERE EO.COUNTY_CODE = EC.COUNTY_CODE\n" +
                "   AND EC.COMPANY_CODE LIKE '"+etsObject.getProvinceCode()+"%'\n" +
                "   AND ("+ SyBaseSQLUtil.isNull()+" OR EO.WORKORDER_OBJECT_CODE LIKE ?)\n" +
                "   AND ("+SyBaseSQLUtil.isNull()+" OR EO.WORKORDER_OBJECT_NAME LIKE ?)\n" +
                "   AND ("+SyBaseSQLUtil.isNull()+" OR EO.WORKORDER_OBJECT_LOCATION LIKE ?)\n" +
                "   AND (? = -1 OR EO.ORGANIZATION_ID = ?)\n" +
                "   AND ("+SyBaseSQLUtil.isNull()+" OR EO.COUNTY_CODE LIKE ?)\n" +
                "   AND ("+SyBaseSQLUtil.isNull()+" OR EO.OBJECT_CATEGORY LIKE ?)\n" +
                "   AND EO.OBJECT_CATEGORY > ?\n" +
                "   AND EO.OBJECT_CATEGORY < ?";
        sqlArgs.add(etsObject.getWorkorderObjectCode());
        sqlArgs.add(etsObject.getWorkorderObjectCode());
        sqlArgs.add(etsObject.getWorkorderObjectName());
        sqlArgs.add(etsObject.getWorkorderObjectName());
        sqlArgs.add(etsObject.getWorkorderObjectLocation());
        sqlArgs.add(etsObject.getWorkorderObjectLocation());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(etsObject.getCountyCode());
        sqlArgs.add(etsObject.getCountyCode());
        sqlArgs.add(etsObject.getObjectCategory());
        sqlArgs.add(etsObject.getObjectCategory());
        sqlArgs.add(WebAttrConstant.INV_CATEGORY);
        sqlArgs.add(DictConstant.NETADDR_OTHERS);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDisabledModel(String[] workorderObjectNos) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String orderno = arrToStr(workorderObjectNos, ", ");
        String sqlStr = "UPDATE "
                + " ETS_OBJECT"
                + " SET"
                + " DISABLE_DATE = GETDATE()"
                + " WHERE"
                + " WORKORDER_OBJECT_NO IN ("+orderno+")";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getEfficientModel(String[] workorderObjectNos) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String orderno = arrToStr(workorderObjectNos, ", ");
        String sqlStr = "UPDATE "
                + " ETS_OBJECT"
                + " SET"
                + " DISABLE_DATE = NULL"
                + " WHERE"
                + " WORKORDER_OBJECT_NO IN ("+orderno+")";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getInureModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO etsObject = (EtsObjectDTO) dtoParameter;
        String sqlStr = "UPDATE "
                + " ETS_OBJECT"
                + " SET"
                + " DISABLE_DATE = NULL"
                + " WHERE"
                + " WORKORDER_OBJECT_NO = ?";
        sqlArgs.add(etsObject.getWorkorderObjectNo());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO etsObject = (EtsObjectDTO) dtoParameter;
        String sqlStr = "UPDATE "
                + " ETS_OBJECT"
                + " SET"
                + " DISABLE_DATE = GETDATE()"
                + " WHERE"
                + " WORKORDER_OBJECT_NO = ?";
        sqlArgs.add(etsObject.getWorkorderObjectNo());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getTypeObjHasBeenModel(String objCategory) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT TOP 1 EO.WORKORDER_OBJECT_CODE" +
                " FROM ETS_OBJECT EO " +
                " WHERE EO.OBJECT_CATEGORY= ?" +
                " AND EO.ORGANIZATION_ID = ?";
        sqlArgs.add(objCategory);
        sqlArgs.add(sfUser.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getCodeHasBeenModel(String objCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT 1 FROM ETS_OBJECT EO " +
                " WHERE EO.WORKORDER_OBJECT_CODE = ?";
        sqlArgs.add(objCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }  

    public SQLModel getAddressCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsObjectDTO etsObject = (EtsObjectDTO) dtoParameter;
        String sqlStr = "INSERT INTO AMS_OBJECT_ADDRESS\n" +
                "  (ADDRESS_ID,\n" +
                "   OBJECT_NO,\n" +
                "   BOX_NO,\n" +
                "   NET_UNIT,\n" +
                "   ORGANIZATION_ID,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY,\n" +
                "   REMARK,\n" +
                "   ADDRESS_NO)\n" +
                "  ( SELECT \n" +
                "   TOP 1\n" +
                "   NEWID(),\n" +
                "   EO.WORKORDER_OBJECT_NO OBJECT_NO,\n" +
                "   '0000' BOX_NO,\n" +
                "   '0000' NET_UNIT,\n" +
                "   EO.ORGANIZATION_ID ORGANIZATION_ID,\n" +
                "   EO.CREATION_DATE CREATION_DATE,\n" +
                "   EO.CREATED_BY CREATED_BY,\n" +
                "   EO.REMARK REMARK,\n" +
                "   EO.WORKORDER_OBJECT_NO||'.0000.0000' ADDRESS_NO\n" +
                "   FROM ETS_OBJECT EO WHERE EO.WORKORDER_OBJECT_CODE = ?\n" +
                "   )";
        sqlArgs.add(etsObject.getWorkorderObjectCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

	public SQLModel getVerifyBarcode(String[] workorderObjectNos) {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String orderno = arrToStr(workorderObjectNos, ", ");
        String strSql = "  SELECT " +
                        " ASI.BARCODE\n" +
                        " FROM " +
                        " ETS_OBJECT EO, AMS_SPARE_INFO ASI\n" +
                        " WHERE " +
                        " EO.WORKORDER_OBJECT_NO = ASI.OBJECT_NO\n" +
                        " AND EO.WORKORDER_OBJECT_NO IN ("+orderno+")";
        sqlModel.setSqlStr(strSql);
        sqlModel.setArgs(strArg);
        return sqlModel;
    }

    private String arrToStr(String[] srcArr, String linkStr) {
        String retStr = "";
        if (srcArr != null && srcArr.length > 0) {
            boolean hasProcessed = false;
            for (int i = 0; i <= srcArr.length - 1; i++) {
                retStr += "'"+srcArr[i]+"'" + linkStr;
                hasProcessed = true;
            }
            if (hasProcessed) {
                retStr = retStr.substring(0, retStr.length() - linkStr.length());
            }
        }
        return retStr;
    }
}
