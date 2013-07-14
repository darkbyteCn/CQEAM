package com.sino.ams.yearchecktaskmanager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class CommonUtil {
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString().replace("-", "");
		return id;
	}
	public static String getYearCheckOrder(){
		//返回全省年度盘点任务编号，一年一个
		return "YEAR-ASS-CHK-TASK-"+Calendar.getInstance().get(Calendar.YEAR);
	}
	public static Date strToDate(String strDate) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(strDate);
	}
	
/*	public static void main(String[]args){
		System.out.println(CommonUtil.getUUID());
		System.out.println(CommonUtil.getYearCheckOrder());
		List list = new ArrayList();
		AssetsYearCheckTaskLineDTO dto = new AssetsYearCheckTaskLineDTO();
		list.add(dto);
		for(int i=0;i<list.size();i++){
			AssetsYearCheckTaskLineDTO d =(AssetsYearCheckTaskLineDTO) list.get(i);
			d.setHeaderId("123");
		}
		
		for(int i=0;i<list.size();i++){
			AssetsYearCheckTaskLineDTO d =(AssetsYearCheckTaskLineDTO) list.get(i);
			System.out.println(d.getHeaderId());
		}
	}*/
}
