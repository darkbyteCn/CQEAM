package com.sino.ams.system.object.dao;

import java.sql.Connection;
import java.util.List;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.object.AmsObjectAddressDTO;
import com.sino.ams.system.object.model.AmsObjectAddressModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
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
public class AmsObjectAddressDAO extends AMSBaseDAO {

    public AmsObjectAddressDAO(SfUserDTO userAccount, AmsObjectAddressDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
     *
     * @param userAccount  BaseUserDTO
     * @param dtoParameter DTO
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsObjectAddressDTO dto = (AmsObjectAddressDTO) dtoParameter;
        sqlProducer = new AmsObjectAddressModel(userAccount, dto);
    }

    public String getAddressIdByObjectNos() throws QueryException {
        StringBuilder addressJSON = new StringBuilder();
        AmsObjectAddressModel modelProducer = (AmsObjectAddressModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getAddressIdByObjectNosModel();
        DTOSet dataList = searchDTOSetByModel(sqlModel);
        if (dataList != null && !dataList.isEmpty()) {
            addressJSON.append("[");
            try {
                int fieldCount = dataList.getSize();
                for (int i = 0; i < fieldCount; i++) {
                    if(i > 0){
                        addressJSON.append(",");
                    }
                    addressJSON.append("{");

                    AmsObjectAddressDTO dto = (AmsObjectAddressDTO)dataList.getDTO(i);
                    String addressId = dto.getAddressId();
                    String objectNo = dto.getObjectNo();

                    addressJSON.append("\"addressId\":\"");
                    addressJSON.append(addressId);
                    addressJSON.append("\"");
                    addressJSON.append(",");
                    addressJSON.append("\"objectNo\":\"");
                    addressJSON.append(objectNo);
                    addressJSON.append("\"");

                    addressJSON.append("}");
                }
            } catch (Throwable ex) {
                Logger.logError(ex);
            }
            addressJSON.append("]");
        }
        return addressJSON.toString();
    }
}
