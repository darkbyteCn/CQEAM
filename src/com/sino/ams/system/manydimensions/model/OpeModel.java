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
 * Function:		业务平台属性维护
 */
public class OpeModel extends AMSSQLProducer {
    private AmsElementMatchDTO dto = null;


    public OpeModel(BaseUserDTO userAccount, AmsElementMatchDTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.dto =  dtoParameter;
    }

    /**
	 * 
	 * Function:		查询所有业务平台属性记录分页数据
	 * @return			SQLModel   返回页面翻页查询SQLModel
	 * @author  		李轶
	 * @Version 		0.1
	 * @Date:   		Apr 27, 2009
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT AO.AMS_OPE_ID, \n" +
				        "       AO.AMS_OPE_ID ROWNUM, \n" +
				        "       AO.OPE_CODE, \n" +
				        "       AO.OPE_NAME\n" +
				        "  FROM AMS_OPE AO\n" +
				        " WHERE (" + SyBaseSQLUtil.nullStringParam() + " OR AO.OPE_CODE LIKE ? )\n" +
				        "   AND (" + SyBaseSQLUtil.nullStringParam() + " OR AO.OPE_NAME LIKE ? )";
		
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getOpeCode() );
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getOpeName() );
//		sqlArgs.add(dto.getOpeCode());
//		sqlArgs.add(dto.getOpeName());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：	通过框架自动根据SQLModel, 删除业务平台属性。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		
		String[] tmp = dto.getAmsOpeId().split(",");
		String amsOpeId = "";
		for (int i = 0; i < tmp.length; i++) {
			amsOpeId += "'" + tmp[i] + "',";
		}
		amsOpeId = amsOpeId.substring(0, amsOpeId.length() - 1);
		
		String sqlStr = "DELETE FROM"
			+ " 				AMS_OPE "
			+ " 		 WHERE"
			//+ " 				AMS_OPE_ID IN ('" + dto.getAmsOpeId() + "')";
			+ " 				AMS_OPE_ID IN (" + amsOpeId + ")";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;		
	}
	
	/**
	 * Function:			得到选定的业务平台属性记录
	 * @return SQLModel 	删除用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr =  "";
		sqlArgs.add(dto.getAmsOpeId());
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
		String sqlStr = "INSERT INTO AMS_OPE\n" +
						"  (AMS_OPE_ID, OPE_CODE, OPE_NAME)\n" +
						" VALUES\n" +
						"  (NEWID(), ?, ?)";
		sqlArgs.add(dto.getOpeCode());
		sqlArgs.add(dto.getOpeName());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：构造判断业务平台编码是否存在的SQL
	 * @return SQLModel
	 */
	public SQLModel getObjectEsistModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " AMS_OPE AO"
						+ " WHERE"
						+ " AO.OPE_CODE = ?";
		sqlArgs.add(dto.getOpeCode());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	
	

}
