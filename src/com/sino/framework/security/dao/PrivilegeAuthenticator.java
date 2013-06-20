package com.sino.framework.security.dao;

import java.sql.Connection;

import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public abstract class PrivilegeAuthenticator {
	protected BaseUserDTO userAccount = null;
	protected Connection conn = null;
	protected boolean hasPrivilege = false;
	protected String urlName = "";

	public PrivilegeAuthenticator(BaseUserDTO userAccount, Connection conn) {
		this.userAccount = userAccount;
		this.conn = conn;
	}

	/**
	 * 功能：验证当前用户对指定资源是否有权限
	 * @param requestURL 当前验证的资源
	 * @return boolean 返回true表示有权限，否则表示无权限
	 * @throws QueryException
	 */
	public boolean hasPrivilege(String requestURL) throws QueryException{
		authenticate(requestURL);
		return hasPrivilege;
	}

	/**
	 * 功能：执行权限验证
	 * @param requestURL 当前验证的资源
	 * 抽象方法：留给具体的继承类实现
	 * @throws QueryException
	 */
	protected abstract void authenticate(String requestURL)throws QueryException;

	/**
	 * 功能：获取URL名称。当用户无权限访问该URL时，给出友好显示名称
	 * @return String
	 */
	public String getUrlName(){
		return urlName;
	}
}
