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
 * Date: 2008-3-17
 * Time: 20:53:57
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsFLCJYCModel extends AMSSQLProducer {
    private AmsAssetsCJYCDTO dto = null;
    private SfUserDTO  udto=null;

    public AmsAssetsFLCJYCModel(SfUserDTO userAccount, AmsAssetsCJYCDTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.dto = (AmsAssetsCJYCDTO) dtoParameter;
        this.udto=userAccount;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT SFFD.CAT_SEGMENT1,\n" +
                "       SUM(SFFD.DEPRN_COST_1) ALL_QTY1,\n" +
                "       SUM(SFFD.DEPRN_COST_2) ALL_QTY2,\n" +
                "       SUM(SFFD.DEPRN_COST_3) ALL_QTY3,\n" +
                "       SUM(SFFD.DEPRN_COST_4) ALL_QTY4,\n" +
                "       SUM(SFFD.DEPRN_COST_5) ALL_QTY5,\n" +
                "       SUM(SFFD.DEPRN_COST_6) ALL_QTY6,\n" +
                "       SUM(SFFD.DEPRN_COST_7) ALL_QTY7,\n" +
                "       SUM(SFFD.DEPRN_COST_8) ALL_QTY8,\n" +
                "       SUM(SFFD.DEPRN_COST_9) ALL_QTY9,\n" +
                "       SUM(SFFD.DEPRN_COST_10) ALL_QTY10,\n" +
                "       SUM(SFFD.DEPRN_COST_11) ALL_QTY11,\n" +
                "       SUM(SFFD.DEPRN_COST_12) ALL_QTY12,\n" +
                "       SUM(SFFD.DEPRN_COST_13) ALL_QTY13,\n" +
                "       SUM(SFFD.DEPRN_COST_14) ALL_QTY14,\n" +
                "       SUM(SFFD.DEPRN_COST_15) ALL_QTY15,\n" +
                "       SUM(SFFD.DEPRN_COST_16) ALL_QTY16,\n" +
                "       SUM(SFFD.DEPRN_COST_17) ALL_QTY17,\n" +
                "       SUM(SFFD.DEPRN_COST_18) ALL_QTY18,\n" +
                "       SUM(SFFD.DEPRN_COST_19) ALL_QTY19,\n" +
                "       SUM(SFFD.DEPRN_COST_20) ALL_QTY20,\n" +
                "       SUM(SFFD.DEPRN_COST_21) ALL_QTY21,\n" +
                "       SUM(SFFD.DEPRN_COST_22) ALL_QTY22,\n" +
                "       SUM(SFFD.DEPRN_COST_23) ALL_QTY23,\n" +
                "       SUM(SFFD.DEPRN_COST_24) ALL_QTY24,\n" +
                "       SUM(SFFD.DEPRN_COST_25) ALL_QTY25,\n" +
                "       SUM(SFFD.DEPRN_COST_26) ALL_QTY26,\n" +
                "       SUM(SFFD.DEPRN_COST_27) ALL_QTY27,\n" +
                "       SUM(SFFD.DEPRN_COST_28) ALL_QTY28,\n" +
                "       SUM(SFFD.DEPRN_COST_29) ALL_QTY29,\n" +
                "       SUM(SFFD.DEPRN_COST_30) ALL_QTY30,\n" +
                "       SUM(SFFD.DEPRN_COST_31) ALL_QTY31,\n" +
                "       SUM(SFFD.DEPRN_COST_32) ALL_QTY32,\n" +
                "       SUM(SFFD.DEPRN_COST_33) ALL_QTY33,\n" +
                "       SUM(SFFD.DEPRN_COST_34) ALL_QTY34,\n" +
                "       SUM(SFFD.DEPRN_COST_35) ALL_QTY35,\n" +
                "       SUM(SFFD.DEPRN_COST_36) ALL_QTY36,\n" +
                "       SUM(SFFD.DEPRN_COST_37) ALL_QTY37,\n" +
                "       SUM(SFFD.DEPRN_COST_38) ALL_QTY38,\n" +
                "       SUM(SFFD.DEPRN_COST_39) ALL_QTY39,\n" +
                "       SUM(SFFD.DEPRN_COST_40) ALL_QTY40,\n" +
                "       SUM(SFFD.DEPRN_COST_41) ALL_QTY41,\n" +
                "       SUM(SFFD.DEPRN_COST_42) ALL_QTY42,\n" +
                "       SUM(SFFD.DEPRN_COST_43) ALL_QTY43,\n" +
                "       SUM(SFFD.DEPRN_COST_44) ALL_QTY44,\n" +
                "       SUM(SFFD.DEPRN_COST_45) ALL_QTY45,\n" +
                "       SUM(SFFD.DEPRN_COST_46) ALL_QTY46,\n" +
                "       SUM(SFFD.DEPRN_COST_47) ALL_QTY47,\n" +
                "       SUM(SFFD.DEPRN_COST_48) ALL_QTY48,\n" +
                "       SUM(SFFD.DEPRN_COST_49) ALL_QTY49,\n" +
                "       SUM(SFFD.DEPRN_COST_50) ALL_QTY50,\n" +
                "       SUM(SFFD.DEPRN_COST_51) ALL_QTY51,\n" +
                "       SUM(SFFD.DEPRN_COST_52) ALL_QTY52,\n" +
                "       SUM(SFFD.DEPRN_COST_53) ALL_QTY53,\n" +
                "       SUM(SFFD.DEPRN_COST_54) ALL_QTY54,\n" +
                "       SUM(SFFD.DEPRN_COST_55) ALL_QTY55,\n" +
                "       SUM(SFFD.DEPRN_COST_56) ALL_QTY56,\n" +
                "       SUM(SFFD.DEPRN_COST_57) ALL_QTY57,\n" +
                "       SUM(SFFD.DEPRN_COST_58) ALL_QTY58,\n" +
                "       SUM(SFFD.DEPRN_COST_59) ALL_QTY59,\n" +
                "       SUM(SFFD.DEPRN_COST_60) ALL_QTY60\n" +
                "FROM   SINO_FA_FORECAST_D    SFFD,\n" +
                "       SINO_FA_FORECAST_HEAD SFFH\n" +
                "WHERE  SFFD.BOOK_TYPE_CODE = SFFH.BOOK_TYPE_CODE\n" +
                "       AND SFFH.COMPLETE_DATE > GETDATE() - 30 " +
                "       AND SFFD.BOOK_TYPE_CODE = dbo.NVL(?, SFFD.BOOK_TYPE_CODE)\n" +
                "       AND SFFD.CAT_SEGMENT2 LIKE dbo.NVL(?, SFFD.CAT_SEGMENT2)\n" +
                "GROUP  BY SFFD.CAT_SEGMENT1";
        sqlArgs.add(dto.getBookTypeCode());
        sqlArgs.add(dto.getCatSegment2());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}

