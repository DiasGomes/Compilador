# Compilador
Implementação de um Compilador inspirada no livro "Compiladores Princípios, técnicas e ferramentas 2a edição"


# Gramática da linguagem:

program ::= class identifier [decl-list] body <br />
decl-list ::= decl ";" { decl ";"} <br />
decl ::= type ident-list <br />
ident-list ::= identifier {"," identifier} <br />
type ::= int | string | float <br />
body ::= "{" stmt-list "}" <br />
stmt-list ::= stmt ";" { stmt ";" } <br />
stmt ::= assign-stmt | if-stmt | do-stmt <br />
| read-stmt | write-stmt <br />
assign-stmt ::= identifier "=" simple_expr <br />
if-stmt ::= if "(" condition ")" "{" stmt-list "}" else-stmt <br />
else-stm ::== else "{" stmt-list "}" | λ <br />
condition ::= expression <br />
do-stmt ::= do "{" stmt-list "}" do-suffix <br />
do-suffix ::= while "(" condition ")" <br />
read-stmt ::= read "(" identifier ")" <br />
write-stmt ::= write "(" writable ")" <br />
writable ::= simple-expr <br />
expression ::= simple-expr expression’ <br />
expression’ ::= relop simple-expr| λ <br />
simple-expr ::= term simple-expr’<br />
simple-expr’ ::= addop simple-expr | λ <br />
term ::= factor-a term’ <br />
term’ ::= mulop term| λ <br />
factor-a ::= factor | "!" factor | "-" factor <br />
factor ::= identifier | constant | "(" expression ")" <br />
relop ::= ">" | ">=" | "<" | "<=" | "!=" | "==" <br />
addop ::= "+" | "-" | "||" <br />
mulop ::= "*" | "/" | "&&” <br />

# Padrão de formação dos tokens
constant → integer_const | literal | real_const <br />
integer_const → nonzero digit* | 0 <br />
real_const → interger_const "." digit+ <br />
literal → " “ " caractere* " ” " <br />
identifier → letter {letter | digit | " _ " } <br />
letter → [A-Za-z] <br />
digit → [0-9] <br />
nonzero → [1-9] <br />
caractere → um dos 256 caracteres do conjunto ASCII, exceto as aspas e quebra de linha <br />
