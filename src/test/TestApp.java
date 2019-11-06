package test;

import frog.calculator.Calculator;
import frog.calculator.CalculatorStater;
import frog.calculator.connect.ICalculatorSession;

import java.util.Scanner;

public class TestApp {

    public static void main(String[] args){
        Calculator calculator = CalculatorStater.start();
        // 自定义函数, lambda表达式

        // 输出计算过程
        // 自定义异常

        Scanner sc = new Scanner(System.in);

        ICalculatorSession session = calculator.getSession();

        while(sc.hasNext()){
            String expression = sc.nextLine();
            expression = expression.replaceAll(" " , "");
            if("exit".equals(expression)) {
                System.out.println("bye !");
                break;
            }

            try{
                System.out.println(calculator.calculate(expression, session));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        sc.close();
    }
}
