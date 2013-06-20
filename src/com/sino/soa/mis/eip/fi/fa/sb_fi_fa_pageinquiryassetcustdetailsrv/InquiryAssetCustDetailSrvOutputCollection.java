
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_pageinquiryassetcustdetailsrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InquiryAssetCustDetailSrvOutputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryAssetCustDetailSrvOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InquiryAssetCustDetailSrvOutputItem" type="{http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetCustDetailSrv}InquiryAssetCustDetailSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryAssetCustDetailSrvOutputCollection", propOrder = {
    "inquiryAssetCustDetailSrvOutputItem"
})
public class InquiryAssetCustDetailSrvOutputCollection {

    @XmlElement(name = "InquiryAssetCustDetailSrvOutputItem", nillable = true)
    protected List<InquiryAssetCustDetailSrvOutputItem> inquiryAssetCustDetailSrvOutputItem;

    /**
     * Gets the value of the inquiryAssetCustDetailSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inquiryAssetCustDetailSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInquiryAssetCustDetailSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InquiryAssetCustDetailSrvOutputItem }
     * 
     * 
     */
    public List<InquiryAssetCustDetailSrvOutputItem> getInquiryAssetCustDetailSrvOutputItem() {
        if (inquiryAssetCustDetailSrvOutputItem == null) {
            inquiryAssetCustDetailSrvOutputItem = new ArrayList<InquiryAssetCustDetailSrvOutputItem>();
        }
        return this.inquiryAssetCustDetailSrvOutputItem;
    }

}
