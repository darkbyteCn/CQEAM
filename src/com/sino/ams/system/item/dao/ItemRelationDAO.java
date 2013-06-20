package com.sino.ams.system.item.dao;

import java.sql.Connection;

import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.item.model.ItemRelationModel;
import com.sino.ams.system.item.model.SetSubItemsModel;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2007-11-22
 */
public class ItemRelationDAO extends BaseDAO {
    /**
     * 功能：构造函数。
     * @param userAccount  UserAccountDTO 用户信息
     * @param dtoParameter DTO 其他与数据库交互时需要的参数。
     * @param conn         Connection 数据库连接
     */
    public ItemRelationDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        super.sqlProducer = new ItemRelationModel(userAccount, dtoParameter);
    }

    /**
     * 根据barcode查询设备名称和型号
     * @return EtsItemInfoDTO
     * @throws QueryException
     */
    public EtsItemInfoDTO getItemInfoByBarcode() throws QueryException {
        SimpleQuery sq = new SimpleQuery(((ItemRelationModel) sqlProducer).getItemInfoByBarcodeModel(), conn);
        sq.setDTOClassName(EtsItemInfoDTO.class.getName());
        sq.executeQuery();
        return (EtsItemInfoDTO) sq.getFirstDTO();
    }

    /**
     * 给父设备增加子设备
     * @param barcodes 子标签号
     * @return success
     */
    public boolean addSubItems(String[] barcodes) {
        return updateSubItems(barcodes, "ADD");
    }

    /**
     * 给父设备删除子设备
     * @param barcodes 子标签号
     * @return success
     */
    public boolean deleteSubItems(String[] barcodes) {
        return updateSubItems(barcodes, "DELETE");
    }

    /**
     * 更新条码的 PARENT_BARCODE 字段
     * @param barcodes 子标签号
     * @param flag     标记,是增加还是删除
     * @return success
     */
    private boolean updateSubItems(String[] barcodes, String flag) {
        boolean success = false;
        SetSubItemsModel model ;
        EtsItemInfoDTO itemInfo = new EtsItemInfoDTO();
        try {
            for (int i = 0; i < barcodes.length; i++) {
                itemInfo.setBarcode(barcodes[i]);
                if (flag.equals("ADD")) {
                    itemInfo.setParentBarcode(((EtsItemInfoDTO) dtoParameter).getParentBarcode());
                }
                model = new SetSubItemsModel(userAccount, itemInfo);
                DBOperator.updateRecord(model.getDataUpdateModel(), conn);
            }
            success = true;
        } catch (DataHandleException e) {
            e.printLog();
            prodMessage("SAVE_SUCCESS");
        }
        return success;
    }
}
