import java.io.FileNotFoundException;

public class Compilador {

    public static void main(String args[])  throws FileNotFoundException {
        if(args.length > 0){
            new Lexer(args[0]);
        }else{
            System.out.println("Erro: Arquivo não informado");
        }
    }
    
}
