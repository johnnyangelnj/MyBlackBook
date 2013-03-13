
package com.johnnyangel.myblackbook.xml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;


@ElementList
public class PasswordRecoveryType {

	@ElementList
    protected List<PasswordRecoveryType.Security> security;
    @Attribute(name = "id")
    protected BigInteger id;

    /**
     * Gets the value of the security property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the security property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSecurity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PasswordRecoveryType.Security }
     * 
     * 
     */
    public List<PasswordRecoveryType.Security> getSecurity() {
        if (security == null) {
            security = new ArrayList<PasswordRecoveryType.Security>();
        }
        return this.security;
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
     * 
     * @author jocordero
     *
     */

    @Element(name= "security")
    public static class Security {

        @Element
        protected String question;
        @Element
        protected String answer;
        @Attribute
        protected BigInteger id;

        /**
         * Gets the value of the question property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getQuestion() {
            return question;
        }

        /**
         * Sets the value of the question property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setQuestion(String value) {
            this.question = value;
        }

        /**
         * Gets the value of the answer property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAnswer() {
            return answer;
        }

        /**
         * Sets the value of the answer property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAnswer(String value) {
            this.answer = value;
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
