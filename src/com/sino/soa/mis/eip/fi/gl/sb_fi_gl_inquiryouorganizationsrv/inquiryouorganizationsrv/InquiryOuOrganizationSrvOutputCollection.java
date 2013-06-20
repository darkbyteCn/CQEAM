
package com.sino.soa.mis.eip.fi.gl.sb_fi_gl_inquiryouorganizationsrv.inquiryouorganizationsrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InquiryOuOrganizationSrvOutputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryOuOrganizationSrvOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InquiryOuOrganizationSrvOutputItem" type="{http://eip.zte.com/common/gl/InquiryOuOrganizationSrv}InquiryOuOrganizationSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryOuOrganizationSrvOutputCollection", propOrder = {
    "inquiryOuOrganizationSrvOutputItem"
})
public class InquiryOuOrganizationSrvOutputCollection {

    @XmlElement(name = "InquiryOuOrganizationSrvOutputItem")
    protected List<InquiryOuOrganizationSrvOutputItem> inquiryOuOrganizationSrvOutputItem;

    /**
     * Gets the value of the inquiryOuOrganizationSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inquiryOuOrganizationSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInquiryOuOrganizationSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InquiryOuOrganizationSrvOutputItem }
     * 
     * 
     */
    public List<InquiryOuOrganizationSrvOutputItem> getInquiryOuOrganizationSrvOutputItem() {
        if (inquiryOuOrganizationSrvOutputItem == null) {
            inquiryOuOrganizationSrvOutputItem = new ArrayList<InquiryOuOrganizationSrvOutputItem>();
        }
        return this.inquiryOuOrganizationSrvOutputItem;
    }

}
