package com.sino.ams.system.object.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.object.AmsObjectAddressDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class AmsObjectAddressModel extends AMSSQLProducer {
    public AmsObjectAddressModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }


    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsObjectAddressDTO dto = (AmsObjectAddressDTO) dtoParameter;
        String sqlStr = "INSERT INTO AMS_OBJECT_ADDRESS("
                + " ADDRESS_ID,"
                + " OBJECT_NO,"
                + " BOX_NO,"
                + " NET_UNIT,"
                + " ORGANIZATION_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY,"
                + " REMARK,"
                + " ADDRESS_NO"
                + " ) VALUES ( NEWID() , ?, ?, ?, ?, GETDATE(), ?, GETDATE(), ?, ?, ?)";
        sqlArgs.add(dto.getObjectNo());
        sqlArgs.add(dto.getBoxNo());
        sqlArgs.add(dto.getNetUnit());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getRemark());
        sqlArgs.add(dto.getAddressNo());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getAddressIdByObjectNosModel() {
        SQLModel sqlModel = new SQLModel();
        AmsObjectAddressDTO dto = (AmsObjectAddressDTO) dtoParameter;
        String sqlStr = "SELECT AOA.ADDRESS_ID,\n" +
                "       AOA.OBJECT_NO\n" +
                "FROM   AMS_OBJECT_ADDRESS AOA\n" +
                "WHERE  AOA.OBJECT_NO IN (" + dto.getObjectNos() + ")";
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
}
