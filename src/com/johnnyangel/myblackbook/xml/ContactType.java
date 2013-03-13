
package com.johnnyangel.myblackbook.xml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import javax.xml.datatype.XMLGregorianCalendar;

@ElementList
public class ContactType {

    @Element
    protected NameType name;
    @Element
    protected XMLGregorianCalendar birthDate;
    @Element
    protected ImageIdType imageId;
    @ElementList
    protected List<AddressType> addressess;
    @ElementList
    protected List<PhoneNumberType> phonenumbers;
    @ElementList
    protected List<EmailType> emails;
    @ElementList
    protected List<ConnectionType> connections;
    @Attribute
    
    protected BigInteger id;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link NameType }
     *     
     */
    public NameType getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameType }
     *     
     */
    public void setName(NameType value) {
        this.name = value;
    }

    /**
     * Gets the value of the birthDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the value of the birthDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBirthDate(XMLGregorianCalendar value) {
        this.birthDate = value;
    }

    /**
     * Gets the value of the imageId property.
     * 
     * @return
     *     possible object is
     *     {@link ImageIdType }
     *     
     */
    public ImageIdType getImageId() {
        return imageId;
    }

    /**
     * Sets the value of the imageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageIdType }
     *     
     */
    public void setImageId(ImageIdType value) {
        this.imageId = value;
    }

    /**
     * Gets the value of the addressess property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addressess property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddressess().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddressType }
     * 
     * 
     */
    public List<AddressType> getAddressess() {
        if (addressess == null) {
            addressess = new ArrayList<AddressType>();
        }
        return this.addressess;
    }

    /**
     * Gets the value of the phonenumbers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the phonenumbers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPhonenumbers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PhoneNumberType }
     * 
     * 
     */
    public List<PhoneNumberType> getPhonenumbers() {
        if (phonenumbers == null) {
            phonenumbers = new ArrayList<PhoneNumberType>();
        }
        return this.phonenumbers;
    }

    /**
     * Gets the value of the emails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the emails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EmailType }
     * 
     * 
     */
    public List<EmailType> getEmails() {
        if (emails == null) {
            emails = new ArrayList<EmailType>();
        }
        return this.emails;
    }

    /**
     * Gets the value of the connections property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the connections property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConnections().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConnectionType }
     * 
     * 
     */
    public List<ConnectionType> getConnections() {
        if (connections == null) {
            connections = new ArrayList<ConnectionType>();
        }
        return this.connections;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

}
