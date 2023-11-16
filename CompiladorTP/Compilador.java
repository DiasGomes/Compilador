package CompiladorTP;

//import java.io.FileNotFoundException;
import java.io.IOException;
import CompiladorTP.Lexico.Lexer;
import CompiladorTP.Sintatico.Parser;

public class Compilador {

    private static Lexer lexer;
    private static Parser parser;

    public static void main(String args[])  throws IOException {
        if(args.length > 0){
            System.out.println("\nCompilando o arquivo "+args[0]+"\n");
            lexer = new Lexer(args[0]);
            parser = new Parser(lexer);
            parser.ExecParser();
            System.out.println("============ Fim de analise! ==========\n");
            if(args.length > 1){
                if(args[1].equalsIgnoreCase("tabela")){
                    lexer.showTable();
                }
            }
        }else{
            System.out.println("Erro: Arquivo não informado");
        }
    }
    
}
