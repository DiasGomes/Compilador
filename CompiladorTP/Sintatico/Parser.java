package CompiladorTP.Sintatico;

import java.io.IOException;

import CompiladorTP.Lexico.Lexer;
import CompiladorTP.Lexico.Tag;
import CompiladorTP.Lexico.Token;

public class Parser {

    private Token tok;
    private Lexer lex;

    public Parser(Lexer l) throws IOException { 
        lex = l; 
        move(); 
    }

    private void move() throws IOException { 
        tok = lex.getToken(); 
    }

    // Consome o token
    private void eat(int tag) throws IOException{
        if(tag == tok.tag){
            move();
        }else{
            erro( Tag.showTag(tag) );
        }
    }

    // Mostra o erro
    private void erro(String msg){
        System.out.println("ERRO: Token esperado: "+msg+", mas obteve "+ Tag.showTag(tok.tag)+ "(linha "+lex.linha+")");
    }


    /* METODOS DA GRAMATICA */
    public void Program() throws IOException{
        // class identifier [decl-list] body
        eat(Tag.CLASS);
        eat(Tag.ID);
        DeclList();
        Body();
    }

    private void DeclList() throws IOException{
        // decl ";" { decl ";"}
    }

    private void Decl() throws IOException{
        // type ident-list
        Type();
        IdentList();
    }

    private void IdentList() throws IOException{
        // identifier {"," identifier}
    }

    private void Type() throws IOException{
        switch(tok.tag){
            case Tag.INT: eat(Tag.INT); break;
            case Tag.STRING: eat(Tag.STRING); break;
            case Tag.FLOAT: eat(Tag.FLOAT); break;
            default: erro("INT, STRING ou FLOAT");
        }
    }

    private void Body() throws IOException{
        eat( Tag.ABRE_CHAVES );
        StmtList();
        eat( Tag.FECHA_CHAVES );
    }

    private void StmtList() throws IOException{}

    private void Stmt() throws IOException{
        // assign-stmt | if-stmt | do-stmt | read-stmt | write-stmt
    }

    private void AssignStmt() throws IOException{
        // identifier "=" simple_expr
    }

    private void IfStmt() throws IOException{
        // if "(" condition ")" "{" stmt-list "}"
        // if "(" condition ")" "{" stmt-list "}" else "{" stmt-list "}"
    }

    private void Condition() throws IOException{
        // expression
        Expression();
    }

    private void DoStmt() throws IOException{
        // do "{" stmt-list "}" do-suffix
        eat(Tag.DO);
        eat( Tag.ABRE_CHAVES );
        StmtList();
        eat( Tag.FECHA_CHAVES );
        DoSuffix();
    }

    private void DoSuffix() throws IOException{
        // while "(" condition ")"
        eat(Tag.WHILE);
        eat( Tag.ABRE_PARENTESES );
        Condition();
        eat( Tag.FECHA_PARENTESES );
    }

    private void ReadStmt() throws IOException{
        // read "(" identifier ")"
        eat(Tag.READ);
        eat( Tag.ABRE_PARENTESES );
        eat(Tag.ID);
        eat( Tag.FECHA_PARENTESES );
    }

    private void WriteStmt() throws IOException{
        // write "(" writable ")"
        eat(Tag.WRITE);
        eat( Tag.ABRE_PARENTESES );
        Writable();
        eat( Tag.FECHA_PARENTESES );
    }

    private void Writable() throws IOException{
        // simple-expr
        SimpleExpr();
    }

    private void Expression() throws IOException{
        // simple-expr | simple-expr relop simple-expr
    }

    private void SimpleExpr() throws IOException{
        // term | simple-expr addop term
    }

    private void Term() throws IOException{
        // factor-a | term mulop factor-a
    }

    private void FactorA() throws IOException{
        // factor | "!" factor | "-" factor
    }

    private void Factor() throws IOException{
        // identifier | constant | "(" expression ")"
        switch(tok.tag){
            case Tag.ID: eat(Tag.ID); break;
            case Tag.FLOAT_CONST: eat(Tag.FLOAT_CONST); break;
            case Tag.INTEGER_CONST: eat(Tag.INTEGER_CONST); break;
            case Tag.STRING_CONST: eat(Tag.STRING_CONST); break;
            case Tag.ABRE_PARENTESES: 
                eat(Tag.ABRE_PARENTESES); Expression(); 
                eat(Tag.FECHA_PARENTESES); break;
            default: erro("ID, CONSTANT ou '('");
        }
    }

    private void Relop() throws IOException{
        // ">" | ">=" | "<" | "<=" | "!=" | "=="
        switch(tok.tag){
            case Tag.GT: eat(Tag.GT); break;
            case Tag.GE: eat(Tag.GE); break;
            case Tag.LT: eat(Tag.LT); break;
            case Tag.LE: eat(Tag.LE); break;
            case Tag.EQ: eat(Tag.EQ); break;
            case Tag.NE: eat(Tag.NE); break;
            default: erro("'>', '>=', '<', '<=', '!=' ou '=='");
        }
    }

    private void Addop() throws IOException{
        // "+" | "-" | "||"
        switch(tok.tag){
            case Tag.SUM: eat(Tag.SUM); break;
            case Tag.SUB: eat(Tag.SUB); break;
            case Tag.OR: eat(Tag.OR); break;
            default: erro("'+', '-', ou '||'");
        }
    }

    private void Mulop() throws IOException{
        // "*" | "/" | "&&"
        switch(tok.tag){
            case Tag.MUL: eat(Tag.MUL); break;
            case Tag.DIV: eat(Tag.DIV); break;
            case Tag.AND: eat(Tag.AND); break;
            default: erro("'*', '/', ou '&&'");
        }
    }
    
}
