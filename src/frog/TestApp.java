package frog;

import frog.calculator.Calculator;
import frog.calculator.operater.oprimpl.dimpl.DoubleBuilderPrototypeHolder;

import java.util.Scanner;

public class TestApp {
    public static void main(String[] args){
        // 输出计算过程
        // 函数, 自定义函数
        Scanner sc = new Scanner(System.in);
        Calculator cal = new Calculator(new DoubleBuilderPrototypeHolder());
//        Calculator cal = new Calculator(new StringBuilderPrototypeHolder());
//        Calculator cal = new Calculator(new StepBuilderPrototypeHolder());

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
