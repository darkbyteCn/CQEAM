package com.sino.ams.newasset.urgenttrans.pda.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newasset.urgenttrans.constant.UrgentAppConstant;
import com.sino.ams.newasset.urgenttrans.dto.UrgentHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dto.BaseUserDTO;

public class UrgentDownloadModel extends AMSSQLProducer {
    private SfUserDTO user = null;
    UrgentHeaderDTO headerDTO = null;

    public UrgentDownloadModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.headerDTO = (UrgentHeaderDTO) dtoParameter;
        this.user = (SfUserDTO) userAccount;
    }

    /**
     * 获取能下载的网络调拨单
     * @return
     * @throws SQLModelException
     */
    @SuppressWarnings("unchecked")
    public SQLModel getHeadersModel(int type) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        StringBuffer sb = new StringBuffer();


        if (type == UrgentAppConstant.DOWNLOAD_TYPE_OUT) {
            sb.append(" SELECT  \n");
            sb.append(" AATH.TRANS_ID, \n");
            sb.append(" AATH.TRANS_NO, \n");    //单据号
            sb.append(" AATH.TRANS_TYPE, \n");  //单据类型
            sb.append(" AATH.TRANSFER_TYPE, \n");
            sb.append(" AATH.TRANS_STATUS, \n");
            sb.append(" AATH.CREATION_DATE, \n");    //创建日期
            sb.append(" AATH.CREATED_BY, \n");       //经办人
            sb.append(" SU.LOGIN_NAME CREATED, \n");       //经办人

            sb.append(" AATH.FROM_OBJECT_NO,  \n");
            sb.append(" EO1.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,  \n");
            sb.append(" EO1.WORKORDER_OBJECT_CODE FROM_OBJECT_CODE,  \n");

            sb.append(" AATH.TO_OBJECT_NO,  \n");
            sb.append(" EO2.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,   \n");
            sb.append(" EO2.WORKORDER_OBJECT_CODE TO_OBJECT_CODE,  \n");

            sb.append(" AATH.CREATED_REASON \n"); //备注
            sb.append(" FROM  \n");
            sb.append(" AMS_ASSETS_TRANS_HEADER AATH, \n");
            sb.append(" ETS_OBJECT 			 EO1, \n");
            sb.append(" ETS_OBJECT 			 EO2, \n");
            sb.append(" SF_USER                 SU  \n");
            sb.append(" WHERE  \n");
            sb.append(" AATH.TRANS_TYPE = ? \n");
            sb.append(" AND AATH.CREATED_BY = SU.USER_ID \n");
            sb.append(" AND AATH.FROM_OBJECT_NO = EO1.WORKORDER_OBJECT_NO \n");
            sb.append(" AND AATH.TO_OBJECT_NO *= EO2.WORKORDER_OBJECT_NO \n");
            sb.append(" AND AATH.IMPLEMENT_BY = ?  \n");
            sb.append(" AND AATH.TRANS_STATUS = ?  \n");
        } else if (type == UrgentAppConstant.DOWNLOAD_TYPE_IN) {
            sb.append(" SELECT  \n");
            sb.append(" AATH.TRANS_ID, \n");
            sb.append(" AATH.TRANS_NO, \n");    //单据号
            sb.append(" AATH.TRANS_TYPE, \n");  //单据类型
            sb.append(" AATH.TRANSFER_TYPE, \n");
            sb.append(" AATH.TRANS_STATUS, \n");
            sb.append(" AATH.CREATION_DATE, \n");    //创建日期
            sb.append(" AATH.CREATED_BY, \n");       //经办人
            sb.append(" SU.LOGIN_NAME CREATED, \n");       //经办人

            sb.append(" AATH.TO_OBJECT_NO,  \n");
            sb.append(" EO1.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,  \n");
            sb.append(" EO1.WORKORDER_OBJECT_CODE TO_OBJECT_CODE,  \n");

            sb.append(" AATH.TO_OBJECT_NO FROM_OBJECT_NO,  \n");
            sb.append(" EO2.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,   \n");
            sb.append(" EO2.WORKORDER_OBJECT_CODE FROM_OBJECT_CODE,  \n");

            sb.append(" AATH.CREATED_REASON \n"); //备注
            sb.append(" FROM  \n");
            sb.append(" AMS_ASSETS_TRANS_HEADER AATH, \n");
            sb.append(" ETS_OBJECT 			 EO1, \n");
            sb.append(" ETS_OBJECT 			 EO2, \n");
            sb.append(" SF_USER                 SU  \n");
            sb.append(" WHERE  \n");
            sb.append(" AATH.TRANS_TYPE = ? \n");
            sb.append(" AND AATH.CREATED_BY = SU.USER_ID \n");
            sb.append(" AND AATH.FROM_OBJECT_NO = EO1.WORKORDER_OBJECT_NO \n");
            sb.append(" AND AATH.TO_OBJECT_NO = EO2.WORKORDER_OBJECT_NO \n");
            sb.append(" AND AATH.TO_IMPLEMENT_BY = ?  \n");
            sb.append(" AND AATH.TRANS_STATUS = ?  \n");
        }
        sqlArgs.add(UrgentAppConstant.TRANS_TYPE);
        sqlArgs.add(this.user.getUserId());

        if (type == UrgentAppConstant.DOWNLOAD_TYPE_OUT) {
            sqlArgs.add(DictConstant.DISTRIBUTED);
        } else if (type == UrgentAppConstant.DOWNLOAD_TYPE_IN) {
            sqlArgs.add(UrgentAppConstant.STATUS_TRANS_OUT);
        }

        sqlModel.setSqlStr(sb.toString());
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 获取能下载的网络调拨单 行
     * @return
     * @throws SQLModelException
     */
    @SuppressWarnings("unchecked")
    public SQLModel getLinesModel(String transId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

//		<item code="4010-90000077" 
//		item_code="null" 
//		type="未知型号" 
//		category="null"
//		username="null" 
//		start_date="2002-08-10" 
//		name="七期覆盖网工程5"/>

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT AATL.BARCODE,  \n");
        sb.append("        AATL.TRANS_ID,  \n");
        sb.append("        AATL.LINE_ID,  \n");
        sb.append("        ESI.ITEM_NAME,  \n");
        sb.append("        EII.ITEM_CODE,  \n");
        sb.append("        ESI.ITEM_SPEC,  \n");
        sb.append("        ESI.ITEM_CATEGORY,  \n");
        sb.append("        EII.START_DATE,  \n");
        sb.append("        EII.RESPONSIBILITY_USER  \n");
        sb.append(" FROM   AMS_ASSETS_TRANS_LINE AATL,  \n");
        sb.append("        ETS_ITEM_INFO         EII,  \n");
        sb.append("        ETS_SYSTEM_ITEM       ESI  \n");
        sb.append(" WHERE  AATL.BARCODE = EII.BARCODE  \n");
        sb.append("        AND EII.ITEM_CODE = ESI.ITEM_CODE  \n");
        sb.append("        AND AATL.TRANS_ID = ?  \n");

        sqlArgs.add(transId);
        sqlModel.setSqlStr(sb.toString());
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
