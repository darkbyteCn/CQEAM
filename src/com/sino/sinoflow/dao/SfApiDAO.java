package com.sino.sinoflow.dao;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.util.FlowTaskTool;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.dto.SfApiDTO;
import com.sino.sinoflow.model.SfApiModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

/**
 * Title: SfApiDAO
 * Description:程序自动生成服务程序“SfApiDAO”，请根据需要自行修改
 * Copyright: Copyright (c) 2008
 * Company: 北京思诺博信息技术有限公司
 * @author Hing
 * @version 1.0
 */

public class SfApiDAO extends BaseDAO {

	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：接口程序信息 SF_API 数据访问层构造函数
	 * @param userAccount 代表本系统的最终操作用户对象
	 * @param dtoParameter 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public SfApiDAO(SfUserBaseDTO userAccount, SfApiDTO dtoParameter,
			Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * 
	 * @param userAccount
	 *            BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter
	 *            DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfApiDTO dtoPara = (SfApiDTO) dtoParameter;
		super.sqlProducer = new SfApiModel((SfUserBaseDTO) userAccount, dtoPara);
	}

	

	/**
	 * 功能：api 接口中所定义的事件
	 * @return Map
	 */
	private Map getApiMap() {
		Map map = new HashMap();
		map.put(new Integer(1), "sfqueryopen");
		map.put(new Integer(2), "sfpostopen");
		map.put(new Integer(4), "sfquerysign");
		map.put(new Integer(8), "sfpostsign");
		map.put(new Integer(16), "sfquerycomplete");
		map.put(new Integer(32), "sfgroupreview");
		map.put(new Integer(64), "sfquerycyclereview");
		map.put(new Integer(128), "sfqueryconditionalflow");
		map.put(new Integer(256), "sfquerygroup");
		map.put(new Integer(512), "sfqueryassistflow");
		map.put(new Integer(1024), "sfquerydistribute");
		map.put(new Integer(2048), "sfquerygoback");
		map.put(new Integer(4096), "sfquerysave");
		map.put(new Integer(8192), "sfpostsave");
		map.put(new Integer(8192*2), "sfparallelflow");
		return map;
	}

	public static String getJointStr(int count,Map map) {
		List list = FlowTaskTool.findNum(count);
		if(list.isEmpty()) return "&nbsp;";
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			str += map.get(list.get(i))+" + ";
		}
		return str.substring(0,str.length()-2);
	}

	public DTOSet operation(DTOSet ds){
		for (int i = 0; i < ds.getSize(); i++) {
			SfApiDTO sad = (SfApiDTO)ds.getDTO(i);
			String str = getJointStr(sad.getApiControl(),getApiMap());
			sad.setApiControlStr(str);
		}
		return ds;
	}
	
	/**
	 * 功能：批删除应用
	 * @param ids String[]
	 * @throws DataHandleException 
	 */
	public void del(String[] ids) throws DataHandleException {
		DBOperator.updateRecord(((SfApiModel)sqlProducer).del(ids), conn);
	}

    public void enabled(String[] ids) throws DataHandleException {
        DBOperator.updateRecord(((SfApiModel)sqlProducer).enabled(ids), conn);
    }

    public void disabled(String[] ids) throws DataHandleException {
        DBOperator.updateRecord(((SfApiModel)sqlProducer).disabled(ids), conn);
    }
}