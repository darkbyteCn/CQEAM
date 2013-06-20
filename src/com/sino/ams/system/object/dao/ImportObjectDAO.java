package com.sino.ams.system.object.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.object.model.ImportObjectModel;
import com.sino.ams.system.user.dao.EtsOuCityMapDAO;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.config.SinoConfig;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: zjs
 * Date: 2008-6-26
 * Time: 20:16:41
 * Function:地点批量导入.
 */
public class ImportObjectDAO extends AMSBaseDAO {
    private ImportObjectModel modelProducer = null;
    private SimpleQuery simpleQuery = null;
    private List<String> countyCodes = null;

    /**
     * 功能：地点导入 AMS_OBJECT_IMPORT 数据访问层构造函数
     *
     * @param userAccount  BaseUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter DTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public ImportObjectDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        super.sqlProducer = new ImportObjectModel(userAccount, dtoParameter);
    }

    /**
     * 功能：导入数据
     *
     * @param dtoSet 由Excel解析出的数据
     * @return true表示导入成功，false表示导入失败
     */
    public boolean importObject(DTOSet dtoSet) {
        boolean dataValid = true;
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            modelProducer = (ImportObjectModel) sqlProducer;
            simpleQuery = new SimpleQuery(null, conn);
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            deleteTmpTableData();
            import2TmpTable(dtoSet);
            if (isAllObjectValid(dtoSet)) {
                submitOrderDtl(dtoSet);
            } else {
                dataValid = false;
            }
            operateResult = true;
        } catch (DataHandleException ex) {
            ex.printLog();
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
        return dataValid;
    }


    /**
     * 功能：删除接口表的数据。
     *
     * @throws DataHandleException 删除接口表的数据出错时抛出数据处理异常
     */
    private void deleteTmpTableData() throws DataHandleException {
        SQLModel sqlModel = modelProducer.deleteImportModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：获取导入的错误信息
     *
     * @return 导入的错误信息
     * @throws QueryException 获取导入的错误信息出错时抛出查询异常
     */
    public RowSet getImportError() throws QueryException {
        SQLModel sqlModel = modelProducer.getImportErrorModel();
        simpleQuery.setSql(sqlModel);
        simpleQuery.executeQuery();
        return simpleQuery.getSearchResult();
    }

    /**
     * 功能：将Excel解析出的数据导入到临时表AMS_OBJECT_IMPORT
     *
     * @param dtoSet DTOSet Excel解析出的数据
     * @throws DataHandleException 将Excel解析出的数据导入到临时表AMS_OBJECT_IMPORT出错时抛出该异常
     */
    private void import2TmpTable(DTOSet dtoSet) throws DataHandleException {
        try {
            if (dtoSet != null && dtoSet.getSize() > 0) {
                countyCodes = new ArrayList<String>();
                for (int i = 0; i < dtoSet.getSize(); i++) {
                    EtsObjectDTO eoDTO = (EtsObjectDTO) dtoSet.getDTO(i);
                    String countyCode = getCountyCodeByAreaCode(eoDTO.getCountyCode());
                    if (countyCode.equals("")) {
                        countyCodes.add(eoDTO.getCountyCode());
                    } else {
                        countyCodes.add(countyCode);
                    }
                    modelProducer.setDTOParameter(eoDTO);
                    SQLModel sqlModel = modelProducer.insertImportModel(countyCode);
                    DBOperator.updateRecord(sqlModel, conn);
                }
            }
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        }
    }

    /**
     * 功能：检查所有数据是否合法
     *
     * @param dtoSet Excel解析出的数据
     * @return true表示全部校验通过，false表示有非法数据存在，且记录错误日志
     */
    private boolean isAllObjectValid(DTOSet dtoSet) {
        int inValidCount = 0;
        if (dtoSet != null && !dtoSet.isEmpty()) {
            int dataCount = dtoSet.getSize();
            for (int i = 0; i < dataCount; i++) {
                EtsObjectDTO objectDTO = (EtsObjectDTO) dtoSet.getDTO(i);
                if (!isObjectValid(objectDTO)) {
                    inValidCount++;
                }
            }
        }
        return (inValidCount == 0);
    }


    /**
     * 功能：将数据从转移到正式表，主要完成如下三件工作：
     * 1：写数据到ETS_OBJECT表；
     * 2：写数据到AMS_OBJECT_ADDRESS表：
     * 3：判断是否在ETS_OBJECT_LOC2表存在，不存在则写地点第二段表ETS_OBJECT_LOC2
     *
     * @param dtoSet DTOSet Excel解析出的数据
     * @throws DataHandleException 将数据从转移到正式表出错时抛出异常
     */
    private void submitOrderDtl(DTOSet dtoSet) throws DataHandleException {
        try {
        	SQLModel sqlModel = null;
            for (int i = 0; i < dtoSet.getSize(); i++) {
                EtsObjectDTO eoDTO = (EtsObjectDTO) dtoSet.getDTO(i);

                eoDTO.setWorkorderObjectNo(getNextGUID());
                eoDTO.setOrganizationId(userAccount.getOrganizationId());
                eoDTO.setCityCode(getCityCode(eoDTO.getCounty()));
                eoDTO.setCountyCode2(getCountyCode(eoDTO.getCounty()));
                String oldObjectCategory = eoDTO.getObjectCategory();
                String objectCategoryCode = getObjectCategoryCode(oldObjectCategory, "CODE");
                eoDTO.setObjectCategory(objectCategoryCode);
                String objectCategoryName = getObjectCategoryCode(oldObjectCategory, "DESCRIPTION");
                eoDTO.setObjectCategoryName(objectCategoryName);
                if (userAccount.getIsTt().equals("Y")) {  //铁通
                	eoDTO.setIsTd("Y");
                } else {
                	eoDTO.setIsTd(userAccount.getIsTd());
                }                 
                eoDTO.setCountyCode(countyCodes.get(i));//区域代码或成本中心表序列号
                modelProducer.setDTOParameter(eoDTO);
                
                if (eoDTO.isAddLocation()) { //新增
                	sqlModel = modelProducer.getDataCreateModel();
                    DBOperator.updateRecord(sqlModel, conn);

                    sqlModel = modelProducer.getAOACreateModel();
                    DBOperator.updateRecord(sqlModel, conn);

                    //if (!userAccount.getIsTt().equals("Y")) {
    	                if (!isLocCode2Exists(eoDTO.getWorkorderObjectCode())) { //判断新增的地点是否在地点第二段代码维护表中存在
    	                    sqlModel = modelProducer.createDoEtsObjectLoc();
    	                    DBOperator.updateRecord(sqlModel, conn);
    	                }
                    //}    	                
                } else if (eoDTO.isUpdateLocation()) { //修改
                	sqlModel = modelProducer.updateEtsObjectInfo(eoDTO);
                    DBOperator.updateRecord(sqlModel, conn);
                    
                    //修改物理地点信息
                    sqlModel = modelProducer.updateEtsObjectLocInfo(eoDTO);
                    DBOperator.updateRecord(sqlModel, conn);
                } else { //失效
                	sqlModel = modelProducer.disabledEtsObject(eoDTO.getWorkorderObjectCode());
                    DBOperator.updateRecord(sqlModel, conn);
                }
                
                
                
            }
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        }
    }

    /**
     * 功能：检查地点是否合法。主要进行以下校验工作：
     * 1：检查地点编码；
     * 2：检查地点名称；
     * 3：检查区域编码；
     * 4：检查行政区划；
     * 5：检查地市区县；
     * 6：检查地点专业；
     * 7：检查地点编号(仅限于基站编号或营业厅编号)
     *
     * @param eoDTO 地点对象
     * @return true表示检查合法，false表示非法
     */
    private boolean isObjectValid(EtsObjectDTO eoDTO) {
        boolean checkResult = false;
        try {
        	checkResult = isMaintainTypeValid(eoDTO);
        	if (checkResult) {
	            checkResult = checkObjectCode(eoDTO);
	            if (checkResult) {
	                checkResult = checkAreaCode(eoDTO);
	                if (checkResult) {
	                    checkResult = checkObjectName(eoDTO);
	                    if (checkResult) {
	                        checkResult = checkAreaType(eoDTO);
	                        if (checkResult) {
	                            checkResult = checkCityAndCounty(eoDTO);
	                            if (checkResult) {
	                                checkResult = checkObjectCategory(eoDTO);
	                                if (checkResult) {
	                                    checkResult = checkBTSOrBizHall(eoDTO);
	                                }
	                            }
	                        }
	                    }
	                }
	            }
        	}
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return checkResult;
    }
    
    /**
     * 功能：检查地点的维护类型是否合法。
     *
     * @return true表示维护类型合法，false表示不合法
     * @throws DataHandleException 
     */
    private boolean isMaintainTypeValid(EtsObjectDTO eoDTO) {
    	boolean checkResult = true;
        String maintainType = eoDTO.getRemark();
        String errorMessage = "";
        if (maintainType == null || maintainType.trim().length() == 0) {
            errorMessage = "未指定地点维护类型，系统不能处理";
            checkResult = false;
        } else {
            if (!(eoDTO.isAddLocation() || eoDTO.isUpdateLocation() || eoDTO.isDisableLocation())) {
                errorMessage = "指定了不支持的地点维护类型，系统不能处理";
                checkResult = false;
            }
        }
        try {
	        if (!checkResult) {
	            logObjectNameError(eoDTO.getWorkorderObjectCode(), errorMessage);
	        }
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return checkResult;
    }

    /**
     * 功能：检查地点编码是否合法。主要完成如下工作：
     * 1：地点代码是否非空；
     * 2：地点代码是否重复(Excel)；
     * 3：地点代码是否与数据库重复；
     * <P>注意：对于内蒙移动有特殊逻辑</P>
     *
     * @param eoDTO 地点对象
     * @return true表示检查合法，false表示非法
     */
    private boolean checkObjectCode(EtsObjectDTO eoDTO) {
        boolean checkResult = true;
        try {
            String errorMessage = "";
            String provinceCode = SinoConfig.getProvinceCode();
            String locationCode = eoDTO.getWorkorderObjectCode();
            EtsOuCityMapDAO etsOuCityMapDAO = new EtsOuCityMapDAO(userAccount, null, conn);
            String companyCode = etsOuCityMapDAO.getCompanyCode(userAccount.getOrganizationId());
            if (StrUtil.isEmpty(locationCode)) {
                errorMessage = "地点代码不能为空";
                checkResult = false;
            } else if (locationCode.indexOf(" ")!=-1) {
            	errorMessage = "地点代码中不能有空格";
                checkResult = false;
            } else if (checkObjectCodeSelfDuplicate(locationCode)) {
                errorMessage = "导入地点代码存在重复";
                checkResult = false;
            }
            if (checkResult) {
                int startIndex = locationCode.indexOf(".");
                int endIndex = locationCode.lastIndexOf(".");
                if (startIndex == 6 && endIndex == 21 && locationCode.length() == 25) {
                    locationCode = locationCode.substring(0, startIndex);
                    
                    if (eoDTO.getCountyCode().length() > 0 || eoDTO.isAddLocation()) {
	                    if (!locationCode.equals(eoDTO.getCountyCode())) {
	                        errorMessage = "导入地点代码第一段与区域代码不一致";
	                        checkResult = false;
	                    }
                    }
                    if (!eoDTO.getWorkorderObjectCode().substring(7, 11).equals(companyCode)) {
                        errorMessage = "导入地点代码第二段前四位与公司代码不一致";
                        checkResult = false;
                    }
                    if (!provinceCode.equals(DictConstant.PROVINCE_CODE_JIN)) {
//                    此条件山西不符合。山西2011-12-28修改
                        if (!StrUtil.isNumber(eoDTO.getWorkorderObjectCode().substring(13, endIndex))) {
                            errorMessage = "导入地点代码第二段后八位应该为数字";
                            checkResult = false;
                        }
                    }
                } else {
                    errorMessage = "导入地点代码格式不正确，必须以区域代码开始，且包含两个点，长度为25个字符";
                    checkResult = false;
                }
            }
            if (checkResult) {
                if (isObjectCodeExist(eoDTO.getWorkorderObjectCode())) {
                    if (eoDTO.isAddLocation()) {
	                	if (provinceCode.equals(DictConstant.PROVINCE_CODE_NM)) {//如果是内蒙
	                        if (getObjectCodeOrganizationId(eoDTO.getWorkorderObjectCode()) == userAccount.getOrganizationId()) {
	                            errorMessage = "导入地点代码在系统中已存在";
	                            checkResult = false;
	                        }
	                    } else {
	                        errorMessage = "导入地点代码在数据库中已存在";
	                        checkResult = false;
	                    }
                    } else if (eoDTO.isDisableLocation()) {
                    	if (hasAssetsUnderLocation(eoDTO.getWorkorderObjectCode())) {
                            if (errorMessage.length() > 0) {
                                errorMessage += ",";
                            }
                            errorMessage += "该地点代码下有资产，不能失效";
                            checkResult = false;
                        }
                    }
                } else {
                	if (eoDTO.isUpdateLocation()) {
                        if (errorMessage.length() > 0) {
                            errorMessage += ",";
                        }
                        errorMessage += "要修改的地点代码不存在";
                        checkResult = false;
                    } else if (eoDTO.isDisableLocation()) {
                        if (errorMessage.length() > 0) {
                            errorMessage += ",";
                        }
                        errorMessage += "要失效的地点代码不存在";
                        checkResult = false;
                    }
                }
            }
            if (!checkResult) {
                logObjectCodeError(eoDTO.getLocation(), errorMessage);
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return checkResult;
    }

    /**
     * 功能：检查地点名称是否合法。需要完成如下工作：
     * 1：地点名称是否非空；
     * 2：地点名称是否重复(Excel，本逻辑暂未开发)；
     * 3：地点名称是否与数据库重复(本逻辑暂未开发)；
     *
     * @param eoDTO 地点对象
     * @return true表示检查合法，false表示非法
     */
    private boolean checkObjectName(EtsObjectDTO eoDTO) {
        boolean checkResult = true;
        try {
            String errorMessage = "";
            String objectName = eoDTO.getWorkorderObjectName();
            int startIndex = objectName.indexOf(".");
            int endIndex = objectName.lastIndexOf(".");
            if (!eoDTO.isDisableLocation()) {
            	if (StrUtil.isEmpty(objectName)) {
                    errorMessage = "地点描述不能为空";
                    checkResult = false;
                }            	
            	if (!SinoConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) { //此条件山西不需要
                	if (objectName.substring(startIndex + 1, endIndex).indexOf(".") != -1) {
                		errorMessage += "地点描述中的“.”的数量不能超过两个";
                        checkResult = false;
                	}
                }
            }
            
            if (objectName.length() > 0) {
            	if (!isCoincidentWithAreaName(eoDTO.getCountyCode(), objectName)) {
                    errorMessage = "地点描述第一段与区域代码对应的描述不一致";
                    checkResult = false;
                } else {
//                    if (startIndex > 0 && endIndex == objectName.length() - 3 && endIndex > startIndex) {
                	if (startIndex > 0 && endIndex > startIndex) {
                        String city = eoDTO.getCity();
                        city += eoDTO.getCounty();
                        objectName = objectName.substring(startIndex + 1);
                        if (!objectName.startsWith(city)) {
                            errorMessage = "地点描述与地市区县名称不一致";
                            checkResult = false;
                        }
                    } else {
                        errorMessage = "地点描述不规范";
                        checkResult = false;
                    }
                }
            	
            	if (checkResult) {
            		objectName = eoDTO.getWorkorderObjectName();
            		objectName = objectName.substring(startIndex + 1, endIndex);
            		String locationCode = eoDTO.getWorkorderObjectCode();
                	startIndex = locationCode.indexOf(".");
                	endIndex = locationCode.lastIndexOf(".");
            		locationCode = locationCode.substring(startIndex + 1, endIndex);
            		String locCode = getLocCode2ByLocDesc(objectName);
                    String locDesc = getLocDesc2ByLocCode(locationCode);
            		
                    if (locCode.length() > 0) {                    	
                		if (!locCode.equals(locationCode)) {
	                			errorMessage += "地点描述第二段“"
		                                 + objectName
		                                 + "”在系统中对应编码为“"
		                                 + locCode
		                                 + "”,与你导入的编码的第二段不一致";
	                			checkResult = false;
                			}
	                 	}
	                 	if (locDesc.length() > 0 && eoDTO.isAddLocation()) {
		                 	if(!locDesc.equals(objectName)) {
		                         errorMessage += "地点代码第二段“"
		                                 + locationCode
		                                 + "”在系统中对应描述为“"
		                                 + locDesc
		                                 + "”,与你导入的描述的第二段不一致";
		                         checkResult = false;
		                 	} 
	                 }            		
//                    String locDesc = eoDTO.getWorkorderObjectName();
//                    locDesc = locDesc.substring(startIndex + 1, endIndex);
//                    String locCode = getLocCode2ByLocDesc(locDesc);
//                    if (!locCode.equals("")) {
//                        if (!eoDTO.getWorkorderObjectCode().contains(locCode)) {
//                            errorMessage = "导入的地点描述的第二段在系统中对应的编号为“" + locCode + "”";
//                            checkResult = false;
//                        }
//                    }
                } 
            	
            	if (checkResult) {
            		if (!eoDTO.isUpdateLocation() && isObjectNameExist(eoDTO.getWorkorderObjectName())) {
            			errorMessage = "导入地点描述在数据库中已存在";
                        checkResult = false;
            		}
            	}
            }
            
            if (!checkResult) {
                logObjectNameError(eoDTO.getWorkorderObjectCode(), errorMessage);
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return checkResult;
    }

    /**
     * 功能：检查区域代码是否合法
     *
     * @param eoDTO 地点对象
     * @return true表示检查合法，false表示非法
     */
    private boolean checkAreaCode(EtsObjectDTO eoDTO) {
        boolean checkResult = true;
        try {
            String errorMessage = "";
            String areaCode = eoDTO.getCountyCode();
            String provinceCode = SinoConfig.getProvinceCode();
            if (eoDTO.isAddLocation()) {
            	if (StrUtil.isEmpty(areaCode)) {
                    errorMessage = "所属区域不能为空";
                    checkResult = false;
                }
            }
            if (areaCode.length() > 0) {
	            if (areaCode.length() != 6) {
	                errorMessage = "所属区域代码位数不是6位";
	                checkResult = false;
	            } else if (!StrUtil.isNumber(areaCode)) {
	                errorMessage = "所属区域非数字";
	                checkResult = false;
	            }
//            else {
//                if (!provinceCode.equals(DictConstant.PROVINCE_CODE_JIN)) {
//                    String preAreaCode = areaCode.substring(0, 4);
//                    if (!preAreaCode.equals(userAccount.getCompanyCode())) {
//                        errorMessage = "所属区域代码必须起始于公司代码，你可能导入了其他公司的区域代码";
//                        checkResult = false;
//                    }
//                }
//            }
		            if (checkResult) {
		                if (!isAreaCodeExist(areaCode)) {      //判断所属区域的存在性
		                    if (!provinceCode.equals(DictConstant.PROVINCE_CODE_JIN)) {
		                        errorMessage = "所属区域不属于贵公司或不存在";
		                    } else {
		                        errorMessage = "所属区域不存在";
		                    }
		                    checkResult = false;
		                }
		            }
	            }
	            if (!checkResult) {
	                logAreaCodeError(eoDTO.getWorkorderObjectCode(), errorMessage);
	            }
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return checkResult;
    }

    /**
     * 功能：检查行政区划是否合法
     *
     * @param eoDTO 地点对象
     * @return true表示检查合法，false表示非法
     */
    private boolean checkAreaType(EtsObjectDTO eoDTO) {
        boolean checkResult = false;
        try {
        	if (eoDTO.isAddLocation()) {
        		if (StrUtil.isEmpty(eoDTO.getAreaType())) {
                    logAreaTypeError(eoDTO.getWorkorderObjectCode(), "行政区划不能为空");
                }	
        	}
           
        	if (!StrUtil.isEmpty(eoDTO.getAreaType())) {
	            if (!isAreaTypeExist(eoDTO.getAreaType())) {
	                logAreaTypeError(eoDTO.getWorkorderObjectCode(), "行政区划不存在");
	            } else {
	                checkResult = true;
	            }
        	}
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return checkResult;
    }

    /**
     * 功能：检查地市区县是否合法
     *
     * @param eoDTO 地点对象
     * @return true表示检查合法，false表示非法
     */
    private boolean checkCityAndCounty(EtsObjectDTO eoDTO) {
        boolean checkResult = true;
        try {
            boolean checkCityResult = true;
            boolean checkCountyResult = true;
            String errorCityMessage = "";
            String errorCountyMessage = "";
            String city = eoDTO.getCity();
            String county = eoDTO.getCounty();
            
            if (eoDTO.isAddLocation()) {
            	if (StrUtil.isEmpty(city)) {
                    errorCityMessage = "地市不能为空";
                    checkCityResult = false;
                }            	
            	if (StrUtil.isEmpty(county)) {
                    errorCountyMessage = "区县不能为空";
                    checkCountyResult = false;
                }
            }
            
            if (city.length() > 0) {
                if (!isCityExist(city)) {
                    errorCityMessage = "地市“" + city + "”不存在";
                    checkCityResult = false;
                }
            }
            if (county.length() >0 && checkCityResult) {
                if (!isCountyExist(county, city)) {
                    errorCountyMessage = "区县“" + county + "”不存在，或者不在地市“" + city + "”下";
                    checkCountyResult = false;
                }
            }
            if (!checkCityResult) {
                logCityError(eoDTO.getWorkorderObjectCode(), errorCityMessage);
            }
            if (!checkCountyResult) {
                logCountyError(eoDTO.getWorkorderObjectCode(), errorCountyMessage);
            }
            checkResult = (checkCityResult && checkCountyResult);
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return checkResult;
    }

    /**
     * 功能：检查地点专业是否合法
     *
     * @param eoDTO 地点对象
     * @return true表示检查合法，false表示非法
     */
    private boolean checkObjectCategory(EtsObjectDTO eoDTO) {
        boolean checkResult = true;
        try {
            String errorMessage = "";
            String objectCategory = eoDTO.getObjectCategory();
            if (eoDTO.isAddLocation()) {
            	if (StrUtil.isEmpty(objectCategory)) {
                    errorMessage = "专业代码不能为空";
                    checkResult = false;
                }
            }
            if (StrUtil.isNotEmpty(objectCategory)) {
            	if (!isObjectCategoryExist(objectCategory)) {      //判断地点代码的存在性
                    errorMessage = "专业代码不存在";
                    logObjectCategoryError(eoDTO.getWorkorderObjectCode(), eoDTO.getWorkorderObjectName(), eoDTO.getCountyCode(), "专业代码不存在");
                    checkResult = false;
                } else if (eoDTO.getWorkorderObjectCode().indexOf(eoDTO.getObjectCategory()) <= 0) { //验证专业代码是否在地点代码中存在
                    errorMessage = "专业代码与地点代码中的专业代码不匹配";
                    checkResult = false;
                }
            }

            if (!checkResult) {
                logObjectCategoryError(eoDTO.getWorkorderObjectCode(), eoDTO.getWorkorderObjectName(), eoDTO.getCountyCode(), errorMessage);
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return checkResult;
    }

    /**
     * 功能：检查基站编号或营业厅编号是否合法
     *
     * @param eoDTO 地点对象
     * @return true表示检查合法，false表示非法
     */
    private boolean checkBTSOrBizHall(EtsObjectDTO eoDTO) {
        boolean checkResult = true;
        try {
            String errorMessage = "";
            String objectCategory = eoDTO.getObjectCategory();
            if (objectCategory.equalsIgnoreCase("JZ") || objectCategory.equalsIgnoreCase("YY")) {
                String btsNo = eoDTO.getBtsNo();
                if (!btsNo.equals("")) {
                    int index = btsNo.indexOf(".");
                    if (index > -1) {
                        btsNo = btsNo.substring(0, index);
                    }
                    if (isBTSOrBizHallNoExist(btsNo)) {
                        if (objectCategory.equalsIgnoreCase("JZ")) {
                            errorMessage = "基站编号“" + btsNo + "”已经存在";
                        } else {
                            errorMessage = "营业厅编号“" + btsNo + "”已经存在";
                        }
                        checkResult = false;
                    }
                } else {
                	if (eoDTO.isAddLocation()) {
	                    if (objectCategory.equalsIgnoreCase("JZ")) {
	                        errorMessage = "基站编号不能为空";
	                    } else {
	                        errorMessage = "营业厅编号不能为空";
	                    }
	                    checkResult = false;
                	}
                }
                if (!checkResult) {
                    logObjectCategoryError(eoDTO.getWorkorderObjectCode(), eoDTO.getWorkorderObjectName(), eoDTO.getCountyCode(), errorMessage);
                }
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return checkResult;
    }

    /**
     * 功能:检查基站或营业厅编号是否存在
     *
     * @param btsNo 基站或营业厅编号
     * @return boolean trueb表示存在，false表示不存在
     */
    private boolean isBTSOrBizHallNoExist(String btsNo) {
        boolean exist = false;
        try {
            SQLModel sqlModel = modelProducer.getBTSNoExistModel(btsNo);
            simpleQuery.setSql(sqlModel);
            simpleQuery.executeQuery();
            exist = simpleQuery.hasResult();
        } catch (QueryException ex) {
            ex.printLog();
        }
        return exist;
    }

    /**
     * 功能：检查地市是否存在
     *
     * @param city 地市名称
     * @return boolean trueb表示存在，false表示不存在
     * @throws QueryException 检查数据出错时抛出查询异常
     */
    private boolean isCityExist(String city) throws QueryException {
        SQLModel sqlModel = modelProducer.getCityExistModel(city);
        simpleQuery.setSql(sqlModel);
        simpleQuery.executeQuery();
        return simpleQuery.hasResult();
    }

    /**
     * 功能：检查区县是否存在
     *
     * @param county 区县名称
     * @param city   地市名称
     * @return boolean trueb表示存在，false表示不存在
     * @throws QueryException 检查数据出错时抛出查询异常
     */
    private boolean isCountyExist(String county, String city) throws QueryException {
        SQLModel sqlModel = modelProducer.getCountyExistModel(city, county);
        simpleQuery.setSql(sqlModel);
        simpleQuery.executeQuery();
        return simpleQuery.hasResult();
    }

    /**
     * 功能：插如的地点代码的为空的信息。
     *
     * @throws DataHandleException
     */
    private void logObjectCodeError(String codeName, String codeError) throws DataHandleException {

        SQLModel sqlModel = modelProducer.getObjectCodeLogModel(codeName, codeError);
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：插如地市为空的信息。
     *
     * @throws DataHandleException
     */
    private void logCityError(String locationCode, String codeError) throws DataHandleException {

        SQLModel sqlModel = modelProducer.getCityErrorLogModel(locationCode, codeError);
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：插如区县为空的信息。
     *
     * @throws DataHandleException
     */
    private void logCountyError(String locationCode, String codeError) throws DataHandleException {
        SQLModel sqlModel = modelProducer.getCountyErrorLogModel(locationCode, codeError);
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：插入导入地点名称非法的错误信息。
     *
     * @throws DataHandleException
     */
    private void logObjectNameError(String code, String codeNameError) throws DataHandleException {

        SQLModel sqlModel = modelProducer.getObjectNameLogModel(code, codeNameError);
        DBOperator.updateRecord(sqlModel, conn);
    }


    /**
     * 功能：插如的行政区划为空的信息。
     *
     * @throws DataHandleException
     */
    private void logAreaTypeError(String locationCode, String areaTypeError) throws DataHandleException {

        SQLModel sqlModel = modelProducer.getAreaTypeLogModel(locationCode, areaTypeError);
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：插如的地点专业的为空的信息。
     *
     * @throws DataHandleException
     */
    private void logObjectCategoryError(String code, String codeName, String countyCode, String obCateError) throws DataHandleException {

        SQLModel sqlModel = modelProducer.getObjectCategoryLogModel(code, codeName, countyCode, obCateError);
        DBOperator.updateRecord(sqlModel, conn);
    }


    /**
     * 功能：插如的地点区县的为空的信息。
     *
     * @throws DataHandleException
     */
    private void logAreaCodeError(String code, String countyError) throws DataHandleException {
        SQLModel sqlModel = modelProducer.getAreaCodeLogModel(code, countyError);
        DBOperator.updateRecord(sqlModel, conn);
    }


    /**
     * 功能：校验在ETS_OBJECT是否存在objectCode ture表示存在，false表示不存在。
     */
    private boolean isObjectCodeExist(String objectCode) {
        boolean hasBarcode = false;
        try {
            SQLModel sqlModel = modelProducer.getObjectCodeExistModel(objectCode);
            simpleQuery.setSql(sqlModel);
            simpleQuery.executeQuery();
            hasBarcode = simpleQuery.hasResult();
        } catch (QueryException ex) {
            ex.printLog();
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return hasBarcode;
    }
    
    /**
     * 功能：校验在ETS_OBJECT是否存在objectName ture表示存在，false表示不存在。
     */
    private boolean isObjectNameExist(String objectName) {
        boolean hasBarcode = false;
        try {
            SQLModel sqlModel = modelProducer.getObjectNameExistModel(objectName);
            simpleQuery.setSql(sqlModel);
            simpleQuery.executeQuery();
            hasBarcode = simpleQuery.hasResult();
        } catch (QueryException ex) {
            ex.printLog();
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return hasBarcode;
    }

    /**
     * 功能：校验在ETS_OBJECT是否存在objectCode ture表示存在，false表示不存在。
     */
    private int getObjectCodeOrganizationId(String objectCode) {
        int existCount = 0;
        try {
            SQLModel sqlModel = modelProducer.getObjectCodeOrganizationIdModel(objectCode);
            simpleQuery.setSql(sqlModel);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                existCount = Integer.parseInt(simpleQuery.getFirstRow().getStrValue("ORGANBIZATION_ID"));
            }
        } catch (QueryException ex) {
            ex.printLog();
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return existCount;
    }


    /**
     * 功能：校验在AMS_OBJECT_IMPORT是否存在barcode ture表示表存在，false表示不存在。
     *
     * @throws DataHandleException
     */
    private boolean isObjectCategoryExist(String category) {
        boolean hasBarcode = false;
        try {
            SQLModel sqlModel = modelProducer.OCModel(category);
            simpleQuery.setSql(sqlModel);
            simpleQuery.executeQuery();
            hasBarcode = simpleQuery.hasResult();
        } catch (QueryException ex) {
            ex.printLog();
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return hasBarcode;
    }

    /**
     * 功能：校验在AMS_OBJECT_IMPORT是否存在AreaType ture表示表存在，false表示不存在。
     */
    private boolean isAreaTypeExist(String dictCode) throws QueryException {
        boolean hasAreaType = true;
        SQLModel sqlModel = modelProducer.isExistAreaType(dictCode);
        simpleQuery.setSql(sqlModel);
        simpleQuery.executeQuery();
        if (!simpleQuery.hasResult()) {
            hasAreaType = false;
        }
        return hasAreaType;
    }

    /**
     * 功能：校验在AMS_OBJECT_IMPORT是否存在error,ture 表示导入有错，false 表示导入无错。
     *
     * @throws DataHandleException
     */
    public boolean importHasError() throws DataHandleException, QueryException {
        boolean hasError = false;
        SQLModel sqlModel = modelProducer.hasErrorModel();
        simpleQuery.setSql(sqlModel);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasError = true;
        }
        return hasError;
    }

    /**
     * 功能：校验导入的countyCode是否存在,ture 表示导入正确(存在ou)，false 表示导入有错。
     *
     * @throws DataHandleException
     */
    private boolean isAreaCodeExist(String areaCode) throws QueryException {
        boolean hasError = false;
        SQLModel sqlModel = modelProducer.hasCountyModel(areaCode);
        simpleQuery.setSql(sqlModel);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasError = true;
        }
        return hasError;
    }

    /**
     * 功能：校验导入的地点描述的第一段是否与导入的所属区域对应的描述一致
     *
     * @param countyCode 区域代码
     * @param objectName 地点三段描述
     * @return true表示地点合法，false表示地点非法
     * @throws QueryException
     */
    private boolean isCoincidentWithAreaName(String countyCode, String objectName) throws QueryException {
        boolean isValid = false;
        try {
            SQLModel sqlModel = modelProducer.hasCountyModel(countyCode);
            simpleQuery.setSql(sqlModel);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                Row row = simpleQuery.getFirstRow();
                String location = row.getStrValue("DESCRIPTION");
                int index = objectName.indexOf(".");
                int endIndex = objectName.lastIndexOf(".");
                if (index > -1 && endIndex > index) {
                    objectName = objectName.substring(0, index);
                    if (objectName.equals(location)) {
                        isValid = true;
                    }
                }
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        }
        return isValid;
    }

    private String getLocCode2ByLocDesc(String locDesc) throws QueryException {
        String locCode2 = "";
        try {
            SQLModel sqlModel = modelProducer.getLocCode2ByDescModel(locDesc);
            simpleQuery.setSql(sqlModel);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                Row row = simpleQuery.getFirstRow();
                locCode2 = row.getStrValue("LOC2_CODE");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return locCode2;
    }
    
    private String getLocDesc2ByLocCode(String locCode) throws QueryException {
        String locDesc2 = "";
        try {
            SQLModel sqlModel = modelProducer.getLocDesc2ByDescModel(locCode);
            simpleQuery.setSql(sqlModel);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                Row row = simpleQuery.getFirstRow();
                locDesc2 = row.getStrValue("LOC2_DESC");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return locDesc2;
    }

    /**
     * 功能：表 AMS_OBJECT_IMPORT 的查询sql。
     *
     * @throws QueryException
     */
    public RowSet getQueryImportModel() throws QueryException {
        SQLModel sqlModel = modelProducer.getQueryImportModel();
        simpleQuery.setSql(sqlModel);
        simpleQuery.executeQuery();
        return simpleQuery.getSearchResult();
    }

    /**
     * 功能：校验在ETS_OBJECT 的地点代码是否重复是否存在error,ture 表示导入有错，false 表示导入无错。
     *
     * @throws QueryException
     */
    private boolean checkObjectCodeSelfDuplicate(String objectCode) throws QueryException {
        SQLModel sqlModel = modelProducer.getObjectCodeSelfDuplicateModel(objectCode);
        simpleQuery.setSql(sqlModel);
        simpleQuery.executeQuery();
        return simpleQuery.hasResult();
    }


    /**
     * 功能：获取成本中心表序列号
     *
     * @param areaCode 区域代码
     * @return 成本中心表序列号
     * @throws QueryException 抛出查询异常
     */
    private String getCountyCodeByAreaCode(String areaCode) throws QueryException {
        String countyCode = "";
        try {
            SQLModel sqlModel = modelProducer.getAreaCountyCode(areaCode);
            simpleQuery.setSql(sqlModel);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                countyCode = simpleQuery.getFirstRow().getStrValue("COUNTY_CODE");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return countyCode;
    }

    /**
     * 功能：根据区县名称获取地市代码
     *
     * @param county 区县名称
     * @return 地市代码
     * @throws QueryException 根据区县名称获取地市代码出错时抛出查询异常
     */
    private String getCityCode(String county) throws QueryException {
        String city = "";
        try {
            SQLModel sqlModel = modelProducer.getCityCodeModel(county);
            simpleQuery.setSql(sqlModel);
            simpleQuery.executeQuery();
            RowSet rs = simpleQuery.getSearchResult();
            if (rs != null && rs.getSize() > 0) {
                Row row = rs.getRow(0);
                city = StrUtil.nullToString(row.getValue("CITY_CODE"));
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return city;
    }

    /**
     * 功能：根据区县名称获取区县代码
     *
     * @param county 区县名称
     * @return 区县代码
     * @throws QueryException 根据区县名称获取区县代码出错时抛出查询异常
     */
    private String getCountyCode(String county) throws QueryException {
        String city = "";
        try {
            SQLModel sqlModel = modelProducer.getCountyCodeModel(county);
            simpleQuery.setSql(sqlModel);
            simpleQuery.executeQuery();
            RowSet rs = simpleQuery.getSearchResult();
            if (rs != null && rs.getSize() > 0) {
                Row row = rs.getRow(0);
                city = StrUtil.nullToString(row.getValue("COUNTY_CODE"));
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return city;

    }

    /**
     * 功能：根据专业代码(JZ、YY、XL)查询EAM系统的专业代码和专业名称
     *
     * @param objectCategory Excel中输入的专业代码
     * @param objectName     字段名称，用于确定返回代码或名称
     * @return 返回代码或名称
     * @throws QueryException 查询EAM系统的专业代码和专业名称出错时抛出查询异常
     */
    private String getObjectCategoryCode(String objectCategory, String objectName) throws QueryException {
        String newId = "";
        try {
            SQLModel sqlModel = modelProducer.getObjectCategoryCode(objectCategory);
            simpleQuery.setSql(sqlModel);
            simpleQuery.executeQuery();
            RowSet rs = simpleQuery.getSearchResult();
            if (rs != null && rs.getSize() > 0) {
                Row row = rs.getRow(0);
                newId = StrUtil.nullToString(row.getValue(objectName));
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return newId;
    }


    /**
     * 功能：检查地点第二段代码是否存在
     *
     * @param workorderObjectCode 地点代码
     * @return true表示地点编码的第二段存在，false表示不存在
     * @throws QueryException 检查地点第二段代码是否存在出错时抛出数据查询异常
     */
    private boolean isLocCode2Exists(String workorderObjectCode) throws QueryException {
        SQLModel sqlModel = modelProducer.getIsExistsLocCode(workorderObjectCode);
        simpleQuery.setSql(sqlModel);
        simpleQuery.executeQuery();
        return simpleQuery.hasResult();
    }
    
    /**
     * 功能：判断地点下是否有资产
     *
     * @return true表示有资产，false表示无资产
     * @throws QueryException 检查地点下是否有资产出错时抛出该异常
     */
    private boolean hasAssetsUnderLocation(String objectCode) throws QueryException {
        SQLModel sqlModel = modelProducer.getLocationAssetsModel(objectCode);
        simpleQuery.setSql(sqlModel);
        simpleQuery.executeQuery();
        return simpleQuery.hasResult();
    }
}
