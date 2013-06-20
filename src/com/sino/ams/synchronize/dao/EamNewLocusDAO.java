package com.sino.ams.synchronize.dao;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.synchronize.model.EamNewLocusModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by sunny song
 * User: song
 * Date: 2008-3-11
 * Time: 8:31:36
 * To change this template use File | Settings | File Templates.
 */
public class EamNewLocusDAO extends AMSBaseDAO {

    /**
     * 功能：eAM新增地点同步 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemMatchDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public EamNewLocusDAO(SfUserDTO userAccount, EamSyschronizeDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EamSyschronizeDTO dtoPara = (EamSyschronizeDTO) dtoParameter;
        super.sqlProducer = new EamNewLocusModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * EAM地点同步
     *
     * @param systemId String
     */
    public void syschronizeLocus(String systemId) {
        CallableStatement cs = null;
        String callStr = "{CALL AMS_SYN_PKG.SYN_EAM_NEW_PLACE(?, ?, ?, ?)}";
        try {
            cs = conn.prepareCall(callStr);
            cs.setInt(1, userAccount.getOrganizationId());
            cs.setString(2, systemId);
            cs.registerOutParameter(3, Types.NUMERIC);
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.execute();

            if(cs.getString(4)!=null)
            {
                 this.message.setMessageValue(cs.getString(4));
                 this.message.setIsError(true);
                 Logger.logError("{CALL AMS_SYN_PKG.SYN_EAM_NEW_PLACE(?, ?, ?, ?)} ERROR:"+cs.getString(4));
                 Logger.logError("(0)"+userAccount.getOrganizationId()+";(1)"+systemId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBStatement(cs);
        }
    }

    public void insertSynTmp(String systemId) {
        CallableStatement cs = null;
        String callStr = "{CALL AMS_SYN_PKG.FN_SPLIT(?, ?)}";
        try {
            cs = conn.prepareCall(callStr);
            cs.setString(1, systemId);
            cs.setString(2, ",");
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBStatement(cs);
        }
    }

    public List changeString(String Str[]) {
        List al = new ArrayList();
        for (int i = 0; i < Str.length; i++) {
            if (Str[i] != null && !Str[i].equals("")) {
                long aa = Long.parseLong(Str[i]);
                al.add(new Long(aa));
            }
        }
        return al;
    }

     public File getExportFile() throws DataTransException {
	   File file = null;
       EamNewLocusModel modelProducer = (EamNewLocusModel)sqlProducer;
       SQLModel sqlModel = modelProducer.getPageQueryModel();
       String reportTitle = "EAM系统新增地点";
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
       rule.setFieldMap(getFieldMap());
       CustomTransData custData = new CustomTransData();
       custData.setReportTitle(reportTitle);
       custData.setReportPerson(userAccount.getUsername());
       custData.setNeedReportDate(true);
       rule.setCustData(custData);
       rule.setCalPattern(LINE_PATTERN);
       TransferFactory factory = new TransferFactory();
       DataTransfer transfer = factory.getTransfer(rule);
       transfer.transData();
       file = (File) transfer.getTransResult();
       return file;
	}

    private Map getFieldMap() {
        Map fieldMap = new HashMap();
        fieldMap.put("WORKORDER_OBJECT_CODE", "EAM地点代码");
        fieldMap.put("WORKORDER_OBJECT_LOCATION", "EAM地点描述");
        fieldMap.put("ASSETS_LOCATION_CODE", "原MIS地点代码");
        fieldMap.put("ASSETS_LOCATION", "原MIS地点描述");
        fieldMap.put("LAST_UPDATE_DATE", "最后更新时间");
        fieldMap.put("USERNAME", "最后更新人");
        fieldMap.put("WORKORDER_CATEGORY", "地点类别");
        fieldMap.put("CREATION_DATE", "创建日期");
        return fieldMap;
    }
}
