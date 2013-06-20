package com.sino.ams.system.object.dao;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.object.AmsObjectAddressDTO;
import com.sino.ams.system.object.model.CommonObjectModel;
import com.sino.ams.system.user.dao.EtsOuCityMapDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.datatrans.CustomTransData;
import com.sino.base.db.datatrans.DataRange;
import com.sino.base.db.datatrans.DataTransfer;
import com.sino.base.db.datatrans.TransRule;
import com.sino.base.db.datatrans.TransferFactory;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class CommonObjectDAO extends AMSBaseDAO {
	public CommonObjectDAO(BaseUserDTO userAccount, EtsObjectDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		commonObjectModel = new CommonObjectModel((SfUserDTO) userAccount, dtoParameter);
	}
	

	private CommonObjectModel commonObjectModel = null;

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		EtsObjectDTO dto = (EtsObjectDTO)dtoParameter;
		sqlProducer = new CommonObjectModel(user, dto);
	}

	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws com.sino.base.exception.WebFileDownException
	 */
	public File getExportFile() throws WebFileDownException {
		File file = null;
		try {
			CommonObjectModel modelProducer = (CommonObjectModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "地点数据";
			String fileName = reportTitle + ".xls";
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setSourceConn(conn);
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);
			Map fieldMap = getFieldMap();
			rule.setFieldMap(fieldMap);
			CustomTransData custData = new CustomTransData();
			custData.setReportTitle(reportTitle);
			custData.setReportPerson(userAccount.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			rule.setCalPattern(LINE_PATTERN);
			TransferFactory factory = new TransferFactory();
			DataTransfer transfer = factory.getTransfer(rule);
			transfer.transData();
			file = (File) transfer.getTransResult();
		} catch (DataTransException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		}
		return file;
	}

	public void insertToData(String[] workorderObjectNos) { //执行同步至TDEAM
        EtsObjectDTO dtoPara = (EtsObjectDTO) dtoParameter;
        try {
            CommonObjectModel etsObjectModel = (CommonObjectModel) sqlProducer;
            SQLModel sqlModel = etsObjectModel.getInsertToModel(workorderObjectNos);
            DBOperator.updateRecord(sqlModel, conn);
            if (userAccount.getIsTd().equals("Y")) {
                syntoEAMLocus();     //TDEAM同步至EAM
            } else {
                syntoTDLocus();          //EAM同步至TDEAM
            }


            prodMessage(CustMessageKey.ENABLE_SUCCESS);
            getMessage().addParameterValue(dtoPara.getCategoryName());
        } catch (DataHandleException ex) {
            prodMessage(CustMessageKey.ENABLE_FAILURE);
            getMessage().addParameterValue(dtoPara.getCategoryName());
            ex.printLog();
        }
    }
	
	public void transferToData(String[] workorderObjectNos) { //执行地点传送
		boolean operateResult = false;
        boolean autoCommit = true;
        int count = 0;
		EtsObjectDTO dtoPara = (EtsObjectDTO) dtoParameter;
        try {
            CommonObjectModel etsObjectModel = (CommonObjectModel) sqlProducer;            
            List<SQLModel> sqlModleList=new ArrayList<SQLModel>();
            int organizationId = getMatchOrgId(userAccount.getOrganizationId());
            String isTd = "Y";
            if (userAccount.getIsTd().equals("Y")) {
            	isTd = "N";
            }
            dtoPara.setOrganizationId(organizationId);
            dtoPara.setIsTd(isTd);
            
            for (int i=0;i<workorderObjectNos.length;i++) {
            	dtoPara.setWorkorderObjectNo(workorderObjectNos[i]);
            	if (!existMatchObjectCode()) {            		
            		sqlModleList.add(etsObjectModel.getTransferToModel());
            		count++;
            	}
            }
            operateResult=DBOperator.updateBatchRecords(sqlModleList, conn);
            
        } catch (DataHandleException ex) {
            ex.printLog();
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (operateResult && count!=0) {
                	prodMessage(CustMessageKey.TRANSFER_SUCCESS);
                    conn.commit();
                } else {
                	prodMessage(CustMessageKey.TRANSFER_FAILURE);
                    getMessage().addParameterValue("可能已传送过");
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
    }
	
	public void syntoTDLocus() {
        CallableStatement cs = null;
        String callStr = "{CALL dbo.ESOP_SYN_LOCATION_TOTD ?}";
        try {
            cs = conn.prepareCall(callStr);
            cs.setInt(1, userAccount.getOrganizationId());
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBStatement(cs);
        }
    }

    public void syntoEAMLocus() {
        CallableStatement cs = null;
        String callStr = "{CALL dbo.ESOP_SYN_LOCATION_TOEAM ?}";
        try {
            cs = conn.prepareCall(callStr);
            cs.setInt(1, userAccount.getOrganizationId());
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBStatement(cs);
        }
    }
    
    public String getNextWorkorderObjectCode(String provinceCode) throws QueryException {
        String maxObjectCode = "";
        try {
            CommonObjectModel modelProducer = (CommonObjectModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getNextObjectCodeModel(provinceCode);
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
    
	private Map getFieldMap(){
		Map fieldMap = new HashMap();
		fieldMap.put("COMPANY", "公司名称");
		fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");
		fieldMap.put("WORKORDER_OBJECT_NAME", "地点名称");
		fieldMap.put("COUNTY_NAME", "所属区县");
		fieldMap.put("OBJECT_CATEGORY_NAME", "地点专业");
		fieldMap.put("IS_TD", "是否TD地点");
		fieldMap.put("CREATION_USER", "创建人");
		fieldMap.put("CREATION_DATE", "创建日期");
		fieldMap.put("DISABLE_DATE", "失效日期");
		fieldMap.put("UPDATED_USER", "上次更新人");
		fieldMap.put("VALUE", "行政区划");
		return fieldMap;
	}

	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws com.sino.base.exception.WebFileDownException
	 */
	public File getExportFile2() throws WebFileDownException {
		File file = null;
		try {
			CommonObjectModel modelProducer = (CommonObjectModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "地点签统计报表";
			String fileName = reportTitle + ".xls";
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setSourceConn(conn);
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);
			Map fieldMap= getFieldMap2();
			rule.setFieldMap(fieldMap);
			CustomTransData custData = new CustomTransData();
			custData.setReportTitle(reportTitle);
			custData.setReportPerson(userAccount.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			rule.setCalPattern(LINE_PATTERN);
			TransferFactory factory = new TransferFactory();
			DataTransfer transfer = factory.getTransfer(rule);
			transfer.transData();
			file = (File) transfer.getTransResult();
		} catch (DataTransException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		}
		return file;
	}


	private Map getFieldMap2(){
		Map fieldMap = new HashMap();
		
		 fieldMap.put("WORKORDER_OBJECT_NO", "编号");
         fieldMap.put("WORKORDER_OBJECT_CODE", "地址代码");
         fieldMap.put("WORKORDER_OBJECT_NAME", "地点名称");
         fieldMap.put("B", "所属区县");
         fieldMap.put("C", "地点代码");
         fieldMap.put("D", "创建日期");
         fieldMap.put("E", "行政区划");
         fieldMap.put("F", "地点描述");
         fieldMap.put("COMPANY", "公司名称");
		return fieldMap;
	}
	/**
	 * 功能:检查地点是否存在
	 * @return boolean
	 */
	public boolean existObject(){
		boolean exist = false;
		try {
			CommonObjectModel modelProducer = (CommonObjectModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getObjectEsistModel();
			SimpleQuery simQuery = new SimpleQuery(sqlModel, conn);
			simQuery.executeQuery();
			exist = simQuery.hasResult();
		} catch (QueryException ex) {
			ex.printLog();
		}
		return exist;
	}
	
	/**
	 * 功能:检查基站或营业厅编号是否存在
	 * @return boolean
	 */
	public boolean existBtsNo(){
		boolean exist = false;
		try {
			CommonObjectModel modelProducer = (CommonObjectModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getBtsNoEsistModel();
			SimpleQuery simQuery = new SimpleQuery(sqlModel, conn);
			simQuery.executeQuery();
			exist = simQuery.hasResult();
		} catch (QueryException ex) {
			ex.printLog();
		}
		return exist;
	}
	
	/**
	 * 功能：检查地点名称是否存在
	 * @return
	 */
	public boolean existWorkorderObjectName(){
		boolean exist = false;
		try {
			CommonObjectModel modelProducer = (CommonObjectModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getWorkorderObjectNameEsistModel();
			SimpleQuery simQuery = new SimpleQuery(sqlModel, conn);
			simQuery.executeQuery();
			exist = simQuery.hasResult();
		} catch (QueryException ex) {
			ex.printLog();
		}
		return exist;
	}

	/**
	 * 功能:保存地点
	 * @return boolean
	 * @throws ContainerException 
	 * @throws QueryException 
	 * @throws SQLModelException 
	 */
	public boolean saveObject() throws SQLModelException, QueryException, ContainerException {
		boolean operateResult = false;
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		String objectNo = dto.getWorkorderObjectNo();
		try {
			if (objectNo.equals("")) {
				createObject();
			} else {				
				super.updateData();
				
				//修改物理地点
				DBOperator.updateRecord(commonObjectModel.updateEtsObjectLocInfo(), conn);
				//修改ETS_OBJECT所有符合要求的地点
				DBOperator.updateRecord(commonObjectModel.updateEtsObjectInfo(), conn);				
			}
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} finally{
			if(operateResult){
				if(objectNo.equals("")){
					prodMessage(CustMessageKey.OBJECT_CREATE_SUCCESS);
				} else {
					prodMessage(CustMessageKey.OBJECT_UPDATE_SUCCESS);
				}
			} else {
				if(objectNo.equals("")){
					prodMessage(CustMessageKey.OBJECT_CREATE_FAILURE);
				} else {
					prodMessage(CustMessageKey.OBJECT_UPDATE_FAILURE);
				}
			}
		}
		return operateResult;
	}

	/**
	 * 功能：创建地点
	 * @throws DataHandleException
	 * @throws ContainerException 
	 * @throws QueryException 
	 * @throws SQLModelException 
	 * @throws ContainerException 
	 * @throws QueryException 
	 * @throws SQLModelException 
	 */
	private void createObject() throws DataHandleException, SQLModelException, QueryException, ContainerException{
		boolean operateResult = false;
		boolean autoCommit = true;
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			SeqProducer seqProducer = new SeqProducer(conn);
			String objectNo = seqProducer.getGUID();
			dto.setRemark("地点统一维护程序创建");
			dto.setWorkorderObjectNo(objectNo);
			
			String countyCode = getAreaCountyCode(dto.getCountyCode());
        	if (StrUtil.isNotEmpty(countyCode)) {
        		dto.setCountyCode(countyCode);
        	}  
			
			setDTOParameter(dto);
			createData();
			
			//如果是新增地点则记录ETS_OBJECT_LOC2表
			//if (!userAccount.getIsTt().equals("Y")) {
				if (StrUtil.isEmpty(dto.getLoc2Code()) && !getLoc2CodeIsExists(dto.getWorkorderObjectCode())) {
					CommonObjectModel modelProducer = (CommonObjectModel) sqlProducer;
					EtsOuCityMapDAO etsOuCityMapDAO = new EtsOuCityMapDAO(userAccount, null, conn);
					dto.setCompanyCode(etsOuCityMapDAO.getCompanyCodeByOrgId(dto.getOrganizationId()));
					SQLModel sqlModel = modelProducer.createDoEtsObjectLoc(dto);
					DBOperator.updateRecord(sqlModel, conn);
				}
			//}
			
			AmsObjectAddressDTO dtoAddress = new AmsObjectAddressDTO();
			dtoAddress.setObjectNo(dto.getWorkorderObjectNo());
			dtoAddress.setBoxNo("0000");
			dtoAddress.setNetUnit("0000");
			dtoAddress.setAddressNo(dto.getWorkorderObjectNo() + ".0000.0000");
			dtoAddress.setRemark("地点统一维护程序创建");
			AmsObjectAddressDAO objectDAO = new AmsObjectAddressDAO(userAccount, dtoAddress, conn);
			objectDAO.createData();
			operateResult = true;
		} catch (SQLException ex) {
			Logger.logError(ex);
		} finally{
			try {
				if (operateResult) {
					conn.commit();
				} else {
					conn.rollback();
					dto.setWorkorderObjectNo("");
				}
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex) {
				Logger.logError(ex);
				throw new DataHandleException(ex);
			}
		}
	}

	/**
	 * 功能：失效地点
	 * @param objectNos String[]
	 * @return boolean
	 */
	public boolean disableObjects(String[] objectNos){
		boolean operateResult = false;
		boolean autoCommit = true;
		try {
			if (objectNos != null && objectNos.length > 0) {
				autoCommit = conn.getAutoCommit();
				conn.setAutoCommit(false);
				int objectCount = objectNos.length;
				EtsObjectDTO dto = null;
				CommonObjectModel modelProducer = (CommonObjectModel) sqlProducer;
				SQLModel sqlModel = null;
				for (int i = 0; i < objectCount; i++) {
					dto = new EtsObjectDTO();
					dto.setWorkorderObjectNo(objectNos[i]);
					setDTOParameter(dto);
					sqlModel = modelProducer.getDisableModel();
					DBOperator.updateRecord(sqlModel, conn);
				}
				operateResult = true;
			} else {
				operateResult = true;
			}
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} finally{
			try {
				if (operateResult) {
					conn.commit();
					prodMessage(CustMessageKey.OBJECT_DISABLE_SUCCESS);
				} else {
					conn.rollback();
					prodMessage(CustMessageKey.OBJECT_DISABLE_FAILURE);
				}
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
			}
		}
		return operateResult;
	}

	public boolean enableObjects(String[] objectNos){
		boolean operateResult = false;
		boolean autoCommit = true;
		try {
			if (objectNos != null && objectNos.length > 0) {
				autoCommit = conn.getAutoCommit();
				conn.setAutoCommit(false);
				int objectCount = objectNos.length;
				EtsObjectDTO dto = null;
				CommonObjectModel modelProducer = (CommonObjectModel) sqlProducer;
				SQLModel sqlModel = null;
				for (int i = 0; i < objectCount; i++) {
					dto = new EtsObjectDTO();
					dto.setWorkorderObjectNo(objectNos[i]);
					setDTOParameter(dto);
					sqlModel = modelProducer.getEnableModel();
					DBOperator.updateRecord(sqlModel, conn);
				}
				operateResult = true;
			} else {
				operateResult = true;
			}
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (SQLException ex) {
			Logger.logError(ex);
		} finally{
			try {
				if (operateResult) {
					conn.commit();
					prodMessage(CustMessageKey.OBJECT_ENABLE_SUCCESS);
				} else {
					conn.rollback();
					prodMessage(CustMessageKey.OBJECT_ENABLE_FAILURE);
				}
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
			}
		}
		return operateResult;
	}
	
	public String getAddress(String addressName) throws QueryException, ContainerException{
		SQLModel sqlModel = commonObjectModel.getAddress(addressName);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rows = simpleQuery.getSearchResult();
        Row row = null;
        
        StringBuffer result = new StringBuffer();
        if (rows != null && rows.getSize() > 0) {
        	result.append("<div id=\"search_suggest\"><font color = '#blue'>&nbsp;&nbsp;如下是已添加的地点名称，请确认是否新增该地点！</font>");
        	for (int i = 0; i < rows.getSize(); i++) {
        		row = rows.getRow(i);
        		result.append("<div onmouseover=\"javascript:suggestOver(this);\" style = 'width:100%'");
        		result.append(" onmouseout=\"javascript:suggestOut(this);\" ");
        		result.append(" onclick=\"javascript:setSearch(this.innerHTML);\" ");
        		result.append(" class=\"suggest_link\">" + row.getValue("WORKORDER_OBJECT_NAME") + "</div>");
        	}
        	result.append("<div align = 'right'><a href=''  onclick = 'do_Cancle();'><font color='blue' size='3'>关闭</font></a></div>");
        	result.append("</div>");
        }
        return result.toString();
	}
	
	private String getIsTD() throws QueryException {
        String isTd = "";
        try {
            CommonObjectModel modelProducer = (CommonObjectModel) sqlProducer;
            SQLModel sqlModel = modelProducer.geIsTDByOrgIdModel();
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                Row row = simpleQuery.getFirstRow();
                isTd = row.getStrValue("IS_TD");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return isTd;
    }
	
	public String getOrgChangeResponse() throws QueryException{
        String resContent = "";
        EtsObjectDTO dto = (EtsObjectDTO)dtoParameter;
        AssetsOptProducer optProducer = new AssetsOptProducer(userAccount, conn);
//          if(servletConfig.getProvinceCode().equals(AssetsDictConstant.PROVINCE_CODE_NM)){
//                resContent = optProducer.getNMAreaOptions(dto.getOrganizationId(), "");
//            }else{
              resContent = optProducer.getAreaOptions(dto.getOrganizationId(), "");
//            }
        String isTd = getIsTD();
        if(isTd.equals("")){
            isTd = "EMPTY_CONTENT";
        }
        resContent += "&";
        resContent += isTd;
        return resContent;
    }
	
	public String getMatchORG() throws QueryException, ContainerException {
        CommonObjectModel modelProducer = (CommonObjectModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getMatchORG();
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        String matchORG = "";
        RowSet rows = simpleQuery.getSearchResult();
        if (rows != null && rows.getSize() > 0) {
            matchORG = simpleQuery.getFirstRow().getStrValue("COMPANY_CODE");
        }
        return matchORG;
    }
	
	public String getLocation(String location) throws QueryException, ContainerException{
		SQLModel sqlModel = commonObjectModel.getLocation(location);
		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
		simpleQuery.executeQuery();
		RowSet rows = simpleQuery.getSearchResult();
		Row row = null;
		
		StringBuffer result = new StringBuffer();
		if (rows != null && rows.getSize() > 0) {
			result.append("<div id=\"search_suggest\"><font color = '#blue'>&nbsp;&nbsp;如下是已添加的描述，请确认是否新增该描述！</font>");
			for (int i = 0; i < rows.getSize(); i++) {
				row = rows.getRow(i);
				result.append("<div onmouseover=\"javascript:suggestOver(this);\" style = 'width:100%'");
				result.append(" onmouseout=\"javascript:suggestOut(this);\" ");
				result.append(" onclick=\"javascript:setSearch(this.innerHTML);\" ");
				result.append(" class=\"suggest_link\">" + row.getValue("LOCATION") + "</div>");
			}
			result.append("<div align = 'right'><a href=''  onclick = 'do_Cancle();'><font color='blue' size='3'>关闭</font></a></div>");
			result.append("</div>");
		}
		return result.toString();
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
    	CommonObjectModel model = (CommonObjectModel) sqlProducer;
        SQLModel sqlModel = model.getAreaCountyCode(str);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        String code = "";
        if (simpleQuery.hasResult()) {
            code = simpleQuery.getSearchResult().getRow(0).getStrValue("COUNTY_CODE");
        }
        return code;
    }
    
    public boolean addObject() throws DataHandleException, SQLModelException, QueryException, ContainerException{
		boolean operateResult = false;
		boolean autoCommit = true;
		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
		EtsObjectDTO objectDTO = getEtsObjectLocData();
		
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			SeqProducer seqProducer = new SeqProducer(conn);
			String objectNo = seqProducer.getGUID();
			dto.setRemark("调用组合地点创建");
			dto.setWorkorderObjectNo(objectNo);
			dto.setWorkorderObjectName(java.net.URLDecoder.decode(dto.getWorkorderObjectName(),"UTF-8"));
			dto.setObjCategoryOption(objectDTO.getObjectCategory());
			dto.setObjectCategoryName(objectDTO.getObjectCategoryName());
			dto.setAreaType(objectDTO.getAreaType());
			dto.setCity(objectDTO.getCity());
			dto.setCounty(objectDTO.getCounty());
			dto.setBtsNo(objectDTO.getBtsNo());
			dto.setLatitudeLongitude(objectDTO.getLatitudeLongitude());
			dto.setAuxiliaryInfo(objectDTO.getAuxiliaryInfo());
			dto.setRemark(objectDTO.getRemark());
			setDTOParameter(dto);
			createData();
			
			AmsObjectAddressDTO dtoAddress = new AmsObjectAddressDTO();
			dtoAddress.setObjectNo(dto.getWorkorderObjectNo());
			dtoAddress.setBoxNo("0000");
			dtoAddress.setNetUnit("0000");
			dtoAddress.setAddressNo(dto.getWorkorderObjectNo() + ".0000.0000");
			dtoAddress.setRemark("调用组合地点创建");
			AmsObjectAddressDAO objectDAO = new AmsObjectAddressDAO(userAccount, dtoAddress, conn);
			objectDAO.createData();
			operateResult = true;
			
		} catch (SQLException ex) {
			Logger.logError(ex);
		} catch (UnsupportedEncodingException ex) {
			Logger.logError(ex);
		} finally{
			try {
				if (operateResult) {
					conn.commit();
				} else {
					conn.rollback();
					dto.setWorkorderObjectNo("");
				}
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex) {
				Logger.logError(ex);
				throw new DataHandleException(ex);
			}
		}
		return operateResult;
	}
    
    /**
     * 获取地点第二段代码
     * @return
     * @throws QueryException
     */
    public EtsObjectDTO getEtsObjectLocData() throws QueryException {
    	CommonObjectModel model = (CommonObjectModel) sqlProducer;
    	SQLModel sqlModel = model.getEtsObjectLoc2();
    	SimpleQuery simp = new SimpleQuery(sqlModel, conn);
    	simp.setDTOClassName(EtsObjectDTO.class.getName());
    	simp.executeQuery();
    	EtsObjectDTO etsObjectDTO = (EtsObjectDTO) simp.getDTOSet().getDTO(0);
        return etsObjectDTO;
    }
    
    public EtsObjectDTO getEtsObjectLocInfo() throws QueryException {
    	CommonObjectModel model = (CommonObjectModel) sqlProducer;
    	SQLModel sqlModel = model.getEtsObjectLocInfo();
    	SimpleQuery simp = new SimpleQuery(sqlModel, conn);
    	simp.setDTOClassName(EtsObjectDTO.class.getName());
    	simp.executeQuery();
    	EtsObjectDTO etsObjectDTO = (EtsObjectDTO) simp.getDTOSet().getDTO(0);
        return etsObjectDTO;
    }
    
    public String getWorkorderObjectNo() throws SQLModelException, QueryException, ContainerException {
    	CommonObjectModel model = (CommonObjectModel) sqlProducer;
        SQLModel sqlModel = model.getWorkorderObjectNo();
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        String objectNo = "";
        if (simpleQuery.hasResult()) {
        	Row row = simpleQuery.getFirstRow();
        	objectNo = row.getStrValue("WORKORDER_OBJECT_NO") + ";" + row.getStrValue("ADDRESS_ID");
        }
        return objectNo;
    }
    
    /**
     * 判断地点第二段代码是否存在
     * @param barcode
     * @param companyCode
     * @return
     * @throws SQLModelException
     * @throws QueryException
     */
    public boolean getLoc2CodeIsExists(String barcode) throws SQLModelException, QueryException {
        boolean hasLocCode = true;
        CommonObjectModel eoModel = (CommonObjectModel) sqlProducer;
        SQLModel sqlModel = eoModel.getLoc2CodeIsExistsModel(barcode);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (!simpleQuery.hasResult()) {
        	hasLocCode = false;
        }
        return hasLocCode;
    }
    
    /**
     * 获取匹配的orgizationId
     * @return
     * @throws QueryException
     * @throws ContainerException
     */
    private int getMatchOrgId(int orgId) throws QueryException, ContainerException {
        CommonObjectModel modelProducer = (CommonObjectModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getMatchOrgIdModel(orgId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        int matchOrgId = 0;
        if (simpleQuery.hasResult()) {
        	matchOrgId = Integer.parseInt(simpleQuery.getFirstRow().getStrValue("MATCH_ORGANIZATION_ID"));
        }
        return matchOrgId;
    }

    /**
	 * 功能:检查匹配地点编码是否存在
	 * @return boolean
	 */
	public boolean existMatchObjectCode(){
		boolean exist = false;
		try {
			CommonObjectModel modelProducer = (CommonObjectModel) sqlProducer;
			SQLModel sqlModel = modelProducer.getMatchObjectCodeExistModel();
			SimpleQuery simQuery = new SimpleQuery(sqlModel, conn);
			simQuery.executeQuery();
			exist = simQuery.hasResult();
		} catch (QueryException ex) {
			ex.printLog();
		}
		return exist;
	}
}
