package com.sino.hn.portal.dao;

import java.sql.Connection;
import java.text.ParseException;

import com.mochasoft.portal.encrypt.EncryptorUtil;
import com.sino.ams.log.dao.UserLoginDAO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.QueryException;
import com.sino.base.util.SinoEncryptor;
import com.sino.hn.constant.PortalConstant;
import com.sino.hn.portal.model.UserInfoModel;

/**
 * 
 * @系统名称: 
 * @功能描述: 单点登录
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Oct 28, 2011
 */
public class UserPortalDAO extends UserLoginDAO {
	private String portalUserId = "";
	private Connection conn = null;
	private String loginName = "";
	private String decodeStr = PortalConstant.PARAM_EKEY;
	
	private String isTd = "";
	
	private boolean isTodo = false; //判断单点或待办

//	public UserPortalDAO(BaseUserDTO userAccount, Connection conn) {
//		super(userAccount, conn);
//		this.portalUserId = SinoEncryptor.decode(decodeStr, portalUserId);
//		this.conn = conn;
//	}
	

	/**
	 * 解密方法
	 * @param secretKey 解密密钥
	 * @param ciphertext 密文
	 * @param outTime 密文失效时间，以秒为单位
	 * @return 解密后的字符串
	 */
	public static String decode(String secretKey, String ciphertext, int outTime) {
		String uid = null;
		try {
			uid = EncryptorUtil.decode(secretKey, ciphertext, outTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return uid;
	}

	public UserPortalDAO(String portalUserId, Connection conn) {
		super(null, conn);
		this.portalUserId = portalUserId;
		this.conn = conn;
	}

	/**
	 * 判断来自portal的用户是否本系统合法用户
	 * 
	 * @return boolean
	 * @throws QueryException
	 */
	public boolean isValidUser() throws QueryException {
		if( isTodo ){
			this.portalUserId = UserPortalDAO.decode(decodeStr, portalUserId , 1800 );
		}else{
			this.portalUserId = SinoEncryptor.decode(decodeStr, portalUserId);
		}
		UserInfoModel loginModel = new UserInfoModel(null);
		SQLModel sqlModel = loginModel.getUserLoginModel( portalUserId , isTd );
		return isValidUser( sqlModel, true );

	}

	public String getLoginName() {
		return loginName;
	}

	public void setTodo(boolean isTodo) {
		this.isTodo = isTodo;
	}

	public String getIsTd() {
		return isTd;
	}

	public void setIsTd(String isTd) {
		this.isTd = isTd;
	}

}
