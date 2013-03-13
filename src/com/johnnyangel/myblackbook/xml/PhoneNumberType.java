
package com.johnnyangel.myblackbook.xml;

import java.math.BigInteger;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;



@Element
public class PhoneNumberType {
	
	public enum PhoneNumberEnumType
	{
	    Mobile,
	    Home,
	    Office,
	    Fax,
	    Pager,
	    Other
		
	}
	
	@Element
    protected PhoneNumberType.PhoneNumber phoneNumber;

    /**
     * Gets the value of the phoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneNumberType.PhoneNumber }
     *     
     */
    public PhoneNumberType.PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the value of the phoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneNumberType.PhoneNumber }
     *     
     */
    public void setPhoneNumber(PhoneNumberType.PhoneNumber value) {
        this.phoneNumber = value;
    }




    public static class PhoneNumber {

        @Element
        protected BigInteger international;
        @Element
        protected BigInteger npa;
        @Element
        protected BigInteger exchange;
        @Element
        protected BigInteger line;
        @Element
        protected BigInteger extension;
        @Element
        protected String other;
        @Attribute
        protected BigInteger id;
        @Attribute
        protected PhoneNumberEnumType type;

        /**
         * Gets the value of the international property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getInternational() {
            return international;
        }

        /**
         * Sets the value of the international property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setInternational(BigInteger value) {
            this.international = value;
        }

        /**
         * Gets the value of the npa property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getNpa() {
            return npa;
        }

        /**
         * Sets the value of the npa property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNpa(BigInteger value) {
            this.npa = value;
        }

        /**
         * Gets the value of the exchange property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getExchange() {
            return exchange;
        }

        /**
         * Sets the value of the exchange property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setExchange(BigInteger value) {
            this.exchange = value;
        }

        /**
         * Gets the value of the line property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getLine() {
            return line;
        }

        /**
         * Sets the value of the line property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setLine(BigInteger value) {
            this.line = value;
        }

        /**
         * Gets the value of the extension property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getExtension() {
            return extension;
        }

        /**
         * Sets the value of the extension property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setExtension(BigInteger value) {
            this.extension = value;
        }

        /**
         * Gets the value of the other property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOther() {
            return other;
        }

        /**
         * Sets the value of the other property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOther(String value) {
            this.other = value;
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

        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link Phonenumberenumtype }
         *     
         */
        public PhoneNumberEnumType getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link Phonenumberenumtype }
         *     
         */
        public void setType(PhoneNumberEnumType value) {
            this.type = value;
        }

    }

}
