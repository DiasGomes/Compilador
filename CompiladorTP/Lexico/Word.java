package CompiladorTP.Lexico;

public class Word extends Token {
    
    private String lexeme = "";
    public static final Word and = new Word("&&", Tag.AND);
    public static final Word or = new Word("||", Tag.OR);
    public static final Word eq = new Word("==", Tag.EQ);
    public static final Word ne = new Word("!=", Tag.NE);
    public static final Word le = new Word("<=", Tag.LE);
    public static final Word ge = new Word(">=", Tag.GE);

    public Word(String s, int tag) {
        super(tag);
        lexeme = s;
    }

    public String toString() {
        String tipo = "";
        switch(tag){
            case 279: tipo = "ID"; break;
            case 288: tipo = "STRING CONST"; break;
            case 289: tipo = "INT"; break;
            case 290: tipo = "FLOAT"; break;
            case 291: tipo = "STRING"; break;
            case 292: tipo = "CLASS"; break;
            case 293: tipo = "IF"; break;
            case 294: tipo = "ELSE"; break;
            case 295: tipo = "READ"; break;
            case 296: tipo = "WRITE"; break;
            case 297: tipo = "WHILE"; break;
            case 298: tipo = "DO"; break;
        }
        return "<" + lexeme + "," + tipo +">";
    }

    public String getLexeme(){
        return lexeme;
    }

}