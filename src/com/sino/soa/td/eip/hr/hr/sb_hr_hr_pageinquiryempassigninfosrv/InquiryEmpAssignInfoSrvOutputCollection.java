
package com.sino.soa.td.eip.hr.hr.sb_hr_hr_pageinquiryempassigninfosrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InquiryEmpAssignInfoSrvOutputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryEmpAssignInfoSrvOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InquiryEmpAssignInfoSrvOutputItem" type="{http://eip.zte.com/common/fi/SB_HR_HR_PageInquiryEmpAssignInfoSrv}InquiryEmpAssignInfoSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryEmpAssignInfoSrvOutputCollection", propOrder = {
    "inquiryEmpAssignInfoSrvOutputItem"
})
public class InquiryEmpAssignInfoSrvOutputCollection {

    @XmlElement(name = "InquiryEmpAssignInfoSrvOutputItem")
    protected List<InquiryEmpAssignInfoSrvOutputItem> inquiryEmpAssignInfoSrvOutputItem;

    /**
     * Gets the value of the inquiryEmpAssignInfoSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inquiryEmpAssignInfoSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInquiryEmpAssignInfoSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InquiryEmpAssignInfoSrvOutputItem }
     * 
     * 
     */
    public List<InquiryEmpAssignInfoSrvOutputItem> getInquiryEmpAssignInfoSrvOutputItem() {
        if (inquiryEmpAssignInfoSrvOutputItem == null) {
            inquiryEmpAssignInfoSrvOutputItem = new ArrayList<InquiryEmpAssignInfoSrvOutputItem>();
        }
        return this.inquiryEmpAssignInfoSrvOutputItem;
    }

}
