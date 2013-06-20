package com.sino.ams.spare.fpjscy.dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.base.constant.WorldConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO;
import com.sino.ams.spare.fpjscy.model.AmsBjFpJsCyModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author TOTTI
 *         Date: 2007-11-29
 */
public class AmsBjFpJsCyDAO extends BaseDAO {
    private SfUserDTO sfUser = null;
    private AmsBjsAllotHDTO dto = null;

    public AmsBjFpJsCyDAO(SfUserDTO userAccount, AmsBjsAllotHDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
        this.dto = (AmsBjsAllotHDTO) super.dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsBjsAllotHDTO dtoPara = (AmsBjsAllotHDTO) dtoParameter;
        super.sqlProducer = new AmsBjFpJsCyModel((SfUserDTO) userAccount, dtoPara);
    }
    public RowSet getLines() throws QueryException {
        AmsBjFpJsCyModel model = new AmsBjFpJsCyModel(sfUser, null);
        SimpleQuery sq = new SimpleQuery(model.getDataByTransIdModel(dto.getTransId()), conn);
        sq.executeQuery();

        return sq.getSearchResult();
    }

    public RowSet getData(String lineId, String itemCode) throws QueryException {
        AmsBjFpJsCyModel model = new AmsBjFpJsCyModel(sfUser, null);
        SimpleQuery sq = new SimpleQuery(model.getDataDiffent(lineId, itemCode), conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    public File exportFile(String transId) throws DataTransException {
        File file = null;

            AmsBjFpJsCyModel model = new AmsBjFpJsCyModel(sfUser, null);
            SQLModel sqlModel =model.getDataByTransIdModel(transId) ;
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);

            String fileName = "备件分配接收差异统计表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("ITEM_NAME", "备件名称");
            fieldMap.put("BARCODE", "备件条码");
            fieldMap.put("FP_STATUS", "分配状态");
            fieldMap.put("ACCEPT_STATUS", "接收状态");
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

        return file;
    }
}
