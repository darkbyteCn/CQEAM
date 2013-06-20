package com.sino.ams.match.amselementmatch.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.match.amselementmatch.dto.AmsElementMatchDTO;
import com.sino.ams.match.amselementmatch.model.AmsElementMatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.framework.dto.BaseUserDTO;

public class AmsElementMatchDAO extends AMSBaseDAO {
	/**
	 * 
	 * Title: 			SinoApplication
	 * @param userAccount		SfUserDTO  代表本系统的最终操作用户对象
	 * @param dtoParameter		LabelDTO   装在本次操作的数据对象
	 * @param conn		Connection  数据库连接,有调用者传入
	 * Function			资产目录与网络元素对应关系维护 数据访问层构造函数
	 * @author 			李轶
	 * @version 		0.1
	 * @Date			Apr 30, 2009
	 */
	public AmsElementMatchDAO(SfUserDTO userAccount, AmsElementMatchDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		amsElementMatchModel = new AmsElementMatchModel((SfUserDTO) userAccount, dtoParameter);
	}
	
	private AmsElementMatchModel amsElementMatchModel = null;	

	/**
	 * 
	 * Title: 			SinoApplication
	 * @param userAccount		SfUserDTO  代表本系统的最终操作用户对象
	 * @param dtoParameter		LabelDTO   装在本次操作的数据对象
	 * @param conn		Connection  数据库连接,有调用者传入
	 * Function			SQL生成器BaseSQLProducer的初始化。
	 * @author 			李轶
	 * @version 		0.1
	 * @Date			Apr 26, 2009
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		AmsElementMatchDTO dtoPara = (AmsElementMatchDTO)dtoParameter;
		sqlProducer = new AmsElementMatchModel(userAccount, dtoPara);
	}
	
	
	/**
	 * 功能：导出Excel文件。
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 *
	 */
	public File exportFile() throws DataTransException {     
		File file = null;
		String accessType = ((AmsElementMatchDTO)dtoParameter).getAccessType();
		AmsElementMatchModel  amsElementMatchModel =(AmsElementMatchModel)sqlProducer;
			SQLModel sqlModel = amsElementMatchModel.getPageQueryModel();
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setCalPattern(CalendarConstant.LINE_PATTERN);
			rule.setSourceConn(conn);
			String fileName = "";
			if("lne".equals(accessType)){
				fileName = "资产目录与逻辑网络元素对应关系.xls";
			}else if("cex".equals(accessType)){
				fileName = "资产目录与投资分类对应关系.xls";
			}else if("ope".equals(accessType)){
				fileName = "资产目录与业务平台对应关系.xls";
			}else if("nle".equals(accessType)){
				fileName = "资产目录与网络层次对应关系.xls";
			}
			
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);

			Map fieldMap = new HashMap();
			CustomTransData custData = new CustomTransData();
			
			if("lne".equals(accessType)){
				fieldMap.put("CONTENT_ID", "资产目录ID");
				fieldMap.put("CONTENT_CODE", "资产目录代码");
				fieldMap.put("CONTENT_NAME", "资产目录描述");
				fieldMap.put("AMS_LNE_ID", "逻辑网络元素编号");
				fieldMap.put("NET_CATEGORY1", "网络专业1");
				fieldMap.put("NET_CATEGORY2", "网络专业2");
				fieldMap.put("NET_UNIT_CODE", "网元编码");
				fieldMap.put("LOG_NET_ELE", "逻辑网络元素");
				
				custData.setReportTitle("资产目录与逻辑网络元素对应关系维护");
			}else if("cex".equals(accessType)){
				fieldMap.put("CONTENT_ID", "资产目录ID");
				fieldMap.put("CONTENT_CODE", "资产目录代码");
				fieldMap.put("CONTENT_NAME", "资产目录描述");
				fieldMap.put("AMS_CEX_ID", "投资分类属性ID");
				fieldMap.put("INVEST_CATEGORY1", "投资大类");
				fieldMap.put("INVEST_CATEGORY2", "投资种类");
				fieldMap.put("INVEST_CAT_CODE", "投资分类代码");
				fieldMap.put("INVEST_CAT_NAME", "投资分类名称");
				
				custData.setReportTitle("资产目录与投资分类对应关系维护");
			}else if("ope".equals(accessType)){
				fieldMap.put("CONTENT_ID", "资产目录ID");
				fieldMap.put("CONTENT_CODE", "资产目录代码");
				fieldMap.put("CONTENT_NAME", "资产目录描述");
				fieldMap.put("AMS_OPE_ID", "业务平台属性ID");
				fieldMap.put("OPE_CODE", "业务平台编码");
				fieldMap.put("OPE_NAME", "业务平台名称");
				
				custData.setReportTitle("资产目录与业务平台对应关系维护");
			}else if("nle".equals(accessType)){
				fieldMap.put("CONTENT_ID", "资产目录ID");
				fieldMap.put("CONTENT_CODE", "资产目录代码");
				fieldMap.put("CONTENT_NAME", "资产目录描述");
				fieldMap.put("LNE_CODE", "网络层次编码");
				fieldMap.put("LNE_NAME", "网络层次名称");
				
				custData.setReportTitle("资产目录与网络层次属性对应关系维护");
			}
			
			rule.setFieldMap(fieldMap);

			custData.setReportPerson(userAccount.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			TransferFactory factory = new TransferFactory();
			DataTransfer transfer = factory.getTransfer(rule);
			transfer.transData();
			file = (File) transfer.getTransResult();
		return file;
	}
	
	/**
	 * 功能：删除多条记录
	 * @param dtos DTOSet
	 * @return boolean
	 * @throws DataHandleException
	 * @throws SQLException 
	 */
	public boolean deleteResponsibility(DTOSet dtos) throws DataHandleException, SQLException {
        boolean operateResult = false;
        if (dtos != null && dtos.getSize() > 0) {
            operateResult = true;
            int dtoCount = dtos.getSize();
            AmsElementMatchModel modelProducer = (AmsElementMatchModel) sqlProducer;
            conn.setAutoCommit(false);
            for (int i = 0; i < dtoCount; i++) {
                try{
                	AmsElementMatchDTO dto = (AmsElementMatchDTO) dtos.getDTO(i);
                	SQLModel sqlModel = modelProducer.deleteModel(dto);
                	DBOperator.updateRecord(sqlModel, conn);
                	conn.commit();
                } catch(Exception e){
                    e.printStackTrace();
                    conn.rollback();
                }
            }
            if(conn != null){
            	conn.close();
            }
        }
        return operateResult;
    }
	
}
