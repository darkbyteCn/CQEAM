package com.sino.td.match.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.match.dto.AmsMactPropertyDTO;
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
import com.sino.td.match.model.AmsMatchedTdListModel;

/**
 * Created by IntelliJ IDEA.
 * User: jiangtao
 * Date: 2007-11-21
 * Time: 11:54:53
 * To change this template use File | Settings | File Templates.
 */
public class AmsMatchedTdListDAO extends AMSBaseDAO  {

	/**
	 * 功能：构造函数。
	 *
	 * @param userAccount  UserAccountDTO 用户信息
	 * @param dtoParameter DTO 其他与数据库交互时需要的参数。
	 * @param conn         Connection 数据库连接
	 */
	public AmsMatchedTdListDAO(SfUserDTO userAccount, AmsMactPropertyDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}
	/**
	* 功能：SQL生成器BaseSQLProducer的初始化。
	*
	* @param userAccount  BaseUserDTO 本系统最终操作用户类
	* @param dtoParameter DTO 本次操作的数据
	*/

	 protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
	   AmsMactPropertyDTO dtoPara = (AmsMactPropertyDTO) dtoParameter;
	   super.sqlProducer = new AmsMatchedTdListModel((SfUserDTO) userAccount, dtoPara);
	}


	/**
	 * 功能：导出Excel文件。
	 * @return File
	 * @throws DataTransException
	 */
	public File exportFile() throws DataTransException {           //导出
		File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setSourceConn(conn);
			String fileName = "已匹配资产清单.xls";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);

			Map fieldMap = new HashMap();
			fieldMap.put("BARCODE", "EAM条码");
			fieldMap.put("TAG_NUMBER", "MIS条码");
			fieldMap.put("ITEM_NAME", "EAM名称");
			fieldMap.put("ASSETS_DESCRIPTION", "MIS名称");
			fieldMap.put("ITEM_SPEC", "EAM型号");
			fieldMap.put("MODEL_NUMBER", "MIS型号");
			fieldMap.put("WORKORDER_OBJECT_CODE", "EAM地点代码");
			fieldMap.put("ASSETS_LOCATION_CODE", "MIS地点代码");
			fieldMap.put("WORKORDER_OBJECT_LOCATION", "EAM资产地点");
			fieldMap.put("ASSETS_LOCATION", "MIS资产地点");
			fieldMap.put("USER_NAME", "EAM责任人");
			fieldMap.put("ASSIGNED_TO_NAME", "MIS责任人");
			fieldMap.put("COST_NAME_AMS", "EAM成本中心");
			fieldMap.put("COST_NAME_MIS", "MIS成本中心");

			rule.setFieldMap(fieldMap);

			CustomTransData custData = new CustomTransData();
			custData.setReportTitle("已匹配资产清单");
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
