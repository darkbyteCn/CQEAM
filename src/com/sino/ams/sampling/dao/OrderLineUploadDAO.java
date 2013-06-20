package com.sino.ams.sampling.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.sampling.constant.SamplingDicts;
import com.sino.ams.sampling.dto.AmsAssetsSamplingLineDTO;
import com.sino.ams.sampling.model.OrderLineUploadModel;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.SQLModelException;
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
public class OrderLineUploadDAO extends AMSBaseDAO {

	public OrderLineUploadDAO(BaseUserDTO userAccount, AmsAssetsSamplingLineDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsAssetsSamplingLineDTO dto = (AmsAssetsSamplingLineDTO)dtoParameter;
		sqlProducer = new OrderLineUploadModel(userAccount, dto);
	}

	/**
	 * 功能：上载设备。
	 * <B>如果设备存在于该工单下发时的数据中，则直接更新上载类字段信息</B>
	 * <B>如果设备不存在于该工单下发时的数据中，插入到该工单的行信息中，且设置其SYSTEM_STATUS值为N；BATCH_ID值为空</B>
	 * @param itemExist boolean 当前上载设备是否存在于该工单下发时的数据中
	 * @throws DataHandleException
	 */
	public void uploadOrderLine(boolean itemExist) throws DataHandleException {
		try {
			AmsAssetsSamplingLineDTO dto = (AmsAssetsSamplingLineDTO)dtoParameter;

			dto.setScanItemCode(dto.getItemCode());
			dto.setScanItemCategory(dto.getItemCategory());
			dto.setScanItemName(dto.getItemName());
			dto.setScanItemSpec(dto.getItemSpec());
			dto.setScanResponsibilityUser(dto.getResponsibilityUser());
			dto.setScanResponsibilityDept(dto.getResponsibilityDept());
			dto.setScanMaintainUser(dto.getMaintainUser());
			dto.setScanStartDate(dto.getStartDate().toString()); //启用日期


			if(itemExist){//扫描设备为本单据下发设备
				if (StrUtil.isEmpty(dto.getItemCode())) { //表示新增加的设备分类，需要插入专业，名称，型号等
					dto.setRemark("PDA创建设备分类");
				}
			} else {//扫描设备不是本单据下发设备
				dto.setSystemStatus(SamplingDicts.STATUS_NO);
				if (StrUtil.isEmpty(dto.getItemCode())) { //表示新增加的设备分类，需要插入专业，名称，型号等
					dto.setRemark("PDA创建该条码，并创建设备分类");
				} else {
					dto.setRemark("PDA创建该条码");
				}
			}
			setDTOParameter(dto);
			OrderLineUploadModel modelProducer = (OrderLineUploadModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getLineUploadModel(itemExist);
			DBOperator.updateRecord(sqlModel, conn);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
	}

	/**
	 * 功能：设置PDA未扫描到的设备的扫描状态为“N”。
	 * <B>该方法需要在对扫描到的设备处理后再进行处理</B>
	 * @throws DataHandleException
	 */
	public void updateLeftBarcodes() throws DataHandleException {
		OrderLineUploadModel modelProducer = (OrderLineUploadModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getLeftBarcodesUpdateModel();
		DBOperator.updateRecord(sqlModel, conn);
	}
}
