package com.sino.soa.td.srv.pagequiryassetdeprecation.dao;

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
import com.sino.soa.td.srv.pagequiryassetdeprecation.dto.SBFIFATdPageQuiryAssetDeprecationDTO;
import com.sino.soa.td.srv.pagequiryassetdeprecation.model.SBFIFATdPageQuiryAssetDeprecationModel;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.SynLogDTO;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-15
 * Time: 15:54:25
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFATdPageQuiryAssetDeprecationDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	public SBFIFATdPageQuiryAssetDeprecationDAO(SfUserDTO userAccount, SBFIFATdPageQuiryAssetDeprecationDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SBFIFATdPageQuiryAssetDeprecationDTO dtoPara = (SBFIFATdPageQuiryAssetDeprecationDTO)dtoParameter;
		super.sqlProducer = new SBFIFATdPageQuiryAssetDeprecationModel((SfUserDTO)userAccount, dtoPara);
	}

	public int SavaAssetCategory(DTOSet ds){
		SynLogUtil log=new SynLogUtil();
        SQLModel sqlModel = null;
        int count=0;
        try {
        	for (int i =0 ; i < ds.getSize(); i++) {
        		SBFIFATdPageQuiryAssetDeprecationDTO dto = (SBFIFATdPageQuiryAssetDeprecationDTO) ds.getDTO(i);
        		initSQLProducer(sfUser,dto);
        		SBFIFATdPageQuiryAssetDeprecationModel modelProducer = (SBFIFATdPageQuiryAssetDeprecationModel) sqlProducer;
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
			logDto.setSynType(SrvType.SRV_TD_FA_PAGE_ASSET_DEPERACTION);
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
        SBFIFATdPageQuiryAssetDeprecationModel modelProducer = (SBFIFATdPageQuiryAssetDeprecationModel) sqlProducer;
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