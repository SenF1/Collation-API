import collation.Collation;
import collation.CollationKey;
import java.util.Arrays;
import java.util.Locale;

public class CollationSample {
    public static void main(String[] args) {
        // Simple comparison
        String s1 = "résumé";
        String s2 = "resume";
        
        // Default Java string comparison
        int defaultCompare = s1.compareTo(s2);
        System.out.println("Default Java comparison:");
        System.out.println("Comparing '" + s1 + "' with '" + s2 + "': " + defaultCompare);
        
        int collationCompare = Collation.compare(s1, s2, Locale.FRENCH);
        System.out.println("French collation comparison:");
        System.out.println("Comparing '" + s1 + "' with '" + s2 + "': " + collationCompare);
        
        System.out.println();

        // Sorting strings using CollationKeys
        String[] words = {"cote", "côte", "coast", "coté", "côté"};
        System.out.println("Original array: " + Arrays.toString(words));
        
        // Show regular Java string sorting (based on character codes)
        String[] defaultSort = words.clone();
        Arrays.sort(defaultSort);
        System.out.println("Default Java sort: " + Arrays.toString(defaultSort));
        
        // Show sorting using our Collation API (French rules)
        CollationKey[] keys = new CollationKey[words.length];
        for (int i = 0; i < words.length; i++) {
            keys[i] = Collation.createKey(words[i], Locale.FRENCH);
        }
        Arrays.sort(keys);
        
        String[] collationSort = new String[words.length];
        for (int i = 0; i < keys.length; i++) {
            collationSort[i] = keys[i].getOriginalString();
        }
        System.out.println("French collation sort: " + Arrays.toString(collationSort));
    }
}