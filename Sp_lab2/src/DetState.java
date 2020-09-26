import java.util.ArrayList;
import java.util.List;

public class DetState implements State {
    private List<Transition> transitions;
    private boolean isFinal;
    private Character num;

    public DetState() {
        this(false, '0');
    }

    public DetState(boolean isFinal, Character num) {
        this.transitions = new ArrayList<>();
        this.isFinal = isFinal;
        this.num = num;
    }

    public Character getNum() {
        return this.num;
    }

    public boolean isFinal() {
        return this.isFinal;
    }

    public void setFinal() {
        this.isFinal = true;
    }

    public State transit(CharSequence c) {
        return transitions
                .stream()
                .filter(t -> t.isPossible(c))
                .map(Transition::state)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Input not accepted: " + c));
    }

    @Override
    public State with(Transition tr) {
        this.transitions.add(tr);
        return this;
    }

}
