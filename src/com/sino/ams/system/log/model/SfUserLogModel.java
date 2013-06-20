package com.sino.ams.system.log.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.log.dto.SfUserLogDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfUserLogModel</p>
 * <p>Description:程序自动生成SQL构造器“SfUserLogModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class SfUserLogModel extends AMSSQLProducer {

	/**
	 * 功能：用户URL访问日志表(EAM) SF_USER_LOG 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfUserLogDTO 本次操作的数据
	 */
	public SfUserLogModel(SfUserBaseDTO userAccount, SfUserLogDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}
	/**
	 * 功能：框架自动生成用户URL访问日志表(EAM) SF_USER_LOG数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfUserLogDTO dto = (SfUserLogDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " SF_USER_LOG("
						+ " LOG_ID,"
						+ " USER_ID,"
						+ " USER_ACCOUNT,"
						+ " CLIENT_IP,"
						+ " REQ_URL,"
						+ " ACTION_TYPE,"
						+ " SOURCE,"
						+ " SERVER"
						+ ") VALUES ("
						+ "  NEWID() , ?, ?, ?, ?, ?, ?, ?)";

		sqlArgs.add(dto.getUserId());
		sqlArgs.add(dto.getUserAccount());
		sqlArgs.add(dto.getClientIp());
		String mm = dto.getReqUrl();
		if (mm.length() > 900) {
			mm = mm.substring(0, 900) + "...";
		}
		sqlArgs.add(mm);
		sqlArgs.add(dto.getActionType());
		sqlArgs.add(dto.getSource());
		sqlArgs.add(dto.getServer());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：框架自动生成用户URL访问日志表(EAM) SF_USER_LOG数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfUserLogDTO dto = (SfUserLogDTO) dtoParameter;
		String sqlStr = "DELETE FROM"
						+ " SF_USER_LOG"
						+ " WHERE"
						+ " LOG_ID = ?";
		sqlArgs.add(dto.getLogId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 userId 构造查询数据SQL。
	 * 框架自动生成数据用户URL访问日志表(EAM) SF_USER_LOG详细信息查询SQLModel，请根据实际需要修改。
	 * @param userId String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByUserIdModel(int userId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " LOG_ID,"
						+ " CLIENT_IP,"
						+ " REQ_URL,"
						+ " ACTION_TYPE,"
						+ " SOURCE,"
						+ " SERVER,"
						+ " LOG_TIME"
						+ " FROM"
						+ " SF_USER_LOG"
						+ " WHERE"
						+ " USER_ID = ?";
		sqlArgs.add(userId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键获取数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey){
		SQLModel sqlModel = null;
		SfUserLogDTO dto = (SfUserLogDTO)dtoParameter;
		if(foreignKey.equals("userId")){
			sqlModel = getDataByUserIdModel(dto.getUserId());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 userId 构造数据删除SQL。
	 * 框架自动生成数据用户URL访问日志表(EAM) SF_USER_LOG 数据删除SQLModel，请根据实际需要修改。
	 * @param userId String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDeleteByUserIdModel(int userId){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM"
						+ " SF_USER_LOG"
						+ " WHERE"
						+ " USER_ID = ?";
		sqlArgs.add(userId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键字段删除数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDeleteByForeignKeyModel(String foreignKey){
		SQLModel sqlModel = null;
		SfUserLogDTO dto = (SfUserLogDTO)dtoParameter;
		if(foreignKey.equals("userId")){
			sqlModel = getDeleteByUserIdModel(dto.getUserId());
		}
		return sqlModel;
	}

	
	/**
	 * 功能：框架自动生成用户URL访问日志表(EAM) SF_USER_LOG页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException
	 * @updater		李轶
	 * @update date Apr 24, 2009
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			SfUserLogDTO dto = (SfUserLogDTO) dtoParameter;
			String sqlStr = "SELECT "
							+ " SUL.LOG_ID,"
							+ " SUL.USER_ID,"
							+ " SU.USERNAME,"
							+ " SUL.CLIENT_IP,"
							+ " SRD.RES_NAME,"
							+ " SUL.ACTION_TYPE,"
							+ " SUL.SOURCE,"
							+ " SUL.SERVER,"
							+ " SUL.LOG_TIME,"
							+ " SUL.USER_ACCOUNT"
							+ " FROM"
							+ " SF_USER_LOG SUL,"
							+ " SF_USER     SU,"
							+ " SF_RES_DEFINE SRD "
							+ " WHERE"
							+ " SU.USER_ID = SUL.USER_ID AND SUL.REQ_URL = SRD.RES_URL"
							+ " AND  (" + SyBaseSQLUtil.nullSimpleCalendarParam() + " OR  SUL.LOG_TIME >= ? ) "
							+ " AND  (" + SyBaseSQLUtil.nullSimpleCalendarParam() + " OR  SUL.LOG_TIME <= ? ) ";
							SyBaseSQLUtil.nullSimpleCalendarParamArgs(sqlArgs, dto.getStartDate() );
							SyBaseSQLUtil.nullSimpleCalendarParamArgs(sqlArgs, dto.getSQLEndDate() );
							if(dto.getUserAccount()!=null&!dto.getUserAccount().trim().equals(""))
							{
								sqlStr+=" AND SU.USERNAME LIKE '%"+dto.getUserAccount()+"%'";
							}
							if(dto.getClientIp()!=null&!dto.getClientIp().trim().equals(""))
							{
								sqlStr+="AND SUL.CLIENT_IP LIKE '%"+dto.getClientIp()+"%'";
							}
							if(dto.getResName()!=null&!dto.getResName().equals(""))
							{
								sqlStr+="AND SRD.RES_NAME LIKE '"+dto.getResName()+"'";
							}
//			sqlArgs.add(dto.getStartDate());
//			sqlArgs.add(dto.getSQLEndDate());
			
			//要判断 当前登陆用户角色是地市管理员1100, 根据组别查询该用户地市下所有的日志记录
//			        当前登录用户角色是系统管理员 (1),所有地市的日志记录都能看到
//			        当前登录用户角色是员工,只能看到自己操作过的日志
			//如果是地市管理员(拥有系统管理员的角色  但是组别是)
//			if (!"".equals(userAccount.getCurrGroupId()) && !"".equals(userAccount.getRoleId())) {
			
			if ("PERSONAL".equals(dto.getColumeType())) {// 个人工作台  //预防同名同姓，所有要加USER_ID
				sqlStr += " AND SUL.USER_ID = ?";  
				sqlArgs.add(userAccount.getUserId());
			} else {
				if (!userAccount.isSysAdmin()) {
					if (userAccount.isCityAdmin()) {
						sqlStr += " AND EXISTS (SELECT 1\n"
								+ "       FROM ETS_OU_CITY_MAP EOCM\n"
								+ "      WHERE EOCM.ORGANIZATION_ID= SU.ORGANIZATION_ID\n"
								+ "			 AND SU.ORGANIZATION_ID = ?)";
						sqlArgs.add(userAccount.getOrganizationId());
						//	当前登陆用户角色是地市管理员1100, 根据组别查询该用户地市下所有的日志记录
					}
					else {  // 其它人员只能查看自己的日志
						sqlStr += " AND SUL.USER_ID = ?";
						sqlArgs.add(userAccount.getUserId());
					}
				}else{
                //	当前登录用户角色是系统管理员 (1),所有地市的日志记录都能看到
				}
			}
			sqlStr += " ORDER BY SUL.LOG_TIME DESC";
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
