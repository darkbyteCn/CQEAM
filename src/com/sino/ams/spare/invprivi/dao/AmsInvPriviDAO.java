package com.sino.ams.spare.invprivi.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.ReflectException;
import com.sino.base.log.Logger;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.spare.invprivi.dto.AmsInvPriviDTO;
import com.sino.ams.spare.invprivi.model.AmsInvPriviModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: AmsInvPriviDAO</p>
 * <p>Description:程序自动生成服务程序“AmsInvPriviDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsInvPriviDAO extends BaseDAO {

    private static List privis = new ArrayList();

    static {
        privis.add("INV_IN");
        privis.add("INV_OUT");
        privis.add("INV_QUERY");
      /*privis.add("INV_APPLY");
        privis.add("INV_BAD_IN");
        privis.add("INV_BAD_RETURN");
        privis.add("INV_DISCARD");
        privis.add("INV_NEW_IN");
        privis.add("INV_ORDER_PRINT");
        privis.add("INV_OUT");
        privis.add("INV_QUERY");
        privis.add("INV_RCV_IN");
        privis.add("INV_REPAIR_IN");
        privis.add("INV_SEND_REPAIR");
        privis.add("INV_TRANSFER");*/
    }

    private SfUserDTO sfUser = null;

    /**
     * 功能：仓库权限表(AMS) AMS_INV_PRIVI 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsInvPriviDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AmsInvPriviDAO(SfUserDTO userAccount, AmsInvPriviDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsInvPriviDTO dtoPara = (AmsInvPriviDTO) dtoParameter;
        super.sqlProducer = new AmsInvPriviModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：插入仓库权限表(AMS)表“AMS_INV_PRIVI”数据。
     */
    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("仓库权限表(AMS)");

    }

    /**
     * 功能：更新仓库权限表(AMS)表“AMS_INV_PRIVI”数据。
     */
    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("仓库权限表(AMS)");

    }

    /**
     * 功能：删除仓库权限表(AMS)表“AMS_INV_PRIVI”数据。
     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("仓库权限表(AMS)");

    }

    public boolean savePrivis(DTOSet argPrivis) throws DataHandleException {
        boolean operateResult = false;
        boolean autoCommit = false;
        boolean hasError = true;
        try {
            if (argPrivis != null && !argPrivis.isEmpty()) {
                autoCommit = conn.getAutoCommit();
                conn.setAutoCommit(false);
                int dtoCount = argPrivis.getSize();
                AmsInvPriviDTO argDTO = null;
                String actionCode = "";
                for (int i = 0; i < dtoCount; i++) {
                    argDTO = (AmsInvPriviDTO) argPrivis.getDTO(i);
                    setDTOParameter(argDTO);
                    deleteData();
                    for (int j = 0; j < privis.size(); j++) {
                        actionCode = (String) privis.get(j);
                        if (argDTO.hasPrivi(actionCode)) {
                            AmsInvPriviDTO dto = new AmsInvPriviDTO();
                            dto.setUserId(argDTO.getUserId());
                            dto.setInvCode(argDTO.getInvCode());
                            dto.setActionCode(actionCode);
                            setDTOParameter(dto);
                            createData();
                        }
                    }
                }
                conn.commit();
                hasError = false;
                operateResult = true;
                //    prodMessage(CustMessageKey.SAVE_PRIVI_SUCCESS);
            }
        } catch (ReflectException ex) {
            ex.printLog();
        } catch (SQLException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (hasError) {
                    conn.rollback();
                    prodMessage(CustMessageKey.SAVE_PRIVI_FAILURE);
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
            }
        }
        return operateResult;
    }
}
