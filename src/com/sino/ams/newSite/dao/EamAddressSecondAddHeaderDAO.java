package com.sino.ams.newSite.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newSite.dto.EamAddressAddHDTO;
import com.sino.ams.newSite.dto.EamAddressAddLDTO;
import com.sino.ams.newSite.model.EamAddressSecondAddHModel;
import com.sino.ams.newSite.model.EamAddressSecondAddImportModel;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.user.dao.EtsOuCityMapDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.Row;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * @author 作者 :wangzhipeng
 * @version 创建时间：Apr 12, 2011 2:11:46 PM
 *          类说明:新增地点工作流DAO
 */
public class EamAddressSecondAddHeaderDAO extends AMSProcedureBaseDAO {

    public EamAddressSecondAddHeaderDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EamAddressAddHDTO dtoPara = (EamAddressAddHDTO) dtoParameter;
        sqlProducer = new EamAddressSecondAddHModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：保存地点单据
     * 头信息，行信息 提交到流程
     */
    public boolean saveOrder(DTOSet lineSet) throws SQLModelException, ContainerException, QueryException {
        boolean result = false;
        boolean autoCommit = true;
        EamAddressAddHDTO dtoPara = (EamAddressAddHDTO) dtoParameter;
        boolean isNewData = dtoPara.getTransNo().equals(AssetsWebAttributes.ORDER_AUTO_PROD);
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String excel = dtoPara.getExcel();
            saveOrderHeader();
            deleteOrderLines();
            if (StrUtil.isEmpty(excel)) {
                saveOrderLines(lineSet);       //保存行信息
            } else {            //验证，导入
            	EamAddressSecondAddImportDAO ImObDAO = new EamAddressSecondAddImportDAO(userAccount, null, conn);
            	ImObDAO.importObject(lineSet, dtoPara.getTransId());              //导入到EAM_ADDRESS_ADD_L     需要导入transId字段
            }
            result = processProcedure();
        } catch (DataHandleException ex) {
            result = false;
            Logger.logError(ex);
        } catch (Throwable ex) {
            result = false;
            Logger.logError(ex);
        } finally {
            try {
                if (!result) {
                    conn.rollback();
                    prodMessage("OBJECT_ORDER_PROCESS_FAILURE");
                    if (isNewData) {
                        rollbackData();
                    }
                } else {
                    conn.commit();
                    prodMessage("OBJECT_ORDER_PROCESS_SUCCESS");
                }
                conn.setAutoCommit(autoCommit);
                message.addParameterValue("保存");
                message.setIsError(!result);
            } catch (SQLException ex) {
                result = false;
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return result;
    }

    public boolean importOrder(DTOSet lineSet) throws SQLModelException, ContainerException, QueryException {
        boolean result = false;
        boolean autoCommit = true;
        EamAddressAddHDTO dtoPara = (EamAddressAddHDTO) dtoParameter;
        boolean isNewData = dtoPara.getTransNo().equals(AssetsWebAttributes.ORDER_AUTO_PROD);
        boolean importResult = true;
        String excel = dtoPara.getExcel();
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            saveOrderHeader();
            deleteOrderLines();
            if (StrUtil.isEmpty(excel)) {
                saveOrderLines(lineSet);       //保存行信息
            } else {            //验证，导入
                EamAddressSecondAddImportDAO ImObDAO = new EamAddressSecondAddImportDAO(userAccount, null, conn);
                importResult = ImObDAO.importObject(lineSet, dtoPara.getTransId());
                result=true;
            }
            result = processProcedure();                   //流程开始流转
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            try {
                boolean needMessage = true;
                if (!StrUtil.isEmpty(excel)) {
                    message = new Message();
                    if (importResult) {
                        message.setMessageValue("地点数据导入成功");
                    } else {
                        message.setMessageValue("地点数据导入失败，请查看错误提示。");
                        message.setIsError(true);
                    }
                    needMessage = false;
                }
                if (!result) {
                    conn.rollback();
                    if (isNewData) {
                        rollbackData();
                    }
                } else {
                    conn.commit();
                }
                if(needMessage){
                    if (!result) {
                        prodMessage("OBJECT_ORDER_PROCESS_FAILURE");
                    } else {
                        prodMessage("OBJECT_ORDER_PROCESS_SUCCESS");
                    }
                    message.addParameterValue("保存");
                    message.setIsError(!result);
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                result = false;
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return result;
    }

    /**
     * 流程表单提交（提交到ets_object(地点表)）
     * 提交的两种情况：1.开始发起流程 保存表单提交信息  2.审核表单，直接流转
     * 两种情况：1.完整表单直接提交 2.需先保存表单信息，再进行提交。
     * 条件：完整表单, AppFlowBaseDTO 流程对象
     *
     * @throws ContainerException
     * @throws QueryException
     */
    public boolean submitOrder(DTOSet lineSet) throws SQLModelException, QueryException, ContainerException {
        boolean result = false;
        boolean autoCommit = true;
        EamAddressAddHDTO dtoPara = (EamAddressAddHDTO) dtoParameter;
        boolean isNewData = dtoPara.getTransNo().equals(AssetsWebAttributes.ORDER_AUTO_PROD);
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            if (dtoPara.getSf_task_attribute1().equals("form1")) {     //流程第一个结点
            	
            	dtoPara.setTransStatus(DictConstant.IN_PROCESS);
            	dtoParameter = dtoPara;
            	
                this.saveOrderHeader();            //保存单据头
                this.deleteOrderLines();            //删除旧信息
                this.saveOrderLines(lineSet);                //增加新信息
            } else {
                if (dtoPara.isFlowOver()) {
                    dtoPara.setTransStatus(DictConstant.APPROVED);//将数据更新到正式表
                    saveEtsObject(lineSet);
                } else {
                    dtoPara.setTransStatus(DictConstant.IN_PROCESS);
                }
                updateTransStatus();      //修改流程状态
            }
            result = processProcedure();
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!result) {
                    conn.rollback();
                    prodMessage("OBJECT_ORDER_PROCESS_FAILURE");
                    if (isNewData) {
                        rollbackData();
                    }
                } else {
                    conn.commit();
                    prodMessage("OBJECT_ORDER_PROCESS_SUCCESS");
                }
                conn.setAutoCommit(autoCommit);
                message.addParameterValue("提交");
                message.setIsError(!result);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return result;
    }


    /**
     * 功能：保存单据头信息
     *
     * @throws SQLException
     * @throws DataHandleException
     */
    private void saveOrderHeader() throws DataHandleException {
        try {
            EamAddressAddHDTO dtoPara = (EamAddressAddHDTO) dtoParameter;
            if (dtoPara.getTransNo().equals(AssetsWebAttributes.ORDER_AUTO_PROD)) {        // 判断是否存在头信息，无增加
                OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), "ASS-LOC");
                String orderNo = ong.getOrderNum();
                dtoPara.setTransNo(orderNo);
                if (dtoPara.getTransId().length() == 0) {
                    SeqProducer seq = new SeqProducer(conn);
                    dtoPara.setTransId(seq.getGUID());
                }
                createData();
            } else {
                updateData();
            }
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
    }

    /**
     * 功能：删除单据行信息
     * 条件：transId
     */
    private void deleteOrderLines() throws DataHandleException {
        EamAddressAddHDTO dto = (EamAddressAddHDTO) dtoParameter;
        EamAddressSecondAddHModel modelProducer = (EamAddressSecondAddHModel) sqlProducer;
        modelProducer.setDTOParameter(dto);
        SQLModel sqlModel = modelProducer.deleteImportModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：保存单据行信息
     *
     * @throws DataHandleException
     */
    private void saveOrderLines(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            EamAddressAddHDTO dtoPara = (EamAddressAddHDTO) dtoParameter;
            String transId = dtoPara.getTransId();
            EamAddressAddLDAO lineDAO = new EamAddressAddLDAO(userAccount, null, conn);
            for (int i = 0; i < lineSet.getSize(); i++) {
                EamAddressAddLDTO lineData = (EamAddressAddLDTO) lineSet.getDTO(i);
                lineData.setTransId(transId);
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
            }
        }
    }

    /**
     * 功能：处理流程信息
     *
     * @return
     */
    protected void prepareProcedureData() {
        EamAddressAddHDTO dtoPara = (EamAddressAddHDTO) dtoParameter;
        dtoPara.setPrimaryKey(dtoPara.getTransId());
        dtoPara.setOrderNo(dtoPara.getTransNo());
        dtoPara.setOrderName(dtoPara.getTransType());
    }

    public void delImportEtsObject(String transId) throws DataHandleException, SQLModelException {
        EamAddressSecondAddHModel model = (EamAddressSecondAddHModel) sqlProducer;
        SQLModel sqlModel = model.delImportModel(transId);
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 修改表单的状态为启用状态:0   条件：地点代码
     *
     * @throws DataHandleException
     * @throws ContainerException
     * @throws QueryException
     * @throws SQLModelException
     */
    private void saveEtsObject(DTOSet lineSet) throws DataHandleException, SQLModelException, QueryException, ContainerException {
        if (lineSet != null && !lineSet.isEmpty()) {
            EamAddressSecondAddHModel model = (EamAddressSecondAddHModel) sqlProducer;
            SQLModel sqlModel = null;
            for (int i = 0; i < lineSet.getSize(); i++) {
                EamAddressAddLDTO lineData = (EamAddressAddLDTO) lineSet.getDTO(i);
                if (lineData.getAddrMaintainType().equals("新增") || lineData.getAddrMaintainType().equals("ADD")) { //新增
                    EtsObjectDTO etsDto = fillDTO(lineData);
                    etsDto.setWorkorderObjectNo(getNextGUID());
                    
                    etsDto.setWorkorderObjectCode(getNextObjectCode2(etsDto.getOrganizationId(),etsDto.getObjectCategory()));
                    
                    EtsOuCityMapDAO etsOuCityMapDAO = new EtsOuCityMapDAO(userAccount, null, conn);
                    etsDto.setCompanyCode(etsOuCityMapDAO.getCompanyCodeByOrgId(etsDto.getOrganizationId()));
    				
                    sqlModel = model.createDoEtsObjectLoc(etsDto);
                    DBOperator.updateRecord(sqlModel, conn);
                    
                    sqlModel = model.updateWorkorderObjectCode(etsDto.getWorkorderObjectCode(), lineData.getLineId());
                    DBOperator.updateRecord(sqlModel, conn);
                } else if (lineData.getAddrMaintainType().equals("修改") || lineData.getAddrMaintainType().equals("UPDATE")) { //修改
                    EtsObjectDTO etsDto = fillDTO(lineData);
                    sqlModel = model.updateEtsObjectLocInfo(etsDto);
                    DBOperator.updateRecord(sqlModel, conn);
                    
                    //修改ETS_OBJECT
                    sqlModel = model.updateEtsObjectInfo(etsDto);
                    DBOperator.updateRecord(sqlModel, conn);
                } else { //失效
                	sqlModel = model.disabledEtsLocObject(lineData.getWorkorderObjectCode());
                    DBOperator.updateRecord(sqlModel, conn);
                    
                    sqlModel = model.disabledEtsObject(lineData.getWorkorderObjectCode());
                    DBOperator.updateRecord(sqlModel, conn);
                }
            }
        }
    }

    /**
     * 修改流程状态
     * 流程状态:transStatus
     */
    public void updateTransStatus() throws DataHandleException {
    	EamAddressSecondAddHModel modelProducer = (EamAddressSecondAddHModel) sqlProducer;
        SQLModel sqlModel = modelProducer.updateTransStatus();
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 获取表单行信息
     */
    public DTOSet getLineData() throws QueryException {
        EamAddressAddHDTO dto = (EamAddressAddHDTO) dtoParameter;
        EamAddressSecondAddHModel model = (EamAddressSecondAddHModel) sqlProducer;
        SQLModel sqlModel = model.getLineDataModel(dto);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(EamAddressAddLDTO.class.getName());
        simp.executeQuery();
        return simp.getDTOSet();
    }

    /**
     * 表单行信息转换为 EtsObjectDTO
     *
     * @param lineData
     * @return
     * @throws ContainerException
     * @throws QueryException
     * @throws SQLModelException
     */
    public EtsObjectDTO fillDTO(EamAddressAddLDTO lineData) throws SQLModelException, QueryException, ContainerException {
        EtsObjectDTO dto = new EtsObjectDTO();
        dto.setWorkorderObjectCode(lineData.getWorkorderObjectCode());     //地点代码
        dto.setWorkorderObjectName(lineData.getWorkorderObjectName());     //地点名称
        dto.setWorkorderObjectLocation(lineData.getWorkorderObjectName()); //所在地点
        dto.setOrganizationId(userAccount.getOrganizationId());            //组织ID()
        dto.setBtsNo(lineData.getBtsNo());
        dto.setCountyCode(lineData.getCountyCode());                       //区县代码 
        dto.setObjectCategory(getObjCatgoryCode(lineData.getObjectCategory()));              //地点类别
        dto.setIsTempAddr(0);                                           //1临时地点,0正式地点
        dto.setCreatedBy(userAccount.getUserId());           //创建人
        if (lineData.getAreaType().length() == 1) {
            lineData.setAreaType("0" + lineData.getAreaType());
        }
        dto.setAreaType(lineData.getAreaType());          //区域类型
        dto.setDeptCode(userAccount.getDeptCode());              //部门
        dto.setCity(getCountyCode(lineData.getCity()));                  //市
        dto.setCounty(getCountyCode(lineData.getCounty()));              //区县
        dto.setShareType(lineData.getShareType());                  //是否共享
        if (lineData.getObjectCategory().equals("JZ") || lineData.getObjectCategory().equals("YY")) { //基站和营业厅才设置编码
            dto.setBtsNo(lineData.getBtsNo());
        }
        return dto;
    }

    public String getCountyCode(String strCode) throws SQLModelException, QueryException, ContainerException {
        EamAddressSecondAddHModel model = (EamAddressSecondAddHModel) sqlProducer;
        SQLModel sqlModel = model.getCountyCode(strCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        String str = "";
        if (simpleQuery.hasResult()) {
            str = simpleQuery.getSearchResult().getRow(0).getStrValue("COUNTY_CODE");
        }
        return str;
    }
    
    /**
     * 获取区域代码
     * @param str
     * @return
     * @throws SQLModelException
     * @throws QueryException
     * @throws ContainerException
     */
    public String getAreaCountyCode(String str) throws SQLModelException, QueryException, ContainerException {
        EamAddressSecondAddHModel model = (EamAddressSecondAddHModel) sqlProducer;
        SQLModel sqlModel = model.getAreaCountyCode(str);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        String code = "";
        if (simpleQuery.hasResult()) {
            code = simpleQuery.getSearchResult().getRow(0).getStrValue("COUNTY_CODE");
        }
        return code;
    }

    /**
     * 功能：获取地点专业代码
     *
     * @throws SQLModelException
     * @throws ContainerException
     */
    public String getObjCatgoryCode(String category) throws SQLModelException, QueryException, ContainerException {
    	EamAddressSecondAddHModel eoModel = (EamAddressSecondAddHModel) sqlProducer;
        SQLModel sqlModel = eoModel.OCModel(category);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        String str = "";
        if (simpleQuery.hasResult()) {
            str = simpleQuery.getSearchResult().getRow(0).getStrValue("CODE");
        }
        return str;
    }

    /**
     * 获取Excel信息 eam_address_L 表中数据
     */
    public DTOSet getImpDS(String transId) throws SQLModelException, QueryException {
        DTOSet ds = new DTOSet();
        EamAddressSecondAddHModel model = (EamAddressSecondAddHModel) sqlProducer;
        SQLModel sqlModel = model.getAllQueryImportModel(transId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.setDTOClassName(EamAddressAddLDTO.class.getName());
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            ds = simpleQuery.getDTOSet();
        }
        return ds;
    }

    private void rollbackData() {
        EamAddressAddHDTO dtoPara = (EamAddressAddHDTO) dtoParameter;
        dtoPara.setTransId("");
        dtoPara.setTransNo(AssetsWebAttributes.ORDER_AUTO_PROD);
    }


    public void rejectOrder() {
        boolean operateResult = rejectProcedure();
        if (operateResult) {
            prodMessage(AssetsMessageKeys.REJECT_ORDER_SUCCESS);
        } else {
            prodMessage(AssetsMessageKeys.REJECT_ORDER_FAILURE);
            message.setIsError(true);
        }
        EamAddressAddHDTO dto = (EamAddressAddHDTO) dtoParameter;
        message.addParameterValue(dto.getTransType());
        message.addParameterValue(dto.getTransNo());
    }

    /**
     * 功能：撤销暂存的单据
     *
     * @return boolean
     */
    public boolean cancelOrder() {
        boolean operateResult = false;
        boolean autoCommit = true;
        EamAddressAddHDTO headerDTO = (EamAddressAddHDTO) dtoParameter;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            headerDTO.setTransStatus(DictConstant.CANCELED);
            EamAddressSecondAddHModel modelProducer = (EamAddressSecondAddHModel) sqlProducer;
            SQLModel sqlModel = modelProducer.updateTransStatus();
            DBOperator.updateRecord(sqlModel, conn);
            operateResult = cancelProcedure();
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                    prodMessage(AssetsMessageKeys.ORDER_CANCEL_SUCCESS);
                } else {
                    conn.rollback();
                    prodMessage(AssetsMessageKeys.ORDER_CANCEL_FAILURE);
                }
                conn.setAutoCommit(autoCommit);
                message.addParameterValue(headerDTO.getTransType());
                message.setIsError(!operateResult);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }
    
    /**
     * 生成地点第二段代码
     * @return
     * @throws QueryException
     */
    public String getNextObjectCode2(int organizationId,String objectCategory) throws QueryException {
        String maxObjectCode = "";
        try {
        	EamAddressSecondAddHModel modelProducer = (EamAddressSecondAddHModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getNextObjectCode2Model(organizationId,objectCategory);
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                Row row = simpleQuery.getFirstRow();
                maxObjectCode = row.getStrValue("OBJECT_CODE");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return maxObjectCode;
    }
    
    /**
     * 功能：校验导入地点第二段描述是否存在
     *
     * @return true表示存在，false表示不存在
     * @throws QueryException 检查地点下是否有资产出错时抛出该异常
     */
    public boolean hasLocName(EamAddressAddLDTO lineDto) throws QueryException {
    	EamAddressSecondAddImportModel modelProducer = new EamAddressSecondAddImportModel(userAccount, lineDto);
        SQLModel sqlModel = modelProducer.getIsExistWorkorderObjectNameModel();
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.setSql(sqlModel);
        //simpleQuery.setDTOClassName(EamAddressAddLDTO.class.getName());
        simpleQuery.executeQuery();
        return simpleQuery.hasResult();
    }

    /**
     * 功能：校验导入地点第二段描述是否存在单据中，单据状态为暂存、处理中
     *
     * @return true表示存在，false表示不存在
     * @throws QueryException 检查地点下是否有资产出错时抛出该异常
     */
    public boolean hasLocNameInOrder(EamAddressAddLDTO lineDto) throws QueryException {
    	EamAddressSecondAddImportModel modelProducer = new EamAddressSecondAddImportModel(userAccount, lineDto);
        SQLModel sqlModel = modelProducer.getIsObjectNameInOrderModel();
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.setSql(sqlModel);
        simpleQuery.executeQuery();
        return simpleQuery.hasResult();
    }

    /**
     * 校验基站编号是否存在
     * @return
     */
	public boolean existBtsNo(String btsNo) {
		boolean exist = false;
		EamAddressAddLDTO lineDto = new EamAddressAddLDTO();
		try {
			EamAddressSecondAddImportModel modelProducer = new EamAddressSecondAddImportModel(userAccount, lineDto);
			SQLModel sqlModel = modelProducer.getBtsNoEsistModel(btsNo);
			SimpleQuery simQuery = new SimpleQuery(sqlModel, conn);
			simQuery.executeQuery();
			exist = simQuery.hasResult();
		} catch (QueryException ex) {
			ex.printLog();
		}
		return exist;
	}

}
