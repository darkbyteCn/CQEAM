package com.sino.ams.net.statistic.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.net.statistic.dto.EquipStatDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EquipStatModel</p>
 * <p>Description:程序自动生成SQL构造器“EquipStatModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class EquipStatModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;


    /**
     * 功能：EQUIP_STAT 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EquipStatDTO 本次操作的数据
     */
    public EquipStatModel(SfUserDTO userAccount, EquipStatDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;

    }

    /**
     * 功能：框架自动生成EQUIP_STAT页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        EquipStatDTO equipStat = (EquipStatDTO) dtoParameter;
        String qryType = equipStat.getQryType();
        if (qryType.equals(WebAttrConstant.BY_LOCUS)) {     //设备现有量--按地点
            sqlStr = "SELECT " +
                    "         EM.COMPANY ORGANIZATION_NAME, " +
                    "         EO.WORKORDER_OBJECT_NAME, " +
                    "         EO.WORKORDER_OBJECT_LOCATION, " +
                    "         ESI.ITEM_CATEGORY, " +
                    "         ESI.ITEM_NAME, " +
                    "         ESI.ITEM_SPEC, " +
                    "         COUNT(1)  CNT  \n " +
                    " FROM     ETS_ITEM_INFO      EII, " +
                    "          AMS_OBJECT_ADDRESS AOA, " +
                    "          ETS_OBJECT         EO, " +
                    "          ETS_OU_CITY_MAP    EM, " +
                    "          ETS_SYSTEM_ITEM    ESI \n" +
                    " WHERE " +
                    "          EII.ITEM_CODE = ESI.ITEM_CODE " +
                    " AND      EII.ADDRESS_ID = AOA.ADDRESS_ID " +
                    " AND      AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO " +
                    " AND      EII.ORGANIZATION_ID = EM.ORGANIZATION_ID " +
                    " AND    ( " + SyBaseSQLUtil.isNull() + "  OR  EII.ORGANIZATION_ID =　?) " +
                    " AND    ( " + SyBaseSQLUtil.isNull() + "  OR  EO.COUNTY_CODE = ?) " +
                    " AND    ( " + SyBaseSQLUtil.isNull() + "  OR  EO.OBJECT_CATEGORY = ?) " +
                    " AND    ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?) " +
                    " AND    ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?) \n" +
                    " AND    ( " + SyBaseSQLUtil.isNull() + "  OR EII.FINANCE_PROP = ?) \n" +
                    " GROUP BY " +
                    "         EM.COMPANY, " +
                    "         EO.WORKORDER_OBJECT_NAME, " +
                    "         EO.WORKORDER_OBJECT_LOCATION, " +
                    "         ESI.ITEM_CATEGORY, " +
                    "         ESI.ITEM_NAME, " +
                    "         ESI.ITEM_SPEC ";

            sqlArgs.add(equipStat.getOrganizationId());
            sqlArgs.add(equipStat.getOrganizationId());
            sqlArgs.add(equipStat.getCountyCode());
            sqlArgs.add(equipStat.getCountyCode());
            sqlArgs.add(equipStat.getObjectCategory());
            sqlArgs.add(equipStat.getObjectCategory());
            sqlArgs.add(equipStat.getWorkorderObjectName());
            sqlArgs.add(equipStat.getWorkorderObjectName());
            sqlArgs.add(equipStat.getItemSpec());
            sqlArgs.add(equipStat.getItemSpec());
            sqlArgs.add(equipStat.getFinaceProp());
            sqlArgs.add(equipStat.getFinaceProp());
        } else if (qryType.equals(WebAttrConstant.BY_CATEGORY)) {      //设备现有量--按状态
            sqlStr = " SELECT" +
                    " EM.COMPANY ORGANIZATION_NAME,    \n" +
//                    " ESI.ITEM_CATEGORY,    \n" +
                    " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,\n" +
                    " AMS_PUB_PKG.GET_FLEX_VALUE(EII.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS,\n" +
                    " ESI.ITEM_NAME,    \n" +
                    " ESI.ITEM_SPEC,    \n" +
                    " COUNT(EII.SYSTEMID) CNT  \n" +
                    " FROM   ETS_ITEM_INFO   EII,    \n" +
                    " ETS_SYSTEM_ITEM ESI,    \n" +
                    " ETS_OU_CITY_MAP EM     \n" +
                    " WHERE  EII.ORGANIZATION_ID = EM.ORGANIZATION_ID    \n" +
                    " AND    EII.ITEM_CODE = ESI.ITEM_CODE    \n" +
                    " AND    ( " + SyBaseSQLUtil.isNull() + "  OR  EII.ORGANIZATION_ID =　?) " +
                    " AND   ( " + SyBaseSQLUtil.isNull() + "  OR  ESI.ITEM_CATEGORY = ?)    \n" +
                    " AND   ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?) \n" +
                    " AND   ( " + SyBaseSQLUtil.isNull() + "  OR  EII.FINANCE_PROP = ?) \n" +
                    " AND   ( " + SyBaseSQLUtil.isNull() + "  OR  EII.ITEM_STATUS = ?)   \n" +
                    " GROUP  BY ESI.ITEM_NAME,    \n" +
                    " ESI.ITEM_SPEC,  \n" +
                    " ESI.ITEM_CATEGORY,  \n" +
                    " EII.ITEM_STATUS,  \n" +
                    " EM.COMPANY  ";
            sqlArgs.add(equipStat.getOrganizationId());
            sqlArgs.add(equipStat.getOrganizationId());
            sqlArgs.add(equipStat.getItemCategory());
            sqlArgs.add(equipStat.getItemCategory());
            sqlArgs.add(equipStat.getItemSpec());
            sqlArgs.add(equipStat.getItemSpec());
            sqlArgs.add(equipStat.getFinaceProp());
            sqlArgs.add(equipStat.getFinaceProp());
            sqlArgs.add(equipStat.getItemStatus());
            sqlArgs.add(equipStat.getItemStatus());

        } else if (qryType.equals(WebAttrConstant.BY_VENDOR)) {     //设备现有量--按厂家
            sqlStr = "SELECT EMV.VENDOR_NAME,\n" +
                    "    ESI.ITEM_NAME,\n" +
                    "    ESI.ITEM_SPEC,\n" +
                    "    EII.ITEM_STATUS ," +
                    "    COUNT(*) CNT \n" +
                    " FROM   ETS_ITEM_INFO      EII,\n" +
                    "    ETS_SYSTEM_ITEM    ESI,\n" +
                    "    ETS_MIS_PO_VENDORS EMV \n" +
                    " WHERE  ESI.VENDOR_ID *= EMV.VENDOR_ID\n" +
                    " AND    EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                   " AND    ( " + SyBaseSQLUtil.isNull() + "  OR  EII.ORGANIZATION_ID =　?) " +
                    " AND   ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?) \n" +
                    " AND   ( " + SyBaseSQLUtil.isNull() + "  OR EII.ITEM_STATUS = ?)\n" +
                    " AND   ( " + SyBaseSQLUtil.isNull() + "  OR  ESI.VENDOR_ID = ?)\n" +
                    " GROUP  BY EMV.VENDOR_NAME,\n" +
                    "     ESI.ITEM_NAME,\n" +
                    "     ESI.ITEM_SPEC,\n" +
                    "     EII.ITEM_STATUS";

            sqlArgs.add(equipStat.getOrganizationId());
            sqlArgs.add(equipStat.getOrganizationId());
            sqlArgs.add(equipStat.getItemSpec());
            sqlArgs.add(equipStat.getItemSpec());
            sqlArgs.add(equipStat.getItemStatus());
            sqlArgs.add(equipStat.getItemStatus());
            sqlArgs.add(equipStat.getVendorId());
            sqlArgs.add(equipStat.getVendorId());
        } else if (qryType.equals(WebAttrConstant.BY_CATEGORY + "2")) {      //全省统计--按状态
            sqlStr = " SELECT" +
                    " EM.COMPANY ORGANIZATION_NAME,    \n" +
                    " ESI.ITEM_CATEGORY,    \n" +
                    " ESI.ITEM_NAME,    \n" +
                    " ESI.ITEM_SPEC,    \n" +
                    " COUNT(EII.SYSTEMID) CNT  \n" +
                    " FROM   " +
                    " ETS_ITEM_INFO   EII,    \n" +
                    " ETS_SYSTEM_ITEM ESI,    \n" +
                    " ETS_OU_CITY_MAP EM ,    \n" +
                    " AMS_OBJECT_ADDRESS AOA, " +
                    " ETS_OBJECT EO" +
                    " WHERE   EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    " AND    AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO " +
                    " AND    EII.ORGANIZATION_ID = EM.ORGANIZATION_ID \n" +
                    " AND    EII.ITEM_CODE = ESI.ITEM_CODE \n" +
                    " AND    EO.OBJECT_CATEGORY > ? " +
                    " AND    EO.OBJECT_CATEGORY < ? " +
                    " AND    EO.OBJECT_CATEGORY <>74 " +
                   " AND    ( " + SyBaseSQLUtil.isNull() + "  OR  EII.ORGANIZATION_ID =　?) " +
                    " AND    ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY = ?)    \n" +
                    " AND    ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?) \n" +
                    " AND    ( " + SyBaseSQLUtil.isNull() + "  OR EII.FINANCE_PROP = ?) \n" +
                    " AND    ( " + SyBaseSQLUtil.isNull() + "  OR EII.ITEM_STATUS = ?)   \n" +
                    " GROUP  BY ESI.ITEM_NAME,    \n" +
                    " ESI.ITEM_SPEC,    \n" +
                    " ESI.ITEM_CATEGORY,    \n" +
                    " EM.COMPANY   ";
            sqlArgs.add(WebAttrConstant.INV_CATEGORY);       sqlArgs.add(DictConstant.NETADDR_OTHERS);
            sqlArgs.add(equipStat.getOrganizationId());
            sqlArgs.add(equipStat.getOrganizationId());
            sqlArgs.add(equipStat.getItemCategory());
            sqlArgs.add(equipStat.getItemCategory());
            sqlArgs.add(equipStat.getItemSpec());
            sqlArgs.add(equipStat.getItemSpec());
            sqlArgs.add(equipStat.getFinaceProp());
            sqlArgs.add(equipStat.getFinaceProp());
            sqlArgs.add(equipStat.getItemStatus());
            sqlArgs.add(equipStat.getItemStatus());

        } else if (qryType.equals(WebAttrConstant.BY_NAME)) {       //全省统计--按地点
            String materialAttr = equipStat.getMaterialAttr();
            if (materialAttr.equals("1")) {    //非条码物料
                sqlStr = " SELECT " +
                        " ESI.ITEM_NAME,\n" +
                        " ESI.ITEM_SPEC,\n" +
                        " AIS.QUANTITY CNT,\n" +
                        " EO.WORKORDER_OBJECT_NAME\n" +
                        " FROM " +
                        "       AMS_INV_STORAGE AIS,\n" +
                        "       ETS_SYSTEM_ITEM ESI,\n" +
                        "       ETS_OBJECT EO \n" +
                        " WHERE" +
                        "     AIS.ITEM_CODE = ESI.ITEM_CODE \n" +
                        " AND AIS.INV_CODE = EO.WORKORDER_OBJECT_NO \n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AIS.INV_CODE = ?) \n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?) \n" +
                        " AND ( " + SyBaseSQLUtil.isNull() + "  OR AIS.ORGANIZATION_ID = ?) " +  //必填
                        " AND EO.OBJECT_CATEGORY > ? "+
                        " AND EO.OBJECT_CATEGORY < ? "+
                         " AND    EO.OBJECT_CATEGORY <>74 " ;
                sqlArgs.add(equipStat.getWorkorderObjectCode());
                sqlArgs.add(equipStat.getWorkorderObjectCode());
                sqlArgs.add(equipStat.getItemSpec());
                sqlArgs.add(equipStat.getItemSpec());
                sqlArgs.add(equipStat.getOrganizationId());
                sqlArgs.add(equipStat.getOrganizationId());
                sqlArgs.add(WebAttrConstant.INV_CATEGORY);
                sqlArgs.add(DictConstant.NETADDR_OTHERS);
            } else if (materialAttr.equals("2")) {  //条码物料
                sqlStr = "SELECT  " +
                        "     ESI.ITEM_NAME,\n" +
                        "     ESI.ITEM_SPEC,\n" +
                        "     EO.WORKORDER_OBJECT_NAME,\n" +
                        "     COUNT(*) CNT\n" +
                        " FROM " +
                        "       ETS_ITEM_INFO EII,\n" +
                        "       ETS_SYSTEM_ITEM ESI,\n" +
                        "       AMS_OBJECT_ADDRESS AOA,\n" +
                        "       ETS_OBJECT EO ," +
                        "       ETS_OU_CITY_MAP EM\n" +
                        " WHERE" +
                        "       EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                        " AND   AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO " +
                        " AND   EII.ORGANIZATION_ID = EM.ORGANIZATION_ID    \n" +
                        " AND   EO.OBJECT_CATEGORY > ? " +
                        " AND   EO.OBJECT_CATEGORY < ? " +
                         " AND    EO.OBJECT_CATEGORY <>74 " +
                        " AND   EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                        " AND   AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                        " AND   EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                       " AND    ( " + SyBaseSQLUtil.isNull() + "  OR  EII.ORGANIZATION_ID =　?) " +
                        " AND  ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE = ? ) \n" +
                        " AND  ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?) \n" +
                        " AND  ( " + SyBaseSQLUtil.isNull() + "  OR EII.FINANCE_PROP = ?)\n" +
                        " GROUP BY " +
                        " ESI.ITEM_NAME,ESI.ITEM_SPEC,EO.WORKORDER_OBJECT_NAME";

                sqlArgs.add(WebAttrConstant.INV_CATEGORY);       sqlArgs.add(DictConstant.NETADDR_OTHERS);
                sqlArgs.add(equipStat.getOrganizationId());
                sqlArgs.add(equipStat.getOrganizationId());
                sqlArgs.add(equipStat.getWorkorderObjectCode());
                sqlArgs.add(equipStat.getWorkorderObjectCode());
                sqlArgs.add(equipStat.getItemSpec());
                sqlArgs.add(equipStat.getItemSpec());
                sqlArgs.add(equipStat.getFinaceProp());
                sqlArgs.add(equipStat.getFinaceProp());
            }

        } else if (qryType.equals(WebAttrConstant.BY_VENDOR + "2")) {      //全省统计--按厂家
            sqlStr = "SELECT EMV.VENDOR_NAME,\n" +
                    "    ESI.ITEM_NAME,\n" +
                    "    ESI.ITEM_SPEC,\n" +
                    "    EII.ITEM_STATUS ," +
                    "    COUNT(*) CNT \n" +
                    " FROM  " +
                    "    ETS_ITEM_INFO  EII,\n" +
                    "    ETS_SYSTEM_ITEM    ESI,\n" +
                    "    ETS_MIS_PO_VENDORS EMV, \n" +
                    "    AMS_OBJECT_ADDRESS AOA, " +
                    "    ETS_OBJECT EO" +
                    " WHERE  ESI.VENDOR_ID *= EMV.VENDOR_ID \n" +
                    " AND    EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                    " AND　　 EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    " AND    AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO " +
                   " AND    ( " + SyBaseSQLUtil.isNull() + "  OR  EII.ORGANIZATION_ID =　?) " +
                    " AND   ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?) \n" +
                    " AND   ( " + SyBaseSQLUtil.isNull() + "  OR EII.ITEM_STATUS = ?)\n" +
                    " AND   ( " + SyBaseSQLUtil.isNull() + "  OR ESI.VENDOR_ID = ?)\n" +
                    " AND EO.OBJECT_CATEGORY > ? " +
                    " AND EO.OBJECT_CATEGORY < ? " +
                     " AND    EO.OBJECT_CATEGORY <>74 " +
                    " GROUP  BY EMV.VENDOR_NAME,\n" +
                    "     ESI.ITEM_NAME,\n" +
                    "     ESI.ITEM_SPEC,\n" +
                    "     EII.ITEM_STATUS";

            sqlArgs.add(equipStat.getOrganizationId());
            sqlArgs.add(equipStat.getOrganizationId());
            sqlArgs.add(equipStat.getItemSpec());
            sqlArgs.add(equipStat.getItemSpec());
            sqlArgs.add(equipStat.getItemStatus());
            sqlArgs.add(equipStat.getItemStatus());
            sqlArgs.add(equipStat.getVendorId());
            sqlArgs.add(equipStat.getVendorId());
            sqlArgs.add(WebAttrConstant.INV_CATEGORY);
                sqlArgs.add(DictConstant.NETADDR_OTHERS);
        }

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}