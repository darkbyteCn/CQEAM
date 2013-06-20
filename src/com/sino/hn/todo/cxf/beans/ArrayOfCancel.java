
package com.sino.hn.todo.cxf.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.sino.hn.todo.cxf.beans.Cancel;


/**
 * <p>Java class for ArrayOfCancel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfCancel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Cancel" type="{http://beans.todo.mochasoft.com}Cancel" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCancel", propOrder = {
    "cancel"
})
public class ArrayOfCancel {

    @XmlElement(name = "Cancel", nillable = true)
    protected List<Cancel> cancel;

    /**
     * Gets the value of the cancel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cancel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCancel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Cancel }
     * 
     * 
     */
    public List<Cancel> getCancel() {
        if (cancel == null) {
            cancel = new ArrayList<Cancel>();
        }
        return this.cancel;
    }
    
    public boolean addCancel( Cancel form ){
    	return cancel.add( form );
    }

}
