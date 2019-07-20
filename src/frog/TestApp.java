package frog;

import frog.calculator.Calculator;
import frog.calculator.DefaultCalculatorConfigure;
import frog.calculator.ICalculatorConfigure;
import frog.calculator.connect.DefaultCalculatorSessionFactory;
import frog.calculator.connect.ICalculatorSessionFactory;
import frog.calculator.dimpl.DoubleExpressionHolder;
import frog.calculator.connect.ICalculatorSession;

import java.util.Scanner;

public class TestApp {

    private static Calculator calculator;

    private static ICalculatorSessionFactory calculatorSessionFactory;

    private static void init(){
        ICalculatorConfigure calculatorConfigure = new DefaultCalculatorConfigure(new DoubleExpressionHolder());
        calculator = new Calculator(calculatorConfigure);
        calculatorSessionFactory = new DefaultCalculatorSessionFactory(calculatorConfigure.getResolverResultFactory());
    }

    public static void main(String[] args){
        init();
        // 函数, 自定义函数
        // lambda表达式 : @(a, b, c, d, e, f, g, h) -> (a+b+c*d)
        // 输出计算过程
        Scanner sc = new Scanner(System.in);

        ICalculatorSession session = calculatorSessionFactory.createSession();

        calculator.defineFunction("frog(", new String[]{"a", "b"}, ")", "a+b", session, ",");

        while(sc.hasNext()){
            String expression = sc.nextLine();

            if("exit".equals(expression)) {
                System.out.println("bye !");
                break;
            }

            try{
                System.out.println(calculator.calculate(expression, session.getSessionResolver()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        sc.close();
    }
}
