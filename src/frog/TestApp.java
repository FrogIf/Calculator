package frog;

import frog.calculator.Calculator;

import java.util.Scanner;

public class TestApp {
    public static void main(String[] args){
        // 输出计算过程
        // 函数, 自定义函数
        // registry修改为AVL树

        Scanner sc = new Scanner(System.in);
        Calculator cal = new Calculator();

        while(sc.hasNext()){
            String expression = sc.nextLine();

            if("exit".equals(expression)) {
                System.out.println("bye !");
                break;
            }

            try{
                System.out.println(cal.calculate(expression));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        sc.close();
    }
}
