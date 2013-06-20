package com.sino.ams.synchronize.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.synchronize.model.AddressChangedModel;
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
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-11-26
 * Time: 11:51:56
 * To change this template use File | Settings | File Templates.
 */

    public class AddressChangedDAO extends AMSBaseDAO {
        private SfUserDTO sfUser = null;

        /**
         * 功能：资产地点表(EAM) ETS_OBJECT 数据访问层构造函数
         * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
         * @param dtoParameter EtsObjectDTO 本次操作的数据
         * @param conn Connection 数据库连接，由调用者传入。
         */
        public AddressChangedDAO(SfUserDTO userAccount, EamSyschronizeDTO dtoParameter, Connection conn) {
            super(userAccount, dtoParameter, conn);
            sfUser = userAccount;
        }

        /**
         * 功能：SQL生成器BaseSQLProducer的初始化。
         * @param userAccount BaseUserDTO 本系统最终操作用户类
         * @param dtoParameter DTO 本次操作的数据
         */
        protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
            EamSyschronizeDTO dtoPara = (EamSyschronizeDTO)dtoParameter;
            super.sqlProducer = new AddressChangedModel((SfUserDTO)userAccount, dtoPara);
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

                String fileName = "地点信息变更同步.xls";
                String filePath = WorldConstant.USER_HOME;
                filePath += WorldConstant.FILE_SEPARATOR;
                filePath += fileName;
                rule.setTarFile(filePath);

                DataRange range = new DataRange();
                rule.setDataRange(range);

                Map fieldMap = new HashMap();
                fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");
                fieldMap.put("WORKORDER_OBJECT_NAME", "EAM地点描述");
                fieldMap.put("ASSETS_LOCATION", "MIS地点描述");

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

