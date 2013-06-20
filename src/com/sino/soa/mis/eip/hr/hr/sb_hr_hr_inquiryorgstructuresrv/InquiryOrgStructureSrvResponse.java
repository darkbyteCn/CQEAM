
package com.sino.soa.mis.eip.hr.hr.sb_hr_hr_inquiryorgstructuresrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ErrorFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="InquiryOrgStructureSrvOutputCollection" type="{http://eip.zte.com/hr/SB_HR_HR_InquiryOrgStructureSrv}InquiryOrgStructureSrvOutputCollection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "errorFlag",
    "errorMessage",
    "inquiryOrgStructureSrvOutputCollection"
})
@XmlRootElement(name = "InquiryOrgStructureSrvResponse")
public class InquiryOrgStructureSrvResponse {

    @XmlElement(name = "ErrorFlag", required = true, nillable = true)
    protected String errorFlag;
    @XmlElement(name = "ErrorMessage", required = true, nillable = true)
    protected String errorMessage;
    @XmlElement(name = "InquiryOrgStructureSrvOutputCollection", required = true)
    protected InquiryOrgStructureSrvOutputCollection inquiryOrgStructureSrvOutputCollection;

    /**
     * Gets the value of the errorFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorFlag() {
        return errorFlag;
    }

    /**
     * Sets the value of the errorFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorFlag(String value) {
        this.errorFlag = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the inquiryOrgStructureSrvOutputCollection property.
     * 
     * @return
     *     possible object is
     *     {@link InquiryOrgStructureSrvOutputCollection }
     *     
     */
    public InquiryOrgStructureSrvOutputCollection getInquiryOrgStructureSrvOutputCollection() {
        return inquiryOrgStructureSrvOutputCollection;
    }

    /**
     * Sets the value of the inquiryOrgStructureSrvOutputCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link InquiryOrgStructureSrvOutputCollection }
     *     
     */
    public void setInquiryOrgStructureSrvOutputCollection(InquiryOrgStructureSrvOutputCollection value) {
        this.inquiryOrgStructureSrvOutputCollection = value;
    }

}
