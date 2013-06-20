package com.sino.framework.security.dao;

import java.sql.Connection;
import java.util.List;

import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.message.MessageManager;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public abstract class BaseLoginDAO {
	protected BaseUserDTO userAccount = null;
	protected Connection conn = null;
	protected ServletConfigDTO servletConfig = null;
	protected boolean isValidUser = false;
	protected Message message = null;
    protected String userDtoName = "";   //用来存放web.xml里配置的userDTO

	public BaseLoginDAO(BaseUserDTO userAccount, Connection conn) {
		this.userAccount = userAccount;
		this.conn = conn;
		this.servletConfig = new ServletConfigDTO();
	}

	/**
	 * 功能：设置初始化Servlet配置对象
	 * @param servletConfig ServletConfigDTO
	 */
	public void setServletConfig(ServletConfigDTO servletConfig) {
		if(servletConfig != null){
			this.servletConfig = servletConfig;
		}
	}
	/**
	 * 功能：获取验证后数据得到丰富的用户信息
	 * @return BaseUserDTO
	 */
	public abstract BaseUserDTO getUserAccount();

	/**
	 * 功能：判断登录用户是否合法用户
	 * @return boolean
	 * @throws QueryException
	 */
	public abstract boolean isValidUser() throws QueryException;

	public Message getMessage() {
		return message;
	}

	/**
	 * 功能：根据消息键名获取消息。
	 * @param messageKey String
	 */
	public void prodMessage(String messageKey){
		Message refMessage = MessageManager.getMessage(userAccount.getLocale() ,messageKey);
		if(refMessage != null){
			message = new Message();
			message.setMessageKey(messageKey);
			message.setMessageValue(refMessage.getMessageValue());
			List parameterNames = refMessage.getParameterNames();
			if (!parameterNames.isEmpty()) {
				for (int i = 0; i < parameterNames.size(); i++) {
					message.addParameter((String) parameterNames.get(i));
				}
			}
		} else {
			message = Message.getUndefinedMessage();
		}
	}

    public String getUserDtoName() {
        return userDtoName;
    }

    public void setUserDtoName(String userDtoName) {
        this.userDtoName = userDtoName;
    }
}
