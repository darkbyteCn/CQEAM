package com.sino.nm.ams.instrument.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.nm.ams.instrument.dto.AmsInstrumentEamYbChkMaintainDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by MyEclipse.
 * User: yushibo
 * Date: 2009-3-6
 * Time: 9:52:18
 * To change this template use File | Settings | File Templates.
 */
public class AmsInstrumentEamYbChkHistoryModel extends AMSSQLProducer {

	public AmsInstrumentEamYbChkHistoryModel(SfUserDTO userAccount, AmsInstrumentEamYbChkMaintainDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try{
        	List sqlArgs = new ArrayList();
            AmsInstrumentEamYbChkMaintainDTO dto = (AmsInstrumentEamYbChkMaintainDTO)dtoParameter;
            String sqlStr = "SELECT"
    			        	+ " EII.BARCODE,"
    			        	+ " EII.VENDOR_BARCODE,"
    			        	+ " EII.ATTRIBUTE3,"
    			        	+ " EII.ORGANIZATION_ID,"
    			        	+ " ESI.ITEM_CATEGORY2,"
    			        	+ " ESI.ITEM_NAME,"
    			        	+ " ESI.ITEM_SPEC,"
    			        	+ " EYCM.CHECK_STATUS,"
    			        	+ " dbo.APP_GET_FLEX_VALUE(EYCM.CHECK_STATUS, 'YB_CHECK_RESULT') CHECK_STATUS_VALUE,"
    			        	+ " EYCM.CHECK_USER_ID,"
    			        	+ " dbo.APP_GET_USER_NAME(EYCM.CHECK_USER_ID) CHECK_USER_NAME,"
    			        	+ " EYCM.CHECK_DATE,"
    			        	+ " EYCM.REMARK,"
    			        	+ " EII.START_DATE"
            				+ " FROM"
            				+ " ETS_ITEM_INFO EII,"
    						+ " EAM_YB_CHK_MAINSTAIN EYCM,"
    						+ " ETS_SYSTEM_ITEM ESI"
            				+ " WHERE"
            				+ " EII.BARCODE = EYCM.BARCODE"
            				+ " AND EII.ITEM_CODE = ESI.ITEM_CODE"
            				+ " AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE > GETDATE())"
            				+ " AND (EII.ORGANIZATION_ID = ? OR -1 = ?)"
        					+ " AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)"
            				+ " AND (" + SyBaseSQLUtil.nullStringParam() + " OR ESI.ITEM_CATEGORY2 LIKE ?)"
            				+ " AND EYCM.CHECK_DATE between ? and ?";
            
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemCategory2());
            sqlArgs.add(dto.getItemCategory2());
            sqlArgs.add(dto.getItemCategory2());
            sqlArgs.add(dto.getMinTime());
            sqlArgs.add(dto.getMaxTime());
            
    		sqlModel.setSqlStr(sqlStr);
    		sqlModel.setArgs(sqlArgs);
        }catch (CalendarException ex) {
        	ex.printLog();
			throw new SQLModelException(ex);
		}
		
		return sqlModel;
	}
}
