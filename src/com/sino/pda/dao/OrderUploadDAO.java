package com.sino.pda.dao;

import java.sql.Connection;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTOSet;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public abstract class OrderUploadDAO {
	protected SfUserDTO userAccount = null;
	protected Connection conn = null;
	protected AMSSQLProducer lineProducer = null;//针对工单行进行数据处理的SQL
	protected DTOSet orderBarcodes = null; //单据下原有资产

	protected ServletConfigDTO servletConfig = null;

	public OrderUploadDAO(SfUserDTO userAccount, Connection conn) {
		this.userAccount = userAccount;
		this.conn = conn;
		orderBarcodes = new DTOSet();
		initUploadApp();
	}

	public void setServletConfig(ServletConfigDTO servletConfig){
		this.servletConfig = servletConfig;
	}

	/**
	 * 功能：补充其他初始化程序
	 * <B>允许子类覆盖</B>
	 */
	protected void initUploadApp(){

	}

	/**
	 * 功能：解析上载的xml文件，并更新数据到数据库
	 * @param uploadFile String
	 * @return boolean
	 */
	public abstract boolean uploadOrders(String uploadFile);
}
