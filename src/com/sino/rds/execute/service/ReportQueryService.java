package com.sino.rds.execute.service;

import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.execute.dao.ReportQueryDAO;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebOptions;
import com.sino.rds.share.form.FavoriteHeaderFrm;
import com.sino.rds.share.util.RDSOptionCreateService;

import java.sql.Connection;
import java.util.List;


public class ReportQueryService extends RDSBaseService {

    private ReportQueryDAO searchDAO = null;

    public ReportQueryService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        searchDAO = new ReportQueryDAO(userAccount, dtoParameter, conn);
        setPrimaryDAO(searchDAO);
    }

    public List<FavoriteHeaderFrm>  searchMyFavoriteReports() throws QueryException {
        return searchDAO.searchMyFavoriteReports();
    }

    public void produceWebComponent() throws WebException {
        try {
            FavoriteHeaderFrm frm = (FavoriteHeaderFrm)dtoParameter;
            List<FavoriteHeaderFrm> favorites = searchDAO.searchMyFavoriteReports();

            RDSOptionCreateService optService = new RDSOptionCreateService(userAccount, conn);
            WebOptions options = optService.getMyFavoriteOptions(favorites, frm.getHeaderId());
            frm.setMyFavoriteOptions(options);

        } catch (QueryException ex) {
            ex.printLog();
            throw new WebException(ex);
        }
    }
}