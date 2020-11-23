public class StringAutomata implements Automata {
    protected State initial;

    public StringAutomata(State initial) {
        this.initial = initial;
    }

    public Automata switchState(Character c) {
        return new IndentifierAutomata(this.initial.transit(c));
    }

    public Character getCurrent() {
        return (Character) this.initial.getNum();
    }

    public boolean canStop() {
        return this.initial.isFinal();
    }

    public void createAutomat() {
        initial = new DetState(false, "0");
        State s2 = new DetState(true, "1");
        State s3 = new DetState(true, "2");
        Transition t1 = new DetTransition("\"", s2);
        Transition t2 = new DetTransition("\'", s3);
        initial.with(t1);
        initial.with(t2);
    }
}
