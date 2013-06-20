package com.sino.ams.instrument.bean;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.instrument.constant.InstrumentLookUpConstant;
import com.sino.ams.instrument.dto.AmsInstrumentEamYbChkMaintainDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.lookup.LookUpModel;
import com.sino.base.lookup.LookUpProp;
import com.sino.framework.dto.BaseUserDTO;

public class InstrumentLoopUpModel extends LookUpModel {

	private SfUserDTO user = null;

	public InstrumentLoopUpModel(BaseUserDTO userAccount, DTO dtoParameter, LookUpProp lookProp) {
		super(userAccount, dtoParameter, lookProp);
		this.user = (SfUserDTO) userAccount;
	}

	/**
	 * 功能：构造查询SQL。由具体需要LookUp操作的应用实现
	 */
	protected void produceSQLModel() {
		sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		String lookUpName = lookProp.getLookUpName();
		if (lookUpName.equals(InstrumentLookUpConstant.LOOK_UP_CHECK_USER)) { //选择检修人
			SfUserDTO dto = (SfUserDTO) dtoParameter;
			sqlStr = "SELECT"
				 + " SU.USER_ID,"
				 + " SU.USERNAME"
				 + " FROM"
				 + " SF_USER SU"
				 + " WHERE"
				 + " ( " + SyBaseSQLUtil.isNull() + "  OR SU.USER_ID = ?)"
				 + " AND ( " + SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?)";
			sqlArgs.add(dto.getUserId());
			sqlArgs.add(dto.getUserId());
			sqlArgs.add(dto.getUsername());
			sqlArgs.add(dto.getUsername());
		} else if(lookUpName.equals(InstrumentLookUpConstant.LOOK_UP_CHECK_RESULT)) { //选择检修结果
			sqlStr = "SELECT"
					 + " EFV.CODE CHECK_STATUS,"
					 + " EFV.VALUE CHECK_STATUS_NAME"
					 + " FROM"
					 + " ETS_FLEX_VALUE_SET EFVS,"
					 + " ETS_FLEX_VALUES    EFV"
					 + " WHERE"
					 + " EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
					 + " AND EFVS.CODE = 'YB_CHECK_RESULT'";
		} else if(lookUpName.equals(InstrumentLookUpConstant.LOOK_UP_TASK_ID)) { //选择检修任务批号
			AmsInstrumentEamYbChkMaintainDTO dto = (AmsInstrumentEamYbChkMaintainDTO) dtoParameter;
			sqlStr = "SELECT"
				 + " EYCT.TASK_ID,"
				 + " EYCT.TASK_NAME"
				 + " FROM"
				 + " EAM_YB_CHK_TASK EYCT"
				 + " WHERE"
				 + " ( " + SyBaseSQLUtil.isNull() + "  OR EYCT.TASK_ID LIKE ?)"
				 + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EYCT.TASK_NAME LIKE ?)";
			sqlArgs.add(dto.getTaskId());
			sqlArgs.add(dto.getTaskId());
			sqlArgs.add(dto.getTaskName());
			sqlArgs.add(dto.getTaskName());
		}

		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
	}

}
