package com.sino.sso.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.mdcl.web.sso.SSOToken;
import com.mdcl.web.sso.SSOTokenManager;
import com.sino.ams.newasset.dao.AmsAssetsPriviDAO;
import com.sino.ams.newasset.dao.AmsAssetsTransConfigDAO;
import com.sino.ams.system.user.dao.SfUserRightDAO;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.dto.SfUserRightDTO;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.sso.model.SSOUserLoginModel;

/**
 * User: zhoujs
 * Date: 2008-7-23
 * Time: 17:19:43
 * Function:单点登录用户验证
 */
public class SSOUserLoginDAO {
    SfUserDTO amsUser = null;
    ServletConfigDTO servletConfig = null;
    Connection conn = null;
    SSOUserLoginModel ssoUserLoginModel = null;

    public SSOUserLoginDAO(ServletConfigDTO servletConfig) {
        this.servletConfig = servletConfig;
        this.amsUser = new SfUserDTO();
        ssoUserLoginModel = new SSOUserLoginModel();
    }

    /**
     * 根据OA登录名查找系统用户
     * @param oaName oa登录名
     * @return SfUserDTO
     */
    public SfUserDTO validateUser(String oaName) {
        amsUser.setOaName(oaName.toUpperCase());
        try {
            conn = DBManager.getDBConnection();
            SQLModel sqlModel = ssoUserLoginModel.getSSOUserLoginModel(amsUser);
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.setDTOClassName(SfUserDTO.class.getName());
            simp.executeQuery();
            if (simp.hasResult()) {
                amsUser = (SfUserDTO) simp.getFirstDTO();
                if (amsUser.getOrganizationId() == servletConfig.getProvinceOrgId()) {
                    amsUser.setProvinceUser(true);
                } else {
                    amsUser.setProvinceUser(false);
                }
                enrichUserAccount();//加入人组别、角色信息
                getAddrConfig();//加入地点配置信息
                setAssetsProperty();//加入资产管理员角色
                setAssetsTransConfigs();//加入资产调拨配置信息
                setTmpInvProperty();//加入在途库信息
            }
        } catch (PoolException e) {
            e.printLog();
        } catch (QueryException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        return amsUser;
    }

    /**
     * 根据OA登录名查找系统用户
     * @param employeeNumber 员工编号
     * @return SfUserDTO
     */
    public SfUserDTO validateUserByEmployeeNumber(String employeeNumber) {
        amsUser.setEmployeeNumber(employeeNumber);
        try {
            conn = DBManager.getDBConnection();
            SQLModel sqlModel = ssoUserLoginModel.getSSOUserLoginByEmployeeNumberModel(amsUser);
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.setDTOClassName(SfUserDTO.class.getName());
            simp.executeQuery();
            if (simp.hasResult()) {
                amsUser = (SfUserDTO) simp.getFirstDTO();
                if (amsUser.getOrganizationId()==servletConfig.getProvinceOrgId()) {
                    amsUser.setProvinceUser(true);
                } else {
                    amsUser.setProvinceUser(false);
                }
                enrichUserAccount();//加入人组别、角色信息
                getAddrConfig();//加入地点配置信息
                setAssetsProperty();//加入资产管理员角色
                setAssetsTransConfigs();//加入资产调拨配置信息
                setTmpInvProperty();//加入在途库信息
            }
        } catch (PoolException e) {
            e.printLog();
        } catch (QueryException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        return amsUser;
    }

    /**
     * 功能：补充用户的组别，角色信息
     * @throws QueryException　
     */
    private void enrichUserAccount() throws QueryException {
        SfUserRightDTO sfUserRight = new SfUserRightDTO();
        sfUserRight.setUserId(amsUser.getUserId());

        SfUserRightDAO userRightDAO = new SfUserRightDAO(amsUser, sfUserRight, conn);
        userRightDAO.setDTOClassName(SfUserRightDTO.class.getName());
        DTOSet userRights = (DTOSet) userRightDAO.getDataByForeignKey("userId");
        try {
            if (userRights != null && !userRights.isEmpty()) {
                amsUser.setUserRights(userRights);
                SfUserRightDTO currUserRight = (SfUserRightDTO) userRights.getDTO(0);
                int currGroupId = currUserRight.getGroupId();
                amsUser.setCurrGroupId(currGroupId);
                amsUser.setCurrGroupName(currUserRight.getGroupName());
                SfUserRightDTO tmpUserRight = null;
                SfGroupDTO sfGroup = null;
                DTOSet sfGroups = new DTOSet();
                Vector vector = new Vector();
                List currRoleIds = new ArrayList();
                String roleName = "";
                for (int i = 0; i < userRights.getSize(); i++) {
                    tmpUserRight = (SfUserRightDTO) userRights.getDTO(i);
                    int tmpGroupId = tmpUserRight.getGroupId();
                    if (!vector.contains(tmpGroupId)) {
                        sfGroup = new SfGroupDTO();
                        sfGroup.setGroupId(tmpGroupId);
                        sfGroup.setGroupname(tmpUserRight.getGroupName());
                        sfGroup.setGroupCode(tmpUserRight.getGroupCode());
                        sfGroup.setGroupProp(tmpUserRight.getGroupProp());
                        sfGroups.addDTO(sfGroup);
                        vector.add(tmpGroupId);
                    }
                    if (currGroupId == tmpUserRight.getGroupId() ) {
                        currRoleIds.add(tmpUserRight.getRoleId());
                    }
                    roleName = tmpUserRight.getRoleName();
                    if (roleName.equals(servletConfig.getSysAdminRole())) {
                        amsUser.setSysAdmin(true);
                    }
                }
                amsUser.setCurrRoleIds(currRoleIds);
                amsUser.setUserGroups(sfGroups);
            }
        } catch (DTOException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
    }

    /**
     * 功能：读取地点配置
     * @throws QueryException　
     */
    private void getAddrConfig() throws QueryException {
        SQLModel sqlModel = ssoUserLoginModel.getAddrConfigDataModel();
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();

        if (simpleQuery.hasResult()) {
            RowSet rows = simpleQuery.getSearchResult();
            for (int i = 0; i < rows.getSize(); i++) {
                Row row = rows.getRow(i);
                try {
                    String id = row.getStrValue("CONFIG_ID");
                    String checkedFlag = row.getStrValue("CHECKED_FLAG");
                    if (id.equals("1")) {
                        amsUser.setIsToAddr(checkedFlag.equals("Y"));
                    }
                    if (id.equals("2")) {
                        amsUser.setIsToNetUnit(checkedFlag.equals("Y"));
                    }
                    if (id.equals("3")) {
                        amsUser.setIsToBox(checkedFlag.equals("Y"));
                    }
                } catch (ContainerException e) {
                    e.printStackTrace();
                    throw new QueryException();
                }
            }
        }
    }

    /**
     * 功能：设置用户的资产管理权限
     * @throws QueryException　
     */
    private void setAssetsProperty() throws QueryException {
        AmsAssetsPriviDAO priviDAO = new AmsAssetsPriviDAO(amsUser, null, conn);
        priviDAO.setServletConfig(servletConfig);
        DTOSet priviAssetsDtp = priviDAO.getPriviDepts();
        String mtlMgrPropps = priviDAO.getMtlMgrProps();
        amsUser.setMtlMgrProps(mtlMgrPropps);
        if (priviAssetsDtp == null || priviAssetsDtp.isEmpty()) {
            amsUser.setDptAssetsManager(false);
        } else {
            amsUser.setDptAssetsManager(true);
            amsUser.setPriviDeptCodes(priviAssetsDtp);
        }
        amsUser.setComAssetsManager(priviDAO.isCompanyManager());
        amsUser.setProvAssetsManager(priviDAO.isProvinceManager());
    }

    /**
     * 功能：设置资产调拨配置信息
     * @throws QueryException　
     */
    private void setAssetsTransConfigs() throws QueryException {
        AmsAssetsTransConfigDAO configDAO = new AmsAssetsTransConfigDAO(amsUser, null, conn);
        configDAO.setServletConfig(servletConfig);
        DTOSet transConfigs = configDAO.getTransConfigs();
        amsUser.setTransConfigs(transConfigs);
    }

    /**
     * 加入在途库信息
     * @throws QueryException 　
     */
    private void setTmpInvProperty() throws QueryException {
        try {
            SQLModel sqlModel = ssoUserLoginModel.getTmpInvAddressModel(amsUser.getOrganizationId());
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                Row row = simp.getFirstRow();
                amsUser.setTmpAddressId(row.getStrValue("ADDRESS_ID"));
                amsUser.setTmpInvCode(row.getStrValue("WORKORDER_OBJECT_NO"));
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
    }

    /**
     * 取单点登录OA登录名
     * @param req HttpServletRequest
     * @return String
     */
    public String validateSSOUser(HttpServletRequest req) {
        String uid = "";
        SSOTokenManager manager = null;
        manager = SSOTokenManager.getInstance();
        SSOToken token = manager.createSSOToken(req);
        if (manager.isValidToken(token)) {
            java.security.Principal p = token.getPrincipal();
            uid = p.getName();
        }
        return uid;
    }
}
