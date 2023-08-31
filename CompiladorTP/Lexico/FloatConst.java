package CompiladorTP.Lexico;

public class FloatConst extends Token{

    public final float value;

    public FloatConst(int value) {
        super(Tag.FLOAT_CONST);
        this.value = value;
    }

    public String toString() {
        return "" + value;
    }
    
}

