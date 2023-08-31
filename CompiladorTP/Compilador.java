package CompiladorTP;

import java.io.FileNotFoundException;

import CompiladorTP.Lexico.Lexer;

public class Compilador {

    public static void main(String args[])  throws FileNotFoundException {
        if(args.length > 0){
            System.out.println("Compilando Arquivo "+args[0]);
            new Lexer(args[0]);
        }else{
            System.out.println("Erro: Arquivo não informado");
        }
    }
    
}
