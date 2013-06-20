
package com.sino.soa.td.eip.fi.gl.sb_fi_gl_pageinquiryaccountbalancesrv;

import java.math.BigDecimal;
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
 *         &lt;element name="TOTAL_RECORD" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="TOTAL_PAGE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PAGE_SIZE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="CURRENT_PAGE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="INSTANCE_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="SB_FI_GL_PageInquiryAccountBalanceSrvOutputCollection" type="{http://mss.cmcc.com/SB_FI_GL_PageInquiryAccountBalanceSrv}SB_FI_GL_PageInquiryAccountBalanceSrvOutputCollection"/>
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
    "totalrecord",
    "totalpage",
    "pagesize",
    "currentpage",
    "instanceid",
    "sbfiglPageInquiryAccountBalanceSrvOutputCollection"
})
@XmlRootElement(name = "SB_FI_GL_PageInquiryAccountBalanceSrvResponse")
public class SBFIGLPageInquiryAccountBalanceSrvResponse {

    @XmlElement(name = "ErrorFlag", required = true)
    protected String errorFlag;
    @XmlElement(name = "ErrorMessage", required = true)
    protected String errorMessage;
    @XmlElement(name = "TOTAL_RECORD", required = true)
    protected BigDecimal totalrecord;
    @XmlElement(name = "TOTAL_PAGE", required = true)
    protected BigDecimal totalpage;
    @XmlElement(name = "PAGE_SIZE", required = true)
    protected BigDecimal pagesize;
    @XmlElement(name = "CURRENT_PAGE", required = true)
    protected BigDecimal currentpage;
    @XmlElement(name = "INSTANCE_ID", required = true)
    protected BigDecimal instanceid;
    @XmlElement(name = "SB_FI_GL_PageInquiryAccountBalanceSrvOutputCollection", required = true)
    protected SBFIGLPageInquiryAccountBalanceSrvOutputCollection sbfiglPageInquiryAccountBalanceSrvOutputCollection;

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
     * Gets the value of the totalrecord property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTOTALRECORD() {
        return totalrecord;
    }

    /**
     * Sets the value of the totalrecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTOTALRECORD(BigDecimal value) {
        this.totalrecord = value;
    }

    /**
     * Gets the value of the totalpage property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTOTALPAGE() {
        return totalpage;
    }

    /**
     * Sets the value of the totalpage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTOTALPAGE(BigDecimal value) {
        this.totalpage = value;
    }

    /**
     * Gets the value of the pagesize property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPAGESIZE() {
        return pagesize;
    }

    /**
     * Sets the value of the pagesize property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPAGESIZE(BigDecimal value) {
        this.pagesize = value;
    }

    /**
     * Gets the value of the currentpage property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCURRENTPAGE() {
        return currentpage;
    }

    /**
     * Sets the value of the currentpage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCURRENTPAGE(BigDecimal value) {
        this.currentpage = value;
    }

    /**
     * Gets the value of the instanceid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getINSTANCEID() {
        return instanceid;
    }

    /**
     * Sets the value of the instanceid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setINSTANCEID(BigDecimal value) {
        this.instanceid = value;
    }

    /**
     * Gets the value of the sbfiglPageInquiryAccountBalanceSrvOutputCollection property.
     * 
     * @return
     *     possible object is
     *     {@link SBFIGLPageInquiryAccountBalanceSrvOutputCollection }
     *     
     */
    public SBFIGLPageInquiryAccountBalanceSrvOutputCollection getSBFIGLPageInquiryAccountBalanceSrvOutputCollection() {
        return sbfiglPageInquiryAccountBalanceSrvOutputCollection;
    }

    /**
     * Sets the value of the sbfiglPageInquiryAccountBalanceSrvOutputCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link SBFIGLPageInquiryAccountBalanceSrvOutputCollection }
     *     
     */
    public void setSBFIGLPageInquiryAccountBalanceSrvOutputCollection(SBFIGLPageInquiryAccountBalanceSrvOutputCollection value) {
        this.sbfiglPageInquiryAccountBalanceSrvOutputCollection = value;
    }

}
