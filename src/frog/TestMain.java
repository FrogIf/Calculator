package frog;

import frog.calculator.Calculator;

import java.util.Scanner;

public class TestMain {
    public static void main(String[] args){
//        String expression = "1 + 1 * 3 / 3 + 4 + 4";
//        String expression = "1 + 2 + 3 + 4 + 5";
//        String expression = "1.4 + 12 * 2 / 3 / 4 * 5 - 6 + 7 * 8 / 9";
//        String expression = "1 + 2 * 3 + 4 * 5";


        // 输出计算过程
        // 括号
        // 函数, 自定义函数


        /*
         * 每新增一个运算符, 需要修改的地方太多, 需要优化
         * 需要修改的地方:
         *      新增IExpression, 新增IOperator(这块不变, 因为目的就是将表达与运算解耦)
         *      修改Discriminator, 修改Resolver(这两块放到一起)
         */

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
