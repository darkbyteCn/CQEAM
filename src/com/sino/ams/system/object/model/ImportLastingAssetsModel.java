package com.sino.ams.system.object.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.object.dto.LastingAssetsDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-21
 * Time: 15:39:22
 * To change this template use File | Settings | File Templates.
 */
public class ImportLastingAssetsModel extends AMSSQLProducer {
    private SfUserDTO sfUser = null;

    public ImportLastingAssetsModel(SfUserDTO userAccount, LastingAssetsDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }


    /**
     * 功能：删除接口表的数据。
     * @return SQLModel 返回数据更新用SQLModel
     * @throws com.sino.base.exception.SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel deleteImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_LASTING_ASSETS_IMPORT "
                + " WHERE"
                + " USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：插入到接口表。
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel insertImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        LastingAssetsDTO eoDTO = (LastingAssetsDTO) dtoParameter;
        String sqlStr = "INSERT INTO AMS_LASTING_ASSETS_IMPORT\n" +
                "  (COMPANY_CODE,\n" +
                "   BARCODE,\n" +
                "   ITEM_NAME,\n" +
                "   ITEM_SPEC,\n" +
                "   EMPLOYEE_NUMBER,\n" +
                "   EMPLOYEE_NAME,\n" +
                "   WORKORDER_OBJECT_CODE,\n" +
                "   POWER,\n" +
                "   EQUIPMENT_PERFORMANCE,\n" +
                "   CONTENT_CODE,\n" +
                "   CONTENT_NAME,\n" +
                "   WORKORDER_OBJECT_NAME,\n" +
                "   SPECIALITY_DEPT,\n" +
                "   MAINTAIN_USER,\n" +
                "   MAINTAIN_DEPT,\n" +
                "   START_DATE,\n" +
                "   END_DATE,\n" +
                "   RENT_PERSON,\n" +
                "   TENANCY,\n" +
                "   YEAR_RENTAL,\n" +
                "   MONTH_REANTAL,\n" +
                "   REMARK,\n" +
                "   USER_ID)\n" +
                "   VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        sqlArgs.add(eoDTO.getCompanyCode());
        sqlArgs.add(eoDTO.getBarcode());
        sqlArgs.add(eoDTO.getItemName());
        sqlArgs.add(eoDTO.getItemSpec());
        sqlArgs.add(eoDTO.getEmployeeNumber());
        sqlArgs.add(eoDTO.getEmployeeName());
        sqlArgs.add(eoDTO.getWorkorderObjectCode());
        sqlArgs.add(eoDTO.getPower());
        sqlArgs.add(eoDTO.getEquipmentPerformance());
        sqlArgs.add(eoDTO.getContentCode());
        sqlArgs.add(eoDTO.getContentName());
        sqlArgs.add(eoDTO.getWorkorderObjectName());
        sqlArgs.add(eoDTO.getSpecialityDept());
        sqlArgs.add(eoDTO.getMaintainUser());
        sqlArgs.add(eoDTO.getMaintainDept());
        sqlArgs.add(eoDTO.getRentStartDate());
        sqlArgs.add(eoDTO.getRentEndDate());
        sqlArgs.add(eoDTO.getRentPerson());
        sqlArgs.add(eoDTO.getTenancy());
        sqlArgs.add(eoDTO.getYearRental());
        sqlArgs.add(eoDTO.getMonthReantal());
        sqlArgs.add(eoDTO.getRemark());
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：校验AMS_ITEM_IMPORT中BARCODE是否在ETS_ITEM_INFO中存在SQL
     * @param barcode String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel validateSameRentBarcode(String barcode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_RENT_INFO ARI WHERE ARI.BARCODE = ?";
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：校验AMS_ITEM_IMPORT中BARCODE是否在ETS_ITEM_INFO中存在SQL
     * @param barcode String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel validateSameEiiBarcode(String barcode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM ETS_ITEM_INFO EII WHERE EII.BARCODE = ?";
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：校验AMS_ITEM_IMPORT中BARCODE是否在ETS_ITEM_INFO中存在SQL
     * @param barcode String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel validateSameBarcode(String barcode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT COUNT(1) BAR_QTY\n" +
                "FROM   AMS_LASTING_ASSETS_IMPORT ALAI\n" +
                "WHERE  ALAI.BARCODE = ?\n" +
                "AND    ALAI.USER_ID = ?";
        sqlArgs.add(barcode);
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：构造地点代码错误更新记录SQL
     * @param barcode    String
     * @param codeError   String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel insertImprotErrorData(String barcode, String codeError) throws
            SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                + " AMS_LASTING_ASSETS_IMPORT"
                + " SET"
                + " ERROR = ?"
                + " WHERE"
                + " BARCODE = ?";
        sqlArgs.add(codeError);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：校验AMS_ITEM_IMPORT中NEW_ITEM_NAME、NEW_ITEM_SPEC是否有效
     * @param itemName String
     * @param itemSpec String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel validateItemNS(String itemName, String itemSpec) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                         " FROM ETS_SYSTEM_ITEM ESI, \n" +
                              " ETS_SYSITEM_DISTRIBUTE ESD\n" +
                        " WHERE ESI.ITEM_CODE = ESD.ITEM_CODE \n" +
                          " AND ESI.ITEM_NAME = ? \n" +
                          " AND ESI.ITEM_SPEC = ? \n" +
                          " AND ESD.ORGANIZATION_ID = ?";
        sqlArgs.add(itemName);
        sqlArgs.add(itemSpec);
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：校验AMS_ITEM_IMPORT中NEW_ITEM_NAME、NEW_ITEM_SPEC是否有效
     * @param newObjectCode String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel validateObjectCode(String newObjectCode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM ETS_OBJECT EO WHERE EO.WORKORDER_OBJECT_CODE = ? AND EO.ORGANIZATION_ID = ?";
        sqlArgs.add(newObjectCode);
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：校验AMS_ITEM_IMPORT中资产类别代码是否有效
     * @param contentCode String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel validateContentCode(String contentCode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT COUNT(1) FROM FA_CATEGORIES@MIS_FA FC WHERE FC.SEGMENT1||'.'||FC.SEGMENT2||'.000' = ?";
        sqlArgs.add(contentCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：校验AMS_ITEM_IMPORT中NEW_EMPLOYEE_NUMBER是否有效
     * @param newResDept String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel validateNewResDept(String newResDept) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_MIS_DEPT AMD WHERE AMD.DEPT_CODE = ?";
        sqlArgs.add(newResDept);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：校验AMS_ITEM_IMPORT中NEW_EMPLOYEE_NUMBER是否有效
     * @param newEmployeeNum String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel validateNewEmployeeNum(String newEmployeeNum) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_MIS_EMPLOYEE AME WHERE AME.EMPLOYEE_NUMBER = ?";
        sqlArgs.add(newEmployeeNum);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：校验AMS_ITEM_IMPORT中NEW_MANUFACTURER_ID是否有效
     * @param NewManufactId String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel validateNewManufactId(String NewManufactId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_MANUFACTURER AM WHERE CONVERT(VARCHAR,AM.MANUFACTURER_ID) = ?";
        sqlArgs.add(NewManufactId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：查看AMS_ITEM_IMPORT表中是否有错误记录
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel hasErrorModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        LastingAssetsDTO eoDTO = (LastingAssetsDTO) dtoParameter;
        String sqlStr = "SELECT 1 FROM AMS_LASTING_ASSETS_IMPORT ALAI WHERE  " + SyBaseSQLUtil.isNotNull("ALAI.ERROR") + "  AND ALAI.USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：查看AMS_ITEM_IMPORT表中错误记录
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getImportErrorModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ALAI.COMPANY_CODE,\n" +
                "       ALAI.BARCODE,\n" +
                "       ALAI.ITEM_NAME,\n" +
                "       ALAI.ITEM_SPEC,\n" +
                "       ALAI.EMPLOYEE_NUMBER,\n" +
                "       ALAI.EMPLOYEE_NAME,\n" +
                "       ALAI.WORKORDER_OBJECT_CODE,\n" +
                "       ALAI.POWER,\n" +
                "       ALAI.EQUIPMENT_PERFORMANCE,\n" +
                "       ALAI.CONTENT_CODE,\n" +
                "       ALAI.CONTENT_NAME,\n" +
                "       ALAI.WORKORDER_OBJECT_NAME,\n" +
                "       ALAI.SPECIALITY_DEPT,\n" +
                "       ALAI.MAINTAIN_USER,\n" +
                "       ALAI.MAINTAIN_DEPT,\n" +
                "       ALAI.START_DATE,\n" +
                "       ALAI.END_DATE,\n" +
                "       ALAI.RENT_PERSON,\n" +
                "       ALAI.TENANCY,\n" +
                "       ALAI.YEAR_RENTAL,\n" +
                "       ALAI.MONTH_REANTAL,\n" +
                "       ALAI.REMARK,\n" +
                "       ALAI.ERROR\n" +        
                "FROM   AMS_LASTING_ASSETS_IMPORT ALAI\n" +
                "WHERE  ALAI.USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：查看AMS_ITEM_IMPORT表中导入成功记录
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getImportFailModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AII.BOOK_TYPE_CODE, \n" +
                              " AII.BARCODE, \n" +
                              " AII.NEW_ITEM_NAME, \n" +
                              " AII.NEW_ITEM_SPEC, \n" +
                              " AII.NEW_OBJECT_CODE,\n" +
                              " AII.NEW_RESPONSIBILITY_DEPT, \n" +
                              " AII.NEW_EMPLOYEE_NUMBER, \n" +
                              " AII.NEW_MAINTAIN_DEPT, \n" +
                              " AII.NEW_MAINTAIN_USER,\n" +
                              " AII.NEW_MANUFACTURER_ID, \n" +
                              " AII.NEW_LNE_ID, \n" +
                              " AII.NEW_CEX_ID, \n" +
                              " AII.NEW_OPE_ID, \n" +
                              " AII.NEW_NLE_ID\n" +
                              " FROM AMS_ITEM_IMPORT AII\n" +
                       " WHERE AII.ERROR " + SyBaseSQLUtil.isNullNoParam() + "  " +
                         " AND AII.USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：查询AMS_ITEM_IMPORT表，返回导入正确数据
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getQueryImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ALAI.COMPANY_CODE,\n" +
                "        ALAI.BARCODE,\n" +
                "        ALAI.ITEM_NAME,\n" +
                "        ALAI.ITEM_SPEC,\n" +
                "        ALAI.EMPLOYEE_NUMBER,\n" +
                "        ALAI.EMPLOYEE_NAME,\n" +
                "        ALAI.WORKORDER_OBJECT_CODE,\n" +
                "        ALAI.POWER,\n" +
                "        ALAI.EQUIPMENT_PERFORMANCE,\n" +
                "        ALAI.CONTENT_CODE,\n" +
                "        ALAI.CONTENT_NAME,\n" +
                "        ALAI.WORKORDER_OBJECT_NAME,\n" +
                "        ALAI.SPECIALITY_DEPT,\n" +
                "        ALAI.MAINTAIN_USER,\n" +
                "        ALAI.MAINTAIN_DEPT,\n" +
                "        ALAI.START_DATE,\n" +
                "        ALAI.END_DATE,\n" +
                "        ALAI.RENT_PERSON,\n" +
                "        ALAI.TENANCY,\n" +
                "        ALAI.YEAR_RENTAL,\n" +
                "        ALAI.MONTH_REANTAL,\n" +
                "        ALAI.REMARK\n" +
                " FROM   AMS_LASTING_ASSETS_IMPORT ALAI\n" +
                " WHERE  ALAI.ERROR " + SyBaseSQLUtil.isNullNoParam() + " \n" +
                "    AND ALAI.USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：通过NEW_ITEM_NAME,NEW_ITEM_SPEC取得ITEM_CODE
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getItemCodeModel(String itemName, String itemSpec) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ESI.ITEM_CODE FROM ETS_SYSTEM_ITEM ESI WHERE ESI.ITEM_NAME = ? AND ESI.ITEM_SPEC = ?";
        sqlArgs.add(itemName);
        sqlArgs.add(itemSpec);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：通过NEW_OBJECT_CODE取得ADDRESS_ID
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getAddressIdModel(String objectCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AOA.ADDRESS_ID " +
                         " FROM ETS_OBJECT EO, " +
                              " AMS_OBJECT_ADDRESS AOA " +
                        " WHERE EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO " +
                          " AND EO.WORKORDER_OBJECT_CODE = ?";
        sqlArgs.add(objectCode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：通过NEW_EMPLOYEE_NUMBER取得EMPLOYEE_ID
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getEmployeeIdModel(String employeeNumber) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AME.EMPLOYEE_ID FROM AMS_MIS_EMPLOYEE AME WHERE AME.EMPLOYEE_NUMBER = ?";
        sqlArgs.add(employeeNumber);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：通过NEW_EMPLOYEE_NUMBER取得DEPT_CODE
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDeptCodeModel(String employeeNumber) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AME.DEPT_CODE FROM AMS_MIS_EMPLOYEE AME WHERE AME.EMPLOYEE_NUMBER = ?";
        sqlArgs.add(employeeNumber);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

        public SQLModel getDataCreateModel() throws SQLModelException {
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            LastingAssetsDTO eoDTO = (LastingAssetsDTO) dtoParameter;
            String sqlStr = "INSERT INTO ETS_ITEM_INFO EII\n" +
                    "  (EII.SYSTEMID,\n" +
                    "   EII.BARCODE,\n" +
                    "   EII.ITEM_CODE,\n" +
                    "   EII.RESPONSIBILITY_DEPT,\n" +
                    "   EII.RESPONSIBILITY_USER,\n" +
                    "   EII.ADDRESS_ID,\n" +
                    "   EII.POWER,\n" +
                    "   EII.CONTENT_CODE,\n" +
                    "   EII.CONTENT_NAME,\n" +
                    "   EII.SPECIALITY_DEPT,\n" +
                    "   EII.MAINTAIN_USER,\n" +
                    "   EII.MAINTAIN_DEPT,\n" +
                    "   EII.CREATION_DATE,\n" +
                    "   EII.FINANCE_PROP,\n" +
                    "   EII.OTHER_INFO,\n" +
                    "   EII.ORGANIZATION_ID,\n" +
                    "   EII.CREATED_BY)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+ SyBaseSQLUtil.getCurDate()+", 'RENT_ASSETS', ?, ?, ?)";
            sqlArgs.add(eoDTO.getSystemid());
            sqlArgs.add(eoDTO.getBarcode());
            sqlArgs.add(eoDTO.getItemCode());
            sqlArgs.add(eoDTO.getResponsibilityDept());
            sqlArgs.add(eoDTO.getEmployeeId());
            sqlArgs.add(eoDTO.getAddressId());
            sqlArgs.add(eoDTO.getPower());
            sqlArgs.add(eoDTO.getContentCode());
            sqlArgs.add(eoDTO.getContentName());
            sqlArgs.add(eoDTO.getSpecialityDept());
            sqlArgs.add(eoDTO.getMaintainUser());
            sqlArgs.add(eoDTO.getMaintainDept());
            sqlArgs.add(eoDTO.getEquipmentPerformance());
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(userAccount.getUserId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            return sqlModel;
    }

        public SQLModel getRENTCreateModel() throws SQLModelException {
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            try {
                LastingAssetsDTO eoDTO = (LastingAssetsDTO) dtoParameter;
                String sqlStr = "INSERT INTO AMS_RENT_INFO ARI\n" +
                        "  (ARI.RENT_ID,\n" +
                        "   ARI.BARCODE,\n" +
                        "   ARI.RENT_PERSON,\n" +
                        "   ARI.RENT_DATE,\n" +
                        "   ARI.END_DATE,\n" +
                        "   ARI.YEAR_RENTAL,\n" +
                        "   ARI.MONTH_REANTAL,\n" +
                        "   ARI.TENANCY,\n" +
                        "   ARI.CREATION_DATE,\n" +
                        "   ARI.CREATED_BY,\n" +
                        "   ARI.REMARK)\n" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, "+ SyBaseSQLUtil.getCurDate()+", ?, ?)";
                sqlArgs.add(eoDTO.getRentId());
                sqlArgs.add(eoDTO.getBarcode());
                sqlArgs.add(eoDTO.getRentPerson());
                sqlArgs.add(eoDTO.getStartDate());
                sqlArgs.add(eoDTO.getEndDate());
                sqlArgs.add(eoDTO.getYearRental());
                sqlArgs.add(eoDTO.getMonthReantal());
                sqlArgs.add(eoDTO.getTenancy());
                sqlArgs.add(userAccount.getUserId());
                sqlArgs.add(eoDTO.getRemark());
                sqlModel.setSqlStr(sqlStr);
                sqlModel.setArgs(sqlArgs);
            } catch (CalendarException ex) {
                ex.printLog();
                throw new SQLModelException(ex);
            }
            return sqlModel;
    }

    /**
     * 功能：校验校验当前用户新增租赁资产的权限
     * @param companyCode String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel validateOU(String companyCode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM ETS_OU_CITY_MAP EOCM WHERE EOCM.COMPANY_CODE = ? AND EOCM.ORGANIZATION_ID = ?";
        sqlArgs.add(companyCode);
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：校验责任人，责任部门对应是否正确
     * @param deptCode String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel validateEmployee(String deptCode, String employeeNumber) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_MIS_EMPLOYEE AME WHERE AME.DEPT_CODE = ? AND AME.EMPLOYEE_NUMBER = ?";
        sqlArgs.add(deptCode);
        sqlArgs.add(employeeNumber);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
