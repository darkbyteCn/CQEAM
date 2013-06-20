package com.sino.ams.newSite.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newSite.dto.EamAddressAddLDTO;
import com.sino.ams.newSite.model.EamAddressAddLModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

/** 
 * @author 作者 :wangzhipeng
 * @version 创建时间：Apr 15, 2011 10:18:10 AM 
 * 类说明:地点信息 增加行信息 
 *
 */
public class EamAddressAddLDAO extends AMSBaseDAO {

	public EamAddressAddLDAO(SfUserDTO userAccount, DTO dtoParameter,Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		EamAddressAddLDTO dtoPara =(EamAddressAddLDTO)dtoParameter;
		sqlProducer = new EamAddressAddLModel((SfUserDTO)userAccount, dtoPara);	
	}

}
