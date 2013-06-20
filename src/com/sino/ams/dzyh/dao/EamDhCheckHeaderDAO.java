package com.sino.ams.dzyh.dao;


import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.dto.EamDhCheckHeaderDTO;
import com.sino.ams.dzyh.model.EamDhCheckHeaderModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EamDhCheckHeaderDAO</p>
 * <p>Description:程序自动生成服务程序“EamDhCheckHeaderDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class EamDhCheckHeaderDAO extends AMSBaseDAO {

	/**
	 * 功能：低值易耗盘点工单头表 EAM_DH_CHECK_HEADER 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamDhCheckHeaderDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EamDhCheckHeaderDAO(BaseUserDTO userAccount, EamDhCheckHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		EamDhCheckHeaderDTO dto = (EamDhCheckHeaderDTO)dtoParameter;
		sqlProducer = new EamDhCheckHeaderModel((SfUserDTO)userAccount, dto);
	}

	/**
	 * 功能：保存工单(含暂存工单、下发工单)
	 * @throws DataHandleException
	 */
	public void saveOrder() throws DataHandleException {
		EamDhCheckHeaderDTO dto = (EamDhCheckHeaderDTO)dtoParameter;
		try {
			if (dto.getHeaderId().equals("")) {
				SeqProducer seqPrd = new SeqProducer(conn);
				//TODO
				dto.setHeaderId(seqPrd.getStrNextSeq("EAM_DH_CHECK_HEADER") + "" );
				String companyCode = userAccount.getCompanyCode();
				String orderType = dto.getOrderType();
				OrderNumGenerator numPrd = new OrderNumGenerator(conn, companyCode, orderType);
				dto.setOrderNo(numPrd.getOrderNum());
			}
			createData();
		} catch (SQLException ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能：根据工单批ID撤销工单
	 * @param batchIds String[]
	 * @throws DataHandleException
	 */
	public void cancelOrders(String[] batchIds) throws DataHandleException {
		EamDhCheckHeaderModel modelProducer = (EamDhCheckHeaderModel)sqlProducer;
		SQLModel sqlModel = modelProducer.getOrderCancelByBatchIdsModel(batchIds);
		DBOperator.updateRecord(sqlModel, conn);
	}


	public Object getDataByForeignKey(String foreigeKey) throws QueryException {
		Object data = super.getDataByForeignKey(foreigeKey);
		EamDhCheckHeaderDTO dto = (EamDhCheckHeaderDTO) dtoParameter;
		if(dto.getOrderType().equals(LvecDicts.ORD_TYPE1_YQYB)){//如果是仪器仪表才需要进行下面的确认方式处理
			if (!StrUtil.isEmpty(dtoClassName)) {
				if (data != null) {
					DTOSet dtos = (DTOSet) data;
					data = prdCheckToolsOpt(dtos);
				}
			}
		}
		return data;
	}

	/**
	 * 功能：为工单行数据生成下拉框
	 * @param orders DTOSet
	 * @return DTOSet
	 * @throws QueryException
	 */
	private DTOSet prdCheckToolsOpt(DTOSet orders) throws QueryException {
		try {
			EamDhCheckHeaderModel modelProducer = (EamDhCheckHeaderModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getCheckToolsModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				RowSet options = simp.getSearchResult();
				int optionCount = options.getSize();
				int dataCount = orders.getSize();
				StringBuffer cfmOpt = null;
				EamDhCheckHeaderDTO orderHeader = null;
				String checkTools = "";
				String optCode = "";
				String optLabel = "";
				String selProp = "";
				for (int i = 0; i < dataCount; i++) {
					cfmOpt = new StringBuffer();
					orderHeader = (EamDhCheckHeaderDTO) orders.getDTO(i);
					checkTools = orderHeader.getCheckTools();
					cfmOpt.append("<option value=\"\">--请选择--</option>");
					for (int j = 0; j < optionCount; j++) {
						Row option = options.getRow(j);
						optCode = option.getStrValue("CODE");
						optLabel = option.getStrValue("VALUE");
						if (checkTools.equals(optCode)) {
							selProp = " selected";
							orderHeader.setCheckToolsValue(optLabel);
						} else {
							selProp = "";
						}
						cfmOpt.append("<option value=\"");
						cfmOpt.append(optCode);
						cfmOpt.append("\"");
						cfmOpt.append(selProp);
						cfmOpt.append(">");
						cfmOpt.append(optLabel);
						cfmOpt.append("</option>");
					}
					orderHeader.setCheckToolsOpt(cfmOpt.toString());
				}
			}
		} catch (ContainerException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return orders;
	}


	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws com.sino.base.exception.WebFileDownException
	 */
	public File getExportFile() throws WebFileDownException {
		File file = null;
		EamDhCheckHeaderModel modelProducer = null;
		try {
			modelProducer = (EamDhCheckHeaderModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "工单执行情况";
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

		fieldMap.put("TASK_NAME", "任务名称");
		fieldMap.put("START_DATE", "起始日期");
		fieldMap.put("END_DATE", "截至日期");
		fieldMap.put("BATCH_NO", "工单批号");
		fieldMap.put("ORDER_NO", "工单编号");
		fieldMap.put("LOCATION_NAME", "工单地点");
		fieldMap.put("START_TIME", "开始日期");
		fieldMap.put("IMPLEMENT_DAYS", "执行周期");
		fieldMap.put("ORDER_TYPE_VALUE", "工单类型");
		fieldMap.put("CHECK_TOOLS_VALUE", "确认方式");
		fieldMap.put("ORDER_STATUS_VALUE", "工单状态");
		fieldMap.put("DISTRIBUTE_DATE", "下发日期");
		fieldMap.put("DISTRIBUTE_USER", "下发人");

		fieldMap.put("IMPLEMENT_USER", "执行人");
		fieldMap.put("DOWNLOAD_DATE", "下载日期");
		fieldMap.put("DOWNLOAD_USER", "下载人");
		fieldMap.put("SCANOVER_DATE", "扫描完成日期");
		fieldMap.put("SCANOVER_USER", "扫描完成人");
		fieldMap.put("UPLOAD_DATE", "上载日期");
		fieldMap.put("UPLOAD_USER", "上载人");
		fieldMap.put("ARCHIVED_DATE", "归档日期");
		fieldMap.put("ARCHIVED_USER", "归档人");

		return fieldMap;
	}
}
