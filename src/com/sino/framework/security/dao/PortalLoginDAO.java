package com.sino.framework.security.dao;

import java.sql.Connection;

import com.sino.base.constant.WebConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.SinoEncryptor;
import com.sino.framework.security.model.PortalLoginModel;

/**
 * 
 * @系统名称: 用户验证
 * @功能描述:
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Oct 12, 2011
 */
public class PortalLoginDAO {
	private String portalUserId = "";
	private Connection conn = null;
	private String loginName = "";
	private String decodeStr = "SinoETS_SSO";

	public PortalLoginDAO(String portalUserId, Connection conn) {
		super();
		this.portalUserId = SinoEncryptor.decode(decodeStr, portalUserId);
		this.conn = conn;
	}

	/**
	 * 判断当前OA用户是否在本系统中存在对应用户信息
	 * 
	 * @return
	 * @throws QueryException 
	 * @throws ContainerException 
	 */
	public boolean isValidUserInCurSystem() throws QueryException, ContainerException {
		boolean isValidUser = false;
        PortalLoginModel loginModel = new PortalLoginModel();
        SQLModel sqlModel = loginModel.getUserPortalModel(portalUserId);
        SimpleQuery gridBean = new SimpleQuery(sqlModel, conn);
        gridBean.executeQuery();
        RowSet rows = gridBean.getSearchResult();
        if (rows != null && rows.getSize() > 0) {
            Row row = rows.getRow(0);
            loginName = row.getStrValue("LOGIN_NAME");
            isValidUser = true;
        }
        return isValidUser;
	}

	/**
	 * 判断来自portal的用户是否本系统合法用户
	 * @throws QueryException 
	 * @throws ContainerException 
	 */
	public boolean isValidUser() throws QueryException, ContainerException {
		boolean isValidUser = false;
        if (!isValidUserInCurSystem()) {     //判断用户是否在有对应的OA登录名
            Connection co = null;
            PortalLoginModel loginModel = new PortalLoginModel();
            SQLModel sqlModel = loginModel.getLoginNameByOaName(portalUserId);
            try {
//                co = DBManager.getDBConnection(WebConstant.DB_POOL_MIS);        //更换为MIS的连接池，不使用DBLINK
                SimpleQuery gridBean = new SimpleQuery(sqlModel, co);
                gridBean.executeQuery();
                RowSet rows = gridBean.getSearchResult();
                if (rows != null && rows.getSize() > 0) {
                    Row row = rows.getRow(0);
                    loginName = row.getStrValue("LOGIN_NAME");
                    isValidUser = true;
                }
            } catch (ContainerException ex) {
                throw new QueryException(ex.getMessage());
            } finally {
                DBManager.closeDBConnection(co);

            }
        } else {
            isValidUser = true;
        }
        return isValidUser;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
