public interface Automata {
    Automata switchState(final Character c);

    Character getCurrent();

    boolean canStop();

    void createAutomat();
}

interface State {
    State with(final Transition tr);

    State transit(final Character c);

    boolean isFinal();

    Object getNum();

    void setFinal();
}

interface Transition {
    boolean isPossible(final Character c);

    State state();
}
