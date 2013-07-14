package com.sino.ams.yearchecktaskmanager.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskBaseDateDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskHeaderDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskLineDTO;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant;
import com.sino.base.db.sql.model.SQLModel;

public class AssetsYearCheckTaskProvinceModel extends AMSSQLProducer{
	private SfUserDTO user;
	public AssetsYearCheckTaskProvinceModel(SfUserDTO userAccount, AssetsYearCheckTaskHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.user = (SfUserDTO) userAccount;
	}
	
	//获取任务信息头表
	public SQLModel getTraskUserModel()
	  {
		    SQLModel sqlModel = new SQLModel();
		    List sqlArgs = new ArrayList();
		    AssetsYearCheckTaskHeaderDTO dto = (AssetsYearCheckTaskHeaderDTO)this.dtoParameter;
		    String sqlStr = "";

		    sqlModel.setSqlStr(sqlStr);
		    sqlModel.setArgs(sqlArgs);
		    return sqlModel;
	  }
	
	//获取任务信息头表
	public SQLModel getProvinceModel()
	  {
		    SQLModel sqlModel = new SQLModel();
		    List sqlArgs = new ArrayList();
		    AssetsYearCheckTaskHeaderDTO dto = (AssetsYearCheckTaskHeaderDTO)this.dtoParameter;
		    String sqlStr = "";

		    sqlModel.setSqlStr(sqlStr);
		    sqlModel.setArgs(sqlArgs);
		    return sqlModel;
	  }

	public SQLModel getSaveBaseDateModel(
			AssetsYearCheckTaskBaseDateDTO baseDateDto) {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	    StringBuffer sqlStr = new StringBuffer();
	    sqlStr.append(" insert into AMS_ASSETS_YAER_CHECK_TASK_BASEDATE( ")
	          .append(" BASEDATE_ID,BASEDATE_YEAR,CHK_YEARTASK_ORDER_NUMBER, ")
	          .append(" BASE_DATE_TYPE,CHECK_BASE_DATE_FROM,CHECK_BASE_DATE_END, ")
	          .append(" CHECK_TASK_DATE_FROM,CHECK_TASK_DATE_END,ORGANIZATION_ID,ENALBED, ")
	          .append(" CREATION_BY,CREATION_DATE) values(?,?,?,?,?,?,?,?,?,?,?,getdate()) ");
	    sqlArgs.add(baseDateDto.getBaseDateId());
	    sqlArgs.add(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
	    sqlArgs.add(baseDateDto.getChkYearTaskOrderNumber());
	    sqlArgs.add(baseDateDto.getBaseDateType());
	    sqlArgs.add(baseDateDto.getCheckBaseDateFrom());
	    sqlArgs.add(baseDateDto.getCheckBaseDateEnd());
	    sqlArgs.add(baseDateDto.getCheckTaskDateFrom());
	    sqlArgs.add(baseDateDto.getCheckTaskDateEnd());
	    System.out.println(user);
	    sqlArgs.add(user.getOrganizationId());
	    sqlArgs.add(baseDateDto.getEnabled());
	    sqlArgs.add(user.getUserId());
	    sqlModel.setSqlStr(sqlStr.toString());
	    sqlModel.setArgs(sqlArgs);
	    return sqlModel;
	}
	//获取所有需要下发的地市
    public SQLModel  getAllOUModel(AssetsYearCheckTaskHeaderDTO headerDto){
    	SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	    StringBuffer sqlStr = new StringBuffer();
	    sqlStr.append(" SELECT '下发后自动生成' ORDER_NUMBER,EOCM.ORGANIZATION_ID IMPLEMENT_ORGANIZATION_ID,\n")
	      .append(" EOCM.COMPANY_CODE, \n")
	      .append(" EOCM.COMPANY, \n")
	      .append(" AMD.DEPT_CODE IMPLEMENT_DEPT_ID, \n")
	      .append(" AMD.DEPT_NAME IMPLEMENT_DEPT_NAME, \n")
	      .append(" SU.USERNAME IMPLEMENT_NAME, \n")
	      .append(" SU.USER_ID IMPLEMENT_BY \n")
		  .append(" FROM ETS_OU_CITY_MAP EOCM,SF_USER SU,SF_USER_AUTHORITY SUA,AMS_MIS_EMPLOYEE AME,AMS_MIS_DEPT AMD \n")
		  .append(" where EOCM.ORGANIZATION_ID = SU.ORGANIZATION_ID \n")
		  .append(" and EOCM.IS_TD='N' \n")
		  .append(" and SU.USER_ID = SUA.USER_ID \n")
		  .append(" and SUA.ROLE_NAME='"+AssetsCheckTaskConstant.ORDER_RECIVE_ROLE_TO_CITY+"' \n")
          .append(" and SU.EMPLOYEE_NUMBER = AME.EMPLOYEE_NUMBER \n")
          .append(" and AME.DEPT_CODE = AMD.DEPT_CODE ")
          .append(" and not exists(select 1 from AMS_ASSETS_YAER_CHECK_TASK_ORDER_HEADER AH,AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE AL")
          .append(" where AH.HEADER_ID = AL.HEADER_ID ")
          .append(" and ( AH.ASSETS_BIG_CLASS ='ALL' or AH.ASSETS_BIG_CLASS=dbo.NVL(?,AH.ASSETS_BIG_CLASS) )")
          .append(" and AL.IMPLEMENT_ORGANIZATION_ID = EOCM.ORGANIZATION_ID )");
	    
	    sqlArgs.add(headerDto.getAssetsBigClass());
	    sqlModel.setSqlStr(sqlStr.toString());
	    sqlModel.setArgs(sqlArgs);
	    return sqlModel;
    }
	//获取省公司定的基准日期间
	public SQLModel getBaseDatePeriodModel() {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	    StringBuffer sqlStr = new StringBuffer();
	    sqlStr.append(" select  ")
	          .append(" BASEDATE_ID,BASEDATE_YEAR,CHK_YEARTASK_ORDER_NUMBER, ")
	          .append(" BASE_DATE_TYPE,CHECK_BASE_DATE_FROM,CHECK_BASE_DATE_END, ")
	          .append(" CHECK_TASK_DATE_FROM,CHECK_TASK_DATE_END,ORGANIZATION_ID,ENALBED, ")
	          .append(" CREATION_BY,CREATION_DATE ")
	          .append(" from AMS_ASSETS_YAER_CHECK_TASK_BASEDATE ")
	          .append(" where BASEDATE_YEAR=SUBSTRING(CONVERT(VARCHAR(32),GETDATE(),112),1,4) and BASE_DATE_TYPE ='1' and ENALBED='Y' ");
	    sqlModel.setSqlStr(sqlStr.toString());
	    sqlModel.setArgs(sqlArgs);
	    return sqlModel;
	}
	
	//获取某地市的基准日
	public SQLModel getBaseDateCityModel() {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	    StringBuffer sqlStr = new StringBuffer();
	    sqlStr.append(" select  ")
	          .append(" BASEDATE_ID, ")
	          .append(" CHECK_BASE_DATE_CITY,")
	          .append(" NON_ADDRESS_ASSETS_SOFT, ")
	          .append(" NON_ADDRESS_ASSETS_CLIENT, ")
	          .append(" NON_ADDRESS_ASSETS_PIPELINE, ")
	          .append(" ENALBED ")
	          .append(" from AMS_ASSETS_YAER_CHECK_TASK_BASEDATE ")
	          .append(" where ORGANIZATION_ID=? and BASE_DATE_TYPE ='2' and ENALBED='Y' and BASEDATE_YEAR=SUBSTRING(CONVERT(VARCHAR(32),GETDATE(),112),1,4) ");
	    sqlArgs.add(user.getOrganizationId());
	    sqlModel.setSqlStr(sqlStr.toString());
	    sqlModel.setArgs(sqlArgs);
	    return sqlModel;
	}

	
	public SQLModel getCreateTaksLineModel(AssetsYearCheckTaskLineDTO yearLineDTO) {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	    StringBuffer sqlStr = new StringBuffer();
	    sqlStr.append(" insert AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE(  ")
	          .append(" TASK_ORDER_ID,HEADER_ID,ORDER_NAME,ORDER_NUMBER,IMPLEMENT_BY, ")
	          .append(" IMPLEMENT_NAME,ORDER_TYPE,ORDER_STATUS,ORDER_LEVEL,IS_LAST_LEVLE, ")
	          .append(" IMPLEMENT_ORGANIZATION_ID,IMPLEMNET_DEPT_NAME,IMPLEMENT_DEPT_ID, ")
	          .append(" IMPLEMNET_ROLE_NAME,CREATION_DATE,LAST_UPDATE_DATE_BY,LAST_UPDATE_DATE) ")
	          .append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,getdate(),?,getdate()) ");
	    sqlArgs.add(yearLineDTO.getTaskOrderId());
	    sqlArgs.add(yearLineDTO.getHeaderId());
	    sqlArgs.add(yearLineDTO.getOrderName());
	    sqlArgs.add(yearLineDTO.getOrderNumber());
	    sqlArgs.add(yearLineDTO.getImplementBy());
	    sqlArgs.add(yearLineDTO.getImplementName());
	    sqlArgs.add(yearLineDTO.getOrderType());
	    sqlArgs.add(yearLineDTO.getOrderStatus());
	    sqlArgs.add(yearLineDTO.getOrderLevel());
	    sqlArgs.add(yearLineDTO.getIsLastLevel());
	    sqlArgs.add(yearLineDTO.getImplementOrganizationId());
	    sqlArgs.add(yearLineDTO.getImplementDeptName());
	    sqlArgs.add(yearLineDTO.getImplementDeptId());
	    sqlArgs.add(yearLineDTO.getImplementRoleName());
	    sqlArgs.add(user.getUserId());
	    
	    sqlModel.setSqlStr(sqlStr.toString());
	    sqlModel.setArgs(sqlArgs);
	    return sqlModel;
	}

	public SQLModel getcreateTaskHeaderModel(AssetsYearCheckTaskHeaderDTO headerDto) {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	    StringBuffer sqlStr = new StringBuffer();
	    sqlStr.append(" insert into AMS_ASSETS_YAER_CHECK_TASK_ORDER_HEADER(  ")
	          .append(" HEADER_ID,DISTRUBTE_BY,DISTRUBTE_BY_NAME,ASSETS_TYPE, ")
	          .append(" NON_ADDR_ASS_DISTRIBUTE_METHOD,NON_ADDR_ASS_CATEGORY,PARENT_ORDER_NUMBER,ASSETS_BIG_CLASS,CREATION_DATE ) ")
	          .append(" values(?,?,?,?,?,?,?,?,getdate()) ");
	   
	    sqlArgs.add(headerDto.getHeaderId());
	    sqlArgs.add(user.getUserId());
	    sqlArgs.add(user.getUsername());
	    sqlArgs.add(headerDto.getAssetsType());
	    sqlArgs.add(headerDto.getNonAddressDistributeMethod());
	    sqlArgs.add(headerDto.getNonAddressCategory());
	    sqlArgs.add(headerDto.getParentOrderNumber());
	    if(headerDto.getAssetsBigClass().equals("")){
	    	sqlArgs.add("ALL");//不选的话就是所有
	    }else{
	    	sqlArgs.add(headerDto.getAssetsBigClass());
	    }
	    sqlModel.setSqlStr(sqlStr.toString());
	    sqlModel.setArgs(sqlArgs);
	    return sqlModel;
	}

}
