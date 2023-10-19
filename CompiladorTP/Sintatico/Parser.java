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
        // Ignora comentarios
        if(tok.tag == -1){
            move();
        }
        //System.out.println(tok);
    }

    // Consome o token
    private void eat(int tag) throws IOException{
        if(tag == tok.tag){
            move();
        }else{
            erro( Tag.showTag(tag) );
            move();
        }
    }

    // Mostra o erro
    private void erro(String msg){
        System.out.println("ERRO: Token esperado: <"+msg+">, mas obteve <"+ Tag.showTag(tok.tag)+"> (linha "+Lexer.linha+")");
    }


    /* METODOS DA GRAMATICA */
    public void ExecParser() throws IOException{
        // Program $
        Program();
        eat(Tag.EOF);
    }

    private void Program() throws IOException{
        //System.out.println("PROGRAM()");
        // class identifier [decl-list] body
        eat(Tag.CLASS);
        eat(Tag.ID);
        DeclList();
        Body();
    }

    private void DeclList() throws IOException{
        //System.out.println("DECL-LIST()");
        // decl ";" { decl ";"}
        Decl();
        eat(Tag.PONTO_VIRGULA);
        if(tok.tag == Tag.INT || tok.tag == Tag.FLOAT || tok.tag == Tag.STRING){
            DeclList();
        }
    }

    private void Decl() throws IOException{
        //System.out.println("DECL()");
        // type ident-list
        Type();
        IdentList();
    }

    private void IdentList() throws IOException{
        //System.out.println("IDENT-LIST()");
        // identifier {"," identifier}
        eat(Tag.ID);
        if(tok.tag == Tag.VIRGULA){
            eat(Tag.VIRGULA);
            IdentList();
        }
    }

    private void Type() throws IOException{
        //System.out.println("TYPE()");
        // int | string | float
        switch(tok.tag){
            case Tag.INT: eat(Tag.INT); break;
            case Tag.STRING: eat(Tag.STRING); break;
            case Tag.FLOAT: eat(Tag.FLOAT); break;
            default: erro("INT, STRING ou FLOAT");
        }
    }

    private void Body() throws IOException{
        //System.out.println("BODY()");
        // "{" stmt-list "}"
        eat( Tag.ABRE_CHAVES );
        StmtList();
        eat( Tag.FECHA_CHAVES );
    }

    private void StmtList() throws IOException{
        //System.out.println("STMT-LIST()");
        // stmt ";" { stmt ";" }
        Stmt();
        eat(Tag.PONTO_VIRGULA);
        if(tok.tag == Tag.ID || tok.tag == Tag.IF || tok.tag == Tag.DO || tok.tag == Tag.READ || tok.tag == Tag.WRITE){
            StmtList();
        }
    }

    private void Stmt() throws IOException{
        //System.out.println("STMT()");
        // assign-stmt | if-stmt | do-stmt | read-stmt | write-stmt
        switch(tok.tag){
            case Tag.ID: AssignStmt(); break;
            case Tag.IF: IfStmt(); break;
            case Tag.DO: DoStmt(); break;
            case Tag.READ: ReadStmt(); break;
            case Tag.WRITE: WriteStmt(); break;
            default: erro("ID, IF, DO, READ ou WRITE");
        }
    }

    private void AssignStmt() throws IOException{
        //System.out.println("ASSIGN-STMT()");
        // identifier "=" simple_expr
        eat(Tag.ID);
        eat(Tag.ATR);
        SimpleExpr();
    }

    private void IfStmt() throws IOException{
        //System.out.println("IF-STMT()");
        // if "(" condition ")" "{" stmt-list "} else-stmt"
        eat(Tag.IF);
        eat( Tag.ABRE_PARENTESES );
        Condition();
        eat( Tag.FECHA_PARENTESES );
        eat( Tag.ABRE_CHAVES );
        StmtList();
        eat( Tag.FECHA_CHAVES );
        ElseStmt();
    }

    private void ElseStmt() throws IOException{
        //System.out.println("ELSE-STMT()");
        // else "{" stmt-list "}" | λ
        switch(tok.tag){
            case Tag.ELSE: 
                eat(Tag.ELSE); eat( Tag.ABRE_CHAVES );
                StmtList(); eat( Tag.FECHA_CHAVES );
                break;
            default: return;
        }
    }

    private void Condition() throws IOException{
        //System.out.println("EXPRESSION()");
        // expression
        Expression();
    }

    private void DoStmt() throws IOException{
        //System.out.println("DO-STMT()");
        // do "{" stmt-list "}" do-suffix
        eat(Tag.DO);
        eat( Tag.ABRE_CHAVES );
        StmtList();
        eat( Tag.FECHA_CHAVES );
        DoSuffix();
    }

    private void DoSuffix() throws IOException{
        //System.out.println("DO-SUFFIX()");
        // while "(" condition ")"
        eat(Tag.WHILE);
        eat( Tag.ABRE_PARENTESES );
        Condition();
        eat( Tag.FECHA_PARENTESES );
    }

    private void ReadStmt() throws IOException{
        //System.out.println("READ-STMT()");
        // read "(" identifier ")"
        eat(Tag.READ);
        eat( Tag.ABRE_PARENTESES );
        eat(Tag.ID);
        eat( Tag.FECHA_PARENTESES );
    }

    private void WriteStmt() throws IOException{
        //System.out.println("WRITE-STMT()");
        // write "(" writable ")"
        eat(Tag.WRITE);
        eat( Tag.ABRE_PARENTESES );
        Writable();
        eat( Tag.FECHA_PARENTESES );
    }

    private void Writable() throws IOException{
        //System.out.println("WRITEBLE()");
        // simple-expr
        SimpleExpr();
    }

    private void Expression() throws IOException{
        //System.out.println("EXPRESSION()");
        // expression ::= simple-expr expression’
        // expression’ ::= relop simple-expr | λ
        SimpleExpr();
        if(tok.tag == Tag.GT || tok.tag == Tag.GE || tok.tag == Tag.LT || tok.tag == Tag.LE  || tok.tag == Tag.EQ  || tok.tag == Tag.NE){
            Relop();
            SimpleExpr();
        }
    }

    private void SimpleExpr() throws IOException{
        //System.out.println("SIMPLE-EXPR()");
        // simple-expr ::= term simple-expr’
        // simple-expr’ ::= addop simple-expr | λ
        Term();
        if(tok.tag == Tag.SUM || tok.tag == Tag.SUB || tok.tag == Tag.OR){
            Addop();
            SimpleExpr();
        }
    }

    private void Term() throws IOException{
        //System.out.println("TERM()");
        // term ::= factor-a term’
        // term’ ::= mulop term| λ
        FactorA();
        if(tok.tag == Tag.MUL || tok.tag == Tag.DIV || tok.tag == Tag.AND){
            Mulop();
            Term();
        }
    }

    private void FactorA() throws IOException{
        //System.out.println("FACTOR-A()");
        // factor | "!" factor | "-" factor
        switch(tok.tag){
            case Tag.SUB: eat(Tag.SUB); Factor(); break;
            case Tag.NEG: eat(Tag.NEG); Factor(); break;
            default: Factor();
        }
    }

    private void Factor() throws IOException{
        //System.out.println("FACTOR()");
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
        //System.out.println("RELOP()");
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
        //System.out.println("ADDOP()");
        // "+" | "-" | "||"
        switch(tok.tag){
            case Tag.SUM: eat(Tag.SUM); break;
            case Tag.SUB: eat(Tag.SUB); break;
            case Tag.OR: eat(Tag.OR); break;
            default: erro("'+', '-', ou '||'");
        }
    }

    private void Mulop() throws IOException{
        //System.out.println("MULOP()");
        // "*" | "/" | "&&"
        switch(tok.tag){
            case Tag.MUL: eat(Tag.MUL); break;
            case Tag.DIV: eat(Tag.DIV); break;
            case Tag.AND: eat(Tag.AND); break;
            default: erro("'*', '/', ou '&&'");
        }
    }
    
}
