package com.sino.ams.newasset.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsItemCorrectLogDTO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.model.ItemMaintainModel3;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.datatrans.*;
import com.sino.base.dto.DTO;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.constant.WorldConstant;
import com.sino.base.data.RowSet;
import com.sino.base.data.Row;
import com.sino.framework.dto.BaseUserDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-3-14
 * Time: 13:48:55
 * To change this template use File | Settings | File Templates.
 */
public class ItemMaintainDAO3 extends AMSBaseDAO {
	private AmsItemCorrectLogDAO logDAO = null;
	private SimpleQuery simp = null;

	public ItemMaintainDAO3(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		logDAO = new AmsItemCorrectLogDAO(userAccount, null, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO)dtoParameter;
		sqlProducer = new ItemMaintainModel3((SfUserDTO)userAccount, dto);
	}

	public boolean updateItems(String[] barcodeNos) {
		boolean operateResult = false;
		boolean autoCommit = true;
		String barcodes = "";
		try {
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			String currTime = CalendarUtil.getCurrCalendar(CAL_PATT_45);
			String remark = userAccount.getUsername()
							+ "在"
							+ currTime
							+ "通过设备台帐维护更改";
			for (int i = 0; i < barcodeNos.length; i++) {
				barcodes += barcodeNos[i] + ",";
				dto.setBarcode(barcodeNos[i]);
				dto.setRemark(remark);
				setDTOParameter(dto);
				logDAO.setDTOParameter(getLogDTO());
				updateData();
				logDAO.createData();
			}
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} catch (QueryException ex) {
			ex.printLog();
		} finally{
			try {
				if (operateResult) {
					prodMessage(AssetsMessageKeys.ITEM_UPDATE_SUCCESS);
					conn.commit();
				} else {
					prodMessage(AssetsMessageKeys.ITEM_UPDATE_FAILURE);
					conn.rollback();
				}
				message.setIsError(!operateResult);
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
			}
		}
		return operateResult;
	}

	/**
	 * 功能：构造台帐维护日志
	 * @return AmsItemCorrectLogDTO
	 * @throws QueryException
	 */
	private AmsItemCorrectLogDTO getLogDTO() throws QueryException{
		AmsItemCorrectLogDTO logDTO = null;
		try {
			ItemMaintainModel3 modelProducer = (ItemMaintainModel3) sqlProducer;
			SQLModel sqlModel = modelProducer.getPrimaryKeyDataModel();
			if (simp == null) {
				simp = new SimpleQuery(sqlModel, conn);
				simp.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
			} else {
				simp.setSql(sqlModel);
			}
			simp.executeQuery();
			if (simp.hasResult()) {
				AmsAssetsAddressVDTO oldDTO = (AmsAssetsAddressVDTO) simp.getFirstDTO();
				AmsAssetsAddressVDTO newDTO = (AmsAssetsAddressVDTO)dtoParameter;
				logDTO = new AmsItemCorrectLogDTO();
				logDTO.setBarcode(newDTO.getBarcode());
				String correctContent = "";
				String[] fieldNames = {"ITEM_CODE", "ITEM_NAME", "ITEM_SPEC", "WORKORDER_OBJECT_CODE",
									  "WORKORDER_OBJECT_NAME", "RESPONSIBILITY_USER_NAME","RESPONSIBILITY_DEPT_NAME",
									  "MAINTAIN_USER", "MAINTAIN_DEPT_NAME"};
				String[] fieldDescs = {"设备分类代码", "设备名称", "设备型号", "地点代码", "地点名称",
									  "责任人","责任部门",
									  "使用人", "使用部门"};
				String fieldName = "";
				String javaField = "";
				String oldValue = "";
				String newValue = "";
				for (int i = 0; i < fieldNames.length; i++) {
					fieldName = fieldNames[i];
					javaField = StrUtil.getJavaField(fieldName);
					oldValue = String.valueOf(ReflectionUtil.getProperty(oldDTO, javaField));
					if(oldValue.equals("")){
						oldValue = "无";
					}
					newValue = String.valueOf(ReflectionUtil.getProperty(newDTO, javaField));
					newDTO.getMaintainDept();
					if (!oldValue.equals(newValue) && !newValue.equals("")) {
						correctContent += fieldDescs[i]
							+ ":"
							+ oldValue
							+ "-->>"
							+ newValue;
						correctContent += WorldConstant.ENTER_CHAR;
					}
				}
				logDTO.setCorrectContent(correctContent);
			}
		} catch (ReflectException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return logDTO;
	}

	public File getExportFile(String excelType) throws DataTransException, SQLModelException {
		ItemMaintainModel3 modelProducer = (ItemMaintainModel3) sqlProducer;
		SQLModel sqlModel = modelProducer.getPageQueryModel();
		String reportTitle = "实物台帐查询";
		if (!StrUtil.isNotEmpty(excelType)) {
			excelType = "xls";
		}
		String fileName = reportTitle + "." + excelType;
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

	private Map getFieldMap(){
        Map fieldMap = new HashMap();
        fieldMap.put("BARCODE", "标签号");
        fieldMap.put("ITEM_NAME", "设备名称");
        fieldMap.put("ITEM_SPEC", "规格型号");
        fieldMap.put("ITEM_UNIT", "计量单位");
        fieldMap.put("ITEM_QTY", "原始数量");
        fieldMap.put("ACTUAL_QTY", "实际数量");
        fieldMap.put("FINANCE_PROP_VALUE", "资产种类");
        fieldMap.put("WORKORDER_OBJECT_CODE", "地点编号");
        fieldMap.put("WORKORDER_OBJECT_NAME", "地点名称");
        fieldMap.put("CONTENT_CODE", "目录代码");
        fieldMap.put("CONTENT_NAME", "目录名称");
        fieldMap.put("CITY", "行政市");
        fieldMap.put("COUNTY", "行政县");
        fieldMap.put("AREA_TYPE_NAME", "行政区划");
        fieldMap.put("RESPONSIBILITY_USER_NAME", "责任人");
        fieldMap.put("EMPLOYEE_NUMBER", "员工编号");
        fieldMap.put("DEPT_CODE", "责任部门代码");
        fieldMap.put("RESPONSIBILITY_DEPT_NAME", "责任部门");
        fieldMap.put("MAINTAIN_USER", "使用人");
        fieldMap.put("MAINTAIN_DEPT_NAME", "使用部门");
        fieldMap.put("SPECIALITY_DEPT_NAME", "实物部门");
        fieldMap.put("PROJECT_NAME", "项目名称");
        fieldMap.put("PROJECT_NUMBER", "项目编号");
        fieldMap.put("IS_SHARE", "是否共享");
        fieldMap.put("CONSTRUCT_STATUS", "是否共建");
        fieldMap.put("LOG_NET_ELE", "逻辑网络元素");
        fieldMap.put("INVEST_CAT_NAME", "投资分类");
        fieldMap.put("OPE_NAME", "业务平台");
        fieldMap.put("LNE_NAME", "网络层次");
        fieldMap.put("LIFE_IN_YEARS", "资产使用年限");
        fieldMap.put("DATE_PLACED_IN_SERVICE", "资产启用日期");
        fieldMap.put("ASSETS_CREATE_DATE", "资产创建日期");
        fieldMap.put("ORIGINAL_COST", "资产原值");
        fieldMap.put("SCRAP_VALUE", "资产残值");
        fieldMap.put("IMPAIR_RESERVE", "资产减值准备");
        fieldMap.put("DEPRN_RESERVE", "资产累计折旧");
        fieldMap.put("DEPRN_COST", "资产净额");
        fieldMap.put("SEGMENT2", "资产类别代码");
        fieldMap.put("FA_CATEGORY2", "资产类别");
        fieldMap.put("REMARK", "备注");
        fieldMap.put("REMARK1", "备注一");
        fieldMap.put("REMARK2", "备注二");
        fieldMap.put("ITEM_STATUS_VALUE", "资产状态");
        return fieldMap;
	}

    public int getCompanyId(String company) throws SQLModelException, QueryException, ContainerException {
        int employeeId = 0;
        ItemMaintainModel3 eoModel = (ItemMaintainModel3) sqlProducer;
        SQLModel sqlModel = eoModel.getCompanyIdModel(company);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            employeeId = Integer.parseInt(row.getStrValue("ORGANIZATION_ID"));
        }
        return employeeId;
    }

    public String getDeptCode(String deptName) throws SQLModelException, QueryException, ContainerException {
        String employeeId = "";
        ItemMaintainModel3 eoModel = (ItemMaintainModel3) sqlProducer;
        SQLModel sqlModel = eoModel.getDeptCodeModel(deptName);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            employeeId = StrUtil.nullToString(row.getStrValue("DEPT_CODE"));
        }
        return employeeId;
    }

    public boolean inItPageQuery(String deptName) throws QueryException {
        boolean isNewPageQuery;
        if (deptName.indexOf("+") > 0) {
            isNewPageQuery = false;
        } else {
            boolean isNewQuery = isNewQuery(deptName);
            if (isNewQuery) {
                isNewPageQuery = true;
            } else {
                isNewPageQuery = false;
            }
        }
        return isNewPageQuery;
    }

    public boolean isNewQuery(String deptName) throws QueryException {
        boolean isNewQuery = false;
        ItemMaintainModel3 eoModel = (ItemMaintainModel3) sqlProducer;
        SQLModel sqlModel = eoModel.isNewQueryModel(deptName);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
           isNewQuery = true;
        }
        return isNewQuery;
    }

    public Map getFincePropCount (AmsAssetsAddressVDTO dto) throws QueryException, ContainerException{
        Map map = new HashMap();
        ItemMaintainModel3 modelProducer = (ItemMaintainModel3) sqlProducer;
        SQLModel sqlModel = modelProducer.getFincePropCount(dto);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        for (int i = 0; i< rs.getSize(); i++) {
            Row row = rs.getRow(i);
            map.put(row.getStrValue("CODE"),row.getStrValue("CNT"));
        }
        return map;
    }
}

