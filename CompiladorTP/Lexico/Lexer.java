package CompiladorTP.Lexico;

import java.io.*;
import java.util.*;

public class Lexer {

    public static int linha = 1; // contador de linhas
    private char ch = ' '; // caractere lido do arquivo
    private FileReader file;
    private Hashtable<String, Word> words = new Hashtable<String, Word>();

    /* Método para inserir palavras reservadas na HashTable */
    private void reserve(Word w) {
        words.put(w.getLexeme(), w); // lexema é a chave para entrada na
        // HashTable
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
            }
            // comentário de multiplas linhas
            else if (ch == '*') {
                boolean terminou = false;
                boolean asterisco = false;
                while(terminou){
                    readch();
                    if(asterisco){
                        asterisco = false;
                        if(ch == '/'){
                            terminou = true;
                        }
                    }else{
                        if(ch == '*'){
                            asterisco = true;
                        }
                    }
                }
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
                    System.out.println("ERRO: < Caractere '!' não reconhecido na linha "+ linha +" >");
                    ch = ' ';
                    return new Token('&');
            case '|':
                if (readch('|'))
                    return Word.or;
                else
                    // REPORTAR ERRO
                    System.out.println("ERRO: < Caractere '!' não reconhecido na linha "+ linha +" >");
                    ch = ' ';
                    return new Token('|');
            case '=':
                if (readch('='))
                    return Word.eq;
                else
                    ch = ' ';
                    return new Token('=');
            case '<':
                if (readch('='))
                    return Word.le;
                else
                    ch = ' ';
                    return new Token('<');
            case '>':
                if (readch('='))
                    return Word.ge;
                else
                    ch = ' ';
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
                    // REPORTAR ERRO
                    System.out.println("ERRO: < Caractere '!' não reconhecido na linha "+ linha +" >");
                    ch = ' ';
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
                ch = ' ';
                StringBuffer sb = new StringBuffer();
                while (ch != '"') {
                    sb.append(ch);
                    readch();
                }
                String s = sb.toString();
                Word w = new Word(s, Tag.STRING_CONST);
                words.put(s, w);
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
            return new IntegerConst(value);
        }

        // Identificadores
        if (Character.isLetter(ch)) {
            StringBuffer sb = new StringBuffer();
            do {
                sb.append(ch);
                readch();
            } while (Character.isLetterOrDigit(ch));
            String s = sb.toString();
            Word w = (Word) words.get(s);
            if (w != null)
                return w; // palavra já existe na HashTable
            w = new Word(s, Tag.ID);
            words.put(s, w);
            return w;
        }
        // fim do arquivo
        if(ch == 65535){
            System.out.println("---------------------------------------------------------------");
        }else{
            // REPORTAR ERRO
            System.out.println("ERRO: < Caractere '"+ ch +"' não reconhecido na linha "+ linha +" >");
        }
        // Caracteres não especificados
        Token t = new Token(ch);
        ch = ' ';
        return t;
    }
}