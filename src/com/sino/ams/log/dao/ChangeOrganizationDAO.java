package com.sino.ams.log.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.log.model.ChangeOrganizationModel;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

import java.sql.Connection;
import java.util.List;


public class ChangeOrganizationDAO extends AMSBaseDAO {

    public ChangeOrganizationDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter){
        sqlProducer = new ChangeOrganizationModel(userAccount, dtoParameter);
    }

    public List<EtsOuCityMapDTO> getUserOrganizationListByOAName() throws QueryException {
        ChangeOrganizationModel modelProducer = (ChangeOrganizationModel)sqlProducer;
        SQLModel sqlModel = modelProducer.getOrganizationListByOANameModel();
        return searchListByModel(sqlModel, EtsOuCityMapDTO.class);
    }

    public String getLoginNameByOAName()throws QueryException {
        String loginName = "";
        ChangeOrganizationModel modelProducer = (ChangeOrganizationModel)sqlProducer;
        SQLModel sqlModel = modelProducer.getLoginNameByOANameModel();
        SfUserDTO userDTO = searchDTOByModel(sqlModel, dtoParameter.getClass());
        if(userDTO != null){
            loginName = userDTO.getLoginName();
        }
        return loginName;
    }
}
