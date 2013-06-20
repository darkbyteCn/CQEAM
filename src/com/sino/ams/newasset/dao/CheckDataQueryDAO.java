package com.sino.ams.newasset.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.newasset.model.CheckDataQueryModel;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.WorldConstant;

import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-6-2
 * Time: 11:33:02
 * To change this template use File | Settings | File Templates.
 */
public class CheckDataQueryDAO extends AMSBaseDAO {
    private SfUserDTO sfUser = null;

	/**
	 * 功能：资产地点表(AMS) ETS_OBJECT 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsObjectDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public CheckDataQueryDAO(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		AmsAssetsCheckHeaderDTO dtoPara = (AmsAssetsCheckHeaderDTO)dtoParameter;
        sqlProducer = new CheckDataQueryModel((SfUserDTO)userAccount, dtoPara);
    }

 /**
	 * 功能：导出Excel文件。
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File exportFile() throws DataTransException {
		File file = null;
		try {
			DataTransfer transfer = null;
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
			String fileName = "正在盘点资产信息查询.xls";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);
			Map fieldMap = new HashMap();
			fieldMap.put("TRANS_NO", "工单编号");
			fieldMap.put("BARCODE", "标签号");
			fieldMap.put("COMPANY_NAME", "盘点公司");
			fieldMap.put("GROUPNAME", "下单组别");
            fieldMap.put("LOCATION_CODE", "地点编码");
            fieldMap.put("CHECK_LOCATION", "盘点地点");
			fieldMap.put("START_TIME", "开始日期");
			fieldMap.put("IMPLEMENT_USER", "执行人");
            fieldMap.put("ARCHIVED_USER", "归档人");
            fieldMap.put("IMPLEMENT_DAYS", "执行周期(天)");
            fieldMap.put("ORDER_STATUS", "单据状态");

            rule.setFieldMap(fieldMap);
			CustomTransData custData = new CustomTransData();
			custData.setReportTitle(fileName);
			custData.setReportPerson(sfUser.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			/*rule.setSheetSize(1000);*/
			//设置分页显示
			TransferFactory factory = new TransferFactory();
			transfer = factory.getTransfer(rule);
			transfer.transData();
			file = (File) transfer.getTransResult();
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataTransException(ex);
		}
		return file;
	}

}
