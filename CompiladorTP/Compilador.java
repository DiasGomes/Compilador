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
            System.out.println("Compilando Arquivo "+args[0]);
            lexer = new Lexer(args[0]);
            showTokens();
        }else{
            System.out.println("Erro: Arquivo não informado");
        }
    }

    private static void showTokens(){
       do {
            try {
                token = lexer.getToken();
                System.out.println(token.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } while(token.tag != 65535);
    }
    
}
