package com.sino.ams.net.statistic.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by sunny.
 * User: sunny
 * Date: 2008-4-11
 * Time: 1:57:17
 * To change this template use File | Settings | File Templates.
 */
public class MisDiffDTO extends CheckBoxDTO  {
    private String eamQuantity = "";
    private String workorderObjectName = "";
    private String misItemCode = "";
    private String ItemName = "";
    private String ItemSpec = "";
    private String Quantity = "";
    private String company = "";
    private String companyName = "";
    private int oranizationId;

    public int getOranizationId() {
        return oranizationId;
    }           

    public void setOranizationId(int oranizationId) {
        this.oranizationId = oranizationId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getItemSpec() {
        return ItemSpec;
    }

    public void setItemSpec(String itemSpec) {
        ItemSpec = itemSpec;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getMisItemCode() {
        return misItemCode;
    }

    public void setMisItemCode(String misItemCode) {
        this.misItemCode = misItemCode;
    }

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }

    public String getEamQuantity() {
        return eamQuantity;
    }

    public void setEamQuantity(String eamQuantity) {
        this.eamQuantity = eamQuantity;
    }

}
