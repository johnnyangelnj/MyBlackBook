
package com.johnnyangel.myblackbook.xml;


import java.math.BigInteger;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;



@Element
public class AddressType {
	
	
	public enum AddressEnumType
	{
	    Home,
	    Office,
	    Other
		
	}

    @Element
    protected AddressType.Address address;

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType.Address }
     *     
     */
    public AddressType.Address getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType.Address }
     *     
     */
    public void setAddress(AddressType.Address value) {
        this.address = value;
    }


    public static class Address {

        @Element
        protected  String country;
        @Element
        protected  String state;
        @Element
        protected  String other;
        @Element
        protected  String city;
        @Element
        protected  String street;
        @Element
        protected  BigInteger zip; 
        @Attribute
        protected BigInteger id;
        @Attribute
        protected AddressEnumType type;


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

        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link Addressenumtype }
         *     
         */
        public AddressEnumType getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link Addressenumtype }
         *     
         */
        public void setType(AddressEnumType value) {
            this.type = value;
        }

    }

}
