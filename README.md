2020-05-24 01:28:25.315 [main] INFO  c.g.r.tools.compiler.launcher.RedKnifeBanner - Welcome to 
   ___         ____ __     _ ___   
  / _ \___ ___/ / //_/__  (_) _/__ 
 / , _/ -_) _  / ,< / _ \/ / _/ -_)
/_/|_|\__/\_,_/_/|_/_//_/_/_/ \__/ 	version: 0.0.1-SNAPSHOT

2020-05-24 01:28:25.317 [main] INFO   - Copyright(C) gao_xianglong@sina.com
2020-05-24 01:28:25.318 [main] INFO   - Ignite documentation: https://github.com/gaoxianglong/red-knife-compiler
2020-05-24 01:28:25.318 [main] INFO   - OS: Mac OS X
2020-05-24 01:28:25.318 [main] INFO   - JVM information: Java(TM) SE Runtime Environment 13.0.1+9 Oracle Corporation Java HotSpot(TM) 64-Bit Server VM
2020-05-24 01:28:25.321 [main] INFO   - Initial heap size is 256.0MB (usedsize=2.74MB, maxsize=4.0GB)
2020-05-24 01:28:25.322 [main] INFO   - Param: --sourcecode-path:/Users/johngao/Desktop/red-knife-compiler/src/main/resources/codes, --output-path:/var/folders/xb/kq9hds5n0kd3f80f0b_3bsnw0000gn/T/
2020-05-24 01:28:25.322 [main] INFO   - Pid: 2967
2020-05-24 01:28:25.327 [main] INFO  c.g.redknife.tools.compiler.core.RedKnifeCompiler - 
source code:
test.rk:
int value = 10;
void main(){
    int v2 = 10+5*(100+2)/2;
    int v3 = v1+v2/10;
    v2++;
    v3--;
    if(true){
        print("1");
    }else if(true){
        if(true){
            print("2");
        }
    }else{
         print("3");
    }
    println("Hello World");
    for(int i=0;i<10;i++){
        if(i==5){print(i);}
        for(int j=0;j<i;j++){
            println(j);
        }
    }
}

void methodTest(){
    print("Hello World!!!");
}

2020-05-24 01:28:25.330 [main] INFO  c.g.r.tools.compiler.core.parser.RedKnifeParser - red-knife-compiler相关初始化完成...
2020-05-24 01:28:25.335 [main] INFO  c.g.redknife.tools.compiler.core.RedKnifeCompiler - AST:
2020-05-24 01:28:25.336 [main] INFO   - class:ClassDecl	tag:CLASSDEF	name:test
2020-05-24 01:28:25.336 [main] INFO   - 	class:VariableDecl	tag:VARDEF	typeTag:INT	name:value
2020-05-24 01:28:25.336 [main] INFO   - 		class:Literal	typeTag:INT	name:10
2020-05-24 01:28:25.337 [main] INFO   - 	class:MethodDecl	tag:METHODDEF	typeTag:VOID	name:main
2020-05-24 01:28:25.337 [main] INFO   - 		class:Block
2020-05-24 01:28:25.337 [main] INFO   - 			class:VariableDecl	tag:VARDEF	typeTag:INT	name:v2
2020-05-24 01:28:25.337 [main] INFO   - 				class:Binary	tag:PLUS	name:+
2020-05-24 01:28:25.337 [main] INFO   - 					class:Literal	typeTag:INT	name:10
2020-05-24 01:28:25.337 [main] INFO   - 					class:Binary	tag:SLASH	name:/
2020-05-24 01:28:25.338 [main] INFO   - 						class:Binary	tag:STAR	name:*
2020-05-24 01:28:25.338 [main] INFO   - 							class:Literal	typeTag:INT	name:5
2020-05-24 01:28:25.338 [main] INFO   - 							class:Binary	tag:PLUS	name:+
2020-05-24 01:28:25.338 [main] INFO   - 								class:Literal	typeTag:INT	name:100
2020-05-24 01:28:25.338 [main] INFO   - 								class:Literal	typeTag:INT	name:2
2020-05-24 01:28:25.339 [main] INFO   - 						class:Literal	typeTag:INT	name:2
2020-05-24 01:28:25.339 [main] INFO   - 			class:VariableDecl	tag:VARDEF	typeTag:INT	name:v3
2020-05-24 01:28:25.339 [main] INFO   - 				class:Binary	tag:PLUS	name:+
2020-05-24 01:28:25.339 [main] INFO   - 					class:Ident	name:v1
2020-05-24 01:28:25.339 [main] INFO   - 					class:Binary	tag:SLASH	name:/
2020-05-24 01:28:25.340 [main] INFO   - 						class:Ident	name:v2
2020-05-24 01:28:25.340 [main] INFO   - 						class:Literal	typeTag:INT	name:10
2020-05-24 01:28:25.340 [main] INFO   - 			class:ExpressionStatement	tag:POSTINC	name:++
2020-05-24 01:28:25.340 [main] INFO   - 				class:Ident	name:v2
2020-05-24 01:28:25.341 [main] INFO   - 			class:ExpressionStatement	tag:POSTDEC	name:--
2020-05-24 01:28:25.341 [main] INFO   - 				class:Ident	name:v3
2020-05-24 01:28:25.341 [main] INFO   - 			class:If	tag:IF
2020-05-24 01:28:25.341 [main] INFO   - 				class:Literal	typeTag:BOOLEAN	name:true
2020-05-24 01:28:25.341 [main] INFO   - 				class:Block
2020-05-24 01:28:25.342 [main] INFO   - 					class:ExpressionStatement		
2020-05-24 01:28:25.342 [main] INFO   - 						class:Literal	typeTag:STRING	name:"1"
2020-05-24 01:28:25.342 [main] INFO   - 						class:FieldAccess
2020-05-24 01:28:25.342 [main] INFO   - 							class:Ident	name:print
2020-05-24 01:28:25.342 [main] INFO   - 				class:If	tag:ELSEIF
2020-05-24 01:28:25.343 [main] INFO   - 					class:Literal	typeTag:BOOLEAN	name:true
2020-05-24 01:28:25.343 [main] INFO   - 					class:Block
2020-05-24 01:28:25.343 [main] INFO   - 						class:If	tag:IF
2020-05-24 01:28:25.343 [main] INFO   - 							class:Literal	typeTag:BOOLEAN	name:true
2020-05-24 01:28:25.343 [main] INFO   - 							class:Block
2020-05-24 01:28:25.343 [main] INFO   - 								class:ExpressionStatement		
2020-05-24 01:28:25.344 [main] INFO   - 									class:Literal	typeTag:STRING	name:"2"
2020-05-24 01:28:25.344 [main] INFO   - 									class:FieldAccess
2020-05-24 01:28:25.344 [main] INFO   - 										class:Ident	name:print
2020-05-24 01:28:25.344 [main] INFO   - 				class:If	tag:ELSE
2020-05-24 01:28:25.344 [main] INFO   - 					class:Block
2020-05-24 01:28:25.344 [main] INFO   - 						class:ExpressionStatement		
2020-05-24 01:28:25.344 [main] INFO   - 							class:Literal	typeTag:STRING	name:"3"
2020-05-24 01:28:25.345 [main] INFO   - 							class:FieldAccess
2020-05-24 01:28:25.345 [main] INFO   - 								class:Ident	name:print
2020-05-24 01:28:25.345 [main] INFO   - 			class:ExpressionStatement		
2020-05-24 01:28:25.345 [main] INFO   - 				class:Literal	typeTag:STRING	name:"Hello World"
2020-05-24 01:28:25.345 [main] INFO   - 				class:FieldAccess
2020-05-24 01:28:25.345 [main] INFO   - 					class:Ident	name:println
2020-05-24 01:28:25.346 [main] INFO   - 			class:ForDecl	tag:FORLOOP
2020-05-24 01:28:25.346 [main] INFO   - 				class:VariableDecl	tag:VARDEF	typeTag:INT	name:i
2020-05-24 01:28:25.346 [main] INFO   - 					class:Literal	typeTag:INT	name:0
2020-05-24 01:28:25.346 [main] INFO   - 				class:Expression	tag:LT	name:<
2020-05-24 01:28:25.346 [main] INFO   - 					class:Ident	name:i
2020-05-24 01:28:25.346 [main] INFO   - 					class:Literal	typeTag:INT	name:10
2020-05-24 01:28:25.347 [main] INFO   - 				class:ExpressionStatement	tag:POSTINC	name:++
2020-05-24 01:28:25.347 [main] INFO   - 					class:Ident	name:i
2020-05-24 01:28:25.347 [main] INFO   - 				class:Block
2020-05-24 01:28:25.347 [main] INFO   - 					class:If	tag:IF
2020-05-24 01:28:25.347 [main] INFO   - 						class:Expression	tag:EQ	name:==
2020-05-24 01:28:25.347 [main] INFO   - 							class:Ident	name:i
2020-05-24 01:28:25.347 [main] INFO   - 							class:Literal	typeTag:INT	name:5
2020-05-24 01:28:25.348 [main] INFO   - 						class:Block
2020-05-24 01:28:25.348 [main] INFO   - 							class:ExpressionStatement		
2020-05-24 01:28:25.348 [main] INFO   - 								class:Ident	name:i
2020-05-24 01:28:25.348 [main] INFO   - 								class:FieldAccess
2020-05-24 01:28:25.348 [main] INFO   - 									class:Ident	name:print
2020-05-24 01:28:25.348 [main] INFO   - 					class:ForDecl	tag:FORLOOP
2020-05-24 01:28:25.348 [main] INFO   - 						class:VariableDecl	tag:VARDEF	typeTag:INT	name:j
2020-05-24 01:28:25.349 [main] INFO   - 							class:Literal	typeTag:INT	name:0
2020-05-24 01:28:25.349 [main] INFO   - 						class:Expression	tag:LT	name:<
2020-05-24 01:28:25.349 [main] INFO   - 							class:Ident	name:j
2020-05-24 01:28:25.349 [main] INFO   - 							class:Ident	name:i
2020-05-24 01:28:25.349 [main] INFO   - 						class:ExpressionStatement	tag:POSTINC	name:++
2020-05-24 01:28:25.349 [main] INFO   - 							class:Ident	name:j
2020-05-24 01:28:25.350 [main] INFO   - 						class:Block
2020-05-24 01:28:25.350 [main] INFO   - 							class:ExpressionStatement		
2020-05-24 01:28:25.350 [main] INFO   - 								class:Ident	name:j
2020-05-24 01:28:25.350 [main] INFO   - 								class:FieldAccess
2020-05-24 01:28:25.350 [main] INFO   - 									class:Ident	name:println
2020-05-24 01:28:25.350 [main] INFO   - 	class:MethodDecl	tag:METHODDEF	typeTag:VOID	name:methodTest
2020-05-24 01:28:25.351 [main] INFO   - 		class:Block
2020-05-24 01:28:25.351 [main] INFO   - 			class:ExpressionStatement		
2020-05-24 01:28:25.351 [main] INFO   - 				class:Literal	typeTag:STRING	name:"Hello World!!!"
2020-05-24 01:28:25.351 [main] INFO   - 				class:FieldAccess
2020-05-24 01:28:25.351 [main] INFO   - 					class:Ident	name:print
