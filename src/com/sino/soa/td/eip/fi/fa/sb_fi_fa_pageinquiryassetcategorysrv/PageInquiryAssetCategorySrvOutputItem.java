
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetcategorysrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PageInquiryAssetCategorySrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageInquiryAssetCategorySrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CATEGORY_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CATEGORY_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_COST_ACCOUNT_CCID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="RESERVE_ACCOUNT_CCID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ASSET_CLEARING_ACCOUNT_CCID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LIFE_IN_MONTHS" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PERCENT_SALVAGE_VALUE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ENABLED_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="INVENTORIAL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CAPITALIZE_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageInquiryAssetCategorySrvOutputItem", propOrder = {
    "categoryid",
    "description",
    "categorytype",
    "segment1",
    "segment2",
    "segment3",
    "assetcostaccountccid",
    "reserveaccountccid",
    "assetclearingaccountccid",
    "lifeinmonths",
    "percentsalvagevalue",
    "enabledflag",
    "attribute1",
    "inventorial",
    "capitalizeflag",
    "booktypecode",
    "lastupdatedate"
})
public class PageInquiryAssetCategorySrvOutputItem {

    @XmlElement(name = "CATEGORY_ID", required = true, nillable = true)
    protected BigDecimal categoryid;
    @XmlElement(name = "DESCRIPTION", required = true, nillable = true)
    protected String description;
    @XmlElement(name = "CATEGORY_TYPE", required = true, nillable = true)
    protected String categorytype;
    @XmlElement(name = "SEGMENT1", required = true, nillable = true)
    protected String segment1;
    @XmlElement(name = "SEGMENT2", required = true, nillable = true)
    protected String segment2;
    @XmlElement(name = "SEGMENT3", required = true, nillable = true)
    protected String segment3;
    @XmlElement(name = "ASSET_COST_ACCOUNT_CCID", required = true, nillable = true)
    protected BigDecimal assetcostaccountccid;
    @XmlElement(name = "RESERVE_ACCOUNT_CCID", required = true, nillable = true)
    protected BigDecimal reserveaccountccid;
    @XmlElement(name = "ASSET_CLEARING_ACCOUNT_CCID", required = true, nillable = true)
    protected BigDecimal assetclearingaccountccid;
    @XmlElement(name = "LIFE_IN_MONTHS", required = true, nillable = true)
    protected BigDecimal lifeinmonths;
    @XmlElement(name = "PERCENT_SALVAGE_VALUE", required = true, nillable = true)
    protected BigDecimal percentsalvagevalue;
    @XmlElement(name = "ENABLED_FLAG", required = true, nillable = true)
    protected String enabledflag;
    @XmlElement(name = "ATTRIBUTE1", required = true, nillable = true)
    protected String attribute1;
    @XmlElement(name = "INVENTORIAL", required = true, nillable = true)
    protected String inventorial;
    @XmlElement(name = "CAPITALIZE_FLAG", required = true, nillable = true)
    protected String capitalizeflag;
    @XmlElement(name = "BOOK_TYPE_CODE", required = true, nillable = true)
    protected String booktypecode;
    @XmlElement(name = "LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastupdatedate;

    /**
     * Gets the value of the categoryid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCATEGORYID() {
        return categoryid;
    }

    /**
     * Sets the value of the categoryid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCATEGORYID(BigDecimal value) {
        this.categoryid = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRIPTION() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRIPTION(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the categorytype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCATEGORYTYPE() {
        return categorytype;
    }

    /**
     * Sets the value of the categorytype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCATEGORYTYPE(String value) {
        this.categorytype = value;
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
     * Gets the value of the assetcostaccountccid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getASSETCOSTACCOUNTCCID() {
        return assetcostaccountccid;
    }

    /**
     * Sets the value of the assetcostaccountccid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setASSETCOSTACCOUNTCCID(BigDecimal value) {
        this.assetcostaccountccid = value;
    }

    /**
     * Gets the value of the reserveaccountccid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRESERVEACCOUNTCCID() {
        return reserveaccountccid;
    }

    /**
     * Sets the value of the reserveaccountccid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRESERVEACCOUNTCCID(BigDecimal value) {
        this.reserveaccountccid = value;
    }

    /**
     * Gets the value of the assetclearingaccountccid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getASSETCLEARINGACCOUNTCCID() {
        return assetclearingaccountccid;
    }

    /**
     * Sets the value of the assetclearingaccountccid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setASSETCLEARINGACCOUNTCCID(BigDecimal value) {
        this.assetclearingaccountccid = value;
    }

    /**
     * Gets the value of the lifeinmonths property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLIFEINMONTHS() {
        return lifeinmonths;
    }

    /**
     * Sets the value of the lifeinmonths property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLIFEINMONTHS(BigDecimal value) {
        this.lifeinmonths = value;
    }

    /**
     * Gets the value of the percentsalvagevalue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPERCENTSALVAGEVALUE() {
        return percentsalvagevalue;
    }

    /**
     * Sets the value of the percentsalvagevalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPERCENTSALVAGEVALUE(BigDecimal value) {
        this.percentsalvagevalue = value;
    }

    /**
     * Gets the value of the enabledflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getENABLEDFLAG() {
        return enabledflag;
    }

    /**
     * Sets the value of the enabledflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setENABLEDFLAG(String value) {
        this.enabledflag = value;
    }

    /**
     * Gets the value of the attribute1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE1() {
        return attribute1;
    }

    /**
     * Sets the value of the attribute1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE1(String value) {
        this.attribute1 = value;
    }

    /**
     * Gets the value of the inventorial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINVENTORIAL() {
        return inventorial;
    }

    /**
     * Sets the value of the inventorial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINVENTORIAL(String value) {
        this.inventorial = value;
    }

    /**
     * Gets the value of the capitalizeflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAPITALIZEFLAG() {
        return capitalizeflag;
    }

    /**
     * Sets the value of the capitalizeflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCAPITALIZEFLAG(String value) {
        this.capitalizeflag = value;
    }

    /**
     * Gets the value of the booktypecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBOOKTYPECODE() {
        return booktypecode;
    }

    /**
     * Sets the value of the booktypecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBOOKTYPECODE(String value) {
        this.booktypecode = value;
    }

    /**
     * Gets the value of the lastupdatedate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLASTUPDATEDATE() {
        return lastupdatedate;
    }

    /**
     * Sets the value of the lastupdatedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLASTUPDATEDATE(XMLGregorianCalendar value) {
        this.lastupdatedate = value;
    }

}
