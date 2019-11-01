package test;

import frog.calculator.Calculator;
import frog.calculator.DefaultCalculatorConfigure;
import frog.calculator.ICalculatorManager;
import frog.calculator.OriginExpressionHolder;
import frog.calculator.connect.ICalculatorSession;

import java.util.Scanner;

public class TestApp {

    private static Calculator calculator;

    private static void init(){
        DefaultCalculatorConfigure configure = new DefaultCalculatorConfigure();
        configure.setExpressionHolder(new OriginExpressionHolder());
        calculator = new Calculator(configure);
    }

    public static void main(String[] args){
        init();
        // +, avg, number


        // 交错矩阵怎么办?
        // 创建operator传参对象
        // 创建list表达式

        // 自定义函数优化

        // lambda表达式 : @frog(a, b, c, d, e, f, g, h) -> {a+b+c*d}
        //      @ - 声明表达式; frog - 变量表达式; () - 形参表达式; a, b, c - 变量表达式
        // 输出计算过程
        // 自定义异常

        Scanner sc = new Scanner(System.in);

        ICalculatorManager manager = calculator.getCalculatorManager();
        ICalculatorSession session = manager.createCalculatorSession();

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
