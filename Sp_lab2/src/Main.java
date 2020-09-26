import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static boolean checkContainsName(final Set<State> states, final Character num) {
        return states.stream().anyMatch(o -> o.getNum().equals(num));
    }

    private static State stateContainsName(final Set<State> states, final Character num) {
        return states.stream().filter(o -> o.getNum().equals(num)).findAny().orElseThrow(() -> new IllegalArgumentException("Actually there is no state like this: " + num));
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Goodbye cruel world ...");
        Scanner in = new Scanner(System.in);
        System.out.println("Input the length of a word: ");
        int length = in.nextInt();

        Path path = Paths.get("data/input.txt");
        Scanner scanner = new Scanner(path);
        System.out.println("Reading a text file");
        Set<State> states = new HashSet<>();
        Set<Character> rules = new HashSet<>();

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();
            char[] connection = line.replaceAll("\\s+", "").toCharArray();

            Map<String, Character> connections = new HashMap<>();
            connections.put("State1", connection[0]);
            connections.put("State2", connection[2]);
            connections.put("Transition rule", connection[1]);
            rules.add(connections.get("Transition rule"));

            if (!checkContainsName(states, connections.get("State1"))) {
                State state = new DetState(false, connections.get("State1"));
                states.add(state);
                if (!checkContainsName(states, connections.get("State2"))) {
                    State connector = new DetState(false, connections.get("State2"));
                    DetTransition tr = new DetTransition(Character.toString(connections.get("Transition rule")), connector);
                    states.add(connector);
                    state.with((Transition) tr);
                } else {
                    DetTransition tr = new DetTransition(Character.toString(connections.get("Transition rule")), stateContainsName(states, connections.get("State2")));
                    state.with((Transition) tr);
                }
            } else {
                if (!checkContainsName(states, connections.get("State2"))) {
                    State connector = new DetState(false, connections.get("State2"));
                    DetTransition tr = new DetTransition(Character.toString(connections.get("Transition rule")), connector);
                    states.add(connector);
                    State state = stateContainsName(states, connections.get("State1"));
                    state.with((Transition) tr);
                } else {
                    DetTransition tr = new DetTransition(Character.toString(connections.get("Transition rule")), stateContainsName(states, connection[2]));
                    State state = stateContainsName(states, connections.get("State1"));
                    state.with((Transition) tr);
                }
            }

        }


    }
}
