package com.sino.ams.system.user.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * User: Zyun
 * Date: 2008-1-11
 * Time: 15:01:34
 */
public class ChangeUserPasswordModel extends AMSSQLProducer {
    private SfUserDTO dto = null;


    public ChangeUserPasswordModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.dto = (SfUserDTO) dtoParameter;
    }


    /**
     * 得到修改用户密码的SQLMODEL(包括个人能修改的信息)
     * @return SQLModel
     */
    public SQLModel getChangeUserInfoPasswordModel() {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql = "UPDATE SF_USER\n" +
                " SET PASSWORD = ETS_ENCRYPT.ENCODE(?),\n" +
                " OFFICE_TEL = ?,\n" +
                " FAX = ?,\n" +
                " MOBILE_PHONE = ?,\n" +
                " EMAIL = ?,\n" +
                " LAST_UPDATE_DATE = GETDATE(),\n" +
                " PASSWORD_DATE = GETDATE()+90\n" +
                " WHERE SU.USER_ID = ? ";
        strArg.add(dto.getPassword());
        strArg.add(dto.getOfficeTel());
        strArg.add(dto.getFax());
        strArg.add(dto.getMobilePhone());
        strArg.add(dto.getEmail());
//        strArg.add(dto.getPasswordDate());
        strArg.add(dto.getUserId());
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }
    
    public SQLModel getChangeUserInfo(){
    	SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql = "UPDATE SF_USER \n" +
		        " SET OFFICE_TEL = ?, \n" + 
		        " FAX = ?, \n" + 
		        " MOBILE_PHONE = ?, \n" + 
		        " EMAIL = ?, \n" +
//		        " PASSWORD = dbo.SFK_ENCODE(?), \n" +
		        " PASSWORD =(CASE WHEN PASSWORD=? THEN PASSWORD ELSE dbo.SFK_ENCODE(?) END), \n" +
		        " LAST_UPDATE_DATE = GETDATE(), \n" +
		        " IS_SMS = ?" +
		        " WHERE USER_ID = ? ";
		strArg.add(dto.getOfficeTel());
		strArg.add(dto.getFax());
		strArg.add(dto.getMobilePhone());
		strArg.add(dto.getEmail());
		strArg.add(dto.getPassword());
		strArg.add(dto.getPassword());
		strArg.add(dto.getIsSms());
		strArg.add(dto.getUserId());
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }

    public SQLModel getCheckPasswordModel(String oldPswd) {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql = "SELECT 1 FROM  SF_USER SU" +
                " WHERE UPPER(SU.LOGIN_NAME) = UPPER(?)" +
                "   AND SU.PASSWORD = ETS_ENCRYPT.ENCODE(?)";
        strArg.add(dto.getLoginName());
        strArg.add(oldPswd);
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }
 
    /**
     * Function:		查询用户的个人信息
     * @return			SQLModel
     * @author  		李轶
     * @Version 		0.1
     * @Date:   		Apr 24, 2009
     */
    public SQLModel getUserInfo(){
    	SQLModel sqlModel = new SQLModel();
    	List strArg = new ArrayList();
    	String strSql = 
    		"SELECT SU.OFFICE_TEL,\n" +
    			"	SU.PASSWORD,\n" +
    			"	SU.FAX," +
    			"	SU.EMAIL," +
    			"	SU.MOBILE_PHONE," +
    			"	SU.PASSWORD_DATE PASSWORD_OVERDUE," +
    			"	SU.IS_SMS" +
    			" FROM SF_USER SU " +
    			" WHERE SU.USER_ID = ?";
    	strArg.add(userAccount.getUserId());
    	sqlModel.setArgs(strArg);
    	sqlModel.setSqlStr(strSql);
    	return sqlModel;
    }
      public SQLModel getChangeUserPasswordModel() {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql = "UPDATE SF_USER\n" +
                " SET PASSWORD = ETS_ENCRYPT.ENCODE(?)," +
                "     PASSWORD_DATE = GETDATE()+90\n" +
                " WHERE UPPER(LOGIN_NAME) = UPPER(?)";
        strArg.add(dto.getPassword());
        strArg.add(dto.getLoginName());
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }
}
