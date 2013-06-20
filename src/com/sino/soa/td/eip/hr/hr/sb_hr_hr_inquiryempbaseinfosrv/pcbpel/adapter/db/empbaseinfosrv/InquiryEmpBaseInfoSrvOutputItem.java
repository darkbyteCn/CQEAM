
package com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryempbaseinfosrv.pcbpel.adapter.db.empbaseinfosrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InquiryEmpBaseInfoSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryEmpBaseInfoSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PERSON_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="EFFECTIVE_START_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="EFFECTIVE_END_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="BUSINESS_GROUP_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PERSON_TYPE_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PERSON_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LAST_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="START_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="BLOOD_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATE_OF_BIRTH" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="D_AGE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="EMAIL_ADDRESS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EMPLOYEE_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FIRST_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FULL_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MAILSTOP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MARITAL_STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MIDDLE_NAMES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NATIONALITY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NATIONAL_IDENTIFIER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ON_MILITARY_SERVICE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEX" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE6" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE7" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE8" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE9" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE10" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE11" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE12" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE13" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE14" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE15" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE16" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE17" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE18" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE19" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE20" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE21" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE22" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE23" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE24" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE25" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE26" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE27" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE28" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE29" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE30" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CREATION_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="PER_INFORMATION4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PER_INFORMATION5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PER_INFORMATION6" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PER_INFORMATION7" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PER_INFORMATION8" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PER_INFORMATION10" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PER_INFORMATION11" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PER_INFORMATION12" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PER_INFORMATION14" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PER_INFORMATION15" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PER_INFORMATION17" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PER_INFORMATION18" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryEmpBaseInfoSrvOutputItem", propOrder = {
    "personid",
    "effectivestartdate",
    "effectiveenddate",
    "businessgroupid",
    "persontypeid",
    "persontype",
    "lastname",
    "startdate",
    "bloodtype",
    "dateofbirth",
    "dage",
    "emailaddress",
    "employeenumber",
    "firstname",
    "fullname",
    "mailstop",
    "maritalstatus",
    "middlenames",
    "nationality",
    "nationalidentifier",
    "onmilitaryservice",
    "sex",
    "attribute1",
    "attribute2",
    "attribute3",
    "attribute4",
    "attribute5",
    "attribute6",
    "attribute7",
    "attribute8",
    "attribute9",
    "attribute10",
    "attribute11",
    "attribute12",
    "attribute13",
    "attribute14",
    "attribute15",
    "attribute16",
    "attribute17",
    "attribute18",
    "attribute19",
    "attribute20",
    "attribute21",
    "attribute22",
    "attribute23",
    "attribute24",
    "attribute25",
    "attribute26",
    "attribute27",
    "attribute28",
    "attribute29",
    "attribute30",
    "lastupdatedate",
    "creationdate",
    "perinformation4",
    "perinformation5",
    "perinformation6",
    "perinformation7",
    "perinformation8",
    "perinformation10",
    "perinformation11",
    "perinformation12",
    "perinformation14",
    "perinformation15",
    "perinformation17",
    "perinformation18"
})
public class InquiryEmpBaseInfoSrvOutputItem {

    @XmlElement(name = "PERSON_ID", required = true, nillable = true)
    protected BigDecimal personid;
    @XmlElement(name = "EFFECTIVE_START_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectivestartdate;
    @XmlElement(name = "EFFECTIVE_END_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectiveenddate;
    @XmlElement(name = "BUSINESS_GROUP_ID", required = true, nillable = true)
    protected BigDecimal businessgroupid;
    @XmlElement(name = "PERSON_TYPE_ID", required = true, nillable = true)
    protected BigDecimal persontypeid;
    @XmlElement(name = "PERSON_TYPE", required = true, nillable = true)
    protected String persontype;
    @XmlElement(name = "LAST_NAME", required = true, nillable = true)
    protected String lastname;
    @XmlElement(name = "START_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startdate;
    @XmlElement(name = "BLOOD_TYPE", required = true, nillable = true)
    protected String bloodtype;
    @XmlElement(name = "DATE_OF_BIRTH", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateofbirth;
    @XmlElement(name = "D_AGE", required = true, nillable = true)
    protected BigDecimal dage;
    @XmlElement(name = "EMAIL_ADDRESS", required = true, nillable = true)
    protected String emailaddress;
    @XmlElement(name = "EMPLOYEE_NUMBER", required = true, nillable = true)
    protected String employeenumber;
    @XmlElement(name = "FIRST_NAME", required = true, nillable = true)
    protected String firstname;
    @XmlElement(name = "FULL_NAME", required = true, nillable = true)
    protected String fullname;
    @XmlElement(name = "MAILSTOP", required = true, nillable = true)
    protected String mailstop;
    @XmlElement(name = "MARITAL_STATUS", required = true, nillable = true)
    protected String maritalstatus;
    @XmlElement(name = "MIDDLE_NAMES", required = true, nillable = true)
    protected String middlenames;
    @XmlElement(name = "NATIONALITY", required = true, nillable = true)
    protected String nationality;
    @XmlElement(name = "NATIONAL_IDENTIFIER", required = true, nillable = true)
    protected String nationalidentifier;
    @XmlElement(name = "ON_MILITARY_SERVICE", required = true, nillable = true)
    protected String onmilitaryservice;
    @XmlElement(name = "SEX", required = true, nillable = true)
    protected String sex;
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
    @XmlElement(name = "ATTRIBUTE7", required = true, nillable = true)
    protected String attribute7;
    @XmlElement(name = "ATTRIBUTE8", required = true, nillable = true)
    protected String attribute8;
    @XmlElement(name = "ATTRIBUTE9", required = true, nillable = true)
    protected String attribute9;
    @XmlElement(name = "ATTRIBUTE10", required = true, nillable = true)
    protected String attribute10;
    @XmlElement(name = "ATTRIBUTE11", required = true, nillable = true)
    protected String attribute11;
    @XmlElement(name = "ATTRIBUTE12", required = true, nillable = true)
    protected String attribute12;
    @XmlElement(name = "ATTRIBUTE13", required = true, nillable = true)
    protected String attribute13;
    @XmlElement(name = "ATTRIBUTE14", required = true, nillable = true)
    protected String attribute14;
    @XmlElement(name = "ATTRIBUTE15", required = true, nillable = true)
    protected String attribute15;
    @XmlElement(name = "ATTRIBUTE16", required = true, nillable = true)
    protected String attribute16;
    @XmlElement(name = "ATTRIBUTE17", required = true, nillable = true)
    protected String attribute17;
    @XmlElement(name = "ATTRIBUTE18", required = true, nillable = true)
    protected String attribute18;
    @XmlElement(name = "ATTRIBUTE19", required = true, nillable = true)
    protected String attribute19;
    @XmlElement(name = "ATTRIBUTE20", required = true, nillable = true)
    protected String attribute20;
    @XmlElement(name = "ATTRIBUTE21", required = true, nillable = true)
    protected String attribute21;
    @XmlElement(name = "ATTRIBUTE22", required = true, nillable = true)
    protected String attribute22;
    @XmlElement(name = "ATTRIBUTE23", required = true, nillable = true)
    protected String attribute23;
    @XmlElement(name = "ATTRIBUTE24", required = true, nillable = true)
    protected String attribute24;
    @XmlElement(name = "ATTRIBUTE25", required = true, nillable = true)
    protected String attribute25;
    @XmlElement(name = "ATTRIBUTE26", required = true, nillable = true)
    protected String attribute26;
    @XmlElement(name = "ATTRIBUTE27", required = true, nillable = true)
    protected String attribute27;
    @XmlElement(name = "ATTRIBUTE28", required = true, nillable = true)
    protected String attribute28;
    @XmlElement(name = "ATTRIBUTE29", required = true, nillable = true)
    protected String attribute29;
    @XmlElement(name = "ATTRIBUTE30", required = true, nillable = true)
    protected String attribute30;
    @XmlElement(name = "LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastupdatedate;
    @XmlElement(name = "CREATION_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationdate;
    @XmlElement(name = "PER_INFORMATION4", required = true, nillable = true)
    protected String perinformation4;
    @XmlElement(name = "PER_INFORMATION5", required = true, nillable = true)
    protected String perinformation5;
    @XmlElement(name = "PER_INFORMATION6", required = true, nillable = true)
    protected String perinformation6;
    @XmlElement(name = "PER_INFORMATION7", required = true, nillable = true)
    protected String perinformation7;
    @XmlElement(name = "PER_INFORMATION8", required = true, nillable = true)
    protected String perinformation8;
    @XmlElement(name = "PER_INFORMATION10", required = true, nillable = true)
    protected String perinformation10;
    @XmlElement(name = "PER_INFORMATION11", required = true, nillable = true)
    protected String perinformation11;
    @XmlElement(name = "PER_INFORMATION12", required = true, nillable = true)
    protected String perinformation12;
    @XmlElement(name = "PER_INFORMATION14", required = true, nillable = true)
    protected String perinformation14;
    @XmlElement(name = "PER_INFORMATION15", required = true, nillable = true)
    protected String perinformation15;
    @XmlElement(name = "PER_INFORMATION17", required = true, nillable = true)
    protected String perinformation17;
    @XmlElement(name = "PER_INFORMATION18", required = true, nillable = true)
    protected String perinformation18;

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
     * Gets the value of the persontypeid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPERSONTYPEID() {
        return persontypeid;
    }

    /**
     * Sets the value of the persontypeid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPERSONTYPEID(BigDecimal value) {
        this.persontypeid = value;
    }

    /**
     * Gets the value of the persontype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERSONTYPE() {
        return persontype;
    }

    /**
     * Sets the value of the persontype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERSONTYPE(String value) {
        this.persontype = value;
    }

    /**
     * Gets the value of the lastname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLASTNAME() {
        return lastname;
    }

    /**
     * Sets the value of the lastname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLASTNAME(String value) {
        this.lastname = value;
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
     * Gets the value of the bloodtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBLOODTYPE() {
        return bloodtype;
    }

    /**
     * Sets the value of the bloodtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBLOODTYPE(String value) {
        this.bloodtype = value;
    }

    /**
     * Gets the value of the dateofbirth property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATEOFBIRTH() {
        return dateofbirth;
    }

    /**
     * Sets the value of the dateofbirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATEOFBIRTH(XMLGregorianCalendar value) {
        this.dateofbirth = value;
    }

    /**
     * Gets the value of the dage property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDAGE() {
        return dage;
    }

    /**
     * Sets the value of the dage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDAGE(BigDecimal value) {
        this.dage = value;
    }

    /**
     * Gets the value of the emailaddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMAILADDRESS() {
        return emailaddress;
    }

    /**
     * Sets the value of the emailaddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMAILADDRESS(String value) {
        this.emailaddress = value;
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
     * Gets the value of the firstname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIRSTNAME() {
        return firstname;
    }

    /**
     * Sets the value of the firstname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIRSTNAME(String value) {
        this.firstname = value;
    }

    /**
     * Gets the value of the fullname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFULLNAME() {
        return fullname;
    }

    /**
     * Sets the value of the fullname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFULLNAME(String value) {
        this.fullname = value;
    }

    /**
     * Gets the value of the mailstop property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMAILSTOP() {
        return mailstop;
    }

    /**
     * Sets the value of the mailstop property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMAILSTOP(String value) {
        this.mailstop = value;
    }

    /**
     * Gets the value of the maritalstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMARITALSTATUS() {
        return maritalstatus;
    }

    /**
     * Sets the value of the maritalstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMARITALSTATUS(String value) {
        this.maritalstatus = value;
    }

    /**
     * Gets the value of the middlenames property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMIDDLENAMES() {
        return middlenames;
    }

    /**
     * Sets the value of the middlenames property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMIDDLENAMES(String value) {
        this.middlenames = value;
    }

    /**
     * Gets the value of the nationality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNATIONALITY() {
        return nationality;
    }

    /**
     * Sets the value of the nationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNATIONALITY(String value) {
        this.nationality = value;
    }

    /**
     * Gets the value of the nationalidentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNATIONALIDENTIFIER() {
        return nationalidentifier;
    }

    /**
     * Sets the value of the nationalidentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNATIONALIDENTIFIER(String value) {
        this.nationalidentifier = value;
    }

    /**
     * Gets the value of the onmilitaryservice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getONMILITARYSERVICE() {
        return onmilitaryservice;
    }

    /**
     * Sets the value of the onmilitaryservice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setONMILITARYSERVICE(String value) {
        this.onmilitaryservice = value;
    }

    /**
     * Gets the value of the sex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEX() {
        return sex;
    }

    /**
     * Sets the value of the sex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEX(String value) {
        this.sex = value;
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
     * Gets the value of the attribute7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE7() {
        return attribute7;
    }

    /**
     * Sets the value of the attribute7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE7(String value) {
        this.attribute7 = value;
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

    /**
     * Gets the value of the attribute12 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE12() {
        return attribute12;
    }

    /**
     * Sets the value of the attribute12 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE12(String value) {
        this.attribute12 = value;
    }

    /**
     * Gets the value of the attribute13 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE13() {
        return attribute13;
    }

    /**
     * Sets the value of the attribute13 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE13(String value) {
        this.attribute13 = value;
    }

    /**
     * Gets the value of the attribute14 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE14() {
        return attribute14;
    }

    /**
     * Sets the value of the attribute14 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE14(String value) {
        this.attribute14 = value;
    }

    /**
     * Gets the value of the attribute15 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE15() {
        return attribute15;
    }

    /**
     * Sets the value of the attribute15 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE15(String value) {
        this.attribute15 = value;
    }

    /**
     * Gets the value of the attribute16 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE16() {
        return attribute16;
    }

    /**
     * Sets the value of the attribute16 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE16(String value) {
        this.attribute16 = value;
    }

    /**
     * Gets the value of the attribute17 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE17() {
        return attribute17;
    }

    /**
     * Sets the value of the attribute17 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE17(String value) {
        this.attribute17 = value;
    }

    /**
     * Gets the value of the attribute18 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE18() {
        return attribute18;
    }

    /**
     * Sets the value of the attribute18 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE18(String value) {
        this.attribute18 = value;
    }

    /**
     * Gets the value of the attribute19 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE19() {
        return attribute19;
    }

    /**
     * Sets the value of the attribute19 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE19(String value) {
        this.attribute19 = value;
    }

    /**
     * Gets the value of the attribute20 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE20() {
        return attribute20;
    }

    /**
     * Sets the value of the attribute20 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE20(String value) {
        this.attribute20 = value;
    }

    /**
     * Gets the value of the attribute21 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE21() {
        return attribute21;
    }

    /**
     * Sets the value of the attribute21 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE21(String value) {
        this.attribute21 = value;
    }

    /**
     * Gets the value of the attribute22 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE22() {
        return attribute22;
    }

    /**
     * Sets the value of the attribute22 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE22(String value) {
        this.attribute22 = value;
    }

    /**
     * Gets the value of the attribute23 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE23() {
        return attribute23;
    }

    /**
     * Sets the value of the attribute23 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE23(String value) {
        this.attribute23 = value;
    }

    /**
     * Gets the value of the attribute24 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE24() {
        return attribute24;
    }

    /**
     * Sets the value of the attribute24 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE24(String value) {
        this.attribute24 = value;
    }

    /**
     * Gets the value of the attribute25 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE25() {
        return attribute25;
    }

    /**
     * Sets the value of the attribute25 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE25(String value) {
        this.attribute25 = value;
    }

    /**
     * Gets the value of the attribute26 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE26() {
        return attribute26;
    }

    /**
     * Sets the value of the attribute26 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE26(String value) {
        this.attribute26 = value;
    }

    /**
     * Gets the value of the attribute27 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE27() {
        return attribute27;
    }

    /**
     * Sets the value of the attribute27 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE27(String value) {
        this.attribute27 = value;
    }

    /**
     * Gets the value of the attribute28 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE28() {
        return attribute28;
    }

    /**
     * Sets the value of the attribute28 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE28(String value) {
        this.attribute28 = value;
    }

    /**
     * Gets the value of the attribute29 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE29() {
        return attribute29;
    }

    /**
     * Sets the value of the attribute29 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE29(String value) {
        this.attribute29 = value;
    }

    /**
     * Gets the value of the attribute30 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE30() {
        return attribute30;
    }

    /**
     * Sets the value of the attribute30 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE30(String value) {
        this.attribute30 = value;
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

    /**
     * Gets the value of the perinformation4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERINFORMATION4() {
        return perinformation4;
    }

    /**
     * Sets the value of the perinformation4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERINFORMATION4(String value) {
        this.perinformation4 = value;
    }

    /**
     * Gets the value of the perinformation5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERINFORMATION5() {
        return perinformation5;
    }

    /**
     * Sets the value of the perinformation5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERINFORMATION5(String value) {
        this.perinformation5 = value;
    }

    /**
     * Gets the value of the perinformation6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERINFORMATION6() {
        return perinformation6;
    }

    /**
     * Sets the value of the perinformation6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERINFORMATION6(String value) {
        this.perinformation6 = value;
    }

    /**
     * Gets the value of the perinformation7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERINFORMATION7() {
        return perinformation7;
    }

    /**
     * Sets the value of the perinformation7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERINFORMATION7(String value) {
        this.perinformation7 = value;
    }

    /**
     * Gets the value of the perinformation8 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERINFORMATION8() {
        return perinformation8;
    }

    /**
     * Sets the value of the perinformation8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERINFORMATION8(String value) {
        this.perinformation8 = value;
    }

    /**
     * Gets the value of the perinformation10 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERINFORMATION10() {
        return perinformation10;
    }

    /**
     * Sets the value of the perinformation10 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERINFORMATION10(String value) {
        this.perinformation10 = value;
    }

    /**
     * Gets the value of the perinformation11 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERINFORMATION11() {
        return perinformation11;
    }

    /**
     * Sets the value of the perinformation11 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERINFORMATION11(String value) {
        this.perinformation11 = value;
    }

    /**
     * Gets the value of the perinformation12 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERINFORMATION12() {
        return perinformation12;
    }

    /**
     * Sets the value of the perinformation12 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERINFORMATION12(String value) {
        this.perinformation12 = value;
    }

    /**
     * Gets the value of the perinformation14 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERINFORMATION14() {
        return perinformation14;
    }

    /**
     * Sets the value of the perinformation14 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERINFORMATION14(String value) {
        this.perinformation14 = value;
    }

    /**
     * Gets the value of the perinformation15 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERINFORMATION15() {
        return perinformation15;
    }

    /**
     * Sets the value of the perinformation15 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERINFORMATION15(String value) {
        this.perinformation15 = value;
    }

    /**
     * Gets the value of the perinformation17 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERINFORMATION17() {
        return perinformation17;
    }

    /**
     * Sets the value of the perinformation17 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERINFORMATION17(String value) {
        this.perinformation17 = value;
    }

    /**
     * Gets the value of the perinformation18 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERINFORMATION18() {
        return perinformation18;
    }

    /**
     * Sets the value of the perinformation18 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERINFORMATION18(String value) {
        this.perinformation18 = value;
    }

}
