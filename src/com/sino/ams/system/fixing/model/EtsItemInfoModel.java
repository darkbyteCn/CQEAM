package com.sino.ams.system.fixing.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsItemInfoModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsItemInfoModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class EtsItemInfoModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：标签号信息(EAM) ETS_ITEM_INFO 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemInfoDTO 本次操作的数据
     */
    public EtsItemInfoModel(SfUserDTO userAccount, EtsItemInfoDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    public SQLModel selectItemInfo() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemInfoDTO amsInstrumentInfo = (EtsItemInfoDTO) dtoParameter;
        String sqlStr = "SELECT 1\n" +
                "  FROM ETS_SYSTEM_ITEM ESI\n" +
                " WHERE ESI.ITEM_NAME =?\n" +
                "   AND ESI.ITEM_SPEC =?\n" +
                "   AND ESI.ITEM_CATEGORY =?";
        sqlArgs.add(amsInstrumentInfo.getItemName());
        sqlArgs.add(amsInstrumentInfo.getItemSpec());
        sqlArgs.add(amsInstrumentInfo.getItemCategory());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;

    }

    public SQLModel insertIntoItem() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemInfoDTO amsInstrumentInfo = (EtsItemInfoDTO) dtoParameter;
        String sqlStr = "INSERT INTO ETS_SYSTEM_ITEM\n" +
                "  (ITEM_CODE,\n" +
                "   ITEM_NAME,\n" +
                "   ITEM_SPEC,\n" +
                "   ITEM_CATEGORY,\n" +
                "   ITEM_UNIT,\n" +
                "   VENDOR_ID,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY,\n" +
                "   IS_TMP_CODE)\n" +
                "VALUES\n" +
                "  (?, ?, ?, ?, '块', ?, GETDATE(), ?, 'Y')";
        sqlArgs.add(amsInstrumentInfo.getItemCode());
        sqlArgs.add(amsInstrumentInfo.getItemName());
        sqlArgs.add(amsInstrumentInfo.getItemSpec());
        sqlArgs.add(amsInstrumentInfo.getItemCategory());
        sqlArgs.add(amsInstrumentInfo.getVendorId());
        sqlArgs.add(sfUser.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel selectDis(String itemCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                "  FROM ETS_SYSITEM_DISTRIBUTE ESD\n" +
                " WHERE ESD.ITEM_CODE = ?\n" +
                "   AND ESD.ORGANIZATION_ID = ?";
        sqlArgs.add(itemCode);
        sqlArgs.add(sfUser.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertIntoDis(String itemcode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemInfoDTO amsInstrumentInfo = (EtsItemInfoDTO) dtoParameter;
        String sqlStr = "INSERT INTO ETS_SYSITEM_DISTRIBUTE\n" +
                "  (SYSTEM_ID,\n" +
                "   ITEM_CODE,\n" +
                "   ORGANIZATION_ID,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY,\n" +
                "   IS_TMP)\n" +
                "VALUES\n" +
                "  ( NEWID() , ?, ?, GETDATE(), ?, 'Y')";
        sqlArgs.add(itemcode);
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(sfUser.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertIntoD() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemInfoDTO amsInstrumentInfo = (EtsItemInfoDTO) dtoParameter;
        String sqlStr = "INSERT INTO ETS_SYSITEM_DISTRIBUTE\n" +
                "  (SYSTEM_ID,\n" +
                "   ITEM_CODE,\n" +
                "   ORGANIZATION_ID,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY,\n" +
                "   IS_TMP)\n" +
                "VALUES\n" +
                "  ( NEWID() , ?, ?, GETDATE(), ?, 'Y')";
        sqlArgs.add(amsInstrumentInfo.getItemCode());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(sfUser.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO) dtoParameter;
        String sqlStr = "UPDATE ETS_SYSTEM_ITEM  SET  VENDOR_ID=?  \n" +
                " WHERE ITEM_NAME =?\n" +
                "   AND ITEM_SPEC =?\n" +
                "   AND ITEM_CATEGORY = ?";

        sqlArgs.add(etsItemInfo.getVendorId());
        sqlArgs.add(etsItemInfo.getItemName());
        sqlArgs.add(etsItemInfo.getItemSpec());
        sqlArgs.add(etsItemInfo.getItemCategory());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成标签号信息(EAM) ETS_ITEM_INFO数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO) dtoParameter;
            String sqlStr = "INSERT INTO ETS_ITEM_INFO\n" +
                    "  (SYSTEMID,\n" +
                    "   BARCODE,  \n" +
                    "   ITEM_QTY,\n" +
                    "   REMARK,\n" +
                    "   START_DATE,\n" +
                    "   PROJECTID,\n" +
                    "   CREATION_DATE,\n" +
                    "   CREATED_BY,\n" +
                    "   ADDRESS_ID,\n" +
                    "   ORGANIZATION_ID," +
                    "   ITEM_CODE)\n" +
                    "VALUES\n" +
                    "  ( NEWID() ,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   GETDATE(),\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?)";
            sqlArgs.add(etsItemInfo.getBarcode1());
            sqlArgs.add(etsItemInfo.getItemQty());
            sqlArgs.add(etsItemInfo.getRemark());
            sqlArgs.add(etsItemInfo.getStartDate());
            sqlArgs.add(etsItemInfo.getPrjId());
            sqlArgs.add(sfUser.getUserId());
            sqlArgs.add(etsItemInfo.getAddressId());
            sqlArgs.add(sfUser.getOrganizationId());
            sqlArgs.add(etsItemInfo.getItemCode());


            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成标签号信息(EAM) ETS_ITEM_INFO数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO) dtoParameter;
        String sqlStr = "UPDATE ETS_ITEM_INFO\n" +
                "   SET BARCODE       = ?,\n" +
                "       ITEM_QTY         = ?,\n" +
                "       REMARK           = ?,\n" +
                "       PROJECTID        = ?,\n" +
                "       LAST_UPDATE_DATE = GETDATE(),\n" +
                "       LAST_UPDATE_BY   = ?,\n" +
                "       ADDRESS_ID       = (SELECT AOA.ADDRESS_ID FROM AMS_OBJECT_ADDRESS AOA  WHERE AOA.OBJECT_NO = ?),\n" +
                "       ITEM_CODE        = ?" +
//                "       ORGANIZATION_ID   =?\n" +
                " WHERE SYSTEMID = ?";

        sqlArgs.add(etsItemInfo.getBarcode1());
        sqlArgs.add(etsItemInfo.getItemQty());
        sqlArgs.add(etsItemInfo.getRemark());
        sqlArgs.add(etsItemInfo.getPrjId());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(etsItemInfo.getWorkorderObjectNo());
//        sqlArgs.add(etsItemInfo.getAddressId());
        sqlArgs.add(etsItemInfo.getItemCode());
//        sqlArgs.add(etsItemInfo.getOrganizationId());
        sqlArgs.add(etsItemInfo.getSystemid());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成标签号信息(EAM) ETS_ITEM_INFO数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " ETS_ITEM_INFO"
                + " WHERE"
                + " SYSTEMID = ?";
        sqlArgs.add(etsItemInfo.getSystemid());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成标签号信息(EAM) ETS_ITEM_INFO数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO) dtoParameter;
        String sqlStr = "SELECT EII.SYSTEMID,\n" +
                "       EII.BARCODE BARCODE1,\n" +
                "       EII.DISABLE_DATE,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       EII.ITEM_QTY,\n" +
                "       ESI.YEARS,\n" +
                "       EII.REMARK," +
                "       EII.START_DATE," +
                "       EPPA.NAME PRJ_NAME,\n" +
                "       EII.PROJECTID PRJ_ID,\n" +
                "       ESI.MASTER_ORGANIZATION_ID,\n" +
                "       ESI.ITEM_CATEGORY,\n" +
                "       EII.VENDOR_BARCODE,\n" +
                "       EMPV.VENDOR_NAME,\n" +
                "       EII.ITEM_CODE,\n" +
                "       EII.ADDRESS_ID,\n" +
                "       EO.WORKORDER_OBJECT_NO,\n" +
                "       EO.WORKORDER_OBJECT_NAME\n" +
                "  FROM ETS_ITEM_INFO       EII,\n" +
                "       ETS_SYSTEM_ITEM     ESI,\n" +
                "       ETS_PA_PROJECTS_ALL EPPA,\n" +
                "       ETS_MIS_PO_VENDORS  EMPV,\n" +
                "       AMS_OBJECT_ADDRESS  AOA,\n" +
                "       ETS_OBJECT          EO\n" +
                " WHERE ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                "   AND EII.PROJECTID *= EPPA.PROJECT_ID\n" +
                /* "   AND ESI.ITEM_CATEGORY = 'BTS'\n" +*/
                "   AND ESI.VENDOR_ID *= EMPV.VENDOR_ID\n" +
                "   AND AOA.OBJECT_NO *= EO.WORKORDER_OBJECT_NO\n" +
                "   AND EII.ADDRESS_ID *= AOA.ADDRESS_ID " +
                "   AND EII.SYSTEMID = ?";
        sqlArgs.add(etsItemInfo.getSystemid());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：框架自动生成标签号信息(EAM) ETS_ITEM_INFO页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO) dtoParameter;
        String sqlStr = "SELECT EII.SYSTEMID,\n" +
                "       EII.BARCODE BARCODE1,\n" +
                "       EFV.VALUE ITEM_CATE_GORY_DESC,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       EII.ITEM_QTY,\n" +
                "       EMPV.VENDOR_NAME,\n" +
                "       EII.DISABLE_DATE,\n" +
                "       EPPA.NAME,\n" +
                "       ESI.MASTER_ORGANIZATION_ID,\n" +
                "       ESI.ITEM_CATEGORY,\n" +
                "       EII.VENDOR_BARCODE,\n" +
                "       EO.WORKORDER_OBJECT_CODE,\n" +
                "       EO.WORKORDER_OBJECT_NAME"+
                "  FROM ETS_ITEM_INFO       EII,\n" +
                "       ETS_SYSTEM_ITEM     ESI,\n" +
                "       ETS_PA_PROJECTS_ALL EPPA,\n" +
                "       ETS_MIS_PO_VENDORS  EMPV,\n" +
                "       ETS_FLEX_VALUES     EFV,\n" +
                "       ETS_FLEX_VALUE_SET  EFVS,\n" +
                "       AMS_OBJECT_ADDRESS  AOA,\n"+
                "       ETS_OBJECT          EO"+
                " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EII.PROJECTID *= EPPA.PROJECT_ID\n" +
                "   AND ESI.VENDOR_ID *= EMPV.VENDOR_ID\n" +
                "   AND EFVS.CODE = 'ITEM_TYPE'\n" +
                "   AND EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n" +
                "   AND EFV.CODE = ESI.ITEM_CATEGORY\n" +
                "   AND ESI.ITEM_CATEGORY = ?\n" +
                "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND AOA.ADDRESS_ID = EII.ADDRESS_ID \n"  +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR  ESI.ITEM_NAME LIKE ?)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR  EII.BARCODE LIKE ?)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR  ESI.VENDOR_ID LIKE ?)\n" +
                 "  AND ( " + SyBaseSQLUtil.isNull() + "  OR AMS_PUB_PKG.GET_WORKORDER_OBJECT_NAME(EII.ADDRESS_ID) LIKE ?)\n" ;
//                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR  EII.ORGANIZATION_ID = ?)\n" ;
        sqlArgs.add(etsItemInfo.getItemCategory());
        sqlArgs.add(etsItemInfo.getItemName());
        sqlArgs.add(etsItemInfo.getItemName());
        sqlArgs.add(etsItemInfo.getBarcode1());
        sqlArgs.add(etsItemInfo.getBarcode1());
        sqlArgs.add(etsItemInfo.getVendorId());
        sqlArgs.add(etsItemInfo.getVendorId());
        sqlArgs.add(etsItemInfo.getWorkorderObjectName());
        sqlArgs.add(etsItemInfo.getWorkorderObjectName());

        if (etsItemInfo.getDisable().equals("是")) {
            sqlStr += "AND  " + SyBaseSQLUtil.isNotNull("EII.DISABLE_DATE") + " \n";
        } else if (etsItemInfo.getDisable().equals("否")) {
            sqlStr += "AND EII.DISABLE_DATE  " + SyBaseSQLUtil.isNullNoParam() + " \n";
        }
         if ((!sfUser.isProvinceUser()) && (!sfUser.isSysAdmin())) {
                sqlStr += "AND EII.ORGANIZATION_ID = ?\n";
                sqlArgs.add(sfUser.getOrganizationId());
            }
        sqlStr +="ORDER  BY EII.ADDRESS_ID,EII.ITEM_CODE"  ;
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getItemCategoryModel(String itemCategory) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EFV.VALUE ITEM_CATEGORY_DESC\n" +
                "  FROM ETS_FLEX_VALUES EFV, ETS_FLEX_VALUE_SET EFVS\n" +
                " WHERE EFV.FLEX_VALUE_SET_ID = 1\n" +
                "   AND EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ITEM_TYPE'\n" +
                "   AND EFV.CODE = ?";

        sqlArgs.add(itemCategory);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getCode() {
        SQLModel sqlModel = new SQLModel();
        List list = new ArrayList();
        String str = "SELECT EOC.COMPANY_CODE\n" +
                "  FROM ETS_OU_CITY_MAP EOC\n" +
                " WHERE EOC.ORGANIZATION_ID = ?";
        list.add(sfUser.getOrganizationId());
        sqlModel.setSqlStr(str);
        sqlModel.setArgs(list);
        return sqlModel;
    }

    public SQLModel getEnableModel(String systemId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
//        String systemIds = ArrUtil.arrToStr(systemId);
        String sqlStr = "UPDATE"
                + " ETS_ITEM_INFO"
                + " SET"
                + " DISABLE_DATE = NULL"
                + " WHERE"
                + " SYSTEMID =?";
        sqlArgs.add(systemId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDisableModel(String systemId) {
        SQLModel sqlModel = new SQLModel();
//        String systemIds = ArrUtil.arrToStr(systemId);
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                + " ETS_ITEM_INFO"
                + " SET"
                + " DISABLE_DATE = GETDATE()"
                + " WHERE"
                + " SYSTEMID =  ? ";
        sqlArgs.add(systemId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel selectItem() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO) dtoParameter;
        String sqlStr = "SELECT 1\n" +
                "  FROM ETS_SYSTEM_ITEM ESI\n" +
                " WHERE ESI.ITEM_NAME =?\n" +
                "   AND ESI.ITEM_SPEC =?\n" +
                "   AND ESI.ITEM_CATEGORY =?";
        sqlArgs.add(etsItemInfo.getItemName());
        sqlArgs.add(etsItemInfo.getItemSpec());
        sqlArgs.add(etsItemInfo.getItemCategory());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;

    }

    public SQLModel selectNO() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO) dtoParameter;
        String sqlStr = "SELECT 1  FROM ETS_ITEM_INFO  EII WHERE EII.BARCODE=?";
        sqlArgs.add(etsItemInfo.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;

    }

    public SQLModel insertDataBase(String itemCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO) dtoParameter;
        String sqlStr = "INSERT INTO ETS_SYSTEM_ITEM\n" +
                "  (ITEM_CODE, ITEM_NAME, ITEM_SPEC, ITEM_CATEGORY, VENDOR_ID, CREATED_BY,IS_TMP_CODE)\n" +
                "VALUES\n" +
                "  (?,?,?,?,?,?,'Y')";
        sqlArgs.add(itemCode);
        sqlArgs.add(etsItemInfo.getItemName());
        sqlArgs.add(etsItemInfo.getItemSpec());
        sqlArgs.add(etsItemInfo.getItemCategory());
        sqlArgs.add(etsItemInfo.getVendorId());
        sqlArgs.add(sfUser.getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
