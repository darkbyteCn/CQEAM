package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.freeflow.AmsAssetsFreeDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 2009-4-13
 * Time: 16:47:53
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsDiscardModel extends AMSSQLProducer {
    public AmsAssetsDiscardModel(BaseUserDTO userAccount, AmsAssetsFreeDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel=new SQLModel();
        List sqlArgs =new ArrayList();
        AmsAssetsFreeDTO fiDTO= (AmsAssetsFreeDTO) dtoParameter;
        String sqlStr="SELECT eocm.company,\n" +
                "       amd.dept_name,\n" +
                "       esi.item_name,\n" +
                "       esi.item_spec,\n" +
                "       efa.asset_number,\n" +
                "       eii.item_status,\n" +
                "		efa.asset_id,\n"	+
                "       eii.barcode,\n" +
                "		efa.current_units,\n"	+      
                "		efa.cost,\n" +                          
                "       eo.WORKORDER_OBJECT_NAME\n" +
                " FROM  ets_item_info      eii,\n" +
                "       AMS_OBJECT_ADDRESS aoa,\n" +
                "       ets_ou_city_map    eocm,\n" +
                "       ams_mis_dept       amd,\n" +
                "       ets_system_item    esi,\n" +
                "       ets_item_match     eim,\n" +
                "       ets_fa_assets      efa,\n" +
                "       ETS_OBJECT         eo\n" +
                " WHERE " +
                "	eii.organization_id = eocm.organization_id\n" +
                "   and eii.responsibility_dept *= amd.dept_code\n" +
                "   and eii.ADDRESS_ID = aoa.ADDRESS_ID\n" +
                "   and eo.WORKORDER_OBJECT_NO = aoa.OBJECT_NO\n" +
                "   and eii.item_code = esi.item_code\n" +
                "   and eii.systemid = eim.systemid\n" +
                "   and eim.asset_id = efa.asset_id\n" +
                "   and eii.finance_prop = 'ASSETS'\n" +
                "   and ( " + SyBaseSQLUtil.isNull() + "  or eii.BARCODE like ?)\n" +
                "   and ( " + SyBaseSQLUtil.isNull() + "  or esi.item_name like ?)\n" +
                "   and ( " + SyBaseSQLUtil.isNull() + "  or eo.WORKORDER_OBJECT_NAME like ?)\n"	+
        		"   and ( " + SyBaseSQLUtil.isNull() + "  or esi.item_spec like ?)";	
        
        sqlStr += "   and eii.ORGANIZATION_ID = ?";
        sqlArgs.add(fiDTO.getBarcode());
        sqlArgs.add(fiDTO.getBarcode());
        sqlArgs.add(fiDTO.getItemName());
        sqlArgs.add(fiDTO.getItemName());
        sqlArgs.add(fiDTO.getWorkorderObjectName());
        sqlArgs.add(fiDTO.getWorkorderObjectName());
        sqlArgs.add(fiDTO.getItemSpec());
        sqlArgs.add(fiDTO.getItemSpec());
        sqlArgs.add(userAccount.getOrganizationId()) ;
        
    	if(!StrUtil.isEmpty(fiDTO.getItemStatus())){
    		
    		sqlStr += "AND eii.item_status  = dbo.NVL(?, eii.item_status)";
    		sqlArgs.add(fiDTO.getItemStatus());
    	}else{
    		sqlStr += "AND (eii.item_status = 'DISCARDED' or eii.item_status ='TO_DISCARD')";
    		
    	}
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

}
