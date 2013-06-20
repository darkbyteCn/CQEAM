package com.sino.ams.ct.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.ct.dto.EtsItemInfoDTO;
import com.sino.ams.ct.model.EtsItemInfoModel;
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

/**
 * <p>Title: EtsItemInfoDAO</p>
 * <p>Description:程序自动生成服务程序“EtsItemInfoDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 于士博
 * @version 1.0
 */
public class EtsItemInfoDAO extends AMSBaseDAO {
	
	private SfUserDTO sfUser = null;

	/**
	 * 功能：标签号信息(EAM) ETS_ITEM_INFO 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsItemInfoDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EtsItemInfoDAO(SfUserDTO userAccount, EtsItemInfoDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		EtsItemInfoDTO dtoPara = (EtsItemInfoDTO)dtoParameter;
		super.sqlProducer = new EtsItemInfoModel((SfUserDTO)userAccount, dtoPara);
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
                String fileName = "村通设备信息.xls";
                String filePath = WorldConstant.USER_HOME;
                filePath += WorldConstant.FILE_SEPARATOR;
                filePath += fileName;
                rule.setTarFile(filePath);
                DataRange range = new DataRange();
                rule.setDataRange(range);

                Map fieldMap = new HashMap();
//            fieldMap.put("ORG_NAME", "公司");
                fieldMap.put("BARCODE", "条码");
                fieldMap.put("ITEM_NAME", "设备名称");
                fieldMap.put("ITEM_SPEC", "规格型号");
                fieldMap.put("WORKORDER_OBJECT_CODE", "地点编号");
                fieldMap.put("WORKORDER_OBJECT_LOCATION", "所在地点");
                fieldMap.put("NAME", "代维公司");

                rule.setFieldMap(fieldMap);

                CustomTransData custData = new CustomTransData();
                custData.setReportTitle("村通设备信息");
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
                String fileName = "村通设备信息.xls";
                String filePath = WorldConstant.USER_HOME;
                filePath += WorldConstant.FILE_SEPARATOR;
                filePath += fileName;
                rule.setTarFile(filePath);
                DataRange range = new DataRange();
                rule.setDataRange(range);
                
                Map fieldMap = new HashMap();
                //fieldMap.put("ORG_NAME", "公司");
                fieldMap.put("BARCODE", "条码");
                fieldMap.put("ITEM_NAME", "设备名称");
                fieldMap.put("ITEM_SPEC", "规格型号");
                fieldMap.put("ITEM_CATEGORY", "设备分类");
                fieldMap.put("START_DATE", "启用日期");
                fieldMap.put("PROJECT_NAME", "所属工程");
                fieldMap.put("WORKORDER_OBJECT_NAME", "所属地点");
                fieldMap.put("COUNTY_NAME", "所属区县");

                rule.setFieldMap(fieldMap);
                
                CustomTransData custData = new CustomTransData();
                custData.setReportTitle("村通设备信息");
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
