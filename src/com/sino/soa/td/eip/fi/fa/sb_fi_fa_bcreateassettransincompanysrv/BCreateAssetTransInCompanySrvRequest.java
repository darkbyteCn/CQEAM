
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_bcreateassettransincompanysrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="MsgHeader" type="{http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv}MsgHeader"/>
 *         &lt;element name="BCreateAssetTransInCompanySrvInputCollection" type="{http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv}BCreateAssetTransInCompanySrvInputCollection"/>
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
    "bCreateAssetTransInCompanySrvInputCollection"
})
@XmlRootElement(name = "BCreateAssetTransInCompanySrvRequest")
public class BCreateAssetTransInCompanySrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "BCreateAssetTransInCompanySrvInputCollection", required = true)
    protected BCreateAssetTransInCompanySrvInputCollection bCreateAssetTransInCompanySrvInputCollection;

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
     * Gets the value of the bCreateAssetTransInCompanySrvInputCollection property.
     * 
     * @return
     *     possible object is
     *     {@link BCreateAssetTransInCompanySrvInputCollection }
     *     
     */
    public BCreateAssetTransInCompanySrvInputCollection getBCreateAssetTransInCompanySrvInputCollection() {
        return bCreateAssetTransInCompanySrvInputCollection;
    }

    /**
     * Sets the value of the bCreateAssetTransInCompanySrvInputCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link BCreateAssetTransInCompanySrvInputCollection }
     *     
     */
    public void setBCreateAssetTransInCompanySrvInputCollection(BCreateAssetTransInCompanySrvInputCollection value) {
        this.bCreateAssetTransInCompanySrvInputCollection = value;
    }

}
