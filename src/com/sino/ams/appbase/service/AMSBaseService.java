package com.sino.ams.appbase.service;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.message.Message;
import com.sino.base.message.MessageManager;
import com.sino.framework.dto.BaseLocaleDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.service.BaseService;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: mshtang
 * Date: 2011-7-6
 * Time: 15:15:30
 * To change this template use File | Settings | File Templates.
 */
public class AMSBaseService extends BaseService {
	protected SfUserDTO userAccount = null;

	public AMSBaseService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		this.userAccount = (SfUserDTO)userAccount;
	}
}
