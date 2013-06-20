package com.sino.sinoflow.user.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ReflectException;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 唐明胜版权所有Copyright (c) 2003~2007。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Copyright: 作者授权北京思诺博信息技术有限公司在一定范围内使用</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 0.1
 */


public class SfUserBaseModel extends BaseSQLProducer {

    /**
     * 功能：用户维护Model构造函数
     */
    public SfUserBaseModel() {
        super(null, null);
    }

    /**
     * 功能：用户维护Model构造函数
     *
     * @param userAccount  BaseUserDTO 代表执行当前操作的用户
     * @param dtoParameter SfUserBaseDTO 代表当前操作的数据
     */
    public SfUserBaseModel(SfUserBaseDTO userAccount, SfUserBaseDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：用户维护Model构造函数
     *
     * @param userAccount  BaseUserDTO 代表执行当前操作的用户
     * @param dtoParameter SfUserBaseDTO 代表当前操作的数据
     */
    public SfUserBaseModel(BaseUserDTO userAccount, SfUserBaseDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：框架自动生成数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        try {
            SfUserBaseDTO dto = (SfUserBaseDTO) super.dtoParameter;
            String endDate = "";
            try {
                endDate = dto.getEndDate().getCalendarValue();
            } catch (CalendarException e) {
                e.printStackTrace();
            }
            List sqlArgs = new ArrayList();
            String sqlStr = "INSERT INTO "
                    + " SF_USER("
//                + " USER_ID,"
                    + " USERNAME,"
                    + " PASSWORD,"
                    + " EMPLOYEE_NUMBER,"
                    + " OA_NAME,"
                    + " DISPLAY_ORDER,"
                    + " OFFICE_TEL,"
                    + " FAX,"
                    + " MOBILE_PHONE,"
                    + " EMAIL,"
                    + " ORGANIZATION,"
                    + " WORK_SCHEDULE_ID,"
                    + " LOGIN_NAME,"
                    + " ENABLED,"
                    + " START_DATE,"
                    + " END_DATE,"
                    //            + " UPDATED_DATE,"
                    + " ORGANIZATION_ID "
                    + ") VALUES ("
                    + "  ?, dbo.SFK_ENCODE(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,";
            if (endDate == null || "".equals(endDate)) {
                sqlStr = sqlStr + "dateadd(year,5,GETDATE()),";
            } else {
                sqlStr = sqlStr + " ?,";
            }
            sqlStr = sqlStr +
                    //             "GETDATE()," +
                    " ?)";

//        sqlArgs.add(dto.getUserId());
            sqlArgs.add(dto.getUsername());
            sqlArgs.add(dto.getPassword());
            sqlArgs.add(dto.getEmployeeNumber());
            sqlArgs.add(StrUtil.nullToString(ReflectionUtil.getProperty(dto, "oaName")));
            sqlArgs.add(dto.getDisplayOrder());
            sqlArgs.add(dto.getOfficeTel());
            sqlArgs.add(dto.getFax());
            sqlArgs.add(dto.getMobilePhone());
            sqlArgs.add(dto.getEmail());
            sqlArgs.add(dto.getOrganization());
            sqlArgs.add(dto.getWorkScheduleId());
            sqlArgs.add(dto.getLoginName().toUpperCase());
            sqlArgs.add(dto.getEnabled());
            sqlArgs.add(dto.getStartDate());   //??
//        sqlArgs.add(dto.getEndDate());
            if (endDate != null && !"".equals(endDate)) {
                sqlArgs.add(dto.getEndDate());
            }
            sqlArgs.add(dto.getOrganizationId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (ReflectException ex) {
            ex.printLog();
            throw new IllegalArgumentException("获取用户OA账号出错");
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据更新SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        try {
            SfUserBaseDTO dto = (SfUserBaseDTO) super.dtoParameter;
            List sqlArgs = new ArrayList();
            String endDate = "";
            try {
                endDate = dto.getEndDate().getCalendarValue();
            } catch (CalendarException e) {
                e.printStackTrace();
            }
            String sqlStr = "UPDATE SF_USER"
                    + " SET"
                    + " USERNAME = ?,"
                    + " PASSWORD = CASE PASSWORD WHEN ? THEN PASSWORD ELSE dbo.SFK_ENCODE(?) END,"
                    + " EMPLOYEE_NUMBER = ?,"
                    + " OA_NAME = ?,"
                    + " DISPLAY_ORDER = ?,"
                    + " OFFICE_TEL = ?,"
                    + " FAX = ?,"
                    + " MOBILE_PHONE = ?,"
                    + " EMAIL = ?,"
                    + " ORGANIZATION = ?,"
                    + " WORK_SCHEDULE_ID = ?,"
                    + " LOGIN_NAME = ?,"
                    + " ENABLED = ?,"
                    + " START_DATE = ?,";


            if (endDate == null || "".equals(endDate)) {
                sqlStr = sqlStr + " END_DATE = dateadd(year,5,GETDATE()) ,";
            } else {
                sqlStr = sqlStr + " END_DATE = ?,";
            }
            sqlStr = sqlStr
//                + " UPDATED_DATE = GETDATE() ,"
                    + " ORGANIZATION_ID = ?"
                    + " WHERE"
                    + " USER_ID = ?";

            sqlArgs.add(dto.getUsername());
            sqlArgs.add(dto.getPassword());
            sqlArgs.add(dto.getPassword());
            sqlArgs.add(dto.getEmployeeNumber());
            sqlArgs.add(StrUtil.nullToString(ReflectionUtil.getProperty(dto, "oaName")));
            sqlArgs.add(dto.getDisplayOrder());
            sqlArgs.add(dto.getOfficeTel());
            sqlArgs.add(dto.getFax());
            sqlArgs.add(dto.getMobilePhone());
            sqlArgs.add(dto.getEmail());
            sqlArgs.add(dto.getOrganization());
            sqlArgs.add(dto.getWorkScheduleId());
            sqlArgs.add(dto.getLoginName().toUpperCase());
            sqlArgs.add(dto.getEnabled());
            sqlArgs.add(dto.getStartDate());
            if (endDate != null && !"".equals(endDate)) {
                sqlArgs.add(dto.getEndDate());
            }
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getUserId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (ReflectException ex) {
            ex.printLog();
            throw new IllegalArgumentException("获取用户OA账号出错");
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据删除SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {

        SfUserBaseDTO dto = (SfUserBaseDTO) super.dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " SF_USER"
                + " WHERE"
                + " USER_ID = ?";
        sqlArgs.add(dto.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据删除SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel delSfUserAuthorityModel() {
        SfUserBaseDTO dto = (SfUserBaseDTO) super.dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " SF_USER_AUTHORITY"
                + " WHERE"
                + " USER_ID = ?";
        sqlArgs.add(dto.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {

        SfUserBaseDTO dto = (SfUserBaseDTO) super.dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " USER_ID,"
                + " USERNAME,"
                + " PASSWORD,"
                + " EMPLOYEE_ID,"
                + " EMPLOYEE_NUMBER,"
                + " OA_NAME,"
                + " DISPLAY_ORDER,"
                + " OFFICE_TEL,"
                + " FAX,"
                + " MOBILE_PHONE,"
                + " EMAIL,"
                + " ORGANIZATION,"
                + " WORK_SCHEDULE_ID,"
                + " LOGIN_NAME, "
                + " END_DATE,"
                + " ENABLED,"
                + " START_DATE,"
                + " ORGANIZATION_ID ,"
//               +  "   AMS_PUB_PKG.GET_PRI_CODE(USER_ID)CATEGORY_CODE "
                + " dbo.APP_GET_PRI_CODE(USER_ID) CATEGORY_CODE "
                + " FROM "
                + " SF_USER"
                + " WHERE USER_ID = ?";

        sqlArgs.add(dto.getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成多条数据信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getMuxDataModel() {

        SfUserBaseDTO dto = (SfUserBaseDTO) super.dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " USER_ID,"
                + " USERNAME,"
                + " PASSWORD,"
                + " EMPLOYEE_ID,"
                + " OFFICE_TEL,"
                + " FAX,"
                + " MOBILE_PHONE,"
                + " EMAIL,"
                + " ORGANIZATION,"
                + " WORK_SCHEDULE_ID,"
                + " LOGIN_NAME "
                + " FROM "
                + " SF_USER SU"
                + " WHERE "
                + " SU.LOGIN_NAME = ?";
        sqlArgs.add(dto.getLoginName());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 根据用户名获取详细数据
     *
     * @return
     */

    public SQLModel getUserDataModel() {

        SfUserBaseDTO dto = (SfUserBaseDTO) super.dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " USER_ID,"
                + " USERNAME,"
                + " PASSWORD,"
                + " EMPLOYEE_ID,"
                + " OFFICE_TEL,"
                + " FAX,"
                + " MOBILE_PHONE,"
                + " EMAIL,"
                + " ORGANIZATION,"
                + " WORK_SCHEDULE_ID,"
                + " LOGIN_NAME "
                + " FROM "
                + " SF_USER SU"
                + " WHERE "
                + " SU.LOGIN_NAME = UPPER(?)";
        sqlArgs.add(dto.getLoginName());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getCompleteDataByLoginNameModel() {
        SfUserBaseDTO dto = (SfUserBaseDTO) super.dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "SELECT U.USER_ID, U.USERNAME, U.PASSWORD, U.EMPLOYEE_ID, U.OFFICE_TEL, " +
                " U.FAX, U.MOBILE_PHONE, U.EMAIL, " +
                " U.ORGANIZATION, U.WORK_SCHEDULE_ID, U.LOGIN_NAME," +
                " U.ORGANIZATION_ID,T.COMPANY_CODE" +
                " FROM SF_USER U,SINO_MIS_EMPLOYEE E,SINO_MIS_DEPT T " +
                " WHERE U.EMPLOYEE_ID = E.EMPLOYEE_ID " +
                " AND E.DEPT_ID = T.DEPT_ID " +
                " AND UPPER(U.LOGIN_NAME) = UPPER(?)";

        sqlArgs.add(dto.getLoginName());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getCompleteDataByUserIdModel() {
        SfUserBaseDTO dto = (SfUserBaseDTO) super.dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "SELECT U.USER_ID, U.USERNAME, U.PASSWORD, U.EMPLOYEE_ID, U.OFFICE_TEL, " +
                " U.FAX, U.MOBILE_PHONE, U.EMAIL, " +
                " U.ORGANIZATION, U.WORK_SCHEDULE_ID, U.LOGIN_NAME," +
                " U.ORGANIZATION_ID,T.COMPANY_CODE" +
                " FROM SF_USER U,SINO_MIS_EMPLOYEE E,SINO_MIS_DEPT T " +
                " WHERE U.EMPLOYEE_ID = E.EMPLOYEE_ID " +
                " AND E.DEPT_ID = T.DEPT_ID " +
                " AND U.USER_ID = ?";

        sqlArgs.add(dto.getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：框架自动生成页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {

        SfUserBaseDTO dto = (SfUserBaseDTO) super.dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " CONVERT(INT,SU.USER_ID) USER_ID,"
//                + " SU.USER_ID,"
                + " SU.USERNAME,"
                + " SU.PASSWORD,"
                + " SU.EMPLOYEE_ID,"
                + " SU.EMPLOYEE_NUMBER,"
                + " SU.OA_NAME,"
                + " SU.OFFICE_TEL,"
                + " SU.FAX,"
                + " SU.MOBILE_PHONE,"
                + " CASE SU.ENABLED WHEN 'Y' THEN '生效' WHEN 'N' THEN '失效' ELSE '空' END ENABLED,"
                + " SU.START_DATE,"
                + " SU.END_DATE,"
                + " SU.EMAIL,"
                + " SU.ORGANIZATION,"
                + " SU.WORK_SCHEDULE_ID,"
                + " SU.LOGIN_NAME,"
                + " SU.DISPLAY_ORDER,"
                + " SU.ORGANIZATION_ID"
                + " FROM"
                + " SF_USER SU"
                + " WHERE"
//                + " (? IS NULL OR UPPER(SU.LOGIN_NAME) LIKE ?)"
//                + " AND (? IS NULL OR SU.USERNAME LIKE ?)";
                + " (? = '' OR UPPER(SU.LOGIN_NAME) LIKE UPPER(?) )"
                + " AND (? = '' OR UPPER( SU.USERNAME ) LIKE UPPER(?) )";
        sqlArgs.add(dto.getLoginName());
        sqlArgs.add("%" + dto.getLoginName().toUpperCase() + "%");
        sqlArgs.add(dto.getUsername());
        sqlArgs.add("%" + dto.getUsername().toUpperCase() + "%");
        if (!dto.getProjectName().equals("")) {
            sqlStr += " AND EXISTS("
                    + " SELECT"
                    + " NULL"
                    + " FROM"
                    + " SF_USER_AUTHORITY SUA"
                    + " WHERE"
                    + " SU.USER_ID = SUA.USER_ID"
//                    + " AND (? IS NULL OR SUA.PROJECT_NAME = ?)"
//                    + " AND (? IS NULL OR SUA.ROLE_NAME = ?)"
//                    + " AND (? IS NULL OR SUA.GROUP_NAME = ?))";
                    + " AND (? = '' OR SUA.PROJECT_NAME = ?)"
                    + " AND (? = '' OR SUA.ROLE_NAME = ?)"
                    + " AND (? = '' OR SUA.GROUP_NAME = ?))";
            sqlArgs.add(dto.getProjectName());
            sqlArgs.add(dto.getProjectName());
            sqlArgs.add(dto.getRoleName());
            sqlArgs.add(dto.getRoleName());
            sqlArgs.add(dto.getGroupName());
            sqlArgs.add(dto.getGroupName());
        }
//		if(!userAccount.isProvinceUser()){
//        sqlStr += " AND (? IS NULL OR SU.ORGANIZATION_ID = ?)";
        sqlStr += " AND (0 >= ? OR SU.ORGANIZATION_ID = ?)";
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
//		}
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据删除SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getSfUserAuthorityModel() {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " AUTHORITY_ID,"
                + " USER_ID,"
                + " DEPARTMENT,"
                + " POSITION,"
                + " GROUP_NAME,"
                + " ROLE_NAME,"
                + " PROJECT_NAME"
                + " FROM"
                + " SF_USER_AUTHORITY"
                + " WHERE"
                + " USER_ID = ?"
                + " ORDER BY PROJECT_NAME,GROUP_NAME,ROLE_NAME";
        sqlArgs.add(((SfUserBaseDTO) super.dtoParameter).getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getSfUserDeptModel() {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT CONVERT( INT , T.USER_ID) USER_ID, T.DEPT_CODE, T.PRIVI_ID, AMD.DEPT_NAME\n" +
                "  FROM AMS_ASSETS_PRIVI T, AMS_MIS_DEPT AMD\n" +
                " WHERE T.USER_ID = ?\n" +
                "   AND AMD.DEPT_CODE = T.DEPT_CODE\n" +
                "   AND T.DEPT_CODE IS NOT NULL\n" +
                "   ORDER BY AMD.DEPT_NAME ";
        sqlArgs.add(((SfUserBaseDTO) super.dtoParameter).getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：为页面显示所用
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel displayUser() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT"
                + " USER_ID,"
                + " LOGIN_NAME"
                + " FROM"
                + " SF_USER"
                + " ORDER BY USER_ID";
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    /**
     * 功能：为页面显示所用
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel displayUsers() {
        SfUserBaseDTO dto = (SfUserBaseDTO) super.dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " DISTINCT SU.USER_ID,"
                + " SU.USERNAME,"
                + " SU.DISPLAY_ORDER"
                + " FROM"
                + " SF_USER SU, SF_USER_AUTHORITY SUA"
                + " WHERE EXISTS (SELECT NULL FROM SF_USER_AUTHORITY SUA2"
                + "    WHERE SUA2.USER_ID = ? AND SUA2.GROUP_NAME = SUA.GROUP_NAME"
                + "    AND SUA2.PROJECT_NAME = SUA.PROJECT_NAME)"
                + " AND SU.USER_ID = SUA.USER_ID"
                + " ORDER BY SU.DISPLAY_ORDER, SU.USERNAME";
        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(((SfUserBaseDTO) super.dtoParameter).getUserId());
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel displayDelegateUsers() {
        SfUserBaseDTO dto = (SfUserBaseDTO) super.dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " DISTINCT SU.USER_ID,"
                + " SU.USERNAME,"
                + " SU.DISPLAY_ORDER"
                + " FROM"
                + " SF_USER SU, SF_USER_AUTHORITY SUA"
                + " WHERE EXISTS (SELECT NULL FROM SF_USER_AUTHORITY SUA2"
//                + "    WHERE SUA2.USER_ID = ? AND INSTR(SUA.GROUP_NAME, SUA2.GROUP_NAME) > 0"
                + "    WHERE SUA2.USER_ID = ? AND CHARINDEX(SUA2.GROUP_NAME, SUA.GROUP_NAME) > 0"
                + "    AND SUA2.PROJECT_NAME = SUA.PROJECT_NAME)"
                + " AND SU.USER_ID = SUA.USER_ID"
                + " AND SU.ORGANIZATION_ID = ?"
                + " ORDER BY SU.DISPLAY_ORDER, SU.USERNAME";
        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(((SfUserBaseDTO) super.dtoParameter).getUserId());
        sqlArgs.add(dto.getOrganizationId());
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据删除SQLModel，请根据实际需要修改。
     *
     * @param prj   工程名称
     * @param group 组别名称, 可多重组别, 以分号隔开
     * @param role  角色名称
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getCycleUsersModel(String prj, String group, String role) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " SU.USER_ID,"
                + " SU.LOGIN_NAME,"
                + " SU.USERNAME,"
                + " SU.ORGANIZATION,"
                + " SUA.GROUP_NAME"
                + " FROM"
                + " SF_USER SU, SF_USER_AUTHORITY SUA"
                + " WHERE"
                + " SUA.PROJECT_NAME = ?"
                + " AND dbo.SFK_GROUP_IN_LIST(?, SUA.GROUP_NAME) <> 0"
//                + " AND GROUP_IN_LIST(?, SUA.GROUP_NAME) <> 0"
                + " AND SUA.ROLE_NAME = ?"
                + " AND SUA.USER_ID = SU.USER_ID"
                + " AND SU.ENABLED <> 'N'"
                + " AND ((SU.START_DATE = '' OR SU.START_DATE IS NULL OR SU.START_DATE <= GETDATE()) AND (SU.END_DATE = '' OR SU.END_DATE IS NULL OR SU.END_DATE >= GETDATE()))"
//                + " AND ((SU.START_DATE IS NULL OR SU.START_DATE <= GETDATE()) AND (SU.END_DATE IS NULL OR SU.END_DATE >= GETDATE()))"
                + " ORDER BY SUA.GROUP_NAME,SUA.ROLE_NAME,SU.DISPLAY_ORDER";
        sqlArgs.add(prj);
        sqlArgs.add(group);
        sqlArgs.add(role);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据删除SQLModel，请根据实际需要修改。
     *
     * @param prj  工程名称
     * @param role 角色名称
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getCycleUsersModel(String prj, String role) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " SU.USER_ID,"
                + " SU.LOGIN_NAME,"
                + " SU.USERNAME,"
                + " SU.ORGANIZATION,"
                + " SUA.GROUP_NAME"
                + " FROM"
                + " SF_USER SU, SF_USER_AUTHORITY SUA"
                + " WHERE"
                + " SUA.PROJECT_NAME = ?"
                + " AND SUA.ROLE_NAME = ?"
                + " AND SUA.USER_ID = SU.USER_ID"
                + " AND SU.ENABLED <> 'N'"
//                + " AND ((SU.START_DATE IS NULL OR SU.START_DATE <= GETDATE()) AND (SU.END_DATE IS NULL OR SU.END_DATE >= GETDATE()))"
                + " AND ((SU.START_DATE = '' OR SU.START_DATE IS NULL OR SU.START_DATE <= GETDATE()) AND (SU.END_DATE = '' OR SU.END_DATE IS NULL OR SU.END_DATE >= GETDATE()))"
                + " ORDER BY SUA.GROUP_NAME, SUA.ROLE_NAME, SU.DISPLAY_ORDER";
        sqlArgs.add(prj);
        sqlArgs.add(role);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据删除SQLModel，请根据实际需要修改。
     *
     * @param prj  工程名称
     * @param role 角色名称
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getCycleGroupsModel(String prj, String role) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " DISTINCT SUA.GROUP_NAME"
                + " FROM"
                + " SF_USER_AUTHORITY SUA"
                + " WHERE"
                + " SUA.PROJECT_NAME = ?"
                + " AND SUA.ROLE_NAME = ?"
                + " ORDER BY SUA.GROUP_NAME";
        sqlArgs.add(prj);
        sqlArgs.add(role);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据删除SQLModel，请根据实际需要修改。
     *
     * @param prj   工程名称
     * @param group 组别名称
     * @param role  角色名称
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getGroupMaskUsersModel(String prj, String group, String role) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " DISTINCT SU.USER_ID,"
                + " SU.LOGIN_NAME,"
                + " SU.USERNAME,"
                + " SU.ORGANIZATION,"
                + " SU.DISPLAY_ORDER,"
                + " SUA.GROUP_NAME"
                + " FROM"
                + " SF_USER SU, SF_USER_AUTHORITY SUA"
                + " WHERE"
                + " SUA.PROJECT_NAME = ?"
                + " AND dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME, ?) = ?"
                + " AND (? = '' OR SUA.ROLE_NAME = ?)"
                + " AND SUA.USER_ID = SU.USER_ID"
                + " AND SU.ENABLED <> 'N'"
                + " AND ((SU.START_DATE = '' OR SU.START_DATE IS NULL OR SU.START_DATE <= GETDATE()) AND (SU.END_DATE = '' OR SU.END_DATE IS NULL OR SU.END_DATE >= GETDATE()))"
                + " ORDER BY SUA.GROUP_NAME, SU.DISPLAY_ORDER, SU.USERNAME";
        sqlArgs.add(prj);
        sqlArgs.add(group);
        sqlArgs.add(group);
        sqlArgs.add(role);
        sqlArgs.add(role);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据删除SQLModel，请根据实际需要修改。
     *
     * @param prj   工程名称
     * @param group 组别名称
     * @param role  角色名称
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getGroupUsersModel(String prj, String group, String role) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " SU.USER_ID,"
                + " SU.LOGIN_NAME,"
                + " SU.USERNAME,"
                + " SU.ORGANIZATION,"
                + " SU.DISPLAY_ORDER,"
                + " SUA.GROUP_NAME"
                + " FROM"
                + " SF_USER SU, SF_USER_AUTHORITY SUA"
                + " WHERE"
                + " SUA.PROJECT_NAME = ?"
                + " AND dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME, ?) = ?"
                + " AND SUA.ROLE_NAME = ?"
                + " AND SUA.USER_ID = SU.USER_ID"
                + " AND SU.ENABLED <> 'N'"
                + " AND ((SU.START_DATE = '' OR SU.START_DATE IS NULL OR SU.START_DATE IS NULL OR SU.START_DATE <= GETDATE()) AND (SU.END_DATE = '' OR SU.END_DATE IS NULL OR SU.END_DATE IS NULL OR SU.END_DATE >= GETDATE()))"
                + " ORDER BY SUA.GROUP_NAME, SU.DISPLAY_ORDER, SU.USERNAME";
        sqlArgs.add(prj);
        sqlArgs.add(group);
        sqlArgs.add(group);
        sqlArgs.add(role);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getGroupUsersModel(String prj, String group, String role, String nList) {
        if (nList != null && !nList.equals("")) {
            nList = nList.replaceAll(";", "','");
            nList = "'" + nList + "'";
        }
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT /*+ ALL_ROWS */ "
                + " SU.USER_ID,"
                + " SU.LOGIN_NAME,"
                + " SU.USERNAME,"
                + " SU.ORGANIZATION,"
                + " SU.DISPLAY_ORDER,"
                + " SUA.GROUP_NAME"
                + " FROM"
                + " SF_USER SU, SF_USER_AUTHORITY SUA"
                + " WHERE"
                + " SUA.PROJECT_NAME = ?"
                + " AND dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME, ?) = ?"
                + " AND SUA.ROLE_NAME = ?"
                + " AND SUA.USER_ID = SU.USER_ID"
                + " AND SU.ENABLED <> 'N'"
                + " AND ((SU.START_DATE = '' OR SU.START_DATE IS NULL OR SU.START_DATE <= GETDATE()) AND (SU.END_DATE = '' OR SU.END_DATE IS NULL OR SU.END_DATE >= GETDATE()))";
        if (nList != null && !nList.equals("")) {
            sqlStr += " AND SU.LOGIN_NAME IN (" + nList + ")";
        }
        sqlStr += " ORDER BY SUA.GROUP_NAME, SU.DISPLAY_ORDER, SU.USERNAME";
        sqlArgs.add(prj);
        sqlArgs.add(group);
        sqlArgs.add(group);
        sqlArgs.add(role);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据删除SQLModel，请根据实际需要修改。
     *
     * @param loginName 用户登入名
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getSfUserRealnameModel(String loginName) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT *"
                + " FROM"
                + " SF_USER"
                + " WHERE"
                + " LOGIN_NAME= ?";
        sqlArgs.add(loginName);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * @param sfUser
     * @return
     */
    public SQLModel getCheckUserModel(SfUserBaseDTO sfUser) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT * FROM SF_USER SU WHERE UPPER(SU.LOGIN_NAME) = UPPER(?)";
        sqlArgs.add(sfUser.getLoginName().toUpperCase());

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据删除SQLModel，请根据实际需要修改。
     *
     * @param userid 用户登入名
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getSfUserLoginNameModel(String userid) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT *"
                + " FROM"
                + " SF_USER"
                + " WHERE"
                + " USER_ID= ?";
        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(userid);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getDeleteUserDeptMgrModel(int userId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "DELETE FROM AMS_ASSETS_PRIVI \n" +
                        " WHERE USER_ID = ?\n" +
                        "   AND EXISTS (SELECT NULL\n" +
                        "          FROM SF_ROLE SR\n" +
                        "         WHERE (SR.ROLE_NAME = '部门资产管理员' OR SR.ROLE_NAME = '公司资产管理员' OR SR.ROLE_NAME = '全省资产管理员')\n" +
                        "           AND SR.ROLE_ID = AMS_ASSETS_PRIVI.ROLE_ID)";

        sqlArgs.add(userId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getInsertUserDeptMgrModel(int userId, String groupName, String proviceCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "INSERT INTO AMS_ASSETS_PRIVI\n" +
                        "  (USER_ID, DEPT_CODE, ROLE_ID, COMPANY_CODE, PRIVI_ID, PROVINCE_CODE)\n" +
                        "  SELECT SU.USER_ID,\n" +
                        "         SGM.DEPT_ID,\n" +
                        "         SR.ROLE_ID,\n" +
                        "         EOCM.COMPANY_CODE,\n" +
                        "         NEWID() ,\n" +
                        "         '" + proviceCode + "'\n" +
                        "    FROM SF_USER         SU,\n" +
                        "         ETS_OU_CITY_MAP EOCM,\n" +
                        "         SF_GROUP        SG,\n" +
                        "         SINO_GROUP_MATCH  SGM,\n" +
                        "         SF_ROLE         SR\n" +
                        "   WHERE SG.GROUP_ID = SGM.GROUP_ID\n" +
                        "     AND SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                        "     AND SR.ROLE_NAME = ?\n" +
                        "     AND SG.GROUP_NAME = ?\n" +
                        "     AND SU.USER_ID = ?";
        sqlArgs.add("部门资产管理员");
        sqlArgs.add(groupName);
        sqlArgs.add(userId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getInsertUserCompMgrModel(int userId, String proviceCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "INSERT INTO AMS_ASSETS_PRIVI\n" +
                        "  (USER_ID,  ROLE_ID, COMPANY_CODE, PRIVI_ID, PROVINCE_CODE)\n" +
                        "  SELECT SU.USER_ID,\n" +
                        "         SR.ROLE_ID,\n" +
                        "         EOCM.COMPANY_CODE,\n" +
                        "         NEWID() ,\n" +
                        "         '" + proviceCode + "'\n" +
                        "    FROM SF_USER         SU,\n" +
                        "         ETS_OU_CITY_MAP EOCM,\n" +
                        "         SF_ROLE         SR\n" +
                        "   WHERE SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                        "     AND SR.ROLE_NAME = ?\n" +
                        "     AND SU.USER_ID = ?";
        sqlArgs.add("公司资产管理员");
        sqlArgs.add(userId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getInsertUserProviceMgrModel(int userId, String proviceCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "INSERT INTO AMS_ASSETS_PRIVI\n" +
                        "  (USER_ID,  ROLE_ID, COMPANY_CODE, PRIVI_ID, PROVINCE_CODE)\n" +
                        "  SELECT SU.USER_ID,\n" +
                        "         SR.ROLE_ID,\n" +
                        "         EOCM.COMPANY_CODE,\n" +
                        "         NEWID() ,\n" +
                        "         '" + proviceCode + "'\n" +
                        "    FROM SF_USER         SU,\n" +
                        "         ETS_OU_CITY_MAP EOCM,\n" +
                        "         SF_ROLE         SR\n" +
                        "   WHERE SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                        "     AND SR.ROLE_NAME = ?\n" +
                        "     AND SU.USER_ID = ?";
        sqlArgs.add("全省资产管理员");
        sqlArgs.add(userId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
}
