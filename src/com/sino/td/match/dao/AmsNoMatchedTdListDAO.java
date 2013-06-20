package com.sino.td.match.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.match.dto.AmsNoMactingAssetDTO;
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
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.td.match.model.AmsNoMatchedTdListModel;

/**
 * Created by IntelliJ IDEA.
 * User: jiangtao
 * Date: 2007-11-21
 * Time: 11:54:53
 * To change this template use File | Settings | File Templates.
 */
public class AmsNoMatchedTdListDAO extends BaseDAO  {
	private SfUserDTO sfUser;

	{
		sfUser = null;
	}

	/**
	 * 功能：构造函数。
	 *
	 * @param userAccount  UserAccountDTO 用户信息
	 * @param dtoParameter DTO 其他与数据库交互时需要的参数。
	 * @param conn         Connection 数据库连接
	 */
	public AmsNoMatchedTdListDAO(SfUserDTO userAccount, AmsNoMactingAssetDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		 sfUser = userAccount;
	}
	/**
	* 功能：SQL生成器BaseSQLProducer的初始化。
	*
	* @param userAccount  BaseUserDTO 本系统最终操作用户类
	* @param dtoParameter DTO 本次操作的数据
	*/

	 protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
	   AmsNoMactingAssetDTO dtoPara = (AmsNoMactingAssetDTO) dtoParameter;
	   super.sqlProducer = new AmsNoMatchedTdListModel((SfUserDTO) userAccount, dtoPara);



	}


	/**
	 * 功能：导出Excel文件。
	 * @return File
	 * @throws DataTransException
	 */
	public File exportFile() throws DataTransException {
		File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setSourceConn(conn);
			String fileName = "未匹配资产清单.xls";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);

			Map fieldMap = new HashMap();
			fieldMap.put("TAG_NUMBER", "资产标签");
			fieldMap.put("ASSET_NUMBER", "资产编号");
			fieldMap.put("ASSETS_DESCRIPTION", "资产描述");
			fieldMap.put("MODEL_NUMBER", "资产型号");
			fieldMap.put("ASSETS_LOCATION", "资产地点");
			fieldMap.put("ASSETS_LOCATION_CODE", "地点代码");
			fieldMap.put("DATE_PLACED_IN_SERVICE", "启用日期");
			fieldMap.put("LIFE_IN_YEARS", "使用年限");
			fieldMap.put("COST", "资产原值");
			fieldMap.put("BOOK_TYPE_CODE", "资产账簿");
			fieldMap.put("COST_CENTER_NAME", "成本中心");

			rule.setFieldMap(fieldMap);

			CustomTransData custData = new CustomTransData();
			custData.setReportTitle("未匹配资产清单");
			custData.setReportPerson(sfUser.getUsername());
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
