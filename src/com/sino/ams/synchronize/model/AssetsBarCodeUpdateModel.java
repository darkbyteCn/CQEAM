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
 * Time: 3:34:23
 * To change this template use File | Settings | File Templates.
 */
public class AssetsBarCodeUpdateModel extends AMSSQLProducer {
        private SfUserDTO sfUser = null;
        private EamSyschronizeDTO dto = null;

    /**
     * 功能：eAM新增地点同步 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemMatchDTO 本次操作的数据
     */
    public AssetsBarCodeUpdateModel(SfUserDTO userAccount, EamSyschronizeDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
        dto = dtoParameter;
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
         sqlStr ="SELECT EII.SYSTEMID                 SYSTEMID,\n" +
                 "       EII.BARCODE                  BARCODE, --EAM资产标签\n" +
                 "       EFA.TAG_NUMBER               TAG_NUMBER,\n" +
                 "       EFA.ASSET_ID                 ASSET_ID,\n" +
                 "       ESI.ITEM_NAME                TIEM_NAME,\n" +
                 "       ESI.ITEM_SPEC                ITEM_SPEC,\n" +
                 "       AMD.DEPT_NAME                DEPT_NAME,\n" +
                 "       AME.USER_NAME                USER_NAME,\n" +
                 "       EII.REMARK                   REMARK,\n" +
                 "       ESI.ITEM_NAME                ITEM_NAME,\n" +
                 "       EFA.ASSET_NUMBER             ASSET_NUMBER,\n" +
                 "       EII.CREATION_DATE            CREATION_DATE,\n" +
                 "       EII.CREATED_BY               CREATED_BY,\n" +
                 "       EO.WORKORDER_OBJECT_LOCATION ASSETS_LOCATION\n" +
                 "  FROM ETS_ITEM_MATCH     EIM,\n" +
                 "       ETS_FA_ASSETS      EFA,\n" +
                 "       ETS_ITEM_INFO      EII,\n" +
                 "       ETS_SYSTEM_ITEM    ESI,\n" +
                 "       AMS_OBJECT_ADDRESS AOA,\n" +
                 "       ETS_OBJECT         EO,\n" +
                 "       AMS_MIS_DEPT       AMD,\n" +
                 "       AMS_MIS_EMPLOYEE   AME\n" +
                 " WHERE EIM.ASSET_ID = EFA.ASSET_ID\n" +
                 "   AND EIM.SYSTEMID = EII.SYSTEMID\n" +
                 "   AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                 "   AND AME.ENABLED = 'Y'\n" +
                 "   AND  " + SyBaseSQLUtil.isNotNull("AME.ENABLED") + " \n" +
                 "   AND EII.ADDRESS_ID *= AOA.ADDRESS_ID\n" +
                 "   AND AOA.OBJECT_NO *= EO.WORKORDER_OBJECT_NO\n" +
                 "   AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
                 "   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                 "   AND EFA.TAG_NUMBER <> EII.BARCODE\n" +
                 "   AND EII.ORGANIZATION_ID = ?\n" +
                 "   AND NOT EXISTS (SELECT NULL\n" +
                 "          FROM ETS_MISFA_UPDATE_LOG EMUL\n" +
                 "         WHERE EMUL.ASSET_ID = EFA.ASSET_ID\n" +
                 "           AND EMUL.ORGANIZATION_ID = EFA.ORGANIZATION_ID\n" +
                 "           AND (EMUL.TRANS_STATUS = 0 OR\n" +
                 "               (EMUL.TRANS_STATUS = 1 AND\n" +
                 "               EMUL.CREATION_DATE =\n" +
                 "               GETDATE())))\n" +
                 "   AND EO.WORKORDER_OBJECT_LOCATION LIKE\n" +
                 "       dbo.NVL(?, EO.WORKORDER_OBJECT_LOCATION)\n" +
                 "   AND EFA.TAG_NUMBER LIKE dbo.NVL(?, EFA.TAG_NUMBER)\n" +
                 "   AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)";

         sqlArgs.add(sfUser.getOrganizationId());
         sqlArgs.add(dto.getNewAssetsLocation());
         sqlArgs.add(dto.getTagNumberFrom());
         sqlArgs.add(dto.getTagNumberTo());
         sqlModel.setSqlStr(sqlStr);
         sqlModel.setArgs(sqlArgs);
         return sqlModel;
     }


}
