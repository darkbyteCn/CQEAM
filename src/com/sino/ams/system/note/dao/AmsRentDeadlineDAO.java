package com.sino.ams.system.note.dao;


import java.sql.Connection;

import com.sino.ams.system.note.dto.AmsRentDeadlineDTO;
import com.sino.ams.system.note.model.AmsRentDeadlineModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.DBActionConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DataUniqueChecker;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ValidateException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsRentDeadlineDAO</p>
 * <p>Description:程序自动生成服务程序“AmsRentDeadlineDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class AmsRentDeadlineDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：租期设置(EAM) AMS_RENT_DEADLINE 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsRentDeadlineDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsRentDeadlineDAO(SfUserDTO userAccount, AmsRentDeadlineDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		AmsRentDeadlineDTO dtoPara = (AmsRentDeadlineDTO)dtoParameter;
		super.sqlProducer = new AmsRentDeadlineModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：插入租期设置(EAM)表“AMS_RENT_DEADLINE”数据。
	 * @return boolean
	 */
    public void  createData() {
        boolean operateResult = false;
        try {
            String tableName = "AMS_RENT_DEADLINE";
            DataUniqueChecker datChecker = new DataUniqueChecker(tableName, dtoParameter, conn);
            datChecker.setDBAction(DBActionConstant.INSERT);
            datChecker.setServletConfig(servletConfig);
            boolean isValid = datChecker.isDataValid();
            if (!isValid) {
                prodMessage(MsgKeyConstant.UNIQUE_ERROR);
                getMessage().addParameterValue("租赁日期提醒设置");
            } else {
                super.createData();
                operateResult = true;
                getMessage().addParameterValue("租赁日期提醒设置");
            }
        } catch (ValidateException ex) {
            ex.printLog();
            prodMessage(MsgKeyConstant.COMMON_ERROR);
        } catch (DataHandleException ex)  {

        }
       
//        return operateResult;
    }

	/**
	 * 功能：更新租期设置(EAM)表“AMS_RENT_DEADLINE”数据。
	 * @return boolean
	 */
	public void updateData() throws DataHandleException{
//		boolean operateResult = super.updateData();
		super.updateData();
		getMessage().addParameterValue("租赁日期提醒设置");
//		return operateResult;
	}

	/**
	 * 功能：删除租期设置(EAM)表“AMS_RENT_DEADLINE”数据。
	 * @return boolean
	 */
	public void deleteData() throws DataHandleException{
//		boolean operateResult = super.deleteData();
		 super.deleteData();
		getMessage().addParameterValue("租赁日期提醒设置");
//		return operateResult;
	}

       public boolean verifyBarcode(String barcode) throws QueryException { //执行校验Barcode操作
        boolean success = false;
//        try {
            AmsRentDeadlineModel amsRentDeadlineModel = (AmsRentDeadlineModel) sqlProducer;
            SQLModel sqlModel = amsRentDeadlineModel.getVerifyBarcodeModel(barcode);

            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
            success = sq.hasResult();

        return success;
    }
}