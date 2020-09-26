import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static boolean checkNotContainsName(final Set<State> states, final Character num) {
        return states.stream().noneMatch(o -> o.getNum().equals(num));
    }

    private static State stateContainsName(final Set<State> states, final Character num) {
        return states.stream().filter(o -> o.getNum().equals(num)).findAny().orElseThrow(() -> new IllegalArgumentException("Actually there is no state like this: " + num));
    }


    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);
        System.out.println("Input the length of a word: ");
        int length = in.nextInt();

        Path path = Paths.get("data/input.txt");
        Scanner scanner = new Scanner(path);
        System.out.println("Reading a text file");

        String stats = scanner.nextLine();
        char[] num_states = stats.replaceAll("\\s+", "").toCharArray();
        Character final_state = num_states[0];

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

            if (checkNotContainsName(states, connections.get("State1"))) {

                State state = new DetState(false, connections.get("State1"));
                states.add(state);

                if (checkNotContainsName(states, connections.get("State2"))) {

                    State connector = new DetState(false, connections.get("State2"));
                    Transition tr = new DetTransition(Character.toString(connections.get("Transition rule")), connector);
                    states.add(connector);
                    state.with((Transition) tr);

                } else {

                    Transition tr = new DetTransition(Character.toString(connections.get("Transition rule")), stateContainsName(states, connections.get("State2")));
                    state.with((Transition) tr);

                }
            } else {
                if (checkNotContainsName(states, connections.get("State2"))) {

                    State connector = new DetState(false, connections.get("State2"));
                    Transition tr = new DetTransition(Character.toString(connections.get("Transition rule")), connector);
                    states.add(connector);
                    State state = stateContainsName(states, connections.get("State1"));
                    state.with((Transition) tr);

                } else {

                    Transition tr = new DetTransition(Character.toString(connections.get("Transition rule")), stateContainsName(states, connections.get("State2")));
                    State state = stateContainsName(states, connections.get("State1"));
                    state.with((Transition) tr);

                }
            }

        }

        State finalState = stateContainsName(states, final_state);
        finalState.setFinal();

        List<String> words = new ArrayList<>();
        StringGen.wordsKLength(rules, words, length);

        Automata detauto = new DetFiniteStateMachine(stateContainsName(states, '1'));

        for (String word : words) {
            for (Character c : word.toCharArray()) {
                detauto = detauto.switchState(String.valueOf(c));
            }
        }

        System.out.println();
    }
}
