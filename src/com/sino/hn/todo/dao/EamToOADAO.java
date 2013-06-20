package com.sino.hn.todo.dao;

import java.sql.Connection;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.hn.todo.model.EamToOAModel;
import com.sino.sinoflow.todo.dao.OaTodoDAO;
import com.sino.sinoflow.todo.dto.OaTodoDTO;

/**
 * 
 * @系统名称: 
 * @功能描述: 获取需要推送的数据
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Dec 1, 2011
 */
public class EamToOADAO extends OaTodoDAO {
	EamToOAModel eamToOAModel = null;

	public EamToOADAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		this.initSQLProducer(userAccount, dtoParameter);
	}

	@Override
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		oaTodoForm = (OaTodoDTO) dtoParameter;
		eamToOAModel = new EamToOAModel((SfUserDTO) userAccount, oaTodoForm);
		super.sqlProducer = eamToOAModel;
	}

	/**
	 * 获取需要推送的数据
	 * @return
	 * @throws QueryException
	 */
	public DTOSet getTodoDataSet() throws QueryException {
		SimpleQuery query = new SimpleQuery(eamToOAModel.getEamToOASQLModel(),
				conn);
		query.setDTOClassName(OaTodoDTO.class.getName());
		query.executeQuery();
		if (query.hasResult()) {
			return query.getDTOSet();
		} else {
			return null;
		}
	}

}
