package com.sino.ams.yj.dao;

import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.WorldConstant;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yj.dto.AmsYjDrillDTO;
import com.sino.ams.yj.model.AmsYjDrillModel;

/**
 * <p>Title: AmsYjDrillDAO</p>
 * <p>Description:程序自动生成服务程序“AmsYjDrillDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-应急演练情况维护
 */


public class AmsYjDrillDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：应急演练情况表 AMS_YJ_DRILL 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsYjDrillDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsYjDrillDAO(SfUserDTO userAccount, AmsYjDrillDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		AmsYjDrillDTO dtoPara = (AmsYjDrillDTO)dtoParameter;
		super.sqlProducer = new AmsYjDrillModel((SfUserDTO)userAccount, dtoPara);
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

                String fileName = "应急演练情况信息.xls";
                String filePath = WorldConstant.USER_HOME;
                filePath += WorldConstant.FILE_SEPARATOR;
                filePath += fileName;
                rule.setTarFile(filePath);

                DataRange range = new DataRange();
                rule.setDataRange(range);

                Map fieldMap = new HashMap();
                fieldMap.put("ORGANIZATION_NAME", "公司名称");
                fieldMap.put("DRILL_ID", "序号");
                fieldMap.put("DRILL_NAME", "演练名称");
                fieldMap.put("DRILL_TYPE", "演练类型");
                fieldMap.put("DRILL_NATURE", "演练性质");
                fieldMap.put("DRILL_DATE", "演练时间");
                fieldMap.put("DRILL_ADDRESS", "演练地点");
                fieldMap.put("PEOPLE_QUALITY", "参演人数");
                fieldMap.put("EQUIPMENT_QUANTITY", "演练装备数量");
                fieldMap.put("PLAN", "参照预案");
                fieldMap.put("QUESTION", "演练存在问题");
                fieldMap.put("IS_PERFECT", "是否需要完善预案");
                fieldMap.put("PLAN_DATE", "完善预案计划时间");
                fieldMap.put("REMARK", "备注");

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