package uk.ac.ed.bikerental;

import java.util.Objects;

/**
 * Location is the class which represents a spatial location described by:
 * <ul>
 *     <li>A postcode</li>
 *     <li>An address</li>
 * </ul>
 *
 * @author Michal Glinski
 * @author Siang Jun Teo
 */
public class Location {
    private String postcode;
    private String address;

    /**
     * Initialises an instance of class Location.
     *
     * @param postcode  The postcode of the object. Expects a string of
     *                  length >= 6.
     * @param address   The address of the object.
     */
    public Location(String postcode, String address) {
        assert postcode.length() >= 6;
        this.postcode = postcode;
        this.address = address;
    }


    /**
     * Tests whether the location is near another {@link Location} passed as a parameter.
     *
     * @param other an object {@link Location}
     * @return <code>true</code> if the location shares the same first two characters,
     *         <code>false</code> otherwise.
     */
    public boolean isNearTo(Location other) {
        if (Objects.isNull(other)) throw new NullPointerException();
        String otherPostcode = other.getPostcode();
        if (this.postcode.charAt(0) == otherPostcode.charAt(0)) {
            if (this.postcode.charAt(1) == otherPostcode.charAt(1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the postcode of the location
     */
    public String getPostcode() {
        return postcode;
    }


    /**
     * @return the address of the location
     */
    public String getAddress() {
        return address;
    }

    // You can add your own methods here
}
