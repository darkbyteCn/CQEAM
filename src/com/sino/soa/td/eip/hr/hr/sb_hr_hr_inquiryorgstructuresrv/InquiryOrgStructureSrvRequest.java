
package com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryorgstructuresrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.sino.soa.td.eip.hr.hr.sb_hr_hr_inquiryorgstructuresrv.msgheader.MsgHeader;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgHeader" type="{http://eip.zte.com/common/MsgHeader}MsgHeader"/>
 *         &lt;element name="BUSINESS_GROUP_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="BUSINESS_GROUP_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORGANIZATION_STRUCTURE_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORGANIZATION_STRUCTURE_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ORG_STRUCTURE_VERSION_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="VERSION_NUMBER" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ORGANIZATION_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ORGANIZATION_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="START_LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="END_LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "msgHeader",
    "businessgroupid",
    "businessgroupname",
    "organizationstructurename",
    "organizationstructureid",
    "orgstructureversionid",
    "versionnumber",
    "organizationid",
    "organizationname",
    "startlastupdatedate",
    "endlastupdatedate"
})
@XmlRootElement(name = "InquiryOrgStructureSrvRequest")
public class InquiryOrgStructureSrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
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
    @XmlElement(name = "ORGANIZATION_NAME", required = true, nillable = true)
    protected String organizationname;
    @XmlElement(name = "START_LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startlastupdatedate;
    @XmlElement(name = "END_LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endlastupdatedate;

    /**
     * Gets the value of the msgHeader property.
     * 
     * @return
     *     possible object is
     *     {@link MsgHeader }
     *     
     */
    public MsgHeader getMsgHeader() {
        return msgHeader;
    }

    /**
     * Sets the value of the msgHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link MsgHeader }
     *     
     */
    public void setMsgHeader(MsgHeader value) {
        this.msgHeader = value;
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
     * Gets the value of the startlastupdatedate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSTARTLASTUPDATEDATE() {
        return startlastupdatedate;
    }

    /**
     * Sets the value of the startlastupdatedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSTARTLASTUPDATEDATE(XMLGregorianCalendar value) {
        this.startlastupdatedate = value;
    }

    /**
     * Gets the value of the endlastupdatedate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getENDLASTUPDATEDATE() {
        return endlastupdatedate;
    }

    /**
     * Sets the value of the endlastupdatedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setENDLASTUPDATEDATE(XMLGregorianCalendar value) {
        this.endlastupdatedate = value;
    }

}
