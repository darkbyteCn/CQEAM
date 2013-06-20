
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_transassetheaderinfosrv.odi_variables;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ODI_Variables complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ODI_Variables">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ODI_Variables_type" type="{http://eip.zte.com/odi/ODI_Variables}ODI_Variables_type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ODI_Variables", propOrder = {
    "odiVariablesType"
})
public class ODIVariables {

    @XmlElement(name = "ODI_Variables_type")
    protected List<ODIVariablesType> odiVariablesType;

    /**
     * Gets the value of the odiVariablesType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the odiVariablesType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getODIVariablesType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ODIVariablesType }
     * 
     * 
     */
    public List<ODIVariablesType> getODIVariablesType() {
        if (odiVariablesType == null) {
            odiVariablesType = new ArrayList<ODIVariablesType>();
        }
        return this.odiVariablesType;
    }

}
