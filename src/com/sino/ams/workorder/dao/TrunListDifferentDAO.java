package com.sino.ams.workorder.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.model.TrunListDifferentModel;
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
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Author: 	李轶
 * Date: 2009-6-3
 * Time: 11:34:55
 * Function	:		转资清单差异DAO
 */
public class TrunListDifferentDAO extends BaseDAO {
    public TrunListDifferentDAO(SfUserDTO userAccount, EtsWorkorderDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        user = userAccount;
        initSQLProducer(userAccount, dtoParameter);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
    	EtsWorkorderDTO ffDTO=(EtsWorkorderDTO) dtoParameter;
        super.sqlProducer = new TrunListDifferentModel((SfUserDTO)userAccount, ffDTO);
    }
    
    private SfUserDTO user = null;
    
    /**
	 * 功能：获取转资清单差异Excel文件
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File getExportFile(EtsWorkorderDTO dto) throws DataTransException {
		File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			String reportTitle = reportTitle = "转资清单差异";
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
			custData.setReportPerson(user.getUsername());
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

    private Map getFieldMap(EtsWorkorderDTO dto){
    	Map fieldMap = new HashMap();
		fieldMap.put("SEGMENT1", "项目编号");
        fieldMap.put("NAME", "项目名称");
        fieldMap.put("ORG_NAME", "公司");
        fieldMap.put("WORKORDER_OBJECT_CODE", "地点编号");
        fieldMap.put("WORKORDER_OBJECT_NAME", "地点简称");
        fieldMap.put("DIFFERENCE_REASON", "差异原因");
        fieldMap.put("WORKORDER_NO", "工单号");
        
        fieldMap.put("IMPLEMENT_USER", "执行人");
        fieldMap.put("CHECKOVER_USER", "归档人");
        
        fieldMap.put("BARCODE", "标签号");
        fieldMap.put("ITEM_NAME", "资产名称");
        fieldMap.put("ITEM_SPEC", "规格型号");
        fieldMap.put("ITEM_STATUS_NAME", "资产当前状态");
        fieldMap.put("SCAN_STATUS_NAME", "扫描状态");
        fieldMap.put("ITEM_QTY", "数量");
        fieldMap.put("ITEM_CATEGORY_DESC", "资产类别");
        fieldMap.put("DEPT_NAME", "责任部门");
        fieldMap.put("USER_NAME", "责任人");
        return fieldMap;
	}
    

}
