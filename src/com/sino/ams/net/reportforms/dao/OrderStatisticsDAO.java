package com.sino.ams.net.reportforms.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.net.reportforms.dto.SitusStatisticsDTO;
import com.sino.ams.net.reportforms.model.OrderStatisticsModel;
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
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by       李轶
 * Date:            2009-07-24
 * Time:            10:26:55
 * Function:        工单统计--地点(陕西)
 */
public class OrderStatisticsDAO extends BaseDAO {
//     private HttpServletRequest request = null;
//    private Connection conn = null;
//    private SitusStatisticsModel situsModel = null;
//    private SQLModel sqlModel = null;
    private SfUserDTO sfUser = null;


//    public SitusStatisticsDAO(SfUserDTO userAccount, Connection conn) {
//        this.conn = conn;
//        this.situsModel = new SitusStatisticsModel();
//        this.sqlModel = new SQLModel();
//    }

        public OrderStatisticsDAO(SfUserDTO userAccount, SitusStatisticsDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

//    public void setParameter(SitusStatisticsDTO projectDTO) {
//        situsModel.setDtoParameter(projectDTO);
//    }
//
//    public void clearParameter() {
//        situsModel.setDtoParameter(new SitusStatisticsDTO());
//    }
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SitusStatisticsDTO dtoPara = (SitusStatisticsDTO) dtoParameter;
		super.sqlProducer = new OrderStatisticsModel( (SfUserDTO) userAccount, dtoPara);
	}
//    /**
//     * 统计出所有工单
//     * @throws QueryException
//     */
//    public void produceProjects() throws QueryException {
//        sqlModel = situsModel.getProjectsModel();
//        WebPageView wpv = new WebPageView(request, conn);
//        wpv.setCalPattern(CalendarConstant.LINE_PATTERN);
//        wpv.produceWebData(sqlModel);
//    }


    /**
	 * 功能：导出Excel文件。
	 * @return File
	 * @throws com.sino.base.exception.DataTransException
	 */
	public File exportFile() throws DataTransException {
		File file = null;
		try {
			SitusStatisticsDTO SitusDTO = (SitusStatisticsDTO) dtoParameter;
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
			String fileName =  "工单统计--按地点.xls";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);

			Map fieldMap = new HashMap();
			fieldMap.put("WORKORDER_OBJECT_CODE", "地点编号");
			fieldMap.put("WORKORDER_OBJECT_NAME", "地点简称");
			fieldMap.put("TMPT_A", "交接工单数");
			fieldMap.put("TMPT_B", "巡检工单数");
			fieldMap.put("TMPT_C", "维修工单数");
			fieldMap.put("TMPT_D", "搬迁工单数");
			fieldMap.put("TMPT_E", "调拨工单数");
			fieldMap.put("TMPT_F", "报废工单数");
//			fieldMap.put("TMPT_B", "所属工程");
			rule.setFieldMap(fieldMap);

			CustomTransData custData = new CustomTransData();
			custData.setReportTitle("工单统计--地点");
			custData.setReportPerson(sfUser.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			/*rule.setSheetSize(1000);*/
			//设置分页显示
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
}