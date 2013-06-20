package com.sino.soa.mis.srv.inquiryassetlocation.model;


import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.util.IntegerUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.mis.srv.inquiryassetlocation.dto.SrvAssetLocationDTO;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Title: SrvAssetCategoryModel</p>
 * <p>Description:程序自动生成SQL构造器“SrvAssetCategoryModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author wzp
 * @version 1.0
 *          DES:同步资产地点
 */


public class SrvAssetLocationModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：资产类别服务 SRV_ASSET_CATEGORY 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter SrvAssetCategoryDTO 本次操作的数据
     */
    public SrvAssetLocationModel(SfUserDTO userAccount, SrvAssetLocationDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成资产类别服务 SRV_ASSET_CATEGORY数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() throws SQLModelException, RuntimeException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SrvAssetLocationDTO srvAssetCategory = (SrvAssetLocationDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " ETS_FA_ASSETS_LOCATION("
                + " BOOK_TYPE_CODE,"
                + " ASSETS_LOCATION,"
                + " ORG_ID,"
                + " ASSETS_LOCATION_CODE,"
                + " ENABLED_FLAG,"
                + " CREATED_BY,"
                + " CREATION_DATE,"
                + " LAST_UPDATE_BY,"
                + " LAST_UPDATE_DATE"
                + ") VALUES ("
                + " ?, ?, ?, ?, ?, ?, GETDATE(), ?, GETDATE())";

        sqlArgs.add(srvAssetCategory.getBookTypeCode());   //
        sqlArgs.add(srvAssetCategory.getLocationCombinationName());
        sqlArgs.add(IntegerUtil.parseInt(srvAssetCategory.getOrgId()));
        sqlArgs.add(srvAssetCategory.getLocationCombinationCode());
        sqlArgs.add(srvAssetCategory.getEnabledFlag());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(sfUser.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成资产类别服务 SRV_ASSET_CATEGORY数据更新SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SrvAssetLocationDTO srvAssetCategory = (SrvAssetLocationDTO) dtoParameter;
        String tmp = srvAssetCategory.getSegment1() + "." + srvAssetCategory.getSegment2() + "." + srvAssetCategory.getSegment3();
        String sqlStr = "UPDATE ETS_FA_ASSETS_LOCATION"
                + " SET"
                //+ " BOOK_TYPE_CODE=?,"
                + " ASSETS_LOCATION=?,"
                //+ " ORG_ID=?,"
                + " ASSETS_LOCATION_CODE=?,"
                + " ENABLED_FLAG=?,"
                + " LAST_UPDATE_BY=?,"
                + " LAST_UPDATE_DATE=GETDATE()"
                + " WHERE ASSETS_LOCATION_CODE=?";
        //sqlArgs.add(srvAssetCategory.getBookTypeCode());
        sqlArgs.add(srvAssetCategory.getLocationCombinationName());
        //sqlArgs.add(srvAssetCategory.getOrgId());
        sqlArgs.add(srvAssetCategory.getLocationCombinationCode());
        sqlArgs.add(srvAssetCategory.getEnabledFlag());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(srvAssetCategory.getLocationCombinationCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能:判断MIS固定资产地点表中是否存在地点三段组合代码
     *
     * @return
     */
    public SQLModel getEcouInforModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SrvAssetLocationDTO srvAssetCategory = (SrvAssetLocationDTO) dtoParameter;
        String sqlStr = "SELECT"
                + "	ACD.ASSETS_LOCATION_CODE "
                + "	FROM ETS_FA_ASSETS_LOCATION ACD WHERE ACD.ASSETS_LOCATION_CODE=?";
        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(srvAssetCategory.getLocationCombinationCode());
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：判断是否已有此TD地址
     *
     * @return
     */
    public SQLModel isExistTdAssetsLocation() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SrvAssetLocationDTO srvAssetCategory = (SrvAssetLocationDTO) dtoParameter;
        String sqlStr = "SELECT"
                + "	ACD.ASSETS_LOCATION_CODE "
                + "	FROM ETS_FA_ASSETS_LOCATION_TD ACD WHERE ACD.ASSETS_LOCATION_CODE=?";
        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(srvAssetCategory.getLocationCombinationCode());
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据地点代码第二段获取OU组织ID和资产账簿代码
     * @param objectCode
     * @return
     */
    public SQLModel getEcomCodeModel(String objectCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT \n" +
                "    EOCM.ORGANIZATION_ID, \n" +
                "    EOCM.BOOK_TYPE_CODE\n" +
                " FROM \n" +
                "    ETS_OU_CITY_MAP EOCM\n" +
                "WHERE \n" +
                "    RIGHT(EOCM.BOOK_TYPE_CODE, 4) = ?";
        int index = objectCode.indexOf(".");
        objectCode = objectCode.substring(index + 1);
        objectCode = objectCode.substring(0, 4);
        sqlArgs.add(objectCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成OU组织信息服务 SRV_OU_ORGANIZATION页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SrvAssetLocationDTO srvOuOrganization = (SrvAssetLocationDTO) dtoParameter;
        String sqlStr = " SELECT EFA.BOOK_TYPE_CODE,                               "
                + "        EFA.ASSETS_LOCATION,                              "
                + "        EFA.CREATION_DATE,                                "
                + "        EFA.CREATED_BY,                                   "
                + "        EFA.LAST_UPDATE_DATE,                             "
                + "        EFA.LAST_UPDATE_BY,                               "
                + "        EFA.ORG_ID,                                       "
                + "        EFA.ASSETS_LOCATION_CODE,                         "
                + "        EFA.ENABLED_FLAG                                  "
                + "   FROM ETS_FA_ASSETS_LOCATION EFA, ETS_OU_CITY_MAP EO    "
                + "  WHERE EO.BOOK_TYPE_CODE = EFA.BOOK_TYPE_CODE            "
                + "        AND (? IS NULL OR  EO.ORGANIZATION_ID LIKE ? )                  ";
        sqlArgs.add(srvOuOrganization.getOrganizationId());
        sqlArgs.add(srvOuOrganization.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


}