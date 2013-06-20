package com.sino.ams.newasset.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.freeflow.AmsAssetsFreeDTO;
import com.sino.ams.newasset.model.AmsAssetsRentModel;
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

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 2009-4-13
 * Time: 17:03:08
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsRentDAO extends AMSBaseDAO {
    public AmsAssetsRentDAO(BaseUserDTO userAccount, AmsAssetsFreeDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsFreeDTO ffDTO=(AmsAssetsFreeDTO) dtoParameter;
        super.sqlProducer = new AmsAssetsRentModel((SfUserDTO)userAccount, ffDTO);
    }
    
    /**
	 * 功能：获取闲置资产应用领域统计Excel文件
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File getExportFile(AmsAssetsFreeDTO dto) throws DataTransException {
		File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			String reportTitle = "出租资产查询";
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
			rule.setFieldMap(getFieldMap(dto));
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
			throw new DataTransException(ex);
		}
		return file;
	}

    private Map getFieldMap(AmsAssetsFreeDTO dto){
		Map fieldMap = new HashMap();
        fieldMap.put("COMPANY", "公司");
        fieldMap.put("ITEM_NAME", "资产名称");
        fieldMap.put("ITEM_SPEC", "规格型号");
        fieldMap.put("ASSET_ID", "资产编号");
        fieldMap.put("BARCODE", "资产标签号");
        fieldMap.put("CURRENT_UNITS", "数量");
        fieldMap.put("COST", "价值");
        return fieldMap;
	}
}
