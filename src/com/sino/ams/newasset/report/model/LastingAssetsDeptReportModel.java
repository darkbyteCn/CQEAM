package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-6-10
 * Time: 17:31:42
 * To change this template use File | Settings | File Templates.
 */
public class LastingAssetsDeptReportModel extends AMSSQLProducer {

    public LastingAssetsDeptReportModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EII.BARCODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       AMD.DEPT_NAME RES_DEPT_NAME,\n" +
                "       AME.USER_NAME RES_USER_NAME,\n" +
                "       AME.EMPLOYEE_NUMBER,\n" +
                "       AMD2.DEPT_NAME SPECIAL_DEPT_NAME,\n" +
                "       AMD3.DEPT_NAME MAINTAIN_DEPT_NAME,\n" +
                "       EII.MAINTAIN_USER,\n" +
                "       EO.WORKORDER_OBJECT_CODE,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       EII.CONTENT_CODE,\n" +
                "       EII.CONTENT_NAME\n" +
                "FROM   ETS_ITEM_INFO      EII,\n" +
                "       ETS_SYSTEM_ITEM    ESI,\n" +
                "       AMS_MIS_DEPT       AMD,\n" +
                "       AMS_MIS_EMPLOYEE   AME,\n" +
                "       AMS_MIS_DEPT       AMD2,\n" +
                "       AMS_MIS_DEPT       AMD3,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       AMS_OBJECT_ADDRESS AOA\n" +
                "WHERE  EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "AND    EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                "AND    EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
                "AND    EII.SPECIALITY_DEPT *= AMD2.DEPT_CODE\n" +
                "AND    EII.MAINTAIN_DEPT *= AMD3.DEPT_CODE\n" +
                "AND    EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "AND    AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "AND    EII.ATTRIBUTE1 = 'RENT'\n" +
                "AND    EII.DISABLE_DAT " + SyBaseSQLUtil.isNull() + " \n" +
                "AND    EII.ORGANIZATION_ID = ?\n" +
                "AND    ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)\n" +
                "AND    (( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?) OR\n" +
                "      ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?) OR\n" +
                "      ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?) OR\n" +
                "      ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?) OR\n" +
                "      ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?) OR\n" +
                "      ( " + SyBaseSQLUtil.isNull() + "  OR AME.USER_NAME LIKE ?) OR\n" +
                "      ( " + SyBaseSQLUtil.isNull() + "  OR AME.EMPLOYEE_NUMBER LIKE ?) OR\n" +
                "      ( " + SyBaseSQLUtil.isNull() + "  OR AMD2.DEPT_NAME LIKE ?) OR\n" +
                "      ( " + SyBaseSQLUtil.isNull() + "  OR AMD3.DEPT_NAME LIKE ?) OR\n" +
                "      ( " + SyBaseSQLUtil.isNull() + "  OR EII.MAINTAIN_USER LIKE ?))";
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getDeptCode());
        sqlArgs.add(dto.getDeptCode());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());
        sqlArgs.add(dto.getKey());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
