import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class Main {
    public List<String> strSort(List<String> originList){
        if(originList == null) {
            throw new NullPointerException("origin is null");
        }
        else{
            List<String> clone = new ArrayList<>(List.copyOf(originList));
            clone.sort(new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    if(s1 == null || s2 == null)
                        throw new NullPointerException("string is null");
                    final int compare = Integer.compare(s1.length(), s2.length());
                    if (compare == 0) {
                        return s1.compareTo(s2);
                    } else if (compare > 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
            return clone;
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("aaa", "bbb", "ccc", "aa", "zz", "avbg", "s"));
        Main m = new Main();
        List<String> cl = m.strSort(list);
        for(String s : cl){
            System.out.println(s);
        }
    }
}
