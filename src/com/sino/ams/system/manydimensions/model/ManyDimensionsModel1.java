package com.sino.ams.system.manydimensions.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-8-18
 * Time: 11:28:40
 * To change this template use File | Settings | File Templates.
 */
public class ManyDimensionsModel1 extends AMSSQLProducer {
    private String deptCodes = "";

    public ManyDimensionsModel1(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
        super(userAccount, dtoParameter);
        initDeptCodes();
    }

    private void initDeptCodes(){
        deptCodes = "(";
        DTOSet depts = userAccount.getPriviDeptCodes();
        if (depts != null && !depts.isEmpty()) {
            AmsMisDeptDTO dept = null;
            for (int i = 0; i < depts.getSize(); i++) {
                dept = (AmsMisDeptDTO) depts.getDTO(i);
                deptCodes += "'" + dept.getDeptCode() + "', ";
            }
        }
        deptCodes += "'')";
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        String sqlStr = "SELECT EII.BARCODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ACD.CONTENT_CODE,\n" +
                "       ACD.CONTENT_CODE OLD_CONTENT_CODE,\n" +
                "       ACD.CONTENT_NAME,\n" +
                "		EII.LNE_ID, \n" +
                "		EII.CEX_ID, \n"	+
                "		EII.OPE_ID, \n" +
                "		EII.NLE_ID, \n" + 
                "       AL.LOG_NET_ELE LNE_NAME,\n" +
                "       AC.INVEST_CAT_NAME CEX_NAME,\n" +
                "       AO.OPE_NAME OPE_NAME,\n" +
                "       AN.LNE_NAME NLE_NAME,\n" +
                "       EO.WORKORDER_OBJECT_CODE,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       PA.NAME, \n" +
                "		EII.RESPONSIBILITY_USER, \n" +	
                "		AME.USER_NAME \n" +	 
                "  FROM ETS_ITEM_INFO      EII,\n" +
                "       ETS_SYSTEM_ITEM    ESI,\n" +
                "       AMS_CONTENT_DIC    ACD,\n" +
                "       AMS_LNE            AL,\n" +
                "       AMS_CEX            AC,\n" +
                "       AMS_OPE            AO,\n" +
                "       AMS_NLE            AN,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       AMS_OBJECT_ADDRESS AOA,\n" +
                "		AMS_MIS_EMPLOYEE   AME, \n" +
                "       ETS_FLEX_VALUE_SET EFVS,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_PA_PROJECTS_ALL PA \n" +
                " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" ;
        		if( StrUtil.isEmpty( dto.getFaContentCode() ) ){
        			sqlStr += "   AND EII.CONTENT_CODE *= ACD.CONTENT_CODE\n" ;
        		}else{
        			sqlStr += "   AND EII.CONTENT_CODE = ACD.CONTENT_CODE\n" ;
        		}
        		sqlStr += 
                "   AND EII.LNE_ID *= AL.AMS_LNE_ID \n" +
                "   AND EII.CEX_ID *= AC.AMS_CEX_ID \n" +
                "   AND EII.OPE_ID *= AO.AMS_OPE_ID \n" +
                "   AND EII.NLE_ID *= AN.AMS_LNE_ID \n" +
                "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n" +
                "   AND EO.OBJECT_CATEGORY = EFV.CODE\n" +
                "   AND EFVS.CODE = 'OBJECT_CATEGORY'\n" +
                "   AND (EII.FINANCE_PROP = 'ASSETS' OR EII.FINANCE_PROP = 'TD_ASSETS' OR\n" +
                "       EII.FINANCE_PROP = 'TT_ASSETS')\n" +
                "   AND (EII.ATTRIBUTE1 IS NULL OR EII.ATTRIBUTE1 = '')\n" +
                "   AND AME.EMPLOYEE_ID = EII.RESPONSIBILITY_USER \n" +
                "   AND ("+SyBaseSQLUtil.isNull()+" OR ESI.ITEM_NAME LIKE ?)\n" +
                "   AND ("+SyBaseSQLUtil.isNull()+" OR EO.WORKORDER_OBJECT_NAME = ?)\n" +
                "   AND ("+SyBaseSQLUtil.isNull()+" OR ACD.CONTENT_CODE = ?)\n" +
                "   AND ("+SyBaseSQLUtil.isNull()+" OR EII.RESPONSIBILITY_DEPT = ?)\n" +
                "   AND ("+SyBaseSQLUtil.isNull()+" OR EII.RESPONSIBILITY_USER = ?)\n" +
                "   AND EII.PROJECTID = PA.PROJECT_ID \n" +
                "   AND ("+SyBaseSQLUtil.isNull()+" OR PA.NAME LIKE ?)\n";
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getWorkorderObjectName());
        sqlArgs.add(dto.getWorkorderObjectName());
        sqlArgs.add(dto.getFaContentCode());
        sqlArgs.add(dto.getFaContentCode());
        sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(dto.getResponsibilityUser());
        sqlArgs.add(dto.getResponsibilityUser());
        sqlArgs.add(dto.getPrjName());
        sqlArgs.add(dto.getPrjName());

        if (userAccount.isComAssetsManager()) {
            sqlStr += " AND EII.ORGANIZATION_ID = ?\n";
            sqlArgs.add(userAccount.getOrganizationId());
        } else if (userAccount.isDptAssetsManager()) {
            sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
        } else {
            sqlStr += " AND EII.RESPONSIBILITY_USER = ?\n";
            sqlArgs.add(userAccount.getEmployeeId());
        }

        if((!StrUtil.isEmpty(dto.getFromBarcode()))&&(!StrUtil.isEmpty(dto.getToBarcode()))){
			   sqlStr +=  " AND EII.BARCODE >= dbo.NVL(?, EII.BARCODE)\n"
						+ " AND EII.BARCODE <= dbo.NVL(?, EII.BARCODE)\n";
			sqlArgs.add(dto.getFromBarcode());
			sqlArgs.add(dto.getToBarcode());
		} else {
			   sqlStr += " AND EII.BARCODE LIKE dbo.NVL(?,EII.BARCODE)\n";
			sqlArgs.add(dto.getFromBarcode() + dto.getToBarcode());
		}

        if (dto.getManyDimensionsType().equals("ZCLB")) {
            if (dto.getManyDimensionsValue().equals("content")) {//表示CHECKBOX已选择
                sqlStr += " AND EII.CONTENT_CODE IS NOT NULL\n" +
                        "   AND ((SUBSTRING(EII.CONTENT_CODE,\n" +
                        "                   CHAR_LENGTH(EII.CONTENT_CODE) - 3,\n" +
                        "                   CHAR_LENGTH(EII.CONTENT_CODE)) = '0000' OR\n" +
                        "       SUBSTRING(EII.CONTENT_CODE,\n" +
                        "                   CHAR_LENGTH(EII.CONTENT_CODE) - 3,\n" +
                        "                   CHAR_LENGTH(EII.CONTENT_CODE)) = '9999') AND EXISTS\n" +
                        "        (SELECT 1\n" +
                        "           FROM AMS_CONTENT_CONTRAST ACC\n" +
                        "          WHERE ACC.CONTENT_CODE =\n" +
                        "                SUBSTRING(EII.CONTENT_CODE,\n" +
                        "                          CHARINDEX('.', EII.CONTENT_CODE) + 1,\n" +
                        "                          CHAR_LENGTH(EII.CONTENT_CODE) -\n" +
                        "                          CHARINDEX('.', EII.CONTENT_CODE) -\n" +
                        "                          CHARINDEX('.', REVERSE(EII.CONTENT_CODE)))))\n" +
                        "   AND NOT EXISTS\n" +
                        " 		(SELECT 1\n" +
                        //"          FROM AMS_ITEM_MANY_DIMENS_HISTORY AIMDH\n" +
                        "          FROM AMS_ITEM_INFO_HISTORY AIIH \n" +
                        "         WHERE AIIH.BARCODE = EII.BARCODE \n" +
                        "           AND (AIIH.CONTENT_CODE IS NOT NULL OR AIIH.CONTENT_CODE <> '')) \n";

            } else {
                sqlStr += " AND (SUBSTRING(EII.CONTENT_CODE, CHAR_LENGTH(EII.CONTENT_CODE)-3, CHAR_LENGTH(EII.CONTENT_CODE)) = '0000' OR\n" +
                        "       SUBSTRING(EII.CONTENT_CODE, CHAR_LENGTH(EII.CONTENT_CODE)-3, CHAR_LENGTH(EII.CONTENT_CODE)) = '9999')\n" +
                        "   AND EXISTS\n" +
                        " (SELECT 1\n" +
                        "          FROM AMS_CONTENT_CONTRAST ACC\n" +
                        "         WHERE ACC.CONTENT_CODE = SUBSTRING(EII.CONTENT_CODE,\n" +
                        "                 CHARINDEX('.', EII.CONTENT_CODE) + 1,\n" +
                        "                 CHAR_LENGTH(EII.CONTENT_CODE) - CHARINDEX('.', EII.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(EII.CONTENT_CODE))))\n" +
                        "   AND EXISTS (SELECT 1\n" +
                        "          FROM AMS_ITEM_INFO_HISTORY AIIH \n" +
                        "         WHERE AIIH.BARCODE = EII.BARCODE \n" +
                        "           AND (AIIH.CONTENT_CODE IS NOT NULL OR AIIH.CONTENT_CODE <> '')) \n";
            }
        } else if (dto.getManyDimensionsType().equals("WLYS")) {
            if (dto.getManyDimensionsValue().equals("lne")) {
                sqlStr += " AND (EII.LNE_ID IS NULL OR EII.LNE_ID = '')\n" +
                        "   AND EXISTS (SELECT 1\n" +
                        "          FROM AMS_LNE_CONTENT ALC\n" +
                        "         WHERE ALC.CORRESPONDENCE = 'DRIVER'\n" +
                        "           AND SUBSTRING(EII.CONTENT_CODE,\n" +
                        "                 1,\n" +
                        "                 CHAR_LENGTH(EII.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(EII.CONTENT_CODE))) =\n" +
                        "               SUBSTRING(ALC.CONTENT_CODE,\n" +
                        "                 1,\n" +
                        "                 CHAR_LENGTH(ALC.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(ALC.CONTENT_CODE))))";
            } else {
                sqlStr += " AND (EII.LNE_ID IS NOT NULL OR EII.LNE_ID <> '')\n" +
                        "   AND EXISTS (SELECT 1\n" +
                        "          FROM AMS_LNE_CONTENT ALC\n" +
                        "         WHERE ALC.CORRESPONDENCE = 'DRIVER'\n" +
                        "           AND SUBSTRING(EII.CONTENT_CODE,\n" +
                        "                 1,\n" +
                        "                 CHAR_LENGTH(EII.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(EII.CONTENT_CODE))) =\n" +
                        "               SUBSTRING(ALC.CONTENT_CODE,\n" +
                        "                 1,\n" +
                        "                 CHAR_LENGTH(ALC.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(ALC.CONTENT_CODE))))";
            }
        } else if (dto.getManyDimensionsType().equals("TZFL")) {
            if (dto.getManyDimensionsValue().equals("cex")) {
                sqlStr += " AND (EII.CEX_ID IS NULL OR EII.CEX_ID = '')\n" +
                        "   AND EXISTS (SELECT 1\n" +
                        "          FROM AMS_CEX_CONTENT ACC\n" +
                        "         WHERE ACC.CORRESPONDENCE = 'DRIVER'\n" +
                        "           AND SUBSTRING(EII.CONTENT_CODE,\n" +
                        "                 1,\n" +
                        "                 CHAR_LENGTH(EII.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(EII.CONTENT_CODE))) =\n" +
                        "               SUBSTRING(ACC.CONTENT_CODE,\n" +
                        "                 1,\n" +
                        "                 CHAR_LENGTH(ACC.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(ACC.CONTENT_CODE))))";
            } else {
                sqlStr += " AND (EII.CEX_ID IS NOT NULL OR EII.CEX_ID <> '')\n" +
                        "   AND EXISTS (SELECT 1\n" +
                        "          FROM AMS_CEX_CONTENT ACC\n" +
                        "         WHERE ACC.CORRESPONDENCE = 'DRIVER'\n" +
                        "           AND SUBSTRING(EII.CONTENT_CODE,\n" +
                        "                 1,\n" +
                        "                 CHAR_LENGTH(EII.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(EII.CONTENT_CODE))) =\n" +
                        "               SUBSTRING(ACC.CONTENT_CODE,\n" +
                        "                 1,\n" +
                        "                 CHAR_LENGTH(ACC.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(ACC.CONTENT_CODE))))";
            }
        } else if (dto.getManyDimensionsType().equals("YWPT")) {
            if (dto.getManyDimensionsValue().equals("ope")) {
                sqlStr += " AND (EII.OPE_ID IS NULL OR EII.OPE_ID = '')\n" +
                        "   AND EXISTS (SELECT 1\n" +
                        "          FROM AMS_OPE_CONTENT AOC\n" +
                        "         WHERE AOC.CORRESPONDENCE = 'DRIVER'\n" +
                        "           AND SUBSTRING(EII.CONTENT_CODE,\n" +
                        "                 1,\n" +
                        "                 CHAR_LENGTH(EII.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(EII.CONTENT_CODE))) =\n" +
                        "               SUBSTRING(AOC.CONTENT_CODE,\n" +
                        "                 1,\n" +
                        "                 CHAR_LENGTH(AOC.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(AOC.CONTENT_CODE))))";
            } else {
                sqlStr += " AND (EII.OPE_ID IS NOT NULL OR EII.OPE_ID <> '')\n" +
                        "   AND EXISTS (SELECT 1\n" +
                        "          FROM AMS_OPE_CONTENT AOC\n" +
                        "         WHERE AOC.CORRESPONDENCE = 'DRIVER'\n" +
                        "           AND SUBSTRING(EII.CONTENT_CODE,\n" +
                        "                 1,\n" +
                        "                 CHAR_LENGTH(EII.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(EII.CONTENT_CODE))) =\n" +
                        "               SUBSTRING(AOC.CONTENT_CODE,\n" +
                        "                 1,\n" +
                        "                 CHAR_LENGTH(AOC.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(AOC.CONTENT_CODE))))";
            }
        } else if (dto.getManyDimensionsType().equals("WLCC")) {
            if (dto.getManyDimensionsValue().equals("nle")) {
                sqlStr += " AND (EII.NLE_ID IS NULL OR EII.NLE_ID = '')\n" +
                        "   AND EXISTS (SELECT 1\n" +
                        "          FROM AMS_NLE_CONTENT ANC\n" +
                        "         WHERE ANC.CORRESPONDENCE = 'DRIVER'\n" +
                        "           AND SUBSTRING(EII.CONTENT_CODE,\n" +
                        "                 1,\n" +
                        "                 CHAR_LENGTH(EII.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(EII.CONTENT_CODE))) =\n" +
                        "               SUBSTRING(ANC.CONTENT_CODE,\n" +
                        "                 1,\n" +
                        "                 CHAR_LENGTH(ANC.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(ANC.CONTENT_CODE))))";
            } else {
                sqlStr += " AND (EII.NLE_ID IS NOT NULL OR EII.NLE_ID <> '')\n" +
                        "   AND EXISTS (SELECT 1\n" +
                        "          FROM AMS_NLE_CONTENT ANC\n" +
                        "         WHERE ANC.CORRESPONDENCE = 'DRIVER'\n" +
                        "           AND SUBSTRING(EII.CONTENT_CODE,\n" +
                        "                 1,\n" +
                        "                 CHAR_LENGTH(EII.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(EII.CONTENT_CODE))) =\n" +
                        "               SUBSTRING(ANC.CONTENT_CODE,\n" +
                        "                 1,\n" +
                        "                 CHAR_LENGTH(ANC.CONTENT_CODE) -\n" +
                        "                 CHARINDEX('.', REVERSE(ANC.CONTENT_CODE))))";
            }
        }

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel updateDataModel(String barcode,
                                    String contentCode,
                                    String contentName,
                                    String lneId,
                                    String cexId,
                                    String opeId,
                                    String nleId){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_ITEM_INFO\n" +
                "   SET OLD_CONTENT_CODE = CONTENT_CODE,\n" +
                "       CONTENT_CODE     = dbo.NVL(?, CONTENT_CODE),\n" +
                "       CONTENT_NAME     = dbo.NVL(?, CONTENT_NAME),\n" +
                "       LNE_ID           = dbo.NVL(?, LNE_ID),\n" +
                "       CEX_ID           = dbo.NVL(?, CEX_ID),\n" +
                "       OPE_ID           = dbo.NVL(?, OPE_ID),\n" +
                "       NLE_ID           = dbo.NVL(?, NLE_ID),\n" +
                "       LAST_UPDATE_DATE = GETDATE(),\n" +
                "       LAST_UPDATE_BY   = ?\n" +
                " WHERE BARCODE = ?";
        sqlArgs.add(contentCode);
        sqlArgs.add(contentName);
        sqlArgs.add(lneId);
        sqlArgs.add(cexId);
        sqlArgs.add(opeId);
        sqlArgs.add(nleId);
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(barcode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getItemInfoModel(String barcode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EII.CONTENT_CODE,\n" +
                "       EII.LNE_ID,\n" +
                "       EII.CEX_ID,\n" +
                "       EII.OPE_ID,\n" +
                "       EII.NLE_ID\n" +
                "  FROM ETS_ITEM_INFO EII\n" +
                " WHERE EII.BARCODE = ?";
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}