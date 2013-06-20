package com.sino.ams.system.item.dao;


import java.sql.Connection;
import java.util.List;

import com.sino.ams.system.item.dto.EtsSysitemDistributeDTO;
import com.sino.ams.system.item.model.EtsSysitemDistributeModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.DateConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EtsSysitemDistributeDAO</p>
 * <p>Description:程序自动生成服务程序“EtsSysitemDistributeDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class EtsSysitemDistributeDAO extends BaseDAO {

	private EtsSysitemDistributeModel etsSysitemDistributeModel = null;
	private SfUserDTO SfUser = null;

	/**
	 * 功能：物料组织分配表 ETS_SYSITEM_DISTRIBUTE 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsSysitemDistributeDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EtsSysitemDistributeDAO(SfUserDTO userAccount, EtsSysitemDistributeDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		SfUser = userAccount;
		initSQLProducer(userAccount, dtoParameter);
	}

    

    /**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		EtsSysitemDistributeDTO dtoPara = (EtsSysitemDistributeDTO)dtoParameter;
		super.sqlProducer = new EtsSysitemDistributeModel((SfUserDTO)userAccount, dtoPara);
		etsSysitemDistributeModel = (EtsSysitemDistributeModel)sqlProducer;
	}

	/**
	 * 功能：插入物料组织分配表表“ETS_SYSITEM_DISTRIBUTE”数据。
	 * @return boolean
	 */
	public void createData() throws DataHandleException{
//		boolean operateResult = super.createData();
		 super.createData();
		getMessage().addParameterValue("物料组织分配表");
//		return operateResult;
	}

	/**
	 * 功能：更新物料组织分配表表“ETS_SYSITEM_DISTRIBUTE”数据。
	 * @return boolean
	 */
	public void updateData() throws DataHandleException{
//		boolean operateResult = super.updateData();
		 super.updateData();
		getMessage().addParameterValue("物料组织分配表");
//		return operateResult;
	}

	/**
	 * 功能：删除物料组织分配表表“ETS_SYSITEM_DISTRIBUTE”数据。
     * @param itemCode
	 * @return boolean
     * @throws DataHandleException
	 */
	public void deleteData(String itemCode) throws DataHandleException {
        SQLModel sqlModel = null;
        sqlModel = etsSysitemDistributeModel.getDataDeleteModel(itemCode);
        DBOperator.updateRecord(sqlModel, conn);
      
            }


//    public EtsSysitemDistributeDAO(Connection conn, SfUserDTO userAccount) {
//        this.conn = conn;
//        this.etsSysitemDistributeModel = new EtsSysitemDistributeModel(userAccount);
//        this.userAccount = userAccount;
//    }

    /**
     * 功能：插入设备分类分配数据
      * @param distrDatas
     * @throws DataHandleException
     */
     public void createDistriDatas(DTOSet distrDatas) throws DataHandleException {
        List sqModels = etsSysitemDistributeModel.getCreateOrgModel(distrDatas);
        DBOperator.updateBatchRecords(sqModels, conn);
    }

    public RowSet produceLinesData(String itemCode) throws QueryException {
            SQLModel sqlModel = etsSysitemDistributeModel.getPrimaryKeyDataModel(itemCode);
            SimpleQuery query = new SimpleQuery(sqlModel, conn);
            query.setCalPattern(DateConstant.LINE_PATTERN);
            query.executeQuery();
            return query.getSearchResult();
        }


    public void deleteItemCodes(String[] itemCodes)   {
      try {

         EtsSysitemDistributeModel etsSysitemDistributeModel = (EtsSysitemDistributeModel) sqlProducer;
		 SQLModel sqlModel = etsSysitemDistributeModel.getDeleteItemModel(itemCodes);

        DBOperator.updateRecord(sqlModel, conn);
    } catch (DataHandleException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
}

}