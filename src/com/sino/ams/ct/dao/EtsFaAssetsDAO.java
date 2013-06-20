package com.sino.ams.ct.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.ct.dto.EtsFaAssetsDTO;
import com.sino.ams.ct.dto.EtsItemInfoDTO;
import com.sino.ams.ct.model.EtsFaAssetsModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
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

public class EtsFaAssetsDAO extends AMSBaseDAO {

	private SfUserDTO sfUser = null;
	
	/**
	 * 功能：村通资产报废信息(EAM) ETS_FA_ASSETS 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsFaAssetsDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EtsFaAssetsDAO(SfUserDTO userAccount, EtsFaAssetsDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}
	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		EtsFaAssetsDTO dtoPara = (EtsFaAssetsDTO)dtoParameter;
		super.sqlProducer = new EtsFaAssetsModel((SfUserDTO)userAccount, dtoPara);
	}

	public File exportFile() throws DataTransException {
		
        File file = null;
        try {
            EtsItemInfoDTO etsItemInfoDto = (EtsItemInfoDTO) dtoParameter;
            if (etsItemInfoDto.getQryType().equals(WebAttrConstant.BY_DAIWEI)) {
                SQLModel sqlModel = sqlProducer.getPageQueryModel();
                TransRule rule = new TransRule();
                rule.setDataSource(sqlModel);
                rule.setCalPattern(CalendarConstant.LINE_PATTERN);
                rule.setSourceConn(conn);
                String fileName = "村通资产报废信息.xls";
                String filePath = WorldConstant.USER_HOME;
                filePath += WorldConstant.FILE_SEPARATOR;
                filePath += fileName;
                rule.setTarFile(filePath);
                DataRange range = new DataRange();
                rule.setDataRange(range);

                Map fieldMap = new HashMap();
                //fieldMap.put("ORG_NAME", "公司");
                fieldMap.put("TAG_NUMBER", "资产标签号");
                fieldMap.put("ASSET_NUMBER", "资产编号");
                fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
                fieldMap.put("MODEL_NUMBER", "资产型号");
                fieldMap.put("ORIGINAL_COST", "资产原值");
                fieldMap.put("DATEPLACED_IN_SERVICE", "启用日期");
                fieldMap.put("DEPRN_COST", "净值");
                fieldMap.put("RESPONSIBILITY_USER", "责任人");
                fieldMap.put("RESPONSIBILITY_DEPT", "责任部门");
                fieldMap.put("RETIRE_DATE", "报废日期");

                rule.setFieldMap(fieldMap);

                CustomTransData custData = new CustomTransData();
                custData.setReportTitle("村通资产报废信息");
                custData.setReportPerson(sfUser.getUsername());
                custData.setNeedReportDate(true);
                rule.setCustData(custData);
                TransferFactory factory = new TransferFactory();
                DataTransfer transfer = factory.getTransfer(rule);
                transfer.transData();
                file = (File) transfer.getTransResult();
            } else {
            	
                SQLModel sqlModel = sqlProducer.getPageQueryModel();
                TransRule rule = new TransRule();
                rule.setDataSource(sqlModel);
                rule.setCalPattern(CalendarConstant.LINE_PATTERN);
                rule.setSourceConn(conn);
                String fileName = "村通资产报废信息.xls";
                String filePath = WorldConstant.USER_HOME;
                filePath += WorldConstant.FILE_SEPARATOR;
                filePath += fileName;
                rule.setTarFile(filePath);
                DataRange range = new DataRange();
                rule.setDataRange(range);
                
                Map fieldMap = new HashMap();
                //fieldMap.put("ORG_NAME", "公司");
                fieldMap.put("TAG_NUMBER", "资产标签号");
                fieldMap.put("ASSET_NUMBER", "资产编号");
                fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
                fieldMap.put("MODEL_NUMBER", "资产型号");
                fieldMap.put("ORIGINAL_COST", "资产原值");
                fieldMap.put("DATEPLACED_IN_SERVICE", "启用日期");
                fieldMap.put("DEPRN_COST", "净值");
                fieldMap.put("RESPONSIBILITY_USER", "责任人");
                fieldMap.put("RESPONSIBILITY_DEPT", "责任部门");
                fieldMap.put("RETIRE_DATE", "报废日期");

                rule.setFieldMap(fieldMap);
                
                CustomTransData custData = new CustomTransData();
                custData.setReportTitle("村通资产报废信息");
                custData.setReportPerson(sfUser.getUsername());
                custData.setNeedReportDate(true);
                rule.setCustData(custData);
                TransferFactory factory = new TransferFactory();
                DataTransfer transfer = factory.getTransfer(rule);
                transfer.transData();
                file = (File) transfer.getTransResult();
            }
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataTransException(ex);
        }
        return file;
    }
}
