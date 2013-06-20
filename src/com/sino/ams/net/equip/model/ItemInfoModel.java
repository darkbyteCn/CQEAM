package com.sino.ams.net.equip.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.net.equip.dto.ItemInfoDTO;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: ItemInfoModel</p>
 * <p>Description:程序自动生成SQL构造器“ItemInfoModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class ItemInfoModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：ITEM_INFO 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter ItemInfoDTO 本次操作的数据
     */
    public ItemInfoModel(SfUserDTO userAccount, ItemInfoDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }


    /**
     * 功能：框架自动生成ITEM_INFO页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ItemInfoDTO itemInfo = (ItemInfoDTO) dtoParameter;
        String sqlStr = "";

        if (itemInfo.getQryType().equals(WebAttrConstant.BY_DAIWEI)) {
            sqlStr = "SELECT " +
                            "      EII.BARCODE,\n" +
                            "      ESI.ITEM_NAME,\n" +
                            "      ESI.ITEM_SPEC,\n" +
                            "      EO.WORKORDER_OBJECT_CODE,\n" +
                            "      EO.WORKORDER_OBJECT_LOCATION,\n" +
                            "      AMC.NAME\n" +
                            "FROM   ETS_OBJECT                  EO,\n" +
                            "      AMS_MAINTAIN_COMPANY        AMC,\n" +
                            "      AMS_MAINTAIN_RESPONSIBILITY AMR,\n" +
                            "      AMS_OBJECT_ADDRESS          AOA,\n" +
                            "      ETS_ITEM_INFO               EII,\n" +
                            "      ETS_SYSTEM_ITEM             ESI\n" +
                            "WHERE  AMC.COMPANY_ID = AMR.COMPANY_ID\n" +
                            "AND    AMR.OBJECT_NO = AOA.OBJECT_NO\n" +
                            "AND    EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                            "AND    AOA.ADDRESS_ID = EII.ADDRESS_ID\n" +
                            "AND    EII.ITEM_CODE = ESI.ITEM_CODE\n" +
//                            "AND    ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)\n" +
//                            "AND    ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
                            "AND    ( " + SyBaseSQLUtil.isNull() + "  OR AMC.NAME LIKE ?)\n" +
                            "AND    ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
                            "AND    ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)";
//                             sqlArgs.add(itemInfo.getItemName());
//                             sqlArgs.add(itemInfo.getItemName());
//                             sqlArgs.add(itemInfo.getItemSpec());
//                             sqlArgs.add(itemInfo.getItemSpec());
                             sqlArgs.add(itemInfo.getDaiwei());
                             sqlArgs.add(itemInfo.getDaiwei());
                             sqlArgs.add(itemInfo.getOrganizationId());
                             sqlArgs.add(itemInfo.getOrganizationId());
                             sqlArgs.add(itemInfo.getBarcode());
                             sqlArgs.add(itemInfo.getBarcode());
        } else {
            sqlStr = "SELECT " +
                    "    EII.SYSTEMID, " +
                    "    EII.BARCODE, " +

                    "    EII.START_DATE  START_DATE, " +
                    "    EII.DISABLE_DATE  DISABLE_DATE, " +

                    "    EP.PROJECT_ID, " +
                    "    EP.NAME PROJECT_NAME, " +

                    "    EII.FINANCE_PROP, " +
                    "    EII.SENDTOMIS_FLAG, " +

                    "    ESI.ITEM_CODE, " +
                    "    ESI.ITEM_NAME, " +
                    "    ESI.ITEM_SPEC, " +
//                    "    EFV.VALUE ITEM_CATEGORY, " +
                    "   AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,"+

                    "    EO.WORKORDER_OBJECT_NAME, " +
                    "    AOA.ADDRESS_ID, " +
                    "    AOA.BOX_NO, " +
                    "    AOA.NET_UNIT, " +
                    "    EC.COUNTY_CODE, " +
                    "    EC.COUNTY_NAME " +

                    " FROM   ETS_ITEM_INFO       EII, " +
                    "    ETS_OBJECT          EO, " +
                    "    AMS_OBJECT_ADDRESS  AOA, " +
                    "    ETS_SYSTEM_ITEM     ESI, " +
                    "    ETS_PA_PROJECTS_ALL EP," +
                    "    ETS_COUNTY          EC, " +
                    "    ETS_OU_CITY_MAP     EOCM " +
//                    "    ETS_FLEX_VALUES EFV," +
//                    "    ETS_FLEX_VALUE_SET EFVS " +
                    " WHERE  EII.ITEM_CODE = ESI.ITEM_CODE    " +
                    " AND    EII.ADDRESS_ID = AOA.ADDRESS_ID " +
//                    " AND    EFVS.FLEX_VALUE_SET_ID=EFV.FLEX_VALUE_SET_ID" +
//                    " AND    EFVS.CODE = 'ITEM_TYPE'\n" +
//                    " AND    ESI.ITEM_CATEGORY = EFV.CODE " +
                    " AND    EII.PROJECTID *= EP.PROJECT_ID " +
                    " AND    AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO " +
                    " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"  +
                    " AND EC.COMPANY_CODE =* EOCM.COMPANY_CODE"        +
                    " AND    EO.COUNTY_CODE *= EC.COUNTY_CODE ";

            if (itemInfo.getQryType().equals(WebAttrConstant.BY_PROJECTID)) {
                sqlStr += " AND ( " + SyBaseSQLUtil.isNull() + "  OR EP.NAME LIKE ?)\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)";
                sqlArgs.add(itemInfo.getProjectName());
                sqlArgs.add(itemInfo.getProjectName());
                sqlArgs.add(itemInfo.getOrganizationId());
                sqlArgs.add(itemInfo.getOrganizationId());
                sqlArgs.add(itemInfo.getItemSpec());
                sqlArgs.add(itemInfo.getItemSpec());
            } else if (itemInfo.getQryType().equals(WebAttrConstant.BY_BARCODE)) {
                sqlStr += " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
                        " AND (  " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)";
//                    +"and rownum<100";
                sqlArgs.add(itemInfo.getOrganizationId());
                sqlArgs.add(itemInfo.getOrganizationId());
                sqlArgs.add(itemInfo.getBarcode());
                sqlArgs.add(itemInfo.getBarcode());
            } else if (itemInfo.getQryType().equals(WebAttrConstant.BY_SPEC)) {
                sqlStr += " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ? )\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ? )" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY =  ? )\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR EP.NAME LIKE ?)\n";
                sqlArgs.add(itemInfo.getOrganizationId());
                sqlArgs.add(itemInfo.getOrganizationId());
                sqlArgs.add(itemInfo.getItemSpec());
                sqlArgs.add(itemInfo.getItemSpec());
                sqlArgs.add(itemInfo.getItemCategory());
                sqlArgs.add(itemInfo.getItemCategory());
                sqlArgs.add(itemInfo.getProjectName());
                sqlArgs.add(itemInfo.getProjectName());
            } else if (itemInfo.getQryType().equals(WebAttrConstant.BY_CATEGORY)) {
                sqlStr += " AND ( " + SyBaseSQLUtil.isNull() + "  OR  EII.ORGANIZATION_ID =  ? )\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR  EII.FINANCE_PROP =  ? )\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR  EII.START_DATE >=    ? )\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR  EII.START_DATE <=    ? )\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR EP.NAME LIKE ?)\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)";
                sqlArgs.add(itemInfo.getOrganizationId());
                sqlArgs.add(itemInfo.getOrganizationId());
                sqlArgs.add(itemInfo.getFinanceProp());
                sqlArgs.add(itemInfo.getFinanceProp());
                sqlArgs.add(itemInfo.getMinTime());
                sqlArgs.add(itemInfo.getMinTime());
                sqlArgs.add(itemInfo.getMaxTime());
                sqlArgs.add(itemInfo.getMaxTime());
                sqlArgs.add(itemInfo.getProjectName());
                sqlArgs.add(itemInfo.getProjectName());
                sqlArgs.add(itemInfo.getItemSpec());
                sqlArgs.add(itemInfo.getItemSpec());
            } else if (itemInfo.getQryType().equals(WebAttrConstant.BY_LOCUS)) {
                sqlStr += " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID =  ? )\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.OBJECT_CATEGORY =  ? )\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR EP.NAME LIKE ?)\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)" +
                        " AND (EO.OBJECT_CATEGORY < ? OR EO.OBJECT_CATEGORY = ?)";
                sqlArgs.add(itemInfo.getOrganizationId());
                sqlArgs.add(itemInfo.getOrganizationId());
                sqlArgs.add(itemInfo.getObjectCategory());
                sqlArgs.add(itemInfo.getObjectCategory());
                sqlArgs.add(itemInfo.getProjectName());
                sqlArgs.add(itemInfo.getProjectName());
                sqlArgs.add(itemInfo.getItemSpec());
                sqlArgs.add(itemInfo.getItemSpec());
                sqlArgs.add(itemInfo.getWorkorderObjectName());
                sqlArgs.add(itemInfo.getWorkorderObjectName());
                sqlArgs.add(WebAttrConstant.INV_CATEGORY);
                sqlArgs.add(AssetsDictConstant.NETADDR_OTHERS);

            } else if (itemInfo.getQryType().equals(WebAttrConstant.BY_ALLOT)) {
                sqlStr += " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.OBJECT_CATEGORY =  ? )\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID =  ? )\n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR EP.NAME LIKE ?)\n" +
                        " AND EO.OBJECT_CATEGORY > ?" +
                        " AND EO.OBJECT_CATEGORY < ?";
                sqlArgs.add(itemInfo.getObjectCategory());
                sqlArgs.add(itemInfo.getObjectCategory());
                sqlArgs.add(itemInfo.getOrganizationId());
                sqlArgs.add(itemInfo.getOrganizationId());
                sqlArgs.add(itemInfo.getWorkorderObjectName());
                sqlArgs.add(itemInfo.getWorkorderObjectName());
                sqlArgs.add(itemInfo.getItemSpec());
                sqlArgs.add(itemInfo.getItemSpec());
                sqlArgs.add(itemInfo.getProjectName());
                sqlArgs.add(itemInfo.getProjectName());
                sqlArgs.add(WebAttrConstant.INV_CATEGORY);
                sqlArgs.add(AssetsDictConstant.NETADDR_OTHERS);
            }
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
