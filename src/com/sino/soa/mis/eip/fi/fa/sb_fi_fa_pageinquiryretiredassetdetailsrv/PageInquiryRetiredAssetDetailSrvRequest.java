
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_pageinquiryretiredassetdetailsrv;

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
 *         &lt;element name="MsgHeader" type="{http://eip.zte.com/fi/SB_FI_FA_PageInquiryRetiredAssetDetailSrv}MsgHeader"/>
 *         &lt;element name="BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LOCATION_DEP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TAG_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RETIREMENT_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATE_RETIRED_FROM" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DATE_RETIRED_TO" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DATE_EFFECTIVE_FROM" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DATE_EFFECTIVE_TO" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
    "locationdep",
    "assetnumber",
    "tagnumber",
    "retirementtypecode",
    "dateretiredfrom",
    "dateretiredto",
    "dateeffectivefrom",
    "dateeffectiveto"
})
@XmlRootElement(name = "PageInquiryRetiredAssetDetailSrvRequest")
public class PageInquiryRetiredAssetDetailSrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "BOOK_TYPE_CODE", required = true, nillable = true)
    protected String booktypecode;
    @XmlElement(name = "LOCATION_DEP", required = true, nillable = true)
    protected String locationdep;
    @XmlElement(name = "ASSET_NUMBER", required = true, nillable = true)
    protected String assetnumber;
    @XmlElement(name = "TAG_NUMBER", required = true, nillable = true)
    protected String tagnumber;
    @XmlElement(name = "RETIREMENT_TYPE_CODE", required = true, nillable = true)
    protected String retirementtypecode;
    @XmlElement(name = "DATE_RETIRED_FROM", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateretiredfrom;
    @XmlElement(name = "DATE_RETIRED_TO", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateretiredto;
    @XmlElement(name = "DATE_EFFECTIVE_FROM", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateeffectivefrom;
    @XmlElement(name = "DATE_EFFECTIVE_TO", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateeffectiveto;

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
     * Gets the value of the locationdep property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOCATIONDEP() {
        return locationdep;
    }

    /**
     * Sets the value of the locationdep property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOCATIONDEP(String value) {
        this.locationdep = value;
    }

    /**
     * Gets the value of the assetnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSETNUMBER() {
        return assetnumber;
    }

    /**
     * Sets the value of the assetnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSETNUMBER(String value) {
        this.assetnumber = value;
    }

    /**
     * Gets the value of the tagnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTAGNUMBER() {
        return tagnumber;
    }

    /**
     * Sets the value of the tagnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTAGNUMBER(String value) {
        this.tagnumber = value;
    }

    /**
     * Gets the value of the retirementtypecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRETIREMENTTYPECODE() {
        return retirementtypecode;
    }

    /**
     * Sets the value of the retirementtypecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRETIREMENTTYPECODE(String value) {
        this.retirementtypecode = value;
    }

    /**
     * Gets the value of the dateretiredfrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATERETIREDFROM() {
        return dateretiredfrom;
    }

    /**
     * Sets the value of the dateretiredfrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATERETIREDFROM(XMLGregorianCalendar value) {
        this.dateretiredfrom = value;
    }

    /**
     * Gets the value of the dateretiredto property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATERETIREDTO() {
        return dateretiredto;
    }

    /**
     * Sets the value of the dateretiredto property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATERETIREDTO(XMLGregorianCalendar value) {
        this.dateretiredto = value;
    }

    /**
     * Gets the value of the dateeffectivefrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATEEFFECTIVEFROM() {
        return dateeffectivefrom;
    }

    /**
     * Sets the value of the dateeffectivefrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATEEFFECTIVEFROM(XMLGregorianCalendar value) {
        this.dateeffectivefrom = value;
    }

    /**
     * Gets the value of the dateeffectiveto property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATEEFFECTIVETO() {
        return dateeffectiveto;
    }

    /**
     * Sets the value of the dateeffectiveto property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATEEFFECTIVETO(XMLGregorianCalendar value) {
        this.dateeffectiveto = value;
    }

}
