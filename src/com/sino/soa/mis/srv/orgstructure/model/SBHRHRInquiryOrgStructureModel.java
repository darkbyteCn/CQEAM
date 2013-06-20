package com.sino.soa.mis.srv.orgstructure.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.mis.srv.orgstructure.dto.SBHRHRInquiryOrgStructureDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-16
 * Time: 18:26:39
 * To change this template use File | Settings | File Templates.
 */
public class SBHRHRInquiryOrgStructureModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	public SBHRHRInquiryOrgStructureModel(SfUserDTO userAccount, SBHRHRInquiryOrgStructureDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

    public SQLModel getDeptInfoModel() {
		SQLModel sqlModel = new SQLModel();
	    String sqlStr = "SELECT AMD.COMPANY_CODE,\n" +
                "       AMD.DEPT_CODE,\n" +
                "       AMD.MIS_DEPT_CODE,\n" +
                "       AMD.DEPT_NAME\n" +
                "  FROM AMS_MIS_DEPT AMD";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
       public SQLModel getOneDeptInfoModel(String companyCode,String misDeptCode) {
		SQLModel sqlModel = new SQLModel();
           List sqlArgs = new ArrayList();
	    String sqlStr = "SELECT AMD.COMPANY_CODE,\n" +
                "       AMD.MIS_DEPT_CODE,\n" +
                "       AMD.DEPT_NAME," +
                "       AMD.DEPT_CODE \n" +
                "  FROM AMS_MIS_DEPT AMD" +
                "     WHERE AMD.COMPANY_CODE=?" +
                "        AND AMD.MIS_DEPT_CODE=?";
            sqlArgs.add(companyCode);
            sqlArgs.add(misDeptCode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBHRHRInquiryOrgStructureDTO srvAssetBookDTO = (SBHRHRInquiryOrgStructureDTO)dtoParameter;
		String sqlStr = "INSERT INTO AMS_MIS_DEPT\n" +
                "  (COMPANY_CODE,\n" +
                "   DEPT_CODE,\n" +
                "   DEPT_NAME,\n" +
                "   LAST_UPDATE_DATE,\n" +
                "   ENABLED,\n" +
                "   IS_DIRECTLY_DEPT,\n" +
                "   MIS_DEPT_CODE,\n" +
                "   IS_TD,\n" +
                "   DEPT_NUMBER)\n" +
                "VALUES\n" +
                "  (?, NEWID(), STR_REPLACE(?,'中国移动通信集团内蒙古有限公司',NULL), GETDATE(), 'Y', 'N', ?, 'N', ?)";
		sqlArgs.add(srvAssetBookDTO.getOrganizationCode());
//        sqlArgs.add(srvAssetBookDTO.getOrganizationId());
		sqlArgs.add(srvAssetBookDTO.getOrganizationName());
		sqlArgs.add(srvAssetBookDTO.getOrganizationId());
        String listNo = srvAssetBookDTO.getListNo();
        if (listNo.equals("")) {
            sqlArgs.add(null);
        } else {
            sqlArgs.add(Integer.parseInt(listNo));
        }
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBHRHRInquiryOrgStructureDTO srvAssetBookDTO = (SBHRHRInquiryOrgStructureDTO)dtoParameter;
		String sqlStr = "UPDATE AMS_MIS_DEPT\n" +
                "   SET " +
//                "COMPANY_CODE = ?, \n" +
                "       DEPT_NAME = STR_REPLACE(?,'中国移动通信集团内蒙古有限公司',NULL), \n" +
                "       LAST_UPDATE_DATE = GETDATE(), \n" +
                "       ENABLED = ?,\n" +
                "       DEPT_NUMBER = ?\n" +
                " WHERE MIS_DEPT_CODE = ?" +
                "     AND COMPANY_CODE = ?";
//		sqlArgs.add(srvAssetBookDTO.getOrganizationCode());
		sqlArgs.add(srvAssetBookDTO.getOrganizationName());
		sqlArgs.add(srvAssetBookDTO.getStructureStatus());
        String listNo = srvAssetBookDTO.getListNo();
        if (listNo.equals("")) {
            sqlArgs.add(null);
        } else {
            sqlArgs.add(Integer.parseInt(listNo));
        }
		sqlArgs.add(srvAssetBookDTO.getOrganizationId());
        sqlArgs.add(srvAssetBookDTO.getOrganizationCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getSinoDeptInfoModel() {
		SQLModel sqlModel = new SQLModel();
	    String sqlStr = "select SMD.DEPT_ID,AMD.MIS_DEPT_CODE from SINO_MIS_DEPT SMD,AMS_MIS_DEPT AMD where SMD.DEPT_ID=AMD.DEPT_CODE";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

    public SQLModel getSinoDeptCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBHRHRInquiryOrgStructureDTO srvAssetBookDTO = (SBHRHRInquiryOrgStructureDTO)dtoParameter;
		String sqlStr = "INSERT INTO SINO_MIS_DEPT\n" +
                "(DEPT_ID, \n" +
                " DEPT_NAME, \n" +
                " ORG_ID, \n" +
                " ENABLED, \n" +
                " LAST_UPDATE_DATE, \n" +
                " COMPANY_CODE,\n" +
                " DEPT_CODE,\n" +
                " PARENT_DEPT_ID,\n" +
                " DISPLAY_ORDER,\n" +
                " SECOND_DEPT,\n" +
                " SPECIALITY_DEPT)\n" +
                "(SELECT ?, STR_REPLACE(?,'中国移动通信集团内蒙古有限公司',NULL), (SELECT ISNULL((SELECT EO.ORGANIZATION_ID FROM ETS_OU_CITY_MAP  EO  WHERE EO.COMPANY_CODE=?),-1)), 'Y', GETDATE(), ?, ?, ?, ?, 'N', 'N')";
        sqlArgs.add(srvAssetBookDTO.getOrganizationId());
		sqlArgs.add(srvAssetBookDTO.getOrganizationName());
        sqlArgs.add(srvAssetBookDTO.getOrganizationCode());
        sqlArgs.add(srvAssetBookDTO.getOrganizationCode());
		sqlArgs.add(srvAssetBookDTO.getOrganizationId());
		sqlArgs.add(srvAssetBookDTO.getParentOrganizationId());
        String listNo = srvAssetBookDTO.getListNo();
        if (listNo.equals("")) {
            sqlArgs.add(null);
        } else {
            sqlArgs.add(Integer.parseInt(listNo));
        }
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getSinoDeptUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBHRHRInquiryOrgStructureDTO srvAssetBookDTO = (SBHRHRInquiryOrgStructureDTO)dtoParameter;
		String sqlStr = "UPDATE SINO_MIS_DEPT \n" +
                "   SET DEPT_NAME = STR_REPLACE(?,'中国移动通信集团内蒙古有限公司',NULL), \n" +
                "        ORG_ID = (SELECT ISNULL((SELECT EO.ORGANIZATION_ID FROM ETS_OU_CITY_MAP  EO  WHERE EO.COMPANY_CODE=?),-1)),\n" +
                "        ENABLED = ?,\n" +
                "        LAST_UPDATE_DATE = GETDATE(),\n" +
                "        COMPANY_CODE = ?,\n" +
                "        DEPT_CODE = ?,\n" +
                "        PARENT_DEPT_ID = ?,\n" +
                "        DISPLAY_ORDER = ?\n" +
                " WHERE DEPT_ID = ?";
		sqlArgs.add(srvAssetBookDTO.getOrganizationName());
        sqlArgs.add(srvAssetBookDTO.getOrganizationCode());
		sqlArgs.add(srvAssetBookDTO.getStructureStatus());
        sqlArgs.add(srvAssetBookDTO.getOrganizationCode());
        sqlArgs.add(srvAssetBookDTO.getOrganizationId());
        sqlArgs.add(srvAssetBookDTO.getParentOrganizationId());
        String listNo = srvAssetBookDTO.getListNo();
        if (listNo.equals("")) {
            sqlArgs.add(null);
        } else {
            sqlArgs.add(Integer.parseInt(listNo));
        }
		sqlArgs.add(srvAssetBookDTO.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}