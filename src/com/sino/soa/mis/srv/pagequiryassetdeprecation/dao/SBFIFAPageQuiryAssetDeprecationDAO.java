package com.sino.soa.mis.srv.pagequiryassetdeprecation.dao;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.common.SrvType;
import com.sino.soa.mis.srv.pagequiryassetdeprecation.dto.SBFIFAPageQuiryAssetDeprecationDTO;
import com.sino.soa.mis.srv.pagequiryassetdeprecation.model.SBFIFAPageQuiryAssetDeprecationModel;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.SynLogDTO;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-14
 * Time: 16:05:00
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFAPageQuiryAssetDeprecationDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	public SBFIFAPageQuiryAssetDeprecationDAO(SfUserDTO userAccount, SBFIFAPageQuiryAssetDeprecationDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SBFIFAPageQuiryAssetDeprecationDTO dtoPara = (SBFIFAPageQuiryAssetDeprecationDTO)dtoParameter;
		super.sqlProducer = new SBFIFAPageQuiryAssetDeprecationModel((SfUserDTO)userAccount, dtoPara);
	}

	public int SavaAssetCategory(DTOSet ds){
		SynLogUtil log=new SynLogUtil();
        SQLModel sqlModel = null;
        int count=0;
        try {
        	for (int i =0 ; i < ds.getSize(); i++) {
        		SBFIFAPageQuiryAssetDeprecationDTO dto = (SBFIFAPageQuiryAssetDeprecationDTO) ds.getDTO(i);
        		initSQLProducer(sfUser,dto);
        		SBFIFAPageQuiryAssetDeprecationModel modelProducer = (SBFIFAPageQuiryAssetDeprecationModel) sqlProducer;
					if(!isEcouInformation(dto.getTagNumber(), dto.getPeriodName())){
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
			logDto.setSynType(SrvType.SRV_FA_PAGE_ASSET_DEPERACTION);
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

    public boolean isEcouInformation(String tagNumber, String periodName){
		boolean returnFlag=false;
        SBFIFAPageQuiryAssetDeprecationModel modelProducer = (SBFIFAPageQuiryAssetDeprecationModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getEcouInforModel(tagNumber, periodName);
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