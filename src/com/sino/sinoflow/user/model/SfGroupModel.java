package com.sino.sinoflow.user.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.util.StrUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.user.dto.SfGroupDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;



/**
 * <p>Title: SfGroupModel</p>
 * <p>Description:程序自动生成SQL构造器“SfGroupModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 *
 * 修改人：白嘉
 * 修改日期：2008.8.22
 */


public class SfGroupModel extends BaseSQLProducer {

	private SfGroupDTO sfGroup = null;

	/**
	 * 功能：SF_GROUP 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfGroupDTO 本次操作的数据
	 */
	public SfGroupModel(SfUserBaseDTO userAccount, SfGroupDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.sfGroup = dtoParameter;
	}
	/**
	 * 功能：框架自动生成数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO "
			+ " SF_GROUP("
			+ " PROJECT_NAME,"
			+ " GROUP_NAME,"
			+ " PARENT_ID,"
			+ " GROUP_DESC,"
			+ " ENABLED"
			+ ") VALUES ("
			+ " ?, ?, ?, ?, ?)";
		sqlArgs.add(sfGroup.getProjectName());
		sqlArgs.add(sfGroup.getGroupName());
		sqlArgs.add(sfGroup.getParentId());
		sqlArgs.add(sfGroup.getGroupDesc());
		sqlArgs.add(sfGroup.getEnabled());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	/**
	 * 功能：框架自动生成数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE SF_GROUP"
			+ " SET"
			+ " PROJECT_NAME = ?,"
			+ " GROUP_NAME = ?,"
			+ " PARENT_ID = ?,"
			+ " GROUP_DESC = ?,"
			+ " ENABLED = ?"
			+ " WHERE"
			+ " GROUP_ID = ?";

		sqlArgs.add(sfGroup.getProjectName());
		sqlArgs.add(sfGroup.getGroupName());
		sqlArgs.add(sfGroup.getParentId());
		sqlArgs.add(sfGroup.getGroupDesc());
		sqlArgs.add(sfGroup.getEnabled());
		sqlArgs.add(sfGroup.getGroupId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE SF_GROUP"
			+ " SET"
			+ " ENABLED = 'N'"
			+ " WHERE"
			+ " GROUP_ID = ?";
		sqlArgs.add(sfGroup.getGroupId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " PROJECT_NAME,"
			+ " GROUP_ID,"
			+ " GROUP_NAME,"
			+ " PARENT_ID,"
			+ " dbo.CMS_SF_PROJECT_FUNCTION(PARENT_ID) PARENT_NAME,"
			+ " GROUP_DESC,"
			+ " ENABLED"
			+ " FROM"
			+ " SF_GROUP"
			+ " WHERE"
			+ " GROUP_ID = ?";
		sqlArgs.add(sfGroup.getGroupId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getDataMuxModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " PROJECT_NAME,"
			+ " GROUP_ID,"
			+ " GROUP_NAME,"
			+ " PARENT_ID,"
			+ " GROUP_DESC,"
			+ " ENABLED"
			+ " FROM"
			+ " SF_GROUP";
			/*+ " WHERE"
			+ " GROUP_ID = ?"
			+ " GROUP_NAME = ?"
			+ " GROUP_DESC = ?"
			+ " ENABLED = ?";
		sqlArgs.add(sfGroup.getGroupId());
		sqlArgs.add(sfGroup.getGroupCode());
		sqlArgs.add(sfGroup.getGroupName());
		sqlArgs.add(sfGroup.getGroupPid());
		sqlArgs.add(sfGroup.getOrgId());
		sqlArgs.add(sfGroup.getSortno());
		sqlArgs.add(sfGroup.getIsroot());
		sqlArgs.add(sfGroup.getCategory());
		sqlArgs.add(sfGroup.getEnabled());
		sqlArgs.add(sfGroup.getIsInner());
		sqlArgs.add(sfGroup.getCreationDate());
		sqlArgs.add(sfGroup.getCreatedBy());
		sqlArgs.add(sfGroup.getLastUpdateDate());
		sqlArgs.add(sfGroup.getLastUpdateBy());
		sqlArgs.add(sfGroup.getIsDesigner());
		sqlArgs.add(sfGroup.getpFlowId());
*/
		sqlModel.setSqlStr(sqlStr);
	//	sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 createdBy 构造查询数据SQL。
	 * 框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
	 * @param createdBy String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByCreatedByModel(int createdBy){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " GROUP_ID,"
			+ " GROUP_CODE,"
			+ " GROUPNAME,"
			+ " GROUP_PID,"
			+ " ORG_ID,"
			+ " SORTNO,"
			+ " ISROOT,"
			+ " CATEGORY,"
			+ " ENABLED,"
			+ " IS_INNER,"
			+ " CREATION_DATE,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " IS_DESIGNER,"
			+ " P_FLOW_ID"
			+ " FROM"
			+ " SF_GROUP"
			+ " WHERE"
			+ " CREATED_BY = ?";
		sqlArgs.add(createdBy);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 lastUpdateBy 构造查询数据SQL。
	 * 框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
	 * @param lastUpdateBy String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByLastUpdateByModel(int lastUpdateBy){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " GROUP_ID,"
			+ " GROUP_CODE,"
			+ " GROUPNAME,"
			+ " GROUP_PID,"
			+ " ORG_ID,"
			+ " SORTNO,"
			+ " ISROOT,"
			+ " CATEGORY,"
			+ " ENABLED,"
			+ " IS_INNER,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " IS_DESIGNER,"
			+ " P_FLOW_ID"
			+ " FROM"
			+ " SF_GROUP"
			+ " WHERE"
			+ " LAST_UPDATE_BY = ?";
		sqlArgs.add(lastUpdateBy);

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
		String sqlStr = "SELECT  "
				+ " PROJECT_NAME, "
				+ " CONVERT( INT , GROUP_ID )  GROUP_ID, "
				+ " GROUP_NAME, "
				+ " PARENT_ID, "
				+ " dbo.NVL(dbo.CMS_SF_PROJECT_FUNCTION(PARENT_ID),'') PARENT_NAME,"
				+ " GROUP_DESC,"
				+ " ENABLED"
				+ " FROM"
				+ " SF_GROUP"
				+ " WHERE (? = '' OR ? IS NULL OR PROJECT_NAME LIKE ?)"
				+ " AND (? <= 0 OR GROUP_ID = ?)"
				+ " AND (? = '' OR ? IS NULL OR GROUP_NAME LIKE ?)"
				//+ " AND (? = '' OR ENABLED LIKE ?)"
				+ " AND (? <= 0 OR PARENT_ID = ?) "
				+ " AND (? = '' OR ? IS NULL OR GROUP_DESC LIKE ?)"
				+ " AND (? = '' OR ENABLED LIKE ?)";

		sqlArgs.add(sfGroup.getProjectName());
		sqlArgs.add(sfGroup.getProjectName());
		sqlArgs.add(sfGroup.getProjectName());
		
		
		sqlArgs.add(sfGroup.getGroupId());
		sqlArgs.add(sfGroup.getGroupId());
		
		sqlArgs.add(sfGroup.getGroupName());
		sqlArgs.add(sfGroup.getGroupName());
		sqlArgs.add(sfGroup.getGroupName());
		
		//sqlArgs.add(sfGroup.getEnabled());
		//sqlArgs.add(sfGroup.getEnabled());
		sqlArgs.add(sfGroup.getParentId());
		sqlArgs.add(sfGroup.getParentId());
		sqlArgs.add(sfGroup.getGroupDesc());
		sqlArgs.add(sfGroup.getGroupDesc());
		sqlArgs.add(sfGroup.getGroupDesc());
		
		sqlArgs.add(sfGroup.getEnabled());
		sqlArgs.add(sfGroup.getEnabled());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：得到组的下列表
	 * @param pName 工程名
	 * @param orgId 组织ID
     * @return SQLModel
	 */
	public SQLModel getOptionGroupModel(String pName, int orgId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();

		String sqlStr = "SELECT "
						+ " SG.GROUP_NAME,"
						+ " SG.GROUP_NAME,"
						+ " SG.GROUP_ID"
						+ " FROM"
						+ " SF_GROUP SG"
						+ " WHERE"
						+ " SG.PROJECT_NAME = ?"
                        + " AND SG.ENABLED = 'Y'"
                        + " AND EXISTS("
                        + " SELECT"
                        + " NULL"
                        + " FROM"
                        + " SINO_MIS_DEPT    SMD,"
                        + " SINO_GROUP_MATCH SGM"
                        + " WHERE"
                        + " SG.GROUP_ID = SGM.GROUP_ID"
                        + " AND SGM.DEPT_ID = SMD.DEPT_ID"
                        + " AND (" + SyBaseSQLUtil.isNull() + " OR SMD.ORG_ID = ?))"
                        + " ORDER BY SG.GROUP_NAME";
		sqlArgs.add(pName);
        sqlArgs.add(orgId);
        sqlArgs.add(orgId);

        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

    public SQLModel getOptionGroupModel(String pName, String orgId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "SELECT "
                        + " SG.GROUP_NAME,"
                        + " SG.GROUP_NAME,"
                        + " SG.GROUP_ID"
                        + " FROM"
                        + " SF_GROUP SG"
                        + " WHERE"
                        + " SG.PROJECT_NAME = ?"
                        + " AND SG.ENABLED = 'Y'"
                        + " AND EXISTS("
                        + " SELECT"
                        + " NULL"
                        + " FROM"
                        + " SINO_MIS_DEPT    SMD,"
                        + " SINO_GROUP_MATCH SGM"
                        + " WHERE"
                        + " SG.GROUP_ID = SGM.GROUP_ID"
                        + " AND SGM.DEPT_ID = SMD.DEPT_ID"
                        + " AND (? <= 0 OR SMD.ORG_ID = ?))"
                        + " ORDER BY SG.GROUP_NAME";
        sqlArgs.add(pName);
        sqlArgs.add(StrUtil.strToInt(orgId));
        sqlArgs.add(StrUtil.strToInt(orgId));

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getOptionDeptModel( String orgId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AMD.DEPT_NAME,AMD.DEPT_NAME, AMD.DEPT_CODE\n" +
                "  FROM AMS_MIS_DEPT AMD, ETS_OU_CITY_MAP EOCM\n" +
                " WHERE AMD.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
                "   AND EOCM.ORGANIZATION_ID = ?";
        sqlArgs.add(StrUtil.strToInt(orgId));
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    
    /**
	 * 功能：得到组的下列表
	 * @param pName 工程名
     * @return SQLModel
	 */
	public SQLModel getOptionGroupModel2(String pName) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();

		String sqlStr = "SELECT "
						+ " GROUP_ID,"
						+ " GROUP_NAME"
						+ " FROM"
						+ " SF_GROUP"
						+ " WHERE"
						+ " PROJECT_NAME = ?"
						+ " AND ENABLED LIKE 'Y'"
						+ " ORDER BY GROUP_NAME";
		sqlArgs.add(pName);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
	
	/**
	 * 功能：得到组的下列表
	 * @param pName 工程名
     * @return SQLModel
	 */
	public SQLModel getOptionGroupModel(String pName) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();

		String sqlStr = "SELECT "
						+ " GROUP_ID,"
						+ " GROUP_NAME"
						+ " FROM"
						+ " SF_GROUP"
						+ " WHERE"
						+ " PROJECT_NAME = ?"
						+ " AND ENABLED LIKE 'Y'"
						+ " ORDER BY GROUP_NAME";
		sqlArgs.add(pName);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

    public SQLModel getOptionDeptUserModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "SELECT "
                        + " SU2.USERNAME || '\\' || SMD.DEPT_NAME USERNAME,"
                        + " SU2.LOGIN_NAME"
                        + " FROM"
                        + " SF_USER SU, SF_USER SU2, SINO_MIS_DEPT SMD"
                        + " WHERE"
                        + " SU.LOGIN_NAME = ?"
                        + " AND SU.DEPT_CODE = SMD.DEPT_ID"
                        + " AND SU2.DEPT_CODE = SMD.DEPT_ID"
                        + " AND SU2.ENABLED = 'Y'"
                        + " ORDER BY SU.DISPLAY_ORDER";
        sqlArgs.add(((SfUserBaseDTO)userAccount).getLoginName());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getOptionFlowUserModel(String actId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "SELECT DISTINCT SU.LOGIN_NAME, SU.USERNAME + '\\' + SMD.DEPT_NAME USERNAME, SU.DISPLAY_ORDER" +
                "  FROM SF_USER SU, SF_ACT_INFO SAI, SF_ACT_LOG SAL, SINO_MIS_DEPT SMD" +
                " WHERE SAI.SFACT_ACT_ID = ? AND SAI.SFACT_CASE_ID = SAL.SFACT_CASE_ID" +
                " AND SAL.SFACT_SIGN_USER = SU.LOGIN_NAME AND SAL.SFACT_SIGN_USER <> 'SYSTEM'" +
                " AND SU.DEPT_CODE = SMD.DEPT_ID" +
                " ORDER BY SU.DISPLAY_ORDER";
        sqlArgs.add(actId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
}
