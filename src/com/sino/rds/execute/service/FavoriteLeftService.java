package com.sino.rds.execute.service;

import com.sino.base.dto.DTO;
import com.sino.base.log.Logger;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.execute.dao.FavoriteHeaderDAO;
import com.sino.rds.execute.dao.FavoriteLeftDAO;
import com.sino.rds.execute.dao.FavoriteLineDAO;
import com.sino.rds.share.form.FavoriteHeaderFrm;
import com.sino.rds.share.form.FavoriteLineFrm;

import java.sql.Connection;

public class FavoriteLeftService extends RDSBaseService {

    public FavoriteLeftService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        FavoriteLeftDAO leftDAO = new FavoriteLeftDAO(userAccount, dtoParameter, conn);
        setPrimaryDAO(leftDAO);
    }

    public boolean deleteByPrimaryKey() {
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            deleteFavoriteLine();
            deleteFavoriteHeader();
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

    private void deleteFavoriteHeader() throws DataHandleException {
        FavoriteHeaderFrm frm = (FavoriteHeaderFrm) dtoParameter;
        FavoriteHeaderDAO headerDAO = new FavoriteHeaderDAO(userAccount, frm, conn);
        headerDAO.deleteByPrimaryKey();
    }

    private void deleteFavoriteLine() throws DataHandleException {
        FavoriteHeaderFrm frm = (FavoriteHeaderFrm) dtoParameter;
        FavoriteLineFrm lineFrm = new FavoriteLineFrm();
        lineFrm.setHeaderId(frm.getHeaderId());
        FavoriteLineDAO lineDAO = new FavoriteLineDAO(userAccount, lineFrm, conn);
        lineDAO.DeleteByForeignKey("headerId");
    }
}