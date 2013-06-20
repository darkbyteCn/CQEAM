package com.sino.ams.system.object.model;

import com.f1j.ss.eo;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.object.dto.EtsItemDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA. User: su Date: 2009-4-26 Time: 10:44:39 To change this template use
 * File | Settings | File Templates.
 */
public class ImportItemModel extends AMSSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：设备在网数量 AMS_ITEM_ON_NET 数据库SQL构造层构造函数
	 * 
	 * @param userAccount
	 *            SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter
	 *            AmsItemOnNetDTO 本次操作的数据
	 */
	public ImportItemModel(SfUserDTO userAccount, EtsItemDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	/**
	 * 功能：删除接口表的数据。
	 * 
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException
	 *             发生日历异常时转化为该异常抛出
	 */
	public SQLModel deleteImportModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM" + " AMS_ITEM_IMPORT" + " WHERE"
				+ " USER_ID = ?";
		sqlArgs.add(userAccount.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：插入到接口表。
	 * 
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException
	 *             发生日历异常时转化为该异常抛出
	 */
	public SQLModel insertImportModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemDTO eoDTO = (EtsItemDTO) dtoParameter;
		String sqlStr = "INSERT INTO AMS_ITEM_IMPORT\n"
				+ "  (BOOK_TYPE_CODE, \n"
				+ "   BARCODE, \n"
				+ "   OLD_CONTENT_CODE, \n"
				+ "   CONTENT_CODE, \n"
				+ "   OLD_ITEM_NAME, \n"
				+ "   NEW_ITEM_NAME, \n"
				+ "   OLD_ITEM_SPEC, \n"
				+ "   NEW_ITEM_SPEC,\n"
				+ "   OLD_OBJECT_CODE, \n"
				+ "   NEW_OBJECT_CODE, \n"
				+ "   OLD_RESPONSIBILITY_DEPT, \n"
				+ "   NEW_RESPONSIBILITY_DEPT,\n"
				+ "   OLD_SPECIALITY_DEPT, \n"
				+ "   NEW_SPECIALITY_DEPT,\n"
				+ "   OLD_EMPLOYEE_NUMBER, \n"
				+ "   NEW_EMPLOYEE_NUMBER, \n"
				+ "   OLD_MAINTAIN_DEPT, \n"
				+ "   NEW_MAINTAIN_DEPT, \n"
				+ "   OLD_MAINTAIN_USER,\n"
				+ "   NEW_MAINTAIN_USER, \n"
				+ "   OLD_MANUFACTURER_ID, \n"
				+ "   NEW_MANUFACTURER_ID, \n"
				+ "   OLD_LNE_ID, \n"
				+ "   NEW_LNE_ID,\n"
				+ "   OLD_CEX_ID, \n"
				+ "   NEW_CEX_ID, \n"
				+ "   OLD_OPE_ID, \n"
				+ "   NEW_OPE_ID, \n"
				+ "   OLD_NLE_ID, \n"
				+ "   NEW_NLE_ID, \n"
				+ "   OLD_REMARK1, \n"
				+ "   NEW_REMARK1, \n"
				+ "   OLD_POWER, \n"
				+ "   NEW_POWER, \n"
				+ "   OLD_SHARE_STATUS, \n"
				+ "   NEW_SHARE_STATUS, \n"
				+ "   OLD_CONSTRUCT_STATUS, \n"
				+ "   NEW_CONSTRUCT_STATUS, \n"
				+ "   USER_ID)\n"
				+ " VALUES\n"
				+ " (?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		sqlArgs.add(eoDTO.getBookTypeCode());
		sqlArgs.add(eoDTO.getBarcode());
		sqlArgs.add(eoDTO.getOldContentCode());
		sqlArgs.add(eoDTO.getContentCode());
		sqlArgs.add(eoDTO.getOldItemName());
		sqlArgs.add(eoDTO.getNewItemName());
		sqlArgs.add(eoDTO.getOldItemSpec());
		sqlArgs.add(eoDTO.getNewItemSpec());
		sqlArgs.add(eoDTO.getOldObjectCode());
		sqlArgs.add(eoDTO.getNewObjectCode());
		sqlArgs.add(eoDTO.getOldResponsibilityDept());
		sqlArgs.add(eoDTO.getNewResponsibilityDept());
		sqlArgs.add(eoDTO.getOldSpecialityDept());
		sqlArgs.add(eoDTO.getNewSpecialityDept());
		sqlArgs.add(eoDTO.getOldEmployeeNumber());
		sqlArgs.add(eoDTO.getNewEmployeeNumber());
		sqlArgs.add(eoDTO.getOldMaintainDept());
		sqlArgs.add(eoDTO.getNewMaintainDept());
		sqlArgs.add(eoDTO.getOldMaintainUser());
		sqlArgs.add(eoDTO.getNewMaintainUser());
		sqlArgs.add(eoDTO.getOldManufacturerId());
		sqlArgs.add(eoDTO.getNewManufacturerId());
		sqlArgs.add(eoDTO.getOldLneId());
		sqlArgs.add(eoDTO.getNewLneId());
		sqlArgs.add(eoDTO.getOldCexId());
		sqlArgs.add(eoDTO.getNewCexId());
		sqlArgs.add(eoDTO.getOldOpeId());
		sqlArgs.add(eoDTO.getNewOpeId());
		sqlArgs.add(eoDTO.getOldNleId());
		sqlArgs.add(eoDTO.getNewNleId());
		sqlArgs.add(eoDTO.getOldRemark1());
		sqlArgs.add(eoDTO.getNewRemark1());
		sqlArgs.add(eoDTO.getOldPower());
		sqlArgs.add(eoDTO.getNewPower());
		sqlArgs.add(eoDTO.getOldShareStatus());
		sqlArgs.add(eoDTO.getNewShareStatus());
		sqlArgs.add(eoDTO.getOldConstructStatus());
		sqlArgs.add(eoDTO.getNewConstructStatus());
		sqlArgs.add(userAccount.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中BARCODE是否在ETS_ITEM_INFO中存在SQL
	 * 
	 * @param barcode
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel isBarcodeModel(String barcode) throws SQLModelException {
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
	 * 
	 * @param barcode
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel validateSameBarcode(String barcode)
			throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT COUNT(1) BAR_QTY FROM AMS_ITEM_IMPORT AIM WHERE AIM.BARCODE = ? AND AIM.USER_ID = ?";
		sqlArgs.add(barcode);
		sqlArgs.add(userAccount.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造地点代码错误更新记录SQL
	 * 
	 * @param barcode
	 *            String
	 * @param codeError
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel insertImprotErrorData(String barcode, String codeError)
			throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE" + " AMS_ITEM_IMPORT" + " SET" + " ERROR = ?"
				+ " WHERE" + " BARCODE = ?";
		sqlArgs.add(codeError);
		sqlArgs.add(barcode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中BARCODE是否属于本公司BARCODE
	 * 
	 * @param bookTypeCode
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel validateOu(String bookTypeCode) throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT 1 FROM ETS_OU_CITY_MAP EOCM WHERE EOCM.BOOK_TYPE_CODE = ? AND EOCM.ORGANIZATION_ID = ?";
		sqlArgs.add(bookTypeCode);
		sqlArgs.add(userAccount.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中BARCODE是否属于本公司BARCODE
	 * 
	 * @param barcode
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel isBarcodeOuModel(String barcode, String bookTypeCode)
			throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT 1 FROM ETS_ITEM_INFO EII WHERE EII.BARCODE = ? AND SUBSTRING(EII.BARCODE, 0,4) like '%?%'";
		sqlArgs.add(barcode);
		sqlArgs.add(bookTypeCode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中deptCode是否属于本公司
	 * 
	 * @param deptCode
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel validateOuDept(String bookTypeCode, String deptCode)
			throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT 1\n" + "FROM   AMS_MIS_DEPT AMD\n"
				+ "WHERE  AMD.COMPANY_CODE = ?\n"
				+ "       AND AMD.DEPT_CODE = ?";
		sqlArgs.add(bookTypeCode);
		sqlArgs.add(deptCode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中NEW_ITEM_NAME、NEW_ITEM_SPEC是否有效
	 * 
	 * @param itemName
	 *            String
	 * @param itemSpec
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel validateItemNS(String itemName, String itemSpec)
			throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT 1\n" + " FROM ETS_SYSTEM_ITEM ESI, \n"
				+ " ETS_SYSITEM_DISTRIBUTE ESD\n"
				+ " WHERE ESI.ITEM_CODE = ESD.ITEM_CODE \n"
				+ " AND ESI.ITEM_NAME = ? \n";
		if (!StrUtil.isEmpty(itemSpec)) {
			sqlStr += " AND ESI.ITEM_SPEC = ? \n";
		}
		sqlStr += " AND ESD.ORGANIZATION_ID = ?";
		sqlArgs.add(itemName);
		if (!StrUtil.isEmpty(itemSpec)) {
			sqlArgs.add(itemSpec);
		}
		sqlArgs.add(userAccount.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中NEW_ITEM_NAME、NEW_ITEM_SPEC是否有效
	 * 
	 * @param newObjectCode
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel validateObjectCode(String newObjectCode)
			throws SQLModelException {
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
	 * 功能：校验AMS_CONTENT_DIC中CONTENT_CODE是否有效
	 * 
	 * @param contentCode
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel validateContentCode(String contentCode)
			throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT 1 FROM AMS_CONTENT_DIC  WHERE CONTENT_CODE = ?";
		sqlArgs.add(contentCode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中NEW_EMPLOYEE_NUMBER是否有效
	 * 
	 * @param newResDept
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel validateNewResDept(String newResDept)
			throws SQLModelException {
		int pos = newResDept.indexOf(".");

		if (pos > -1) {
			newResDept = newResDept.substring(0, pos);
		}

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
	 * 
	 * @param newEmployeeNum
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel validateNewEmployeeNum(String newEmployeeNum)
			throws SQLModelException {
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
	 * 
	 * @param NewManufactId
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel validateNewManufactId(String NewManufactId)
			throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT 1 FROM AMS_MANUFACTURER AM WHERE AM.MANUFACTURER_ID = ?";
		sqlArgs.add(NewManufactId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中NEW_LNE_ID是否有效
	 * 
	 * @param newLneId
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel validateNewLNE(String newLneId) throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT 1 FROM AMS_LNE AL WHERE AL.AMS_LNE_ID = ?";
		sqlArgs.add(newLneId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中CEX_ID是否有效
	 * 
	 * @param newLneId
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel validateNewCEX(String newLneId) throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT 1 FROM AMS_CEX AC WHERE AC.AMS_CEX_ID = ?";
		sqlArgs.add(newLneId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中OPE_ID是否有效
	 * 
	 * @param newOpeId
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel validateNewOPE(String newOpeId) throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT 1 FROM AMS_OPE AO WHERE AO.AMS_OPE_ID = ?";
		sqlArgs.add(newOpeId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中NLE_ID是否有效
	 * 
	 * @param newNleId
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel validateNewNLE(String newNleId) throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT 1 FROM AMS_NLE AN WHERE AN.AMS_LNE_ID = ?";
		sqlArgs.add(newNleId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：查看AMS_ITEM_IMPORT表中是否有错误记录
	 * 
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException
	 *             发生日历异常时转化为该异常抛出
	 */
	public SQLModel hasErrorModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemDTO eoDTO = (EtsItemDTO) dtoParameter;
		String sqlStr = "SELECT 1 FROM AMS_ITEM_IMPORT AII WHERE AII.ERROR IS NOT NULL AND AII.USER_ID = ?";
		sqlArgs.add(userAccount.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：查看AMS_ITEM_IMPORT表中错误记录
	 * 
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException
	 *             发生日历异常时转化为该异常抛出
	 */
	public SQLModel getImportErrorModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT AII.BOOK_TYPE_CODE, \n" + " AII.BARCODE, \n"
				+ " AII.NEW_ITEM_NAME, \n" + " AII.NEW_ITEM_SPEC, \n"
				+ " AII.NEW_OBJECT_CODE,\n"
				+ " AII.NEW_RESPONSIBILITY_DEPT, \n"
				+ " AII.NEW_EMPLOYEE_NUMBER, \n"
				+ " AII.NEW_SPECIALITY_DEPT, \n" + " AII.NEW_MAINTAIN_DEPT, \n"
				+ " AII.NEW_MAINTAIN_USER,\n" + " AII.NEW_MANUFACTURER_ID, \n"
				+ " AII.NEW_LNE_ID, \n" + " AII.NEW_CEX_ID, \n"
				+ " AII.NEW_OPE_ID, \n" + " AII.NEW_NLE_ID,\n"
				+ " AII.NEW_REMARK1,\n" + " AII.NEW_POWER,\n"
				+ " AII.NEW_SHARE_STATUS,\n" + " AII.NEW_CONSTRUCT_STATUS,\n"
				+ " AII.ERROR\n" + " FROM AMS_ITEM_IMPORT AII\n"
				+ " WHERE AII.USER_ID = ?";
		sqlArgs.add(userAccount.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：查看AMS_ITEM_IMPORT表中导入成功记录
	 * 
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException
	 *             发生日历异常时转化为该异常抛出
	 */
	public SQLModel getImportFailModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT AII.BOOK_TYPE_CODE, \n" + " AII.BARCODE, \n"
				+ " AII.NEW_ITEM_NAME, \n" + " AII.NEW_ITEM_SPEC, \n"
				+ " AII.NEW_OBJECT_CODE,\n"
				+ " AII.NEW_RESPONSIBILITY_DEPT, \n"
				+ " AII.NEW_EMPLOYEE_NUMBER, \n" + " AII.NEW_MAINTAIN_DEPT, \n"
				+ " AII.NEW_MAINTAIN_USER,\n" + " AII.NEW_MANUFACTURER_ID, \n"
				+ " AII.NEW_LNE_ID, \n" + " AII.NEW_CEX_ID, \n"
				+ " AII.NEW_OPE_ID, \n" + " AII.NEW_NLE_ID\n"
				+ " FROM AMS_ITEM_IMPORT AII\n" + " WHERE AII.ERROR IS NULL "
				+ " AND AII.USER_ID = ?";
		sqlArgs.add(userAccount.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：查询AMS_ITEM_IMPORT表，返回导入正确数据
	 * 
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException
	 *             发生日历异常时转化为该异常抛出
	 */
	public SQLModel getQueryImportModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT AII.BOOK_TYPE_CODE, \n" + " AII.BARCODE, \n"
				+ " AII.OLD_CONTENT_CODE, \n" + " AII.CONTENT_CODE, \n"
				+ " AII.NEW_ITEM_NAME, \n" + " AII.NEW_ITEM_SPEC, \n"
				+ " AII.NEW_OBJECT_CODE,\n"
				+ " AII.NEW_RESPONSIBILITY_DEPT, \n"
				+ " AII.NEW_EMPLOYEE_NUMBER, \n"
				+ " AII.NEW_SPECIALITY_DEPT, \n" + " AII.NEW_MAINTAIN_DEPT, \n"
				+ " AII.NEW_MAINTAIN_USER,\n" + " AII.NEW_MANUFACTURER_ID, \n"
				+ " AII.NEW_LNE_ID, \n" + " AII.NEW_CEX_ID, \n"
				+ " AII.NEW_OPE_ID, \n" + " AII.NEW_NLE_ID,\n"
				+ " AII.NEW_REMARK1,\n" + " AII.NEW_POWER,\n"
				+ " AII.NEW_SHARE_STATUS,\n" + " AII.NEW_CONSTRUCT_STATUS\n"
				+ " FROM AMS_ITEM_IMPORT AII\n" + " WHERE AII.ERROR IS NULL \n"
				+ " AND AII.USER_ID = ?";
		sqlArgs.add(userAccount.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：通过NEW_ITEM_NAME,NEW_ITEM_SPEC取得ITEM_CODE
	 * 
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
	 * 
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getAddressIdModel(String objectCode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT AOA.ADDRESS_ID\n"
				+ "FROM   ETS_OBJECT         EO,\n"
				+ "       AMS_OBJECT_ADDRESS AOA\n"
				+ "WHERE  EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n"
				+ "AND    EO.WORKORDER_OBJECT_CODE = ?\n"
				+ "AND    EO.ORGANIZATION_ID = ?";
		sqlArgs.add(objectCode);
		sqlArgs.add(userAccount.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：通过NEW_EMPLOYEE_NUMBER取得EMPLOYEE_ID
	 * 
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
	 * 功能：新增ETS_ITEM_INFO
	 * 
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws CalendarException
	 */
	public SQLModel getDataInsertModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemDTO eoDTO = (EtsItemDTO) dtoParameter;
		
		
		//sangjun modify start 
		StringBuilder sb = new StringBuilder();
		sb.append( " INSERT INTO ETS_ITEM_INFO( \n " );
		sb.append( " SYSTEMID, \n " );
		sb.append( " BARCODE, \n " );
		sb.append( " VENDOR_BARCODE, \n " );
		sb.append( " ITEM_QTY, \n " );
		sb.append( " DISABLE_DATE, \n " );
		
		sb.append( " REMARK, \n " );
		sb.append( " START_DATE, \n " );
		sb.append( " ITEM_CODE, \n " );
		sb.append( " PROJECTID, \n " );
		sb.append( " ITEM_STATUS, \n " );
		
		sb.append( " ATTRIBUTE1, \n " );
		sb.append( " ATTRIBUTE2, \n " );
		sb.append( " SENDTOMIS_FLAG, \n " );
		sb.append( " MIS_ITEMNAME, \n " );
		sb.append( " MIS_ITEMSPEC, \n " );
		
		//4
		sb.append( " CREATION_DATE, \n " );
		sb.append( " CREATED_BY, \n " );
		sb.append( " LAST_UPDATE_DATE, \n " );
		sb.append( " LAST_UPDATE_BY, \n " );
		sb.append( " ASSET_ID, \n " );
		//5
		sb.append( " ADDRESS_ID, \n " );
		sb.append( " FINANCE_PROP, \n " );
		sb.append( " ATTRIBUTE3, \n " );
		sb.append( " PARENT_BARCODE, \n " );
		sb.append( " LAST_LOC_CHG_DATE, \n " );
		//6
		sb.append( " ORGANIZATION_ID, \n " );
		sb.append( " FA_BARCODE, \n " );
		sb.append( " IS_PARENT, \n " );
		sb.append( " RESPONSIBILITY_USER, \n " );
		sb.append( " RESPONSIBILITY_DEPT, \n " );
		//7
		sb.append( " MAINTAIN_USER, \n " );
		sb.append( " MAINTAIN_DEPT, \n " );
		sb.append( " MANUFACTURER_ID, \n " );
		sb.append( " IS_SHARE, \n " );
		sb.append( " CONTENT_CODE, \n " );
		//8
		sb.append( " CONTENT_NAME, \n " );
		sb.append( " POWER, \n " );
		sb.append( " LNE_ID, \n " );
		sb.append( " CEX_ID, \n " );
		sb.append( " OPE_ID, \n " );
		//9
		sb.append( " NLE_ID, \n " );
		sb.append( " IS_TMP, \n " );
		sb.append( " PRICE, \n " );
		sb.append( " OLD_CONTENT_CODE, \n " );
		sb.append( " OLD_CONTENT_NAME, \n " );
		//10
		sb.append( " REP_MANUFACTURER_ID, \n " );
		sb.append( " SPECIALITY_DEPT, \n " );
		sb.append( " DZYH_ADDRESS, \n " );
		sb.append( " OTHER_INFO, \n " );
		sb.append( " SHARE_STATUS, \n " );
		//11
		sb.append( " IS_TD, \n " );
		sb.append( " ACTUAL_QTY, \n " );
		sb.append( " IS_RENTAL, \n " );
		sb.append( " REMARK1, \n " );
		sb.append( " REMARK2, \n " );
		//12
		sb.append( " UNIT_OF_MEASURE, \n " );
		sb.append( " DISCARD_TYPE, \n " );
		sb.append( " DEAL_TYPE, \n " );
		sb.append( " REFER_NATIONAL_FUND, \n " );
		sb.append( " SN_ID, \n " );
		//13
		sb.append( " CONSTRUCT_STATUS, \n " );
		sb.append( " TF_NET_ASSET_VALUE, \n " );
		sb.append( " TF_DEPRN_COST, \n " );
		sb.append( " TF_DEPRECIATION, \n " );
		sb.append( " OLD_BARCODE, \n " );
		//14
		sb.append( " SPECIALITY_USER2, \n " );
		sb.append( " SPECIALITY_USER \n " );
		
		//1-5 
		sb.append( " )values( \n " );
		sb.append(  " NEWID(), ?, ?, ?, ? , " );
		sb.append(  " ?, ?, ?, ?, ?," );
		sb.append(  " ?, ?, ?, ?, ?," );
		sb.append(  " GETDATE(), ?, GETDATE(), ?, ?," ); 
		sb.append(  " ?, ?, ?, ?, ?," );
		//6-10
		sb.append(  " ?, ?, ?, ?, ?," );
		sb.append(  " ?, ?, ?, ?, ?," );
		sb.append(  " ?, ?, ?, ?, ?," );
		sb.append(  " ?, ?, ?, ?, ?," );
		sb.append(  " ?, ?, ?, ?, ?," );
		
		sb.append(  " ?, ?, ?, ?, ?," );
		sb.append(  " ?, ?, ?, ?, ?," );
		sb.append(  " ?, ?, ?, ?, ?," );
		sb.append(  " ?, ? ) " );
		
//				+ " ?, ?, ?, ?, " + " ?, ?, ?, ?," + " ?, GETDATE(), ?, ?,"
//				+ " ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?, ?,"
//				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?, ?, ?, "
//				+ " ?, ?, ?, ?, ?, ?, ?, ?, " + " ?, ?, ?, ?, ?, ?,"
//				+ " ?, ? )" );
		
//		String sqlStr = "INSERT INTO ETS_ITEM_INFO( \n"
//				+ " SYSTEMID, BARCODE, VENDOR_BARCODE, ITEM_QTY, \n"
//				+ " DISABLE_DATE, REMARK, START_DATE, ITEM_CODE, \n"
//				+ " PROJECTID, ITEM_STATUS, ATTRIBUTE1, ATTRIBUTE2, \n"
//				+ " SENDTOMIS_FLAG, MIS_ITEMNAME, MIS_ITEMSPEC, CREATION_DATE,\n"
//				+ " CREATED_BY, LAST_UPDATE_DATE, LAST_UPDATE_BY, ASSET_ID,\n"
//				+ " ADDRESS_ID, FINANCE_PROP, ATTRIBUTE3, PARENT_BARCODE, LAST_LOC_CHG_DATE,\n"
//				
//				+ " ORGANIZATION_ID, FA_BARCODE, IS_PARENT, RESPONSIBILITY_USER, RESPONSIBILITY_DEPT,\n"
//				+ " MAINTAIN_USER, MAINTAIN_DEPT, MANUFACTURER_ID, IS_SHARE, CONTENT_CODE, CONTENT_NAME,\n"
//				+ " POWER, LNE_ID, CEX_ID, OPE_ID, NLE_ID, IS_TMP, PRICE, OLD_CONTENT_CODE, OLD_CONTENT_NAME,\n"
//				
//				+ " REP_MANUFACTURER_ID, SPECIALITY_DEPT, DZYH_ADDRESS, OTHER_INFO, SHARE_STATUS, IS_TD, ACTUAL_QTY,\n "
//				+ " IS_RENTAL, REMARK1, REMARK2, UNIT_OF_MEASURE, DISCARD_TYPE, DEAL_TYPE, REFER_NATIONAL_FUND, SN_ID,\n "
//				
//				+ " CONSTRUCT_STATUS, TF_NET_ASSET_VALUE, TF_DEPRN_COST, TF_DEPRECIATION, OLD_BARCODE, SPECIALITY_USER2,\n"
//				+ " SPECIALITY_USER,\n"
//				// + " OLD_ITEM_NAME,ITEM_NAME)\n"
//				+ " values(" + " NEWID(), ?, ?, ?, " + " ?, ?, ?, ?, "
//				+ " ?, ?, ?, ?, " + " ?, ?, ?, ?," + " ?, GETDATE(), ?, ?,"
//				+ " ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?, ?,"
//				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?, ?, ?, "
//				+ " ?, ?, ?, ?, ?, ?, ?, ?, " + " ?, ?, ?, ?, ?, ?,"
//				+ " ?, ?, ?)";
		try {
			// systemId----数据库自动生成NEWID()
			sqlArgs.add(eoDTO.getBarcode());// excel 导入
			sqlArgs.add(eoDTO.getVendorBarcode());
			sqlArgs.add(eoDTO.getItemQty());
			sqlArgs.add( StrUtil.nullToString( eoDTO.getDisableDate() ) );
			
			sqlArgs.add(eoDTO.getRemark());
			sqlArgs.add( StrUtil.nullToString( eoDTO.getStartDates() ) );
			sqlArgs.add(eoDTO.getItemCode());// 通过 导入的 NEW_ITEM_NAME、 NEW_ITEM_SPEC
												// 查询ETS_SYSTEM_ITEM 表获取
			sqlArgs.add(eoDTO.getProjectObjId());
			sqlArgs.add(eoDTO.getItemSatus());
			
			
			sqlArgs.add(eoDTO.getAttribute1());
			sqlArgs.add(eoDTO.getAttribute2());
			sqlArgs.add(eoDTO.getSendtomisFlag());
			sqlArgs.add(eoDTO.getMisItemname());
			sqlArgs.add(eoDTO.getMisItemspec());
			
//			sqlArgs.add(eoDTO.getCreationDate());
			sqlArgs.add(eoDTO.getCreatedBy());
			sqlArgs.add(userAccount.getUserId());// 当前用户
			sqlArgs.add(eoDTO.getAssetId());
			
			sqlArgs.add(eoDTO.getAddressId());// 通过NEW_OBJECT_CODE取得ADDRESS_ID
			sqlArgs.add(eoDTO.getFinanceProp());
			sqlArgs.add(eoDTO.getAttribute3());
			sqlArgs.add(eoDTO.getParentBarcode());
			sqlArgs.add( StrUtil.nullToString(  eoDTO.getLastLocChgDate() ) );
			
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(eoDTO.getFaBarCode());
			sqlArgs.add(eoDTO.getIsParent());
			sqlArgs.add(eoDTO.getEmployeeId());// getNewEmployeeNumber
			sqlArgs.add(eoDTO.getNewResponsibilityDept());// excel导入
			
			sqlArgs.add(eoDTO.getNewMaintainUser());// excel导入
			sqlArgs.add(eoDTO.getNewMaintainDept());// excel导入
			sqlArgs.add(eoDTO.getNewManufacturerId());// excel导入
			sqlArgs.add(eoDTO.getIsShare());
			sqlArgs.add(eoDTO.getContentCode());//
			
			sqlArgs.add(eoDTO.getContentName());
			sqlArgs.add(eoDTO.getPower());
			sqlArgs.add(eoDTO.getNewLneId());// excel
			sqlArgs.add(eoDTO.getNewCexId());// excel
			sqlArgs.add(eoDTO.getNewOpeId());// excel
			
			sqlArgs.add(eoDTO.getNewNleId());// excel
			sqlArgs.add(eoDTO.getIsTap());
			sqlArgs.add(eoDTO.getPrice());
			sqlArgs.add(eoDTO.getOldContentCode());//
			sqlArgs.add(eoDTO.getOldContentName());
			
			sqlArgs.add(eoDTO.getRepManufacturerId());
			sqlArgs.add(eoDTO.getNewSpecialityDept());// excel导入
			sqlArgs.add(eoDTO.getDzyhAddress());
			sqlArgs.add(eoDTO.getOtherInfo());
			sqlArgs.add(eoDTO.getNewShareStatus());// excel导入
			
			sqlArgs.add(eoDTO.getIsTd());
			sqlArgs.add(eoDTO.getActualQty());
			sqlArgs.add(eoDTO.getIsRental());
			sqlArgs.add(eoDTO.getNewRemark1());// excel导入
			sqlArgs.add(eoDTO.getRemark2());
			
			sqlArgs.add(eoDTO.getUnitOfMeasure());
			sqlArgs.add(eoDTO.getDiscardType());
			sqlArgs.add(eoDTO.getDealType());
			sqlArgs.add(eoDTO.getReferNationalFound());
			sqlArgs.add(eoDTO.getSnId());
			
			sqlArgs.add(eoDTO.getNewConstructStatus());// excel导入
			sqlArgs.add(eoDTO.getTfnetAssetValue());
			sqlArgs.add(eoDTO.getTfDeprnCost());
			sqlArgs.add(eoDTO.getTfDepreciation());
			sqlArgs.add(eoDTO.getOldBarcode());
			
			sqlArgs.add(eoDTO.getSpecialityUser2());
			sqlArgs.add(eoDTO.getSpecialityUser());
			// sqlArgs.add(eoDTO.getOldItemName());
			// sqlArgs.add(eoDTO.getNewItemName());

		} catch (Throwable e) {
			e.printStackTrace();
		}
		sqlModel.setSqlStr( sb.toString() );
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：更新ETS_ITEM_INFO
	 * 
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsItemDTO eoDTO = (EtsItemDTO) dtoParameter;
		String sqlStr = "UPDATE ETS_ITEM_INFO\n"
				+ " SET ITEM_CODE = dbo.NVL(?, ITEM_CODE), \n"
				+ " CONTENT_CODE = dbo.NVL(?, CONTENT_CODE),\n";
				if( !StrUtil.isEmpty( eoDTO.getContentCode() )){
					sqlStr += " CONTENT_NAME = ( SELECT CONTENT_NAME FROM AMS_CONTENT_DIC WHERE CONTENT_CODE = ? ),\n";
				}
//				+ " CONTENT_NAME = dbo.NVL(?, CONTENT_NAME),\n"
				sqlStr += " OLD_CONTENT_CODE = dbo.NVL(?, OLD_CONTENT_CODE),\n"
				+ " ADDRESS_ID = dbo.NVL(?, ADDRESS_ID),\n"
				+ " RESPONSIBILITY_DEPT = dbo.NVL(?, RESPONSIBILITY_DEPT),\n"
				+ " RESPONSIBILITY_USER = dbo.NVL(?, RESPONSIBILITY_USER),\n"
				+ " SPECIALITY_DEPT = dbo.NVL(?, SPECIALITY_DEPT),\n"
				+ " MAINTAIN_DEPT = dbo.NVL(?, MAINTAIN_DEPT),\n"
				+ " MAINTAIN_USER = dbo.NVL(?, MAINTAIN_USER),\n"
				+ " MANUFACTURER_ID = dbo.NVL(?, MANUFACTURER_ID),\n"
				+ " LNE_ID = dbo.NVL(?, LNE_ID),\n"
				+ " CEX_ID = dbo.NVL(?, CEX_ID), \n"
				+ " OPE_ID = dbo.NVL(?, OPE_ID), \n"
				+ " NLE_ID = dbo.NVL(?, NLE_ID), \n"
				+ " REMARK1 = dbo.NVL(?, REMARK1), \n"
				+ " CONSTRUCT_STATUS = dbo.NVL(?, CONSTRUCT_STATUS), \n"
				+ " SHARE_STATUS = dbo.NVL(?, SHARE_STATUS), \n"
				+ " LAST_UPDATE_DATE = GETDATE(), \n" + " LAST_UPDATE_BY = ?\n"
				+
				// " OLD_ITEM_NAME = dbo.NVL(?, OLD_ITEM_NAME),\n" +
				// " ITEM_NAME = dbo.NVL(?, ITEM_NAME),\n" +
				" WHERE BARCODE = ?";

		sqlArgs.add(eoDTO.getItemCode());
		sqlArgs.add(eoDTO.getContentCode());
		if( !StrUtil.isEmpty( eoDTO.getContentCode() )){
			sqlArgs.add(eoDTO.getContentCode());
		}
//		sqlArgs.add(eoDTO.getContentName());
		sqlArgs.add(eoDTO.getOldContentCode());
		sqlArgs.add(eoDTO.getAddressId());
		sqlArgs.add(eoDTO.getNewResponsibilityDept());
		sqlArgs.add(eoDTO.getEmployeeId());
		sqlArgs.add(eoDTO.getNewSpecialityDept());
		sqlArgs.add(eoDTO.getNewMaintainDept());
		sqlArgs.add(eoDTO.getNewMaintainUser());
		sqlArgs.add(eoDTO.getNewManufacturerId());
		sqlArgs.add(eoDTO.getNewLneId());
		sqlArgs.add(eoDTO.getNewCexId());
		sqlArgs.add(eoDTO.getNewOpeId());
		sqlArgs.add(eoDTO.getNewNleId());
		sqlArgs.add(eoDTO.getNewRemark1());
		sqlArgs.add(eoDTO.getNewConstructStatus());
		sqlArgs.add(eoDTO.getNewShareStatus());
		sqlArgs.add(userAccount.getUserId());
		// sqlArgs.add(eoDTO.getOldItemName());
		// sqlArgs.add(eoDTO.getNewItemName());
		sqlArgs.add(eoDTO.getBarcode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：如果填写责任部门为空，查找EII中责任部门
	 * 
	 * @param barcode
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel findDeprCode(String barcode) throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT EII.RESPONSIBILITY_DEPT FROM ETS_ITEM_INFO EII WHERE EII.BARCODE = ?";
		sqlArgs.add(barcode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：校验责任人，责任部门对应是否正确
	 * 
	 * @param deptCode
	 *            String
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel validateEmployee(String deptCode, String employeeNumber)
			throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT 1 FROM AMS_MIS_EMPLOYEE AME WHERE AME.DEPT_CODE = ? AND AME.EMPLOYEE_NUMBER = ?";
		sqlArgs.add(deptCode);
		sqlArgs.add(employeeNumber);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel validateNewShareStatus(String NewShareStatus)
			throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT 1\n" + "  FROM ETS_FLEX_VALUE_SET EFVS,\n"
				+ "       ETS_FLEX_VALUES    EFV\n"
				+ " WHERE EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n"
				+ "   AND EFVS.CODE = 'SHARE_STATUS'\n" + "   AND EFV.CODE = ?";
		sqlArgs.add(NewShareStatus);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel validateNewContractStatus(String NewContractStatus)
			throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT 1\n" + "  FROM ETS_FLEX_VALUE_SET EFVS,\n"
				+ "       ETS_FLEX_VALUES    EFV\n"
				+ " WHERE EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n"
				+ "   AND EFVS.CODE = 'CONSTRUCT_STATUS'\n"
				+ "   AND EFV.CODE = ?";
		sqlArgs.add(NewContractStatus);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
