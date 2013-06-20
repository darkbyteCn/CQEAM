
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_pageinquiryassetheaderinfosrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PageInquiryAssetHeaderInfoSrvOutputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageInquiryAssetHeaderInfoSrvOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PageInquiryAssetHeaderInfoSrvOutputItem" type="{http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetHeaderInfoSrv}PageInquiryAssetHeaderInfoSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageInquiryAssetHeaderInfoSrvOutputCollection", propOrder = {
    "pageInquiryAssetHeaderInfoSrvOutputItem"
})
public class PageInquiryAssetHeaderInfoSrvOutputCollection {

    @XmlElement(name = "PageInquiryAssetHeaderInfoSrvOutputItem", nillable = true)
    protected List<PageInquiryAssetHeaderInfoSrvOutputItem> pageInquiryAssetHeaderInfoSrvOutputItem;

    /**
     * Gets the value of the pageInquiryAssetHeaderInfoSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pageInquiryAssetHeaderInfoSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPageInquiryAssetHeaderInfoSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PageInquiryAssetHeaderInfoSrvOutputItem }
     * 
     * 
     */
    public List<PageInquiryAssetHeaderInfoSrvOutputItem> getPageInquiryAssetHeaderInfoSrvOutputItem() {
        if (pageInquiryAssetHeaderInfoSrvOutputItem == null) {
            pageInquiryAssetHeaderInfoSrvOutputItem = new ArrayList<PageInquiryAssetHeaderInfoSrvOutputItem>();
        }
        return this.pageInquiryAssetHeaderInfoSrvOutputItem;
    }

}
