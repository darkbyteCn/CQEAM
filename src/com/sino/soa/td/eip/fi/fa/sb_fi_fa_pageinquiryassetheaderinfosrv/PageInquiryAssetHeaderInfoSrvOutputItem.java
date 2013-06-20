
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetheaderinfosrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PageInquiryAssetHeaderInfoSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageInquiryAssetHeaderInfoSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ASSET_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TAG_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATE_PLACED_IN_SERVICE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="LIFE_IN_MONTH" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="DEPRN_METHOD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DEPRECIATE_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FIXED_ASSETS_COST" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="DEPRN_RESERVE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="SALVAGE_VALUE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="IMPAIRMENT_RESERVE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MANUFACTURER_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MODEL_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SERIAL_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IN_USE_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="INVENTORIAL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_SOURCE_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PROJECT_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CONSTRUCTION_STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CATEGORY_CON_SEG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CATEGORY_DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_KEY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_KEY_DESC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CREATION_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="RETIREMENT_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FIXED_ASSETS_UNIT" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageInquiryAssetHeaderInfoSrvOutputItem", propOrder = {
    "booktypecode",
    "assetid",
    "assetnumber",
    "tagnumber",
    "dateplacedinservice",
    "lifeinmonth",
    "deprnmethod",
    "depreciateflag",
    "fixedassetscost",
    "deprnreserve",
    "salvagevalue",
    "impairmentreserve",
    "description",
    "manufacturername",
    "modelnumber",
    "serialnumber",
    "inuseflag",
    "inventorial",
    "assetsourceid",
    "projectnumber",
    "constructionstatus",
    "categoryconseg",
    "categorydescription",
    "assetkey",
    "assetkeydesc",
    "creationdate",
    "lastupdatedate",
    "retirementflag",
    "fixedassetsunit"
})
public class PageInquiryAssetHeaderInfoSrvOutputItem {

    @XmlElement(name = "BOOK_TYPE_CODE", required = true, nillable = true)
    protected String booktypecode;
    @XmlElement(name = "ASSET_ID", required = true, nillable = true)
    protected BigDecimal assetid;
    @XmlElement(name = "ASSET_NUMBER", required = true, nillable = true)
    protected String assetnumber;
    @XmlElement(name = "TAG_NUMBER", required = true, nillable = true)
    protected String tagnumber;
    @XmlElement(name = "DATE_PLACED_IN_SERVICE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateplacedinservice;
    @XmlElement(name = "LIFE_IN_MONTH", required = true, nillable = true)
    protected BigDecimal lifeinmonth;
    @XmlElement(name = "DEPRN_METHOD", required = true, nillable = true)
    protected String deprnmethod;
    @XmlElement(name = "DEPRECIATE_FLAG", required = true, nillable = true)
    protected String depreciateflag;
    @XmlElement(name = "FIXED_ASSETS_COST", required = true, nillable = true)
    protected BigDecimal fixedassetscost;
    @XmlElement(name = "DEPRN_RESERVE", required = true, nillable = true)
    protected BigDecimal deprnreserve;
    @XmlElement(name = "SALVAGE_VALUE", required = true, nillable = true)
    protected BigDecimal salvagevalue;
    @XmlElement(name = "IMPAIRMENT_RESERVE", required = true, nillable = true)
    protected BigDecimal impairmentreserve;
    @XmlElement(name = "DESCRIPTION", required = true, nillable = true)
    protected String description;
    @XmlElement(name = "MANUFACTURER_NAME", required = true, nillable = true)
    protected String manufacturername;
    @XmlElement(name = "MODEL_NUMBER", required = true, nillable = true)
    protected String modelnumber;
    @XmlElement(name = "SERIAL_NUMBER", required = true, nillable = true)
    protected String serialnumber;
    @XmlElement(name = "IN_USE_FLAG", required = true, nillable = true)
    protected String inuseflag;
    @XmlElement(name = "INVENTORIAL", required = true, nillable = true)
    protected String inventorial;
    @XmlElement(name = "ASSET_SOURCE_ID", required = true, nillable = true)
    protected String assetsourceid;
    @XmlElement(name = "PROJECT_NUMBER", required = true, nillable = true)
    protected String projectnumber;
    @XmlElement(name = "CONSTRUCTION_STATUS", required = true, nillable = true)
    protected String constructionstatus;
    @XmlElement(name = "CATEGORY_CON_SEG", required = true, nillable = true)
    protected String categoryconseg;
    @XmlElement(name = "CATEGORY_DESCRIPTION", required = true, nillable = true)
    protected String categorydescription;
    @XmlElement(name = "ASSET_KEY", required = true, nillable = true)
    protected String assetkey;
    @XmlElement(name = "ASSET_KEY_DESC", required = true, nillable = true)
    protected String assetkeydesc;
    @XmlElement(name = "CREATION_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationdate;
    @XmlElement(name = "LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastupdatedate;
    @XmlElement(name = "RETIREMENT_FLAG", required = true, nillable = true)
    protected String retirementflag;
    @XmlElement(name = "FIXED_ASSETS_UNIT", required = true, nillable = true)
    protected BigDecimal fixedassetsunit;

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
     * Gets the value of the dateplacedinservice property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATEPLACEDINSERVICE() {
        return dateplacedinservice;
    }

    /**
     * Sets the value of the dateplacedinservice property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATEPLACEDINSERVICE(XMLGregorianCalendar value) {
        this.dateplacedinservice = value;
    }

    /**
     * Gets the value of the lifeinmonth property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLIFEINMONTH() {
        return lifeinmonth;
    }

    /**
     * Sets the value of the lifeinmonth property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLIFEINMONTH(BigDecimal value) {
        this.lifeinmonth = value;
    }

    /**
     * Gets the value of the deprnmethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDEPRNMETHOD() {
        return deprnmethod;
    }

    /**
     * Sets the value of the deprnmethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDEPRNMETHOD(String value) {
        this.deprnmethod = value;
    }

    /**
     * Gets the value of the depreciateflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDEPRECIATEFLAG() {
        return depreciateflag;
    }

    /**
     * Sets the value of the depreciateflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDEPRECIATEFLAG(String value) {
        this.depreciateflag = value;
    }

    /**
     * Gets the value of the fixedassetscost property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFIXEDASSETSCOST() {
        return fixedassetscost;
    }

    /**
     * Sets the value of the fixedassetscost property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFIXEDASSETSCOST(BigDecimal value) {
        this.fixedassetscost = value;
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
     * Gets the value of the manufacturername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMANUFACTURERNAME() {
        return manufacturername;
    }

    /**
     * Sets the value of the manufacturername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMANUFACTURERNAME(String value) {
        this.manufacturername = value;
    }

    /**
     * Gets the value of the modelnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMODELNUMBER() {
        return modelnumber;
    }

    /**
     * Sets the value of the modelnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMODELNUMBER(String value) {
        this.modelnumber = value;
    }

    /**
     * Gets the value of the serialnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSERIALNUMBER() {
        return serialnumber;
    }

    /**
     * Sets the value of the serialnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSERIALNUMBER(String value) {
        this.serialnumber = value;
    }

    /**
     * Gets the value of the inuseflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINUSEFLAG() {
        return inuseflag;
    }

    /**
     * Sets the value of the inuseflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINUSEFLAG(String value) {
        this.inuseflag = value;
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
     * Gets the value of the assetsourceid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSETSOURCEID() {
        return assetsourceid;
    }

    /**
     * Sets the value of the assetsourceid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSETSOURCEID(String value) {
        this.assetsourceid = value;
    }

    /**
     * Gets the value of the projectnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROJECTNUMBER() {
        return projectnumber;
    }

    /**
     * Sets the value of the projectnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROJECTNUMBER(String value) {
        this.projectnumber = value;
    }

    /**
     * Gets the value of the constructionstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONSTRUCTIONSTATUS() {
        return constructionstatus;
    }

    /**
     * Sets the value of the constructionstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONSTRUCTIONSTATUS(String value) {
        this.constructionstatus = value;
    }

    /**
     * Gets the value of the categoryconseg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCATEGORYCONSEG() {
        return categoryconseg;
    }

    /**
     * Sets the value of the categoryconseg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCATEGORYCONSEG(String value) {
        this.categoryconseg = value;
    }

    /**
     * Gets the value of the categorydescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCATEGORYDESCRIPTION() {
        return categorydescription;
    }

    /**
     * Sets the value of the categorydescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCATEGORYDESCRIPTION(String value) {
        this.categorydescription = value;
    }

    /**
     * Gets the value of the assetkey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSETKEY() {
        return assetkey;
    }

    /**
     * Sets the value of the assetkey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSETKEY(String value) {
        this.assetkey = value;
    }

    /**
     * Gets the value of the assetkeydesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSETKEYDESC() {
        return assetkeydesc;
    }

    /**
     * Sets the value of the assetkeydesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSETKEYDESC(String value) {
        this.assetkeydesc = value;
    }

    /**
     * Gets the value of the creationdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCREATIONDATE() {
        return creationdate;
    }

    /**
     * Sets the value of the creationdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCREATIONDATE(XMLGregorianCalendar value) {
        this.creationdate = value;
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
     * Gets the value of the retirementflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRETIREMENTFLAG() {
        return retirementflag;
    }

    /**
     * Sets the value of the retirementflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRETIREMENTFLAG(String value) {
        this.retirementflag = value;
    }

    /**
     * Gets the value of the fixedassetsunit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFIXEDASSETSUNIT() {
        return fixedassetsunit;
    }

    /**
     * Sets the value of the fixedassetsunit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFIXEDASSETSUNIT(BigDecimal value) {
        this.fixedassetsunit = value;
    }

}
