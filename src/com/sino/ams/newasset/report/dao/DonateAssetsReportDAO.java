package com.sino.ams.newasset.report.dao;


import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.report.model.DonateAssetsReportModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
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
 * @author:		李轶
 * Date:		2009-6-2
 * Function:	捐赠资产统计DAO
 *
 */
public class DonateAssetsReportDAO extends AMSBaseDAO {

    private SfUserDTO sfUser = null;
    

    /**
     * 功能：捐赠资产统计  数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public DonateAssetsReportDAO(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
    	AmsAssetsAddressVDTO dtoPara = (AmsAssetsAddressVDTO) dtoParameter;
        super.sqlProducer = new DonateAssetsReportModel((SfUserDTO) userAccount, dtoPara);
    }

      public File exportFile() throws DataTransException {
        File file = null;
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        try {
        	SQLModel sqlModel = null;
        	 String fileName = "捐赠资产统计";
            sqlModel = sqlProducer.getPageQueryModel(); 
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);
           
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath + ".xls");

            DataRange range = new DataRange();
            rule.setDataRange(range);
            Map fieldMap = new HashMap();
            
            fieldMap.put("COMPANY", "所属公司");
            fieldMap.put("DEPT_NAME", "责任部门");
            
            fieldMap.put("BARCODE", "标签号");
            fieldMap.put("ITEM_NAME", "资产名称");
            fieldMap.put("ITEM_SPEC", "规格型号");
//            fieldMap.put("DEPRN_RESERVE", "累计折旧");
//            fieldMap.put("NET_BOOK_VALUE", "净值");
//            fieldMap.put("LIMIT_VALUE", "净额");
//            fieldMap.put("IMPAIRMENT_RESERVE", "累计减值准备");
//            fieldMap.put("PTD_DEPRN", "当期折旧");
            fieldMap.put("ITEM_QTY", "资产数量");
            fieldMap.put("COST", "价值");
            fieldMap.put("ASSET_ID", "资产编号");
            
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
