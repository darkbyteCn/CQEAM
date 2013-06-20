
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetdistributionsrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PageInquiryAssetDistributionSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageInquiryAssetDistributionSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ASSET_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="DISTRIBUTION_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DEPARTMENT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LOCATION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LOCATION_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CODE_COMBINATION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DISTRIBUTION_UNIT" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ASSIGNED_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSIGNED_TO" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ASSIGNED_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EXPENSE_CODE_COMBINATION_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageInquiryAssetDistributionSrvOutputItem", propOrder = {
    "assetid",
    "distributionid",
    "lastupdatedate",
    "department",
    "location",
    "status",
    "locationid",
    "codecombination",
    "distributionunit",
    "assignedname",
    "assignedto",
    "assignednumber",
    "expensecodecombinationid"
})
public class PageInquiryAssetDistributionSrvOutputItem {

    @XmlElement(name = "ASSET_ID", required = true, nillable = true)
    protected BigDecimal assetid;
    @XmlElement(name = "DISTRIBUTION_ID", required = true, nillable = true)
    protected BigDecimal distributionid;
    @XmlElement(name = "LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastupdatedate;
    @XmlElement(name = "DEPARTMENT", required = true, nillable = true)
    protected String department;
    @XmlElement(name = "LOCATION", required = true, nillable = true)
    protected String location;
    @XmlElement(name = "STATUS", required = true, nillable = true)
    protected String status;
    @XmlElement(name = "LOCATION_ID", required = true, nillable = true)
    protected String locationid;
    @XmlElement(name = "CODE_COMBINATION", required = true, nillable = true)
    protected String codecombination;
    @XmlElement(name = "DISTRIBUTION_UNIT", required = true, nillable = true)
    protected BigDecimal distributionunit;
    @XmlElement(name = "ASSIGNED_NAME", required = true, nillable = true)
    protected String assignedname;
    @XmlElement(name = "ASSIGNED_TO", required = true, nillable = true)
    protected BigDecimal assignedto;
    @XmlElement(name = "ASSIGNED_NUMBER", required = true, nillable = true)
    protected String assignednumber;
    @XmlElement(name = "EXPENSE_CODE_COMBINATION_ID", required = true, nillable = true)
    protected BigDecimal expensecodecombinationid;

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
     * Gets the value of the distributionid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDISTRIBUTIONID() {
        return distributionid;
    }

    /**
     * Sets the value of the distributionid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDISTRIBUTIONID(BigDecimal value) {
        this.distributionid = value;
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
     * Gets the value of the department property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDEPARTMENT() {
        return department;
    }

    /**
     * Sets the value of the department property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDEPARTMENT(String value) {
        this.department = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOCATION() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOCATION(String value) {
        this.location = value;
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
     * Gets the value of the locationid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOCATIONID() {
        return locationid;
    }

    /**
     * Sets the value of the locationid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOCATIONID(String value) {
        this.locationid = value;
    }

    /**
     * Gets the value of the codecombination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODECOMBINATION() {
        return codecombination;
    }

    /**
     * Sets the value of the codecombination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODECOMBINATION(String value) {
        this.codecombination = value;
    }

    /**
     * Gets the value of the distributionunit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDISTRIBUTIONUNIT() {
        return distributionunit;
    }

    /**
     * Sets the value of the distributionunit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDISTRIBUTIONUNIT(BigDecimal value) {
        this.distributionunit = value;
    }

    /**
     * Gets the value of the assignedname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSIGNEDNAME() {
        return assignedname;
    }

    /**
     * Sets the value of the assignedname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSIGNEDNAME(String value) {
        this.assignedname = value;
    }

    /**
     * Gets the value of the assignedto property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getASSIGNEDTO() {
        return assignedto;
    }

    /**
     * Sets the value of the assignedto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setASSIGNEDTO(BigDecimal value) {
        this.assignedto = value;
    }

    /**
     * Gets the value of the assignednumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSIGNEDNUMBER() {
        return assignednumber;
    }

    /**
     * Sets the value of the assignednumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSIGNEDNUMBER(String value) {
        this.assignednumber = value;
    }

    /**
     * Gets the value of the expensecodecombinationid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getEXPENSECODECOMBINATIONID() {
        return expensecodecombinationid;
    }

    /**
     * Sets the value of the expensecodecombinationid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setEXPENSECODECOMBINATIONID(BigDecimal value) {
        this.expensecodecombinationid = value;
    }

}
