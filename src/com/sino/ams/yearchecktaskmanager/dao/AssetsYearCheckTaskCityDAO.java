package com.sino.ams.yearchecktaskmanager.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckSendOneTimeDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskBaseDateDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskHeaderDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskLineDTO;
import com.sino.ams.yearchecktaskmanager.model.AssetsYearCheckTaskCityModel;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskOrderGeneretor;
import com.sino.ams.yearchecktaskmanager.util.CommonUtil;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

public class AssetsYearCheckTaskCityDAO extends AMSProcedureBaseDAO{
	
	private AssetsYearCheckTaskCityModel myModel;
	public AssetsYearCheckTaskCityDAO(SfUserDTO userAccount, AssetsYearCheckTaskHeaderDTO dtoParameter,
			Connection conn) {
		super(userAccount, dtoParameter, conn);
		myModel = new  AssetsYearCheckTaskCityModel((SfUserDTO) userAccount,dtoParameter);
	}

	protected void initSQLProducer(BaseUserDTO arg0, DTO arg1) {
		AssetsYearCheckTaskHeaderDTO dtoPara = (AssetsYearCheckTaskHeaderDTO) dtoParameter;
		sqlProducer = new AssetsYearCheckTaskCityModel((SfUserDTO) userAccount,dtoPara);
	}
	
	//保存地市基准日
	public void saveBaseDateCity(AssetsYearCheckTaskBaseDateDTO cityBaseDto) throws DataHandleException {
		//AssetsYearCheckTaskCityModel model = (AssetsYearCheckTaskCityModel) sqlProducer;
        SQLModel sqlModel =myModel.getSaveCityBaseDateModel(cityBaseDto);
        DBOperator.updateRecord(sqlModel, conn);
	}
	
	public boolean createHeader(AssetsYearCheckTaskHeaderDTO headerDto) throws DataHandleException{
		//AssetsYearCheckTaskCityModel model = (AssetsYearCheckTaskCityModel) sqlProducer;
		AssetsYearCheckTaskHeaderDTO headerDTO = (AssetsYearCheckTaskHeaderDTO) dtoParameter;
		headerDTO.setHeaderId(CommonUtil.getUUID());
		setDTOParameter(headerDTO);
		SQLModel headerMdoel = myModel.getcreateTaskHeaderModel(headerDto);
		return DBOperator.updateRecord(headerMdoel, conn);
	}
	public boolean createOrderLines(DTOSet lineSet)throws Exception{
		boolean result = true;
		//AssetsYearCheckTaskCityModel model = (AssetsYearCheckTaskCityModel) sqlProducer;
		AssetsYearCheckTaskHeaderDTO headerDTO = (AssetsYearCheckTaskHeaderDTO) dtoParameter;
		AssetsCheckTaskOrderGeneretor orderGeneretor = new AssetsCheckTaskOrderGeneretor(conn,userAccount.getCompanyCode(),AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK);
		if(this.createHeader(headerDTO)){
			for (int i = 0; i < lineSet.getSize(); i++){
	         	AssetsYearCheckTaskLineDTO lineDto = (AssetsYearCheckTaskLineDTO) lineSet.getDTO(i);
	         	lineDto.setTaskOrderId(CommonUtil.getUUID());
	         	lineDto.setHeaderId(headerDTO.getHeaderId());
	         	lineDto.setOrderNumber(orderGeneretor.getOrderNum());
	         	lineDto.setOrderStatus(AssetsCheckTaskConstant.DO_CREATE);
	         	lineDto.setOrderLevel("3");
	         	lineDto.setIsLastLevel("N");
	         	if(headerDTO.getAssetsType().equals(AssetsCheckTaskConstant.ASSETS_TYPE_NON_ADDRESS)){
	         		lineDto.setImplementRoleName(AssetsCheckTaskConstant.ORDER_RECIVE_ROLE_TO_DEPT_FOR_NON_ADDRESS);
	         	}else if(headerDTO.getAssetsType().equals(AssetsCheckTaskConstant.ASSETS_TYPE_ADDRESS_WIRELESS)){
	         		lineDto.setImplementRoleName(AssetsCheckTaskConstant.ORDER_RECIVE_ROLE_TO_DEPT_FOR_ADDRESS_WIRELESS);
	         	}else if(headerDTO.getAssetsType().endsWith(AssetsCheckTaskConstant.ASSETS_TYPE_ADDRESS_NON_WIRELESS)){
	         		lineDto.setImplementRoleName(AssetsCheckTaskConstant.ORDER_RECIVE_ROLE_TO_DEPT_FOR_ADDRESS_NON_WIRELESS);
	         	}
	         	
	         	SQLModel headerModel = myModel.getCreateTaksLineModel(lineDto);
				boolean singleResult = DBOperator.updateRecord(headerModel, conn);
				if(singleResult){
					continue;
				}else{
					result = false;
					break;
				}
	         }
		}else{
			result = false;
		}
		return result;
	}
	public boolean createOrder(DTOSet lineSet) {
		boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            
            createOrderLines(lineSet); //创建下发任务
            
            operateResult= true;
        }catch (Exception ex) {
            ex.printStackTrace();
            operateResult = false;
        }  finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                } else {
                    conn.commit();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
	}
	//一次性创建
	public List<AssetsYearCheckSendOneTimeDTO> ceateAllOrder(AssetsYearCheckTaskBaseDateDTO cityBaseDto) {
		boolean operateResult = false;
        boolean autoCommit = true;
        List<AssetsYearCheckSendOneTimeDTO> resultList = new ArrayList<AssetsYearCheckSendOneTimeDTO>();
        AssetsYearCheckSendOneTimeDTO resultDto = new AssetsYearCheckSendOneTimeDTO();
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            //下发实地无线
            resultDto =  createlines("CITY_ADDRESS_FOR_CHECK_PERSON");
            resultList.add(resultDto);
            //下发实地非无线
            resultDto = createlines("CITY_ADDRESS_FOR_DEPT");
            resultList.add(resultDto);
            //非实地软件
            if(cityBaseDto.getSoftWareMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER)){
            	resultDto = createlines("CITY_NON_ADDRESS_SOFTEWARE_1");
            }else if(cityBaseDto.getSoftWareMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER)){
            	resultDto = createlines("CITY_NON_ADDRESS_SOFTEWARE_2");
            }else if(cityBaseDto.getSoftWareMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON)){
            	resultDto = createlines("CITY_NON_ADDRESS_SOFTEWARE_3");
            }
            resultList.add(resultDto);
            
            //非实地客户端
            if(cityBaseDto.getClientMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER)){
            	resultDto = createlines("CITY_NON_ADDRESS_CLIENT_1");
            }else if(cityBaseDto.getClientMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER)){
            	resultDto = createlines("CITY_NON_ADDRESS_CLIENT_2");
            }else if(cityBaseDto.getClientMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON)){
            	resultDto = createlines("CITY_NON_ADDRESS_CLIENT_3");
            }
            resultList.add(resultDto);
            //非实地管道
            if(cityBaseDto.getPipeLineMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER)){
            	resultDto = createlines("CITY_NON_ADDRESS_PIPELINE_1");
            }else if(cityBaseDto.getPipeLineMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER)){
            	resultDto = createlines("CITY_NON_ADDRESS_PIPELINE_2");
            }else if(cityBaseDto.getPipeLineMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON)){
            	resultDto = createlines("CITY_NON_ADDRESS_PIPELINE_3");
            }
            resultList.add(resultDto);
            operateResult= true;
        }catch (Exception ex) {
        	resultDto.setFlag("创建失败");
			resultDto.setErrorMessage(ex.getMessage());
        	resultList.add(resultDto);
            ex.printStackTrace();
            operateResult = false;
        }  finally {
            try {
                if (!operateResult) {
                	System.out.println("roll back");
                    conn.rollback();
                } else {
                    conn.commit();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return resultList;
	}
	//一次性下发的方法
	private AssetsYearCheckSendOneTimeDTO createlines(String lookUpName) throws Exception {
		AssetsYearCheckSendOneTimeDTO resultDto = new AssetsYearCheckSendOneTimeDTO();
		try{
			AssetsYearCheckTaskHeaderDTO headerDTO = (AssetsYearCheckTaskHeaderDTO) dtoParameter;
			AssetsYearCheckTaskHeaderDTO dto = new AssetsYearCheckTaskHeaderDTO();
			String headerId = CommonUtil.getUUID();
			dto.setHeaderId(headerId);
			dto.setParentOrderNumber(headerDTO.getParentOrderNumber());
			if(lookUpName.equals("CITY_ADDRESS_FOR_CHECK_PERSON")){
				dto.setAssetsType(AssetsCheckTaskConstant.ASSETS_TYPE_ADDRESS_WIRELESS);
			}else if(lookUpName.equals("CITY_ADDRESS_FOR_DEPT")){
				dto.setAssetsType(AssetsCheckTaskConstant.ASSETS_TYPE_ADDRESS_NON_WIRELESS);
			}
			else if(lookUpName.equals("CITY_NON_ADDRESS_SOFTEWARE_1") || lookUpName.equals("CITY_NON_ADDRESS_SOFTEWARE_2")
					||lookUpName.equals("CITY_NON_ADDRESS_SOFTEWARE_3")){
				dto.setAssetsType(AssetsCheckTaskConstant.ASSETS_TYPE_NON_ADDRESS);
				dto.setNonAddressCategory(AssetsCheckTaskConstant.NON_ADDRESS_CATEGORY_SOFTWIRE);
				if(lookUpName.equals("CITY_NON_ADDRESS_SOFTEWARE_1")){
					dto.setNonAddressDistributeMethod(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER);
				}else if(lookUpName.equals("CITY_NON_ADDRESS_SOFTEWARE_2")){
					dto.setNonAddressDistributeMethod(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER);
				}else if(lookUpName.equals("CITY_NON_ADDRESS_SOFTEWARE_3")){
					dto.setNonAddressDistributeMethod(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON);
				}
			}else if(lookUpName.equals("CITY_NON_ADDRESS_CLIENT_1")||lookUpName.equals("CITY_NON_ADDRESS_CLIENT_2") 
					|| lookUpName.equals("CITY_NON_ADDRESS_CLIENT_3")){
				dto.setAssetsType(AssetsCheckTaskConstant.ASSETS_TYPE_NON_ADDRESS);
				dto.setNonAddressCategory(AssetsCheckTaskConstant.NON_ADDRESS_CATEGORY_CLIENT);
				if(lookUpName.equals("CITY_NON_ADDRESS_CLIENT_1")){
					dto.setNonAddressDistributeMethod(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER);
				}else if(lookUpName.equals("CITY_NON_ADDRESS_CLIENT_2")){
					dto.setNonAddressDistributeMethod(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER);
				}else if(lookUpName.equals("CITY_NON_ADDRESS_CLIENT_3")){
					dto.setNonAddressDistributeMethod(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON);
				}
			}else if(lookUpName.equals("CITY_NON_ADDRESS_PIPELINE_1")||lookUpName.equals("CITY_NON_ADDRESS_PIPELINE_2")
					||lookUpName.equals("CITY_NON_ADDRESS_PIPELINE_3")){
				dto.setAssetsType(AssetsCheckTaskConstant.ASSETS_TYPE_NON_ADDRESS);
				dto.setNonAddressCategory(AssetsCheckTaskConstant.NON_ADDRESS_CATEGORY_PIPELINE);
				if(lookUpName.equals("CITY_NON_ADDRESS_PIPELINE_1")){
					dto.setNonAddressDistributeMethod(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER);
				}else if(lookUpName.equals("CITY_NON_ADDRESS_PIPELINE_2")){
					dto.setNonAddressDistributeMethod(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER);
				}else if(lookUpName.equals("CITY_NON_ADDRESS_PIPELINE_3")){
					dto.setNonAddressDistributeMethod(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON);
				}
			}
			this.createHeader(dto);
			
			SQLModel linesModel = myModel.getLinesModel(lookUpName);
			AssetsCheckTaskOrderGeneretor orderGeneretor = new AssetsCheckTaskOrderGeneretor(conn,userAccount.getCompanyCode(),AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK);
			RowSet rs = null;
			SimpleQuery sq = new SimpleQuery(linesModel, conn);
		    sq.executeQuery();
		    if (sq.hasResult()) {
		        rs = sq.getSearchResult();
		    }
		    if(rs!=null && rs.getSize()>0){
		    	resultDto.setOrderSum(rs.getSize());
		    	for(int i=0;i<rs.getSize();i++){
		    		 Row row = rs.getRow(i);
			         AssetsYearCheckTaskLineDTO lineDto = new AssetsYearCheckTaskLineDTO();
			         lineDto.setHeaderId(headerId);
			         lineDto.setTaskOrderId(CommonUtil.getUUID());
			         lineDto.setOrderNumber(orderGeneretor.getOrderNum());
			         lineDto.setOrderName(row.getStrValue("ORDER_NAME"));
			         lineDto.setOrderType(row.getStrValue("ORDER_TYPE"));
			         resultDto.setOrderType(row.getStrValue("ORDER_TYPE_NAME"));
			         lineDto.setImplementDeptId(row.getStrValue("IMPLEMENT_DEPT_ID"));
			         lineDto.setImplementDeptName(row.getStrValue("IMPLEMENT_DEPT_NAME"));
			         lineDto.setImplementName(row.getStrValue("IMPLEMENT_NAME"));
			         lineDto.setImplementOrganizationId(Integer.parseInt(String.valueOf(row.getValue("IMPLEMENT_ORGANIZATION_ID"))));
			         lineDto.setImplementBy(Integer.parseInt(String.valueOf(row.getValue("IMPLEMENT_BY"))));
			         lineDto.setOrderStatus(AssetsCheckTaskConstant.DO_CREATE);
			         lineDto.setOrderLevel("3");
			         lineDto.setIsLastLevel("N");
			         SQLModel headerModel = myModel.getCreateTaksLineModel(lineDto);
				     boolean singleResult = DBOperator.updateRecord(headerModel, conn);
					 if(singleResult){
						continue;
					}else{
						break;
					}
		    	}
		    }
		}catch(Exception e){
			resultDto.setFlag("创建失败");
			resultDto.setErrorMessage(e.getMessage());
			throw new Exception(e);
		}
		resultDto.setFlag("创建成功");
	    return resultDto;
	}
}
