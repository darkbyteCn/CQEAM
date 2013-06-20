
package com.sino.soa.mis.eip.sc.po.sb_sc_po_inquiryvendorinfosrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for VendorSiteInfoItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VendorSiteInfoItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VENDOR_SITE_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ORG_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VENDOR_SITE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VALID_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CREATION_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ADDRESS_LINE1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ADDRESS_LINE2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ADDRESS_LINE3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ADDRESS_LINE4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LIABILITY_ACCOUNT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PROVINCE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CITY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZIP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CONTACT_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CONTACT_PHONE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CONTACT_EMAIL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CONTACT_FAX" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IC_REGISTRATION_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BANK_ACCOUNT_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BANK_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BANK_BRANCH_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BANK_ABSTRCT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BANK_ACCOUNT_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BANK_ACCOUNT_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BANK_VALID_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BANK_BRANCH_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="COST_CENTER_VALUE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PREPAY_ACCOUNT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE6" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ABAU_LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ABAA_LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="BANK_ACCOUNT_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="BANK_ACCOUNT_USES_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="VENDOR_CONTACT_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="SITE_INACTIVE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="BANK_USER_END_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="PRIMARY_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ACCOUNT_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VendorSiteInfoItem", propOrder = {
    "vendorsiteid",
    "orgname",
    "vendorsitecode",
    "validflag",
    "creationdate",
    "addressline1",
    "addressline2",
    "addressline3",
    "addressline4",
    "liabilityaccount",
    "country",
    "province",
    "city",
    "zip",
    "contactname",
    "contactphone",
    "contactemail",
    "contactfax",
    "icregistrationnum",
    "bankaccounttype",
    "bankname",
    "bankbranchname",
    "bankabstrct",
    "bankaccountname",
    "bankaccountnum",
    "bankvalidflag",
    "bankbranchtype",
    "costcentervalue",
    "prepayaccount",
    "attribute1",
    "attribute2",
    "attribute3",
    "attribute4",
    "attribute5",
    "attribute6",
    "lastupdatedate",
    "abaulastupdatedate",
    "abaalastupdatedate",
    "bankaccountid",
    "bankaccountusesid",
    "vendorcontactid",
    "siteinactivedate",
    "bankuserenddate",
    "primaryflag",
    "accounttype"
})
public class VendorSiteInfoItem {

    @XmlElement(name = "VENDOR_SITE_ID", required = true, nillable = true)
    protected BigDecimal vendorsiteid;
    @XmlElement(name = "ORG_NAME", required = true, nillable = true)
    protected String orgname;
    @XmlElement(name = "VENDOR_SITE_CODE", required = true, nillable = true)
    protected String vendorsitecode;
    @XmlElement(name = "VALID_FLAG", required = true, nillable = true)
    protected String validflag;
    @XmlElement(name = "CREATION_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationdate;
    @XmlElement(name = "ADDRESS_LINE1", required = true, nillable = true)
    protected String addressline1;
    @XmlElement(name = "ADDRESS_LINE2", required = true, nillable = true)
    protected String addressline2;
    @XmlElement(name = "ADDRESS_LINE3", required = true, nillable = true)
    protected String addressline3;
    @XmlElement(name = "ADDRESS_LINE4", required = true, nillable = true)
    protected String addressline4;
    @XmlElement(name = "LIABILITY_ACCOUNT", required = true, nillable = true)
    protected String liabilityaccount;
    @XmlElement(name = "COUNTRY", required = true, nillable = true)
    protected String country;
    @XmlElement(name = "PROVINCE", required = true, nillable = true)
    protected String province;
    @XmlElement(name = "CITY", required = true, nillable = true)
    protected String city;
    @XmlElement(name = "ZIP", required = true, nillable = true)
    protected String zip;
    @XmlElement(name = "CONTACT_NAME", required = true, nillable = true)
    protected String contactname;
    @XmlElement(name = "CONTACT_PHONE", required = true, nillable = true)
    protected String contactphone;
    @XmlElement(name = "CONTACT_EMAIL", required = true, nillable = true)
    protected String contactemail;
    @XmlElement(name = "CONTACT_FAX", required = true, nillable = true)
    protected String contactfax;
    @XmlElement(name = "IC_REGISTRATION_NUM", required = true, nillable = true)
    protected String icregistrationnum;
    @XmlElement(name = "BANK_ACCOUNT_TYPE", required = true, nillable = true)
    protected String bankaccounttype;
    @XmlElement(name = "BANK_NAME", required = true, nillable = true)
    protected String bankname;
    @XmlElement(name = "BANK_BRANCH_NAME", required = true, nillable = true)
    protected String bankbranchname;
    @XmlElement(name = "BANK_ABSTRCT", required = true, nillable = true)
    protected String bankabstrct;
    @XmlElement(name = "BANK_ACCOUNT_NAME", required = true, nillable = true)
    protected String bankaccountname;
    @XmlElement(name = "BANK_ACCOUNT_NUM", required = true, nillable = true)
    protected String bankaccountnum;
    @XmlElement(name = "BANK_VALID_FLAG", required = true, nillable = true)
    protected String bankvalidflag;
    @XmlElement(name = "BANK_BRANCH_TYPE", required = true, nillable = true)
    protected String bankbranchtype;
    @XmlElement(name = "COST_CENTER_VALUE", required = true, nillable = true)
    protected String costcentervalue;
    @XmlElement(name = "PREPAY_ACCOUNT", required = true, nillable = true)
    protected String prepayaccount;
    @XmlElement(name = "ATTRIBUTE1", required = true, nillable = true)
    protected String attribute1;
    @XmlElement(name = "ATTRIBUTE2", required = true, nillable = true)
    protected String attribute2;
    @XmlElement(name = "ATTRIBUTE3", required = true, nillable = true)
    protected String attribute3;
    @XmlElement(name = "ATTRIBUTE4", required = true, nillable = true)
    protected String attribute4;
    @XmlElement(name = "ATTRIBUTE5", required = true, nillable = true)
    protected String attribute5;
    @XmlElement(name = "ATTRIBUTE6", required = true, nillable = true)
    protected String attribute6;
    @XmlElement(name = "LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastupdatedate;
    @XmlElement(name = "ABAU_LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar abaulastupdatedate;
    @XmlElement(name = "ABAA_LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar abaalastupdatedate;
    @XmlElement(name = "BANK_ACCOUNT_ID", required = true, nillable = true)
    protected BigDecimal bankaccountid;
    @XmlElement(name = "BANK_ACCOUNT_USES_ID", required = true, nillable = true)
    protected BigDecimal bankaccountusesid;
    @XmlElement(name = "VENDOR_CONTACT_ID", required = true, nillable = true)
    protected BigDecimal vendorcontactid;
    @XmlElement(name = "SITE_INACTIVE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar siteinactivedate;
    @XmlElement(name = "BANK_USER_END_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar bankuserenddate;
    @XmlElement(name = "PRIMARY_FLAG", required = true, nillable = true)
    protected String primaryflag;
    @XmlElement(name = "ACCOUNT_TYPE", required = true, nillable = true)
    protected String accounttype;

    /**
     * Gets the value of the vendorsiteid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVENDORSITEID() {
        return vendorsiteid;
    }

    /**
     * Sets the value of the vendorsiteid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVENDORSITEID(BigDecimal value) {
        this.vendorsiteid = value;
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
     * Gets the value of the vendorsitecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVENDORSITECODE() {
        return vendorsitecode;
    }

    /**
     * Sets the value of the vendorsitecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVENDORSITECODE(String value) {
        this.vendorsitecode = value;
    }

    /**
     * Gets the value of the validflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVALIDFLAG() {
        return validflag;
    }

    /**
     * Sets the value of the validflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVALIDFLAG(String value) {
        this.validflag = value;
    }

    /**
     * Gets the value of the creationdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCREATIONDATE() {
        return creationdate;
    }

    /**
     * Sets the value of the creationdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCREATIONDATE(XMLGregorianCalendar value) {
        this.creationdate = value;
    }

    /**
     * Gets the value of the addressline1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADDRESSLINE1() {
        return addressline1;
    }

    /**
     * Sets the value of the addressline1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADDRESSLINE1(String value) {
        this.addressline1 = value;
    }

    /**
     * Gets the value of the addressline2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADDRESSLINE2() {
        return addressline2;
    }

    /**
     * Sets the value of the addressline2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADDRESSLINE2(String value) {
        this.addressline2 = value;
    }

    /**
     * Gets the value of the addressline3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADDRESSLINE3() {
        return addressline3;
    }

    /**
     * Sets the value of the addressline3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADDRESSLINE3(String value) {
        this.addressline3 = value;
    }

    /**
     * Gets the value of the addressline4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADDRESSLINE4() {
        return addressline4;
    }

    /**
     * Sets the value of the addressline4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADDRESSLINE4(String value) {
        this.addressline4 = value;
    }

    /**
     * Gets the value of the liabilityaccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLIABILITYACCOUNT() {
        return liabilityaccount;
    }

    /**
     * Sets the value of the liabilityaccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLIABILITYACCOUNT(String value) {
        this.liabilityaccount = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOUNTRY() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOUNTRY(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the province property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCE() {
        return province;
    }

    /**
     * Sets the value of the province property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCE(String value) {
        this.province = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCITY() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCITY(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the zip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZIP() {
        return zip;
    }

    /**
     * Sets the value of the zip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZIP(String value) {
        this.zip = value;
    }

    /**
     * Gets the value of the contactname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTACTNAME() {
        return contactname;
    }

    /**
     * Sets the value of the contactname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTACTNAME(String value) {
        this.contactname = value;
    }

    /**
     * Gets the value of the contactphone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTACTPHONE() {
        return contactphone;
    }

    /**
     * Sets the value of the contactphone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTACTPHONE(String value) {
        this.contactphone = value;
    }

    /**
     * Gets the value of the contactemail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTACTEMAIL() {
        return contactemail;
    }

    /**
     * Sets the value of the contactemail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTACTEMAIL(String value) {
        this.contactemail = value;
    }

    /**
     * Gets the value of the contactfax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTACTFAX() {
        return contactfax;
    }

    /**
     * Sets the value of the contactfax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTACTFAX(String value) {
        this.contactfax = value;
    }

    /**
     * Gets the value of the icregistrationnum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getICREGISTRATIONNUM() {
        return icregistrationnum;
    }

    /**
     * Sets the value of the icregistrationnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setICREGISTRATIONNUM(String value) {
        this.icregistrationnum = value;
    }

    /**
     * Gets the value of the bankaccounttype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBANKACCOUNTTYPE() {
        return bankaccounttype;
    }

    /**
     * Sets the value of the bankaccounttype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBANKACCOUNTTYPE(String value) {
        this.bankaccounttype = value;
    }

    /**
     * Gets the value of the bankname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBANKNAME() {
        return bankname;
    }

    /**
     * Sets the value of the bankname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBANKNAME(String value) {
        this.bankname = value;
    }

    /**
     * Gets the value of the bankbranchname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBANKBRANCHNAME() {
        return bankbranchname;
    }

    /**
     * Sets the value of the bankbranchname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBANKBRANCHNAME(String value) {
        this.bankbranchname = value;
    }

    /**
     * Gets the value of the bankabstrct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBANKABSTRCT() {
        return bankabstrct;
    }

    /**
     * Sets the value of the bankabstrct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBANKABSTRCT(String value) {
        this.bankabstrct = value;
    }

    /**
     * Gets the value of the bankaccountname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBANKACCOUNTNAME() {
        return bankaccountname;
    }

    /**
     * Sets the value of the bankaccountname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBANKACCOUNTNAME(String value) {
        this.bankaccountname = value;
    }

    /**
     * Gets the value of the bankaccountnum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBANKACCOUNTNUM() {
        return bankaccountnum;
    }

    /**
     * Sets the value of the bankaccountnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBANKACCOUNTNUM(String value) {
        this.bankaccountnum = value;
    }

    /**
     * Gets the value of the bankvalidflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBANKVALIDFLAG() {
        return bankvalidflag;
    }

    /**
     * Sets the value of the bankvalidflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBANKVALIDFLAG(String value) {
        this.bankvalidflag = value;
    }

    /**
     * Gets the value of the bankbranchtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBANKBRANCHTYPE() {
        return bankbranchtype;
    }

    /**
     * Sets the value of the bankbranchtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBANKBRANCHTYPE(String value) {
        this.bankbranchtype = value;
    }

    /**
     * Gets the value of the costcentervalue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOSTCENTERVALUE() {
        return costcentervalue;
    }

    /**
     * Sets the value of the costcentervalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOSTCENTERVALUE(String value) {
        this.costcentervalue = value;
    }

    /**
     * Gets the value of the prepayaccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPREPAYACCOUNT() {
        return prepayaccount;
    }

    /**
     * Sets the value of the prepayaccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPREPAYACCOUNT(String value) {
        this.prepayaccount = value;
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
     * Gets the value of the attribute2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE2() {
        return attribute2;
    }

    /**
     * Sets the value of the attribute2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE2(String value) {
        this.attribute2 = value;
    }

    /**
     * Gets the value of the attribute3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE3() {
        return attribute3;
    }

    /**
     * Sets the value of the attribute3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE3(String value) {
        this.attribute3 = value;
    }

    /**
     * Gets the value of the attribute4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE4() {
        return attribute4;
    }

    /**
     * Sets the value of the attribute4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE4(String value) {
        this.attribute4 = value;
    }

    /**
     * Gets the value of the attribute5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE5() {
        return attribute5;
    }

    /**
     * Sets the value of the attribute5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE5(String value) {
        this.attribute5 = value;
    }

    /**
     * Gets the value of the attribute6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE6() {
        return attribute6;
    }

    /**
     * Sets the value of the attribute6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE6(String value) {
        this.attribute6 = value;
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
     * Gets the value of the abaulastupdatedate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getABAULASTUPDATEDATE() {
        return abaulastupdatedate;
    }

    /**
     * Sets the value of the abaulastupdatedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setABAULASTUPDATEDATE(XMLGregorianCalendar value) {
        this.abaulastupdatedate = value;
    }

    /**
     * Gets the value of the abaalastupdatedate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getABAALASTUPDATEDATE() {
        return abaalastupdatedate;
    }

    /**
     * Sets the value of the abaalastupdatedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setABAALASTUPDATEDATE(XMLGregorianCalendar value) {
        this.abaalastupdatedate = value;
    }

    /**
     * Gets the value of the bankaccountid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBANKACCOUNTID() {
        return bankaccountid;
    }

    /**
     * Sets the value of the bankaccountid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBANKACCOUNTID(BigDecimal value) {
        this.bankaccountid = value;
    }

    /**
     * Gets the value of the bankaccountusesid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBANKACCOUNTUSESID() {
        return bankaccountusesid;
    }

    /**
     * Sets the value of the bankaccountusesid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBANKACCOUNTUSESID(BigDecimal value) {
        this.bankaccountusesid = value;
    }

    /**
     * Gets the value of the vendorcontactid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVENDORCONTACTID() {
        return vendorcontactid;
    }

    /**
     * Sets the value of the vendorcontactid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVENDORCONTACTID(BigDecimal value) {
        this.vendorcontactid = value;
    }

    /**
     * Gets the value of the siteinactivedate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSITEINACTIVEDATE() {
        return siteinactivedate;
    }

    /**
     * Sets the value of the siteinactivedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSITEINACTIVEDATE(XMLGregorianCalendar value) {
        this.siteinactivedate = value;
    }

    /**
     * Gets the value of the bankuserenddate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBANKUSERENDDATE() {
        return bankuserenddate;
    }

    /**
     * Sets the value of the bankuserenddate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBANKUSERENDDATE(XMLGregorianCalendar value) {
        this.bankuserenddate = value;
    }

    /**
     * Gets the value of the primaryflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRIMARYFLAG() {
        return primaryflag;
    }

    /**
     * Sets the value of the primaryflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRIMARYFLAG(String value) {
        this.primaryflag = value;
    }

    /**
     * Gets the value of the accounttype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACCOUNTTYPE() {
        return accounttype;
    }

    /**
     * Sets the value of the accounttype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACCOUNTTYPE(String value) {
        this.accounttype = value;
    }

}
