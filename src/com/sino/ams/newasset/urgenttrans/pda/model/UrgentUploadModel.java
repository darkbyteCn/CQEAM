package com.sino.ams.newasset.urgenttrans.pda.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsReservedDTO;
import com.sino.ams.newasset.urgenttrans.dto.UrgentHeaderDTO;
import com.sino.ams.newasset.urgenttrans.dto.UrgentLineDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.framework.dto.BaseUserDTO;

public class UrgentUploadModel extends AMSSQLProducer {
	private SfUserDTO user = null;
	UrgentHeaderDTO headerDTO = null;
	
	public UrgentUploadModel(BaseUserDTO userAccount, DTO dtoParameter) { 
		super(userAccount, dtoParameter);
		this.headerDTO = (UrgentHeaderDTO) dtoParameter;
		this.user = (SfUserDTO) userAccount;
	}
	
	public SQLModel updateHeaderStatusModel( UrgentHeaderDTO header ) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuffer sb = new StringBuffer();

		sb.append( " UPDATE AMS_ASSETS_TRANS_HEADER SET  \n" );
    	sb.append( " TRANS_STATUS = ? ,  \n" );
    	sb.append( " LAST_UPDATE_DATE = GETDATE() ,  \n" );
    	sb.append( " LAST_UPDATE_BY = ?  \n" );
    	sb.append( " WHERE  \n" );
    	sb.append( " TRANS_ID = ?  \n" ); 
    	
        sqlArgs.add( header.getTransStatus() );
        sqlArgs.add( user.getUserId() );
        sqlArgs.add( header.getTransId() );
	       
		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	
	
	public SQLModel updateLineStatusModel( UrgentLineDTO line ) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuffer sb = new StringBuffer();

		sb.append( " UPDATE AMS_ASSETS_TRANS_LINE SET  \n" );
    	sb.append( " TRANS_STATUS = ? ,  \n" );
    	sb.append( " LAST_UPDATE_DATE = GETDATE() ,  \n" );
    	sb.append( " LAST_UPDATE_BY = ?  \n" );
    	sb.append( " WHERE  \n" );
    	sb.append( " TRANS_ID = ?  \n" ); 
    	sb.append( " AND LINE_ID = ?  \n" ); 
    	
//        sqlArgs.add( header.getTransStatus() );
//        sqlArgs.add( user.getUserId() );
    	
        sqlArgs.add( line.getTransId() );
        sqlArgs.add( line.getLineId() );
	       
		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	 
	/**
     * 删除资产保留
     * @param transId
     * @param barcode
     * @return
     */
    public SQLModel deleteLineModel( String transId ){ 
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        
        StringBuffer sb = new StringBuffer();
        sb.append( " DELETE FROM AMS_ASSETS_TRANS_LINE  \n" );
        sb.append( " WHERE   \n" );
        sb.append( " TRANS_ID = ?   \n" );  
        
        sqlArgs.add( transId );
        sqlModel.setSqlStr( sb.toString() );
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
    
	 /**
     * 创建行信息
     * @param line
     * @return
     * @throws CalendarException 
     */
    public SQLModel createLineModel(UrgentLineDTO line ){
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        StringBuffer sb = new StringBuffer();
         
        sb.append( " INSERT INTO AMS_ASSETS_TRANS_LINE  \n" );
        sb.append( " (TRANS_ID,  \n" );
        sb.append( " LINE_ID ,  \n" );
        sb.append( " BARCODE ,  \n" );
        sb.append( " RESPONSIBILITY_USER )   \n" );
        sb.append( " VALUES (  \n" );
        sb.append( " ?, NEWID() ,?,?  \n" );
        sb.append( " )  \n" );
        
        sqlArgs.add( line.getTransId() );
//        sqlArgs.add( line.getLineId() );
        sqlArgs.add( line.getBarcode() );
        sqlArgs.add( line.getResponsibilityUser() );  
        
        sqlModel.setSqlStr(sb.toString());
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 对资产进行保留
     * @param transId
     * @param barcode
     * @return
     */
    public SQLModel createReservedModel( String transId , String barcode ){ 
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        
        StringBuffer sb = new StringBuffer();
        sb.append( " INSERT INTO AMS_ASSETS_RESERVED  \n" );
        sb.append( " (TRANS_ID,  \n" );
        sb.append( " RESERVED_DATE ,  \n" );
        sb.append( " BARCODE  )   \n" );
        sb.append( " VALUES (  \n" );
        sb.append( " ?, GETDATE() ,?  \n" );
        sb.append( " )  \n" ); 
        
        sqlArgs.add( transId );
        sqlArgs.add( barcode );
        sqlModel.setSqlStr( sb.toString() );
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
    
    /**
     * 删除资产保留
     * @param transId
     * @param barcode
     * @return
     */
    public SQLModel deleteReservedModel( String transId ){ 
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        
        StringBuffer sb = new StringBuffer();
        sb.append( " DELETE FROM AMS_ASSETS_RESERVED  \n" );
        sb.append( " WHERE   \n" );
        sb.append( " TRANS_ID = ?   \n" );  
        
        sqlArgs.add( transId );
        sqlModel.setSqlStr( sb.toString() );
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
    
    //查找 PDA 控制
    public SQLModel findPDAArchiveControlModel(){ 
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        
        StringBuffer sb = new StringBuffer();
    
	    sb.append( " SELECT \n" );
	    sb.append( " EPC.KEYVALUE  \n" );
	    sb.append( " FROM \n" );
	    sb.append( " ETS_PDA_CONFIG EPC  \n" );
	    sb.append( " WHERE \n" );
	    sb.append( " EPC.APPNAME = 'control'  \n" );
	    sb.append( " AND EPC.KEYNAME = 'urgentArchive' \n" );
	     
	    sqlModel.setSqlStr( sb.toString() );
	    sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
    
    //更新资产地点信息
    public SQLModel updateAssetsAddressModel(String transId ){
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        
        StringBuffer sb = new StringBuffer();
        
        sb.append( " UPDATE ETS_ITEM_INFO \n" );
        sb.append( " SET    ADDRESS_ID = (SELECT AOA.ADDRESS_ID \n" );
        sb.append( "                          FROM   AMS_ASSETS_TRANS_HEADER AH, \n" );
        sb.append( "                                 AMS_OBJECT_ADDRESS      AOA \n" );
        sb.append( "                          WHERE  AH.TO_OBJECT_NO = AOA.OBJECT_NO \n" );
        sb.append( "                                 AND AH.TRANS_ID = ?) \n" );
        sb.append( " WHERE  EXISTS (SELECT NULL \n" );
        sb.append( "         FROM   AMS_ASSETS_TRANS_LINE AL \n" );
        sb.append( "         WHERE  AL.BARCODE = ETS_ITEM_INFO.BARCODE \n" );
        sb.append( "                AND AL.TRANS_ID = ?) \n" );
        
        sqlArgs.add( transId );
        sqlArgs.add( transId );
        sqlModel.setSqlStr( sb.toString() );
	    sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
    
}
