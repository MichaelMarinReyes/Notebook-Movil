#! /bin/bash

echo "STARTING LEXER"
java -jar jflex-full-1.9.1.jar Lexer.flex

echo "STARTING PARSER"
java -jar java-cup-11b.jar -parser Parser -symbols ParserSym Parser.cup
