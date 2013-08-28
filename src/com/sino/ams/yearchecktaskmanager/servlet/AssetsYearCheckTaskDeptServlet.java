package com.sino.ams.yearchecktaskmanager.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dao.AssetsYearCheckTaskCityDAO;
import com.sino.ams.yearchecktaskmanager.dao.AssetsYearCheckTaskDeptDAO;
import com.sino.ams.yearchecktaskmanager.dao.AssetsYearCheckTaskProvinceDAO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskBaseDateDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskHeaderDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskLineDTO;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant;
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
 * 部门下发盘点任务工单
 * @author Administrator
 *
 */
public class AssetsYearCheckTaskDeptServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		 String forwardURL = "";
	     Message message = SessionUtil.getMessage(req);
	     message = new Message();
	     Connection conn = null;
        try {
        	Request2DTO req2DTO = new Request2DTO();
        	SfUserDTO user = (SfUserDTO) getUserAccount(req);
        	//下发盘点任务的头信息
			req2DTO.setDTOClassName(AssetsYearCheckTaskHeaderDTO.class.getName());
			AssetsYearCheckTaskHeaderDTO headerDto = (AssetsYearCheckTaskHeaderDTO) req2DTO.getDTO(req);
			System.out.println("headerDTO:"+headerDto);
			//下发盘点任务的基准期间信息
			req2DTO.setDTOClassName(AssetsYearCheckTaskBaseDateDTO.class.getName());
			AssetsYearCheckTaskBaseDateDTO baseDateDto = (AssetsYearCheckTaskBaseDateDTO) req2DTO.getDTO(req);
			
			//地市基准日
			String cityBaseDateParameter = req.getParameter("cityBaseDate");
			
			String action = headerDto.getAct();
			conn = getDBConnection(req);
			//创建DAO，传的headerDto
			AssetsYearCheckTaskProvinceDAO checkTaskProvinceDAO = new AssetsYearCheckTaskProvinceDAO(user, headerDto, conn);
			checkTaskProvinceDAO.setServletConfig(getServletConfig(req));
			
			AssetsYearCheckTaskCityDAO checkTastCityDAO = new AssetsYearCheckTaskCityDAO(user, headerDto, conn);
			checkTastCityDAO.setServletConfig(getServletConfig(req));
			
			AssetsYearCheckTaskDeptDAO deptDAO = new AssetsYearCheckTaskDeptDAO(user,headerDto,conn);
			deptDAO.setServletConfig(getServletConfig(req));
			
			//省公司的基准日期间和任务期间
			baseDateDto = fillBaseDateData(checkTaskProvinceDAO,baseDateDto,user,conn);
			//地市基准日
			String cityBaseDate = getCityBaseDate(checkTaskProvinceDAO);
			System.out.println("cityBaseDate="+cityBaseDate);
			
			String fromAction = req.getParameter("action");
			if(fromAction!=null && fromAction.equals("fromRemain")){
				//任务提醒
				//父级任务信息
				String parentOrderNumber = req.getParameter("parentOrderNumber");
				String parentOrderName = req.getParameter("orderName");
				String parentOrderType = req.getParameter("orderType");//工单类型
				headerDto.setParentOrderNumber(parentOrderNumber);
				headerDto.setParentOrderName(parentOrderName);
				headerDto.setParentOrderType(parentOrderType);
				if(parentOrderType.equals(AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK)){
					headerDto.setParentOrderTypeName("年度盘点任务");
				}else if(parentOrderType.equals(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_SOFTWARE)){
					headerDto.setParentOrderTypeName("非实地[软件类]");
				}else if(parentOrderType.equals(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_CLIENT)){
					headerDto.setParentOrderTypeName("非实地[客户端类]");
				}else if(parentOrderType.equals(AssetsCheckTaskConstant.ORDER_TYPE_NON_ADDRESS_PIPELINE)){
					headerDto.setParentOrderTypeName("非实地[光缆、杆路及管道类]");
				}else if(parentOrderType.equals(AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_WIRELESS)){
					headerDto.setParentOrderTypeName("实地无线类");
				}else if(parentOrderType.equals(AssetsCheckTaskConstant.ORDER_TYPE_ADDRESS_NON_WIRELESS)){
					headerDto.setParentOrderTypeName("实地非无线类");
				}
				
				req.setAttribute("CITY_BASE_DATE", cityBaseDate);
				headerDto.setCalPattern(LINE_PATTERN);
				req.setAttribute("ORDER_HEAD_DATA", headerDto);
				req.setAttribute("ORDER_BASE_DATE", baseDateDto);
				forwardURL = "/yearchecktaskmanager/assetsYearCheckTaskDept.jsp";
			}else{
				if(action.equals("")) {
					if(cityBaseDate!=null){
						req.setAttribute("CITY_BASE_DATE", cityBaseDate);
					}else{
						req.setAttribute("CITY_BASE_DATE", "");
					}
					headerDto.setCalPattern(LINE_PATTERN);
					req.setAttribute("ORDER_HEAD_DATA", headerDto);
					req.setAttribute("ORDER_BASE_DATE", baseDateDto);
					forwardURL = "/yearchecktaskmanager/assetsYearCheckTaskDept.jsp";
				}else if(action.equals("DO_SEND")){
					//下发任务
					req2DTO.setDTOClassName(AssetsYearCheckTaskLineDTO.class.getName());
					req2DTO.setIgnoreFields(AssetsYearCheckTaskHeaderDTO.class);
		            DTOSet lineSet = req2DTO.getDTOSet(req);
		            System.out.println("lineSet size:"+lineSet.getSize());
		            for (int i = 0; i < lineSet.getSize(); i++){
		            	AssetsYearCheckTaskLineDTO lineDto = (AssetsYearCheckTaskLineDTO) lineSet.getDTO(i);
		            	System.out.println(lineDto);
		            }
		            System.out.println("just send...");
		            //仅下发任务即可
		            deptDAO.createOrder(lineSet);
		            
		            req.setAttribute("CITY_BASE_DATE", cityBaseDateParameter);
		            req.setAttribute("ORDER_HEAD_DATA", headerDto);
		            req.setAttribute("ORDER_BASE_DATE", baseDateDto);
		            req.setAttribute("ORDER_LINE_DATA",lineSet);
		            message.setIsError(false);
		            message.setMessageValue("下发盘点任务成功");
		            forwardURL = "/yearchecktaskmanager/assetsYearCheckTaskDeptDetail.jsp";
				}
			}
		} catch (Exception e){
			message.setIsError(true);
			message.setMessageValue("出现错误，请联系管理员！");
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
	//基准日期间信息
	private AssetsYearCheckTaskBaseDateDTO fillBaseDateData(AssetsYearCheckTaskProvinceDAO checkTaskProvinceDAO,AssetsYearCheckTaskBaseDateDTO dto, SfUserDTO user, Connection conn)
    throws DTOException, QueryException, CalendarException, ContainerException
    {
	  //从数据获取，已经存在基准日期间信息
		AssetsYearCheckTaskBaseDateDTO queryDto = checkTaskProvinceDAO.getBaseDatePeriod();
		if(queryDto!=null){
			return queryDto;
		}
        return dto;
   } 
	//获取地市基准日
	private String getCityBaseDate(AssetsYearCheckTaskProvinceDAO checkTaskProvinceDAO) throws QueryException, ContainerException {
		AssetsYearCheckTaskBaseDateDTO dto = checkTaskProvinceDAO.getBaseDateCity();
		if(dto!=null){
			return dto.getCheckBaseDateCity();
		}else {
			return null;
		}
	}
}
