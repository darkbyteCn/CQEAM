package com.sino.nm.ams.mss.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.CalendarException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.nm.ams.mss.dto.MssAssetsAddDTO;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: T_yuyao
 * Date: 2011-4-26
 * Time: 11:45:09
 * To change this template use File | Settings | File Templates.
 */
public class MssAssetsAddModel extends AMSSQLProducer {
              SfUserDTO sfDto=null;
    public MssAssetsAddModel(SfUserDTO userAccount, MssAssetsAddDTO dtoParameter) {

        super(userAccount, dtoParameter);
          this.sfDto=userAccount;
    }

    /**
     * 功能：执行新增数据操作。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
//            try {
        List sqlArgs = new ArrayList();
        MssAssetsAddDTO dto = (MssAssetsAddDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                        + " ETS_MSS_ASSETS_ADD_H("
                        + " HEAD_ID,"
                        + " BILL_NO,"
                        + " STATUS,"
                        + " CREATE_USER,"
                        + " CREATED_DATE,"
                        + " REMARK"
                        + ") "
                        + " VALUES (?, ?, ?, ?, GETDATE(),?)";

        sqlArgs.add(dto.getHeadId());
        sqlArgs.add(dto.getBillNo());
        sqlArgs.add(dto.getStatus());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getRemark());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件事务头表(AMS) ETS_MSS_ASSETS_ADD_H数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        MssAssetsAddDTO dto = (MssAssetsAddDTO) dtoParameter;
        String sqlStr = "SELECT EAAH.HEAD_ID,\n" +
                        "       EAAH.BILL_NO,\n" +
//                      "       DECODE(EAAH.STATUS,0,'未完成',1,'已完成')  STATUS,\n" +
//                        "		(CASE WHEN EAAH.STATUS = 0 THEN '未完成' ELSE '已完成' END) AS STATUS,\n" +
                        "		STATUS,\n" +
                        "       EAAH.CREATE_USER  CREATED_BY,\n " +
//                        "       TO_CHAR(EAAH.CREATED_DATE,'YYYY-MM-DD') CREATED_DATE,\n" +
                        "       EAAH.CREATED_DATE,\n" +
                        "       EAAH.REMARK,\n" +
                        "       SU.USERNAME  CREATE_USER\n" +
                        "  FROM ETS_MSS_ASSETS_ADD_H  EAAH,\n" +
                        "       SF_USER           SU\n" +
                        " WHERE EAAH.CREATE_USER = SU.USER_ID\n" +
                        "   AND EAAH.HEAD_ID = ?";
        sqlArgs.add(dto.getHeadId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件事务头表(AMS) ETS_MSS_ASSETS_ADD_H数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        MssAssetsAddDTO dto = (MssAssetsAddDTO) dtoParameter;
        String sqlStr = "UPDATE ETS_MSS_ASSETS_ADD_H"
                        + " SET"
                        + "     STATUS = ?, "
                        + "     REMARK = ? "
                        + " WHERE"
                        + "     HEAD_ID = ?";
//            try {
        sqlArgs.add(dto.getStatus());
        sqlArgs.add(dto.getRemark());
        sqlArgs.add(dto.getHeadId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：新增管理资产查询。
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws com.sino.base.exception.SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            MssAssetsAddDTO dto = (MssAssetsAddDTO) dtoParameter;
//             AssetsAddLDTO dtoL = (AssetsAddLDTO) dtoParameter;
            String sqlStr ="SELECT EAAH.HEAD_ID,\n" +
                    "       EAAH.BILL_NO,\n" +
//                    "       DECODE(EAAH.STATUS, 0, '未完成', 1, '已完成') STATUS,\n" +
                    "		(CASE WHEN EAAH.STATUS = 0 THEN '未完成' ELSE '已完成' END) AS STATUS,\n" +
                    "       EAAH.CREATE_USER,\n" +
                    "       EAAH.CREATED_DATE,\n" +
                    "       EAAL.BARCODE,\n" +
                    "       EAAL.MSS_BARCODE,\n" +
                    "       EAAL.ITEM_NAME,\n" +
                    "       EAAL.ITEM_SPEC,\n" +
                    "       SU.USERNAME\n" +
                    "  FROM ETS_MSS_ASSETS_ADD_H EAAH, ETS_MSS_ASSETS_ADD_L EAAL, SF_USER SU\n" +
                    " WHERE EAAH.HEAD_ID = EAAL.HEAD_ID\n" +
                    "   AND EAAH.CREATE_USER = SU.USER_ID\n" +
                    "   AND SU.ORGANIZATION_ID = ?\n" +
//                  "   AND SU.USERNAME LIKE NVL(?, SU.USERNAME)\n" +
                    "   AND (SU.USERNAME LIKE ? OR ?='' OR ? IS NULL)\n" +
//                  "   AND (EAAL.ITEM_SPEC LIKE ? OR ?='' OR ? IS NULL)\n" +
                    "   AND (" + SyBaseSQLUtil.nullStringParam() + " OR EAAL.ITEM_SPEC LIKE ?)\n" +
//                  "   AND EAAL.ITEM_NAME LIKE NVL(?, EAAL.ITEM_NAME)\n" +
                    "   AND (EAAL.ITEM_NAME LIKE ? OR ?='' OR ? IS NULL)\n" +
////                "   AND EAAH.STATUS = NVL(?, EAAH.STATUS)\n" +
                    "   AND (EAAH.STATUS = ? OR ? = -1)\n" +
//               	"   AND EAAH.CREATED_DATE <= NVL(?, EAAH.CREATED_DATE)\n" +
                    "   AND (EAAH.CREATED_DATE >? OR ?='' OR ? IS NULL )\n" +               
                    "   AND (EAAH.CREATED_DATE <= DATEADD(DAY,1,?) OR ?='' OR ? IS NULL )\n" +
//                  "   AND EAAL.MSS_BARCODE LIKE NVL(?, EAAL.MSS_BARCODE)\n" + 
                    // 条件EAAL.MSS_BARCODE貌似错误，已改为EAAH.BILL_NO
                    "   AND (EAAH.BILL_NO LIKE ? OR ?='' OR ? IS NULL )\n" +
                    " ORDER BY EAAH.BILL_NO, EAAL.BARCODE";
            sqlArgs.add(sfDto.getOrganizationId());
            sqlArgs.add(dto.getCreateUser());
            sqlArgs.add(dto.getCreateUser());
            sqlArgs.add(dto.getCreateUser());
//            
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            
            sqlArgs.add(dto.getStatus());
            sqlArgs.add(dto.getStatus());
            
            sqlArgs.add(dto.getFromDate());
            sqlArgs.add(dto.getFromDate());
            sqlArgs.add(dto.getFromDate());
            
            sqlArgs.add(dto.getToDate());
            sqlArgs.add(dto.getToDate());
            sqlArgs.add(dto.getToDate());
            
            sqlArgs.add(dto.getBarcod());
            sqlArgs.add(dto.getBarcod());
            sqlArgs.add(dto.getBarcod());
  
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }
     public SQLModel getQueryAdressModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();

            List sqlArgs = new ArrayList();
            MssAssetsAddDTO dto = (MssAssetsAddDTO) dtoParameter;
//             AssetsAddLDTO dtoL = (AssetsAddLDTO) dtoParameter;
            String sqlStr = "SELECT EO.WORKORDER_OBJECT_NAME, EO.WORKORDER_OBJECT_CODE\n" +
                    "  FROM AMS_OBJECT_ADDRESS AOA, ETS_OBJECT EO\n" +
                    " WHERE EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                    "   AND EO.OBJECT_CATEGORY = '80'\n" +
                    "   AND EO.ORGANIZATION_ID = ?";
            sqlArgs.add(sfDto.getOrganizationId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
      public SQLModel getQueryItemModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();

            List sqlArgs = new ArrayList();
            MssAssetsAddDTO dto = (MssAssetsAddDTO) dtoParameter;
//             AssetsAddLDTO dtoL = (AssetsAddLDTO) dtoParameter;
            String sqlStr = "SELECT ESI.ITEM_NAME, ESI.ITEM_SPEC\n" +
                    "  FROM ETS_SYSTEM_ITEM ESI, ETS_SYSITEM_DISTRIBUTE ESD\n" +
                    " WHERE ESI.ITEM_CODE = ESD.ITEM_CODE\n" +
                    "   AND ESI.ITEM_CATEGORY IN ('HOUSE', 'LAND', 'OTHERS')\n" +
                    "   AND ESD.ORGANIZATION_ID = ?\n" +
                    " GROUP BY ESI.ITEM_NAME, ESI.ITEM_SPEC";
            sqlArgs.add(sfDto.getOrganizationId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
      public SQLModel getQueryDeptModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();

            List sqlArgs = new ArrayList();
            MssAssetsAddDTO dto = (MssAssetsAddDTO) dtoParameter;
//             AssetsAddLDTO dtoL = (AssetsAddLDTO) dtoParameter;
            String sqlStr = "SELECT AMD.DEPT_NAME, AMD.DEPT_CODE\n" +
                    "  FROM AMS_MIS_DEPT AMD, ETS_OU_CITY_MAP EOCM\n" +
                    " WHERE EOCM.COMPANY_CODE = AMD.COMPANY_CODE\n" +
                    "   AND EOCM.ORGANIZATION_ID = ?";
            sqlArgs.add(sfDto.getOrganizationId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
    public SQLModel getQueryBarcodeModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();

            List sqlArgs = new ArrayList();
            MssAssetsAddDTO dto = (MssAssetsAddDTO) dtoParameter;
//             AssetsAddLDTO dtoL = (AssetsAddLDTO) dtoParameter;
            String sqlStr = "SELECT " +
//            		"ROWNUM LINE_COUNT,\n" +
                    "       EAAL.BARCODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       GETDATE() START_DATE\n" +
                    "  FROM ETS_MSS_ASSETS_ADD_L EAAL, ETS_SYSTEM_ITEM ESI\n" +
                    " WHERE EAAL.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "   AND EAAL.HEAD_ID = ?";
            sqlArgs.add(dto.getHeadId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
    public SQLModel getOrderModel() { //导出模板
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT '4210-20017179' BARCODE,\n" +
                "       '笔记本电脑' ITEM_NAME,\n" +
                "       'IBM T61' ITEM_SPEC,\n" +
                "       '管理信息系统部' ADDRES,\n" +
                "       '42610000.内蒙移动管理信息系统部' DEPT,\n" +
                "       '张三' USER_NAME,\n" +
                "       '张三' M_USER,\n" +
                "       '租赁资产' REMARK\n" +
                "  FROM AMS_EMAIL_CONFIG";
		List sqlArgs = new ArrayList();
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}