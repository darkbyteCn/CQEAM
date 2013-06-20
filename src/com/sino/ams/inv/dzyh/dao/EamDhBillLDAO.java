package com.sino.ams.inv.dzyh.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.inv.dzyh.dto.EamDhBillLDTO;
import com.sino.ams.inv.dzyh.model.EamDhBillLModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.pda.inv.model.InvOperateModel;

/**
 * <p>Title: EamDhBillLDAO</p>
 * <p>Description:程序自动生成服务程序“EamDhBillLDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 于士博
 * @version 1.0
 */
public class EamDhBillLDAO extends AMSBaseDAO {

    EamDhBillLModel eamDhBillLModel = null;

    /**
     * 功能：低值易耗品目录维护表(EAM) EAM_DH_BILL_L 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EamDhBillLDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public EamDhBillLDAO(SfUserDTO userAccount, EamDhBillLDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        eamDhBillLModel = (EamDhBillLModel) sqlProducer;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EamDhBillLDTO dtoPara = (EamDhBillLDTO) dtoParameter;
        super.sqlProducer = new EamDhBillLModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：更新ETS_ITEM_INFO表中的MAINTAIN_USER字段
     */
    public void updateData() throws DataHandleException {
        super.updateData();
    }

    /**
     * 功能：生成单据号
     */
    public String createBillNo() throws DataHandleException {
        boolean autoCommit = true;
        DataHandleException error = null;
        boolean operateResult = false;

        String transactionNo = "";

        try {
            autoCommit = conn.getAutoCommit();

            conn.setAutoCommit(false);

            InvOperateModel iom = new InvOperateModel();

            transactionNo = iom.getAssetNumber(conn, userAccount.getCompanyCode(), "INV-O", 1); //单据编号

            if (!("".equals(transactionNo) || transactionNo == null)) {
                operateResult = true;
            }
        } catch (SQLException ex) {
            Logger.logError(ex);
            error = new DataHandleException(ex);
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
                if (!operateResult) {
                    throw error;
                }
            } catch (SQLException ex) {
                Logger.logError(ex);
                throw new DataHandleException(ex);
            }
        }
        return transactionNo;
    }


    /**
     * 功能：生成行表信息所需要的trans_id字段
     */
    public int createTransId(String transactionNo, String workorderObjectCode) throws DataHandleException {
        boolean autoCommit = true;
        DataHandleException error = null;
        boolean operateResult = false;

        int trans_id = 0;

        try {
            autoCommit = conn.getAutoCommit();

            conn.setAutoCommit(false);

            trans_id = eamDhBillLModel.createInvBillHeaderOut(
                    conn, transactionNo, workorderObjectCode,
                    "库存出库", "低值易耗",
                    StrUtil.strToInt(String.valueOf(userAccount.getUserId())));  //生成行信息所需要的  ..号

            if (trans_id != 0) {
                operateResult = true;
            }
        } catch (SQLException ex) {
            Logger.logError(ex);
            error = new DataHandleException(ex);
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
                if (!operateResult) {
                    throw error;
                }
            } catch (SQLException ex) {
                Logger.logError(ex);
                throw new DataHandleException(ex);
            }
        }
        return trans_id;
    }


    /* int k = 0;
    sqlStr = "BEGIN ? := AMS_ORDERNO_PKG.GET_ORDER_NO(?, ?, ?); END;";
    cst = conn.prepareCall(sqlStr);

    cst.registerOutParameter(k++, Types.INTEGER);
    cst.setString(k++, companyCode);
    cst.setString(k++, "INV-O");
    cst.setInt(k++, 4);

    cst.registerOutParameter(k++, Types.VARCHAR);
    cst.execute();


    InvOperateModel iom = new InvOperateModel();
    String transactionNo = iom.getAssetNumber(conn, userAccount.getCompanyCode(), "INV-O", 1); //单据编号

    int bill_id = eamDhBillLModel.createInvBillHeaderOut(
            conn, transactionNo, workorderObjectCode,
            "库存出库", "低值易耗",
            StrUtil.strToInt( userAccount.getUserId())
            );  //生成行信息所需要的  ..号

    */

    /**
     * 功能：批量更新领用出库记录(EAM)表"EAM_ITEM_INFO"数据。
     */
    public void updateDatas(String systemid, String catalogValueId2,
                            String barcode, String deptCode, String employeeId,
                            String addressId, String maintainUser, int trans_id,
                            String itemCode, String itemCategory, String itemCategory2)
            throws DataHandleException {


        boolean autoCommit = true;
        DataHandleException error = null;
        boolean operateResult = false;

        try {
            autoCommit = conn.getAutoCommit();

            conn.setAutoCommit(false);

            int success = 0;

            if (trans_id > 0) {

                success = eamDhBillLModel.createInvBillLineOut(conn, trans_id, barcode, itemCode, itemCategory, itemCategory2, deptCode, employeeId, maintainUser, addressId);
            }

            if (success > 0) {
                operateResult = true;
            }
        } catch (SQLException ex) {
            Logger.logError(ex);
            error = new DataHandleException(ex);
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
                if (!operateResult) {
                    throw error;
                }
            } catch (SQLException ex) {
                Logger.logError(ex);
                throw new DataHandleException(ex);
            }
        }
    }


    /**
     * 功能：更新ETS_ITEM_INFO表中的RESPONSIBILITY_DEPT字段。
     */
    public void updateResponsibilityDeptData(String barcode, String newDept) throws DataHandleException {
        boolean operateResult = false;
        SQLModel sqlModel = null;
        sqlModel = eamDhBillLModel.getDataUpdateDeptModel(barcode, newDept);

        if (sqlModel != null && !sqlModel.isEmpty()) {
            DBOperator.updateRecord(sqlModel, conn);
            operateResult = true;
        }
    }

    /**
     * 功能：更新ETS_ITEM_INFO表中的RESPONSIBILITY_USER字段。
     */
    public void updateResponsibilityUserData(String barcode, String responsibilityUser) throws DataHandleException {
        boolean operateResult = false;
        SQLModel sqlModel = null;
        sqlModel = eamDhBillLModel.getDataUpdateUserModel(barcode, responsibilityUser);
        if (sqlModel != null && !sqlModel.isEmpty()) {
            DBOperator.updateRecord(sqlModel, conn);
            operateResult = true;
        }
    }

    /**
     * 功能：更新ETS_ITEM_INFO表中的WORKORDER_OBJECT_NO字段。
     */
    public void updateAddressIdData(String barcode, String addressId) throws DataHandleException {
        boolean operateResult = false;
        SQLModel sqlModel = null;
        sqlModel = eamDhBillLModel.getDataUpdateAddressIdModel(barcode, addressId);
        if (sqlModel != null && !sqlModel.isEmpty()) {
            DBOperator.updateRecord(sqlModel, conn);
            operateResult = true;
        }
    }

    /**
     * 功能：更新ETS_ITEM_INFO表中的LAST_LOC_CHG_DATE字段，这个字段是日期类型，需要注意，传进来的参数是String类型的。
     * @throws CalendarException
     */
    public void updateLastLocChgDateData(String barcode, String lastLocChgDate) throws DataHandleException, CalendarException {
        boolean operateResult = false;
        SQLModel sqlModel = null;
        sqlModel = eamDhBillLModel.getDataUpdateLastLocChgDateModel(barcode, lastLocChgDate);
        if (sqlModel != null && !sqlModel.isEmpty()) {
            DBOperator.updateRecord(sqlModel, conn);
            operateResult = true;
        }
    }

    /**
     * 功能：更新ETS_ITEM_INFO表中的MAINTAIN_USER字段，这个字段是日期类型，需要注意，传进来的参数是String类型的。
     */
    public void updateMaintainUserDateData(String barcode, String maintainUser) throws DataHandleException {
        boolean operateResult = false;
        SQLModel sqlModel = null;
        sqlModel = eamDhBillLModel.getDataUpdateMaintainUserModel(barcode, maintainUser);
        if (sqlModel != null && !sqlModel.isEmpty()) {
            DBOperator.updateRecord(sqlModel, conn);
            operateResult = true;
        }
    }
}
