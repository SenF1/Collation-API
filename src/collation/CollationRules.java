package collation;

import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

/**
 * Internal class that handles the collation rules for different locales.
 * This class maintains the character mappings and normalization logic for
 * each supported locale.
 */
final class CollationRules {
    private static final Map<Locale, Map<String, String>> LOCALE_MAPPINGS = new HashMap<>();
    
    static {
        // Spanish mappings
        Map<String, String> spanishMappings = new HashMap<>();
        spanishMappings.put("á", "a");
        spanishMappings.put("é", "e");
        spanishMappings.put("í", "i");
        spanishMappings.put("ó", "o");
        spanishMappings.put("ú", "u");
        spanishMappings.put("ñ", "nz");
        spanishMappings.put("ü", "u");
        LOCALE_MAPPINGS.put(Collation.SPANISH, spanishMappings);
        
        // French mappings
        Map<String, String> frenchMappings = new HashMap<>();
        frenchMappings.put("é", "e");
        frenchMappings.put("è", "e");
        frenchMappings.put("ê", "e");
        frenchMappings.put("ë", "e");
        frenchMappings.put("à", "a");
        frenchMappings.put("â", "a");
        frenchMappings.put("ù", "u");
        frenchMappings.put("û", "u");
        frenchMappings.put("ç", "c");
        frenchMappings.put("î", "i");
        frenchMappings.put("ï", "i");
        frenchMappings.put("ô", "o");
        frenchMappings.put("œ", "oe");
        LOCALE_MAPPINGS.put(Locale.FRENCH, frenchMappings);
    }
    
    /**
     * Prevents instantiation of this utility class.
     *
     * @throws AssertionError if instantiation is attempted
     */
    private CollationRules() {
        throw new AssertionError("Non-instantiable");
    }
    
    /**
     * Determines if collation rules exist for the given locale.
     *
     * @param locale the locale to check
     * @return true if the locale has defined collation rules, false otherwise
     */
    static boolean isSupported(Locale locale) {
        return LOCALE_MAPPINGS.containsKey(locale);
    }
    
    /**
     * Normalizes a string according to the collation rules of the specified locale.
     * The normalization process converts the string to lowercase and replaces accented
     * characters with their unaccented equivalents according to locale-specific rules.
     *
     * @param str the string to normalize
     * @param locale the locale whose rules should be applied
     * @return the normalized string
     */
    static String normalize(String str, Locale locale) {
        String input = str.toLowerCase(locale);
        Map<String, String> mappings = LOCALE_MAPPINGS.get(locale);
        
        for (Map.Entry<String, String> entry : mappings.entrySet()) {
            input = input.replace(entry.getKey(), entry.getValue());
        }
        
        return input;
    }
    
    /**
     * Generates a byte array representing the collation key for a normalized string.
     * Each character in the normalized string is converted to a byte, creating a 
     * sequence that can be compared using byte-by-byte comparison to determine
     * the correct collation order.
     *
     * @param normalized the normalized string to convert
     * @return a byte array representing the string's position in the collation sequence
     */
    static byte[] generateCollationBytes(String normalized) {
        byte[] bytes = new byte[normalized.length()];
        for (int i = 0; i < normalized.length(); i++) {
            bytes[i] = (byte) normalized.charAt(i);
        }
        return bytes;
    }
}