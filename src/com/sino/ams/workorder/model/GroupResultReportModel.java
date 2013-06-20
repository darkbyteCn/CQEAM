package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 中国移动资产实物管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class GroupResultReportModel extends AMSSQLProducer {

	public GroupResultReportModel(SfUserDTO userAccount, EtsWorkorderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：按部门统计巡检地点数
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT"
							+ " SG.GROUP_ID,"
							+ " SG.GROUP_NAME,"
							+ " COUNT(TMP_V.GROUP_ID) SCAN_COUNT"
							+ " FROM"
							+ " SF_GROUP      SG,"
							+ " (SELECT DISTINCT"
							+ " EW.GROUP_ID,"
							+ " EW.WORKORDER_OBJECT_NO"
							+ " FROM"
							+ " ETS_WORKORDER      EW"
							+ " WHERE"
							+ " EW.WORKORDER_TYPE = ?"
							+ " AND (EW.WORKORDER_FLAG = ? OR EW.WORKORDER_FLAG = ?)"
							+ " AND EW.GROUP_ID = dbo.NVL(?, EW.GROUP_ID)"
							+ " AND ((EW.UPLOAD_DATE >= dbo.NVL(?, EW.UPLOAD_DATE)"
							+ " AND EW.UPLOAD_DATE <= dbo.NVL(?, EW.UPLOAD_DATE))"
							+ " OR (EW.UPLOAD_DATE >= dbo.NVL(?, EW.UPLOAD_DATE)"
							+ " AND EW.UPLOAD_DATE <= dbo.NVL(?, EW.UPLOAD_DATE)))) TMP_V"
							+ " WHERE"
							+ " SG.GROUP_ID *= TMP_V.GROUP_ID"
							+ " AND SG.ORGANIZATION_ID = ?"
							+ " AND SG.GROUP_ID = dbo.NVL(?, SG.GROUP_ID)"
							+ " GROUP BY"
							+ " SG.GROUP_ID,"
							+ " SG.GROUP_NAME";
			EtsWorkorderDTO dto = (EtsWorkorderDTO) dtoParameter;
			sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
			sqlArgs.add(DictConstant.WORKORDER_STATUS_UPLOADED);
			sqlArgs.add(DictConstant.WORKORDER_STATUS_ACHIEVED);
			sqlArgs.add(dto.getGroupId());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(dto.getGroupId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
