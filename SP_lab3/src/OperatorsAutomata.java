public class OperatorsAutomata implements Automata {
    protected State initial;

    public OperatorsAutomata(State initial) {
        this.initial = initial;
    }

    public Automata switchState(Character c) {
        return new OperatorsAutomata(this.initial.transit(c));
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
        State s2 = new DetState(true, "2");
        State s3 = new DetState(true, "3");
        State s4 = new DetState(true, "4");
        State s5 = new DetState(true, "5");
        State s6 = new DetState(true, "6");
        State s7 = new DetState(true, "7");
        State s8 = new DetState(true, "8");
        State s9 = new DetState(true, "9");
        State s10 = new DetState(true, "10");
        State s11 = new DetState(true, "11");
        State s12 = new DetState(true, "12");
        State s13 = new DetState(false, "13");
        State s14 = new DetState(true, "14");
        State s31 = new DetState(true, "31");
        State s32 = new DetState(true, "32");
        State s33 = new DetState(true, "33");
        State s34 = new DetState(true, "34");

        State s35 = new DetState(true, "35");
        State s36 = new DetState(true, "36");

        Transition t35 = new DetTransition("=", s35);
        Transition t36 = new DetTransition("==", s36);

        initial.with(t35);
        s35.with(t36);

        Transition t31 = new DetTransition("{", s31);
        Transition t32 = new DetTransition("}", s32);
        Transition t33 = new DetTransition("(", s33);
        Transition t34 = new DetTransition(")", s34);

        initial.with(t31);
        initial.with(t32);
        initial.with(t33);
        initial.with(t34);

        Transition t1 = new DetTransition("^", s1);
        Transition t2 = new DetTransition("-", s2);
        Transition t3 = new DetTransition("+", s3);
        Transition t4 = new DetTransition("<", s4);
        Transition t5 = new DetTransition(">", s5);
        Transition t6 = new DetTransition("!", s6);
        Transition t7 = new DetTransition("|", s7);
        Transition t8 = new DetTransition("&", s8);
        Transition t9 = new DetTransition(":", s9);
        Transition t10 = new DetTransition("$", s10);
        Transition t11 = new DetTransition("/", s11);
        Transition t12 = new DetTransition("~", s12);
        Transition t13 = new DetTransition("%", s13);
        Transition t14 = new DetTransition("?", s14);

        initial.with(t1);
        initial.with(t2);
        initial.with(t3);
        initial.with(t4);
        initial.with(t5);
        initial.with(t6);
        initial.with(t7);
        initial.with(t8);
        initial.with(t9);
        initial.with(t10);
        initial.with(t11);
        initial.with(t12);
        initial.with(t13);
        initial.with(t14);

        State s15 = new DetState(true, "15");
        State s16 = new DetState(true, "16");
        State s17 = new DetState(true, "17");
        State s18 = new DetState(true, "18");
        State s19 = new DetState(false, "19");
        State s20 = new DetState(true, "20");
        State s21 = new DetState(true, "21");
        State s22 = new DetState(false, "22");
        State s23 = new DetState(true, "23");
        State s24 = new DetState(false, "24");
        State s25 = new DetState(false, "25");

        Transition t15 = new DetTransition(">", s15);
        Transition t16 = new DetTransition("-", s16);
        Transition t17 = new DetTransition("=", s17);
        Transition t18 = new DetTransition("=", s18);
        Transition t19 = new DetTransition("*", s19);
        Transition t20 = new DetTransition("&", s20);
        Transition t21 = new DetTransition("|", s21);
        Transition t22 = new DetTransition("x", s22);
        Transition t23 = new DetTransition("%", s23);
        Transition t24 = new DetTransition("i", s24);
        Transition t25 = new DetTransition("/", s25);

        s2.with(t15);
        s4.with(t16);
        s4.with(t17);
        s5.with(t18);
        s8.with(t20);
        s7.with(t21);
        s13.with(t22);
        s13.with(t23);
        s13.with(t24);
        s13.with(t19);
        s13.with(t25);

        State s26 = new DetState(true, "26");
        State s27 = new DetState(false, "27");
        State s28 = new DetState(true, "28");
        State s29 = new DetState(true, "29");

        Transition t26 = new DetTransition("%", s26);
        Transition t27 = new DetTransition("n", s27);
        Transition t28 = new DetTransition("%", s28);
        Transition t29 = new DetTransition("%", s29);

        s22.with(t26);
        s24.with(t27);
        s13.with(t24);
        s19.with(t28);
        s25.with(t29);

        State s30 = new DetState(true, "30");

        Transition t30 = new DetTransition("%", s30);

        s27.with(t30);

    }
}
