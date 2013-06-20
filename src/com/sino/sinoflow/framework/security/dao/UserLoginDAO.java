package com.sino.sinoflow.framework.security.dao;

import java.sql.Connection;

import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dao.BaseLoginDAO;
import com.sino.sinoflow.framework.security.dto.SfUserAuthorityDTO;
import com.sino.sinoflow.framework.security.model.UserLoginModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class UserLoginDAO extends BaseLoginDAO {
    private UserLoginModel userLoginModel = null;
    private SfUserBaseDTO user = null;

    private String loginType = "";

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public UserLoginDAO(BaseUserDTO userAccount, Connection conn) {
        super(userAccount, conn);
        userLoginModel = new UserLoginModel(userAccount);
        this.conn = conn;
    }

    /**
     * 功能：获取登录用户的详细信息
     * @return BaseUserDTO
     */
    public BaseUserDTO getUserAccount() {
        return user;
    }

    /**
     * 功能：判断登录用户是否合法用户
     * @return boolean
     * @throws QueryException
     */
    public boolean isValidUser() throws QueryException {
        SQLModel sqlModel=new SQLModel();
        if (getUserInfo()) {
            user = (SfUserBaseDTO) userAccount;
            if (user.getOrganizationId() == servletConfig.getProvinceOrgId()) {
                user.setProvinceUser(true);
            }
            enrichUserAccount(); //加入人组别、角色信息
            enhanceUser();//补充用户员工Id，部门等信息

            isValidUser = true;
        } else {
            prodMessage(MsgKeyConstant.LOGIN_ERROR);
            message.setIsError(true);
        }

        return isValidUser;
    }

    /**
     * 功能：判断登录用户是否合法用户
     * @return boolean
     * @throws QueryException QueryException
     */
    public boolean isValidUserForSSO() throws QueryException {
        SQLModel sqlModel = userLoginModel.getUserLoginModelForSSO();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(userDtoName);
        simp.executeQuery();
        if (simp.hasResult()) {
            userAccount = (BaseUserDTO) simp.getFirstDTO();
            user = (SfUserBaseDTO) userAccount;
            if (user.getOrganizationId() == servletConfig.getProvinceOrgId()) {
                user.setProvinceUser(true);
            }
            enrichUserAccount(); //加入人组别、角色信息
            isValidUser = true;
        } else {
            prodMessage(MsgKeyConstant.LOGIN_ERROR);
            message.setIsError(true);
        }
        return isValidUser;
    }


    /**
     * 功能：补充用户的组别，角色信息
     * @throws QueryException QueryException
     */

    private void enrichUserAccount() throws QueryException {
        SfUserAuthorityDTO sfUserAuthority = new SfUserAuthorityDTO();
        sfUserAuthority.setUserId(user.getUserId());
        SfUserAuthorityDAO userAuthorityDAO = new SfUserAuthorityDAO(user, sfUserAuthority, conn);
        userAuthorityDAO.setDTOClassName(SfUserAuthorityDTO.class.getName());
        DTOSet userAuthoritys = (DTOSet) userAuthorityDAO.getDataByForeignKey("userId");
        if (userAuthoritys != null && !userAuthoritys.isEmpty()) {
            user.setAuthoritys(userAuthoritys);
            SfUserAuthorityDTO tmpUserAuthority;
            String roleName;
            for (int i = 0; i < userAuthoritys.getSize(); i++) {
                tmpUserAuthority = (SfUserAuthorityDTO) userAuthoritys.getDTO(i);
                roleName = tmpUserAuthority.getRoleName();
                if (roleName.equals(servletConfig.getSysAdminRole())) {
                    user.setSysAdmin(true);
                }
            }
        }
    }

    private boolean getUserInfo() throws QueryException {
        SQLModel sqlModel = new SQLModel();
        if (this.loginType.equals("SSO")) {
            sqlModel = userLoginModel.getUserLoginModelForSSO();
        } else {
            sqlModel = userLoginModel.getUserLoginModel();
        }

        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(userDtoName);
        simp.executeQuery();
        if (simp.hasResult()) {
            userAccount = (BaseUserDTO) simp.getFirstDTO();
        }
        return simp.hasResult();
    }

    private  void enhanceUser() throws QueryException {
        SQLModel sqlModel=new SQLModel();
        sqlModel=userLoginModel.getEmployeeIdModel(user.getEmployeeNumber(),user.getOrganizationId());
        SimpleQuery sq=new SimpleQuery(sqlModel,conn);
        sq.executeQuery();
        if(sq.hasResult()){
            try {
                user.setEmployeeId(sq.getSearchResult().getRow(0).getStrValue("EMPLOYEE_ID"));
            } catch (ContainerException e) {
                Logger.logError(e);
            }
        }

    }

}
