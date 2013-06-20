package com.sino.ams.sampling.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO;
import com.sino.ams.sampling.model.BatchOrderModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.data.Row;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class BatchOrderDAO extends AMSBaseDAO {

	public BatchOrderDAO(SfUserDTO userAccount, AmsAssetsSamplingHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO)dtoParameter;
		sqlProducer = new BatchOrderModel(user, dto);
	}


	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws com.sino.base.exception.WebFileDownException
	 */
	public File getExportFile() throws WebFileDownException {
		File file = null;
		BatchOrderModel modelProducer = null;
		try {
			modelProducer = (BatchOrderModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "抽查工单";
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
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		} catch (DataTransException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		}
		return file;
	}

	private Map getFieldMap(){
		Map fieldMap = new HashMap();
		fieldMap.put("ORDER_NO", "工单编号");
		fieldMap.put("SAMPLING_LOCATION_NAME", "抽查地点");
		fieldMap.put("CREATION_DATE", "工单创建日期");
		fieldMap.put("IMPLEMENT_USER", "工单执行人");
		fieldMap.put("IMPLEMENT_DAYS", "工单执行周期");
		fieldMap.put("ORDER_STATUS_VALUE", "工单状态");
		fieldMap.put("SAMPLEDED_OU_NAME", "执行公司");
		fieldMap.put("TASK_NO", "任务编号");
		fieldMap.put("TASK_NAME", "任务名称");
		fieldMap.put("TASK_CREATION_DATE", "任务创建日期");
		fieldMap.put("START_DATE", "任务起始日期");
		fieldMap.put("END_DATE", "任务截止日期");
		fieldMap.put("CREATED_OU_NAME", "任务创建公司");
		fieldMap.put("TASK_CREATED_USER", "任务创建人");
		return fieldMap;
	}

	/**
	 * 功能：获取任务创建公司、任务执行公司信息(创建工单时使用)
	 * @throws QueryException
	 */
	public void appendOuInfo() throws QueryException {
		try {
			AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
			if(dto.getCreatedOu() != -1){
				return;
			}
			BatchOrderModel modelProducer = (BatchOrderModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getOrgInfoModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				Row row = simp.getFirstRow();
				dto.setSampledOu(StrUtil.strToInt(row.getStrValue("SAMPLED_OU")));
				dto.setSampledOuName(row.getStrValue("SAMPLED_OU_NAME"));
				dto.setCreatedOu(Integer.parseInt(row.getStrValue("CREATED_OU")));
				dto.setCreatedOuName(row.getStrValue("CREATED_OU_NAME"));
				setDTOParameter(dto);
			}
		} catch (ContainerException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
	}
}
