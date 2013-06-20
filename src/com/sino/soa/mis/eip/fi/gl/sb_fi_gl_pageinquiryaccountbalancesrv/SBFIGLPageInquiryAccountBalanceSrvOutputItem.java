
package com.sino.soa.mis.eip.fi.gl.sb_fi_gl_pageinquiryaccountbalancesrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SB_FI_GL_PageInquiryAccountBalanceSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SB_FI_GL_PageInquiryAccountBalanceSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SET_OF_BOOKS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PERIOD_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CURRENCY_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ACTUAL_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CODE_COMBINATION_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="SEGMENT1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT1_DES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT2_DES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT3_DES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT4_DES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT5_DES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT6" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT6_DES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT7" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT7_DES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BEGIN_BALANCE_DR" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="BEGIN_BALANCE_CR" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="BEGIN_BALANCE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PERIOD_NET_DR" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PERIOD_NET_CR" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PERIOD_NET" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="END_BALANCE_DR" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="END_BALANCE_CR" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="END_BALANCE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="STRUCTURED_HIERARCHY_NAME_COM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="STRUCTURED_HIERARCHY_NAME_COS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CONTRACT_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CONTRACT_LINE_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SB_FI_GL_PageInquiryAccountBalanceSrvOutputItem", propOrder = {
    "setofbooks",
    "periodname",
    "currencycode",
    "actualflag",
    "codecombinationid",
    "segment1",
    "segment1DES",
    "segment2",
    "segment2DES",
    "segment3",
    "segment3DES",
    "segment4",
    "segment4DES",
    "segment5",
    "segment5DES",
    "segment6",
    "segment6DES",
    "segment7",
    "segment7DES",
    "beginbalancedr",
    "beginbalancecr",
    "beginbalance",
    "periodnetdr",
    "periodnetcr",
    "periodnet",
    "endbalancedr",
    "endbalancecr",
    "endbalance",
    "structuredhierarchynamecom",
    "structuredhierarchynamecos",
    "contractnum",
    "contractlinenum"
})
public class SBFIGLPageInquiryAccountBalanceSrvOutputItem {

    @XmlElement(name = "SET_OF_BOOKS", required = true)
    protected String setofbooks;
    @XmlElement(name = "PERIOD_NAME", required = true)
    protected String periodname;
    @XmlElement(name = "CURRENCY_CODE", required = true)
    protected String currencycode;
    @XmlElement(name = "ACTUAL_FLAG", required = true)
    protected String actualflag;
    @XmlElement(name = "CODE_COMBINATION_ID", required = true)
    protected BigDecimal codecombinationid;
    @XmlElement(name = "SEGMENT1", required = true)
    protected String segment1;
    @XmlElement(name = "SEGMENT1_DES", required = true)
    protected String segment1DES;
    @XmlElement(name = "SEGMENT2", required = true)
    protected String segment2;
    @XmlElement(name = "SEGMENT2_DES", required = true)
    protected String segment2DES;
    @XmlElement(name = "SEGMENT3", required = true)
    protected String segment3;
    @XmlElement(name = "SEGMENT3_DES", required = true)
    protected String segment3DES;
    @XmlElement(name = "SEGMENT4", required = true)
    protected String segment4;
    @XmlElement(name = "SEGMENT4_DES", required = true)
    protected String segment4DES;
    @XmlElement(name = "SEGMENT5", required = true)
    protected String segment5;
    @XmlElement(name = "SEGMENT5_DES", required = true)
    protected String segment5DES;
    @XmlElement(name = "SEGMENT6", required = true)
    protected String segment6;
    @XmlElement(name = "SEGMENT6_DES", required = true)
    protected String segment6DES;
    @XmlElement(name = "SEGMENT7", required = true)
    protected String segment7;
    @XmlElement(name = "SEGMENT7_DES", required = true)
    protected String segment7DES;
    @XmlElement(name = "BEGIN_BALANCE_DR", required = true)
    protected BigDecimal beginbalancedr;
    @XmlElement(name = "BEGIN_BALANCE_CR", required = true)
    protected BigDecimal beginbalancecr;
    @XmlElement(name = "BEGIN_BALANCE", required = true)
    protected BigDecimal beginbalance;
    @XmlElement(name = "PERIOD_NET_DR", required = true)
    protected BigDecimal periodnetdr;
    @XmlElement(name = "PERIOD_NET_CR", required = true)
    protected BigDecimal periodnetcr;
    @XmlElement(name = "PERIOD_NET", required = true)
    protected BigDecimal periodnet;
    @XmlElement(name = "END_BALANCE_DR", required = true)
    protected BigDecimal endbalancedr;
    @XmlElement(name = "END_BALANCE_CR", required = true)
    protected BigDecimal endbalancecr;
    @XmlElement(name = "END_BALANCE", required = true)
    protected BigDecimal endbalance;
    @XmlElement(name = "STRUCTURED_HIERARCHY_NAME_COM", required = true)
    protected String structuredhierarchynamecom;
    @XmlElement(name = "STRUCTURED_HIERARCHY_NAME_COS", required = true)
    protected String structuredhierarchynamecos;
    @XmlElement(name = "CONTRACT_NUM", required = true)
    protected String contractnum;
    @XmlElement(name = "CONTRACT_LINE_NUM", required = true)
    protected String contractlinenum;

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
     * Gets the value of the periodname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERIODNAME() {
        return periodname;
    }

    /**
     * Sets the value of the periodname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERIODNAME(String value) {
        this.periodname = value;
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
     * Gets the value of the codecombinationid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCODECOMBINATIONID() {
        return codecombinationid;
    }

    /**
     * Sets the value of the codecombinationid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCODECOMBINATIONID(BigDecimal value) {
        this.codecombinationid = value;
    }

    /**
     * Gets the value of the segment1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT1() {
        return segment1;
    }

    /**
     * Sets the value of the segment1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT1(String value) {
        this.segment1 = value;
    }

    /**
     * Gets the value of the segment1DES property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT1DES() {
        return segment1DES;
    }

    /**
     * Sets the value of the segment1DES property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT1DES(String value) {
        this.segment1DES = value;
    }

    /**
     * Gets the value of the segment2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT2() {
        return segment2;
    }

    /**
     * Sets the value of the segment2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT2(String value) {
        this.segment2 = value;
    }

    /**
     * Gets the value of the segment2DES property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT2DES() {
        return segment2DES;
    }

    /**
     * Sets the value of the segment2DES property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT2DES(String value) {
        this.segment2DES = value;
    }

    /**
     * Gets the value of the segment3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT3() {
        return segment3;
    }

    /**
     * Sets the value of the segment3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT3(String value) {
        this.segment3 = value;
    }

    /**
     * Gets the value of the segment3DES property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT3DES() {
        return segment3DES;
    }

    /**
     * Sets the value of the segment3DES property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT3DES(String value) {
        this.segment3DES = value;
    }

    /**
     * Gets the value of the segment4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT4() {
        return segment4;
    }

    /**
     * Sets the value of the segment4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT4(String value) {
        this.segment4 = value;
    }

    /**
     * Gets the value of the segment4DES property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT4DES() {
        return segment4DES;
    }

    /**
     * Sets the value of the segment4DES property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT4DES(String value) {
        this.segment4DES = value;
    }

    /**
     * Gets the value of the segment5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT5() {
        return segment5;
    }

    /**
     * Sets the value of the segment5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT5(String value) {
        this.segment5 = value;
    }

    /**
     * Gets the value of the segment5DES property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT5DES() {
        return segment5DES;
    }

    /**
     * Sets the value of the segment5DES property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT5DES(String value) {
        this.segment5DES = value;
    }

    /**
     * Gets the value of the segment6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT6() {
        return segment6;
    }

    /**
     * Sets the value of the segment6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT6(String value) {
        this.segment6 = value;
    }

    /**
     * Gets the value of the segment6DES property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT6DES() {
        return segment6DES;
    }

    /**
     * Sets the value of the segment6DES property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT6DES(String value) {
        this.segment6DES = value;
    }

    /**
     * Gets the value of the segment7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT7() {
        return segment7;
    }

    /**
     * Sets the value of the segment7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT7(String value) {
        this.segment7 = value;
    }

    /**
     * Gets the value of the segment7DES property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT7DES() {
        return segment7DES;
    }

    /**
     * Sets the value of the segment7DES property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT7DES(String value) {
        this.segment7DES = value;
    }

    /**
     * Gets the value of the beginbalancedr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBEGINBALANCEDR() {
        return beginbalancedr;
    }

    /**
     * Sets the value of the beginbalancedr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBEGINBALANCEDR(BigDecimal value) {
        this.beginbalancedr = value;
    }

    /**
     * Gets the value of the beginbalancecr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBEGINBALANCECR() {
        return beginbalancecr;
    }

    /**
     * Sets the value of the beginbalancecr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBEGINBALANCECR(BigDecimal value) {
        this.beginbalancecr = value;
    }

    /**
     * Gets the value of the beginbalance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBEGINBALANCE() {
        return beginbalance;
    }

    /**
     * Sets the value of the beginbalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBEGINBALANCE(BigDecimal value) {
        this.beginbalance = value;
    }

    /**
     * Gets the value of the periodnetdr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPERIODNETDR() {
        return periodnetdr;
    }

    /**
     * Sets the value of the periodnetdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPERIODNETDR(BigDecimal value) {
        this.periodnetdr = value;
    }

    /**
     * Gets the value of the periodnetcr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPERIODNETCR() {
        return periodnetcr;
    }

    /**
     * Sets the value of the periodnetcr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPERIODNETCR(BigDecimal value) {
        this.periodnetcr = value;
    }

    /**
     * Gets the value of the periodnet property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPERIODNET() {
        return periodnet;
    }

    /**
     * Sets the value of the periodnet property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPERIODNET(BigDecimal value) {
        this.periodnet = value;
    }

    /**
     * Gets the value of the endbalancedr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getENDBALANCEDR() {
        return endbalancedr;
    }

    /**
     * Sets the value of the endbalancedr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setENDBALANCEDR(BigDecimal value) {
        this.endbalancedr = value;
    }

    /**
     * Gets the value of the endbalancecr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getENDBALANCECR() {
        return endbalancecr;
    }

    /**
     * Sets the value of the endbalancecr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setENDBALANCECR(BigDecimal value) {
        this.endbalancecr = value;
    }

    /**
     * Gets the value of the endbalance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getENDBALANCE() {
        return endbalance;
    }

    /**
     * Sets the value of the endbalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setENDBALANCE(BigDecimal value) {
        this.endbalance = value;
    }

    /**
     * Gets the value of the structuredhierarchynamecom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTRUCTUREDHIERARCHYNAMECOM() {
        return structuredhierarchynamecom;
    }

    /**
     * Sets the value of the structuredhierarchynamecom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTRUCTUREDHIERARCHYNAMECOM(String value) {
        this.structuredhierarchynamecom = value;
    }

    /**
     * Gets the value of the structuredhierarchynamecos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTRUCTUREDHIERARCHYNAMECOS() {
        return structuredhierarchynamecos;
    }

    /**
     * Sets the value of the structuredhierarchynamecos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTRUCTUREDHIERARCHYNAMECOS(String value) {
        this.structuredhierarchynamecos = value;
    }

    /**
     * Gets the value of the contractnum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTRACTNUM() {
        return contractnum;
    }

    /**
     * Sets the value of the contractnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTRACTNUM(String value) {
        this.contractnum = value;
    }

    /**
     * Gets the value of the contractlinenum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTRACTLINENUM() {
        return contractlinenum;
    }

    /**
     * Sets the value of the contractlinenum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTRACTLINENUM(String value) {
        this.contractlinenum = value;
    }

}
