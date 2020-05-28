执行方式:
```java
java com.github.redknife.tools.compiler.launcher.Main -d -e --in path --out path
```

文法规则:
```java
 * parse -> block
 * ifDecl -> 'if' '(' expression ')' block ('else' 'if' '(' expression ')' block)* else block
 * forDecl -> 'for' '(' variableDecl expression ';' expressionStatement')' block
 * expression -> primary | primary ('<' | '>' | '<=' | '>=' | '!=' | '==') primary
 * block -> '{' variableDecl expressionStatement';' forDecl ifDecl '}'
 * <p>
 * variableDecl -> intDecl | charsDecl | booleanDecl | assignmentStatement
 * assignmentStatement -> id '=' additive ';'
 * expressionStatement -> primary(++ | --) | primary ('.'primary)* '('primary')'
 * intDecl -> 'int' id '=' additive ';'
 * charsDecl -> 'chars' id '=' additive ';'
 * booleanDecl -> 'boolean' id '=' additive ';'
 * additive -> multiplicative ((+ | -) multiplicative)*
 * multiplicative -> primary ((* | /) primary)*
 * primary -> 0-9 | id | (additive)
 * id -> identifier
```
脚本示例:
```java
int v1 = (10 + 2) * 10 / 9 + 1 * (3 + 1);
v1++;
v1--;
println(v1);
if(true){if(true){}else{}}else if(true){}else{print("");}

for(int i=1;i<=9;i++){
    for(int j=1;j<=i;j++){
        print(i);
        print("*");
        print(j);
        print("=");
        int temp = i * j;
        print(temp);
        print(" ");
    }
    println("");
}

bool v3 = true;
println(v4);
chars v4 = "Hello World";
println(v4);
```

