package com.sino.soa.td.srv.pagequiryaccountbalance.dao;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.calen.SimpleCalendar;
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
import com.sino.soa.td.srv.pagequiryaccountbalance.dto.SBFIGLTdPageQuiryAccountBalanceDTO;
import com.sino.soa.td.srv.pagequiryaccountbalance.model.SBFIGLTdPageQuiryAccountBalanceModel;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.SynLogDTO;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-15
 * Time: 20:36:07
 * To change this template use File | Settings | File Templates.
 */
public class SBFIGLTdPageQuiryAccountBalanceDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	public SBFIGLTdPageQuiryAccountBalanceDAO(SfUserDTO userAccount, SBFIGLTdPageQuiryAccountBalanceDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SBFIGLTdPageQuiryAccountBalanceDTO dtoPara = (SBFIGLTdPageQuiryAccountBalanceDTO)dtoParameter;
		super.sqlProducer = new SBFIGLTdPageQuiryAccountBalanceModel((SfUserDTO)userAccount, dtoPara);
	}

	public int SavaAssetCategory(DTOSet ds){
		SynLogUtil log=new SynLogUtil();
        SQLModel sqlModel = null;
        int count=0;
        try {
        	for (int i =0 ; i < ds.getSize(); i++) {
        		SBFIGLTdPageQuiryAccountBalanceDTO dto = (SBFIGLTdPageQuiryAccountBalanceDTO) ds.getDTO(i);
        		initSQLProducer(sfUser,dto);
        		SBFIGLTdPageQuiryAccountBalanceModel modelProducer = (SBFIGLTdPageQuiryAccountBalanceModel) sqlProducer;
					if(!isEcouInformation(dto.getCodeCombinationId(), dto.getPeriodName())){
						sqlModel=modelProducer.getDataCreateModel();
					}else {
						sqlModel=modelProducer.getDataUpdateModel();
					}
					if(DBOperator.updateRecord(sqlModel, conn)){
						count++;
					}
        		}
		} catch (DataHandleException e) {
			e.printLog();
			SimpleCalendar s=new SimpleCalendar();
			SynLogDTO logDto=new SynLogDTO();
			logDto.setSynType(SrvType.SRV_TD_FA_PAGE_ACCOUNT_BALANCE);
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

    public boolean isEcouInformation(String codeCombinationId, String periodName){
		boolean returnFlag=false;
        SBFIGLTdPageQuiryAccountBalanceModel modelProducer = (SBFIGLTdPageQuiryAccountBalanceModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getEcouInforModel(codeCombinationId, periodName);
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

}