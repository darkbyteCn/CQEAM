package com.sino.ams.spare.dzyh.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.spare.dzyh.dto.CostEasyDTO;
import com.sino.ams.spare.dzyh.model.CostEasyModel;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.switches.model.EtsObjectModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-4-7
 * Time: 16:32:53
 * Function；低值易耗维护。
 */
public class CostEasyDAO extends AMSBaseDAO {

	private SfUserDTO sfUser = null;

/**
	 * 功能：资产地点表(AMS) ETS_OBJECT 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsObjectDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public CostEasyDAO(SfUserDTO userAccount, CostEasyDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

    /**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		CostEasyDTO dtoPara = (CostEasyDTO) dtoParameter;
		super.sqlProducer = new CostEasyModel( (SfUserDTO) userAccount, dtoPara);
	}

	/**
	 * 功能：根据ObjectCategory获得 categoryName
	 * @return String
	 * @throws com.sino.base.exception.QueryException
     */
	public String getCategoryName() throws QueryException {
        String categoryName = "";
        EtsObjectDTO etsObjectDTO = (EtsObjectDTO) dtoParameter;
        EtsObjectModel eomodel = new EtsObjectModel(sfUser, etsObjectDTO);
        SQLModel sModel = eomodel.getCategoryNameModel();
        SimpleQuery sQuery = new SimpleQuery(sModel, conn);
        sQuery.executeQuery();
        if (sQuery.hasResult()) {
            RowSet row = sQuery.getSearchResult();
            try {
                categoryName = (String) row.getRow(0).getValue("VALUE");
            } catch (ContainerException e) {
                Logger.logError(e);
                throw new QueryException();
            }
        }
        return categoryName;
    }

    /**
	 * 功能：插入资产地点表(AMS)表“ETS_OBJECT”数据。
	 * @return boolean
	 */
	public void createData() throws DataHandleException {
//		CostEasyDTO dtoPara = (CostEasyDTO) dtoParameter;
	    super.createData();
	getMessage().addParameterValue("低值易耗信息");
//		return operateResult;
	}

    /**
	 * 功能：执行单个失效操作。
	 * @return boolean
	 */
	public void deleteData() throws DataHandleException{
		 super.deleteData();
		getMessage().addParameterValue("低值易耗信息");
	}

    /**
	 * 功能：更新资产地点表(AMS)表“ETS_OBJECT”数据。
	 * @return boolean
	 */
	public void updateData() throws DataHandleException{
//		CostEasyDTO dtoPara = (CostEasyDTO) dtoParameter;
		 super.updateData();
        getMessage().addParameterValue("低值易耗信息");
	}

	public void disabledData(String[] workorderObjectNos){ //执行批量失效操作
		EtsObjectDTO dtoPara = (EtsObjectDTO) dtoParameter;
		try {
			EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
			SQLModel sqlModel = etsObjectModel.getDisabledModel(workorderObjectNos);
			DBOperator.updateRecord(sqlModel, conn);
			prodMessage(CustMessageKey.DISABLE_SUCCESS);
			getMessage().addParameterValue(dtoPara.getCategoryName());
		} catch (DataHandleException ex) {
			prodMessage(CustMessageKey.DISABLE_FAILURE);
			getMessage().addParameterValue(dtoPara.getCategoryName());
			ex.printLog();
		}
	}

	public void efficientData(String[] workorderObjectNos){ //执行批量生效效操作
		EtsObjectDTO dtoPara = (EtsObjectDTO) dtoParameter;
		try {
			EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
			SQLModel sqlModel = etsObjectModel.getEfficientModel(workorderObjectNos);
			DBOperator.updateRecord(sqlModel, conn);
			prodMessage(CustMessageKey.ENABLE_SUCCESS);
			getMessage().addParameterValue(dtoPara.getCategoryName());
		} catch (DataHandleException ex) {
			prodMessage(CustMessageKey.ENABLE_FAILURE);
			getMessage().addParameterValue(dtoPara.getCategoryName());
			ex.printLog();
		}
	}

	public void inureData() throws DataHandleException { //执行生效操作
		EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
		SQLModel sqlModel = etsObjectModel.getInureModel();
		DBOperator.updateRecord(sqlModel, conn);
	}

   public File exportFile() throws DataTransException {
        File file = null;
        try {
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setCalPattern(CalendarConstant.LINE_PATTERN);
            rule.setSourceConn(conn);
            String fileName = "低值易耗品数据.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);
            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("BARCODE", "低值易耗品标签号");
            fieldMap.put("ITEM_NAME", "低值易耗品名称");
            fieldMap.put("ITEM_SPEC", "规格型号");
            fieldMap.put("ITEM_CATE_GORY_DESC", "设备类型");
            fieldMap.put("ITEM_QTY", "数量");
            fieldMap.put("WORKORDER_OBJECT_NAME", "地点名称");

            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle("低值易耗品数据");
            custData.setReportPerson(sfUser.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
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


  public String insertData() throws SQLException, DataHandleException, QueryException {
        String msg = "";
        try {
            conn.setAutoCommit(false);
            CostEasyDTO dto = (CostEasyDTO) dtoParameter;
            CostEasyModel model = new CostEasyModel(sfUser, dto);
//            SimpleQuery sq = new SimpleQuery(model.selectItemInfo(), conn);//检查 表ETS_SYSTEM_ITEM 的唯一性
//            sq.executeQuery();
//            if (sq.getSearchResult().getSize() > 0) {
//                DBOperator.updateRecord(model.updateModel(), conn);//如果有即对表 ETS_SYSTEM_ITEM 执行修改操作
//            } else {
//                SeqProducer seq = new SeqProducer(conn);
//                String itemCode = seq.getStrNextSeq("ETS_SYSTEM_ITEM_S");
//                dto.setItemCode(itemCode);
//                DBOperator.updateRecord(model.insertIntoItem(), conn);//对表 ETS_SYSTEM_ITEM 执行增加操作
//                SimpleQuery sq1 = new SimpleQuery(model.selectDis(itemCode), conn);//对表ETS_SYSITEM_DISTRIBUTE进行判断
//                sq1.executeQuery();
//                if (sq1.getSearchResult().getSize() > 0) {
//                } else {
//                    DBOperator.updateRecord(model.insertIntoDis(itemCode), conn);//增加信息到表ETS_SYSITEM_DISTRIBUTE
//                }
//            }
            createData();//ETS_ITEM_INFO进行新增的操作
            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                Logger.logError(e1);
            }
            Logger.logError(e);
            throw e;
        }
        catch (DataHandleException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                Logger.logError(e1);
            }
            Logger.logError(e);
            throw e;
        }
        return msg;
    }
}
