package com.sino.ams.newasset.report.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.newasset.report.dto.AssetsInDataReportDTO;
import com.sino.ams.newasset.report.model.AssetsInDataReportModel;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.data.RowSet;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.DataTransException;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.datatrans.*;
import com.sino.base.constant.WorldConstant;

import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-16
 * Time: 11:26:47
 * To change this template use File | Settings | File Templates.
 */
public class AssetsInDataReportDAO extends AMSBaseDAO {

	public AssetsInDataReportDAO(SfUserDTO userAccount, AssetsInDataReportDTO dtoParameter, Connection conn) {
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
		AssetsInDataReportDTO dto = (AssetsInDataReportDTO) dtoParameter;
		sqlProducer = new AssetsInDataReportModel(user, dto);
	}

    /**
	 * 功能：总账模块和资产模块数据准确率/固定资产回报率/固定资产周转率
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File getExportFile(AssetsInDataReportDTO dto) throws DataTransException {
		File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
            String reportTitle = "";
            if (dto.getManagerGuideType().equals("TRUN_RATE")) {
                reportTitle = "转资率报表";
            } else if (dto.getManagerGuideType().equals("IN_TIME_RATE")) {
                reportTitle = "决策分析报表上报及时率报表";
            } else if (dto.getManagerGuideType().equals("NICETY_RATE")) {
                reportTitle = "转资信息准确率报表";
            } else if (dto.getManagerGuideType().equals("CHECK_RATE")) {
                reportTitle = "抽查任务完成率报表";
            } else if (dto.getManagerGuideType().equals("MATCH_CASE_RATE")) {
                reportTitle = "抽查盘点资产账实相符率报表";
            } else if (dto.getManagerGuideType().equals("COP_RATE")) {
                reportTitle = "日常巡检资产盘点完成率报表";
            } else if (dto.getManagerGuideType().equals("COP_MATCH_RATE")) {
                reportTitle = "日常巡检资产盘点账实相符率报表";
            } else if (dto.getManagerGuideType().equals("ACCOUNTING_ACCURATE")) {
                reportTitle = "资产核算准确率报表";
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

    private Map getFieldMap(AssetsInDataReportDTO dto){
		Map fieldMap = new HashMap();
        if (dto.getManagerGuideType().equals("TRUN_RATE")) {
            fieldMap.put("COMPANY", "公司");
            fieldMap.put("PERIOD", "会计期间");
            fieldMap.put("PROJECT_TRUN_ASSETS", "考核期内工程转资额");
            fieldMap.put("PROJECT_SUM_ASSETS", "工程累计投入总额");
            fieldMap.put("ASSETS_RATE", "转资率");
        } else if(dto.getManagerGuideType().equals("IN_TIME_RATE")) {
            fieldMap.put("PERIOD", "会计期间");
            fieldMap.put("NO_TIMELY_REPORT_NUM", "未及时上报次数");
            fieldMap.put("ASSETSMENT_REPORT_NUM", "考核期应上报次数");
            fieldMap.put("ASSETS_RATE", "决策分析报表上报及时率");
        } else if (dto.getManagerGuideType().equals("NICETY_RATE")) {
            fieldMap.put("PERIOD", "会计期间");
            fieldMap.put("ASSETSMENT_FALSE_NUM", "考核期内发生的转资资产不准确的数量");
            fieldMap.put("ASSETSMENT_ASSETS_SUM", "考核期内转资资产总量");
            fieldMap.put("ASSETS_RATE", "转资信息准确率");
        } else if (dto.getManagerGuideType().equals("CHECK_RATE")) {
            fieldMap.put("COMPANY", "公司");
            fieldMap.put("PERIOD", "会计期间");
            fieldMap.put("COMPLETE_CHECK_NUM", "抽查盘点任务工单数量");
            fieldMap.put("PLAN_CHECK_NUM", "计划规定的抽查盘点任务工单总数");
            fieldMap.put("ASSETS_RATE", "抽查任务完成率");
        } else if (dto.getManagerGuideType().equals("MATCH_CASE_RATE")) {
            fieldMap.put("COMPANY", "公司");
            fieldMap.put("PERIOD", "会计期间");
            fieldMap.put("ACCOUNT_MATCH_CASE", "抽查中账实相符的资产数量");
            fieldMap.put("CHECK_ASSETS_SUM", "抽查资产总数量");
            fieldMap.put("ASSETS_RATE", "抽查盘点资产账实相符率");
        } else if (dto.getManagerGuideType().equals("COP_RATE")) {
            fieldMap.put("COMPANY", "公司");
            fieldMap.put("PERIOD", "会计期间");
            fieldMap.put("ASSETS_COP_NUM", "已完成的日常巡检盘点的工单数");
            fieldMap.put("ASSETS_COM_SUM", "计划的日常巡检盘点工单总数");
            fieldMap.put("ASSETS_RATE", "日常巡检资产盘点完成率");
        } else if (dto.getManagerGuideType().equals("COP_MATCH_RATE")) {
            fieldMap.put("COMPANY", "公司");
            fieldMap.put("PERIOD", "会计期间");
            fieldMap.put("ASSETS_MATCH_CASE", "盘点中账实相符的资产数量");
            fieldMap.put("ASSETS_CHECK_SUM", "盘点资产总数量");
            fieldMap.put("ASSETS_RATE", "日常巡检资产盘点账实相符率");
        } else if (dto.getManagerGuideType().equals("ACCOUNTING_ACCURATE")) {
            fieldMap.put("COMPANY", "公司");
            fieldMap.put("PERIOD", "会计期间");
            fieldMap.put("ACCURATE_ERROR_NUMBER", "资产核算相关的差错次数");
        }
        return fieldMap;
	}
}
