package com.sino.ams.newasset.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.model.AssetsAssignModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AssetsAssignDAO extends AMSBaseDAO {


    public AssetsAssignDAO(SfUserDTO userAccount,
                           AmsAssetsAddressVDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
     * @param userAccount BaseUserDTO
     * @param dtoParameter DTO
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        sqlProducer = new AssetsAssignModel((SfUserDTO) userAccount, dto);
    }

    /**
     * 功能：进行资产的分配。
     * @param assignDatas DTOSet
     * @return boolean
     */
    public boolean assignAssets(DTOSet assignDatas) {
        boolean operateResult = false;
        boolean autoCommit = true;
        AmsAssetsAddressVDTO dto = null;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            int assetsCount = assignDatas.getSize();
            AssetsAssignModel modelProducer = (AssetsAssignModel) sqlProducer;
            SQLModel sqlModel = null;
            for (int i = 0; i < assetsCount; i++) {
                dto = (AmsAssetsAddressVDTO) assignDatas.getDTO(i);
                modelProducer.setDTOParameter(dto);
                sqlModel = modelProducer.getAssetsAssignModel();
                DBOperator.updateRecord(sqlModel, conn); //分配资产
            }
            operateResult = true;
        } catch (DataHandleException ex) {
            ex.printLog();
        } catch (SQLModelException ex) {
            ex.printLog();
        } catch (SQLException ex) {
            Logger.logError(ex);
        } finally {
            String assProp = dto.getAssProp();
            String paraValue = "";
            if (assProp.equals(AssetsWebAttributes.ASSIGN_COST_CENTER)) {
                paraValue = "部门";
            } else if (assProp.equals(AssetsWebAttributes.
                                      ASSIGN_RESPONSIBLE_USER)) {
                paraValue = "责任人";
            } else if (assProp.equals(AssetsWebAttributes.ASSIGN_MAINTAIN_USER)) {
                paraValue = "使用人";
            }
            try {
                if (!operateResult) {
                    conn.rollback();
                    prodMessage(AssetsMessageKeys.ASN_ASSETS_FAILURE);
                } else {
                    conn.commit();
                    prodMessage(AssetsMessageKeys.ASN_ASSETS_SUCCESS);
                }
                message.addParameterValue(paraValue);
                message.setIsError(!operateResult);
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }
}
