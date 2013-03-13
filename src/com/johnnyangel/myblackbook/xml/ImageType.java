
package com.johnnyangel.myblackbook.xml;

import java.math.BigInteger;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;


@ElementList
public class ImageType {

    @Element
    protected ImageType.Image image;

    /**
     * Gets the value of the image property.
     * 
     * @return
     *     possible object is
     *     {@link ImageType.Image }
     *     
     */
    public ImageType.Image getImage() {
        return image;
    }

    /**
     * Sets the value of the image property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageType.Image }
     *     
     */
    public void setImage(ImageType.Image value) {
        this.image = value;
    }

    @Element
    public static class Image {

        @Element
        protected String name;
        @Element
        protected String desctiption;
        @Element
        protected BigInteger size;
        @Element
        protected String uri;
        @Attribute(name = "id")
        protected BigInteger id;

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
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
         * Gets the value of the size property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getSize() {
            return size;
        }

        /**
         * Sets the value of the size property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setSize(BigInteger value) {
            this.size = value;
        }

        /**
         * Gets the value of the uri property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUri() {
            return uri;
        }

        /**
         * Sets the value of the uri property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUri(String value) {
            this.uri = value;
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
