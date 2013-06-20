package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Author: 李轶 Date: 2009-6-4 Time: 9:29:55 Function: 转资清单查询Model
 */
public class TrunListQueryModel extends BaseSQLProducer {
	EtsWorkorderDTO workorderDTO = null;
	SfUserDTO sfUser = null;

	public TrunListQueryModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.workorderDTO = (EtsWorkorderDTO) dtoParameter;
		sfUser = (SfUserDTO) userAccount;
	}

	/**
	 * 功能：删除ETS_ITEM_INFO表中
	 */
	public String  getDelInsertModel(String projectId,String address) {
	  
		String sqlStr = " DELETE FROM ETS_ITEM_INFO_TEMP  "
				+ " WHERE ADDRESS_ID = '" + address +"'" +" AND PROJECTID = '"+projectId + "'";
		
		return sqlStr;
	}
	
	/**
	 * 功能：插入ETS_ITEM_INFO_TEMP表
	 */
	public String  getDataInsertModel(String projectId,String address) {
	  
		String sqlStr = "INSERT INTO ETS_ITEM_INFO(SYSTEMID,BARCODE,ITEM_CODE,ITEM_QTY,UNIT_OF_MEASURE,MIS_ITEMNAME,"
				+ "MIS_ITEMSPEC,RESPONSIBILITY_USER,RESPONSIBILITY_DEPT,ADDRESS_ID,ORGANIZATION_ID,FINANCE_PROP,ITEM_STATUS,"
				+ "PROJECTID,REMARK,CREATION_DATE,CREATED_BY,LAST_UPDATE_DATE,LAST_UPDATE_BY,MANUFACTURER_ID,CONTENT_CODE,"
				+ "CONTENT_NAME,IS_TD,CONSTRUCT_STATUS,MAINTAIN_USER,MAINTAIN_DEPT,START_DATE"
				+ ") SELECT SYSTEMID,BARCODE,ITEM_CODE,ITEM_QTY,UNIT_OF_MEASURE,MIS_ITEMNAME,"
				+ "MIS_ITEMSPEC,RESPONSIBILITY_USER,RESPONSIBILITY_DEPT,ADDRESS_ID,ORGANIZATION_ID,FINANCE_PROP,ITEM_STATUS,"
				+ "PROJECTID,REMARK,CREATION_DATE,CREATED_BY,LAST_UPDATE_DATE,LAST_UPDATE_BY,MANUFACTURER_ID,CONTENT_CODE,"
				+ "CONTENT_NAME,IS_TD,CONSTRUCT_STATUS,MAINTAIN_USER,MAINTAIN_DEPT,START_DATE FROM ETS_ITEM_INFO_TEMP  "
				+ " WHERE ADDRESS_ID = '" + address +"'" +" AND PROJECTID = '"+projectId + "'";
		  
		return sqlStr;
	}

	/**
	 * 功能：查询ETS_ITEM_INFO_TEMP表
	 */
	public String  getAddressDataQueryModel(String projectId ,String address) {
	  
//		String sqlStr = " SELECT SYSTEMID,BARCODE,ITEM_CODE,ITEM_QTY,UNIT_OF_MEASURE,MIS_ITEMNAME,"
//				+ "MIS_ITEMSPEC,RESPONSIBILITY_USER,RESPONSIBILITY_DEPT,ADDRESS_ID,ORGANIZATION_ID,FINANCE_PROP,ITEM_STATUS,"
//				+ "PROJECTID,REMARK,CREATION_DATE,CREATED_BY,LAST_UPDATE_DATE,LAST_UPDATE_BY,MANUFACTURER_ID,CONTENT_CODE,"
//				+ "CONTENT_NAME,IS_TD,CONSTRUCT_STATUS,MAINTAIN_USER,MAINTAIN_DEPT,START_DATE FROM ETS_ITEM_INFO_TEMP  ";
		
		String sqlStr =" SELECT EI.ADDRESS_ID,EI.PROJECTID,EO.WORKORDER_OBJECT_CODE,EO.WORKORDER_OBJECT_NAME,EPA.SEGMENT1,EPA.NAME" 
			          +" FROM ETS_ITEM_INFO_TEMP EI,"
			          +" ETS_OBJECT  EO,"
			          +" AMS_OBJECT_ADDRESS AOA,"
			          +" ETS_PA_PROJECTS_ALL EPA"
			          +" WHERE AOA.OBJECT_NO=EO.WORKORDER_OBJECT_NO"
			          +" AND   EI.ADDRESS_ID=AOA.ADDRESS_ID"
			          +" AND   EI.PROJECTID=EPA.PROJECT_ID";
//			          +" AND   EO.WORKORDER_OBJECT_CODE='"+address+"'"
//			          +" AND   EPA.SEGMENT1='"+projectId+"'";
				
		
		if(projectId != null && !projectId.trim().equals("") && address != null && !address.trim().equals("")){
			sqlStr = sqlStr+ " AND   EO.WORKORDER_OBJECT_CODE='"+address+"'" +" AND   EPA.SEGMENT1='"+projectId+"'";
		}
		else if(projectId != null && !projectId.trim().equals("")){
			sqlStr = sqlStr+ " AND   EPA.SEGMENT1='"+projectId+"'";
		}
		else if( address != null && !address.trim().equals("")){
			sqlStr = sqlStr+ " AND   EO.WORKORDER_OBJECT_CODE='"+address+"'";
		}
		 		
		return sqlStr;
	}
	
	/**
	 * Function: 获取转资清单查询的SQLModel
	 * 
	 * @author 李轶
	 * @return SQLModel
	 * @date 2009-6-4
	 * @time 9:31:55
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT EO.WORKORDER_OBJECT_CODE ASSETS_LOCATION_CODE, \n");
		sb.append("        EO.WORKORDER_OBJECT_NAME ASSETS_LOCATION, \n");
		sb.append("        EPPA.SEGMENT1, \n");
		sb.append("        EPPA.NAME, \n");

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append("        dbo.APP_GET_ORGNIZATION_NAME(EII.ORGANIZATION_ID) ORG_NAME, \n");
		} else {
			sb.append("        dbo.APP_GET_ORGNIZATION_NAME(EIIT.ORGANIZATION_ID) ORG_NAME, \n");
		}

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append("        EII.BARCODE, \n");
		} else {
			sb.append("        EIIT.BARCODE, \n");
		}

		sb.append("        ESI.ITEM_NAME, \n");
		sb.append("        ESI.ITEM_SPEC, \n");

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append("        STR( EII.ITEM_QTY , 16 , 5 ) ITEM_QTY , \n");
		} else {
			sb.append("        STR( EIIT.ITEM_QTY , 16 , 5 ) ITEM_QTY , \n");
		}
		
		sb.append("        ACD.UNIT_OF_MEASURE,   \n");

		sb.append("          dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_DESC, \n");
		// sb.append(
		// "        dbo.AMP_GET_DEPT_NAME( EII.RESPONSIBILITY_DEPT ) DEPT_NAME, \n");
		sb.append("        AMD.DEPT_NAME, \n");
		sb.append("        AMP.USER_NAME,   \n");		

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append("        EII.MAINTAIN_DEPT, \n");
		} else {
			sb.append("        EIIT.MAINTAIN_DEPT, \n");
		}

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append("        EII.MAINTAIN_USER, \n");
		} else {
			sb.append("        EIIT.MAINTAIN_USER, \n");
		}

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append("        EII.CREATION_DATE, \n");
		} else {
			sb.append("        EIIT.CREATION_DATE, \n");
		}

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append("        EII.START_DATE, \n");
		} else {
			sb.append("        EIIT.START_DATE, \n");
		}

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append("        dbo.GET_PRINT_HISTORY_COUNT( EII.ORGANIZATION_ID , EII.BARCODE ) PRINT_NUM , \n"); // 打印次数
		} else {
			sb.append("        dbo.GET_PRINT_HISTORY_COUNT( EIIT.ORGANIZATION_ID , EIIT.BARCODE ) PRINT_NUM , \n"); // 打印次数
		}

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append("        EII.LAST_UPDATE_DATE, \n");
		} else {
			sb.append("        EIIT.LAST_UPDATE_DATE, \n");
		}

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append("        EII.CONTENT_CODE, \n");
		} else {
			sb.append("        EIIT.CONTENT_CODE, \n");
		}

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append("        EII.CONTENT_NAME  \n");
		} else {
			sb.append("        EIIT.CONTENT_NAME  \n");
		}

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append("   FROM ETS_ITEM_INFO       EII, \n");
		} else {
			sb.append("   FROM ETS_ITEM_INFO_TEMP       EIIT, \n");
		}

		sb.append("        ETS_OBJECT          EO, \n");
		sb.append("        AMS_OBJECT_ADDRESS  AOA, \n");
		sb.append("        ETS_SYSTEM_ITEM     ESI, \n");
		sb.append("        ETS_PA_PROJECTS_ALL EPPA, \n");
		sb.append("        AMS_MIS_DEPT        AMD, \n");
		sb.append("        AMS_MIS_EMPLOYEE    AMP, \n");

		// 添加 "       AMS_CONTENT_DIC    ACD\n" 资产目录表
		sb.append("   AMS_CONTENT_DIC    ACD \n");

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append("  WHERE EII.ITEM_CODE = ESI.ITEM_CODE \n");
		} else {
			sb.append("  WHERE EIIT.ITEM_CODE = ESI.ITEM_CODE \n");
		}

		// 判读资产表中是否存在此信息
		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append("   AND EII.CONTENT_CODE *= ACD.CONTENT_CODE \n");
		} else {
			sb.append("   AND EIIT.CONTENT_CODE *= ACD.CONTENT_CODE \n");
		}

		// sb.append( "    AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE \n");

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			if (!StrUtil.isEmpty(workorderDTO.getDeptCode())) {
				sb.append("   AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE \n");
			} else {
				sb.append("   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE \n");
			}
		} else {
			if (!StrUtil.isEmpty(workorderDTO.getDeptCode())) {
				sb.append("   AND EIIT.RESPONSIBILITY_DEPT = AMD.DEPT_CODE \n");
			} else {
				sb.append("   AND EIIT.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE \n");
			}
		}

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			if (!StrUtil.isEmpty(workorderDTO.getResponsibilityUser())) {
				sb.append("   AND EII.RESPONSIBILITY_USER = AMP.EMPLOYEE_ID \n");
			} else {
				sb.append("   AND EII.RESPONSIBILITY_USER *= AMP.EMPLOYEE_ID \n");
			}
		} else {
			if (!StrUtil.isEmpty(workorderDTO.getResponsibilityUser())) {
				sb.append("   AND EIIT.RESPONSIBILITY_USER = AMP.EMPLOYEE_ID \n");
			} else {
				sb.append("   AND EIIT.RESPONSIBILITY_USER *= AMP.EMPLOYEE_ID \n");
			}
		}

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			if (!StrUtil.isEmpty(workorderDTO.getPrjId())) {
				sb.append("   AND EII.PROJECTID = EPPA.PROJECT_ID \n");
			} else {
				sb.append("   AND EII.PROJECTID *= EPPA.PROJECT_ID \n");
			}
		} else {
			if (!StrUtil.isEmpty(workorderDTO.getPrjId())) {
				sb.append("   AND EIIT.PROJECTID = EPPA.PROJECT_ID \n");
			} else {
				sb.append("   AND EIIT.PROJECTID *= EPPA.PROJECT_ID \n");
			}
		}

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			if (workorderDTO.getOpinionType().equals("part")) {
				List partBar = workorderDTO.getPartBarcode();
				sb.append("AND (");
				boolean boolType = true;
				for (int i = 0; i < partBar.size(); i++) {
					if (boolType) {
						sb.append(" EII.BARCODE = ? \n");
						if (partBar.get(i) != ""
								&& !partBar.get(i).equals(null)) {
							sqlArgs.add(partBar.get(i).toString());
						}
						boolType = false;
						continue;
					}
					sb.append("OR EII.BARCODE = ? \n");
					if (partBar.get(i) != "" && !partBar.get(i).equals(null)) {
						sqlArgs.add(partBar.get(i).toString());
					}

				}
				sb.append(")");
			}
		} else {
			if (workorderDTO.getOpinionType().equals("part")) {
				List partBar = workorderDTO.getPartBarcode();
				sb.append("AND (");
				boolean boolType = true;
				for (int i = 0; i < partBar.size(); i++) {
					if (boolType) {
						sb.append(" EIIT.BARCODE = ? \n");
						if (partBar.get(i) != ""
								&& !partBar.get(i).equals(null)) {
							sqlArgs.add(partBar.get(i).toString());
						}
						boolType = false;
						continue;
					}
					sb.append("OR EIIT.BARCODE = ? \n");
					if (partBar.get(i) != "" && !partBar.get(i).equals(null)) {
						sqlArgs.add(partBar.get(i).toString());
					}

				}
				sb.append(")");
			}
		}

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append("   AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n");
		} else {
			sb.append("   AND EIIT.ADDRESS_ID = AOA.ADDRESS_ID \n");
		}
		sb.append("   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n");

		if (!StrUtil.isEmpty(workorderDTO.getDeptCode())) {
			sb.append("   AND AMD.DEPT_CODE LIKE ? \n");
		}

		if (!StrUtil.isEmpty(workorderDTO.getResponsibilityUser())) {
			sb.append("   AND AMP.USER_NAME LIKE ? \n");
		}

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			if (!StrUtil.isEmpty(workorderDTO.getOrganizationId())) {
				sb.append("   AND EII.ORGANIZATION_ID = ? \n");
			}
		} else {
			if (!StrUtil.isEmpty(workorderDTO.getOrganizationId())) {
				sb.append("   AND EIIT.ORGANIZATION_ID = ? \n");
			}
		}

		if (!StrUtil.isEmpty(workorderDTO.getPrjId())) {
			sb.append("   AND EPPA.PROJECT_ID = ? \n");
		}

		if (!StrUtil.isEmpty(workorderDTO.getWorkorderObjectCode())) {
			sb.append("   AND EO.WORKORDER_OBJECT_CODE LIKE ? \n");
		}

		// if( !StrUtil.isEmpty( workorderDTO.getDeptCode() ) ){
		// sb.append( "   AND  AMD.DEPT_CODE = ? \n");
		// }
		// sb.append( "   AND         ( " + SyBaseSQLUtil.isNull() +
		// "  OR AMP.USER_NAME LIKE ?) \n");
		// sb.append(
		// "   AND         (  ?=NULL OR EII.ORGANIZATION_ID = ?) \n");
		// sb.append( "   AND         (  ?=NULL  OR EPPA.PROJECT_ID = ?) \n");
		// sb.append( "   AND         ( " + SyBaseSQLUtil.isNull() +
		// "  OR EO.WORKORDER_OBJECT_CODE LIKE ?) \n");
		// sb.append( "   AND         ( " + SyBaseSQLUtil.isNull() +
		// "  OR AMD.DEPT_CODE = ?) \n");

		if (!StrUtil.isEmpty(workorderDTO.getDeptCode())) {
			sqlArgs.add(workorderDTO.getDeptCode());
		}

		if (!StrUtil.isEmpty(workorderDTO.getResponsibilityUser())) {
			sqlArgs.add(workorderDTO.getResponsibilityUser());
		}

		if (!StrUtil.isEmpty(workorderDTO.getOrganizationId())
				&& workorderDTO.getOrganizationId() > 0) {
			sqlArgs.add(workorderDTO.getOrganizationId());
		}

		if (!StrUtil.isEmpty(workorderDTO.getPrjId())) {
			sqlArgs.add(workorderDTO.getPrjId());
		}

		if (!StrUtil.isEmpty(workorderDTO.getWorkorderObjectCode())) {
			sqlArgs.add(workorderDTO.getWorkorderObjectCode());
		}

		// sqlArgs.add(workorderDTO.getResponsibilityUser());
		// sqlArgs.add(workorderDTO.getResponsibilityUser());
		//
		// sqlArgs.add(workorderDTO.getOrganizationId());
		// sqlArgs.add(workorderDTO.getOrganizationId());
		//
		// sqlArgs.add(workorderDTO.getPrjId());
		// sqlArgs.add(workorderDTO.getPrjId());
		//
		// sqlArgs.add(workorderDTO.getWorkorderObjectCode());
		// sqlArgs.add(workorderDTO.getWorkorderObjectCode());

		// sqlArgs.add(workorderDTO.getDeptCode());
		// sqlArgs.add(workorderDTO.getDeptCode());

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append("   AND EII.FINANCE_PROP = ? \n"); // 'ASSETS'
			sqlArgs.add(workorderDTO.getFinanceProp());
		}
		// 如果是查询待确认预转资就无需判断此条件
		// else
		// {
		// sb.append( "   AND EIIT.FINANCE_PROP = ? \n"); //'PRJ_MTL'
		// }
		// sqlArgs.add(workorderDTO.getFinanceProp());

		if (!workorderDTO.getFinanceProp().trim().equals("CFM_PRJ_MTL")) {
			sb.append(" ORDER BY EII.LAST_UPDATE_DATE, EII.ADDRESS_ID, EPPA.SEGMENT1 ");
		} else {
			sb.append(" ORDER BY EIIT.LAST_UPDATE_DATE, EIIT.ADDRESS_ID, EPPA.SEGMENT1 ");
		}

		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
}
