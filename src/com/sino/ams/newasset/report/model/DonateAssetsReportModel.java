package com.sino.ams.newasset.report.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;


/**
 * <p>Title: DonateAssetsReportModel</p>
 * <p>Description:程序自动生成SQL构造器“DonateAssetsReportModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 李轶
 * @version 1.0
 */


public class DonateAssetsReportModel extends AMSSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：捐赠资产统计 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
	 */
	public DonateAssetsReportModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	
	/**
	 * 功能：框架自动生成捐赠资产统计 页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{ 
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String sqlStr = "SELECT   EOCM.COMPANY,\n"		+
							"	  AMD.DEPT_NAME,\n"		+
				           "      ESI.ITEM_NAME,\n" +
				           "      ESI.ITEM_SPEC,\n" +
				           "      EFAHR.ASSET_ID,\n" +
				           "      EII.BARCODE,\n" +
				           "       1 ITEM_QTY,\n" +
				           "	  EFAHR.COST\n"	+
				          "  FROM ETS_ITEM_INFO    		EII,\n" +
				           "       ETS_SYSTEM_ITEM  		ESI,\n" +
				           "       ETS_FA_ASSETS_HIS_REP    EFAHR,\n" +
				           "       AMS_MIS_DEPT     		AMD,\n" +
		                    "      ETS_OU_CITY_MAP 			EOCM,\n" +
		                    "      ETS_ITEM_MATCH  			EIM,\n" +
		                    "	   ETS_ITEM_INFO_ATTR_CHG   EIIAC\n"	+
				           " WHERE EII.ITEM_STATUS = 'DONATE'\n" +
				           "   AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
                    "          AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                    "          AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
                    "   	   AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                    "          AND EOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
                    "          AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                    "		   AND EFAHR.PERIOD_NAME = EIIAC.PERIOD_NAME \n"	+
			        "          AND EFAHR.PERIOD_NAME = ?\n" +

                    "          AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCM.ORGANIZATION_ID = ?)\n" +       
                   "   		   AND AMD.DEPT_CODE = ISNULL(?, AMD.DEPT_CODE)" +
                   "   		   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)"	+
                   "   		   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?) "	+
                   "     ORDER BY EII.SYSTEMID";
		
//		 sqlArgs.add(dto.getPeriodNameByHisRep());
         sqlArgs.add(dto.getOrganizationId());
         sqlArgs.add(dto.getOrganizationId());
	      
	     sqlArgs.add(dto.getResponsibilityDept());
	     sqlArgs.add(dto.getItemName());
	     sqlArgs.add(dto.getItemName());
	     sqlArgs.add(dto.getBarcode());
	     sqlArgs.add(dto.getBarcode());

         sqlModel.setSqlStr(sqlStr);
		 sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}

}
