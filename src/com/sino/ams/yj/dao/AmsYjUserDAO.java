package com.sino.ams.yj.dao;


import java.sql.Connection;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.WorldConstant;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yj.dto.AmsYjUserDTO;
import com.sino.ams.yj.model.AmsYjUserModel;


/**
 * <p>Title: AmsYjUserDAO</p>
 * <p>Description:程序自动生成服务程序“AmsYjUserDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-应急保障人员维护
 */


public class AmsYjUserDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：应急通信保障队伍表 AMS_YJ_USER 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsYjUserDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsYjUserDAO(SfUserDTO userAccount, AmsYjUserDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		AmsYjUserDTO dtoPara = (AmsYjUserDTO)dtoParameter;
		super.sqlProducer = new AmsYjUserModel((SfUserDTO)userAccount, dtoPara);
	}

     /**
         * 功能：导出Excel文件。
         * @return File
         * @throws com.sino.base.exception.DataTransException
         */
        public File exportFile() throws DataTransException {
            File file = null;
            try {
                DataTransfer transfer = null;
                SQLModel sqlModel = sqlProducer.getPageQueryModel();
                TransRule rule = new TransRule();
                rule.setDataSource(sqlModel);
                rule.setCalPattern(CalendarConstant.LINE_PATTERN);
                rule.setSourceConn(conn);

                String fileName = "应急保障人员信息.xls";
                String filePath = WorldConstant.USER_HOME;
                filePath += WorldConstant.FILE_SEPARATOR;
                filePath += fileName;
                rule.setTarFile(filePath);

                DataRange range = new DataRange();
                rule.setDataRange(range);

                Map fieldMap = new HashMap();
                fieldMap.put("ORGANIZATION_NAME", "公司名称");
                fieldMap.put("TEAM_ID", "队伍号");
                fieldMap.put("TEAM_NAME", "队伍名称");
                fieldMap.put("RESPONSIBILITY_USER", "企业负责人");
                fieldMap.put("TEL1", "企业负责人手机");
                fieldMap.put("SITUATION", "队伍基本情况及特点");
                fieldMap.put("USER_NAME", "姓名");
                fieldMap.put("TEL", "手机");
                fieldMap.put("POST", "职务");
                fieldMap.put("CATEGORY", "专业");
                fieldMap.put("ATTRIBUTE", "属性");
                fieldMap.put("REMARK", "备注");
                fieldMap.put("CREATE_USER", "创建人");
                fieldMap.put("CREATION_DATE", "创建日期");
                fieldMap.put("LAST_UPDATE_USER", "更新人");
                fieldMap.put("LAST_UPDATE_DATE", "更新日期");

                rule.setFieldMap(fieldMap);

                CustomTransData custData = new CustomTransData();
                custData.setReportTitle(fileName);
                custData.setReportPerson(sfUser.getUsername());
                custData.setNeedReportDate(true);
                rule.setCustData(custData);
                /*rule.setSheetSize(1000);*/
                //设置分页显示
                TransferFactory factory = new TransferFactory();
                transfer = factory.getTransfer(rule);
                transfer.transData();
                file = (File) transfer.getTransResult();
            } catch (SQLModelException ex) {
                ex.printLog();
                throw new DataTransException(ex);
            }
            return file;
        }

}