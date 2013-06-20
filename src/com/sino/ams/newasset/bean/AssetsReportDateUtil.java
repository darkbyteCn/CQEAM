package com.sino.ams.newasset.bean;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.exception.CalendarException;

public class AssetsReportDateUtil {

	public AssetsReportDateUtil(CommonRecordDTO dto) {
		this.dto = dto;
        this.getYear(dto.getYear());
        this.month = dto.getMonth();
//		    dto.setMonth(this.getMonth(dto.getAccountingPeriod()));
//			dto.setPeriodName(this.getPeriodName(dto.getAccountingPeriod()));


        dto.setLastMonthPeriodNameByHisRep(this.getLastMonthPeriodNameByHisRep());
        if(!this.month.equals("")){
            if(Integer.parseInt(this.month) < 10){
                dto.setPeriodNameByHisRep(this.year + "-0" + this.month);
                dto.setLastYearPeriodNameByHisRep(this.lastYear + "-0" + this.month);
            } else {
                dto.setPeriodNameByHisRep(this.year + "-" + this.month);
                dto.setLastYearPeriodNameByHisRep(this.lastYear + "-" + this.month);
            }
        }
        dto.setLastFourYearPeriodNameByHisRep(this.lastFourYear + "-12");
        dto.setLastThreeYearPeriodNameByHisRep(this.lastThreeYear + "-12");
        dto.setLastTwoYearPeriodNameByHisRep(this.lastTwoYear + "-12");
        dto.setLastOneYearPeriodNameByHisRep(this.lastYear + "-12");

        dto.setLastFourYear(this.lastFourYear);
        dto.setLastThreeYear(this.lastThreeYear);
        dto.setLastTwoYear(this.lastTwoYear);
        dto.setLastYear(this.lastYear);
			
//			dto.setLastMonthPeriodName(this.getLastMonthPeriodName());
//			dto.setLastYearPeriodName(this.getLastYearSameMonthPeriodName());
			
//			dto.setLastOneYearPeriodName(this.getLastOneYearPeriodName());
//			dto.setLastTwoYearPeriodName(this.getLastTwoYearPeriodName());
//			dto.setLastThreeYearPeriodName(this.getLastThreeYearPeriodName());
//			dto.setLastFourYearPeriodName(this.getLastFourYearPeriodName());
			

	}
	
	private CommonRecordDTO dto = null;
	private String monthSimp = "";  //当前所属月的英文前3个字母
	private String lastMonthSimp = "";	//上月所属月份的英文前3个字母
	private String year = "";		//当前年份
	private String month = "";		//当前月份
	private String lastMonth = "";	//上月月份
	private String lastYear = "";//去年年份
	private String lastFourYear = "";	//4年前年份
	private String lastThreeYear = "";	//3年前年份
	private String lastTwoYear = "";	//2年前年份
	

	public String getPeriodName(SimpleCalendar accountingPeriod){
		String periodName = "";
		String year;
		try {
			year = "" + accountingPeriod.get(CalendarConstant.YEAR);
			if(!year.equals("-1")){
				year = year.substring(2, year.length());
				int month = accountingPeriod.get(CalendarConstant.MONTH);
//				String monthSimp = this.getMonthSimp(month);
				periodName += monthSimp + "-" + year;
				
			}
			
		} catch (CalendarException e) {
			e.printStackTrace();
		}	
		return periodName;
	}
	
//	public String getYear(SimpleCalendar accountingPeriod){
//		try {
//			if(accountingPeriod.get(CalendarConstant.YEAR) > 0){
//				int year = accountingPeriod.get(CalendarConstant.YEAR);
//				this.year = "" + year;
//				this.lastYear = year - 1 + "";
//				this.lastTwoYear = year - 2 + "";
//				this.lastThreeYear = year - 3 + "";
//				this.lastFourYear = year - 4 + "";
//			}
//		} catch (CalendarException e) {
//			e.printStackTrace();
//		}
//		return year;
//	}

    public void getYear(String yearInput){
            int year = Integer.parseInt(yearInput);
            this.year = "" + year;
            this.lastYear = year - 1 + "";
            this.lastTwoYear = year - 2 + "";
            this.lastThreeYear = year - 3 + "";
            this.lastFourYear = year - 4 + "";
	}
	
//	public String getMonth(SimpleCalendar accountingPeriod){
//		try {
//			if(accountingPeriod.get(CalendarConstant.MONTH) > 0){
//				month += accountingPeriod.get(CalendarConstant.MONTH);
//			}
//		} catch (CalendarException e) {
//			e.printStackTrace();
//		}
//		return month;
//	}

   
//	public String getMonthSimp(int month){
//		if(month == 1){
//			this.monthSimp = "Jan";
//			this.lastMonthSimp = "Dec";
//		} else if (month == 2){
//			monthSimp = "Feb";
//			this.lastMonthSimp = "Jan";
//		} else if (month == 3){
//			monthSimp = "Mar";
//			this.lastMonthSimp = "Feb";
//		} else if (month == 4){
//			monthSimp = "Apr";
//			this.lastMonthSimp = "Mar";
//		} else if (month == 5){
//			monthSimp = "May";
//			this.lastMonthSimp = "Apr";
//		} else if (month == 6){
//			monthSimp = "Jun";
//			this.lastMonthSimp = "May";
//		} else if (month == 7){
//			monthSimp = "Jul";
//			this.lastMonthSimp = "Jun";
//		} else if (month == 8){
//			monthSimp = "Aug";
//			this.lastMonthSimp = "Jul";
//		} else if (month == 9){
//			monthSimp = "Sep";
//			this.lastMonthSimp = "Aug";
//		} else if (month == 10){
//			monthSimp = "Oct";
//			this.lastMonthSimp = "Sep";
//		} else if (month == 11){
//			monthSimp = "Nov";
//			this.lastMonthSimp = "Oct";
//		} else if (month == 12){
//			monthSimp = "Dec";
//			this.lastMonthSimp = "Nov";
//		}
//		return monthSimp;
//	}
	
//	public String getLastMonthPeriodName(){
//		String year = "";
//		if(!this.year.equals("-1") && !this.year.equals("")){
//			if(this.lastMonthSimp.equals("Dec")){
//				year = Integer.parseInt(this.year) - 1 + "";
//			} else {
//				year = this.year;
//			}
//			year = year.substring(2, year.length());
//		}
//		return this.lastMonthSimp + "-" + year;
//	}

//	public String getLastYearSameMonthPeriodName(){
//		if(!this.lastYear.equals("-1") && !this.lastYear.equals("")){
//			this.lastYear = this.lastYear.substring(2, this.lastYear.length());
//		}
//		return this.monthSimp + "-" + this.lastYear;
//	}
//
//	public String getLastOneYearPeriodName(){
//		return "Dec-" + this.lastYear;
//	}
	
//	public String getLastTwoYearPeriodName(){
//		if(!this.lastTwoYear.equals("-1") && !this.lastTwoYear.equals("")){
//			this.lastTwoYear = this.lastTwoYear.substring(2, this.lastTwoYear.length());
//		}
//		return "Dec-" + this.lastTwoYear;
//	}
//
//	public String getLastThreeYearPeriodName(){
//		if(!this.lastThreeYear.equals("-1") && !this.lastThreeYear.equals("")){
//			this.lastThreeYear = this.lastThreeYear.substring(2, this.lastThreeYear.length());
//		}
//		return "Dec-" + this.lastThreeYear;
//	}
//
//	public String getLastFourYearPeriodName(){
//		if(!this.lastFourYear.equals("-1") && !this.lastFourYear.equals("")){
//			this.lastFourYear = this.lastFourYear.substring(2, this.lastFourYear.length());
//		}
//		return "Dec-" + this.lastFourYear;
//	}
	
	public String getLastMonthPeriodNameByHisRep(){
		if(!this.year.equals("-1") && !this.year.equals("")){
			if(this.month.equals("1")){
				this.lastMonth = "12";
				return (Integer.parseInt(this.year) - 1) + "-12";
			} else {
				int lastMonth = Integer.parseInt(this.month) -1;
				if(lastMonth < 10){
					return this.year + "-0" + lastMonth; 
				}
				this.lastMonth = String.valueOf(lastMonth);
				return this.year + "-" + lastMonth;
			}
		}
		return "";
	}
	
}
