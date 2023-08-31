package CompiladorTP.Lexico;

public class Tag {

    public final static int
    // Operadores e pontuação
    EQ = 256,
    NE = 257,
    GE = 258,
    LE = 259,
    GT = 260,
    LT = 271,
    SUM = 272,
    SUB = 273,
    OR = 274,
    MUL = 275,
    DIV = 276,
    AND = 277,
    ATR = 278,

    // outros tokens
    ID = 279,
    PONTO_VIRGULA = 280,
    VIRGULA = 281,
    ABRE_PARENTESES = 282,
    FECHA_PARENTESES = 283,
    ABRE_CHAVES = 284,
    FECHA_CHAVES = 285,
    INTEGER_CONST = 286,
    FLOAT_CONST = 287,

    // Palavras reservadas
    INT = 288,
    FLOAT = 289,
    STRING = 290,
    CLASS = 291,
    IF = 292,
    ELSE = 293,
    READ = 294,
    WRITE = 295,
    WHILE = 296,
    DO = 297;

}
