
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_pageinquiryretiredassetdetailsrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PageInquiryRetiredAssetDetailSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageInquiryRetiredAssetDetailSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RETIREMENT_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ASSET_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TAG_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATE_PLACED_IN_SERVICE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DATE_RETIRED" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DATE_EFFECTIVE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="COST_RETIRED" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UNITS" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="RETIREMENT_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageInquiryRetiredAssetDetailSrvOutputItem", propOrder = {
    "retirementid",
    "booktypecode",
    "assetid",
    "assetnumber",
    "tagnumber",
    "dateplacedinservice",
    "dateretired",
    "dateeffective",
    "costretired",
    "status",
    "units",
    "retirementtypecode"
})
public class PageInquiryRetiredAssetDetailSrvOutputItem {

    @XmlElement(name = "RETIREMENT_ID", required = true, nillable = true)
    protected BigDecimal retirementid;
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
    @XmlElement(name = "DATE_RETIRED", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateretired;
    @XmlElement(name = "DATE_EFFECTIVE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateeffective;
    @XmlElement(name = "COST_RETIRED", required = true, nillable = true)
    protected BigDecimal costretired;
    @XmlElement(name = "STATUS", required = true, nillable = true)
    protected String status;
    @XmlElement(name = "UNITS", required = true, nillable = true)
    protected BigDecimal units;
    @XmlElement(name = "RETIREMENT_TYPE_CODE", required = true, nillable = true)
    protected String retirementtypecode;

    /**
     * Gets the value of the retirementid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRETIREMENTID() {
        return retirementid;
    }

    /**
     * Sets the value of the retirementid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRETIREMENTID(BigDecimal value) {
        this.retirementid = value;
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
     * Gets the value of the dateretired property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATERETIRED() {
        return dateretired;
    }

    /**
     * Sets the value of the dateretired property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATERETIRED(XMLGregorianCalendar value) {
        this.dateretired = value;
    }

    /**
     * Gets the value of the dateeffective property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATEEFFECTIVE() {
        return dateeffective;
    }

    /**
     * Sets the value of the dateeffective property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATEEFFECTIVE(XMLGregorianCalendar value) {
        this.dateeffective = value;
    }

    /**
     * Gets the value of the costretired property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCOSTRETIRED() {
        return costretired;
    }

    /**
     * Sets the value of the costretired property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCOSTRETIRED(BigDecimal value) {
        this.costretired = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATUS() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATUS(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the units property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUNITS() {
        return units;
    }

    /**
     * Sets the value of the units property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUNITS(BigDecimal value) {
        this.units = value;
    }

    /**
     * Gets the value of the retirementtypecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRETIREMENTTYPECODE() {
        return retirementtypecode;
    }

    /**
     * Sets the value of the retirementtypecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRETIREMENTTYPECODE(String value) {
        this.retirementtypecode = value;
    }

}
