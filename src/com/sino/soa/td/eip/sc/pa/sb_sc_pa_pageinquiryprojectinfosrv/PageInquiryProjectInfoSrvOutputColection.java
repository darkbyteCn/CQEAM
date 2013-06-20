
package com.sino.soa.td.eip.sc.pa.sb_sc_pa_pageinquiryprojectinfosrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PageInquiryProjectInfoSrvOutputColection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageInquiryProjectInfoSrvOutputColection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PageInquiryProjectInfoSrvOutputItem" type="{http://eip.zte.com/SC/SB_SC_PA_PageInquiryProjectInfoSrv}PageInquiryProjectInfoSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageInquiryProjectInfoSrvOutputColection", propOrder = {
    "pageInquiryProjectInfoSrvOutputItem"
})
public class PageInquiryProjectInfoSrvOutputColection {

    @XmlElement(name = "PageInquiryProjectInfoSrvOutputItem", nillable = true)
    protected List<PageInquiryProjectInfoSrvOutputItem> pageInquiryProjectInfoSrvOutputItem;

    /**
     * Gets the value of the pageInquiryProjectInfoSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pageInquiryProjectInfoSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPageInquiryProjectInfoSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PageInquiryProjectInfoSrvOutputItem }
     * 
     * 
     */
    public List<PageInquiryProjectInfoSrvOutputItem> getPageInquiryProjectInfoSrvOutputItem() {
        if (pageInquiryProjectInfoSrvOutputItem == null) {
            pageInquiryProjectInfoSrvOutputItem = new ArrayList<PageInquiryProjectInfoSrvOutputItem>();
        }
        return this.pageInquiryProjectInfoSrvOutputItem;
    }

}
