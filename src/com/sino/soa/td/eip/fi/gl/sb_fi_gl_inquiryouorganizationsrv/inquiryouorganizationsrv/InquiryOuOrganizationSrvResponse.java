
package com.sino.soa.td.eip.fi.gl.sb_fi_gl_inquiryouorganizationsrv.inquiryouorganizationsrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InquiryOuOrganizationSrvResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryOuOrganizationSrvResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InquiryOuOrganizationSrvOutputCollection" type="{http://eip.zte.com/common/gl/InquiryOuOrganizationSrv}InquiryOuOrganizationSrvOutputCollection"/>
 *         &lt;element name="ErrorFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryOuOrganizationSrvResponse", propOrder = {
    "inquiryOuOrganizationSrvOutputCollection",
    "errorFlag",
    "errorMessage"
})
public class InquiryOuOrganizationSrvResponse {

    @XmlElement(name = "InquiryOuOrganizationSrvOutputCollection", required = true)
    protected InquiryOuOrganizationSrvOutputCollection inquiryOuOrganizationSrvOutputCollection;
    @XmlElement(name = "ErrorFlag", required = true)
    protected String errorFlag;
    @XmlElement(name = "ErrorMessage", required = true)
    protected String errorMessage;

    /**
     * Gets the value of the inquiryOuOrganizationSrvOutputCollection property.
     * 
     * @return
     *     possible object is
     *     {@link InquiryOuOrganizationSrvOutputCollection }
     *     
     */
    public InquiryOuOrganizationSrvOutputCollection getInquiryOuOrganizationSrvOutputCollection() {
        return inquiryOuOrganizationSrvOutputCollection;
    }

    /**
     * Sets the value of the inquiryOuOrganizationSrvOutputCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link InquiryOuOrganizationSrvOutputCollection }
     *     
     */
    public void setInquiryOuOrganizationSrvOutputCollection(InquiryOuOrganizationSrvOutputCollection value) {
        this.inquiryOuOrganizationSrvOutputCollection = value;
    }

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

}
