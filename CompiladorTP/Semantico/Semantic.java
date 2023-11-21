package CompiladorTP.Semantico;

import CompiladorTP.Lexico.Lexer;

public class Semantic {

    private Lexer lex;

    public Semantic(Lexer l){
        lex = l;
    }

    // adiciona o tipo da variável
    public void setIdType(char type){
        if(lex.current.getType() == null){
            lex.current.setType(type);
        }else{
            // se o tipo já existe é por que já foi declarada
            erroDecl(lex.current.getLexeme());
        }
    }

    // Verifica tipo para operação com mulop
    public char checkTermRule(char c1, char c2, boolean div){
        // operação inválida para string e tipos diferentes
        if(c1 != c2 || c1 == 's' || c2 == 's'){
            erroType();
            return 'e';
        }else{
            // se for divisão retorna float
            if(div){
                return 'f';
            }
            return c1;
        }
    }

    // Verifica tipo para operação com mulop
    public char checkSimpleExprRule(char c1, char c2, boolean add){
        // operação inválida para tipos diferentes ou strings sem operador de soma
        if( (c1 != c2) || (!add && c1 == 's')){
            erroType();
            return 'e';
        }
        return c1;
    }

    public char checkTypes(char c1, char c2){
        if(c1 != c2){
            erroType();
            return 'e';
        }
        return c1;
    }

    public char checkRelopExpression(char c1, char c2){
        if(c1 != c2){
            erroType();
            return 'e';
        }
        return 'b';
    }

    public void checkCondition(char c){
        if(c != 'b'){
            erroCondition();
        }
    }

    public char checkAssign(String var, char c1, char c2){
        if(c1 == 'e'){
            erroNotDecl(var);
            return 'e';
        }else if(c1 != c2){
            erroType();
            return 'e';
        }
        return c1;
    }

    public char isDecl(){
        if(lex.current.getType() != null){
            return lex.current.getTypeAsChar();
        } 
        erroNotDecl(lex.current.getLexeme());
        return 'e';
    }

    private void erroType(){
        System.out.println("ERRO: Tipo invalido (linha "+Lexer.linha+")");
    }

    private void erroCondition(){
        System.out.println("ERRO: Condicao invalida (linha "+Lexer.linha+")");
    }

    private void erroDecl(String var){
        System.out.println("ERRO: Variavel '"+var+"' ja declarada (linha "+Lexer.linha+")");
    }

    private void erroNotDecl(String var){
        System.out.println("ERRO: Variavel '"+var+"' nao declarada (linha "+Lexer.linha+")");
    }

}
