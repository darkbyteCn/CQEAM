package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * <p>Title: AmsFaAssetsModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsFaAssetsModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AssetsQueryModel extends AMSSQLProducer {

    /**
     * 功能：固定资产当前信息(EAM) ETS_FA_ASSETS 数据库SQL构造层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsAddressVDTO 本次操作的数据
     */
    public AssetsQueryModel(SfUserDTO userAccount,
                            AmsAssetsAddressVDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：构造获取处置资产的SQL
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
            List sqlArgs = new ArrayList();
            String treeCategory = dto.getTreeCategory();
            String transType = AssetsDictConstant.ASS_DIS;
            if (treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_CLEAR)) { //处置资产
                transType = AssetsDictConstant.ASS_CLR;
            }
            String sqlStr = "SELECT"
                            + " AAAV.BARCODE,"
                            + " AAAV.ASSET_NUMBER,"
                            + " AAAV.ASSETS_DESCRIPTION,"
                            + " AAAV.MODEL_NUMBER,"
                            + " AAAV.COST,"
                            + " AAAV.DEPRN_COST,"
                            + " AAAV.DATE_PLACED_IN_SERVICE,"
                            + " AAAV.RESPONSIBILITY_USER_NAME,"
                            + " AAAV.MAINTAIN_USER_NAME,"
                            + " AAAV.RESPONSIBILITY_USER,"
                            + " AAAV.DEPT_NAME,"
                            + " AAAV.DEPT_CODE,"
                            + " AAAV.CURRENT_UNITS,"
                            + " AAAV.WORKORDER_OBJECT_NAME,"
                            + " AAAV.WORKORDER_OBJECT_NO,"
                            + " SU.USERNAME,"
                            + " AATH.TRANS_DATE"
                            + " FROM"
                            + " AMS_ASSETS_ADDRESS_V    AAAV,"
                            + " AMS_ASSETS_TRANS_LINE   AATL,"
                            + " AMS_ASSETS_TRANS_HEADER AATH,"
                            + " SF_USER                 SU,"
                            + " AMS_MIS_DEPT            AMD,"
                            + " ETS_OU_CITY_MAP         EOCM"
                            + " WHERE"
                            + " AAAV.BARCODE = AATL.BARCODE"
                            + " AND AATL.TRANS_ID = AATH.TRANS_ID"
                            + " AND AATH.FROM_DEPT = AMD.DEPT_CODE"
                            +
                    " AND AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                            + " AND AATH.CREATED_BY = SU.USER_ID"
                            + " AND AATH.TRANS_TYPE = ?"
                            + " AND AATH.TRANS_STATUS = ?"
                            + " AND AATH.CREATED_BY = ?"
                            +
                    " AND AAAV.ASSETS_DESCRIPTION LIKE dbo.NVL(?, AAAV.ASSETS_DESCRIPTION)"
                            +
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR AAAV.MODEL_NUMBER LIKE dbo.NVL(?, AAAV.MODEL_NUMBER))"
                            + " AND AAAV.BARCODE LIKE dbo.NVL(?, AAAV.BARCODE)"
                            + " AND AATH.TRANS_DATE >= dbo.NVL(?, AATH.TRANS_DATE)"
                            + " AND AATH.TRANS_DATE <= dbo.NVL(?, AATH.TRANS_DATE)";
            sqlArgs.add(transType);
            sqlArgs.add(AssetsDictConstant.APPROVED);
            sqlArgs.add(userAccount.getUserId());
            sqlArgs.add(dto.getAssetsDescription());
            sqlArgs.add(dto.getModelNumber());
            sqlArgs.add(dto.getModelNumber());
            sqlArgs.add(dto.getBarcode());
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
}
