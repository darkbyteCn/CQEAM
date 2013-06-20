
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_pageinquiryretiredassetdetailsrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PageInquiryRetiredAssetDetailSrvOutputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageInquiryRetiredAssetDetailSrvOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PageInquiryRetiredAssetDetailSrvOutputItem" type="{http://eip.zte.com/fi/SB_FI_FA_PageInquiryRetiredAssetDetailSrv}PageInquiryRetiredAssetDetailSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageInquiryRetiredAssetDetailSrvOutputCollection", propOrder = {
    "pageInquiryRetiredAssetDetailSrvOutputItem"
})
public class PageInquiryRetiredAssetDetailSrvOutputCollection {

    @XmlElement(name = "PageInquiryRetiredAssetDetailSrvOutputItem")
    protected List<PageInquiryRetiredAssetDetailSrvOutputItem> pageInquiryRetiredAssetDetailSrvOutputItem;

    /**
     * Gets the value of the pageInquiryRetiredAssetDetailSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pageInquiryRetiredAssetDetailSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPageInquiryRetiredAssetDetailSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PageInquiryRetiredAssetDetailSrvOutputItem }
     * 
     * 
     */
    public List<PageInquiryRetiredAssetDetailSrvOutputItem> getPageInquiryRetiredAssetDetailSrvOutputItem() {
        if (pageInquiryRetiredAssetDetailSrvOutputItem == null) {
            pageInquiryRetiredAssetDetailSrvOutputItem = new ArrayList<PageInquiryRetiredAssetDetailSrvOutputItem>();
        }
        return this.pageInquiryRetiredAssetDetailSrvOutputItem;
    }

}
