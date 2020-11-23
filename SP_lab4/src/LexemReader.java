import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexemReader {
    private StringReader stringReader;
    private List<Lexem> lexemList;
    private String[] stringList;
    private char[] buffer;
    private int forward;
    private int lexemBegin;

    private Pattern DELIM;
    private Pattern OPERATORS;
    private Pattern NUMERIC;
    private Pattern IDENTIFIER;
    private Pattern STRING;
    private Pattern COMMENT;
    private Pattern current;

    LexemReader() {
        this.forward = 1;
        this.lexemBegin = 0;
        this.lexemList = new ArrayList<>();
        this.DELIM = Pattern.compile("(\\(|\\)|\\[|\\]|\\{|\\}|\\,|\\:|\\;|\\.)");
        this.OPERATORS = Pattern.compile("(\\\\+|&|<-|&&|%%|<<-|%\\/%|==|!=|-|\\\\|\\|\\||\\\\|\\/|<|<=|\\*|\\^|\\+|>|>=|<<=|=|->>)");
        this.COMMENT = Pattern.compile("#.*");
        this.IDENTIFIER = Pattern.compile("[.A-Za-z][_a-zA-Z0-9.]*");
        this.NUMERIC = Pattern.compile("[0-9]+(.[0-9]+)?(e[+-]?[0-9]+)?");
        this.STRING = Pattern.compile("([\\\"].*[\\\"])|([\\'].*[\\'])");
    }

    public void readLexem(String line) throws IOException {
        int len = 1;
        this.forward = 1;
        this.lexemBegin = 0;
        int position = 0;
        this.buffer = new char[line.length()];
        this.stringReader = new StringReader(line);

        current = COMMENT;
        int line_end = line.length();
        while (forward != line.length()) {
            char c = (char) getNext(position, len);
            if (String.valueOf(c).matches("\\s+")) {
                lexemBegin++;
                forward++;
                position++;
                continue;
            }
            len = 1;
            if (COMMENT.equals(current)) {
                char[] temp = Arrays.copyOfRange(buffer, lexemBegin, forward);
                Matcher matcher = current.matcher(new String(temp));
                if (!matcher.matches()) {
                    if (lexemBegin == position) {
                        len = 0;
                        current = IDENTIFIER;
                    } else {
                        addLexemeFromBuffer("INLINE COMMENT");
                        lexemBegin = forward;
                        current = IDENTIFIER;
                        forward++;
                        position++;
                    }
                } else {
                    position++;
                    forward++;
                    if (forward == line_end) {
                        addLexemeFromBuffer("Comment");
                    }
                }
            } else if (IDENTIFIER.equals(current)) {
                char[] temp = Arrays.copyOfRange(buffer, lexemBegin, forward);
                Matcher matcher = current.matcher(new String(temp));
                if (!matcher.matches()) {
                    if (lexemBegin == position) {
                        current = DELIM;
                    } else {
                        if (Character.isSpaceChar(c)) {
                            addLexemeFromBuffer("Identifier");
                        } else if (c == '\"' || c == '\'') {
                            System.out.println("Unexpected token" + temp[temp.length - 1]);
                            lexemBegin = forward;
                            current = STRING;
                            forward++;
                            position++;
                            len = 0;
                        } else if (c == '#') {
                            current = COMMENT;
                            addLexemeFromBuffer("Identifier");
                        } else if (String.valueOf(temp[temp.length - 1]).matches("(\\(|\\)|\\[|\\]|\\{|\\}|\\,|\\:|\\;|\\.)")) {
                            addLexemeFromBuffer("Identifier");
                            lexemBegin = forward - 1;
                            len = 0;
                            current = DELIM;
                        } else if (String.valueOf(temp[temp.length - 1]).matches("(\\+|<|&|%|=|!|-|\\\\|\\|\\||\\\\|\\/|<|<=|\\*|\\^|\\+|>|<<=|=|->>)")) {
                            addLexemeFromBuffer("Identifier");
                            lexemBegin = forward;
                            forward++;
                            position++;
                            len = 0;
                            current = OPERATORS;
                        } else {
                            addLexemeFromBuffer("Identifier");
                            System.out.println("Unexpected token" + temp[temp.length - 1]);
                            lexemBegin = forward;
                            current = IDENTIFIER;
                            forward += 2;
                            position++;
                        }
                    }
                } else {
                    position++;
                    ++forward;
                    if (forward == line_end) {
                        addLexemeFromBuffer("Identifier");
                    }
                }
            } else if (DELIM.equals(current)) {
                char[] temp = Arrays.copyOfRange(buffer, lexemBegin, forward);
                Matcher matcher = current.matcher(new String(temp));
                if (!matcher.matches()) {
                    if (lexemBegin == position) {
                        current = NUMERIC;
                    } else {
                        if (Character.isSpaceChar(c)) {
                            addLexemeFromBuffer("Delimiter");
                        } else if (temp[temp.length - 1] == '\"' || temp[temp.length - 1] == '\'') {
                            addLexemeFromBuffer("Delimiter");
                            lexemBegin = forward - 1;
                            current = STRING;
                            len = 0;
                        } else if (String.valueOf(temp[temp.length - 1]).matches("(\\+|<|&|%|=|!|-|\\\\|\\|\\||\\\\|\\/|<|<=|\\*|\\^|\\+|>|<<=|=|->>)")) {
                            addLexemeFromBuffer("Delimiter");
                            lexemBegin = forward;
                            forward += 2;
                            position++;
                            len = 0;
                            current = OPERATORS;
                        } else if (Character.isLetter(temp[temp.length - 1])) {
                            addLexemeFromBuffer("Delimiter");
                            lexemBegin = forward;
                            forward += 2;
                            position++;
                            len = 0;
                            current = IDENTIFIER;
                        } else {
                            addLexemeFromBuffer("Delimiter");
                            System.out.println("Unexpected token" + temp[temp.length - 1]);
                            lexemBegin = forward;
                            current = IDENTIFIER;
                            forward++;
                            position++;
                            len = 0;
                        }
                    }
                } else {
                    addLexemeFromBuffer("Delimiter");
                    lexemBegin = forward;
                    position++;
                    ++forward;
                    if (forward == line_end) {
                        addLexemeFromBuffer("Delimiter");
                    }
                }
            } else if (NUMERIC.equals(current)) {
                char[] temp = Arrays.copyOfRange(buffer, lexemBegin, forward);
                Matcher matcher = current.matcher(new String(temp));
                if (!matcher.matches()) {
                    if (lexemBegin == position) {
                        current = STRING;
                    } else {
                        if (Character.isSpaceChar(temp[temp.length - 1])) {
                            addLexemeFromBuffer("Numeric");
                        } else if (c == '\"' || c == '\'') {
                            System.out.println("Unexpected token" + temp[temp.length - 1]);
                            lexemBegin = forward;
                            current = STRING;
                            forward++;
                            position++;
                            len = 0;
                        } else if (String.valueOf(temp[temp.length - 1]).matches("(\\+|<|&|%|=|!|-|\\\\|\\|\\||\\\\|\\/|<|<=|\\*|\\^|\\+|>|<<=|=|->>)")) {
                            addLexemeFromBuffer("Numeric");
                            lexemBegin = forward;
                            forward++;
                            position++;
                            len = 0;
                            current = OPERATORS;
                        } else if (Character.isLetter(temp[temp.length - 1])) {
                            System.out.println("Unexpected token" + temp[temp.length - 1]);
                            addLexemeFromBuffer("Numeric");
                            lexemBegin = forward;
                            forward++;
                            position++;
                            len = 0;
                            current = IDENTIFIER;
                        } else {
                            addLexemeFromBuffer("Numeric");
                            System.out.println("Unexpected token" + temp[temp.length - 1]);
                            lexemBegin = forward;
                            current = IDENTIFIER;
                            forward++;
                            position++;
                            len = 0;
                        }
                    }
                } else {
                    position++;
                    forward++;
                    if (forward == line_end) {
                        addLexemeFromBuffer("Numeric");
                    }
                }
            } else if (current.equals(STRING)) {
                char[] temp = Arrays.copyOfRange(buffer, lexemBegin, forward);
                Matcher matcher = current.matcher(new String(temp));

                while (!matcher.matches()) {
                    forward++;
                    position++;
                    c = getNext(position, len);
                    if (forward == line_end) {
                        System.out.println("No token" + '\"');
                    }
                    temp = Arrays.copyOfRange(buffer, lexemBegin, forward);
                    matcher = current.matcher(new String(temp));
                }
                addLexemeFromBuffer("Character");
                current = IDENTIFIER;
                lexemBegin = forward;
                forward++;
                position++;
                len = 0;

            } else if (OPERATORS.equals(current)) {
                char[] temp = Arrays.copyOfRange(buffer, lexemBegin, forward);
                Matcher matcher = current.matcher(new String(temp));
                if (!matcher.find()) {
                    if (lexemBegin == position) {
                        current = IDENTIFIER;
                    } else {
                        addLexemeFromBuffer("Operator");
                        lexemBegin = forward;
                        len = 0;
                        current = IDENTIFIER;
                    }
                } else {
                    position++;
                    forward++;
                    if (forward == line_end) {
                        addLexemeFromBuffer("Character");
                    }
                }
            }


        }

    }

    private char getNext(int position, int len) throws IOException {
        return (char) stringReader.read(buffer, position, len);
    }

    private void addLexemeFromBuffer(String id) {
        char[] temp = Arrays.copyOfRange(buffer, lexemBegin, forward - 1);
        Lexem l = new Lexem(id, temp);
        lexemList.add(l);
    }

    public void getLexemes() {
        for (Lexem lex : lexemList) {
            lex.getLexem();
        }
    }
}
