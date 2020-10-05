package frog.test;

import frog.calculator.Calculator;
import frog.calculator.CommonCalculatorConfigure;
import frog.calculator.connect.ICalculatorSession;

import java.util.Scanner;

public class TestApp {

    public static void main(String[] args){
        CommonCalculatorConfigure configure = new CommonCalculatorConfigure();
        configure.setPrecision(20);
        configure.setOutputDecimal(false);
        Calculator calculator = new Calculator(configure);

        Scanner sc = new Scanner(System.in);

        System.out.println("**********Calculator************");

        ICalculatorSession session = calculator.getSession();

        while(sc.hasNext()){
            String expression = sc.nextLine();
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
