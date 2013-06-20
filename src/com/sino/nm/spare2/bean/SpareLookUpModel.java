package com.sino.nm.spare2.bean;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.lookup.LookUpModel;
import com.sino.base.lookup.LookUpProp;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.nm.spare2.dto.AmsItemTransLDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-3-26
 */
public class SpareLookUpModel extends LookUpModel {
    private SfUserDTO user = null;

    /**
     * 功能：构造函数
     * @param userAccount  BaseUserDTO
     * @param dtoParameter DTO
     * @param lookProp     LookUpProp
     */
    public SpareLookUpModel(BaseUserDTO userAccount, DTO dtoParameter, LookUpProp lookProp) {
        super(userAccount, dtoParameter, lookProp);
        this.user = (SfUserDTO) userAccount;
    }

    protected void produceSQLModel() {
        sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        String lookUpName = lookProp.getLookUpName();
        if (lookUpName.equals(SpareLookUpConstant.BJSXFH_ITEM_INFO)) { //备件送修返还查询设备(NM) HERRY 2008-3-26
            AmsItemTransLDTO dto = (AmsItemTransLDTO) dtoParameter;
            sqlStr = "SELECT ESI.MIS_ITEM_CODE BARCODE,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       AITH.TRANS_NO BATCH_NO,\n" +
                    "       AITL.QUANTITY REPAIR_QUANTITY,\n" +
                    "       AITL.RETURNED_QUANTITY\n" +
                    "  FROM AMS_ITEM_TRANS_H AITH, AMS_ITEM_TRANS_L AITL, ETS_SYSTEM_ITEM ESI\n" +
                    " WHERE AITH.TRANS_ID = AITL.TRANS_ID\n" +
                    "   AND AITL.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "   AND AITH.TRANS_TYPE = '" + DictConstant.BJSX + "'\n" +
                    "   AND AITH.FROM_OBJECT_NO = ?\n" +
                    "   AND ESI.MIS_ITEM_CODE LIKE dbo.NVL(?, ESI.MIS_ITEM_CODE)\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND (? IS NULL OR ESI.ITEM_SPEC LIKE ?) ";

            sqlArgs.add(dto.getObjectNo());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());

        } else if (lookUpName.equals(SpareLookUpConstant.BJCK_SPARE_INFO)) { //备件出库查找设备(NM) HERRY 2008-3-27
            AmsItemTransLDTO dto = (AmsItemTransLDTO) dtoParameter;
            sqlStr = "SELECT ASI.BARCODE,\n" +
                    "       dbo.AMS_INV_TRANS2_GET_AVAILABLE_QTY(ESI.ITEM_CODE,\n" +
                    "                                        ASI.OBJECT_NO) ONHAND_QTY,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC\n" +
                    "  FROM AMS_SPARE_INFO ASI, ETS_SYSTEM_ITEM ESI\n" +
                    " WHERE ASI.BARCODE = ESI.MIS_ITEM_CODE\n" +
                    "   AND ASI.OBJECT_NO = ?\n" +
                    "   AND ESI.MIS_ITEM_CODE LIKE dbo.NVL(?, ESI.MIS_ITEM_CODE)\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                   "   AND (ESI.ITEM_SPEC LIKE ? OR "+ SyBaseSQLUtil.nullStringParam() + ") ";


            sqlArgs.add(dto.getObjectNo());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
        } else if (lookUpName.equals(SpareLookUpConstant.BJBF_SPARE_INFO_DX)) { //备件报废查找设备(NM)从待修数量报废 HERRY 2008-7-1
            AmsItemTransLDTO dto = (AmsItemTransLDTO) dtoParameter;
            sqlStr = "SELECT ASI.BARCODE,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       ASI.OBJECT_NO STORAGE_ID,\n" +
                    "       ASI.SPARE_ID,\n" +
                    "       ASI.DISREPAIR_QUANTITY ONHAND_QTY\n" +
                    "  FROM AMS_SPARE_INFO ASI, ETS_SYSTEM_ITEM ESI\n" +
                    " WHERE ASI.BARCODE = ESI.MIS_ITEM_CODE\n" +
                    "   AND ASI.DISREPAIR_QUANTITY > 0\n" +
                    "   AND ASI.OBJECT_NO = ISNULL(?, ASI.OBJECT_NO)\n" +
                    "   AND ESI.MIS_ITEM_CODE LIKE ISNULL(?, ESI.MIS_ITEM_CODE)\n" +
                    "   AND ESI.ITEM_NAME LIKE ISNULL(?, ESI.ITEM_NAME)\n" +
                    "   AND (? IS NULL OR ESI.ITEM_SPEC LIKE ?) " +
                    "   AND ASI.ORGANIZATION_ID = ?\n";

            sqlArgs.add(dto.getObjectNo());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(user.getOrganizationId());
        }else if (lookUpName.equals(SpareLookUpConstant.BJBF_SPARE_INFO_SX)) { //备件报废查找设备(NM)从送修数量报废 HERRY 2008-7-3
            AmsItemTransLDTO dto = (AmsItemTransLDTO) dtoParameter;
            sqlStr = "SELECT ESI.MIS_ITEM_CODE BARCODE,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       AITH.FROM_OBJECT_NO STORAGE_ID,\n" +
                    "       AITL.QUANTITY - AITL.RETURNED_QUANTITY ONHAND_QTY,\n" +
                    "       AITL.LINE_ID SOURCE_ID\n" + //送修单的行ID
                    "  FROM ETS_SYSTEM_ITEM ESI, AMS_ITEM_TRANS_H AITH, AMS_ITEM_TRANS_L AITL\n" +
                    " WHERE AITL.BARCODE = ESI.MIS_ITEM_CODE\n" +
                    "   AND AITH.TRANS_ID = AITL.TRANS_ID\n" +
                    "   AND AITH.TRANS_TYPE = 'BJSX'\n" +
                    "   AND AITL.RETURNED_QUANTITY < AITL.QUANTITY\n" +
                    "   AND AITH.FROM_OBJECT_NO = dbo.NVL(?, AITH.FROM_OBJECT_NO)\n" +
                    "   AND ESI.MIS_ITEM_CODE LIKE dbo.NVL(?, ESI.MIS_ITEM_CODE)\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND (? IS NULL OR ESI.ITEM_SPEC LIKE ?) " +
                    "   AND AITH.FROM_ORGANIZATION_ID = ?\n";

            sqlArgs.add(dto.getObjectNo());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(user.getOrganizationId());
        } else if (lookUpName.equals(SpareLookUpConstant.OBJECT_NO)) {      //备件仓库
			EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
			sqlStr = "SELECT EO.WORKORDER_OBJECT_CODE,\n" +
					"       EO.WORKORDER_OBJECT_NO,\n" +
					"       EO.WORKORDER_OBJECT_NAME,\n" +
					"       EO.WORKORDER_OBJECT_LOCATION\n" +
					"  FROM ETS_OBJECT EO\n" +
					" WHERE (?='' OR EO.WORKORDER_OBJECT_NAME LIKE ?)\n" +
					"   AND (?='' OR EO.WORKORDER_OBJECT_CODE LIKE ?)" +
					"   AND (?='' OR EO.WORKORDER_OBJECT_LOCATION LIKE  ?)" +
					"   AND  EO.DISABLE_DATE IS NULL"+    //该地点没有被失效
					"   AND (EO.WORKORDER_OBJECT_CODE LIKE '%BJ%' OR EO.BUSINESS_CATEGORY ='INV_BIZ_SPARE')" + 
					"   AND EO.ORGANIZATION_ID = ?";
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getWorkorderObjectCode());
			sqlArgs.add(dto.getWorkorderObjectCode());
			sqlArgs.add(dto.getWorkorderObjectLocation());
			sqlArgs.add(dto.getWorkorderObjectLocation());
			sqlArgs.add(user.getOrganizationId());
		}else if (lookUpName.equals(SpareLookUpConstant.OU_OBJECT)) {      //备件仓库和OU
			EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
			sqlStr = "SELECT EO.WORKORDER_OBJECT_NO,\n" +
					"       EO.WORKORDER_OBJECT_NAME,\n" +
					"       EO.WORKORDER_OBJECT_LOCATION,\n" +
					"       EO.ORGANIZATION_ID,\n" +
					"       EOCM.COMPANY\n" +
					"  FROM ETS_OBJECT EO, ETS_OU_CITY_MAP EOCM\n" +
					" WHERE EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
					"   AND EO.WORKORDER_OBJECT_CODE LIKE '%BJ%'\n" +
					"   AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)\n" +
					"   AND EOCM.COMPANY LIKE dbo.NVL(?, EOCM.COMPANY)\n";
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getCountyName());  //这里表示COMPANY
		}

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
    }
}
