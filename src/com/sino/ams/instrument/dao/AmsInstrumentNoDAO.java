package com.sino.ams.instrument.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.instrument.dto.AmsInstrumentHDTO;
import com.sino.ams.instrument.dto.AmsInstrumentLDTO;
import com.sino.ams.instrument.model.AmsInstrumentNoModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-10-30
 * Time: 17:13:48
 * To change this template use File | Settings | File Templates.
 */
public class AmsInstrumentNoDAO extends BaseDAO {
    private SfUserDTO sfUser = null;
    private AmsInstrumentHDTO dto = null;

    public AmsInstrumentNoDAO(SfUserDTO userAccount, AmsInstrumentHDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
        this.dto = (AmsInstrumentHDTO) super.dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsInstrumentHDTO dtoPara = (AmsInstrumentHDTO) dtoParameter;
        super.sqlProducer = new AmsInstrumentNoModel((SfUserDTO) userAccount, dtoPara);
    }
    public DTOSet getLines() throws QueryException {
        AmsInstrumentNoModel model = new AmsInstrumentNoModel(sfUser, null);
        SimpleQuery sq = new SimpleQuery(model.getByTransIdModel(dto.getTransId()), conn);
        sq.setDTOClassName(AmsInstrumentLDTO.class.getName());
        sq.executeQuery();

        return sq.getDTOSet();
    }
    public File exportFile() throws DataTransException {
        File file = null;
        try {
            AmsInstrumentHDTO itemDTO = (AmsInstrumentHDTO) dtoParameter;
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);

            String fileName = "仪具单据统计表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("TRANS_NO", "仪具单据号");
            fieldMap.put("BNAME", "借用人");
            fieldMap.put("RNAME", "返还人");
            fieldMap.put("CKNAME", "盘点人");
            fieldMap.put("TRANS_TYPE", "单据类型");
            fieldMap.put("TRANS_STATUS", "单据状态");
            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle(fileName);
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
