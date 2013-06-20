package com.sino.ams.newasset.dao;

import java.sql.Connection;

import com.sino.base.dto.DTO;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.model.ItemOrderLinePrintModel;
import com.sino.ams.system.user.dto.SfUserDTO;
public class ItemOrderLinePrintDAO extends AMSBaseDAO {

    /**
     * 功能：AMS_ASSETS_TRANS_LINE 数据访问层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsTransLineDTO 本次操作的数据
     * @param conn Connection 数据库连接，由调用者传入。
     */
    public ItemOrderLinePrintDAO(SfUserDTO userAccount, AmsAssetsTransLineDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
        sqlProducer = new ItemOrderLinePrintModel((SfUserDTO) userAccount, dto);
    }
//
//    public void setTransType(String transType) {
//        OrderLinePrintModel modelProducer = (OrderLinePrintModel) sqlProducer;
//        modelProducer.setTransType(transType);
//    }

    /**
     * 功能：设置打印类型：调出打印还是调入打印(仅用于调拨单)
     * @param printType String
     */
    public void setPrintType(String printType) {
        ItemOrderLinePrintModel modelProducer = (ItemOrderLinePrintModel) sqlProducer;
        modelProducer.setPrintType(printType);
    }
}
