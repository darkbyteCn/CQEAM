
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BImportAssetLocCombSrvInputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BImportAssetLocCombSrvInputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PRI_KEY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEGMENT3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ENABLED_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="EMPLOYEE_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RESPONSIBILITY_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="RESPONSIBILITY_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BImportAssetLocCombSrvInputItem", propOrder = {
    "prikey",
    "segment1",
    "segment2",
    "segment3",
    "enabledflag",
    "createdby",
    "employeenumber",
    "responsibilityid",
    "responsibilityname"
})
public class BImportAssetLocCombSrvInputItem {

    @XmlElement(name = "PRI_KEY", required = true, nillable = true)
    protected String prikey;
    @XmlElement(name = "SEGMENT1", required = true, nillable = true)
    protected String segment1;
    @XmlElement(name = "SEGMENT2", required = true, nillable = true)
    protected String segment2;
    @XmlElement(name = "SEGMENT3", required = true, nillable = true)
    protected String segment3;
    @XmlElement(name = "ENABLED_FLAG", required = true, nillable = true)
    protected String enabledflag;
    @XmlElement(name = "CREATED_BY", required = true, nillable = true)
    protected BigDecimal createdby;
    @XmlElement(name = "EMPLOYEE_NUMBER", required = true, nillable = true)
    protected String employeenumber;
    @XmlElement(name = "RESPONSIBILITY_ID", required = true, nillable = true)
    protected BigDecimal responsibilityid;
    @XmlElement(name = "RESPONSIBILITY_NAME", required = true, nillable = true)
    protected String responsibilityname;

    /**
     * Gets the value of the prikey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRIKEY() {
        return prikey;
    }

    /**
     * Sets the value of the prikey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRIKEY(String value) {
        this.prikey = value;
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
     * Gets the value of the segment2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT2() {
        return segment2;
    }

    /**
     * Sets the value of the segment2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT2(String value) {
        this.segment2 = value;
    }

    /**
     * Gets the value of the segment3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT3() {
        return segment3;
    }

    /**
     * Sets the value of the segment3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT3(String value) {
        this.segment3 = value;
    }

    /**
     * Gets the value of the enabledflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getENABLEDFLAG() {
        return enabledflag;
    }

    /**
     * Sets the value of the enabledflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setENABLEDFLAG(String value) {
        this.enabledflag = value;
    }

    /**
     * Gets the value of the createdby property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCREATEDBY() {
        return createdby;
    }

    /**
     * Sets the value of the createdby property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCREATEDBY(BigDecimal value) {
        this.createdby = value;
    }

    /**
     * Gets the value of the employeenumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPLOYEENUMBER() {
        return employeenumber;
    }

    /**
     * Sets the value of the employeenumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPLOYEENUMBER(String value) {
        this.employeenumber = value;
    }

    /**
     * Gets the value of the responsibilityid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRESPONSIBILITYID() {
        return responsibilityid;
    }

    /**
     * Sets the value of the responsibilityid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRESPONSIBILITYID(BigDecimal value) {
        this.responsibilityid = value;
    }

    /**
     * Gets the value of the responsibilityname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRESPONSIBILITYNAME() {
        return responsibilityname;
    }

    /**
     * Sets the value of the responsibilityname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRESPONSIBILITYNAME(String value) {
        this.responsibilityname = value;
    }

}
