package frog.test;

import java.util.Scanner;

import frog.calculator.Calculator;
import frog.calculator.CommonCalculatorConfigure;
import frog.calculator.connect.ICalculatorSession;

public class Bootstrap {

	public static void main(String[] args){
        CommonCalculatorConfigure configure = new CommonCalculatorConfigure();
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
