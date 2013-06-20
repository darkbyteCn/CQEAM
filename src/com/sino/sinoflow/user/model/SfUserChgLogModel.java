package com.sino.sinoflow.user.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.sinoflow.user.dto.SfUserChgLogDTO;

/**
 * <p>
 * Title: SinoApplication
 * </p>
 * <p>
 * Description: Java Enterprise Edition 平台应用开发基础框架
 * </p>
 * <p>
 * Copyright: 唐明胜版权所有Copyright (c) 2003~2007。
 * <p>
 * Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。
 * </p>
 * <p>
 * Copyright: 作者授权北京思诺博信息技术有限公司在一定范围内使用
 * </p>
 * <p>
 * Company: 北京思诺博信息技术有限公司
 * </p>
 * 
 * @author 唐明胜
 * @version 0.1
 */

public class SfUserChgLogModel extends BaseSQLProducer {

	/**
	 * 功能：用户维护Model构造函数
	 * 
	 * @param userAccount
	 *            BaseUserDTO 代表执行当前操作的用户
	 * @param dtoParameter
	 *            SfUserBaseDTO 代表当前操作的数据
	 */
	public SfUserChgLogModel(SfUserBaseDTO userAccount,
			SfUserChgLogDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成数据插入SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SfUserChgLogDTO dto = (SfUserChgLogDTO) dtoParameter;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = 
				  "INSERT INTO SF_USER_CHG_LOG\n" 
				+ "  (LOG_ID,\n"
				+ "   USER_ID,\n" 
				+ "   CHG_TYPE,\n" 
				+ "   OPERATOR,\n"
				+ "   OPERATOR_DATE,\n" 
				+ "   LOG_FROM,\n" 
				+ "   LOG_TO,\n"
				+ "   REMARK)\n" 
				+ "VALUES\n"
				+ "  (NEWID(), ?, ?, ?, GETDATE(), ?, ?, ?)";

		// sqlArgs.add(dto.getLogId());
		sqlArgs.add(dto.getUserId());
		sqlArgs.add(dto.getChgType());
		sqlArgs.add(dto.getOperator());
		// sqlArgs.add(dto.getOperatorDate());
		sqlArgs.add(dto.getLogFrom());
		sqlArgs.add(dto.getLogTo());
		sqlArgs.add(dto.getRemark());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成数据删除SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SfUserChgLogDTO dto = (SfUserChgLogDTO) dtoParameter;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();

		String sqlStr = "DELETE FROM" + " SF_USER_CHG_LOG" + " WHERE"
				+ " LOG_ID = ?";
		sqlArgs.add(dto.getLogId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	/**
	 * 功能：框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {

		SfUserBaseDTO dto = (SfUserBaseDTO) super.dtoParameter;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT LOG_ID,\n" + "       USER_ID,\n"
				+ "       CHG_TYPE,\n" + "       OPERATOR,\n"
				+ "       OPERATOR_DATE,\n" + "       LOG_FROM,\n"
				+ "       LOG_TO,\n" + "       REMARK\n"
				+ "  FROM SF_USER_CHG_LOG\n" + " WHERE LOG_ID = ?";

		sqlArgs.add(dto.getUserId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成页面翻页查询SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */

    /**
     * 功能：框架自动生成页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {

            SfUserChgLogDTO dto = (SfUserChgLogDTO) dtoParameter;
            List sqlArgs = new ArrayList();
            String sqlStr =
                    "SELECT LOG_ID,\n" +
                    "       USER_ID,\n" +
                    "       dbo.APP_GET_USER_NAME(USER_ID) USER_NAME,\n" +
                    "       CHG_TYPE,\n" +
                    "       OPERATOR,\n" +
                    "       dbo.APP_GET_USER_NAME(OPERATOR) OPERATOR_NAME,\n" +
                    "       OPERATOR_DATE,\n" +
                    "       LOG_FROM,\n" +
                    "       LOG_TO,\n" +
                    "       REMARK\n" +
                    "  FROM SF_USER_CHG_LOG\n" +
                    " WHERE (? = '' OR dbo.APP_GET_USER_NAME(USER_ID) = ?) " +
                    "   AND (? = '' OR dbo.APP_GET_USER_NAME(OPERATOR)= ?)\n" +
                    "   AND (? = '' OR OPERATOR_DATE >=ISNULL(?,OPERATOR_DATE))\n" +
                    "   AND (? = '' OR OPERATOR_DATE <=ISNULL(?,OPERATOR_DATE))\n" +
                    "   ORDER BY OPERATOR_DATE DESC\n";

            sqlArgs.add(dto.getUserName());
            sqlArgs.add(dto.getUserName());
            sqlArgs.add(dto.getOperatorName());
            sqlArgs.add(dto.getOperatorName());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getEndDate());
            sqlArgs.add(dto.getEndDate());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }
}
