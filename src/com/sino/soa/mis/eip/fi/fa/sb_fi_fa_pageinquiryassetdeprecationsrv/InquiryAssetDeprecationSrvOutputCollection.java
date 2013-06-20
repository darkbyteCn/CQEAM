
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_pageinquiryassetdeprecationsrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InquiryAssetDeprecationSrvOutputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryAssetDeprecationSrvOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InquiryAssetDeprecationSrvOutputItem" type="{http://eip.zte.com/common/fi/SB_FI_FA_PageInquiryAssetDeprecationSrv}InquiryAssetDeprecationSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryAssetDeprecationSrvOutputCollection", propOrder = {
    "inquiryAssetDeprecationSrvOutputItem"
})
public class InquiryAssetDeprecationSrvOutputCollection {

    @XmlElement(name = "InquiryAssetDeprecationSrvOutputItem", nillable = true)
    protected List<InquiryAssetDeprecationSrvOutputItem> inquiryAssetDeprecationSrvOutputItem;

    /**
     * Gets the value of the inquiryAssetDeprecationSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inquiryAssetDeprecationSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInquiryAssetDeprecationSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InquiryAssetDeprecationSrvOutputItem }
     * 
     * 
     */
    public List<InquiryAssetDeprecationSrvOutputItem> getInquiryAssetDeprecationSrvOutputItem() {
        if (inquiryAssetDeprecationSrvOutputItem == null) {
            inquiryAssetDeprecationSrvOutputItem = new ArrayList<InquiryAssetDeprecationSrvOutputItem>();
        }
        return this.inquiryAssetDeprecationSrvOutputItem;
    }

}
