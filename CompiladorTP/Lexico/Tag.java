package CompiladorTP.Lexico;

public class Tag {

    public final static int
    // Operadores e pontuação
    EQ = 256,
    NE = 257,
    GE = 258,
    LE = 259,
    GT = (int) '>',
    LT = (int) '<',
    SUM = (int) '+',
    SUB = (int) '-',
    OR = 274,
    MUL = (int) '*',
    DIV = (int) '/',
    AND = 277,
    ATR = (int) '=',
    NEG = (int) '!',

    // outros tokens
    ID = 279,
    PONTO_VIRGULA = (int) ';',
    VIRGULA = (int) ',',
    ABRE_PARENTESES = (int) '(',
    FECHA_PARENTESES = (int) ')',
    ABRE_CHAVES = (int) '{',
    FECHA_CHAVES = (int) '}',
    INTEGER_CONST = 286,
    FLOAT_CONST = 287,
    STRING_CONST = 288,

    // fim de arquivo
    EOF = 65535,

    // Palavras reservadas
    INT = 289,
    FLOAT = 290,
    STRING = 291,
    CLASS = 292,
    IF = 293,
    ELSE = 294,
    READ = 295,
    WRITE = 296,
    WHILE = 297,
    DO = 298;

    public static String showTag(int t){
        switch (t){
            case EQ: return "==";
            case NE: return "!=";
            case GE: return ">=";
            case LE: return "<=";
            case OR: return "OR";
            case AND: return "AND";
            case ID: return "ID";
            case INTEGER_CONST: return "INTEGER_CONST";
            case FLOAT_CONST: return "FLOAT_CONST";
            case STRING_CONST: return "STRING_CONST";
            case INT: return "INT";
            case FLOAT: return "FLOAT";
            case STRING: return "STRING";
            case CLASS: return "CLASS";
            case IF: return "IF";
            case ELSE: return "ELSE";
            case READ: return "READ";
            case WRITE: return "WRITE";
            case WHILE: return "WHILE";
            case DO: return "DO";
            case EOF: return "EOF";
            default: return "" + (char) t;
        }
    }

}
