package com.sino.soa.td.srv.pagequiryassetcustdetail.dao;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.common.SrvType;
import com.sino.soa.td.srv.pagequiryassetcustdetail.dto.SBFIFATdPageinquiryAssetCustDetailDTO;
import com.sino.soa.td.srv.pagequiryassetcustdetail.model.SBFIFATdPageinquiryAssetCustDetailModel;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.SynLogDTO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-16
 * Time: 16:53:01
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFATdPageinquiryAssetCustDetailDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	public SBFIFATdPageinquiryAssetCustDetailDAO(SfUserDTO userAccount, SBFIFATdPageinquiryAssetCustDetailDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SBFIFATdPageinquiryAssetCustDetailDTO dtoPara = (SBFIFATdPageinquiryAssetCustDetailDTO)dtoParameter;
		super.sqlProducer = new SBFIFATdPageinquiryAssetCustDetailModel((SfUserDTO)userAccount, dtoPara);
	}
  
	/**
	 * 写入临时表
	 * @param ds
	 * @return
	 */
	public int SavaAssetCategory(DTOSet ds){
		SynLogUtil log=new SynLogUtil();
        SQLModel sqlModel = null;
        int count=0;
        try {
        	for (int i =0 ; i < ds.getSize(); i++) {
        		SBFIFATdPageinquiryAssetCustDetailDTO dto = (SBFIFATdPageinquiryAssetCustDetailDTO) ds.getDTO(i);
        		initSQLProducer(sfUser,dto);
        		SBFIFATdPageinquiryAssetCustDetailModel modelProducer = (SBFIFATdPageinquiryAssetCustDetailModel) sqlProducer;
                if (!dto.getTagNumber().equals("")&&!dto.getProjectNumber().equals("")&&!dto.getEmployeeNumber().equals("")&&(dto.getAttribute3().equals("")||dto.getAttribute3().equals(null))) {
                    sqlModel=modelProducer.getDataCreateModel();
					if(DBOperator.updateRecord(sqlModel, conn)){
						count++;
					}
                }
        	}
		} catch (DataHandleException e) {
			e.printLog();
			SimpleCalendar s=new SimpleCalendar();
			SynLogDTO logDto=new SynLogDTO();
			logDto.setSynType(SrvType.SRV_TD_FA_PAGE_CUST_DETAIL);
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

    public boolean isEcouInformation(String projectAssetId, String taskId, String projectId){
		boolean returnFlag=false;
        SBFIFATdPageinquiryAssetCustDetailModel modelProducer = (SBFIFATdPageinquiryAssetCustDetailModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getEcouInforModel(projectAssetId, taskId, projectId);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        try {
			simp.executeQuery();
			if(simp.hasResult()){
                returnFlag = true;
			}
		} catch (QueryException e) {
			 e.printLog();
		}
        return returnFlag;
	}

    public void SyncEam() {
        CallableStatement cStmt = null;
        String sqlStr = "{call dbo.ZTE_TD_CUST_DETAIL_SYNC(?, ?)}";
        try {
			cStmt = conn.prepareCall(sqlStr);
            cStmt.setInt(1, sfUser.getUserId());
            cStmt.registerOutParameter(2, Types.INTEGER);
            cStmt.execute();
            int count=cStmt.getInt(2);//重要：必须要取一下返回参数，否则存储过程中循环有问题（勿删）
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
            DBManager.closeDBStatement(cStmt);
        }
    }

	/**
	 * 统计同步数量（临时表）
	 * @return
	 */
    public String getSyncTotalCount(String projectNum) {
        String syncTotalCount = "0";
        try {
            SBFIFATdPageinquiryAssetCustDetailModel modelProducer = (SBFIFATdPageinquiryAssetCustDetailModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getSyncTotalCountModel(projectNum);
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                syncTotalCount = simp.getFirstRow().getStrValue("SYNC_TOTAL_COUNT");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return syncTotalCount;
    }

    public String getSyncErrorCount() {
        String syncErrorCount = "0";
        try {
            SBFIFATdPageinquiryAssetCustDetailModel modelProducer = (SBFIFATdPageinquiryAssetCustDetailModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getSyncErrorModel();
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                syncErrorCount = simp.getFirstRow().getStrValue("SYNC_ERROR_COUNT");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return syncErrorCount;
    }
    
    /**
     * 删除临时表信息
     * @param projectNum
     * @return
     */
    public void deleteImpData(String projectNum) throws SQLModelException, DataHandleException {
        SBFIFATdPageinquiryAssetCustDetailModel model = new SBFIFATdPageinquiryAssetCustDetailModel(sfUser, new SBFIFATdPageinquiryAssetCustDetailDTO());
        DBOperator.updateRecord(model.getDataImpDeleteModel(projectNum), conn);
    }

    public void deleteLogData() throws SQLModelException, DataHandleException {
        SBFIFATdPageinquiryAssetCustDetailModel model = new SBFIFATdPageinquiryAssetCustDetailModel(sfUser, new SBFIFATdPageinquiryAssetCustDetailDTO());
        DBOperator.updateRecord(model.getDataLogDeleteModel(), conn);
    }

    public RowSet getErrorRow() throws QueryException {
        SBFIFATdPageinquiryAssetCustDetailModel model = new SBFIFATdPageinquiryAssetCustDetailModel(sfUser, new SBFIFATdPageinquiryAssetCustDetailDTO());
        SimpleQuery sq = new SimpleQuery(model.getErrorRowModel(), conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        return rs;
    }

}