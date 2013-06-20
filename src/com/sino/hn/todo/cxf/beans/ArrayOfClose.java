
package com.sino.hn.todo.cxf.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfClose complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfClose">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Close" type="{http://beans.todo.mochasoft.com}Close" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfClose", propOrder = {
    "close"
})
public class ArrayOfClose {

    @XmlElement(name = "Close", nillable = true)
    protected List<Close> close;

    /**
     * Gets the value of the close property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the close property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClose().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Close }
     * 
     * 
     */
    public List<Close> getClose() {
        if (close == null) {
            close = new ArrayList<Close>();
        }
        return this.close;
    }
    

    public boolean addClose( Close form ){
    	return close.add( form );
    }

}
