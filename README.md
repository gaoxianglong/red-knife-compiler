执行方式:
```java
java com.github.redknife.tools.compiler.launcher.Main -d -e --in path --out path
```
参数解释：
```java
java com.github.redknife.tools.compiler.launcher.Main -h
```
```java
操作和入参:
	-h -help --help               打印使用规则
	-d -debug --debug             开启调试信息, 缺省关闭
	-e -execute --execute         编译结束是否立即运行, 缺省不运行
	--in           <value>        源代码目录地址
	--out          <value>        中间代码的输出目录地址, 缺省为操作系统临时目录下
	--version      <value>        输出当前版本号
```
文法规则:
<font color=#90EE90>
 * parse -> block
 * ifDecl -> 'if' '(' expression ')' block ('else' 'if' '(' expression ')' block)* else block
 * forDecl -> 'for' '(' variableDecl expression ';' expressionStatement')' block
 * expression -> primary | primary ('<' | '>' | '<=' | '>=' | '!=' | '==') primary
 * block -> '{' variableDecl expressionStatement';' forDecl ifDecl '}'
 * variableDecl -> intDecl | charsDecl | booleanDecl | assignmentStatement
 * assignmentStatement -> id '=' additive ';'
 * expressionStatement -> primary(++ | --) | primary ('.'primary)* '('primary')'
 * intDecl -> 'int' id '=' additive ';'
 * charsDecl -> 'chars' id '=' additive ';'
 * boolDecl -> 'bool' id '=' additive ';'
 * additive -> multiplicative ((+ | -) multiplicative)*
 * multiplicative -> primary ((* | /) primary)*
 * primary -> 0-9 | id | (additive) | true | false
 * id -> identifier
</font>

脚本示例(test.rs):
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
println(v3);
chars v4 = "Hello World";
println(v4);
```