
package com.sino.soa.mis.eip.sc.po.sb_sc_pa_pageinquiryprojectinfosrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PageInquiryProjectInfoSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageInquiryProjectInfoSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ORG_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ORG_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TEMPLATE_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PROJECT_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="SEGMENT1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PROJECT_CLASS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PROJECT_MANAGER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PROJECT_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PROJECT_STATUS_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="START_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="COMPLETION_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="PM_PROJECT_REFERENCE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PM_PRODUCT_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CREATION_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ATTRIBUTE8" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE9" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE10" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE11" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageInquiryProjectInfoSrvOutputItem", propOrder = {
    "orgid",
    "orgname",
    "templateflag",
    "projectid",
    "segment1",
    "name",
    "description",
    "projectclass",
    "projectmanager",
    "projecttype",
    "projectstatuscode",
    "startdate",
    "completiondate",
    "pmprojectreference",
    "pmproductcode",
    "creationdate",
    "lastupdatedate",
    "attribute8",
    "attribute9",
    "attribute10",
    "attribute11"
})
public class PageInquiryProjectInfoSrvOutputItem {

    @XmlElement(name = "ORG_ID", required = true, nillable = true)
    protected BigDecimal orgid;
    @XmlElement(name = "ORG_NAME", required = true, nillable = true)
    protected String orgname;
    @XmlElement(name = "TEMPLATE_FLAG", required = true, nillable = true)
    protected String templateflag;
    @XmlElement(name = "PROJECT_ID", required = true, nillable = true)
    protected BigDecimal projectid;
    @XmlElement(name = "SEGMENT1", required = true, nillable = true)
    protected String segment1;
    @XmlElement(name = "NAME", required = true, nillable = true)
    protected String name;
    @XmlElement(name = "DESCRIPTION", required = true, nillable = true)
    protected String description;
    @XmlElement(name = "PROJECT_CLASS", required = true, nillable = true)
    protected String projectclass;
    @XmlElement(name = "PROJECT_MANAGER", required = true, nillable = true)
    protected String projectmanager;
    @XmlElement(name = "PROJECT_TYPE", required = true, nillable = true)
    protected String projecttype;
    @XmlElement(name = "PROJECT_STATUS_CODE", required = true, nillable = true)
    protected String projectstatuscode;
    @XmlElement(name = "START_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startdate;
    @XmlElement(name = "COMPLETION_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar completiondate;
    @XmlElement(name = "PM_PROJECT_REFERENCE", required = true, nillable = true)
    protected String pmprojectreference;
    @XmlElement(name = "PM_PRODUCT_CODE", required = true, nillable = true)
    protected String pmproductcode;
    @XmlElement(name = "CREATION_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationdate;
    @XmlElement(name = "LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastupdatedate;
    @XmlElement(name = "ATTRIBUTE8", required = true, nillable = true)
    protected String attribute8;
    @XmlElement(name = "ATTRIBUTE9", required = true, nillable = true)
    protected String attribute9;
    @XmlElement(name = "ATTRIBUTE10", required = true, nillable = true)
    protected String attribute10;
    @XmlElement(name = "ATTRIBUTE11", required = true, nillable = true)
    protected String attribute11;

    /**
     * Gets the value of the orgid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getORGID() {
        return orgid;
    }

    /**
     * Sets the value of the orgid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setORGID(BigDecimal value) {
        this.orgid = value;
    }

    /**
     * Gets the value of the orgname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGNAME() {
        return orgname;
    }

    /**
     * Sets the value of the orgname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGNAME(String value) {
        this.orgname = value;
    }

    /**
     * Gets the value of the templateflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTEMPLATEFLAG() {
        return templateflag;
    }

    /**
     * Sets the value of the templateflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTEMPLATEFLAG(String value) {
        this.templateflag = value;
    }

    /**
     * Gets the value of the projectid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPROJECTID() {
        return projectid;
    }

    /**
     * Sets the value of the projectid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPROJECTID(BigDecimal value) {
        this.projectid = value;
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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNAME() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNAME(String value) {
        this.name = value;
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
     * Gets the value of the projectclass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROJECTCLASS() {
        return projectclass;
    }

    /**
     * Sets the value of the projectclass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROJECTCLASS(String value) {
        this.projectclass = value;
    }

    /**
     * Gets the value of the projectmanager property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROJECTMANAGER() {
        return projectmanager;
    }

    /**
     * Sets the value of the projectmanager property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROJECTMANAGER(String value) {
        this.projectmanager = value;
    }

    /**
     * Gets the value of the projecttype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROJECTTYPE() {
        return projecttype;
    }

    /**
     * Sets the value of the projecttype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROJECTTYPE(String value) {
        this.projecttype = value;
    }

    /**
     * Gets the value of the projectstatuscode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROJECTSTATUSCODE() {
        return projectstatuscode;
    }

    /**
     * Sets the value of the projectstatuscode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROJECTSTATUSCODE(String value) {
        this.projectstatuscode = value;
    }

    /**
     * Gets the value of the startdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSTARTDATE() {
        return startdate;
    }

    /**
     * Sets the value of the startdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSTARTDATE(XMLGregorianCalendar value) {
        this.startdate = value;
    }

    /**
     * Gets the value of the completiondate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCOMPLETIONDATE() {
        return completiondate;
    }

    /**
     * Sets the value of the completiondate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCOMPLETIONDATE(XMLGregorianCalendar value) {
        this.completiondate = value;
    }

    /**
     * Gets the value of the pmprojectreference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPMPROJECTREFERENCE() {
        return pmprojectreference;
    }

    /**
     * Sets the value of the pmprojectreference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPMPROJECTREFERENCE(String value) {
        this.pmprojectreference = value;
    }

    /**
     * Gets the value of the pmproductcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPMPRODUCTCODE() {
        return pmproductcode;
    }

    /**
     * Sets the value of the pmproductcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPMPRODUCTCODE(String value) {
        this.pmproductcode = value;
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
     * Gets the value of the attribute8 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE8() {
        return attribute8;
    }

    /**
     * Sets the value of the attribute8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE8(String value) {
        this.attribute8 = value;
    }

    /**
     * Gets the value of the attribute9 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE9() {
        return attribute9;
    }

    /**
     * Sets the value of the attribute9 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE9(String value) {
        this.attribute9 = value;
    }

    /**
     * Gets the value of the attribute10 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE10() {
        return attribute10;
    }

    /**
     * Sets the value of the attribute10 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE10(String value) {
        this.attribute10 = value;
    }

    /**
     * Gets the value of the attribute11 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE11() {
        return attribute11;
    }

    /**
     * Sets the value of the attribute11 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE11(String value) {
        this.attribute11 = value;
    }

}
