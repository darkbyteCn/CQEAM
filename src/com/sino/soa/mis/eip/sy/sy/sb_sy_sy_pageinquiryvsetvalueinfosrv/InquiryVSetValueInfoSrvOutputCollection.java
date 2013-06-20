
package com.sino.soa.mis.eip.sy.sy.sb_sy_sy_pageinquiryvsetvalueinfosrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InquiryVSetValueInfoSrvOutputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryVSetValueInfoSrvOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InquiryVSetValueInfoSrvOutputItem" type="{http://eip.zte.com/common/sy/SB_SY_SY_PageInquiryVSetValueInfoSrv}InquiryVSetValueInfoSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryVSetValueInfoSrvOutputCollection", propOrder = {
    "inquiryVSetValueInfoSrvOutputItem"
})
public class InquiryVSetValueInfoSrvOutputCollection {

    @XmlElement(name = "InquiryVSetValueInfoSrvOutputItem")
    protected List<InquiryVSetValueInfoSrvOutputItem> inquiryVSetValueInfoSrvOutputItem;

    /**
     * Gets the value of the inquiryVSetValueInfoSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inquiryVSetValueInfoSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInquiryVSetValueInfoSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InquiryVSetValueInfoSrvOutputItem }
     * 
     * 
     */
    public List<InquiryVSetValueInfoSrvOutputItem> getInquiryVSetValueInfoSrvOutputItem() {
        if (inquiryVSetValueInfoSrvOutputItem == null) {
            inquiryVSetValueInfoSrvOutputItem = new ArrayList<InquiryVSetValueInfoSrvOutputItem>();
        }
        return this.inquiryVSetValueInfoSrvOutputItem;
    }

}
