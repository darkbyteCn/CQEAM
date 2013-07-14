package com.sino.ams.yearchecktaskmanager.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.LinkedMap;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dao.AssetsYearCheckTaskProvinceDAO;
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
 * ʡ��˾��������̵����񹤵�
 * @author Administrator
 *
 */
public class AssetsYearCheckTaskProvinceServlet extends BaseServlet{

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		 String forwardURL = "";
	     Message message = SessionUtil.getMessage(req);
	     message = new Message();
	     Connection conn = null;
        try {
        	Request2DTO req2DTO = new Request2DTO();
        	SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
        	System.out.println(user.getUserId());
        	//�·��̵������ͷ��Ϣ
			req2DTO.setDTOClassName(AssetsYearCheckTaskHeaderDTO.class.getName());
			AssetsYearCheckTaskHeaderDTO headerDto = (AssetsYearCheckTaskHeaderDTO) req2DTO.getDTO(req);
			System.out.println("headerDTO:"+headerDto);
			//�·��̵�����Ļ�׼����Ϣ
			req2DTO.setDTOClassName(AssetsYearCheckTaskBaseDateDTO.class.getName());
			AssetsYearCheckTaskBaseDateDTO baseDateDto = (AssetsYearCheckTaskBaseDateDTO) req2DTO.getDTO(req);
			System.out.println("baseDateDto:"+baseDateDto);

			String action = headerDto.getAct();
			conn = getDBConnection(req);
			//����DAO������headerDto
			AssetsYearCheckTaskProvinceDAO checkTaskProvinceDAO = new AssetsYearCheckTaskProvinceDAO(user, headerDto, conn);
			
			headerDto = fillHeaderData(checkTaskProvinceDAO,headerDto, user, conn);
			baseDateDto = fillBaseDateData(checkTaskProvinceDAO,baseDateDto,user,conn);
			String assetsBigClassHtml = this.getAssetsBigClass(headerDto); //�ʲ�����
			
			if (action.equals("")) {
				//headerDto = fillHeaderData(checkTaskProvinceDAO,headerDto, user, conn);
				//baseDateDto = fillBaseDateData(checkTaskProvinceDAO,baseDateDto,user,conn);
				DTOSet  allLineSet = checkTaskProvinceDAO.getAllOU(headerDto);
				DTOSet lineSet = this.deletRepeatCompany(allLineSet);
				headerDto.setCalPattern(LINE_PATTERN);
				req.setAttribute("ORDER_HEAD_DATA", headerDto);
				req.setAttribute("ORDER_BASE_DATE", baseDateDto);
				req.setAttribute("ASSETS_BIG_CLASS", assetsBigClassHtml);
				 req.setAttribute("ORDER_LINE_DATA_DETAIL",lineSet);
				forwardURL = "/yearchecktaskmanager/assetsYearCheckTaskProvince.jsp";
			}else if(action.equals("DO_SEND")){
				//�·�����
				req2DTO.setDTOClassName(AssetsYearCheckTaskLineDTO.class.getName());
				req2DTO.setIgnoreFields(AssetsYearCheckTaskHeaderDTO.class);
	            DTOSet lineSet = req2DTO.getDTOSet(req);
	            System.out.println("lineSet size:"+lineSet.getSize());
	            for (int i = 0; i < lineSet.getSize(); i++){
	            	AssetsYearCheckTaskLineDTO lineDto = (AssetsYearCheckTaskLineDTO) lineSet.getDTO(i);
	            	System.out.println(lineDto);
	            }
	            //�����������
	            //�����׼����Ϣ
	            //�������·�������
	            //�����׼����Ϊ�� ,˵���ǵ�һ�δ������·�
	            if(baseDateDto.getBaseDateId().equals("")){
	            	System.out.println("first time create and send ...");
	            	AssetsYearCheckTaskLineDTO yearlineDTO = this.createYearLineDTO(headerDto);
	            	baseDateDto = this.createBaseDateDTO(baseDateDto);
	            	headerDto = this.createHeaderDTO(headerDto);
	            	checkTaskProvinceDAO.createAll(yearlineDTO,baseDateDto,lineSet);
	            }else{
	            	System.out.println("just send...");
	            	//���·����񼴿�
	            	checkTaskProvinceDAO.createOrder(lineSet);
	            }
	            req.setAttribute("ORDER_HEAD_DATA", headerDto);
	            req.setAttribute("ORDER_BASE_DATE", baseDateDto);
	            req.setAttribute("ASSETS_BIG_CLASS", assetsBigClassHtml);
	            req.setAttribute("ORDER_LINE_DATA_DETAIL",lineSet);
	            message.setIsError(false);
	            message.setMessageValue("�·�����̵�����ɹ�");
	            forwardURL = "/yearchecktaskmanager/assetsYearCheckTaskProvinceDetail.jsp";
			}
		} catch (Exception e){
			message.setIsError(true);
			message.setMessageValue("���ִ�������ϵ����Ա��");
		    forwardURL = MessageConstant.MSG_PRC_SERVLET;
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
	public static void main(String[]args){
		AssetsYearCheckTaskProvinceServlet servlet = new AssetsYearCheckTaskProvinceServlet();
		System.out.println(servlet.getCheckBaseDateFrom());
	}
	
	//��׼�տ�ʼ���� �ǵ�ǰ�����ϸ��·ݵ����һ�죬Ĭ��Ϊ��ֵ���������޸ġ�
	public String getCheckBaseDateFrom(){
		String  strDate = null;
		Calendar calender = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try{
			int lastMmonth = calender.get(Calendar.MONTH)-1 ; //ȥϵͳ���ڵ���һ���·�
			Calendar lastCalendar = Calendar.getInstance();
			lastCalendar.set(Calendar.MONTH, lastMmonth);
			final int  lastDay = lastCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);   
			lastCalendar.set(Calendar.DAY_OF_MONTH, lastDay);
			strDate= format.format(lastCalendar.getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return strDate;
	}
	//��ȡ�ʲ�������Ϣ
	private String getAssetsBigClass(AssetsYearCheckTaskHeaderDTO headerDto){
		if(headerDto.getAssetsBigClass().equals("")){
			headerDto.setAssetsBigClass(AssetsCheckTaskConstant.ASSETS_BIG_CLASS_ALL);
		}
		//��ʵ���ʲ�����
		 StringBuffer htmlDataClass= new StringBuffer();
           Map dataClassMap = new LinkedMap();
           dataClassMap.put("","--��ѡ��--");
           dataClassMap.put(AssetsCheckTaskConstant.ASSETS_BIG_CLASS_MIS,"���й̶��ʲ�");
           dataClassMap.put(AssetsCheckTaskConstant.ASSETS_BIG_CLASS_TD,"TD�̶��ʲ�");
           dataClassMap.put(AssetsCheckTaskConstant.ASSETS_BIG_CLASS_TF,"ͨ���ʲ�");
           dataClassMap.put(AssetsCheckTaskConstant.ASSETS_BIG_CLASS_TT,"��ͨ�������ʲ�");
           dataClassMap.put(AssetsCheckTaskConstant.ASSETS_BIG_CLASS_ALL,"ȫ��");
           for(Iterator it=dataClassMap.keySet().iterator();it.hasNext();){
           	String key =(String) it.next();
           	if(key.equals(headerDto.getAssetsBigClass())){
           		htmlDataClass.append("<option value=\""+key+"\" selected >"+dataClassMap.get(key)+"</option>");
           	}else{
           		htmlDataClass.append("<option value=\""+key+"\">"+dataClassMap.get(key)+"</option>");
           	}
           }
          return htmlDataClass.toString();
	}
	
	//����OUȥ���ظ���
	public DTOSet deletRepeatCompany(DTOSet lineSet) throws DTOException{
		List<String> companyList = new ArrayList<String>();
		DTOSet noRepeatSet = new DTOSet();
		for(int i=0;i<lineSet.getSize();i++){
			AssetsYearCheckTaskLineDTO lineDto = (AssetsYearCheckTaskLineDTO)lineSet.getDTO(i);
			String companyCode = lineDto.getCompanyCode();
			if(!companyList.contains(companyCode)){
				noRepeatSet.addDTO(lineDto);
				companyList.add(companyCode);
			}else{
				continue;
			}
		}
		return noRepeatSet;
	}

	//����ͷ��Ϣ
	private AssetsYearCheckTaskHeaderDTO fillHeaderData(AssetsYearCheckTaskProvinceDAO checkTaskProvinceDAO,AssetsYearCheckTaskHeaderDTO dto, SfUserDTO user, Connection conn)
    throws DTOException, QueryException, CalendarException
    {
	    dto.setCreatedBy(user.getUserId());
	    dto.setTaskName(AssetsCheckTaskConstant.YEAR_TASK_NAME);
     return dto;
   }
	//�����׼����Ϣ
	private AssetsYearCheckTaskBaseDateDTO fillBaseDateData(AssetsYearCheckTaskProvinceDAO checkTaskProvinceDAO,AssetsYearCheckTaskBaseDateDTO dto, SfUserDTO user, Connection conn)
    throws DTOException, QueryException, CalendarException, ContainerException
    {
	  //�����ݻ�ȡ���Ƿ��Ѿ����ڻ�׼���ڼ���Ϣ
		AssetsYearCheckTaskBaseDateDTO queryDto = checkTaskProvinceDAO.getBaseDatePeriod();
		if(queryDto!=null){
			return queryDto;
		}
		dto.setChkYearTaskOrderNumber(AssetsCheckTaskConstant.ORDER_NUMBER);
		//---��׼���ڼ俪ʼ����Ĭ�����ϸ��µ����һ��
		dto.setCheckBaseDateFrom(this.getCheckBaseDateFrom());
		//----
        return dto;
   } 
	
	private AssetsYearCheckTaskLineDTO createYearLineDTO(AssetsYearCheckTaskHeaderDTO headerDTO){
		AssetsYearCheckTaskLineDTO linedto = new AssetsYearCheckTaskLineDTO();
		linedto.setTaskOrderId(CommonUtil.getUUID());//����
		linedto.setOrderName(headerDTO.getTaskName());//�����������
		linedto.setOrderNumber(CommonUtil.getYearCheckOrder());//���������
		linedto.setOrderType(AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK);//��������
		linedto.setOrderStatus(AssetsCheckTaskConstant.DO_SEND);//���·�
		linedto.setOrderLevel("1");
		linedto.setIsLastLevel("N");
		return linedto;
	}
	
	private AssetsYearCheckTaskBaseDateDTO  createBaseDateDTO(AssetsYearCheckTaskBaseDateDTO  baseDateDto){
	 	baseDateDto.setBaseDateId(CommonUtil.getUUID());
    	baseDateDto.setChkYearTaskOrderNumber(CommonUtil.getYearCheckOrder());
    	baseDateDto.setBaseDateType("1");
    	baseDateDto.setEnabled("Y");
		return baseDateDto;
	}
	
	private AssetsYearCheckTaskHeaderDTO createHeaderDTO(AssetsYearCheckTaskHeaderDTO headerDTO){
		headerDTO.setHeaderId(CommonUtil.getUUID());
		headerDTO.setParentOrderNumber(CommonUtil.getYearCheckOrder());
		return headerDTO;
	}
}
