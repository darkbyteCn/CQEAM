package com.sino.ams.yearchecktaskmanager.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newSite.dto.EamAddressAddHDTO;
import com.sino.ams.newSite.model.EamAddressSecondAddHModel;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.model.AssetsConfirmModel;
import com.sino.ams.newasset.model.EtsFaAssetsModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.ZeroTurnHeaderDTO;
import com.sino.ams.workorder.model.ZeroTurnModel;
import com.sino.ams.yearchecktaskmanager.dto.EtsItemYearCheckDTO;
import com.sino.ams.yearchecktaskmanager.dto.EtsItemYearCheckLineDTO;
import com.sino.ams.yearchecktaskmanager.model.AssetsYearCheckFaModel;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.data.Row;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

public class AssetsYearCheckFaDAO extends AMSBaseDAO {

	public AssetsYearCheckFaDAO(SfUserDTO userAccount,
			EtsItemYearCheckDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	@Override
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		EtsItemYearCheckDTO dto = (EtsItemYearCheckDTO) dtoParameter;
		sqlProducer = new AssetsYearCheckFaModel((SfUserDTO) userAccount, dto);
	}

	/**
	 * 获取当前用户拥有的非实地盘点资产信息
	 * @throws ContainerException 
	 */
	public DTOSet getLineData() throws QueryException, ContainerException {
		EtsItemYearCheckDTO dto = (EtsItemYearCheckDTO) dtoParameter;
		String[] str=getLevel(dto);
		dto.setLevel(str[0]);
		dto.setSendType(str[1]);
		AssetsYearCheckFaModel model = (AssetsYearCheckFaModel) sqlProducer;
		SQLModel sqlModel = model.getLineModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(EtsItemYearCheckLineDTO.class.getName());
		simp.executeQuery();
		return simp.getDTOSet();
	}


	// jeffery  <!-- 2013-07-04 Jeffery-->
	public boolean confirm(String str) {
		boolean flag=true;
		int userId=userAccount.getUserId();
		int orgId=userAccount.getOrganizationId();
		try {
			String dot[] = str.split(";");
			for (int i = 0; i < dot.length; i++) {
				String dStr = dot[i];
				int index = dStr.indexOf(",");
				String barcode = dStr.substring(0, index);
				String nextStr = dStr.substring(index+1);
				int index2 = nextStr.indexOf(",");
				String checkStatus = nextStr.substring(0, index2);
				String notes = nextStr.substring(index2+1);

				AssetsYearCheckFaModel model = (AssetsYearCheckFaModel) sqlProducer;
				SQLModel sqlModel = model.getConfirmModel(barcode, checkStatus,
						notes);
				DBOperator.updateRecord(sqlModel, conn);
				
			}
		} catch (DataHandleException e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	
	 
	 public File exportFile(SfUserDTO user, EtsItemYearCheckDTO dto, Connection conn) throws DataTransException, SQLModelException, QueryException, ContainerException {
	       File file = null;
	       String[] str=getLevel(dto);
		   dto.setLevel(str[0]);
		   dto.setSendType(str[1]);
	       AssetsYearCheckFaModel assetsModel = (AssetsYearCheckFaModel)sqlProducer;
		   SQLModel sqlModel =assetsModel.getLineModel();
		   String reportTitle = "";

		   reportTitle = "个人待确认非实地资产";
		   String fileName = reportTitle + ".xls";
		   String filePath = WorldConstant.USER_HOME;
		   filePath += WorldConstant.FILE_SEPARATOR;
		   filePath += fileName;
		   TransRule rule = new TransRule();
		   rule.setDataSource(sqlModel);
		   rule.setSourceConn(conn);
		   rule.setTarFile(filePath);
		   DataRange range = new DataRange();
		   rule.setDataRange(range);
		   Map fieldMap = new HashMap();

		   fieldMap.put("BARCODE", "资产标签号");
		   fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
		   fieldMap.put("CONTENT_NAME", "资产类别描述");
		   fieldMap.put("CHECK_STATUS", "确认状态");
		   fieldMap.put("NOTES", "备注");
		   rule.setFieldMap(fieldMap);
		   CustomTransData custData = new CustomTransData();
		   custData.setReportTitle(reportTitle);
		   custData.setReportPerson(userAccount.getUsername());
		   custData.setNeedReportDate(true);
		   rule.setCustData(custData);
		   rule.setCalPattern(LINE_PATTERN);
		   TransferFactory factory = new TransferFactory();
		   DataTransfer transfer = factory.getTransfer(rule);
		   transfer.transData();
		   file = (File) transfer.getTransResult();
	       return file;
	   }
	 
	 //获取任务等级
	 public String[] getLevel(EtsItemYearCheckDTO dto) throws QueryException, ContainerException{
	    String level="";					//任务等级
	    String orderType=dto.getOrderType();//工单类型
	    String sendType="";
	    String deptCode="";
	    String [] str=new String[3];
	    
	    AssetsYearCheckFaModel model = (AssetsYearCheckFaModel) sqlProducer;
		SQLModel sqlModel = model.getQueryLevelModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.executeQuery();
        Row row = sq.getFirstRow();
        
        level = row.getStrValue("ORDER_LEVEL");
        if(orderType.equals(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_SOFTWARE)){
        	sendType = row.getStrValue("NON_ADDRESS_ASSETS_SOFT");
        }else if(orderType.equals(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_CLIENT)){
        	sendType = row.getStrValue("NON_ADDRESS_ASSETS_CLIENT");
        }else if(orderType.equals(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_PIPELINE)){
        	sendType = row.getStrValue("NON_ADDRESS_ASSETS_PIPELINE");
        }
        deptCode = row.getStrValue("IMPLEMENT_DEPT_ID");
       
        if(level==null){
        	level="";
        }
        if(sendType==null){
        	sendType="";
        }
        if(deptCode==null){
        	deptCode="";
        }
        str[0]=level;
        str[1]=sendType;
        str[2]=deptCode;
        return str;
	}
	 
}
