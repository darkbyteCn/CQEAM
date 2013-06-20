
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.createassettransferincompanysrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateAssetTransferInCompanySrvGroupCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateAssetTransferInCompanySrvGroupCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CreateAssetTransferInCompanySrvInputCollection" type="{http://eip.zte.com/common/fa/CreateAssetTransferInCompanySrv}CreateAssetTransferInCompanySrvInputCollection" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateAssetTransferInCompanySrvGroupCollection", propOrder = {
    "createAssetTransferInCompanySrvInputCollection"
})
public class CreateAssetTransferInCompanySrvGroupCollection {

    @XmlElement(name = "CreateAssetTransferInCompanySrvInputCollection")
    protected List<CreateAssetTransferInCompanySrvInputCollection> createAssetTransferInCompanySrvInputCollection;

    /**
     * Gets the value of the createAssetTransferInCompanySrvInputCollection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the createAssetTransferInCompanySrvInputCollection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCreateAssetTransferInCompanySrvInputCollection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CreateAssetTransferInCompanySrvInputCollection }
     * 
     * 
     */
    public List<CreateAssetTransferInCompanySrvInputCollection> getCreateAssetTransferInCompanySrvInputCollection() {
        if (createAssetTransferInCompanySrvInputCollection == null) {
            createAssetTransferInCompanySrvInputCollection = new ArrayList<CreateAssetTransferInCompanySrvInputCollection>();
        }
        return this.createAssetTransferInCompanySrvInputCollection;
    }

}
