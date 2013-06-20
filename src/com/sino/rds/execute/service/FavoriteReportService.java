package com.sino.rds.execute.service;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.execute.dao.FavoriteHeaderDAO;
import com.sino.rds.execute.dao.FavoriteLineDAO;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebOptions;
import com.sino.rds.share.form.FavoriteHeaderFrm;
import com.sino.rds.share.form.FavoriteLineFrm;
import com.sino.rds.share.util.RDSOptionCreateService;

import java.sql.Connection;
import java.util.List;

public class FavoriteReportService extends RDSBaseService {
    private FavoriteHeaderDAO headerDAO = null;
    private FavoriteLineDAO lineDAO = null;

    public FavoriteReportService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        headerDAO = new FavoriteHeaderDAO(userAccount, dtoParameter, conn);
        lineDAO = new FavoriteLineDAO(userAccount, null, conn);
        setPrimaryDAO(headerDAO);
    }


    public void produceWebComponent() throws WebException {
        try {
            FavoriteHeaderFrm frm = (FavoriteHeaderFrm)dtoParameter;
            List<FavoriteHeaderFrm> favorites = headerDAO.searchMyFavoriteReports();

            RDSOptionCreateService optService = new RDSOptionCreateService(userAccount, conn);
            WebOptions options = optService.getMyFavoriteOptions(favorites, frm.getHeaderId());
            frm.setMyFavoriteOptions(options);

        } catch (QueryException ex) {
            ex.printLog();
            throw new WebException(ex);
        }
    }

    public boolean saveFavoriteReports() {
        boolean operateResult = false;
        boolean autoCommit = false;
        FavoriteHeaderFrm frm = (FavoriteHeaderFrm) dtoParameter;
        String headerId = frm.getHeaderId();
        boolean isNewData = (headerId.equals(""));
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            saveFavoriteHeader();
            saveFavoriteLines();
            operateResult = true;
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                } else {
                    conn.rollback();
                    if (isNewData) {
                        frm.setHeaderId("");
                    }
                }
                conn.setAutoCommit(autoCommit);
            } catch (Throwable ex) {
                Logger.logError(ex);
                operateResult = false;
            }
        }
        return operateResult;
    }

    private void saveFavoriteHeader() throws DataHandleException {
        FavoriteHeaderFrm frm = (FavoriteHeaderFrm) dtoParameter;
        String headerId = frm.getHeaderId();
        boolean isNewData = (headerId.equals(""));
        if (isNewData) {
            headerId = headerDAO.getNextPrimaryKey();
            frm.setHeaderId(headerId);
            setDTOParameter(frm);
            headerDAO.setDTOParameter(frm);
            headerDAO.createData();
        } else {
            headerDAO.updateData();
        }
    }

    private void saveFavoriteLines() throws DataHandleException {
        FavoriteHeaderFrm frm = (FavoriteHeaderFrm) dtoParameter;
        FavoriteLineFrm lineFrm = new FavoriteLineFrm();
        lineFrm.setHeaderId(frm.getHeaderId());
        lineDAO.setDTOParameter(lineFrm);
        lineDAO.saveFavoriteLines(frm.getReportIds());
    }

    public FavoriteHeaderFrm searchFavoriteByPrimaryKey() throws QueryException {
        FavoriteHeaderFrm favorite = null;
        FavoriteHeaderFrm frm = (FavoriteHeaderFrm) dtoParameter;
        String headerId = frm.getHeaderId();
        if(headerId.equals("")){
            favorite = frm;            
        } else {
            favorite = headerDAO.searchDTOByPrimaryKey();

            FavoriteLineFrm lineFrm = new FavoriteLineFrm();
            lineFrm.setHeaderId(frm.getHeaderId());
            List<FavoriteLineFrm> lines = lineDAO.searchListByForeignKey("headerId");
            favorite.setLines(lines);
        }
        return favorite;
    }
}