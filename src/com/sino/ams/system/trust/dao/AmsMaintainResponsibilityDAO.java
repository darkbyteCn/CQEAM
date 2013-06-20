package com.sino.ams.system.trust.dao;


import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.trust.dto.AmsMaintainResponsibilityDTO;
import com.sino.ams.system.trust.model.AmsMaintainResponsibilityModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.DatabaseForWeb;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: AmsMaintainResponsibilityDAO</p>
 * <p>Description:程序自动生成服务程序“AmsMaintainResponsibilityDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsMaintainResponsibilityDAO extends AMSBaseDAO {

    /**
     * 功能：待维责任 AMS_MAINTAIN_RESPONSIBILITY 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsMaintainResponsibilityDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AmsMaintainResponsibilityDAO(SfUserDTO userAccount, AmsMaintainResponsibilityDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsMaintainResponsibilityDTO dtoPara = (AmsMaintainResponsibilityDTO) dtoParameter;
        super.sqlProducer = new AmsMaintainResponsibilityModel((SfUserDTO) userAccount, dtoPara);
    }

	/**
	 * 功能：插入多条记录
	 * @param dtos DTOSet
	 * @return boolean
	 * @throws DataHandleException
	 */
	public boolean saveResponsibility(DTOSet dtos) throws DataHandleException {
        boolean operateResult = false;
        if (dtos != null && dtos.getSize() > 0) {
            operateResult = true;
            int dtoCount = dtos.getSize();
            for (int i = 0; i < dtoCount; i++) {
                AmsMaintainResponsibilityDTO dto = (AmsMaintainResponsibilityDTO) dtos.getDTO(i);
                setDTOParameter(dto);
                deleteData();
                SeqProducer seq = new SeqProducer(conn);
                dto.setSystemId(seq.getGUID());
                createData();
            }
        }
        return operateResult;
    }

	/**
	 * 功能：删除多条记录
	 * @param dtos DTOSet
	 * @return boolean
	 * @throws DataHandleException
	 */
	public boolean deleteResponsibility(DTOSet dtos) throws DataHandleException {
        boolean operateResult = false;
        if (dtos != null && dtos.getSize() > 0) {
            operateResult = true;
            int dtoCount = dtos.getSize();
            for (int i = 0; i < dtoCount; i++) {
                AmsMaintainResponsibilityDTO dto = (AmsMaintainResponsibilityDTO) dtos.getDTO(i);
                setDTOParameter(dto);
                deleteData();
            }
        }
        return operateResult;
    }


	/**
	 * 功能：构造代维公司还未确认的责任地点多选下拉框
	 * @return String
	 * @throws QueryException
	 */
	public String getToConfirmLocOpt() throws QueryException {
		AmsMaintainResponsibilityModel modelProducer = (AmsMaintainResponsibilityModel)sqlProducer;
		SQLModel sqlModel = modelProducer.getToConfirmLocModel();
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml();
	}

	/**
	 * 功能：插入多条记录
	 * @param workorderObjectNos String[]
	 * @throws DataHandleException
	 */
	public void saveMainResp(String[] workorderObjectNos) throws DataHandleException {
		boolean hasError = true;
		boolean autoCommit = true;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			AmsMaintainResponsibilityModel modelProducer = (AmsMaintainResponsibilityModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getLDeleteByCompanyIdModel();
			DBOperator.updateRecord(sqlModel, conn);
			if (workorderObjectNos != null) {
				AmsMaintainResponsibilityDTO dto;
				for (int i = 0; i < workorderObjectNos.length; i++) {
					dto = new AmsMaintainResponsibilityDTO();
					dto.setObjectNo(StrUtil.strToInt(workorderObjectNos[i]));
					dto.setCompanyId(userAccount.getMaintainCompany());
					setDTOParameter(dto);
					createData();
				}
			}
			hasError = false;
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (hasError) {
					conn.rollback();
					prodMessage("MAINTAIN_SUCCESS");
				} else {
					conn.commit();
					conn.setAutoCommit(autoCommit);
					prodMessage("MAINTAIN_SUCCESS");
				}
			} catch (SQLException ex1) {
				Logger.logError(ex1);
				throw new DataHandleException();
			}
		}
     }
        /**
     * 功能：导出Excel文件。
     * @return File
     * @throws com.sino.base.exception.DataTransException
     */
    public File exportFile() throws DataTransException {
        File file = null;
        try {
            AmsMaintainResponsibilityDTO itemDTO = (AmsMaintainResponsibilityDTO) dtoParameter;
            SQLModel sqlModel = sqlProducer.getPageQueryModel();
            TransRule rule = new TransRule();
            rule.setDataSource(sqlModel);
            rule.setSourceConn(conn);

            String fileName =  "代维公司责任统计表.xls";
            String filePath = WorldConstant.USER_HOME;
            filePath += WorldConstant.FILE_SEPARATOR;
            filePath += fileName;
            rule.setTarFile(filePath);

            DataRange range = new DataRange();
            rule.setDataRange(range);

            Map fieldMap = new HashMap();
            fieldMap.put("OBJECT_CODE", "地点编号");
            fieldMap.put("OBJECT_NAME", "地点简称");
            fieldMap.put("COUNTY_NAME", "所属区县");
            fieldMap.put("COMPANY_NAME", "代维公司");
            rule.setFieldMap(fieldMap);

            CustomTransData custData = new CustomTransData();
            custData.setReportTitle(fileName);
            custData.setReportPerson(userAccount.getUsername());
            custData.setNeedReportDate(true);
            rule.setCustData(custData);
            /*rule.setSheetSize(1000);*/
            //设置分页显示
            TransferFactory factory = new TransferFactory();
            DataTransfer transfer = factory.getTransfer(rule);
            transfer.transData();
            file = (File) transfer.getTransResult();
        } catch (SQLModelException ex) {
            ex.printLog();
            throw new DataTransException(ex);
        }
        return file;
    }
}
