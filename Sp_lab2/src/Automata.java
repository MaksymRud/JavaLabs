public interface Automata {
    Automata switchState(final CharSequence c);

    Character getCurrent();

    boolean canStop();
}

interface State {
    State with(final Transition tr);

    State transit(final CharSequence c);

    boolean isFinal();

    Object getNum();

    void setFinal();
}

interface Transition {
    boolean isPossible(final CharSequence c);

    State state();
}
