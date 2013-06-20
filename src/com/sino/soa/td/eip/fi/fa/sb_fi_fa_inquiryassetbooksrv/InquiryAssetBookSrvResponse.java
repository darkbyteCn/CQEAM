
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetbooksrv;

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
 *         &lt;element name="InquiryAssetBookSrvOutputCollection" type="{http://eip.zte.com/common/fa/SB_FI_FA_InquiryAssetBookSrv}InquiryAssetBookSrvOutputCollection"/>
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
    "inquiryAssetBookSrvOutputCollection"
})
@XmlRootElement(name = "InquiryAssetBookSrvResponse")
public class InquiryAssetBookSrvResponse {

    @XmlElement(name = "ErrorFlag", required = true, nillable = true)
    protected String errorFlag;
    @XmlElement(name = "ErrorMessage", required = true, nillable = true)
    protected String errorMessage;
    @XmlElement(name = "InquiryAssetBookSrvOutputCollection", required = true)
    protected InquiryAssetBookSrvOutputCollection inquiryAssetBookSrvOutputCollection;

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
     * Gets the value of the inquiryAssetBookSrvOutputCollection property.
     * 
     * @return
     *     possible object is
     *     {@link InquiryAssetBookSrvOutputCollection }
     *     
     */
    public InquiryAssetBookSrvOutputCollection getInquiryAssetBookSrvOutputCollection() {
        return inquiryAssetBookSrvOutputCollection;
    }

    /**
     * Sets the value of the inquiryAssetBookSrvOutputCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link InquiryAssetBookSrvOutputCollection }
     *     
     */
    public void setInquiryAssetBookSrvOutputCollection(InquiryAssetBookSrvOutputCollection value) {
        this.inquiryAssetBookSrvOutputCollection = value;
    }

}
