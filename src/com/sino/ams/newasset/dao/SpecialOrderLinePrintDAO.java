package com.sino.ams.newasset.dao;


import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.model.OrderLinePrintModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsAssetsTransLineDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsTransLineDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class SpecialOrderLinePrintDAO extends AMSBaseDAO {

    /**
     * 功能：AMS_ASSETS_TRANS_LINE 数据访问层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsTransLineDTO 本次操作的数据
     * @param conn Connection 数据库连接，由调用者传入。
     */
    public SpecialOrderLinePrintDAO(SfUserDTO userAccount, AmsAssetsTransLineDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
        sqlProducer = new OrderLinePrintModel((SfUserDTO) userAccount, dto);
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
        OrderLinePrintModel modelProducer = (OrderLinePrintModel) sqlProducer;
        modelProducer.setPrintType(printType);
    }
}
