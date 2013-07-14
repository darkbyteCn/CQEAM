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
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
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
	 */
	public DTOSet getLineData() throws QueryException {
		EtsItemYearCheckDTO dto = (EtsItemYearCheckDTO) dtoParameter;
		AssetsYearCheckFaModel model = (AssetsYearCheckFaModel) sqlProducer;
		SQLModel sqlModel = model.getLineModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(EtsItemYearCheckLineDTO.class.getName());
		simp.executeQuery();
		return simp.getDTOSet();
	}


	// jeffery
	public void confirm(String str) {
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
				
				SQLModel sqlModel2 = model.getMatchUpdateModel(barcode, checkStatus, notes);
				DBOperator.updateRecord(sqlModel2, conn);

			}
		} catch (DataHandleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	 public File exportFile(SfUserDTO user, EtsItemYearCheckDTO dto, Connection conn) throws DataTransException {
//	        File file = null;
//	        DataTransfer transfer = null;
//			AssetsYearCheckFaModel assetsModel = (AssetsYearCheckFaModel)sqlProducer;
//			SQLModel sqlModel =assetsModel.getLineModel();
//			TransRule rule = new TransRule();
//			rule.setDataSource(sqlModel);
//			rule.setCalPattern(CalendarConstant.LINE_PATTERN);
//			rule.setSourceConn(conn);
//			String fileName = "";
//			fileName = "个人待确认非实地资产.xls";
//			String filePath = WorldConstant.USER_HOME;
//			filePath += WorldConstant.FILE_SEPARATOR;
//			filePath += fileName;
//			rule.setTarFile(filePath);
//
//			DataRange range = new DataRange();
//			rule.setDataRange(range);
//
//			Map fieldMap = new HashMap();
//
//			fieldMap.put("BARCODE", "资产标签号");
//			fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
//			fieldMap.put("CONTENT_NAME", "资产类别描述");
//			fieldMap.put("CHECK_STATUS", "确认状态");
//			fieldMap.put("NOTES", "备注");
//
//			rule.setFieldMap(fieldMap);
//
//			CustomTransData custData = new CustomTransData();
//			custData.setReportTitle(fileName);
//			custData.setReportPerson(user.getUsername());
//			custData.setNeedReportDate(true);
//			rule.setCustData(custData);
//			//设置分页显示
//			TransferFactory factory = new TransferFactory();
//			transfer = factory.getTransfer(rule);
//			transfer.transData();
//			file = (File) transfer.getTransResult();
//	        return file;
//	    }
//	 
	 
	 public File exportFile(SfUserDTO user, EtsItemYearCheckDTO dto, Connection conn) throws DataTransException, SQLModelException {
	       File file = null;
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
	 
	 
	// jeffery
		public void conClient(String str) {
//			int userId=userAccount.getUserId();
//			int orgId=userAccount.getOrganizationId();
			try {
				String dot[] = str.split(",");
				for (int i = 0; i < dot.length; i++) {
					String barcode=dot[i];
					if(!barcode.equals("")){
						AssetsYearCheckFaModel model = (AssetsYearCheckFaModel) sqlProducer;
						SQLModel sqlModel = model.getClientModel(barcode);
						DBOperator.updateRecord(sqlModel, conn);
					}
				}
			} catch (DataHandleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		 public File exportClientFile(SfUserDTO user, EtsItemYearCheckDTO dto, Connection conn) throws DataTransException, SQLModelException {
		       File file = null;
		       AssetsYearCheckFaModel assetsModel = (AssetsYearCheckFaModel)sqlProducer;
			   SQLModel sqlModel =assetsModel.getExportModel();
			   String reportTitle = "";
			   reportTitle = "客户端资产确认";
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
			   fieldMap.put("FA_CATEGORY1", "应用领域描述");
			   fieldMap.put("WORKORDER_OBJECT_CODE", "地点编码");
			   fieldMap.put("WORKORDER_OBJECT_NAME", "地点描述");
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

}
