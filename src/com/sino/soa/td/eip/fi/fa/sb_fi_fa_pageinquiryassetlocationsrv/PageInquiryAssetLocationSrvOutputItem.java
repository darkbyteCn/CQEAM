
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetlocationsrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PageInquiryAssetLocationSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageInquiryAssetLocationSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LOCATION_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LOCATION_COMBINATION_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LOCATION_COMBINATION_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ENABLED_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="START_DATE_ACTIVE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="END_DATE_ACTIVE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
@XmlType(name = "PageInquiryAssetLocationSrvOutputItem", propOrder = {
    "locationid",
    "locationcombinationcode",
    "locationcombinationname",
    "enabledflag",
    "startdateactive",
    "enddateactive",
    "lastupdatedate"
})
public class PageInquiryAssetLocationSrvOutputItem {

    @XmlElement(name = "LOCATION_ID", required = true, nillable = true)
    protected BigDecimal locationid;
    @XmlElement(name = "LOCATION_COMBINATION_CODE", required = true, nillable = true)
    protected String locationcombinationcode;
    @XmlElement(name = "LOCATION_COMBINATION_NAME", required = true, nillable = true)
    protected String locationcombinationname;
    @XmlElement(name = "ENABLED_FLAG", required = true, nillable = true)
    protected String enabledflag;
    @XmlElement(name = "START_DATE_ACTIVE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startdateactive;
    @XmlElement(name = "END_DATE_ACTIVE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar enddateactive;
    @XmlElement(name = "LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastupdatedate;

    /**
     * Gets the value of the locationid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLOCATIONID() {
        return locationid;
    }

    /**
     * Sets the value of the locationid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLOCATIONID(BigDecimal value) {
        this.locationid = value;
    }

    /**
     * Gets the value of the locationcombinationcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOCATIONCOMBINATIONCODE() {
        return locationcombinationcode;
    }

    /**
     * Sets the value of the locationcombinationcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOCATIONCOMBINATIONCODE(String value) {
        this.locationcombinationcode = value;
    }

    /**
     * Gets the value of the locationcombinationname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOCATIONCOMBINATIONNAME() {
        return locationcombinationname;
    }

    /**
     * Sets the value of the locationcombinationname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOCATIONCOMBINATIONNAME(String value) {
        this.locationcombinationname = value;
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
     * Gets the value of the startdateactive property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSTARTDATEACTIVE() {
        return startdateactive;
    }

    /**
     * Sets the value of the startdateactive property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSTARTDATEACTIVE(XMLGregorianCalendar value) {
        this.startdateactive = value;
    }

    /**
     * Gets the value of the enddateactive property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getENDDATEACTIVE() {
        return enddateactive;
    }

    /**
     * Sets the value of the enddateactive property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setENDDATEACTIVE(XMLGregorianCalendar value) {
        this.enddateactive = value;
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
