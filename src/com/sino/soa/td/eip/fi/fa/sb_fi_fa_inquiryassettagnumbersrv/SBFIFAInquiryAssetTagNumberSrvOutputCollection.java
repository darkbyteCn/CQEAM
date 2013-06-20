
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassettagnumbersrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SB_FI_FA_InquiryAssetTagNumberSrvOutputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SB_FI_FA_InquiryAssetTagNumberSrvOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SB_FI_FA_InquiryAssetTagNumberSrvOutputItem" type="{http://mss.cmcc.com/SB_FI_FA_InquiryAssetTagNumberSrv}SB_FI_FA_InquiryAssetTagNumberSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_FI_FA_InquiryAssetTagNumberSrvOutputCollection", propOrder = {
    "sbfifaInquiryAssetTagNumberSrvOutputItem"
})
public class SBFIFAInquiryAssetTagNumberSrvOutputCollection {

    @XmlElement(name = "SB_FI_FA_InquiryAssetTagNumberSrvOutputItem")
    protected List<SBFIFAInquiryAssetTagNumberSrvOutputItem> sbfifaInquiryAssetTagNumberSrvOutputItem;

    /**
     * Gets the value of the sbfifaInquiryAssetTagNumberSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sbfifaInquiryAssetTagNumberSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSBFIFAInquiryAssetTagNumberSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SBFIFAInquiryAssetTagNumberSrvOutputItem }
     * 
     * 
     */
    public List<SBFIFAInquiryAssetTagNumberSrvOutputItem> getSBFIFAInquiryAssetTagNumberSrvOutputItem() {
        if (sbfifaInquiryAssetTagNumberSrvOutputItem == null) {
            sbfifaInquiryAssetTagNumberSrvOutputItem = new ArrayList<SBFIFAInquiryAssetTagNumberSrvOutputItem>();
        }
        return this.sbfifaInquiryAssetTagNumberSrvOutputItem;
    }

}
