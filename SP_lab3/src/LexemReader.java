import java.util.ArrayList;
import java.util.List;

public class LexemReader {
    private List<String> tokenReservedWords;
    private List<String> tokenOperators;
    private List<Character> tokenWS;
    private List<Character> tokenWSDLM;
    private List<Character> tokenSingleOper;
    private String buffer = "";
    private States state;

    private enum States {S, NUM, DLM, CH, FIN, ID, ER, ASGN, COM}

    LexemReader() {
        this.state = States.S;
        this.tokenReservedWords = new ArrayList<>(List.of("if", "else", "repeat", "while",
                "function", "for", "in", "next", "break", "TRUE", "FALSE", "NULL",
                "Inf", "NaN", "NA", "NA_integer_", "NA_real_", "NA_complex_", "NA_character_"));
        this.tokenOperators = new ArrayList<>(List.of("-", "+", "!", "~", "?", ":", "*", "/", "^", "%x%", "%%", "%/%", "=",
                "%*%", "%in%", "<", ">", "==", ">=", "<=", "&", "&&", "|", "||", "<-", "->", "$", "{", "}", "[", "]"));
        this.tokenSingleOper = new ArrayList<>(List.of('-', '+', '!', '~', '?', ':', '*', '/', '^', '=',
                '<', '>', '&', '|', '$', '{', '}', '[', ']', '(', ')'));
        this.tokenWS = new ArrayList<>(List.of(' ', '\n', '\t', '\0', '\r', ','));
        this.tokenWSDLM = new ArrayList<>(List.of('\n', '\t', '\0', '\r'));
    }

    public void ReadBuffer(String text) {
        int forward = 0;
        int lexemeBegin = 0;
        char ch = text.charAt(forward);
        try {
            while (!buffer.equals("eof")) {
                ch = text.charAt(forward);
                if (tokenWS.contains(ch)) {
                    forward++;
                    ch = text.charAt(forward);
                } else if (Character.isLetter(ch) || ch == '.') {
                    State init = new DetState();
                    Automata idAutomata = new IndentifierAutomata(init);
                    idAutomata.createAutomat();
                    while (true) {
                        if (tokenReservedWords.contains(buffer)) {
                            getResWordLexem(buffer);
                        }
                        if (tokenWS.contains(ch)) {
                            lexemeBegin = forward;
                            getIdLexem(buffer);
                            clearBuffer();
                            forward++;
                            break;
                        }
                        try {
                            ch = text.charAt(forward);
                            idAutomata = idAutomata.switchState(ch);
                            addToBuffer(ch);
                            forward++;
                        } catch (IllegalArgumentException e) {
                            if (tokenSingleOper.contains(ch)) {
                                getIdLexem(buffer);
                                getOperatorLexem(Character.toString(ch));
                            } else if (tokenWS.contains(ch)) {
                                getIdLexem(buffer);
                            } else {
                                if (tokenWS.contains(ch)) {
                                    forward++;
                                } else {
                                    System.err.println("Unexpected token " + ch);
                                }
                                getIdLexem(buffer);
                            }
                            clearBuffer();
                            break;
                        }
                    }
                    getIdLexem(buffer);
                    clearBuffer();
                    lexemeBegin = forward;
                    forward++;
                    ch = text.charAt(forward);
                } else if (Character.isDigit(ch)) {
                    State init = new DetState();
                    Automata numberAutomata = new NumberAutomata(init);
                    numberAutomata.createAutomat();
                    while (true) {
                        if (tokenWS.contains(ch)) {

                            getNumLexem(buffer);
                            clearBuffer();
                            forward++;
                            lexemeBegin = forward;
                            break;

                        }
                        try {
                            ch = text.charAt(forward);
                            numberAutomata = numberAutomata.switchState(ch);
                            addToBuffer(ch);
                            forward++;
                        } catch (IllegalArgumentException e) {
                            if (tokenWS.contains(ch) || tokenSingleOper.contains(ch)) {
                                getNumLexem(buffer);
                            } else {
                                System.err.println("Unexpected token " + ch);
                                getNumLexem(buffer);
                            }
                            clearBuffer();
                            break;
                        }
                    }
                    getNumLexem(buffer);
                    clearBuffer();
                    forward++;
                    lexemeBegin = forward;
                    ch = text.charAt(forward);
                } else if (ch == '"' || ch == '\'') {
                    char ticket = ch;
                    addToBuffer(ch);
                    forward++;
                    ch = text.charAt(forward);
                    while (true) {
                        if (Character.isDefined(ch) && ch != ticket) {
                            addToBuffer(ch);
                            forward++;
                            ch = text.charAt(forward);
                        } else {
                            if (ch == ticket) {
                                addToBuffer(ch);
                                getCharLexem(buffer);
                                clearBuffer();
                                forward++;
                                lexemeBegin = forward;
                                break;
                            } else if (!Character.isDefined(ch)) {
                                System.err.println("Character" + ch + "is not defined in unicode");
                            }
                        }
                    }
                } else if (ch == '#') {
                    while (!tokenWSDLM.contains(ch)) {
                        addToBuffer(ch);
                        forward++;
                        ch = text.charAt(forward);
                    }
                    System.out.println("<" + buffer + ">" + "-" + "<oneLineComment>");
                    clearBuffer();
                } else {
                    ch = text.charAt(forward);
                    State init = new DetState();
                    Automata operatorsAutomata = new OperatorsAutomata(init);
                    operatorsAutomata.createAutomat();
                    if (Character.isDefined(ch)) {
                        while (true) {
                            if (tokenWS.contains(ch)) {
                                lexemeBegin = forward;
                                getIdLexem(buffer);
                                clearBuffer();
                                break;
                            }
                            try {
                                ch = text.charAt(forward);
                                operatorsAutomata = operatorsAutomata.switchState(ch);
                                addToBuffer(ch);
                                forward++;
                            } catch (IllegalArgumentException e) {
                                getOperatorLexem(buffer);
                                clearBuffer();
                                break;
                            }
                        }
                        if (tokenReservedWords.contains(buffer)) {
                            getResWordLexem(buffer);
                        }
                        getOperatorLexem(buffer);
                        clearBuffer();
                        lexemeBegin = forward;
                        forward++;
                        ch = text.charAt(forward);
                    } else {
                        System.err.println("Character" + ch + "is not defined if unicode");
                    }

                }


            }
        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Text Ended");
        }
    }


    private void addToBuffer(Character c) {
        buffer += c;
    }

    private void clearBuffer() {
        buffer = "";
    }

    private void getIdLexem(String buffer) {
        if (!buffer.isEmpty()) {
            if (buffer.equals("eof")) {
                System.out.println("<eof> - <endfile>");
            } else {
                System.out.println("<" + buffer + ">" + "-" + "<identifier>");
            }
        }
    }

    private void getResWordLexem(String buffer) {
        if (!buffer.isEmpty()) {
            if (buffer.equals("eof")) {
                System.out.println("<eof> - <endfile>");
            } else {
                System.out.println("<" + buffer + ">" + "-" + "<reserved word>");
            }
        }
    }

    private void getNumLexem(String buffer) {
        if (!buffer.isEmpty()) {
            if (buffer.equals("eof")) {
                System.out.println("<eof> - <endfile>");
            } else {
                System.out.println("<" + buffer + ">" + "-" + "<number>");
            }
        }
    }

    private void getCharLexem(String buffer) {
        if (!buffer.isEmpty()) {
            if (buffer.equals("eof")) {
                System.out.println("<eof> - <endfile>");
            } else {
                System.out.println("<" + buffer + ">" + "-" + "<character>");
            }
        }
    }

    private void getOperatorLexem(String buffer) {
        if (!buffer.isEmpty()) {
            if (buffer.equals("eof")) {
                System.out.println("<eof> - <endfile>");
            } else {
                System.out.println("<" + buffer + ">" + "-" + "<operator>");
            }
        }
    }
}
