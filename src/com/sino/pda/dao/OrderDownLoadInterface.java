package com.sino.pda.dao;

import com.sino.base.exception.QueryException;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public interface OrderDownLoadInterface {

	/**
	 * 功能：获取工单下载XML文件字符串
	 * @return StringBuffer
	 * @throws QueryException
	 */
	public StringBuffer getOrderXml() throws QueryException;
}
