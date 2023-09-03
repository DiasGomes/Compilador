package CompiladorTP.Lexico;

public class FloatConst extends Token{

    public final double value;

    public FloatConst(double value) {
        super(Tag.FLOAT_CONST);
        this.value = value;
    }

    public String toString() {
        return "<" + value + ", CONSTANTE FLOAT>";
    }
    
}

