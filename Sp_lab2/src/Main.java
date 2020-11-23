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

        Path path = Paths.get("data/input.txt");
        Scanner scanner = new Scanner(path);
        System.out.println("Reading a text file of automata");

        String line = scanner.nextLine();
        char[] length = line.replaceAll("\\s+", "").toCharArray();
        int len = Character.getNumericValue(length[0]);

        scanner.nextLine();
        line = scanner.nextLine();

        char[] start = line.replaceAll("\\s+", "").toCharArray();

        Character input_state = start[0];


        List<Character> final_states = new ArrayList<>();

        line = scanner.nextLine();
        char[] final_stat = line.replaceAll("\\s+", "").toCharArray();
        char[] finale = Arrays.copyOfRange(final_stat, 1, final_stat.length);
        String fin = new String(finale);
        fin = fin.replaceAll("\\s+", "");
        for (Character c : fin.toCharArray()) {
            final_states.add(c);
        }

        Set<State> states = new HashSet<>();
        Set<Character> rules = new HashSet<>();

        while (scanner.hasNextLine()) {

            line = scanner.nextLine();
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
                    state.with(tr);

                } else {

                    Transition tr = new DetTransition(Character.toString(connections.get("Transition rule")), stateContainsName(states, connections.get("State2")));
                    state.with(tr);

                }
            } else {
                if (checkNotContainsName(states, connections.get("State2"))) {

                    State connector = new DetState(false, connections.get("State2"));
                    Transition tr = new DetTransition(Character.toString(connections.get("Transition rule")), connector);
                    states.add(connector);
                    State state = stateContainsName(states, connections.get("State1"));
                    state.with(tr);

                } else {

                    Transition tr = new DetTransition(Character.toString(connections.get("Transition rule")), stateContainsName(states, connections.get("State2")));
                    State state = stateContainsName(states, connections.get("State1"));
                    state.with(tr);

                }
            }

        }

        for (Character final_state : final_states) {
            State finalState = stateContainsName(states, final_state);
            finalState.setFinal();
        }


        List<String> words = new ArrayList<>();
        StringGen.wordsKLength(rules, words, len);


        for (String word : words) {
            try {
                Automata detauto = new DetFiniteStateMachine(stateContainsName(states, input_state));
                for (Character c : word.toCharArray()) {
                    System.out.print(detauto.getCurrent());
                    detauto = detauto.switchState(String.valueOf(c));
                    System.out.print("->");
                }
                if (detauto.canStop()) {
                    System.out.println("This state is FINAL!");
                } else {
                    System.out.println(" Word is not accepted");
                    System.out.println("This automata doesn't except every word of length k");
                }
            } catch (Exception e) {
                System.out.println(" Word is not accepted");
                System.out.println("This automata doesn't except every word of length k");
                break;
            }
        }
    }
}
