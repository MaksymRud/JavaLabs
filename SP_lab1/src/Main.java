import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.*;

public class Main {

    private static Set<String> getWords(String fileName){
        List<String> words = new ArrayList<>();
        try {
            Path path = Paths.get(fileName);
            Scanner scanner = new Scanner(path);
            System.out.println("Reading a text file");

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                words.addAll(Arrays.asList(line.split("[^A-Za-zА-Яа-я]+")));
            }

            ListIterator<String> ite = words.listIterator();

            while(ite.hasNext()){
                String value = ite.next();
                if(value.length() > 30){
                    String temp = value.substring(0,29);
                    ite.remove();
                    ite.add(temp);
                }
            }
        }
        catch (IOException ex){
            System.out.println("Wrong path, no such file or directory");
        }
        return new HashSet<>(words);
    }

    private static List<String> getUniqueMaxCharacters(Set<String> words){
        int maxNumChar = 0;
        List<String> res_list = new ArrayList<>();
        for(String s : words){

            Set<Character> charsSet = new HashSet<>();

            for(Character c : s.toCharArray()) {
                charsSet.add(c);
            }

            int charsSetSize = charsSet.size();

            if(charsSetSize > maxNumChar) {
                maxNumChar = charsSetSize;
                res_list.clear();
                res_list.add(s);
            }
            else if(charsSetSize == maxNumChar){
                res_list.add(s);
            }

        }

        return res_list;
    }

    public static void main(String[] args) throws IOException {

            Set<String> words = getWords("test_data/test1.txt");
            List<String> words_most_unique = getUniqueMaxCharacters(words);
            for(String s : words_most_unique) {
                System.out.println(s);
            }
    }
}
