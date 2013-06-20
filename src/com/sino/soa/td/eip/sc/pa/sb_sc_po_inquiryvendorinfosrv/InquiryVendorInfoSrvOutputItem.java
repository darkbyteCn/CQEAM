
package com.sino.soa.td.eip.sc.pa.sb_sc_po_inquiryvendorinfosrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InquiryVendorInfoSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryVendorInfoSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VENDOR_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="VENDOR_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VENDOR_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VENDOR_TYPE_LOOKUP_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VENDOR_TYPE_DISP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VENDOR_CREATION_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="VAT_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VAT_REGISTRATION_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VENDOR_END_DATE_ACTIVE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ATTRIBUTE1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CON_VENDOR_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="EMPLOYEE_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PERSON_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="EMPLOYEE_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="VENDOR_NAME_ALT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="COMPANY_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="INACTIVE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ALLOW_SUBSTITUTE_RECEIPTS_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VENDOR_SITE_INFO" type="{http://xmlns.oracle.com/SB_SC_PO_InquiryVendorInfoSrv}VendorSiteInfoCollection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryVendorInfoSrvOutputItem", propOrder = {
    "vendorid",
    "vendorname",
    "vendornumber",
    "vendortypelookupcode",
    "vendortypedisp",
    "vendorcreationdate",
    "createdby",
    "vatflag",
    "vatregistrationnum",
    "vendorenddateactive",
    "attribute1",
    "convendorid",
    "employeename",
    "personid",
    "employeenumber",
    "lastupdatedate",
    "vendornamealt",
    "companycode",
    "inactivedate",
    "allowsubstitutereceiptsflag",
    "vendorsiteinfo"
})
public class InquiryVendorInfoSrvOutputItem {

    @XmlElement(name = "VENDOR_ID", required = true, nillable = true)
    protected BigDecimal vendorid;
    @XmlElement(name = "VENDOR_NAME", required = true, nillable = true)
    protected String vendorname;
    @XmlElement(name = "VENDOR_NUMBER", required = true, nillable = true)
    protected String vendornumber;
    @XmlElement(name = "VENDOR_TYPE_LOOKUP_CODE", required = true, nillable = true)
    protected String vendortypelookupcode;
    @XmlElement(name = "VENDOR_TYPE_DISP", required = true, nillable = true)
    protected String vendortypedisp;
    @XmlElement(name = "VENDOR_CREATION_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar vendorcreationdate;
    @XmlElement(name = "CREATED_BY", required = true, nillable = true)
    protected BigDecimal createdby;
    @XmlElement(name = "VAT_FLAG", required = true, nillable = true)
    protected String vatflag;
    @XmlElement(name = "VAT_REGISTRATION_NUM", required = true, nillable = true)
    protected String vatregistrationnum;
    @XmlElement(name = "VENDOR_END_DATE_ACTIVE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar vendorenddateactive;
    @XmlElement(name = "ATTRIBUTE1", required = true, nillable = true)
    protected String attribute1;
    @XmlElement(name = "CON_VENDOR_ID", required = true, nillable = true)
    protected BigDecimal convendorid;
    @XmlElement(name = "EMPLOYEE_NAME", required = true, nillable = true)
    protected String employeename;
    @XmlElement(name = "PERSON_ID", required = true, nillable = true)
    protected BigDecimal personid;
    @XmlElement(name = "EMPLOYEE_NUMBER", required = true, nillable = true)
    protected String employeenumber;
    @XmlElement(name = "LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastupdatedate;
    @XmlElement(name = "VENDOR_NAME_ALT", required = true, nillable = true)
    protected String vendornamealt;
    @XmlElement(name = "COMPANY_CODE", required = true, nillable = true)
    protected String companycode;
    @XmlElement(name = "INACTIVE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar inactivedate;
    @XmlElement(name = "ALLOW_SUBSTITUTE_RECEIPTS_FLAG", required = true, nillable = true)
    protected String allowsubstitutereceiptsflag;
    @XmlElement(name = "VENDOR_SITE_INFO", required = true)
    protected VendorSiteInfoCollection vendorsiteinfo;

    /**
     * Gets the value of the vendorid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVENDORID() {
        return vendorid;
    }

    /**
     * Sets the value of the vendorid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVENDORID(BigDecimal value) {
        this.vendorid = value;
    }

    /**
     * Gets the value of the vendorname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVENDORNAME() {
        return vendorname;
    }

    /**
     * Sets the value of the vendorname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVENDORNAME(String value) {
        this.vendorname = value;
    }

    /**
     * Gets the value of the vendornumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVENDORNUMBER() {
        return vendornumber;
    }

    /**
     * Sets the value of the vendornumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVENDORNUMBER(String value) {
        this.vendornumber = value;
    }

    /**
     * Gets the value of the vendortypelookupcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVENDORTYPELOOKUPCODE() {
        return vendortypelookupcode;
    }

    /**
     * Sets the value of the vendortypelookupcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVENDORTYPELOOKUPCODE(String value) {
        this.vendortypelookupcode = value;
    }

    /**
     * Gets the value of the vendortypedisp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVENDORTYPEDISP() {
        return vendortypedisp;
    }

    /**
     * Sets the value of the vendortypedisp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVENDORTYPEDISP(String value) {
        this.vendortypedisp = value;
    }

    /**
     * Gets the value of the vendorcreationdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVENDORCREATIONDATE() {
        return vendorcreationdate;
    }

    /**
     * Sets the value of the vendorcreationdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVENDORCREATIONDATE(XMLGregorianCalendar value) {
        this.vendorcreationdate = value;
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
     * Gets the value of the vatflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVATFLAG() {
        return vatflag;
    }

    /**
     * Sets the value of the vatflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVATFLAG(String value) {
        this.vatflag = value;
    }

    /**
     * Gets the value of the vatregistrationnum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVATREGISTRATIONNUM() {
        return vatregistrationnum;
    }

    /**
     * Sets the value of the vatregistrationnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVATREGISTRATIONNUM(String value) {
        this.vatregistrationnum = value;
    }

    /**
     * Gets the value of the vendorenddateactive property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVENDORENDDATEACTIVE() {
        return vendorenddateactive;
    }

    /**
     * Sets the value of the vendorenddateactive property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVENDORENDDATEACTIVE(XMLGregorianCalendar value) {
        this.vendorenddateactive = value;
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
     * Gets the value of the convendorid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCONVENDORID() {
        return convendorid;
    }

    /**
     * Sets the value of the convendorid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCONVENDORID(BigDecimal value) {
        this.convendorid = value;
    }

    /**
     * Gets the value of the employeename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPLOYEENAME() {
        return employeename;
    }

    /**
     * Sets the value of the employeename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPLOYEENAME(String value) {
        this.employeename = value;
    }

    /**
     * Gets the value of the personid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPERSONID() {
        return personid;
    }

    /**
     * Sets the value of the personid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPERSONID(BigDecimal value) {
        this.personid = value;
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
     * Gets the value of the lastupdatedate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLASTUPDATEDATE() {
        return lastupdatedate;
    }

    /**
     * Sets the value of the lastupdatedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLASTUPDATEDATE(XMLGregorianCalendar value) {
        this.lastupdatedate = value;
    }

    /**
     * Gets the value of the vendornamealt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVENDORNAMEALT() {
        return vendornamealt;
    }

    /**
     * Sets the value of the vendornamealt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVENDORNAMEALT(String value) {
        this.vendornamealt = value;
    }

    /**
     * Gets the value of the companycode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOMPANYCODE() {
        return companycode;
    }

    /**
     * Sets the value of the companycode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOMPANYCODE(String value) {
        this.companycode = value;
    }

    /**
     * Gets the value of the inactivedate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getINACTIVEDATE() {
        return inactivedate;
    }

    /**
     * Sets the value of the inactivedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setINACTIVEDATE(XMLGregorianCalendar value) {
        this.inactivedate = value;
    }

    /**
     * Gets the value of the allowsubstitutereceiptsflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getALLOWSUBSTITUTERECEIPTSFLAG() {
        return allowsubstitutereceiptsflag;
    }

    /**
     * Sets the value of the allowsubstitutereceiptsflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setALLOWSUBSTITUTERECEIPTSFLAG(String value) {
        this.allowsubstitutereceiptsflag = value;
    }

    /**
     * Gets the value of the vendorsiteinfo property.
     * 
     * @return
     *     possible object is
     *     {@link VendorSiteInfoCollection }
     *     
     */
    public VendorSiteInfoCollection getVENDORSITEINFO() {
        return vendorsiteinfo;
    }

    /**
     * Sets the value of the vendorsiteinfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorSiteInfoCollection }
     *     
     */
    public void setVENDORSITEINFO(VendorSiteInfoCollection value) {
        this.vendorsiteinfo = value;
    }

}
