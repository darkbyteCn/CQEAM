
package com.sino.soa.mis.eip.fi.gl.sb_fi_gl_inquiryouorganizationsrv.inquiryouorganizationsrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InquiryOuOrganizationSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryOuOrganizationSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ORG_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ORG_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SET_OF_BOOKS_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SET_OF_BOOKS_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ATTRIBUTE1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ENABLE_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryOuOrganizationSrvOutputItem", propOrder = {
    "orgid",
    "orgname",
    "setofbooksname",
    "setofbooksid",
    "attribute1",
    "enableflag"
})
public class InquiryOuOrganizationSrvOutputItem {

    @XmlElement(name = "ORG_ID", required = true)
    protected BigDecimal orgid;
    @XmlElement(name = "ORG_NAME", required = true)
    protected String orgname;
    @XmlElement(name = "SET_OF_BOOKS_NAME", required = true)
    protected String setofbooksname;
    @XmlElement(name = "SET_OF_BOOKS_ID", required = true)
    protected BigDecimal setofbooksid;
    @XmlElement(name = "ATTRIBUTE1", required = true)
    protected String attribute1;
    @XmlElement(name = "ENABLE_FLAG", required = true)
    protected String enableflag;

    /**
     * Gets the value of the orgid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getORGID() {
        return orgid;
    }

    /**
     * Sets the value of the orgid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setORGID(BigDecimal value) {
        this.orgid = value;
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
     * Gets the value of the setofbooksname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSETOFBOOKSNAME() {
        return setofbooksname;
    }

    /**
     * Sets the value of the setofbooksname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSETOFBOOKSNAME(String value) {
        this.setofbooksname = value;
    }

    /**
     * Gets the value of the setofbooksid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSETOFBOOKSID() {
        return setofbooksid;
    }

    /**
     * Sets the value of the setofbooksid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSETOFBOOKSID(BigDecimal value) {
        this.setofbooksid = value;
    }

    /**
     * Gets the value of the attribute1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE1() {
        return attribute1;
    }

    /**
     * Sets the value of the attribute1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE1(String value) {
        this.attribute1 = value;
    }

    /**
     * Gets the value of the enableflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getENABLEFLAG() {
        return enableflag;
    }

    /**
     * Sets the value of the enableflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setENABLEFLAG(String value) {
        this.enableflag = value;
    }

}
