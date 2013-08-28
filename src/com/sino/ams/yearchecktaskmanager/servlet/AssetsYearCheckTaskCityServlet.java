package com.sino.ams.yearchecktaskmanager.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.LinkedMap;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dao.AssetsYearCheckTaskCityDAO;
import com.sino.ams.yearchecktaskmanager.dao.AssetsYearCheckTaskProvinceDAO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckSendOneTimeDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskBaseDateDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskHeaderDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskLineDTO;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant;
import com.sino.ams.yearchecktaskmanager.util.CommonUtil;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * �����·��̵����񹤵�
 * @author Administrator
 *
 */
public class AssetsYearCheckTaskCityServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		 String forwardURL = "";
	     Message message = SessionUtil.getMessage(req);
	     message = new Message();
	     Connection conn = null;
        try {
        	Request2DTO req2DTO = new Request2DTO();
        	SfUserDTO user = (SfUserDTO) getUserAccount(req);
        	//�·��̵������ͷ��Ϣ
			req2DTO.setDTOClassName(AssetsYearCheckTaskHeaderDTO.class.getName());
			AssetsYearCheckTaskHeaderDTO headerDto = (AssetsYearCheckTaskHeaderDTO) req2DTO.getDTO(req);
			System.out.println("headerDTO:"+headerDto);
			//�·��̵�����Ļ�׼�ڼ���Ϣ
			req2DTO.setDTOClassName(AssetsYearCheckTaskBaseDateDTO.class.getName());
			AssetsYearCheckTaskBaseDateDTO baseDateDto = (AssetsYearCheckTaskBaseDateDTO) req2DTO.getDTO(req);
			
			//���еĻ�׼�պ��·�������Ϣ
			req2DTO.setDTOClassName(AssetsYearCheckTaskBaseDateDTO.class.getName());
			AssetsYearCheckTaskBaseDateDTO cityBaseDto = (AssetsYearCheckTaskBaseDateDTO) req2DTO.getDTO(req);
			System.out.println("cityBaseDto:"+cityBaseDto);
			
			//���л�׼��
			String isExsitsCityBaseDate = req.getParameter("cityBaseDateIsExists");
			
			//�ʲ�����������
			String htmlDataClass = this.getAssetsType(headerDto);
			//��ʵ���ʲ�������
			String nonAddressCageroy = this.getnonAddressCageroy(headerDto);
			
			String action = headerDto.getAct();
			conn = getDBConnection(req);
			//����DAO������headerDto
			AssetsYearCheckTaskProvinceDAO checkTaskProvinceDAO = new AssetsYearCheckTaskProvinceDAO(user, headerDto, conn);
			checkTaskProvinceDAO.setServletConfig(getServletConfig(req));
			
			AssetsYearCheckTaskCityDAO checkTastCityDAO = new AssetsYearCheckTaskCityDAO(user, headerDto, conn);
			checkTastCityDAO.setServletConfig(getServletConfig(req));
			
			//ʡ��˾�Ļ�׼���ڼ�������ڼ�
			baseDateDto = fillBaseDateData(checkTaskProvinceDAO,baseDateDto,user,conn);
			//���л�׼��
			AssetsYearCheckTaskBaseDateDTO cityBaseDate = getCityBaseDate(checkTaskProvinceDAO);
			System.out.println("cityBaseDate="+cityBaseDate);
			
			String fromAction = req.getParameter("action");
			if(fromAction!=null && fromAction.equals("fromRemain ")){
				//�������ӹ���ʱ
				String parentOrderNumber = req.getParameter("parentOrderNumber");
				System.out.println("action fromRemain,parentOrderNumber="+parentOrderNumber);
				
				req.setAttribute("fromRemain_parentOrderNumber",parentOrderNumber);
				req.setAttribute("CITY_BASE_DATE", cityBaseDate);
				req.setAttribute("CITY_BASE_DATE_IS_EXISTS", "Y");
				headerDto.setCalPattern(LINE_PATTERN);
				req.setAttribute("ORDER_HEAD_DATA", headerDto);
				req.setAttribute("ORDER_BASE_DATE", baseDateDto);
				req.setAttribute("ASSETS_CLASS", htmlDataClass.toString());
				req.setAttribute("NON_ADDRESS_CATEGORY", nonAddressCageroy);
				forwardURL = "/yearchecktaskmanager/assetsYearCheckTaskCity.jsp";
			}else{//ϵͳ�˵�����
				if (action.equals("")) {
					if(cityBaseDate!=null){
						req.setAttribute("CITY_BASE_DATE", cityBaseDate);
						req.setAttribute("CITY_BASE_DATE_IS_EXISTS", "Y");
					}else{
						req.setAttribute("CITY_BASE_DATE", null);
						req.setAttribute("CITY_BASE_DATE_IS_EXISTS", "N");
						AssetsYearCheckTaskBaseDateDTO dto = new AssetsYearCheckTaskBaseDateDTO();
						req.setAttribute("SOFT_METHOD", this.getSoftWareMethod(dto));
						req.setAttribute("CLIENT_METHOD", this.getClientMethod(dto));
						req.setAttribute("PIPELINE_METHOD", this.getPipeLineMethod(dto));
					}
					headerDto.setCalPattern(LINE_PATTERN);
					req.setAttribute("ORDER_HEAD_DATA", headerDto);
					req.setAttribute("ORDER_BASE_DATE", baseDateDto);
					req.setAttribute("ASSETS_CLASS", htmlDataClass.toString());
					req.setAttribute("NON_ADDRESS_CATEGORY", nonAddressCageroy);
					
					forwardURL = "/yearchecktaskmanager/assetsYearCheckTaskCity.jsp";
				}else if(action.equals("DO_SEND")){
					//�·�����
					req2DTO.setDTOClassName(AssetsYearCheckTaskLineDTO.class.getName());
					req2DTO.setIgnoreFields(AssetsYearCheckTaskHeaderDTO.class);
		            DTOSet lineSet = req2DTO.getDTOSet(req);
		           /* System.out.println("lineSet size:"+lineSet.getSize());
		            for (int i = 0; i < lineSet.getSize(); i++){
		            	AssetsYearCheckTaskLineDTO lineDto = (AssetsYearCheckTaskLineDTO) lineSet.getDTO(i);
		            	System.out.println(lineDto);
		            }*/
		            //�жϻ�׼���Ƿ���Ҫ�½�
		            System.out.println("isExsitsCityBaseDate="+isExsitsCityBaseDate);
		            String result = "";
		            if(isExsitsCityBaseDate.equals("N")){
		            	//�����׼��
		            	checkTastCityDAO.saveBaseDateCity(cityBaseDto);
		            	//����
			            result = checkTastCityDAO.snapshotData(user.getOrganizationId(),cityBaseDto.getCheckBaseDateCity());
		            }else{
		            	//System.out.println("����");
		            }
		            //���·����񼴿�
		            checkTastCityDAO.createOrder(lineSet);
		            
		            if(result.equals("N")){
		            	 message.setIsError(true);
				         message.setMessageValue("�·��̵�����ɹ�,����������ʧ�ܣ�����ϵ����Ա��");
		            }else{
		            	 message.setIsError(false);
				         message.setMessageValue("�·��̵�����ɹ�");
		            }
		            
		            req.setAttribute("CITY_BASE_DATE", getCityBaseDate(checkTaskProvinceDAO));
					req.setAttribute("CITY_BASE_DATE_IS_EXISTS", "Y");
		            req.setAttribute("ORDER_HEAD_DATA", headerDto);
		            req.setAttribute("ORDER_BASE_DATE", baseDateDto);
		            req.setAttribute("ASSETS_CLASS", htmlDataClass.toString());
		            req.setAttribute("NON_ADDRESS_CATEGORY", nonAddressCageroy);
		            req.setAttribute("ORDER_LINE_DATA",lineSet);
		           
		            forwardURL = "/yearchecktaskmanager/assetsYearCheckTaskCityDetail.jsp";
				}else if(action.equals("DO_SEND_ONE_TIME")){
					//һ�����·���������
					System.out.println("222");
					if(isExsitsCityBaseDate.equals("N")){
			            	checkTastCityDAO.saveBaseDateCity(cityBaseDto);
			            }else{
			            	System.out.println("����");
			            }
			            System.out.println("just send...");
			            List<AssetsYearCheckSendOneTimeDTO> resultList = checkTastCityDAO.ceateAllOrder(cityBaseDto);
			            req.setAttribute("CITY_BASE_DATE", getCityBaseDate(checkTaskProvinceDAO));
						req.setAttribute("CITY_BASE_DATE_IS_EXISTS", "Y");
			            req.setAttribute("ORDER_HEAD_DATA", headerDto);
			            req.setAttribute("ORDER_BASE_DATE", baseDateDto);
			            req.setAttribute("NON_ADDRESS_CATEGORY", nonAddressCageroy);
			            req.setAttribute("ORDER_LINE_DATA",resultList);
			            message.setIsError(false);
			            message.setMessageValue("�·��̵�����ɹ�");
			            forwardURL = "/yearchecktaskmanager/assetsYearCheckTaskCityOneTimeDetail.jsp";
				}
			}
		} catch (Exception e){
			message.setIsError(true);
			message.setMessageValue("���ִ�������ϵ����Ա��");
			forwardURL = MessageConstant.MSG_PRC_SERVLET;;
		}finally{
			 ServletForwarder forwarder;
		     closeDBConnection(conn);
		     setHandleMessage(req, message);
		     if (!StrUtil.isEmpty(forwardURL)) {
		        forwarder = new ServletForwarder(req, res);
		        forwarder.forwardView(forwardURL);
		     }
	    }
	}
	
	//��ȡ���л�׼�պͷ�ʵ���ʲ��·�������Ϣ
	private AssetsYearCheckTaskBaseDateDTO getCityBaseDate(AssetsYearCheckTaskProvinceDAO checkTaskProvinceDAO) throws QueryException, ContainerException {
		return checkTaskProvinceDAO.getBaseDateCity();
	}
	private String getSoftWareMethod(AssetsYearCheckTaskBaseDateDTO baseDateDto ){
		 StringBuffer htmlDataClass= new StringBuffer();
           Map dataClassMap = new LinkedMap();
           
           dataClassMap.put("","--��ѡ��--");
           dataClassMap.put(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER,"���й���Ա");
           dataClassMap.put(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER, "�����ʲ�����Ա");
           dataClassMap.put(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON,"�ʲ������˻��ض���Ա");
          
           for(Iterator it=dataClassMap.keySet().iterator();it.hasNext();){
           	String key =(String) it.next();
           	if(key.equals(baseDateDto.getSoftWareMethod())){
           		htmlDataClass.append("<option value=\""+key+"\" selected >"+dataClassMap.get(key)+"</option>");
           	}else{
           		htmlDataClass.append("<option value=\""+key+"\">"+dataClassMap.get(key)+"</option>");
           	}
           }
           System.out.println(htmlDataClass.toString());
          return htmlDataClass.toString();
	}
	private String getClientMethod(AssetsYearCheckTaskBaseDateDTO baseDateDto ){
		 StringBuffer htmlDataClass= new StringBuffer();
          Map dataClassMap = new LinkedMap();
          
          dataClassMap.put("","--��ѡ��--");
          dataClassMap.put(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER,"���й���Ա");
          dataClassMap.put(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER, "�����ʲ�����Ա");
          dataClassMap.put(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON,"�ʲ������˻��ض���Ա");
         
          for(Iterator it=dataClassMap.keySet().iterator();it.hasNext();){
          	String key =(String) it.next();
          	if(key.equals(baseDateDto.getClientMethod())){
          		htmlDataClass.append("<option value=\""+key+"\" selected >"+dataClassMap.get(key)+"</option>");
          	}else{
          		htmlDataClass.append("<option value=\""+key+"\">"+dataClassMap.get(key)+"</option>");
          	}
          }
          System.out.println(htmlDataClass.toString());
         return htmlDataClass.toString();
	}
	private String getPipeLineMethod(AssetsYearCheckTaskBaseDateDTO baseDateDto ){
		 StringBuffer htmlDataClass= new StringBuffer();
          Map dataClassMap = new LinkedMap();
          
          dataClassMap.put("","--��ѡ��--");
          dataClassMap.put(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER,"���й���Ա");
          dataClassMap.put(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER, "�����ʲ�����Ա");
          dataClassMap.put(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON,"�ʲ������˻��ض���Ա");
         
          for(Iterator it=dataClassMap.keySet().iterator();it.hasNext();){
          	String key =(String) it.next();
          	if(key.equals(baseDateDto.getPipeLineMethod())){
          		htmlDataClass.append("<option value=\""+key+"\" selected >"+dataClassMap.get(key)+"</option>");
          	}else{
          		htmlDataClass.append("<option value=\""+key+"\">"+dataClassMap.get(key)+"</option>");
          	}
          }
          System.out.println(htmlDataClass.toString());
         return htmlDataClass.toString();
	}
	
	private String getAssetsType(AssetsYearCheckTaskHeaderDTO headerDto){
		//�ʲ�����
		 StringBuffer htmlDataClass= new StringBuffer();
           Map dataClassMap = new LinkedMap();
           
           dataClassMap.put("","--��ѡ��--");
           dataClassMap.put(AssetsCheckTaskConstant.ASSETS_TYPE_ADDRESS_WIRELESS,"ʵ�������ʲ�");
           dataClassMap.put(AssetsCheckTaskConstant.ASSETS_TYPE_ADDRESS_NON_WIRELESS, "ʵ�ط������ʲ�");
           dataClassMap.put(AssetsCheckTaskConstant.ASSETS_TYPE_NON_ADDRESS,"��ʵ���ʲ�");
          
           for(Iterator it=dataClassMap.keySet().iterator();it.hasNext();){
           	String key =(String) it.next();
           	if(key.equals(headerDto.getAssetsType())){
           		htmlDataClass.append("<option value=\""+key+"\" selected >"+dataClassMap.get(key)+"</option>");
           	}else{
           		htmlDataClass.append("<option value=\""+key+"\">"+dataClassMap.get(key)+"</option>");
           	}
           }
           System.out.println(htmlDataClass.toString());
          return htmlDataClass.toString();
	}
	
	private String getnonAddressCageroy(AssetsYearCheckTaskHeaderDTO headerDto){
		//��ʵ���ʲ�����
		 StringBuffer htmlDataClass= new StringBuffer();
           Map dataClassMap = new LinkedMap();
           dataClassMap.put("","--��ѡ��--");
           dataClassMap.put(AssetsCheckTaskConstant.NON_ADDRESS_CATEGORY_SOFTWIRE,"�����");
           dataClassMap.put(AssetsCheckTaskConstant.NON_ADDRESS_CATEGORY_CLIENT,"�ͻ�����");
           dataClassMap.put(AssetsCheckTaskConstant.NON_ADDRESS_CATEGORY_PIPELINE,"���¡���·���ܵ���");
           for(Iterator it=dataClassMap.keySet().iterator();it.hasNext();){
           	String key =(String) it.next();
           	if(key.equals(headerDto.getNonAddressCategory())){
           		htmlDataClass.append("<option value=\""+key+"\" selected >"+dataClassMap.get(key)+"</option>");
           	}else{
           		htmlDataClass.append("<option value=\""+key+"\">"+dataClassMap.get(key)+"</option>");
           	}
           }
           System.out.println(htmlDataClass.toString());
          return htmlDataClass.toString();
	}

	
	//��׼���ڼ���Ϣ
	private AssetsYearCheckTaskBaseDateDTO fillBaseDateData(AssetsYearCheckTaskProvinceDAO checkTaskProvinceDAO,AssetsYearCheckTaskBaseDateDTO dto, SfUserDTO user, Connection conn)
    throws DTOException, QueryException, CalendarException, ContainerException
    {
	  //�����ݻ�ȡ���Ѿ����ڻ�׼���ڼ���Ϣ
		AssetsYearCheckTaskBaseDateDTO queryDto = checkTaskProvinceDAO.getBaseDatePeriod();
		if(queryDto!=null){
			return queryDto;
		}
        return dto;
   } 
	
	private AssetsYearCheckTaskHeaderDTO createHeaderDTO(AssetsYearCheckTaskHeaderDTO headerDTO){
		headerDTO.setHeaderId(CommonUtil.getUUID());
		return headerDTO;
	}
}
