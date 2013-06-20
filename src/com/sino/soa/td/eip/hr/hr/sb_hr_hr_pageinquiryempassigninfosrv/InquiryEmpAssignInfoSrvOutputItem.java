
package com.sino.soa.td.eip.hr.hr.sb_hr_hr_pageinquiryempassigninfosrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InquiryEmpAssignInfoSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryEmpAssignInfoSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BUSINESS_GROUP_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="BUSINESS_GROUP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PERSON_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="EMPLOYEE_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSIGNMENT_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ASSIGNMENT_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EFFECTIVE_START_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="EFFECTIVE_END_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ASSIGNMENT_STATUS_TYPE_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSIGNMENT_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EMPLOYEE_CATEGORY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EMPLOYMENT_CATEGORY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="GRADE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="GRADE_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="JOB_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="JOB" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LOCATION_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LOCATION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORGANIZATION_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ORGANIZATION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="POSITION_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="POSITION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PRIMARY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PRIMARY_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SUPERVISOR_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="SUPERVISOR_ASSIGNMENT_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="SUPERVISOR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASS_ATTRIBUTE1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CREATION_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryEmpAssignInfoSrvOutputItem", propOrder = {
    "businessgroupid",
    "businessgroup",
    "personid",
    "employeenumber",
    "assignmentid",
    "assignmentnumber",
    "effectivestartdate",
    "effectiveenddate",
    "assignmentstatustypeid",
    "status",
    "assignmenttype",
    "employeecategory",
    "employmentcategory",
    "grade",
    "gradeid",
    "jobid",
    "job",
    "locationid",
    "location",
    "organizationid",
    "organization",
    "positionid",
    "position",
    "primary",
    "primaryflag",
    "supervisorid",
    "supervisorassignmentid",
    "supervisor",
    "assattribute1",
    "lastupdatedate",
    "creationdate"
})
public class InquiryEmpAssignInfoSrvOutputItem {

    @XmlElement(name = "BUSINESS_GROUP_ID", required = true, nillable = true)
    protected BigDecimal businessgroupid;
    @XmlElement(name = "BUSINESS_GROUP", required = true, nillable = true)
    protected String businessgroup;
    @XmlElement(name = "PERSON_ID", required = true, nillable = true)
    protected BigDecimal personid;
    @XmlElement(name = "EMPLOYEE_NUMBER", required = true, nillable = true)
    protected String employeenumber;
    @XmlElement(name = "ASSIGNMENT_ID", required = true, nillable = true)
    protected BigDecimal assignmentid;
    @XmlElement(name = "ASSIGNMENT_NUMBER", required = true, nillable = true)
    protected String assignmentnumber;
    @XmlElement(name = "EFFECTIVE_START_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectivestartdate;
    @XmlElement(name = "EFFECTIVE_END_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectiveenddate;
    @XmlElement(name = "ASSIGNMENT_STATUS_TYPE_ID", required = true, nillable = true)
    protected BigDecimal assignmentstatustypeid;
    @XmlElement(name = "STATUS", required = true, nillable = true)
    protected String status;
    @XmlElement(name = "ASSIGNMENT_TYPE", required = true, nillable = true)
    protected String assignmenttype;
    @XmlElement(name = "EMPLOYEE_CATEGORY", required = true, nillable = true)
    protected String employeecategory;
    @XmlElement(name = "EMPLOYMENT_CATEGORY", required = true, nillable = true)
    protected String employmentcategory;
    @XmlElement(name = "GRADE", required = true, nillable = true)
    protected String grade;
    @XmlElement(name = "GRADE_ID", required = true, nillable = true)
    protected BigDecimal gradeid;
    @XmlElement(name = "JOB_ID", required = true, nillable = true)
    protected BigDecimal jobid;
    @XmlElement(name = "JOB", required = true, nillable = true)
    protected String job;
    @XmlElement(name = "LOCATION_ID", required = true, nillable = true)
    protected BigDecimal locationid;
    @XmlElement(name = "LOCATION", required = true, nillable = true)
    protected String location;
    @XmlElement(name = "ORGANIZATION_ID", required = true, nillable = true)
    protected BigDecimal organizationid;
    @XmlElement(name = "ORGANIZATION", required = true, nillable = true)
    protected String organization;
    @XmlElement(name = "POSITION_ID", required = true, nillable = true)
    protected BigDecimal positionid;
    @XmlElement(name = "POSITION", required = true, nillable = true)
    protected String position;
    @XmlElement(name = "PRIMARY", required = true, nillable = true)
    protected String primary;
    @XmlElement(name = "PRIMARY_FLAG", required = true, nillable = true)
    protected String primaryflag;
    @XmlElement(name = "SUPERVISOR_ID", required = true, nillable = true)
    protected BigDecimal supervisorid;
    @XmlElement(name = "SUPERVISOR_ASSIGNMENT_ID", required = true, nillable = true)
    protected BigDecimal supervisorassignmentid;
    @XmlElement(name = "SUPERVISOR", required = true, nillable = true)
    protected String supervisor;
    @XmlElement(name = "ASS_ATTRIBUTE1", required = true, nillable = true)
    protected String assattribute1;
    @XmlElement(name = "LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastupdatedate;
    @XmlElement(name = "CREATION_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationdate;

    /**
     * Gets the value of the businessgroupid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBUSINESSGROUPID() {
        return businessgroupid;
    }

    /**
     * Sets the value of the businessgroupid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBUSINESSGROUPID(BigDecimal value) {
        this.businessgroupid = value;
    }

    /**
     * Gets the value of the businessgroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBUSINESSGROUP() {
        return businessgroup;
    }

    /**
     * Sets the value of the businessgroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUSINESSGROUP(String value) {
        this.businessgroup = value;
    }

    /**
     * Gets the value of the personid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPERSONID() {
        return personid;
    }

    /**
     * Sets the value of the personid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPERSONID(BigDecimal value) {
        this.personid = value;
    }

    /**
     * Gets the value of the employeenumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPLOYEENUMBER() {
        return employeenumber;
    }

    /**
     * Sets the value of the employeenumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPLOYEENUMBER(String value) {
        this.employeenumber = value;
    }

    /**
     * Gets the value of the assignmentid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getASSIGNMENTID() {
        return assignmentid;
    }

    /**
     * Sets the value of the assignmentid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setASSIGNMENTID(BigDecimal value) {
        this.assignmentid = value;
    }

    /**
     * Gets the value of the assignmentnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSIGNMENTNUMBER() {
        return assignmentnumber;
    }

    /**
     * Sets the value of the assignmentnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSIGNMENTNUMBER(String value) {
        this.assignmentnumber = value;
    }

    /**
     * Gets the value of the effectivestartdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEFFECTIVESTARTDATE() {
        return effectivestartdate;
    }

    /**
     * Sets the value of the effectivestartdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEFFECTIVESTARTDATE(XMLGregorianCalendar value) {
        this.effectivestartdate = value;
    }

    /**
     * Gets the value of the effectiveenddate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEFFECTIVEENDDATE() {
        return effectiveenddate;
    }

    /**
     * Sets the value of the effectiveenddate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEFFECTIVEENDDATE(XMLGregorianCalendar value) {
        this.effectiveenddate = value;
    }

    /**
     * Gets the value of the assignmentstatustypeid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getASSIGNMENTSTATUSTYPEID() {
        return assignmentstatustypeid;
    }

    /**
     * Sets the value of the assignmentstatustypeid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setASSIGNMENTSTATUSTYPEID(BigDecimal value) {
        this.assignmentstatustypeid = value;
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
     * Gets the value of the assignmenttype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSIGNMENTTYPE() {
        return assignmenttype;
    }

    /**
     * Sets the value of the assignmenttype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSIGNMENTTYPE(String value) {
        this.assignmenttype = value;
    }

    /**
     * Gets the value of the employeecategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPLOYEECATEGORY() {
        return employeecategory;
    }

    /**
     * Sets the value of the employeecategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPLOYEECATEGORY(String value) {
        this.employeecategory = value;
    }

    /**
     * Gets the value of the employmentcategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPLOYMENTCATEGORY() {
        return employmentcategory;
    }

    /**
     * Sets the value of the employmentcategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPLOYMENTCATEGORY(String value) {
        this.employmentcategory = value;
    }

    /**
     * Gets the value of the grade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGRADE() {
        return grade;
    }

    /**
     * Sets the value of the grade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGRADE(String value) {
        this.grade = value;
    }

    /**
     * Gets the value of the gradeid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGRADEID() {
        return gradeid;
    }

    /**
     * Sets the value of the gradeid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGRADEID(BigDecimal value) {
        this.gradeid = value;
    }

    /**
     * Gets the value of the jobid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getJOBID() {
        return jobid;
    }

    /**
     * Sets the value of the jobid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setJOBID(BigDecimal value) {
        this.jobid = value;
    }

    /**
     * Gets the value of the job property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJOB() {
        return job;
    }

    /**
     * Sets the value of the job property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJOB(String value) {
        this.job = value;
    }

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
     * Gets the value of the organizationid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getORGANIZATIONID() {
        return organizationid;
    }

    /**
     * Sets the value of the organizationid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setORGANIZATIONID(BigDecimal value) {
        this.organizationid = value;
    }

    /**
     * Gets the value of the organization property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGANIZATION() {
        return organization;
    }

    /**
     * Sets the value of the organization property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGANIZATION(String value) {
        this.organization = value;
    }

    /**
     * Gets the value of the positionid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPOSITIONID() {
        return positionid;
    }

    /**
     * Sets the value of the positionid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPOSITIONID(BigDecimal value) {
        this.positionid = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOSITION() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOSITION(String value) {
        this.position = value;
    }

    /**
     * Gets the value of the primary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRIMARY() {
        return primary;
    }

    /**
     * Sets the value of the primary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRIMARY(String value) {
        this.primary = value;
    }

    /**
     * Gets the value of the primaryflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRIMARYFLAG() {
        return primaryflag;
    }

    /**
     * Sets the value of the primaryflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRIMARYFLAG(String value) {
        this.primaryflag = value;
    }

    /**
     * Gets the value of the supervisorid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSUPERVISORID() {
        return supervisorid;
    }

    /**
     * Sets the value of the supervisorid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSUPERVISORID(BigDecimal value) {
        this.supervisorid = value;
    }

    /**
     * Gets the value of the supervisorassignmentid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSUPERVISORASSIGNMENTID() {
        return supervisorassignmentid;
    }

    /**
     * Sets the value of the supervisorassignmentid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSUPERVISORASSIGNMENTID(BigDecimal value) {
        this.supervisorassignmentid = value;
    }

    /**
     * Gets the value of the supervisor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSUPERVISOR() {
        return supervisor;
    }

    /**
     * Sets the value of the supervisor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSUPERVISOR(String value) {
        this.supervisor = value;
    }

    /**
     * Gets the value of the assattribute1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSATTRIBUTE1() {
        return assattribute1;
    }

    /**
     * Sets the value of the assattribute1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSATTRIBUTE1(String value) {
        this.assattribute1 = value;
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

}
