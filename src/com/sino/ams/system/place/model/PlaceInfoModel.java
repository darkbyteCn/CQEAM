package com.sino.ams.system.place.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.place.dto.PlaceInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class PlaceInfoModel extends AMSSQLProducer {
	public PlaceInfoModel(SfUserDTO userAccount, PlaceInfoDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：构造翻页查询SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		PlaceInfoDTO dto = (PlaceInfoDTO) dtoParameter;
		List sqlArgs = new ArrayList(); 
		String sb = "SELECT"
			+ " MFFV.FLEX_VALUE_ID,"
			+ " MFFV.FLEX_VALUE_SET_ID,"
			+ " MFFV.FLEX_VALUE,"
			+ " MFFV.FLEX_VALUE_MEANING,"
			+ " MFFV.DESCRIPTION,"
			+ " MFFV.PARENT_FLEX_VALUE_LOW,"
			+ " MFFV.PARENT_FLEX_VALUE_HIGH,"
			+ " MFFV.SUMMARY_FLAG,"
			+ " MFFV.START_DATE_ACTIVE,"
			+ " MFFV.END_DATE_ACTIVE,"
			+ " MFFV.COMPANY_CODE,"
			+ " MFFV.COUNTY_CODE,"
			+ " EC.COUNTY_NAME,"
			+ " EOCM.COMPANY,"
			+ "	MFFVS.FLEX_VALUE_SET_NAME"
			+ " FROM"
			+ " M_FND_FLEX_VALUES     MFFV," 
			+ " ETS_COUNTY     EC," 
			+ " ETS_OU_CITY_MAP     EOCM," 
			+ " M_FND_FLEX_VALUE_SETS     MFFVS" 
			+ " WHERE" 
			//+ " EC.COMPANY_CODE =EOCM.COMPANY_CODE"
			+ " MFFV.COMPANY_CODE *=EOCM.COMPANY_CODE "
			+ " AND MFFV.COUNTY_CODE *=EC.COUNTY_CODE "
			+ " AND MFFV.FLEX_VALUE_SET_ID =MFFVS.FLEX_VALUE_SET_ID "
			+ " AND MFFVS.FLEX_VALUE_SET_NAME = ?";
			sqlArgs.add(dto.getLoc1SetName());
		if (dto.getFlexValueId()!=0) {
			sb =sb+" AND MFFV.FLEX_VALUE_ID = ?";
			sqlArgs.add(dto.getFlexValueId());
		}
		if (!dto.getFlexValue().equals("")) {
			sb =sb+" AND MFFV.FLEX_VALUE = ?";
			sqlArgs.add(dto.getFlexValue());
		}
		if (!dto.getCompanyCode().equals("")) {
			sb =sb+" AND MFFV.COMPANY_CODE = ?";
			sqlArgs.add(dto.getCompanyCode());
		}
		if (!dto.getCountyCode().equals("")) {
			sb =sb+" AND MFFV.COUNTY_CODE = ?";
			sqlArgs.add(dto.getCountyCode());
		}
		 	
		sqlModel.setSqlStr(sb);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：根据主键修改数据
	 * @return SQLModel
	 */
	public SQLModel getDataUpdateModel(PlaceInfoDTO dto) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " M_FND_FLEX_VALUES"
						+ " SET"
						+ " COUNTY_CODE = ?,"
						+ " COMPANY_CODE = ?" 
						+ " WHERE"
						+ " FLEX_VALUE_ID = ?"; 
		sqlArgs.add(dto.getCountyCode());
		sqlArgs.add(dto.getCompanyCode()); 
		sqlArgs.add(dto.getFlexValueId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
