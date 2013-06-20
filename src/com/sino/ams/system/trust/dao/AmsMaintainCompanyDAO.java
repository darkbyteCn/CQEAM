package com.sino.ams.system.trust.dao;


import java.sql.Connection;

import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.trust.dto.AmsMaintainCompanyDTO;
import com.sino.ams.system.trust.model.AmsMaintainCompanyModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsMaintainCompanyDAO</p>
 * <p>Description:程序自动生成服务程序“AmsMaintainCompanyDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsMaintainCompanyDAO extends AMSBaseDAO {

    /**
     * 功能：代维公司表(EAM) AMS_MAINTAIN_COMPANY 数据访问层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsMaintainCompanyDTO 本次操作的数据
     * @param conn Connection 数据库连接，由调用者传入。
     */
    public AmsMaintainCompanyDAO(SfUserDTO userAccount, AmsMaintainCompanyDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
        AmsMaintainCompanyDTO dtoPara = (AmsMaintainCompanyDTO)dtoParameter;
        super.sqlProducer = new AmsMaintainCompanyModel((SfUserDTO)userAccount, dtoPara);
    }

    /**
     * 功能：插入代维公司表(EAM)表“AMS_MAINTAIN_COMPANY”数据。

     */
    public void createData() throws DataHandleException {
        try {
           // String tableName = "AMS_MAINTAIN_COMPANY";
//            DataUniqueChecker datChecker = new DataUniqueChecker(tableName, dtoParameter, conn);
//            datChecker.setDBAction(DBActionConstant.INSERT);
//            boolean isValid = datChecker.isDataValid();
//            if (isValid) {
                AmsMaintainCompanyDTO company = (AmsMaintainCompanyDTO)super.dtoParameter;
                 
                if( StrUtil.isEmpty( company.getCompanyId() ) ){
                	SeqProducer seq = new SeqProducer(conn);
                    company.setCompanyId( seq.getGUID() );
                }
                
                setDTOParameter(company);
                super.createData();
                getMessage().addParameterValue("代维公司");
//            } else {
//                prodMessage(MsgKeyConstant.UNIQUE_ERROR);
//                AmsMaintainCompanyDTO dto = (AmsMaintainCompanyDTO)dtoParameter;
//                String msg = "名称为" + dto.getName() + "的代维公司";
//                message.addParameterValue(msg);
//            }
        } 
//        catch (ValidateException ex) {
//            ex.printLog();
//            prodMessage(MsgKeyConstant.COMMON_ERROR);
//            message.addParameterValue("代维公司");
//        } 
        catch (Exception ex) {
            Logger.logError(ex);
            prodMessage(MsgKeyConstant.SEQ_ERROR);
            message.addParameterValue("AMS_MAINTAIN_COMPANY_S");
        }

    }

    /**
     * 功能：更新代维公司表(EAM)表“AMS_MAINTAIN_COMPANY”数据。

     */
    public void updateData() throws DataHandleException {
       super.updateData();
        getMessage().addParameterValue("代维公司");
    }

    /**
     * 功能：删除代维公司表(EAM)表“AMS_MAINTAIN_COMPANY”数据。

     */
    public void deleteData() throws DataHandleException {
       super.deleteData();
        getMessage().addParameterValue("代维公司");

    }

    public boolean uploadFiles(UploadFile[] files){
        boolean operateResult = false;
        return operateResult;
    }
}
