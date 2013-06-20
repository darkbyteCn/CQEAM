package com.sino.ams.system.object.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.onnet.dto.AmsItemOnNetDTO;
import com.sino.ams.system.object.dto.ImportObjectDTOSn;
import com.sino.ams.system.object.model.ImportObjectModelSn;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.RowSet;
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

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-6-1
 * Time: 17:47:48
 * To change this template use File | Settings | File Templates.
 */
public class ImportObjectDAOSn  extends AMSBaseDAO {

	private SfUserDTO sfUser = null;

	public ImportObjectDAOSn(SfUserDTO userAccount, AmsItemOnNetDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		ImportObjectDTOSn dtoPara = (ImportObjectDTOSn)dtoParameter;
		super.sqlProducer = new ImportObjectModelSn((SfUserDTO)userAccount, dtoPara);
	}

    public void doVerifyData(DTOSet dtoSet) throws SQLModelException, QueryException {
        if (dtoSet != null && dtoSet.getSize() > 0) {
            for (int i = 0; i < dtoSet.getSize(); i++) {
                ImportObjectDTOSn eoDTO = (ImportObjectDTOSn) dtoSet.getDTO(i);
                if (StrUtil.isEmpty(eoDTO.getLocationCode())) {
                    insertEmtyErrorData(eoDTO.getLocation(), eoDTO.getObjectCategory(), eoDTO.getCountyCode(), "地点代码不能为空");
                } else if (efficientData(eoDTO.getWorkorderObjectCode(), eoDTO.getCompanyCode())) {  //判断地点代码
                    insertEmtyErrorData(eoDTO.getLocation(), eoDTO.getObjectCategory(), eoDTO.getCountyCode(), "地点代码已存在");
                } else if (doubleModel(eoDTO.getWorkorderObjectCode(), eoDTO.getOrganizationId())) {
                    insertEmtyErrorData(eoDTO.getLocation(), eoDTO.getObjectCategory(), eoDTO.getCountyCode(), "导入地点代码存在重复");
                }
                if (StrUtil.isEmpty(eoDTO.getCity())) {
                    insertEmtyCityData(eoDTO.getLocationCode(), "地市不能为空");
                }
                if (StrUtil.isEmpty(eoDTO.getCity())) {
                    insertEmtyCountyData(eoDTO.getLocationCode(), "区县不能为空");
                }
                if (StrUtil.isEmpty(eoDTO.getLocation())) {
                    inEtyNaErrorData(eoDTO.getLocationCode(), eoDTO.getObjectCategory(), eoDTO.getCountyCode(), "地点名称不能为空");
                }
                if (StrUtil.isEmpty(eoDTO.getObjectCategory())) {
                    inEtyCaErrorData(eoDTO.getWorkorderObjectCode(), eoDTO.getWorkorderObjectName(), eoDTO.getCountyCode(), "地点专业不能为空");
                }
                if (StrUtil.isEmpty(eoDTO.getCountyCode())) {
                    inEtyCountyErrorData(eoDTO.getWorkorderObjectCode(), eoDTO.getWorkorderObjectName(), eoDTO.getObjectCategory(), "地点区县不能为空");
                }
                if(StrUtil.isEmpty(eoDTO.getAreaType())){
                	inEtyNaErrorData(eoDTO.getWorkorderObjectCode(), eoDTO.getWorkorderObjectName(), eoDTO.getCountyCode(), "行政区划不能为空");
                }
                if (!isExistAreaType(eoDTO.getAreaType())) {
                	inEtyCaErrorData(eoDTO.getWorkorderObjectCode(), eoDTO.getWorkorderObjectName(), eoDTO.getCountyCode(), "行政区划不存在");
                }
                if (!isExistObjCatgory(eoDTO.getObjectCategory())) {      //判断地点代码的存在性
                    inEtyCaErrorData(eoDTO.getWorkorderObjectCode(), eoDTO.getWorkorderObjectName(), eoDTO.getCountyCode(), "地点专业代码不存在");
                }

                if(!StrUtil.isNumber(eoDTO.getCountyCode())){
                    inEtyCountyErrorData(eoDTO.getWorkorderObjectCode(), eoDTO.getWorkorderObjectName(), eoDTO.getObjectCategory(), "成本中心代码非数字");
                }else  if (!hasCountyModel(eoDTO.getCountyCode())) {      //判断区县代码的存在性
                    inEtyCountyErrorData(eoDTO.getWorkorderObjectCode(), eoDTO.getWorkorderObjectName(), eoDTO.getObjectCategory(), "成本中心代码不存在");
                }
                if (StrUtil.isEmpty(eoDTO.getCompanyCode())) {
                    inEtyCompanyErrorData(eoDTO.getWorkorderObjectCode(), "公司代码不能为空");
                } else if (!hasCompanyModel(eoDTO.getCompanyCode())) {
                    inEtyCompanyErrorData(eoDTO.getWorkorderObjectCode(), "公司代码不存在");
                }
                if (StrUtil.isEmpty(eoDTO.getIsTd())) {
                    inEtyIsTdErrorData(eoDTO.getWorkorderObjectCode(), "是否TD不能为空");
                } else if (!isTdError(eoDTO.getIsTd())) {
                    inEtyIsTdErrorData(eoDTO.getWorkorderObjectCode(), "是否TD填写错误");
                }
            }
        }
    }

     public void insertErrorData(String code,String codeError,String objCategoryError,String ctyError) throws SQLModelException {
        try {
            ImportObjectModelSn onNetModel = (ImportObjectModelSn) sqlProducer;
            if(!StrUtil.isEmpty(code)){
            SQLModel sqlModel = onNetModel.insertPartErrorModel(code, codeError);
             DBOperator.updateRecord(sqlModel, conn);
            }
            if(!StrUtil.isEmpty(objCategoryError)){
              SQLModel sqlModel = onNetModel.insertCaErrorModel( code, objCategoryError);
              DBOperator.updateRecord(sqlModel, conn);
            }
            if(!StrUtil.isEmpty(ctyError)){
              SQLModel sqlModel = onNetModel.insertCtyErrorModel( code, ctyError);
              DBOperator.updateRecord(sqlModel, conn);
            }
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }

    public boolean isTdError(String isTd) {
        boolean hasError = false;
        if (isTd.equals("Y") || isTd.equals("N")) {
            hasError = true;
        }
        return hasError;
    }

    public void inEtyIsTdErrorData(String code,String tdError) throws SQLModelException {
        try {
            ImportObjectModelSn onNetModel = (ImportObjectModelSn) sqlProducer;
            SQLModel sqlModel = onNetModel.inIsTdErrorData(code, tdError);
             DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }

    public boolean hasCompanyModel(String companyCode) throws SQLModelException, QueryException {
        boolean hasError=false;
            ImportObjectModelSn eoModel = (ImportObjectModelSn) sqlProducer;
            SQLModel sqlModel = eoModel.hasCompanyModel(companyCode);
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if(simpleQuery.hasResult()){
               hasError = true;
            }
        return hasError;
    }

    public void inEtyCompanyErrorData(String code,String companyError) throws SQLModelException {
        try {
            ImportObjectModelSn onNetModel = (ImportObjectModelSn) sqlProducer;
            SQLModel sqlModel = onNetModel.inEtyCompanyErrorData(code, companyError);
             DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }

     public void insertEmtyErrorData(String codeName,String objCategory,String countyCode,String codeError) throws SQLModelException {
        try {
            ImportObjectModelSn onNetModel = (ImportObjectModelSn) sqlProducer;
            SQLModel sqlModel = onNetModel.insertEmtyErrorData(codeName, objCategory,countyCode,codeError);
             DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }

     public void insertEmtyCityData(String locationCode, String codeError) throws SQLModelException {
        try {
            ImportObjectModelSn onNetModel = (ImportObjectModelSn) sqlProducer;
            SQLModel sqlModel = onNetModel.insertEmtyCityData(locationCode, codeError);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }

     public void insertEmtyCountyData(String locationCode, String codeError) throws SQLModelException {
        try {
            ImportObjectModelSn onNetModel = (ImportObjectModelSn) sqlProducer;
            SQLModel sqlModel = onNetModel.insertEmtyCountyData(locationCode, codeError);
            DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }

     public void inEtyNaErrorData(String code,String objCategory,String countyCode,String codeNameError) throws SQLModelException {
        try {
            ImportObjectModelSn onNetModel = (ImportObjectModelSn) sqlProducer;
            SQLModel sqlModel = onNetModel.inEmtyNameErrorData(code, objCategory,countyCode,codeNameError);
             DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }

     public void inEtyCaErrorData(String code,String codeName,String countyCode,String obCateError) throws SQLModelException {
        try {
            ImportObjectModelSn onNetModel = (ImportObjectModelSn) sqlProducer;
            SQLModel sqlModel = onNetModel.inEmtyCategoryErrorData(code, codeName,countyCode,obCateError);
             DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }

     public void inEtyCountyErrorData(String code,String codeName,String objCategory,String countyError) throws SQLModelException {
        try {
            ImportObjectModelSn onNetModel = (ImportObjectModelSn) sqlProducer;
            SQLModel sqlModel = onNetModel.inEmtyCountyErrorData(code, codeName,objCategory,countyError);
             DBOperator.updateRecord(sqlModel, conn);
        } catch (DataHandleException ex) {
            ex.printLog();
        }
    }

     public boolean efficientData(String barcode, String companyCode) throws SQLModelException, QueryException {
        boolean hasBarcode=true;
        ImportObjectModelSn eoModel = (ImportObjectModelSn) sqlProducer;
        SQLModel sqlModel = eoModel.noBarModel(barcode, companyCode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if(!simpleQuery.hasResult()){
           hasBarcode = false;
        }
        return hasBarcode;
    }

     public boolean isExistObjCatgory(String category) throws SQLModelException, QueryException {
        boolean hasBarcode=true;
            ImportObjectModelSn eoModel = (ImportObjectModelSn) sqlProducer;
            SQLModel sqlModel = eoModel.OCModel(category);
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if(!simpleQuery.hasResult()){
               hasBarcode = false;
            }
        return hasBarcode;
    }

    public  boolean isExistAreaType(String dictCode) throws QueryException{
    	boolean hasAreaType = true;
    	ImportObjectModelSn eoModel = (ImportObjectModelSn) sqlProducer;
    	SQLModel sqlModel = eoModel.isExistAreaType(dictCode);
    	SimpleQuery simpleQuery = new SimpleQuery(sqlModel,conn);
    	simpleQuery.executeQuery();
    	if(! simpleQuery.hasResult()){
    		hasAreaType = false;
    	}
    	return hasAreaType;
    }

     public boolean importHasError() throws SQLModelException, QueryException {
        boolean hasError=false;
            ImportObjectModelSn eoModel = (ImportObjectModelSn) sqlProducer;
            SQLModel sqlModel = eoModel.hasErrorModel();
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if(simpleQuery.hasResult()){
               hasError = true;
            }
        return hasError;
    }

     public boolean hasCountyModel(String countyCode) throws SQLModelException, QueryException {
        boolean hasError=false;
            ImportObjectModelSn eoModel = (ImportObjectModelSn) sqlProducer;
            SQLModel sqlModel = eoModel.hasCountyModel(countyCode);
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if(simpleQuery.hasResult()){
               hasError = true;
            }
        return hasError;
    }

    public RowSet getQueryImportModel() throws SQLModelException, QueryException {
       RowSet rows = new RowSet();
       ImportObjectModelSn eoModel = (ImportObjectModelSn) sqlProducer;
       SQLModel sqlModel = eoModel.getQueryImportModel();
       SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
       simpleQuery.executeQuery();
       if(simpleQuery.hasResult()){
          rows = simpleQuery.getSearchResult();
       }
       return rows;
   }

     public void getImpOnNetModel() throws SQLModelException, QueryException, DataHandleException {
            ImportObjectModelSn eoModel = (ImportObjectModelSn) sqlProducer;
            SQLModel sqlModel = eoModel.getImpOnNetModel();
            DBOperator.updateRecord(sqlModel, conn);
    }

     public void deleteImportModel() throws SQLModelException, QueryException, DataHandleException {
            ImportObjectModelSn eoModel = (ImportObjectModelSn) sqlProducer;
            SQLModel sqlModel = eoModel.deleteImportModel();
            DBOperator.updateRecord(sqlModel, conn);
    }

     public boolean doubleModel(String barcode,int orgsId) throws SQLModelException, QueryException {
        boolean hasError=false;
            ImportObjectModelSn eoModel = (ImportObjectModelSn) sqlProducer;
            SQLModel sqlModel = eoModel.doubleModel(barcode,orgsId);
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if(simpleQuery.hasResult()){
               hasError = true;
            }
        return hasError;
    }

     public boolean doubleNameModel(String name,String orgsId) throws SQLModelException, QueryException {
        boolean hasError=false;
            ImportObjectModelSn eoModel = (ImportObjectModelSn) sqlProducer;
            SQLModel sqlModel = eoModel.doubleNameModel(name,orgsId);
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if(simpleQuery.hasResult()){
               hasError = true;
            }
        return hasError;
    }

   public DTOSet getImport() throws QueryException, SQLModelException {
        ImportObjectModelSn eoModel = (ImportObjectModelSn) sqlProducer;
        SimpleQuery sq = new SimpleQuery(eoModel.getQueryImportModel(), conn);
        sq.setDTOClassName(ImportObjectDTOSn.class.getName());
        sq.executeQuery();
        return sq.getDTOSet();
    }
}