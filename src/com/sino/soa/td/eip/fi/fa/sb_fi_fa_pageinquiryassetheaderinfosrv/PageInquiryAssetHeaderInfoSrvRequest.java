
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetheaderinfosrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_pageinquiryassetheaderinfosrv.msgheader.MsgHeader;


/**
 * <p>Java class for PageInquiryAssetHeaderInfoSrvRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PageInquiryAssetHeaderInfoSrvRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgHeader" type="{http://eip.zte.com/common/MsgHeader}MsgHeader"/>
 *         &lt;element name="BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TAG_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_SOURCE_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CREATION_DATE_FROM" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CREATION_DATE_TO" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
@XmlType(name = "PageInquiryAssetHeaderInfoSrvRequest", propOrder = {
    "msgHeader",
    "booktypecode",
    "assetnumber",
    "tagnumber",
    "assetsourceid",
    "creationdatefrom",
    "creationdateto",
    "startlastupdatedate",
    "endlastupdatedate"
})
public class PageInquiryAssetHeaderInfoSrvRequest {

    @XmlElement(name = "MsgHeader", required = true, nillable = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "BOOK_TYPE_CODE", required = true, nillable = true)
    protected String booktypecode;
    @XmlElement(name = "ASSET_NUMBER", required = true, nillable = true)
    protected String assetnumber;
    @XmlElement(name = "TAG_NUMBER", required = true, nillable = true)
    protected String tagnumber;
    @XmlElement(name = "ASSET_SOURCE_ID", required = true, nillable = true)
    protected String assetsourceid;
    @XmlElement(name = "CREATION_DATE_FROM", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationdatefrom;
    @XmlElement(name = "CREATION_DATE_TO", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationdateto;
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
     * Gets the value of the assetsourceid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSETSOURCEID() {
        return assetsourceid;
    }

    /**
     * Sets the value of the assetsourceid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSETSOURCEID(String value) {
        this.assetsourceid = value;
    }

    /**
     * Gets the value of the creationdatefrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCREATIONDATEFROM() {
        return creationdatefrom;
    }

    /**
     * Sets the value of the creationdatefrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCREATIONDATEFROM(XMLGregorianCalendar value) {
        this.creationdatefrom = value;
    }

    /**
     * Gets the value of the creationdateto property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCREATIONDATETO() {
        return creationdateto;
    }

    /**
     * Sets the value of the creationdateto property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCREATIONDATETO(XMLGregorianCalendar value) {
        this.creationdateto = value;
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
