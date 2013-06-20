package com.sino.ams.inv.common.bean;

import java.sql.Connection;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: SinoIES</p>
 * <p>Description: 河南移动IES系统</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 于士博
 * @version 1.0
 */
public class InvOptionProducer extends OptionProducer{


	public InvOptionProducer(SfUserDTO userAccount, Connection conn) {
		super(userAccount, conn);
	}
}
