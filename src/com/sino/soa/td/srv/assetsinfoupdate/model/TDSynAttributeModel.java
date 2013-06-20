package com.sino.soa.td.srv.assetsinfoupdate.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.soa.util.dto.EtsMisfaUpdateBatchDTO;
import com.sino.soa.util.dto.EtsMisfaUpdateLogDTO;
import com.sino.soa.util.dto.SynLogDTO;
import java.util.ArrayList;
import java.util.List;
/**
 * <p>Title: EtsAutoSynLogModel</p>
 * <p>Description:程序自动生成SQL构造器“获取ETS_ITEM_INFO表的对应属性”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>v
 * @author wangzp
 * @version 1.0
 */

public class TDSynAttributeModel {

	   /**
     * 参数:投资分类ID
     * 获取支撑网设备类型
     * @param 
     * @return SQLModel
     */
    public SQLModel getCexType(String cexId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT SN_CODE,\n" +
        		        " SN_NAME \n" +
        		        " FROM AMS_CEX \n" +
        		        " WHERE AMS_CEX_ID=? " ;
        
        sqlArgs.add(cexId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
	
    /**
     * 获取业务平台编码，网络层次编码 
     * @param 
     * @return SQLModel
     */
    public SQLModel getOpeModel(String  opeId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "select AO.OPE_CODE,AO.OPE_NAME from AMS_OPE AO WHERE AO.AMS_OPE_ID= ?";
       sqlArgs.add(opeId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
	
	
}
