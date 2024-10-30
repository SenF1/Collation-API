package collation;

import java.util.Locale;
import java.util.Objects;

/**
 * A utility class that provides string collation operations following locale-specific rules.
 * Collation determines how strings should be ordered in a locale-sensitive manner,
 * accounting for language-specific conventions like accents and special character combinations.
 *
 * <p>This implementation is a Java translation of the GNU C Library Collation Functions:
 * <a href="https://www.gnu.org/software/libc/manual/html_node/Collation-Functions.html">
 * https://www.gnu.org/software/libc/manual/html_node/Collation-Functions.html</a>
 *
 * <p>This implementation supports French and Spanish locales, providing proper comparison
 * of accented characters and special character combinations according to each language's rules.
 *
 * <p>Example usage:
 * <pre>{@code
 * // Simple comparison
 * if (Collation.compare("résumé", "resume", Locale.FRENCH) == 0) {
 *     System.out.println("Strings are equal in French collation");
 * }
 * 
 * // Using CollationKeys for multiple comparisons
 * String[] words = {"café", "apple", "zebra"};
 * CollationKey[] keys = Arrays.stream(words)
 *     .map(s -> Collation.createKey(s, Locale.FRENCH))
 *     .toArray(CollationKey[]::new);
 * Arrays.sort(keys);
 * }</pre>
 *
 * @see CollationKey
 * @see java.util.Locale
 */
public final class Collation {
    /** Locale for Spanish since there's no built-in constant. */
    public static final Locale SPANISH = Locale.forLanguageTag("es");
    
    private Collation() {
        throw new AssertionError("Non-instantiable");
    }

    /**
     * Determines if the given locale is supported by this collation implementation.
     *
     * @param locale the locale to check for support
     * @return true if the locale is supported, false otherwise
     * @throws NullPointerException if locale is null
     */
    public static boolean isSupported(Locale locale) {
        Objects.requireNonNull(locale, "locale must not be null");
        return CollationRules.isSupported(locale);
    }

    /**
     * Compares two strings according to the collation rules of the specified locale.
     * The comparison is case-insensitive and accounts for locale-specific character 
     * equivalences (such as treating 'e' and 'é' as equal in French).
     *
     * @param s1 the first string to compare, must not be null
     * @param s2 the second string to compare, must not be null
     * @param locale the locale whose rules should be used for comparison, must not be null
     * @return negative if s1 is less than s2, zero if s1 equals s2, positive if s1 is greater than s2
     * @throws NullPointerException if any parameter is null
     * @throws IllegalArgumentException if the locale is not supported
     * @see #isSupported(Locale)
     */
    public static int compare(String s1, String s2, Locale locale) {
        Objects.requireNonNull(s1, "first string must not be null");
        Objects.requireNonNull(s2, "second string must not be null");
        Objects.requireNonNull(locale, "locale must not be null");
        
        CollationKey key1 = createKey(s1, locale);
        CollationKey key2 = createKey(s2, locale);
        return key1.compareTo(key2);
    }

    /**
     * Creates a CollationKey for the given string using the specified locale's rules.
     * CollationKeys can be reused for multiple comparisons, providing better performance
     * when comparing the same string multiple times.
     *
     * @param str the string for which to create a collation key, must not be null
     * @param locale the locale whose rules should be used, must not be null
     * @return a CollationKey representing the string's position in the locale's collation sequence
     * @throws NullPointerException if any parameter is null
     * @throws IllegalArgumentException if the locale is not supported
     * @see CollationKey
     * @see #isSupported(Locale)
     */
    public static CollationKey createKey(String str, Locale locale) {
        Objects.requireNonNull(str, "string must not be null");
        Objects.requireNonNull(locale, "locale must not be null");
        
        if (!isSupported(locale)) {
            throw new IllegalArgumentException("Unsupported locale: " + locale.getLanguage());
        }
        
        String normalized = CollationRules.normalize(str, locale);
        byte[] collationBytes = CollationRules.generateCollationBytes(normalized);
        
        return new CollationKey(str, collationBytes);
    }
}