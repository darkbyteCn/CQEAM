package com.sino.ams.workorder.dao;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.ZeroTurnDifferentCheckDTO;
import com.sino.ams.workorder.model.ZeroTurnDifferentCheckModel;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.constant.WorldConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
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
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.common.SrvType;
import com.sino.soa.mis.srv.dealProjectDiversity.dto.DealProjectDiversityCheckDTO;
import com.sino.soa.mis.srv.dealProjectDiversity.model.DealProjectDiversityCheckModel;
import com.sino.soa.mis.srv.pagequiryassetcustdetail.dto.SBFIFAPageinquiryAssetCustDetailDTO;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.SynLogDTO;

public class ZeroTurnDifferentCheckDAO extends BaseDAO {
	
	private SfUserDTO sfUser = null;

	public ZeroTurnDifferentCheckDAO(BaseUserDTO userAccount, DTO dtoParameter,
			Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = (SfUserDTO) userAccount;
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		ZeroTurnDifferentCheckDTO dtoPara = (ZeroTurnDifferentCheckDTO) dtoParameter;
		super.sqlProducer = new ZeroTurnDifferentCheckModel(
				(SfUserDTO) userAccount, dtoPara);
	}
	
	//查詢
	public RowSet getDataRow(String bookTypeCode,String procureCode,String diffTypeCode) throws QueryException {
		ZeroTurnDifferentCheckModel model = new ZeroTurnDifferentCheckModel(
				sfUser, new ZeroTurnDifferentCheckDTO());
		SimpleQuery sq = new SimpleQuery(model.getDataRow( bookTypeCode, procureCode, diffTypeCode), conn);
		sq.executeQuery();
		RowSet rs = sq.getSearchResult();
		return rs;
	}
	
	//核对
	public void checkEtsItemInfo(String bookTypeCode,String procureCode){
	       CallableStatement cs = null;
		      try {
		          String callStr = "{CALL dbo.GENE_EAM_DIFF_PA_VS_EII_ZERO_TURN(?, ?)}";
		          cs = conn.prepareCall(callStr);
		          cs.setString(1,bookTypeCode);
		          cs.setString(2,procureCode);
		          cs.execute();
		      } catch (SQLException ex) {
		          Logger.logError(ex);
		      } catch (Throwable ex) {
		          Logger.logError(ex);
		      } finally {
		          DBManager.closeDBStatement(cs);
		      }
	  }

	public int SavaAssetCategory(DTOSet ds) {
		SynLogUtil log = new SynLogUtil();
		SQLModel sqlModel = null;
		int count = 0;
		try {
			for (int i = 0; i < ds.getSize(); i++) {
				SBFIFAPageinquiryAssetCustDetailDTO dtoSb = (SBFIFAPageinquiryAssetCustDetailDTO) ds
						.getDTO(i);
				DealProjectDiversityCheckDTO deal = new DealProjectDiversityCheckDTO();
				// 赋值
				deal.setTagNumber(dtoSb.getTagNumber());
				deal.setAssetName(dtoSb.getAssetName());
				deal.setAssetDescription(dtoSb.getAssetDescription());
				deal.setModelNumber(dtoSb.getModelNumber());
				deal.setAssetCategory(dtoSb.getAssetCategory());
				deal.setAssetCategoryDesc(dtoSb.getAssetCategoryDesc());
				deal.setUnitOfMeasure(dtoSb.getUnitOfMeasure());
				deal.setEmployeeNumber(dtoSb.getEmployeeNumber());
				deal.setLocationCode(dtoSb.getLocationCode());
				deal.setAssetLocation(dtoSb.getAssetLocation());
				deal.setManufactorerName(dtoSb.getManufactorerName());
				deal.setAttribute4(dtoSb.getAttribute4());
				deal.setAttribute5(dtoSb.getAttribute5());
				deal.setAttribute6(dtoSb.getAttribute6());
				deal.setAttribute7(dtoSb.getAttribute7());
				deal.setBookTypeCode(dtoSb.getBookTypeCode());
				deal.setProjectNumber(dtoSb.getProjectNumber());
				deal.setAssetUnits(dtoSb.getAssetUnits());
				deal.setTaskId(dtoSb.getTaskId());
				deal.setProjectId(dtoSb.getProjectId());
				deal.setProjectAssetId(dtoSb.getProjectAssetId());
				// 增加start_date
				deal.setDatePlacedInService(getHandleDateFromERP(dtoSb
						.getDatePlacedInService()));
				// 赋值结束
				initSQLProducer(sfUser, deal);
				DealProjectDiversityCheckModel modelProducer = (DealProjectDiversityCheckModel) sqlProducer;
				if (!deal.getTagNumber().equals("")
						&& !deal.getProjectNumber().equals("")
						&& !deal.getEmployeeNumber().equals("")) { //
					sqlModel = modelProducer.getDataCreateModel();
					if (DBOperator.updateRecord(sqlModel, conn)) {
						count++;
					}
				}
			}
		} catch (DataHandleException e) {
			e.printLog();
			SimpleCalendar s = new SimpleCalendar();
			SynLogDTO logDto = new SynLogDTO();
			logDto.setSynType(SrvType.SRV_FA_PAGE_CUST_DETAIL);
			logDto.setSynMsg(e.getMessage());
			logDto.setCreatedBy(sfUser.getUserId());
			try {
				logDto.setCreationDate(s.getCalendarValue());
			} catch (CalendarException e1) {
				e1.printLog();
			}
			try {
				log.synLog(logDto, conn);
			} catch (DataHandleException e1) {
				e1.printLog();
			}
		} catch (SQLModelException e) {
			e.printLog();
		}
		return count;
	}

	/**
	 * 处理从ERP过来的这种格式“2011-08-15 00:00:00.000+08:00”时间
	 * 
	 * @param date
	 * @return
	 */
	private String getHandleDateFromERP(String date) {
		if (StrUtil.isEmpty(date) || date.length() < 11) {
			return date;
		}
		date = date.replace("T", " ");
		int pos = date.indexOf("+");
		if (pos > -1) {
			date = date.substring(0, pos);
		}
		return date;
	}

	public boolean isEcouInformation(String projectAssetId, String taskId,
			String projectId) {
		boolean returnFlag = false;
		DealProjectDiversityCheckModel modelProducer = (DealProjectDiversityCheckModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getEcouInforModel(projectAssetId,
				taskId, projectId);
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		try {
			simp.executeQuery();
			if (simp.hasResult()) {
				returnFlag = true;
			}
		} catch (QueryException e) {
			e.printLog();
		}
		return returnFlag;
	}
	public String getMisPId() {
		String projectId="";
		DealProjectDiversityCheckModel modelProducer = (DealProjectDiversityCheckModel) sqlProducer;
		DealProjectDiversityCheckDTO srvAssetCategory = (DealProjectDiversityCheckDTO) dtoParameter;
		String projectNum2 =srvAssetCategory.getProjectNumber2();
		SQLModel sqlModel = modelProducer.getMisProjectId_two(projectNum2);
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		try {
			simp.executeQuery();
			if (simp.hasResult()) {	
				try {
					projectId = simp.getFirstRow().getStrValue("MIS_PROJECT_ID");
				} catch (ContainerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return projectId;
	}
	public void SyncEam() {
		CallableStatement cStmt = null;
		DealProjectDiversityCheckDTO srvAssetCategory = (DealProjectDiversityCheckDTO) dtoParameter;
		
		String sqlStr = "{call dbo.GENE_EAM_DIFF_PA_VS_EII(?,?)}";
		try {
			String misId=getMisPId();
			cStmt = conn.prepareCall(sqlStr);
			cStmt.setString(1, srvAssetCategory.getBookTypeCode());
			cStmt.setString(2, misId);
			cStmt.execute();
//			String count = cStmt.getString(1);// 重要：必须要取一下返回参数，否则存储过程中循环有问题（勿删）
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBStatement(cStmt);
		}
	}

	/**
	 * 统计同步数量
	 * 
	 * @return
	 */
	public String getSyncTotalCount(String projectNum) {
		String syncTotalCount = "0";
		try {
			DealProjectDiversityCheckModel modelProducer = (DealProjectDiversityCheckModel) sqlProducer;
			SQLModel sqlModel = modelProducer
					.getSyncTotalCountModel(projectNum);
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				syncTotalCount = simp.getFirstRow().getStrValue(
						"SYNC_TOTAL_COUNT");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return syncTotalCount;
	}

	public String getSyncErrorCount() {
		String syncErrorCount = "0";
		try {
			DealProjectDiversityCheckModel modelProducer = (DealProjectDiversityCheckModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getSyncErrorModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				syncErrorCount = simp.getFirstRow().getStrValue(
						"SYNC_ERROR_COUNT");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return syncErrorCount;
	}

	public void deleteImpData(String projectNum, String bookTypeCode)
			throws SQLModelException, DataHandleException {
		DealProjectDiversityCheckModel model = new DealProjectDiversityCheckModel(
				sfUser, new DealProjectDiversityCheckDTO());
		DBOperator.updateRecord(
				model.getDataImpDeleteModel(projectNum, bookTypeCode), conn);
	}

	public void deleteLogData() throws SQLModelException, DataHandleException {
		DealProjectDiversityCheckModel model = new DealProjectDiversityCheckModel(
				sfUser, new DealProjectDiversityCheckDTO());
		DBOperator.updateRecord(model.getDataLogDeleteModel(), conn);

	}

	public RowSet getErrorRow() throws QueryException {
		DealProjectDiversityCheckModel model = new DealProjectDiversityCheckModel(
				sfUser, new DealProjectDiversityCheckDTO());
		SimpleQuery sq = new SimpleQuery(model.getErrorRowModel(), conn);
		sq.executeQuery();
		RowSet rs = sq.getSearchResult();
		return rs;
	}

	// public RowSet getDiversityCheck(String misProjectId,String diffTypeCode)
	// throws QueryException, SQLModelException, RuntimeException {
	// DealProjectDiversityCheckModel model = new
	// DealProjectDiversityCheckModel(sfUser, new
	// DealProjectDiversityCheckDTO());
	// SimpleQuery sq = new SimpleQuery(model.getPageQueryModel(misProjectId ,
	// diffTypeCode), conn);
	// sq.executeQuery();
	// RowSet rs = sq.getSearchResult();
	// return rs;
	// }
	public File getExportFile(String bookTCode, String procureCode, String diffTypeCode) throws SQLModelException {
		ZeroTurnDifferentCheckModel modelProducer = (ZeroTurnDifferentCheckModel) sqlProducer;
		SQLModel sqlModel;
		sqlModel = modelProducer.getPageQueryModel(bookTCode, procureCode, diffTypeCode);
		String reportTitle = "零购差异核对";
		String fileName = reportTitle + ".xls";
		TransRule rule = new TransRule();
		rule.setDataSource(sqlModel);
		rule.setSourceConn(conn);
		String filePath = WorldConstant.USER_HOME;
		filePath += WorldConstant.FILE_SEPARATOR;
		filePath += fileName;
		rule.setTarFile(filePath);
		DataRange range = new DataRange();
		rule.setDataRange(range);
		rule.setFieldMap(getFieldMap());
		CustomTransData custData = new CustomTransData();
		custData.setReportTitle(reportTitle);
		custData.setReportPerson(sfUser.getUsername());
		custData.setNeedReportDate(true);
		rule.setCustData(custData);
		rule.setCalPattern(CAL_PATT_50);
		TransferFactory factory = new TransferFactory();
		DataTransfer transfer = null;
		try {
			transfer = factory.getTransfer(rule);
		} catch (DataTransException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			transfer.transData();
		} catch (DataTransException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (File) transfer.getTransResult();

	}

	private HashMap getFieldMap() {
		HashMap fieldMap = new HashMap();
		fieldMap.put("BOOK_TYPE_CODE", "资产账簿");
		fieldMap.put("PROCURE_CODE", "采购单号");
		fieldMap.put("TAG_NUMBER", "资产标签号");
		fieldMap.put("DIFF_TYPE", "差异类型");
		fieldMap.put("PA_VALUE", "PA值");
		fieldMap.put("EAM_VALUE", "EAM值");
		return fieldMap;
	}


}
