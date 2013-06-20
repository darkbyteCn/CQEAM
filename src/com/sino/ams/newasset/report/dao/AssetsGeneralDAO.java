package com.sino.ams.newasset.report.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.report.dto.AssetsGeneralDTO;
import com.sino.ams.newasset.report.model.AssetsGeneralModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-13
 * Time: 15:28:36
 * To change this template use File | Settings | File Templates.
 */
public class AssetsGeneralDAO extends AMSBaseDAO {

	public AssetsGeneralDAO(SfUserDTO userAccount, AssetsGeneralDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 *
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		AssetsGeneralDTO dto = (AssetsGeneralDTO) dtoParameter;
		sqlProducer = new AssetsGeneralModel(user, dto);
	}

    public RowSet getGenVerReportData(AssetsGeneralDTO dto) throws QueryException {
		RowSet rows = null;
		try {
			AssetsGeneralModel modelProducer = (AssetsGeneralModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getGenVerCheckModel(dto);
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			rows = simp.getSearchResult();
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return rows;
	}

    /**
	 * 功能：总账模块和资产模块数据准确率/固定资产回报率/固定资产周转率(明细信息导出)
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File getDetialExportFile(AssetsGeneralDTO dto) throws DataTransException {
		File file = null;
		try {
            AssetsGeneralModel modelProducer = (AssetsGeneralModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getGenVerCheckModel(dto);
            String reportTitle = "";
            if (dto.getManagerGuideType().equals("DATA_NICETY")) {
                reportTitle = "总账模块和资产模块数据准确率明细报表";
            } else if (dto.getManagerGuideType().equals("ASSETS_REDOUND")) {
                reportTitle = "固定资产回报率明细报表";
            } else if (dto.getManagerGuideType().equals("ASSETS_TURNOVER")) {
                reportTitle = "固定资产周转率明细报表";
            }
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

    /**
	 * 功能：总账模块和资产模块数据准确率/固定资产回报率/固定资产周转率
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File getExportFile(AssetsGeneralDTO dto) throws DataTransException {
		File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
            String reportTitle = "";
            if (dto.getManagerGuideType().equals("DATA_NICETY")) {
                reportTitle = "总账模块和资产模块数据准确率统计报表";
            } else if (dto.getManagerGuideType().equals("ASSETS_REDOUND")) {
                reportTitle = "固定资产回报率统计报表";
            } else if (dto.getManagerGuideType().equals("ASSETS_TURNOVER")) {
                reportTitle = "固定资产周转率统计报表";
            }
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

    private Map getFieldMap(AssetsGeneralDTO dto){
		Map fieldMap = new HashMap();
        if (dto.getManagerGuideType().equals("DATA_NICETY")) {
            fieldMap.put("COMPANY", "公司");
            fieldMap.put("PERIOD", "会计期间");
            fieldMap.put("ASSETS_RATE", "准确率");
        } else if(dto.getManagerGuideType().equals("ASSETS_REDOUND")) {
            fieldMap.put("COMPANY", "公司");
            fieldMap.put("PERIOD", "会计期间");
            fieldMap.put("CURRENT_AMOUNT", "当前净利润");
            fieldMap.put("BEGIN_AMOUNT", "期初总固定资产净额");
            fieldMap.put("END_AMOUNT", "期末总固定资产净额");
            fieldMap.put("ASSETS_RATE", "固定资产回报率");
        } else if (dto.getManagerGuideType().equals("ASSETS_TURNOVER")) {
            fieldMap.put("COMPANY", "公司");
            fieldMap.put("PERIOD", "会计期间");
            fieldMap.put("CURRENT_AMOUNT", "当期营业收入");
            fieldMap.put("BEGIN_AMOUNT", "期初总固定资产净额");
            fieldMap.put("END_AMOUNT", "期末总固定资产净额");
            fieldMap.put("ASSETS_RATE", "固定资产周转率");
        }
        return fieldMap;
	}
}
