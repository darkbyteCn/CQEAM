
package com.sino.soa.mis.eip.sc.po.sb_sc_pa_pageinquiryprojectinfosrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.sino.soa.mis.eip.sc.po.sb_sc_pa_pageinquiryprojectinfosrv.msgheader.MsgHeader;


/**
 * <p>Java class for PageInquiryProjectInfoSrvRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageInquiryProjectInfoSrvRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgHeader" type="{http://eip.zte.com/common/MsgHeader}MsgHeader"/>
 *         &lt;element name="ORG_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORG_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TEMPLATE_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PROJECT_CLASS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PROJECT_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PROJECT_STATUS_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="START_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="COMPLETION_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
@XmlType(name = "PageInquiryProjectInfoSrvRequest", propOrder = {
    "msgHeader",
    "orgcode",
    "orgname",
    "segment1",
    "templateflag",
    "projectclass",
    "projecttype",
    "projectstatuscode",
    "startdate",
    "completiondate",
    "creationdate"
})
public class PageInquiryProjectInfoSrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "ORG_CODE", required = true, nillable = true)
    protected String orgcode;
    @XmlElement(name = "ORG_NAME", required = true, nillable = true)
    protected String orgname;
    @XmlElement(name = "SEGMENT1", required = true, nillable = true)
    protected String segment1;
    @XmlElement(name = "TEMPLATE_FLAG", required = true, nillable = true)
    protected String templateflag;
    @XmlElement(name = "PROJECT_CLASS", required = true, nillable = true)
    protected String projectclass;
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
    @XmlElement(name = "CREATION_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationdate;

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
     * Gets the value of the orgcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGCODE() {
        return orgcode;
    }

    /**
     * Sets the value of the orgcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGCODE(String value) {
        this.orgcode = value;
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
