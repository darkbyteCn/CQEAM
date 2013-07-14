package com.sino.ams.yearchecktaskmanager.dao;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckProGressStatiDTO;
import com.sino.ams.yearchecktaskmanager.model.AssetsYearCheckProGressStatiModel;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.base.web.DatabaseForWeb;
import com.sino.framework.dto.BaseUserDTO;

public class AssetsYearCheckProGressStatiDAO extends AMSBaseDAO {
	

	private AssetsYearCheckProGressStatiDTO dto ;

	public AssetsYearCheckProGressStatiDAO(SfUserDTO userAccount,AssetsYearCheckProGressStatiDTO dtoParameter,Connection conn){
		super(userAccount, dtoParameter, conn);
		dto  = dtoParameter;
		
	}
	protected void initSQLProducer(BaseUserDTO arg0, DTO arg1) {
		AssetsYearCheckProGressStatiDTO dtoPara = (AssetsYearCheckProGressStatiDTO) arg1;
		super.sqlProducer = new AssetsYearCheckProGressStatiModel((SfUserDTO) arg0,dtoPara);

	}
	  //默认必选
    public String getOrgnizationOption(String selectValue, boolean andBlank) throws QueryException {
        boolean addBlank = false;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT EOCM.ORGANIZATION_ID,\n" +
                        "       EOCM.COMPANY_CODE || STR_REPLACE(EOCM.COMPANY, 'OU_', ' ')\n" +
                        "  FROM  ETS_OU_CITY_MAP EOCM\n" +
                        " WHERE EOCM.ENABLED = 'Y' and IS_TD='N'\n";

        if (!(userAccount.isProvinceUser() || userAccount.isProvAssetsManager() || userAccount.isSysAdmin())) {//如果不是全省资产管理员，只能看到本地的
			sqlStr += " AND EOCM.ORGANIZATION_ID = ?";
			sqlArgs.add(userAccount.getOrganizationId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
			return webFieldProducer.getOptionHtml(StrUtil.nullToString(selectValue), true);
		} else {
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
			return webFieldProducer.getOptionHtml(StrUtil.nullToString(selectValue), true);
		}
    }
    
    
    /**
	  * excel 导出
	  * @return
	  * @throws DataTransException
	  * @throws SQLModelException
	  */
	 public File getExportFile() throws DataTransException, SQLModelException,Exception {
		 
		 AssetsYearCheckProGressStatiModel modelProducer = new AssetsYearCheckProGressStatiModel(userAccount, dto);
	        SQLModel sqlModel = modelProducer.getPageQueryModel();
	        String reportTitle = "年度盘点进度统计报表";
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
	        rule.setFieldMap(getFieldMap());
	        CustomTransData custData = new CustomTransData();
	        custData.setReportTitle(reportTitle);
	        custData.setReportPerson(userAccount.getUsername());
	        custData.setNeedReportDate(true);
	        rule.setCustData(custData);
	        rule.setCalPattern(CAL_PATT_50);
	        TransferFactory factory = new TransferFactory();
	        DataTransfer transfer = factory.getTransfer(rule);
	        transfer.transData();
	        return (File) transfer.getTransResult();
	 }
	 
	 @SuppressWarnings("unchecked")
	private Map getFieldMap() {
	        Map fieldMap = new HashMap();
	        fieldMap.put("COMPANY", "公司");
	        fieldMap.put("RESP_DEPT_NAME", "部门名称");
	        fieldMap.put("SUM_ASSETS", "资产总量");
	        fieldMap.put("SUM_CHECK_ASSETS", "已盘点资产总量");
	        fieldMap.put("PERCENT_BY_ASSETS", "完成百分比（按照资产）");
	        fieldMap.put("SUM_WORKORDER_OBJECT_CODE", "地点总量");
	        fieldMap.put("SUM_CHECK_WORKORDER_OBJECT_CODE", "已盘点地点总量");
	        fieldMap.put("PERCENT_BY_OBJECT", "完成百分比(按照地点)");
	        return fieldMap;
	    }
}
