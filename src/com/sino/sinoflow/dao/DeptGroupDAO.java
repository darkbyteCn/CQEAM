package com.sino.sinoflow.dao;


import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.datatrans.*;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.base.constant.WorldConstant;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.dto.DeptGroupDTO;
import com.sino.sinoflow.dto.DeptGroupLineDTO;
import com.sino.sinoflow.dto.SfGroupDTO;
import com.sino.sinoflow.model.DeptGroupModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;


/**
 * <p>Title: SfGroupDAO</p>
 * <p>Description:程序自动生成服务程序“SfGroupDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class DeptGroupDAO extends BaseDAO {

//	private SfUserBaseDTO sfUser = null;
    private int pageSize = 20;
    private boolean countPages = true;

	/**
	 * 功能：组别属性 SF_GROUP 数据访问层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfGroupDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public DeptGroupDAO(SfUserBaseDTO userAccount, DeptGroupDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
//		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		DeptGroupDTO dtoPara = (DeptGroupDTO)dtoParameter;
		super.sqlProducer = new DeptGroupModel((SfUserBaseDTO)userAccount, dtoPara);
	}

    public DeptGroupDTO getDeptGroupData() throws QueryException {
        DeptGroupDTO dtoPara = (DeptGroupDTO)dtoParameter;
        SQLModel sqlModel = (new DeptGroupModel((SfUserBaseDTO)userAccount, dtoPara)).getDeptGroupDataModel();
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.setDTOClassName(DeptGroupDTO.class.getName());
        simpleQuery.executeQuery();
        return (DeptGroupDTO)simpleQuery.getFirstDTO();
    }

    public void produceMatchData(HttpServletRequest req) throws QueryException {
        SQLModel sqlModel = ((DeptGroupModel)sqlProducer).getDeptGroupMatchModel();
        WebPageView pageView = new WebPageView(req, conn);
        if (!StrUtil.isEmpty(dtoClassName)) {
            pageView.setDTOClassName(dtoClassName);
        }
        if (pageSize > 0) {
            pageView.setPageSize(pageSize);
        }
        pageView.setCountPages(countPages);
        pageView.setWebCheckProp(getWebCheckProp());
        pageView.setCalPattern(getCalPattern());
        pageView.produceWebData(sqlModel);
    }

    public void createGroup(DTOSet deptLines) throws DataHandleException {
        if(deptLines == null)
            return;
        for(int i = 0; i < deptLines.getSize(); i++) {
            DeptGroupLineDTO line = (DeptGroupLineDTO)deptLines.getDTO(i);
            if(line.getIsCheck().equals("0")) {
                continue;
            }
            SQLModel sqlModel = new DeptGroupModel((SfUserBaseDTO)userAccount, (DeptGroupDTO)dtoParameter).updateDept(line);
            DBOperator.updateRecord(sqlModel, conn);
            SfGroupDTO groupDto = new SfGroupDTO();
            groupDto.setGroupName(line.getGroupName());
            groupDto.setGroupDesc(line.getDeptName());
            groupDto.setEnabled("Y");
            groupDto.setProjectName(line.getProjectName());
            SfGroupDAO groupDAO = new SfGroupDAO((SfUserBaseDTO)userAccount, groupDto, conn);
            int groupId = Integer.parseInt(groupDAO.createData2());
            sqlModel = new DeptGroupModel((SfUserBaseDTO)userAccount, (DeptGroupDTO)dtoParameter).insertGroupMatch(line.getDeptId(), groupId);
            DBOperator.updateRecord(sqlModel, conn);
        }
    }

    public void updateGroup(DTOSet deptLines) throws DataHandleException {
        if(deptLines == null)
            return;
        for(int i = 0; i < deptLines.getSize(); i++) {
            DeptGroupLineDTO line = (DeptGroupLineDTO)deptLines.getDTO(i);
            if(line.getIsCheck().equals("0")) {
                continue;
            }
            SQLModel sqlModel = new DeptGroupModel((SfUserBaseDTO)userAccount, (DeptGroupDTO)dtoParameter).updateDept(line);
            DBOperator.updateRecord(sqlModel, conn);
            // curGroupName 为旧组别名称, groupName 为新组别名称, 不相等则为组别名称被更改了
            if(!line.getCurGroupName().equals(line.getGroupName())) {
                sqlModel = new DeptGroupModel((SfUserBaseDTO)userAccount, (DeptGroupDTO)dtoParameter).updateGroupName(line);
                DBOperator.updateRecord(sqlModel, conn);
                sqlModel = new DeptGroupModel((SfUserBaseDTO)userAccount, (DeptGroupDTO)dtoParameter).updateAuthority(line);
                DBOperator.updateRecord(sqlModel, conn);
                sqlModel = new DeptGroupModel((SfUserBaseDTO)userAccount, (DeptGroupDTO)dtoParameter).updateActInfo(line);
                DBOperator.updateRecord(sqlModel, conn);
                sqlModel = new DeptGroupModel((SfUserBaseDTO)userAccount, (DeptGroupDTO)dtoParameter).updateActLog(line);
                DBOperator.updateRecord(sqlModel, conn);
            }
        }
    }

    public File exportFile() throws DataTransException, SQLModelException {           //导出
        File file = null;
        SQLModel sqlModel = sqlProducer.getPageQueryModel();
        TransRule rule = new TransRule();
        rule.setDataSource(sqlModel);
        rule.setSourceConn(conn);
        String fileName = "部门组别已匹配清单.xls";
        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
        rule.setTarFile(filePath);
        DataRange range = new DataRange();
        rule.setDataRange(range);

        Map fieldMap = new HashMap();
        fieldMap.put("PROJECT_NAME", "项目名称");
        fieldMap.put("DEPT_NAME", "部门名称");
        fieldMap.put("GROUP_NAME", "组别名称");
        fieldMap.put("PARENT_NAME", "上级部门");
        fieldMap.put("SECOND_DEPT", "二级部门");
        fieldMap.put("SPECIALITY_DEPT", "专业部门");

        rule.setFieldMap(fieldMap);

        CustomTransData custData = new CustomTransData();
        custData.setReportTitle("部门组别已匹配清单");
        custData.setReportPerson(((SfUserBaseDTO)userAccount).getUsername());
        custData.setNeedReportDate(true);
        rule.setCustData(custData);
        TransferFactory factory = new TransferFactory();
        DataTransfer transfer = factory.getTransfer(rule);
        transfer.transData();
        file = (File) transfer.getTransResult();
        return file;
    }

    public File exportMatchFile() throws DataTransException {           //导出
        File file = null;
        SQLModel sqlModel = ((DeptGroupModel)sqlProducer).getDeptGroupMatchModel();
        TransRule rule = new TransRule();
        rule.setDataSource(sqlModel);
        rule.setSourceConn(conn);
        String fileName = "部门组别未匹配清单.xls";
        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
        rule.setTarFile(filePath);
        DataRange range = new DataRange();
        rule.setDataRange(range);

        Map fieldMap = new HashMap();
        fieldMap.put("PROJECT_NAME", "项目名称");
        fieldMap.put("DEPT_NAME", "部门名称");
        fieldMap.put("GROUP_NAME", "组别名称");
        fieldMap.put("PARENT_NAME", "上级部门");
        fieldMap.put("SECOND_DEPT", "二级部门");
        fieldMap.put("SPECIALITY_DEPT", "专业部门");

        rule.setFieldMap(fieldMap);

        CustomTransData custData = new CustomTransData();
        custData.setReportTitle("部门组别未匹配清单");
        custData.setReportPerson(((SfUserBaseDTO)userAccount).getUsername());
        custData.setNeedReportDate(true);
        rule.setCustData(custData);
        TransferFactory factory = new TransferFactory();
        DataTransfer transfer = factory.getTransfer(rule);
        transfer.transData();
        file = (File) transfer.getTransResult();
        return file;
    }
}