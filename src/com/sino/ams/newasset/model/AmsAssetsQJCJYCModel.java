package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsCJYCDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-14
 * Time: 18:10:26
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsQJCJYCModel extends AMSSQLProducer {
    private AmsAssetsCJYCDTO dto = null;

    public AmsAssetsQJCJYCModel(SfUserDTO userAccount, AmsAssetsCJYCDTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.dto = (AmsAssetsCJYCDTO) dtoParameter;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr ="SELECT MFFV1.DESCRIPTION FA_CAT_NAME_1,\n" +
                "       MFFV2.DESCRIPTION FA_CAT_NAME_2,\n" +
                "       MFFV3.DESCRIPTION FA_CAT_NAME_3,\n" +
                "       SUM(SFFD.DEPRN_COST_1) DEPRN_COST_1,\n" +
                "       SUM(SFFD.DEPRN_COST_2) DEPRN_COST_2,\n" +
                "       SUM(SFFD.DEPRN_COST_3) DEPRN_COST_3,\n" +
                "       SUM(SFFD.DEPRN_COST_4) DEPRN_COST_4,\n" +
                "       SUM(SFFD.DEPRN_COST_5) DEPRN_COST_5,\n" +
                "       SUM(SFFD.DEPRN_COST_6) DEPRN_COST_6,\n" +
                "       SUM(SFFD.DEPRN_COST_7) DEPRN_COST_7,\n" +
                "       SUM(SFFD.DEPRN_COST_8) DEPRN_COST_8,\n" +
                "       SUM(SFFD.DEPRN_COST_9) DEPRN_COST_9,\n" +
                "       SUM(SFFD.DEPRN_COST_10) DEPRN_COST_10,\n" +
                "       SUM(SFFD.DEPRN_COST_11) DEPRN_COST_11,\n" +
                "       SUM(SFFD.DEPRN_COST_12) DEPRN_COST_12,\n" +
                "       SUM(SFFD.DEPRN_COST_13) DEPRN_COST_13,\n" +
                "       SUM(SFFD.DEPRN_COST_14) DEPRN_COST_14,\n" +
                "       SUM(SFFD.DEPRN_COST_15) DEPRN_COST_15,\n" +
                "       SUM(SFFD.DEPRN_COST_16) DEPRN_COST_16,\n" +
                "       SUM(SFFD.DEPRN_COST_17) DEPRN_COST_17,\n" +
                "       SUM(SFFD.DEPRN_COST_18) DEPRN_COST_18,\n" +
                "       SUM(SFFD.DEPRN_COST_19) DEPRN_COST_19,\n" +
                "       SUM(SFFD.DEPRN_COST_20) DEPRN_COST_20,\n" +
                "       SUM(SFFD.DEPRN_COST_21) DEPRN_COST_21,\n" +
                "       SUM(SFFD.DEPRN_COST_22) DEPRN_COST_22,\n" +
                "       SUM(SFFD.DEPRN_COST_23) DEPRN_COST_23,\n" +
                "       SUM(SFFD.DEPRN_COST_24) DEPRN_COST_24,\n" +
                "       SUM(SFFD.DEPRN_COST_25) DEPRN_COST_25,\n" +
                "       SUM(SFFD.DEPRN_COST_26) DEPRN_COST_26,\n" +
                "       SUM(SFFD.DEPRN_COST_27) DEPRN_COST_27,\n" +
                "       SUM(SFFD.DEPRN_COST_28) DEPRN_COST_28,\n" +
                "       SUM(SFFD.DEPRN_COST_29) DEPRN_COST_29,\n" +
                "       SUM(SFFD.DEPRN_COST_30) DEPRN_COST_30,\n" +
                "       SUM(SFFD.DEPRN_COST_31) DEPRN_COST_31,\n" +
                "       SUM(SFFD.DEPRN_COST_32) DEPRN_COST_32,\n" +
                "       SUM(SFFD.DEPRN_COST_33) DEPRN_COST_33,\n" +
                "       SUM(SFFD.DEPRN_COST_34) DEPRN_COST_34,\n" +
                "       SUM(SFFD.DEPRN_COST_35) DEPRN_COST_35,\n" +
                "       SUM(SFFD.DEPRN_COST_36) DEPRN_COST_36,\n" +
                "       SUM(SFFD.DEPRN_COST_37) DEPRN_COST_37,\n" +
                "       SUM(SFFD.DEPRN_COST_38) DEPRN_COST_38,\n" +
                "       SUM(SFFD.DEPRN_COST_39) DEPRN_COST_39,\n" +
                "       SUM(SFFD.DEPRN_COST_40) DEPRN_COST_40,\n" +
                "       SUM(SFFD.DEPRN_COST_41) DEPRN_COST_41,\n" +
                "       SUM(SFFD.DEPRN_COST_42) DEPRN_COST_42,\n" +
                "       SUM(SFFD.DEPRN_COST_43) DEPRN_COST_43,\n" +
                "       SUM(SFFD.DEPRN_COST_44) DEPRN_COST_44,\n" +
                "       SUM(SFFD.DEPRN_COST_45) DEPRN_COST_45,\n" +
                "       SUM(SFFD.DEPRN_COST_46) DEPRN_COST_46,\n" +
                "       SUM(SFFD.DEPRN_COST_47) DEPRN_COST_47,\n" +
                "       SUM(SFFD.DEPRN_COST_48) DEPRN_COST_48,\n" +
                "       SUM(SFFD.DEPRN_COST_49) DEPRN_COST_49,\n" +
                "       SUM(SFFD.DEPRN_COST_50) DEPRN_COST_50,\n" +
                "       SUM(SFFD.DEPRN_COST_51) DEPRN_COST_51,\n" +
                "       SUM(SFFD.DEPRN_COST_52) DEPRN_COST_52,\n" +
                "       SUM(SFFD.DEPRN_COST_53) DEPRN_COST_53,\n" +
                "       SUM(SFFD.DEPRN_COST_54) DEPRN_COST_54,\n" +
                "       SUM(SFFD.DEPRN_COST_55) DEPRN_COST_55,\n" +
                "       SUM(SFFD.DEPRN_COST_56) DEPRN_COST_56,\n" +
                "       SUM(SFFD.DEPRN_COST_57) DEPRN_COST_57,\n" +
                "       SUM(SFFD.DEPRN_COST_58) DEPRN_COST_58,\n" +
                "       SUM(SFFD.DEPRN_COST_59) DEPRN_COST_59,\n" +
                "       SUM(SFFD.DEPRN_COST_60) DEPRN_COST_60\n" +
                "FROM   SINO_FA_FORECAST_HEAD SFFH,\n" +
                "       SINO_FA_FORECAST_D    SFFD,\n" +
                "       M_FND_FLEX_VALUES     MFFV1,\n" +
                "       M_FND_FLEX_VALUE_SETS MFFVS1,\n" +
                "       M_FND_FLEX_VALUES     MFFV2,\n" +
                "       M_FND_FLEX_VALUE_SETS MFFVS2,\n" +
                "       M_FND_FLEX_VALUES     MFFV3,\n" +
                "       M_FND_FLEX_VALUE_SETS MFFVS3\n" +
                "WHERE  SFFH.BOOK_TYPE_CODE = SFFD.BOOK_TYPE_CODE\n" +
                "        AND SFFH.COMPLETE_DATE > GETDATE() - 30 " +
                "       AND MFFV1.FLEX_VALUE = SFFD.CAT_SEGMENT1\n" +
                "       AND MFFV2.FLEX_VALUE = SFFD.CAT_SEGMENT2\n" +
                "       AND MFFV3.FLEX_VALUE = SFFD.CAT_SEGMENT3\n" +
                "       AND MFFV1.FLEX_VALUE_SET_ID = MFFVS1.FLEX_VALUE_SET_ID\n" +
                "       AND MFFVS1.FLEX_VALUE_SET_NAME = ?\n" +
                "       AND MFFV2.FLEX_VALUE_SET_ID = MFFVS2.FLEX_VALUE_SET_ID\n" +
                "       AND MFFVS2.FLEX_VALUE_SET_NAME = ?\n" +
                "       AND MFFV3.FLEX_VALUE_SET_ID = MFFVS3.FLEX_VALUE_SET_ID\n" +
                "       AND MFFVS3.FLEX_VALUE_SET_NAME = ?\n" +
                "       AND MFFV2.FLEX_VALUE LIKE MFFV1.FLEX_VALUE || '%'\n" +
                "       AND MFFV3.PARENT_FLEX_VALUE_LOW <= MFFV2.FLEX_VALUE\n" +
                "       AND MFFV3.PARENT_FLEX_VALUE_HIGH >= MFFV2.FLEX_VALUE\n" +
                "       AND SFFD.DEPRN_REMAIN_MONTH <= dbo.NVL(?, SFFD.DEPRN_REMAIN_MONTH)\n" +
                "       AND SFFD.DEPRN_REMAIN_MONTH >= dbo.NVL(?, SFFD.DEPRN_REMAIN_MONTH)\n" +
                "       AND SFFH.BOOK_TYPE_CODE = dbo.NVL(?, SFFH.BOOK_TYPE_CODE)\n" +
                "       AND SFFD.CAT_SEGMENT2 LIKE dbo.NVL(?, SFFD.CAT_SEGMENT2)\n" +
                "       AND SFFD.CAT_SEGMENT1 = dbo.NVL(?, SFFD.CAT_SEGMENT1)\n" +
                "GROUP  BY MFFV1.DESCRIPTION,\n" +
                "          MFFV2.DESCRIPTION,\n" +
                "          MFFV3.DESCRIPTION";
        sqlArgs.add(dto.getFaCategory1());
        sqlArgs.add(dto.getFaCategory2());
        sqlArgs.add(dto.getFaCategory3());
        sqlArgs.add(dto.getToMonth());
        sqlArgs.add(dto.getFromMonth());
        sqlArgs.add(dto.getBookTypeCode());
        sqlArgs.add(dto.getCatSegment2());
        sqlArgs.add(dto.getCatSegment1());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
