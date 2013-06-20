package com.sino.ams.system.user.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.model.ChangeUserPasswordModel;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * User: Zyun
 * Date: 2008-1-11
 * Time: 15:02:06
 */
public class ChangeUserPasswordDAO extends AMSBaseDAO {
      private SfUserDTO sfUser = null;
      private ChangeUserPasswordModel   changeUserPasswordModel = null;

    public ChangeUserPasswordDAO(SfUserDTO userAccount, SfUserDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
        changeUserPasswordModel = new ChangeUserPasswordModel((SfUserDTO) userAccount, dtoParameter);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SfUserDTO dtoPara = (SfUserDTO) dtoParameter;

    }


    public boolean submit(String oldPswd) throws DataHandleException, QueryException {
        boolean success = false;
        //先验证加密过的密码是否正确
        if(sfUser.getPassword().equals(oldPswd)){
        	changeUserInfoAndPassword();
        	success = true;
        }else{
        	//先检查原密码正不正确,不正确不能修改        
            if(checkPswd(oldPswd)){
                changeUserPassword();
                success = true;
            }else{
                message.setMessageValue("原密码不正确!");
            }
        }        
        return success;
    }
      /**
     * 修改用户个人信息(包含密码)
     * @throws com.sino.base.exception.DataHandleException
     * @updater 李轶
     * @update Date:   		Apr 24, 2009
     */
    public void changeUserPassword() throws DataHandleException {
       SQLModel sqlModel = changeUserPasswordModel.getChangeUserInfoPasswordModel();
       DBOperator.updateRecord(sqlModel, conn);
    }
    
    /**
     * 
     * Function: 修改用户个人信息(不包含密码)
     * @throws DataHandleException
     * @author  		李轶
     * @Version 		0.1
     * @Date:   		Apr 24, 2009
     */
    public void changeUserInfoAndPassword() throws DataHandleException {
        SQLModel sqlModel = changeUserPasswordModel.getChangeUserInfo();
        DBOperator.updateRecord(sqlModel, conn);
     }

    private boolean checkPswd(String oldPswd) throws QueryException {
        SimpleQuery sq = new SimpleQuery(changeUserPasswordModel.getCheckPasswordModel(oldPswd),conn);
        sq.executeQuery();
        return sq.hasResult();
    }
    
    /**
     * 
     * Function:		根据登陆的用户编号,查询用户基本信息
     * @return			SfUserDTO
     * @throws 			QueryException
     * @author  		李轶
     * @Version 		0.1
     * @Date:   		Apr 24, 2009
     */
    public SfUserDTO queryUserInfo()throws QueryException{
    	SfUserDTO dto = null;
    	DTOSet  dtos = new DTOSet();
    	ChangeUserPasswordModel modelProducer = new ChangeUserPasswordModel(sfUser, userAccount);
    	SQLModel sqlModel = modelProducer.getUserInfo();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(SfUserDTO.class.getName());
		simp.executeQuery();
		dtos = simp.getDTOSet();
		if (dtos != null) {
		    dto = (SfUserDTO)dtos.getDTO(0);
		}
		return dto;
    }
    
    public void firstChangeUserPassword() throws DataHandleException {
       SQLModel sqlModel = changeUserPasswordModel.getChangeUserPasswordModel();
       DBOperator.updateRecord(sqlModel, conn);
    }
}
