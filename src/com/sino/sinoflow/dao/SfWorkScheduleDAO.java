package com.sino.sinoflow.dao;


import java.sql.Connection;
import java.util.List;

import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.util.FlowTaskTool;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.dto.SfWorkScheduleDTO;
import com.sino.sinoflow.model.SfWorkScheduleModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfWorkScheduleDAO</p>
 * <p>Description:程序自动生成服务程序“SfWorkScheduleDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfWorkScheduleDAO extends BaseDAO {

	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：工作时间总表 SF_WORK_SCHEDULE 数据访问层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfWorkScheduleDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public SfWorkScheduleDAO(SfUserBaseDTO userAccount, SfWorkScheduleDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		SfWorkScheduleDTO dtoPara = (SfWorkScheduleDTO)dtoParameter;
		super.sqlProducer = new SfWorkScheduleModel((SfUserBaseDTO)userAccount, dtoPara);
	}
	
	/**
	 * 功能：批删除应用
	 * @param String[]
	 * @throws DataHandleException 
	 */
	public void del(String[] ids) throws DataHandleException {
		DBOperator.updateRecord(((SfWorkScheduleModel)sqlProducer).del(ids), conn);
	}
	
	/**
	 * 功能：将星期还原为字符串
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @throws ContainerException 
	 * @throws NumberFormatException 
	 */
	public void setWorkingDateStr(DTOSet ds) throws NumberFormatException, ContainerException{
		
		for(int i=0; i<ds.getSize();i++){
			SfWorkScheduleDTO sd = (SfWorkScheduleDTO)ds.getDTO(i);
			List list = FlowTaskTool.findNum(Integer.parseInt(sd.getWorkingDate()));
			String str = "";
			for(int j=0;j<list.size();j++){
				int ig = Integer.parseInt(list.get(j).toString());
				switch (ig){
					case 1 : 
						str += 星期一 +" ";
						break;
					case 2 :
						str += 星期二 +" ";
						break;
					case 4 :
						str += 星期三 +" ";
						break;
					case 8 :
						str += 星期四 +" ";
						break;
					case 16 :
						str += 星期五 +" ";
						break;
					case 32 :
						str += 星期六 +" ";
						break;
					case 64 :
						str += 星期日;
						break;
				}
			}
			sd.setWorkStr(str);
		}
	}
}