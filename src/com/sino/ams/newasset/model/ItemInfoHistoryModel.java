package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.newasset.dto.ItemInfoHistoryDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: ItemInfoHistoryModel</p>
 * <p>Description:程序自动生成SQL构造器“ItemInfoHistoryModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class ItemInfoHistoryModel extends AMSSQLProducer {

	/**
	 * 功能：设备地点变动历史表(EAM) AMS_ITEM_INFO_HISTORY 数据库SQL构造层构造函数
	 * @param userAccount BaseUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter DTO 本次操作的数据
	 */
	public ItemInfoHistoryModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成设备地点变动历史表(EAM) AMS_ITEM_INFO_HISTORY数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		ItemInfoHistoryDTO dto = (ItemInfoHistoryDTO) dtoParameter;
		String sqlStr =
            "SELECT\n" +
            "       AIIH.BARCODE,\n" +
            "       AIIH.CREATION_DATE, \n" +
            "       SU.USERNAME LOG_USER,\n" +
            "       AIIH.ORDER_NO,\n" +
            "       AIIH.ITEM_CATEGORY_NAME,\n" +
            
            "       AIIH.ITEM_NAME,\n" +
            "       AIIH.ITEM_SPEC,\n" +
            "       AL.LOG_NET_ELE, \n" +
            "		AC.INVEST_CAT_NAME, \n" +
            "		AO.OPE_NAME, \n" +
            
            "       AIIH.NLE_ID, \n" +
            "		AN.LNE_NAME, \n" +
            "       AIIH.WORKORDER_OBJECT_NAME,\n" +
            "       AIIH.WORKORDER_OBJECT_CODE,\n" +
            "       AIIH.RESPONSIBILITY_DEPT_NAME DEPT_NAME,\n" +
            "       AIIH.RESPONSIBILITY_USER_NAME USER_NAME,\n" +
            
            "       AIIH.RESPONSIBILITY_USER_NUMBER EMPLOYEE_NUMBER,\n" +
            "       AIIH.MAINTAIN_USER,\n" +
            "       AIIH.OLD_CONTENT_CODE, \n" +
            "       AIIH.CONTENT_CODE, \n" +
            "       AIIH.CONTENT_NAME, \n" +
            "       AIIH.ITEM_STATUS_NAME,\n" +
            
            "       AIIH.ORDER_DTL_URL,\n" +
            "       AIIH.LNE_ID, \n" +
            "       AIIH.CEX_ID, \n" +
            "       AIIH.OPE_ID \n" +
            "FROM   AMS_ITEM_INFO_HISTORY AIIH, \n" +
            "		 SF_USER               SU, \n" +
            "		 ETS_OU_CITY_MAP    EOCM, \n" +
            "       AMS_LNE 			  AL, \n" +
            "       AMS_CEX  			  AC, \n" +	
            "       AMS_OPE  			  AO, \n" +	
            "       AMS_NLE  			  AN  \n" +	
            "WHERE  SUBSTRING(AIIH.BARCODE, 1, 4) = RIGHT(EOCM.BOOK_TYPE_CODE, 4) \n" +
            "       AND AIIH.CREATED_BY = SU.USER_ID \n" +
            "       AND (? = '' OR AIIH.ITEM_CATEGORY = ?) \n" +
            "       AND (? = '' OR AIIH.ITEM_NAME LIKE ?) \n" +
            "       AND (? = '' OR AIIH.RESPONSIBILITY_USER_NAME LIKE ?) \n" +
            "       AND (? = '' OR AIIH.ITEM_STATUS = ?) \n" +
            "       AND (? = '' OR AIIH.BARCODE >= ?) \n" +
            "       AND (? = '' OR AIIH.BARCODE <= ?) \n" +
            "       AND (? = '' OR AIIH.WORKORDER_OBJECT_CODE LIKE ? OR AIIH.WORKORDER_OBJECT_NAME LIKE ?) \n" +
            "       AND EOCM.BOOK_TYPE_CODE = ? \n" +
       		"		AND CONVERT(VARCHAR(32), AIIH.LNE_ID) *= AL.AMS_LNE_ID \n" +
       		"		AND CONVERT(VARCHAR(32), AIIH.CEX_ID) *= AC.AMS_CEX_ID \n" +
       		"		AND CONVERT(VARCHAR(32), AIIH.OPE_ID) *= AO.AMS_OPE_ID \n" +
       		"		AND CONVERT(VARCHAR(32), AIIH.NLE_ID) *= AN.AMS_LNE_ID \n" ;

        sqlArgs.add(dto.getItemCategory());
        sqlArgs.add(dto.getItemCategory());
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getUserName());
        sqlArgs.add(dto.getUserName());
        sqlArgs.add(dto.getItemStatus());
        sqlArgs.add(dto.getItemStatus());
        sqlArgs.add(dto.getStartBarcode());
        sqlArgs.add(dto.getStartBarcode());
        sqlArgs.add(dto.getEndBarcode());
        sqlArgs.add(dto.getEndBarcode());
        sqlArgs.add(dto.getWorkorderObjectCode());
        sqlArgs.add(dto.getWorkorderObjectCode());
        sqlArgs.add(dto.getWorkorderObjectCode());
        sqlArgs.add(userAccount.getBookTypeCode());
        
        if (!dto.getLogNetEle().equals("")) {
        	sqlStr += "		AND ( AL.LOG_NET_ELE LIKE ? ) \n" ;
        	sqlArgs.add(dto.getLogNetEle());
        }
        if (!dto.getInvestCatName().equals("")) {
        	sqlStr += "		AND ( AC.INVEST_CAT_NAME LIKE ? ) \n" +
        	sqlArgs.add(dto.getInvestCatName());
        }
        if (!dto.getOpeName().equals("")) {
        	sqlStr += "		AND ( AO.OPE_NAME LIKE ? ) \n" +
        	sqlArgs.add(dto.getOpeName());
        }
        if (!dto.getLneName().equals("")) {
        	sqlStr += "		AND ( AN.LNE_NAME LIKE ? ) \n" ;
        	sqlArgs.add(dto.getLneName());
        }

        if(dto.getDeptCode().length() > 0){
            sqlStr += " AND AIIH.RESPONSIBILITY_DEPT = ?";
            sqlArgs.add(dto.getDeptCode());
        } else {
            if(!userAccount.isComAssetsManager()){
                if(userAccount.isDptAssetsManager()){
                    DTOSet depts = userAccount.getPriviDeptCodes();
                    String deptStr = "'";
                    if( null!= depts){
	                    int deptCount = depts.getSize();
	                    
	                    for(int i = 0; i < deptCount; i++){
	                        AmsMisDeptDTO dept = (AmsMisDeptDTO)depts.getDTO(i);
	                        deptStr += dept.getDeptCode() + "'";
	                        if(i < deptCount -1){
	                            deptStr += ", '";
	                        }
	                    }
                    }
                    sqlStr += " AND AIIH.RESPONSIBILITY_DEPT IN (" + deptStr + ")";
                } else {
                    sqlStr += " AND AIIH.RESPONSIBILITY_USER = ?";
                    sqlArgs.add(userAccount.getEmployeeId());
                }
            }
        }
        if(dto.getChangeTimes()>1){
            sqlStr +=
                    " AND EXISTS\n" +
                    " (SELECT AIIH2.BARCODE"+
                    " FROM AMS_ITEM_INFO_HISTORY AIIH2\n" +
                    " WHERE AIIH.BARCODE = AIIH2.BARCODE GROUP BY AIIH2.BARCODE HAVING COUNT(1) < ?)";
            sqlArgs.add(dto.getChangeTimes());
        }
        sqlStr += " ORDER BY AIIH.CREATION_DATE DESC, AIIH.BARCODE \n";

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
