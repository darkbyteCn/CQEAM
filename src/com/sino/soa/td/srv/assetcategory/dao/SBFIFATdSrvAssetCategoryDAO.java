package com.sino.soa.td.srv.assetcategory.dao;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.common.SrvType;
import com.sino.soa.td.srv.assetcategory.dto.SBFIFATdSrvAssetCategoryDTO;
import com.sino.soa.td.srv.assetcategory.model.SBFIFATdSrvAssetCategoryModel;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.dto.SynLogDTO;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-7
 * Time: 12:50:57
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFATdSrvAssetCategoryDAO extends BaseDAO {

	private SfUserDTO sfUser = null;
    private int errorCount = 0;

	public SBFIFATdSrvAssetCategoryDAO(SfUserDTO userAccount, SBFIFATdSrvAssetCategoryDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SBFIFATdSrvAssetCategoryDTO dtoPara = (SBFIFATdSrvAssetCategoryDTO)dtoParameter;
		super.sqlProducer = new SBFIFATdSrvAssetCategoryModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：对资产类型信息的更新是否成功。
	 */
	public int SavaAssetCategory(DTOSet ds){
		SynLogUtil log=new SynLogUtil();
        SQLModel sqlModel = null;
        int count=0;
        try {
      		RowSet rs=getOuInfom();
      		String tmp="";
        	for (int i =0 ; i < ds.getSize(); i++) {
                boolean isUpdate= false;
        		SBFIFATdSrvAssetCategoryDTO dto = (SBFIFATdSrvAssetCategoryDTO) ds.getDTO(i);
        		initSQLProducer(sfUser,dto);
        		SBFIFATdSrvAssetCategoryModel modelProducer = (SBFIFATdSrvAssetCategoryModel) sqlProducer;
                tmp=dto.getSegment1()+"."+dto.getSegment2()+"."+dto.getSegment3();
                if(!isEcouInformation(tmp,rs)){
                    //王志鹏修改 2012-02-13  对于新增类型进行过滤(过滤掉失效的类型)
                    if(dto.getEnabledFlag().equals("Y")){
                        sqlModel=modelProducer.getDataCreateModel();
                        isUpdate = DBOperator.updateRecord(sqlModel, conn);
                    }
                }else {
                    sqlModel=modelProducer.getDataUpdateModel();
                    isUpdate = DBOperator.updateRecord(sqlModel, conn);
                }
                if(isUpdate){
                    count++;
                }
            }
		} catch (DataHandleException e) {
            errorCount++;
			e.printLog();
			SimpleCalendar s=new SimpleCalendar();
			SynLogDTO logDto=new SynLogDTO();
			logDto.setSynType(SrvType.SRV_FA_CATEGORY);
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
	 * 功能：传入的ou参数是否存在数据库ou表中。
	 */
	public boolean isEcouInformation(String ou,RowSet rs){
		boolean returnFlag=false;
		if(rs==null){
			return returnFlag;
		}
        Row row=null;
        for(int i = 0; i < rs.getSize(); i++){
        	row = rs.getRow(i);
        	try {
				if(ou.equals(row.getValue("CONTENT_CODE")))
					returnFlag=true;
			} catch (ContainerException e) {
				e.printLog();
			}
        }
        return returnFlag;

	}

	public RowSet getOuInfom(){
		SBFIFATdSrvAssetCategoryModel modelProducer = (SBFIFATdSrvAssetCategoryModel) sqlProducer;
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