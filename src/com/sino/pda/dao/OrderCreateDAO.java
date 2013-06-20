package com.sino.pda.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.XMLParseException;
import com.sino.base.log.Logger;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public abstract class OrderCreateDAO {
	protected Connection conn = null;
	protected FilterConfigDTO filterConfig = null;
	protected String filePath = "";
	protected SfUserDTO userAccount = null;


	protected String orderNo = "";
	protected ServletConfigDTO servletConfig = null;
	protected AMSSQLProducer sqlProducer = null;
	protected DTO orderParameter = null;

	public OrderCreateDAO(Connection conn, FilterConfigDTO filterConfig, String filePath) {
		this.conn = conn;
		this.filterConfig = filterConfig;
		this.filePath = filePath;
		try {
			initOrderData();
		} catch (XMLParseException ex) {
			ex.printLog();
		}
	}

	public void setOrderData(DTO orderParameter) {
		this.orderParameter = orderParameter;
		if(sqlProducer != null){
			sqlProducer.setDTOParameter(orderParameter);
		}
	}

	/**
	 * 功能：设置servletConfig
	 * @param servletConfig ServletConfigDTO
	 */
	public void setServletConfig(ServletConfigDTO servletConfig) {
		this.servletConfig = servletConfig;
	}

	/**
	 * 功能：由传入的XML文件解析后初始化相关程序
	 * @throws XMLParseException
	 */
	protected abstract void initOrderData() throws XMLParseException;


	/**
	 * 功能：判断指定地点点是否已经存在未归档工单。
	 * <B>对于盘点工单，需要人工归档</B>
	 * <B>对于抽查工单，工单上载时则自动归档</B>
	 * @return boolean
	 */
	public abstract boolean hasPreviousOrder();

	/**
	 * 功能：创建工单。
	 * @return String 成功则返回创建工单的编号，否则返回空字符串
	 */
	public String createOrder() {
		boolean operateResult = false;
		boolean autoCommit = true;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			createOrderBatch();
			createOrderHeader();
			createOrderLine();
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					orderNo = "";
					conn.rollback();
				} else {
					conn.commit();
				}
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
			}
		}
		return orderNo;
	}


	/**
	 * 功能：创建工单批
	 * @throws DataHandleException
	 */
	protected abstract void createOrderBatch() throws DataHandleException;


	/**
	 * 功能：创建工单头
	 * @throws DataHandleException
	 */
	protected abstract void createOrderHeader() throws DataHandleException;

	/**
	 * 功能：创建工单设备行
	 * @throws DataHandleException
	 */
	protected abstract void createOrderLine() throws DataHandleException;
}
