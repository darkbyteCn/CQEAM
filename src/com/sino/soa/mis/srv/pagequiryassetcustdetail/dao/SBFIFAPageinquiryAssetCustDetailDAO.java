package com.sino.soa.mis.srv.pagequiryassetcustdetail.dao;

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
import com.sino.soa.mis.srv.pagequiryassetcustdetail.dto.SBFIFAPageinquiryAssetCustDetailDTO;
import com.sino.soa.mis.srv.pagequiryassetcustdetail.model.SBFIFAPageinquiryAssetCustDetailModel;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.SynLogDTO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-15
 * Time: 21:14:31
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFAPageinquiryAssetCustDetailDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	public SBFIFAPageinquiryAssetCustDetailDAO(SfUserDTO userAccount, SBFIFAPageinquiryAssetCustDetailDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SBFIFAPageinquiryAssetCustDetailDTO dtoPara = (SBFIFAPageinquiryAssetCustDetailDTO)dtoParameter;
		super.sqlProducer = new SBFIFAPageinquiryAssetCustDetailModel((SfUserDTO)userAccount, dtoPara);
	}

	public int SavaAssetCategory(DTOSet ds){
		SynLogUtil log=new SynLogUtil();
        SQLModel sqlModel = null;
        int count=0;
        try {
        	for (int i =0 ; i < ds.getSize(); i++) {
        		SBFIFAPageinquiryAssetCustDetailDTO dto = (SBFIFAPageinquiryAssetCustDetailDTO) ds.getDTO(i);
        		initSQLProducer(sfUser,dto);
        		SBFIFAPageinquiryAssetCustDetailModel modelProducer = (SBFIFAPageinquiryAssetCustDetailModel) sqlProducer;
                if (!dto.getTagNumber().equals("")&&!dto.getProjectNumber().equals("")&&!dto.getEmployeeNumber().equals("")&&(dto.getAttribute3().equals("")||dto.getAttribute3().equals(null))) { //&&(dto.getAttribute3().equals("")||dto.getAttribute3().equals(null))
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

    public boolean isEcouInformation(String projectAssetId, String taskId, String projectId){
		boolean returnFlag=false;
        SBFIFAPageinquiryAssetCustDetailModel modelProducer = (SBFIFAPageinquiryAssetCustDetailModel) sqlProducer;
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
        String sqlStr = "{call dbo.ZTE_FA_CUST_DETAIL_SYNC(?, ?)}";
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
     * 统计同步数量
     * @return
     */
    public String getSyncTotalCount(String projectNum) {
        String syncTotalCount = "0";
        try {
            SBFIFAPageinquiryAssetCustDetailModel modelProducer = (SBFIFAPageinquiryAssetCustDetailModel) sqlProducer;
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
            SBFIFAPageinquiryAssetCustDetailModel modelProducer = (SBFIFAPageinquiryAssetCustDetailModel) sqlProducer;
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

    public void deleteImpData(String projectNum) throws SQLModelException, DataHandleException {
        SBFIFAPageinquiryAssetCustDetailModel model = new SBFIFAPageinquiryAssetCustDetailModel(sfUser, new SBFIFAPageinquiryAssetCustDetailDTO());
        DBOperator.updateRecord(model.getDataImpDeleteModel(projectNum), conn);
    }

    public void deleteLogData() throws SQLModelException, DataHandleException {
        SBFIFAPageinquiryAssetCustDetailModel model = new SBFIFAPageinquiryAssetCustDetailModel(sfUser, new SBFIFAPageinquiryAssetCustDetailDTO());
        DBOperator.updateRecord(model.getDataLogDeleteModel(), conn);

    }

    public RowSet getErrorRow() throws QueryException {
        SBFIFAPageinquiryAssetCustDetailModel model = new SBFIFAPageinquiryAssetCustDetailModel(sfUser, new SBFIFAPageinquiryAssetCustDetailDTO());
        SimpleQuery sq = new SimpleQuery(model.getErrorRowModel(), conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        return rs;
    }

}