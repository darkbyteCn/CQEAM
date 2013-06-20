
package com.sino.soa.td.eip.sy.sy.sb_sy_sy_pageinquiryvsetvalueinfosrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InquiryVSetValueInfoSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InquiryVSetValueInfoSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FLEX_VALUE_SET_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VALIDATION_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FLEX_VALUE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FLEX_VALUE_MEARNING" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ENABLED_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SUMMARY_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="START_DATE_ACTIVE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="END_DATE_ACTIVE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="STRUCTURED_HIERARCHY_LEVEL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="HIERARCHY_LEVEL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PARENT_FLEX_VALUE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="COMPILED_VALUE_ATTRIBUTES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE6" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FLEX_VALUE_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="FLEX_VALUE_SET_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LANGUAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InquiryVSetValueInfoSrvOutputItem", propOrder = {
    "flexvaluesetname",
    "validationtype",
    "flexvalue",
    "flexvaluemearning",
    "description",
    "enabledflag",
    "summaryflag",
    "startdateactive",
    "enddateactive",
    "structuredhierarchylevel",
    "hierarchylevel",
    "parentflexvalue",
    "compiledvalueattributes",
    "attribute1",
    "attribute2",
    "attribute3",
    "attribute4",
    "attribute5",
    "attribute6",
    "flexvalueid",
    "flexvaluesetid",
    "language",
    "lastupdatedate"
})
public class InquiryVSetValueInfoSrvOutputItem {

    @XmlElement(name = "FLEX_VALUE_SET_NAME", required = true, nillable = true)
    protected String flexvaluesetname;
    @XmlElement(name = "VALIDATION_TYPE", required = true, nillable = true)
    protected String validationtype;
    @XmlElement(name = "FLEX_VALUE", required = true, nillable = true)
    protected String flexvalue;
    @XmlElement(name = "FLEX_VALUE_MEARNING", required = true, nillable = true)
    protected String flexvaluemearning;
    @XmlElement(name = "DESCRIPTION", required = true, nillable = true)
    protected String description;
    @XmlElement(name = "ENABLED_FLAG", required = true, nillable = true)
    protected String enabledflag;
    @XmlElement(name = "SUMMARY_FLAG", required = true, nillable = true)
    protected String summaryflag;
    @XmlElement(name = "START_DATE_ACTIVE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startdateactive;
    @XmlElement(name = "END_DATE_ACTIVE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar enddateactive;
    @XmlElement(name = "STRUCTURED_HIERARCHY_LEVEL", required = true, nillable = true)
    protected String structuredhierarchylevel;
    @XmlElement(name = "HIERARCHY_LEVEL", required = true, nillable = true)
    protected String hierarchylevel;
    @XmlElement(name = "PARENT_FLEX_VALUE", required = true, nillable = true)
    protected String parentflexvalue;
    @XmlElement(name = "COMPILED_VALUE_ATTRIBUTES", required = true, nillable = true)
    protected String compiledvalueattributes;
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
    @XmlElement(name = "FLEX_VALUE_ID", required = true, nillable = true)
    protected BigDecimal flexvalueid;
    @XmlElement(name = "FLEX_VALUE_SET_ID", required = true, nillable = true)
    protected BigDecimal flexvaluesetid;
    @XmlElement(name = "LANGUAGE", required = true, nillable = true)
    protected String language;
    @XmlElement(name = "LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastupdatedate;

    /**
     * Gets the value of the flexvaluesetname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLEXVALUESETNAME() {
        return flexvaluesetname;
    }

    /**
     * Sets the value of the flexvaluesetname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLEXVALUESETNAME(String value) {
        this.flexvaluesetname = value;
    }

    /**
     * Gets the value of the validationtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVALIDATIONTYPE() {
        return validationtype;
    }

    /**
     * Sets the value of the validationtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVALIDATIONTYPE(String value) {
        this.validationtype = value;
    }

    /**
     * Gets the value of the flexvalue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLEXVALUE() {
        return flexvalue;
    }

    /**
     * Sets the value of the flexvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLEXVALUE(String value) {
        this.flexvalue = value;
    }

    /**
     * Gets the value of the flexvaluemearning property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLEXVALUEMEARNING() {
        return flexvaluemearning;
    }

    /**
     * Sets the value of the flexvaluemearning property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLEXVALUEMEARNING(String value) {
        this.flexvaluemearning = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRIPTION() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRIPTION(String value) {
        this.description = value;
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
     * Gets the value of the summaryflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSUMMARYFLAG() {
        return summaryflag;
    }

    /**
     * Sets the value of the summaryflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSUMMARYFLAG(String value) {
        this.summaryflag = value;
    }

    /**
     * Gets the value of the startdateactive property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSTARTDATEACTIVE() {
        return startdateactive;
    }

    /**
     * Sets the value of the startdateactive property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSTARTDATEACTIVE(XMLGregorianCalendar value) {
        this.startdateactive = value;
    }

    /**
     * Gets the value of the enddateactive property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getENDDATEACTIVE() {
        return enddateactive;
    }

    /**
     * Sets the value of the enddateactive property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setENDDATEACTIVE(XMLGregorianCalendar value) {
        this.enddateactive = value;
    }

    /**
     * Gets the value of the structuredhierarchylevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTRUCTUREDHIERARCHYLEVEL() {
        return structuredhierarchylevel;
    }

    /**
     * Sets the value of the structuredhierarchylevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTRUCTUREDHIERARCHYLEVEL(String value) {
        this.structuredhierarchylevel = value;
    }

    /**
     * Gets the value of the hierarchylevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHIERARCHYLEVEL() {
        return hierarchylevel;
    }

    /**
     * Sets the value of the hierarchylevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHIERARCHYLEVEL(String value) {
        this.hierarchylevel = value;
    }

    /**
     * Gets the value of the parentflexvalue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARENTFLEXVALUE() {
        return parentflexvalue;
    }

    /**
     * Sets the value of the parentflexvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARENTFLEXVALUE(String value) {
        this.parentflexvalue = value;
    }

    /**
     * Gets the value of the compiledvalueattributes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOMPILEDVALUEATTRIBUTES() {
        return compiledvalueattributes;
    }

    /**
     * Sets the value of the compiledvalueattributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOMPILEDVALUEATTRIBUTES(String value) {
        this.compiledvalueattributes = value;
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
     * Gets the value of the flexvalueid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFLEXVALUEID() {
        return flexvalueid;
    }

    /**
     * Sets the value of the flexvalueid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFLEXVALUEID(BigDecimal value) {
        this.flexvalueid = value;
    }

    /**
     * Gets the value of the flexvaluesetid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFLEXVALUESETID() {
        return flexvaluesetid;
    }

    /**
     * Sets the value of the flexvaluesetid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFLEXVALUESETID(BigDecimal value) {
        this.flexvaluesetid = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLANGUAGE() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLANGUAGE(String value) {
        this.language = value;
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

}
