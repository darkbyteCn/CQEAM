package com.sino.ams.system.object.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.object.model.AssetsNoMatchResponsibleModel;
import com.sino.ams.system.object.model.CommonObjectModel;
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
import com.sino.base.exception.WebFileDownException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AssetsNoMatchResponsibleDAO extends AMSBaseDAO {
	public AssetsNoMatchResponsibleDAO(BaseUserDTO userAccount, EtsFaAssetsDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		model = new AssetsNoMatchResponsibleModel((SfUserDTO) userAccount, dtoParameter);
	}


	private AssetsNoMatchResponsibleModel model = null;

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		EtsFaAssetsDTO dto = (EtsFaAssetsDTO)dtoParameter;
		sqlProducer = new AssetsNoMatchResponsibleModel(user, dto);
	}

	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws com.sino.base.exception.WebFileDownException
	 */
	public File getExportFile() throws WebFileDownException {
		File file = null;
		try {
			AssetsNoMatchResponsibleModel modelProducer = (AssetsNoMatchResponsibleModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "责任人与部门差异查询";
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
			Map fieldMap = getFieldMap();
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
		} catch (DataTransException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		}
		return file;
	}


	private Map getFieldMap(){
		Map fieldMap = new HashMap();
		fieldMap.put("COMPANY", "公司名称");
		fieldMap.put("RESPONSIBILITY_DEPT", "资产所属责任部门");
		fieldMap.put("BARCODE", "标签号");
		fieldMap.put("ITEM_NAME", "资产名称");
		fieldMap.put("ITEM_SPEC", "规格型号");
		fieldMap.put("USER_NAME", "责任人");
		fieldMap.put("DEPT_NAME", "责任人所属部门");

		fieldMap.put("CONTENT_CODE", "资产目录代码");
		fieldMap.put("CONTENT_NAME", "资产目录名称");
		fieldMap.put("WORKORDER_OBJECT_CODE", "地点编号");
		fieldMap.put("WORKORDER_OBJECT_NAME", "地点名称");
		return fieldMap;
	}


}
