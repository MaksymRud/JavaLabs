public class Lexem {
    private String id;
    private char[] val;

    Lexem(String id, char[] val) {
        this.id = id;
        this.val = val;
    }

    public void getLexem() {
        System.out.println("<" + new String(this.val) + ">" + "->" + "<" + this.id + ">");
    }
}
