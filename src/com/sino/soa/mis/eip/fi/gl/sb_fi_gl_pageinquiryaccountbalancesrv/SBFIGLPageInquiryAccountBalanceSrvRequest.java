
package com.sino.soa.mis.eip.fi.gl.sb_fi_gl_pageinquiryaccountbalancesrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.sino.soa.mis.eip.fi.gl.sb_fi_gl_pageinquiryaccountbalancesrv.msgheader.MsgHeader;


/**
 * <p>Java class for SB_FI_GL_PageInquiryAccountBalanceSrvRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SB_FI_GL_PageInquiryAccountBalanceSrvRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgHeader" type="{http://mss.cmcc.com/MsgHeader}MsgHeader"/>
 *         &lt;element name="SET_OF_BOOKS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PERIOD_NAME_FROM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PERIOD_NAME_TO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ACTUAL_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CURRENCY_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ACCOUNT_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="COMPANY_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CONCATENATED_SEGMENTS_FROM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CONCATENATED_SEGMENTS_TO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TEMPLATE_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_FI_GL_PageInquiryAccountBalanceSrvRequest", propOrder = {
    "msgHeader",
    "setofbooks",
    "periodnamefrom",
    "periodnameto",
    "actualflag",
    "currencycode",
    "accounttype",
    "companycode",
    "concatenatedsegmentsfrom",
    "concatenatedsegmentsto",
    "templatename"
})
public class SBFIGLPageInquiryAccountBalanceSrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "SET_OF_BOOKS", required = true, nillable = true)
    protected String setofbooks;
    @XmlElement(name = "PERIOD_NAME_FROM", required = true, nillable = true)
    protected String periodnamefrom;
    @XmlElement(name = "PERIOD_NAME_TO", required = true, nillable = true)
    protected String periodnameto;
    @XmlElement(name = "ACTUAL_FLAG", required = true, nillable = true)
    protected String actualflag;
    @XmlElement(name = "CURRENCY_CODE", required = true, nillable = true)
    protected String currencycode;
    @XmlElement(name = "ACCOUNT_TYPE", required = true, nillable = true)
    protected String accounttype;
    @XmlElement(name = "COMPANY_CODE", required = true, nillable = true)
    protected String companycode;
    @XmlElement(name = "CONCATENATED_SEGMENTS_FROM", required = true, nillable = true)
    protected String concatenatedsegmentsfrom;
    @XmlElement(name = "CONCATENATED_SEGMENTS_TO", required = true, nillable = true)
    protected String concatenatedsegmentsto;
    @XmlElement(name = "TEMPLATE_NAME", required = true, nillable = true)
    protected String templatename;

    /**
     * Gets the value of the msgHeader property.
     * 
     * @return
     *     possible object is
     *     {@link MsgHeader }
     *     
     */
    public MsgHeader getMsgHeader() {
        return msgHeader;
    }

    /**
     * Sets the value of the msgHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link MsgHeader }
     *     
     */
    public void setMsgHeader(MsgHeader value) {
        this.msgHeader = value;
    }

    /**
     * Gets the value of the setofbooks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSETOFBOOKS() {
        return setofbooks;
    }

    /**
     * Sets the value of the setofbooks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSETOFBOOKS(String value) {
        this.setofbooks = value;
    }

    /**
     * Gets the value of the periodnamefrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERIODNAMEFROM() {
        return periodnamefrom;
    }

    /**
     * Sets the value of the periodnamefrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERIODNAMEFROM(String value) {
        this.periodnamefrom = value;
    }

    /**
     * Gets the value of the periodnameto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERIODNAMETO() {
        return periodnameto;
    }

    /**
     * Sets the value of the periodnameto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERIODNAMETO(String value) {
        this.periodnameto = value;
    }

    /**
     * Gets the value of the actualflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACTUALFLAG() {
        return actualflag;
    }

    /**
     * Sets the value of the actualflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACTUALFLAG(String value) {
        this.actualflag = value;
    }

    /**
     * Gets the value of the currencycode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCURRENCYCODE() {
        return currencycode;
    }

    /**
     * Sets the value of the currencycode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCURRENCYCODE(String value) {
        this.currencycode = value;
    }

    /**
     * Gets the value of the accounttype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACCOUNTTYPE() {
        return accounttype;
    }

    /**
     * Sets the value of the accounttype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACCOUNTTYPE(String value) {
        this.accounttype = value;
    }

    /**
     * Gets the value of the companycode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOMPANYCODE() {
        return companycode;
    }

    /**
     * Sets the value of the companycode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOMPANYCODE(String value) {
        this.companycode = value;
    }

    /**
     * Gets the value of the concatenatedsegmentsfrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONCATENATEDSEGMENTSFROM() {
        return concatenatedsegmentsfrom;
    }

    /**
     * Sets the value of the concatenatedsegmentsfrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONCATENATEDSEGMENTSFROM(String value) {
        this.concatenatedsegmentsfrom = value;
    }

    /**
     * Gets the value of the concatenatedsegmentsto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONCATENATEDSEGMENTSTO() {
        return concatenatedsegmentsto;
    }

    /**
     * Sets the value of the concatenatedsegmentsto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONCATENATEDSEGMENTSTO(String value) {
        this.concatenatedsegmentsto = value;
    }

    /**
     * Gets the value of the templatename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTEMPLATENAME() {
        return templatename;
    }

    /**
     * Sets the value of the templatename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTEMPLATENAME(String value) {
        this.templatename = value;
    }

}
