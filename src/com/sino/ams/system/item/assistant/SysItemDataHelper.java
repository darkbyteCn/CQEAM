package com.sino.ams.system.item.assistant;

import com.sino.ams.system.item.dto.EtsSysitemDistributeDTO;
import com.sino.ams.system.item.dto.EtsSystemItemDTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;
import com.sino.base.util.ConvertUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-9-22
 * Time: 16:43:51
 * To change this template use File | Settings | File Templates.
 */
public class SysItemDataHelper {

    /**
     * 功能：构造设备分类的分配数据
     *
     * @param orgIds
     * @throws DTOException
     * @return
     */
    public static DTOSet getDistriDatas(EtsSystemItemDTO itemData, String[] orgIds) throws DTOException {
        DTOSet distrDatas = new DTOSet();
        if (orgIds != null) {
            EtsSysitemDistributeDTO distrData = null;
//            System.out.println("itemData.getItemCode = " + itemData.getItemCode());
            for (int i = 0; i < orgIds.length; i++) {
                distrData = new EtsSysitemDistributeDTO();
                distrData.setItemCode(itemData.getItemCode());
                distrData.setOrganizationId( ConvertUtil.String2Int( orgIds[i] ) );
                distrDatas.addDTO(distrData);
            }
        }
        return distrDatas;
    }
}
