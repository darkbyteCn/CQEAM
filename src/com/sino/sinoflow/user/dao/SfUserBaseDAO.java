package com.sino.sinoflow.user.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.sinoflow.user.dto.SfUserRightDTO;
import com.sino.sinoflow.user.model.SfUserBaseModel;

/**
 * Title: SinoApplication
 * Description: Java Enterprise Edition 平台应用开发基础框架
 * Copyright: 唐明胜版权所有Copyright (c) 2003~2007。
 * Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。
 * Copyright: 作者授权北京思诺博信息技术有限公司在一定范围内使用
 * Company: 北京思诺博信息技术有限公司
 * @author 唐明胜
 * @version 0.1
 *          修改人：白嘉 修改日期：2008.9.2
 */

public class SfUserBaseDAO extends BaseDAO {
    private SfUserBaseDTO userAccount = null;
    private String msg = null;
    private boolean isSuccess = false;

    public SfUserBaseDAO(SfUserBaseDTO userAccount, SfUserBaseDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器baseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO
     * @param dtoParameter DTO
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SfUserBaseDTO sfUser = (SfUserBaseDTO) dtoParameter;
        super.sqlProducer = new SfUserBaseModel((SfUserBaseDTO) userAccount, sfUser);
    }

    /**
     * 检查用户登录名是否存在
     * @param sfUser SfUserBaseDTO
     * @return boolean
     * @throws QueryException
     */
    public boolean checkSfUser(SfUserBaseDTO sfUser) throws QueryException {
        SQLModel sqlModel = getCheckUserModel(sfUser);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        return simpleQuery.hasResult();
    }
    
    public int getCurIdValue() throws QueryException, NumberFormatException, ContainerException{
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT @@IDENTITY "; 
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if( simpleQuery.hasResult() ){
        	return  Integer.parseInt( simpleQuery.getSearchResult().getRow( 0 ).getStrValue( 0 ) );
        } else{
        	return 0;
        }
    }

    public SfUserBaseDTO doSave( SfUserBaseDTO dto , String str , String cat , String strDept , ServletConfigDTO configDTO ) throws SQLException {
    	boolean operateResult = false;
    	boolean autoCommit = false;
    	Statement stmt = null ;
        try {
        	autoCommit = conn.getAutoCommit(); 
        	conn.setAutoCommit(false);
	    	SeqProducer seqProducer = new SeqProducer(conn);
//	        int userId = seqProducer.getStrNextSeq("SF_USER_S");
//	        dto.setUserId(userId);
	        this.createData();
	        int userId = getCurIdValue();
	        dto.setUserId(userId);
	        
	        operateResult = this.saveUserAuthority(str, "", cat);
	        
//	        if( operateResult ){
//	        	operateResult = false;
//	        	operateResult = this.saveUserDeptPri(strDept, "");
//	        }
	        
	        if( operateResult ){
	        	operateResult = false;
	        	this.saveUserAssetsPrvi(userId,str,"",configDTO.getProvinceCode());
	        	operateResult = true ;
	        }   
        } catch (SQLException e) {
        	msg = e.getMessage();
            Logger.logError(e);
        } catch (DataHandleException e) {
        	msg = e.getMessage();
        	Logger.logError(e);
		} catch (QueryException e) {
			msg = e.getMessage();
			Logger.logError(e);
		} catch (NumberFormatException e) {
			msg = e.getMessage();
			Logger.logError(e);
		} catch (ContainerException e) {
			msg = e.getMessage();
			Logger.logError(e);
		}finally{
			isSuccess = operateResult;
			if( operateResult ){
				conn.commit();
			}else{
				msg = msg + "保存用户信息失败";
				conn.rollback(); 
			}
			conn.setAutoCommit( autoCommit );
		}
        return dto ;
    }
    
    /**
     * 用户是否还有待办
     * @return
     * @throws QueryException 
     */
    public boolean userHasDoWork( SfUserBaseDTO dto ) throws QueryException{
    	SQLModel sqlModel = getCheckUserHasWorkModel( dto );
    	SimpleQuery sq = new SimpleQuery( sqlModel , conn  );
    	sq.executeQuery();
    	return sq.hasResult() ;
    }
    
    public SfUserBaseDTO doUpdate( SfUserBaseDTO dto , String str , String cat , String strDept , ServletConfigDTO configDTO ) {
    	boolean operateResult = false;
    	boolean autoCommit = false; 
    	Statement stmt = null ;
        try {
        	autoCommit = conn.getAutoCommit(); 
        	conn.setAutoCommit(false);
        	
        	if( dto.getEnabled().equals( "N" ) ){
        		if( userHasDoWork( dto ) ){
        			msg = "用户还有未处理完的在办任务，不能失效.";
            		throw new DataHandleException( msg );
            	}
        	}
        	
	        this.updateData();
	        operateResult = this.saveUserAuthority( str, "update", cat);
	        
//	        operateResult = this.saveUserAuthority( stmt , str, "update", cat);
//	        if( operateResult ){
//	        	operateResult = false;
//	        	operateResult = this.saveUserDeptPri( strDept, "update");
////	        	operateResult = this.saveUserDeptPri( stmt , strDept, "update");
//	        }
	        
	        if( operateResult && !StrUtil.isEmpty( str ) ){
	        	operateResult = false;
	        	this.saveUserAssetsPrvi(dto.getUserId(),str,"update",configDTO.getProvinceCode());
	        	operateResult = true ;
	        }  
        } catch (SQLException e) {
        	msg = e.getMessage();
            Logger.logError(e);
        } catch (DataHandleException e) {
        	msg = e.getMessage();
        	Logger.logError(e);
		} catch (QueryException e) {
			msg = e.getMessage();
        	Logger.logError(e);
		}finally{
			isSuccess = operateResult;
			try{
				if( operateResult ){ 
					conn.commit();
				}else{
					msg = msg + "保存用户信息失败";
					conn.rollback();
				}
				conn.setAutoCommit( autoCommit );
			}catch( SQLException e ){
				msg = e.getMessage();
	        	Logger.logError(e);
			}
		}
		DBManager.closeDBConnection( conn );
        return dto ;
    }
    
    
    public boolean saveData(SfUserBaseDTO sfUser, DTOSet dtoSet) throws DataHandleException {
        boolean operateResult = false;
        try {
            boolean autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
			boolean isNew = (sfUser.getUserId() <= 0); 
            if (isNew) {
                SeqProducer seqProducer = new SeqProducer(conn);
                sfUser.setUserId(seqProducer.getStrNextSeq("SF_USER_S"));
                createData();
//				sfUser.setUserId(StrUtil.strToInt(createData2()));
            } else {
                updateData();
            }
            SfUserRightDTO userRightDTO = new SfUserRightDTO();
            userRightDTO.setUserId(sfUser.getUserId());

            SfUserRightDAO sfUserRightDAO = new SfUserRightDAO(userAccount, userRightDTO, conn);
            sfUserRightDAO.deleteData();
            if (dtoSet != null && dtoSet.getSize() > 0) {
                for (int i = 0; i < dtoSet.getSize(); i++) {
                    userRightDTO = (SfUserRightDTO) dtoSet.getDTO(i);
                    if (isNew) {
                        userRightDTO.setUserId(sfUser.getUserId());
                    }
                    sfUserRightDAO.setDTOParameter(userRightDTO);
                    sfUserRightDAO.createData();
                }
            }
            conn.commit();
            conn.setAutoCommit(autoCommit);
            operateResult = true ;
        } catch (SQLException e) {
            Logger.logError(e);
        }
        return operateResult;
    }

    public RowSet getGroupOfOu(String orgId) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT SG.GROUP_ID, SG.GROUPNAME\n"
                + "  FROM SF_GROUP SG\n" + " WHERE SG.ORG_ID = ?\n"
                + "   AND SG.ENABLED = 'Y'";
        sqlArgs.add(orgId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        return simpleQuery.getSearchResult();
    }

    /**
     * 检查用户登录名是否重复
     * @param sfUser SfUserBaseDTO
     * @return SQLModel
     */

    private SQLModel getCheckUserModel(SfUserBaseDTO sfUser) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT * FROM SF_USER SU WHERE UPPER(SU.LOGIN_NAME) = UPPER(?)";
        sqlArgs.add(sfUser.getLoginName().toUpperCase());

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
    
    
    private SQLModel getCheckUserHasWorkModel(SfUserBaseDTO sfUser) {
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        StringBuilder sb = new StringBuilder();
    	sb.append( " \n" );
    	sb.append( " SELECT \n" );
    	sb.append( "     1 \n" );
    	sb.append( " FROM \n" );
    	sb.append( " 	SF_ACT_INFO SAI, \n" );
    	sb.append( " 	SF_APPLICATION SA, \n" );
    	sb.append( " 	SF_PROCEDURE SP, \n" ); 
    	sb.append( " 	SF_USER SU2  \n" );
    	sb.append( " WHERE \n" );
    	sb.append( " 	UPPER(SU2.LOGIN_NAME) = UPPER( ? ) \n" ); 
    	sb.append( " 	AND ((SAI.SFACT_SIGN_USER = SU2.LOGIN_NAME \n" ); 
    	sb.append( " 	AND NOT EXISTS (SELECT \n" );
    	sb.append( " 						NULL  \n" );
    	sb.append( " 					FROM \n" );
    	sb.append( " 						SF_DELEGATION SD  \n" );
    	sb.append( " 					WHERE \n" );
    	sb.append( " 						SU2.USER_ID = SD.USER_ID  \n" );
    	sb.append( " 						AND SD.STATUS_CTL = 1  \n" );
    	sb.append( " 						AND ((GETDATE() >= SD.START_DATE  \n" );
    	sb.append( " 						OR SD.START_DATE IS NULL)  \n" );
    	sb.append( " 						AND (GETDATE() <= SD.END_DATE  \n" );
    	sb.append( " 						OR SD.END_DATE IS NULL)) \n" );
    	sb.append( " 	) \n" );
    	sb.append( " 	)  \n" );
    	sb.append( " 	OR EXISTS (	SELECT \n" );
    	sb.append( " 					NULL \n" ); 
    	sb.append( " 				FROM \n" );
    	sb.append( " 					SF_DELEGATION SD, \n" );
    	sb.append( " 					SF_USER SU3  \n" );
    	sb.append( " 				WHERE \n" );
    	sb.append( " 					SU2.USER_ID = SD.DELEGATE_TO  \n" );
    	sb.append( " 					AND SU3.USER_ID = SD.USER_ID \n" ); 
    	sb.append( " 					AND SU3.LOGIN_NAME = SAI.SFACT_SIGN_USER  \n" );
		sb.append( " 					AND SD.STATUS_CTL = 1  \n" );
		sb.append( " 					AND ((GETDATE() >= SD.START_DATE  \n" );
		sb.append( " 					OR SD.START_DATE IS NULL)  \n" );
		sb.append( " 					AND (GETDATE() <= SD.END_DATE  \n" );
		sb.append( " 					OR SD.END_DATE IS NULL)) \n" );
		sb.append( " 	) \n" );
		sb.append( " 	)  \n" );
		sb.append( " 	AND (SAI.SFACT_SIGN_STATUS = 1  \n" );
		sb.append( " 	AND SAI.SFACT_SUSPEND_FLAG <> 1)  \n" );
		sb.append( " 	AND SAI.SFACT_COMPLETE_STATUS <> 1  \n" );
		sb.append( " 	AND SAI.SFACT_APPDEF_ID = SA.APP_ID  \n" );
		sb.append( " 	AND SAI.SFACT_PROC_ID = SP.PROCEDURE_ID   \n" );
    	
        sqlArgs.add(sfUser.getLoginName().toUpperCase());
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr( sb.toString() );
        return sqlModel;
    }
    
	 

    public Object getMuxData() throws QueryException {
        return super.getMuxData();
    }

    public Object getUserData() throws QueryException {
        Object detailData = null;
        SQLModel sql = ((SfUserBaseModel) sqlProducer).getUserDataModel();
        SimpleQuery splq = new SimpleQuery(sql, conn);
        splq.setCalPattern(getCalPattern());
        if (!StrUtil.isEmpty(dtoClassName)) {
            splq.setDTOClassName(dtoClassName);
        }
        splq.executeQuery();
        if (splq.hasResult()) {
            if (!StrUtil.isEmpty(dtoClassName)) {
                detailData = splq.getFirstDTO();
            } else {
                detailData = splq.getFirstRow();
            }
        }
        return detailData;
    }

    /**
     * 功能：保存用户所在的工程，组别，角色
     * @param str str
     * @param tag tag
     * @throws SQLException 
     * @throws SQLException
     */
    public boolean saveUserAuthority( String str, String tag, String cat) throws SQLException {
    	boolean operateResult = false; 
        Statement stmt = null ;
        try { 
            stmt = conn.createStatement();
            String sql = "";
            boolean comManger = false;
            boolean catManger = false;

            String[][] p = StrUtil.splitStr(str, ";", ",");

            if (tag.equals("update")) {
                sql = "DELETE FROM SF_USER_AUTHORITY WHERE USER_ID = " + ((SfUserBaseDTO) super.dtoParameter).getUserId();
                stmt.execute(sql);
//                sql = "DELETE FROM AMS_ASSETS_PRIVI WHERE USER_ID = " + ((SfUserBaseDTO) super.dtoParameter).getUserId();
//                stmt.execute(sql);
            }

            if (p[0].length < 2){
            	operateResult = true; 
            	return true;
            }

            for (int i = 0; i < p.length; i++) {
                sql = "INSERT INTO SF_USER_AUTHORITY ("
//                        + " AUTHORITY_ID,"
                        + " USER_ID,"
                        + " DEPARTMENT,"
                        + " POSITION,"
                        + " GROUP_NAME,"
                        + " ROLE_NAME,"
                        + " PROJECT_NAME)"
                        + " VALUES(  "
                        + ((SfUserBaseDTO) super.dtoParameter).getUserId()
                        + ",'','','" + p[i][1] + "','" + p[i][2] + "','" + p[i][0] + "')";
                stmt.addBatch(sql);
                if (p[i][2].equals("公司资产管理员")) {
                    comManger = true;
                }
                if (p[i][2].equals("专业资产管理员")) {
                    catManger = true;
                }
            }
            
//            if(comManger){
//
//            	StringBuffer sb = new StringBuffer();
//            	sb.append( " DECLARE @AMS_ASSETS_PRIVI_KEY INT ");
//            	sb.append( " DECLARE @T_ROLE_ID INT  ");
//            	sb.append( " DECLARE @T_COMPANY_CODE VARCHAR(30)  ");
//            	sb.append( " DECLARE @T_COMPANY_CODE2 VARCHAR(30)  ");
//
//            	sb.append( "  	SELECT ");
//            	sb.append( "         @T_COMPANY_CODE2 =  SUBSTRING( CONVERT( VARCHAR , E.COMPANY_CODE ), 1, 2)  ");
//            	sb.append( "     FROM ");
//            	sb.append( "         ETS_OU_CITY_MAP E  ");
//            	sb.append( "     WHERE ");
//            	sb.append( "         E.ORGANIZATION_ID = " + ((SfUserBaseDTO)super.dtoParameter).getOrganizationId() );
//            	sb.append( " SELECT ");
//            	sb.append( "         @T_COMPANY_CODE = CONVERT( VARCHAR , T.COMPANY_CODE ) ");
//            	sb.append( "     FROM ");
//            	sb.append( "         ETS_OU_CITY_MAP T  ");
//            	sb.append( "     WHERE ");
//            	sb.append( "         T.ORGANIZATION_ID = " + ((SfUserBaseDTO)super.dtoParameter).getOrganizationId() );
//            	sb.append( " SELECT ");
//            	sb.append( "     TOP 1 @T_ROLE_ID =  SR.ROLE_ID  ");
//            	sb.append( "     FROM ");
//            	sb.append( "         SF_ROLE SR  ");
//            	sb.append( "     WHERE ");
//            	sb.append( "         SR.ROLE_NAME = '公司资产管理员'  ");
//            	sb.append( "         AND SR.ENABLED = 'Y'  ");
//
//            	sb.append( " 	INSERT ");
//            	sb.append( " 	INTO ");
//            	sb.append( " 		AMS_ASSETS_PRIVI  ");
//            	sb.append( " 		( ");
//            	sb.append( " 			USER_ID, ");
//            	sb.append( " 			ROLE_ID, ");
//            	sb.append( " 			PRIVI_ID, ");
//            	sb.append( " 			COMPANY_CODE, ");
//            	sb.append( " 			PROVINCE_CODE, ");
//            	sb.append( " 			CREATED_BY, ");
//            	sb.append( " 			CREATION_DATE)  ");
//            	sb.append( " 		VALUES ");
//            	sb.append( " 			( ");
//            	sb.append( " 				"  + ((SfUserBaseDTO)super.dtoParameter).getUserId()  + " , ");
//            	sb.append( " 				@T_ROLE_ID , ");
//            	sb.append( " 				NEWID() , ");
//            	sb.append( " 				@T_COMPANY_CODE , ");
//				sb.append( " 				@T_COMPANY_CODE2 , ");
//				sb.append( " 				" + ((SfUserBaseDTO)super.dtoParameter).getUserId()  + " , ");
//				sb.append( " 				GETDATE() ) ");
//				stmt.addBatch( sb.toString() );
//            }
            
//            if (comManger) {
//                sql = "INSERT INTO AMS_ASSETS_PRIVI\n" +
//                        "  (USER_ID,\n" +
//                        "   ROLE_ID,\n" +
//                        "   PRIVI_ID,\n" +
//                        "   COMPANY_CODE,\n" +
//                        "   PROVINCE_CODE,\n" +
//                        "   CREATED_BY,\n" +
//                        "   CREATION_DATE)\n" +
//                        "VALUES\n(" +
//                        ((SfUserBaseDTO) super.dtoParameter).getUserId() +
//                        "   ,(SELECT SR.ROLE_ID\n" +
//                        "      FROM SF_ROLE SR\n" +
//                        "     WHERE SR.ROLE_NAME = '公司资产管理员'\n" +
//                        "       AND SR.ENABLED = 'Y'\n" +
//                        "       AND ROWNUM < 2),\n" +
//                        "   NEWID() ,\n" +
//                        "   (SELECT T.COMPANY_CODE FROM ETS_OU_CITY_MAP T WHERE T.ORGANIZATION_ID =  " + ((SfUserBaseDTO) super.dtoParameter).getOrganizationId() +
//                        ")," +
//                        "(SELECT SUBSTRING(E.COMPANY_CODE, 1, 2)\n" +
//                        "  FROM ETS_OU_CITY_MAP E\n" +
//                        " WHERE E.ORGANIZATION_ID = " + ((SfUserBaseDTO) super.dtoParameter).getOrganizationId() +
//                        ")," +
//                        ((SfUserBaseDTO) super.dtoParameter).getUserId() +
//                        "   ,GETDATE())";
//                stmt.addBatch(sql);
//            }
            
//            if(catManger&&!cat.equals("")){
//                if(cat.equals("1")){
//                    cat="MGR-ASSETS" ;
//                }else{
//                    cat="NET-ASSETS" ;
//                }
//
//             StringBuffer sb = new StringBuffer();
//            	sb.append( " DECLARE @AMS_ASSETS_PRIVI_KEY_CM INT ");
//            	sb.append( " DECLARE @T_ROLE_ID_CM INT  ");
//            	sb.append( " DECLARE @T_COMPANY_CODE_CM VARCHAR(30)  ");
//            	sb.append( " DECLARE @T_COMPANY_CODE2_CM VARCHAR(30)  ");
//
//            	sb.append( " SELECT ");
//            	sb.append( " 	@AMS_ASSETS_PRIVI_KEY_CM = newid() ");
//            	sb.append( "  	SELECT ");
//            	sb.append( "         @T_COMPANY_CODE2_CM =  SUBSTRING( CONVERT( VARCHAR , E.COMPANY_CODE ), 1, 2)  ");
//            	sb.append( "     FROM ");
//            	sb.append( "         ETS_OU_CITY_MAP E  ");
//            	sb.append( "     WHERE ");
//            	sb.append( "         E.ORGANIZATION_ID = " + ((SfUserBaseDTO)super.dtoParameter).getOrganizationId()  );
//            	sb.append( " SELECT ");
//            	sb.append( "         @T_COMPANY_CODE_CM = CONVERT( VARCHAR , T.COMPANY_CODE ) ");
//            	sb.append( "     FROM ");
//            	sb.append( "         ETS_OU_CITY_MAP T  ");
//            	sb.append( "     WHERE ");
//            	sb.append( "         T.ORGANIZATION_ID = " + ((SfUserBaseDTO)super.dtoParameter).getOrganizationId() );
//            	sb.append( " SELECT ");
//            	sb.append( "     TOP 1 @T_ROLE_ID_CM =  SR.ROLE_ID  ");
//            	sb.append( "     FROM ");
//            	sb.append( "         SF_ROLE SR  ");
//            	sb.append( "     WHERE ");
//            	sb.append( "         SR.ROLE_NAME = '部门资产管理员'  ");
//            	sb.append( "         AND SR.ENABLED = 'Y'  ");
//
//            	sb.append( " 	INSERT ");
//            	sb.append( " 	INTO ");
//            	sb.append( " 		AMS_ASSETS_PRIVI  ");
//            	sb.append( " 		( ");
//            	sb.append( " 			USER_ID, ");
//            	sb.append( " 			ROLE_ID, ");
//            	sb.append( " 		    FA_CATEGORY_CODE,\n ");
//            	sb.append( " 			PRIVI_ID, ");
//            	sb.append( " 			COMPANY_CODE, ");
//            	sb.append( " 			PROVINCE_CODE, ");
//            	sb.append( " 			CREATED_BY, ");
//            	sb.append( " 			CREATION_DATE)  ");
//            	sb.append( " 		VALUES ");
//            	sb.append( " 			( ");
//            	sb.append( " 				"  + ((SfUserBaseDTO)super.dtoParameter).getUserId()  + " , ");
//            	sb.append( " 				@T_ROLE_ID_CM , ");
//            	sb.append( " 			    '"+ cat +  "', ");
//            	sb.append( " 				@AMS_ASSETS_PRIVI_KEY_CM , ");
//            	sb.append( " 				@T_COMPANY_CODE_CM , ");
//				sb.append( " 				@T_COMPANY_CODE2_CM , ");
//				sb.append( " 				" + ((SfUserBaseDTO)super.dtoParameter).getUserId()  + " , ");
//				sb.append( " 				GETDATE() ) ");
//				stmt.addBatch( sb.toString() );
//            }
//            if (catManger && !cat.equals("")) {
//                if (cat.equals("1")) {
//                    cat = "MGR-ASSETS";
//                } else {
//                    cat = "NET-ASSETS";
//                }
//                sql = "INSERT INTO AMS_ASSETS_PRIVI\n" +
//                        "  (USER_ID,\n" +
//                        "   ROLE_ID,\n" +
//                        "   FA_CATEGORY_CODE,\n" +
//                        "   PRIVI_ID,\n" +
//                        "   COMPANY_CODE,\n" +
//                        "   PROVINCE_CODE,\n" +
//                        "   CREATED_BY,\n" +
//                        "   CREATION_DATE)\n" +
//                        "VALUES\n(" +
//                        ((SfUserBaseDTO) super.dtoParameter).getUserId() +
//                        "   ,(SELECT SR.ROLE_ID\n" +
//                        "      FROM SF_ROLE SR\n" +
//                        "     WHERE SR.ROLE_NAME = '部门资产管理员'\n" +
//                        "       AND SR.ENABLED = 'Y'\n" +
//                        "       AND ROWNUM < 2),\n" +
//                        "'" + cat + "'" +
//                        "   , NEWID() ,\n" +
//                        "   (SELECT T.COMPANY_CODE FROM ETS_OU_CITY_MAP T WHERE T.ORGANIZATION_ID =  " + ((SfUserBaseDTO) super.dtoParameter).getOrganizationId() +
//                        ")," +
//                        "(SELECT SUBSTRING(E.COMPANY_CODE, 1, 2)\n" +
//                        "  FROM ETS_OU_CITY_MAP E\n" +
//                        " WHERE E.ORGANIZATION_ID = " + ((SfUserBaseDTO) super.dtoParameter).getOrganizationId() +
//                        ")," +
//                        ((SfUserBaseDTO) super.dtoParameter).getUserId() +
//                        "   ,GETDATE())";
//
//                stmt.addBatch(sql);
//            }
            stmt.executeBatch();
            operateResult = true ; 
        } catch (SQLException e) {
            Logger.logError(e);
        } finally {
        	if( !operateResult ){
        		conn.rollback();
        		DBManager.closeDBConnection(conn);
        	}  
        	return operateResult;
//            DBManager.closeDBConnection(conn);
        }
    }

    public boolean saveUserDeptPri(String str, String tag) throws SQLException {
	   	boolean isSuccess =	false;
        Statement stmt;
		boolean isAuto = false;
        try {
        	isAuto = conn.getAutoCommit();
			conn.setAutoCommit( false ); 
            stmt = conn.createStatement();
            String sql = "";
//			String[] p = StrUtil.splitStr(str, ";");
            String[] p = str.split(";");
//                String[][] p = StrUtil.splitStr(str, ";", ",");
            if (tag.equals("update")) {
                sql = "DELETE AMS_ASSETS_PRIVI \n" +
                        " WHERE DEPT_CODE IS NOT NULL\n" +
                        "   AND USER_ID = " + ((SfUserBaseDTO) super.dtoParameter).getUserId();
                stmt.execute(sql);
            }

            if (p.length < 2) {
				isSuccess =	true;
//				return isSuccess;
            }
//			SeqProducer seqProducer = new SeqProducer(conn);
//			
//            for (int i = 0; i < p.length; i++) {
//                String[] a = p[i].split(",");
//                String deptName = "";
//                if (a.length >= 2) {
//                    deptName = a[1];
//                } else {
//                    deptName = a[0];
//                }
//                
//                if(!deptName.equals("")){
////                	int priviId = seqProducer.getNumNextSeq( "AMS_ASSETS_PRIVI"  );
//                	String priviId = seqProducer.getGUID();
//               	 	StringBuffer sb = new StringBuffer(); 
////                   	sb.append( " DECLARE @AMS_ASSETS_PRIVI_2_KEY" + i + " INT ");
//                   	sb.append( " DECLARE @T_ROLE_ID_" + i + " INT  ");
//                   	sb.append( " DECLARE @T_COMPANY_CODE_" + i + " VARCHAR(30)  ");
//                   	sb.append( " DECLARE @T_COMPANY_CODE2_" + i + " VARCHAR(30)  ");
//                   	sb.append( " DECLARE @T_DEPT_CODE_" + i + " VARCHAR(30)  ");
//                   	 
//                   	sb.append( "  	SELECT ");
//                   	sb.append( "         @T_COMPANY_CODE2_" + i + " =  SUBSTRING( CONVERT( VARCHAR , E.COMPANY_CODE ), 1, 2)  ");
//                   	sb.append( "     FROM ");
//                   	sb.append( "         ETS_OU_CITY_MAP E  ");
//                   	sb.append( "     WHERE ");
//                   	sb.append( "         E.ORGANIZATION_ID = " + ((SfUserBaseDTO)super.dtoParameter).getOrganizationId()  );
//                   	sb.append( " SELECT ");
//                   	sb.append( "         @T_COMPANY_CODE_" + i + " = CONVERT( VARCHAR , T.COMPANY_CODE ) ");
//                   	sb.append( "     FROM ");
//                   	sb.append( "         ETS_OU_CITY_MAP T  ");
//                   	sb.append( "     WHERE ");
//                   	sb.append( "         T.ORGANIZATION_ID = " + ((SfUserBaseDTO)super.dtoParameter).getOrganizationId() );
//                   	sb.append( " SELECT ");
//                   	sb.append( "     TOP 1 @T_ROLE_ID_" + i + " =  SR.ROLE_ID   ");
//                   	sb.append( "     FROM ");
//                   	sb.append( "         SF_ROLE SR  ");
//                   	sb.append( "     WHERE ");
//                   	sb.append( "         SR.ROLE_NAME = '部门资产管理员'  ");
//                   	sb.append( "         AND SR.ENABLED = 'Y'  ");
//                   	
//                   	sb.append( " SELECT TOP 1 @T_DEPT_CODE_" + i + " =D.DEPT_CODE\n   ");
//                   	sb.append( "       FROM AMS_MIS_DEPT D\n   ");
//                   	sb.append( "      WHERE D.DEPT_NAME = " + "'"+deptName+"'   "); 
//                   	
//                   	sb.append( " 	INSERT ");
//                   	sb.append( " 	INTO ");
//                   	sb.append( " 		AMS_ASSETS_PRIVI  "); //AMS_ASSETS_PRIVI
//                   	sb.append( " 		( ");
//                   	sb.append( " 			USER_ID, ");
//                   	sb.append( " 			ROLE_ID, ");
//                   	sb.append( " 		    DEPT_CODE,\n ");
//                   	sb.append( " 			PRIVI_ID, ");
//                   	sb.append( " 			COMPANY_CODE, ");
//                   	sb.append( " 			PROVINCE_CODE, ");
//                   	sb.append( " 			CREATED_BY, ");
//                   	sb.append( " 			CREATION_DATE)  ");
//                   	sb.append( " 		VALUES ");
//                   	sb.append( " 			( ");
//                   	sb.append( " 				"  + ((SfUserBaseDTO)super.dtoParameter).getUserId()  + " , ");
//                   	sb.append( " 				@T_ROLE_ID_" + i + " , ");
//                   	sb.append( " 			    @T_DEPT_CODE_" + i + ", ");
//                   	sb.append( " 				'" + priviId + "' , "); 
//                   	sb.append( " 				@T_COMPANY_CODE_" + i + " , ");
//       				sb.append( " 				@T_COMPANY_CODE2_" + i + " , ");
//       				sb.append( " 				" + ((SfUserBaseDTO)super.dtoParameter).getUserId()  + " , ");
//       				sb.append( " 				GETDATE() ) ");
//       				stmt.addBatch( sb.toString() );		
//                } 
//                if (!deptName.equals("")) {
//                    sql = "INSERT INTO AMS_ASSETS_PRIVI\n" +
//                            "  (USER_ID,\n" +
//                            "   ROLE_ID,\n" +
//                            "   DEPT_CODE,\n" +
//                            "   PRIVI_ID,\n" +
//                            "   COMPANY_CODE,\n" +
//                            "   PROVINCE_CODE,\n" +
//                            "   CREATED_BY,\n" +
//                            "   CREATION_DATE)\n" +
//                            "VALUES\n(" +
//                            ((SfUserBaseDTO) super.dtoParameter).getUserId() +
//                            "   ,(SELECT SR.ROLE_ID\n" +
//                            "      FROM SF_ROLE SR\n" +
//                            "     WHERE SR.ROLE_NAME = '部门资产管理员'\n" +
//                            "       AND SR.ENABLED = 'Y'\n" +
//                            "       AND ROWNUM < 2),\n" +
//                            "   (SELECT D.DEPT_CODE\n" +
//                            "      FROM AMS_MIS_DEPT D\n" +
//                            "     WHERE D.DEPT_NAME = " + "'" + deptName + "'" +
//                            "       AND ROWNUM < 2),\n" +
//                            "   AMS_ASSETS_PRIVI_S.NEXTVAL,\n" +
//                            "   (SELECT T.COMPANY_CODE FROM ETS_OU_CITY_MAP T WHERE T.ORGANIZATION_ID =  " + ((SfUserBaseDTO) super.dtoParameter).getOrganizationId() +
//                            ")," +
//                            "(SELECT SUBSTRING(E.COMPANY_CODE, 1, 2)\n" +
//                            "  FROM ETS_OU_CITY_MAP E\n" +
//                            " WHERE E.ORGANIZATION_ID = " + ((SfUserBaseDTO) super.dtoParameter).getOrganizationId() +
//                            ")," +
//                            ((SfUserBaseDTO) super.dtoParameter).getUserId() +
//                            "   ,GETDATE())";
//
//                    stmt.addBatch(sql);
//                } 
//            }
//            stmt.executeBatch();
//			isSuccess =	true;
        } catch (SQLException e) {
            Logger.logError(e);
        } finally {
			if( !isSuccess){
				conn.rollback();
				conn.setAutoCommit( isAuto );
				throw new SQLException( "保存部门权限失败" ); 
			}else{
				conn.setAutoCommit( isAuto );
			}
//            DBManager.closeDBConnection(conn);
			return isSuccess;
        }
    }

    /**
     * 功能：删除用户所在的工程，组别，角色
     * @throws SQLException
     */
    public void delUserAuthority() {
        SimpleQuery simp = null;
        try {
            simp = new SimpleQuery(((SfUserBaseModel) sqlProducer).delSfUserAuthorityModel(), conn);
            simp.executeQuery();
        } catch (QueryException e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
        }
    }

    /**
     * 功能：获取用户所在的工程，组别，角色
     * @return RowSet
     */
    public RowSet getUserAuthority() {
        RowSet rowSet = null;
        try {
            SimpleQuery simp = new SimpleQuery(((SfUserBaseModel) sqlProducer).getSfUserAuthorityModel(), conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                rowSet = simp.getSearchResult();
            }
        } catch (QueryException e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
        }
        return rowSet;
    }

    public RowSet getUserDept() {
        RowSet rowSet = null;
        try {
            SimpleQuery simp = new SimpleQuery(((SfUserBaseModel) sqlProducer).getSfUserDeptModel(), conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                rowSet = simp.getSearchResult();
            }
        } catch (QueryException e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
        }
        return rowSet;
    }

    /**
     * 保存用户的部门资产管理员，公司资产管理员，全省资产管理员权限 SJ ADD 
     * @param userId
     * @param str
     * @param tag
     * @param proviceCode
     * @throws DataHandleException
     */
    public void saveUserAssetsPrvi(int userId,String str, String tag, String proviceCode) throws DataHandleException {
        HashMap mp = new HashMap();
        List lst=new ArrayList();
        String[][] p = StrUtil.splitStr(str, ";", ",");
        boolean compMgr=false;
        boolean proviceMgr=false;
        for (int i = 0; i < p.length; i++) {
            String[] role = p[i];
            if (role[2].equals("部门资产管理员")) {
                lst.add(role);
                mp.put(i, role);
            } else if (role[2].equals("公司资产管理员")) {
                 compMgr=true;
            }else if(role[2].equals("全省资产管理员")){
                 proviceMgr=true;
            }
        }
        List list = new ArrayList();
        SQLModel sqlModel = ((SfUserBaseModel) sqlProducer).getDeleteUserDeptMgrModel(userId);
        list.add(sqlModel);
        for (int k = 0; k < lst.size(); k++) {
            String[] x = (String[]) lst.get(k);
            sqlModel = ((SfUserBaseModel) sqlProducer).getInsertUserDeptMgrModel(userId, x[1], proviceCode);
            list.add(sqlModel);
        }
        if(compMgr){
            sqlModel = ((SfUserBaseModel) sqlProducer).getInsertUserCompMgrModel(userId, proviceCode);
            list.add(sqlModel);
        }
        if(proviceMgr){
            sqlModel = ((SfUserBaseModel) sqlProducer).getInsertUserProviceMgrModel(userId, proviceCode);
            list.add(sqlModel);
        }
        DBOperator.updateBatchRecords(list,conn);
    }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
}
