package com.sino.appbase.help.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.appbase.help.bean.HelpIDProducer;
import com.sino.appbase.help.dto.HelpDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.sinoflow.appbase.model.SFSQLProducer;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

public class HelpProcessModel extends SFSQLProducer {

    /**
     * 功能：SINO_HELP_DEFINE 数据库SQL构造层构造函数
     * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
     * @param dtoParameter SfResDefineDTO 本次操作的数据
     */
    public HelpProcessModel(SfUserBaseDTO userAccount, HelpDTO dtoParameter) {
        super(userAccount, dtoParameter);
        dtoParameter.setCreatedBy(userAccount.getUserId());
    }

    /**
     * 功能：框架自动生成数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        HelpDTO dto = (HelpDTO)dtoParameter;
        String sqlStr = "INSERT INTO "
                        + " SINO_HELP_DEFINE("
                        + " RES_ID,"
                        + " RES_PAR_ID,"
                        + " RES_NAME,"
                        + " RES_URL,"
                        + " SORT_NO,"
                        + " IS_POPUP,"
                        + " PRINCIPAL,"
                        + " ENABLED,"
                        + " IS_INNER,"
                        + " VISIBLE,"
                        + " POPSCRIPT,"
                        + " LEVEL_NUM,"
                        + " BUSINESS_DESC,"
                        + " CREATION_DATE,"
                        + " CREATED_BY"
                        + ") VALUES ("
                        + " dbo.SRP_GET_NEXT_RESID(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        sqlArgs.add(dto.getResParId());
        sqlArgs.add(dto.getResParId());
        sqlArgs.add(dto.getResName());
        sqlArgs.add(dto.getResUrl());
        sqlArgs.add(dto.getSortNo());
        sqlArgs.add(dto.getIsPopup());
        sqlArgs.add(dto.getPrincipal());
        sqlArgs.add(dto.getEnabled());
        sqlArgs.add(dto.getIsInner());
        sqlArgs.add(dto.getVisible());
        sqlArgs.add(dto.getPopscript());
        sqlArgs.add(dto.getLevelNum());
        sqlArgs.add(dto.getBusinessDesc());
        sqlArgs.add(dto.getCreationDate());
        sqlArgs.add(dto.getCreatedBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        HelpDTO dto = (HelpDTO)dtoParameter;
        String sqlStr = "UPDATE SINO_HELP_DEFINE"
                        + " SET"
                        + " RES_ID = ?,"
                        + " RES_PAR_ID = ?,"
                        + " RES_NAME = ?,"
                        + " RES_URL = ?,"
                        + " SORT_NO = ?,"
                        + " IS_POPUP = ?,"
                        + " PRINCIPAL = ?,"
                        + " ENABLED = ?,"
                        + " IS_INNER = ?,"
                        + " VISIBLE = ?,"
                        + " POPSCRIPT = ?,"
                       // + " LEVEL_NUM = ?,"
                        + " BUSINESS_DESC = ?,"
                        + " LAST_UPDATE_DATE = ?,"
                        + " LAST_UPDATE_BY = ?"
                        + " WHERE"
                        + " SYSTEM_ID = ?";
        sqlArgs.add(dto.getResId());
        sqlArgs.add(dto.getResParId());
        sqlArgs.add(dto.getResName());
        sqlArgs.add(dto.getResUrl());
        sqlArgs.add(dto.getSortNo());
        sqlArgs.add(dto.getIsPopup());
        sqlArgs.add(dto.getPrincipal());
        sqlArgs.add(dto.getEnabled());
        sqlArgs.add(dto.getIsInner());
        sqlArgs.add(dto.getVisible());
        sqlArgs.add(dto.getPopscript());
        //sqlArgs.add(dto.getLevelNum());
        sqlArgs.add(dto.getBusinessDesc());
        Date date=new Date();
        sqlArgs.add(String.format("%tY",date)+"-"+String.format("%tm",date)+"-"+String.format("%td",date));
        sqlArgs.add(dto.getLastUpdateBy());
        sqlArgs.add(dto.getSystemId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        HelpDTO dto = (HelpDTO)dtoParameter;
        String sqlStr = "DELETE FROM"
                        + " SINO_HELP_DEFINE"
                        + " WHERE"
                        + " SYSTEM_ID = ?";
        sqlArgs.add(dto.getSystemId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        HelpDTO dto = (HelpDTO)dtoParameter;
        String sqlStr = "SELECT"
                        + " *"
                        + " FROM"
                        + " SINO_HELP_DEFINE"
                        + " WHERE"
                        + " SYSTEM_ID = ?";
        sqlArgs.add(dto.getSystemId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        HelpDTO dto = (HelpDTO)dtoParameter;
        String sqlStr = "SELECT"
                        + " A.SYSTEM_ID,"
                        + " A.RES_ID,"
                        + " A.RES_PAR_ID,"
                        + " A.RES_NAME,"
                        + " A.RES_URL,"
                        + " ( CASE A.ENABLED WHEN 'Y' THEN '<font color=green>有效</font>' ELSE '<font color=red>无效</font>' END ) ENABLED,"
                        + " ( CASE A.VISIBLE WHEN 'Y' THEN '<font color=green>可见</font>' ELSE '<font color=red>不可见</font>' END ) VISIBLE,"
                        + " B.RES_NAME  PAR_NAME"
                        + " FROM"
                        + " SINO_HELP_DEFINE A"
                        + " LEFT OUTER JOIN SINO_HELP_DEFINE B ON (A.RES_PAR_ID = B.RES_ID)"
                        + " WHERE"
                        + " (? = '' OR A.ENABLED = ?)"
                        + " AND (? = '' OR A.VISIBLE = ?)"
                        + " AND (? = '' OR A.RES_NAME LIKE ?)"
                        + " AND (? = '' OR A.RES_URL LIKE ?)"
                        + " AND (? = '' OR (A.RES_ID LIKE ? OR A.RES_ID = ?))"
                        ;
        sqlArgs.add(dto.getEnabled());
        sqlArgs.add(dto.getEnabled());
        sqlArgs.add(dto.getVisible());
        sqlArgs.add(dto.getVisible());
        sqlArgs.add(dto.getResName());
        sqlArgs.add(dto.getResName());
        sqlArgs.add(dto.getResUrl());
        sqlArgs.add(dto.getResUrl());
        sqlArgs.add(dto.getResId());
        sqlArgs.add(dto.getResId() + ".%");
        sqlArgs.add(dto.getResId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：获取URL资源菜单栏目的下拉列表SQL。
     * 不显示指定资源极其子资源
     * @param resourceId 指定资源
     * @return SQLModel
     */
    public SQLModel getResourceOptionModel(String resourceId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                        + " SRD.RES_ID,"
                        + " SRD.RES_NAME"
                        + " FROM"
                        + " SINO_HELP_DEFINE SRD"
                        + " WHERE"
                        + " (SRD.RES_ID <> ? "
                        + " AND SRD.RES_ID NOT LIKE ?)"
                        + " OR ? = ''"
                        + " ORDER  BY"
                        + " SRD.RES_ID,"
                        + " SRD.RES_PAR_ID";
        sqlArgs.add(resourceId);
        sqlArgs.add(resourceId + ".%");
        sqlArgs.add(resourceId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：产生URL资源树的SQL。栏目定义用
     * @return SQLModel
     */
    public SQLModel getResourceTreeModel() { 
    	SQLModel sqlModel = new SQLModel(); 
	    String sqlStr  = " SELECT "
	      			   + " A.RES_ID ID1, "
	      			   + " A.RES_NAME TEXT1, "
	      			   + " A2.RES_ID ID2, "
	      			   + " A2.RES_NAME TEXT2, "
	      			   + " A3.RES_ID ID3, "
	      			   + " A3.RES_NAME TEXT3, "
	      			   + " A4.RES_ID ID4, "
	      			   + " A4.RES_NAME TEXT4 "
	      			   + " FROM "
	      			   + " SINO_HELP_DEFINE A, "
	      			   + " SINO_HELP_DEFINE A2, "
	      			   + " SINO_HELP_DEFINE A3, "
	      			   + " SINO_HELP_DEFINE A4 "
	      			   + " WHERE 1 = 1"
	      			   //+ " AND A4.RES_PAR_ID = A3.RES_ID "
	      			   + " AND A.RES_ID *= A2.RES_PAR_ID "
	      			   + " AND A2.RES_ID *= A3.RES_PAR_ID "
	      			   + " AND A3.RES_ID *= A4.RES_PAR_ID "

	      			   + " AND DATALENGTH(A.RES_ID) <= 1" //权宜条件，否则生成的树节点层次会有重叠
	      			   
	      			   + " ORDER BY A.RES_ID, A2.RES_ID , A3.RES_ID, A4.RES_ID ";
/*	    		 " SELECT "
			   + " A.RES_ID ID1, "
			   + " A.RES_NAME TEXT1, "
			   + " A2.RES_ID ID2, "
			   + " A2.RES_NAME TEXT2 "
			   + " FROM "
			   + " SINO_HELP_DEFINE A, "
			   + " SINO_HELP_DEFINE A2 "
			   + " WHERE 1 = 1 "
			   + " AND A2.RES_PAR_ID = A.RES_ID "
			   + " AND datalength(A.RES_ID) <= 3 "
			   + " ORDER BY A.RES_ID, A2.RES_ID ";*/

	    sqlModel.setSqlStr(sqlStr); 
	    return sqlModel; 
    }

    /**
     * 功能：获取指定父资源下的新一个资源编号SQL
     * @param resourcePid String
     * @return SQLModel
     */
    public SQLModel getAllChildModel(String resourcePid) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT"
                        + " *"
                        + " FROM"
                        + " SINO_HELP_DEFINE SRD"
                        + " WHERE"
                        + " SRD.RES_ID LIKE ?"
                        + " ORDER BY"
                        + " RES_ID,"
                        + " RES_PAR_ID";
        sqlArgs.add(resourcePid + ".%");
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：获取指定父资源下的新一个资源编号SQL
     * @param resourcePid String
     * @return SQLModel
     */
    public SQLModel getNewChildModel(String resourcePid) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT"
                        + " dbo.SRP_GET_NEXT_RESID(?) RES_ID";
        sqlArgs.add(resourcePid);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：获取指定父资源下的新一个资源失效SQL
     * @return SQLModel
     */
    public SQLModel getPassviateResourceModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        HelpDTO dto = (HelpDTO)dtoParameter;
        String sqlStr = "UPDATE SINO_HELP_DEFINE"
                        + " SET"
                        + " ENABLED = ?"
                        + " WHERE"
                        + " RES_ID = ?"
                        + " OR RES_ID LIKE ?";
        sqlArgs.add(dto.getEnabled());
        sqlArgs.add(dto.getResId());
        sqlArgs.add(dto.getResId() + ".%");
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：获取指定资源的所有直系上级栏目资源的生效SQL。
     * @return SQLModel
     */
    public SQLModel getActivateResourceModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        HelpDTO dto = (HelpDTO)dtoParameter;
        String sqlStr = "UPDATE SINO_HELP_DEFINE"
                        + " SET"
                        + " ENABLED = ?"
                        + " WHERE"
                        + " ? LIKE RES_ID||'%'";
        sqlArgs.add(dto.getEnabled());
        sqlArgs.add(dto.getResId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据资源ID获取URL资源SQL。
     * @return SQLModel
     */
    public SQLModel getResourceByIdModel(String helpCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        HelpDTO dto = (HelpDTO)dtoParameter;
        String sqlStr = "SELECT"
                        + " SRD.*,"
                        + " dbo.HELP_GET_TREE_ID(SRD.RES_ID) TREE_ID,"
                        + " dbo.SRP_HAS_CHILD(SRD.RES_ID) HAS_CHILD"
                        + " FROM"
                        + " SINO_HELP_DEFINE SRD"
                        + " WHERE"
                        + " SRD.ENABLED = ?"
                        + " AND SRD.VISIBLE = ?"
                        + " AND SRD.RES_ID = ?";
        sqlArgs.add(WebAttrConstant.TRUE_VALUE);
        sqlArgs.add(WebAttrConstant.TRUE_VALUE);
        //sqlArgs.add(dto.getResId());
        sqlArgs.add(helpCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：根据资源ResId获取helpCode。
     * @return SQLModel
     */
    public SQLModel getHelpCodeByResIdModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        HelpDTO dto = (HelpDTO)dtoParameter;
        String sqlStr = "SELECT"
			            + " SHD.RES_URL, \n"
			            + " dbo.HELP_GET_TREE_ID(SHD.RES_ID) TREE_ID \n"
			            + " FROM"
			            + " SINO_HELP_DEFINE SHD"
			            + " WHERE 1 = 1"
			            + " AND SHD.ENABLED = ?"
			            + " AND SHD.VISIBLE = ?"
			            + " AND SHD.RES_ID = ?";
        sqlArgs.add(WebAttrConstant.TRUE_VALUE);
        sqlArgs.add(WebAttrConstant.TRUE_VALUE);
        sqlArgs.add(dto.getResId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：根据helpKeyName获取URL资源SQL。
     * @return SQLModel
     */
    public SQLModel getResourceByHelpKeyNameModel(String helpKeyName) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        HelpDTO dto = (HelpDTO)dtoParameter;
        String sqlStr = "SELECT TOP 1 "
                        + " SRD.*,"
                        + " dbo.HELP_GET_TREE_ID(SRD.RES_ID) TREE_ID, "
                        + " dbo.SRP_HAS_CHILD(SRD.RES_ID) HAS_CHILD"
                        + " FROM"
                        + " SINO_HELP_DEFINE SRD"
                        + " WHERE"
                        + " SRD.ENABLED = ?"
                        + " AND SRD.VISIBLE = ?"
                        + " AND SRD.RES_NAME LIKE '%' || ? || '%'";
        sqlArgs.add(WebAttrConstant.TRUE_VALUE);
        sqlArgs.add(WebAttrConstant.TRUE_VALUE);
        sqlArgs.add(helpKeyName);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
}
