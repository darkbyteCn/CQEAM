package com.sino.ams.workorder.dao;


import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderBatchDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.dto.ZeroTurnHeaderDTO;
import com.sino.ams.workorder.dto.ZeroTurnLineDTO;
import com.sino.ams.workorder.model.ZeroTurnModel;
import com.sino.ams.workorder.util.WorkOrderUtil;
import com.sino.base.constant.WorldConstant;
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
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dto.BaseUserDTO;

public class ZeroTurnHeaderDAO extends AMSProcedureBaseDAO {

	
	public ZeroTurnHeaderDAO(SfUserDTO userAccount, ZeroTurnHeaderDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}
	
	
	@Override
	protected void initSQLProducer(BaseUserDTO arg0, DTO arg1) {
		 ZeroTurnHeaderDTO dtoPara = (ZeroTurnHeaderDTO) dtoParameter;
	     sqlProducer = new ZeroTurnModel((SfUserDTO) userAccount, dtoPara);
	}
	
	//验证导入的数据是否正确
	public boolean validateImport(DTOSet  dtoSet) throws QueryException{
		boolean flag=true;
		int count=0;
		DecimalFormat df = new DecimalFormat("#.##");
	    
		if (dtoSet!=null) {
			if (!dtoSet.isEmpty()) {
				for (int i = 0; i < dtoSet.getSize(); i++) {
					ZeroTurnLineDTO  lineDTO=(ZeroTurnLineDTO) dtoSet.getDTO(i);
					boolean isEt=true;
					String errorMsg="";
					
					String companyCode=lineDTO.getCompanyCode();//公司代码（必输）
					lineDTO.setCompanyCode(companyCode);
					if(companyCode.equals("1000")){
						lineDTO.setIsTd("N");
					}else {
						lineDTO.setIsTd("Y");
					}
					isEt=checkExist(companyCode);
					if (!isEt) {
						errorMsg+="公司代码必须输入，不能为空;<br>";
					}else
					if (getOrgId(companyCode)==0) {
						errorMsg+="公司代码不正确;<br>";
					}else {
						lineDTO.setCompanyCodeTWO(getOrgId(companyCode));
					}
					String procureCode = lineDTO.getProcureCode();// 采购单号（必输）
					procureCode = df.format(Double.parseDouble(procureCode));
					isEt=checkExist(procureCode);
					if (!isEt) {
						errorMsg+="采购单号必须输入，不能为空;<br>";
					}else {
						lineDTO.setProcureCode(procureCode);
					}
					
					String assetsDescription = lineDTO.getAssetsDescription();// 资产名称
					isEt=checkExist(assetsDescription);
					if (!isEt) {
						errorMsg+="资产名称必须输入，不能为空;<br>";
					}
					
					String itemSpec = lineDTO.getItemSpec();// 规格型号（必输）
					isEt=checkExist(itemSpec);
					if (!isEt) {
						errorMsg+="规格型号必须输入，不能为空;<br>";
					}
					
					String manufacturerName = lineDTO.getManufacturerName();// 厂商（必输）
					isEt=checkExist(manufacturerName);
					if (isEt) {
//						errorMsg+="厂商必须输入，不能为空;<br>";
						lineDTO.setManufacturerIdTWO(getManufactuerId(manufacturerName));
						lineDTO.setManufacturerId(getManufactuerId(manufacturerName));
					}
//					else{
//						lineDTO.setManufacturerIdTWO(getManufactuerId(manufacturerName));
//					}
					
					
					String contentCode=lineDTO.getContentCode();//资产目录（必输）
					isEt=checkExist(contentCode);
					if (!isEt) {
						errorMsg+="资产目录必须输入，不能为空;<br>";
					}else {
						lineDTO.setContentName(getContentName(contentCode));
						lineDTO.setUnitOfMeasure(getUnitOfMeasure(contentCode));
					}
					
					String itemQty = lineDTO.getItemQty();// 数量（必输）
					isEt=checkExist(itemQty);
					if (!isEt) {
						errorMsg+="数量必须输入，不能为空;<br>";
					}
					
					String year = lineDTO.getYears();// 使用年限（必输）
//					Double b=Double.parseDouble(a);
//					int c=(int) (b*100/100);
//					lineDTO.setYears(year);
//					isEt=checkExist(years);
//					if (!isEt) {
//						errorMsg+="使用年限必须输入，不能为空;<br>";
//					}
					
					String price = lineDTO.getPrice();// 金额（必输）
					isEt=checkExist(price);
					if (!isEt) {
						errorMsg+="金额必须输入，不能为空;<br>";
					}
					
					// String startDate = null; // 启用日期（可选）
					
					String opeId = lineDTO.getOpeId();// 业务平台（必输）
					isEt=checkExist(opeId);
					if (!isEt) {
						errorMsg+="业务平台必须输入，不能为空;<br>";
					} else
					if (getYwpt(opeId)==null||getYwpt(opeId)==null){
						errorMsg+="业务平台不正确;<br>";
					}else {
						lineDTO.setOpeIdTWO(getYwpt(opeId));
					}
					
					String nleId = lineDTO.getNleId();// 网各层次（必输）
					isEt=checkExist(nleId);
					if (!isEt) {
						errorMsg+="网络层次必须输入，不能为空;<br>";
					} else if (getWlcc(nleId)==null||getWlcc(nleId)==null){
						errorMsg+="网络层次不正确;<br>";
					}else {
						lineDTO.setNleIdTWO(getWlcc(nleId));
					}
					
					String isBulid = lineDTO.getIsBulid();// 是否共建设备（必输）
					isEt=checkExist(isBulid);
					if (!isEt) {
						errorMsg+="是否共建设备必须输入，不能为空;<br>";
					} 
					
					String responsibilityUser = lineDTO.getResponsibilityUser();// 责任人编号（必输）
				    responsibilityUser = df.format(Double.parseDouble(responsibilityUser));
					isEt=checkExist(responsibilityUser);
					if (!isEt) {
						errorMsg+="资产责任人员工编号必须输入，不能为空;<br>";
					}else { 
						if (getPers(responsibilityUser)==""||getPers(responsibilityUser)==null) {
							 errorMsg+="资产责任人员工编号不正确;<br>";
						}else {
						lineDTO.setResponsibilityUser(responsibilityUser);
						lineDTO.setResponsibilityUserTWO(getPers(responsibilityUser));
						lineDTO.setResponsibilityDept(getDeptName(responsibilityUser));
						lineDTO.setResponsibilityDeptTWO(getDeptCode(getDeptName(responsibilityUser)));
						}
					}
					
					String costCenterCode = lineDTO.getCostCenterCode();// 成本中心（必输）
					costCenterCode = df.format(Double.parseDouble(costCenterCode));
					isEt=checkExist(costCenterCode);
					if (!isEt) {
						errorMsg+="成本中心必须输入，不能为空;<br>";
					} else
					if (!getCbzx(getDeptName(responsibilityUser)).equals(costCenterCode)) {
						errorMsg+="成本中心与责任部门不对应;<br>";
					}else {
						lineDTO.setCostCenterCode(costCenterCode);
					}
					
					String objectNo = lineDTO.getObjectNo();// 地点编号（必输）
					isEt=checkExist(objectNo);
					if (!isEt) {
						errorMsg+="地点编号必须输入，不能为空;<br>";
					}else
					if (getAddressId(objectNo,companyCode)==""||getAddressId(objectNo,companyCode)==null) {
						errorMsg+="地点编号不正确;<br>";
					}else {
						lineDTO.setAddressIdTWO(getAddressId(objectNo,companyCode));
						lineDTO.setAddressId(getAddressId(objectNo,companyCode));
					}
					String workorderObjectName = lineDTO.getWorkorderObjectName();// 地点名称（可选）
//					String queryObjectName=this.getAddressName(objectNo);//根据资产目录查询资产目录名称
					isEt=checkExist(workorderObjectName);
					if (!isEt) {
//						if (!workorderObjectName.trim().equals(queryObjectName.trim())) {
							errorMsg+="地点名称不能为空;<br>";
//						}
					}
					
					
					String responsibilityName=lineDTO.getResponsibilityName();//责任人姓名
					isEt=checkExist(responsibilityName);
					if (!isEt) {
						errorMsg+="资产责任人姓名必须输入，不能为空;<br>";
					}
					
					String procureType = lineDTO.getProcureType();
					isEt=checkExist(procureType);
					if (!isEt) {
						errorMsg+="请购类别必须输入，不能为空;<br>";
					}
					
					String receiver = lineDTO.getReceiver();
					isEt=checkExist(receiver);
					if (!isEt) {
						errorMsg+="收货人必须输入，不能为空;<br>";
					}
					
					String receiverContact = lineDTO.getReceiverContact();
					receiverContact = df.format(Double.parseDouble(receiverContact));
					isEt=checkExist(receiverContact);
					if (!isEt) {
						errorMsg+="收货人联系方式必须输入，不能为空;<br>";
					}else {
						lineDTO.setReceiverContact(receiverContact);
					}
					
//					String misProcureCode = lineDTO.getMisProcureCode();
//					isEt=checkExist(misProcureCode);
//					if (!isEt) {
//						errorMsg+="发货单编号必须输入，不能为空;<br>";
//					}
					
					
//					
//					
//					String contentName=lineDTO.getContentName();//资产目录名称（可选）
//					String queryName=this.getContentName(contentCode);//根据资产目录查询资产目录名称
//					if (!contentName.equals("")) {
//						if (!contentName.trim().equals(queryName.trim())) {
//							errorMsg+="资产目录对应的资产目录名称不正确;<br>";
//						}
//					}
					
//					String barCode=lineDTO.getBarcode();//资产标签
//					if (!barCode.equals("")) {
//						if (!isUniqueBarCode(barCode)) {
//							errorMsg+="标签号"+barCode+"已存在;<br>";
//						}
//					}
					
					String unitOfMeasure = getUnitOfMeasure(contentCode);// 数量单位（必输）
					lineDTO.setUnitOfMeasure(unitOfMeasure);
					 
//					String manufacturerName = lineDTO.getManufacturerName();// 厂商（必输）
//					isEt=checkExist(manufacturerName);
//					if (!isEt) {
//						errorMsg+="厂商必须输入，不能为空;<br>";
//					}else{
//						lineDTO.setManufacturerIdTWO(getManufactuerId(manufacturerName));
//					}
					 
					
					 
//					String responsibilityDept = lineDTO.getResponsibilityDept();// 责任部门（必输）
//					isEt=checkExist(responsibilityDept);
//					if (!isEt) {
//						errorMsg+="责任部门必须输入，不能为空;<br>";
//					}else
//					if (getDeptCode(responsibilityDept)==""||getDeptCode(responsibilityDept)==null) {
//						errorMsg+="责任部门不正确;<br>";
//					}else {
//						lineDTO.setResponsibilityDeptTWO(getDeptCode(responsibilityDept));
//					}
						
					
					// String specialityDept = lineDTO.;// 专业部门（可选）
//					 if (lineDTO.getSpecialityDept()==null||lineDTO.getSpecialityDept()=="") {
//						 
//					}else {
//						if (getZYDetp(lineDTO.getSpecialityDept())==null||getZYDetp(lineDTO.getSpecialityDept())==null){
//							errorMsg+="专业部门不正确;<br>";
//						}else {
//							lineDTO.setSpecialityDeptTWO(getZYDetp(lineDTO.getSpecialityDept()));
//						}
//					}
//					
					
					
					
					// String isShare = lineDTO.;// 是否共享设备（可选）
					
					 //String lneId = lineDTO.;// 逻辑网络元素（可选）
					//
					
					 //String cexId = lineDTO.;// 投资分类（可选）
//					if (lineDTO.getCexId()==null||lineDTO.getCexId()=="") {
//						
//					}else {
//						if (getTzfl(lineDTO.getCexId())==null||getTzfl(lineDTO.getCexId())==null){
//							errorMsg+="投资分类不正确;<br>";
//						}else {
//							lineDTO.setCexIdTWO(getTzfl(lineDTO.getCexId()));
//						}
//					}
					
//					String assetKey1=lineDTO.getAssetKey1();
//					isEt=checkExist(assetKey1);
//					if (!isEt) {
//						errorMsg+="业务分类必须输入，不能为空;<br>";
//					}
//					String assetKey2=lineDTO.getAssetKey2();
//					isEt=checkExist(assetKey2);
//					if (!isEt) {
//						errorMsg+="总公司分类必须输入，不能为空;<br>";
//					}
//					String assetKey3=lineDTO.getAssetKey3();
//					isEt=checkExist(assetKey3);
//					if (!isEt) {
//						errorMsg+="总公司备用必须输入，不能为空;<br>";
//					}
//					String assetType=lineDTO.getAssetType();
//					isEt=checkExist(assetType);
//					if (!isEt) {
//						errorMsg+="资产类型必须输入，不能为空;<br>";
//					}
//					String isDeprn=lineDTO.getIsDeprn();
//					isEt=checkExist(isDeprn);
//					if (!isEt) {
//						errorMsg+="是否折旧必须输入，不能为空;<br>";
//					}
//					String isAdjust=lineDTO.getIsAdjust();
//					isEt=checkExist(isAdjust);
//					if (!isEt) {
//						errorMsg+="是否摊销调整必须输入，不能为空;<br>";
//					}
//					String attribute4=lineDTO.getAttribute4();
//					isEt=checkExist(attribute4);
//					if (!isEt) {
//						errorMsg+="建设状态必须输入，不能为空;<br>";
//					}
//					String attribute5=lineDTO.getAttribute5();
//					isEt=checkExist(attribute5);
//					if (!isEt) {
//						errorMsg+="资产来源必须输入，不能为空;<br>";
//					}
					lineDTO.setItemCode(getItemCode(contentCode,assetsDescription,itemSpec,itemQty,getOrgId(companyCode)));
					lineDTO.setCenterName(getCountyName(costCenterCode,companyCode));
					lineDTO.setpUserName(getUserName(responsibilityUser));
					if (!errorMsg.equals("")) {
						count++;
					}
					
					lineDTO.setErrorMessage(errorMsg);
				}
			}
		}
		if (count>0) {
			flag=false;
		}
		return flag;
	}
	
	//判断字符串是否存在
	public boolean checkExist(String str){
		boolean isExist=true;
		if (str==null||str.equals("")) {
			isExist=false;
		}
		return isExist;
	}
	
	  /**
     * 功能：保存零购单据
     * 头信息，行信息 提交到流程
     */
    public boolean saveOrder(DTOSet lineSet) throws SQLModelException, ContainerException, QueryException {
        boolean result = false;
        boolean autoCommit = true;
        ZeroTurnHeaderDTO dtoPara=(ZeroTurnHeaderDTO)dtoParameter;
        boolean isNewData = dtoPara.getTransNo().equals(AssetsWebAttributes.ORDER_AUTO_PROD);
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            if(isNewData){
	            if (lineSet!=null) {
					if (lineSet.getSize()>0) {
						ZeroTurnLineDTO zdto=(ZeroTurnLineDTO) lineSet.getDTO(0);
						String barcode=zdto.getBarcode();
						if (!barcode.equals("")) {
							saveOrderHeader();
							deleteOrderLines();
							saveOrderLines(lineSet);            
						}
					}
	            }
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
                    prodMessage("ZERO_TURN_FAILURE");
                    if (isNewData) {
                        rollbackData();
                    }
                } else {
                    conn.commit();
                    prodMessage("ZERO_TURN_SUCCESS");
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
	
	 /**
     * 功能：提交资产单据：含调拨，报废，处置
     *
     * @param lineSet DTOSet 资产数据
     * @return boolean
     */
    public boolean submitOrder(DTOSet lineSet,String orderExecuter, String orderExecuterName,String orderFiler,String orderFilerName) {
        boolean operateResult = false;
        boolean autoCommit = true;
        ZeroTurnHeaderDTO headerDTO=(ZeroTurnHeaderDTO)dtoParameter;
        String trnasStatus=headerDTO.getTransStatus();
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            //流程第一个节点
            if (headerDTO.getSf_task_attribute1().equals("from1")&&!trnasStatus.equals("ALR_FILLING")) { 
	            saveOrderHeader();
	            deleteOrderLines();
	            saveOrderLines(lineSet);
	        //流程其它节点
            }else {
            	//单位资产管理员分发工单
            	if (trnasStatus.equals("PRE_ASSETS")) {
            		//修改状态[业务操作]
//            		updateZeroHeader();
            		String status="ALR_ISSUED";
            		String statusVal="已下发";
            		updateHeaderStatusByMgr(status,statusVal,orderExecuter,orderExecuterName,orderFiler,orderFilerName);
            		//修改ETS_ITEM_INFO状态
            		//updateEtsItemStatus(lineSet,status);
            		updateLineStatus(status,statusVal);
				 }
            	//工单归档人归档确认
            	else if(trnasStatus.equals("ALR_ISSUED")){
            		String status="ALR_FILLING";
            		String statusVal="已归档";
            		updateHeaderStatus(status,statusVal);
            		//updateEtsItemStatus(lineSet,status);
            		updateLineStatus(status,statusVal);
            		//提交采购部人员结束流程
            	}else if(trnasStatus.equals("ALR_FILLING")){
            		String status="TO_END";
            		String statusVal="已零购";
            		updateHeaderStatus(status,statusVal);
            		//updateEtsItemStatus(lineSet,status);
            		updateLineStatus(status,statusVal);
            	}
            }
            operateResult = processProcedure(true);
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                    prodMessage("ZERO_TURN_FAILURE");
                } else {
                    conn.commit();
                    prodMessage("ZERO_TURN_SUCCESS");
                }
                conn.setAutoCommit(autoCommit);
                message.addParameterValue("提交");
                message.setIsError(!operateResult);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }

    /**
     * 功能：保存单据头信息
     *
     * @return String
     * @throws DataHandleException
     */
    private void saveOrderHeader() throws DataHandleException {
        try {
            ZeroTurnHeaderDTO headerDTO = (ZeroTurnHeaderDTO) dtoParameter;
            String transNo = headerDTO.getTransNo();
           // headerDTO.setFromPerson(userAccount.getEmployeeNumber());
            String act = headerDTO.getAct();
            if (transNo.equals(AssetsWebAttributes.ORDER_AUTO_PROD)) {
                String transId = headerDTO.getTransId();
                //if (StrUtil.isEmpty(transId)) {
                    SeqProducer seq = new SeqProducer(conn);
                    transId = StrUtil.nullToString(seq.getGUID());
                    headerDTO.setTransId(transId);
                //}
                String companyCode = userAccount.getCompanyCode(); //还是采用该方法，以下考虑周子君认为没必要
                //String transType = headerDTO.getTransType();
                
                OrderNumGenerator numberProducer = new OrderNumGenerator(conn, companyCode, "ZERO-TURN");
                headerDTO.setTransNo(numberProducer.getOrderNum());
                setDTOParameter(headerDTO);
                createData();
            } else {
                updateData();
                //deleteLines();
                //deleteReserveAssets();
            }
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
    }
    
    
    /**
     * 功能：保存单据行信息
     *
     * @param lineSet DTOSet
     * @throws DataHandleException
     * @throws QueryException 
     */
    private void saveOrderLines(DTOSet lineSet) throws
            DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            ZeroTurnHeaderDTO headerDTO = (ZeroTurnHeaderDTO) dtoParameter;
            ZeroTurnLineDAO lineDAO = new ZeroTurnLineDAO(userAccount, null, conn);
            String transId=headerDTO.getTransId();
            for (int i = 0; i < lineSet.getSize(); i++) {
                ZeroTurnLineDTO lineData = (ZeroTurnLineDTO) lineSet.getDTO(i);
                String costCenterCode=lineData.getCostCenterCode();
                if(costCenterCode.equals("")){
                	try {
						costCenterCode=this.getCostCenterCode(lineData.getRecord());
					} catch (QueryException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	lineData.setCostCenterCode(costCenterCode);
                }
                lineData.setTransId(transId);
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
            }
        }
    }
    
    /**
     * 获取表单行信息
     */
    public DTOSet getLineData() throws QueryException {
        ZeroTurnHeaderDTO dto = (ZeroTurnHeaderDTO) dtoParameter;
        ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
        SQLModel sqlModel = model.getLineDataModel(dto);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(ZeroTurnLineDTO.class.getName());
        simp.executeQuery();
        return simp.getDTOSet();
    }
    public DTOSet getAllExcelData() throws QueryException {
        ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
        SQLModel sqlModel = model.getAllExcelData();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(ZeroTurnLineDTO.class.getName());
        simp.executeQuery();
        return simp.getDTOSet();
    }
    
    public String getObjectNo(int userId,String code) throws QueryException{
        ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
        String s="";
        SQLModel sqlModel = model.getObjectNo(userId,code);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
    }
    
    protected void prepareProcedureData() {
        ZeroTurnHeaderDTO dtoPara = (ZeroTurnHeaderDTO) dtoParameter;
        dtoPara.setPrimaryKey(dtoPara.getTransId());
        dtoPara.setOrderNo(dtoPara.getTransNo());
        dtoPara.setOrderName(dtoPara.getTransType());
    }
    
    
    public boolean isUniqueBarCode(String barCode){
    	boolean flag=false;
         try {
			ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
			SQLModel sqlModel = model.getCountBarCode(barCode);
			SimpleQuery qeuryQuery = new SimpleQuery(sqlModel, conn);
			qeuryQuery.executeQuery();
			RowSet rs = qeuryQuery.getSearchResult();
			int n = Integer.parseInt(rs.getRowValues().get(0).toString().substring(1, 2));
			if (n==0) {
				flag=true;
			}
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
    }
    
    public void updateZeroHeader(){
   	 try {
   		 ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
   		 SQLModel updateModel = model.updateHeader();
		 DBOperator.updateRecord(updateModel, conn);
		} catch (DataHandleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
    
    public void updateHeaderStatus(String status,String val){
    	 try {
       		 ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       		 SQLModel updateModel = model.updateZeroHeader(status,val);
    		 DBOperator.updateRecord(updateModel, conn);
    		} catch (DataHandleException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    }
    
    public void updateHeaderStatusByMgr(String status,String val, String orderExecuter,String orderExecuterName,String orderFiler,String orderFilerName){
   	 try {
      		 ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
      		 SQLModel updateModel = model.updateZeroHeaderByMgr(status, val,orderExecuter,orderExecuterName,orderFiler,orderFilerName);
   		     DBOperator.updateRecord(updateModel, conn);
   		} catch (DataHandleException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   }
    
    public void updateLineStatus(String status,String statusVal){
    	 try {
       		 ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       		 SQLModel updateModel = model.updateZeroLineStatus(status,statusVal);
    		 DBOperator.updateRecord(updateModel, conn);
    		} catch (DataHandleException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    }
    
   public void updateEtsItemStatus(DTOSet lineSet,String status){
	   try {
			ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
			for (int i = 0; i < lineSet.getSize(); i++) {
				ZeroTurnLineDTO lineDTO=(ZeroTurnLineDTO) lineSet.getDTO(i);
				String barcode=lineDTO.getBarcode();
				SQLModel updateModel = model.updateEtsItemStatus(barcode,status);
				DBOperator.updateRecord(updateModel, conn);
			}
	   } catch (DataHandleException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   public void insertEtsItem(DTOSet lineSet){
//	   boolean fg = true;
	   try {
			ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
			for (int i = 0; i < lineSet.getSize(); i++) {
				ZeroTurnLineDTO lineDTO=(ZeroTurnLineDTO) lineSet.getDTO(i);
				lineDTO.setCreatedBy(userAccount.getUserId());
				lineDTO.setOrganizationId(userAccount.getOrganizationId());
				SQLModel updateModel = model.insertEtsItemInfo(lineDTO);
				DBOperator.updateRecord(updateModel, conn);
			}
	   } catch (DataHandleException e) {
//			fg=false;
		e.printStackTrace();
	   }
//	   return fg;
   }
   private void rollbackData() {
       ZeroTurnHeaderDTO dtoPara = (ZeroTurnHeaderDTO) dtoParameter;
       dtoPara.setTransId("");
       dtoPara.setTransNo(AssetsWebAttributes.ORDER_AUTO_PROD);
   }
   
   public void deleteOrderLines(){
       try {
			ZeroTurnHeaderDTO dto = (ZeroTurnHeaderDTO) dtoParameter;
			ZeroTurnModel modelProducer = (ZeroTurnModel) sqlProducer;
			modelProducer.setDTOParameter(dto);
			SQLModel sqlModel = modelProducer.deleteImportModel();
			DBOperator.updateRecord(sqlModel, conn);
       } catch (DataHandleException e) {
		// TODO Auto-generated catch block
    	   e.printStackTrace();
       }
   }
   
   public void rejectOrder() {
       boolean operateResult = rejectProcedure();
       if (operateResult) {
           prodMessage(AssetsMessageKeys.REJECT_ORDER_SUCCESS);
       } else {
           prodMessage(AssetsMessageKeys.REJECT_ORDER_FAILURE);
           message.setIsError(true);
       }
       ZeroTurnHeaderDTO dto = (ZeroTurnHeaderDTO) dtoParameter;
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
       ZeroTurnHeaderDTO headerDTO = (ZeroTurnHeaderDTO) dtoParameter;
       try {
           autoCommit = conn.getAutoCommit();
           conn.setAutoCommit(false);
           String status=DictConstant.CANCELED;
           String ststusType="撤销";
           ZeroTurnModel modelProducer = (ZeroTurnModel) sqlProducer;
           SQLModel sqlModel = modelProducer.updateZeroHeader(status,ststusType);
           DBOperator.updateRecord(sqlModel, conn);
           
           SQLModel sql2=modelProducer.updateZeroLineStatus(status, ststusType);
           DBOperator.updateRecord(sql2, conn);
           
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
   
//    SQLModel sqlModel = model.getInserBatchModel(dto);
//    SQLModel sqlModel1 = model.getCreateWorkorderDataModel(dto);
//    SQLModel sqlModel2 = model.getInsertDtlModel(dto);
//	        isExsitVallueSet();
//		DBOperator.updateRecord(sqlModel, conn);
//		DBOperator.updateRecord(sqlModel1, conn);
//		DBOperator.updateRecord(sqlModel2, conn);
   public boolean subIn(DTOSet lineSet,SfUserDTO user,int groupId ,String groupName,String orderExecuter,String orderFiler){
	   	boolean autoCommit=false;
	   	boolean operateResult=false;
	   	ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
	    ZeroTurnHeaderDTO headerDTO=(ZeroTurnHeaderDTO)dtoParameter;
	    String trnasStatus=headerDTO.getTransStatus();
	    String transId=headerDTO.getTransId();
	    String computeDays=headerDTO.getComputeTims();//执行周期
	   	try {
	       	autoCommit = conn.getAutoCommit();
	        conn.setAutoCommit(false);
	        if (trnasStatus.equals("PRE_ASSETS")&&!computeDays.equals("")) {
	        	isExsitSegment();//添加零购工程
	        	operateResult=createTmpData(lineSet, user,groupId,groupName, headerDTO, orderExecuter, orderFiler,transId);
			}else{
				operateResult=true;
			}
		} catch (DataHandleException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (operateResult) {
					conn.commit();
			        autoCommit=true;
			    } else {
			        conn.rollback();
			    }
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
   	return autoCommit;
   }
   
   
   public void isExsitSegment() throws DataHandleException, QueryException{
	   ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
	   String segment="Z9999999";
	   SQLModel existModel = model.existsProjectModel(segment);
	   SimpleQuery qeuryQuery = new SimpleQuery(existModel, conn);
	   qeuryQuery.executeQuery();
	   RowSet rs = qeuryQuery.getSearchResult();
	   int n = Integer.parseInt(rs.getRowValues().get(0).toString().substring(1, 2));
	   if (n==0) {
		    SQLModel inModel = model.insertEtsProjects(segment);
			DBOperator.updateRecord(inModel, conn);
	   }
   }
   
   public void isExsitVallueSet() throws DataHandleException, QueryException{
	   ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
	   String segment="ZERO_TURN";
	   SQLModel existModel = model.existEtsValSet(segment);
	   SimpleQuery qeuryQuery = new SimpleQuery(existModel, conn);
	   qeuryQuery.executeQuery();
	   RowSet rs = qeuryQuery.getSearchResult();
	   int n = Integer.parseInt(rs.getRowValues().get(0).toString().substring(1, 2));
	   if (n==0) {
		    SQLModel inModel = model.insertEtsValSet(segment);
			DBOperator.updateRecord(inModel, conn);
	   }
	   
	   SQLModel extModel = model.existEtsVal(segment);
	   SimpleQuery qeuryQuery2 = new SimpleQuery(extModel, conn);
	   qeuryQuery2.executeQuery();
	   RowSet rs2 = qeuryQuery2.getSearchResult();
	   int n2 = Integer.parseInt(rs2.getRowValues().get(0).toString().substring(1, 2));
	   if (n2==0) {
		    SQLModel inModel = model.insertEtsVal(segment);
			DBOperator.updateRecord(inModel, conn);
	   }
   }
   
   /**
    * 插入数据至工单临时表
    * @param workorderObjectNos
    * @param workorderDTO
    * @param sfUser
    * @return
    * @throws DataHandleException
    */
   public boolean createTmpData(DTOSet orderLines, SfUserDTO sfUser,int groupId,String groupName,ZeroTurnHeaderDTO headerDTO,String orderExecuter,String orderFiler,String transId) throws DataHandleException {
       boolean operatorResult = true;
		try {
			ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
			SQLModel sqlModel = null;
			List sqlModList = new ArrayList();
			EtsWorkorderDTO workorderDTO=new EtsWorkorderDTO();
			workorderDTO.setWorkorderBatch(WorkOrderUtil.getWorkorderBatchNo(conn));                           //工单批号
			for (int i = 0; i < orderLines.getSize(); i++) {
				ZeroTurnLineDTO dto=(ZeroTurnLineDTO) orderLines.getDTO(i);
				String record=dto.getRecord();//标签号
				String costCode=this.getCostCenterCode(record);
//				String costCenterCode=dto.getCostCenterCode();                                                 //成本中心
				String objectCode=dto.getObjectNo();
				String workorderNo = WorkOrderUtil.getWorkorderNo(workorderDTO.getWorkorderBatch(), conn);     //工单号
				workorderDTO.setWorkorderNo(workorderNo);
				workorderDTO.setCostCenterCode(costCode);                                                //成本中心
			    String objeNo="";
			    objeNo=this.getObjectNo(sfUser.getOrganizationId(), objectCode);
				workorderDTO.setWorkorderObjectNo(objeNo);                                                     //工单对象
				workorderDTO.setImplementDays(Integer.parseInt(headerDTO.getComputeTims()));                   //实施周期
				workorderDTO.setGroupId(groupId);                                                              //派往部门/接单部门
				workorderDTO.setGroupName(groupName);                                                          
				workorderDTO.setImplementBy(Integer.parseInt(orderExecuter));                                  //工单执行人
				workorderDTO.setCheckoverBy(Integer.parseInt(orderFiler));
				workorderDTO.setPrjId("d87500c590d34fd3882dd1a73757ef0d");                                     //工程ID
				workorderDTO.setResponsibilityUser(dto.getResponsibilityUser());                               //责任人
				workorderDTO.setOrganizationId(sfUser.getOrganizationId());                                    //组织
				workorderDTO.setWorkorderFlag(DictConstant.WOR_STATUS_NEW);                                    //工单状态
//				workorderDTO.setRemark(remark);                                                                //备注
				workorderDTO.setCreatedBy(sfUser.getUserId());                                                 //创建人
				workorderDTO.setCreationDate(dto.getCreateDate());                                             //创建日期
				workorderDTO.setDistributeGroup(groupId);
				workorderDTO.setWorkorderType("18");
//				workorderDTO.setWorkorderType("19");
				
				SQLModel itemModel = model.insertWorkItem(workorderDTO.getWorkorderBatch(), workorderNo, record,transId);
				DBOperator.updateRecord(itemModel, conn);
				 
				sqlModel = model.getInsertWorkorderDataModel(workorderDTO);
				sqlModList.add(sqlModel);
			}
				operatorResult = DBOperator.updateBatchRecords(sqlModList, conn);
				
				FlowDTO flowDTO = new FlowDTO();
				flowDTO.setProcName("零购转资流程下发工单");
				flowDTO.setSessionUserId(-1);
				EtsWorkorderBatchDTO workorderBatch = new EtsWorkorderBatchDTO();
				SeqProducer sp = new SeqProducer(conn);
	            workorderBatch.setSystemid(sp.getGUID());
	            workorderBatch.setWorkorderBatch(workorderDTO.getWorkorderBatch());
	            workorderBatch.setPrjId(DictConstant.ZERO_PRJ_ID); 
//	            workorderBatch.setPrjId("d87500c590d34fd3882dd1a73757ef0d"); 
	            workorderBatch.setWorkorderType("18");
//	            workorderBatch.setWorkorderType("19");
	            workorderBatch.setStatus(0);
	            workorderBatch.setArchflag(0);
	            workorderBatch.setDistributeGroupId(groupId);
	            workorderBatch.setCreatedBy(sfUser.getUserId());
	            SQLModel inSql=model.getInserBatchModel(workorderBatch, sfUser);
	            DBOperator.updateRecord(inSql, conn);
	            
	            flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
	            WorkOrderUtil workOrderUtil = new WorkOrderUtil();
	            workOrderUtil.saveBatchExtends(conn, workorderBatch);
	            String  workorderNode = "PROCESS3";
	            boolean isNew=true;
	            boolean isFlowOver=true;
	            workOrderUtil.saveOrderStatus(conn, workorderBatch.getWorkorderBatch(), workorderNode);
	            workOrderUtil.saveWorkorderPorcess(conn, workorderBatch.getWorkorderBatch(), workorderNode, isNew, false, isFlowOver);
				 
				 
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new DataHandleException(ex);
		} catch (CalendarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return operatorResult;
   }
   
   public String getContentName(String contentCode) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getContentName(contentCode);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
   
   public String getAddressId(String objectNo,String companyCode) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getAddressId(objectNo,companyCode);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
   
   public String getAddressName(String objectCode) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getAddressName(objectCode);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
   public String getZYDetp(String objectCode) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getZYDetp(objectCode);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
   public String getWlys(String objectCode) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getWlys(objectCode);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
   public String getWlcc(String objectCode) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getWlcc(objectCode);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
//   public String getYwpt(String objectCode) throws QueryException{
//       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
//       String s="";
//       SQLModel sqlModel = model.getYwpt(objectCode);
//       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
//       simp.executeQuery();
//		RowSet rs = simp.getSearchResult();
//		if (rs.getSize()==1) {
//			s =rs.getRowValues().get(0).toString();
//			s=s.substring(1, s.length()-1);
//		}
//		return s;
//   }
   public String getCbzx(String objectCode) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getCbzx(objectCode);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
   public String getTzfl(String objectCode) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getTzfl(objectCode);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
   public String getYwpt(String objectCode) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getYwpt(objectCode);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
   public String getEfvCode(String contentCode) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getEfvCode(contentCode);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
   
   public String getItemCode(String contentCode,String assetsDescription,String itemSpec,String unitOfMeasure,int orgnizationId  ) throws QueryException{
	   String s="";
	   String efvCode=this.getEfvCode(contentCode);
	   s=queryItemCode(efvCode, assetsDescription, itemSpec, unitOfMeasure, orgnizationId);
	   return s;
   }
   
   public String queryItemCode(String contentCode,String assetsDescription,String itemSpec,String unitOfMeasure,int orgnizationId ){
       CallableStatement cs = null;
       String s="";
	      try {
	          String callStr = "{CALL SYN_FA_EFA_SYSTEM_ITEM(?, ?, ?, ?, ?, ?)}";
	          cs = conn.prepareCall(callStr);
	          cs.setString(1,contentCode);
	          cs.registerOutParameter(2, java.sql.Types.VARCHAR); 
	          cs.setString(3,assetsDescription);
	          cs.setString(4,itemSpec);
	          cs.setString(5,unitOfMeasure);
	          cs.setInt(6,orgnizationId);
	          cs.execute();
	          s=cs.getString(2);	          
	      } catch (SQLException ex) {
	          Logger.logError(ex);
	      } catch (Throwable ex) {
	          Logger.logError(ex);
	      } finally {
	          DBManager.closeDBStatement(cs);
	      }
	      return s;
  }

   public String getManufactuerId(String manufacturerName){
       CallableStatement cs = null;
       String s="";
	      try {
	          String callStr = "{CALL dbo.SYN_FA_EFA_MANUFACTURER(?, ?)}";
	          cs = conn.prepareCall(callStr);
	          cs.registerOutParameter(1, java.sql.Types.VARCHAR); 
	          cs.setString(2,manufacturerName);
	          cs.execute();
	          s=cs.getString(1);	          
	      } catch (SQLException ex) {
	          Logger.logError(ex);
	      } catch (Throwable ex) {
	          Logger.logError(ex);
	      } finally {
	          DBManager.closeDBStatement(cs);
	      }
	      return s;
  }
   
   public String getDeptCode(String responsibilityDept) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getRespDetp(responsibilityDept);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
   
   public String getDeptName(String userId) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getDetpName(userId);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
   
   public int getOrgId(String responsibilityDept) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       int s1=0;
       SQLModel sqlModel = model.checkOrgId(responsibilityDept);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
			s1=Integer.parseInt(s);
		}
		return s1;
   }
   public String getPers(String responsibilityDept) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getPers(responsibilityDept);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
   public String getUserName(String responsibilityDept) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getUserName(responsibilityDept);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
   
//   public String getManufacturerId(String manufacturerName) throws QueryException{
//       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
//       String s="";
//       SQLModel sqlModel = model.getManufacturerId(manufacturerName);
//       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
//       simp.executeQuery();
//		RowSet rs = simp.getSearchResult();
//		if (rs.getSize()==1) {
//			s =rs.getRowValues().get(0).toString();
//			s=s.substring(1, s.length()-1);
//		}
//		return s;
//   }
   public String getCountyName(String countyId,String code) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getcountyName(countyId,code);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
   //barcode 输入参数
   public String getBarcode(String companyCode) throws QueryException{
	   String bc="";
	   String barcode="";
	   ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       SQLModel sqlModel = model.getBarcode(companyCode);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
	   RowSet rs = simp.getSearchResult();
	   if (rs.getSize()==1) {
			bc =rs.getRowValues().get(0).toString();
			bc=bc.substring(1, bc.length()-1);
			barcode=returnStr(bc);
	   }
	   return barcode;
   }
   
   public static String returnStr(String barcode){
	   String rStr = "";
	   String firStr=barcode.substring(0, 7);
	   int index=barcode.indexOf("-");
	   String str=barcode.substring(index+3);
	   Integer codeValue=Integer.parseInt(str);
	   codeValue++;
	   rStr=firStr+String.format("%06d", codeValue); 
	   return rStr;
   }
   
   
   public DTOSet getZeroTurnDto() throws QueryException {
       ZeroTurnHeaderDTO dto = (ZeroTurnHeaderDTO) dtoParameter;
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       SQLModel sqlModel = model.getZeroTurnDto();
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.setDTOClassName(ZeroTurnLineDTO.class.getName());
       simp.executeQuery();
       return simp.getDTOSet();
   }
   
   public String getCostCenterCode(String record) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getCostCenterCode(record);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
   public String getUnitOfMeasure(String zcml) throws QueryException{
       ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       String s="";
       SQLModel sqlModel = model.getUnitOfMeasure(zcml);
       SimpleQuery simp = new SimpleQuery(sqlModel, conn);
       simp.executeQuery();
		RowSet rs = simp.getSearchResult();
		if (rs.getSize()==1) {
			s =rs.getRowValues().get(0).toString();
			s=s.substring(1, s.length()-1);
		}
		return s;
   }
   public String validateFilter(String transId) throws QueryException{
	   String s="";
	   ZeroTurnModel model = (ZeroTurnModel) sqlProducer;
       SQLModel queryModel = new SQLModel();
       queryModel = model.isFilter(transId);
       SimpleQuery qeuryQuery = new SimpleQuery(queryModel, conn);
       qeuryQuery.executeQuery();
       RowSet rs = qeuryQuery.getSearchResult();
       int n = Integer.parseInt(rs.getRowValues().get(0).toString().substring(1, 2));
       if(n==0){
    	   s="0";
       }else {
    	   s="1";
       }
	   return s;
   }
   public File getExportFile(String barcode) throws DataTransException, SQLModelException {
       File file = null;
       ZeroTurnModel ztm = new ZeroTurnModel(userAccount, (ZeroTurnHeaderDTO) dtoParameter);
	   SQLModel sqlModel = ztm.getExport(barcode);
	   String reportTitle = "";

	   reportTitle = "零购转资数据";
	   String fileName = reportTitle + ".xls";
	   String filePath = WorldConstant.USER_HOME;
	   filePath += WorldConstant.FILE_SEPARATOR;
	   filePath += fileName;
	   TransRule rule = new TransRule();
	   rule.setDataSource(sqlModel);
	   rule.setSourceConn(conn);
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
       return file;
   }

   private Map getFieldMap() {
       Map fieldMap = new HashMap();
       fieldMap.put("MIS_PROCURE_CODE", "发货单编号");
       fieldMap.put("PROCURE_CODE", "MIS请购订单号");
       fieldMap.put("BARCODE", "标签号");
       fieldMap.put("COMPANY_CODE", "公司代码");
       fieldMap.put("ASSETS_DESCRIPTION", "资产名称");
       fieldMap.put("ITEM_SPEC", "规格型号");
       fieldMap.put("MANUFACTURER_NAME", "厂商");
       fieldMap.put("CONTENT_CODE", "资产类别");
       fieldMap.put("ITEM_QTY", "数量");
       fieldMap.put("YEARS", "使用年限");
       fieldMap.put("PRICE", "资产原值");
       fieldMap.put("START_DATE", "启用日期");
       fieldMap.put("OPE_ID", "业务平台");
       fieldMap.put("NLE_ID", "网络层次");
       fieldMap.put("IS_BULID", "是否共建");
       fieldMap.put("COST_CENTER_CODE", "成本中心");
       fieldMap.put("WORKORDER_OBJECT_NAME", "资产地点名称");
       fieldMap.put("OBJECT_NO", "资产所在地点编码");
       fieldMap.put("RESPONSIBILITY_USER", "资产责任人员工编号");
       fieldMap.put("RESPONSIBILITY_NAME", "资产责任人姓名");
       fieldMap.put("PROCURE_TYPE", "请购类别");
       fieldMap.put("RECEIVER", "收货人");
       fieldMap.put("RECEIVER_CONTACT", "收货人联系方式");
       fieldMap.put("EXPECTED_DATE", "预计到货日期");

       return fieldMap;
   }
   public static void main(String[] args) {
	String a="3.0";
	Double b=Double.parseDouble(a);
	int c=(int) (b*10/10);
//	DecimalFormat df = new DecimalFormat("#.##");
//
//    String account = df.format(Double.parseDouble(a));

	System.out.println(c);
}
   
}
