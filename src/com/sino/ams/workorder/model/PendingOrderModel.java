package com.sino.ams.workorder.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: SfRoleModel</p>
 * <p>Description:程序自动生成SQL构造器“SfRoleModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class PendingOrderModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    public PendingOrderModel(SfUserDTO userAccount, EtsWorkorderDTO dtoParameter) {
		super(userAccount, dtoParameter);
        sfUser=userAccount;
    }


    /**
	 * 功能：框架自动生成页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel(){
        EtsWorkorderDTO workorderDTO=(EtsWorkorderDTO)dtoParameter;
        SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT " +
                "       dbo.AOP_GET_LAST_ORDER_NO(EO.WORKORDER_OBJECT_NO) FIRSTPENDINGORDER,\n" +
                "       EW.WORKORDER_BATCH,\n" +
				"       EWB.WORKORDER_BATCH_NAME,\n" +
				"       EW.SYSTEMID,\n" +
				"       EW.WORKORDER_NO,\n" +
				"       EW.WORKORDER_OBJECT_NO,\n" +
				"       'PLACEHOLDER' PLACEHOLDER,\n" +
				"       EW.WORKORDER_TYPE,\n" +
				"       dbo.APP_GET_FLEX_VALUE(EW.WORKORDER_TYPE,'WORKORDER_TYPE') WORKORDER_TYPE_DESC,\n" +
				"       EW.START_DATE,\n" +
				"       EW.IMPLEMENT_BY,\n" +
				"       EO.WORKORDER_OBJECT_LOCATION OBJECT_NAME,\n" +
				"       SUV.USERNAME,\n" +
				"       EW.UPLOAD_DATE UPLOAD_DATE,\n" +
				"       EW.ORGANIZATION_ID,\n" +
//				"       EW.PRJ_ID,\n" +
				//"       '' FIRSTPENDINGORDER,\n" +
				"       EW.DISTRIBUTE_DATE,\n" +
				"       '' HASDIFF\n" +
				"  FROM ETS_WORKORDER EW, SF_USER_V SUV,ETS_WORKORDER_BATCH EWB,ETS_OBJECT EO\n" +
				" WHERE EW.WORKORDER_FLAG = '" + DictConstant.WORKORDER_STATUS_UPLOADED + "'\n" +
				"   AND EW.WORKORDER_BATCH=EWB.WORKORDER_BATCH\n" +
				"   AND EW.IMPLEMENT_BY = SUV.USER_ID\n" +
				"   AND EW.WORKORDER_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
				"   AND EW.ORGANIZATION_ID = EO.ORGANIZATION_ID\n" +
				"   AND EW.ORGANIZATION_ID=?\n" +
				"   AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.WORKORDER_NO LIKE ?)\n" +
				"   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_LOCATION LIKE ?)\n" +
				/*"   AND (EW.CHECKOVER_BY=? OR EW.CHECKOVER_BY " + SyBaseSQLUtil.isNull() + " )\n" +
				"   AND (EXISTS (SELECT 'A'\n" +
				"          FROM SF_USER_RIGHT SUR\n" +
				"         WHERE SUR.USER_ID = ?\n" +
				"           AND EW.GROUP_ID = SUR.GROUP_ID)\n)" +*/
                "   AND (\n" +
                "        EW.CHECKOVER_BY=?\n" +
                "        OR (EW.CHECKOVER_BY <= 0  \n" +
                "             AND EXISTS (SELECT 'A'\n" +
                "                    FROM SF_USER_RIGHT SUR\n" +
                "                   WHERE SUR.USER_ID = ?\n" +
                "                     AND EW.GROUP_ID = SUR.GROUP_ID)\n" +
                "       ))" ;
                
                if (workorderDTO.getWorkorderType().equals("18")) { //交接
                	sqlStr += "   AND EW.WORKORDER_TYPE = '18' \n" ;
                	if(workorderDTO.getPrjId().equals("d87500c590d34fd3882dd1a73757ef0d")){
                		sqlStr += "   AND EW.PRJ_ID = 'd87500c590d34fd3882dd1a73757ef0d' \n" ;
                	}else{
                		sqlStr += "   AND EW.PRJ_ID <> 'd87500c590d34fd3882dd1a73757ef0d' \n" ;
                	}
                } else { //巡检
                	sqlStr += "   AND EW.WORKORDER_TYPE <> '18' \n" ;
                }

                sqlStr += " ORDER BY EW.UPLOAD_DATE";

		sqlArgs.add(sfUser.getOrganizationId());
		sqlArgs.add(workorderDTO.getWorkorderNo());
		sqlArgs.add(workorderDTO.getWorkorderNo());
		sqlArgs.add(workorderDTO.getWorkorderObjectLocation());
		sqlArgs.add(workorderDTO.getWorkorderObjectLocation());
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(sfUser.getUserId());


		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);

		return sqlModel;
	}

}