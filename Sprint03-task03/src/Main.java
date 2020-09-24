import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Main {

    public boolean listMapCompare(List<String> list, Map<String, String> map) {
        Set<String> set = new HashSet<String>(list);
        for(String e : map.values()){
            if(!(set.contains(e)))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
	// write your code here
    }
}
