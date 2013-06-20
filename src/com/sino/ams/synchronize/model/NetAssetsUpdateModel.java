package com.sino.ams.synchronize.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-3-14
 * Time: 7:29:49
 * To change this template use File | Settings | File Templates.
 */
public class NetAssetsUpdateModel extends AMSSQLProducer {
    private SfUserDTO sfUser = null;

      /**
       * 功能：eAM新增地点同步 数据库SQL构造层构造函数
       *
       * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
       * @param dtoParameter EtsItemMatchDTO 本次操作的数据
       */
      public NetAssetsUpdateModel(SfUserDTO userAccount, EamSyschronizeDTO dtoParameter) {
          super(userAccount, dtoParameter);
          sfUser = userAccount;
      }

       /**
      * 功能：框架自动生成LOCUS页面翻页查询SQLModel，请根据实际需要修改。
      *
      * @return SQLModel 返回页面翻页查询SQLModel
      * @throws com.sino.base.exception.SQLModelException 发生日历异常时转化为该异常抛出
      */
     public SQLModel getPageQueryModel() throws SQLModelException {
         SQLModel sqlModel = new SQLModel();
         List sqlArgs = new ArrayList();
         EamSyschronizeDTO locus = (EamSyschronizeDTO) dtoParameter;
         String sqlStr;
         sqlStr ="SELECT EII.SYSTEMID,\n" +
                 "       EII.BARCODE NEW_BARCODE,\n" +
                 "       ESI.ITEM_NAME NEW_ITEM_NAME,\n" +
                 "       ESI.ITEM_SPEC NEW_ITEM_SPEC,\n" +
                 "       EII.RESPONSIBILITY_USER NEW_USER,\n" +
                 "       AME1.USER_NAME NEW_USER_NAME,\n" +
                 "       EII.RESPONSIBILITY_DEPT NEW_DEPT,\n" +
                 "       AMD1.DEPT_NAME NEW_DEPT_NAME,\n" +
                 "       AMD1.DEPT_CODE DEPT_CODE,\n" +
                 "       EO.WORKORDER_OBJECT_LOCATION NEW_ASSETS_LOCATION,\n" +
                 "       EII.ORGANIZATION_ID,\n" +
                 "       EFA.ASSET_ID,\n" +
                 "       EFA.TAG_NUMBER OLD_BARDOE,\n" +
                 "       EFA.ASSET_NUMBER,\n" +
                 "       EFA.ASSETS_DESCRIPTION OLD_ASSETS_DESCRIPTION,\n" +
                 "       EFA.MODEL_NUMBER OLD_MODEL_NUMBER,\n" +
                 "       EFA.ASSETS_LOCATION OLD_ASSETS_LOCATION,\n" +
                 "       AME2.EMPLOYEE_ID OLD_USER,\n" +
                 "       EFA.ASSIGNED_TO_NAME OLD_USER_NAME,\n" +
                 "       AMD2.DEPT_CODE OLD_DEPT_CODE,\n" +
                 "       AMD2.DEPT_NAME OLD_DEPT_NAME,\n" +
                 "       EII.BARCODE,\n" +
                 "       ESI.ITEM_NAME,\n" +
                 "       EFV.ATTRIBUTE2,\n" +
                 "       EFA.DEPRECIATION_ACCOUNT\n" +
                 "  FROM ETS_ITEM_INFO      EII,\n" +
                 "       ETS_SYSTEM_ITEM    ESI,\n" +
                 "       ETS_FLEX_VALUE_SET EFVS,\n" +
                 "       ETS_FLEX_VALUES    EFV,\n" +
                 "       AMS_OBJECT_ADDRESS AOA,\n" +
                 "       ETS_OBJECT         EO,\n" +
                 "       AMS_MIS_EMPLOYEE   AME1,\n" +
                 "       AMS_MIS_DEPT       AMD1,\n" +
                 "       ETS_ITEM_MATCH     EIM,\n" +
                 "       ETS_FA_ASSETS      EFA,\n" +
                 "       AMS_MIS_EMPLOYEE   AME2,\n" +
                 "       AMS_MIS_DEPT       AMD2\n" +
                 " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                 "   AND ESI.ITEM_CATEGORY *= EFV.CODE\n" +
                 "   AND EFV.FLEX_VALUE_SET_ID *= EFVS.FLEX_VALUE_SET_ID\n" +
                 "   AND EFVS.CODE = 'ITEM_TYPE'\n" +
                 "   AND EFV.ATTRIBUTE2 = 'NET'\n" +
                 "   AND EII.RESPONSIBILITY_USER *= AME1.EMPLOYEE_ID\n" +
                 "   AND EII.RESPONSIBILITY_DEPT *= AMD1.DEPT_CODE\n" +
                 "   AND EII.ADDRESS_ID *= AOA.ADDRESS_ID\n" +
                 "   AND AOA.OBJECT_NO *= EO.WORKORDER_OBJECT_NO\n" +
                 "   AND EII.SYSTEMID = EIM.SYSTEMID\n" +
                 "   AND EIM.ASSET_ID = EFA.ASSET_ID\n" +
                 "   AND EFA.ASSIGNED_TO_NUMBER *= AME2.EMPLOYEE_NUMBER\n" +
                 "   AND AME2.DEPT_CODE *= AMD2.DEPT_CODE\n" +
                 "   AND EII.FINANCE_PROP = 'ASSETS'\n" +
                 "   AND EII.ORGANIZATION_ID = ?\n" +
                 "   AND NOT EXISTS (SELECT NULL\n" +
                 "          FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
                 "         WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
                 "           AND (EMUL.TRANS_STATUS = 0 OR\n" +
                 "               (EMUL.TRANS_STATUS = 1 AND\n" +
                 "               CONVERT(VARCHAR,EMUL.CREATION_DATE,110) =CONVERT(VARCHAR,GETDATE(),110))))\n" +
                 "   AND NOT EXISTS (SELECT NULL\n" +
                 "          FROM AMS_ASSETS_CHK_LOG ASCL\n" +
                 "         WHERE ASCL.BARCODE = EII.BARCODE\n" +
                 "           AND ASCL.SYN_STATUS = 0)\n" +
                 "   AND EXISTS\n" +
                 " (SELECT NULL\n" +
                 "          FROM ETS_WORKORDER_DTL EWL\n" +
                 "         WHERE EWL.BARCODE = EII.BARCODE)\n" +
                 "   AND (ESI.ITEM_NAME <> EFA.ASSETS_DESCRIPTION OR\n" +
                 "       dbo.NVL(ESI.ITEM_SPEC, 'A') <> dbo.NVL(EFA.MODEL_NUMBER, 'A') OR\n" +
                 "       EII.BARCODE <> EFA.TAG_NUMBER OR\n" +
                 "       dbo.NVL(EFA.ASSETS_LOCATION, 'A') <> dbo.NVL(EO.WORKORDER_OBJECT_NAME, 'A') OR\n" +
                 "       dbo.NVL(EFA.ASSIGNED_TO_NAME, 'A') <> dbo.NVL(AME1.USER_NAME, 'A'))\n" +
                 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_DESCRIPTION LIKE ?)\n" +
                 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE LIKE ?)\n" +
                 "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD1.DEPT_NAME LIKE ?)";
         sqlArgs.add(sfUser.getOrganizationId());
         sqlArgs.add(locus.getAssetsDescription());
         sqlArgs.add(locus.getAssetsDescription());
         sqlArgs.add(locus.getBarCode());
         sqlArgs.add(locus.getBarCode());
         sqlArgs.add(locus.getNewDeptName());
         sqlArgs.add(locus.getNewDeptName());
         sqlModel.setSqlStr(sqlStr);
         sqlModel.setArgs(sqlArgs);
         return sqlModel;
     }

}
