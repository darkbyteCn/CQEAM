
package com.sino.soa.td.eip.sy.sy.sb_sy_sy_pageinquiryvsetvalueinfosrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="MsgHeader" type="{http://eip.zte.com/common/sy/SB_SY_SY_PageInquiryVSetValueInfoSrv}MsgHeader"/>
 *         &lt;element name="FLEX_VALUE_SET_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FLEX_VALUE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PARENT_FLEX_VALUE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="START_DATE_ACTIVE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
    "flexvaluesetname",
    "flexvalue",
    "parentflexvalue",
    "startdateactive",
    "startlastupdatedate",
    "endlastupdatedate"
})
@XmlRootElement(name = "PageInquiryVSetValueInfoSrvRequest")
public class PageInquiryVSetValueInfoSrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "FLEX_VALUE_SET_NAME", required = true, nillable = true)
    protected String flexvaluesetname;
    @XmlElement(name = "FLEX_VALUE", required = true, nillable = true)
    protected String flexvalue;
    @XmlElement(name = "PARENT_FLEX_VALUE", required = true, nillable = true)
    protected String parentflexvalue;
    @XmlElement(name = "START_DATE_ACTIVE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startdateactive;
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
     * Gets the value of the flexvaluesetname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLEXVALUESETNAME() {
        return flexvaluesetname;
    }

    /**
     * Sets the value of the flexvaluesetname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLEXVALUESETNAME(String value) {
        this.flexvaluesetname = value;
    }

    /**
     * Gets the value of the flexvalue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLEXVALUE() {
        return flexvalue;
    }

    /**
     * Sets the value of the flexvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLEXVALUE(String value) {
        this.flexvalue = value;
    }

    /**
     * Gets the value of the parentflexvalue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARENTFLEXVALUE() {
        return parentflexvalue;
    }

    /**
     * Sets the value of the parentflexvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARENTFLEXVALUE(String value) {
        this.parentflexvalue = value;
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
