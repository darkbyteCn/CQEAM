package com.sino.ams.yearchecktaskmanager.util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.QueryException;
import com.sino.base.web.DatabaseForWeb;

public class AssetsYearOptProducer extends OptionProducer {

	public AssetsYearOptProducer(SfUserDTO userAccount, Connection conn) {
		super(userAccount, conn);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 *//**
     * 功能：填充资产年度盘点任务状态下拉框
     * @param dto AmsAssetsCheckBatchDTO
     * @return AmsAssetsCheckBatchDTO
     * @throws QueryException
     *//*
    public AssetsYearCheckBatchDTO fillBatchStatus(AssetsYearCheckBatchDTO dto) throws
            QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " EFV.CODE,"
                + " EFV.VALUE"
                + " FROM"
                + " ETS_FLEX_VALUES    EFV,"
                + " ETS_FLEX_VALUE_SET EFVS"
                + " WHERE"
                + " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
                + " AND EFVS.CODE = ?";
        String excludeOpt = AssetsDictConstant.CREATE;
        excludeOpt += "', '" + AssetsDictConstant.ORDER_STS_RECEIVED;
        excludeOpt += "', '" + AssetsDictConstant.ORDER_STS_ASSIGNED;
        excludeOpt += "', '" + AssetsDictConstant.ORDER_STS_CONFIRMD;
        excludeOpt = "('" + excludeOpt + "')";
        sqlStr += " AND EFV.CODE NOT IN " + excludeOpt;
        sqlArgs.add(AssetsDictConstant.ORDER_STATUS);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
        String optHtml = optProducer.getOptionHtml(dto.getBatchStatus(), true);
        dto.setBatchStatusOption(optHtml);
        return dto;
    }
    
    *//**
     * 功能：构造用户担当的部门资产管理员的部门下拉列表(MIS)
     * @param selectedValue String
     * @return String
     * @throws QueryException
     *//*
    public String getUserAsssetsDeptOption(String selectedValue) throws QueryException {
        String deptOption = "";
        boolean isDptManager = userAccount.isDptAssetsManager();
        boolean isCompanyMgr = userAccount.isComAssetsManager();
        String mtlMgrProps = userAccount.getMtlMgrProps();
        if (isCompanyMgr || !mtlMgrProps.equals("")) { //本公司所有部门
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = "SELECT"
                    + " AMD.DEPT_CODE,"
                    + " AMD.DEPT_NAME"
                    + " FROM"
                    + " AMS_MIS_DEPT AMD"
                    + " WHERE"
                    + " 	     AMD.COMPANY_CODE = ? " 
                    + "     AND  AMD.ENABLED='Y' " 
                    + "ORDER BY  AMD.DEPT_NAME "
                    ;
            sqlArgs.add(userAccount.getCompanyCode());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
            deptOption = optProducer.getOptionHtml(selectedValue, true);
        } else if (isDptManager) { //部门资产管理员所管理的部门
            StringBuffer assetsDept = new StringBuffer();
            DTOSet depts = userAccount.getPriviDeptCodes();
            if (depts != null) {
                AmsMisDeptDTO dept = null;
                assetsDept.append("<option value=\"\">--请选择--</option>");
                assetsDept.append(WorldConstant.ENTER_CHAR);
                for (int i = 0; i < depts.getSize(); i++) {
                    dept = (AmsMisDeptDTO) depts.getDTO(i);
                    assetsDept.append("<option value=\"");
                    assetsDept.append(dept.getDeptCode());
                    if (dept.getDeptCode().equals(selectedValue)) {
                        assetsDept.append("\" selected>");
                    } else {
                        assetsDept.append("\">");
                    }
                    assetsDept.append(dept.getDeptName());
                    assetsDept.append("</option>");
                    assetsDept.append(WorldConstant.ENTER_CHAR);
                }
                deptOption = assetsDept.toString();
            }
        } else { //责任人资产所属部门
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = "SELECT"
                    + " AMD.DEPT_CODE,"
                    + " AMD.DEPT_NAME"
                    + " FROM"
                    + " AMS_MIS_DEPT AMD"
                    + " WHERE"
                    + " EXISTS("
                    + " SELECT"
                    + " NULL"
                    + " FROM"
                    + " ETS_ITEM_INFO EII"
                    + " WHERE"
                    + " EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE"
                    + " AND EII.RESPONSIBILITY_USER = ?)"
                    + " AND AMD.ENABLED='Y' " 
                    + "  ORDER BY AMD.DEPT_NAME ";
            sqlArgs.add(userAccount.getEmployeeId());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
            deptOption = optProducer.getOptionHtml(selectedValue, false);
        }
        return deptOption;
    }
    
    *//**
     * 功能：获取盘点工单中的扫描专业
     * @param selectedValue String
     * @return String
     * @throws QueryException
     *//*
    public String getChkCategoryOption(String selectedValue) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " EFV.CODE,"
                + " EFV.VALUE"
                + " FROM"
                + " ETS_FLEX_VALUES    EFV,"
                + " ETS_FLEX_VALUE_SET EFVS"
                + " WHERE"
                + " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
                + " AND EFVS.CODE = ?";
        sqlArgs.add(AssetsDictConstant.ITEM_TYPE);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }

*/}
