package CompiladorTP;

import java.io.FileNotFoundException;
import java.io.IOException;

import CompiladorTP.Lexico.Lexer;
import CompiladorTP.Lexico.Token;

public class Compilador {

    private static Token token;
    private static Lexer lexer;

    public static void main(String args[])  throws FileNotFoundException {
        if(args.length > 0){
            System.out.println("\nCompilando o arquivo "+args[0]+"\n");
            lexer = new Lexer(args[0]);
            showTokens();
            if(args.length > 1){
                if(args[1].equalsIgnoreCase("tabela")){
                    lexer.showTable();
                }
            }
        }else{
            System.out.println("Erro: Arquivo não informado");
        }
    }

    private static void showTokens(){
        //System.out.println("TOKENS");
        //System.out.println("----------------------");
        do {
                try {
                    token = lexer.getToken();
                    /*
                    if(token.tag != 65535 && token.tag > 0){
                        System.out.println(token.toString());
                    }
                     */
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            } while(token.tag != 65535);
            System.out.println("=============================================================\n");
    }
    
}
