
package com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryorgstructuresrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InquiryOrgStructureSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryOrgStructureSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BUSINESS_GROUP_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="BUSINESS_GROUP_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORGANIZATION_STRUCTURE_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORGANIZATION_STRUCTURE_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ORG_STRUCTURE_VERSION_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="VERSION_NUMBER" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ORGANIZATION_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ORGANIZATION_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORGANIZATION_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORGANIZATION_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORGANIZATION_CLASS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="STATUS" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PARENT_ORGANIZATION_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PARENT_ORG_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PARENT_ORG_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="STRUCTURE_STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ORG_SHORT_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORG_DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORG_SUPERVISOR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MANAGER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MANAGER_EMPLOYEE_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VICE_MANAGER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ADMINISTRATOR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LIST_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryOrgStructureSrvOutputItem", propOrder = {
    "businessgroupid",
    "businessgroupname",
    "organizationstructurename",
    "organizationstructureid",
    "orgstructureversionid",
    "versionnumber",
    "organizationid",
    "organizationcode",
    "organizationname",
    "organizationtype",
    "organizationclass",
    "status",
    "parentorganizationid",
    "parentorgcode",
    "parentorgname",
    "structurestatus",
    "lastupdatedate",
    "orgshortname",
    "orgdescription",
    "orgsupervisor",
    "manager",
    "manageremployeenumber",
    "vicemanager",
    "administrator",
    "listno"
})
public class InquiryOrgStructureSrvOutputItem {

    @XmlElement(name = "BUSINESS_GROUP_ID", required = true, nillable = true)
    protected BigDecimal businessgroupid;
    @XmlElement(name = "BUSINESS_GROUP_NAME", required = true, nillable = true)
    protected String businessgroupname;
    @XmlElement(name = "ORGANIZATION_STRUCTURE_NAME", required = true, nillable = true)
    protected String organizationstructurename;
    @XmlElement(name = "ORGANIZATION_STRUCTURE_ID", required = true, nillable = true)
    protected BigDecimal organizationstructureid;
    @XmlElement(name = "ORG_STRUCTURE_VERSION_ID", required = true, nillable = true)
    protected BigDecimal orgstructureversionid;
    @XmlElement(name = "VERSION_NUMBER", required = true, nillable = true)
    protected BigDecimal versionnumber;
    @XmlElement(name = "ORGANIZATION_ID", required = true, nillable = true)
    protected BigDecimal organizationid;
    @XmlElement(name = "ORGANIZATION_CODE", required = true, nillable = true)
    protected String organizationcode;
    @XmlElement(name = "ORGANIZATION_NAME", required = true, nillable = true)
    protected String organizationname;
    @XmlElement(name = "ORGANIZATION_TYPE", required = true, nillable = true)
    protected String organizationtype;
    @XmlElement(name = "ORGANIZATION_CLASS", required = true, nillable = true)
    protected String organizationclass;
    @XmlElement(name = "STATUS", required = true, nillable = true)
    protected BigDecimal status;
    @XmlElement(name = "PARENT_ORGANIZATION_ID", required = true, nillable = true)
    protected BigDecimal parentorganizationid;
    @XmlElement(name = "PARENT_ORG_CODE", required = true, nillable = true)
    protected String parentorgcode;
    @XmlElement(name = "PARENT_ORG_NAME", required = true, nillable = true)
    protected String parentorgname;
    @XmlElement(name = "STRUCTURE_STATUS", required = true, nillable = true)
    protected String structurestatus;
    @XmlElement(name = "LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastupdatedate;
    @XmlElement(name = "ORG_SHORT_NAME", required = true, nillable = true)
    protected String orgshortname;
    @XmlElement(name = "ORG_DESCRIPTION", required = true, nillable = true)
    protected String orgdescription;
    @XmlElement(name = "ORG_SUPERVISOR", required = true, nillable = true)
    protected String orgsupervisor;
    @XmlElement(name = "MANAGER", required = true, nillable = true)
    protected String manager;
    @XmlElement(name = "MANAGER_EMPLOYEE_NUMBER", required = true, nillable = true)
    protected String manageremployeenumber;
    @XmlElement(name = "VICE_MANAGER", required = true, nillable = true)
    protected String vicemanager;
    @XmlElement(name = "ADMINISTRATOR", required = true, nillable = true)
    protected String administrator;
    @XmlElement(name = "LIST_NO", required = true, nillable = true)
    protected String listno;

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
     * Gets the value of the businessgroupname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBUSINESSGROUPNAME() {
        return businessgroupname;
    }

    /**
     * Sets the value of the businessgroupname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUSINESSGROUPNAME(String value) {
        this.businessgroupname = value;
    }

    /**
     * Gets the value of the organizationstructurename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGANIZATIONSTRUCTURENAME() {
        return organizationstructurename;
    }

    /**
     * Sets the value of the organizationstructurename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGANIZATIONSTRUCTURENAME(String value) {
        this.organizationstructurename = value;
    }

    /**
     * Gets the value of the organizationstructureid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getORGANIZATIONSTRUCTUREID() {
        return organizationstructureid;
    }

    /**
     * Sets the value of the organizationstructureid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setORGANIZATIONSTRUCTUREID(BigDecimal value) {
        this.organizationstructureid = value;
    }

    /**
     * Gets the value of the orgstructureversionid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getORGSTRUCTUREVERSIONID() {
        return orgstructureversionid;
    }

    /**
     * Sets the value of the orgstructureversionid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setORGSTRUCTUREVERSIONID(BigDecimal value) {
        this.orgstructureversionid = value;
    }

    /**
     * Gets the value of the versionnumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVERSIONNUMBER() {
        return versionnumber;
    }

    /**
     * Sets the value of the versionnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVERSIONNUMBER(BigDecimal value) {
        this.versionnumber = value;
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
     * Gets the value of the organizationcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGANIZATIONCODE() {
        return organizationcode;
    }

    /**
     * Sets the value of the organizationcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGANIZATIONCODE(String value) {
        this.organizationcode = value;
    }

    /**
     * Gets the value of the organizationname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGANIZATIONNAME() {
        return organizationname;
    }

    /**
     * Sets the value of the organizationname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGANIZATIONNAME(String value) {
        this.organizationname = value;
    }

    /**
     * Gets the value of the organizationtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGANIZATIONTYPE() {
        return organizationtype;
    }

    /**
     * Sets the value of the organizationtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGANIZATIONTYPE(String value) {
        this.organizationtype = value;
    }

    /**
     * Gets the value of the organizationclass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGANIZATIONCLASS() {
        return organizationclass;
    }

    /**
     * Sets the value of the organizationclass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGANIZATIONCLASS(String value) {
        this.organizationclass = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSTATUS() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSTATUS(BigDecimal value) {
        this.status = value;
    }

    /**
     * Gets the value of the parentorganizationid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPARENTORGANIZATIONID() {
        return parentorganizationid;
    }

    /**
     * Sets the value of the parentorganizationid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPARENTORGANIZATIONID(BigDecimal value) {
        this.parentorganizationid = value;
    }

    /**
     * Gets the value of the parentorgcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARENTORGCODE() {
        return parentorgcode;
    }

    /**
     * Sets the value of the parentorgcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARENTORGCODE(String value) {
        this.parentorgcode = value;
    }

    /**
     * Gets the value of the parentorgname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARENTORGNAME() {
        return parentorgname;
    }

    /**
     * Sets the value of the parentorgname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARENTORGNAME(String value) {
        this.parentorgname = value;
    }

    /**
     * Gets the value of the structurestatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTRUCTURESTATUS() {
        return structurestatus;
    }

    /**
     * Sets the value of the structurestatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTRUCTURESTATUS(String value) {
        this.structurestatus = value;
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
     * Gets the value of the orgshortname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGSHORTNAME() {
        return orgshortname;
    }

    /**
     * Sets the value of the orgshortname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGSHORTNAME(String value) {
        this.orgshortname = value;
    }

    /**
     * Gets the value of the orgdescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGDESCRIPTION() {
        return orgdescription;
    }

    /**
     * Sets the value of the orgdescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGDESCRIPTION(String value) {
        this.orgdescription = value;
    }

    /**
     * Gets the value of the orgsupervisor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGSUPERVISOR() {
        return orgsupervisor;
    }

    /**
     * Sets the value of the orgsupervisor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGSUPERVISOR(String value) {
        this.orgsupervisor = value;
    }

    /**
     * Gets the value of the manager property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMANAGER() {
        return manager;
    }

    /**
     * Sets the value of the manager property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMANAGER(String value) {
        this.manager = value;
    }

    /**
     * Gets the value of the manageremployeenumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMANAGEREMPLOYEENUMBER() {
        return manageremployeenumber;
    }

    /**
     * Sets the value of the manageremployeenumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMANAGEREMPLOYEENUMBER(String value) {
        this.manageremployeenumber = value;
    }

    /**
     * Gets the value of the vicemanager property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVICEMANAGER() {
        return vicemanager;
    }

    /**
     * Sets the value of the vicemanager property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVICEMANAGER(String value) {
        this.vicemanager = value;
    }

    /**
     * Gets the value of the administrator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADMINISTRATOR() {
        return administrator;
    }

    /**
     * Sets the value of the administrator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADMINISTRATOR(String value) {
        this.administrator = value;
    }

    /**
     * Gets the value of the listno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLISTNO() {
        return listno;
    }

    /**
     * Sets the value of the listno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLISTNO(String value) {
        this.listno = value;
    }

}
