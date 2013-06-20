package com.sino.ams.log.service;

import com.sino.ams.appbase.service.AMSBaseService;
import com.sino.ams.log.dao.ChangeOrganizationDAO;
import com.sino.ams.log.dao.UserLoginDAO;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

import java.sql.Connection;
import java.util.List;


public class ChangeOrganizationService extends AMSBaseService {

    private ChangeOrganizationDAO organizationDAO = null;

    public ChangeOrganizationService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        organizationDAO = new ChangeOrganizationDAO(userAccount, dtoParameter, conn);
    }

    public List<EtsOuCityMapDTO> getUserOrgListExceptCurrent() throws QueryException {
        List<EtsOuCityMapDTO> allOrgList = organizationDAO.getUserOrganizationListByOAName();
        if (allOrgList != null && !allOrgList.isEmpty()) {
            int orgCount = allOrgList.size();
            for (int i = 0; i < orgCount; i++) {
                EtsOuCityMapDTO org = allOrgList.get(i);
                if (org.getOrganizationId() == userAccount.getOrganizationId()) {
                    allOrgList.remove(i);
                    break;
                }
            }
        }
        return allOrgList;
    }

    public BaseUserDTO getChangedAccount() throws QueryException {
        BaseUserDTO anotherAccount = null;
        try {
            String loginName = organizationDAO.getLoginNameByOAName();
            SfUserDTO userDTO = new SfUserDTO();
            userDTO.setLoginName(loginName);
            UserLoginDAO loginDAO = new UserLoginDAO(userDTO, conn);
            loginDAO.setFromPDA(true);
            if (loginDAO.isValidUser()) {
                anotherAccount = loginDAO.getUserAccount();
            }
        } catch (Throwable ex) {
            if (ex instanceof QueryException) {
                throw (QueryException) ex;
            } else {
                Logger.logError(ex);
                throw new QueryException(ex.getMessage());
            }
        }
        return anotherAccount;
    }
}
