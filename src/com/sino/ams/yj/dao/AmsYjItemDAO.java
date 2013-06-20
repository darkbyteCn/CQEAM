package com.sino.ams.yj.dao;

import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.WorldConstant;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yj.dto.AmsYjItemDTO;
import com.sino.ams.yj.model.AmsYjItemModel;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.spare.model.SpareVendorModel;

/**
 * <p>Title: AmsYjItemDAO</p>
 * <p>Description:程序自动生成服务程序“AmsYjItemDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-应急保障装备名称维护
 */

public class AmsYjItemDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：应急保障设备名称字典表 AMS_YJ_ITEM 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsYjItemDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsYjItemDAO(SfUserDTO userAccount, AmsYjItemDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		AmsYjItemDTO dtoPara = (AmsYjItemDTO)dtoParameter;
		super.sqlProducer = new AmsYjItemModel((SfUserDTO)userAccount, dtoPara);
	}


    /**
     * 功能：生效选定设备
     * @param itemCode String
     */
    public void enableItem(String itemCode) {
        try {
            AmsYjItemModel a = (AmsYjItemModel) sqlProducer;
            SQLModel sModel = a.getEnableModel(itemCode);
            DBOperator.updateRecord(sModel, conn);
            prodMessage(CustMessageKey.ENABLE_SUCCESS);
            getMessage().addParameterValue("设备");
        } catch (DataHandleException ex) {
            ex.printLog();
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            getMessage().addParameterValue("设备");
        }
    }
     public boolean doVerify(String itemName) throws QueryException {
       boolean success = false;
       AmsYjItemModel aModel = (AmsYjItemModel) sqlProducer;
       SQLModel sqlModel = aModel.doVerify(itemName);
       SimpleQuery sq = new SimpleQuery(sqlModel, conn);
       sq.executeQuery();
       success = sq.hasResult();
       return success;
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

                String fileName = "应急保障装备名称.xls";
                String filePath = WorldConstant.USER_HOME;
                filePath += WorldConstant.FILE_SEPARATOR;
                filePath += fileName;
                rule.setTarFile(filePath);

                DataRange range = new DataRange();
                rule.setDataRange(range);

                Map fieldMap = new HashMap();
                fieldMap.put("ITEM_CODE", "ID号");
                fieldMap.put("ITEM_NAME", "装备名称");
                fieldMap.put("ITEM_CATEGORY", "字典类型");
                fieldMap.put("CREATE_USER", "创建人");
                fieldMap.put("CREATION_DATE", "创建日期");
                fieldMap.put("LAST_UPDATE_USER", "更新人");
                fieldMap.put("LAST_UPDATE_DATE", "更新日期");
                fieldMap.put("DISABLE_DATE", "失效日期");

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