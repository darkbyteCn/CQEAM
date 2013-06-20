package com.sino.ams.match.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.match.dto.OverplusEquipmentDTO;
import com.sino.ams.match.model.OverplusEquipmentModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
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
 * User: Suhp
 * Date: 2007-11-23
 * Time: 16:56:27
 * To change this template use File | Settings | File Templates.
 */
public class OverplusEquipmentDAO extends AMSBaseDAO {

	public OverplusEquipmentDAO(SfUserDTO userAccount, OverplusEquipmentDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		OverplusEquipmentDTO dtoPara = (OverplusEquipmentDTO) dtoParameter;
		super.sqlProducer = new OverplusEquipmentModel((SfUserDTO) userAccount, dtoPara);
	}

	/**
	 * 功能：导出Excel文件。
	 *
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 *
	 */
	public File exportFile() throws DataTransException {
		File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setCalPattern(CalendarConstant.LINE_PATTERN);
			rule.setSourceConn(conn);
			String fileName = "剩余设备清单.xls";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);

			Map fieldMap = new HashMap();
			fieldMap.put("ROWNUM", "序号");
			fieldMap.put("BARCODE", "EAM条码");
			fieldMap.put("ITEM_NAME", "EAM设备名称");
			fieldMap.put("ITEM_SPEC", "EAM规格型号");
			fieldMap.put("ITEM_CATEGORY", "设备类型");
			fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");
			fieldMap.put("WORKORDER_OBJECT_NAME", "设备地点");
			fieldMap.put("COST_CENTER_NAME", "成本中心");
			rule.setFieldMap(fieldMap);

			CustomTransData custData = new CustomTransData();
			custData.setReportTitle("剩余设备清单报表");
			custData.setReportPerson(userAccount.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
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
