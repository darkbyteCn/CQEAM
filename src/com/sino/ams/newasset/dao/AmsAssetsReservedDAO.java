package com.sino.ams.newasset.dao;


import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.assetsharing.model.AssetSharingModel;
import com.sino.ams.newasset.dto.AmsAssetsReservedDTO;
import com.sino.ams.newasset.model.AmsAssetsReservedModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsAssetsReservedDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsReservedDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class AmsAssetsReservedDAO extends AMSBaseDAO {
	AmsAssetsReservedModel amsAssetsReservedModel = null;
    /**
     * 功能：资产事务保留表 AMS_ASSETS_RESERVED 数据访问层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsReservedDTO 本次操作的数据
     * @param conn Connection 数据库连接，由调用者传入。
     */
    public AmsAssetsReservedDAO(SfUserDTO userAccount,
                                AmsAssetsReservedDTO dtoParameter,
                                Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.initSQLProducer(userAccount, dtoParameter);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsReservedDTO dtoPara = (AmsAssetsReservedDTO) dtoParameter;
        amsAssetsReservedModel = new AmsAssetsReservedModel((SfUserDTO) userAccount,
                dtoPara);
        super.sqlProducer =amsAssetsReservedModel;
    }
    
 
	
	/**
	 * 功能：保存行 -- sj add 
	 * 
	 * @throws DataHandleException
	 * @throws SQLModelException 
	 */
	public void createReserved( String transId , String barcode  )
			throws DataHandleException  {
		amsAssetsReservedModel = new AmsAssetsReservedModel( userAccount,  null );
		SQLModel sqlModel = amsAssetsReservedModel.getDataCreateModel( transId ,  barcode );
		DBOperator.updateRecord(sqlModel, conn);
	}
	
	/**
	 * 功能：删除行 -- sj add 
	 * 
	 * @throws DataHandleException
	 */
	public void deleteReserved( String transId )
			throws DataHandleException {
		SQLModel sqlModel = amsAssetsReservedModel.getDataDeleteModel( transId ) ;
		DBOperator.updateRecord(sqlModel, conn);
	}
}
