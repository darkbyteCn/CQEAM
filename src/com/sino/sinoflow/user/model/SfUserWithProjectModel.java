package com.sino.sinoflow.user.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.sinoflow.user.dto.SfUserWithProjectDTO;


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


public class SfUserWithProjectModel extends BaseSQLProducer {

    /**
     * 功能：用户维护Model构造函数
     */
    public SfUserWithProjectModel() {
    	super(null,null);
    }

    
    /**
     * 功能：用户维护Model构造函数
     *
     * @param userAccount  BaseUserDTO 代表执行当前操作的用户
     * @param dtoParameter SfUserBaseDTO 代表当前操作的数据
     */
    public SfUserWithProjectModel(SfUserBaseDTO userAccount, SfUserWithProjectDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：框架自动生成页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
    	
        SfUserWithProjectDTO sfUserProj = (SfUserWithProjectDTO) super.dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        sqlStr = "SELECT"
    		+ " CONVERT(INT,SU.USER_ID) USER_ID,"
        	+ " SU.USERNAME,"
        	+ " SU.PASSWORD,"
        	+ " SU.EMPLOYEE_ID,"
        	+ " SU.OFFICE_TEL,"
        	+ " SU.FAX,"
        	+ " SU.MOBILE_PHONE,"
        	+ " SU.EMAIL,"
        	+ " SU.ORGANIZATION,"
        	+ " SU.WORK_SCHEDULE_ID,"
        	+ " SU.LOGIN_NAME "
        	+ " FROM "
        	+ " SF_USER SU"
        	+ " WHERE"
        	+ " (? = '' OR SU.USERNAME LIKE ?)"
        	+ " AND (? = '' OR UPPER(SU.LOGIN_NAME) LIKE UPPER(?))";
        if(!sfUserProj.getProjectName().equals("")) {
            sqlStr += " AND EXISTS"
                    + "   (SELECT USER_ID FROM SF_USER_AUTHORITY SUA"
                    + "    WHERE "
                    + "      (? = '' OR SUA.PROJECT_NAME LIKE ?)"
                    + "      AND (? = '' OR SUA.GROUP_NAME LIKE ?)"
                    + "      AND (? = '' OR SUA.ROLE_NAME LIKE ?)"
                    + "      AND (SUA.USER_ID = SU.USER_ID))";
        }

        sqlArgs.add(sfUserProj.getUsername());
        sqlArgs.add(sfUserProj.getUsername());
        sqlArgs.add(sfUserProj.getLoginName());
        sqlArgs.add(sfUserProj.getLoginName());

        if(!sfUserProj.getProjectName().equals("")) {
            sqlArgs.add(sfUserProj.getProjectName());
            sqlArgs.add(sfUserProj.getProjectName());
            sqlArgs.add(sfUserProj.getGroupName());
            sqlArgs.add(sfUserProj.getGroupName());
            sqlArgs.add(sfUserProj.getRoleName());
            sqlArgs.add(sfUserProj.getRoleName());
        }


        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
