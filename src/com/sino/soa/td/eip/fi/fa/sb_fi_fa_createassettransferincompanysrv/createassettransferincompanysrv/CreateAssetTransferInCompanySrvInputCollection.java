
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_createassettransferincompanysrv.createassettransferincompanysrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateAssetTransferInCompanySrvInputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateAssetTransferInCompanySrvInputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CreateAssetTransferInCompanySrvInputItem" type="{http://eip.zte.com/common/fa/CreateAssetTransferInCompanySrv}CreateAssetTransferInCompanySrvInputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateAssetTransferInCompanySrvInputCollection", propOrder = {
    "createAssetTransferInCompanySrvInputItem"
})
public class CreateAssetTransferInCompanySrvInputCollection {

    @XmlElement(name = "CreateAssetTransferInCompanySrvInputItem", nillable = true)
    protected List<CreateAssetTransferInCompanySrvInputItem> createAssetTransferInCompanySrvInputItem;

    /**
     * Gets the value of the createAssetTransferInCompanySrvInputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the createAssetTransferInCompanySrvInputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCreateAssetTransferInCompanySrvInputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CreateAssetTransferInCompanySrvInputItem }
     * 
     * 
     */
    public List<CreateAssetTransferInCompanySrvInputItem> getCreateAssetTransferInCompanySrvInputItem() {
        if (createAssetTransferInCompanySrvInputItem == null) {
            createAssetTransferInCompanySrvInputItem = new ArrayList<CreateAssetTransferInCompanySrvInputItem>();
        }
        return this.createAssetTransferInCompanySrvInputItem;
    }

}
