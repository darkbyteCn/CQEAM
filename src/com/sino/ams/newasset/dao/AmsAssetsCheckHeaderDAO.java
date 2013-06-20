package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
import com.sino.ams.newasset.model.AmsAssetsCheckHeaderModel;
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
import com.sino.sms.bean.MessageSaver;
import com.sino.sms.constant.SMSConstant;
import com.sino.sms.dto.SfMsgDefineDTO;

/**
 * <p>Title: AmsAssetsCheckHeaderDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsCheckHeaderDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class AmsAssetsCheckHeaderDAO extends AMSBaseDAO {

	/**
	 * 功能：资产盘点头表(EAM) AMS_ASSETS_CHECK_HEADER 数据访问层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsCheckHeaderDTO 本次操作的数据
	 * @param conn         Connection 数据库连接，由调用者传入。
	 */
	public AmsAssetsCheckHeaderDAO(BaseUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount  BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
		sqlProducer = new AmsAssetsCheckHeaderModel((SfUserDTO)userAccount, dto);
	}


	/**
	 * 功能:根据工单批ID删除工单信息
	 * @throws DataHandleException
	 */
	public void deleteByBatchId() throws DataHandleException {
		AmsAssetsCheckHeaderModel modelProducer = (AmsAssetsCheckHeaderModel)sqlProducer;
		SQLModel sqlModel = modelProducer.getDeleteByForeignKeyModel("batchId");
		DBOperator.updateRecord(sqlModel, conn);
	}

	/**
	 * 功能：保存盘点工单信息，并返回盘点工单的工单号
	 * @throws DataHandleException
	 */
	public void saveCheckHeader() throws DataHandleException {
		AmsAssetsCheckHeaderModel modelProducer = (AmsAssetsCheckHeaderModel) sqlProducer;
		try {
			boolean isNewData = false;
			AmsAssetsCheckHeaderDTO headerDTO = (AmsAssetsCheckHeaderDTO) dtoParameter;
			String headerId = headerDTO.getHeaderId();
			if (StrUtil.isEmpty(headerId)) {
				SeqProducer seqProducer = new SeqProducer(conn);
				headerId = seqProducer.getGUID();
				headerDTO.setHeaderId(headerId);
				if (headerDTO.getTransNo().equals(AssetsWebAttributes.ORDER_AUTO_PROD)) {
					String companyCode = userAccount.getCompanyCode();
					String orderType = headerDTO.getOrderType();
					OrderNumGenerator numberProducer = new OrderNumGenerator(conn, companyCode, orderType);
					headerDTO.setTransNo(numberProducer.getOrderNum());
				}
				headerDTO.setCreatedBy(userAccount.getUserId());
				headerDTO.setOrganizationId(userAccount.getOrganizationId());
				isNewData = true;
			}
			setDTOParameter(headerDTO);
			if (isNewData) {
				createData();
			} else {
				updateData();
			}
			AmsAssetsCheckLineDTO lineDTO = new AmsAssetsCheckLineDTO();
			lineDTO.setHeaderId(headerDTO.getHeaderId());
			AmsAssetsCheckLineDAO lineDAO = new AmsAssetsCheckLineDAO(userAccount, lineDTO, conn);
			lineDAO.DeleteByForeignKey("headerId");
			setDTOParameter(headerDTO);
			SQLModel sqlModel = modelProducer.getLocAssetsSaveModel();
			DBOperator.updateRecord(sqlModel, conn);
		} catch (Throwable ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex.getMessage());
		}
	}

	/**
	 * 功能：检查该地点是否有未归档工单
	 * @return boolean
	 * @throws QueryException
	 */
	public boolean hasPreviousOrder() throws QueryException {
		AmsAssetsCheckHeaderModel modelProducer = (AmsAssetsCheckHeaderModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getHasPreviousOrderModel();
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.executeQuery();
		return simp.hasResult();
	}
	/**
	 * 功能：发送手机短信给工单执行人
	 * @throws DataHandleException
	 */
	private void saveMessage() throws DataHandleException {
		AmsAssetsCheckHeaderDTO headerDTO = (AmsAssetsCheckHeaderDTO) dtoParameter;
		int userId = headerDTO.getImplementBy();
		String orderNum = headerDTO.getTransNo();
		String userName = headerDTO.getImplementUser();
		String userTel = "";
		try {
			MessageSaver msgSaver = new MessageSaver(conn);
			SQLModel sqlModel = new SQLModel();
			List strArg = new ArrayList();
			String strSql =
					"SELECT DISTINCT SU.MOVETEL FROM SF_USER SU WHERE SU.USER_ID = ?";
			strArg.add(userId);
			sqlModel.setSqlStr(strSql);
			sqlModel.setArgs(strArg);
			SimpleQuery sq = new SimpleQuery(sqlModel, conn);
			sq.executeQuery();
			userTel = String.valueOf(sq.getFirstRow().getValue("MOVETEL"));
			SfMsgDefineDTO msgDefineDTO = new SfMsgDefineDTO();
			msgDefineDTO.setMsgCategoryId(SMSConstant.ASSET_DIS_ID);
			msgDefineDTO.setCreatedBy(userAccount.getUserId());
			msgDefineDTO.setCellPhone(userTel);
			msgDefineDTO.setApplyNumber(orderNum);
			msgDefineDTO.setUserId(String.valueOf(userId));
			msgDefineDTO.setMsgContent(userName + "：工单：" + orderNum +
									   "需要您办理，请使用PDA下载。");
			msgSaver.saveMsg(msgDefineDTO);
		} catch (QueryException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (ContainerException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (Exception ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能：下发盘点工单
	 * @throws DataHandleException
	 */
	public void distributeChkOrder() throws DataHandleException {
		AmsAssetsCheckHeaderModel modelProducer = (AmsAssetsCheckHeaderModel)
												  sqlProducer;
		SQLModel sqlModel = modelProducer.getDistributeModel();
		DBOperator.updateRecord(sqlModel, conn);
	}

	/**
	 * 功能：提交盘点工单到审批人
	 * @return boolean
	 */
	public boolean submitOrder() {
		boolean operateResult = false;
		return operateResult;
	}

	/**
	 * 功能：获取盘点工单下的标签号
	 * @param includeAdded boolean 是否包含PDA扫描后，工单上传新加入的设备。
	 * @return List
	 * @throws QueryException
	 */
	public DTOSet getOrderBarcodes(boolean includeAdded) throws QueryException {
		AmsAssetsCheckHeaderModel modelProducer = (AmsAssetsCheckHeaderModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getOrderBarcodesModel(includeAdded);
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(AmsAssetsCheckLineDTO.class.getName());
		simp.executeQuery();
		return simp.getDTOSet();
	}

	/**
	 * 功能：根据父主键获取数据。
	 * @param foreignKey String
	 * @return Object DTOSet或者RowSet。获取后根据是否设置DTOClassName进行类型强制转换。
	 * @throws QueryException
	 */
	public Object getDataByForeignKey(String foreignKey) throws QueryException {
		Object retDatas = null;
		try {
			AmsAssetsCheckHeaderModel modelProducer = (AmsAssetsCheckHeaderModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getDataByForeignKeyModel(foreignKey);
			SimpleQuery splq = new SimpleQuery(sqlModel, conn);
			splq.setCalPattern(getCalPattern());
			if (!StrUtil.isEmpty(dtoClassName)) {
				splq.setDTOClassName(dtoClassName);
			}
			splq.executeQuery();
			if (!StrUtil.isEmpty(dtoClassName)) {
				retDatas = splq.getDTOSet();
				if(foreignKey.endsWith("batchId")){//针对批ID的外键数据获取进行进一步的补充
					DTOSet dtos = (DTOSet)retDatas;
					if(dtos != null && !dtos.isEmpty()){
						AmsAssetsCheckHeaderDTO chkOrder = null;
						AssetsOptProducer optProducer = new AssetsOptProducer(userAccount, conn);
						String chkCategoryOpt = "";
						for(int i = 0; i < dtos.getSize(); i++){
							chkOrder = (AmsAssetsCheckHeaderDTO)dtos.getDTO(i);
							chkCategoryOpt = optProducer.getChkCategoryOption(chkOrder.getCheckCategory());
							chkOrder.setCheckCategoryOpt(chkCategoryOpt);
						}
					}
				}
			} else {
				retDatas = splq.getSearchResult();
				if(foreignKey.endsWith("batchId")){//针对批ID的外键数据获取进行进一步的补充
					RowSet rows = (RowSet)retDatas;
					if(rows != null && !rows.isEmpty()){
						Row row = null;
						AssetsOptProducer optProducer = new AssetsOptProducer(userAccount, conn);
						String chkCategoryOpt = "";
						for(int i = 0; i < rows.getSize(); i++){
							row = rows.getRow(i);
							chkCategoryOpt = optProducer.getChkCategoryOption(row.getStrValue("CHECK_CATEGORY"));
							row.addField("CHECK_CATEGORY_OPT", chkCategoryOpt);
							rows.set(i, row);
						}
					}
				}
			}
		} catch (ContainerException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return retDatas;
	}


	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws com.sino.base.exception.WebFileDownException
	 */
	public File getExportFile() throws WebFileDownException {
		File file = null;
		AmsAssetsCheckHeaderModel modelProducer = null;
		try {
			modelProducer = (AmsAssetsCheckHeaderModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "盘点工单查询导出";
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
		fieldMap.put("TRANS_NO", "工单编号");
		fieldMap.put("COMPANY_NAME", "盘点公司");
		fieldMap.put("GROUPNAME", "下单组别");
		fieldMap.put("LOCATION_CODE", "地点代码");
		fieldMap.put("CHECK_LOCATION", "所在位置");
		fieldMap.put("CHECK_CATEGORY_NAME", "扫描设备专业");
		fieldMap.put("START_TIME", "开始日期");
		fieldMap.put("IMPLEMENT_USER", "执行人");
		fieldMap.put("IMPLEMENT_DAYS", "任务天数");
		fieldMap.put("ARCHIVED_USER", "归档人");
		fieldMap.put("ARCHIVED_DATE", "归档日期");
		fieldMap.put("ORDER_STATUS", "工单状态");
		return fieldMap;
	}
}
