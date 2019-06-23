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


        // 计算策略使用桥接
        // 结算结果不使用double

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




    }
}
