package com.sino.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * <p>Title: 设备地点变动历史表(EAM) AmsItemInfoHistory</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class ItemInfoHistoryDTO extends CommonRecordDTO {

    private String barcode = "";
    private String startBarcode = "";
    private String endBarcode = "";
    private String itemCategory = "";
    private String itemCategoryName = "";
    private String itemCategoryOption = "";
    private String itemStatus = "";
    private String itemStatusName = "";
    private String itemStatusOption = "";
    private String itemName = "";
    private String itemSpec = "";
    private String deptCode = "";
    private String deptName = "";
    private String deptOption = "";
    private String userName = "";
    private String employeeNumber = "";
    private String workorderObjectCode = "";
    private String workorderObjectName = "";
    private String organizationId = "";
    private String organizationOption = "";
    private int changeTimes = 1;//变更次数
    private String contentCode = "";
    private String contentName = "";
    private String maintainUser = "";
    private String logUser = "";
    private String orderNo = "";
    private String orderDtlUrl = "";

    private static final String[] LIMIT_TIMES = {"", "2", "3", "4", "5"};
    private static final String[] LIMIT_TIME_DESCS = {"--请选择--", "小于2", "小于3", "小于4", "小于5"};
    
    private String lneId = ""; //逻辑网络元素
    private String cexId = ""; //投资分类
    private String opeId = ""; //业务平台
    private String nleId = ""; //网络层次
    private String logNetEle = ""; //逻辑网络元素
    private String investCatName = ""; //投资分类
    private String opeName = ""; //业务平台
    private String lneName = ""; //网络层次
        
    private String oldContentCode = ""; //资产类别旧值
    private String oldContentName = ""; //资产类别描述旧值

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemCategoryName() {
        return itemCategoryName;
    }

    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
    }

    public String getItemCategoryOption() {
        return itemCategoryOption;
    }

    public void setItemCategoryOption(String itemCategoryOption) {
        this.itemCategoryOption = itemCategoryOption;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getWorkorderObjectCode() {
        return workorderObjectCode;
    }

    public void setWorkorderObjectCode(String workorderObjectCode) {
        this.workorderObjectCode = workorderObjectCode;
    }

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationOption() {
        return organizationOption;
    }

    public void setOrganizationOption(String organizationOption) {
        this.organizationOption = organizationOption;
    }

    public String getStartBarcode() {
        return startBarcode;
    }

    public void setStartBarcode(String startBarcode) {
        this.startBarcode = startBarcode;
    }

    public String getEndBarcode() {
        return endBarcode;
    }

    public void setEndBarcode(String endBarcode) {
        this.endBarcode = endBarcode;
    }

    public int getChangeTimes() {
        return changeTimes;
    }

    public void setChangeTimes(int changeTimes) {
        this.changeTimes = changeTimes;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemStatusName() {
        return itemStatusName;
    }

    public void setItemStatusName(String itemStatusName) {
        this.itemStatusName = itemStatusName;
    }

    public String getItemStatusOption() {
        return itemStatusOption;
    }

    public void setItemStatusOption(String itemStatusOption) {
        this.itemStatusOption = itemStatusOption;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptOption() {
        return deptOption;
    }

    public void setDeptOption(String deptOption) {
        this.deptOption = deptOption;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getMaintainUser() {
        return maintainUser;
    }

    public void setMaintainUser(String maintainUser) {
        this.maintainUser = maintainUser;
    }

    public String getLogUser() {
        return logUser;
    }

    public void setLogUser(String logUser) {
        this.logUser = logUser;
    }

    public String getOrderDtlUrl() {
        return orderDtlUrl;
    }

    public void setOrderDtlUrl(String orderDtlUrl) {
        this.orderDtlUrl = orderDtlUrl;
    }

    public String getChangeTimeOption() {
        StringBuffer timeHTML = new StringBuffer();
        for (int i = 0; i < LIMIT_TIMES.length; i++) {
            String time = LIMIT_TIMES[i];
            timeHTML.append("<option value=\"");
            timeHTML.append(time);
            timeHTML.append("\"");
            if (time.equals(getChangeTimes())) {
                timeHTML.append(" selected");
            }
            timeHTML.append(">");
            timeHTML.append(LIMIT_TIME_DESCS[i]);
            timeHTML.append("</option>");
        }
        return timeHTML.toString();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

	public String getLneId() {
		return lneId;
	}

	public void setLneId(String lneId) {
		this.lneId = lneId;
	}

	public String getCexId() {
		return cexId;
	}

	public void setCexId(String cexId) {
		this.cexId = cexId;
	}

	public String getOpeId() {
		return opeId;
	}

	public void setOpeId(String opeId) {
		this.opeId = opeId;
	}

	public String getNleId() {
		return nleId;
	}

	public void setNleId(String nleId) {
		this.nleId = nleId;
	}

	public String getLogNetEle() {
		return logNetEle;
	}

	public void setLogNetEle(String logNetEle) {
		this.logNetEle = logNetEle;
	}

	public String getInvestCatName() {
		return investCatName;
	}

	public void setInvestCatName(String investCatName) {
		this.investCatName = investCatName;
	}

	public String getOpeName() {
		return opeName;
	}

	public void setOpeName(String opeName) {
		this.opeName = opeName;
	}

	public String getLneName() {
		return lneName;
	}

	public void setLneName(String lneName) {
		this.lneName = lneName;
	}

	public String getOldContentCode() {
		return oldContentCode;
	}

	public void setOldContentCode(String oldContentCode) {
		this.oldContentCode = oldContentCode;
	}

	public String getOldContentName() {
		return oldContentName;
	}

	public void setOldContentName(String oldContentName) {
		this.oldContentName = oldContentName;
	}
}
