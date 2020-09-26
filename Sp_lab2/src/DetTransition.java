public class DetTransition implements Transition {
    private String rule;
    private State next;


    public DetTransition(String rule, State next) {
        this.rule = rule;
        this.next = next;
    }

    public State state() {
        return this.next;
    }

    public boolean isPossible(CharSequence c) {
        return this.rule.equalsIgnoreCase(String.valueOf(c));
    }
}
