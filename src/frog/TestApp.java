package frog;

import frog.calculator.Calculator;
import frog.calculator.DefaultCalculatorConfigure;
import frog.calculator.ICalculatorConfigure;
import frog.calculator.connect.DefaultCalculatorSession;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.dimpl.DoubleExpressionHolder;

import java.util.Scanner;

public class TestApp {

    private static Calculator calculator;

    private static void init(){
        ICalculatorConfigure calculatorConfigure = new DefaultCalculatorConfigure(new DoubleExpressionHolder());
        calculator = new Calculator(calculatorConfigure);
    }

    public static void main(String[] args){
        init();
        // 创建list类
        // 创建operator传参对象
        // 创建list表达式

        // 自定义函数优化

        // lambda表达式 : @frog(a, b, c, d, e, f, g, h) -> {a+b+c*d}
        //      @ - 声明表达式; frog - 变量表达式; () - 形参表达式; a, b, c - 变量表达式
        // 输出计算过程
        // 自定义异常
        
        Scanner sc = new Scanner(System.in);

        ICalculatorSession session = new DefaultCalculatorSession();

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
