public class NumberAutomata implements Automata {
    protected State initial;

    public NumberAutomata(State initial) {
        this.initial = initial;
    }

    public Automata switchState(Character c) {
        return new NumberAutomata(this.initial.transit(c));
    }

    public Character getCurrent() {
        return (Character) this.initial.getNum();
    }

    public boolean canStop() {
        return this.initial.isFinal();
    }

    public void createAutomat() {
        initial = new DetState(false, "0");

        State s1 = new DetState(true, "1");
        State s6 = new DetState(true, "6");
        Transition t1 = new DetTransition("123456789", s1);
        Transition t2 = new DetTransition("0", s6);
        Transition t3 = new DetTransition("0123456789", s1);
        initial.with(t1);
        initial.with(t2);
        s1.with(t3);

        State s11 = new DetState(false, "11");
        State s4 = new DetState(true, "4");
        State s5 = new DetState(true, "5");
        State s2 = new DetState(true, "2");
        Transition t4 = new DetTransition("xX", s11);
        Transition t5 = new DetTransition(".", s2);
        Transition t6 = new DetTransition("L", s4);
        Transition t7 = new DetTransition(":", s5);
        s6.with(t4);
        s1.with(t5);
        s1.with(t6);
        s1.with(t7);

        State s12 = new DetState(true, "12");
        State s7 = new DetState(true, "7");
        Transition t8 = new DetTransition("123456789", s7);
        Transition t9 = new DetTransition("abcdefABCDEF1234567890", s12);
        Transition t10 = new DetTransition("1234567890", s7);
        s11.with(t9);
        s12.with(t9);
        s5.with(t8);
        s7.with(t10);
    }
}
