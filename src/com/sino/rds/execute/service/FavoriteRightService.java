package com.sino.rds.execute.service;

import com.sino.base.dto.DTO;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.execute.dao.FavoriteLineDAO;
import com.sino.rds.execute.dao.FavoriteRightDAO;
import com.sino.rds.share.form.FavoriteLineFrm;

import java.sql.Connection;

public class FavoriteRightService extends RDSBaseService {

    public FavoriteRightService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        FavoriteRightDAO rightDAO = new FavoriteRightDAO(userAccount, dtoParameter, conn);
        setPrimaryDAO(rightDAO);
    }

    public boolean deleteByLinesIds(String[] lineIds) {
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            FavoriteLineFrm lineFrm = new FavoriteLineFrm();
            FavoriteLineDAO lineDAO = new FavoriteLineDAO(userAccount, null, conn);
            for (String lineId : lineIds) {
                lineFrm.setLineId(lineId);
                lineDAO.setDTOParameter(lineFrm);
                lineDAO.deleteByPrimaryKey();
            }
            operateResult = true;
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            } catch (Throwable ex) {
                Logger.logError(ex);
            }
        }
        return operateResult;
    }

}