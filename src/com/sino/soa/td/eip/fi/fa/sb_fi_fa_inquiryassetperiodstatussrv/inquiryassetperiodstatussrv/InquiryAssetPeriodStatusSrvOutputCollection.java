
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.inquiryassetperiodstatussrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InquiryAssetPeriodStatusSrvOutputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryAssetPeriodStatusSrvOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InquiryAssetPeriodStatusSrvOutputItem" type="{http://eip.zte.com/common/InquiryAssetPeriodStatusSrv}InquiryAssetPeriodStatusSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryAssetPeriodStatusSrvOutputCollection", propOrder = {
    "inquiryAssetPeriodStatusSrvOutputItem"
})
public class InquiryAssetPeriodStatusSrvOutputCollection {

    @XmlElement(name = "InquiryAssetPeriodStatusSrvOutputItem", nillable = true)
    protected List<InquiryAssetPeriodStatusSrvOutputItem> inquiryAssetPeriodStatusSrvOutputItem;

    /**
     * Gets the value of the inquiryAssetPeriodStatusSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inquiryAssetPeriodStatusSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInquiryAssetPeriodStatusSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InquiryAssetPeriodStatusSrvOutputItem }
     * 
     * 
     */
    public List<InquiryAssetPeriodStatusSrvOutputItem> getInquiryAssetPeriodStatusSrvOutputItem() {
        if (inquiryAssetPeriodStatusSrvOutputItem == null) {
            inquiryAssetPeriodStatusSrvOutputItem = new ArrayList<InquiryAssetPeriodStatusSrvOutputItem>();
        }
        return this.inquiryAssetPeriodStatusSrvOutputItem;
    }

}
