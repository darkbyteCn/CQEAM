package com.sino.ams.sampling.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.sampling.constant.SamplingDicts;
import com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO;
import com.sino.ams.sampling.model.SampOrderPDACreateModel;
import com.sino.base.data.Row;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.XMLParseException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.pda.dao.OrderCreateDAO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class SampOrderPDACreateDAO extends OrderCreateDAO {

	public SampOrderPDACreateDAO(Connection conn, FilterConfigDTO filterConfig, String filePath) {
		super(conn, filterConfig, filePath);
	}

	/**
	 * 功能：穿件工单批
	 * @throws DataHandleException
	 */
	protected void createOrderBatch() throws DataHandleException {
		SampOrderPDACreateModel modelProducer = (SampOrderPDACreateModel)sqlProducer;
		SQLModel sqlModel = modelProducer.getOrderBatchCreateModel();
		DBOperator.updateRecord(sqlModel, conn);
	}

	/**
	 * 功能：创建工单头
	 *
	 * @throws DataHandleException
	 */
	protected void createOrderHeader() throws DataHandleException {
		SampOrderPDACreateModel modelProducer = (SampOrderPDACreateModel)sqlProducer;
		SQLModel sqlModel = modelProducer.getOrderHeaderCreateModel();
		DBOperator.updateRecord(sqlModel, conn);
	}

	/**
	 * 功能：创建工单设备行
	 * @throws DataHandleException
	 */
	protected void createOrderLine() throws DataHandleException {
		try {
			SampOrderPDACreateModel modelProducer = (SampOrderPDACreateModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getOrderLineCreateModel();
			DBOperator.updateRecord(sqlModel, conn);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能：判断指定地点点是否已经存在未归档工单。
	 * <B>对于盘点工单，需要人工归档</B>
	 * <B>对于抽查工单，工单上载时则自动归档</B>
	 * @return boolean
	 */
	public boolean hasPreviousOrder() {
		boolean hasPreviousOrder = false;
		try {
			SampOrderPDACreateModel modelProducer = (SampOrderPDACreateModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getHasPreviousOrderModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			hasPreviousOrder = simp.hasResult();
		} catch (QueryException ex) {
			ex.printLog();
		}
		return hasPreviousOrder;
	}

	/**
	 * 功能：由传入的XML文件解析后初始化相关程序
	 * @throws XMLParseException
	 */
	protected void initOrderData() throws XMLParseException {
		try {
			SampOrderPDAXMLParser chkParser = new SampOrderPDAXMLParser();
			chkParser.parseXML(filterConfig, conn, filePath);
			orderParameter = chkParser.getOrder();
			AmsAssetsSamplingHeaderDTO order = (AmsAssetsSamplingHeaderDTO)orderParameter;
			order.setBatchRemark("PDA创建抽查工单");
			userAccount = chkParser.getCreatedUser();
			sqlProducer = new SampOrderPDACreateModel(userAccount, order);
			setOrderData(order);
			appendOrderNo();
			setOrderData(order);
			appendAllIds();
			setOrderData(order);
		} catch (QueryException ex) {
			ex.printLog();
			throw new XMLParseException(ex);
		} catch (SQLException ex) {
			Logger.logError(ex);
			throw new XMLParseException(ex);
		}
	}

	private void appendOrderNo() throws SQLException {
		String companyCode = userAccount.getCompanyCode();
		String orderType = SamplingDicts.ORDR_NO_MARK;
		OrderNumGenerator orderPrd = new OrderNumGenerator(conn, companyCode, orderType);
		AmsAssetsSamplingHeaderDTO order = (AmsAssetsSamplingHeaderDTO)orderParameter;
		orderNo = orderPrd.getOrderNum();
		order.setOrderNo(orderNo);
		orderPrd.setType(SamplingDicts.BATH_NO_MARK);
		orderPrd.setOrderLength(3);
		order.setBatchNo(orderPrd.getOrderNum());
		setOrderData(order);
	}

	private void appendAllIds() throws QueryException {
		try {
			SampOrderPDACreateModel modelProducer = (SampOrderPDACreateModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getOtherDataModel();
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				Row row = simp.getFirstRow();
				String data = row.getStrValue("ORGANIZATION_ID");
				AmsAssetsSamplingHeaderDTO order = (AmsAssetsSamplingHeaderDTO)orderParameter;
				order.setOrganizationId(StrUtil.strToInt(data));
				data = row.getStrValue("BATCH_ID");
				order.setBatchId(data);
				data = row.getStrValue("HEADER_ID");
				order.setHeaderId(data);
				data = row.getStrValue("TASK_ID");
				order.setTaskId(data);
				setOrderData(order);
			}
		} catch (ContainerException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
	}
}
