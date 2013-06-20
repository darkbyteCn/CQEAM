
package com.sino.soa.mis.eip.sc.pa.sb_sc_pa_pageinquirytaskinfosrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.sino.soa.mis.eip.sc.pa.sb_sc_pa_pageinquirytaskinfosrv.msgheader.MsgHeader;


/**
 * <p>Java class for PageInquiryTaskInfoSrvRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageInquiryTaskInfoSrvRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgHeader" type="{http://eip.zte.com/common/MsgHeader}MsgHeader"/>
 *         &lt;element name="PROJECT_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TASK_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BILLABLE_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="COST_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "PageInquiryTaskInfoSrvRequest", propOrder = {
    "msgHeader",
    "projectnum",
    "tasknum",
    "billableflag",
    "costflag",
    "startlastupdatedate",
    "endlastupdatedate"
})
public class PageInquiryTaskInfoSrvRequest {

    @XmlElement(name = "MsgHeader", required = true, nillable = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "PROJECT_NUM", required = true, nillable = true)
    protected String projectnum;
    @XmlElement(name = "TASK_NUM", required = true, nillable = true)
    protected String tasknum;
    @XmlElement(name = "BILLABLE_FLAG", required = true, nillable = true)
    protected String billableflag;
    @XmlElement(name = "COST_FLAG", required = true, nillable = true)
    protected String costflag;
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
     * Gets the value of the projectnum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROJECTNUM() {
        return projectnum;
    }

    /**
     * Sets the value of the projectnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROJECTNUM(String value) {
        this.projectnum = value;
    }

    /**
     * Gets the value of the tasknum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTASKNUM() {
        return tasknum;
    }

    /**
     * Sets the value of the tasknum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTASKNUM(String value) {
        this.tasknum = value;
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
