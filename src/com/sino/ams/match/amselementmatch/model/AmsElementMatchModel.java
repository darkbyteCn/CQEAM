package com.sino.ams.match.amselementmatch.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.match.amselementmatch.dto.AmsElementMatchDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

public class AmsElementMatchModel extends BaseSQLProducer {
	/**
	 * Function:		逻辑网络元素与资产目录对应Model
	 * Title: 			SinoApplication
	 * @param userAccount	登陆用户DTO
	 * @param dtoParameter	装载页面提交数据DTO
	 * Description:		Java Enterprise Edition 应用开发
	 * Copyright:		李轶版权所有Copyright (c)2009~2022。
	 * Copyright: 		其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。
	 * Copyright: 		作者授权北京思诺博信息技术有限公司在一定范围内使用
	 * Company: 		北京思诺博信息技术有限公司
	 * @author 			李轶
	 * @version 		0.1
	 * @Date			Apr 30, 2009
	 */
	public AmsElementMatchModel(BaseUserDTO userAccount, AmsElementMatchDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}
	
	private AmsElementMatchDTO dto = (AmsElementMatchDTO) super.dtoParameter;
	
	/**
	 * 
	 * Function:		查询所有逻辑网络元素分页数据
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @author  		李轶
	 * @Version 		0.1
	 * @Date:   		Apr 27, 2009
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		if("lne".equals(dto.getAccessType())){
			sqlStr = "SELECT ACD.CONTENT_ID,"
				+	" ACD.CONTENT_CODE,"
				+	" ACD.CONTENT_NAME,"
				+	" AL.AMS_LNE_ID,"
				+	" AL.NET_CATEGORY1,"
				+	" AL.NET_CATEGORY2,"
				+	" AL.NET_UNIT_CODE,"
				+	" AL.LOG_NET_ELE"
			+	" FROM AMS_LNE AL, AMS_LNE_CONTENT ALC, AMS_CONTENT_DIC ACD"
			+	" WHERE AL.NET_UNIT_CODE = ALC.LNE_CODE"
			+		" AND ALC.CONTENT_CODE = ACD.CONTENT_CODE"
			+		" AND ACD.ENABLE = 'Y'"
			+		" AND  (" + SyBaseSQLUtil.nullStringParam() + " OR ALC.CONTENT_CODE LIKE ? )"
			+		" AND  (" + SyBaseSQLUtil.nullStringParam() + " OR ACD.CONTENT_NAME LIKE ? )"
			+		" AND  (" + SyBaseSQLUtil.nullStringParam() + " OR AL.NET_UNIT_CODE LIKE UPPER(?) )"
			+		" AND  (" + SyBaseSQLUtil.nullStringParam() + " OR AL.LOG_NET_ELE LIKE ? )"
			+		" AND  (" + SyBaseSQLUtil.nullStringParam() + " OR AL.NET_CATEGORY1 LIKE ? )"
			+		" AND  (" + SyBaseSQLUtil.nullStringParam() + " OR AL.NET_CATEGORY2 LIKE ? )"
			+		" ORDER BY ACD.CONTENT_ID";
			
			  
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getContentCode());
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getContentName());
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getNetUnitCode());
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getLogNetEle());
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getNetCategory1());
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getNetCategory2());
		}else if("cex".equals(dto.getAccessType())){
			sqlStr = "SELECT ACD.CONTENT_ID,"
					+	"	 ACD.CONTENT_CODE,"
					+	"	 ACD.CONTENT_NAME,"
					+	"	 AC.AMS_CEX_ID,"
					+	"	 AC.INVEST_CAT_CODE,"
					+	"	 AC.INVEST_CAT_NAME,"
					+	"	 AC.INVEST_CATEGORY1,"
					+	"	 AC.INVEST_CATEGORY2"
					+	"	 FROM AMS_CEX AC, AMS_CEX_CONTENT ACC, AMS_CONTENT_DIC ACD"
					+	" WHERE AC.INVEST_CAT_CODE = ACC.CEX_CODE"
					+	"	 AND ACC.CONTENT_CODE = ACD.CONTENT_CODE"
					+	"	 AND ACD.ENABLE = 'Y'"
					+	"	 AND (" + SyBaseSQLUtil.nullStringParam() + " OR ACD.CONTENT_CODE LIKE ?)"
					+	"	 AND (" + SyBaseSQLUtil.nullStringParam() + " OR ACD.CONTENT_NAME LIKE ?)"
					+	"	 AND (" + SyBaseSQLUtil.nullStringParam() + " OR AC.INVEST_CAT_CODE  LIKE UPPER(?))"
					+	"	 AND (" + SyBaseSQLUtil.nullStringParam() + " OR AC.INVEST_CAT_NAME LIKE ?)"
					+	"	 AND (" + SyBaseSQLUtil.nullStringParam() + " OR AC.INVEST_CATEGORY1 LIKE ?)"
					+	" 	 AND (" + SyBaseSQLUtil.nullStringParam() + " OR AC.INVEST_CATEGORY2 LIKE ?)"
					+	" ORDER BY ACD.CONTENT_ID";
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getContentCode());
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getContentName());
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getInvestCatCode());
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getInvestCatName());
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getInvestCategory1());
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getInvestCategory2());
		}else if("ope".equals(dto.getAccessType())){
			sqlStr = "SELECT ACD.CONTENT_ID,"
				+	"		 ACD.CONTENT_CODE,"
				+	"		 ACD.CONTENT_NAME,"
				+	"		 AO.AMS_OPE_ID,"
				+	"		 AO.OPE_CODE,"
				+	"		 AO.OPE_NAME"
				+	" FROM AMS_OPE AO, AMS_OPE_CONTENT AOC, AMS_CONTENT_DIC ACD"
				+	" WHERE AO.OPE_CODE = AOC.OPE_CODE"
				+	"		AND AOC.CONTENT_CODE = ACD.CONTENT_CODE"
				+	"		AND ACD.ENABLE = 'Y'"
				+	"		AND (" + SyBaseSQLUtil.nullStringParam() + " OR ACD.CONTENT_CODE LIKE ?)"
				+	"		AND (" + SyBaseSQLUtil.nullStringParam() + " OR ACD.CONTENT_NAME LIKE ?)"
				+	"		AND (" + SyBaseSQLUtil.nullStringParam() + " OR AO.OPE_CODE  LIKE UPPER(?) )"	
				+	"		AND (" + SyBaseSQLUtil.nullStringParam() + " OR AO.OPE_NAME LIKE ? )"
				+	" ORDER BY ACD.CONTENT_ID";
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getContentCode());
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getContentName());
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getOpeCode());
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getOpeName());
		}else if("nle".equals(dto.getAccessType())){
			sqlStr = "SELECT ACD.CONTENT_ID,"
				+	"		 ACD.CONTENT_CODE,"
				+	"		 ACD.CONTENT_NAME,"
				+	"		 AN.AMS_LNE_ID,"
				+	"		 AN.LNE_CODE,"
				+	"		 AN.LNE_NAME"
				+	" FROM AMS_NLE AN, AMS_NLE_CONTENT ANC, AMS_CONTENT_DIC ACD"
				+	" WHERE AN.LNE_CODE = ANC.NLE_CODE"
				+	"		 AND ANC.CONTENT_CODE = ACD.CONTENT_CODE"
				+	"		 AND ACD.ENABLE = 'Y'"
				+	"		 AND (" + SyBaseSQLUtil.nullStringParam() + " OR ACD.CONTENT_CODE LIKE ?)"
				+	"		 AND (" + SyBaseSQLUtil.nullStringParam() + " OR ACD.CONTENT_NAME LIKE ?)"
				+	"		 AND (" + SyBaseSQLUtil.nullStringParam() + " OR AN.LNE_CODE  LIKE UPPER(?) )"
				+	"	  	 AND (" + SyBaseSQLUtil.nullStringParam() + " OR AN.LNE_NAME LIKE ? )"
				+	" ORDER BY ACD.CONTENT_ID";
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getContentCode());
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getContentName());
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getLneCode());
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getLneName());
		}		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * Function			新增逻辑网络元素与资产目录对应记录
	 * @return boolean	SQLModel
	 */	
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		if("lne".equals(dto.getAccessType())){
			sqlStr = "INSERT INTO AMS_LNE_CONTENT"
				+				"(IN_RANGE,"
				+				"CORRESPONDENCE,"
				+				"CONTENT_CODE,"
				+				"CONTENT_NAME,"
				+				"LNE_ID,"
				+				"LNE_CODE,"
				+				"LNE_NAME)"
				+			"VALUES"
				+				"('Y', 'Direct', ?, ?, ?, ?, ?)" ;
				
			sqlArgs.add(dto.getContentCode());
			sqlArgs.add(dto.getContentName());
			sqlArgs.add(dto.getAmsLneId());
			sqlArgs.add(dto.getNetUnitCode());
			sqlArgs.add(dto.getLogNetEle());
		}else if("cex".equals(dto.getAccessType())){
			sqlStr = "INSERT INTO AMS_CEX_CONTENT"
				+				"(IN_RANGE,"
				+				"CORRESPONDENCE,"
				+				"CONTENT_CODE,"
				+				"CONTENT_NAME,"
				+				"CEX_ID,"
				+				"CEX_CODE,"
				+				"CEX_NAME)"
				+			"VALUES"
				+				"('Y', 'Direct', ?, ?, ?, ?, ?)" ;
				
			sqlArgs.add(dto.getContentCode());
			sqlArgs.add(dto.getContentName());
			sqlArgs.add(dto.getAmsCexId());
			sqlArgs.add(dto.getInvestCatCode());
			sqlArgs.add(dto.getInvestCatName());
		}else if("ope".equals(dto.getAccessType())){
			sqlStr = "INSERT INTO AMS_OPE_CONTENT"
				+				"(IN_RANGE,"
				+				"CORRESPONDENCE,"
				+				"CONTENT_CODE,"
				+				"CONTENT_NAME,"
				+				"OPE_ID,"
				+				"OPE_CODE,"
				+				"OPE_NAME)"
				+			"VALUES"
				+				"('Y', 'Direct', ?, ?, ?, ?, ?)" ;
				
			sqlArgs.add(dto.getContentCode());
			sqlArgs.add(dto.getContentName());
			sqlArgs.add(dto.getAmsOpeId());
			sqlArgs.add(dto.getOpeCode());
			sqlArgs.add(dto.getOpeName());
		}else if("nle".equals(dto.getAccessType())){
			sqlStr = "INSERT INTO AMS_NLE_CONTENT"
				+				"(IN_RANGE,"
				+				"CORRESPONDENCE,"
				+				"CONTENT_CODE,"
				+				"CONTENT_NAME,"
				+				"NLE_ID,"
				+				"NLE_CODE,"
				+				"NLE_NAME)"
				+			"VALUES"
				+				"('Y', 'Direct', ?, ?, ?, ?, ?)" ;
				
			sqlArgs.add(dto.getContentCode());
			sqlArgs.add(dto.getContentName());
			sqlArgs.add(dto.getAmsLneId());
			sqlArgs.add(dto.getLneCode());
			sqlArgs.add(dto.getLneName());
			
		}
			
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能： 	批量执行删除逻辑网络元素与资产目录对应记录。
	 * @return SQLModel 删除用SQLModel
	 */
	public SQLModel deleteModel(AmsElementMatchDTO dto){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String  sqlStr = "DELETE FROM ";
		if("lne".equals(dto.getAccessType())){
			sqlStr += " AMS_LNE_CONTENT ";
		}else if("cex".equals(dto.getAccessType())){
			sqlStr += " AMS_CEX_CONTENT ";
		}else if("ope".equals(dto.getAccessType())){
			sqlStr += " AMS_OPE_CONTENT  ";
		}else if("nle".equals(dto.getAccessType())){
			sqlStr += " AMS_NLE_CONTENT";
		}			
		sqlStr += " WHERE"
		+ " 	CONTENT_CODE = ?";
		sqlArgs.add(dto.getContentCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;		
	}
	
}
