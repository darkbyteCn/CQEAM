package com.sino.ams.match.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
 * <p>Title: ETS_ITEM_MATCH_REC EtsItemMatchRec</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class EtsItemMatchRecDTO extends CheckBoxDTO {
    private String id = "";
    private SimpleCalendar matchDate = null;
    private String startDate = "";
   
	private String endDate = "";
   
	private int matchUserId;
    private String  systemId="";
    private int assetId;
    private String oldFinanceProp = "";
    private String newFinanceProp = "";
    private String matchType="";
    private String unyokeFlag = "";
    private String itemCode = "";
    private String itemName = "";
    private String workorderObjectName = "";
    private String workorderObjectNo = "";
    private String barcode = "";
    private String itemSpec = "";
    private String tagNumber = "";
    private String workorderobjectCode = "";
    private String userName = "";
    private String maintainUser = "";
    
    private String prjId = "";  //项目编号
    private String prjName = ""; //项目名称

    public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	public EtsItemMatchRecDTO() {
        super();
        this.matchDate = new SimpleCalendar();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SimpleCalendar getMatchDate() throws CalendarException {
        matchDate.setCalPattern(getCalPattern());
        return this.matchDate;
    }

    public void setMatchDate(String matchDate) throws CalendarException {
        this.matchDate.setCalendarValue(matchDate);
    }

    public int getMatchUserId() {
        return matchUserId;
    }

    public void setMatchUserId(int matchUserId) {
        this.matchUserId = matchUserId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public String getOldFinanceProp() {
        return oldFinanceProp;
    }

    public void setOldFinanceProp(String oldFinanceProp) {
        this.oldFinanceProp = oldFinanceProp;
    }

    public String getNewFinanceProp() {
        return newFinanceProp;
    }

    public void setNewFinanceProp(String newFinanceProp) {
        this.newFinanceProp = newFinanceProp;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getUnyokeFlag() {
        return unyokeFlag;
    }

    public void setUnyokeFlag(String unyokeFlag) {
        this.unyokeFlag = unyokeFlag;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }

    public String getWorkorderObjectNo() {
        return workorderObjectNo;
    }

    public void setWorkorderObjectNo(String workorderObjectNo) {
        this.workorderObjectNo = workorderObjectNo;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }


    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public String getWorkorderobjectCode() {
        return workorderobjectCode;
    }

    public void setWorkorderobjectCode(String workorderobjecTCode) {
        this.workorderobjectCode = workorderobjecTCode;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserNamer(String userName) {
        this.userName = userName;
    }

     public String getMaintainUser() {
        return maintainUser;
    }
     public String getStartDate() {
 		return startDate;
 	}

 	public void setStartDate(String startDate) {
 		this.startDate = startDate;
 	}

 	public String getEndDate() {
 		return endDate;
 	}

 	public void setEndDate(String endDate) {
 		this.endDate = endDate;
 	}


}