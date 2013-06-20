package com.sino.nm.ams.mss.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.nm.ams.mss.dto.MssAssetsAddDTO;
import com.sino.nm.ams.mss.dto.MssAssetsAddLDTO;
import com.sino.ams.newasset.dto.AssetsAddLDTO;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: T_yuyao
 * Date: 2011-4-26
 * Time: 11:46:55
 * To change this template use File | Settings | File Templates.
 */
public class MssAssetsAddLModel extends AMSSQLProducer {

    public MssAssetsAddLModel(SfUserDTO userAccount, MssAssetsAddDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：框架自动生成备件事务行表(AMS) AMS_ITEM_TRANS_L数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        MssAssetsAddLDTO dto = (MssAssetsAddLDTO) dtoParameter;
        String sqlStr ="INSERT INTO ETS_MSS_ASSETS_ADD_L\n" +
                "  (LINE_ID,\n" +
                "   HEAD_ID,\n" +
                "   REMARK,\n" +
                "   BARCODE,\n" +
                "   ITEM_NAME,\n" +
                "   ITEM_SPEC,\n" +
                "   ITEM_STATUS,\n" +
                "   ITEM_BRAND,\n" +
                "   ITEM_SERIAL,\n" +
                "   CREATE_USER,\n" +
                "   USE_BY_SYSTEM,\n" +
                "   RESPONSIBILITY_USER,\n" +
                "   RESPONSIBILITY_DEPT,\n" +
                "   MEMORY,\n" +
                "   CPU,\n" +
                "   IP_ADDRESS,\n" +
                "   DISK_INFORMATION,\n" +
                "   SYSTEM_NAME,\n" +
                "   TRUSTEESHIP_TYPE,\n" +
                "   IMPORTANT_LEVEL,\n" +
                "   ITEM_CATEGORY1,\n" +
                "   ITEM_CATEGORY2,\n" +
                "   ITEM_CATEGORY3,\n" +
                "   ATTRIBUTE1,\n" +
                "   ATTRIBUTE2,\n" +
                "   ATTRIBUTE3,\n" +
                "   MAINTAIN_USER,\n" +
                "   MAINTAIN_DEPT,\n" +
                "   MSS_BARCODE,\n" +
                "   UPDATE_VERSION,\n" +
                "   LICENSE_NUM,\n" +
                "   LICENSE_NAME,\n" +
                "   SOFT_MEDIUM,\n" +
                "   PRODUCT_NUM,\n" +
                "   USER_LEVEL,\n" +
                "   THIRD_COMPANY,\n" +
                "   SECURE_LEVEL,\n" +
                "   PRODUCT_ID,\n" +
                "   ADDRESS,\n" +
                "   COMPLETENESS_LEVEL)\n" +
                "VALUES\n" +
                "  (NEWID(),\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?)";

        sqlArgs.add(dto.getHeadId());
        sqlArgs.add(dto.getRemark1());
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getItemName1());
        sqlArgs.add(dto.getItemSpec1());
        sqlArgs.add(dto.getItemStatus());
        sqlArgs.add(dto.getItemBrand());
        sqlArgs.add(dto.getItemSerial());
        sqlArgs.add(userAccount.getUserId());

        sqlArgs.add(dto.getUseBySystem());
        sqlArgs.add(dto.getRespUser());
        sqlArgs.add(dto.getRespDept());
        sqlArgs.add(dto.getMemory());
        sqlArgs.add(dto.getCpu());
        sqlArgs.add(dto.getIpAddress());
        sqlArgs.add(dto.getDiskInformation());
        sqlArgs.add(dto.getSystemName());
        sqlArgs.add(dto.getTrusteeshipType());
        sqlArgs.add(dto.getImportantLevel());

        sqlArgs.add(dto.getItemCategory1());
        sqlArgs.add(dto.getItemCategory2());
        sqlArgs.add(dto.getItemCategory3());
        sqlArgs.add(dto.getAttribute1());
        sqlArgs.add(dto.getAttribute2());
        sqlArgs.add(dto.getAttribute3());
        sqlArgs.add(dto.getMaintainUser());
        sqlArgs.add(dto.getMaintainDept());
        sqlArgs.add(dto.getMssBarcode());
        sqlArgs.add(dto.getUpdateVersion());
        sqlArgs.add(dto.getLicenseNum());
        sqlArgs.add(dto.getLicenseName());
        sqlArgs.add(dto.getSoftMedium());
        sqlArgs.add(dto.getProductNum());
        sqlArgs.add(dto.getUserLevel());
        sqlArgs.add(dto.getThirdCompany());
        sqlArgs.add(dto.getSecureLevel());
        sqlArgs.add(dto.getProductId());
        sqlArgs.add(dto.getWorkorderObjectName());
        sqlArgs.add(dto.getCompletenessLevel());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 headId 构造查询数据SQL。
     * 框架自动生成数据备件事务行表(AMS) ETS_MSS_ASSETS_ADD_L详细信息查询SQLModel，请根据实际需要修改。
     * @param headId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getDataByHeadIdModel(String headId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr ="SELECT EAAL.* FROM ETS_MSS_ASSETS_ADD_L EAAL WHERE EAAL.HEAD_ID = ?";
        sqlArgs.add(headId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
     public SQLModel getDataByHeadIPrintdModel(String headId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EAAL.HEAD_ID,\n" +
                        "       EAAL.LINE_ID,\n" +
                        "       EAAL.BARCODE,\n" +
                        "       EAAL.ITEM_CODE,\n" +
                        "       EAAL.RESP_USER,\n" +
                        "       EAAL.RESP_DEPT,\n" +
                        "       EAAL.REMARK,\n" +
                        "       EAAL.ADDRESS_ID,\n" +
                        "       ESI.ITEM_NAME,\n" +
                        "       ESI.ITEM_SPEC,\n" +
                        "       AMD.DEPT_NAME,\n" +
                        "       AME.USER_NAME,\n" +
                        "       EO.WORKORDER_OBJECT_NAME," +
                "             EAAL.MAINTAIN_USER ," +
                "             EAAL.COST\n" +
                        "  FROM ETS_MSS_ASSETS_ADD_L  EAAL, ETS_SYSTEM_ITEM  ESI,AMS_MIS_DEPT  AMD,\n" +
                        "       AMS_MIS_EMPLOYEE  AME,AMS_OBJECT_ADDRESS  AOA, ETS_OBJECT  EO\n" +
                        " WHERE EAAL.ITEM_CODE = ESI.ITEM_CODE\n" +
                        "   AND EAAL.RESP_DEPT = AMD.DEPT_CODE\n" +
                        "   AND EAAL.RESP_USER = AME.EMPLOYEE_ID\n" +
                        "   AND EAAL.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                        "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                        "   AND EAAL.HEAD_ID = ?";
        sqlArgs.add(headId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    /**
     * 功能：根据外键关联字段 transId 构造数据删除SQL。
     * 框架自动生成数据备件事务行表(AMS) ETS_MSS_ASSETS_ADD_L 数据删除SQLModel，请根据实际需要修改。
     * @param headId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getDeleteByHeadIdModel(String headId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                        + " ETS_MSS_ASSETS_ADD_L"
                        + " WHERE"
                        + " HEAD_ID = ?";
        sqlArgs.add(headId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 行信息写入ETS_ITEM_INFO的数据插入SQLModel
     * @return
     */
    public SQLModel getCreateEIIModel(MssAssetsAddLDTO lineDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " INSERT  INTO " +
                        " ETS_ITEM_INFO(" +
                        " SYSTEMID,\n" +
                        " BARCODE,\n" +
                        " ITEM_CODE,\n" +
                        " CREATION_DATE,\n" +
                        " CREATED_BY,\n" +
                        " FINANCE_PROP,\n" +
                        " ADDRESS_ID,\n" +
                        " ORGANIZATION_ID,\n" +
                        " RESPONSIBILITY_USER,\n" +
                        " RESPONSIBILITY_DEPT," +
                "        MAINTAIN_USER," +
                "        COST ," +
                "        ITEM_USAGE_STATUS," +
                "        ITEM_USAGE)\n" +
                        " VALUES\n" +
                        "  (NEWID(),?, ?,GETDATE(), ?,'UNKNOW', ?, ?, ?, ?,?,?,?,?)\n";

        sqlArgs.add(lineDTO.getBarcode());
        sqlArgs.add(lineDTO.getItemCode());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(lineDTO.getAddressId());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(lineDTO.getEmployeeId());
        sqlArgs.add(lineDTO.getDeptCode());
        sqlArgs.add(lineDTO.getMaintainUser());
        sqlArgs.add(lineDTO.getCost());
        sqlArgs.add(lineDTO.getItemUsageStatus());
        sqlArgs.add(lineDTO.getItemUsage());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel getCreateESIModel(MssAssetsAddLDTO lineDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO EAM_MSS_ITEM_INFO\n" +
                "  (ITEM_ID,\n" +
                "   BARCODE,\n" +
                "   ITEM_NAME,\n" +
                "   ITEM_SPEC,\n" +
                "   ITEM_STATUS,\n" +
                "   ITEM_BRAND,\n" +
                "   ITEM_SERIAL,\n" +
                "   CREATED_BY,\n" +
                "   USE_BY_SYSTEM,\n" +
                "   RESPONSIBILITY_USER,\n" +
                "   RESPONSIBILITY_DEPT,\n" +
                "   MEMORY,\n" +
                "   CPU,\n" +
                "   IP_ADDRESS,\n" +
                "   DISK_INFORMATION,\n" +
                "   SYSTEM_NAME,\n" +
                "   TRUSTEESHIP_TYPE,\n" +
                "   IMPORTANT_LEVEL,\n" +
                "   ITEM_CATEGORY1,\n" +
                "   ITEM_CATEGORY2,\n" +
                "   ITEM_CATEGORY3,\n" +
                "   ATTRIBUTE1,\n" +
                "   ATTRIBUTE2,\n" +
                "   ATTRIBUTE3,\n" +
                "   MAINTAIN_USER,\n" +
                "   MAINTAIN_DEPT,\n" +
                "   MSS_BARCODE,\n" +
                "   UPDATE_VERSION,\n" +
                "   LICENSE_NUM,\n" +
                "   LICENSE_NAME,\n" +
                "   SOFT_MEDIUM,\n" +
                "   PRODUCT_NUM,\n" +
                "   USER_LEVEL,\n" +
                "   THIRD_COMPANY,\n" +
                "   SECURE_LEVEL,\n" +
                "   PRODUCT_ID,\n" +
                "   ADDRESS,\n" +
                "   COMPLETENESS_LEVEL)\n" +
                "VALUES\n" +
                "  (NEWID(),\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?)";

        sqlArgs.add(lineDTO.getBarcode());
        sqlArgs.add(lineDTO.getItemName1());
        sqlArgs.add(lineDTO.getItemSpec1());
        sqlArgs.add(lineDTO.getItemStatus());
        sqlArgs.add(lineDTO.getItemBrand());
        sqlArgs.add(lineDTO.getItemSerial());
        sqlArgs.add(userAccount.getUserId());

        sqlArgs.add(lineDTO.getUseBySystem());
        sqlArgs.add(lineDTO.getRespUser());
        sqlArgs.add(lineDTO.getRespDept());
        sqlArgs.add(lineDTO.getMemory());
        sqlArgs.add(lineDTO.getCpu());
        sqlArgs.add(lineDTO.getIpAddress());
        sqlArgs.add(lineDTO.getDiskInformation());
        sqlArgs.add(lineDTO.getSystemName());
        sqlArgs.add(lineDTO.getTrusteeshipType());
        sqlArgs.add(lineDTO.getImportantLevel());

        sqlArgs.add(lineDTO.getItemCategory1());
        sqlArgs.add(lineDTO.getItemCategory2());
        sqlArgs.add(lineDTO.getItemCategory3());
        sqlArgs.add(lineDTO.getAttribute1());
        sqlArgs.add(lineDTO.getAttribute2());
        sqlArgs.add(lineDTO.getAttribute3());
        sqlArgs.add(lineDTO.getMaintainUser());
        sqlArgs.add(lineDTO.getMaintainDept());
        sqlArgs.add(lineDTO.getMssBarcode());
        sqlArgs.add(lineDTO.getUpdateVersion());
        sqlArgs.add(lineDTO.getLicenseNum());
        sqlArgs.add(lineDTO.getLicenseName());
        sqlArgs.add(lineDTO.getSoftMedium());
        sqlArgs.add(lineDTO.getProductNum());
        sqlArgs.add(lineDTO.getUserLevel());
        sqlArgs.add(lineDTO.getThirdCompany());
        sqlArgs.add(lineDTO.getSecureLevel());
        sqlArgs.add(lineDTO.getProductId());
        sqlArgs.add(lineDTO.getWorkorderObjectName());
        sqlArgs.add(lineDTO.getCompletenessLevel());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
