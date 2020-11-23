class IndentifierAutomata implements Automata {
    protected State initial;
    private char[] LETTERS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
    private char[] NUMBERS_SIGNS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '_'};

    public IndentifierAutomata(State initial) {
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
        Transition t1 = new DetTransition(".", s2);
        Transition t2 = new DetTransition(String.copyValueOf(LETTERS), s3);
        initial.with(t1);
        initial.with(t2);
        StringBuilder sb = new StringBuilder(64);
        sb.append(LETTERS);
        sb.append(NUMBERS_SIGNS);

        char[] res_trans_rules = sb.toString().toCharArray();

        Transition t3 = new DetTransition(String.copyValueOf(res_trans_rules), s3);
        Transition t4 = new DetTransition(String.copyValueOf(res_trans_rules), s2);

        s2.with(t4);
        s3.with(t3);
    }

}
