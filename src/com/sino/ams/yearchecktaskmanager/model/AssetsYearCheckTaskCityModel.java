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
import com.sino.ams.yearchecktaskmanager.util.CommonUtil;
import com.sino.base.db.sql.model.SQLModel;

public class AssetsYearCheckTaskCityModel extends AMSSQLProducer{
	private SfUserDTO user;
	public AssetsYearCheckTaskCityModel(SfUserDTO userAccount, AssetsYearCheckTaskHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
		user = (SfUserDTO)userAccount;
	}
	
	//������л�׼��
	public SQLModel getSaveCityBaseDateModel(AssetsYearCheckTaskBaseDateDTO cityBaseDto) {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	    StringBuffer sqlStr = new StringBuffer();
	    sqlStr.append(" insert into AMS_ASSETS_YAER_CHECK_TASK_BASEDATE( ")
	          .append(" BASEDATE_ID,BASEDATE_YEAR, ")
	          .append(" BASE_DATE_TYPE,")
	          .append(" CHECK_BASE_DATE_CITY,ORGANIZATION_ID,ENALBED,NON_ADDRESS_ASSETS_SOFT,NON_ADDRESS_ASSETS_CLIENT,NON_ADDRESS_ASSETS_PIPELINE, ")
	          .append(" CREATION_BY,CREATION_DATE) values(?,?,?,?,?,?,?,?,?,?,getdate()) ");
	    sqlArgs.add(CommonUtil.getUUID());
	    sqlArgs.add(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
	    sqlArgs.add("2");
	    sqlArgs.add(cityBaseDto.getCheckBaseDateCity());
	    sqlArgs.add(user.getOrganizationId());
	    sqlArgs.add("Y");
	    sqlArgs.add(cityBaseDto.getSoftWareMethod());
	    sqlArgs.add(cityBaseDto.getClientMethod());
	    sqlArgs.add(cityBaseDto.getPipeLineMethod());
	    sqlArgs.add(user.getUserId());
	    sqlModel.setSqlStr(sqlStr.toString());
	    sqlModel.setArgs(sqlArgs);
	    return sqlModel;
	}
	public SQLModel getCreateTaksLineModel(AssetsYearCheckTaskLineDTO yearLineDTO) {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	    StringBuffer sqlStr = new StringBuffer();
	    sqlStr.append(" insert into AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE(  ")
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
	          .append(" NON_ADDR_ASS_DISTRIBUTE_METHOD,NON_ADDR_ASS_CATEGORY,PARENT_ORDER_NUMBER,ASSETS_BIG_CLASS,CREATION_DATE) ")
	          .append(" values(?,?,?,?,?,?,?,?,getdate()) ");
	   
	    sqlArgs.add(headerDto.getHeaderId());
	    sqlArgs.add(user.getUserId());
	    sqlArgs.add(user.getUsername());
	    sqlArgs.add(headerDto.getAssetsType());
	    sqlArgs.add(headerDto.getNonAddressDistributeMethod());
	    sqlArgs.add(headerDto.getNonAddressCategory());
	    sqlArgs.add(headerDto.getParentOrderNumber());
	    sqlArgs.add(headerDto.getAssetsBigClass());
	    sqlModel.setSqlStr(sqlStr.toString());
	    sqlModel.setArgs(sqlArgs);
	    return sqlModel;
	}
	
	public SQLModel getLinesModel(String lookUpName){
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
	    StringBuffer sqlStr = new StringBuffer();
	    String orderName = "" ; //�̵����񹤵�����
		String orderType = "" ; //�̵����񹤵�����
		String orderTypeName = "" ; //�̵�������������
		String implementsRoleName = "" ;//�̵�����ִ���˽�ɫ
		if(lookUpName.equals("CITY_ADDRESS_FOR_CHECK_PERSON")){//ʵ�������ʲ��������̵�������
			orderName =AssetsCheckTaskConstant.ORDER_NAME_ADDRESS_WIRELESS;
			orderType = AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_WIRELESS;
			orderTypeName = "ʵ���ʲ�[������]";
			implementsRoleName= AssetsCheckTaskConstant.ORDER_RECIVE_ROLE_TO_DEPT_FOR_ADDRESS_WIRELESS;
		}else if(lookUpName.equals("CITY_ADDRESS_FOR_DEPT")){//ʵ�ط�����,���Ҳ��Ź���Ա
			orderName =AssetsCheckTaskConstant.ORDER_NAME_ADDRESS_NON_WIRELESS;//ʵ�ط������ʲ�����̵�����
			orderType=AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_NON_WIRELESS;
			orderTypeName = "ʵ���ʲ�[��������]";
			implementsRoleName = AssetsCheckTaskConstant.ORDER_RECIVE_ROLE_TO_DEPT_FOR_ADDRESS_NON_WIRELESS;
		}else if(lookUpName.equals("CITY_NON_ADDRESS_SOFTEWARE_1") || lookUpName.equals("CITY_NON_ADDRESS_SOFTEWARE_2")
				||lookUpName.equals("CITY_NON_ADDRESS_SOFTEWARE_3")){
			orderName =AssetsCheckTaskConstant.ORDER_NAME_NON_ADDRESS; //��ʵ���ʲ�����̵�����
			orderType = AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_SOFTWARE;
			orderTypeName = "��ʵ���ʲ�[�����]";
			implementsRoleName = AssetsCheckTaskConstant.ORDER_RECIVE_ROLE_TO_DEPT_FOR_NON_ADDRESS;//��ʵ���ʲ�
		}else if(lookUpName.equals("CITY_NON_ADDRESS_CLIENT_1")||lookUpName.equals("CITY_NON_ADDRESS_CLIENT_2")
				||lookUpName.equals("CITY_NON_ADDRESS_CLIENT_3")){
			orderName = AssetsCheckTaskConstant.ORDER_NAME_NON_ADDRESS; //��ʵ���ʲ�����̵�����
			orderType = AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_CLIENT;
			orderTypeName = "��ʵ���ʲ�[�ͻ�����]";
			implementsRoleName = AssetsCheckTaskConstant.ORDER_RECIVE_ROLE_TO_DEPT_FOR_NON_ADDRESS;//��ʵ���ʲ�
		}else if(lookUpName.equals("CITY_NON_ADDRESS_PIPELINE_1")||lookUpName.equals("CITY_NON_ADDRESS_PIPELINE_2")
				||lookUpName.equals("CITY_NON_ADDRESS_PIPELINE_3")){
			orderName = AssetsCheckTaskConstant.ORDER_NAME_NON_ADDRESS; //��ʵ���ʲ�����̵�����
			orderType= AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_PIPELINE;
			orderTypeName = "��ʵ���ʲ�[���¡���·���ܵ���]";
			implementsRoleName = AssetsCheckTaskConstant.ORDER_RECIVE_ROLE_TO_DEPT_FOR_NON_ADDRESS;//��ʵ���ʲ�
		}
		if(lookUpName.equals("CITY_ADDRESS_FOR_CHECK_PERSON") || lookUpName.equals("CITY_ADDRESS_FOR_DEPT")){//ʵ�����ߺͷ�����
			sqlStr.append(" SELECT '�·����Զ�����' ORDER_NUMBER, \n")
		      .append(" '"+orderName+"' ORDER_NAME, \n")
		      .append(" '"+orderType+"' ORDER_TYPE, \n")
		      .append(" '"+orderTypeName+"' ORDER_TYPE_NAME, \n")
		      .append(" AMD.DEPT_CODE IMPLEMENT_DEPT_ID, \n")
		      .append(" AMD.DEPT_NAME IMPLEMENT_DEPT_NAME, \n")
		      .append(" SU.USERNAME IMPLEMENT_NAME, \n")
		      .append(" SU.ORGANIZATION_ID IMPLEMENT_ORGANIZATION_ID, \n")
		      .append(" SU.USER_ID IMPLEMENT_BY,AME.EMPLOYEE_NUMBER \n")
			  .append(" FROM SF_USER SU,SF_USER_AUTHORITY SUA,AMS_MIS_EMPLOYEE AME,AMS_MIS_DEPT AMD \n")
			  .append(" where SU.USER_ID = SUA.USER_ID \n")
			  .append(" and SU.ENABLED = 'Y' ")
			  .append(" and SU.EMPLOYEE_NUMBER = AME.EMPLOYEE_NUMBER \n")
			  .append(" and AME.DEPT_CODE = AMD.DEPT_CODE \n")
			  .append(" and SUA.ROLE_NAME ='"+implementsRoleName+"' \n")
              .append(" and SU.ORGANIZATION_ID = ? \n");
			
		}else if(lookUpName.equals("CITY_NON_ADDRESS_SOFTEWARE_1") || lookUpName.equals("CITY_NON_ADDRESS_CLIENT_1")
				|| lookUpName.equals("CITY_NON_ADDRESS_PIPELINE_1") ){//����
			sqlStr.append(" SELECT '�·����Զ�����' ORDER_NUMBER, \n")
		      .append(" '"+orderName+"' ORDER_NAME, \n")
		      .append(" '"+orderType+"' ORDER_TYPE, \n")
		      .append(" '"+orderTypeName+"' ORDER_TYPE_NAME, \n")
		      .append(" AMD.DEPT_CODE IMPLEMENT_DEPT_ID, \n")
		      .append(" AMD.DEPT_NAME IMPLEMENT_DEPT_NAME, \n")
		      .append(" SU.USERNAME IMPLEMENT_NAME, \n")
		      .append(" SU.ORGANIZATION_ID IMPLEMENT_ORGANIZATION_ID, \n")
		      .append(" SU.USER_ID IMPLEMENT_BY,AME.EMPLOYEE_NUMBER \n")
			  .append(" FROM SF_USER SU,SF_USER_AUTHORITY SUA,AMS_MIS_EMPLOYEE AME,AMS_MIS_DEPT AMD \n")
			  .append(" where SU.USER_ID = SUA.USER_ID \n")
			  .append(" and SU.ENABLED = 'Y' ")
			  .append(" and SU.EMPLOYEE_NUMBER = AME.EMPLOYEE_NUMBER \n")
			  .append(" and AME.DEPT_CODE = AMD.DEPT_CODE \n")
			  .append(" and SUA.ROLE_NAME ='��˾�ʲ�����Ա' \n")
          .append(" and SU.ORGANIZATION_ID = ? \n");
		}else if(lookUpName.equals("CITY_NON_ADDRESS_SOFTEWARE_2") || lookUpName.equals("CITY_NON_ADDRESS_CLIENT_2")
				|| lookUpName.equals("CITY_NON_ADDRESS_PIPELINE_2") ){ //�����ʲ�����Ա
			sqlStr.append(" SELECT '�·����Զ�����' ORDER_NUMBER, \n")
		      .append(" '"+orderName+"' ORDER_NAME, \n")
		      .append(" '"+orderType+"' ORDER_TYPE, \n")
		      .append(" '"+orderTypeName+"' ORDER_TYPE_NAME, \n")
		      .append(" AMD.DEPT_CODE IMPLEMENT_DEPT_ID, \n")
		      .append(" AMD.DEPT_NAME IMPLEMENT_DEPT_NAME, \n")
		      .append(" SU.USERNAME IMPLEMENT_NAME, \n")
		      .append(" SU.ORGANIZATION_ID IMPLEMENT_ORGANIZATION_ID, \n")
		      .append(" SU.USER_ID IMPLEMENT_BY,AME.EMPLOYEE_NUMBER \n")
			  .append(" FROM SF_USER SU,SF_USER_AUTHORITY SUA,AMS_MIS_EMPLOYEE AME,AMS_MIS_DEPT AMD \n")
			  .append(" where SU.USER_ID = SUA.USER_ID \n")
			  .append(" and SU.ENABLED = 'Y' ")
			  .append(" and SU.EMPLOYEE_NUMBER = AME.EMPLOYEE_NUMBER \n")
			  .append(" and AME.DEPT_CODE = AMD.DEPT_CODE \n")
			  .append(" and SUA.ROLE_NAME ='"+implementsRoleName+"' \n")
            .append(" and SU.ORGANIZATION_ID = ? \n");
		}else if(lookUpName.equals("CITY_NON_ADDRESS_SOFTEWARE_3")|| lookUpName.equals("CITY_NON_ADDRESS_CLIENT_3")
				|| lookUpName.equals("CITY_NON_ADDRESS_PIPELINE_3") ){//�ʲ�������
			sqlStr.append(" SELECT '�·����Զ�����' ORDER_NUMBER, \n")
		      .append(" '"+orderName+"' ORDER_NAME, \n")
		      .append(" '"+orderType+"' ORDER_TYPE, \n")
		      .append(" '"+orderTypeName+"' ORDER_TYPE_NAME, \n")
		      .append(" AMD.DEPT_CODE IMPLEMENT_DEPT_ID, \n")
		      .append(" AMD.DEPT_NAME IMPLEMENT_DEPT_NAME, \n")
		      .append(" SU.USERNAME IMPLEMENT_NAME, \n")
		      .append(" SU.ORGANIZATION_ID IMPLEMENT_ORGANIZATION_ID, \n")
		      .append(" SU.USER_ID IMPLEMENT_BY,AME.EMPLOYEE_NUMBER \n")
			  .append(" FROM SF_USER SU,AMS_MIS_EMPLOYEE AME,AMS_MIS_DEPT AMD \n")
			  .append(" where  SU.ENABLED = 'Y' \n")
			  .append(" and SU.EMPLOYEE_NUMBER = AME.EMPLOYEE_NUMBER \n")
			  .append(" and AME.DEPT_CODE = AMD.DEPT_CODE \n")
              .append(" and SU.ORGANIZATION_ID = ? \n");
		}
		sqlArgs.add(user.getOrganizationId());
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr.toString());
	    return sqlModel;
	}
}
