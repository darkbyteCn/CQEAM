
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetlocationsrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PageInquiryAssetLocationSrvOutputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageInquiryAssetLocationSrvOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PageInquiryAssetLocationSrvOutputItem" type="{http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetLocationSrv}PageInquiryAssetLocationSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageInquiryAssetLocationSrvOutputCollection", propOrder = {
    "pageInquiryAssetLocationSrvOutputItem"
})
public class PageInquiryAssetLocationSrvOutputCollection {

    @XmlElement(name = "PageInquiryAssetLocationSrvOutputItem")
    protected List<PageInquiryAssetLocationSrvOutputItem> pageInquiryAssetLocationSrvOutputItem;

    /**
     * Gets the value of the pageInquiryAssetLocationSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pageInquiryAssetLocationSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPageInquiryAssetLocationSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PageInquiryAssetLocationSrvOutputItem }
     * 
     * 
     */
    public List<PageInquiryAssetLocationSrvOutputItem> getPageInquiryAssetLocationSrvOutputItem() {
        if (pageInquiryAssetLocationSrvOutputItem == null) {
            pageInquiryAssetLocationSrvOutputItem = new ArrayList<PageInquiryAssetLocationSrvOutputItem>();
        }
        return this.pageInquiryAssetLocationSrvOutputItem;
    }

}
