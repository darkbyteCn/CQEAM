package com.sino.ams.yearchecktaskmanager.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dto.ASSCHKTaskRemainDTO;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;

public class ASSCHKTaskRemainModel extends BaseSQLProducer {
	  
	private SfUserDTO sfUser = null;
	  
	  public ASSCHKTaskRemainModel(SfUserDTO userAccount, ASSCHKTaskRemainDTO dtoParameter) {
	        super(userAccount, dtoParameter);
	        sfUser = userAccount;
	  }
	  
	  //��ȡ�̵����������б�
	  public SQLModel getData() {
	        SQLModel sqlModel = new SQLModel();
	        List sqlArgs = new ArrayList();
	        
	        StringBuffer sqlBuffer  = new StringBuffer();
	        sqlBuffer.append(" SELECT TOP 10 * from ( \n")
	                 .append(" select ORDER_NAME,ORDER_NUMBER,CREATION_DATE,ORDER_TYPE,IMPLEMNET_ROLE_NAME,ORDER_LEVEL,\n")
   		             .append(" case when ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK+"' then '����̵�' " )
				     .append(" when ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_SOFTWARE+"' then '��ʵ��[�����]' " )
				     .append(" when ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_CLIENT+"' then '��ʵ��[�ͻ�����]' " )
				     .append(" when ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_PIPELINE+"' then '��ʵ��[���¡���·���ܵ���]' " )
				     .append(" when ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_WIRELESS+"' then 'ʵ��������' " )
				     .append(" when ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_NON_WIRELESS+"' then 'ʵ�ط�������' " )
				     .append(" else '����̵�' end ORDER_TYPE_NAME ")
			   		 .append(" from AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE \n")
			   		 .append(" where IMPLEMENT_BY=? and ORDER_STATUS NOT IN('DO_CANCLE','DO_RETURN','DO_COMPLETE') \n")
			   		 .append(" ) T ORDER BY CREATION_DATE ");

	     
	        sqlArgs.add(sfUser.getUserId());
//	        sqlArgs.add(sfUser.getOrganizationId());
	        sqlModel.setArgs(sqlArgs);
	        sqlModel.setSqlStr(sqlBuffer.toString());
	        return sqlModel;
	 }
	  
	  public SQLModel getAllTaskRemainModel(String taskName) {
	        SQLModel sqlModel = new SQLModel();
	        ArrayList sqlArgs = new ArrayList();
	        
	        String sqlStr=" SELECT ORDER_NAME,ORDER_NUMBER,CREATION_DATE,ORDER_TYPE,IMPLEMNET_ROLE_NAME,ORDER_LEVEL,\n" +
	        		      " CASE WHEN ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK+"' then '����̵�' " +
	        		      " WHEN ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_SOFTWARE+"' then '��ʵ��[�����]'" +
	        		      " WHEN ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_CLIENT+"' then '��ʵ��[�ͻ�����]'" +
	        		      " WHEN ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_PIPELINE+"' then '��ʵ��[���¡���·���ܵ���]' " +
	        		      " WHEN ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_WIRELESS+"' then ' ʵ��������'" +
	        		      " WHEN ORDER_TYPE='"+AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_NON_WIRELESS+"' then 'ʵ�ط�������' " +
	        		      " ELSE '����̵�' end ORDER_TYPE_NAME" +
	        		      " FROM AMS_ASSETS_YAER_CHECK_TASK_ORDER_LINE \n" +
	        		      " WHERE IMPLEMENT_BY=? and ORDER_STATUS NOT IN('DO_CANCLE','DO_RETURN','DO_COMPLETE') \n";
	        
	        if(taskName!=null && !taskName.equals("")){
	        	sqlStr+=" AND ORDER_NAME LIKE '%"+taskName.trim()+"%'";
	        }
	        
	        sqlArgs.add(sfUser.getUserId());
//	        sqlArgs.add(sfUser.getOrganizationId());
	        sqlModel.setArgs(sqlArgs);
	        sqlModel.setSqlStr(sqlStr);
	        return sqlModel;
	    }

}
