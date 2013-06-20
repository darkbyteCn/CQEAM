package com.sino.ams.system.house.dao;


import java.sql.Connection;

import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.house.model.AmsHouseInfoModel;
import com.sino.ams.system.house.model.EtsItemInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EtsItemInfoDAO</p>
 * <p>Description:程序自动生成服务程序“EtsItemInfoDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class EtsItemInfoDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：标签号信息(EAM) ETS_ITEM_INFO 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsItemInfoDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EtsItemInfoDAO(SfUserDTO userAccount, EtsItemInfoDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		EtsItemInfoDTO dtoPara = (EtsItemInfoDTO)dtoParameter;
		super.sqlProducer = new EtsItemInfoModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：插入标签号信息(EAM)表“ETS_ITEM_INFO”数据。
	 * @return boolean
	 */
	public void createData() throws DataHandleException {
//		boolean operateResult = super.createData();
		 super.createData();
		getMessage().addParameterValue("标签号信息");
//		return operateResult;
	}

	/**
	 * 功能：更新标签号信息(EAM)表“ETS_ITEM_INFO”数据。
	 * @return boolean
	 */
	public void updateData() throws DataHandleException{
//		boolean operateResult = super.updateData();
		 super.updateData();
		getMessage().addParameterValue("标签号信息");
//		return operateResult;
	}

	/**
	 * 功能：删除标签号信息(EAM)表“ETS_ITEM_INFO”数据。
	 * @return boolean
	 */
	public void deleteData() throws DataHandleException{
//		boolean operateResult = super.deleteData();
		 super.deleteData();
		getMessage().addParameterValue("标签号信息");
//		return operateResult;
	}


    public void updateAttribute1(String Barcode) throws DataHandleException {
        AmsHouseInfoModel amsHouseInfoModel = (AmsHouseInfoModel) sqlProducer;
		SQLModel sqlModel = amsHouseInfoModel.getAttribute1Model(Barcode);
		DBOperator.updateRecord(sqlModel, conn);
    }

    
}