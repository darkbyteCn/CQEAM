
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_pageinquiryassetdeprecationsrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InquiryAssetDeprecationSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryAssetDeprecationSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="TAG_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="COST" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="NET_BOOK_VALUE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PTD_IMPAIRMENT" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="YTD_IMPAIRMENT" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="IMPAIRMENT_RESERVE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PTD_DEPRN" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="YTD_DEPRN" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="DEPRN_RESERVE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PERIOD_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DEPRN_LEFT_MONTH" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="SALVAGE_VALUE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LIFE_YEARS" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="RETIREMENT_PENDING_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryAssetDeprecationSrvOutputItem", propOrder = {
    "booktypecode",
    "assetid",
    "tagnumber",
    "assetnumber",
    "description",
    "segment1",
    "segment2",
    "cost",
    "netbookvalue",
    "ptdimpairment",
    "ytdimpairment",
    "impairmentreserve",
    "ptddeprn",
    "ytddeprn",
    "deprnreserve",
    "periodname",
    "deprnleftmonth",
    "lastupdatedate",
    "salvagevalue",
    "lifeyears",
    "retirementpendingflag"
})
public class InquiryAssetDeprecationSrvOutputItem {

    @XmlElement(name = "BOOK_TYPE_CODE", required = true, nillable = true)
    protected String booktypecode;
    @XmlElement(name = "ASSET_ID", required = true, nillable = true)
    protected BigDecimal assetid;
    @XmlElement(name = "TAG_NUMBER", required = true, nillable = true)
    protected String tagnumber;
    @XmlElement(name = "ASSET_NUMBER", required = true, nillable = true)
    protected String assetnumber;
    @XmlElement(name = "DESCRIPTION", required = true, nillable = true)
    protected String description;
    @XmlElement(name = "SEGMENT1", required = true, nillable = true)
    protected String segment1;
    @XmlElement(name = "SEGMENT2", required = true, nillable = true)
    protected String segment2;
    @XmlElement(name = "COST", required = true, nillable = true)
    protected BigDecimal cost;
    @XmlElement(name = "NET_BOOK_VALUE", required = true, nillable = true)
    protected BigDecimal netbookvalue;
    @XmlElement(name = "PTD_IMPAIRMENT", required = true, nillable = true)
    protected BigDecimal ptdimpairment;
    @XmlElement(name = "YTD_IMPAIRMENT", required = true, nillable = true)
    protected BigDecimal ytdimpairment;
    @XmlElement(name = "IMPAIRMENT_RESERVE", required = true, nillable = true)
    protected BigDecimal impairmentreserve;
    @XmlElement(name = "PTD_DEPRN", required = true, nillable = true)
    protected BigDecimal ptddeprn;
    @XmlElement(name = "YTD_DEPRN", required = true, nillable = true)
    protected BigDecimal ytddeprn;
    @XmlElement(name = "DEPRN_RESERVE", required = true, nillable = true)
    protected BigDecimal deprnreserve;
    @XmlElement(name = "PERIOD_NAME", required = true, nillable = true)
    protected String periodname;
    @XmlElement(name = "DEPRN_LEFT_MONTH", required = true, nillable = true)
    protected BigDecimal deprnleftmonth;
    @XmlElement(name = "LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastupdatedate;
    @XmlElement(name = "SALVAGE_VALUE", required = true, nillable = true)
    protected BigDecimal salvagevalue;
    @XmlElement(name = "LIFE_YEARS", required = true, nillable = true)
    protected BigDecimal lifeyears;
    @XmlElement(name = "RETIREMENT_PENDING_FLAG", required = true, nillable = true)
    protected String retirementpendingflag;

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
     * Gets the value of the assetid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getASSETID() {
        return assetid;
    }

    /**
     * Sets the value of the assetid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setASSETID(BigDecimal value) {
        this.assetid = value;
    }

    /**
     * Gets the value of the tagnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTAGNUMBER() {
        return tagnumber;
    }

    /**
     * Sets the value of the tagnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTAGNUMBER(String value) {
        this.tagnumber = value;
    }

    /**
     * Gets the value of the assetnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSETNUMBER() {
        return assetnumber;
    }

    /**
     * Sets the value of the assetnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSETNUMBER(String value) {
        this.assetnumber = value;
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
     * Gets the value of the cost property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCOST() {
        return cost;
    }

    /**
     * Sets the value of the cost property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCOST(BigDecimal value) {
        this.cost = value;
    }

    /**
     * Gets the value of the netbookvalue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNETBOOKVALUE() {
        return netbookvalue;
    }

    /**
     * Sets the value of the netbookvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNETBOOKVALUE(BigDecimal value) {
        this.netbookvalue = value;
    }

    /**
     * Gets the value of the ptdimpairment property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPTDIMPAIRMENT() {
        return ptdimpairment;
    }

    /**
     * Sets the value of the ptdimpairment property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPTDIMPAIRMENT(BigDecimal value) {
        this.ptdimpairment = value;
    }

    /**
     * Gets the value of the ytdimpairment property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getYTDIMPAIRMENT() {
        return ytdimpairment;
    }

    /**
     * Sets the value of the ytdimpairment property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setYTDIMPAIRMENT(BigDecimal value) {
        this.ytdimpairment = value;
    }

    /**
     * Gets the value of the impairmentreserve property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPAIRMENTRESERVE() {
        return impairmentreserve;
    }

    /**
     * Sets the value of the impairmentreserve property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPAIRMENTRESERVE(BigDecimal value) {
        this.impairmentreserve = value;
    }

    /**
     * Gets the value of the ptddeprn property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPTDDEPRN() {
        return ptddeprn;
    }

    /**
     * Sets the value of the ptddeprn property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPTDDEPRN(BigDecimal value) {
        this.ptddeprn = value;
    }

    /**
     * Gets the value of the ytddeprn property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getYTDDEPRN() {
        return ytddeprn;
    }

    /**
     * Sets the value of the ytddeprn property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setYTDDEPRN(BigDecimal value) {
        this.ytddeprn = value;
    }

    /**
     * Gets the value of the deprnreserve property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDEPRNRESERVE() {
        return deprnreserve;
    }

    /**
     * Sets the value of the deprnreserve property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDEPRNRESERVE(BigDecimal value) {
        this.deprnreserve = value;
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
     * Gets the value of the deprnleftmonth property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDEPRNLEFTMONTH() {
        return deprnleftmonth;
    }

    /**
     * Sets the value of the deprnleftmonth property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDEPRNLEFTMONTH(BigDecimal value) {
        this.deprnleftmonth = value;
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

    /**
     * Gets the value of the salvagevalue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSALVAGEVALUE() {
        return salvagevalue;
    }

    /**
     * Sets the value of the salvagevalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSALVAGEVALUE(BigDecimal value) {
        this.salvagevalue = value;
    }

    /**
     * Gets the value of the lifeyears property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLIFEYEARS() {
        return lifeyears;
    }

    /**
     * Sets the value of the lifeyears property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLIFEYEARS(BigDecimal value) {
        this.lifeyears = value;
    }

    /**
     * Gets the value of the retirementpendingflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRETIREMENTPENDINGFLAG() {
        return retirementpendingflag;
    }

    /**
     * Sets the value of the retirementpendingflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRETIREMENTPENDINGFLAG(String value) {
        this.retirementpendingflag = value;
    }

}
