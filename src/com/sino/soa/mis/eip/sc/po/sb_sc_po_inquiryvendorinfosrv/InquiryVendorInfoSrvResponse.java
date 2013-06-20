
package com.sino.soa.mis.eip.sc.po.sb_sc_po_inquiryvendorinfosrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InquiryVendorInfoSrvResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryVendorInfoSrvResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ErrorFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="InquiryVendorInfoSrvOutputCollection" type="{http://xmlns.oracle.com/SB_SC_PO_InquiryVendorInfoSrv}InquiryVendorInfoSrvOutputCollection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryVendorInfoSrvResponse", propOrder = {
    "errorFlag",
    "errorMessage",
    "inquiryVendorInfoSrvOutputCollection"
})
public class InquiryVendorInfoSrvResponse {

    @XmlElement(name = "ErrorFlag", required = true, nillable = true)
    protected String errorFlag;
    @XmlElement(name = "ErrorMessage", required = true, nillable = true)
    protected String errorMessage;
    @XmlElement(name = "InquiryVendorInfoSrvOutputCollection", required = true)
    protected InquiryVendorInfoSrvOutputCollection inquiryVendorInfoSrvOutputCollection;

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
     * Gets the value of the inquiryVendorInfoSrvOutputCollection property.
     * 
     * @return
     *     possible object is
     *     {@link InquiryVendorInfoSrvOutputCollection }
     *     
     */
    public InquiryVendorInfoSrvOutputCollection getInquiryVendorInfoSrvOutputCollection() {
        return inquiryVendorInfoSrvOutputCollection;
    }

    /**
     * Sets the value of the inquiryVendorInfoSrvOutputCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link InquiryVendorInfoSrvOutputCollection }
     *     
     */
    public void setInquiryVendorInfoSrvOutputCollection(InquiryVendorInfoSrvOutputCollection value) {
        this.inquiryVendorInfoSrvOutputCollection = value;
    }

}
