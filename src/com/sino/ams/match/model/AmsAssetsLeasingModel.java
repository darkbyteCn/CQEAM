package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-17
 * Time: 22:17:44
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsLeasingModel extends AMSSQLProducer {
    public AmsAssetsLeasingModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsItemInfoDTO dto = (EtsItemInfoDTO) dtoParameter;
        String sqlStr = "SELECT EII.BARCODE,\n" +
                "       EII.SYSTEMID,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ARI.RENT_PERSON,\n" +
                "       ARI.RENT_DATE,\n" +
                "       ARI.END_DATE\n" +
                "  FROM ETS_ITEM_INFO EII, ETS_SYSTEM_ITEM ESI, AMS_RENT_INFO ARI\n" +
                " WHERE EII.ATTRIBUTE1 = 'RENT'\n" +
                "   AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EII.BARCODE *= ARI.BARCODE \n" +
                "   AND EII.ORGANIZATION_ID = ?\n" +
                "   AND (" + SyBaseSQLUtil.nullStringParam() + " OR EII.BARCODE LIKE ? )\n" +
                "   AND  (" + SyBaseSQLUtil.nullStringParam() + " OR  ESI.ITEM_NAME LIKE ? )\n" +
                "   AND  (" + SyBaseSQLUtil.nullStringParam() + " OR ESI.ITEM_SPEC LIKE ? )";
        sqlArgs.add(userAccount.getOrganizationId());
        
        SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getBarcode() );
        SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemName() );
        SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemSpec() );
//        sqlArgs.add(dto.getItemName());
//        sqlArgs.add(dto.getItemSpec());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}

