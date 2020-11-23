public class DetFiniteStateMachine implements Automata {
    private State current;

    public DetFiniteStateMachine(State initial) {
        this.current = initial;
    }

    public Automata switchState(CharSequence c) {
        return new DetFiniteStateMachine(this.current.transit(c));
    }

    public Character getCurrent() {
        return (Character) this.current.getNum();
    }

    public boolean canStop() {
        return this.current.isFinal();
    }
}


