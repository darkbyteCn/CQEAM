package com.sino.nm.ams.mss.dao;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.nm.ams.mss.model.MssItemInfoChangeModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.DataTransException;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.datatrans.*;
import com.sino.base.log.Logger;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.WorldConstant;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: T_yuyao
 * Date: 2011-4-27
 * Time: 14:07:50
 * To change this template use File | Settings | File Templates.
 */
public class MssItemInfoChangeDAO extends BaseDAO {

    private SfUserDTO sfUser = null;


    public MssItemInfoChangeDAO(SfUserDTO userAccount, EtsItemInfoDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EtsItemInfoDTO dtoPara = (EtsItemInfoDTO) dtoParameter;
        super.sqlProducer = new MssItemInfoChangeModel((SfUserDTO) userAccount, dtoPara);
    }
      public String  confirmRentAssets(String[] systemIds, String[] newResponsibilityDept, String[] newResponsibilityUser, String[] newAddressId,String[] mainUser,String []barcodes,String[]itemCodes) throws DataHandleException, SQLModelException {
        boolean autoCommit = false;
        boolean hasError = true;
        String msg = "";
          boolean ret=false;

        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            if ((systemIds != null) && (systemIds.length > 0)) {

                    for (int i = 0; i < systemIds.length; i++) {
                        String systemId[] = systemIds[i].split(";");
                        int j = Integer.parseInt(systemId[1]);
                        MssItemInfoChangeModel modelProducer = (MssItemInfoChangeModel) sqlProducer;
                        EtsItemInfoDTO dto=new EtsItemInfoDTO();
                          dto.setSystemId(systemId[0]);
                        dto.setNewResponsibilityDept(newResponsibilityDept[j]);
                        dto.setNewResponsibilityUser(newResponsibilityUser[j]);
                        dto.setNewMaintainUser(mainUser[j]);
                        dto.setNewAddressId(newAddressId[j]);
                        dto.setBarcode(barcodes[j]);
//                        dto.setItemCode(itemCodes[j]);
                          SQLModel sqlModel = modelProducer.updateMSSModel(dto);
                        DBOperator.updateRecord(sqlModel, conn);
//                        SQLModel sm=modelProducer.getCreateHistoryModel(dto);
//                        DBOperator.updateRecord(sm,conn) ;
//                        if(!newResponsibilityUser[j].equals("")){
//                         insertEmail(systemId[0],newResponsibilityUser[j]);
//                        }

                    }


            }
            conn.commit();
             hasError = !msg.equals("");
        } catch (SQLException e) {
            Logger.logError(e);
        }  finally {
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
        return  msg;
    }
    public String  enableAssets(String[] systemIds, String[] newResponsibilityDept, String[] newResponsibilityUser, String[] newAddressId,String[] mainUser,String []barcodes,String[]itemCodes) throws DataHandleException, SQLModelException {
        boolean autoCommit = false;
        boolean hasError = true;
        String msg = "";
          boolean ret=false;

        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            if ((systemIds != null) && (systemIds.length > 0)) {

                    for (int i = 0; i < systemIds.length; i++) {
                        String systemId[] = systemIds[i].split(";");
                        int j = Integer.parseInt(systemId[1]);
                        MssItemInfoChangeModel modelProducer = (MssItemInfoChangeModel) sqlProducer;
                        EtsItemInfoDTO dto=new EtsItemInfoDTO();
                          dto.setSystemId(systemId[0]);
                        dto.setNewResponsibilityDept(newResponsibilityDept[j]);
                        dto.setNewResponsibilityUser(newResponsibilityUser[j]);
                        dto.setNewMaintainUser(mainUser[j]);
                        dto.setNewAddressId(newAddressId[j]);
                        dto.setBarcode(barcodes[j]);
//                        dto.setItemCode(itemCodes[j]);
                          SQLModel sqlModel = modelProducer.enableMSSModel(dto);
                        DBOperator.updateRecord(sqlModel, conn);
//                        SQLModel sm=modelProducer.getCreateHistoryModel(dto);
//                        DBOperator.updateRecord(sm,conn) ;
//                        if(!newResponsibilityUser[j].equals("")){
//                         insertEmail(systemId[0],newResponsibilityUser[j]);
//                        }

                    }


            }
            conn.commit();
             hasError = !msg.equals("");
        } catch (SQLException e) {
            Logger.logError(e);
        }  finally {
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
        return  msg;
    }
    public String  disableAssets(String[] systemIds, String[] newResponsibilityDept, String[] newResponsibilityUser, String[] newAddressId,String[] mainUser,String []barcodes,String[]itemCodes) throws DataHandleException, SQLModelException {
        boolean autoCommit = false;
        boolean hasError = true;
        String msg = "";
          boolean ret=false;

        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            if ((systemIds != null) && (systemIds.length > 0)) {

                    for (int i = 0; i < systemIds.length; i++) {
                        String systemId[] = systemIds[i].split(";");
                        int j = Integer.parseInt(systemId[1]);
                        MssItemInfoChangeModel modelProducer = (MssItemInfoChangeModel) sqlProducer;
                        EtsItemInfoDTO dto=new EtsItemInfoDTO();
                          dto.setSystemId(systemId[0]);
                        dto.setNewResponsibilityDept(newResponsibilityDept[j]);
                        dto.setNewResponsibilityUser(newResponsibilityUser[j]);
                        dto.setNewMaintainUser(mainUser[j]);
                        dto.setNewAddressId(newAddressId[j]);
                        dto.setBarcode(barcodes[j]);
//                        dto.setItemCode(itemCodes[j]);
                          SQLModel sqlModel = modelProducer.disableMSSModel(dto);
                        DBOperator.updateRecord(sqlModel, conn);
//                        SQLModel sm=modelProducer.getCreateHistoryModel(dto);
//                        DBOperator.updateRecord(sm,conn) ;
//                        if(!newResponsibilityUser[j].equals("")){
//                         insertEmail(systemId[0],newResponsibilityUser[j]);
//                        }

                    }


            }
            conn.commit();
             hasError = !msg.equals("");
        } catch (SQLException e) {
            Logger.logError(e);
        }  finally {
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
        return  msg;
    }
    public boolean insertEmail(String sysId, String newUser) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        CallableStatement cStmt = null;
        String sqlStr = "{call AMS_DEPRECIATION_PKG.INSERT_EMAIL(?,?,?)}";
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            cStmt = conn.prepareCall(sqlStr);
                cStmt.setString(1, sysId);
                cStmt.setString(2, newUser);
                cStmt.setInt(3, sfUser.getUserId());
                cStmt.execute();
                conn.commit();

                success=true;



        } catch (SQLException e) {
            Logger.logError(e);
            conn.rollback();
            message.setIsError(true);
        } finally {
            DBManager.closeDBStatement(cStmt);
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }
    public File exportFile() throws DataTransException {
		File file = null;
		try {
			EtsItemInfoDTO etsObjectDTO = (EtsItemInfoDTO) dtoParameter;
			SQLModel sqlModel = sqlProducer.getPageQueryModel();
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
			String fileName =  "物资信息统计表.xls";
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);

			Map fieldMap = new HashMap();
			fieldMap.put("ITEM_CATEGORY1", "物资一级分类");
			fieldMap.put("ITEM_CATEGORY2", "物资二级分类");
			fieldMap.put("MSS_BARCODE", "物资条码");
			fieldMap.put("ITEM_NAME", "物资名称");
			fieldMap.put("ITEM_SPEC", "物资型号");
			fieldMap.put("RESPONSIBILITY_USER", "责任人");
			fieldMap.put("MAINTAIN_USER", "使用人");
			fieldMap.put("RESPONSIBILITY_DEPT", "责任部门");
			fieldMap.put("WORKORDER_OBJECT_NAME", "物理地点");
			fieldMap.put("ITEM_STATUS", "使用状态");
			fieldMap.put("USER_LEVEL", "使用等级");
			fieldMap.put("SECURE_LEVEL", "保密等级");
			fieldMap.put("COMPLETENESS_LEVEL", "完整等级");


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