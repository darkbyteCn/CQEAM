package com.sino.ams.yj.comvan.dao;


import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

import com.sino.base.dto.DTO;
import com.sino.base.util.StrUtil;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.DataTransException;
import com.sino.base.log.Logger;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.datatrans.*;
import com.sino.base.constant.WorldConstant;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yj.comvan.dto.AmsYjComvanDTO;
import com.sino.ams.yj.comvan.model.AmsYjComvanModel;


/**
 * <p>Title: AmsYjComvanDAO</p>
 * <p>Description:程序自动生成服务程序“AmsYjComvanDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急通信车
 */


public class AmsYjComvanDAO extends BaseDAO {

    private SfUserDTO sfUser = null;

    /**
     * 功能：应急通信车信息表 AMS_YJ_COMVAN 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsYjComvanDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AmsYjComvanDAO(SfUserDTO userAccount, AmsYjComvanDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsYjComvanDTO dtoPara = (AmsYjComvanDTO) dtoParameter;
        super.sqlProducer = new AmsYjComvanModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * isNew:ture 记录不存在，插入语句   false：记录存在，修改语句 
     * @param isNew
     * @return
     */
    public boolean saveData(boolean isNew) {
        boolean success = false;
        try {
            if (isNew) {
                createData();
            } else {
                updateData();
            }
            success = true;
        } catch (DataHandleException e) {
            Logger.logError(e);
        }
        return success;
    }

    public void deleteAllData(String[] comvanIds) throws DataHandleException {
        AmsYjComvanModel comvanModel=(AmsYjComvanModel)sqlProducer;
        SQLModel sqlModel=comvanModel.getDeleteModel(comvanIds);
        DBOperator.updateRecord(sqlModel,conn);
    }

    public File getExportFile() throws DataTransException {
        File file = null;
		try {
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			String reportTitle = "应急通信车信息报表";
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
			Map fieldMap = new HashMap();
			fieldMap.put("COMVAN_ID", "序号");
			fieldMap.put("DEPT_NAME", "储备单位");
			fieldMap.put("MANUFACTURER", "生产商");
			fieldMap.put("MODEL", "型号");
			fieldMap.put("REFIT_FIRM", "车辆改装厂");
			fieldMap.put("LENGTH", "整车长度(mm)");
			fieldMap.put("QUALITY", "整备质量(吨)");
			fieldMap.put("ANTENNA_MAST_FORM", "天线桅杆形式");
			fieldMap.put("HAS_OILENGINE", "是否有油机");
			fieldMap.put("LICENSE_PLATE", "现有车牌照");
			fieldMap.put("FRAME_NUMBER", "车架号");
			fieldMap.put("L_W_H", "长×宽×高(mm)");
			fieldMap.put("ORIGINAL_COST", "资产原值(万元)");
			fieldMap.put("BTS_MANUFACTURER", "厂家");
			fieldMap.put("BTS_MODEL", "型号");
			fieldMap.put("CARRIER_FREQUENCYV_ALLOCATE", "载频分配");
			fieldMap.put("CARRIER_FREQUENCYV_QTY", "总载频数");
			fieldMap.put("INSTALLED_BSC", "是否安装BSC");
			fieldMap.put("OTHER_GSM_UNIT", "安装的其他GSM系统网元");
			fieldMap.put("TRANS_FORM", "传输方式");
			fieldMap.put("TRANS_ITEM_MODEL", "设备型号");
			fieldMap.put("BANDWIDTH", "带宽");
			fieldMap.put("HAS_SATELLITE_TRANSMISSIONS", "是否有卫星传输");
			fieldMap.put("TYPE_OF_TRAFFIC", "可提供业务种类");
			fieldMap.put("USE_TIMES", "使用次数");
			fieldMap.put("USED_TRAFFIC", "使用时提供的业务");
			fieldMap.put("USE_SCENE", "主要使用场景及地点");
			fieldMap.put("REMARK", "其他说明");

			rule.setFieldMap(fieldMap);
			CustomTransData custData = new CustomTransData();
			custData.setReportTitle(reportTitle);
			custData.setReportPerson(sfUser.getUsername());
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

}