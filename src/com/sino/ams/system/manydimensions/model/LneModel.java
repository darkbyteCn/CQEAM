package com.sino.ams.system.manydimensions.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.match.amselementmatch.dto.AmsElementMatchDTO;
import com.sino.ams.system.county.dto.EtsCountyDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * User: 李轶
 * Date: 2009-6-16
 * Time: 17:32:55
 * Function:		逻辑网络元素属性维护
 */
public class LneModel extends AMSSQLProducer {
    private AmsElementMatchDTO dto = null;


    public LneModel(BaseUserDTO userAccount, AmsElementMatchDTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.dto =  dtoParameter;
    }

    /**
	 * 
	 * Function:		查询所有逻辑网络元素属性记录分页数据
	 * @return			SQLModel   返回页面翻页查询SQLModel
	 * @author  		李轶
	 * @Version 		0.1
	 * @Date:   		Apr 27, 2009
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT AL.AMS_LNE_ID,\n" +
				        "       AL.AMS_LNE_ID ROWNUM,\n" +
				        "       AL.NET_CATEGORY1,\n" +
				        "       AL.NET_CATEGORY2,\n" +
				        "       AL.NET_CATEGORY1_CODE,\n" +
				        "       AL.NET_CATEGORY2_CODE,\n" +
				        "       AL.ASSET_RANGE,\n" +
				        "       AL.ORDER_ID,\n" +
				        "       AL.NET_UNIT_CODE,\n" +
				        "       AL.LOG_NET_ELE,\n" +
				        "       AL.ENABLED,\n" +
				        "       AL.COST_TYPE,\n" +
				        "       AL.ENG_AB\n" +
				        "  FROM AMS_LNE AL\n" +
				        " WHERE (" + SyBaseSQLUtil.nullStringParam() + " OR  AL.NET_CATEGORY1 LIKE ? )\n" +
				        "   AND (" + SyBaseSQLUtil.nullStringParam() + " OR AL.NET_CATEGORY2 LIKE ? )\n" +
				        "   AND (" + SyBaseSQLUtil.nullStringParam() + " OR AL.NET_UNIT_CODE LIKE ? )\n" +
				        "   AND (" + SyBaseSQLUtil.nullStringParam() + " OR AL.LOG_NET_ELE LIKE ? )" +
				        "   AND (" + SyBaseSQLUtil.nullStringParam() + " OR AL.ENABLED LIKE ? )\n" +
				        "   AND (" + SyBaseSQLUtil.nullStringParam() + " OR AL.COST_TYPE LIKE ? )\n" ;
		
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getNetCategory1() );
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getNetCategory2() );
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getNetUnitCode() );
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getLogNetEle() );
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getEnabled() );
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getCostType() );
//		sqlArgs.add(dto.getNetCategory1());
//		sqlArgs.add(dto.getNetCategory2());
//		sqlArgs.add(dto.getNetUnitCode());
//		sqlArgs.add(dto.getLogNetEle());
		if(!dto.getAmsLneId().equals("")){
			sqlStr +="   AND (" + SyBaseSQLUtil.nullStringParam() + " OR AL.AMS_LNE_ID LIKE ? )\n" ;
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getAmsLneId());
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：	通过框架自动根据SQLModel, 删除逻辑网络元素属性。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		
		String[] tmp = dto.getAmsLneId().split(",");
		String amsLneId = "";
		for (int i = 0; i < tmp.length; i++) {
			amsLneId += "'" + tmp[i] + "',";
		}
		amsLneId = amsLneId.substring(0, amsLneId.length() - 1);
		
		String sqlStr = "UPDATE "
			+ " 		 AMS_LNE "
			+ "          SET ENABLED   = 'N' "
			+ " 		 WHERE"
			//+ " 				AMS_LNE_ID IN ('" + dto.getAmsLneId() + "')";
			+ " 				AMS_LNE_ID IN (" + amsLneId + ")";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;		
	}
	
	/**
	 * Function:			得到选定的逻辑网络元素属性记录
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
		String sqlStr = "INSERT INTO AMS_LNE\n" +
						"  (AMS_LNE_ID,\n" +
						"   NET_CATEGORY1,\n" +
						"   NET_CATEGORY2,\n" +
						"   NET_CATEGORY1_CODE,\n" +
						"   NET_CATEGORY2_CODE,\n" +
						"   NET_UNIT_CODE,\n" +
						"   LOG_NET_ELE,\n" +
						"   ENABLED,\n" +
						"   COST_TYPE,\n" +
						"   ENG_AB)\n" +
						" VALUES\n" +
						"  ( NEWID(), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		sqlArgs.add(dto.getNetCategory1());
		sqlArgs.add(dto.getNetCategory2());
		sqlArgs.add(dto.getNetCategory1Code());
		sqlArgs.add(dto.getNetCategory2Code());
		sqlArgs.add(dto.getNetUnitCode());
		sqlArgs.add(dto.getLogNetEle());
		sqlArgs.add(dto.getEnabled());
		sqlArgs.add(dto.getCostType());
		sqlArgs.add(dto.getEngAb());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：构造判断逻辑网络元素编码是否存在的SQL
	 * @return SQLModel
	 */
	public SQLModel getObjectEsistModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " AMS_LNE AL"
						+ " WHERE"
						+ " AL.NET_UNIT_CODE = ?";
		sqlArgs.add(dto.getNetUnitCode());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	/**
	 * 功能：构造判断逻辑网络元素ID是否存能失效
	 * @return SQLModel
	 */
	public SQLModel isValidity() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " ETS_ITEM_INFO EII,"
						+ " AMS_LNE AL"
						+ " WHERE"
						+ " AL.AMS_LNE_ID = EII.LNE_ID"
						+ " AND EII.LNE_ID = ?";
		sqlArgs.add(dto.getAmsLneId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	/**
     * 功能：框架自动生成数据插入SQLModel，请根据实际需要修改。。
     *
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE "
					  + " AMS_LNE "
			          + " SET "
			          + " NET_CATEGORY1 = ? ,"
			          + " NET_CATEGORY2 = ? ,"
			          + " NET_UNIT_CODE = ? ,"
			          + " LOG_NET_ELE = ? ,"
			          + " ENG_AB = ? ,"
			          + " COST_TYPE = ? ,"
			          + " LAST_UPDATE_BY = ? ,"
			          + " LAST_UPDATE_DATE = GETDATE() "
			          + " WHERE"
			          + " AMS_LNE_ID LIKE ? ";
        sqlArgs.add(dto.getNetCategory1());
        sqlArgs.add(dto.getNetCategory2());
        sqlArgs.add(dto.getNetUnitCode());
        sqlArgs.add(dto.getLogNetEle());
        sqlArgs.add(dto.getEngAb());
        sqlArgs.add(dto.getCostType());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getAmsLneId());
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
	
    /**
	 * 功能：构造判断逻辑网络元素专业1編碼是否存
	 * @return SQLModel
	 */
	public SQLModel isValidityNetUnitCode() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "select 1 from ABM_OBJ WHERE OBJID =  ? ";
		sqlArgs.add(dto.getNetUnitCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	public SQLModel insertNetUnitCode() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO dbo.ABM_OBJ(OBJID, OBJNAME, OBJSNAME, OBJDESCR, OBJCATA, OBJTYPE)  " +
		" SELECT NET_UNIT_CODE, LOG_NET_ELE, NULL, NULL, NET_CATEGORY2_CODE, 'LNE' FROM AMS_LNE WHERE NET_UNIT_CODE = ? ";
		sqlArgs.add(dto.getNetUnitCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	 /**
	 * 功能：构造判断逻辑网络元素专业2編碼是否存
	 * @return SQLModel
	 */
	public SQLModel isValidityCode2() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "select 1 from ABM_OBJCATA WHERE OBJCATA = ? ";
		sqlArgs.add(dto.getNetCategory2Code());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	public SQLModel insertCode2() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO dbo.ABM_OBJCATA(OBJTYPE, OBJCATA, OBJCATANAME, LEV, PID, SEQ) " +
		" SELECT 'LNE', NET_CATEGORY2_CODE, NET_CATEGORY2, 0,NULL,0 FROM AMS_LNE WHERE NET_UNIT_CODE = ? ";
		sqlArgs.add(dto.getNetUnitCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}
