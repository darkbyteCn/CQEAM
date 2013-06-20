
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_pageinquiryassetcategorysrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PageInquiryAssetCategorySrvOutputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageInquiryAssetCategorySrvOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PageInquiryAssetCategorySrvOutputItem" type="{http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetCategorySrv}PageInquiryAssetCategorySrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageInquiryAssetCategorySrvOutputCollection", propOrder = {
    "pageInquiryAssetCategorySrvOutputItem"
})
public class PageInquiryAssetCategorySrvOutputCollection {

    @XmlElement(name = "PageInquiryAssetCategorySrvOutputItem")
    protected List<PageInquiryAssetCategorySrvOutputItem> pageInquiryAssetCategorySrvOutputItem;

    /**
     * Gets the value of the pageInquiryAssetCategorySrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pageInquiryAssetCategorySrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPageInquiryAssetCategorySrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PageInquiryAssetCategorySrvOutputItem }
     * 
     * 
     */
    public List<PageInquiryAssetCategorySrvOutputItem> getPageInquiryAssetCategorySrvOutputItem() {
        if (pageInquiryAssetCategorySrvOutputItem == null) {
            pageInquiryAssetCategorySrvOutputItem = new ArrayList<PageInquiryAssetCategorySrvOutputItem>();
        }
        return this.pageInquiryAssetCategorySrvOutputItem;
    }

}
