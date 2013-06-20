package com.sino.ams.ct.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.ct.dto.EtsFaAssetsDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;

public class EtsFaAssetsModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;
	
	/**
	 * 功能：标签号信息(EAM) ETS_FA_CT_ASSETS 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsFaAssetsDTO 本次操作的数据
	 */
	public EtsFaAssetsModel(SfUserDTO userAccount, EtsFaAssetsDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	
	/**
     * 功能：框架自动生成ETS_FA_CT_ASSETS页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
	public SQLModel getPageQueryModel() throws SQLModelException {
		
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsFaAssetsDTO etsFaAssets = (EtsFaAssetsDTO) dtoParameter;
        String sqlStr = "";
        sqlStr = "SELECT" +
        				" EFCA.TAG_NUMBER," +
        				" EFCA.ASSET_NUMBER," +
        				" EFCA.ASSETS_DESCRIPTION," +
        				" EFCA.MODEL_NUMBER," +
        				" EFCA.ORIGINAL_COST," +
        				" EFCA.DATE_PLACED_IN_SERVICE," +
        				" EFCA.DEPRN_COST," +
        				" EII.RESPONSIBILITY_USER," +
        				" AMD.DEPT_NAME," +
        				" EII.RESPONSIBILITY_DEPT," +
        				" AME.USER_NAME," +
        				
        				" EFCA.RETIRE_DATE" +
        				" FROM" +
        				" ETS_FA_CT_ASSETS EFCA," +
        				" AMS_MIS_DEPT AMD," +
        				" AMS_MIS_EMPLOYEE AME," +
        				" ETS_ITEM_INFO EII" +
        				" WHERE" +
        				" EII.FINANCE_PROP = 'CTZC'" +
        				" AND EFCA.ASSETS_STATUS = 2" +
        				" AND EII.ASSET_ID = EFCA.ASSET_ID" +
        				" AND EII.RESPONSIBILITY_DEPT=AMD.DEPT_CODE" +
        				" AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID" +
        				" AND EII.ORGANIZATION_ID = ?" +
        				" AND ( " + SyBaseSQLUtil.isNull() + "  OR EFCA.ASSETS_DESCRIPTION LIKE ?)" +
        				" AND ( " + SyBaseSQLUtil.isNull() + "  OR EFCA.MODEL_NUMBER LIKE ?)" +
        				" AND ( " + SyBaseSQLUtil.isNull() + "  OR EFCA.ASSET_NUMBER LIKE ?)" +
        				" AND ( " + SyBaseSQLUtil.isNull() + "  OR EFCA.TAG_NUMBER LIKE ?)" +
        				" AND ( " + SyBaseSQLUtil.isNull() + "  OR EFCA.ASSETS_STATUS LIKE ?)";
        	
        	sqlArgs.add(sfUser.getOrganizationId());
        	sqlArgs.add(etsFaAssets.getAssetsDescription());
        	sqlArgs.add(etsFaAssets.getAssetsDescription());
        	sqlArgs.add(etsFaAssets.getModelNumber());
        	sqlArgs.add(etsFaAssets.getModelNumber());
        	sqlArgs.add(etsFaAssets.getAssetNumber());
        	sqlArgs.add(etsFaAssets.getAssetNumber());
        	sqlArgs.add(etsFaAssets.getTagNumber());
        	sqlArgs.add(etsFaAssets.getTagNumber());
        	sqlArgs.add(etsFaAssets.getAssetsStatus());
        	sqlArgs.add(etsFaAssets.getAssetsStatus());
        
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
	
	/**
     * 功能：框架自动生成ETS_FA_CT_ASSETS根据TAG_NUMBER的键值查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsFaAssetsDTO etsFaAssets = (EtsFaAssetsDTO) dtoParameter;
        String sqlStr = "";
        sqlStr = "SELECT"
        		+ " *"
        		+ " FROM"
        		+ " ETS_FA_CT_ASSETS EFCA"
        		+ " WHERE"
        		+ " EFCA.TAG_NUMBER = ?";
        sqlArgs.add(etsFaAssets.getTagNumber());
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
        return sqlModel;	
	}
}
