package com.sino.ams.workorder.model;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

import java.util.ArrayList;
import java.util.List;


public class TrunListMisQueryModel extends BaseSQLProducer {
    EtsWorkorderDTO workorderDTO = null;
    SfUserDTO sfUser = null;

    public TrunListMisQueryModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.workorderDTO = (EtsWorkorderDTO) dtoParameter;
        sfUser = (SfUserDTO) userAccount;
    }

    /**
     * Function:		获取转资清单查询的SQLModel
     * @author 			李轶
     * @return			SQLModel
     * @date			2009-6-4
     * @time			9:31:55
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();

		StringBuilder sb = new StringBuilder();
        if(sfUser.getIsTd().equals("Y")||sfUser.getIsTt().equals("Y")){
            sb.append( " SELECT \n" +
                            "\n" +
                            " EFAT.LOCATION_CODE ASSETS_LOCATION_CODE, \n" +
                            "         EFAT.ASSET_LOCATION ASSETS_LOCATION, \n" +
                            " EFAT.PROJRCT_NUMBER SEGMENT1, EPPA.NAME,\n" +
                            "       \n" +
                            "        EOCM.COMPANY ORG_NAME, \n" +
                            "        EFAT.TAG_NUMBER BARCODE, \n" +
                            "        EFAT.ASSET_DESCRIPTION ITEM_NAME, \n" +
                            "        EFAT.MODEL_NUMBER ITEM_SPEC, \n" +
                            "        STR( EFAT.ASSET_UNITS , 20 , 0 ) ITEM_QTY , \n" +
                            "          dbo.APP_GET_FLEX_VALUE( EFAT.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_DESC, \n" +
                            "        AME.HR_DEPT_NAME DEPT_NAME, \n" +
                            "       AME.USER_NAME ,   \n" +
                            "        AME.HR_DEPT_NAME  MAINTAIN_DEPT, \n" +
                            "        AME.USER_NAME  MAINTAIN_USER, \n" +
                            "         EFAT.CREATION_DATE , \n" +
                            "        EFAT.START_DATE, \n" +
                            "        dbo.GET_PRINT_HISTORY_COUNT( EOCM.ORGANIZATION_ID , EFAT.TAG_NUMBER ) PRINT_NUM , \n" +
                            "        EFAT.CREATION_DATE LAST_UPDATE_DATE, \n" +
                            "        EFAT.ASSET_CATEGORY CONTENT_CODE, \n" +
                            "        EFAT.ASSET_CATEGORY_DESC CONTENT_NAME," +
                    "              EFAT.ATTRIBUTE4,\n" +
                    "           EFAT.ATTRIBUTE5,\n" +
                    "           EFAT.ATTRIBUTE6,\n" +
                    "           EFAT.ATTRIBUTE7,\n" +
                            "     EPPA.PROJECT_TYPE\n" +
                            "\n" +
                            "     FROM ETS_TD_FA_CUST_DETAIL_IMP EFAT,\n" +
                            "           ETS_OU_CITY_MAP        EOCM,\n" +
                            "          ETS_PA_PROJECTS_ALL  EPPA,\n" +
                            "           AMS_MIS_EMPLOYEE       AME\n" +
                            "    WHERE EFAT.BOOK_TYPE_CODE = EOCM.BOOK_TYPE_CODE   \n" +
                            "     AND EFAT.PROJRCT_NUMBER *=EPPA.SEGMENT1\n" +
                            "    \n" +
                            "      AND  EFAT.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER\n" +
                            "      AND EOCM.COMPANY_CODE *= AME.COMPANY_CODE");

        }else {
            sb.append( " SELECT \n" +
                            "\n" +
                            " EFAT.LOCATION_CODE ASSETS_LOCATION_CODE, \n" +
                            "         EFAT.ASSET_LOCATION ASSETS_LOCATION, \n" +
                            " EFAT.PROJRCT_NUMBER SEGMENT1, EPPA.NAME,\n" +
                            "       \n" +
                            "        EOCM.COMPANY ORG_NAME, \n" +
                            "        EFAT.TAG_NUMBER BARCODE, \n" +
                            "        EFAT.ASSET_DESCRIPTION ITEM_NAME, \n" +
                            "        EFAT.MODEL_NUMBER ITEM_SPEC, \n" +
                            "        STR( EFAT.ASSET_UNITS , 20 , 0 ) ITEM_QTY , \n" +
                            "          dbo.APP_GET_FLEX_VALUE( EFAT.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_DESC, \n" +
                            "        AME.HR_DEPT_NAME DEPT_NAME, \n" +
                            "       AME.USER_NAME ,   \n" +
                            "        AME.HR_DEPT_NAME  MAINTAIN_DEPT, \n" +
                            "        AME.USER_NAME  MAINTAIN_USER, \n" +
                            "         EFAT.CREATION_DATE , \n" +
                            "        EFAT.START_DATE, \n" +
                            "        dbo.GET_PRINT_HISTORY_COUNT( EOCM.ORGANIZATION_ID , EFAT.TAG_NUMBER ) PRINT_NUM , \n" +
                            "        EFAT.CREATION_DATE LAST_UPDATE_DATE, \n" +
                            "        EFAT.ASSET_CATEGORY CONTENT_CODE, \n" +
                            "        EFAT.ASSET_CATEGORY_DESC CONTENT_NAME," +
                    "              EFAT.ATTRIBUTE4,\n" +
                    "           EFAT.ATTRIBUTE5,\n" +
                    "           EFAT.ATTRIBUTE6,\n" +
                    "           EFAT.ATTRIBUTE7,\n" +
                            "     EPPA.PROJECT_TYPE\n" +
                            "\n" +
                            "     FROM ETS_FA_CUST_DETAIL_IMP EFAT,\n" +
                            "           ETS_OU_CITY_MAP        EOCM,\n" +
                            "          ETS_PA_PROJECTS_ALL  EPPA,\n" +
                            "           AMS_MIS_EMPLOYEE       AME\n" +
                            "    WHERE EFAT.BOOK_TYPE_CODE = EOCM.BOOK_TYPE_CODE   \n" +
                            "     AND EFAT.PROJRCT_NUMBER *=EPPA.SEGMENT1\n" +
                            "    \n" +
                            "      AND  EFAT.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER\n" +
                            "      AND EOCM.COMPANY_CODE *= AME.COMPANY_CODE");

        }

        sb.append( "   AND EFAT.CREATE_USER_ID=? \n");
         sqlArgs.add(sfUser.getUserId());
        if( !StrUtil.isEmpty( workorderDTO.getDeptCode() ) ){
        	sb.append( "   AND AME.HR_DEPT_NAME LIKE ? \n");
        }

        if( !StrUtil.isEmpty( workorderDTO.getResponsibilityUser() ) ){
        	sb.append( "   AND AME.USER_NAME LIKE ? \n");
        }

        if( !StrUtil.isEmpty( workorderDTO.getOrganizationId() ) ){
        	sb.append( "   AND EOCM.ORGANIZATION_ID = ? \n");
        }

        if( !StrUtil.isEmpty( workorderDTO.getPrjId() ) ){
        	sb.append( "   AND EFAT.PROJECT_ID = ? \n");
        }

        if( !StrUtil.isEmpty( workorderDTO.getWorkorderObjectCode() ) ){
        	sb.append( "   AND EFAT.LOCATION_CODE LIKE ? \n");
        }

//                if( !StrUtil.isEmpty( workorderDTO.getDeptCode() ) ){
//                	sb.append( "   AND  AMD.DEPT_CODE = ? \n");
//                }
//                sb.append( "   AND         ( " + SyBaseSQLUtil.isNull() + "  OR AMP.USER_NAME LIKE ?) \n");
//                sb.append( "   AND         (  ?=NULL OR EII.ORGANIZATION_ID = ?) \n");
//                sb.append( "   AND         (  ?=NULL  OR EPPA.PROJECT_ID = ?) \n");
//                sb.append( "   AND         ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?) \n");
//                sb.append( "   AND         ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE = ?) \n");

        if( !StrUtil.isEmpty( workorderDTO.getDeptCode() ) ){
        	sqlArgs.add(workorderDTO.getDeptName());
        }

        if( !StrUtil.isEmpty( workorderDTO.getResponsibilityUser() ) ){
        	sqlArgs.add(workorderDTO.getResponsibilityUser());
        }

        if( !StrUtil.isEmpty( workorderDTO.getOrganizationId() ) && workorderDTO.getOrganizationId() > 0 ){
        	sqlArgs.add(workorderDTO.getOrganizationId());
        }

        if( !StrUtil.isEmpty( workorderDTO.getPrjId() ) ){
        	sqlArgs.add(StrUtil.strToInt(workorderDTO.getPrjId()));
        }

        if( !StrUtil.isEmpty( workorderDTO.getWorkorderObjectCode() ) ){
        	sqlArgs.add(workorderDTO.getWorkorderObjectCode());
        }

//		sqlArgs.add(workorderDTO.getResponsibilityUser());
//		sqlArgs.add(workorderDTO.getResponsibilityUser());
//
//		sqlArgs.add(workorderDTO.getOrganizationId());
//		sqlArgs.add(workorderDTO.getOrganizationId());
//
//		sqlArgs.add(workorderDTO.getPrjId());
//		sqlArgs.add(workorderDTO.getPrjId());
//
//		sqlArgs.add(workorderDTO.getWorkorderObjectCode());
//		sqlArgs.add(workorderDTO.getWorkorderObjectCode());

//		sqlArgs.add(workorderDTO.getDeptCode());
//		sqlArgs.add(workorderDTO.getDeptCode());



        sb.append( " ORDER BY  EFAT.PROJRCT_NUMBER ");


		sqlModel.setSqlStr( sb.toString() );
		sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
}
