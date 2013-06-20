package com.sino.ams.system.manydimensions.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.match.amselementmatch.dto.AmsElementMatchDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.dto.BaseUserDTO;

/**
 * User: 李轶
 * Date: 2009-6-16
 * Time: 17:32:55
 * Function:		投资分类属性维护
 */
public class CexModel extends AMSSQLProducer {
    private AmsElementMatchDTO dto = null;


    public CexModel(BaseUserDTO userAccount, AmsElementMatchDTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.dto =  dtoParameter;
    }

    /**
	 * 
	 * Function:		查询所有投资分类属性记录分页数据
	 * @return			SQLModel   返回页面翻页查询SQLModel
	 * @author  		李轶
	 * @Version 		0.1
	 * @Date:   		Apr 27, 2009
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT AC.AMS_CEX_ID,\n" +
				        "       AC.AMS_CEX_ID ROWNUM,\n" +
				        "       AC.INVEST_CATEGORY1,\n" +
				        "       AC.INVEST_CATEGORY2,\n" +
				        "       AC.INVEST_CAT_CODE,\n" +
				        "       AC.INVEST_CAT_NAME\n" +
				        "  FROM AMS_CEX AC\n" +
				        " WHERE (" + SyBaseSQLUtil.nullStringParam() + " OR AC.INVEST_CATEGORY1 LIKE ? )\n" +
				        "   AND (" + SyBaseSQLUtil.nullStringParam() + " OR AC.INVEST_CATEGORY2 LIKE ? )\n" +
				        "   AND (" + SyBaseSQLUtil.nullStringParam() + " OR AC.INVEST_CAT_CODE LIKE ? )\n" +
				        "   AND (" + SyBaseSQLUtil.nullStringParam() + " OR AC.INVEST_CAT_NAME LIKE ? )";
		
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getInvestCategory1() );
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getInvestCategory2() );
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getInvestCatCode() );
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getInvestCatName() );
		
//		sqlArgs.add(dto.getInvestCategory1());
//		sqlArgs.add(dto.getInvestCategory2());
//		sqlArgs.add(dto.getInvestCatCode());
//		sqlArgs.add(dto.getInvestCatName());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：	通过框架自动根据SQLModel, 删除投资分类属性。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		
		String[] tmp = dto.getAmsCexId().split(",");
		String amsCexId = "";
		for (int i = 0; i < tmp.length; i++) {
			amsCexId += "'" + tmp[i] + "',";
		}
		amsCexId = amsCexId.substring(0, amsCexId.length() - 1);

		String sqlStr = "DELETE FROM"
			+ " 				AMS_CEX "
			+ " 		 WHERE"
			//+ " 				AMS_CEX_ID IN ('" + dto.getAmsCexId() + "')";
			+ " 				AMS_CEX_ID IN (" + amsCexId + ")";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;		
	}
	
	/**
	 * Function:			得到选定的投资分类属性记录
	 * @return SQLModel 	删除用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr =  "";
		sqlArgs.add(dto.getAmsLneId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：框架自动生成数据插入SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = " INSERT INTO AMS_CEX\n" +
						"  (AMS_CEX_ID,\n" +
						"   INVEST_CATEGORY1,\n" +
						"   INVEST_CATEGORY2,\n" +
						"   INVEST_CAT_CODE,\n" +
						"   INVEST_CAT_NAME)\n" +
						" VALUES\n" +
						"  (NEWID(), ?, ?, ?, ?)";
		
		sqlArgs.add(dto.getInvestCategory1());
		sqlArgs.add(dto.getInvestCategory2());
		sqlArgs.add(dto.getInvestCatCode());
		sqlArgs.add(dto.getInvestCatName());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：构造判断投资分类编码是否存在的SQL
	 * @return SQLModel
	 */
	public SQLModel getObjectEsistModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " AMS_CEX AC"
						+ " WHERE"
						+ " AC.INVEST_CAT_CODE = ?";
		sqlArgs.add(dto.getInvestCatCode());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	
	

}
