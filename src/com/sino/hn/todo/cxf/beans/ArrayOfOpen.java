
package com.sino.hn.todo.cxf.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOpen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOpen">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Open" type="{http://beans.todo.mochasoft.com}Open" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOpen", propOrder = {
    "open"
})
public class ArrayOfOpen {

    @XmlElement(name = "Open", nillable = true)
    protected List<Open> open;

    /**
     * Gets the value of the open property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the open property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOpen().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Open }
     * 
     * 
     */
    public List<Open> getOpen() {
        if (open == null) {
            open = new ArrayList<Open>();
        }
        return this.open;
    }
    
    public boolean addOpen( Open form ){
    	if (open == null) {
            open = new ArrayList<Open>();
        }
    	return open.add( form );
    }

}
