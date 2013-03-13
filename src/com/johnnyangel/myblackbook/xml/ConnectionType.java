
package com.johnnyangel.myblackbook.xml;

import java.math.BigInteger;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;



@ElementList
public class ConnectionType {
	
	public enum ConnectionEnumType
	{
	    Aol,
	    Yahoo,
	    Google,
	    Facebook,
	    Twitter,
	    Other
		
	}


    @Element
    protected ConnectionType.Connection connection;

    /**
     * Gets the value of the connection property.
     * 
     * @return
     *     possible object is
     *     {@link ConnectionType.Connection }
     *     
     */
    public ConnectionType.Connection getConnection() {
        return connection;
    }

    /**
     * Sets the value of the connection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConnectionType.Connection }
     *     
     */
    public void setConnection(ConnectionType.Connection value) {
        this.connection = value;
    }

    @Element
    public static class Connection {

        @Element
        protected String username;
        @Element
        protected String other;
        @Attribute
        protected BigInteger id;
        @Attribute
        protected ConnectionEnumType type;

        /**
         * Gets the value of the username property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUsername() {
            return username;
        }

        /**
         * Sets the value of the username property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUsername(String value) {
            this.username = value;
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

    }

}
