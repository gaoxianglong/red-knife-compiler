int v1 = (10 + 2) * 10 / 9 + 1 * (3 + 1);
v1++;
v1--;
println(v1);
v1 = 100;
if(true){if(true){}else{}}else if(true){}else{print("");}//流程控制语句
//循环控制语句
//九九乘法表打印
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
float v5 = 100 * 75.4 + v1;
println(v5);
println("测试ABC~");