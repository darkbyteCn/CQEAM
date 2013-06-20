package com.sino.ams.newasset.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.model.UserAssignModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.base.web.DatabaseForWeb;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class UserAssignDAO extends AMSBaseDAO {


    public UserAssignDAO(SfUserDTO userAccount,
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
        sqlProducer = new UserAssignModel((SfUserDTO) userAccount, dto);
    }

    /**
     * 功能：获取SinoEAM系统用户下拉框
     * @param selectedUser String
     * @param selectUserId boolean
     * @return String
     * @throws QueryException
     */
    public String getUserOptions(String selectedUser, boolean selectUserId) throws
            QueryException {
        UserAssignModel modelProducer = (UserAssignModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getUserOptionsModel(selectUserId);
        DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
        return optProducer.getOptionHtml(selectedUser);
    }
}
