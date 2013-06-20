package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class LastChangeViewModel extends AMSSQLProducer {

	public LastChangeViewModel(BaseUserDTO userAccount, EtsItemInfoDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：获取通过“新增管理资产”栏目创建的信息
	 * @return SQLModel
	 */
	public SQLModel getLastChangeModel() {
		SQLModel sqlModel = new SQLModel();
		EtsItemInfoDTO dto = (EtsItemInfoDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr =
			"SELECT TEMP_V.BARCODE,\n" +
			"       TEMP_V.LAST_CHG_USER,\n" +
			"       TEMP_V.LAST_ORDER_TYPE,\n" +
			"       TEMP_V.LAST_ORDER_NO,\n" +
			"       TEMP_V.LAST_CHG_DATE\n" +
			"FROM   (SELECT EII.BARCODE,\n" +
			"               SU.USERNAME LAST_CHG_USER,\n" +
			"               EII.CREATION_DATE LAST_CHG_DATE,\n" +
			"               '资产新增单' LAST_ORDER_TYPE,\n" +
			"               EAAH.BILL_NO LAST_ORDER_NO\n" +
			"        FROM   ETS_ITEM_INFO    EII,\n" +
			"               ETS_ASSETS_ADD_H EAAH,\n" +
			"               ETS_ASSETS_ADD_L EAAL,\n" +
			"               SF_USER          SU\n" +
			"        WHERE  EII.BARCODE = EAAL.BARCODE\n" +
			"               AND EAAL.HEAD_ID = EAAH.HEAD_ID\n" +
			"               AND EAAL.CREATE_USER = SU.USER_ID\n" +
			"               AND EAAH.STATUS = '1'\n" +
			"               AND EII.BARCODE = ?\n" +
			"        UNION ALL\n" +
			"        SELECT EII.BARCODE,\n" +
			"               SU.USERNAME LAST_CHG_USER,\n" +
			"               AICL.CREATION_DATE LAST_CHG_DATE,\n" +
			"               '' LAST_ORDER_TYPE,\n" +
			"               '' LAST_ORDER_NO\n" +
			"        FROM   AMS_ITEM_CORRECT_LOG AICL,\n" +
			"               ETS_ITEM_INFO        EII,\n" +
			"               SF_USER              SU\n" +
			"        WHERE  AICL.BARCODE = EII.BARCODE\n" +
			"               AND AICL.CREATED_BY = SU.USER_ID\n" +
			"               AND AICL.BARCODE = ?\n" +
			"               AND EXISTS\n" +
			"         (SELECT NULL\n" +
			"                FROM   AMS_ITEM_CORRECT_LOG AICL2\n" +
			"                WHERE  AICL.BARCODE = AICL2.BARCODE HAVING\n" +
			"                 AICL.CREATION_DATE = MAX(AICL2.CREATION_DATE))\n" +
			"        UNION ALL\n" +
			"        SELECT EII.BARCODE,\n" +
			"               SU.USERNAME LAST_CHG_USER,\n" +
			"               AACH.ARCHIVED_DATE LAST_CHG_DATE,\n" +
			"               '盘点工单' LAST_ORDER_TYPE,\n" +
			"               AACH.TRANS_NO LAST_ORDER_NO\n" +
			"        FROM   ETS_ITEM_INFO           EII,\n" +
			"               AMS_ASSETS_CHECK_LINE   AACL,\n" +
			"               AMS_ASSETS_CHECK_HEADER AACH,\n" +
			"               SF_USER                 SU\n" +
			"        WHERE  EII.BARCODE = AACL.BARCODE\n" +
			"               AND AACL.HEADER_ID = AACH.HEADER_ID\n" +
			"               AND AACH.UPLOAD_BY = SU.USER_ID\n" +
			"               AND EII.BARCODE = ?\n" +
			"               AND ((AACL.SYSTEM_STATUS = 'Y' AND AACL.ARCHIVE_STATUS = '1') OR\n" +
			"               (AACL.SCAN_STATUS = 'Y' AND AACL.ARCHIVE_STATUS = '0'))\n" +
			"               AND EXISTS\n" +
			"         (SELECT NULL\n" +
			"                FROM   AMS_ASSETS_CHECK_LINE   AACL2,\n" +
			"                       AMS_ASSETS_CHECK_HEADER AACH2\n" +
			"                WHERE  AACL.BARCODE = AACL2.BARCODE\n" +
			"                       AND AACL2.HEADER_ID = AACH2.HEADER_ID HAVING\n" +
			"                 AACH.ARCHIVED_DATE = MAX(AACH2.ARCHIVED_DATE))\n" +
			"        UNION ALL\n" +
			"        SELECT EII.BARCODE,\n" +
			"               SU.USERNAME LAST_CHG_USER,\n" +
			"               EW.CHECKOVER_DATE LAST_CHG_DATE,\n" +
			"               EFV.VALUE LAST_ORDER_TYPE,\n" +
			"               EW.WORKORDER_NO LAST_ORDER_NO\n" +
			"        FROM   ETS_ITEM_INFO      EII,\n" +
			"               ETS_WORKORDER_DTL  EWD,\n" +
			"               ETS_WORKORDER      EW,\n" +
			"               SF_USER            SU,\n" +
			"               ETS_FLEX_VALUE_SET EFVS,\n" +
			"               ETS_FLEX_VALUES    EFV\n" +
			"        WHERE  EII.BARCODE = EWD.BARCODE\n" +
			"               AND EWD.WORKORDER_NO = EW.WORKORDER_NO\n" +
			"               AND EW.UPLOAD_BY = SU.USER_ID\n" +
			"               AND EW.WORKORDER_TYPE = EFV.CODE\n" +
			"               AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
			"               AND EFVS.CODE = 'WORKORDER_TYPE'\n" +
			"               AND EW.ARCHFLAG = 5\n" +
			"               AND EWD.BARCODE = ?\n" +
			"        UNION ALL\n" +
			"        SELECT EII.BARCODE,\n" +
			"               SU.USERNAME LAST_CHG_USER,\n" +
			"               EW.CHECKOVER_DATE LAST_CHG_DATE,\n" +
			"               '巡检工单' LAST_ORDER_TYPE,\n" +
			"               EW.WORKORDER_NO LAST_ORDER_NO\n" +
			"        FROM   ETS_ITEM_INFO          EII,\n" +
			"               ETS_WORKORDER_DIFF_DTL EWDD,\n" +
			"               ETS_WORKORDER          EW,\n" +
			"               SF_USER                SU\n" +
			"        WHERE  EII.BARCODE = EWDD.BARCODE\n" +
			"               AND EWDD.WORKORDER_NO = EW.WORKORDER_NO\n" +
			"               AND EW.UPLOAD_BY = SU.USER_ID\n" +
			"               AND EW.ARCHFLAG = 5\n" +
			"               AND EWDD.BARCODE = ?) TEMP_V\n" +
			"WHERE  TEMP_V.LAST_CHG_DATE =\n" +
			"       (SELECT MAX(LAST_CHG_DATE)\n" +
			"        FROM   (SELECT EII.BARCODE,\n" +
			"                       SU.USERNAME LAST_CHG_USER,\n" +
			"                       EII.CREATION_DATE LAST_CHG_DATE,\n" +
			"                       '资产新增单' LAST_ORDER_TYPE,\n" +
			"                       EAAH.BILL_NO LAST_ORDER_NO\n" +
			"                FROM   ETS_ITEM_INFO    EII,\n" +
			"                       ETS_ASSETS_ADD_H EAAH,\n" +
			"                       ETS_ASSETS_ADD_L EAAL,\n" +
			"                       SF_USER          SU\n" +
			"                WHERE  EII.BARCODE = EAAL.BARCODE\n" +
			"                       AND EAAL.HEAD_ID = EAAH.HEAD_ID\n" +
			"                       AND EAAL.CREATE_USER = SU.USER_ID\n" +
			"                       AND EAAH.STATUS = '1'\n" +
			"                       AND EII.BARCODE = ?\n" +
			"                UNION ALL\n" +
			"                SELECT EII.BARCODE,\n" +
			"                       SU.USERNAME LAST_CHG_USER,\n" +
			"                       AICL.CREATION_DATE LAST_CHG_DATE,\n" +
			"                       '' LAST_ORDER_TYPE,\n" +
			"                       '' LAST_ORDER_NO\n" +
			"                FROM   AMS_ITEM_CORRECT_LOG AICL,\n" +
			"                       ETS_ITEM_INFO        EII,\n" +
			"                       SF_USER              SU\n" +
			"                WHERE  AICL.BARCODE = EII.BARCODE\n" +
			"                       AND AICL.CREATED_BY = SU.USER_ID\n" +
			"                       AND AICL.BARCODE = ?\n" +
			"                       AND EXISTS\n" +
			"                 (SELECT NULL\n" +
			"                        FROM   AMS_ITEM_CORRECT_LOG AICL2\n" +
			"                        WHERE  AICL.BARCODE = AICL2.BARCODE HAVING\n" +
			"                         AICL.CREATION_DATE = MAX(AICL2.CREATION_DATE))\n" +
			"                UNION ALL\n" +
			"                SELECT EII.BARCODE,\n" +
			"                       SU.USERNAME LAST_CHG_USER,\n" +
			"                       AACH.ARCHIVED_DATE LAST_CHG_DATE,\n" +
			"                       '盘点工单' LAST_ORDER_TYPE,\n" +
			"                       AACH.TRANS_NO LAST_ORDER_NO\n" +
			"                FROM   ETS_ITEM_INFO           EII,\n" +
			"                       AMS_ASSETS_CHECK_LINE   AACL,\n" +
			"                       AMS_ASSETS_CHECK_HEADER AACH,\n" +
			"                       SF_USER                 SU\n" +
			"                WHERE  EII.BARCODE = AACL.BARCODE\n" +
			"                       AND AACL.HEADER_ID = AACH.HEADER_ID\n" +
			"                       AND AACH.UPLOAD_BY = SU.USER_ID\n" +
			"                       AND EII.BARCODE = ?\n" +
			"                       AND\n" +
			"                       ((AACL.SYSTEM_STATUS = 'Y' AND AACL.ARCHIVE_STATUS = '1') OR\n" +
			"                       (AACL.SCAN_STATUS = 'Y' AND AACL.ARCHIVE_STATUS = '0'))\n" +
			"                       AND EXISTS\n" +
			"                 (SELECT NULL\n" +
			"                        FROM   AMS_ASSETS_CHECK_LINE   AACL2,\n" +
			"                               AMS_ASSETS_CHECK_HEADER AACH2\n" +
			"                        WHERE  AACL.BARCODE = AACL2.BARCODE\n" +
			"                               AND AACL2.HEADER_ID = AACH2.HEADER_ID HAVING\n" +
			"                         AACH.ARCHIVED_DATE = MAX(AACH2.ARCHIVED_DATE))\n" +
			"                UNION ALL\n" +
			"                SELECT EII.BARCODE,\n" +
			"                       SU.USERNAME LAST_CHG_USER,\n" +
			"                       EW.CHECKOVER_DATE LAST_CHG_DATE,\n" +
			"                       EFV.VALUE LAST_ORDER_TYPE,\n" +
			"                       EW.WORKORDER_NO LAST_ORDER_NO\n" +
			"                FROM   ETS_ITEM_INFO      EII,\n" +
			"                       ETS_WORKORDER_DTL  EWD,\n" +
			"                       ETS_WORKORDER      EW,\n" +
			"                       SF_USER            SU,\n" +
			"                       ETS_FLEX_VALUE_SET EFVS,\n" +
			"                       ETS_FLEX_VALUES    EFV\n" +
			"                WHERE  EII.BARCODE = EWD.BARCODE\n" +
			"                       AND EWD.WORKORDER_NO = EW.WORKORDER_NO\n" +
			"                       AND EW.UPLOAD_BY = SU.USER_ID\n" +
			"                       AND EW.WORKORDER_TYPE = EFV.CODE\n" +
			"                       AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
			"                       AND EFVS.CODE = 'WORKORDER_TYPE'\n" +
			"                       AND EW.ARCHFLAG = 5\n" +
			"                       AND EWD.BARCODE = ?\n" +
			"                UNION ALL\n" +
			"                SELECT EII.BARCODE,\n" +
			"                       SU.USERNAME LAST_CHG_USER,\n" +
			"                       EW.CHECKOVER_DATE LAST_CHG_DATE,\n" +
			"                       '巡检工单' LAST_ORDER_TYPE,\n" +
			"                       EW.WORKORDER_NO LAST_ORDER_NO\n" +
			"                FROM   ETS_ITEM_INFO          EII,\n" +
			"                       ETS_WORKORDER_DIFF_DTL EWDD,\n" +
			"                       ETS_WORKORDER          EW,\n" +
			"                       SF_USER                SU\n" +
			"                WHERE  EII.BARCODE = EWDD.BARCODE\n" +
			"                       AND EWDD.WORKORDER_NO = EW.WORKORDER_NO\n" +
			"                       AND EW.UPLOAD_BY = SU.USER_ID\n" +
			"                       AND EW.ARCHFLAG = 5\n" +
			"                       AND EWDD.BARCODE = ?))";
		int argCount = 10;
		for (int i = 0; i < argCount; i++) {
			sqlArgs.add(dto.getBarcode());
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
