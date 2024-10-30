/**
 * Provides classes for locale-sensitive string collation.
 *
 * <p>This package provides a API for comparing strings according to
 * language-specific sorting rules. It supports both direct string comparison and
 * the creation of reusable collation keys for optimizing multiple comparisons of
 * the same strings.
 *
 * <p>This implementation is a Java translation of the GNU C Library Collation Functions:
 * <a href="https://www.gnu.org/software/libc/manual/html_node/Collation-Functions.html">
 * https://www.gnu.org/software/libc/manual/html_node/Collation-Functions.html</a>
 *
 * <p>The main classes in this package are:
 * <ul>
 *   <li>{@link collation.Collation} - The main utility class providing collation operations
 *   <li>{@link collation.CollationKey} - An immutable key representing a string's position in
 *       a locale's collation sequence
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 * // Simple comparison
 * if (Collation.compare("résumé", "resume", Locale.FRENCH) == 0) {
 *     System.out.println("Strings are equal in French collation");
 * }
 * 
 * // Sorting with CollationKeys
 * String[] words = {"côte", "coté", "cote"};
 * CollationKey[] keys = Arrays.stream(words)
 *     .map(s -> Collation.createKey(s, Locale.FRENCH))
 *     .toArray(CollationKey[]::new);
 * Arrays.sort(keys);
 * }</pre>
 *
 * @author Dongzhi Zhang (dongzhiz), Sen Feng (senf)
 * @since 1.0
 */
package collation;