package com.sino.ams.system.trust.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.trust.dto.AmsMaintainCompanyDTO;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: AmsMaintainCompanyModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsMaintainCompanyModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 *    
 * 修改人:wangzhipeng 
 * 日期：2011.04.06
 * 内容:getPageQueryModel 方法 sql语句增加条件: "AND AMC.ORGANIZATION_ID = EC.ORGANIZATION_ID  \n"  公司--->区县   建立关联  
 * 
 */


public class AmsMaintainCompanyModel extends AMSSQLProducer {


    /**
     * 功能：代维公司表(EAM) AMS_MAINTAIN_COMPANY 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsMaintainCompanyDTO 本次操作的数据
     */
    public AmsMaintainCompanyModel(SfUserDTO userAccount, AmsMaintainCompanyDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：框架自动生成代维公司表(EAM) AMS_MAINTAIN_COMPANY数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
		AmsMaintainCompanyDTO dto = (AmsMaintainCompanyDTO)dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO "
                + " AMS_MAINTAIN_COMPANY("
                + " COMPANY_ID,"
                + " NAME,"
                + " ADDRESS,"
                + " LEGAL_REPRESENTATIVE,"
                + " CONTACT_PEOPLE,"
                + " OFFICE_TELEPHONE,"
                + " CONTACT_TELEPHONE,"
                + " FAX_NUMBER,"
                + " ORGANIZATION_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY,"
                + " COUNTY_CODE,"
                + " REMARK"
                + ") VALUES ("
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, " + SyBaseSQLUtil.getCurDate() + ", ?, " + SyBaseSQLUtil.getCurDate() + ",?,?,?)";
        
        sqlArgs.add(dto.getCompanyId());
        sqlArgs.add(dto.getName());
        sqlArgs.add(dto.getAddress());
        sqlArgs.add(dto.getLegalRepresentative());
        sqlArgs.add(dto.getContactPeople());
        sqlArgs.add(dto.getOfficeTelephone());
        sqlArgs.add(dto.getContactTelephone());
        sqlArgs.add(dto.getFaxNumber());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getCountyCode());
        sqlArgs.add(dto.getRemark());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成代维公司表(EAM) AMS_MAINTAIN_COMPANY数据更新SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
		AmsMaintainCompanyDTO dto = (AmsMaintainCompanyDTO)dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_MAINTAIN_COMPANY"
                + " SET"
                + " NAME = ?,"
                + " ADDRESS = ?,"
                + " LEGAL_REPRESENTATIVE = ?,"
                + " CONTACT_PEOPLE = ?,"
                + " OFFICE_TELEPHONE = ?,"
                + " CONTACT_TELEPHONE = ?,"
                + " FAX_NUMBER = ?,"
                + " ORGANIZATION_ID = ?,"
         //       + " CREATION_DATE = ?,"
         //       + " CREATED_BY = ?,"
                + " LAST_UPDATE_DATE = " + SyBaseSQLUtil.getCurDate() + ","
                + " LAST_UPDATE_BY = ?,"
                + " COUNTY_CODE = ?,"
                + " REMARK = ?"
                + " WHERE"
                + " COMPANY_ID = ?";

        sqlArgs.add(dto.getName());
        sqlArgs.add(dto.getAddress());
        sqlArgs.add(dto.getLegalRepresentative());
        sqlArgs.add(dto.getContactPeople());
        sqlArgs.add(dto.getOfficeTelephone());
        sqlArgs.add(dto.getContactTelephone());
        sqlArgs.add(dto.getFaxNumber());
        sqlArgs.add(userAccount.getOrganizationId());
     //   sqlArgs.add(dto.getCreationDate());
     //   sqlArgs.add(dto.getCreatedBy());
     //   sqlArgs.add(dto.getLastUpdateDate());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getCountyCode());
        sqlArgs.add(dto.getRemark());
        sqlArgs.add(dto.getCompanyId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成代维公司表(EAM) AMS_MAINTAIN_COMPANY数据删除SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
		AmsMaintainCompanyDTO dto = (AmsMaintainCompanyDTO)dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM"
						+ " AMS_MAINTAIN_COMPANY"
						+ " WHERE"
						+ " COMPANY_ID = ?";
        sqlArgs.add(dto.getCompanyId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成代维公司表(EAM) AMS_MAINTAIN_COMPANY数据详细信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		AmsMaintainCompanyDTO dto = (AmsMaintainCompanyDTO)dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " COMPANY_ID,"
						+ " NAME,"
						+ " ADDRESS,"
						+ " LEGAL_REPRESENTATIVE,"
						+ " CONTACT_PEOPLE,"
						+ " OFFICE_TELEPHONE,"
						+ " CONTACT_TELEPHONE,"
						+ " FAX_NUMBER,"
						+ " ORGANIZATION_ID,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " LAST_UPDATE_DATE,"
						+ " LAST_UPDATE_BY,"
						+ " COUNTY_CODE,"
						+ " REMARK"
						+ " FROM"
						+ " AMS_MAINTAIN_COMPANY"
						+ " WHERE"
						+ " COMPANY_ID = ?";
        sqlArgs.add(dto.getCompanyId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：框架自动生成代维公司表(EAM) AMS_MAINTAIN_COMPANY页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
		AmsMaintainCompanyDTO dto = (AmsMaintainCompanyDTO)dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT " +
                "        AMC.COMPANY_ID,/*公司代码*/ " +
                "        AMC.NAME, /*公司名称*/\n" +
                "        AMC.CONTACT_PEOPLE, /*联系人*/\n" +
                "        AMC.CONTACT_TELEPHONE, /*联系人电话*/\n" +
                "        AMC.OFFICE_TELEPHONE,/*办公电话*/\n" +
                "        EOCM.COMPANY ,/*所属组织*/\n" +
                "        EC.COUNTY_NAME ,/*所在区县*/\n" +
                "        AMC.REMARK /*备注*/\n" +
                " FROM   ETS_OU_CITY_MAP EOCM, AMS_MAINTAIN_COMPANY AMC,ETS_COUNTY EC\n" +
                " WHERE  EOCM.ORGANIZATION_ID = AMC.ORGANIZATION_ID  \n" +
                " AND    AMC.ORGANIZATION_ID = EC.ORGANIZATION_ID  \n " +        //增加查询条件 公司--->区县 关联
                " AND    ( EC.COUNTY_CODE_MIS =AMC.COUNTY_CODE OR EC.COUNTY_CODE =AMC.COUNTY_CODE )\n" +
                " AND    (" + SyBaseSQLUtil.nullStringParam() + " OR  AMC.CONTACT_PEOPLE LIKE  ? )\n" +
                " AND   (" + SyBaseSQLUtil.nullStringParam() + " OR  AMC.NAME LIKE  ? )\n" +
                " AND   (" + SyBaseSQLUtil.nullStringParam() + " OR ?='-1' OR  AMC.COUNTY_CODE = ?) \n"+
                " AND    EOCM.ORGANIZATION_ID = ? ";

       // System.out.println("代维公司=="+sqlStr);
        SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getContactPeople() );
        SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getName() );
       
        sqlArgs.add(dto.getCountyCode());
        sqlArgs.add(dto.getCountyCode());
        sqlArgs.add(dto.getCountyCode());
        sqlArgs.add(dto.getCountyCode());
//        sqlArgs.add(dto.getContactPeople());
//        sqlArgs.add(dto.getContactPeople());
//        sqlArgs.add(dto.getName());
//        sqlArgs.add(dto.getName());
//        sqlArgs.add(dto.getCountyCode());
//        sqlArgs.add(dto.getCountyCode());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}

}
