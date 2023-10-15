package CompiladorTP.Lexico;

import java.io.*;
import java.util.*;

public class Lexer {

    public static int linha = 1; // contador de linhas
    private char ch = ' '; // caractere lido do arquivo
    private FileReader file;
    private Hashtable<String, Word> words = new Hashtable<String, Word>();
    private final Token TOKEN_IGNORA = new Token(-1);

    public void showTable(){
        System.out.println("TABELA DE SIMBOLOS");
        System.out.println("PALAVRA | TIPO");
        System.out.println("----------------------");
        for( Map.Entry<String, Word> entry : words.entrySet() ){
            System.out.println( entry.getKey() + " = " + entry.getValue() );
        }
        System.out.println("=======================");
    }

    /* Método para inserir palavras reservadas na HashTable */
    private void reserve(Word w) {
        // lexema é a chave para entrada na HashTable
        words.put(w.getLexeme(), w); 
    }

    /* Método construtor */
    public Lexer(String fileName) throws FileNotFoundException {
        try {
            file = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
            throw e;
        }
        // Insere palavras reservadas na HashTable
        reserve(new Word("int", Tag.INT));
        reserve(new Word("float", Tag.FLOAT));
        reserve(new Word("string", Tag.STRING));
        reserve(new Word("class", Tag.CLASS));
        reserve(new Word("if", Tag.IF));
        reserve(new Word("else", Tag.ELSE));
        reserve(new Word("read", Tag.READ));
        reserve(new Word("write", Tag.WRITE));
        reserve(new Word("while", Tag.WHILE));
        reserve(new Word("do", Tag.DO));
    }

    private Token erroMsg(String msg){
        System.out.println("ERRO: < "+msg+" >"); 
        Token t = new Token(ch);
        ch = ' ';
        return t;
    }

    private Token erro(char c){
        int c_num = (int) c;
        System.out.println("ERRO: < Caractere {'"+ c +"', "+ c_num +"} nao reconhecido > (linha "+ linha +")");
        ch = ' ';
        return new Token(c);
    }

    /* Lê o próximo caractere do arquivo */
    private void readch() throws IOException {
        ch = (char) file.read();
    }

    /* Lê o próximo caractere do arquivo e verifica se é igual a c */
    private boolean readch(char c) throws IOException {
        readch();
        if (ch != c)
            return false;
        ch = ' ';
        return true;
    }

    public Token getToken() throws IOException {
        // Desconsidera delimitadores na entrada
        for (;; readch()) {
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b')
                continue;
            else if (ch == '\n')
                linha++; // conta linhas
            else
                break;
        }

        // tratando divisão e comentarios
        if (ch == '/') {
            readch();
            // comentário de uma linha
            if(ch == '/'){
                while(ch != '\n'){
                    readch();
                }
                linha++;
                ch = ' ';
                //System.out.println("// comentario de uma linha");
                return TOKEN_IGNORA;
            }
            // comentário de multiplas linhas
            else if (ch == '*') {
                boolean terminou = false;
                boolean asterisco = false;
                while(!terminou){
                    readch();
                    switch(ch){
                        // verifica fim de arquivo
                        case Tag.EOF: 
                            terminou = true;  
                            erroMsg("Comentario nao foi fechado"); 
                        // verifica termino de comentario
                        case '*': asterisco = true; break;
                        case '/':   if(asterisco){
                                        //System.out.println("/* Comentario de multiplas linhas */");
                                        terminou = true;
                                    }else{
                                        asterisco = false; 
                                    } break;
                        case '\n': linha++; break;
                        default: asterisco = false;
                    }
                }
                ch = ' ';
                return TOKEN_IGNORA;
            }
            // divisão
            else
                return new Token('/');
        }
        
        // caracteres gerais
        switch (ch) {
            // Operadores
            case '&':
                if (readch('&'))
                    return Word.and;
                else
                    // REPORTAR ERRO
                    erro('&');
            case '|':
                if (readch('|'))
                    return Word.or;
                else
                    // REPORTAR ERRO
                    erro('|');
            case '=':
                if (readch('='))
                    return Word.eq;
                else
                    return new Token('=');
            case '<':
                if (readch('='))
                    return Word.le;
                else
                    return new Token('<');
            case '>':
                if (readch('='))
                    return Word.ge;
                else
                    return new Token('>');
            case '+':
                ch = ' ';
                return new Token('+');
            case '-':
                ch = ' ';
                return new Token('-');
            case '*':
                ch = ' ';
                return new Token('*');
            case '!':
                if (readch('='))
                    return Word.ne;
                else{
                    return new Token('!');
                }
            case ';':
                ch = ' ';
                return new Token(';');
            case ',':
                ch = ' ';
                return new Token(',');  
            case '(':
                ch = ' ';
                return new Token('(');
            case ')':
                ch = ' ';
                return new Token(')');  
            case '{':
                ch = ' ';
                return new Token('{');
            case '}':
                ch = ' ';
                return new Token('}');   
            case '"':
                ch = 0;
                StringBuffer sb = new StringBuffer();
                while (ch != '"') {
                    // não fechou string
                    if(ch == Tag.EOF){
                        erroMsg("STRING nao finalizada");
                    }
                    sb.append(ch);
                    readch();
                }
                String s = sb.toString();
                Word w = new Word(s, Tag.STRING_CONST);
                //words.put(s, w);
                ch = ' ';
                return w;
                
        }

        // Números
        if (Character.isDigit(ch)) {
            int value = 0;
            do {
                value = 10 * value + Character.digit(ch, 10);
                readch();
            } while (Character.isDigit(ch));
            // Número Decimal
            if(ch == '.'){
                double value_dec = 0;
                int qtd_casas = 1;
                readch();
                while (Character.isDigit(ch)) {
                    value_dec += (Character.digit(ch, 10)) / (Math.pow(10, qtd_casas));
                    qtd_casas++;
                    readch();
                }
                return new FloatConst(value+value_dec);
            }
            return new IntegerConst(value);
        }

        // Identificadores
        if (Character.isLetter(ch)) {
            StringBuffer sb = new StringBuffer();
            do {
                sb.append(ch);
                readch();
            } while (Character.isLetterOrDigit(ch) || ch == '_');
            String s = sb.toString();
            Word w = (Word) words.get(s);
            if (w != null)
                return w; // palavra já existe na HashTable
            w = new Word(s, Tag.ID);
            words.put(s, w);
            return w;
        }

        // Caractere desconhecido (diferente de EOF)
        if(ch != Tag.EOF){
            // REPORTAR ERRO
            erro(ch);
        }

        // Caracteres não especificados
        Token t = new Token(ch);
        ch = ' ';
        return t;
    }
}
