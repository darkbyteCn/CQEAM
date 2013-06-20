
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.inquiryassetperiodstatussrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InquiryAssetPeriodStatusSrvResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryAssetPeriodStatusSrvResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InquiryAssetPeriodStatusSrvOutputCollection" type="{http://eip.zte.com/common/InquiryAssetPeriodStatusSrv}InquiryAssetPeriodStatusSrvOutputCollection"/>
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
@XmlType(name = "InquiryAssetPeriodStatusSrvResponse", propOrder = {
    "inquiryAssetPeriodStatusSrvOutputCollection",
    "errorFlag",
    "errorMessage"
})
public class InquiryAssetPeriodStatusSrvResponse {

    @XmlElement(name = "InquiryAssetPeriodStatusSrvOutputCollection", required = true, nillable = true)
    protected InquiryAssetPeriodStatusSrvOutputCollection inquiryAssetPeriodStatusSrvOutputCollection;
    @XmlElement(name = "ErrorFlag", required = true, nillable = true)
    protected String errorFlag;
    @XmlElement(name = "ErrorMessage", required = true, nillable = true)
    protected String errorMessage;

    /**
     * Gets the value of the inquiryAssetPeriodStatusSrvOutputCollection property.
     * 
     * @return
     *     possible object is
     *     {@link InquiryAssetPeriodStatusSrvOutputCollection }
     *     
     */
    public InquiryAssetPeriodStatusSrvOutputCollection getInquiryAssetPeriodStatusSrvOutputCollection() {
        return inquiryAssetPeriodStatusSrvOutputCollection;
    }

    /**
     * Sets the value of the inquiryAssetPeriodStatusSrvOutputCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link InquiryAssetPeriodStatusSrvOutputCollection }
     *     
     */
    public void setInquiryAssetPeriodStatusSrvOutputCollection(InquiryAssetPeriodStatusSrvOutputCollection value) {
        this.inquiryAssetPeriodStatusSrvOutputCollection = value;
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
