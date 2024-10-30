package collation;

import java.util.Arrays;
import java.util.Objects;

/**
 * A CollationKey represents a String transformed into a series of bytes that can
 * be compared byte by byte to determine the relative collation order of the
 * original strings.
 */
public final class CollationKey implements Comparable<CollationKey> {
    private final String originalString;
    private final byte[] collationKey;
    
    /**
     * Creates a new CollationKey.
     *
     * @param originalString the original string from which this key was created
     * @param collationKey the collation key bytes
     * @throws NullPointerException if either parameter is null
     */
    CollationKey(String originalString, byte[] collationKey) {
        this.originalString = Objects.requireNonNull(originalString);
        this.collationKey = Objects.requireNonNull(collationKey).clone();
    }
    
    /**
     * Returns the original string from which this key was created.
     *
     * @return the original string
     */
    public String getOriginalString() {
        return originalString;
    }
    
    /**
     * Compares this CollationKey to another CollationKey.
     *
     * @param other the CollationKey to compare with
     * @return negative if this key is less than other key, zero if equal, positive if greater than
     */
    @Override
    public int compareTo(CollationKey other) {
        return Arrays.compareUnsigned(this.collationKey, other.collationKey);
    }
    
     /**
     * Compares this CollationKey with another object. Two CollationKeys are considered
     * equal if they have the same collation bytes, meaning they represent the same relative 
     * position in the collation sequence.
     *
     * @param obj the object to compare with
     * @return true if obj is a CollationKey with the same collation bytes as this one
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CollationKey)) {
            return false;
        }
        CollationKey other = (CollationKey) obj;
        return Arrays.equals(collationKey, other.collationKey);
    }
    
    /**
     * Returns a hash code value for this CollationKey. The hash code is based on the
     * collation bytes rather than the original string, so two keys that are equal according
     * to equals() will have the same hash code.
     *
     * @return a hash code value for this CollationKey
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(collationKey);
    }
    
    /**
     * Returns a string representation of this CollationKey, showing the original string 
     * from which it was created.
     *
     * @return a string in the format "CollationKey[string=originalString]"
     */
    @Override
    public String toString() {
        return "CollationKey[string=" + originalString + "]";
    }
}