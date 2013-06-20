package com.sino.ams.newasset.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsAssetsCheckLineModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsCheckLineModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class ChkLineUpLoadModel extends AMSSQLProducer {

    /**
     * 功能：资产盘点行表--待盘点资产表(EAM) AMS_ASSETS_CHECK_LINE 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsCheckLineDTO 本次操作的数据
     */
    public ChkLineUpLoadModel(BaseUserDTO userAccount, AmsAssetsCheckLineDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：获取工单行设备上载处理SQL
     * @param itemExist boolean
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel getLineUploadModel(boolean itemExist) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            String sqlStr = "";
            AmsAssetsCheckLineDTO dto = (AmsAssetsCheckLineDTO) dtoParameter;
            if (itemExist) {
                sqlStr = "UPDATE "
                        + " AMS_ASSETS_CHECK_LINE"
                        + " SET"
                        + " SCAN_STATUS = ?,"
                        + " SCAN_ITEM_CODE = ?,"
                        + " SCAN_ITEM_CATEGORY = ?,"
                        + " SCAN_ITEM_NAME = ?,"
                        + " SCAN_ITEM_SPEC = ?,"
                        + " SCAN_RESPONSIBILITY_USER = ?,"
                        + " SCAN_RESPONSIBILITY_DEPT = ?,"
                        + " SCAN_ORGANIZATION_ID = ?,"
                        + " SCAN_MAINTAIN_USER = ?,"
                        + " SCAN_START_DATE = ?,"
                        + " REMARK = ?,"
                        + " MANUFACTURER_ID = ?,"
                        + " IS_SHARE = ?,"
                        + " CONTENT_CODE = ?,"
                        + " CONTENT_NAME = ?,"
                        + " POWER = ?,"
                        + " CONSTRUCT_STATUS = ?,"
                        + " REPLACE_FLAG = ?,"
                        + " NEW_TAG = ?,"
                        + " LNE_ID = ?,"
                        + " LNE_NAME = ?,"
                        + " CEX_ID = ?,"
                        + " CEX_NAME = ?,"
                        + " OPE_ID = ?,"
                        + " OPE_NAME = ?,"
                        + " NLE_ID = ?,"
                        + " NLE_NAME = ?"
                        + " WHERE"
                        + " HEADER_ID = ?"
                        + " AND BARCODE = ?";
                sqlArgs.add(AssetsDictConstant.STATUS_YES);
                sqlArgs.add(dto.getScanItemCode());
                sqlArgs.add(dto.getScanItemCategory());
                sqlArgs.add(dto.getScanItemName());
                sqlArgs.add(dto.getScanItemSpec());
                sqlArgs.add(dto.getScanResponsibilityUser());
                sqlArgs.add(dto.getScanResponsibilityDept());
                sqlArgs.add(userAccount.getOrganizationId());
                sqlArgs.add(dto.getScanMaintainUser());
                sqlArgs.add(dto.getScanStartDate());
                sqlArgs.add(dto.getRemark());
                sqlArgs.add(dto.getManufacturerId());
                sqlArgs.add(dto.getShare());
                sqlArgs.add(dto.getContentCode());
                sqlArgs.add(dto.getContentName());
                sqlArgs.add(dto.getPower());
                sqlArgs.add(dto.getConstructStatus());
                sqlArgs.add(dto.getReplaceFlag());
                sqlArgs.add(dto.getNewTag());
                sqlArgs.add(dto.getLneId());
                sqlArgs.add(dto.getLneName());
                sqlArgs.add(dto.getCexId());
                sqlArgs.add(dto.getCexName());
                sqlArgs.add(dto.getOpeId());
                sqlArgs.add(dto.getOpeName());
                sqlArgs.add(dto.getNleId());
                sqlArgs.add(dto.getNleName());

                sqlArgs.add(dto.getHeaderId());
                sqlArgs.add(dto.getBarcode());
            } else {
                sqlStr = "INSERT INTO"
                        + " AMS_ASSETS_CHECK_LINE("
                        + " HEADER_ID,"
                        + " BARCODE,"
                        + " SYSTEM_STATUS,"
                        + " SCAN_STATUS,"
                        + " SCAN_ITEM_CODE,"
                        + " SCAN_ITEM_CATEGORY,"
                        + " SCAN_ITEM_NAME,"
                        + " SCAN_ITEM_SPEC,"
                        + " SCAN_RESPONSIBILITY_USER,"
                        + " SCAN_RESPONSIBILITY_DEPT,"
                        + " SCAN_ORGANIZATION_ID,"
                        + " SCAN_MAINTAIN_USER,"
                        + " SCAN_START_DATE,"
                        + " REMARK,"
                        + " MANUFACTURER_ID,"
                        + " IS_SHARE,"
                        + " CONTENT_CODE,"
                        + " CONTENT_NAME,"
                        + " POWER,"
                        + " REPLACE_FLAG,"
                        + " NEW_TAG,"
                        + " CONSTRUCT_STATUS,"
                        + " LNE_ID,"
                        + " LNE_NAME,"
                        + " CEX_ID,"
                        + " CEX_NAME,"
                        + " OPE_ID,"
                        + " OPE_NAME,"
                        + " NLE_ID,"
                        + " NLE_NAME"
                        + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                sqlArgs.add(dto.getHeaderId());
                sqlArgs.add(dto.getBarcode());
                sqlArgs.add(AssetsDictConstant.STATUS_NO);
                sqlArgs.add(AssetsDictConstant.STATUS_YES);
                sqlArgs.add(dto.getScanItemCode());
                sqlArgs.add(dto.getScanItemCategory());
                sqlArgs.add(dto.getScanItemName());
                sqlArgs.add(dto.getScanItemSpec());
                sqlArgs.add(dto.getScanResponsibilityUser());
                sqlArgs.add(dto.getScanResponsibilityDept());
                sqlArgs.add(dto.getScanOrganizationId());
                sqlArgs.add(dto.getScanMaintainUser());
                sqlArgs.add(dto.getScanStartDate());
                sqlArgs.add(dto.getRemark());
                sqlArgs.add(dto.getManufacturerId());
                sqlArgs.add(dto.getShare());
                sqlArgs.add(dto.getContentCode());
                sqlArgs.add(dto.getContentName());
                sqlArgs.add(dto.getPower());
                sqlArgs.add(dto.getReplaceFlag());
                sqlArgs.add(dto.getNewTag());
                sqlArgs.add(dto.getConstructStatus());
                sqlArgs.add(dto.getLneId());
                sqlArgs.add(dto.getLneName());
                sqlArgs.add(dto.getCexId());
                sqlArgs.add(dto.getCexName());
                sqlArgs.add(dto.getOpeId());
                sqlArgs.add(dto.getOpeName());
                sqlArgs.add(dto.getNleId());
                sqlArgs.add(dto.getNleName());
            }
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }


    /**
     * 功能：更新剩余条码为未扫描到信息。
     *
     * @return SQLModel
     */
    public SQLModel getLeftBarcodesUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsCheckLineDTO dto = (AmsAssetsCheckLineDTO) dtoParameter;

        String sqlStr = "UPDATE "
                + " AMS_ASSETS_CHECK_LINE"
                + " SET"
                + " SCAN_STATUS = ?,"
                + " REMARK = ?"
                + " WHERE"
                + " SCAN_STATUS " + SyBaseSQLUtil.isNullNoParam() + " "
                + " AND HEADER_ID = ?";
        sqlArgs.add(AssetsDictConstant.STATUS_NO);
        sqlArgs.add("PDA未扫描到该设备");
        sqlArgs.add(dto.getHeaderId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
