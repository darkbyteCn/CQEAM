
package com.sino.soa.td.eip.sc.pa.sb_sc_pa_pageinquirytaskinfosrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PageInquiryTaskInfoSrvOutputColection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageInquiryTaskInfoSrvOutputColection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PageInquiryTaskInfoSrvOutputItem" type="{http://eip.zte.com/SC/SB_SC_PA_PageInquiryTaskInfoSrv}PageInquiryTaskInfoSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageInquiryTaskInfoSrvOutputColection", propOrder = {
    "pageInquiryTaskInfoSrvOutputItem"
})
public class PageInquiryTaskInfoSrvOutputColection {

    @XmlElement(name = "PageInquiryTaskInfoSrvOutputItem", nillable = true)
    protected List<PageInquiryTaskInfoSrvOutputItem> pageInquiryTaskInfoSrvOutputItem;

    /**
     * Gets the value of the pageInquiryTaskInfoSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pageInquiryTaskInfoSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPageInquiryTaskInfoSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PageInquiryTaskInfoSrvOutputItem }
     * 
     * 
     */
    public List<PageInquiryTaskInfoSrvOutputItem> getPageInquiryTaskInfoSrvOutputItem() {
        if (pageInquiryTaskInfoSrvOutputItem == null) {
            pageInquiryTaskInfoSrvOutputItem = new ArrayList<PageInquiryTaskInfoSrvOutputItem>();
        }
        return this.pageInquiryTaskInfoSrvOutputItem;
    }

}
