package com.sino.sinoflow.dao;


import java.sql.Connection;
import java.util.List;

import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.util.FlowTaskTool;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.dto.SfAutoValueDTO;
import com.sino.sinoflow.model.SfAutoValueModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfAutoValueDAO</p>
 * <p>Description:程序自动生成服务程序“SfAutoValueDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfAutoValueDAO extends BaseDAO {

	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：自动赋值信息 SF_AUTO_VALUE 数据访问层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfAutoValueDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public SfAutoValueDAO(SfUserBaseDTO userAccount, SfAutoValueDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		SfAutoValueDTO dtoPara = (SfAutoValueDTO)dtoParameter;
		super.sqlProducer = new SfAutoValueModel((SfUserBaseDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：设置属性何时赋值，为方便页面显示
	 * @param ds DTOSet
	 */
	public void setAssignOn(DTOSet ds){
		for(int i=0;i<ds.getSize();i++){
			SfAutoValueDTO sav = (SfAutoValueDTO)ds.getDTO(i);
			List list = FlowTaskTool.findNum(sav.getAssignOn());
			
			for(int j=0;j<list.size();j++){
				int ig = Integer.parseInt(list.get(j).toString());
				switch (ig) {
					case 1:
						sav.setTh(true);
						break;
					case 2:
						sav.setTs(true);
						break;
					case 4:
						sav.setWc(true);
						break;
					case 8:
						sav.setZc(true);
						break;
					case 16:
						sav.setQs(true);
						break;

				}
			}
		}
	}
	
	/**
	 * 功能：批删除应用
	 * @param ids String[]
	 * @throws DataHandleException 
	 */
	public void del(String[] ids) throws DataHandleException {
		DBOperator.updateRecord((( SfAutoValueModel)sqlProducer).del(ids), conn);
	}
	
}