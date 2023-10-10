package CompiladorTP;

import java.io.FileNotFoundException;
import java.io.IOException;

import CompiladorTP.Lexico.Lexer;
import CompiladorTP.Lexico.Token;
import CompiladorTP.Sintatico.Parser;

public class Compilador {

    private static Token token;
    private static Lexer lexer;
    private static Parser parser;

    public static void main(String args[])  throws IOException {
        if(args.length > 0){
            System.out.println("\nCompilando o arquivo "+args[0]+"\n");
            lexer = new Lexer(args[0]);
            if(args.length > 1){
                if(args[1].equalsIgnoreCase("tabela")){
                    lexer.showTable();
                }
            }
            parser = new Parser(lexer);
            parser.Program();
        }else{
            System.out.println("Erro: Arquivo não informado");
        }
    }



    // metodo usado para avaliar funcionamento do analisador lexico
    private static void showTokens(){
        System.out.println("TOKENS");
        System.out.println("----------------------");
        do {
                try {
                    token = lexer.getToken();
                    if(token.tag != 65535 && token.tag > 0){
                        System.out.println(token.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            } while(token.tag != 65535);
        System.out.println("=============================================================\n");
    }
    
}
