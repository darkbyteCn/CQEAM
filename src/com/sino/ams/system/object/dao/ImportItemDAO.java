package com.sino.ams.system.object.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.object.model.ImportItemModel;
import com.sino.ams.system.object.dto.EtsItemDTO;
import com.sino.ams.onnet.dto.AmsItemOnNetDTO;
import com.sino.ams.newasset.dao.AmsItemInfoHistoryDAO;
import com.sino.ams.newasset.dto.AmsItemInfoHistoryDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.util.StrUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.datatrans.*;
import com.sino.base.data.RowSet;
import com.sino.base.data.Row;
import com.sino.base.constant.WorldConstant;

import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA. User: su Date: 2009-4-26 Time: 10:44:18 To change this template use
 * File | Settings | File Templates.
 */
public class ImportItemDAO extends AMSBaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：地点导入 AMS_OBJECT_IMPORT 数据访问层构造函数
	 * 
	 * @param userAccount
	 *            SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter
	 *            AmsItemOnNetDTO 本次操作的数据
	 * @param conn
	 *            Connection 数据库连接，由调用者传入。
	 */
	public ImportItemDAO(SfUserDTO userAccount, AmsItemOnNetDTO dtoParameter,
			Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * 
	 * @param userAccount
	 *            BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter
	 *            DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		EtsItemDTO dtoPara = (EtsItemDTO) dtoParameter;
		super.sqlProducer = new ImportItemModel((SfUserDTO) userAccount,
				dtoPara);
	}

	/**
	 * 功能：删除接口表的数据。
	 * 
	 * @throws SQLModelException
	 */
	public void deleteImportModel() throws SQLModelException, QueryException,
			DataHandleException {
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.deleteImportModel();
		DBOperator.updateRecord(sqlModel, conn);
	}

	/**
	 * 新增的进行验证
	 * 
	 * @param dtoSet
	 * @throws SQLModelException
	 * @throws QueryException
	 * @throws ContainerException
	 */
	public void doVerifyData_insert(DTOSet dtoSet) throws SQLModelException,
			QueryException, ContainerException {
		if (dtoSet != null && dtoSet.getSize() > 0) {
			for (int i = 0; i < dtoSet.getSize(); i++) {
				EtsItemDTO itemDTO = (EtsItemDTO) dtoSet.getDTO(i);
				if (!validateBarcode(itemDTO.getBarcode())) {
					doVerifyData_insert(itemDTO);
				} else {
					insertImprotErrorData(itemDTO.getBarcode(), "第"
							+ itemDTO.getExcelLineId() + "行: " + "条码在系统中已存在");
				}
			}
		}
	}

	/**
	 * 新增的数据进行验证
	 * 
	 * @param dtoSet
	 * @throws SQLModelException
	 * @throws QueryException
	 * @throws ContainerException
	 */
	public void doVerifyData_insert(EtsItemDTO itemDTO)
			throws SQLModelException, QueryException, ContainerException {
		String excelLineId = itemDTO.getExcelLineId();
		String msg = "第" + excelLineId + "行: ";
		boolean flag = true;// 默认新名称跟型号存在
		boolean flags = true;
		// 不存在barcode, 则应该新增数据
		if (!validateOu(itemDTO.getBookTypeCode())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "资产账簿不属于本公司账簿");
		} else if (StrUtil.isEmpty(itemDTO.getNewItemName())
				|| StrUtil.isEmpty(itemDTO.getNewItemSpec())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新增资产新名称和型号必须输入");
		} else if (!validateItemNS(itemDTO.getNewItemName(), itemDTO
				.getNewItemSpec())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新名称和型号不存在");
			flag = false;
		} else if (StrUtil.isEmpty(itemDTO.getContentCode())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新资产目录组合代码必须输入");
		} else if (StrUtil.isEmpty(itemDTO.getContentCode())
				|| !validateContentCode(itemDTO.getContentCode())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新资产目录组合代码不存在");
		} else if (StrUtil.isEmpty(itemDTO.getNewObjectCode())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新增资产新地点编码必须输入");
		} else if (StrUtil.isEmpty(itemDTO.getBarcode())
				|| !validateObjectCode(itemDTO.getNewObjectCode())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新地点编码不存在");
			flags = false;
		} else if (StrUtil.isEmpty(itemDTO.getNewResponsibilityDept())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新责任部门代码必须输入");
		} else if (!validateNewResDept(itemDTO.getNewResponsibilityDept())
				&& !StrUtil.isEmpty(itemDTO.getNewResponsibilityDept())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新责任部门代码不存在");
		} else if (StrUtil.isEmpty(itemDTO.getNewEmployeeNumber())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新员工编号必须输入");
		} else if (!validateNewEmployeeNum(itemDTO.getNewEmployeeNumber())
				&& !StrUtil.isEmpty(itemDTO.getNewEmployeeNumber())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新员工编号不存在");
		} else if (StrUtil.isEmpty(itemDTO.getNewSpecialityDept())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新实物部门代码必须输入");
		} else if (!validateNewResDept(itemDTO.getNewSpecialityDept())
				&& !StrUtil.isEmpty(itemDTO.getNewSpecialityDept())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新实物部门代码不存在");
		}  else if (StrUtil.isEmpty(itemDTO.getNewMaintainDept())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新使用部门代码必须输入");
		}  else if (!validateNewResDept(itemDTO.getNewMaintainDept()) ) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新使用部门代码不存在");
		}  else if (StrUtil.isEmpty(itemDTO.getNewMaintainUser())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新使用人必须输入");
		} else if (!validateNewManufactId(itemDTO.getNewManufacturerId())
				&& !StrUtil.isEmpty(itemDTO.getNewManufacturerId())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新厂商代码不存在");
		}  else if (!validateNewLNE(itemDTO.getNewLneId())
				&& !StrUtil.isEmpty(itemDTO.getNewLneId())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新网元编码不存在");
		}  else if (!validateNewCEX(itemDTO.getNewCexId())
				&& !StrUtil.isEmpty(itemDTO.getNewCexId())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新投资分类编码不存在");
		}  else if (!validateNewOPE(itemDTO.getNewOpeId())
				&& !StrUtil.isEmpty(itemDTO.getNewOpeId())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新业务平台编码不存在");
		}  else if (!validateNewNLE(itemDTO.getNewNleId())
				&& !StrUtil.isEmpty(itemDTO.getNewNleId())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新网络层次编码不存在");
		}  else if (!validateOuDept(itemDTO.getBookTypeCode(), itemDTO
				.getNewResponsibilityDept())
				&& !StrUtil.isEmpty(itemDTO.getNewResponsibilityDept())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "责任部门对应公司不正确");
		}  else if (!validateOuDept(itemDTO.getBookTypeCode(), itemDTO
				.getNewMaintainDept())
				&& !StrUtil.isEmpty(itemDTO.getNewMaintainDept())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "使用部门对应公司不正确");
		} else if (StrUtil.isEmpty(itemDTO.getNewResponsibilityDept())
				&& !StrUtil.isEmpty(itemDTO.getNewEmployeeNumber())) {
			String deptCode = findDeprCode(itemDTO.getBarcode());
			if (!validateEmployee(deptCode, itemDTO.getNewEmployeeNumber())) {
				insertImprotErrorData(itemDTO.getBarcode(), msg
						+ "员工编号和EAM责任部门不一致");
			}
		} else if (!StrUtil.isEmpty(itemDTO.getNewResponsibilityDept())
				&& !StrUtil.isEmpty(itemDTO.getNewEmployeeNumber())) {
			if (!validateEmployee(itemDTO.getNewResponsibilityDept(), itemDTO
					.getNewEmployeeNumber())) {
				insertImprotErrorData(itemDTO.getBarcode(), msg
						+ "新员工编号和新责任部门不一致");
			}
		} else if (!validateShareStatus(itemDTO.getNewShareStatus())
				&& !StrUtil.isEmpty(itemDTO.getNewShareStatus())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "共享状态代码不存在");
		} else if (!validateContractStatus(itemDTO.getNewConstructStatus())
				&& !StrUtil.isEmpty(itemDTO.getNewConstructStatus())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "共建状态代码不存在");
		}
		// else if (!validateNewResDeptIsNum(itemDTO.getNewResponsibilityDept()) &&
		// !StrUtil.isEmpty(itemDTO.getNewResponsibilityDept())) {
		// insertImprotErrorData(itemDTO.getBarcode(), "新责任部门代码必须为数字");
		// }

		if (flags && !StrUtil.isEmpty(itemDTO.getNewObjectCode())) {
			String addressId = this.getAddressId(itemDTO.getNewObjectCode(),
					itemDTO.getBookTypeCode());
			if (addressId == null || "".equals(addressId)) {
				insertImprotErrorData(itemDTO.getBarcode(), msg
						+ "新地点编码对应的资产地点ID不存在");
			}
		}

		if (flag && !StrUtil.isEmpty(itemDTO.getNewItemName())
				&& !StrUtil.isEmpty(itemDTO.getNewItemSpec())) {// 获取ITEM_CODE
			String itemCode = this.getItemCode(itemDTO.getNewItemName(),
					itemDTO.getNewItemSpec());
			if (itemCode == null || "".equals(itemCode)) {
				insertImprotErrorData(itemDTO.getBarcode(), msg
						+ "新名称和型号对应的设备分类代码不存在");
			}
		}
	}

	/**
	 * 更新的进行验证
	 * 
	 * @param dtoSet
	 * @throws SQLModelException
	 * @throws QueryException
	 * @throws ContainerException
	 */
	public void doVerifyData_update(DTOSet dtoSet) throws SQLModelException,
			QueryException, ContainerException {
		if (dtoSet != null && dtoSet.getSize() > 0) {
			for (int i = 0; i < dtoSet.getSize(); i++) {
				EtsItemDTO itemDTO = (EtsItemDTO) dtoSet.getDTO(i);
				if (validateBarcode(itemDTO.getBarcode())) {
					doVerifyData_update(itemDTO);
				} else {
					insertImprotErrorData(itemDTO.getBarcode(), "第"
							+ itemDTO.getExcelLineId() + "行: " + "条码在系统中不存在");
				}
			}
		}
	}

	/**
	 * 更新的数据进行验证
	 * 
	 * @param dtoSet
	 * @throws SQLModelException
	 * @throws QueryException
	 * @throws ContainerException
	 */
	public void doVerifyData_update(EtsItemDTO itemDTO)
			throws SQLModelException, QueryException, ContainerException {
		String excelLineId = itemDTO.getExcelLineId();
		String msg = "第" + excelLineId + "行: ";
		// boolean flag = true;// 默认新名称跟型号存在
		// boolean flags = true;
		// 存在barcode, 则应该更新数据
		if (!validateOu(itemDTO.getBookTypeCode())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "资产账簿不属于本公司账簿");
		} else if (!validateSameBarcode(itemDTO.getBarcode())) {
			// 导入中存在重复的条码
			insertImprotErrorData(itemDTO.getBarcode(), msg + "条码重复");
		} else if (!StrUtil.isEmpty(itemDTO.getContentCode())
				&& !validateContentCode(itemDTO.getContentCode())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新资产目录组合代码不存在");
		}
		// else if (!validateBarcodeOu(itemDTO.getBarcode(), itemDTO.getBookTypeCode()))
		// {
		// insertImprotErrorData(itemDTO.getBarcode(), "条码不属于本公司条码");
		// }
		else if (!validateItemNS(itemDTO.getNewItemName(), itemDTO
				.getNewItemSpec())
				&& (!StrUtil.isEmpty(itemDTO.getNewItemName()))) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新名称或型号不存在");
		} else if (!validateObjectCode(itemDTO.getNewObjectCode())
				&& !StrUtil.isEmpty(itemDTO.getNewObjectCode())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新地点编码不存在");
		} else if (!validateNewResDept(itemDTO.getNewResponsibilityDept())
				&& !StrUtil.isEmpty(itemDTO.getNewResponsibilityDept())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新责任部门代码不存在");
		} else if (!validateNewEmployeeNum(itemDTO.getNewEmployeeNumber())
				&& !StrUtil.isEmpty(itemDTO.getNewEmployeeNumber())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新员工编号不存在");
		} else if (!validateNewResDept(itemDTO.getNewSpecialityDept())
				&& !StrUtil.isEmpty(itemDTO.getNewSpecialityDept())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新实物部门代码不存在");
		} else if (!validateNewResDept(itemDTO.getNewMaintainDept())
				&& !StrUtil.isEmpty(itemDTO.getNewMaintainDept())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新使用部门代码不存在");
		} else if (!validateNewManufactId(itemDTO.getNewManufacturerId())
				&& !StrUtil.isEmpty(itemDTO.getNewManufacturerId())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新厂商代码不存在");
		} else if (!validateNewLNE(itemDTO.getNewLneId())
				&& !StrUtil.isEmpty(itemDTO.getNewLneId())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新网元编码不存在");
		} else if (!validateNewCEX(itemDTO.getNewCexId())
				&& !StrUtil.isEmpty(itemDTO.getNewCexId())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新投资分类编码不存在");
		} else if (!validateNewOPE(itemDTO.getNewOpeId())
				&& !StrUtil.isEmpty(itemDTO.getNewOpeId())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新业务平台编码不存在");
		} else if (!validateNewNLE(itemDTO.getNewNleId())
				&& !StrUtil.isEmpty(itemDTO.getNewNleId())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "新网络层次编码不存在");
		} else if (!validateOuDept(itemDTO.getBookTypeCode(), itemDTO
				.getNewResponsibilityDept())
				&& !StrUtil.isEmpty(itemDTO.getNewResponsibilityDept())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "责任部门对应公司不正确");
		} else if (!validateOuDept(itemDTO.getBookTypeCode(), itemDTO
				.getNewMaintainDept())
				&& !StrUtil.isEmpty(itemDTO.getNewMaintainDept())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "使用部门对应公司不正确");
		} else if (StrUtil.isEmpty(itemDTO.getNewResponsibilityDept())
				&& !StrUtil.isEmpty(itemDTO.getNewEmployeeNumber())) {
			String deptCode = findDeprCode(itemDTO.getBarcode());
			if (!validateEmployee(deptCode, itemDTO.getNewEmployeeNumber())) {
				insertImprotErrorData(itemDTO.getBarcode(), msg
						+ "员工编号和EAM责任部门不一致");
			}
		} else if (!StrUtil.isEmpty(itemDTO.getNewResponsibilityDept())
				&& !StrUtil.isEmpty(itemDTO.getNewEmployeeNumber())) {
			if (!validateEmployee(itemDTO.getNewResponsibilityDept(), itemDTO
					.getNewEmployeeNumber())) {
				insertImprotErrorData(itemDTO.getBarcode(), msg
						+ "新员工编号和新责任部门不一致");
			}
		} else if (!validateShareStatus(itemDTO.getNewShareStatus())
				&& !StrUtil.isEmpty(itemDTO.getNewShareStatus())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "共享状态代码不存在");
		} else if (!validateContractStatus(itemDTO.getNewConstructStatus())
				&& !StrUtil.isEmpty(itemDTO.getNewConstructStatus())) {
			insertImprotErrorData(itemDTO.getBarcode(), msg + "共建状态代码不存在");
		}

	}

	/**
	 * 功能：检查接口表数据的有效性。
	 */
	public void doVerifyData(DTOSet dtoSet) throws SQLModelException,
			QueryException, ContainerException {
		if (dtoSet != null && dtoSet.getSize() > 0) {
			for (int i = 0; i < dtoSet.getSize(); i++) {
				EtsItemDTO itemDTO = (EtsItemDTO) dtoSet.getDTO(i);
				// 存量资产校验
				if (!validateBarcode(itemDTO.getBarcode())) {
					// 不存在barcode, 则应该新增数据
					doVerifyData_insert(itemDTO);
				} else {
					// 存在barcode, 则应该更新数据
					doVerifyData_update(itemDTO);
					// else if (!validateNewResDeptIsNum(itemDTO.getNewResponsibilityDept()) &&
					// !StrUtil.isEmpty(itemDTO.getNewResponsibilityDept())) {
					// insertImprotErrorData(itemDTO.getBarcode(), "新责任部门代码必须为数字");
					// }
				}
			}
		}
	}

	// /**
	// * 功能：校验AMS_ITEM_IMPORT中NEW_RESPONSIBILITY_DEPT是否为数字
	// * @throws SQLModelException
	// */
	// public boolean validateNewResDeptIsNum(String newResDept) throws SQLModelException,
	// QueryException {
	// boolean isNum;
	// try {
	// Integer.parseInt(newResDept);
	// isNum = true;
	// Integer.parseInt(newResDept);
	// } catch(NumberFormatException ex) {
	// isNum = false;
	// }
	// return isNum;
	// }

	/**
	 * 功能：校验AMS_ITEM_IMPORT中BARCODE是否存在于ETS_ITEM_INFO中
	 * 
	 * @throws SQLModelException
	 */
	public boolean validateEmployee(String deptCode, String employeeNumber)
			throws SQLModelException, QueryException {
		boolean hasBarcode = false;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.validateEmployee(deptCode, employeeNumber);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasBarcode = true;
		}
		return hasBarcode;
	}

	/**
	 * 功能：填写责任部门为空，查找EII中责任部门（为校验责任人是否属于责任部门）
	 * 
	 * @throws SQLModelException
	 */
	public String findDeprCode(String barcode) throws SQLModelException,
			QueryException, ContainerException {
		String deptCode = "";
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.findDeprCode(barcode);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		RowSet rs = simpleQuery.getSearchResult();
		if (rs != null && rs.getSize() > 0) {
			Row row = rs.getRow(0);
			deptCode = StrUtil.nullToString(row
					.getStrValue("RESPONSIBILITY_DEPT"));
		}
		return deptCode;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中BARCODE是否属于本公司BARCODE
	 * 
	 * @throws SQLModelException
	 */
	public boolean validateOuDept(String bookTypeCode, String deptCode)
			throws SQLModelException, QueryException {
		boolean hasBarcode = false;
		String subBookTypeCode = bookTypeCode.substring(8, 12);
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.validateOuDept(subBookTypeCode, deptCode);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasBarcode = true;
		}
		return hasBarcode;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中BARCODE是否存在于ETS_ITEM_INFO中
	 * 
	 * @throws SQLModelException
	 */
	public boolean validateBarcode(String barcode) throws SQLModelException,
			QueryException {
		boolean hasBarcode = false;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.isBarcodeModel(barcode);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasBarcode = true;
		}
		return hasBarcode;
	}

	/**
	 * 功能：插入错误信息。
	 * 
	 * @throws SQLModelException
	 */
	public void insertImprotErrorData(String barcode, String codeError)
			throws SQLModelException {
		try {
			ImportItemModel onNetModel = (ImportItemModel) sqlProducer;
			SQLModel sqlModel = onNetModel.insertImprotErrorData(barcode,
					codeError);
			DBOperator.updateRecord(sqlModel, conn);
		} catch (DataHandleException ex) {
			ex.printLog();
		}
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中BARCODE条码重复
	 * 
	 * @throws SQLModelException
	 */
	public boolean validateSameBarcode(String barcode)
			throws SQLModelException, QueryException, ContainerException {
		boolean hasBarcode = true;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.validateSameBarcode(barcode);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		RowSet rs = simpleQuery.getSearchResult();
		if (rs != null && rs.getSize() > 0) {
			Row row = rs.getRow(0);
			int count = Integer.parseInt(StrUtil.nullToString(row
					.getStrValue("BAR_QTY")));
			if (count > 1) {
				hasBarcode = false;
			}
		}
		return hasBarcode;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中BARCODE是否属于本公司BARCODE
	 * 
	 * @throws SQLModelException
	 */
	public boolean validateOu(String bookTypeCode) throws SQLModelException,
			QueryException {
		boolean hasBarcode = false;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.validateOu(bookTypeCode);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasBarcode = true;
		}
		return hasBarcode;
	}

	/**
	 * 功能：校验 AMS_CONTENT_DIC 中CONTENT_CODE是否有效
	 * 
	 * @throws SQLModelException
	 */
	public boolean validateContentCode(String contentCode)
			throws SQLModelException, QueryException {
		boolean hasBarcode = false;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.validateContentCode(contentCode);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasBarcode = true;
		}
		return hasBarcode;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中BARCODE是否属于本公司BARCODE
	 * 
	 * @throws SQLModelException
	 */
	public boolean validateBarcodeOu(String barcode, String bookTypeCode)
			throws SQLModelException, QueryException {
		boolean hasBarcode = false;
		String subBarcode = barcode.substring(0, 4);
		String subBookTypeCode = bookTypeCode.substring(8, 12);
		if (subBarcode.equals(subBookTypeCode)) {
			hasBarcode = true;
		}
		// ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		// SQLModel sqlModel = eoModel.isBarcodeOuModel(barcode, bookTypeCode);
		// SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		// simpleQuery.executeQuery();
		// if(simpleQuery.hasResult()){
		// hasBarcode = true;
		// }
		return hasBarcode;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中NEW_ITEM_NAME、NEW_ITEM_SPEC是否有效
	 * 
	 * @throws SQLModelException
	 */
	public boolean validateItemNS(String itemName, String itemSpec)
			throws SQLModelException, QueryException {
		boolean hasBarcode = false;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.validateItemNS(itemName, itemSpec);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasBarcode = true;
		}
		return hasBarcode;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中NEW_OBJECT_CODE是否有效
	 * 
	 * @throws SQLModelException
	 */
	public boolean validateObjectCode(String newObjectCode)
			throws SQLModelException, QueryException {
		boolean hasBarcode = false;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.validateObjectCode(newObjectCode);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasBarcode = true;
		}
		return hasBarcode;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中NEW_RESPONSIBILITY_DEPT是否有效
	 * 
	 * @throws SQLModelException
	 */
	public boolean validateNewResDept(String newResDept)
			throws SQLModelException, QueryException {
		boolean hasBarcode = false;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.validateNewResDept(newResDept);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasBarcode = true;
		}
		return hasBarcode;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中NEW_EMPLOYEE_NUMBER是否有效
	 * 
	 * @throws SQLModelException
	 */
	public boolean validateNewEmployeeNum(String newEmployeeNum)
			throws SQLModelException, QueryException {
		boolean hasBarcode = false;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.validateNewEmployeeNum(newEmployeeNum);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasBarcode = true;
		}
		return hasBarcode;
	}

	/**
	 * 功能:记录资产信息变动历史
	 * 
	 * @throws DataHandleException
	 */
	public void logBarcodeHistory(EtsItemDTO eoDTO) {
		AmsItemInfoHistoryDTO historyDTO = new AmsItemInfoHistoryDTO();
		historyDTO.setOrderCategory("3");
		historyDTO.setOrderNo("");
		historyDTO.setCreatedBy(userAccount.getUserId());
		historyDTO.setOrderDtlUrl("");
		historyDTO.setBarcode(eoDTO.getBarcode());
		AmsItemInfoHistoryDAO historyDAO = new AmsItemInfoHistoryDAO(
				userAccount, historyDTO, conn);
		historyDAO.recordHistory();
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中NEW_MANUFACTURER_ID是否有效
	 * 
	 * @throws SQLModelException
	 */
	public boolean validateNewManufactId(String NewManufactId)
			throws SQLModelException, QueryException {
		boolean hasBarcode = false;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.validateNewManufactId(NewManufactId);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasBarcode = true;
		}
		return hasBarcode;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中NEW_LNE_ID是否有效
	 * 
	 * @throws SQLModelException
	 */
	public boolean validateNewLNE(String newLneId) throws SQLModelException,
			QueryException {
		boolean hasBarcode = false;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.validateNewLNE(newLneId);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasBarcode = true;
		}
		return hasBarcode;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中CEX_ID是否有效
	 * 
	 * @throws SQLModelException
	 */
	public boolean validateNewCEX(String newCexId) throws SQLModelException,
			QueryException {
		boolean hasBarcode = false;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.validateNewCEX(newCexId);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasBarcode = true;
		}
		return hasBarcode;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中OPE_ID是否有效
	 * 
	 * @throws SQLModelException
	 */
	public boolean validateNewOPE(String newOpeId) throws SQLModelException,
			QueryException {
		boolean hasBarcode = false;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.validateNewOPE(newOpeId);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasBarcode = true;
		}
		return hasBarcode;
	}

	/**
	 * 功能：校验AMS_ITEM_IMPORT中NLE_ID是否有效
	 * 
	 * @throws SQLModelException
	 */
	public boolean validateNewNLE(String newNleId) throws SQLModelException,
			QueryException {
		boolean hasBarcode = false;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.validateNewNLE(newNleId);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasBarcode = true;
		}
		return hasBarcode;
	}

	/**
	 * 功能：校验在AMS_ITEM_IMPORT是否存在导入错误
	 * 
	 * @throws SQLModelException
	 */
	public boolean importHasError() throws SQLModelException, QueryException {
		boolean hasError = false;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.hasErrorModel();
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasError = true;
		}
		return hasError;
	}

	/**
	 * 功能：在AMS_ITEM_IMPORT中获取导入成功的数据
	 * 
	 * @throws QueryException
	 */
	public DTOSet getImport() throws QueryException, SQLModelException {
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SimpleQuery sq = new SimpleQuery(eoModel.getQueryImportModel(), conn);
		sq.setDTOClassName(EtsItemDTO.class.getName());
		sq.executeQuery();
		return sq.getDTOSet();
	}

	/**
	 * 功能：通过NEW_ITEM_NAME,NEW_ITEM_SPEC取得ITEM_CODE
	 * 
	 * @throws SQLModelException
	 */
	public String getItemCode(String itemName, String itemSpce)
			throws SQLModelException, QueryException, ContainerException {
		String itemCode = "";
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.getItemCodeModel(itemName, itemSpce);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		RowSet rs = simpleQuery.getSearchResult();
		if (rs != null && rs.getSize() > 0) {
			Row row = rs.getRow(0);
			itemCode = StrUtil.nullToString(row.getStrValue("ITEM_CODE"));
		}
		return itemCode;
	}

	/**
	 * 功能：通过NEW_OBJECT_CODE取得ADDRESS_ID
	 * 
	 * @throws SQLModelException
	 */
	public String getAddressId(String objectCode, String bookTypeCode)
			throws SQLModelException, QueryException, ContainerException {
		String addressId = "";
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		// String bookCode = bookTypeCode.substring(8);//可根据模板上填写的账簿判断OU
		SQLModel sqlModel = eoModel.getAddressIdModel(objectCode);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		RowSet rs = simpleQuery.getSearchResult();
		if (rs != null && rs.getSize() > 0) {
			Row row = rs.getRow(0);
			addressId = StrUtil.nullToString(row.getStrValue("ADDRESS_ID"));
		}
		return addressId;
	}

	/**
	 * 功能：通过NEW_EMPLOYEE_NUMBER取得EMPLOYEE_ID
	 * 
	 * @throws SQLModelException
	 */
	public String getEmployeeId(String employeeNumber)
			throws SQLModelException, QueryException, ContainerException {
		String employeeId = "";
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.getEmployeeIdModel(employeeNumber);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		RowSet rs = simpleQuery.getSearchResult();
		if (rs != null && rs.getSize() > 0) {
			Row row = rs.getRow(0);
			employeeId = StrUtil.nullToString(row.getStrValue("EMPLOYEE_ID"));
		}
		return employeeId;
	}

	public boolean validateShareStatus(String NewShareStatus)
			throws SQLModelException, QueryException {
		boolean hasBarcode = false;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel.validateNewShareStatus(NewShareStatus);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasBarcode = true;
		}
		return hasBarcode;
	}

	public boolean validateContractStatus(String NewContractStatus)
			throws SQLModelException, QueryException {
		boolean hasBarcode = false;
		ImportItemModel eoModel = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = eoModel
				.validateNewContractStatus(NewContractStatus);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		if (simpleQuery.hasResult()) {
			hasBarcode = true;
		}
		return hasBarcode;
	}

	/**
	 * 功能：错误信息导出EXCEL
	 */
	public File getExportFile() throws DataTransException, SQLModelException {
		ImportItemModel modelProducer = (ImportItemModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getImportErrorModel();
		String reportTitle = "资产实物成批调拨错误信息导出";
		String fileName = reportTitle + ".xls";
		TransRule rule = new TransRule();
		rule.setDataSource(sqlModel);
		rule.setSourceConn(conn);
		String filePath = WorldConstant.USER_HOME;
		filePath += WorldConstant.FILE_SEPARATOR;
		filePath += fileName;
		rule.setTarFile(filePath);
		DataRange range = new DataRange();
		rule.setDataRange(range);
		rule.setFieldMap(getFieldMap());
		CustomTransData custData = new CustomTransData();
		custData.setReportTitle(reportTitle);
		custData.setReportPerson(userAccount.getUsername());
		custData.setNeedReportDate(true);
		rule.setCustData(custData);
		rule.setCalPattern(LINE_PATTERN);
		TransferFactory factory = new TransferFactory();
		DataTransfer transfer = factory.getTransfer(rule);
		transfer.transData();
		return (File) transfer.getTransResult();
	}

	private Map getFieldMap() {
		Map fieldMap = new HashMap();
		fieldMap.put("BOOK_TYPE_CODE", "资产帐簿");
		fieldMap.put("BARCODE", "标签号");
		fieldMap.put("NEW_ITEM_NAME", "新资产名称");
		fieldMap.put("NEW_ITEM_SPEC", "新规格型号");
		fieldMap.put("NEW_OBJECT_CODE", "新地点编码组合");
		fieldMap.put("NEW_RESPONSIBILITY_DEPT", "新责任部门代码");
		fieldMap.put("NEW_EMPLOYEE_NUMBER", "新责任人员工编号");
		fieldMap.put("NEW_SPECIALITY_DEPT", "新实物部门代码");
		fieldMap.put("NEW_MAINTAIN_DEPT", "新使用部门代码");
		fieldMap.put("NEW_MAINTAIN_USER", "新使用人姓名");
		fieldMap.put("NEW_MANUFACTURER_ID", "新厂商代码");
		fieldMap.put("NEW_LNE_ID", "新网元编码");
		fieldMap.put("NEW_CEX_ID", "新投资分类编码");
		fieldMap.put("NEW_OPE_ID", "新业务平台编码");
		fieldMap.put("NEW_NLE_ID", "新网络层次编码");
		fieldMap.put("NEW_CONSTRUCT_STATUS", "新共建状态");
		fieldMap.put("NEW_SHARE_STATUS", "新共享状态");
		fieldMap.put("ERROR", "错误信息");
		return fieldMap;
	}
}