package com.sino.ams.newasset.bean;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransConfigDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.base.web.DatabaseForWeb;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: ɽ���ƶ�ʵ���ʲ�����ϵͳ</p>
 * <p>Copyright: ����˼ŵ����Ϣ�������޹�˾��Ȩ����Copyright (c) 2007</p>
 * <p>Company: ����˼ŵ����Ϣ�������޹�˾</p>
 * @author ����ʤ
 * @version 1.0
 */
public class AssetsOptProducer extends OptionProducer {

    public AssetsOptProducer(SfUserDTO userAccount, Connection conn) {
        super(userAccount, conn);
    }


    /**
     * ���ܣ�������ѡ���û������б�
     * @param selectedValue String
     * @return String
     * @throws QueryException
     */
    public String getAllUsersOption(String selectedValue) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " SR.ROLE_ID,"
                + " SR.ROLE_NAME"
                + " FROM"
                + " SF_ROLE SR,"
                + " SF_RES_PRIVS SRP,"
                + " SF_RES_DEFINE SRD"
                + " WHERE"
                + " SR.ROLE_NAME = SRP.ROLE_NAME"
                + " AND SRP.SYSTEM_ID = SRD.SYSTEM_ID"
                + " AND SRD.RES_ID = ?";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
        return optProducer.getOptionHtml(selectedValue);
    }

    /**
     * ���ܣ������û������Ĳ����ʲ�����Ա�Ĳ��������б�(MIS)
     * @param selectedValue String
     * @return String
     * @throws QueryException
     */
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
    public String getUserAsssetsDeptOptiont(String selectedValue) throws QueryException {
        String deptOption = "";
        boolean isDptManager = userAccount.isDptAssetsManager();
        boolean isCompanyMgr = userAccount.isComAssetsManager();
        String mtlMgrProps = userAccount.getMtlMgrProps();
       
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = "SELECT"
                    + " AMD.DEPT_CODE,"
                    + " AMD.DEPT_NAME"
                    + " FROM"
                    + " AMS_MIS_DEPT AMD"
                    + " WHERE"
                    + " 	     AMD.COMPANY_CODE = ? " 
                    + "     AND  AMD.ENABLED='Y' " ;
            sqlArgs.add(userAccount.getCompanyCode());
            if (!selectedValue.equals("")&&!selectedValue.equals(null)) {
				sqlStr+= " AND AMD.DEPT_NAME = ?";
				sqlArgs.add(selectedValue);
			}
                    sqlStr+="ORDER BY  AMD.DEPT_NAME ";
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
            deptOption = optProducer.getOptionHtml(selectedValue, false);
      
        return deptOption;
    }
    public String getUserAsssetsDeptOption2(String selectedValue) throws QueryException {
        String deptOption = "";
        boolean isDptManager = userAccount.isDptAssetsManager();
        boolean isCompanyMgr = userAccount.isComAssetsManager();
        String mtlMgrProps = userAccount.getMtlMgrProps();
        if (isCompanyMgr) { //����˾���в���
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = "SELECT"
                    + " AMD.DEPT_CODE,"
                    + " AMD.DEPT_NAME"
                    + " FROM"
                    + " AMS_MIS_DEPT AMD"
                    + " WHERE"
                    + " AMD.COMPANY_CODE = ?"
                    + " AND AMD.ENABLED='Y' " 
                    + " ORDER BY AMD.DEPT_NAME ";
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
            String sqlStr = "SELECT AMD.DEPT_CODE,\n" +
                    "       AMD.DEPT_NAME\n" +
                    "  FROM AMS_MIS_DEPT     AMD,\n" +
                    "       AMS_MIS_EMPLOYEE AME\n" +
                    " WHERE AMD.DEPT_CODE = AME.DEPT_CODE\n" +
                    "   AND AME.EMPLOYEE_ID = ?"
                    + " AND AMD.ENABLED='Y' " 
                    + " ORDER BY AMD.DEPT_NAME ";
            sqlArgs.add(userAccount.getEmployeeId());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
            deptOption = optProducer.getOptionHtml(selectedValue, true);
        }
        return deptOption;
    }

    public String getSelectedDeptOption(String fromDept) throws QueryException {
        String deptOption = "";
        SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = "SELECT"
                    + " AMD.DEPT_CODE,"
                    + " AMD.DEPT_NAME"
                    + " FROM"
                    + " AMS_MIS_DEPT AMD"
                    + " WHERE  AMD.DEPT_CODE = ?" 
                    + " AND AMD.ENABLED='Y' " 
                    + " ORDER BY AMD.DEPT_NAME ";
            sqlArgs.add(fromDept);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
            deptOption = optProducer.getOptionHtml(fromDept, true);
        return deptOption;
    }

    public String getSpecialAsssetsDeptOption(String selectedValue) throws QueryException {
    	//TODO ʵ�ﲿ��
        String deptOption = "";
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = /*"SELECT AMD.DEPT_CODE,\n" +
                "       AMD.DEPT_NAME\n" +
                "FROM   AMS_MIS_DEPT AMD,\n" +
                "       SINO_GROUP_MATCH SGM," +
                "       SF_GROUP     SG\n" +
                "WHERE  SGM.GROUP_ID = SG.GROUP_ID\n" +
                "AND    SG.SPECIALITY_DEPT = 'Y'\n" +
                "AND 	SGM.DEPT_ID=AMD.DEPT_CODE " +
                "AND 	AMD.COMPANY_CODE = ?" +
                "AND 	AMD.ENABLED='Y' " +
                "ORDER BY AMD.DEPT_NAME ";*/
        	"SELECT SMD.DEPT_ID DEPT_CODE, SMD.DEPT_NAME \n" +
        	"  FROM SINO_MIS_DEPT SMD \n" +
        	" WHERE 1 = 1 \n" +
        	"   AND SMD.SPECIALITY_DEPT = 'Y' \n" +
        	"   AND SMD.COMPANY_CODE = ? \n" +
        	"   AND SMD.ENABLED = 'Y' \n" +
        	" ORDER BY SMD.DEPT_NAME \n" ;

        sqlArgs.add(userAccount.getCompanyCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
        deptOption = optProducer.getOptionHtml(selectedValue, true);
        return deptOption;
    }

    public String getSpecialAsssetsDeptOption(String SpecialAsssetsDept, String companyCode, String companyName) throws QueryException {
        String deptOption = "";
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT AMD.DEPT_CODE, AMD.DEPT_NAME\n" +
                        "  FROM AMS_MIS_DEPT AMD, SF_GROUP SG, ETS_OU_CITY_MAP EOCM\n" +
                        " WHERE AMD.DEPT_CODE = SG.GROUP_CODE\n" +
                        "   AND EOCM.COMPANY_CODE = AMD.COMPANY_CODE\n" +
                        "   AND SG.SPECIALITY_DEPT = 'Y'\n" +
                        "   AND EOCM.COMPANY_CODE LIKE dbo.NVL(?, EOCM.COMPANY_CODE)\n" +
                        "   AND EOCM.COMPANY LIKE dbo.NVL(?, EOCM.COMPANY)"
                        + " AND AMD.ENABLED='Y' " 
                        + " ORDER BY AMD.DEPT_NAME ";

        sqlArgs.add(companyCode);
        sqlArgs.add(companyName);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
        deptOption = optProducer.getOptionHtml(SpecialAsssetsDept, true);
        return deptOption;
    }


    /**
     * ���ܣ����첿�������˵�(ȡ����Դ��ETS_COUNTY,רΪETS_FA_ASSETS�����β��Ŷ�д)
     * @param selectedValue String
     * @return String
     * @throws QueryException
     */
    public String getAsssetsDeptOption(String selectedValue) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " EC.COUNTY_CODE,"
                + " EC.COUNTY_NAME"
                + " FROM"
                + " ETS_COUNTY EC"
                + " WHERE"
                + " EC.COMPANY_CODE = ?";
        sqlArgs.add(userAccount.getCompanyCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
        return optProducer.getOptionHtml(selectedValue, true);
    }

    //�������в��������˵�

    public String getALLUserAsssetsDeptOption(String selectedValue,String companyCode) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " AMD.DEPT_CODE,"
                + " AMD.DEPT_NAME"
                + " FROM"
                + " AMS_MIS_DEPT AMD"
                + " WHERE AMD.COMPANY_CODE=dbo.NVL(?,AMD.COMPANY_CODE)"
                + " AND AMD.ENABLED='Y' " 
                + " ORDER BY AMD.COMPANY_CODE,AMD.DEPT_NUMBER, AMD.DEPT_NAME ";
        sqlArgs.add(companyCode);
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
        DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
        String deptOption = optProducer.getOptionHtml(selectedValue, true);
        return deptOption;
    }


    /**
     * ���ܣ���ȡ�û����в����ʲ�����Ա����𣺺����ID�����ƣ�����
     * @return DTOSet
     * @throws DTOException
     */
//	public DTOSet getUserAssetsGroups() throws DTOException {
//		DTOSet userRights = userAccount.getUserRight();
//		DTOSet assetsGroups = new DTOSet();
//		for (int i = 0; i < userRights.getSize(); i++) {
//			SfUserRightDTO userRight = (SfUserRightDTO) userRights.getDTO(i);
//			if(!assetsGroups.contains("groupId", userRight.getGroupId())){
//				SfGroupDTO groupDTO = new SfGroupDTO();
//				groupDTO.setGroupId(userRight.getGroupId());
//				groupDTO.setGroupname(userRight.getGroupname());
//				groupDTO.setGroupCode(userRight.getGroupCode());
//				assetsGroups.addDTO(groupDTO);
//			}
//		}
//		return assetsGroups;
//	}

    /**
     * ���ܣ������û���������б��Ҿ߱�����𡰲����ʲ�����Ա����Ȩ��
     * @param dto AmsAssetsTransHeaderDTO
     * @return String
     * @throws DTOException
     */
    public String getUserGroupOption(AmsAssetsTransHeaderDTO dto) throws
            DTOException {
        StringBuffer assetsDept = new StringBuffer();
        DTOSet userRights = userAccount.getUserGroups();
        if (userRights != null) {
            SfGroupDTO sfGroup = null;
            String provinceCode = dto.getServletConfig().getProvinceCode();
            String transType = dto.getTransType();
            String transferType = dto.getTransferType();
            for (int i = 0; i < userRights.getSize(); i++) {
                sfGroup = (SfGroupDTO) userRights.getDTO(i);
                if (provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_NM)
                        && (transType.equals(AssetsDictConstant.ASS_SUB) || transferType.equals(AssetsDictConstant.TRANS_BTW_COMP))) { //�����ƶ����⴦��
                    if (!sfGroup.getGroupProp().equals(AssetsDictConstant.GROUP_PROP_SPEC)) {
                        continue;
                    }
                }
                if (provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_SX)
                        && (transType.equals(AssetsDictConstant.ASS_DIS) || transType.equals(AssetsDictConstant.ASS_FREE) || transType.equals(AssetsDictConstant.ASS_SUB))) { //ɽ���ƶ����⴦��
                    if (!sfGroup.getGroupProp().equals(AssetsDictConstant.GROUP_PROP_SPEC)) {
                        continue;
                    }
                }
                assetsDept.append("<option value=\"");
                assetsDept.append(sfGroup.getGroupId());
                assetsDept.append("\">");
                assetsDept.append(sfGroup.getGroupname());
                assetsDept.append("</option>");
                assetsDept.append(WorldConstant.ENTER_CHAR);
            }
        }
        return assetsDept.toString();
    }

    /**
     * ���ܣ�����ʲ�����(���������ϣ�����)״̬������
     * @param dto AmsAssetsTransHeaderDTO
     * @return AmsAssetsTransHeaderDTO
     * @throws QueryException
     */
    public AmsAssetsTransHeaderDTO fillTransStatus(AmsAssetsTransHeaderDTO dto) throws
            QueryException {
        String transType = dto.getTransType();
        if (!transType.equals("")) {
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
            if (!transType.equals(AssetsDictConstant.ASS_RED)) {
                excludeOpt += "', '" + AssetsDictConstant.ORDER_STS_RECEIVED;
                excludeOpt += "', '" + AssetsDictConstant.ORDER_STS_ASSIGNED;
                excludeOpt += "', '" + AssetsDictConstant.ORDER_STS_CONFIRMD;
            }
            excludeOpt = "('" + excludeOpt + "')";
            sqlStr += " AND EFV.CODE NOT IN " + excludeOpt;
            sqlArgs.add(AssetsDictConstant.ORDER_STATUS);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
            String optHtml = optProducer.getOptionHtml(dto.getTransStatus(), true);
            dto.setStatusOption(optHtml);
        }
        return dto;
    }

//    /**
//     * ���ܣ�TD����ʲ�����(���������ϣ�����)״̬������
//     * @param dto TdAssetsTransHeaderDTO
//     * @return TdAssetsTransHeaderDTO
//     * @throws QueryException
//     */
//    public TdAssetsTransHeaderDTO fillTransStatus(TdAssetsTransHeaderDTO dto) throws
//            QueryException {
//        String transType = dto.getTransType();
//        if (!transType.equals("")) {
//            SQLModel sqlModel = new SQLModel();
//            List sqlArgs = new ArrayList();
//            String sqlStr = "SELECT"
//                    + " EFV.CODE,"
//                    + " EFV.VALUE"
//                    + " FROM"
//                    + " ETS_FLEX_VALUES    EFV,"
//                    + " ETS_FLEX_VALUE_SET EFVS"
//                    + " WHERE"
//                    + " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
//                    + " AND EFVS.CODE = ?";
//            String excludeOpt = AssetsDictConstant.CREATE;
//            if (!transType.equals(AssetsDictConstant.ASS_RED)) {
//                excludeOpt += "', '" + AssetsDictConstant.ORDER_STS_RECEIVED;
//                excludeOpt += "', '" + AssetsDictConstant.ORDER_STS_ASSIGNED;
//                excludeOpt += "', '" + AssetsDictConstant.ORDER_STS_CONFIRMD;
//            }
//            excludeOpt = "('" + excludeOpt + "')";
//            sqlStr += " AND EFV.CODE NOT IN " + excludeOpt;
//            sqlArgs.add(AssetsDictConstant.ORDER_STATUS);
//            sqlModel.setSqlStr(sqlStr);
//            sqlModel.setArgs(sqlArgs);
//            DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
//            String optHtml = optProducer.getOptionHtml(dto.getTransStatus(), true);
//            dto.setStatusOption(optHtml);
//        }
//        return dto;
//    }

    /**
     * ���ܣ�����ʲ��̵�����״̬������
     * @param dto AmsAssetsCheckBatchDTO
     * @return AmsAssetsCheckBatchDTO
     * @throws QueryException
     */
    public AmsAssetsCheckBatchDTO fillBatchStatus(AmsAssetsCheckBatchDTO dto) throws
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

    /**
     * ���ܣ���ȡ����������������
     * @param selectedValue String
     * @return String
     */
    public String getTransferOption(String selectedValue) {
        StringBuffer transOptHtml = new StringBuffer();
        transOptHtml.append("<option value=\"\">--��ѡ��--</option>");
        String[] transOpts = AssetsDictConstant.TRANS_OPT_VALUES;
        for (int i = 0; i < transOpts.length; i++) {
            if ("Y".equalsIgnoreCase(userAccount.getIsTd()) && transOpts[i].equals("BTW_COMP")) {
                continue;
            }
            transOptHtml.append("<option value=\"");
            transOptHtml.append(transOpts[i]);
            if (transOpts[i].equals(selectedValue)) {
                transOptHtml.append("\" selected>");
            } else {
                transOptHtml.append("\">");
            }
            transOptHtml.append(AssetsDictConstant.TRANS_OPT_LABELS[i]);
            transOptHtml.append("</option>");
        }
        return transOptHtml.toString();
    }

    /**
     * ���ܣ��õ����ⵥ������������
     * @param selectedValue
     */
    public String getSpecialAccetsTypeOption(String selectedValue) {
        StringBuffer spAccetsOptHtml = new StringBuffer();
        spAccetsOptHtml.append("<option value=\"\">--��ѡ��--</option>");
        String[] spAccetsOpts = AssetsDictConstant.SP_ASSETS_OPT_VALUES;
        for (int i = 0; i < spAccetsOpts.length; i++) {
            spAccetsOptHtml.append("<option value=\"");
            spAccetsOptHtml.append(spAccetsOpts[i]);
            if (spAccetsOpts[i].equals(selectedValue)) {
                spAccetsOptHtml.append("\" selected>");
            } else {
                spAccetsOptHtml.append("\">");
            }
            spAccetsOptHtml.append(AssetsDictConstant.SP_ASSETS_OPT_LABELS[i]);
            spAccetsOptHtml.append("</option>");
        }
        return spAccetsOptHtml.toString();
    }

    /**
     * ���ܣ��õ������ʲ�����
     * @param selectedValue
     */
    public String getAccetsTypeOption(String selectedValue) {
        StringBuffer spAccetsOptHtml = new StringBuffer();
        spAccetsOptHtml.append("<option value=\"\">--��ѡ��--</option>");
        String[] spAccetsOpts = AssetsDictConstant.ASSETS_TYPE_OPT_VALUES;
        for (int i = 0; i < spAccetsOpts.length; i++) {
            spAccetsOptHtml.append("<option value=\"");
            spAccetsOptHtml.append(spAccetsOpts[i]);
            if (spAccetsOpts[i].equals(selectedValue)) {
                spAccetsOptHtml.append("\" selected>");
            } else {
                spAccetsOptHtml.append("\">");
            }
            spAccetsOptHtml.append(AssetsDictConstant.ASSETS_TYPE_OPT_LABELS[i]);
            spAccetsOptHtml.append("</option>");
        }
        return spAccetsOptHtml.toString();
    }

    /**
     * ���ܣ���ȡ����������������(�������м������)
     * @param selectedValue String
     * @return String
     */
    public String getFilterTransferOption(String selectedValue) {
        StringBuffer transOptHtml = new StringBuffer();
//		transOptHtml.append("<option value=\"\">--��ѡ��--</option>");
        String[] transOpts = AssetsDictConstant.TRANS_OPT_VALUES;
        for (int i = 0; i < transOpts.length; i++) {
            if (!transOpts[i].equals("BTW_COMP")) {
                transOptHtml.append("<option value=\"");
                transOptHtml.append(transOpts[i]);
                if (transOpts[i].equals(selectedValue)) {
                    transOptHtml.append("\" selected>");
                } else {
                    transOptHtml.append("\">");
                }
                transOptHtml.append(AssetsDictConstant.TRANS_OPT_LABELS[i]);
                transOptHtml.append("</option>");
            }
        }
        return transOptHtml.toString();
    }

    /**
     * ���ܣ���ȡ����������������(�����м������)
     * @param selectedValue String
     * @return String
     */
    public String getBtwTransferOption(String selectedValue) {
        StringBuffer transOptHtml = new StringBuffer();
        String[] transOpts = AssetsDictConstant.TRANS_OPT_VALUES;
        for (int i = 0; i < transOpts.length; i++) {
            if (transOpts[i].equals("BTW_COMP")) {
                transOptHtml.append("<option value=\"");
                transOptHtml.append(transOpts[i]);
                if (transOpts[i].equals(selectedValue)) {
                    transOptHtml.append("\" selected >");
                } else {
                    transOptHtml.append("\">");
                }
                transOptHtml.append(AssetsDictConstant.TRANS_OPT_LABELS[i]);
                transOptHtml.append("</option>");
            }
        }
        return transOptHtml.toString();
    }

    /**
     * ���ܣ���ȡ��˾������
     * @param selectedValue String
     * @return String
     * @throws QueryException
     */
    public String getOrganizationOption(int selectedValue) throws
            QueryException {
        String deptOption = "";
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " EOCM.ORGANIZATION_ID,"
                + " EOCM.COMPANY"
                + " FROM"
                + " ETS_OU_CITY_MAP EOCM";
        if (!userAccount.isProvinceUser()) {
            sqlStr = sqlStr
                    + " WHERE"
                    + " EOCM.ORGANIZATION_ID = ?";
            sqlArgs.add(userAccount.getOrganizationId());
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
        deptOption = optProducer.getOptionHtml(StrUtil.nullToString(selectedValue), true);
        return deptOption;
    }

    /**
     * ��ȡ����OU��֯�����б��
     * @param selectedValue String
     * @return String
     * @throws QueryException
     */
    public String getAllOrganization(int selectedValue) throws
            QueryException {
        return getAllOU(selectedValue, true);
    }

    /**
     * ���ܣ���ȡ����Ŀ��OU������(�ų���ǰ��¼�û�����OUѡ��)
     * @param fromOrgId     ԴOU��֯ID
     * @param selectedValue String ѡ�е�Ŀ��OU
     * @return String
     * @throws QueryException
     */
    public String getTargetOrganization(int fromOrgId, int selectedValue) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT ORGANIZATION_ID, COMPANY\n" +
                        "  FROM ETS_OU_CITY_MAP EOCM\n" +
                        " WHERE EOCM.ORGANIZATION_ID <> ?\n" +
                        "   AND EXISTS (SELECT NULL\n" +
                        "          FROM ETS_OU_CITY_MAP T\n" +
                        "         WHERE T.IS_TD = EOCM.IS_TD\n" +
                        "           AND T.ORGANIZATION_ID = ?)";


        sqlArgs.add(fromOrgId);
        sqlArgs.add(fromOrgId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(StrUtil.nullToString(selectedValue), true);
    }

    /**
     * ��ȡ����OU��֯�����б��
     * @param selectedValue String
     * @return String
     * @throws QueryException
     */
    public String getAllOu(int selectedValue) throws QueryException {
        return getAllOrganization(selectedValue, userAccount.isProvinceUser());
    }

    /**
     * ���ܣ���ȡ�û����е�רҵ�ʲ�Ȩ��
     * @param userId ѡ���û�
     * @return String
     * @throws QueryException
     */
    public String getExistContentPrivi(String userId) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " EFV.CODE,"
                + " EFV.VALUE"
                + " FROM"
                + " ETS_FLEX_VALUE_SET EFVS,"
                + " ETS_FLEX_VALUES    EFV"
                + " WHERE"
                + " EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
                + " AND EFVS.CODE = ?"
                + " AND EXISTS ("
                + " SELECT"
                + " NULL"
                + " FROM"
                + " AMS_ASSETS_PRIVI AAP"
                + " WHERE"
                + " AAP.FA_CATEGORY_CODE = EFV.CODE"
                + " AND AAP.USER_ID = ?)";
        sqlArgs.add(AssetsDictConstant.FA_CONTENT_CODE);
        sqlArgs.add(userId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml();
    }

    /**
     * ���ܣ���ȡ�û�û�е�רҵ�ʲ�Ȩ��
     * @param userId ѡ���û�
     * @return String
     * @throws QueryException
     */
    public String getNoContentPrivi(String userId) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " EFV.CODE,"
                + " EFV.VALUE"
                + " FROM"
                + " ETS_FLEX_VALUE_SET EFVS,"
                + " ETS_FLEX_VALUES    EFV"
                + " WHERE"
                + " EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
                + " AND EFVS.CODE = ?"
                + " AND NOT EXISTS ("
                + " SELECT"
                + " NULL"
                + " FROM"
                + " AMS_ASSETS_PRIVI AAP"
                + " WHERE"
                + " AAP.FA_CATEGORY_CODE = EFV.CODE"
                + " AND AAP.USER_ID = ?)";
        sqlArgs.add(AssetsDictConstant.FA_CONTENT_CODE);
        sqlArgs.add(userId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml();
    }

    /**
     * ���ܣ���ȡ�ʲ�����𣺹����໹��������
     * @param selectedValue ѡ����ֵ
     * @return String
     * @throws QueryException
     */
    public String getFAContentOption(String selectedValue) throws
            QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " EFV.CODE,"
                + " EFV.VALUE"
                + " FROM"
                + " ETS_FLEX_VALUE_SET EFVS,"
                + " ETS_FLEX_VALUES    EFV"
                + " WHERE"
                + " EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
                + " AND EFVS.CODE = ?";
        sqlArgs.add(AssetsDictConstant.FA_CONTENT_CODE);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }
    
    /**
     * ��ȡ�ʲ�����
     * @param selectedValue
     * @return
     * @throws QueryException
     */
    public String getFAContentOptionT(String selectedValue,String transType,String isTd) throws
		    QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
		        + " EFV.CODE,"
		        + " EFV.VALUE"
		        + " FROM"
		        + " ETS_FLEX_VALUE_SET EFVS,"
		        + " ETS_FLEX_VALUES    EFV"
		        + " WHERE"
		        + " EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
		        + " AND EFVS.CODE = 'FINANCE_PROP'";
				
		if ("N".equals(isTd)){
			if ("ASS-RENT".equals(transType)) { //����
				sqlStr += " AND EFV.CODE IN ('ASSETS')";
	    	} else if ("ASS-DONATE".equals(transType)) { //����
	    		sqlStr += " AND EFV.CODE IN ('ASSETS','DG_ASSETS','DH_ASSETS','SPARE')";
	    	} else if ("ASS-REPAIRRETURN".equals(transType)) { //���޷���
	    		sqlStr += " AND EFV.CODE IN ('ASSETS','DG_ASSETS','DH_ASSETS','SPARE')";
	    	} else if ("ASS-REPAIR".equals(transType)) { //����
	    		sqlStr += " AND EFV.CODE IN ('ASSETS','DG_ASSETS','DH_ASSETS','SPARE')";
	    	} else if ("ASS-SELL".equals(transType)) { //����
	    		sqlStr += " AND EFV.CODE IN ('ASSETS','DG_ASSETS','DH_ASSETS','SPARE')";
	    	} else if ("ASS-WASTEFORECAST".equals(transType)) { //Ԥ����
	    		//sqlStr += " AND EFV.CODE IN ('ASSETS')";
	    		sqlStr += " AND EFV.CODE IN ('ASSETS','DG_ASSETS','DH_ASSETS','SPARE')";
	    	} else if("ASS-REPEAL".equals(transType)){  //Ԥ���ϳ���
	    		//sqlStr += " AND EFV.CODE IN ('ASSETS')"; 
	    		sqlStr += " AND EFV.CODE IN ('ASSETS','DG_ASSETS','DH_ASSETS','SPARE')";
	    	}else if ("ASS-EXCHANGE".equals(transType)) { //�û�
	    		sqlStr += " AND EFV.CODE IN ('ASSETS')";
	    	} else if ("ASS-BORROW".equals(transType) || "ASS-RETURN".equals(transType)) { //����/�ͻ�
	    		sqlStr += " AND EFV.CODE IN ('DH_ASSETS')";
	    	}
		} else {
			sqlStr += " AND EFV.CODE IN ('TD_ASSETS','TT_ASSETS')";			
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedValue, true);
		}
    
     public String getItemContentOption(String selectedValue) throws
            QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " EFV.CODE,"
                + " EFV.VALUE"
                + " FROM"
                + " ETS_FLEX_VALUE_SET EFVS,"
                + " ETS_FLEX_VALUES    EFV"
                + " WHERE"
                + " EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
                + " AND EFVS.CODE = 'FINANCE_PROP'" +
                "   AND EFV.CODE IN ('DG_ASSETS', 'DH_ASSETS', 'QD_ASSETS', 'RENT_ASSETS', 'SPARE') ";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }
    /**
     * ���ܣ���ȡ���õ��ʲ�����
     * @param selectedValue String
     * @param transferType  String
     * @return String
     */
    public String getTransferFaOption(String selectedValue, String transferType) {
        StringBuffer transOptHtml = new StringBuffer();
        DTOSet transConfigs = userAccount.getTransConfigs();
        if (transConfigs != null && !transConfigs.isEmpty()) {
            transOptHtml.append("<option value=\"\">--��ѡ��--</option>");
            AmsAssetsTransConfigDTO configDTO = null;
            String tmpTransType = "";
            int activeCount = 0;
            for (int i = 0; i < transConfigs.getSize(); i++) {
                configDTO = (AmsAssetsTransConfigDTO) transConfigs.getDTO(i);
                tmpTransType = configDTO.getTransferType();
                if (!tmpTransType.equals(transferType)) {
                    continue;
                }
                activeCount++;
                transOptHtml.append("<option value=\"");
                transOptHtml.append(configDTO.getFaContentCode());
                if (configDTO.getFaContentCode().equals(selectedValue)) {
                    transOptHtml.append("\" selected>");
                } else {
                    transOptHtml.append("\">");
                }
                transOptHtml.append(configDTO.getFaContentName());
                transOptHtml.append("</option>");
            }
            if (activeCount == 0) {
                transOptHtml = new StringBuffer();
            }
        }
        return transOptHtml.toString();
    }

    /**
     * ���ܣ������ʲ��˲�������
     * @param selectedValue ѡ����ֵ
     * @return String
     * @throws QueryException
     */
    public String getBookTypeOption(String selectedValue) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " EOCM.BOOK_TYPE_CODE,"
                + " EOCM.BOOK_TYPE_NAME"
                + " FROM"
                + " ETS_OU_CITY_MAP EOCM";
        if (!userAccount.isProvinceUser()) {
            sqlStr += " WHERE EOCM.ORGANIZATION_ID = ?";
            sqlArgs.add(userAccount.getOrganizationId());
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }

    /**
     * ���ܣ������ʲ��˲�������
     * @param selectedValue ѡ����ֵ
     * @return String
     * @throws QueryException
     */
    public String getBookTypeOption2(String selectedValue) throws
            QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " EOCM.ORGANIZATION_ID,"
                + " EOCM.BOOK_TYPE_CODE||'--'||EOCM.BOOK_TYPE_NAME"
                + " FROM"
                + " ETS_OU_CITY_MAP EOCM";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }

    /**
     * ���ܣ���ȡ�豸�����ֵ�(�����豸̨��ά��)
     * @param selectedValue String
     * @return String
     * @throws QueryException
     */
    public String getObjectCategoryOption(String selectedValue) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " EFV.CODE,"
                + " EFV.VALUE"
                + " FROM"
                + " ETS_FLEX_VALUE_SET EFVS,"
                + " ETS_FLEX_VALUES    EFV"
                + " WHERE"
                + " EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
                + " AND EFVS.CODE = ?"
                + " AND (EFV.CODE < ? OR EFV.CODE = ?)";
        
        sqlArgs.add(AssetsDictConstant.OBJECT_CATEGORY);
        sqlArgs.add(AssetsDictConstant.INV_NORMAL);
        sqlArgs.add(AssetsDictConstant.NETADDR_OTHERS);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }
    /**
     * ����ص�ҳ���������
     * @param selectedValue
     * @return
     * @throws QueryException
     */
    public String getObjectCategory2Option(String selectedValue) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " EFV.ATTRIBUTE2,"
                + " EFV.VALUE"
                + " FROM"
                + " ETS_FLEX_VALUE_SET EFVS,"
                + " ETS_FLEX_VALUES    EFV"
                + " WHERE"
                + " EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
                + " AND EFVS.CODE = ?"
                + " AND (EFV.CODE < ? OR EFV.CODE = ?)";
        
        sqlArgs.add(AssetsDictConstant.OBJECT_CATEGORY);
        sqlArgs.add(AssetsDictConstant.INV_NORMAL);
        sqlArgs.add(AssetsDictConstant.NETADDR_OTHERS);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }

    /**
     * ���ܣ���ȡ�豸�����ֵ�(�����豸̨��ά��)
     * @param selectedValue String
     * @return String
     * @throws QueryException
     */
    public String getItemCategoryOption(String selectedValue) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String limitType = "('BSC', 'BTS', 'CABEL', 'DATA', 'ELEC', 'EXCHG', 'NETMGR', 'NETOPT', 'TRANS', 'OTHERS')";
        String sqlStr = "SELECT"
                + " EFV.CODE,"
                + " EFV.VALUE"
                + " FROM"
                + " ETS_FLEX_VALUE_SET EFVS,"
                + " ETS_FLEX_VALUES    EFV"
                + " WHERE"
                + " EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
                + " AND EFVS.CODE = ?"
                + " AND EFV.ENABLED = 'Y'"
                + " AND EFV.CODE IN " + limitType;
        sqlArgs.add(AssetsDictConstant.ITEM_TYPE);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }

    /**
     * ���ܣ���ȡ�豸�����ֵ�(�����豸̨��ά��)
     * @param selectedValue String
     * @return String
     * @throws QueryException
     */
    public String getFinancePropOption(String selectedValue , boolean hasEmpty) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " EFV.CODE,"
                + " EFV.VALUE"
                + " FROM"
                + " ETS_FLEX_VALUE_SET EFVS,"
                + " ETS_FLEX_VALUES    EFV"
                + " WHERE"
                + " EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
                + " AND EFVS.CODE = ?\n"
                + " AND EFV.CODE <> 'PRJ_MTL'";
        sqlArgs.add(AssetsDictConstant.FINANCE_PROP);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
//        if(selectedValue.equals("")){
//            selectedValue = "ASSETS";
//        }
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, hasEmpty );
    }
    
    /**
     * ���ܣ���ȡ�豸�����ֵ�(�����豸̨��ά��)
     * @param selectedValue String
     * @return String
     * @throws QueryException
     */
    public String getFinancePropOption(String selectedValue) throws QueryException { 
        return getFinancePropOption(selectedValue, false);
    }
    
    
    /**
     * ���ܣ���ȡ�豸�����ֵ�(�����豸̨��ά��)
     * @param selectedValue String
     * @return String
     * @throws QueryException
     */
    public String getAssetsPropOption(String selectedValue) throws QueryException {
        StringBuilder optionBuilder = new StringBuilder();
        String[] valueArr = {"ASSETS", "OTHERS", "UNKNOW"};
        String[] lebelArr = {"�ʲ�", "����", "δ֪"};
        String[] tdValueArr = {"TD_ASSETS", "TT_ASSETS", "UNKNOW"};
        String[] tdLebelArr = {"TD�ʲ�", "��ͨ�ʲ�", "δ֪"};
        String[] loopValueArr = null;
        String[] loopLabelArr = null;
        if(userAccount.getIsTd().equals("Y")){
            loopValueArr = tdValueArr;
            loopLabelArr = tdLebelArr;
            if(selectedValue.equals("")){
                selectedValue = "TD_ASSETS";
            }
        } else {
            loopValueArr = valueArr;
            loopLabelArr = lebelArr;
            if(selectedValue.equals("")){
                selectedValue = "ASSETS";
            }
        }
        int optCount = loopValueArr.length;
        for(int i = 0; i < optCount; i++){
            optionBuilder.append("<option value=\"");
            optionBuilder.append(loopValueArr[i]);
            if(loopValueArr[i].equals(selectedValue)){
                optionBuilder.append("\" selected>");
            }else{
            	optionBuilder.append("\">");
            }
            optionBuilder.append(loopLabelArr[i]);
            optionBuilder.append("</option>");
        }
        return optionBuilder.toString();
    }



    /**
     * ���ܣ���ȡ�ʲ�״̬������
     * @param selectedValue String
     * @return String
     * @throws QueryException
     */
    public String getAssetsStatusOption(String selectedValue) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " EFV.CODE,"
                + " EFV.VALUE"
                + " FROM"
                + " ETS_FLEX_VALUE_SET EFVS,"
                + " ETS_FLEX_VALUES    EFV"
                + " WHERE"
                + " EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
                + " AND EFVS.CODE = ?"
                + " AND EFV.ATTRIBUTE2 = 'ASSETS_STATUS'";
        sqlArgs.add(AssetsDictConstant.ITEM_STATUS);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }

    /**
     * ���ܣ���ȡ���в���������
     * @param selectedValue String
     * @return String
     * @throws QueryException
     */
    public String getAllDeptOption(String selectedValue) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " AMD.DEPT_CODE,"
                + " AMD.DEPT_NAME"
                + " FROM"
                + " AMS_MIS_DEPT AMD"
                + " WHERE"
                + " AMD.COMPANY_CODE = ?" 
                + " ORDER BY AMD.DEPT_NAME ";
        sqlArgs.add(userAccount.getCompanyCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }

    /**
     * ���ܣ���ȡ�̵㹤���е�ɨ��רҵ
     * @param selectedValue String
     * @return String
     * @throws QueryException
     */
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

    public String getRejectTypeOption(String selectedValue) throws QueryException {
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
                + " AND EFVS.CODE = 'DIS_TYPE'" +
                "   AND EFV.CODE !='DB_DISCARDED'";
//		sqlArgs.add(AssetsDictConstant.ITEM_TYPE);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }

    /**
     * ���ܣ���ȡ�ɱ�����������
     * @param orgId         OU��֯ID
     * @param selectedValue String
     * @return String
     * @throws QueryException
     */
    public String getCostCenterOption(int orgId, String selectedValue) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = 
        		/*  "SELECT "
                + " 	  ACCV.COST_CENTER_CODE COST_CODE,"
                + " 	  ACCV.COST_CENTER_NAME COST_NAME"
                + "  FROM MS_COST_CENTER_V   ACCV"
                + " WHERE EXISTS("
                + " 	   SELECT NULL"
                + " 		 FROM AMS_COST_DEPT_MATCH ACDM"
                + " 		WHERE 1 = 1"
                + " 		  AND ACCV.COST_CENTER_CODE = ACDM.COST_CENTER_CODE"
                + " 		  AND ACCV.IS_VIRTUAL_COST = 'N')"
                + "   AND ACCV.ENABLED_FLAG = 'Y'"
                + "   AND ACCV.ORGANIZATION_ID = ?";*/
				"SELECT ACCV.COST_CENTER_CODE COST_CODE, \n" +
		        " 	    ACCV.COST_CENTER_NAME COST_NAME \n" +
		        "  FROM AMS_COST_CENTER_V   ACCV, \n" +
		        "       AMS_COST_DEPT_MATCH ACDM, \n" +
		        "       AMS_MIS_DEPT 		AMD   \n" +
		        " WHERE ACCV.COST_CENTER_CODE = ACDM.COST_CENTER_CODE \n" +
		        "	AND AMD.DEPT_CODE = ACDM.DEPT_CODE \n" +
		        "   AND AMD.COMPANY_CODE = ACCV.COMPANY_CODE \n" +
		        " 	AND ACCV.IS_VIRTUAL_COST = 'N' \n" +
		        "   AND ACCV.ENABLED_FLAG = 'Y' \n" +
		        "   AND ACCV.ORGANIZATION_ID = ? \n" +
		        "   AND AMD.DEPT_CODE = ? \n" ;
        
        if (StrUtil.isEmpty(orgId)) {
            orgId = userAccount.getOrganizationId();
        }
        sqlArgs.add(orgId);
        sqlArgs.add(userAccount.getDeptCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }


    /**
     * ���ܣ���ȡ�ɱ�����������
     * @param selectedValue String
     * @return String
     * @throws QueryException
     */
    public String getCostCenterOption(String selectedValue) throws QueryException {
        return getCostCenterOption(userAccount.getOrganizationId(), selectedValue);
    }

    /**
     * ���ܣ���������ڼ�(2004��1�¿�ʼ����ǰ����50��֮��)
     * @param selectedValue String
     * @return String
     * @throws CalendarException
     */
    public String getAccPeriodOpt(String selectedValue) throws CalendarException {
        StringBuffer periodOpt = new StringBuffer();
        SimpleCalendar calObj = new SimpleCalendar();
        calObj.setCalendarValue(System.currentTimeMillis());
        calObj.adjust(CalendarConstant.YEAR, 50);
        int startYear = 2004;
        int endYear = calObj.get(CalendarConstant.YEAR);
        String period = "<option value=\"\">------��ѡ��------</option>";
        periodOpt.append(period);
        for (int year = startYear; year < endYear; year++) {
            for (int month = 1; month < 13; month++) {
                if (month < 10) {
                    period = "" + year + "-0" + month;
                } else {
                    period = "" + year + "-" + month;
                }
                periodOpt.append(WorldConstant.ENTER_CHAR);
                periodOpt.append("<option value=\"");
                periodOpt.append(period);
                periodOpt.append("\"");
                if (period.equals(selectedValue)) {
                    periodOpt.append(" selected");
                }
                periodOpt.append(">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                periodOpt.append(period);
                periodOpt.append("</option>");
            }
        }
        return periodOpt.toString();
    }

    /**
     * ���ܣ����ڸ��˹���ͳһ��ѯ��OPTION
     */
//	public String getTransTypeDictOption(String selectedValue) throws QueryException {
//		SQLModel sqlModel = new SQLModel();
//		List sqlArgs = new ArrayList();
//		String sqlStr = "SELECT"
//				+ " EFV.CODE,"
//				+ " EFV.VALUE"
//				+ " FROM"
//				+ " ETS_FLEX_VALUES    EFV,"
//				+ " ETS_FLEX_VALUE_SET EFVS"
//				+ " WHERE"
//				+ " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
//				+ " AND EFV.ENABLED = 'Y'"
//				+ " AND EFVS.CODE IN ('CHKORDER_STATUS', 'ORDER_STATUS')";
//		sqlModel.setSqlStr(sqlStr);
//		sqlModel.setArgs(sqlArgs);
//		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
//		return webFieldProducer.getOptionHtml(selectedValue, true);
//	}

    /**
     * ���ܣ����ڸ��˹���ͳһ��ѯ��OPTION
     */
    public String getTransTypeDictOption(String selectedValue) {
        StringBuffer transOptHtml = new StringBuffer();
        transOptHtml.append("<option value=\"\">--��ѡ��--</option>");
        String[] transOpts = AssetsDictConstant.TRANS_TYPE_VALUES;
        for (int i = 0; i < transOpts.length; i++) {
            transOptHtml.append("<option value=\"");
            transOptHtml.append(transOpts[i]);
            if (transOpts[i].equals(selectedValue)) {
                transOptHtml.append("\" selected>");
            } else {
                transOptHtml.append("\">");
            }
            transOptHtml.append(AssetsDictConstant.TRANS_TYPE_LABELS[i]);
            transOptHtml.append("</option>");
        }
        return transOptHtml.toString();
    }

    public String getDeptOptions(int orgId, String selectedValue) throws QueryException {//ͳ�Ƶ�ֵ�׺��ʲ�ʱ��
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AMD.DEPT_CODE, \n" +
                "       AMD.DEPT_NAME\n" +
                "FROM   AMS_MIS_DEPT AMD, \n" +
                "       ETS_OU_CITY_MAP EOCM\n" +
                "WHERE  AMD.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
                "AND    EOCM.ORGANIZATION_ID = ?" 
                + " AND AMD.ENABLED='Y' " 
                + " ORDER BY AMD.DEPT_NAME ";
        sqlArgs.add(orgId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml(selectedValue, true);
    }

      public String getUserGroupOption2(String sfProject, String sfGroup, String sfRole) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT (CONVERT(VARCHAR,TOTAL.GROUP_ID,1) || ',' || TOTAL.GROUP_NAME || ',' || TOTAL.DEPT_CODE || ',' ||\n" +
                "       TOTAL.DEPT_NAME) TOTAL_COL,\n" +
                "       TOTAL.DEPT_NAME\n" +
                "  FROM (SELECT DISTINCT dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME, ?) GROUP_NAME,\n" +
                "                        SMD.COMPANY_CODE,\n" +
                "                        SMD.DISPLAY_ORDER,\n" +
                "                        SG.GROUP_ID,\n" +
                "                        SMD.DEPT_ID DEPT_CODE,\n" +
                "                        SMD.DEPT_NAME\n" +
                "          FROM SF_GROUP          SG,\n" +
                "               SINO_MIS_DEPT     SMD,\n" +
                "               SF_USER_AUTHORITY SUA,\n" +
                "               SINO_GROUP_MATCH  SGM\n" +
                "         WHERE SUA.USER_ID = ?\n" +
                "           AND SUA.ROLE_NAME = ?\n" +
                "           AND SG.GROUP_NAME = SUA.GROUP_NAME\n" +
                "           AND SGM.GROUP_ID = SG.GROUP_ID\n" +
                "           AND SGM.DEPT_ID = SMD.DEPT_ID\n" +
                "           AND (? IS NULL OR SUA.PROJECT_NAME = ?)\n" +
                "           AND SG.PROJECT_NAME = SUA.PROJECT_NAME\n" +
                "           AND SG.ENABLED <> 'N'\n" +
                "           AND dbo.SFK_IS_SAME_GROUP_WITH_MASK(?, SUA.GROUP_NAME) = 1\n" +
                "           AND (NULL IS NULL OR dbo.SFK_GROUP_IN_LIST(NULL, SUA.GROUP_NAME) <> 1)\n" +
                "        ) TOTAL ";
        sqlArgs.add(sfGroup);
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(sfRole);
        sqlArgs.add(sfProject);
        sqlArgs.add(sfProject);
        sqlArgs.add(sfGroup);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
        return webFieldProducer.getOptionHtml("", false);
    }
      
      /**
       * ���ܣ���ȡ�豸�����ֵ�(�����豸̨��ά��)
       * @param selectedValue String
       * @return String
       * @throws QueryException
       */
      public String getFinanceProp2Option(String selectedValue) throws QueryException {
          SQLModel sqlModel = new SQLModel();
          List sqlArgs = new ArrayList();
          StringBuffer sb = new StringBuffer();
           
          sb.append( "SELECT" );
          sb.append( " EFV.CODE," );
          sb.append( " EFV.VALUE" );
          sb.append( " FROM" );
          sb.append( " ETS_FLEX_VALUE_SET EFVS," );
          sb.append( " ETS_FLEX_VALUES    EFV" );
          sb.append( " WHERE" );
          sb.append( " EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID" );
          sb.append( " AND EFVS.CODE = ?" );
          sb.append( " AND (EFV.CODE = 'DG_ASSETS' " ); 
          sb.append( " 	OR EFV.CODE = 'QD_ASSETS' " ); 
          sb.append( " 	OR EFV.CODE = 'DH_ASSETS' ) " ); 
//          sb.append( " 	OR EFV.CODE = 'SPARE') " );
                  
          sqlArgs.add(AssetsDictConstant.FINANCE_PROP);
          sqlModel.setSqlStr( sb.toString() );
          sqlModel.setArgs(sqlArgs);
          DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
          return webFieldProducer.getOptionHtml(selectedValue, true);
      }
      
      public String getOdAssetsStatusOption(String selectedValue) throws QueryException {
          SQLModel sqlModel = new SQLModel();
          List sqlArgs = new ArrayList();
          String sqlStr = "SELECT"
                  + " EFV.CODE,"
                  + " EFV.VALUE"
                  + " FROM"
                  + " ETS_FLEX_VALUE_SET EFVS,"
                  + " ETS_FLEX_VALUES    EFV"
                  + " WHERE"
                  + " EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
                  + " AND EFVS.CODE = ?"
                  + " AND EFV.ATTRIBUTE2 = 'ASSETS_STATUS'"
                  + " AND EFV.CODE = 'DISCARDED'";
          sqlArgs.add(AssetsDictConstant.ITEM_STATUS);
          sqlModel.setSqlStr(sqlStr);
          sqlModel.setArgs(sqlArgs);
          DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
          return webFieldProducer.getOptionHtml(selectedValue, true);
      }
      /* ���ܣ���ȡ�ʲ����� ѡ��(�����ʲ�����)
      * @param selectedValue String
      * @return DatabaseForWeb()  .getOptionHtml(selectedValue, true)
      * @throws QueryException
      */
      public DatabaseForWeb getAssetsShareOptions() throws QueryException{
    	    SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = "SELECT B.CODE, B.VALUE FROM ETS_FLEX_VALUE_SET T,ETS_FLEX_VALUES B WHERE T.CODE='SHARE_STATUS' AND T.FLEX_VALUE_SET_ID=B.FLEX_VALUE_SET_ID";
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
            return webFieldProducer;
      }
      /**
       * ���� ���ڴ������ӹ���ѡ��ص�ʱ�� ���������б�
       * @param selectedOption
       * @return
       * @throws QueryException
       */
      public String getAmsCountNameOptions(String selectedOption) throws QueryException{
    	  SQLModel sqlModel=new SQLModel();
    	  List sqlArgs=new ArrayList();
    	  String sqlStr="SELECT * FROM AMS_COUNTY WHERE PARENT_CODE IS NULL";
    	  sqlModel.setSqlStr(sqlStr);
    	  sqlModel.setArgs(sqlArgs);
    	  DatabaseForWeb webFieldProducer=new DatabaseForWeb(sqlModel, conn);
    	  return webFieldProducer.getOptionHtml(selectedOption,true);
      }
      /**
       * ������ ���봴�����ӹ���ѡ��ص�ʱ�� ���������б���
       * @return
       * @throws QueryException
       */
      public String getAmsCountNameChByShi(String cityCode, String countyCode) throws QueryException{
    	  SQLModel sqlModel=new SQLModel();
    	  List sqlArgs=new ArrayList();
    	  String sqlStr="select * from AMS_COUNTY where PARENT_CODE ='"+cityCode+"'";
    	  sqlModel.setSqlStr(sqlStr);
    	  sqlModel.setArgs(sqlArgs);
    	  DatabaseForWeb dfb=new DatabaseForWeb(sqlModel,conn);
    	  return dfb.getOptionHtml(countyCode, true);
      }
      /**
       * ���ܣ����̽����̶������б�
       * @param emergentLevel
       * @return
       * @throws QueryException
       */
      public String getAmsEmergentLevel(String selectedOption) throws QueryException {
    	  SQLModel sqlModel=new SQLModel();
    	  List sqlArgs=new ArrayList();
    	  String sqlStr = "SELECT"
              + " EFV.CODE,"
              + " EFV.VALUE"
              + " FROM"
              + " ETS_FLEX_VALUE_SET EFVS,"
              + " ETS_FLEX_VALUES    EFV"
              + " WHERE"
              + " EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID"
              + " AND EFV.ENABLED = 'Y'"
              + " AND EFVS.CODE = 'EMERGENT_LEVEL'";
    	  sqlModel.setSqlStr(sqlStr);
    	  sqlModel.setArgs(sqlArgs);
    	  DatabaseForWeb webFieldProducer=new DatabaseForWeb(sqlModel, conn);
    	  return webFieldProducer.getOptionHtml(selectedOption,false);
      }
      
      
      /**
       * ���ܣ��Ƿ��������б�
       * @param emergentLevel
       * @return
       * @throws QueryException
       */
      public String getReimburseStatus(String selectedOption) throws QueryException {
    	  SQLModel sqlModel=new SQLModel();
    	  List sqlArgs=new ArrayList();
    	  String sqlStr="SELECT DISTINCT ARRIVAL_STATUS,ARRIVAL_STATUS_NAME  FROM ETS_ITEM_TURN";// WHERE  REIMBURSE_STATUS  LIKE dbo.NVL(?,REIMBURSE_STATUS)";
//    	  sqlArgs.add(selectedOption);
    	  sqlModel.setSqlStr(sqlStr);
    	  sqlModel.setArgs(sqlArgs);
    	  DatabaseForWeb webFieldProducer=new DatabaseForWeb(sqlModel, conn);
    	  return webFieldProducer.getOptionHtml(selectedOption,false);
      }
      
      /**
       * ���ܣ�����ѡ����ɸѡ�û�
       * @param emergentLevel
       * @return
       * @throws QueryException
       */
      public String getUserByDeptOption(String selectedValue, String parm) throws QueryException {
          String userByDeptOption = "";
          SQLModel sqlModel = new SQLModel();
              List sqlArgs = new ArrayList();
              String sqlStr = 
              	" SELECT AME.EMPLOYEE_ID, \n" +
              	"		 (AME.EMPLOYEE_NUMBER || '.' || AME.USER_NAME) USER_NAME \n" +
              	"   FROM AMS_MIS_EMPLOYEE AME \n" +
              	"  WHERE AME.COMPANY_CODE = ? \n" +
              	"    AND AME.DEPT_CODE = ISNULL(LTRIM(?), AME.DEPT_CODE) \n" +
              	"    AND AME.ENABLED = 'Y' \n" +
              	"  ORDER BY AME.USER_NAME \n";
              sqlArgs.add(userAccount.getCompanyCode());
              sqlArgs.add(parm);
              sqlModel.setSqlStr(sqlStr);
              sqlModel.setArgs(sqlArgs);
              DatabaseForWeb optProducer = new DatabaseForWeb(sqlModel, conn);
              userByDeptOption = optProducer.getOptionHtml(selectedValue, true);
          return userByDeptOption;
      }
      /**
       * ������ ���봴�����ӹ���ѡ��ص�ʱ�� ���������б���
       * @return
       * @throws QueryException
       */
      public String getAmsCountyCode(String companyCode,String countyCode) throws QueryException{
    	  SQLModel sqlModel=new SQLModel();
    	  List sqlArgs=new ArrayList();
    	  String sqlStr="select * from ETS_COUNTY where COMPANY_CODE ='"+companyCode+"'";
    	  sqlModel.setSqlStr(sqlStr);
    	  sqlModel.setArgs(sqlArgs);
    	  DatabaseForWeb dfb=new DatabaseForWeb(sqlModel,conn);
    	  return dfb.getOptionHtml(countyCode, true);
      }
      
      public String getValue(String matchCode) throws QueryException{
    	  SQLModel sqlModel=new SQLModel();
    	  List sqlArgs=new ArrayList();
    	  String sqlStr="select '*'||v.ATTRIBUTE2||'*' MATCH_CODE,v.VALUE from ETS_FLEX_VALUES v where v.FLEX_VALUE_SET_ID='2'";
    	  sqlModel.setSqlStr(sqlStr);
    	  sqlModel.setArgs(sqlArgs);
    	  DatabaseForWeb dfb=new DatabaseForWeb(sqlModel,conn);
    	  return dfb.getOptionHtml(matchCode, true);
      }
      
      public String getNleValue(String nleCode) throws QueryException{
    	  SQLModel sqlModel=new SQLModel();
    	  List sqlArgs=new ArrayList();
    	  String sqlStr="select LNE_CODE AS NLE_COD,LNE_NAME AS NLE_NAME from AMS_NLE";
    	  sqlModel.setSqlStr(sqlStr);
    	  sqlModel.setArgs(sqlArgs);
    	  DatabaseForWeb dfb=new DatabaseForWeb(sqlModel,conn);
    	  return dfb.getOptionHtml(nleCode, true);
      }
      
      public String getLneValue(String lneCode) throws QueryException{
    	  SQLModel sqlModel=new SQLModel();
    	  List sqlArgs=new ArrayList();
    	  String sqlStr="select NET_UNIT_CODE AS LNE_CODE,LOG_NET_ELE AS LNE_NAME from dbo.AMS_LNE";
    	  sqlModel.setSqlStr(sqlStr);
    	  sqlModel.setArgs(sqlArgs);
    	  DatabaseForWeb dfb=new DatabaseForWeb(sqlModel,conn);
    	  return dfb.getOptionHtml(lneCode, true);
      }
      
      /**
       * ���ܣ���ȡ�ص�ڶ��������б�ֵ
       * @param locDesc
       * @return
       * @throws QueryException
       */
      public String getLoc2Desc(String locDesc) throws QueryException {
    	  SQLModel sqlModel=new SQLModel();
    	  List sqlArgs=new ArrayList();
    	  String sqlStr="SELECT LOC2_CODE,LOC2_DESC FROM ETS_OBJECT_LOC2 ";
    	  sqlModel.setSqlStr(sqlStr);
    	  sqlModel.setArgs(sqlArgs);
    	  DatabaseForWeb dfb=new DatabaseForWeb(sqlModel,conn);
    	  return dfb.getOptionHtml(locDesc, true);
      }

  	/**
  	 * ���ܣ���ȡ�������������
  	 * @param selectedGroup String
  	 * @return String
  	 * @throws QueryException
  	 */
  	public String getAllGroup(String selectedGroup) throws QueryException {
  		return getAllGroup(selectedGroup, 0, false, true);
  	}

  	/**
  	 * ���ܣ���ȡָ�������ѡ�е����������
  	 * @param selectedGroup  String
  	 * @param organizationId String
  	 * @param onlySelf       boolean
  	 * @param addBlank       boolean
  	 * @return String
  	 * @throws QueryException
  	 */
  	public String getAllGroup(String selectedGroup, int organizationId, boolean onlySelf, boolean addBlank) throws
  			QueryException {
  		SQLModel sqlModel = new SQLModel();
  		List sqlArgs = new ArrayList();
          String sqlStr = "SELECT SG.GROUP_ID,\n" +
                  "       SG.GROUP_NAME\n" +
                  "FROM   SF_GROUP SG\n" +
                  "WHERE  EXISTS (SELECT NULL\n" +
                  "        FROM   SINO_GROUP_MATCH SGM,\n" +
                  "               SINO_MIS_DEPT    SMD\n" +
                  "        WHERE  SG.GROUP_ID = SGM.GROUP_ID\n" +
                  "               AND SGM.DEPT_ID = SMD.DEPT_ID)";
  		if (onlySelf) {
  			sqlStr += " AND SG.GROUP_ID=?";
  			sqlArgs.add(StrUtil.strToInt(selectedGroup));
  		} else {
  			if (StrUtil.nullToString(organizationId).equals("")) {
  				if (!userAccount.isSysAdmin()) {
  					sqlStr += " AND SG.ORGANIZATION_ID=?";
  					sqlArgs.add(userAccount.getOrganizationId());
  				}
  			} else {
  				sqlStr += " AND SG.ORGANIZATION_ID=?";
  				sqlArgs.add(userAccount.getOrganizationId());
  			}
  		}
          sqlStr += " ORDER BY GROUP_NAME";
          sqlModel.setSqlStr(sqlStr);
  		sqlModel.setArgs(sqlArgs);
  		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
  		return webFieldProducer.getOptionHtml(selectedGroup, addBlank);
  	}
  	
  	/**
  	 * ���ܣ���ʵ���ʲ��̵㷽ʽ�����б�
  	 * @param emergentLevel
  	 * @return
  	 * @throws QueryException
  	 */
  	public String getApdStatus(String selectedOption,String typeValue) throws QueryException {
  		  SQLModel sqlModel=new SQLModel();
  		  List sqlArgs=new ArrayList();
  		  String sqlStr=" SELECT CREATE_TYPE, CREATE_TYPE_VALUE FROM AMS_CREAE_ORDER \n" ;
  		  if(!selectedOption.equals("")){
  			sqlStr+= " WHERE CREATE_TYPE=?";
  			sqlArgs.add(selectedOption);
  		  }else{
  			sqlStr+= " WHERE TYPE_VALUE=?";
  			sqlArgs.add(typeValue);
  		  }
  		  sqlModel.setSqlStr(sqlStr);
  		  sqlModel.setArgs(sqlArgs);
  		  DatabaseForWeb webFieldProducer=new DatabaseForWeb(sqlModel, conn);
  		  return webFieldProducer.getOptionHtml(selectedOption,false);
  	}
  	
  	
}
