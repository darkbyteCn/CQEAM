package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AssetsAddDTO;
import com.sino.ams.newasset.dto.AssetsAddLDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: SinoIES</p>
 * <p>Description: 河南移动IES系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author ai
 * @date: 2008-3-14
 * 新增管理资产
 */
public class AssetsAddLModel extends AMSSQLProducer {

    public AssetsAddLModel(SfUserDTO userAccount, AssetsAddDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：框架自动生成备件事务行表(EAM) AMS_ITEM_TRANS_L数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AssetsAddLDTO dto = (AssetsAddLDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                        + " ETS_ASSETS_ADD_L("
                        + " LINE_ID,"
                        + " HEAD_ID,"
                        + " BARCODE,"
                        + " ITEM_CODE,"
                        + " RESP_USER,"
                        + " RESP_DEPT,"
                        
                        + " SPEC_DEPT,"
                        
                        + " CREATE_USER,"
                        + " CREATED_DATE,"
                        + " REMARK," 
                        + " MAINTAIN_USER, "
                        + " ADDRESS_ID"
                        + ") VALUES ("
                        +
                "  NEWID() , ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?,?, ?)";

        sqlArgs.add(dto.getHeadId());
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getItemCode());
        sqlArgs.add(dto.getEmployeeId());
        sqlArgs.add(dto.getDeptCode());
        sqlArgs.add(dto.getSpecialityDept());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getRemark1());
        sqlArgs.add(dto.getMaintainUser());
        sqlArgs.add(dto.getAddressId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 headId 构造查询数据SQL。
     * 框架自动生成数据备件事务行表(EAM) ETS_ASSETS_ADD_L详细信息查询SQLModel，请根据实际需要修改。
     * @param headId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getDataByHeadIdModel(String headId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EAAL.HEAD_ID, \n" +
                        "       EAAL.LINE_ID, \n" +
                        "       EAAL.BARCODE, \n" +
                        "       EAAL.ITEM_CODE, \n" +
                        "       EAAL.RESP_USER, \n" +
                        "       EAAL.RESP_DEPT, \n" +
                        
                        "       EAAL.SPEC_DEPT, \n" +
                        "       AMD2.DEPT_NAME SPEC_DEPT_NAME, \n" +
                        
                        "       EAAL.REMARK, \n" +
                        "       EAAL.ADDRESS_ID, \n" +
                        "       ESI.ITEM_NAME, \n" +
                        "       ESI.ITEM_SPEC, \n" +
                        "       AMD.DEPT_NAME, \n" +
                        "       AME.USER_NAME, \n" +
                        "       EO.WORKORDER_OBJECT_NAME, \n" +
                        "       EAAL.MAINTAIN_USER \n" +
                        "  FROM ETS_ASSETS_ADD_L  EAAL, \n" +
                        "		ETS_SYSTEM_ITEM   ESI, \n" +
                        "		AMS_MIS_DEPT      AMD, \n" +
                        
                        "		AMS_MIS_DEPT      AMD2, \n" +
                        
                        "       AMS_MIS_EMPLOYEE  AME, \n" +
                        "		AMS_OBJECT_ADDRESS  AOA, \n" +
                        "		ETS_OBJECT  		EO \n" +
                        " WHERE EAAL.ITEM_CODE = ESI.ITEM_CODE \n" +
                        "   AND EAAL.RESP_DEPT = AMD.DEPT_CODE \n" +
                        
                        "   AND EAAL.SPEC_DEPT *= AMD2.DEPT_CODE \n" +
                        
                        "   AND CONVERT(VARCHAR,EAAL.RESP_USER) = AME.EMPLOYEE_ID \n" +
                        "   AND EAAL.ADDRESS_ID = AOA.ADDRESS_ID \n" +
                        "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n" +
                        "   AND EAAL.HEAD_ID = ? \n";
        sqlArgs.add(headId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 transId 构造数据删除SQL。
     * 框架自动生成数据备件事务行表(EAM) ETS_ASSETS_ADD_L 数据删除SQLModel，请根据实际需要修改。
     * @param headId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getDeleteByHeadIdModel(String headId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                        + " ETS_ASSETS_ADD_L"
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
    public SQLModel getCreateEIIModel(AssetsAddLDTO lineDTO) {
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
                        " SPECIALITY_DEPT," +
                        " MAINTAIN_USER )\n" +
                        " VALUES\n" +
                        "  ( NEWID() ,?, ?,GETDATE(), ?,'UNKNOW', ?, ?, ?, ?, ?, ?)\n";

        sqlArgs.add(lineDTO.getBarcode());
        sqlArgs.add(lineDTO.getItemCode());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(lineDTO.getAddressId());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(lineDTO.getEmployeeId());
        sqlArgs.add(lineDTO.getDeptCode());
        sqlArgs.add(lineDTO.getSpecialityDept());
        sqlArgs.add(lineDTO.getMaintainUser());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
