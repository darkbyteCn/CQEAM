package com.sino.ams.synchronize.dao;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.synchronize.dto.AmsSynTmpDTO;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.synchronize.model.AssetsChangeSynModel;
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
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.log.Logger;
import com.sino.base.util.ArrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统:资产变动直接同步，用于地市公司同步责任部门、地点的变更</p>
 * <p>目前山西使用本功能</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2007~2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AssetsChangeSynDAO extends AMSBaseDAO {

    /**
     * 功能：eAM新增地点同步 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemMatchDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AssetsChangeSynDAO(SfUserDTO userAccount, EamSyschronizeDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EamSyschronizeDTO dtoPara = (EamSyschronizeDTO) dtoParameter;
        super.sqlProducer = new AssetsChangeSynModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：资产变动直接同步
     * <B>该方法存在事务控制，其他需要事务控制的方法不能调用本方法</B>
     * @param orgnizationId String
     * @param assetIds String[]
     */
    public synchronized void syschronizeAssets(int orgnizationId, String[] assetIds) {
        boolean operateResult = false;
        boolean autoCommit = true;
        CallableStatement cs = null;
        try {
            int assetsCount = assetIds.length;
            String sourceStr = ArrUtil.arrToSqlStr(assetIds);
            AmsSynTmpDTO assetsDTO = new AmsSynTmpDTO();
            assetsDTO.setSourceStr(sourceStr);
            String targetStr = "";
            AmsSynTmpDAO synTmpDAO = new AmsSynTmpDAO(userAccount, null, conn);
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            for (int i = 0; i < assetsCount; i++) {
                targetStr = assetIds[i];
                assetsDTO.setTargetStr(targetStr);
                synTmpDAO.setDTOParameter(assetsDTO);
                synTmpDAO.createData();
            }
            String callStr = "{CALL AMS_SYN_PKG.SYN_ASSET_CHANGE_IMMEDIATE(?, ?)}";
            cs = conn.prepareCall(callStr);
            cs.setInt(1, orgnizationId);
            cs.setInt(2, userAccount.getUserId());
            cs.execute();
            synTmpDAO.deleteData();
            operateResult = true;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (DataHandleException ex) {
            ex.printLog();
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                    prodMessage("SYSC_SUCCESS");
                } else {
                    conn.rollback();
                    prodMessage("SYSC_FAILURE");
                    message.setIsError(true);
                }
                conn.setAutoCommit(autoCommit);
                DBManager.closeDBStatement(cs);
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
    }

    public File getExportFile() throws DataTransException {
        AssetsChangeSynModel modelProducer = (AssetsChangeSynModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getPageQueryModel();
        String reportTitle = "EAM资产变动报表";
        String fileName = reportTitle + ".xls";
        TransRule rule = new TransRule();
        rule.setDataSource(sqlModel);
        rule.setSourceConn(conn);
        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
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
        return (File) transfer.getTransResult();
    }

    private Map getFieldMap() {
        Map fieldMap = new HashMap();
        fieldMap.put("ASSET_NUMBER", "资产编号");
        fieldMap.put("BARCODE", "EAM条码");
        fieldMap.put("TAG_NUMBER", "MIS条码");
        fieldMap.put("COMPANY_NAME", "公司名称");
        fieldMap.put("ASSETS_DESCRIPTION", "MIS名称");
        fieldMap.put("ITEM_NAME", "EAM名称");

        fieldMap.put("MODEL_NUMBER", "MIS型号");
        fieldMap.put("ITEM_SPEC", "EAM型号");

        fieldMap.put("ASSETS_LOCATION", "MIS地点");
        fieldMap.put("LOCATION_CODE", "EAM地点");
        fieldMap.put("OLD_ASSIGNED_TO_NAME", "MIS责任人");
        fieldMap.put("NEW_USER_NAME", "EAM责任人");
        fieldMap.put("DATE_PLACED_IN_SERVICE", "MIS启用日期");
        fieldMap.put("LIFE_IN_YEARS", "MIS折旧年限");

		return fieldMap;
	}
}
