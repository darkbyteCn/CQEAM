package com.sino.ams.log.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.ams.log.model.UserLoginModel;
import com.sino.ams.newasset.dao.AmsAssetsPriviDAO;
import com.sino.ams.newasset.dao.AmsAssetsTransConfigDAO;
import com.sino.ams.system.user.dao.SfUserRightDAO;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.dto.SfUserRightDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dao.BaseLoginDAO;

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
    private SfUserDTO amsUser = null;

    public UserLoginDAO(BaseUserDTO userAccount, Connection conn) {
        super(userAccount, conn);
        userLoginModel = new UserLoginModel(userAccount);
        this.conn = conn;
    }

    /**
     * 功能：设置用户是否从PDA登录
     * @param fromPDA boolean
     */
    public void setFromPDA(boolean fromPDA) {
        userLoginModel.setFromPDA(fromPDA);
    }

    /**
     * 功能：获取登录用户的详细信息
     * @return BaseUserDTO
     */
    public BaseUserDTO getUserAccount() {
        return amsUser;
    }

    /**
     * 功能：判断登录用户是否合法用户
     * @return boolean
     * @throws QueryException
     */
    public boolean isValidUser() throws QueryException {
    	SQLModel sqlModel = userLoginModel.getUserLoginModel();
        return isValidUser( sqlModel , false  );
    }
    
    /**
     * 功能：判断登录用户是否合法用户
     * @return boolean
     * @throws QueryException
     */
    public boolean isValidUser( SQLModel sqlModel , boolean isThrow ) throws QueryException {
//        SQLModel sqlModel = userLoginModel.getUserLoginModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(SfUserDTO.class.getName());
        simp.executeQuery();
        if (simp.hasResult()) {
            userAccount = (BaseUserDTO) simp.getFirstDTO();
            
            amsUser = (SfUserDTO) userAccount;
            
            if( amsUser.getEnabled().equals( "N" ) ){
            	prodMessage(MsgKeyConstant.LOGIN_ERROR);
            	message.setMessageValue( "用户已被失效" );
            	message.setIsError(true);
            	return false;
            }
//            if (String.valueOf(amsUser.getOrganizationId()).equals(servletConfig.getProvinceOrgId())) {
            if (amsUser.getOrganizationId() == servletConfig.getProvinceOrgId()) {
                amsUser.setProvinceUser(true);
            } else {
                amsUser.setProvinceUser(false);
            }
            enhanceUser();
            enrichUserAccount();//加入人组别、角色信息
            getAddrConfig();//加入地点配置信息
            setAssetsProperty();//加入资产管理员角色
            setAssetsTransConfigs();//加入资产调拨配置信息
            setTmpInvProperty();//加入在途库信息
            isValidUser = true;
        } else {
        	if( isThrow ){
        		throw new QueryException( "资产系统没有这个用户或密码错误" );
        	}else{
        		prodMessage(MsgKeyConstant.LOGIN_ERROR);
        		message.setIsError(true);
        	}
        }
        return isValidUser;
    }

    /**
     * 功能：补充用户的组别，角色信息
     * @throws QueryException
     */
    private void enrichUserAccount() throws QueryException {
        SfUserRightDTO sfUserRight = new SfUserRightDTO();
        sfUserRight.setUserId(amsUser.getUserId());

        List lsRoleName = new ArrayList();
        List lsRoleId = new ArrayList();
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
                for (int i = 0; i < userRights.getSize(); i++) {
                    tmpUserRight = (SfUserRightDTO) userRights.getDTO(i);
                    int tmpGroupId = tmpUserRight.getGroupId();
                    if (!vector.contains(tmpGroupId)) {
                        sfGroup = new SfGroupDTO();
                        sfGroup.setGroupId(tmpGroupId);
                        sfGroup.setGroupname(tmpUserRight.getGroupName());
                        sfGroup.setGroupCode(tmpUserRight.getGroupCode());
                        sfGroup.setGroupProp(tmpUserRight.getGroupProp());
                        sfGroup.setDeptId(tmpUserRight.getDeptId());
                        sfGroup.setDeptName(tmpUserRight.getDeptName());
                        sfGroups.addDTO(sfGroup);
                        vector.add(tmpGroupId);
                    }

                    if(!lsRoleId.contains(tmpUserRight.getRoleId())){
                        lsRoleId.add(tmpUserRight.getRoleId());
                    }
                    if (!lsRoleName.contains(tmpUserRight.getRoleName())) {
                        lsRoleName.add(tmpUserRight.getRoleName());
                    }
                }

                if (lsRoleName.contains(servletConfig.getSysAdminRole())) {
                    amsUser.setSysAdmin(true);
                }
                if (lsRoleName.contains(servletConfig.getCityAdminRole())) {
                    amsUser.setCityAdmin(true);
                }
                amsUser.setCurrRoleIds(lsRoleId);
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
        SQLModel sqlModel = userLoginModel.getAddrConfigDataModel();
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
        if (mtlMgrPropps.equals("")) {
            amsUser.setMtlAssetsManager(false);
        } else {
            amsUser.setMtlAssetsManager(true);
        }
        amsUser.setComAssetsManager(priviDAO.isCompanyManager());
        amsUser.setProvAssetsManager(priviDAO.isProvinceManager());
//        amsUser.setDptMgr(priviDAO.isDptMgr());
    }

    /**
     * 功能：加入在途库信息
     * @throws QueryException
     */
    private void setTmpInvProperty() throws QueryException {
        try {
            userLoginModel.setDTOParameter(amsUser);
            SQLModel sqlModel = userLoginModel.getTmpInvAddressModel();
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
     * 功能：设置资产调拨配置信息
     * @throws QueryException
     */
    private void setAssetsTransConfigs() throws QueryException {
        AmsAssetsTransConfigDAO configDAO = new AmsAssetsTransConfigDAO(amsUser, null, conn);
        configDAO.setServletConfig(servletConfig);
        DTOSet transConfigs = configDAO.getTransConfigs();
        amsUser.setTransConfigs(transConfigs);
    }

        private  void enhanceUser() throws QueryException {
        SQLModel sqlModel=new SQLModel();
        sqlModel=userLoginModel.getEmployeeIdModel(amsUser.getEmployeeNumber(),amsUser.getOrganizationId());
        SimpleQuery sq=new SimpleQuery(sqlModel,conn);
        sq.executeQuery();
        if(sq.hasResult()){
            try {
                amsUser.setEmployeeId(sq.getSearchResult().getRow(0).getStrValue("EMPLOYEE_ID"));
            } catch (ContainerException e) {
                Logger.logError(e);
            }
        }

    }
}
