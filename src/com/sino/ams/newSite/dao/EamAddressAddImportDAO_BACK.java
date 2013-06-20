package com.sino.ams.newSite.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newSite.dto.EamAddressAddLDTO;
import com.sino.ams.newSite.model.EamAddressAddImportModel_BACK;
import com.sino.ams.system.object.dao.ImportObjectDAO;
import com.sino.ams.system.object.model.ImportObjectModel;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

import java.sql.Connection;

/**
 * @author 作者 :wangzhipeng
 * @version 创建时间：Apr 18, 2011 3:42:18 PM
 *          类说明：地点信息导入 DAO
 */
public class EamAddressAddImportDAO_BACK extends AMSBaseDAO {


    public EamAddressAddImportDAO_BACK(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);

    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        super.sqlProducer = new EamAddressAddImportModel_BACK(userAccount, dtoParameter);
    }

    /**
     * 功能：删除接口表的数据。
     * @throws com.sino.base.exception.SQLModelException
     */
    public void deleteImportModel(String transId) throws SQLModelException, QueryException, DataHandleException {
        EamAddressAddImportModel_BACK eoModel = (EamAddressAddImportModel_BACK) sqlProducer;
        SQLModel sqlModel = eoModel.deleteImportModel(transId);
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：检查接口表数据的有效性。
     * 地点代码的唯一性，地点名称的唯一性，地点专业代码的存在性，地点区县代码的存在性
     * 0://地点代码 onNetDtlDTO.setWorkorderObjectCode(strValue);
     * 1://地点名称 onNetDtlDTO.setWorkorderObjectName(strValue);
     * 2://地点专业 onNetDtlDTO.setObjectCategory(strValue);
     * 3://所属区县 onNetDtlDTO.setCountyCode(strValue);  区县代码
     * 4://区域代码 onNetDtlDTO.setAreaType(strValue);
     * 5://市 onNetDtlDTO.setCity(strValue);
     * 6://区县 onNetDtlDTO.setCounty(strValue);
     * 7://维护类型 onNetDtlDTO.setAddrMaintainType(strValue);
     */
    public void doVerifyData(DTOSet dtoSet) throws SQLModelException, QueryException {
        if (dtoSet != null && dtoSet.getSize() > 0) {
        	ImportObjectDAO objectDAO = new ImportObjectDAO(userAccount, null, conn);
            String workorderObjectCode = "";
            for (int i = 0; i < dtoSet.getSize(); i++) {
                EamAddressAddLDTO eoDTO = (EamAddressAddLDTO) dtoSet.getDTO(i);
                workorderObjectCode = eoDTO.getWorkorderObjectCode();
                String excelLineId = eoDTO.getExcelLineId();
                String msg = "第" + excelLineId + "行：";
                if (StrUtil.isEmpty(workorderObjectCode)) {
                    insertEmtyErrorData(workorderObjectCode, eoDTO.getObjectCategory(), eoDTO.getCountyCode(), msg + "地点代码不能为空");
                } else if (doubleModel(workorderObjectCode, eoDTO.getOrganizationId(), eoDTO.getTransId())) {
                    insertEmtyErrorData(workorderObjectCode, eoDTO.getObjectCategory(), eoDTO.getCountyCode(), msg + "导入地点代码存在重复");
                } else if ((workorderObjectCode.indexOf(".") == -1)) {
                    insertEmtyErrorData(workorderObjectCode, eoDTO.getObjectCategory(), eoDTO.getCountyCode(), msg + "地点代码格式不正确");
                } else if (workorderObjectCode.substring(workorderObjectCode.indexOf(".") + 1).substring(0, (workorderObjectCode.substring(workorderObjectCode.indexOf(".") + 1).indexOf("."))).length() != 14) {
                    insertEmtyErrorData(workorderObjectCode, eoDTO.getObjectCategory(), eoDTO.getCountyCode(), msg + "地点代码中间的编码必须是14位数");
                } else if (eoDTO.getAddrMaintainType().trim().equals("失效")) {
                    if (!existsObject(workorderObjectCode, userAccount.getOrganizationId())) {
                        verifyDisableData(workorderObjectCode, "地点下存在失效地点，不能失效");
                    } else if (!existsItem(workorderObjectCode, userAccount.getOrganizationId())){
                        verifyDisableData(workorderObjectCode, "地点下存在设备，不能失效");
                    }
                } else if (eoDTO.getAddrMaintainType().equals("新增") && efficientData(workorderObjectCode,userAccount.getCompanyCode())) {        //判断地点代码是否合法
	                insertEmtyErrorData(workorderObjectCode, eoDTO.getObjectCategory(), eoDTO.getCountyCode(), msg+"地点代码已存在");

//唐明胜注释，待完善
//	            } else if (!objectDAO.isObjectNameValid(eoDTO.getCountyCode(), eoDTO.getWorkorderObjectName())) {
//	            	insertEmtyErrorData(workorderObjectCode, eoDTO.getObjectCategory(), eoDTO.getCountyCode(), msg+"地点描述第一段与区域代码对应的描述不一致");

                } else if (!("新增").equals(eoDTO.getAddrMaintainType()) && (!efficientData(workorderObjectCode,userAccount.getCompanyCode()))) {
	            	insertEmtyErrorData(workorderObjectCode, eoDTO.getObjectCategory(), eoDTO.getCountyCode(), msg+"地点代码不存在");
	            }
                if (StrUtil.isEmpty(eoDTO.getWorkorderObjectName())) {
                    logImportError(workorderObjectCode, msg + "地点描述不能为空");
                } else if (eoDTO.getAddrMaintainType().equals("新增") && isExistWorkorderObjectName(eoDTO.getWorkorderObjectName())) {
                	logImportError(workorderObjectCode, msg + "地点描述已经存在");
                }
                                
                if (StrUtil.isEmpty(eoDTO.getCountyCode())) {
                    logImportError(eoDTO.getCountyCode(), msg + "所属区域代码不能为空");
                } else if(!StrUtil.isNumber(String.valueOf(eoDTO.getCountyCode()))) {
                	logImportError(eoDTO.getCountyCode(), msg + "所属区域非数字");
                } else if(!hasCountyModel(String.valueOf(eoDTO.getCountyCode()))) {
                	logImportError(eoDTO.getCountyCode(), msg + "所属区域不存在");
                } else if (StrUtil.isNotEmpty(workorderObjectCode) && !eoDTO.getWorkorderObjectCode().substring(0,6).equals(eoDTO.getCountyCode())) {
                	logImportError(eoDTO.getCountyCode(), msg + "所属区域与地点代码第一段不相同");
                }
                if (StrUtil.isEmpty(eoDTO.getObjectCategory())) {
                    logImportError(eoDTO.getWorkorderObjectCode(), msg + "专业代码不能为空");
                } else if (StrUtil.isNotEmpty(workorderObjectCode) && eoDTO.getWorkorderObjectCode().indexOf(eoDTO.getObjectCategory()) <= 0) {
                	logImportError(eoDTO.getWorkorderObjectCode(), msg + "专业代码与地点代码中的专业代码不匹配");
                }
                if (eoDTO.getObjectCategory().equals("JZ") || eoDTO.getObjectCategory().equals("YY")) {
                    String btsNo = eoDTO.getBtsNo();
                    if(!btsNo.equals("")){
                        int index = btsNo.indexOf(".");
                        if(index > -1){
                            btsNo = btsNo.substring(0, index);
                        }
                        if (existBtsNo(btsNo)) {
                            logImportError(eoDTO.getObjectCategory(), msg + "基站或营业厅编号不能重复！");
                        }
                    }
                }
                if (StrUtil.isEmpty(eoDTO.getAreaType())) {
                    logImportError(eoDTO.getWorkorderObjectCode(), msg + "行政区域不能为空");
                } else if (!isExistAreaType(eoDTO.getAreaType())) {     //判断区域代码是否存在
                    logImportError(eoDTO.getWorkorderObjectCode(), msg + "行政区域不存在");
                }
                if (!isExistObjCatgory(eoDTO.getObjectCategory())) {      //判断地点专业的存在性
                    logImportError(eoDTO.getWorkorderObjectCode(), msg + "地点专业代码不存在");
                }
                if (StrUtil.isEmpty(eoDTO.getCity())) {
                	logImportError(eoDTO.getWorkorderObjectCode(), msg + "地市不能为空");
                } else if (!isHasCounty(eoDTO.getCity())) {
                    logImportError(eoDTO.getWorkorderObjectCode(), msg + "地市不存在");
                }
                if (StrUtil.isEmpty(eoDTO.getCounty())) {
                	logImportError(eoDTO.getWorkorderObjectCode(), msg + "区县不能为空");
                } else if (!isHasCounty(eoDTO.getCounty())) {
                    logImportError(eoDTO.getWorkorderObjectCode(), msg + "区县不存在");
                }                
                if (StrUtil.isEmpty(eoDTO.getAddrMaintainType())) {
                    logImportError(eoDTO.getWorkorderObjectCode(), msg + "维护类型不能为空");
                }
            }
        }
    }










































    /**
     * 功能：插如的地点代码的为空,重复及已存在的信息(错误信息)
     */
    public void insertEmtyErrorData(String codeName, String objCategory, String countyCode, String codeError) throws SQLModelException {
        try {
            EamAddressAddImportModel_BACK onNetModel = (EamAddressAddImportModel_BACK) sqlProducer;
            SQLModel sqlModel = onNetModel.insertEmtyErrorData(codeName, objCategory, countyCode, codeError);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }

    /**
     * 功能：插如的地点名称的为空的信息。 地点专业 ,所属区县 ,区域代码
     */
    private void logImportError(String code, String codeNameError) throws SQLModelException {
        try {
            EamAddressAddImportModel_BACK onNetModel = (EamAddressAddImportModel_BACK) sqlProducer;
            SQLModel sqlModel = onNetModel.getImportErrorLogModel(code, codeNameError);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }

    public void verifyDisableData(String code, String codeNameError) throws SQLModelException {
        try {
            EamAddressAddImportModel_BACK onNetModel = (EamAddressAddImportModel_BACK) sqlProducer;
            SQLModel sqlModel = onNetModel.getImportErrorLogModel(code, codeNameError);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }

    /**
     * 地点代码是否存在
     * 功能：校验在ETS_OBJECT是否 ture表示表存在，false表示不存在。
     * barcode ：地点代码       companyCode：公司代码
     * @throws com.sino.base.exception.SQLModelException
     */
    public boolean efficientData(String barcode, String companyCode) throws SQLModelException, QueryException {
        boolean hasBarcode = true;
        EamAddressAddImportModel_BACK eoModel = (EamAddressAddImportModel_BACK) sqlProducer;
        SQLModel sqlModel = eoModel.noBarModel(barcode, companyCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (!simpleQuery.hasResult()) {
            hasBarcode = false;
        }
        return hasBarcode;
    }

    /**
     * 校验地点代码是否重复
     * 功能：校验在ETS_OBJECT 的是否存在error,ture 表示导入有错，false 表示导入无错。
     * @throws com.sino.base.exception.SQLModelException
     */
    public boolean doubleModel(String barcode, int orgsId, String transId) throws SQLModelException, QueryException {
        boolean hasError = false;
        EamAddressAddImportModel_BACK eoModel = (EamAddressAddImportModel_BACK) sqlProducer;
        SQLModel sqlModel = eoModel.doubleModel(barcode, orgsId, transId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasError = true;
        }
        return hasError;
    }
    
    public boolean existsObject(String objectCode, int orgId) throws SQLModelException, QueryException {
        boolean hasObject = false;
        EamAddressAddImportModel_BACK eoModel = (EamAddressAddImportModel_BACK) sqlProducer;
        SQLModel sqlModel = eoModel.hasObjectModel(objectCode, orgId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasObject = true;
        }
        return hasObject;
    }

    public boolean existsItem(String objectCode, int orgId) throws SQLModelException, QueryException {
        boolean hasObject = false;
        EamAddressAddImportModel_BACK eoModel = (EamAddressAddImportModel_BACK) sqlProducer;
        SQLModel sqlModel = eoModel.hasItemModel(objectCode, orgId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasObject = true;
        }
        return hasObject;
    }

    /**
     * 功能:检查基站或营业厅编号是否存在
     * @return boolean
     */
    public boolean existBtsNo(String btsNo) {
        boolean exist = false;
        try {
            EamAddressAddImportModel_BACK eoModel = (EamAddressAddImportModel_BACK) sqlProducer;
            SQLModel sqlModel = eoModel.getBtsNoEsistModel(btsNo);
            SimpleQuery simQuery = new SimpleQuery(sqlModel, conn);
            simQuery.executeQuery();
            exist = simQuery.hasResult();
        } catch (QueryException ex) {
            ex.printLog();
        }
        return exist;
    }
    
    /**
     * 功能:检查地点名称是否存在
     * @return boolean
     */
    public boolean isExistWorkorderObjectName(String workorderObjectName) {
    	boolean exist = false;
        try {
            EamAddressAddImportModel_BACK eoModel = (EamAddressAddImportModel_BACK) sqlProducer;
            SQLModel sqlModel = eoModel.getIsExistWorkorderObjectNameModel(workorderObjectName);
            SimpleQuery simQuery = new SimpleQuery(sqlModel, conn);
            simQuery.executeQuery();
            exist = simQuery.hasResult();
        } catch (QueryException ex) {
            ex.printLog();
        }
        return exist;
    }

    /**
     * 区县代码合法性校验
     */
    public boolean isExistCountyCode(String countyCode) throws SQLModelException, QueryException {
        boolean hasError = false;
        EamAddressAddImportModel_BACK eoModel = (EamAddressAddImportModel_BACK) sqlProducer;
        SQLModel sqlModel = eoModel.IsExistCountyCode(countyCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasError = true;
        }
        return hasError;
    }

    /**
     * 功能：地点专业合法性校验
     * @throws com.sino.base.exception.SQLModelException
     */
    public boolean isExistObjCatgory(String category) throws SQLModelException, QueryException {
        boolean hasBarcode = true;
        EamAddressAddImportModel_BACK eoModel = (EamAddressAddImportModel_BACK) sqlProducer;
        SQLModel sqlModel = eoModel.OCModel(category);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (!simpleQuery.hasResult()) {
            hasBarcode = false;
        }
        return hasBarcode;
    }

    /**
     * 功能：区域类型校验
     */
    public boolean isExistAreaType(String dictCode) throws QueryException {
        boolean hasAreaType = true;
        EamAddressAddImportModel_BACK eoModel = (EamAddressAddImportModel_BACK) sqlProducer;
        SQLModel sqlModel = eoModel.isExistAreaType(dictCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (!simpleQuery.hasResult()) {
            hasAreaType = false;
        }
        return hasAreaType;
    }


    /**
     * 功能：导入数据从接口表到正式表。
     * @throws com.sino.base.exception.SQLModelException
     */
    public void getImpOnNetModel() throws SQLModelException, QueryException, DataHandleException {
        ImportObjectModel eoModel = (ImportObjectModel) sqlProducer;
        SQLModel sqlModel = eoModel.getImpOnNetModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：删除接口表的数据。
     * @throws com.sino.base.exception.SQLModelException
     */
    public void deleteImportModel() throws SQLModelException, QueryException, DataHandleException {
        ImportObjectModel eoModel = (ImportObjectModel) sqlProducer;
        SQLModel sqlModel = eoModel.deleteImportModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：导出错误信息
     * 校验在EAM_ADDRESS_ADD_L是否存在error, ture 表示导入有错，false 表示导入无错。
     * @throws com.sino.base.exception.SQLModelException
     */
    public boolean importHasError(String tranId) throws SQLModelException, QueryException {
        boolean hasError = false;
        EamAddressAddImportModel_BACK eoModel = (EamAddressAddImportModel_BACK) sqlProducer;
        SQLModel sqlModel = eoModel.hasErrorModel(tranId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasError = true;
        }
        return hasError;
    }

    /**
     * 功能:判断是否存在此条件下的记录
     */
    public boolean importExist(String tranId) throws SQLModelException, QueryException {
        boolean hasError = false;
        EamAddressAddImportModel_BACK eoModel = (EamAddressAddImportModel_BACK) sqlProducer;
        SQLModel sqlModel = eoModel.hasLineModel(tranId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasError = true;
        }
        return hasError;
    }

    /**
     * 功能:判断是否存在此条件下的记录
     */
    public boolean isHasCounty(String strCode) throws SQLModelException, QueryException {
        boolean isHas = false;
        EamAddressAddImportModel_BACK eoModel = (EamAddressAddImportModel_BACK) sqlProducer;
        SQLModel sqlModel = eoModel.getCountyCode(strCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            isHas = true;
        }
        return isHas;
    }

    /**
     * EAM_ADDRESS_ADD_L  返回校验正常的记录
     */
    public DTOSet getImport(String tranId) throws QueryException, SQLModelException {
        EamAddressAddImportModel_BACK eoModel = (EamAddressAddImportModel_BACK) sqlProducer;
        SQLModel sqlModel = eoModel.getQueryImportModel(tranId);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.setDTOClassName(EamAddressAddLDTO.class.getName());
        sq.executeQuery();
        return sq.getDTOSet();
    }

    /**
     * 功能：校验导入的countyCode是否存在,ture 表示导入正确(存在ou)，false 表示导入有错。
     * @throws com.sino.base.exception.SQLModelException
     */
     public boolean hasCountyModel(String countyCode) throws SQLModelException, QueryException {
    	 boolean hasError=false;
        ImportObjectModel eoModel = new ImportObjectModel(userAccount, null);
        SQLModel sqlModel = eoModel.hasCountyModel(countyCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(simpleQuery.hasResult()){
           hasError = true;
        }
        return hasError;
    }
}
