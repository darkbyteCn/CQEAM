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
import com.sino.sinoflow.dto.SfValidationDTO;
import com.sino.sinoflow.model.SfValidationModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfValidationDAO</p>
 * <p>Description:程序自动生成服务程序“SfValidationDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfValidationDAO extends BaseDAO {

	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：合法性检查信息 SF_VALIDATION 数据访问层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfValidationDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public SfValidationDAO(SfUserBaseDTO userAccount, SfValidationDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		SfValidationDTO dtoPara = (SfValidationDTO)dtoParameter;
		super.sqlProducer = new SfValidationModel((SfUserBaseDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：设置属性empty，为方便页面显示
	 * @param ds DTOSet
	 */
	public void setEmpty(DTOSet ds){
		for(int i=0;i<ds.getSize();i++){
			SfValidationDTO sf = (SfValidationDTO)ds.getDTO(i);
			List list = FlowTaskTool.findNum(sf.getValidationType());
			for (int j = 0; j < list.size(); j++) {
				int ig = Integer.parseInt(list.get(j).toString());
				if(ig == 1){
					sf.setEmpty(true);
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
		DBOperator.updateRecord((( SfValidationModel)sqlProducer).del(ids), conn);
	}
}