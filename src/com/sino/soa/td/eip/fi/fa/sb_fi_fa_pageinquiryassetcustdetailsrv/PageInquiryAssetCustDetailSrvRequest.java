
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetcustdetailsrv;

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
 *         &lt;element name="MsgHeader" type="{http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetCustDetailSrv}MsgHeader"/>
 *         &lt;element name="BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PROJRCT_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TASK_NUM_FROM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TASK_NUM_TO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CAPITALIZED_DATE_FROM" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CAPITALIZED_DATE_TO" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="BATCH" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "booktypecode",
    "projrctnumber",
    "tasknumfrom",
    "tasknumto",
    "capitalizeddatefrom",
    "capitalizeddateto",
    "batch"
})
@XmlRootElement(name = "PageInquiryAssetCustDetailSrvRequest")
public class PageInquiryAssetCustDetailSrvRequest {

    @XmlElement(name = "MsgHeader", required = true, nillable = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "BOOK_TYPE_CODE", required = true, nillable = true)
    protected String booktypecode;
    @XmlElement(name = "PROJRCT_NUMBER", required = true, nillable = true)
    protected String projrctnumber;
    @XmlElement(name = "TASK_NUM_FROM", required = true, nillable = true)
    protected String tasknumfrom;
    @XmlElement(name = "TASK_NUM_TO", required = true, nillable = true)
    protected String tasknumto;
    @XmlElement(name = "CAPITALIZED_DATE_FROM", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar capitalizeddatefrom;
    @XmlElement(name = "CAPITALIZED_DATE_TO", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar capitalizeddateto;
    @XmlElement(name = "BATCH", required = true, nillable = true)
    protected String batch;

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
     * Gets the value of the booktypecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBOOKTYPECODE() {
        return booktypecode;
    }

    /**
     * Sets the value of the booktypecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBOOKTYPECODE(String value) {
        this.booktypecode = value;
    }

    /**
     * Gets the value of the projrctnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROJRCTNUMBER() {
        return projrctnumber;
    }

    /**
     * Sets the value of the projrctnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROJRCTNUMBER(String value) {
        this.projrctnumber = value;
    }

    /**
     * Gets the value of the tasknumfrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTASKNUMFROM() {
        return tasknumfrom;
    }

    /**
     * Sets the value of the tasknumfrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTASKNUMFROM(String value) {
        this.tasknumfrom = value;
    }

    /**
     * Gets the value of the tasknumto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTASKNUMTO() {
        return tasknumto;
    }

    /**
     * Sets the value of the tasknumto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTASKNUMTO(String value) {
        this.tasknumto = value;
    }

    /**
     * Gets the value of the capitalizeddatefrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCAPITALIZEDDATEFROM() {
        return capitalizeddatefrom;
    }

    /**
     * Sets the value of the capitalizeddatefrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCAPITALIZEDDATEFROM(XMLGregorianCalendar value) {
        this.capitalizeddatefrom = value;
    }

    /**
     * Gets the value of the capitalizeddateto property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCAPITALIZEDDATETO() {
        return capitalizeddateto;
    }

    /**
     * Sets the value of the capitalizeddateto property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCAPITALIZEDDATETO(XMLGregorianCalendar value) {
        this.capitalizeddateto = value;
    }

    /**
     * Gets the value of the batch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBATCH() {
        return batch;
    }

    /**
     * Sets the value of the batch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBATCH(String value) {
        this.batch = value;
    }

}
