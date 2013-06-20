package com.sino.ams.system.trust.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.trust.dto.AmsMaintainPeopleDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: AmsMaintainPeopleModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsMaintainPeopleModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsMaintainPeopleModel extends BaseSQLProducer {

    private AmsMaintainPeopleDTO amsMaintainPeople = null;
    private SfUserDTO SfUser = null;

    /**
     * 功能：代维人员表(EAM) AMS_MAINTAIN_PEOPLE 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsMaintainPeopleDTO 本次操作的数据
     */
    public AmsMaintainPeopleModel(SfUserDTO userAccount, AmsMaintainPeopleDTO dtoParameter) {
        super(userAccount, dtoParameter);
        SfUser = userAccount;
        this.amsMaintainPeople = (AmsMaintainPeopleDTO) dtoParameter;
    }

    /**
     * 功能：框架自动生成代维人员表(EAM) AMS_MAINTAIN_PEOPLE数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = 
        		 "INSERT INTO "
                + " AMS_MAINTAIN_PEOPLE("
                + " USER_ID,"
                + " USER_NAME,"
                + " USER_TELEPHONE,"
                + " USER_MOBILE_PHONE,"
                + " EMAIL,"
                + " BP_NUMBER,"
                + " COMPANY_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + ") VALUES ("
                + "  ?, ?, ?, ?, ?, ?, ?, " +  SyBaseSQLUtil.getCurDate() + " , ?,  " +  SyBaseSQLUtil.getCurDate() + " , ?)";
       sqlArgs.add(amsMaintainPeople.getUserId());
        sqlArgs.add(amsMaintainPeople.getUserName());
        sqlArgs.add(amsMaintainPeople.getUserTelephone());
        sqlArgs.add(amsMaintainPeople.getUserMobilePhone());
        sqlArgs.add(amsMaintainPeople.getEmail());
        sqlArgs.add(amsMaintainPeople.getBpNumber());
        sqlArgs.add(amsMaintainPeople.getCompanyId()+"");
        sqlArgs.add(SfUser.getUserId());
        sqlArgs.add(SfUser.getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成代维人员表(EAM) AMS_MAINTAIN_PEOPLE数据更新SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_MAINTAIN_PEOPLE"
                + " SET"
                + " USER_NAME = ?,"
                + " USER_TELEPHONE = ?,"
                + " USER_MOBILE_PHONE = ?,"
                + " EMAIL = ?,"
                + " BP_NUMBER = ?,"
                + " COMPANY_ID = ?,"
                /*	+ " CREATION_DATE = ?,"
                + " CREATED_BY = ?,"*/
                + " LAST_UPDATE_DATE =  " +  SyBaseSQLUtil.getCurDate() + "  ,"
                + " LAST_UPDATE_BY = ?"
                + " WHERE"
                + " USER_ID = ?";

        sqlArgs.add(amsMaintainPeople.getUserName());
        sqlArgs.add(amsMaintainPeople.getUserTelephone());
        sqlArgs.add(amsMaintainPeople.getUserMobilePhone());
        sqlArgs.add(amsMaintainPeople.getEmail());
        sqlArgs.add(amsMaintainPeople.getBpNumber());
        sqlArgs.add(amsMaintainPeople.getCompanyId()+"");
/*		sqlArgs.add(amsMaintainPeople.getCreationDate());
		sqlArgs.add(amsMaintainPeople.getCreatedBy());
		sqlArgs.add(amsMaintainPeople.getLastUpdateDate());*/
        sqlArgs.add(SfUser.getUserId());
        sqlArgs.add(amsMaintainPeople.getUserId()+"");

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成代维人员表(EAM) AMS_MAINTAIN_PEOPLE数据删除SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_MAINTAIN_PEOPLE"
                + " WHERE"
                + " USER_ID = ?";
        sqlArgs.add(amsMaintainPeople.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成代维人员表(EAM) AMS_MAINTAIN_PEOPLE数据详细信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " USER_ID,"
                + " USER_NAME,"
                + " USER_TELEPHONE,"
                + " USER_MOBILE_PHONE,"
                + " EMAIL,"
                + " BP_NUMBER,"
                + " COMPANY_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " AMS_MAINTAIN_PEOPLE"
                + " WHERE"
                + " USER_ID = ?";
        sqlArgs.add(amsMaintainPeople.getUserId()+"");

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成代维人员表(EAM) AMS_MAINTAIN_PEOPLE多条数据信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getDataMuxModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " USER_ID,"
                + " USER_NAME,"
                + " USER_TELEPHONE,"
                + " USER_MOBILE_PHONE,"
                + " EMAIL,"
                + " BP_NUMBER,"
                + " COMPANY_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " AMS_MAINTAIN_PEOPLE"
                + " WHERE"
                + "( " + SyBaseSQLUtil.isNull() + "  OR USER_ID LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR USER_NAME LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR USER_TELEPHONE LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR USER_MOBILE_PHONE LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR EMAIL LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR BP_NUMBER LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR COMPANY_ID LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
        sqlArgs.add(amsMaintainPeople.getUserId());
        sqlArgs.add(amsMaintainPeople.getUserId());
        sqlArgs.add(amsMaintainPeople.getUserName());
        sqlArgs.add(amsMaintainPeople.getUserName());
        sqlArgs.add(amsMaintainPeople.getUserTelephone());
        sqlArgs.add(amsMaintainPeople.getUserTelephone());
        sqlArgs.add(amsMaintainPeople.getUserMobilePhone());
        sqlArgs.add(amsMaintainPeople.getUserMobilePhone());
        sqlArgs.add(amsMaintainPeople.getEmail());
        sqlArgs.add(amsMaintainPeople.getEmail());
        sqlArgs.add(amsMaintainPeople.getBpNumber());
        sqlArgs.add(amsMaintainPeople.getBpNumber());
        sqlArgs.add(amsMaintainPeople.getCompanyId());
        sqlArgs.add(amsMaintainPeople.getCompanyId());
        sqlArgs.add(amsMaintainPeople.getCreationDate());
        sqlArgs.add(amsMaintainPeople.getCreationDate());
        sqlArgs.add(amsMaintainPeople.getCreatedBy());
        sqlArgs.add(amsMaintainPeople.getCreatedBy());
        sqlArgs.add(amsMaintainPeople.getLastUpdateDate());
        sqlArgs.add(amsMaintainPeople.getLastUpdateDate());
        sqlArgs.add(amsMaintainPeople.getLastUpdateBy());
        sqlArgs.add(amsMaintainPeople.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成代维人员表(EAM) AMS_MAINTAIN_PEOPLE页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "   SELECT \n" +
                " AMP.USER_ID USER_ID , \n" +
                " AMP.USER_NAME USER_NAME, \n" +
                " AMP.USER_TELEPHONE USER_TELEPHONE, \n" +
                " AMP.USER_MOBILE_PHONE USER_MOBILE_PHONE, \n" +
                " AMP.EMAIL EMAIL, \n" +
                " AMC.NAME COMPANY_NAME \n" +
                " FROM \n" +
                " AMS_MAINTAIN_COMPANY AMC, \n" +
                " AMS_MAINTAIN_PEOPLE  AMP \n" +
                " WHERE \n" +
                " AMC.COMPANY_ID = AMP.COMPANY_ID  \n " +
                " AND (" + SyBaseSQLUtil.nullStringParam() + " OR AMP.USER_NAME LIKE ?) \n" +
                " AND (" + SyBaseSQLUtil.nullStringParam() + " OR ?='-1' OR AMP.COMPANY_ID = ? ) \n" +
                " AND AMC.ORGANIZATION_ID = ? \n ";

        SyBaseSQLUtil.nullStringParamArgs(sqlArgs, amsMaintainPeople.getUserName() );
        sqlArgs.add(amsMaintainPeople.getCompanyId()+"");
        sqlArgs.add(amsMaintainPeople.getCompanyId()+"");
        sqlArgs.add(amsMaintainPeople.getCompanyId()+"");
        sqlArgs.add(amsMaintainPeople.getCompanyId()+"");
        
//        sqlArgs.add(amsMaintainPeople.getUserName());
//        sqlArgs.add(amsMaintainPeople.getUserName());
//        sqlArgs.add(amsMaintainPeople.getCompanyId());
//        sqlArgs.add(amsMaintainPeople.getCompanyId());
        sqlArgs.add(SfUser.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}