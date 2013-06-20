package com.sino.ams.match.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.match.model.MisEquipmentScreenModel;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;
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
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2008-6-17
 * Time: 10:36:57
 * To change this template use File | Settings | File Templates.
 */
public class MisEquipmentScreenDAO extends BaseDAO {
    private SfUserDTO sfUser = null;

    public MisEquipmentScreenDAO(SfUserDTO userAccount, EtsFaAssetsDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EtsFaAssetsDTO dtoPara = (EtsFaAssetsDTO) dtoParameter;
        super.sqlProducer = new MisEquipmentScreenModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：更新MIS资产表多条记录
     *
     */
    public void updateMISDTOs(DTOSet dtos, EtsFaAssetsDTO matchTypeDto) throws DataHandleException {
        if (dtos != null && dtos.getSize() > 0) {
            int dtoCount = dtos.getSize();
            for (int i = 0; i < dtoCount; i++) {
                EtsFaAssetsDTO dto = (EtsFaAssetsDTO) dtos.getDTO(i);
                updateMISDTO(dto, matchTypeDto);
            }
        }
    }
    public void updateMISDTO(EtsFaAssetsDTO dto, EtsFaAssetsDTO matchTypeDto) throws DataHandleException {
        boolean hasError = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            MisEquipmentScreenModel modelProducer = (MisEquipmentScreenModel) sqlProducer;
            SQLModel sqlModel =null;
            if (matchTypeDto.getKey().equals(WebAttrConstant.MATCH_MODE_0THER_RET)) {
                sqlModel = modelProducer.deleteAssistMis(dto);
            } else {
                sqlModel = modelProducer.insertAssistMis(dto);
            }
            DBOperator.updateRecord(sqlModel, conn);
            conn.commit();
            hasError = false;
        } catch (SQLException e) {
            Logger.logError(e);
        } finally {
            try {
                if (hasError) {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            }
            catch (SQLException e) {
                Logger.logError(e);
            }
        }
    }

    /**
     * 功能：导出Excel文件。
     *
     * @return File
     * @throws com.sino.base.exception.DataTransException
     *
     */
    public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "MIS已屏蔽设备清单.csv";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("BARCODE", "MIS条码");
            fieldMap.put("ITEM_NAME", "MIS设备名称");
            fieldMap.put("ITEM_SPEC", "MIS规格型号");
            fieldMap.put("WORKORDER_OBJECT_NAME", "MIS设备地点");
            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("MIS已屏蔽设备清单");
            custData.setReportPerson(sfUser.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
            TransferFactory factory = new TransferFactory();
            DataTransfer transfer = factory.getTransfer(rule);
            transfer.transData();
            file = (File) transfer.getTransResult();
        }
        catch (SQLModelException ex) {
            ex.printLog();
            throw new DataTransException(ex);
        }
        return file;
    }

    //自己实现分页，但没有首页和尾页
//    public void getScreenEquipment() throws QueryException {
//        MisEquipmentScreenModel model = (MisEquipmentScreenModel) sqlProducer;
//        SQLModel sqlmodel = model.getScreenEquipment();
//        WebPageView view = new WebPageView(req, conn);
//        view.setCalPattern(CalendarConstant.LINE_PATTERN);
//        view.produceWebData(sqlmodel);
//    }
}
