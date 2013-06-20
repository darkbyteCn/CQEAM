
package com.sino.soa.mis.eip.hr.hr.sb_hr_hr_inquiryorgstructuresrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InquiryOrgStructureSrvOutputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryOrgStructureSrvOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InquiryOrgStructureSrvOutputItem" type="{http://eip.zte.com/hr/SB_HR_HR_InquiryOrgStructureSrv}InquiryOrgStructureSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryOrgStructureSrvOutputCollection", propOrder = {
    "inquiryOrgStructureSrvOutputItem"
})
public class InquiryOrgStructureSrvOutputCollection {

    @XmlElement(name = "InquiryOrgStructureSrvOutputItem")
    protected List<InquiryOrgStructureSrvOutputItem> inquiryOrgStructureSrvOutputItem;

    /**
     * Gets the value of the inquiryOrgStructureSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inquiryOrgStructureSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInquiryOrgStructureSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InquiryOrgStructureSrvOutputItem }
     * 
     * 
     */
    public List<InquiryOrgStructureSrvOutputItem> getInquiryOrgStructureSrvOutputItem() {
        if (inquiryOrgStructureSrvOutputItem == null) {
            inquiryOrgStructureSrvOutputItem = new ArrayList<InquiryOrgStructureSrvOutputItem>();
        }
        return this.inquiryOrgStructureSrvOutputItem;
    }

}
