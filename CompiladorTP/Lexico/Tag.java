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
    STRING_CONST = 288,

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

}
