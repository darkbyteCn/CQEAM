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
     * ���ܣ�����ʲ�����̵�����״̬������
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
     * ���ܣ������û������Ĳ����ʲ�����Ա�Ĳ��������б�(MIS)
     * @param selectedValue String
     * @return String
     * @throws QueryException
     *//*
    public String getUserAsssetsDeptOption(String selectedValue) throws QueryException {
        String deptOption = "";
        boolean isDptManager = userAccount.isDptAssetsManager();
        boolean isCompanyMgr = userAccount.isComAssetsManager();
        String mtlMgrProps = userAccount.getMtlMgrProps();
        if (isCompanyMgr || !mtlMgrProps.equals("")) { //����˾���в���
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
        } else if (isDptManager) { //�����ʲ�����Ա������Ĳ���
            StringBuffer assetsDept = new StringBuffer();
            DTOSet depts = userAccount.getPriviDeptCodes();
            if (depts != null) {
                AmsMisDeptDTO dept = null;
                assetsDept.append("<option value=\"\">--��ѡ��--</option>");
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
        } else { //�������ʲ���������
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
     * ���ܣ���ȡ�̵㹤���е�ɨ��רҵ
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
