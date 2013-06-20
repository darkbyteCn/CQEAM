package com.sino.ams.newasset.report.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.report.model.RentDeptAssetsReportModel;
import com.sino.ams.system.rent.dto.RentDTO;
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
 * Date: 2009-3-4
 * Time: 13:29:25
 * To change this template use File | Settings | File Templates.
 */

public class RentDeptAssetsReportDAO extends AMSBaseDAO {

	public RentDeptAssetsReportDAO(SfUserDTO userAccount, RentDTO dtoParameter, Connection conn) {
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
		RentDTO dto = (RentDTO) dtoParameter;
		sqlProducer = new RentDeptAssetsReportModel(user, dto);
	}


	/**
	 * 功能：获取经营租赁部门统计Excel文件
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File getExportFile() throws DataTransException {
		File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			String reportTitle = "经营租赁资产部门统计报表";
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
			fieldMap.put("COMPANY", "公司OU");
			fieldMap.put("RESPONSIBILITY_DEPT", "责任部门");
            fieldMap.put("BARCODE", "标签号");
            fieldMap.put("ITEM_NAME", "资产名称");
            fieldMap.put("ITEM_SPEC", "规格型号");
            fieldMap.put("ITEM_UNIT", "单位");
            
            fieldMap.put("MANUFACTURER_NAME", "生产厂商名称");
            fieldMap.put("POWER", "额定功率");
            fieldMap.put("OTHER_INFO", "设备性能");
            fieldMap.put("CONTENT_CODE", "资产类别代码组合");
            fieldMap.put("CONTENT_NAME", "资产类别描述");
            fieldMap.put("RESPONSIBILITY_USER", "责任人编号");
            
            fieldMap.put("USER_NAME", "责任人姓名");
            fieldMap.put("OBJECT_NAME", "资产地点");
            fieldMap.put("RENT_DATE", "起始日期");
            fieldMap.put("END_DATE", "到期时间");
            fieldMap.put("RENT_PERSON", "出租方");
            
            fieldMap.put("TENANCY", "租期(年)");
            fieldMap.put("YEAR_RENTAL", "年租金(元)");
            fieldMap.put("MONTH_REANTAL", "月租金");

//            fieldMap.put("LAST_MONTH_RATE", "比上月增长率");
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
