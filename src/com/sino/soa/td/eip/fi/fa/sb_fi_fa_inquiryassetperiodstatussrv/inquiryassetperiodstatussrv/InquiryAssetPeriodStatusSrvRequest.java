
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.inquiryassetperiodstatussrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.sino.soa.td.eip.fi.fa.sb_fi_fa_inquiryassetperiodstatussrv.msgheader.MsgHeader;


/**
 * <p>Java class for InquiryAssetPeriodStatusSrvRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryAssetPeriodStatusSrvRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgHeader" type="{http://eip.zte.com/common/MsgHeader}MsgHeader"/>
 *         &lt;element name="BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PERIOD_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryAssetPeriodStatusSrvRequest", propOrder = {
    "msgHeader",
    "booktypecode",
    "periodname"
})
public class InquiryAssetPeriodStatusSrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "BOOK_TYPE_CODE", required = true, nillable = true)
    protected String booktypecode;
    @XmlElement(name = "PERIOD_NAME", required = true, nillable = true)
    protected String periodname;

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
     * Gets the value of the periodname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERIODNAME() {
        return periodname;
    }

    /**
     * Sets the value of the periodname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERIODNAME(String value) {
        this.periodname = value;
    }

}
