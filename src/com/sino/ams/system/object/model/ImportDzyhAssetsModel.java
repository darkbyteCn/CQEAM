package com.sino.ams.system.object.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.object.dto.ImportDzyhAssetsDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class ImportDzyhAssetsModel extends AMSSQLProducer {
    private SfUserDTO sfUser = null;

    public ImportDzyhAssetsModel(SfUserDTO userAccount, ImportDzyhAssetsDTO dtoParameter) {
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
                + " AMS_DZYH_ASSETS_IMPORT "
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
        ImportDzyhAssetsDTO eoDTO = (ImportDzyhAssetsDTO) dtoParameter;
        String sqlStr = "INSERT INTO AMS_DZYH_ASSETS_IMPORT\n" +
                "  (COMPANY_CODE,\n" +
                "   BARCODE,\n" +
                "   ITEM_NAME,\n" +
                "   ITEM_SPEC,\n" +
                "   ITEM_UNIT,\n" +
                "   SPECIALITY_DEPT,\n" +
                "   SPECIALITY_USER,\n" +
                "   ADDRESS,\n" +
                "   RESPONSIBILITY_DEPT,\n" +
                "   RESPONSIBILITY_USER,\n" +
                "   MAINTAIN_USER,\n" +
                "   PRICE,\n" +
                "   IS_TD,\n" +
                "   DZYH_START_DATE,\n" +
                "   REMARK,\n" +
                "   USER_ID)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CONVERT(INT, ?))";
        sqlArgs.add(eoDTO.getCompanyCode());
        sqlArgs.add(eoDTO.getBarcode());
        sqlArgs.add(eoDTO.getItemName());
        sqlArgs.add(eoDTO.getItemSpec());
        sqlArgs.add(eoDTO.getItemUnit());
        sqlArgs.add(eoDTO.getSpecialityDept());
        sqlArgs.add(eoDTO.getSpecialityUser());
        sqlArgs.add(eoDTO.getAddress());
        sqlArgs.add(eoDTO.getResponsibilityDept());
        sqlArgs.add(eoDTO.getResponsibilityUser());
        sqlArgs.add(eoDTO.getMaintainUser());
        sqlArgs.add(eoDTO.getPrice());
        sqlArgs.add(eoDTO.getTd());
        sqlArgs.add(eoDTO.getDzyhStartDate());
        sqlArgs.add(eoDTO.getRemark());
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：校验AMS_DZYH_ASSETS_IMPORT中BARCODE是否在ETS_ITEM_INFO中存在SQL
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
     * 功能：校验AMS_DZYH_ASSETS_IMPORT中BARCODE是否有多条
     * @param barcode String
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel validateSameBarcode(String barcode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT COUNT(1) BAR_QTY\n" +
                "FROM   AMS_DZYH_ASSETS_IMPORT ADAI\n" +
                "WHERE  ADAI.BARCODE = ?\n" +
                "AND    ADAI.USER_ID = ?";
        sqlArgs.add(barcode);
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：构造错误信息
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
                + " AMS_DZYH_ASSETS_IMPORT "
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
     * 功能：校验AMS_DZYH_ASSETS_IMPORT中NEW_EMPLOYEE_NUMBER是否有效
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
     * 功能：校验AMS_VILLAGE_ASSETS_IMPORT中NEW_EMPLOYEE_NUMBER是否有效
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
     * 功能：查看AMS_DZYH_ASSETS_IMPORT表中是否有错误记录
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel hasErrorModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_DZYH_ASSETS_IMPORT ADAI WHERE  " + SyBaseSQLUtil.isNotNull("ADAI.ERROR") + "  AND ADAI.USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：查看AMS_DZYH_ASSETS_IMPORT表中错误记录
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getImportErrorModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ADAI.COMPANY_CODE,\n" +
                "       ADAI.BARCODE,\n" +
                "       ADAI.ITEM_NAME,\n" +
                "       ADAI.ITEM_SPEC,\n" +
                "       ADAI.ITEM_UNIT,\n" +
                "       ADAI.SPECIALITY_DEPT,\n" +
                "       ADAI.SPECIALITY_USER,\n" +
                "       ADAI.ADDRESS,\n" +
//                "       ADAI.RESPONSIBILITY_DEPT,\n" +
                "       ADAI.RESPONSIBILITY_USER,\n" +
                "       ADAI.MAINTAIN_USER,\n" +
                "       ADAI.PRICE,\n" +
                "       ADAI.IS_TD,\n" +
                "       ADAI.DZYH_START_DATE,\n" +
                "       ADAI.REMARK,\n" +
                "       ADAI.ERROR\n" +
                "FROM   AMS_DZYH_ASSETS_IMPORT ADAI\n" +
                "WHERE  ADAI.USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：查询AMS_DZYH_ASSETS_IMPORT表，返回导入正确数据
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getQueryImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ADAI.COMPANY_CODE,\n" +
                "       ADAI.BARCODE,\n" +
                "       ADAI.ITEM_NAME,\n" +
                "       ADAI.ITEM_SPEC,\n" +
                "       ADAI.ITEM_UNIT,\n" +
                "       ADAI.SPECIALITY_DEPT,\n" +
                "       ADAI.SPECIALITY_USER,\n" +
                "       ADAI.ADDRESS,\n" +
                "       ADAI.RESPONSIBILITY_DEPT,\n" +
                "       ADAI.RESPONSIBILITY_USER,\n" +
                "       ADAI.MAINTAIN_USER,\n" +
                "       ADAI.PRICE,\n" +
                "       ADAI.IS_TD,\n" +
                "       ADAI.DZYH_START_DATE START_DATE,\n" +
                "       ADAI.REMARK\n" +
                "FROM   AMS_DZYH_ASSETS_IMPORT ADAI\n" +
                //"WHERE  ADAI.ERROR " + SyBaseSQLUtil.isNull() + " \n" +
                "WHERE  (ADAI.ERROR IS NULL OR ADAI.ERROR = '') \n" +
                "AND    ADAI.USER_ID = ?";
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
        String sqlStr = "SELECT ESI.ITEM_CODE FROM ETS_SYSTEM_ITEM ESI WHERE ESI.ITEM_NAME = ? AND ESI.ITEM_SPEC = ? AND ESI.ITEM_CATEGORY = 'DZYH'";
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
            try {
                ImportDzyhAssetsDTO eoDTO = (ImportDzyhAssetsDTO) dtoParameter;
                String sqlStr = "INSERT INTO ETS_ITEM_INFO\n" +
                        "  (SYSTEMID,\n" +
                        "   BARCODE,\n" +
                        "   ITEM_CODE,\n" +
                        "   DZYH_ADDRESS,\n" +
                        "   ADDRESS_ID,\n" +
                        "   ORGANIZATION_ID,\n" +
                        "   RESPONSIBILITY_DEPT,\n" +
                        "   RESPONSIBILITY_USER,\n" +
                        "   SPECIALITY_DEPT,\n" +
                        "   SPECIALITY_USER,\n" +
                        "   MAINTAIN_USER,\n" +
                        "   IS_TD,\n" +
                        "   START_DATE,\n" +
                        "   PRICE,\n" +
                        "   REMARK,\n" +
                        "   FINANCE_PROP,\n" +
                        "   CREATION_DATE,\n" +
                        "   CREATED_BY)\n" +
                        "VALUES (?, ?, ?, ?, '-1', ?, ?, ?, ?, CONVERT(INT, ?), ?, ?, ?, CONVERT(decimal, ?), ?, 'DZYH', GETDATE(), ?) ";
                sqlArgs.add(eoDTO.getSystemid());
                sqlArgs.add(eoDTO.getBarcode());
                sqlArgs.add(eoDTO.getItemCode());
                sqlArgs.add(eoDTO.getAddress());
                sqlArgs.add(userAccount.getOrganizationId());
                sqlArgs.add(eoDTO.getResponsibilityDept());
                sqlArgs.add(eoDTO.getResponsEmployeeId());
                sqlArgs.add(eoDTO.getSpecialityDept());
                sqlArgs.add(eoDTO.getSpecialEmployeeId());
                sqlArgs.add(eoDTO.getMaintainUser());
                sqlArgs.add(eoDTO.getTd());
                sqlArgs.add(eoDTO.getStartDate());
                sqlArgs.add(eoDTO.getPrice());
                sqlArgs.add(eoDTO.getRemark());
                sqlArgs.add(userAccount.getUserId());
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

    /**
     * 功能：新增设备分类
     * @return SQLModel
     */
    public SQLModel insertSystemItem(String nextItemCode, String itemName, String itemSpec, String itemUnit) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_SYSTEM_ITEM \n" +
                "  (ITEM_CODE,\n" +
                "   ITEM_NAME,\n" +
                "   ITEM_SPEC,\n" +
                "   ITEM_CATEGORY,\n" +
                "   ITEM_UNIT,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY)\n" +
                "VALUES(?, ?, ?, 'DZYH', ?, GETDATE(), ?)";
        sqlArgs.add(nextItemCode);
        sqlArgs.add(itemName);
        sqlArgs.add(itemSpec);
        sqlArgs.add(itemUnit);
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：新增设备分类权限表
     * @return SQLModel
     */
    public SQLModel insertDistribute(String distributeCode, String itemCode, String orgId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_SYSITEM_DISTRIBUTE \n" +
                "  (SYSTEM_ID,\n" +
                "   ITEM_CODE,\n" +
                "   ORGANIZATION_ID,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY)\n" +
                "VALUES(?, ?, ?, GETDATE(), ?)";
        sqlArgs.add(distributeCode);
        sqlArgs.add(itemCode);
        sqlArgs.add(orgId);
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：查找ETS_OU_CITY_MATCH中的OU
     */
    public SQLModel findOu() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT EOCM.ORGANIZATION_ID FROM ETS_OU_CITY_MAP EOCM ";
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
}
