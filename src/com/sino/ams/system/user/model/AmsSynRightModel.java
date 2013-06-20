package com.sino.ams.system.user.model;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.dto.AmsSynRightDTO;
import com.sino.base.db.sql.model.SQLModel;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2009-6-12
 * Time: 11:19:40
 * To change this template use File | Settings | File Templates.
 */
public class AmsSynRightModel extends BaseSQLProducer {

	private AmsSynRightDTO dto = null;
	private SfUserDTO sfUser=null;

	/**
	 * 功能：SF_GROUP 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfGroupDTO 本次操作的数据
	 */
	public AmsSynRightModel(SfUserDTO userAccount, AmsSynRightDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.dto = (AmsSynRightDTO)dtoParameter;
		this.sfUser=userAccount;
	}
	/**
	 * 功能：框架自动生成数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";


		sqlArgs.add(dto.getUserId());
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
		String sqlStr = "";


		sqlArgs.add(sfUser.getUserId());


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
		String sqlStr = "";
		sqlArgs.add(dto.getSynRightId());
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
		String sqlStr ="SELECT SU.LOGIN_NAME,\n" +
                "       SU.USERNAME USER_NAME,\n" +
                "       SU.EMPLOYEE_NUMBER,\n" +
                "       SU.MOVETEL,\n" +
                "       SU.USER_ID\n" +
                "  FROM SF_USER SU\n" +
                " WHERE SU.USER_ID = ?\n" +
                "   AND (SU.DISABLE_DATE IS NULL OR SU.DISABLE_DATE < GETDATE())";
       sqlArgs.add(dto.getUserId());
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
		String sqlStr = "";


		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 createdBy 构造查询数据SQL。
	 * 框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
	 * @param createdBy String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByCreatedByModel(String createdBy){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " GROUP_ID,"
			+ " GROUP_CODE,"
			+ " GROUPNAME,"
			+ " GROUP_PID,"
			+ " ORGANIZATION_ID,"
			+ " SORTNO,"
			+ " ISROOT,"
			+ " CATEGORY,"
			+ " ENABLED,"
			+ " IS_INNER,"
			+ " CREATION_DATE,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " IS_DESIGNER,"
			+ " P_FLOW_ID,"
            + " ABBREVIATION"
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
	private SQLModel getDataByLastUpdateByModel(String lastUpdateBy){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " GROUP_ID,"
			+ " GROUP_CODE,"
			+ " GROUPNAME,"
			+ " GROUP_PID,"
			+ " ORGANIZATION_ID,"
			+ " SORTNO,"
			+ " ISROOT,"
			+ " CATEGORY,"
			+ " ENABLED,"
			+ " IS_INNER,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " IS_DESIGNER,"
			+ " P_FLOW_ID,"
			+ " ABBREVIATION"
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
		String sqlStr ="SELECT SU.LOGIN_NAME,\n" +
                "       SU.USERNAME,\n" +
                "       SU.EMPLOYEE_NUMBER,\n" +
                "       SU.MOVETEL,\n" +
                "       dbo.ADP_GET_ORG_NAME(SU.USER_ID) ORGNIZATION_NAME,\n" +
                "       CONVERT(INT,SU.USER_ID) USER_ID\n" +
                "  FROM SF_USER SU\n" +
                " WHERE SU.ORGANIZATION_ID = ?\n" +
                "   AND (SU.DISABLE_DATE IS NULL OR SU.DISABLE_DATE < GETDATE())\n" +
                "   AND EXISTS\n" +
                " (SELECT 'A' FROM AMS_SYN_RIGHT AYR WHERE SU.USER_ID = AYR.USER_ID)\n" +
                "   AND SU.LOGIN_NAME LIKE dbo.NVL(?, SU.LOGIN_NAME)\n" +
                "   AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)";
		
		sqlArgs.add(sfUser.getOrganizationId());
		sqlArgs.add(dto.getLoginName());
		sqlArgs.add(dto.getUserName());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
    public SQLModel deletByUser(){
       SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
      String sql="DELETE FROM AMS_SYN_RIGHT WHERE USER_ID = ?";
        	sqlArgs.add(dto.getUserId());
        sqlModel.setSqlStr(sql);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
      public SQLModel insertByUser(AmsSynRightDTO aDto){
       SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
      String sql="INSERT INTO AMS_SYN_RIGHT\n" +
              "  (SYN_RIGHT_ID, USER_ID, ORGANIZATION_ID, CREATION_DATE, CREATED_BY)\n" +
              "VALUES\n" +
              "  (NEWID(), ?, ?, GETDATE(), ?)";
        	sqlArgs.add(aDto.getUserId());
        	sqlArgs.add(aDto.getOrganizationId());
        	sqlArgs.add(sfUser.getUserId());
        sqlModel.setSqlStr(sql);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }

}
