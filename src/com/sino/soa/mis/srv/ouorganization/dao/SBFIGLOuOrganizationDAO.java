package com.sino.soa.mis.srv.ouorganization.dao;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.common.SrvType;
import com.sino.soa.mis.srv.ouorganization.dto.SBFIGLOuOrganizationDTO;
import com.sino.soa.mis.srv.ouorganization.model.SBFIGLOuOrganizationModel;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.SynLogDTO;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-8
 * Time: 16:05:51
 * To change this template use File | Settings | File Templates.
 */
public class SBFIGLOuOrganizationDAO extends BaseDAO {

	private SfUserDTO sfUser = null;
    private int errorCount = 0;

	public SBFIGLOuOrganizationDAO(SfUserDTO userAccount, SBFIGLOuOrganizationDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SBFIGLOuOrganizationDTO dtoPara = (SBFIGLOuOrganizationDTO)dtoParameter;
		super.sqlProducer = new SBFIGLOuOrganizationModel((SfUserDTO)userAccount, dtoPara);
	}

	public int isSavaOuInfor(DTOSet ds){
		SynLogUtil log=new SynLogUtil();
        SQLModel sqlModel = null;
        boolean autoCommit =true;
        int count=0;
        try {
      		RowSet rs=getOuInfom();
        	for (int i =0 ; i < ds.getSize(); i++) {
        		SBFIGLOuOrganizationDTO dto = (SBFIGLOuOrganizationDTO) ds.getDTO(i);
        		initSQLProducer(sfUser,dto);
        		SBFIGLOuOrganizationModel modelProducer = (SBFIGLOuOrganizationModel) sqlProducer;
					if(!isEcouInformation(dto.getOrgId(),rs)){
						sqlModel=modelProducer.getDataCreateModel();
					}
					else {
						sqlModel=modelProducer.getDataUpdateModel();
					}
					if(DBOperator.updateRecord(sqlModel, conn)){
						count++;
					}
        	}
		}
        catch (DataHandleException e) {
            errorCount++;
			e.printLog();
			SimpleCalendar s=new SimpleCalendar();
			SynLogDTO logDto=new SynLogDTO();
			logDto.setSynType(SrvType.SRV_OU);
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
		}
        return count;
	}

	public boolean isEcouInformation(int ou,RowSet rs){
		boolean returnFlag=false;
		if(rs==null){
			return returnFlag;
		}
        Row row=null;
        for(int i = 0; i < rs.getSize(); i++){
        	row = rs.getRow(i);
        	try {
				if(String.valueOf(ou).equals(row.getStrValue("ORGANIZATION_ID")))
					returnFlag=true;
			} catch (ContainerException e) {
				e.printLog();
			}
        }
        return returnFlag;

	}
	public RowSet getOuInfom(){
		 SBFIGLOuOrganizationModel modelProducer = (SBFIGLOuOrganizationModel) sqlProducer;
         SQLModel sqlModel = modelProducer.getEcouInforModel();
         SimpleQuery simp = new SimpleQuery(sqlModel, conn);
         RowSet rs=null;
         try {
			simp.executeQuery();
			if(simp.hasResult()){
				rs = simp.getSearchResult();
			}

		} catch (QueryException e) {
			 e.printLog();
		}
		return rs;
	}

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}


}