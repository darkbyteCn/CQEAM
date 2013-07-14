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
 * <p>Description:�����Զ����ɷ������EtsWorkorderDAO�����������Ҫ�����޸�</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ����˼ŵ����Ϣ�������޹�˾</p>
 * @author zhoujs
 * @version 1.0
 */


public class EtsWorkorderDAO extends BaseDAO {

    private EtsWorkorderModel etsWorkorderModel = null;
    private SfUserDTO SfUser = null;

    /**
     * ���ܣ���������(EAM) ETS_WORKORDER ���ݷ��ʲ㹹�캯��
     * @param userAccount  SfUserDTO ����ϵͳ�����ղ����û�����
     * @param dtoParameter EtsWorkorderDTO ���β���������
     * @param conn         Connection ���ݿ����ӣ��ɵ����ߴ��롣
     */
    public EtsWorkorderDAO(SfUserDTO userAccount, EtsWorkorderDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        SfUser = userAccount;
        initSQLProducer(userAccount, dtoParameter);
    }

    /**
     * ���ܣ�SQL������BaseSQLProducer�ĳ�ʼ����
     * @param userAccount  BaseUserDTO ��ϵͳ���ղ����û���
     * @param dtoParameter DTO ���β���������
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EtsWorkorderDTO dtoPara = (EtsWorkorderDTO) dtoParameter;
        super.sqlProducer = new EtsWorkorderModel((SfUserDTO) userAccount, dtoPara);
        etsWorkorderModel = (EtsWorkorderModel) sqlProducer;
    }

    /**
     * ���ܣ����빤������(EAM)��ETS_WORKORDER�����ݡ�
     */
    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("��������(EAM)");
    }

    /**
     * ���ܣ����¹�������(EAM)��ETS_WORKORDER�����ݡ�
     */
    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("��������(EAM)");
    }

    /**
     * ���ܣ�ɾ����������(EAM)��ETS_WORKORDER�����ݡ�
     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("��������(EAM)");
    }

    /**
     * ����������������ʱ��
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
					//ϵͳ�Զ�ƥ��鵵�˺�ִ����Ϊ�ֹ�˾��ǰ��Ч���ʲ�����Ա
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
     * Function    ��ѯδ�鵵�Ľ��ӹ������Ѿ�����ָ����Ŀ�µĵص�
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
     * ���¹������ݼ�������Ϣ (��ʱ��)
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
