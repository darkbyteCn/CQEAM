package com.sino.ams.newasset.lose.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.ams.newasset.lose.dto.LoseHeaderDTO;
import com.sino.ams.newasset.lose.dto.LoseLineDTO;
import com.sino.ams.newasset.lose.model.LoseModel;
import com.sino.ams.system.user.dto.SfUserDTO;
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
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * 
 * @系统名称: 挂失
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Jul 14, 2011
 */
public class LoseDAO extends AMSProcedureBaseDAO{

	LoseHeaderDTO headerDTO = null;
	LoseModel loseModel = null;
	public LoseDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn); 
		this.initSQLProducer(userAccount, dtoParameter); 
	}

	@Override
	protected void initSQLProducer(BaseUserDTO arg0, DTO arg1) {
		// TODO Auto-generated method stub
		headerDTO = (LoseHeaderDTO) dtoParameter;
		loseModel = new LoseModel((SfUserDTO)userAccount, headerDTO );
		sqlProducer = loseModel ;
	}
	
	public DTOSet getLinesData( String headerId ) throws QueryException {
		if( !StrUtil.isEmpty( headerId )) {
	        SQLModel sqlModel = loseModel.getLinesModel( headerId );
	        SimpleQuery splq = new SimpleQuery(sqlModel, conn);
	        splq.setCalPattern(getCalPattern());
	        splq.setDTOClassName( LoseLineDTO.class.getName());
	        splq.executeQuery();
	        return splq.getDTOSet();
		}else{
			return new DTOSet();
		}
    }
	
	public void createHeader( LoseHeaderDTO header ) throws DataHandleException{
		SQLModel sqlModel = loseModel.createHeaderModel( header );
		DBOperator.updateRecord( sqlModel , conn );
	}
	
	public void createLine( LoseLineDTO line ) throws DataHandleException, CalendarException{
		if( !StrUtil.isEmpty( line.getBarcode() ) ){
			SQLModel sqlModel = loseModel.createLineModel( line );
			DBOperator.updateRecord( sqlModel , conn );
		}
	}
	//创建时更新头
	public void updateHeader( LoseHeaderDTO header ) throws DataHandleException{
		SQLModel sqlModel = loseModel.updateHeaderModel( header );
		DBOperator.updateRecord( sqlModel , conn );
	}
	//审批中更新头信息的状态
	public void updateHeaderStatus( LoseHeaderDTO header ) throws DataHandleException{
		SQLModel sqlModel = loseModel.updateHeaderStatusModel( header );
		DBOperator.updateRecord( sqlModel , conn );
	}
	
	public void deleteLine( String headerId ) throws DataHandleException{
		SQLModel sqlModel = loseModel.deleteLinesModel(headerId);
		DBOperator.updateRecord( sqlModel , conn );
	}
	
	public File exportFile() throws DataTransException{
		File file = null;
        try {
            DataTransfer transfer = null;
            
            SQLModel sqlModel = loseModel.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);            
            String fileName = "挂失单据表.xls" ;
            
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap(); 
             
            fieldMap.put("TRANS_NO", "挂失单号");
            fieldMap.put("TRANS_STATUS_DESC", "单据状态");
            fieldMap.put("FROM_COMPANY_NAME", "公司名称");
            fieldMap.put("CREATED", "申请人");
            fieldMap.put("CREATION_DATE", "创建日期"); 

            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle(fileName);
            custData.setReportPerson( this.userAccount.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
            //设置分页显示
            TransferFactory factory = new TransferFactory();
            transfer = factory.getTransfer(rule);
            transfer.transData();
            file = (File) transfer.getTransResult();
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataTransException(ex);
        }
        return file;
	}

}
