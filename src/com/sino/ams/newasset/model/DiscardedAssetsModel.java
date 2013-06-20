package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-10-14
 */
public class DiscardedAssetsModel extends AMSSQLProducer {
    private EtsFaAssetsDTO dto = null;

    public DiscardedAssetsModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        dto = (EtsFaAssetsDTO) dtoParameter;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		try {
			sqlStr = "SELECT EFA.ORGANIZATION_ID,\n" +
					 "       EFA.ASSET_NUMBER,\n" +
					 "       EFA.TAG_NUMBER,\n" +
					 "       EFA.ASSETS_DESCRIPTION,\n" +
					 "       EFA.MODEL_NUMBER,\n" +
					 "       EFA.DATE_PLACED_IN_SERVICE,\n" +
					 "       EFA.RETIRE_DATE,\n" +
					 "       EFA.ASSIGNED_TO_NAME,\n" +
					 "       EFA.ASSIGNED_TO_NUMBER,\n" +
					 "       AMS_PUB_PKG.GET_ORGNIZATION_NAME(EFA.ORGANIZATION_ID) ORGANIZATION_NAME,\n" +
					 "       EFA.FA_CATEGORY_CODE\n" +
					 "  FROM ETS_FA_ASSETS EFA\n" +
					 " WHERE EFA.IS_RETIREMENTS = 1 \n" +
					 "   AND EFA.TAG_NUMBER LIKE dbo.NVL(?, EFA.TAG_NUMBER)\n" +
					 "   AND EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFA.ASSETS_DESCRIPTION)\n" +
					 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.MODEL_NUMBER LIKE ?)\n" +
					 "   AND ORGANIZATION_ID = ISNULL(?, ORGANIZATION_ID)\n" +
					 "   AND EFA.RETIRE_DATE >= dbo.NVL(?, EFA.RETIRE_DATE)\n" +
					 "   AND EFA.RETIRE_DATE <= dbo.NVL(?, EFA.RETIRE_DATE)";
			sqlArgs.add(dto.getTagNumber());
			sqlArgs.add(dto.getAssetsDescription());
			sqlArgs.add(dto.getModelNumber());
			sqlArgs.add(dto.getModelNumber());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
		} catch (CalendarException e) {
			e.printLog();
			throw new SQLModelException(e);
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
