
package com.sino.soa.td.eip.sc.pa.sb_sc_pa_pageinquirytaskinfosrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PageInquiryTaskInfoSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageInquiryTaskInfoSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ORG_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ORG_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PROJECT_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="SEGMENT1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TASK_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="TASK_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TASK_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="WBS_LEVEL" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="TASK_MANAGER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PARENT_TASK_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PARENT_TASK_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="START_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="COMPLETION_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="SERVICE_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CHARGEABLE_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BILLABLE_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="COST_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE6" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PM_PRODUCT_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PM_TASK_REFERENCE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "PageInquiryTaskInfoSrvOutputItem", propOrder = {
    "orgid",
    "orgname",
    "projectid",
    "segment1",
    "taskid",
    "tasknumber",
    "taskname",
    "description",
    "wbslevel",
    "taskmanager",
    "parenttaskid",
    "parenttasknum",
    "startdate",
    "completiondate",
    "servicetypecode",
    "chargeableflag",
    "billableflag",
    "costflag",
    "attribute1",
    "attribute2",
    "attribute3",
    "attribute4",
    "attribute5",
    "attribute6",
    "pmproductcode",
    "pmtaskreference",
    "creationdate",
    "lastupdatedate",
    "attribute8",
    "attribute9",
    "attribute10",
    "attribute11"
})
public class PageInquiryTaskInfoSrvOutputItem {

    @XmlElement(name = "ORG_ID", required = true, nillable = true)
    protected BigDecimal orgid;
    @XmlElement(name = "ORG_NAME", required = true, nillable = true)
    protected String orgname;
    @XmlElement(name = "PROJECT_ID", required = true, nillable = true)
    protected BigDecimal projectid;
    @XmlElement(name = "SEGMENT1", required = true, nillable = true)
    protected String segment1;
    @XmlElement(name = "TASK_ID", required = true, nillable = true)
    protected BigDecimal taskid;
    @XmlElement(name = "TASK_NUMBER", required = true, nillable = true)
    protected String tasknumber;
    @XmlElement(name = "TASK_NAME", required = true, nillable = true)
    protected String taskname;
    @XmlElement(name = "DESCRIPTION", required = true, nillable = true)
    protected String description;
    @XmlElement(name = "WBS_LEVEL", required = true, nillable = true)
    protected BigDecimal wbslevel;
    @XmlElement(name = "TASK_MANAGER", required = true, nillable = true)
    protected String taskmanager;
    @XmlElement(name = "PARENT_TASK_ID", required = true, nillable = true)
    protected BigDecimal parenttaskid;
    @XmlElement(name = "PARENT_TASK_NUM", required = true, nillable = true)
    protected String parenttasknum;
    @XmlElement(name = "START_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startdate;
    @XmlElement(name = "COMPLETION_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar completiondate;
    @XmlElement(name = "SERVICE_TYPE_CODE", required = true, nillable = true)
    protected String servicetypecode;
    @XmlElement(name = "CHARGEABLE_FLAG", required = true, nillable = true)
    protected String chargeableflag;
    @XmlElement(name = "BILLABLE_FLAG", required = true, nillable = true)
    protected String billableflag;
    @XmlElement(name = "COST_FLAG", required = true, nillable = true)
    protected String costflag;
    @XmlElement(name = "ATTRIBUTE1", required = true, nillable = true)
    protected String attribute1;
    @XmlElement(name = "ATTRIBUTE2", required = true, nillable = true)
    protected String attribute2;
    @XmlElement(name = "ATTRIBUTE3", required = true, nillable = true)
    protected String attribute3;
    @XmlElement(name = "ATTRIBUTE4", required = true, nillable = true)
    protected String attribute4;
    @XmlElement(name = "ATTRIBUTE5", required = true, nillable = true)
    protected String attribute5;
    @XmlElement(name = "ATTRIBUTE6", required = true, nillable = true)
    protected String attribute6;
    @XmlElement(name = "PM_PRODUCT_CODE", required = true, nillable = true)
    protected String pmproductcode;
    @XmlElement(name = "PM_TASK_REFERENCE", required = true, nillable = true)
    protected String pmtaskreference;
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
     * Gets the value of the taskid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTASKID() {
        return taskid;
    }

    /**
     * Sets the value of the taskid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTASKID(BigDecimal value) {
        this.taskid = value;
    }

    /**
     * Gets the value of the tasknumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTASKNUMBER() {
        return tasknumber;
    }

    /**
     * Sets the value of the tasknumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTASKNUMBER(String value) {
        this.tasknumber = value;
    }

    /**
     * Gets the value of the taskname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTASKNAME() {
        return taskname;
    }

    /**
     * Sets the value of the taskname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTASKNAME(String value) {
        this.taskname = value;
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
     * Gets the value of the wbslevel property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWBSLEVEL() {
        return wbslevel;
    }

    /**
     * Sets the value of the wbslevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWBSLEVEL(BigDecimal value) {
        this.wbslevel = value;
    }

    /**
     * Gets the value of the taskmanager property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTASKMANAGER() {
        return taskmanager;
    }

    /**
     * Sets the value of the taskmanager property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTASKMANAGER(String value) {
        this.taskmanager = value;
    }

    /**
     * Gets the value of the parenttaskid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPARENTTASKID() {
        return parenttaskid;
    }

    /**
     * Sets the value of the parenttaskid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPARENTTASKID(BigDecimal value) {
        this.parenttaskid = value;
    }

    /**
     * Gets the value of the parenttasknum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARENTTASKNUM() {
        return parenttasknum;
    }

    /**
     * Sets the value of the parenttasknum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARENTTASKNUM(String value) {
        this.parenttasknum = value;
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
     * Gets the value of the servicetypecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSERVICETYPECODE() {
        return servicetypecode;
    }

    /**
     * Sets the value of the servicetypecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSERVICETYPECODE(String value) {
        this.servicetypecode = value;
    }

    /**
     * Gets the value of the chargeableflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCHARGEABLEFLAG() {
        return chargeableflag;
    }

    /**
     * Sets the value of the chargeableflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCHARGEABLEFLAG(String value) {
        this.chargeableflag = value;
    }

    /**
     * Gets the value of the billableflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBILLABLEFLAG() {
        return billableflag;
    }

    /**
     * Sets the value of the billableflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBILLABLEFLAG(String value) {
        this.billableflag = value;
    }

    /**
     * Gets the value of the costflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOSTFLAG() {
        return costflag;
    }

    /**
     * Sets the value of the costflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOSTFLAG(String value) {
        this.costflag = value;
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
     * Gets the value of the attribute2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE2() {
        return attribute2;
    }

    /**
     * Sets the value of the attribute2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE2(String value) {
        this.attribute2 = value;
    }

    /**
     * Gets the value of the attribute3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE3() {
        return attribute3;
    }

    /**
     * Sets the value of the attribute3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE3(String value) {
        this.attribute3 = value;
    }

    /**
     * Gets the value of the attribute4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE4() {
        return attribute4;
    }

    /**
     * Sets the value of the attribute4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE4(String value) {
        this.attribute4 = value;
    }

    /**
     * Gets the value of the attribute5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE5() {
        return attribute5;
    }

    /**
     * Sets the value of the attribute5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE5(String value) {
        this.attribute5 = value;
    }

    /**
     * Gets the value of the attribute6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE6() {
        return attribute6;
    }

    /**
     * Sets the value of the attribute6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE6(String value) {
        this.attribute6 = value;
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
     * Gets the value of the pmtaskreference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPMTASKREFERENCE() {
        return pmtaskreference;
    }

    /**
     * Sets the value of the pmtaskreference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPMTASKREFERENCE(String value) {
        this.pmtaskreference = value;
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
