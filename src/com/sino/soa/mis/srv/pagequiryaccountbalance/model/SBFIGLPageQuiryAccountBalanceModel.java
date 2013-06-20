package com.sino.soa.mis.srv.pagequiryaccountbalance.model;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.config.SinoConfig;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.mis.srv.pagequiryaccountbalance.dto.SBFIGLPageQuiryAccountBalanceDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-15
 * Time: 16:51:51
 * To change this template use File | Settings | File Templates.
 */
public class SBFIGLPageQuiryAccountBalanceModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	public SBFIGLPageQuiryAccountBalanceModel(SfUserDTO userAccount, SBFIGLPageQuiryAccountBalanceDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

    public SQLModel getEcouInforModel(String codeCombinationId, String periodName) {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        String sqlStr = "SELECT * FROM ZTE_GL_ACCOUNT_BALANCE ZGAB WHERE ZGAB.CODE_COMBINATION_ID = CONVERT(FLOAT, ?) AND ZGAB.PERIOD_NAME = ?";
		sqlArgs.add(codeCombinationId);
		sqlArgs.add(periodName);
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIGLPageQuiryAccountBalanceDTO srvAssetCategory = (SBFIGLPageQuiryAccountBalanceDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " ZTE_GL_ACCOUNT_BALANCE(" 
			+ " ROW_ID,"
			+ " SET_OF_BOOKS,"
			+ " PERIOD_NAME,"
			+ " CURRENCY_CODE,"
			+ " ACTUAL_FLAG,"
			+ " CODE_COMBINATION_ID,"
			+ " SEGMENT1,"
			+ " SEGMENT1_DES,"
            + " SEGMENT2,"
            + " SEGMENT2_DES,"
            + " SEGMENT3,"
            + " SEGMENT3_DES,"
            + " SEGMENT4,"
            + " SEGMENT4_DES,"
            + " SEGMENT5,"
            + " SEGMENT5_DES,"
            + " SEGMENT6,"
            + " SEGMENT6_DES,"
            + " SEGMENT7,"
            + " SEGMENT7_DES,"
            + " BEGIN_BALANCE_DR,"
            + " BEGIN_BALANCE_CR,"
            + " BEGIN_BALANCE,"
            + " PERIOD_NET_DR,"
            + " PERIOD_NET_CR,"
            + " PERIOD_NET,"
            + " END_BALANCE_DR,"
            + " END_BALANCE_CR,"
            + " END_BALANCE,"
            + " STRUCTURED_HIERARCHY_NAME_COM,"
            + " STRUCTURED_HIERARCHY_NAME_COS,"
            + " CONTRACT_NUM,"
			+ " CONTRACT_LINE_NUM"
			+ ") VALUES ("
			+ " NEWID(),?, ?, ?, ?, CONVERT(FLOAT, ?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), ?, ?)";
		sqlArgs.add(srvAssetCategory.getSetOfBooks());
		sqlArgs.add(srvAssetCategory.getPeriodName());
		sqlArgs.add(srvAssetCategory.getCurrencyCode());
		sqlArgs.add(srvAssetCategory.getActualFlag());
        sqlArgs.add(srvAssetCategory.getCodeCombinationId());
		sqlArgs.add(srvAssetCategory.getSegment1());
		sqlArgs.add(srvAssetCategory.getSegment1Des());
        sqlArgs.add(srvAssetCategory.getSegment2());
		sqlArgs.add(srvAssetCategory.getSegment2Des());
        sqlArgs.add(srvAssetCategory.getSegment3());
		sqlArgs.add(srvAssetCategory.getSegment3Des());
        sqlArgs.add(srvAssetCategory.getSegment4());
		sqlArgs.add(srvAssetCategory.getSegment4Des());
        sqlArgs.add(srvAssetCategory.getSegment5());
		sqlArgs.add(srvAssetCategory.getSegment5Des());
        sqlArgs.add(srvAssetCategory.getSegment6());
		sqlArgs.add(srvAssetCategory.getSegment6Des());
        sqlArgs.add(srvAssetCategory.getSegment7());
		sqlArgs.add(srvAssetCategory.getSegment7Des());
        sqlArgs.add(srvAssetCategory.getBeginBalanceDr());
        sqlArgs.add(srvAssetCategory.getBeginBalanceCr());
        sqlArgs.add(srvAssetCategory.getBeginBalance());
        sqlArgs.add(srvAssetCategory.getPeriodNetDr());
        sqlArgs.add(srvAssetCategory.getPeriodNetCr());
        sqlArgs.add(srvAssetCategory.getPeriodNet());
        sqlArgs.add(srvAssetCategory.getEndBalanceDr());
        sqlArgs.add(srvAssetCategory.getEndBalanceCr());
        sqlArgs.add(srvAssetCategory.getEndBalance());
        sqlArgs.add(srvAssetCategory.getStructuredHierarchyNameCom());
        sqlArgs.add(srvAssetCategory.getStructuredHierarchyNameCos());
        sqlArgs.add(srvAssetCategory.getContractNum());
        sqlArgs.add(srvAssetCategory.getContractLineNum());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SBFIGLPageQuiryAccountBalanceDTO srvAssetCategory = (SBFIGLPageQuiryAccountBalanceDTO)dtoParameter;
		String sqlStr = "UPDATE ZTE_FA_DEPRN_DETAIL"
                    + " SET"
                    + " SET_OF_BOOKS = ?,"
                    + " PERIOD_NAME = ?,"
                    + " CURRENCY_CODE = ?,"
                    + " ACTUAL_FLAG = ?,"
                    + " CODE_COMBINATION_ID = CONVERT(FLOAT, ?),"
                    + " SEGMENT1 = ?,"
                    + " SEGMENT1_DES = ?,"
                    + " SEGMENT2 = ?,"
                    + " SEGMENT2_DES = ?,"
                    + " SEGMENT3 = ?,"
                    + " SEGMENT3_DES = ?,"
                    + " SEGMENT4 = ?,"
                    + " SEGMENT4_DES = ?,"
                    + " SEGMENT5 = ?,"
                    + " SEGMENT5_DES = ?,"
                    + " SEGMENT6 = ?,"
                    + " SEGMENT6_DES = ?,"
                    + " SEGMENT7 = ?,"
                    + " SEGMENT7_DES = ?,"
                    + " BEGIN_BALANCE_DR = CONVERT(FLOAT, ?),"
                    + " BEGIN_BALANCE_CR = CONVERT(FLOAT, ?),"
                    + " BEGIN_BALANCE = CONVERT(FLOAT, ?),"
                    + " PERIOD_NET_DR = CONVERT(FLOAT, ?),"
                    + " PERIOD_NET_CR = CONVERT(FLOAT, ?),"
                    + " PERIOD_NET = CONVERT(FLOAT, ?),"
                    + " END_BALANCE_DR = CONVERT(FLOAT, ?),"
                    + " END_BALANCE_CR = CONVERT(FLOAT, ?),"
                    + " END_BALANCE = CONVERT(FLOAT, ?),"
                    + " STRUCTURED_HIERARCHY_NAME_COM = CONVERT(FLOAT, ?),"
                    + " STRUCTURED_HIERARCHY_NAME_COS = CONVERT(FLOAT, ?),"
                    + " CONTRACT_NUM = ?,"
                    + " CONTRACT_LINE_NUM = ?"
                    + " WHERE CODE_COMBINATION_ID = CONVERT(FLOAT, ?)"
                    + " AND PERIOD_NAME = ?";
		sqlArgs.add(srvAssetCategory.getSetOfBooks());
		sqlArgs.add(srvAssetCategory.getPeriodName());
		sqlArgs.add(srvAssetCategory.getCurrencyCode());
		sqlArgs.add(srvAssetCategory.getActualFlag());
        sqlArgs.add(srvAssetCategory.getCodeCombinationId());
		sqlArgs.add(srvAssetCategory.getSegment1());
		sqlArgs.add(srvAssetCategory.getSegment1Des());
        sqlArgs.add(srvAssetCategory.getSegment2());
		sqlArgs.add(srvAssetCategory.getSegment2Des());
        sqlArgs.add(srvAssetCategory.getSegment3());
		sqlArgs.add(srvAssetCategory.getSegment3Des());
        sqlArgs.add(srvAssetCategory.getSegment4());
		sqlArgs.add(srvAssetCategory.getSegment4Des());
        sqlArgs.add(srvAssetCategory.getSegment5());
		sqlArgs.add(srvAssetCategory.getSegment5Des());
        sqlArgs.add(srvAssetCategory.getSegment6());
		sqlArgs.add(srvAssetCategory.getSegment6Des());
        sqlArgs.add(srvAssetCategory.getSegment7());
		sqlArgs.add(srvAssetCategory.getSegment7Des());
        sqlArgs.add(srvAssetCategory.getBeginBalanceDr());
        sqlArgs.add(srvAssetCategory.getBeginBalanceCr());
        sqlArgs.add(srvAssetCategory.getBeginBalance());
        sqlArgs.add(srvAssetCategory.getPeriodNetDr());
        sqlArgs.add(srvAssetCategory.getPeriodNetCr());
        sqlArgs.add(srvAssetCategory.getPeriodNet());
        sqlArgs.add(srvAssetCategory.getEndBalanceDr());
        sqlArgs.add(srvAssetCategory.getEndBalanceCr());
        sqlArgs.add(srvAssetCategory.getEndBalance());
        sqlArgs.add(srvAssetCategory.getStructuredHierarchyNameCom());
        sqlArgs.add(srvAssetCategory.getStructuredHierarchyNameCos());
        sqlArgs.add(srvAssetCategory.getContractNum());
        sqlArgs.add(srvAssetCategory.getContractLineNum());
        sqlArgs.add(srvAssetCategory.getCodeCombinationId());
        sqlArgs.add(srvAssetCategory.getPeriodName());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SBFIGLPageQuiryAccountBalanceDTO srvAssetCategory = (SBFIGLPageQuiryAccountBalanceDTO)dtoParameter;
        String sqlStr = "SELECT "
            + " CATEGORY_ID,"
            + " DESCRIPTION,"
            + " CATEGORY_TYPE,"
            + " SEGMENT1,"
            + " SEGMENT2,"
            + " SEGMENT3,"
            + " ASSET_COST_ACCOUNT_CCID,"
            + " RESERVE_ACCOUNT_CCID,"
            + " ASSET_CLEARING_ACCOUNT_CCID,"
            + " LIFE_IN_MONTHS,"
            + " PERCENT_SALVAGE_VALUE,"
            + " ENABLED_FLAG,"
            + " ATTRIBUTE1,"
            + " INVENTORIAL,"
            + " CAPITALIZE_FLAG,"
            + " BOOK_TYPE_CODE,"
            + " LAST_UPDATE_DATE"
            + " FROM"
            + " SRV_ASSET_CATEGORY"
            + " WHERE"
            + " (? IS NULL OR CATEGORY_ID LIKE ?)"
            + " AND (? IS NULL OR DESCRIPTION LIKE ?)"
            + " AND (? IS NULL OR CATEGORY_TYPE LIKE ?)"
            + " AND (? IS NULL OR SEGMENT1 LIKE ?)"
            + " AND (? IS NULL OR SEGMENT2 LIKE ?)"
            + " AND (? IS NULL OR SEGMENT3 LIKE ?)"
            + " AND (? IS NULL OR ASSET_COST_ACCOUNT_CCID LIKE ?)"
            + " AND (? IS NULL OR RESERVE_ACCOUNT_CCID LIKE ?)"
            + " AND (? IS NULL OR ASSET_CLEARING_ACCOUNT_CCID LIKE ?)"
            + " AND (? IS NULL OR LIFE_IN_MONTHS LIKE ?)"
            + " AND (? IS NULL OR PERCENT_SALVAGE_VALUE LIKE ?)"
            + " AND (? IS NULL OR ENABLED_FLAG LIKE ?)"
            + " AND (? IS NULL OR ATTRIBUTE1 LIKE ?)"
            + " AND (? IS NULL OR INVENTORIAL LIKE ?)"
            + " AND (? IS NULL OR CAPITALIZE_FLAG LIKE ?)"
            + " AND (? IS NULL OR BOOK_TYPE_CODE LIKE ?)"
            + " AND (? IS NULL OR LAST_UPDATE_DATE LIKE ?)";
//        sqlArgs.add(srvAssetCategory.getBookTypeCode());
//        sqlArgs.add(srvAssetCategory.getBookTypeCode());
//        sqlArgs.add(srvAssetCategory.getLastUpdateDate());
//        sqlArgs.add(srvAssetCategory.getLastUpdateDate());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}