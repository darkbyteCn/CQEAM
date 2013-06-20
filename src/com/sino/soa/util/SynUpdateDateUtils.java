package com.sino.soa.util;

import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 
 * @author FengYH
 *
 */
public class SynUpdateDateUtils {
	
	/** 
	* 取得两个时间段的时间间隔 
	* @author FengYH 
	* @param t1 时间1 
	* @param t2 时间2 
	* @return t2 与t1的间隔天数 
	* @throws ParseException 如果输入的日期格式不是0000-00-00 格式抛出异常 
	*/ 
	public static int getBetweenDays(String t1,String t2) throws ParseException{ 
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		int betweenDays = 0; 
		Date d1 = format.parse(t1); 
		//Date d1 = (Date)format.parseObject(t1); 
		Date d2 = format.parse(t2); 
		Calendar c1 = Calendar.getInstance(); 
		Calendar c2 = Calendar.getInstance(); 
		c1.setTime(d1); 
		c2.setTime(d2); 
		// 保证第二个时间一定大于第一个时间 
		if(c1.after(c2)){ 
			c1 = c2; 
			c2.setTime(d1); 
		}
		int betweenYears = c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR); 
		betweenDays = c2.get(Calendar.DAY_OF_YEAR)-c1.get(Calendar.DAY_OF_YEAR); 
		for(int i=0;i<betweenYears;i++){ 
			c1.set(Calendar.YEAR,(c1.get(Calendar.YEAR)+1)); 
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR); 
		} 
		return betweenDays; 
	} 
	
	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	 public static String format(Date date, String pattern) {
	     return date == null ? "" : new SimpleDateFormat(pattern).format(date);
	 }
    
	/**
	 * 功能：根据ds判断是否存在lastupdatedate,进行新增
	 * @param  ds 传入的数据
	 */
	public static void createLastUpdateDate(DTOSet ds,String synType,Connection conn){
		SynLogUtil log=new SynLogUtil();
    	if(ds.getSize()>0){
    		try {
				String lastUpdate =log.getLastUpdateDate(synType,conn);
				if(lastUpdate == ""){
					log.createUpdateDate(synType,conn);
				}
			} catch (QueryException e) {
				e.printStackTrace();
			} catch (DataHandleException e) {
				e.printStackTrace();
			} catch (ContainerException e) {
				e.printStackTrace();
			}
    	}      			
	}
	
	/**
	 * 功能：判断是否有最新更新日期，如果没有则新增
	 * @param synType
	 * @param conn
	 */
	public static void createLastUpdateDate(String synType,Connection conn){
		SynLogUtil log=new SynLogUtil();
    		try {
				String lastUpdate =log.getLastUpdateDate(synType,conn);
				if(lastUpdate.length() == 0){
					log.createUpdateDate(synType,conn);
				}
			} catch (QueryException e) {
				e.printStackTrace();
			} catch (DataHandleException e) {
				e.printStackTrace();
			} catch (ContainerException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 功能：修改最近更新日期
	 * @param synType
	 * @param conn
	 */
	public static void updateLastUpdateDate(String synType,Connection conn){
		SynLogUtil log=new SynLogUtil();
		try {
			log.logUpdate(synType,conn);
		} catch (DataHandleException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 得到当前修改日期
	 * @param synType
	 * @param conn
	 * @return
	 * @throws QueryException
	 * @throws ContainerException
	 */
	public static String  getLastUpdateDate(String synType,Connection conn) throws QueryException, ContainerException{
		SynLogUtil log=new SynLogUtil();
		String lastUpdateDate = "";
		lastUpdateDate = log.getLastUpdateDate(synType,conn);
		return lastUpdateDate;
	}
 
	/**
	 * 功能：通过服务名称得到环境代码（用于ODI同步时）
	 * @param odiCode
	 * @param conn
	 * @return
	 * @throws QueryException
	 * @throws ContainerException
	 */
    public static String getEnvCode(String odiCode, Connection conn) throws QueryException, ContainerException {
        String envcode = "";
        SrvOptionUtils optionUtils = new SrvOptionUtils(null, conn);
        SQLModel sqlModel = optionUtils.getEnvCode(odiCode);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if (sq.hasResult()) {
        	envcode = sq.getFirstRow().getStrValue("ENV_CODE");
        }
        return envcode;
    }
    
    /**
     * 得到期间例如：Jan-09
     * @param str
     * @return
     * @throws ParseException
     */  
    @SuppressWarnings("static-access")
	public static String getPeriodName(String str) throws ParseException{
    	String perName = "";
    	Calendar calendar = new GregorianCalendar();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date date =sdf.parse(str);
    	calendar.setTime(date);
    	calendar.add(calendar.MONTH,-1);
    	date=calendar.getTime();
    	String dateString = sdf.format(date);
    	DateFormat df1 = new SimpleDateFormat("MMM",Locale.ENGLISH);
    	perName = df1.format(date)+"-"+dateString.substring(2, 4);
    	return perName;
    }
    
	/**
	 * 格式:2001-01-01
	 * 日期向前推200天
	 */
	public static String getDate3(String str) throws ParseException{
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		 Date date = format.parse(str);
		 Calendar calendar = Calendar.getInstance();//日历对象
		 calendar.setTime(date);
		 //calendar.add(Calendar.MONTH, -1);//月份减一
		 calendar.add(Calendar.DATE, -200); //日期减200天
		 return format.format(calendar.getTime());//输出格式化的日期
	}
	
    public static void main(String args[]) throws ParseException{
    	String str ="2012-02-01";
//    	String b = getPeriodName(str);
    	String b = getDate3(str);
    	System.out.println("b--------"+b);
    
    }
}
