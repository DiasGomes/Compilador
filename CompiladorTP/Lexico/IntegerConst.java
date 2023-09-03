package CompiladorTP.Lexico;

public class IntegerConst extends Token{

    public final int value;

    public IntegerConst(int value) {
        super(Tag.INTEGER_CONST);
        this.value = value;
    }

    public String toString() {
        return "<" + value + ", CONSTANTE INT>";
    }
    
}
