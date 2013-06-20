package com.sino.ams.newSite.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newSite.dto.EamAddressAddLDTO;
import com.sino.ams.newSite.model.EamAddressAddImportModel;
import com.sino.ams.system.user.dao.EtsOuCityMapDAO;
import com.sino.base.data.Row;
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
 * @author 作者 :wangzhipeng
 * @version 创建时间：Apr 18, 2011 3:42:18 PM
 *          类说明：地点信息导入 DAO
 */
public class EamAddressAddImportDAO extends AMSBaseDAO {

    private EamAddressAddImportModel modelProducer = null;
    private SimpleQuery simpleQuery = null;
    private EamAddressAddLDTO lineDTO = null;
    private DTOSet dataLines = null;

    public EamAddressAddImportDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        super.sqlProducer = new EamAddressAddImportModel(userAccount, dtoParameter);
    }

    public void setDTOParameter(DTO dtoParameter) {
        super.setDTOParameter(dtoParameter);
        lineDTO = (EamAddressAddLDTO) dtoParameter;
    }

    /**
     * 功能：导入数据。
     *
     * @param dtoSet  由Excel解析出的数据
     * @param transId 系统生成的主表主键
     * @return true表示导入成功，且所有数据校验合法，false表示导入失败或有非法数据存在
     */
    public boolean importObject(DTOSet dtoSet, String transId) {
        boolean dataValid = true;
        try {
            modelProducer = (EamAddressAddImportModel) sqlProducer;
            simpleQuery = new SimpleQuery(null, conn);
            dataLines = dtoSet;

            deleteLineData(transId);
            importLineData(dtoSet, transId);
            dataValid = checkAllObject(dtoSet);
        } catch (Throwable ex) {
            Logger.logError(ex);
            dataValid = false;
        }
        return dataValid;
    }

    /**
     * 功能：删除接口表的数据。
     *
     * @param transId 流程单据ID
     * @throws com.sino.base.exception.DataHandleException
     *          删除数据出错时抛出数据处理异常
     */
    private void deleteLineData(String transId) throws DataHandleException {
        EamAddressAddLDTO lineDTO = new EamAddressAddLDTO();
        lineDTO.setTransId(transId);
        setDTOParameter(lineDTO);
        SQLModel sqlModel = modelProducer.deleteImportModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：将Excel解析出的数据导入到行表EAM_ADDRESS_ADD_L
     *
     * @param dtoSet  DTOSet Excel解析出的数据
     * @param transId 单据头ID
     * @throws DataHandleException 将Excel解析出的数据导入到行表EAM_ADDRESS_ADD_L出错时抛出该异常
     */
    private void importLineData(DTOSet dtoSet, String transId) throws DataHandleException {
        try {
            if (dtoSet != null && dtoSet.getSize() > 0) {
                for (int i = 0; i < dtoSet.getSize(); i++) {
                    EamAddressAddLDTO lineDTO = (EamAddressAddLDTO) dtoSet.getDTO(i);
                    lineDTO.setTransId(transId);
                    String oldCountyCode = lineDTO.getCountyCode();
                    if (lineDTO.getAddrMaintainType().equals("新增")) {
                        String countyCode = getCountyCodeByAreaCode();
                        if (StrUtil.isNotEmpty(countyCode)) {
                            lineDTO.setCountyCode(countyCode);
                        }
                    }
                    if (!lineDTO.getAddrMaintainType().equals("失效")) {
                    	String objectName = lineDTO.getWorkorderObjectName();
                    	int startIndex = objectName.indexOf(".");
                        int endIndex = objectName.lastIndexOf(".");
                        if (startIndex > 0 && endIndex == lineDTO.getWorkorderObjectName().length() - 4 && endIndex > startIndex) {
                        	StringBuffer sbObjectName = new StringBuffer(objectName);
                        	//去掉第二段地点描述前后空格
                        	lineDTO.setWorkorderObjectName(sbObjectName.replace(startIndex + 1, endIndex,objectName.substring(startIndex + 1,endIndex).trim()).toString());
                        }                        
                    }
                    lineDTO.setLineId(getNextGUID());
                    setDTOParameter(lineDTO);
                    createData();//插入数据到行表
                    lineDTO.setCountyCode(oldCountyCode);
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
     * @return true表示所有数据合法，false表示数据非法或检查过程中出现错误
     */
    private boolean checkAllObject(DTOSet dtoSet) {
        int invalidCount = 0;
        if (dtoSet != null && !dtoSet.isEmpty()) {
            int dataCount = dtoSet.getSize();
            for (int i = 0; i < dataCount; i++) {
                setDTOParameter(dtoSet.getDTO(i));
                if (!isDataValid()) {
                    invalidCount++;
                }
            }
        }
        return (invalidCount == 0);
    }

    /**
     * 功能：检查地点是否合法。主要进行以下校验工作：
     * 1：检查地点编码；
     * 2：检查地点描述；
     * 3：检查区域编码；
     * 4：检查行政区划；
     * 5：检查地市区县；
     * 6：检查地点专业；
     * 7：检查地点编号(仅限于基站编号或营业厅编号)
     *
     * @return true表示所有检查合法，false表示数据非法或检查过程中出现错误
     */
    private boolean isDataValid() {
        boolean isObjectValid = false;
        try {
            if (isMaintainTypeValid()) {
                checkObjectCode();
                checkObjectCodeSelfDuplicate();
                checkAreaCode();
                checkObjectName();
                checkObjectNameSelfDuplicate();
                checkObjectLoc2SelfDuplicate();
                checkAreaType();
                checkCityAndCounty();
                checkObjectCategory();
                checkBTSOrBizHall();
                checkBTSNOSelfDuplicate();
            }
            if (lineDTO.getErrorMessage().length() > 0) {
                logImportError();
            } else {
                isObjectValid = true;
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return isObjectValid;
    }

    /**
     * 功能：检查地点的维护类型是否合法。
     *
     * @return true表示维护类型合法，false表示不合法
     */
    private boolean isMaintainTypeValid() {
        String maintainType = lineDTO.getAddrMaintainType();
        String errorMessage = "";
        if (maintainType == null || maintainType.trim().length() == 0) {
            errorMessage = "未指定地点维护类型，系统不能处理";
        } else {
            if (!(lineDTO.isAddLocation() || lineDTO.isUpdateLocation() || lineDTO.isDisableLocation())) {
                errorMessage = "指定了不支持的地点维护类型，系统不能处理";
            }
        }
        setErrorMessage("维护类型", errorMessage);
        return (errorMessage.length() == 0);
    }

    /**
     * 功能：检查地点编码是否合法。主要完成如下工作：
     * 1：地点代码是否非空；
     * 2：地点代码是否重复(Excel)；
     * 3：地点代码是否与数据库重复；
     * <P>注意：对于内蒙移动有特殊逻辑</P>
     *
     * @throws QueryException 检查数据出错时抛出查询异常
     */
    private void checkObjectCode() throws QueryException {

        boolean checkResult = true;
        String errorMessage = "";
        String provinceCode = SinoConfig.getProvinceCode();
        String locationCode = lineDTO.getWorkorderObjectCode();
        EtsOuCityMapDAO etsOuCityMapDAO = new EtsOuCityMapDAO(userAccount, null, conn);
		String companyCode = etsOuCityMapDAO.getCompanyCode(userAccount.getOrganizationId());
        if (StrUtil.isEmpty(locationCode)) {
            errorMessage = "地点代码不能为空";
            checkResult = false;
        } else if (locationCode.indexOf(" ")!=-1) {
        	errorMessage = "地点代码中不能有空格";
            checkResult = false;
        }
        if (checkResult) {
            int startIndex = locationCode.indexOf(".");
            int endIndex = locationCode.lastIndexOf(".");
            if (startIndex == 6 && endIndex == 21 && locationCode.length() == 25) {
                locationCode = locationCode.substring(0, startIndex);
                if (lineDTO.getCountyCode().length() > 0 || lineDTO.isAddLocation()) {
                    if (!locationCode.equals(lineDTO.getCountyCode())) {
                        if (errorMessage.length() > 0) {
                            errorMessage += ",";
                        }
                        errorMessage += "导入地点代码“"
                                + lineDTO.getWorkorderObjectCode()
                                + "”第一段“"
                                + locationCode
                                + "”与区域代码“"
                                + lineDTO.getCountyCode()
                                + "”不一致";
                        checkResult = false;
                    }
//                    if (!StrUtil.isNumber(lineDTO.getWorkorderObjectCode().substring(13,endIndex))) {
//                    	errorMessage += "导入地点代码“"
//		                            + lineDTO.getWorkorderObjectCode()
//		                            + "”第二段“"
//		                            + lineDTO.getWorkorderObjectCode().substring(7, endIndex)
//		                            + "后八位应该为数字";
//                        checkResult = false;
//                    }
                }
                if (!lineDTO.getWorkorderObjectCode().substring(7, 11).equals(companyCode)) {
                	errorMessage += "导入地点代码“"
                        + lineDTO.getWorkorderObjectCode()
                        + "”第二段“"
                        + lineDTO.getWorkorderObjectCode().substring(7, endIndex)
                        + "”前四位“"
                        + lineDTO.getWorkorderObjectCode().substring(7, 11)
                        + "”与公司代码“"
                        + companyCode
                        + "”不一致";
                	checkResult = false;
                }
            } else {
                if (errorMessage.length() > 0) {
                    errorMessage += ",";
                }
                errorMessage += "导入地点代码“"
                        + lineDTO.getWorkorderObjectCode()
                        + "”格式不正确，必须以区域代码开始，且包含两个点，长度为25个字符";
                checkResult = false;
            }
        }
        if (checkResult) {
        	if (lineDTO.isAddLocation()) {
            	if (isObjectCodeExistInOrder("WORKORDER_OBJECT_CODE")) {
            		if (errorMessage.length() > 0) {
                        errorMessage += ",";
                    }
            		errorMessage += "地点代码“"
                        + lineDTO.getWorkorderObjectCode()
                        + "正在审批过程中";
            	}
        	}
        	
            if (isObjectCodeExist()) { 
            	if (lineDTO.isAddLocation()) {
                    if (provinceCode.equals(DictConstant.PROVINCE_CODE_NM)) {//如果是内蒙
                        if (getObjectCodeOrganizationId() == userAccount.getOrganizationId()) {
                            if (errorMessage.length() > 0) {
                                errorMessage += ",";
                            }
                            errorMessage += "导入地点代码“"
                                    + lineDTO.getWorkorderObjectCode()
                                    + "”在数据库中已存在";
                        }
                    } else {
                        if (errorMessage.length() > 0) {
                            errorMessage += ",";
                        }
                        errorMessage += "导入地点代码“"
                                + lineDTO.getWorkorderObjectCode()
                                + "”在数据库中已存在";
                    }
                } else if (lineDTO.isDisableLocation()) {
                    if (hasAssetsUnderLocation()) {
                        if (errorMessage.length() > 0) {
                            errorMessage += ",";
                        }
                        errorMessage += "代码为“"
                                + lineDTO.getWorkorderObjectCode()
                                + "”的地点下有资产，不能失效。";
                    }
                }
            } else {
                if (lineDTO.isUpdateLocation()) {
                    if (errorMessage.length() > 0) {
                        errorMessage += ",";
                    }
                    errorMessage += "要修改的地点代码“"
                            + lineDTO.getWorkorderObjectCode()
                            + "”不存在";
                } else if (lineDTO.isDisableLocation()) {
                    if (errorMessage.length() > 0) {
                        errorMessage += ",";
                    }
                    errorMessage += "要失效的地点代码“"
                            + lineDTO.getWorkorderObjectCode()
                            + "”不存在";
                }
            }
        }
        setErrorMessage("地点代码", errorMessage);
    }


    /**
     * 功能：检查区域代码是否合法
     *
     * @throws QueryException 检查数据出错时抛出查询异常
     */
    private void checkAreaCode() throws QueryException {
        boolean checkResult = true;
        String errorMessage = "";
        String areaCode = lineDTO.getCountyCode();
        String provinceCode = SinoConfig.getProvinceCode();
        if (lineDTO.isAddLocation()) {
            if (StrUtil.isEmpty(areaCode)) {
                errorMessage += "所属区域不能为空";
                checkResult = false;
            }
        }
        if (areaCode.length() > 0) {
            if (areaCode.length() != 6) {
                if (errorMessage.length() > 0) {
                    errorMessage += ",";
                }
                errorMessage += "所属区域代码“"
                        + areaCode
                        + "”位数不是6位";
                checkResult = false;
            } else if (!StrUtil.isNumber(areaCode)) {
                if (errorMessage.length() > 0) {
                    errorMessage += ",";
                }
                errorMessage += "所属区域代码不是“"
                        + areaCode
                        + "”数字";
                checkResult = false;
            } 
//            else {
//                if (!provinceCode.equals(DictConstant.PROVINCE_CODE_JIN)) {
//                    String preAreaCode = areaCode.substring(0, 4);
//                    if (!preAreaCode.equals(userAccount.getCompanyCode())) {
//                        if (errorMessage.length() > 0) {
//                            errorMessage += ",";
//                        }
//                        errorMessage += "所属区域代码“"
//                                + areaCode
//                                + "”非法，请注意，区域代码必须起始于贵公司代码“"
//                                + userAccount.getCompanyCode()
//                                + "”，你可能导入了其他公司的地点";
//                        checkResult = false;
//                    }
//                }
//            }
            if (checkResult) {
                if (!isAreaCodeExist()) {      //判断所属区域的存在性
                    if (!provinceCode.equals(DictConstant.PROVINCE_CODE_JIN)) {
                        if (errorMessage.length() > 0) {
                            errorMessage += ",";
                        }
                        errorMessage += "所属区域代码“"
                                + areaCode
                                + "”不属于贵公司或不存在";
                    } else {
                        if (errorMessage.length() > 0) {
                            errorMessage += ",";
                        }
                        errorMessage += "所属区域代码“"
                                + areaCode
                                + "”不存在";
                    }
                }
            }
        }
        setErrorMessage("区域代码", errorMessage);
    }

    /**
     * 功能：检查地点描述是否合法。需要完成如下工作：
     * 1：地点描述是否非空；
     * 2：地点描述是否重复(Excel，本逻辑暂未开发)；
     * 3：地点描述是否与数据库重复(本逻辑暂未开发)；
     *
     * @throws QueryException 检查数据出错时抛出查询异常
     */
    private void checkObjectName() throws QueryException {
        boolean checkResult = true;
        String errorMessage = "";
        String objectName = lineDTO.getWorkorderObjectName();
        int startIndex = objectName.indexOf(".");
        int endIndex = objectName.lastIndexOf(".");
        if (!lineDTO.isDisableLocation()) {
            if (StrUtil.isEmpty(objectName)) {
                errorMessage += "地点描述不能为空";
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
            String areaCode = lineDTO.getCountyCode();
            if (lineDTO.isAddLocation() || areaCode.length() > 0) {
                if (startIndex > -1 && endIndex > startIndex) {
                    objectName = objectName.substring(0, startIndex);
                    String location = getFirstSegmentDescByAreaCode();
                    if (!objectName.equals(location)) {
                        if (errorMessage.length() > 0) {
                            errorMessage += ",";
                        }
                        errorMessage += "导入的地点描述“"
                                + lineDTO.getWorkorderObjectName()
                                + "”的第一段“"
                                + objectName
                                + "”与区域代码“"
                                + lineDTO.getCountyCode()
                                + "”对应的描述“"
                                + location
                                + "”不一致";
                        checkResult = false;
                    }
                }
            }
            if (checkResult) {
//                if (startIndex > 0 && endIndex == lineDTO.getWorkorderObjectName().length() - 3 && endIndex > startIndex) {
            	if (startIndex > 0 && endIndex > startIndex) {
                    String city = lineDTO.getCity();
                    city += lineDTO.getCounty();
                    objectName = lineDTO.getWorkorderObjectName().substring(startIndex + 1);
                    boolean needCheck = lineDTO.isAddLocation();//如果是新增
                    if (!needCheck) {
                        needCheck = city.length() > 0;
                    }
                    if (needCheck) {
                        if (!objectName.startsWith(city)) {
                            if (errorMessage.length() > 0) {
                                errorMessage += ",";
                            }
                            errorMessage += "地点描述“"
                                    + lineDTO.getWorkorderObjectName()
                                    + "”与地市“"
                                    + lineDTO.getCity()
                                    + "”区县“"
                                    + lineDTO.getCounty()
                                    + "”名称不一致";
                            checkResult = false;
                        } else if (isObjectCodeExistInOrder("WORKORDER_OBJECT_NAME")) {
                    		if (errorMessage.length() > 0) {
                                errorMessage += ",";
                            }
                    		errorMessage += "地点描述“"
                                + lineDTO.getWorkorderObjectName()
                                + "“正在审批过程中";
                    	}
                    }
                } else {
                    if (errorMessage.length() > 0) {
                        errorMessage += ",";
                    }
                    errorMessage += "地点描述“"
                            + lineDTO.getWorkorderObjectName()
                            + "”不规范";
                    checkResult = false;
                }
            }
            if (checkResult) {
                String locCode = getLocCode2ByLocDesc();
                String locDesc = getLocDesc2ByLocCode();
                objectName = lineDTO.getWorkorderObjectName();
                objectName = objectName.substring(startIndex + 1, endIndex);
                String locationCode = lineDTO.getWorkorderObjectCode();
                startIndex = locationCode.indexOf(".");
                endIndex = locationCode.lastIndexOf(".");
                locationCode = locationCode.substring(startIndex + 1, endIndex);
                if (locCode.length() > 0) {
                	if (!locCode.equals(locationCode)) {
                        if (errorMessage.length() > 0) {
                            errorMessage += ",";
                        }
                        errorMessage += "地点描述“"
                                + lineDTO.getWorkorderObjectName()
                                + "”的第二段“"
                                + objectName
                                + "”在系统中对应的编码为“"
                                + locCode
                                + "”,与你导入的编码“"
                                + lineDTO.getWorkorderObjectCode()
                                + "”的第二段“"
                                + locationCode
                                + "”不一致";
                    }
                }
                if (locDesc.length() > 0 && lineDTO.isAddLocation()) {
                	if(!locDesc.equals(objectName)) {
                   	 if (errorMessage.length() > 0) {
                            errorMessage += ",";
                        }
                        errorMessage += "地点代码“"
                                + lineDTO.getWorkorderObjectCode()
                                + "”的第二段“"
                                + locationCode
                                + "”在系统中对应的描述为“"
                                + locDesc
                                + "”,与你导入的描述“"
                                + lineDTO.getWorkorderObjectName()
                                + "”的第二段“"
                                + objectName
                                + "”不一致";
                   } 
                }
            }
        }
        setErrorMessage("地点描述", errorMessage);
    }

    /**
     * 功能：校验导入的数据中，地点描述是否重复
     */
    private void checkObjectNameSelfDuplicate() {
        if (lineDTO.getWorkorderObjectName().length() > 0) {
            int dataCount = dataLines.getSize();
            String errorMessage = "";
            String duplicateLine = "";
            for (int i = 0; i < dataCount; i++) {
                EamAddressAddLDTO dto = (EamAddressAddLDTO) dataLines.getDTO(i);
                if (lineDTO.getLineId().equals(dto.getLineId())) {
                    continue;
                }
                if (lineDTO.getWorkorderObjectName().equals(dto.getWorkorderObjectName())) {
                    duplicateLine += String.valueOf(i + 1) + ", ";
                }
            }
            if (duplicateLine.length() > 0) {
                if (errorMessage.length() > 0) {
                    errorMessage += ",";
                }
                errorMessage = "地点描述“"
                        + lineDTO.getWorkorderObjectName()
                        + "”与第"
                        + duplicateLine.substring(0, duplicateLine.length() - 2)
                        + "行地点描述重复";
                setErrorMessage("地点描述", errorMessage);
            }
        }
    }
    
    /**
     * 功能:校验物理地点代码相同，描述不相同、物理地点描述相同代码不同
     */
    private void checkObjectLoc2SelfDuplicate() {
    	if (!lineDTO.isDisableLocation() && lineDTO.getWorkorderObjectCode().length() > 0 && lineDTO.getWorkorderObjectName().length() > 0) {
    		String objectCode = lineDTO.getWorkorderObjectCode();
    		String objectName = lineDTO.getWorkorderObjectName();
            int codeStartIndex = objectCode.indexOf(".");
            int codeEndIndex = objectCode.lastIndexOf(".");
    		int nameStartIndex = objectName.indexOf(".");
            int nameEndIndex = objectName.lastIndexOf(".");
            String loc2Code = objectCode.substring(7, codeEndIndex);
            String loc2Name = objectName.substring(nameStartIndex + 1, nameEndIndex);
            int dataCount = dataLines.getSize();
            String errorMessage = "";
            String codeDuplicateLine = "";
            String nameDuplicateLine = "";
            
        	if (codeStartIndex == 6 && codeEndIndex == 21 && objectCode.length() == 25) {
        		for (int i = 0; i < dataCount; i++) {
                    EamAddressAddLDTO dto = (EamAddressAddLDTO) dataLines.getDTO(i);
                    if (lineDTO.getLineId().equals(dto.getLineId())) {
                        continue;
                    }
                    if (StrUtil.isNotEmpty(dto.getWorkorderObjectCode())) {
                    	if (loc2Code.equals(dto.getWorkorderObjectCode().substring(7,codeEndIndex)) && 
                        		!loc2Name.equals(dto.getWorkorderObjectName().substring(dto.getWorkorderObjectName().indexOf(".") + 1, dto.getWorkorderObjectName().lastIndexOf(".")))) {
                        	codeDuplicateLine += String.valueOf(i + 1) + ", ";
                        }
                        if (!loc2Code.equals(dto.getWorkorderObjectCode().substring(7,codeEndIndex)) && 
                        		loc2Name.equals(dto.getWorkorderObjectName().substring(dto.getWorkorderObjectName().indexOf(".") + 1, dto.getWorkorderObjectName().lastIndexOf(".")))) {
                        	nameDuplicateLine += String.valueOf(i + 1) + ", ";
                        }
                    }
                }        		
        	}
            
            if (codeDuplicateLine.length() > 0) {
                if (errorMessage.length() > 0) {
                    errorMessage += ",";
                }
                errorMessage = "物理地点代码“"
                        + loc2Code
                        + "”对应的物理地点描述与第"
                        + codeDuplicateLine.substring(0, codeDuplicateLine.length() - 2)
                        + "行物理地点代码对应的物理地点描述不相同";
                setErrorMessage("地点描述", errorMessage);
            }
            
            if (nameDuplicateLine.length() > 0) {
                if (errorMessage.length() > 0) {
                    errorMessage += ",";
                }
                errorMessage = "物理地点描述“"
                        + loc2Name
                        + "”对应的物理地点代码与第"
                        + nameDuplicateLine.substring(0, nameDuplicateLine.length() - 2)
                        + "行物理地点描述对应的物理地点代码不相同";
                setErrorMessage("地点描述", errorMessage);
            }
    		
    	}
    }


    /**
     * 功能：检查行政区划是否合法
     *
     * @throws QueryException 检查数据出错时抛出查询异常
     */
    private void checkAreaType() throws QueryException {
        String errorMessage = "";
        if (lineDTO.isAddLocation()) {
            if (StrUtil.isEmpty(lineDTO.getAreaType())) {
                errorMessage = "行政区划不能为空";
            }
        }
        if (!StrUtil.isEmpty(lineDTO.getAreaType())) {
            if (!isAreaTypeExist()) {
                if (errorMessage.length() > 0) {
                    errorMessage += ",";
                }
                errorMessage = "行政区划“"
                        + lineDTO.getAreaType()
                        + "”不存在";
            }
        }
        setErrorMessage("行政区划", errorMessage);
    }

    /**
     * 功能：检查地市区县是否合法
     *
     * @throws QueryException 检查数据出错时抛出查询异常
     */
    private void checkCityAndCounty() throws QueryException {

        String errorMessage = "";
        String city = lineDTO.getCity();
        String county = lineDTO.getCounty();
        if (lineDTO.isAddLocation()) {
            if (StrUtil.isEmpty(city)) {
                errorMessage = "地市不能为空";
            }
            if (StrUtil.isEmpty(county)) {
                if (errorMessage.length() > 0) {
                    errorMessage += ",";
                }
                errorMessage += "区县不能为空";
            }
        }
        if (city.length() > 0) {
            if (!isCityExist()) {
                if (errorMessage.length() > 0) {
                    errorMessage += ",";
                }
                errorMessage += "地市“" + city + "”不存在";
            }
        }
        if (county.length() > 0) {
            if (!isCountyExist()) {
                if (errorMessage.length() > 0) {
                    errorMessage += ",";
                }
                errorMessage += "区县“" + county + "”不存在，或者不在地市“" + city + "”下";
            }
        }
        setErrorMessage("地市与区县", errorMessage);
    }

    /**
     * 功能：检查地点专业是否合法
     */
    private void checkObjectCategory() {
        String errorMessage = "";
        String objectCategory = lineDTO.getObjectCategory();
        if (lineDTO.isAddLocation()) {
            if (StrUtil.isEmpty(objectCategory)) {
                errorMessage = "专业代码不能为空";
            }
        }
        if (StrUtil.isNotEmpty(objectCategory)) {
            if (objectCategory.length() != 2) {
                if (errorMessage.length() > 0) {
                    errorMessage += ",";
                }
                errorMessage += "专业代码“"
                        + lineDTO.getObjectCategory()
                        + "”位数不正确";
            }
            if (lineDTO.getWorkorderObjectCode().indexOf(lineDTO.getObjectCategory()) != 11) { //验证专业代码是否在地点代码中存在
                if (errorMessage.length() > 0) {
                    errorMessage += ",";
                }
                String objectCategory2 = lineDTO.getWorkorderObjectCode();
                if(objectCategory2.length() > 13){
                    objectCategory2 = objectCategory2.substring(11, 13);
                    errorMessage += "专业代码“"
                            + objectCategory
                            + "”与地点代码“"
                            + lineDTO.getWorkorderObjectCode()
                            + "”中的专业代码“"
                            + objectCategory2
                            + "”不匹配";
                }
            }
            if (!isObjectCategoryExist()) {      //判断地点代码的存在性
                if (errorMessage.length() > 0) {
                    errorMessage += ",";
                }
                errorMessage += "专业代码“"
                        + objectCategory
                        + "”不存在";
            }
        }
        setErrorMessage("地点专业", errorMessage);
    }

    /**
     * 功能：检查基站编号或营业厅编号是否合法
     *
     * @throws QueryException 检查基站或营业厅编号出错时抛出查询异常
     */
    private void checkBTSOrBizHall() throws QueryException {
        String errorMessage = "";
        String objectCategory = lineDTO.getObjectCategory();
        if (objectCategory.equalsIgnoreCase("JZ") || objectCategory.equalsIgnoreCase("YY")) {
            String btsNo = lineDTO.getBtsNo();
            if (!btsNo.equals("")) {
                String objectCode = getObjectCodeByBTSNo();//获取基站编号对应的地点第二段编码；
                if (objectCode.length() > 0) {
                    String locationCode = lineDTO.getWorkorderObjectCode();
                    int startIndex = locationCode.indexOf(".");
                    int endIndex = locationCode.lastIndexOf(".");
                    if (startIndex == 6 && endIndex == 21 && locationCode.length() == 25) {
                        locationCode = locationCode.substring(startIndex + 1, endIndex);
                        if (!objectCode.equals(locationCode)) {
                            if (errorMessage.length() > 0) {
                                errorMessage += ",";
                            }
                            errorMessage = "基站编号“"
                                    + btsNo
                                    + "”在数据库中对应的地点编码第二段是“"
                                    + objectCode
                                    + "”，与你导入的地点编码“"
                                    + lineDTO.getWorkorderObjectCode()
                                    + "”的第二段“"
                                    + locationCode
                                    + "”不符。";
                        }
                    }
                }
            } else {//Excel未输入基站编号
                if (lineDTO.isAddLocation()) {
                    if (objectCategory.equalsIgnoreCase("JZ")) {
                        if (errorMessage.length() > 0) {
                            errorMessage += ",";
                        }
                        errorMessage = "基站编号不能为空";
                    } else {
                        if (errorMessage.length() > 0) {
                            errorMessage += ",";
                        }
                        errorMessage = "营业厅编号不能为空";
                    }
                }
            }
            setErrorMessage("基站或营业厅编号", errorMessage);
        }
    }

    /**
     * 功能：校验导入的数据中，基站代码是否重复
     */
    private void checkBTSNOSelfDuplicate() {
    	String objectCategory = lineDTO.getObjectCategory();
    	if (objectCategory.equalsIgnoreCase("JZ") || objectCategory.equalsIgnoreCase("YY")) {
	        if (lineDTO.getBtsNo().length() > 0) {
	            int dataCount = dataLines.getSize();
	            String errorMessage = "";
	            String duplicateLine = "";
	            for (int i = 0; i < dataCount; i++) {
	                EamAddressAddLDTO dto = (EamAddressAddLDTO) dataLines.getDTO(i);
	                if (lineDTO.getLineId().equals(dto.getLineId())) {
	                    continue;
	                }
	                if (lineDTO.getBtsNo().equals(dto.getBtsNo())) {
	                    duplicateLine += String.valueOf(i + 1) + ", ";
	                }
	            }
	            if (duplicateLine.length() > 0) {
	                if (errorMessage.length() > 0) {
	                    errorMessage += ",";
	                }
	                String btsStr = "营业厅";
	                if (objectCategory.equals("JZ")) {
	                	btsStr = "基站";
	                } 	                
	                errorMessage = btsStr + "编号“"
	                        + lineDTO.getBtsNo()
	                        + "”与第"
	                        + duplicateLine.substring(0, duplicateLine.length() - 2)
	                        + "行" + btsStr + "编号重复";
	                setErrorMessage(btsStr + "编号", errorMessage);
	            }
	        }
    	}
    }


    /**
     * 功能：根据区域代码获取成本中心表序列号
     *
     * @return 成本中心表序列号
     * @throws QueryException 抛出查询异常
     */
    private String getCountyCodeByAreaCode() throws QueryException {
        String countyCode = "";
        try {
            SQLModel sqlModel = modelProducer.getCountyCodeByAreaCodeModel();
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
     * 功能：校验在ETS_OBJECT是否存在objectCode
     *
     * @return ture表示存在，false表示不存在。
     */
    private boolean isObjectCodeExist() {
        boolean hasBarcode = false;
        try {
            SQLModel sqlModel = modelProducer.getObjectCodeExistModel();
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
     * 功能：校验在ETS_OBJECT是否存在objectCode
     *
     * @return ture表示存在，false表示不存在。
     */
    private boolean isObjectCodeExistInOrder(String str) {
        boolean hasBarcode = false;
        try {
            SQLModel sqlModel = modelProducer.getObjectCodeExistInOrderModel(str);
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
     * 功能:检查基站或营业厅编号是否存在
     *
     * @return String 返回BTS编号在系统中对应的地点编码
     * @throws QueryException 获取地点编码出错时抛出该异常
     */
    private String getObjectCodeByBTSNo() throws QueryException {
        String objectCode = "";
        try {
            SQLModel sqlModel = modelProducer.getObjectCodeByBTSNoModel();
            simpleQuery.setSql(sqlModel);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                Row row = simpleQuery.getFirstRow();
                objectCode = row.getStrValue("LOC2_CODE");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return objectCode;
    }

    /**
     * 功能：检查地市是否存在
     *
     * @return boolean trueb表示存在，false表示不存在
     * @throws QueryException 检查数据出错时抛出查询异常
     */
    private boolean isCityExist() throws QueryException {
        SQLModel sqlModel = modelProducer.getCityExistModel();
        simpleQuery.setSql(sqlModel);
        simpleQuery.executeQuery();
        return simpleQuery.hasResult();
    }

    /**
     * 功能：检查区县是否存在
     *
     * @return boolean trueb表示存在，false表示不存在
     * @throws QueryException 检查数据出错时抛出查询异常
     */
    private boolean isCountyExist() throws QueryException {
        SQLModel sqlModel = modelProducer.getCountyExistModel();
        simpleQuery.setSql(sqlModel);
        simpleQuery.executeQuery();
        return simpleQuery.hasResult();
    }


    /**
     * 功能：校验在ETS_OBJECT 的地点代码是否重复
     */
    private void checkObjectCodeSelfDuplicate() {
        if (lineDTO.getWorkorderObjectCode().length() > 0) {
            int dataCount = dataLines.getSize();
            String errorMessage = "";
            String duplicateLine = "";
            for (int i = 0; i < dataCount; i++) {
                EamAddressAddLDTO dto = (EamAddressAddLDTO) dataLines.getDTO(i);
                if (lineDTO.getLineId().equals(dto.getLineId())) {
                    continue;
                }
                if (lineDTO.getWorkorderObjectCode().equals(dto.getWorkorderObjectCode())) {
                    duplicateLine += String.valueOf(i + 1) + ", ";
                }
            }
            if (duplicateLine.length() > 0) {
                if (errorMessage.length() > 0) {
                    errorMessage += ",";
                }
                errorMessage = "地点代码与第"
                        + duplicateLine.substring(0, duplicateLine.length() - 2)
                        + "行地点代码重复";
                setErrorMessage("地点代码", errorMessage);
            }
        }
    }


    /**
     * 功能：校验在ETS_OBJECT是否存在objectCode
     *
     * @return ture表示存在，false表示不存在。
     */
    private int getObjectCodeOrganizationId() {
        int existCount = 0;
        try {
            SQLModel sqlModel = modelProducer.getObjectCodeOrganizationIdModel();
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
     * @return true表示存在，false表示不存在
     */
    private boolean isObjectCategoryExist() {
        boolean hasBarcode = false;
        try {
            SQLModel sqlModel = modelProducer.getObjectCategoryExistModel();
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
     * 功能：校验在AMS_OBJECT_IMPORT是否存在AreaType
     *
     * @return 　ture表示表存在，false表示不存在。
     */
    private boolean isAreaTypeExist() throws QueryException {
        boolean hasAreaType = true;
        SQLModel sqlModel = modelProducer.getAreaTypeExistModel();
        simpleQuery.setSql(sqlModel);
        simpleQuery.executeQuery();
        if (!simpleQuery.hasResult()) {
            hasAreaType = false;
        }
        return hasAreaType;
    }

    /**
     * 功能：检查导入的区域代码是否存在。
     *
     * @return ture 表示导入正确(存在ou)，false 表示导入有错。
     * @throws QueryException 检查导入的区域代码是否存在出错时抛出查询异常
     */
    private boolean isAreaCodeExist() throws QueryException {
        SQLModel sqlModel = modelProducer.getAreaCodeExistModel();
        simpleQuery.setSql(sqlModel);
        simpleQuery.executeQuery();
        return simpleQuery.hasResult();
    }

    /**
     * 功能：校验导入的地点描述的第一段是否与导入的所属区域对应的描述一致
     *
     * @return true表示地点合法，false表示地点非法
     * @throws QueryException
     */
    private String getFirstSegmentDescByAreaCode() throws QueryException {
        String location = "";
        try {
            SQLModel sqlModel = modelProducer.getAreaCodeExistModel();
            simpleQuery.setSql(sqlModel);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                Row row = simpleQuery.getFirstRow();
                location = row.getStrValue("DESCRIPTION");
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        }
        return location;
    }

    private String getLocCode2ByLocDesc() throws QueryException {
        String locCode2 = "";
        try {
            SQLModel sqlModel = modelProducer.getLocCode2ByDescModel();
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

    private String getLocDesc2ByLocCode() throws QueryException {
        String locDesc2 = "";
        try {
            SQLModel sqlModel = modelProducer.getLocDesc2ByDescModel();
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
     * 功能：记录特定行校验的非法错误信息
     *
     * @throws DataHandleException 记录校验信息出错时抛出该异常
     */
    private void logImportError() throws DataHandleException {
        SQLModel sqlModel = modelProducer.getImportErrorLogModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：判断地点下是否有资产
     *
     * @return true表示有资产，false表示无资产
     * @throws QueryException 检查地点下是否有资产出错时抛出该异常
     */
    private boolean hasAssetsUnderLocation() throws QueryException {
        SQLModel sqlModel = modelProducer.getLocationAssetsModel();
        simpleQuery.setSql(sqlModel);
        simpleQuery.executeQuery();
        return simpleQuery.hasResult();
    }

    private void setErrorMessage(String errorCode, String errorMessage) {
        if (errorMessage.length() > 0) {
            String message = lineDTO.getErrorMessage();
            if (message.length() > 0) {
                message += "<br>";
            }
            lineDTO.setErrorMessage(message + errorCode + "错误：" + errorMessage);
        }
    }
}
