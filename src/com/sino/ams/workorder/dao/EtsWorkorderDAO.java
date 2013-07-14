package com.sino.ams.workorder.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWoSchemeDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.model.EtsWorkorderModel;
import com.sino.ams.workorder.model.OrderExtendModel;
import com.sino.ams.workorder.util.WorkOrderUtil;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sybase.jdbc3.jdbc.Convert;


/**
 * <p>Title: EtsWorkorderDAO</p>
 * <p>Description:程序自动生成服务程序“EtsWorkorderDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author zhoujs
 * @version 1.0
 */


public class EtsWorkorderDAO extends BaseDAO {

    private EtsWorkorderModel etsWorkorderModel = null;
    private SfUserDTO SfUser = null;

    /**
     * 功能：工单主表(EAM) ETS_WORKORDER 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsWorkorderDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public EtsWorkorderDAO(SfUserDTO userAccount, EtsWorkorderDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        SfUser = userAccount;
        initSQLProducer(userAccount, dtoParameter);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EtsWorkorderDTO dtoPara = (EtsWorkorderDTO) dtoParameter;
        super.sqlProducer = new EtsWorkorderModel((SfUserDTO) userAccount, dtoPara);
        etsWorkorderModel = (EtsWorkorderModel) sqlProducer;
    }

    /**
     * 功能：插入工单主表(EAM)表“ETS_WORKORDER”数据。
     */
    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("工单主表(EAM)");
    }

    /**
     * 功能：更新工单主表(EAM)表“ETS_WORKORDER”数据。
     */
    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("工单主表(EAM)");
    }

    /**
     * 功能：删除工单主表(EAM)表“ETS_WORKORDER”数据。
     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("工单主表(EAM)");
    }

    /**
     * 插入数据至工单临时表
     * @param workorderObjectNos
     * @param workorderDTO
     * @param sfUser
     * @return
     * @throws DataHandleException    
     */
    public boolean createTmpData(String[] workorderObjectNos, EtsWorkorderDTO workorderDTO, SfUserDTO sfUser) throws DataHandleException, ContainerException {
        boolean operatorResult = true;
		try {
			SQLModel sqlModel = null;			
			List<SQLModel> sqlModList = new ArrayList<SQLModel>();
			OrderExtendModel orderExtend = new OrderExtendModel();
			
			if (workorderObjectNos != null && workorderObjectNos.length > 0) {
				for (int i = 0; i < workorderObjectNos.length; i++) {
					String objectNo = workorderObjectNos[i];
					//系统自动匹配归档人和执行人为分公司当前有效的资产管理员
		            sqlModel = orderExtend.getAvailableAssetsAdmin(objectNo, sfUser.getOrganizationId());
		            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		            simpleQuery.executeQuery();
		            String groupName="";
		            String userName ="";
		            String userId="";
		            String groupId="";
		            if(simpleQuery.hasResult())
		            {
		            	Row row=simpleQuery.getFirstRow();
		            	groupName = (String) row.getValue(row.getFiledIndex("GROUP_NAME"));
		                userName = (String)row.getValue(row.getFiledIndex("USERNAME"));
		            	userId=(String)row.getValue(row.getFiledIndex("USER_ID"));
		            	groupId=(String)row.getValue(row.getFiledIndex("GROUP_ID"));
		            }
					String workorderNo = WorkOrderUtil.getWorkorderNo(workorderDTO.getWorkorderBatch(), conn);
					workorderDTO.setWorkorderNo(workorderNo);
					workorderDTO.setWorkorderObjectNo(objectNo);
					workorderDTO.setWorkorderFlag(DictConstant.WOR_STATUS_NEW);
					workorderDTO.setOrganizationId(sfUser.getOrganizationId());
					workorderDTO.setCreatedBy(sfUser.getUserId());
					workorderDTO.setCheckoverBy(Convert.objectToInt(userId));
					workorderDTO.setCheckoverUser(userName);
					workorderDTO.setExecuteUserName(userName);
					workorderDTO.setImplementUser(userName);
					workorderDTO.setGroupId(Convert.objectToInt(groupId));
					workorderDTO.setGroupName(groupName);
					
					workorderDTO.setImplementBy(Convert.objectToInt(userId));
					sqlModel = orderExtend.getInsertWorkorderDataModel(workorderDTO);
					sqlModList.add(sqlModel);
					
				}
				operatorResult = DBOperator.updateBatchRecords(sqlModList, conn);
			}
		} catch (SQLModelException | QueryException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return operatorResult;
    }


    /**
     * Function    查询未归档的交接工单中已经存在指定项目下的地点
     * @param workorderObjectNos
     * @param workorderDTO
     * @return RowSet
     * @throws QueryException
     */
    public RowSet existHandoverWorkorder(String[] workorderObjectNos, EtsWorkorderDTO workorderDTO) throws QueryException {
        SQLModel sqlModel = null;
        OrderExtendModel orderExtend = new OrderExtendModel();
        if (workorderObjectNos != null && workorderObjectNos.length > 0) {
            sqlModel = orderExtend.getExistHandoverWorkorderModel(workorderObjectNos, workorderDTO);
        }
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        return simpleQuery.getSearchResult();
    }

    /**
     * 更新工单数据及配置信息 (临时表)
     * @param etsWorkorderDTO
     * @param sfUser
     * @param itemCodes
     * @param itemQtys
     * @return boolean
     */
    public boolean updateTmpData(EtsWorkorderDTO etsWorkorderDTO, SfUserDTO sfUser, String[] itemCodes, String[] itemQtys) throws DataHandleException {
        boolean operatorResult = true;
		try {
			SQLModel sqlModel = new SQLModel();
			List sqlModList = new ArrayList();
			OrderExtendModel orderExtend = new OrderExtendModel();
			sqlModel = orderExtend.getUpdateImplmentModel(etsWorkorderDTO);
			sqlModList.add(sqlModel);
			sqlModel = orderExtend.getDeleteSchemeModel(true, etsWorkorderDTO.getWorkorderNo());
			sqlModList.add(sqlModel);
			EtsWoSchemeDTO etsWoScheme = new EtsWoSchemeDTO();
			etsWoScheme.setSystemid(StrUtil.strToInt(etsWorkorderDTO.getSystemid()));
			etsWoScheme.setWorkorderNo(etsWorkorderDTO.getWorkorderNo());

			if (itemCodes != null && itemCodes.length > 0) {
				for (int i = 0; i < itemCodes.length; i++) {
					String itemCode = itemCodes[i];
                    if (StrUtil.isNotEmpty(itemCode)) {
                        String itemQty = itemQtys[i];
                        etsWoScheme.setItemCode(itemCode);
                        etsWoScheme.setItemQty(StrUtil.strToInt(itemQty));
                        sqlModel = orderExtend.getInsertSchemeModel(true, etsWoScheme);
                        sqlModList.add(sqlModel);
                    }
                }
			}
			operatorResult = DBOperator.updateBatchRecords(sqlModList, conn);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		}
        return operatorResult;
    }


}
