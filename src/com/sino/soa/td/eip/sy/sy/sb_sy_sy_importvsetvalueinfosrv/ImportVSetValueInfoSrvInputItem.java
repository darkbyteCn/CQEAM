
package com.sino.soa.td.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ImportVSetValueInfoSrvInputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImportVSetValueInfoSrvInputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PRI_KEY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FLEX_VALUE_SET_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VALIDATION_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FLEX_VALUE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
 *         &lt;element name="ATTRIBUTE7" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE8" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE9" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE10" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE11" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE12" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE13" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE14" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE15" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="EMPLOYEE_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImportVSetValueInfoSrvInputItem", propOrder = {
    "prikey",
    "flexvaluesetname",
    "validationtype",
    "flexvalue",
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
    "attribute7",
    "attribute8",
    "attribute9",
    "attribute10",
    "attribute11",
    "attribute12",
    "attribute13",
    "attribute14",
    "attribute15",
    "createdby",
    "employeenumber"
})
public class ImportVSetValueInfoSrvInputItem {

    @XmlElement(name = "PRI_KEY", required = true, nillable = true)
    protected String prikey;
    @XmlElement(name = "FLEX_VALUE_SET_NAME", required = true, nillable = true)
    protected String flexvaluesetname;
    @XmlElement(name = "VALIDATION_TYPE", required = true, nillable = true)
    protected String validationtype;
    @XmlElement(name = "FLEX_VALUE", required = true, nillable = true)
    protected String flexvalue;
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
    @XmlElement(name = "ATTRIBUTE7", required = true, nillable = true)
    protected String attribute7;
    @XmlElement(name = "ATTRIBUTE8", required = true, nillable = true)
    protected String attribute8;
    @XmlElement(name = "ATTRIBUTE9", required = true, nillable = true)
    protected String attribute9;
    @XmlElement(name = "ATTRIBUTE10", required = true, nillable = true)
    protected String attribute10;
    @XmlElement(name = "ATTRIBUTE11", required = true, nillable = true)
    protected String attribute11;
    @XmlElement(name = "ATTRIBUTE12", required = true, nillable = true)
    protected String attribute12;
    @XmlElement(name = "ATTRIBUTE13", required = true, nillable = true)
    protected String attribute13;
    @XmlElement(name = "ATTRIBUTE14", required = true, nillable = true)
    protected String attribute14;
    @XmlElement(name = "ATTRIBUTE15", required = true, nillable = true)
    protected String attribute15;
    @XmlElement(name = "CREATED_BY", required = true, nillable = true)
    protected BigDecimal createdby;
    @XmlElement(name = "EMPLOYEE_NUMBER", required = true, nillable = true)
    protected String employeenumber;

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
     * Gets the value of the attribute7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE7() {
        return attribute7;
    }

    /**
     * Sets the value of the attribute7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE7(String value) {
        this.attribute7 = value;
    }

    /**
     * Gets the value of the attribute8 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE8() {
        return attribute8;
    }

    /**
     * Sets the value of the attribute8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE8(String value) {
        this.attribute8 = value;
    }

    /**
     * Gets the value of the attribute9 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE9() {
        return attribute9;
    }

    /**
     * Sets the value of the attribute9 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE9(String value) {
        this.attribute9 = value;
    }

    /**
     * Gets the value of the attribute10 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE10() {
        return attribute10;
    }

    /**
     * Sets the value of the attribute10 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE10(String value) {
        this.attribute10 = value;
    }

    /**
     * Gets the value of the attribute11 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE11() {
        return attribute11;
    }

    /**
     * Sets the value of the attribute11 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE11(String value) {
        this.attribute11 = value;
    }

    /**
     * Gets the value of the attribute12 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE12() {
        return attribute12;
    }

    /**
     * Sets the value of the attribute12 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE12(String value) {
        this.attribute12 = value;
    }

    /**
     * Gets the value of the attribute13 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE13() {
        return attribute13;
    }

    /**
     * Sets the value of the attribute13 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE13(String value) {
        this.attribute13 = value;
    }

    /**
     * Gets the value of the attribute14 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE14() {
        return attribute14;
    }

    /**
     * Sets the value of the attribute14 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE14(String value) {
        this.attribute14 = value;
    }

    /**
     * Gets the value of the attribute15 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE15() {
        return attribute15;
    }

    /**
     * Sets the value of the attribute15 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE15(String value) {
        this.attribute15 = value;
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

}
