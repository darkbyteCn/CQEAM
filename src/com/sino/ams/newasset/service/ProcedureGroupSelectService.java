package com.sino.ams.newasset.service;

import com.sino.ams.appbase.service.AMSBaseService;
import com.sino.ams.newasset.dao.ItemFinanceReportDAO;
import com.sino.ams.newasset.dao.ProcedureGroupSelectDAO;
import com.sino.ams.newasset.dto.ItemFinanceReportDTO;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

import java.sql.Connection;


public class ProcedureGroupSelectService extends AMSBaseService {
    private ProcedureGroupSelectDAO groupDAO = null;

    public ProcedureGroupSelectService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        groupDAO = new ProcedureGroupSelectDAO(userAccount, dtoParameter, conn);
    }

    public void setServletConfig(ServletConfigDTO servletConfig){
        super.setServletConfig(servletConfig);
        groupDAO.setServletConfig(servletConfig);
    }


    public String getProSpecialGroups() throws QueryException {
        String groups = "";
        try {
            RowSet groupList = groupDAO.getProSpecialGroupList();
            if (groupList != null && !groupList.isEmpty()) {
                int groupCount = groupList.getSize();
                for (int i = 0; i < groupCount; i++) {
                    Row row = groupList.getRow(i);
                    groups += row.getStrValue(0);
                    if (i < groupCount - 1) {
                        groups += ";";
                    }
                }
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        }
        return groups;
    }
}
