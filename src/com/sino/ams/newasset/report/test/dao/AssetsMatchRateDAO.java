package com.sino.ams.newasset.report.test.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.report.test.dto.SpecialAssetsReportDTO;
import com.sino.ams.newasset.report.test.model.AssetsMatchRateModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-3-10
 * Time: 13:57:34
 * To change this template use File | Settings | File Templates.
 */
public class AssetsMatchRateDAO extends AMSBaseDAO {

	public AssetsMatchRateDAO(SfUserDTO userAccount, SpecialAssetsReportDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 *
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO) dtoParameter;
		sqlProducer = new AssetsMatchRateModel(user, dto);
	}


	/**
	 * 功能：获取部门盘点设备明细Excel文件
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File getExportFile(String matchAssetsType) throws DataTransException {
		File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
            String reportTitle = "";
            if (matchAssetsType.equals("MATCH_DEPT")) {
               reportTitle = "资产账实相符率(实物管理部门层面)";
            } else {
               reportTitle = "资产账实相符率(公司层面)";
            }
			String fileName = reportTitle + ".xls";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setSourceConn(conn);
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);
			Map fieldMap = new HashMap();
            if (matchAssetsType.equals("MATCH_DEPT")) {
                fieldMap.put("COMPANY", "公司");
                fieldMap.put("DEPT_NAME", "责任部门");
                fieldMap.put("SUM_UNITS", "MIS资产数量");
                fieldMap.put("SUM_COST2", "MIS资产原值");
                fieldMap.put("SUM_COUNT", "账实相符数量");
                fieldMap.put("SUM_COST", "账实相符原值");
                fieldMap.put("AMOUNT_RATE", "账实相符数量百分比");
                fieldMap.put("MONEY_RATE", "账实相符原值百分比");
            } else {
                fieldMap.put("COMPANY", "公司");
                fieldMap.put("SUM_UNITS", "MIS资产数量");
                fieldMap.put("SUM_COST2", "MIS资产原值");
                fieldMap.put("SUM_COUNT", "账实相符数量");
                fieldMap.put("SUM_COST", "账实相符原值");
                fieldMap.put("AMOUNT_RATE", "账实相符数量百分比");
                fieldMap.put("MONEY_RATE", "账实相符原值百分比"); 
            }
			rule.setFieldMap(fieldMap);
			CustomTransData custData = new CustomTransData();
			custData.setReportTitle(reportTitle);
			custData.setReportPerson(userAccount.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			rule.setCalPattern(LINE_PATTERN);
			TransferFactory factory = new TransferFactory();
			DataTransfer transfer = factory.getTransfer(rule);
			transfer.transData();
			file = (File) transfer.getTransResult();
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataTransException(ex);
		}
		return file;
	}
}
