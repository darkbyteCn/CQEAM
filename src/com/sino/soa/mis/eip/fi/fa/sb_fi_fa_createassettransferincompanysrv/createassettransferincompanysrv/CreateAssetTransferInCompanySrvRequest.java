
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.createassettransferincompanysrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.msgheader.MsgHeader;


/**
 * <p>Java class for CreateAssetTransferInCompanySrvRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateAssetTransferInCompanySrvRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgHeader" type="{http://eip.zte.com/common/MsgHeader}MsgHeader"/>
 *         &lt;element name="CreateAssetTransferInCompanySrvInputCollection" type="{http://eip.zte.com/common/fa/CreateAssetTransferInCompanySrv}CreateAssetTransferInCompanySrvInputCollection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateAssetTransferInCompanySrvRequest", propOrder = {
    "msgHeader",
    "createAssetTransferInCompanySrvInputCollection"
})
public class CreateAssetTransferInCompanySrvRequest {

    @XmlElement(name = "MsgHeader", required = true, nillable = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "CreateAssetTransferInCompanySrvInputCollection", required = true, nillable = true)
    protected CreateAssetTransferInCompanySrvInputCollection createAssetTransferInCompanySrvInputCollection;

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
     * Gets the value of the createAssetTransferInCompanySrvInputCollection property.
     * 
     * @return
     *     possible object is
     *     {@link CreateAssetTransferInCompanySrvInputCollection }
     *     
     */
    public CreateAssetTransferInCompanySrvInputCollection getCreateAssetTransferInCompanySrvInputCollection() {
        return createAssetTransferInCompanySrvInputCollection;
    }

    /**
     * Sets the value of the createAssetTransferInCompanySrvInputCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateAssetTransferInCompanySrvInputCollection }
     *     
     */
    public void setCreateAssetTransferInCompanySrvInputCollection(CreateAssetTransferInCompanySrvInputCollection value) {
        this.createAssetTransferInCompanySrvInputCollection = value;
    }

}
