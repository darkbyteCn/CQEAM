package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AssetsTagNumberQueryDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-4-1
 * Time: 14:56:38
 * To change this template use File | Settings | File Templates.
 */
public class EquipmentInfoQueryModel extends AMSSQLProducer {
    private AssetsTagNumberQueryDTO dto = null;

    public EquipmentInfoQueryModel(SfUserDTO userAccount, AssetsTagNumberQueryDTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.dto = dtoParameter;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        if (dto.getType().equals("task")) {
            try {
                sqlStr ="SELECT AACL.BARCODE,\n" +
                        "       ESI.ITEM_NAME,\n" +
                        "       ESI.ITEM_SPEC,\n" +
                        "       AME.USER_NAME,\n" +
                        "       AMD.DEPT_NAME,\n" +
                        "       EO.WORKORDER_OBJECT_NAME,\n" +
                        "       dbo.APP_GET_FLEX_VALUE(EII.FINANCE_PROP, 'FINANCE_PROP') FINANCE_PROP_NAME,\n" +
                        "       AACH.TRANS_NO WORKORDER_BATCH,\n" +
                        "       AACB.TASK_DESC\n" +
                        "FROM   AMS_ASSETS_CHECK_BATCH  AACB,\n" +
                        "       AMS_ASSETS_CHECK_LINE   AACL,\n" +
                        "       AMS_ASSETS_CHECK_HEADER AACH,\n" +
                        "       ETS_ITEM_INFO           EII,\n" +
                        "       ETS_SYSTEM_ITEM         ESI,\n" +
                        "       AMS_MIS_EMPLOYEE        AME,\n" +
                        "       AMS_MIS_DEPT            AMD,\n" +
                        "       ETS_OBJECT              EO,\n" +
                        "       AMS_OBJECT_ADDRESS      AOA\n" +
                        "WHERE  AACB.BATCH_ID = AACH.BATCH_ID\n" +
                        "       AND AACH.HEADER_ID = AACL.HEADER_ID" +
                        "       AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                        "       AND EII.BARCODE = AACL.BARCODE\n" +
                        "       AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                        "       AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
                        
                        "       AND EII.ITEM_STATUS = 'NORMAL'\n" +
                        "       AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE = '')\n" +
                        "       AND NOT EXISTS \n" +
                        "               (SELECT 'A'\n" +
                        "                  FROM ETS_ITEM_INFO EII1\n" +
                        "                 WHERE EII1.FINANCE_PROP = 'OTHER'\n" +
                        "                   AND EII1.ATTRIBUTE1 IS NULL\n" +
                        "                   AND EII.BARCODE = EII1.BARCODE) " +
                        
                        //"       AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                        "       AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
                        "       AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                        "       AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                        "       AND AACH.ORGANIZATION_ID = ISNULL(?, AACH.ORGANIZATION_ID)\n" +
                        "       AND ( LTRIM(?) IS NULL OR AACB.BATCH_NO LIKE dbo.NVL(LTRIM(?), AACB.BATCH_NO))\n" +
                        "       AND ( LTRIM(?) IS NULL OR AACB.TASK_DESC LIKE dbo.NVL(LTRIM(?), AACB.TASK_DESC))\n" +
                        "       AND AACH.DISTRIBUTE_DATE >= dbo.NVL(LTRIM(?), AACH.DISTRIBUTE_DATE)\n" +
                        "       AND AACH.DISTRIBUTE_DATE <= dbo.NVL(LTRIM(?), AACH.DISTRIBUTE_DATE)\n" ;
                        
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getWorkorderBatch());
                sqlArgs.add(dto.getWorkorderBatch());
                sqlArgs.add(dto.getWorkorderBatchName());
                sqlArgs.add(dto.getWorkorderBatchName());
                sqlArgs.add(dto.getFromDate());
                sqlArgs.add(dto.getToDate());
            } catch (CalendarException e) {
                e.printLog();
                throw new SQLModelException(e);
            }
        } else if(dto.getType().equals("dept")) {
            sqlStr = "SELECT EII.BARCODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       AME.USER_NAME,\n" +
                    "       AMD.DEPT_NAME,\n" +
                    "       EO.WORKORDER_OBJECT_NAME,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(EII.FINANCE_PROP, 'FINANCE_PROP') FINANCE_PROP_NAME \n" +
                    
                    //"       dbo.APP_GET_SYN_STUAS(EII.BARCODE, EII.ORGANIZATION_ID) SYN_STATUS, \n" +
                    //"       dbo.APP_GET_TRANS_STATUS(EII.BARCODE) TRANS_NAME \n" +
                    
                    "  FROM ETS_ITEM_INFO      EII,\n" +
                    "       ETS_SYSTEM_ITEM    ESI,\n" +
                    "       AMS_MIS_EMPLOYEE   AME,\n" +
                    "       AMS_MIS_DEPT       AMD,\n" +
                    "       ETS_OBJECT         EO,\n" +
                    "       AMS_OBJECT_ADDRESS AOA\n" +
                    " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                    
                    "   AND EII.ITEM_STATUS = 'NORMAL'\n" +
                    "   AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE = '')\n" +
                    "   AND NOT EXISTS\n" +
                    "           (SELECT 'A'\n" +
                    "              FROM ETS_ITEM_INFO EII1\n" +
                    "             WHERE EII1.FINANCE_PROP = 'OTHER'\n" +
                    "               AND EII1.ATTRIBUTE1 IS NULL\n" +
                    "               AND EII.BARCODE = EII1.BARCODE)\n";

            if(dto.getUserName().equals("")){
            sqlStr += "   AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n";
            } else {
                sqlStr += "   AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n";
            }
if(dto.getDeptCode().equals("")){
            sqlStr += "   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n";
} else {
    sqlStr += "   AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n";
}
            sqlStr +=

                    "   AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                    "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    //"   AND EII.ORGANIZATION_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EII.ORGANIZATION_ID)))\n" +
                    "   AND (? IS NULL OR ? = -1 OR EII.ORGANIZATION_ID = ?)\n" +
                    //"   AND ( LTRIM(?) IS NULL OR EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(LTRIM(?), EO.WORKORDER_OBJECT_NAME))\n" +
                    //"   AND ( LTRIM(?) IS NULL OR AME.USER_NAME LIKE dbo.NVL(LTRIM(?), AME.USER_NAME))\n" +
                    //"   AND ( LTRIM(?) IS NULL OR AMD.DEPT_NAME LIKE dbo.NVL(LTRIM(?), AMD.DEPT_NAME))\n" +
                    
/*                    "   AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)\n" +
                    "   AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n" +
                    "   AND (? IS NULL OR ESI.ITEM_SPEC LIKE ? )\n" +
                    "   AND EII.FINANCE_PROP = dbo.NVL(?,EII.FINANCE_PROP)\n" +*/
                    
		            "   AND (EXISTS (SELECT 'A'\n" +
		            "                  FROM AMS_ASSETS_PRIVI AAP\n" +
		            "                 WHERE AAP.DEPT_CODE = EII.RESPONSIBILITY_DEPT\n" +
		            "                   AND AAP.USER_ID = ?) OR EXISTS\n" +
		            "        		(SELECT 'A'\n" +
		            "           	   FROM AMS_MIS_EMPLOYEE AME, SF_USER SU\n" +
		            "          		  WHERE AME.EMPLOYEE_NUMBER = SU.EMPLOYEE_NUMBER\n" +
		            "                   AND AME.DEPT_CODE = EII.RESPONSIBILITY_DEPT\n" +
		            "                   AND SU.USER_ID = ?))\n";
            
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(userAccount.getUserId());
            sqlArgs.add(userAccount.getUserId());
            if (!dto.getWorkorderObjectName().equals("")) {
            	sqlStr += "   AND EO.WORKORDER_OBJECT_NAME LIKE ? \n" ;
            	sqlArgs.add('%' + dto.getWorkorderObjectName() + '%');
            }
            if (!dto.getUserName().equals("")) {
            	sqlStr += "   AND AME.USER_NAME LIKE ? \n" ;
            	sqlArgs.add('%' + dto.getUserName() + '%');
            }
            if (!dto.getDeptName().equals("")) {
            	sqlStr += "   AND AMD.DEPT_NAME LIKE ? \n" ;
            	sqlArgs.add('%' + dto.getDeptName() + '%');
            }
            
            //sqlArgs.add(dto.getWorkorderObjectName());
            //sqlArgs.add(dto.getWorkorderObjectName());
            //sqlArgs.add('%' + dto.getUserName() + '%');
            //sqlArgs.add('%' + dto.getUserName() + '%');
            //sqlArgs.add('%' + dto.getDeptName() + '%');
            //sqlArgs.add('%' + dto.getDeptName() + '%');
            
/*            sqlArgs.add(dto.getBarcode());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getFinanceProp());*/
            
        } else {
            sqlStr = "SELECT /*+FIRST_ROWS*/\n" +
		            " AIAV.BARCODE,\n" +
		            " AIAV.ITEM_NAME,\n" +
		            " AIAV.ITEM_SPEC,\n" +
		            " AIAV.US_NAME USER_NAME,\n" +
		            " AIAV.MAINTAIN_USER,\n" +
		            " AIAV.DP_NAME DEPT_NAME,\n" +
		            " AIAV.WORKORDER_OBJECT_NAME,\n" +
		            " AIAV.FINANCE_PROP_NAME,\n" +
		            " AIAV.SYN_STUAS SYN_STATUS,\n" +
		            " AIAV.TRANS_STATUS TRANS_NAME,\n" +
		            " AIAV.TAG_NUMBER,\n" +
		            " AIAV.ASSETS_DESCRIPTION,\n" +
		            " AIAV.MODEL_NUMBER,\n" +
		            " AIAV.ASSIGNED_TO_NAME,\n" +
		            " AIAV.ASSETS_LOCATION,\n" +
		            " AIAV.DATE_PLACED_IN_SERVICE,\n" +
		            " AIAV.COST\n" +
		            "  FROM EAM_ITEM_ALL_V AIAV\n" +
		            " WHERE ( ? IS NULL OR ? = -1 OR AIAV.ORGANIZATION_ID = ? )\n" +
		            "   AND ( LTRIM(?) IS NULL OR AIAV.WORKORDER_OBJECT_NAME LIKE LTRIM(?) )\n" +
		            "   AND ( LTRIM(?) IS NULL OR AIAV.US_NAME LIKE LTRIM(?) )\n" +
		            "   AND ( LTRIM(?) IS NULL OR AIAV.DP_NAME LIKE LTRIM(?) )\n" +
		            "   AND (( LTRIM(?) IS NULL OR AIAV.BARCODE LIKE LTRIM(?) ) OR ( LTRIM(?) IS NULL OR AIAV.TAG_NUMBER LIKE LTRIM(?) ) )\n" +
		            "   AND ( LTRIM(?) IS NULL OR AIAV.ITEM_NAME LIKE LTRIM(?) )\n" +
		            "   AND ( LTRIM(?) IS NULL OR AIAV.ITEM_SPEC LIKE LTRIM(?) )\n" +
		            "   AND ( LTRIM(?) IS NULL OR AIAV.FINANCE_PROP = LTRIM(?) )\n" +
		            "   AND ( LTRIM(?) IS NULL OR AIAV.MAINTAIN_USER LIKE LTRIM(?) )";
		       sqlArgs.add(userAccount.getOrganizationId());
		       sqlArgs.add(userAccount.getOrganizationId());
		       sqlArgs.add(userAccount.getOrganizationId());
		       sqlArgs.add(dto.getWorkorderObjectName());
		       sqlArgs.add(dto.getWorkorderObjectName());
		       sqlArgs.add(dto.getUserName());
		       sqlArgs.add(dto.getUserName());
		       sqlArgs.add(dto.getDeptName());
		       sqlArgs.add(dto.getDeptName());
		       sqlArgs.add(dto.getBarcode());
		       sqlArgs.add(dto.getBarcode());
		       sqlArgs.add(dto.getBarcode());
		       sqlArgs.add(dto.getBarcode());
		       sqlArgs.add(dto.getItemName());
		       sqlArgs.add(dto.getItemName());
		       sqlArgs.add(dto.getItemSpec());
		       sqlArgs.add(dto.getItemSpec());
		       sqlArgs.add(dto.getFinanceProp());
		       sqlArgs.add(dto.getFinanceProp());
		       sqlArgs.add(dto.getMaintainUser());
		       sqlArgs.add(dto.getMaintainUser());
		       
		       if (dto.getAssetsType().equals("Y")) {
		    	   sqlStr += "AND AIAV.ITEM_CATEGORY ='OTHERS' \n";
		       } else if (dto.getAssetsType().equals("N")){
		    	   sqlStr += "AND (AIAV.ITEM_CATEGORY !='OTHERS' OR AIAV.ITEM_CATEGORY IS NULL)\n ";
		       }
        }

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}