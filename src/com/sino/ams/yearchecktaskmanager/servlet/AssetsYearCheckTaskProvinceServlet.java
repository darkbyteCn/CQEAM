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
 * 省公司发起年度盘点任务工单
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
        	//下发盘点任务的头信息
			req2DTO.setDTOClassName(AssetsYearCheckTaskHeaderDTO.class.getName());
			AssetsYearCheckTaskHeaderDTO headerDto = (AssetsYearCheckTaskHeaderDTO) req2DTO.getDTO(req);
			System.out.println("headerDTO:"+headerDto);
			//下发盘点任务的基准日信息
			req2DTO.setDTOClassName(AssetsYearCheckTaskBaseDateDTO.class.getName());
			AssetsYearCheckTaskBaseDateDTO baseDateDto = (AssetsYearCheckTaskBaseDateDTO) req2DTO.getDTO(req);
			System.out.println("baseDateDto:"+baseDateDto);

			String action = headerDto.getAct();
			conn = getDBConnection(req);
			//创建DAO，传的headerDto
			AssetsYearCheckTaskProvinceDAO checkTaskProvinceDAO = new AssetsYearCheckTaskProvinceDAO(user, headerDto, conn);
			
			headerDto = fillHeaderData(checkTaskProvinceDAO,headerDto, user, conn);
			baseDateDto = fillBaseDateData(checkTaskProvinceDAO,baseDateDto,user,conn);
			String assetsBigClassHtml = this.getAssetsBigClass(headerDto); //资产大类
			
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
				//下发任务
				req2DTO.setDTOClassName(AssetsYearCheckTaskLineDTO.class.getName());
				req2DTO.setIgnoreFields(AssetsYearCheckTaskHeaderDTO.class);
	            DTOSet lineSet = req2DTO.getDTOSet(req);
	            System.out.println("lineSet size:"+lineSet.getSize());
	            for (int i = 0; i < lineSet.getSize(); i++){
	            	AssetsYearCheckTaskLineDTO lineDto = (AssetsYearCheckTaskLineDTO) lineSet.getDTO(i);
	            	System.out.println(lineDto);
	            }
	            //创建年度任务
	            //保存基准日信息
	            //创建已下发的任务
	            //如果基准日期为空 ,说明是第一次创建和下发
	            if(baseDateDto.getBaseDateId().equals("")){
	            	System.out.println("first time create and send ...");
	            	AssetsYearCheckTaskLineDTO yearlineDTO = this.createYearLineDTO(headerDto);
	            	baseDateDto = this.createBaseDateDTO(baseDateDto);
	            	headerDto = this.createHeaderDTO(headerDto);
	            	checkTaskProvinceDAO.createAll(yearlineDTO,baseDateDto,lineSet);
	            }else{
	            	System.out.println("just send...");
	            	//仅下发任务即可
	            	checkTaskProvinceDAO.createOrder(lineSet);
	            }
	            req.setAttribute("ORDER_HEAD_DATA", headerDto);
	            req.setAttribute("ORDER_BASE_DATE", baseDateDto);
	            req.setAttribute("ASSETS_BIG_CLASS", assetsBigClassHtml);
	            req.setAttribute("ORDER_LINE_DATA_DETAIL",lineSet);
	            message.setIsError(false);
	            message.setMessageValue("下发年度盘点任务成功");
	            forwardURL = "/yearchecktaskmanager/assetsYearCheckTaskProvinceDetail.jsp";
			}
		} catch (Exception e){
			message.setIsError(true);
			message.setMessageValue("出现错误，请联系管理员！");
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
	
	//基准日开始日期 是当前日期上个月份的最后一天，默认为次值，不允许修改。
	public String getCheckBaseDateFrom(){
		String  strDate = null;
		Calendar calender = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try{
			int lastMmonth = calender.get(Calendar.MONTH)-1 ; //去系统日期的上一个月份
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
	//获取资产大类信息
	private String getAssetsBigClass(AssetsYearCheckTaskHeaderDTO headerDto){
		if(headerDto.getAssetsBigClass().equals("")){
			headerDto.setAssetsBigClass(AssetsCheckTaskConstant.ASSETS_BIG_CLASS_ALL);
		}
		//非实地资产种类
		 StringBuffer htmlDataClass= new StringBuffer();
           Map dataClassMap = new LinkedMap();
           dataClassMap.put("","--请选择--");
           dataClassMap.put(AssetsCheckTaskConstant.ASSETS_BIG_CLASS_MIS,"上市固定资产");
           dataClassMap.put(AssetsCheckTaskConstant.ASSETS_BIG_CLASS_TD,"TD固定资产");
           dataClassMap.put(AssetsCheckTaskConstant.ASSETS_BIG_CLASS_TF,"通服资产");
           dataClassMap.put(AssetsCheckTaskConstant.ASSETS_BIG_CLASS_TT,"铁通城域网资产");
           dataClassMap.put(AssetsCheckTaskConstant.ASSETS_BIG_CLASS_ALL,"全部");
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
	
	//根据OU去掉重复的
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

	//补充头信息
	private AssetsYearCheckTaskHeaderDTO fillHeaderData(AssetsYearCheckTaskProvinceDAO checkTaskProvinceDAO,AssetsYearCheckTaskHeaderDTO dto, SfUserDTO user, Connection conn)
    throws DTOException, QueryException, CalendarException
    {
	    dto.setCreatedBy(user.getUserId());
	    dto.setTaskName(AssetsCheckTaskConstant.YEAR_TASK_NAME);
     return dto;
   }
	//补充基准日信息
	private AssetsYearCheckTaskBaseDateDTO fillBaseDateData(AssetsYearCheckTaskProvinceDAO checkTaskProvinceDAO,AssetsYearCheckTaskBaseDateDTO dto, SfUserDTO user, Connection conn)
    throws DTOException, QueryException, CalendarException, ContainerException
    {
	  //从数据获取，是否已经存在基准日期间信息
		AssetsYearCheckTaskBaseDateDTO queryDto = checkTaskProvinceDAO.getBaseDatePeriod();
		if(queryDto!=null){
			return queryDto;
		}
		dto.setChkYearTaskOrderNumber(AssetsCheckTaskConstant.ORDER_NUMBER);
		//---基准日期间开始日期默认是上个月的最后一天
		dto.setCheckBaseDateFrom(this.getCheckBaseDateFrom());
		//----
        return dto;
   } 
	
	private AssetsYearCheckTaskLineDTO createYearLineDTO(AssetsYearCheckTaskHeaderDTO headerDTO){
		AssetsYearCheckTaskLineDTO linedto = new AssetsYearCheckTaskLineDTO();
		linedto.setTaskOrderId(CommonUtil.getUUID());//主键
		linedto.setOrderName(headerDTO.getTaskName());//年度任务名称
		linedto.setOrderNumber(CommonUtil.getYearCheckOrder());//年度任务编号
		linedto.setOrderType(AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK);//工单类型
		linedto.setOrderStatus(AssetsCheckTaskConstant.DO_SEND);//已下发
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
