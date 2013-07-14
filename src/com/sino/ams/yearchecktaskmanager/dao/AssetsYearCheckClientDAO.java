package com.sino.ams.yearchecktaskmanager.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newSite.dto.EamAddressAddHDTO;
import com.sino.ams.newSite.model.EamAddressSecondAddHModel;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.model.AssetsConfirmModel;
import com.sino.ams.newasset.model.EtsFaAssetsModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.ZeroTurnHeaderDTO;
import com.sino.ams.workorder.model.ZeroTurnModel;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearClientDTO;
import com.sino.ams.yearchecktaskmanager.dto.EtsItemYearCheckDTO;
import com.sino.ams.yearchecktaskmanager.dto.EtsItemYearCheckLineDTO;
import com.sino.ams.yearchecktaskmanager.model.AssetsYearCheckClientModel;
import com.sino.ams.yearchecktaskmanager.model.AssetsYearCheckFaModel;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

public class AssetsYearCheckClientDAO extends AMSBaseDAO {

	public AssetsYearCheckClientDAO(SfUserDTO userAccount,
			AssetsYearClientDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	@Override
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AssetsYearClientDTO dto = (AssetsYearClientDTO) dtoParameter;
		sqlProducer = new AssetsYearCheckClientModel((SfUserDTO) userAccount,
				dto);
	}

	// jeffery
	public void conClient(String str) {
		try {
			String dot[] = str.split(",");
			for (int i = 0; i < dot.length; i++) {
				String barcode = dot[i];
				if (!barcode.equals("")) {
					AssetsYearCheckClientModel model = (AssetsYearCheckClientModel) sqlProducer;
					SQLModel sqlModel = model.getClientModel(barcode);
					DBOperator.updateRecord(sqlModel, conn);
				}
			}
		} catch (DataHandleException e) {
			e.printStackTrace();
		}
	}

	public File exportClientFile(SfUserDTO user, AssetsYearClientDTO dto,
			Connection conn) throws DataTransException, SQLModelException {
		File file = null;
		AssetsYearCheckClientModel assetsModel = (AssetsYearCheckClientModel) sqlProducer;
		SQLModel sqlModel = assetsModel.getExportModel();
		String reportTitle = "";
		reportTitle = "客户端资产确认";
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

		fieldMap.put("BARCODE", "资产标签号");
		fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
		fieldMap.put("FA_CATEGORY1", "应用领域描述");
		fieldMap.put("WORKORDER_OBJECT_CODE", "地点编码");
		fieldMap.put("WORKORDER_OBJECT_NAME", "地点描述");
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
		return file;
	}

	public void svaeTmp(DTOSet dtoSet) {
		try {
			if (dtoSet == null) {
				// do nothing
			} else {
				AssetsYearCheckClientModel model2 = (AssetsYearCheckClientModel) sqlProducer;
				SQLModel sqlModel2 = model2.getClientDelModel();
				DBOperator.updateRecord(sqlModel2, conn);
				for (int i = 0; i < dtoSet.getSize(); i++) {
					AssetsYearClientDTO dto = (AssetsYearClientDTO) dtoSet
							.getDTO(i);
					AssetsYearCheckClientModel model = (AssetsYearCheckClientModel) sqlProducer;
					SQLModel sqlModel = model.getClientImpModel(dto);
					DBOperator.updateRecord(sqlModel, conn);
				}
			}
		} catch (DataHandleException e1) {
			e1.printStackTrace();
		}

	}

}
