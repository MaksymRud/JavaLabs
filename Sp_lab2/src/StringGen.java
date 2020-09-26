import java.util.List;
import java.util.Set;

public class StringGen {
    static void wordsKLength(Set<Character> set, List<String> words, int k) {
        int n = set.size();
        recGen(set, words, "", n, k);
    }

    private static void recGen(Set<Character> set, List<String> words, String prefix, int n, int k) {

        if (k == 0) {
            words.add(prefix);
            return;
        }

        for (Character c : set) {
            String newPrefix = prefix + c;
            recGen(set, words, newPrefix,
                    n, k - 1);
        }
    }
}
