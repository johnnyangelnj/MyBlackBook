
package com.johnnyangel.myblackbook.xml;

import java.math.BigInteger;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import javax.xml.datatype.XMLGregorianCalendar;


@ElementList
public class NoteType {

	@Element
    protected NoteType.Note note;

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link NoteType.Note }
     *     
     */
    public NoteType.Note getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link NoteType.Note }
     *     
     */
    public void setNote(NoteType.Note value) {
        this.note = value;
    }

    @Element
    public static class Note {

        @Element
        protected String title;
        @Element
        protected String desctiption;
        @Element
        protected XMLGregorianCalendar modifyDate;
        @Element
        protected XMLGregorianCalendar createDate;
        @Element
        protected String message;
        @Attribute(name = "id")
        protected BigInteger id;

        /**
         * Gets the value of the title property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sets the value of the title property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTitle(String value) {
            this.title = value;
        }

        /**
         * Gets the value of the desctiption property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDesctiption() {
            return desctiption;
        }

        /**
         * Sets the value of the desctiption property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDesctiption(String value) {
            this.desctiption = value;
        }

        /**
         * Gets the value of the modifyDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getModifyDate() {
            return modifyDate;
        }

        /**
         * Sets the value of the modifyDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setModifyDate(XMLGregorianCalendar value) {
            this.modifyDate = value;
        }

        /**
         * Gets the value of the createDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getCreateDate() {
            return createDate;
        }

        /**
         * Sets the value of the createDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setCreateDate(XMLGregorianCalendar value) {
            this.createDate = value;
        }

        /**
         * Gets the value of the message property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMessage() {
            return message;
        }

        /**
         * Sets the value of the message property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMessage(String value) {
            this.message = value;
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
