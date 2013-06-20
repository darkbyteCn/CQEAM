package com.sino.ams.newasset.model;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.freeflow.AmsAssetsFreeDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;


public class AmsAssetsShareModel extends AMSSQLProducer {
    public AmsAssetsShareModel(BaseUserDTO userAccount, AmsAssetsFreeDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
         SQLModel sqlModel=new SQLModel();
        List sqlArgs =new ArrayList();
        AmsAssetsFreeDTO fiDTO= (AmsAssetsFreeDTO) dtoParameter;
        String sqlStr="select eocm.company,\n" +
                "       amd.dept_name,\n" +
                "       esi.item_name,\n" +
                "       esi.item_spec,\n" +
                "       efa.asset_number,\n" +
                "       eii.barcode,\n" +
                "       efv.value share_status,\n"	+
                "       eo.WORKORDER_OBJECT_NAME\n" +
                "  from ets_item_info      eii,\n" +
                "       AMS_OBJECT_ADDRESS aoa,\n" +
                "       ets_ou_city_map    eocm,\n" +
                "       ams_mis_dept       amd,\n" +
                "       ets_system_item    esi,\n" +
                "       ets_item_match     eim,\n" +
                "       ets_fa_assets      efa,\n" +
                "       ets_flex_values    efv,\n"	+
                "		ETS_FLEX_VALUE_SET EFVS,\n"	+
                "       ETS_OBJECT         eo\n" +
                " where eii.organization_id = eocm.organization_id\n" +
                "   and eii.responsibility_dept *= amd.dept_code\n" +
                "   and eii.ADDRESS_ID = aoa.ADDRESS_ID\n" +
                "   and eo.WORKORDER_OBJECT_NO = aoa.OBJECT_NO\n" +
                "   and eii.item_status = 'NORMAL'\n" +
                "   and eii.item_code = esi.item_code\n" +
                "   and eii.systemid = eim.systemid\n" +
                "   and eim.asset_id = efa.asset_id\n" +
                "   and eii.finance_prop = 'ASSETS'\n" +
                "   and 'SHARE_STATUS' *= EFVS.CODE\n"+
                "	and efv.flex_value_set_id *= EFVS.FLEX_VALUE_SET_ID"	+
                "   and eii.share_status *= efv.code"	+
                "	and eii.is_share = 'Y'" +
                "   and ( " + SyBaseSQLUtil.isNull() + "  or eii.BARCODE like ?)\n" +
                "   and ( " + SyBaseSQLUtil.isNull() + "  or esi.item_name like ?)\n" +
                "   and ( " + SyBaseSQLUtil.isNull() + "  or eo.WORKORDER_OBJECT_NAME like ?)\n" +
                "	and ( " + SyBaseSQLUtil.isNull() + "  or eii.share_status = ?)"	+
                "   and eii.ORGANIZATION_ID = ?";
        sqlArgs.add(fiDTO.getBarcode());
        sqlArgs.add(fiDTO.getBarcode());
        sqlArgs.add(fiDTO.getItemName());
        sqlArgs.add(fiDTO.getItemName());
        sqlArgs.add(fiDTO.getWorkorderObjectName());
        sqlArgs.add(fiDTO.getWorkorderObjectName());
        sqlArgs.add(fiDTO.getShareStatus());
        sqlArgs.add(fiDTO.getShareStatus());
        sqlArgs.add(userAccount.getOrganizationId()) ;        
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
    
    public SQLModel getAllShareStatus(Connection conn) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT EFV.CODE, " +
				"				EFV.VALUE " +
				"		   FROM ETS_FLEX_VALUE_SET EFVS, " +
				"				ETS_FLEX_VALUES EFV " +
				"		  WHERE EFVS.CODE = 'SHARE_STATUS' " +
				"			AND EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
    
    public SQLModel getDataUpdateModel(String shareStatus, String params){
    	SQLModel sqlModel = new SQLModel();
    	List sqlArgs =new ArrayList();
        AmsAssetsFreeDTO fiDTO= (AmsAssetsFreeDTO) dtoParameter;
        String sqlStr = "UPDATE ETS_ITEM_INFO EII " +
        		"				SET EII.SHARE_STATUS = dbo.NVL(?, EII.SHARE_STATUS) " +
        		"		 WHERE EII.BARCODE IN " + params;
        
        sqlArgs.add(shareStatus);
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
    	return sqlModel;
    }

}
