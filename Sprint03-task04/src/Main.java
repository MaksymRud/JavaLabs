import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Main {
    public Map<String, List<String>> createNotebook(Map<String, String> phones) {
        Map<String, List<String>> notebook = new HashMap<String, List<String>>();
        for(Map.Entry<String, String> entry : phones.entrySet()){
            for(String number : phones.keySet()){
                if (entry.getKey().equals(number)){
                    if(!(notebook.containsKey(entry.getValue()))){
                        List<String> phone_list = new ArrayList<String>();
                        phone_list.add(number);
                        notebook.put(entry.getValue(), phone_list);
                    }
                    else{
                        List<String> val = notebook.get(entry.getValue());
                        if(!val.contains(number)) {
                            val.add(number);
                            notebook.put(entry.getValue(), val);
                        }
                    }
                }
            }
        }
        return notebook;
    }

    public static void main(String[] args) {
	// write your cod e here
        HashMap<String, String> hash_map = new HashMap<String, String>();

        // Mapping string values to int keys
        hash_map.put("0967654321", "Petro");
        hash_map.put("0677654321", "Petro");
        hash_map.put("0501234567", "Ivan");
        hash_map.put("0970011223", "Stepan");
        hash_map.put("0631234567", "Ivan");

        // Displaying the HashMap
        System.out.println("Initial Mappings are: " + hash_map);
        Main m = new Main();
        // Using entrySet() to get the set view
        System.out.println("The set is: " + m.createNotebook(hash_map));
    }
}


